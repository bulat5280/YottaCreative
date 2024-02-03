package org.flywaydb.core.internal.database.db2;

import java.sql.Connection;
import java.sql.SQLException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.SqlScript;
import org.flywaydb.core.internal.exception.FlywayDbUpgradeRequiredException;
import org.flywaydb.core.internal.exception.FlywayEnterpriseUpgradeRequiredException;
import org.flywaydb.core.internal.util.scanner.Resource;

public class DB2Database extends Database {
   public DB2Database(FlywayConfiguration configuration, Connection connection) {
      super(configuration, connection, 12);
   }

   protected org.flywaydb.core.internal.database.Connection getConnection(Connection connection, int nullType) {
      return new DB2Connection(this.configuration, this, connection, nullType);
   }

   protected final void ensureSupported() {
      String version = this.majorVersion + "." + this.minorVersion;
      if (this.majorVersion >= 9 && (this.majorVersion != 9 || this.minorVersion >= 7)) {
         if (this.majorVersion == 9 || this.majorVersion == 10 && this.minorVersion < 5) {
            throw new FlywayEnterpriseUpgradeRequiredException("IBM", "DB2", version);
         } else {
            if (this.majorVersion > 11 || this.majorVersion == 11 && this.minorVersion > 1) {
               this.recommendFlywayUpgrade("DB2", version);
            }

         }
      } else {
         throw new FlywayDbUpgradeRequiredException("DB2", version, "9.7");
      }
   }

   protected SqlScript doCreateSqlScript(Resource resource, String sqlScriptSource, boolean mixed) {
      return new DB2SqlScript(resource, sqlScriptSource, mixed);
   }

   public String getRawCreateScript() {
      return "CREATE TABLE \"${schema}\".\"${table}\" (\n    \"installed_rank\" INT NOT NULL,\n    \"version\" VARCHAR(50),\n    \"description\" VARCHAR(200) NOT NULL,\n    \"type\" VARCHAR(20) NOT NULL,\n    \"script\" VARCHAR(1000) NOT NULL,\n    \"checksum\" INT,\n    \"installed_by\" VARCHAR(100) NOT NULL,\n    \"installed_on\" TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL,\n    \"execution_time\" INT NOT NULL,\n    \"success\" SMALLINT NOT NULL,\n    CONSTRAINT \"${table}_s\" CHECK (\"success\" in(0,1))\n) ORGANIZE BY ROW;\nALTER TABLE \"${schema}\".\"${table}\" ADD CONSTRAINT \"${table}_pk\" PRIMARY KEY (\"installed_rank\");\n\nCREATE INDEX \"${schema}\".\"${table}_s_idx\" ON \"${schema}\".\"${table}\" (\"success\");";
   }

   public String getDbName() {
      return "db2";
   }

   protected String doGetCurrentUser() throws SQLException {
      return this.mainConnection.getJdbcTemplate().queryForString("select CURRENT_USER from sysibm.sysdummy1");
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
      return false;
   }

   public boolean useSingleConnection() {
      return false;
   }
}
