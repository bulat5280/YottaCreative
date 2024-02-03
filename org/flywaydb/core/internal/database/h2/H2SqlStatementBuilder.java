package org.flywaydb.core.internal.database.h2;

import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.SqlStatementBuilder;

public class H2SqlStatementBuilder extends SqlStatementBuilder {
   public H2SqlStatementBuilder(Delimiter defaultDelimiter) {
      super(defaultDelimiter);
   }

   protected String extractAlternateOpenQuote(String token) {
      return token.startsWith("$$") ? "$$" : null;
   }
}
