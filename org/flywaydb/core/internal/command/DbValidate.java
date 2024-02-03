package org.flywaydb.core.internal.command;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.callback.FlywayCallback;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.api.resolver.MigrationResolver;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.info.MigrationInfoServiceImpl;
import org.flywaydb.core.internal.schemahistory.SchemaHistory;
import org.flywaydb.core.internal.util.Pair;
import org.flywaydb.core.internal.util.StopWatch;
import org.flywaydb.core.internal.util.TimeFormat;
import org.flywaydb.core.internal.util.jdbc.TransactionTemplate;

public class DbValidate {
   private static final Log LOG = LogFactory.getLog(DbValidate.class);
   private final MigrationVersion target;
   private final SchemaHistory schemaHistory;
   private final Schema schema;
   private final MigrationResolver migrationResolver;
   private final Connection connection;
   private final boolean outOfOrder;
   private final boolean pending;
   private final boolean missing;
   private final boolean future;
   private final List<FlywayCallback> callbacks;

   public DbValidate(Database database, SchemaHistory schemaHistory, Schema schema, MigrationResolver migrationResolver, MigrationVersion target, boolean outOfOrder, boolean pending, boolean missing, boolean future, List<FlywayCallback> callbacks) {
      this.connection = database.getMainConnection();
      this.schemaHistory = schemaHistory;
      this.schema = schema;
      this.migrationResolver = migrationResolver;
      this.target = target;
      this.outOfOrder = outOfOrder;
      this.pending = pending;
      this.missing = missing;
      this.future = future;
      this.callbacks = callbacks;
   }

   public String validate() {
      if (!this.schema.exists()) {
         return !this.migrationResolver.resolveMigrations().isEmpty() && !this.pending ? "Schema " + this.schema + " doesn't exist yet" : null;
      } else {
         String var12;
         try {
            Iterator var1 = this.callbacks.iterator();

            while(var1.hasNext()) {
               final FlywayCallback callback = (FlywayCallback)var1.next();
               (new TransactionTemplate(this.connection.getJdbcConnection())).execute(new Callable<Object>() {
                  public Object call() {
                     DbValidate.this.connection.changeCurrentSchemaTo(DbValidate.this.schema);
                     callback.beforeValidate(DbValidate.this.connection.getJdbcConnection());
                     return null;
                  }
               });
            }

            LOG.debug("Validating migrations ...");
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            Pair<Integer, String> result = (Pair)(new TransactionTemplate(this.connection.getJdbcConnection())).execute(new Callable<Pair<Integer, String>>() {
               public Pair<Integer, String> call() {
                  DbValidate.this.connection.changeCurrentSchemaTo(DbValidate.this.schema);
                  MigrationInfoServiceImpl migrationInfoService = new MigrationInfoServiceImpl(DbValidate.this.migrationResolver, DbValidate.this.schemaHistory, DbValidate.this.target, DbValidate.this.outOfOrder, DbValidate.this.pending, DbValidate.this.missing, DbValidate.this.future);
                  migrationInfoService.refresh();
                  int count = migrationInfoService.all().length;
                  String validationError = migrationInfoService.validate();
                  return Pair.of(count, validationError);
               }
            });
            stopWatch.stop();
            String error = (String)result.getRight();
            if (error == null) {
               int count = (Integer)result.getLeft();
               if (count == 1) {
                  LOG.info(String.format("Successfully validated 1 migration (execution time %s)", TimeFormat.format(stopWatch.getTotalTimeMillis())));
               } else {
                  LOG.info(String.format("Successfully validated %d migrations (execution time %s)", count, TimeFormat.format(stopWatch.getTotalTimeMillis())));
               }
            }

            Iterator var11 = this.callbacks.iterator();

            while(var11.hasNext()) {
               final FlywayCallback callback = (FlywayCallback)var11.next();
               (new TransactionTemplate(this.connection.getJdbcConnection())).execute(new Callable<Object>() {
                  public Object call() {
                     DbValidate.this.connection.changeCurrentSchemaTo(DbValidate.this.schema);
                     callback.afterValidate(DbValidate.this.connection.getJdbcConnection());
                     return null;
                  }
               });
            }

            var12 = error;
         } finally {
            this.connection.restoreCurrentSchema();
         }

         return var12;
      }
   }
}
