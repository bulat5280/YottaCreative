package org.flywaydb.core.internal.command;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.callback.FlywayCallback;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.api.resolver.MigrationResolver;
import org.flywaydb.core.api.resolver.ResolvedMigration;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.info.MigrationInfoImpl;
import org.flywaydb.core.internal.info.MigrationInfoServiceImpl;
import org.flywaydb.core.internal.schemahistory.AppliedMigration;
import org.flywaydb.core.internal.schemahistory.SchemaHistory;
import org.flywaydb.core.internal.util.ObjectUtils;
import org.flywaydb.core.internal.util.StopWatch;
import org.flywaydb.core.internal.util.TimeFormat;
import org.flywaydb.core.internal.util.jdbc.TransactionTemplate;

public class DbRepair {
   private static final Log LOG = LogFactory.getLog(DbRepair.class);
   private final Connection connection;
   private final MigrationInfoServiceImpl migrationInfoService;
   private final Schema schema;
   private final SchemaHistory schemaHistory;
   private final List<FlywayCallback> callbacks;
   private final Database database;

   public DbRepair(Database database, Schema schema, MigrationResolver migrationResolver, SchemaHistory schemaHistory, List<FlywayCallback> callbacks) {
      this.database = database;
      this.connection = database.getMainConnection();
      this.schema = schema;
      this.migrationInfoService = new MigrationInfoServiceImpl(migrationResolver, schemaHistory, MigrationVersion.LATEST, true, true, true, true);
      this.schemaHistory = schemaHistory;
      this.callbacks = callbacks;
   }

   public void repair() {
      try {
         Iterator var1 = this.callbacks.iterator();

         while(var1.hasNext()) {
            final FlywayCallback callback = (FlywayCallback)var1.next();
            (new TransactionTemplate(this.connection.getJdbcConnection())).execute(new Callable<Object>() {
               public Object call() throws SQLException {
                  DbRepair.this.connection.changeCurrentSchemaTo(DbRepair.this.schema);
                  callback.beforeRepair(DbRepair.this.connection.getJdbcConnection());
                  return null;
               }
            });
         }

         StopWatch stopWatch = new StopWatch();
         stopWatch.start();
         (new TransactionTemplate(this.connection.getJdbcConnection())).execute(new Callable<Object>() {
            public Void call() {
               DbRepair.this.connection.changeCurrentSchemaTo(DbRepair.this.schema);
               DbRepair.this.schemaHistory.removeFailedMigrations();
               DbRepair.this.alignAppliedMigrationsWithResolvedMigrations();
               return null;
            }
         });
         stopWatch.stop();
         LOG.info("Successfully repaired schema history table " + this.schemaHistory + " (execution time " + TimeFormat.format(stopWatch.getTotalTimeMillis()) + ").");
         if (!this.database.supportsDdlTransactions()) {
            LOG.info("Manual cleanup of the remaining effects the failed migration may still be required.");
         }

         Iterator var8 = this.callbacks.iterator();

         while(var8.hasNext()) {
            final FlywayCallback callback = (FlywayCallback)var8.next();
            (new TransactionTemplate(this.connection.getJdbcConnection())).execute(new Callable<Object>() {
               public Object call() throws SQLException {
                  DbRepair.this.connection.changeCurrentSchemaTo(DbRepair.this.schema);
                  callback.afterRepair(DbRepair.this.connection.getJdbcConnection());
                  return null;
               }
            });
         }

      } finally {
         this.connection.restoreCurrentSchema();
      }
   }

   private void alignAppliedMigrationsWithResolvedMigrations() {
      this.migrationInfoService.refresh();
      MigrationInfo[] var1 = this.migrationInfoService.all();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         MigrationInfo migrationInfo = var1[var3];
         MigrationInfoImpl migrationInfoImpl = (MigrationInfoImpl)migrationInfo;
         ResolvedMigration resolved = migrationInfoImpl.getResolvedMigration();
         AppliedMigration applied = migrationInfoImpl.getAppliedMigration();
         if (resolved != null && applied != null && resolved.getVersion() != null && (this.checksumUpdateNeeded(resolved, applied) || this.descriptionUpdateNeeded(resolved, applied) || this.typeUpdateNeeded(resolved, applied))) {
            this.schemaHistory.update(applied, resolved);
         }
      }

   }

   private boolean checksumUpdateNeeded(ResolvedMigration resolved, AppliedMigration applied) {
      return !ObjectUtils.nullSafeEquals(resolved.getChecksum(), applied.getChecksum());
   }

   private boolean descriptionUpdateNeeded(ResolvedMigration resolved, AppliedMigration applied) {
      return !ObjectUtils.nullSafeEquals(resolved.getDescription(), applied.getDescription());
   }

   private boolean typeUpdateNeeded(ResolvedMigration resolved, AppliedMigration applied) {
      return !ObjectUtils.nullSafeEquals(resolved.getType(), applied.getType()) && applied.getType().isSynthetic();
   }
}
