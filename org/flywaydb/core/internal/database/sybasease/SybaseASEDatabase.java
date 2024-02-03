package org.flywaydb.core.internal.database.sybasease;

import java.sql.Connection;
import java.sql.SQLException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.SqlScript;
import org.flywaydb.core.internal.exception.FlywayDbUpgradeRequiredException;
import org.flywaydb.core.internal.util.scanner.Resource;

public class SybaseASEDatabase extends Database<SybaseASEConnection> {
   public SybaseASEDatabase(FlywayConfiguration configuration, Connection connection, boolean jconnect) {
      super(configuration, connection, jconnect ? 12 : 0);
   }

   protected SybaseASEConnection getConnection(Connection connection, int nullType) {
      return new SybaseASEConnection(this.configuration, this, connection, nullType);
   }

   protected void ensureSupported() {
      String version = this.majorVersion + "." + this.minorVersion;
      if (this.majorVersion < 15 || this.majorVersion == 15 && this.minorVersion < 7) {
         throw new FlywayDbUpgradeRequiredException("Sybase ASE", version, "15.7");
      } else {
         if (this.majorVersion > 16 || this.majorVersion == 16 && this.minorVersion > 2) {
            this.recommendFlywayUpgrade("Sybase ASE", version);
         }

      }
   }

   protected SqlScript doCreateSqlScript(Resource sqlScriptResource, String sqlScriptSource, boolean mixed) {
      return new SybaseASESqlScript(sqlScriptResource, sqlScriptSource, mixed);
   }

   public Delimiter getDefaultDelimiter() {
      return new Delimiter("GO", true);
   }

   public String getDbName() {
      return "sybasease";
   }

   protected String doGetCurrentUser() throws SQLException {
      return ((SybaseASEConnection)this.mainConnection).getJdbcTemplate().queryForString("SELECT user_name()");
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

   protected String doQuote(String identifier) {
      return identifier;
   }

   public boolean catalogIsSchema() {
      return false;
   }
}
