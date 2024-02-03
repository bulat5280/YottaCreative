package org.flywaydb.core.internal.database.db2;

import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.ExecutableSqlScript;
import org.flywaydb.core.internal.database.SqlStatementBuilder;
import org.flywaydb.core.internal.util.jdbc.ContextImpl;
import org.flywaydb.core.internal.util.scanner.Resource;

class DB2SqlScript extends ExecutableSqlScript<ContextImpl> {
   DB2SqlScript(Resource resource, String sqlScriptSource, boolean mixed) {
      super(resource, sqlScriptSource, mixed);
   }

   protected SqlStatementBuilder createSqlStatementBuilder() {
      return new DB2SqlStatementBuilder(Delimiter.SEMICOLON);
   }
}
