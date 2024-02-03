package org.flywaydb.core.internal.database.saphana;

import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.ExecutableSqlScript;
import org.flywaydb.core.internal.database.SqlStatementBuilder;
import org.flywaydb.core.internal.util.scanner.Resource;

class SAPHANASqlScript extends ExecutableSqlScript {
   SAPHANASqlScript(Resource sqlScriptResource, String sqlScriptSource, boolean mixed) {
      super(sqlScriptResource, sqlScriptSource, mixed);
   }

   protected SqlStatementBuilder createSqlStatementBuilder() {
      return new SAPHANASqlStatementBuilder(Delimiter.SEMICOLON);
   }
}
