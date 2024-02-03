package org.flywaydb.core.internal.database.sqlite;

import java.sql.SQLException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Schema;

public class SQLiteConnection extends Connection<SQLiteDatabase> {
   private static final Log LOG = LogFactory.getLog(SQLiteConnection.class);
   private static boolean schemaMessagePrinted;

   SQLiteConnection(FlywayConfiguration configuration, SQLiteDatabase database, java.sql.Connection connection, int nullType) {
      super(configuration, database, connection, nullType);
   }

   public void doChangeCurrentSchemaTo(String schema) throws SQLException {
      if (!schemaMessagePrinted) {
         LOG.info("SQLite does not support setting the schema. Default schema NOT changed to " + schema);
         schemaMessagePrinted = true;
      }

   }

   public Schema getSchema(String name) {
      return new SQLiteSchema(this.jdbcTemplate, (SQLiteDatabase)this.database, name);
   }

   protected String doGetCurrentSchemaName() throws SQLException {
      return "main";
   }
}
