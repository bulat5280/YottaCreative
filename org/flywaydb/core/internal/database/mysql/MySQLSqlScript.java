package org.flywaydb.core.internal.database.mysql;

import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.ExecutableSqlScript;
import org.flywaydb.core.internal.database.SqlStatementBuilder;
import org.flywaydb.core.internal.util.jdbc.ContextImpl;
import org.flywaydb.core.internal.util.scanner.Resource;

class MySQLSqlScript extends ExecutableSqlScript<ContextImpl> {
   MySQLSqlScript(Resource sqlScriptResource, String sqlScriptSource, boolean mixed) {
      super(sqlScriptResource, sqlScriptSource, mixed);
   }

   protected SqlStatementBuilder createSqlStatementBuilder() {
      return new MySQLSqlStatementBuilder(Delimiter.SEMICOLON);
   }
}
