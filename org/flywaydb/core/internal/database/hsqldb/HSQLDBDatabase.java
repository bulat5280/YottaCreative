package org.flywaydb.core.internal.database.hsqldb;

import java.sql.Connection;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.SqlScript;
import org.flywaydb.core.internal.exception.FlywayDbUpgradeRequiredException;
import org.flywaydb.core.internal.util.scanner.Resource;

public class HSQLDBDatabase extends Database {
   public HSQLDBDatabase(FlywayConfiguration configuration, Connection connection) {
      super(configuration, connection, 12);
   }

   protected org.flywaydb.core.internal.database.Connection getConnection(Connection connection, int nullType) {
      return new HSQLDBConnection(this.configuration, this, connection, nullType);
   }

   protected final void ensureSupported() {
      String version = this.majorVersion + "." + this.minorVersion;
      if (this.majorVersion < 1 || this.majorVersion == 18 && this.minorVersion < 8) {
         throw new FlywayDbUpgradeRequiredException("HSQLDB", version, "1.8");
      }
   }

   protected SqlScript doCreateSqlScript(Resource sqlScriptResource, String sqlScriptSource, boolean mixed) {
      return new HSQLDBSqlScript(sqlScriptResource, sqlScriptSource, mixed);
   }

   public String getDbName() {
      return "hsqldb";
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
      return "\"" + identifier + "\"";
   }

   public boolean catalogIsSchema() {
      return false;
   }

   public boolean useSingleConnection() {
      return true;
   }
}
