package org.flywaydb.core.internal.database.saphana;

import java.sql.Connection;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Database;
import org.flywaydb.core.internal.database.SqlScript;
import org.flywaydb.core.internal.exception.FlywayEnterpriseUpgradeRequiredException;
import org.flywaydb.core.internal.util.scanner.Resource;

public class SAPHANADatabase extends Database<SAPHANAConnection> {
   public SAPHANADatabase(FlywayConfiguration configuration, Connection connection) {
      super(configuration, connection, 12);
   }

   protected SAPHANAConnection getConnection(Connection connection, int nullType) {
      return new SAPHANAConnection(this.configuration, this, connection, nullType);
   }

   protected void ensureSupported() {
      String version = this.majorVersion + "." + this.minorVersion;
      if (this.majorVersion == 1) {
         throw new FlywayEnterpriseUpgradeRequiredException("SAP", "HANA", version);
      } else {
         if (this.majorVersion > 2) {
            this.recommendFlywayUpgrade("SAP HANA", version);
         }

      }
   }

   protected SqlScript doCreateSqlScript(Resource sqlScriptResource, String sqlScriptSource, boolean mixed) {
      return new SAPHANASqlScript(sqlScriptResource, sqlScriptSource, mixed);
   }

   public String getDbName() {
      return "saphana";
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
}
