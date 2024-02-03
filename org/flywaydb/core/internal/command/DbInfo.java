package org.flywaydb.core.internal.command;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import org.flywaydb.core.api.MigrationInfoService;
import org.flywaydb.core.api.callback.FlywayCallback;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.api.resolver.MigrationResolver;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.info.MigrationInfoServiceImpl;
import org.flywaydb.core.internal.schemahistory.SchemaHistory;
import org.flywaydb.core.internal.util.jdbc.TransactionTemplate;

public class DbInfo {
   private final MigrationResolver migrationResolver;
   private final SchemaHistory schemaHistory;
   private final Connection connection;
   private final FlywayConfiguration configuration;
   private final Schema[] schemas;
   private final List<FlywayCallback> effectiveCallbacks;

   public DbInfo(MigrationResolver migrationResolver, SchemaHistory schemaHistory, Database database, FlywayConfiguration configuration, Schema[] schemas, List<FlywayCallback> effectiveCallbacks) {
      this.migrationResolver = migrationResolver;
      this.schemaHistory = schemaHistory;
      this.connection = database.getMainConnection();
      this.configuration = configuration;
      this.schemas = schemas;
      this.effectiveCallbacks = effectiveCallbacks;
   }

   public MigrationInfoService info() {
      try {
         Iterator var1 = this.effectiveCallbacks.iterator();

         while(var1.hasNext()) {
            final FlywayCallback callback = (FlywayCallback)var1.next();
            (new TransactionTemplate(this.connection.getJdbcConnection())).execute(new Callable<Object>() {
               public Object call() throws SQLException {
                  DbInfo.this.connection.changeCurrentSchemaTo(DbInfo.this.schemas[0]);
                  callback.beforeInfo(DbInfo.this.connection.getJdbcConnection());
                  return null;
               }
            });
         }

         MigrationInfoServiceImpl migrationInfoService = new MigrationInfoServiceImpl(this.migrationResolver, this.schemaHistory, this.configuration.getTarget(), this.configuration.isOutOfOrder(), true, true, true);
         migrationInfoService.refresh();
         Iterator var8 = this.effectiveCallbacks.iterator();

         while(var8.hasNext()) {
            final FlywayCallback callback = (FlywayCallback)var8.next();
            (new TransactionTemplate(this.connection.getJdbcConnection())).execute(new Callable<Object>() {
               public Object call() throws SQLException {
                  DbInfo.this.connection.changeCurrentSchemaTo(DbInfo.this.schemas[0]);
                  callback.afterInfo(DbInfo.this.connection.getJdbcConnection());
                  return null;
               }
            });
         }

         MigrationInfoServiceImpl var9 = migrationInfoService;
         return var9;
      } finally {
         this.connection.restoreCurrentSchema();
      }
   }
}
