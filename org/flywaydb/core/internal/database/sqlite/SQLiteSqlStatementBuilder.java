package org.flywaydb.core.internal.database.sqlite;

import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.SqlStatementBuilder;
import org.flywaydb.core.internal.util.StringUtils;

public class SQLiteSqlStatementBuilder extends SqlStatementBuilder {
   private String statementStart = "";

   SQLiteSqlStatementBuilder(Delimiter defaultDelimiter) {
      super(defaultDelimiter);
   }

   protected Delimiter changeDelimiterIfNecessary(String line, Delimiter delimiter) {
      if (StringUtils.countOccurrencesOf(this.statementStart, " ") < 8) {
         this.statementStart = this.statementStart + line;
         this.statementStart = this.statementStart + " ";
         this.statementStart = this.statementStart.replaceAll("\\s+", " ");
      }

      boolean createTriggerStatement = this.statementStart.matches("CREATE( TEMP| TEMPORARY)? TRIGGER.*");
      return createTriggerStatement && !line.endsWith("END;") ? null : this.defaultDelimiter;
   }

   protected String cleanToken(String token) {
      return token.startsWith("X'") ? token.substring(token.indexOf("'")) : token;
   }
}
