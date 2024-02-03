package org.flywaydb.core.internal.database.oracle;

import java.sql.SQLException;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.ExecutableSqlScript;
import org.flywaydb.core.internal.database.SqlStatementBuilder;
import org.flywaydb.core.internal.sqlscript.SqlStatement;
import org.flywaydb.core.internal.util.scanner.Resource;

class OracleSqlScript extends ExecutableSqlScript<OracleContextImpl> {
   private static final Log LOG = LogFactory.getLog(OracleSqlScript.class);

   OracleSqlScript(Resource sqlScriptResource, String sqlScriptSource, boolean mixed) {
      super(sqlScriptResource, sqlScriptSource, mixed);
   }

   protected SqlStatementBuilder createSqlStatementBuilder() {
      return new OracleSqlStatementBuilder(Delimiter.SEMICOLON);
   }

   protected void handleException(SQLException e, SqlStatement sqlStatement, OracleContextImpl context) {
      super.handleException(e, sqlStatement, context);
   }

   protected OracleContextImpl createContext() {
      return new OracleContextImpl();
   }
}
