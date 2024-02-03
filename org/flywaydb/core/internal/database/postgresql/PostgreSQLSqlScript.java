package org.flywaydb.core.internal.database.postgresql;

import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.ExecutableSqlScript;
import org.flywaydb.core.internal.database.SqlStatementBuilder;
import org.flywaydb.core.internal.util.jdbc.ContextImpl;
import org.flywaydb.core.internal.util.scanner.Resource;

class PostgreSQLSqlScript extends ExecutableSqlScript<ContextImpl> {
   PostgreSQLSqlScript(Resource sqlScriptResource, String sqlScriptSource, boolean mixed) {
      super(sqlScriptResource, sqlScriptSource, mixed);
   }

   protected SqlStatementBuilder createSqlStatementBuilder() {
      return new PostgreSQLSqlStatementBuilder(Delimiter.SEMICOLON);
   }
}
