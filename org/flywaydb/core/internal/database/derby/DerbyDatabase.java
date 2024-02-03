package org.flywaydb.core.internal.database.derby;

import java.sql.Connection;
import java.sql.SQLException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.SqlScript;
import org.flywaydb.core.internal.exception.FlywayDbUpgradeRequiredException;
import org.flywaydb.core.internal.util.scanner.Resource;

public class DerbyDatabase extends Database {
   public DerbyDatabase(FlywayConfiguration configuration, Connection connection) {
      super(configuration, connection, 12);
   }

   protected org.flywaydb.core.internal.database.Connection getConnection(Connection connection, int nullType) {
      return new DerbyConnection(this.configuration, this, connection, nullType);
   }

   protected final void ensureSupported() {
      String version = this.majorVersion + "." + this.minorVersion;
      if (this.majorVersion < 10 || this.majorVersion == 10 && this.minorVersion < 8) {
         throw new FlywayDbUpgradeRequiredException("Derby", version, "10.8.1.2");
      }
   }

   protected SqlScript doCreateSqlScript(Resource sqlScriptResource, String sqlScriptSource, boolean mixed) {
      return new DerbySqlScript(sqlScriptResource, sqlScriptSource, mixed);
   }

   public String getDbName() {
      return "derby";
   }

   protected String doGetCurrentUser() throws SQLException {
      return this.mainConnection.getJdbcTemplate().queryForString("SELECT CURRENT_USER FROM SYSIBM.SYSDUMMY1");
   }

   public boolean supportsDdlTransactions() {
      return true;
   }

   public String getBooleanTrue() {
      return "true";
   }

   public String getBooleanFalse() {
      return "false";
   }

   public String doQuote(String identifier) {
      return "\"" + identifier + "\"";
   }

   public boolean catalogIsSchema() {
      return false;
   }

   public boolean useSingleConnection() {
      return true;
   }
}
