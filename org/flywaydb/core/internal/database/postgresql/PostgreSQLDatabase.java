package org.flywaydb.core.internal.database.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.SqlScript;
import org.flywaydb.core.internal.exception.FlywayDbUpgradeRequiredException;
import org.flywaydb.core.internal.exception.FlywayEnterpriseUpgradeRequiredException;
import org.flywaydb.core.internal.util.StringUtils;
import org.flywaydb.core.internal.util.scanner.Resource;

public class PostgreSQLDatabase extends Database {
   public PostgreSQLDatabase(FlywayConfiguration configuration, Connection connection) {
      super(configuration, connection, 0);
   }

   protected org.flywaydb.core.internal.database.Connection getConnection(Connection connection, int nullType) {
      return new PostgreSQLConnection(this.configuration, this, connection, nullType);
   }

   protected final void ensureSupported() {
      String version = this.majorVersion + "." + this.minorVersion;
      if (this.majorVersion < 9) {
         throw new FlywayDbUpgradeRequiredException("PostgreSQL", version, "9.0");
      } else if (this.majorVersion == 9 && this.minorVersion < 3) {
         throw new FlywayEnterpriseUpgradeRequiredException("PostgreSQL", "PostgreSQL", version);
      } else {
         if (this.majorVersion > 10) {
            this.recommendFlywayUpgrade("PostgreSQL", version);
         }

      }
   }

   protected SqlScript doCreateSqlScript(Resource sqlScriptResource, String sqlScriptSource, boolean mixed) {
      return new PostgreSQLSqlScript(sqlScriptResource, sqlScriptSource, mixed);
   }

   public String getDbName() {
      return "postgresql";
   }

   protected String doGetCurrentUser() throws SQLException {
      return this.mainConnection.getJdbcTemplate().queryForString("SELECT current_user");
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
      return pgQuote(identifier);
   }

   static String pgQuote(String identifier) {
      return "\"" + StringUtils.replaceAll(identifier, "\"", "\"\"") + "\"";
   }

   public boolean catalogIsSchema() {
      return false;
   }

   public boolean useSingleConnection() {
      return true;
   }
}
