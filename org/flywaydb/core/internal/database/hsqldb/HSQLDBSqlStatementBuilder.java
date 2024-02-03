package org.flywaydb.core.internal.database.hsqldb;

import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.SqlStatementBuilder;

public class HSQLDBSqlStatementBuilder extends SqlStatementBuilder {
   private boolean insideAtomicBlock;

   HSQLDBSqlStatementBuilder(Delimiter defaultDelimiter) {
      super(defaultDelimiter);
   }

   protected Delimiter changeDelimiterIfNecessary(String line, Delimiter delimiter) {
      if (line.contains("BEGIN ATOMIC")) {
         this.insideAtomicBlock = true;
      }

      if (line.endsWith("END;")) {
         this.insideAtomicBlock = false;
      }

      return this.insideAtomicBlock ? null : this.defaultDelimiter;
   }
}
