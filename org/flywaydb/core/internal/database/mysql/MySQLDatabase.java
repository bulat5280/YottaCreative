package org.flywaydb.core.internal.database.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.SqlScript;
import org.flywaydb.core.internal.exception.FlywayDbUpgradeRequiredException;
import org.flywaydb.core.internal.exception.FlywayEnterpriseUpgradeRequiredException;
import org.flywaydb.core.internal.exception.FlywaySqlException;
import org.flywaydb.core.internal.util.scanner.Resource;

public class MySQLDatabase extends Database {
   public MySQLDatabase(FlywayConfiguration configuration, Connection connection) {
      super(configuration, connection, 12);
   }

   protected org.flywaydb.core.internal.database.Connection getConnection(Connection connection, int nullType) {
      return new MySQLConnection(this.configuration, this, connection, nullType);
   }

   protected final void ensureSupported() {
      String version = this.majorVersion + "." + this.minorVersion;

      boolean isMariaDB;
      try {
         isMariaDB = this.jdbcMetaData.getDatabaseProductVersion().contains("MariaDB");
      } catch (SQLException var4) {
         throw new FlywaySqlException("Unable to determine database product version", var4);
      }

      String productName = isMariaDB ? "MariaDB" : "MySQL";
      if (this.majorVersion < 5) {
         throw new FlywayDbUpgradeRequiredException(productName, version, "5.0");
      } else {
         if (this.majorVersion == 5) {
            if (this.minorVersion < 5) {
               throw new FlywayEnterpriseUpgradeRequiredException(isMariaDB ? "MariaDB" : "Oracle", productName, version);
            }

            if (this.minorVersion > 7) {
               this.recommendFlywayUpgrade(productName, version);
            }
         } else if (isMariaDB) {
            if (this.majorVersion > 10 || this.majorVersion == 10 && this.minorVersion > 2) {
               this.recommendFlywayUpgrade(productName, version);
            }
         } else {
            this.recommendFlywayUpgrade(productName, version);
         }

      }
   }

   protected SqlScript doCreateSqlScript(Resource sqlScriptResource, String sqlScriptSource, boolean mixed) {
      return new MySQLSqlScript(sqlScriptResource, sqlScriptSource, mixed);
   }

   public String getDbName() {
      return "mysql";
   }

   protected String doGetCurrentUser() throws SQLException {
      return this.mainConnection.getJdbcTemplate().queryForString("SELECT SUBSTRING_INDEX(USER(),'@',1)");
   }

   public boolean supportsDdlTransactions() {
      return false;
   }

   public String getBooleanTrue() {
      return "1";
   }

   public String getBooleanFalse() {
      return "0";
   }

   public String doQuote(String identifier) {
      return "`" + identifier + "`";
   }

   public boolean catalogIsSchema() {
      return true;
   }

   public boolean useSingleConnection() {
      return true;
   }
}
