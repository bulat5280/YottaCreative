package org.flywaydb.core.internal.database.sybasease;

import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.SqlStatementBuilder;

public class SybaseASESqlStatementBuilder extends SqlStatementBuilder {
   SybaseASESqlStatementBuilder(Delimiter defaultDelimiter) {
      super(defaultDelimiter);
   }

   protected String computeAlternateCloseQuote(String openQuote) {
      char specialChar = openQuote.charAt(2);
      switch(specialChar) {
      case '(':
         return ")'";
      default:
         return specialChar + "'";
      }
   }
}
