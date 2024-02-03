package org.flywaydb.core.internal.command;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationState;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.callback.FlywayCallback;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.api.resolver.MigrationExecutor;
import org.flywaydb.core.api.resolver.MigrationResolver;
import org.flywaydb.core.api.resolver.ResolvedMigration;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.info.MigrationInfoImpl;
import org.flywaydb.core.internal.info.MigrationInfoServiceImpl;
import org.flywaydb.core.internal.schemahistory.SchemaHistory;
import org.flywaydb.core.internal.sqlscript.FlywaySqlScriptException;
import org.flywaydb.core.internal.sqlscript.SqlStatement;
import org.flywaydb.core.internal.util.StopWatch;
import org.flywaydb.core.internal.util.StringUtils;
import org.flywaydb.core.internal.util.TimeFormat;
import org.flywaydb.core.internal.util.jdbc.TransactionTemplate;
import org.flywaydb.core.internal.util.scanner.Resource;

public class DbMigrate {
   private static final Log LOG = LogFactory.getLog(DbMigrate.class);
   private final Database database;
   private final SchemaHistory schemaHistory;
   private final Schema schema;
   private final MigrationResolver migrationResolver;
   private final FlywayConfiguration configuration;
   private final List<FlywayCallback> effectiveCallbacks;
   private final Connection connectionUserObjects;

   public DbMigrate(Database database, SchemaHistory schemaHistory, Schema schema, MigrationResolver migrationResolver, FlywayConfiguration configuration, List<FlywayCallback> effectiveCallbacks) {
      this.database = database;
      this.connectionUserObjects = database.getMigrationConnection();
      this.schemaHistory = schemaHistory;
      this.schema = schema;
      this.migrationResolver = migrationResolver;
      this.configuration = configuration;
      this.effectiveCallbacks = effectiveCallbacks;
   }

   public int migrate() throws FlywayException {
      try {
         Iterator var1 = this.effectiveCallbacks.iterator();

         while(var1.hasNext()) {
            final FlywayCallback callback = (FlywayCallback)var1.next();
            (new TransactionTemplate(this.connectionUserObjects.getJdbcConnection())).execute(new Callable<Object>() {
               public Object call() {
                  DbMigrate.this.connectionUserObjects.changeCurrentSchemaTo(DbMigrate.this.schema);
                  callback.beforeMigrate(DbMigrate.this.connectionUserObjects.getJdbcConnection());
                  return null;
               }
            });
         }

         StopWatch stopWatch = new StopWatch();
         stopWatch.start();
         this.schemaHistory.create();
         int count = this.configuration.isGroup() ? (Integer)this.schemaHistory.lock(new Callable<Integer>() {
            public Integer call() {
               return DbMigrate.this.migrateAll();
            }
         }) : this.migrateAll();
         stopWatch.stop();
         this.logSummary(count, stopWatch.getTotalTimeMillis());
         Iterator var3 = this.effectiveCallbacks.iterator();

         while(var3.hasNext()) {
            final FlywayCallback callback = (FlywayCallback)var3.next();
            (new TransactionTemplate(this.connectionUserObjects.getJdbcConnection())).execute(new Callable<Object>() {
               public Object call() {
                  DbMigrate.this.connectionUserObjects.changeCurrentSchemaTo(DbMigrate.this.schema);
                  callback.afterMigrate(DbMigrate.this.connectionUserObjects.getJdbcConnection());
                  return null;
               }
            });
         }

         int var10 = count;
         return var10;
      } finally {
         this.connectionUserObjects.restoreCurrentSchema();
      }
   }

   private int migrateAll() {
      int total = 0;

      int count;
      do {
         final boolean firstRun = total == 0;
         count = this.configuration.isGroup() ? this.migrateGroup(firstRun) : (Integer)this.schemaHistory.lock(new Callable<Integer>() {
            public Integer call() {
               return DbMigrate.this.migrateGroup(firstRun);
            }
         });
         total += count;
      } while(count != 0);

      return total;
   }

   private Integer migrateGroup(boolean firstRun) {
      MigrationInfoServiceImpl infoService = new MigrationInfoServiceImpl(this.migrationResolver, this.schemaHistory, this.configuration.getTarget(), this.configuration.isOutOfOrder(), true, true, true);
      infoService.refresh();
      MigrationInfo current = infoService.current();
      MigrationVersion currentSchemaVersion = current == null ? MigrationVersion.EMPTY : current.getVersion();
      if (firstRun) {
         LOG.info("Current version of schema " + this.schema + ": " + currentSchemaVersion);
         if (this.configuration.isOutOfOrder()) {
            LOG.warn("outOfOrder mode is active. Migration of schema " + this.schema + " may not be reproducible.");
         }
      }

      MigrationInfo[] future = infoService.future();
      if (future.length > 0) {
         List<MigrationInfo> resolved = Arrays.asList(infoService.resolved());
         Collections.reverse(resolved);
         if (resolved.isEmpty()) {
            LOG.warn("Schema " + this.schema + " has version " + currentSchemaVersion + ", but no migration could be resolved in the configured locations !");
         } else {
            Iterator var7 = resolved.iterator();

            while(var7.hasNext()) {
               MigrationInfo migrationInfo = (MigrationInfo)var7.next();
               if (migrationInfo.getVersion() != null) {
                  LOG.warn("Schema " + this.schema + " has a version (" + currentSchemaVersion + ") that is newer than the latest available migration (" + migrationInfo.getVersion() + ") !");
                  break;
               }
            }
         }
      }

      MigrationInfo[] failed = infoService.failed();
      if (failed.length > 0) {
         if (failed.length != 1 || failed[0].getState() != MigrationState.FUTURE_FAILED || !this.configuration.isIgnoreFutureMigrations()) {
            if (failed[0].getVersion() == null) {
               throw new FlywayException("Schema " + this.schema + " contains a failed repeatable migration (" + failed[0].getDescription() + ") !");
            } else {
               throw new FlywayException("Schema " + this.schema + " contains a failed migration to version " + failed[0].getVersion() + " !");
            }
         }

         LOG.warn("Schema " + this.schema + " contains a failed future migration to version " + failed[0].getVersion() + " !");
      }

      LinkedHashMap<MigrationInfoImpl, Boolean> group = new LinkedHashMap();
      MigrationInfoImpl[] var15 = infoService.pending();
      int var9 = var15.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         MigrationInfoImpl pendingMigration = var15[var10];
         boolean isOutOfOrder = pendingMigration.getVersion() != null && pendingMigration.getVersion().compareTo(currentSchemaVersion) < 0;
         group.put(pendingMigration, isOutOfOrder);
         if (!this.configuration.isGroup()) {
            break;
         }
      }

      if (!group.isEmpty()) {
         this.applyMigrations(group);
      }

      return group.size();
   }

   private void logSummary(int migrationSuccessCount, long executionTime) {
      if (migrationSuccessCount == 0) {
         LOG.info("Schema " + this.schema + " is up to date. No migration necessary.");
      } else {
         if (migrationSuccessCount == 1) {
            LOG.info("Successfully applied 1 migration to schema " + this.schema + " (execution time " + TimeFormat.format(executionTime) + ")");
         } else {
            LOG.info("Successfully applied " + migrationSuccessCount + " migrations to schema " + this.schema + " (execution time " + TimeFormat.format(executionTime) + ")");
         }

      }
   }

   private void applyMigrations(final LinkedHashMap<MigrationInfoImpl, Boolean> group) {
      boolean executeGroupInTransaction = this.isExecuteGroupInTransaction(group);
      final StopWatch stopWatch = new StopWatch();

      try {
         if (executeGroupInTransaction) {
            (new TransactionTemplate(this.connectionUserObjects.getJdbcConnection())).execute(new Callable<Object>() {
               public Object call() {
                  DbMigrate.this.doMigrateGroup(group, stopWatch);
                  return null;
               }
            });
         } else {
            this.doMigrateGroup(group, stopWatch);
         }

      } catch (DbMigrate.FlywayMigrateSqlException var8) {
         MigrationInfoImpl migration = var8.getMigration();
         String failedMsg = "Migration of " + this.toMigrationText(migration, var8.isOutOfOrder()) + " failed!";
         if (this.database.supportsDdlTransactions() && executeGroupInTransaction) {
            LOG.error(failedMsg + " Changes successfully rolled back.");
         } else {
            LOG.error(failedMsg + " Please restore backups and roll back database and code!");
            stopWatch.stop();
            int executionTime = (int)stopWatch.getTotalTimeMillis();
            this.schemaHistory.addAppliedMigration(migration.getVersion(), migration.getDescription(), migration.getType(), migration.getScript(), migration.getResolvedMigration().getChecksum(), executionTime, false);
         }

         throw var8;
      }
   }

   private boolean isExecuteGroupInTransaction(LinkedHashMap<MigrationInfoImpl, Boolean> group) {
      boolean executeGroupInTransaction = true;
      boolean first = true;
      Iterator var4 = group.entrySet().iterator();

      while(true) {
         while(var4.hasNext()) {
            Entry<MigrationInfoImpl, Boolean> entry = (Entry)var4.next();
            ResolvedMigration resolvedMigration = ((MigrationInfoImpl)entry.getKey()).getResolvedMigration();
            boolean inTransaction = resolvedMigration.getExecutor().executeInTransaction();
            if (first) {
               executeGroupInTransaction = inTransaction;
               first = false;
            } else {
               if (!this.configuration.isMixed() && executeGroupInTransaction != inTransaction) {
                  throw new FlywayException("Detected both transactional and non-transactional migrations within the same migration group (even though mixed is false). First offending migration:" + (resolvedMigration.getVersion() == null ? "" : " " + resolvedMigration.getVersion()) + (StringUtils.hasLength(resolvedMigration.getDescription()) ? " " + resolvedMigration.getDescription() : "") + (inTransaction ? "" : " [non-transactional]"));
               }

               executeGroupInTransaction = executeGroupInTransaction && inTransaction;
            }
         }

         return executeGroupInTransaction;
      }
   }

   private void doMigrateGroup(LinkedHashMap<MigrationInfoImpl, Boolean> group, StopWatch stopWatch) {
      Iterator var3 = group.entrySet().iterator();

      while(var3.hasNext()) {
         Entry<MigrationInfoImpl, Boolean> entry = (Entry)var3.next();
         MigrationInfoImpl migration = (MigrationInfoImpl)entry.getKey();
         boolean isOutOfOrder = (Boolean)entry.getValue();
         String migrationText = this.toMigrationText(migration, isOutOfOrder);
         stopWatch.start();
         LOG.info("Migrating " + migrationText);
         this.connectionUserObjects.changeCurrentSchemaTo(this.schema);
         Iterator var8 = this.effectiveCallbacks.iterator();

         FlywayCallback callback;
         while(var8.hasNext()) {
            callback = (FlywayCallback)var8.next();
            callback.beforeEachMigrate(this.connectionUserObjects.getJdbcConnection(), migration);
         }

         try {
            migration.getResolvedMigration().getExecutor().execute(this.connectionUserObjects.getJdbcConnection());
         } catch (FlywaySqlScriptException var10) {
            throw new DbMigrate.FlywayMigrateSqlException(migration, isOutOfOrder, var10);
         } catch (SQLException var11) {
            throw new DbMigrate.FlywayMigrateSqlException(migration, isOutOfOrder, var11);
         }

         LOG.debug("Successfully completed migration of " + migrationText);
         var8 = this.effectiveCallbacks.iterator();

         while(var8.hasNext()) {
            callback = (FlywayCallback)var8.next();
            callback.afterEachMigrate(this.connectionUserObjects.getJdbcConnection(), migration);
         }

         stopWatch.stop();
         int executionTime = (int)stopWatch.getTotalTimeMillis();
         this.schemaHistory.addAppliedMigration(migration.getVersion(), migration.getDescription(), migration.getType(), migration.getScript(), migration.getResolvedMigration().getChecksum(), executionTime, true);
      }

   }

   private String toMigrationText(MigrationInfoImpl migration, boolean isOutOfOrder) {
      MigrationExecutor migrationExecutor = migration.getResolvedMigration().getExecutor();
      String migrationText;
      if (migration.getVersion() != null) {
         migrationText = "schema " + this.schema + " to version " + migration.getVersion() + " - " + migration.getDescription() + (isOutOfOrder ? " [out of order]" : "") + (migrationExecutor.executeInTransaction() ? "" : " [non-transactional]");
      } else {
         migrationText = "schema " + this.schema + " with repeatable migration " + migration.getDescription() + (migrationExecutor.executeInTransaction() ? "" : " [non-transactional]");
      }

      return migrationText;
   }

   public static class FlywayMigrateSqlException extends FlywaySqlScriptException {
      private final MigrationInfoImpl migration;
      private final boolean outOfOrder;

      FlywayMigrateSqlException(MigrationInfoImpl migration, boolean outOfOrder, SQLException e) {
         super((Resource)null, (SqlStatement)null, e);
         this.migration = migration;
         this.outOfOrder = outOfOrder;
      }

      FlywayMigrateSqlException(MigrationInfoImpl migration, boolean outOfOrder, FlywaySqlScriptException e) {
         super(e.getResource(), e.getSqlStatement(), (SQLException)e.getCause());
         this.migration = migration;
         this.outOfOrder = outOfOrder;
      }

      public MigrationInfoImpl getMigration() {
         return this.migration;
      }

      public boolean isOutOfOrder() {
         return this.outOfOrder;
      }
   }
}
