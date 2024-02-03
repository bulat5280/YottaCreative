package org.flywaydb.core.internal.command;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.callback.FlywayCallback;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.schemahistory.SchemaHistory;
import org.flywaydb.core.internal.util.StopWatch;
import org.flywaydb.core.internal.util.TimeFormat;
import org.flywaydb.core.internal.util.jdbc.TransactionTemplate;

public class DbClean {
   private static final Log LOG = LogFactory.getLog(DbClean.class);
   private final Connection connection;
   private final SchemaHistory schemaHistory;
   private final Schema[] schemas;
   private final List<FlywayCallback> callbacks;
   private boolean cleanDisabled;

   public DbClean(Database database, SchemaHistory schemaHistory, Schema[] schemas, List<FlywayCallback> callbacks, boolean cleanDisabled) {
      this.connection = database.getMainConnection();
      this.schemaHistory = schemaHistory;
      this.schemas = schemas;
      this.callbacks = callbacks;
      this.cleanDisabled = cleanDisabled;
   }

   public void clean() throws FlywayException {
      if (this.cleanDisabled) {
         throw new FlywayException("Unable to execute clean as it has been disabled with the \"flyway.cleanDisabled\" property.");
      } else {
         try {
            Iterator var1 = this.callbacks.iterator();

            while(var1.hasNext()) {
               final FlywayCallback callback = (FlywayCallback)var1.next();
               (new TransactionTemplate(this.connection.getJdbcConnection())).execute(new Callable<Object>() {
                  public Object call() throws SQLException {
                     DbClean.this.connection.changeCurrentSchemaTo(DbClean.this.schemas[0]);
                     callback.beforeClean(DbClean.this.connection.getJdbcConnection());
                     return null;
                  }
               });
            }

            this.connection.changeCurrentSchemaTo(this.schemas[0]);
            boolean dropSchemas = false;

            try {
               dropSchemas = this.schemaHistory.hasSchemasMarker();
            } catch (Exception var9) {
               LOG.error("Error while checking whether the schemas should be dropped", var9);
            }

            Schema[] var12 = this.schemas;
            int var3 = var12.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Schema schema = var12[var4];
               if (!schema.exists()) {
                  LOG.warn("Unable to clean unknown schema: " + schema);
               } else if (dropSchemas) {
                  this.dropSchema(schema);
               } else {
                  this.cleanSchema(schema);
               }
            }

            Iterator var13 = this.callbacks.iterator();

            while(var13.hasNext()) {
               final FlywayCallback callback = (FlywayCallback)var13.next();
               (new TransactionTemplate(this.connection.getJdbcConnection())).execute(new Callable<Object>() {
                  public Object call() throws SQLException {
                     DbClean.this.connection.changeCurrentSchemaTo(DbClean.this.schemas[0]);
                     callback.afterClean(DbClean.this.connection.getJdbcConnection());
                     return null;
                  }
               });
            }

            this.schemaHistory.clearCache();
         } finally {
            this.connection.restoreCurrentSchema();
         }
      }
   }

   private void dropSchema(final Schema schema) {
      LOG.debug("Dropping schema " + schema + " ...");
      StopWatch stopWatch = new StopWatch();
      stopWatch.start();
      (new TransactionTemplate(this.connection.getJdbcConnection())).execute(new Callable<Object>() {
         public Void call() {
            schema.drop();
            return null;
         }
      });
      stopWatch.stop();
      LOG.info(String.format("Successfully dropped schema %s (execution time %s)", schema, TimeFormat.format(stopWatch.getTotalTimeMillis())));
   }

   private void cleanSchema(final Schema schema) {
      LOG.debug("Cleaning schema " + schema + " ...");
      StopWatch stopWatch = new StopWatch();
      stopWatch.start();
      (new TransactionTemplate(this.connection.getJdbcConnection())).execute(new Callable<Object>() {
         public Void call() {
            schema.clean();
            return null;
         }
      });
      stopWatch.stop();
      LOG.info(String.format("Successfully cleaned schema %s (execution time %s)", schema, TimeFormat.format(stopWatch.getTotalTimeMillis())));
   }
}
