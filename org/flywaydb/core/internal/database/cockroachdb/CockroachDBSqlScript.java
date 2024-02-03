package org.flywaydb.core.internal.database.cockroachdb;

import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.ExecutableSqlScript;
import org.flywaydb.core.internal.database.SqlStatementBuilder;
import org.flywaydb.core.internal.util.scanner.Resource;

class CockroachDBSqlScript extends ExecutableSqlScript {
   CockroachDBSqlScript(Resource sqlScriptResource, String sqlScriptSource, boolean mixed) {
      super(sqlScriptResource, sqlScriptSource, mixed);
   }

   protected SqlStatementBuilder createSqlStatementBuilder() {
      return new CockroachDBSqlStatementBuilder(Delimiter.SEMICOLON);
   }
}
