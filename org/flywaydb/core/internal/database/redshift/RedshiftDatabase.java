package org.flywaydb.core.internal.database.redshift;

import java.sql.Connection;
import java.sql.SQLException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.SqlScript;
import org.flywaydb.core.internal.util.StringUtils;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;
import org.flywaydb.core.internal.util.scanner.Resource;

public class RedshiftDatabase extends Database<RedshiftConnection> {
   public static boolean isRedshift(Connection connection) {
      try {
         return (new JdbcTemplate(connection)).queryForString("SELECT version()").contains("Redshift");
      } catch (Exception var2) {
         return false;
      }
   }

   public RedshiftDatabase(FlywayConfiguration configuration, Connection connection) {
      super(configuration, connection, 12);
   }

   protected RedshiftConnection getConnection(Connection connection, int nullType) {
      return new RedshiftConnection(this.configuration, this, connection, nullType);
   }

   protected final void ensureSupported() {
   }

   protected SqlScript doCreateSqlScript(Resource sqlScriptResource, String sqlScriptSource, boolean mixed) {
      return new RedshiftSqlScript(sqlScriptResource, sqlScriptSource, mixed);
   }

   public String getDbName() {
      return "redshift";
   }

   protected String doGetCurrentUser() throws SQLException {
      return ((RedshiftConnection)this.getMainConnection()).getJdbcTemplate().queryForString("SELECT current_user");
   }

   public boolean supportsDdlTransactions() {
      return true;
   }

   public String getBooleanTrue() {
      return "TRUE";
   }

   public String getBooleanFalse() {
      return "FALSE";
   }

   public String doQuote(String identifier) {
      return redshiftQuote(identifier);
   }

   static String redshiftQuote(String identifier) {
      return "\"" + StringUtils.replaceAll(identifier, "\"", "\"\"") + "\"";
   }

   public boolean catalogIsSchema() {
      return false;
   }

   public boolean useSingleConnection() {
      return false;
   }
}
