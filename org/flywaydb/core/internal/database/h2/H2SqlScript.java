package org.flywaydb.core.internal.database.h2;

import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.ExecutableSqlScript;
import org.flywaydb.core.internal.database.SqlStatementBuilder;
import org.flywaydb.core.internal.util.scanner.Resource;

public class H2SqlScript extends ExecutableSqlScript {
   public H2SqlScript(Resource sqlScriptResource, String sqlScriptSource, boolean mixed) {
      super(sqlScriptResource, sqlScriptSource, mixed);
   }

   protected SqlStatementBuilder createSqlStatementBuilder() {
      return new H2SqlStatementBuilder(Delimiter.SEMICOLON);
   }
}
