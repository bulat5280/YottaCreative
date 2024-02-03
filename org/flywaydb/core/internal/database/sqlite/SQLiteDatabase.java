package org.flywaydb.core.internal.database.sqlite;

import java.sql.Connection;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.SqlScript;
import org.flywaydb.core.internal.exception.FlywayDbUpgradeRequiredException;
import org.flywaydb.core.internal.util.scanner.Resource;

public class SQLiteDatabase extends Database {
   public SQLiteDatabase(FlywayConfiguration configuration, Connection connection) {
      super(configuration, connection, 12);
   }

   protected org.flywaydb.core.internal.database.Connection getConnection(Connection connection, int nullType) {
      return new SQLiteConnection(this.configuration, this, connection, nullType);
   }

   protected final void ensureSupported() {
      String version = this.majorVersion + "." + this.minorVersion;
      if (this.majorVersion < 3) {
         throw new FlywayDbUpgradeRequiredException("SQLite", version, "3.7.2");
      }
   }

   protected SqlScript doCreateSqlScript(Resource sqlScriptResource, String sqlScriptSource, boolean mixed) {
      return new SQLiteSqlScript(sqlScriptResource, sqlScriptSource, mixed);
   }

   public String getDbName() {
      return "sqlite";
   }

   protected String doGetCurrentUser() {
      return "";
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

   public String doQuote(String identifier) {
      return "\"" + identifier + "\"";
   }

   public boolean catalogIsSchema() {
      return true;
   }

   public boolean useSingleConnection() {
      return true;
   }
}
