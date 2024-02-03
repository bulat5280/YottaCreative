package org.flywaydb.core.internal.database;

import org.flywaydb.core.internal.sqlscript.SqlStatement;
import org.flywaydb.core.internal.util.jdbc.ContextImpl;

public abstract class AbstractSqlStatement<C extends ContextImpl> implements SqlStatement<C> {
   protected int lineNumber;
   protected String sql;

   public AbstractSqlStatement(int lineNumber, String sql) {
      this.lineNumber = lineNumber;
      this.sql = sql;
   }

   public int getLineNumber() {
      return this.lineNumber;
   }

   public String getSql() {
      return this.sql;
   }
}
