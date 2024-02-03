package org.flywaydb.core.internal.database.sqlserver;

import java.sql.Connection;
import java.sql.SQLException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.SqlScript;
import org.flywaydb.core.internal.exception.FlywayDbUpgradeRequiredException;
import org.flywaydb.core.internal.exception.FlywayEnterpriseUpgradeRequiredException;
import org.flywaydb.core.internal.exception.FlywaySqlException;
import org.flywaydb.core.internal.util.StringUtils;
import org.flywaydb.core.internal.util.scanner.Resource;

public class SQLServerDatabase extends Database {
   private final boolean azure;

   public SQLServerDatabase(FlywayConfiguration configuration, Connection connection) {
      super(configuration, connection, 12);

      try {
         this.azure = "SQL Azure".equals(this.mainConnection.getJdbcTemplate().queryForString("SELECT CAST(SERVERPROPERTY('edition') AS VARCHAR)"));
      } catch (SQLException var4) {
         throw new FlywaySqlException("Unable to determine database edition", var4);
      }
   }

   protected org.flywaydb.core.internal.database.Connection getConnection(Connection connection, int nullType) {
      return new SQLServerConnection(this.configuration, this, connection, nullType);
   }

   protected final void ensureSupported() {
      String release = this.versionToReleaseName(this.majorVersion, this.minorVersion);
      if (this.majorVersion < 10) {
         throw new FlywayDbUpgradeRequiredException("SQL Server", release, "2008");
      } else if (this.majorVersion < 12) {
         throw new FlywayEnterpriseUpgradeRequiredException("Microsoft", "SQL Server", release);
      } else {
         if (this.majorVersion > 14 || this.majorVersion == 14 && this.minorVersion > 0) {
            this.recommendFlywayUpgrade("SQL Server", release);
         }

      }
   }

   private String versionToReleaseName(int major, int minor) {
      if (major < 8) {
         return major + "." + minor;
      } else if (major == 8) {
         return "2000";
      } else if (major == 9) {
         return "2005";
      } else if (major == 10) {
         return minor == 0 ? "2008" : "2008 R2";
      } else if (major == 11) {
         return "2012";
      } else if (major == 12) {
         return "2014";
      } else if (major == 13) {
         return "2016";
      } else {
         return major == 14 ? "2017" : major + "." + minor;
      }
   }

   protected SqlScript doCreateSqlScript(Resource sqlScriptResource, String sqlScriptSource, boolean mixed) {
      return new SQLServerSqlScript(sqlScriptResource, sqlScriptSource, mixed);
   }

   public String getDbName() {
      return "sqlserver";
   }

   public Delimiter getDefaultDelimiter() {
      return new Delimiter("GO", true);
   }

   protected String doGetCurrentUser() throws SQLException {
      return this.mainConnection.getJdbcTemplate().queryForString("SELECT SUSER_SNAME()");
   }

   public boolean supportsDdlTransactions() {
      return true;
   }

   public String getBooleanTrue() {
      return "1";
   }

   public String getBooleanFalse() {
      return "0";
   }

   private String escapeIdentifier(String identifier) {
      return StringUtils.replaceAll(identifier, "]", "]]");
   }

   public String doQuote(String identifier) {
      return "[" + this.escapeIdentifier(identifier) + "]";
   }

   public boolean catalogIsSchema() {
      return false;
   }

   public boolean useSingleConnection() {
      return true;
   }

   boolean isAzure() {
      return this.azure;
   }
}
