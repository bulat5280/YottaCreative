package org.flywaydb.core.internal.database.sqlserver;

import java.sql.SQLException;
import java.util.concurrent.Callable;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Schema;
import org.flywaydb.core.internal.database.Table;
import org.flywaydb.core.internal.exception.FlywaySqlException;

public class SQLServerConnection extends Connection<SQLServerDatabase> {
   private static final Log LOG = LogFactory.getLog(SQLServerConnection.class);
   private final String originalDatabaseName;
   private final String originalAnsiNulls;
   private static boolean schemaMessagePrinted;

   SQLServerConnection(FlywayConfiguration configuration, SQLServerDatabase database, java.sql.Connection connection, int nullType) {
      super(configuration, database, connection, nullType);

      try {
         this.originalDatabaseName = this.jdbcTemplate.queryForString("SELECT DB_NAME()");
      } catch (SQLException var7) {
         throw new FlywaySqlException("Unable to determine current database", var7);
      }

      try {
         this.originalAnsiNulls = database.isAzure() ? null : this.jdbcTemplate.queryForString("DECLARE @ANSI_NULLS VARCHAR(3) = 'OFF';\nIF ( (32 & @@OPTIONS) = 32 ) SET @ANSI_NULLS = 'ON';\nSELECT @ANSI_NULLS AS ANSI_NULLS;");
      } catch (SQLException var6) {
         throw new FlywaySqlException("Unable to determine ANSI NULLS state", var6);
      }
   }

   public void setCurrentDatabase(String databaseName) throws SQLException {
      this.jdbcTemplate.execute("USE " + ((SQLServerDatabase)this.database).quote(new String[]{databaseName}));
   }

   protected String doGetCurrentSchemaName() throws SQLException {
      return this.jdbcTemplate.queryForString("SELECT SCHEMA_NAME()");
   }

   public void doChangeCurrentSchemaTo(String schema) throws SQLException {
      this.setCurrentDatabase(this.originalDatabaseName);
      if (!((SQLServerDatabase)this.database).isAzure()) {
         this.jdbcTemplate.execute("SET ANSI_NULLS " + this.originalAnsiNulls);
      }

      if (!schemaMessagePrinted) {
         LOG.info("SQLServer does not support setting the schema for the current session. Default schema NOT changed to " + schema);
         schemaMessagePrinted = true;
      }

   }

   public Schema getSchema(String name) {
      return new SQLServerSchema(this.jdbcTemplate, (SQLServerDatabase)this.database, this.originalDatabaseName, name);
   }

   public <T> T lock(Table table, Callable<T> callable) {
      return (new SQLServerApplicationLockTemplate(this, this.jdbcTemplate, this.originalDatabaseName, table.toString().hashCode())).execute(callable);
   }
}
