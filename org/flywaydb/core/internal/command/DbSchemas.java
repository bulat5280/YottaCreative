package org.flywaydb.core.internal.command;

import java.util.concurrent.Callable;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.schemahistory.SchemaHistory;
import org.flywaydb.core.internal.util.jdbc.TransactionTemplate;

public class DbSchemas {
   private static final Log LOG = LogFactory.getLog(DbSchemas.class);
   private final Connection connection;
   private final Schema[] schemas;
   private final SchemaHistory schemaHistory;

   public DbSchemas(Database database, Schema[] schemas, SchemaHistory schemaHistory) {
      this.connection = database.getMainConnection();
      this.schemas = schemas;
      this.schemaHistory = schemaHistory;
   }

   public void create() {
      int retries = 0;

      while(true) {
         try {
            (new TransactionTemplate(this.connection.getJdbcConnection())).execute(new Callable<Object>() {
               public Void call() {
                  Schema[] var1 = DbSchemas.this.schemas;
                  int var2 = var1.length;

                  int var3;
                  Schema schema;
                  for(var3 = 0; var3 < var2; ++var3) {
                     schema = var1[var3];
                     if (schema.exists()) {
                        DbSchemas.LOG.debug("Schema " + schema + " already exists. Skipping schema creation.");
                        return null;
                     }
                  }

                  var1 = DbSchemas.this.schemas;
                  var2 = var1.length;

                  for(var3 = 0; var3 < var2; ++var3) {
                     schema = var1[var3];
                     DbSchemas.LOG.info("Creating schema " + schema + " ...");
                     schema.create();
                  }

                  DbSchemas.this.schemaHistory.create();
                  DbSchemas.this.schemaHistory.addSchemasMarker(DbSchemas.this.schemas);
                  return null;
               }
            });
            return;
         } catch (RuntimeException var5) {
            ++retries;
            if (retries >= 10) {
               throw var5;
            }

            try {
               LOG.debug("Schema creation failed. Retrying in 1 sec ...");
               Thread.sleep(1000L);
            } catch (InterruptedException var4) {
            }
         }
      }
   }
}
