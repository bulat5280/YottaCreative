package org.flywaydb.core.internal.database.cockroachdb;

import java.sql.Connection;
import java.sql.SQLException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.SqlScript;
import org.flywaydb.core.internal.exception.FlywayDbUpgradeRequiredException;
import org.flywaydb.core.internal.exception.FlywaySqlException;
import org.flywaydb.core.internal.util.Pair;
import org.flywaydb.core.internal.util.StringUtils;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;
import org.flywaydb.core.internal.util.scanner.Resource;

public class CockroachDBDatabase extends Database {
   public static boolean isCockroachDB(Connection connection) {
      try {
         return (new JdbcTemplate(connection)).queryForString("SELECT version()").contains("CockroachDB");
      } catch (Exception var2) {
         return false;
      }
   }

   public CockroachDBDatabase(FlywayConfiguration configuration, Connection connection) {
      super(configuration, connection, 0);
   }

   protected org.flywaydb.core.internal.database.Connection getConnection(Connection connection, int nullType) {
      return new CockroachDBConnection(this.configuration, this, connection, nullType);
   }

   protected final void ensureSupported() {
      String version = this.majorVersion + "." + this.minorVersion;
      if (this.majorVersion >= 1 && (this.majorVersion != 1 || this.minorVersion >= 1)) {
         if (this.majorVersion > 1) {
            this.recommendFlywayUpgrade("CockroachDB", version);
         }

      } else {
         throw new FlywayDbUpgradeRequiredException("CockroachDB", version, "1.1");
      }
   }

   protected SqlScript doCreateSqlScript(Resource sqlScriptResource, String sqlScriptSource, boolean mixed) {
      return new CockroachDBSqlScript(sqlScriptResource, sqlScriptSource, mixed);
   }

   protected Pair<Integer, Integer> determineMajorAndMinorVersion() {
      String version;
      try {
         version = this.mainConnection.getJdbcTemplate().queryForString("SELECT value FROM crdb_internal.node_build_info where field='Version'");
         if (version == null) {
            version = this.mainConnection.getJdbcTemplate().queryForString("SELECT value FROM crdb_internal.node_build_info where field='Tag'");
         }
      } catch (SQLException var6) {
         throw new FlywaySqlException("Unable to determine CockroachDB version", var6);
      }

      int firstDot = version.indexOf(".");
      int majorVersion = Integer.parseInt(version.substring(1, firstDot));
      String minorPatch = version.substring(firstDot + 1);
      int minorVersion = Integer.parseInt(minorPatch.substring(0, minorPatch.indexOf(".")));
      return Pair.of(majorVersion, minorVersion);
   }

   public String getDbName() {
      return "cockroachdb";
   }

   protected String doGetCurrentUser() throws SQLException {
      return this.mainConnection.getJdbcTemplate().queryForString("(SELECT * FROM [SHOW SESSION_USER])");
   }

   public boolean supportsDdlTransactions() {
      return false;
   }

   public String getBooleanTrue() {
      return "TRUE";
   }

   public String getBooleanFalse() {
      return "FALSE";
   }

   public String doQuote(String identifier) {
      return "\"" + StringUtils.replaceAll(identifier, "\"", "\"\"") + "\"";
   }

   public boolean catalogIsSchema() {
      return false;
   }

   public boolean useSingleConnection() {
      return false;
   }
}
