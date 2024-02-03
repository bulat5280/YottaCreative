package org.flywaydb.core.internal.database.redshift;

import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.ExecutableSqlScript;
import org.flywaydb.core.internal.database.SqlStatementBuilder;
import org.flywaydb.core.internal.util.jdbc.ContextImpl;
import org.flywaydb.core.internal.util.scanner.Resource;

class RedshiftSqlScript extends ExecutableSqlScript<ContextImpl> {
   RedshiftSqlScript(Resource sqlScriptResource, String sqlScriptSource, boolean mixed) {
      super(sqlScriptResource, sqlScriptSource, mixed);
   }

   protected SqlStatementBuilder createSqlStatementBuilder() {
      return new RedshiftSqlStatementBuilder(Delimiter.SEMICOLON);
   }
}
