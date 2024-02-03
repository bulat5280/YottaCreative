package org.flywaydb.core.internal.command;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.callback.FlywayCallback;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.schemahistory.AppliedMigration;
import org.flywaydb.core.internal.schemahistory.SchemaHistory;
import org.flywaydb.core.internal.util.jdbc.TransactionTemplate;

public class DbBaseline {
   private static final Log LOG = LogFactory.getLog(DbBaseline.class);
   private final Connection connection;
   private final SchemaHistory schemaHistory;
   private final MigrationVersion baselineVersion;
   private final String baselineDescription;
   private final List<FlywayCallback> callbacks;
   private final Schema schema;

   public DbBaseline(Database database, SchemaHistory schemaHistory, Schema schema, MigrationVersion baselineVersion, String baselineDescription, List<FlywayCallback> callbacks) {
      this.connection = database.getMainConnection();
      this.schemaHistory = schemaHistory;
      this.schema = schema;
      this.baselineVersion = baselineVersion;
      this.baselineDescription = baselineDescription;
      this.callbacks = callbacks;
   }

   public void baseline() {
      try {
         Iterator var1 = this.callbacks.iterator();

         final FlywayCallback callback;
         while(var1.hasNext()) {
            callback = (FlywayCallback)var1.next();
            (new TransactionTemplate(this.connection.getJdbcConnection())).execute(new Callable<Object>() {
               public Object call() throws SQLException {
                  DbBaseline.this.connection.changeCurrentSchemaTo(DbBaseline.this.schema);
                  callback.beforeBaseline(DbBaseline.this.connection.getJdbcConnection());
                  return null;
               }
            });
         }

         this.schemaHistory.create();
         (new TransactionTemplate(this.connection.getJdbcConnection())).execute(new Callable<Object>() {
            public Void call() {
               DbBaseline.this.connection.changeCurrentSchemaTo(DbBaseline.this.schema);
               if (DbBaseline.this.schemaHistory.hasBaselineMarker()) {
                  AppliedMigration baselineMarker = DbBaseline.this.schemaHistory.getBaselineMarker();
                  if (DbBaseline.this.baselineVersion.equals(baselineMarker.getVersion()) && DbBaseline.this.baselineDescription.equals(baselineMarker.getDescription())) {
                     DbBaseline.LOG.info("Schema history table " + DbBaseline.this.schemaHistory + " already initialized with (" + DbBaseline.this.baselineVersion + "," + DbBaseline.this.baselineDescription + "). Skipping.");
                     return null;
                  } else {
                     throw new FlywayException("Unable to baseline schema history table " + DbBaseline.this.schemaHistory + " with (" + DbBaseline.this.baselineVersion + "," + DbBaseline.this.baselineDescription + ") as it has already been initialized with (" + baselineMarker.getVersion() + "," + baselineMarker.getDescription() + ")");
                  }
               } else if (DbBaseline.this.schemaHistory.hasSchemasMarker() && DbBaseline.this.baselineVersion.equals(MigrationVersion.fromVersion("0"))) {
                  throw new FlywayException("Unable to baseline schema history table " + DbBaseline.this.schemaHistory + " with version 0 as this version was used for schema creation");
               } else if (DbBaseline.this.schemaHistory.hasAppliedMigrations()) {
                  throw new FlywayException("Unable to baseline schema history table " + DbBaseline.this.schemaHistory + " as it already contains migrations");
               } else {
                  DbBaseline.this.schemaHistory.addBaselineMarker(DbBaseline.this.baselineVersion, DbBaseline.this.baselineDescription);
                  return null;
               }
            }
         });
         LOG.info("Successfully baselined schema with version: " + this.baselineVersion);
         var1 = this.callbacks.iterator();

         while(var1.hasNext()) {
            callback = (FlywayCallback)var1.next();
            (new TransactionTemplate(this.connection.getJdbcConnection())).execute(new Callable<Object>() {
               public Object call() throws SQLException {
                  DbBaseline.this.connection.changeCurrentSchemaTo(DbBaseline.this.schema);
                  callback.afterBaseline(DbBaseline.this.connection.getJdbcConnection());
                  return null;
               }
            });
         }

      } finally {
         this.connection.restoreCurrentSchema();
      }
   }
}
