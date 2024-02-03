package org.flywaydb.core.internal.database.derby;

import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.SqlStatementBuilder;

public class DerbySqlStatementBuilder extends SqlStatementBuilder {
   public DerbySqlStatementBuilder(Delimiter defaultDelimiter) {
      super(defaultDelimiter);
   }

   protected String extractAlternateOpenQuote(String token) {
      return token.startsWith("$$") ? "$$" : null;
   }

   protected String cleanToken(String token) {
      return token.startsWith("X'") ? token.substring(token.indexOf("'")) : super.cleanToken(token);
   }
}
