package org.flywaydb.core.internal.database.sqlserver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.SqlStatementBuilder;
import org.flywaydb.core.internal.util.StringUtils;

public class SQLServerSqlStatementBuilder extends SqlStatementBuilder {
   private static final Pattern KEYWORDS_BEFORE_STRING_LITERAL_REGEX = Pattern.compile("^(LIKE)('.*)");
   private String statementStart = "";

   public SQLServerSqlStatementBuilder(Delimiter defaultDelimiter) {
      super(defaultDelimiter);
   }

   protected void applyStateChanges(String line) {
      super.applyStateChanges(line);
      if (this.executeInTransaction) {
         if (StringUtils.countOccurrencesOf(this.statementStart, " ") < 3) {
            this.statementStart = this.statementStart + line;
            this.statementStart = this.statementStart + " ";
            this.statementStart = this.statementStart.replaceAll("\\s+", " ");
         }

         if (this.statementStart.matches("^(BACKUP|RESTORE|ALTER DATABASE) .*")) {
            this.executeInTransaction = false;
         }

      }
   }

   protected String cleanToken(String token) {
      if (token.startsWith("N'")) {
         return token.substring(token.indexOf("'"));
      } else {
         Matcher beforeMatcher = KEYWORDS_BEFORE_STRING_LITERAL_REGEX.matcher(token);
         if (beforeMatcher.find()) {
            token = beforeMatcher.group(2);
         }

         return token;
      }
   }
}
