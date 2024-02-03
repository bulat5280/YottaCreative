package org.flywaydb.core.internal.database.sqlite;

import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.ExecutableSqlScript;
import org.flywaydb.core.internal.database.SqlStatementBuilder;
import org.flywaydb.core.internal.util.scanner.Resource;

class SQLiteSqlScript extends ExecutableSqlScript {
   SQLiteSqlScript(Resource sqlScriptResource, String sqlScriptSource, boolean mixed) {
      super(sqlScriptResource, sqlScriptSource, mixed);
   }

   protected SqlStatementBuilder createSqlStatementBuilder() {
      return new SQLiteSqlStatementBuilder(Delimiter.SEMICOLON);
   }
}
