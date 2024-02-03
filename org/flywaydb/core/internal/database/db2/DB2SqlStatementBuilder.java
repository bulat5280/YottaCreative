package org.flywaydb.core.internal.database.db2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.SqlStatementBuilder;
import org.flywaydb.core.internal.util.StringUtils;

public class DB2SqlStatementBuilder extends SqlStatementBuilder {
   private static final String DELIMITER_KEYWORD = "--#SET TERMINATOR";
   private static final Pattern KEYWORDS_AFTER_STRING_LITERAL_REGEX = Pattern.compile("(.*')(DO)(?!.)");
   private static final Pattern BLOCK_STATEMENT_REGEX = Pattern.compile("^CREATE( OR REPLACE)? (FUNCTION|PROCEDURE|TRIGGER)(\\s.*)?");
   private String statementStart = "";
   private Delimiter currentDelimiter;
   private String previousLine;

   public DB2SqlStatementBuilder(Delimiter defaultDelimiter) {
      super(defaultDelimiter);
      this.currentDelimiter = this.defaultDelimiter;
      this.previousLine = "";
   }

   public Delimiter extractNewDelimiterFromLine(String line) {
      return line.toUpperCase().startsWith("--#SET TERMINATOR") ? new Delimiter(line.substring("--#SET TERMINATOR".length()).trim(), false) : null;
   }

   protected boolean isSingleLineComment(String line) {
      return line.startsWith("--") && !line.startsWith("--#SET TERMINATOR");
   }

   protected String cleanToken(String token) {
      if (token.startsWith("X'")) {
         return token.substring(token.indexOf("'"));
      } else {
         Matcher afterMatcher = KEYWORDS_AFTER_STRING_LITERAL_REGEX.matcher(token);
         if (afterMatcher.find()) {
            token = afterMatcher.group(1);
         }

         return super.cleanToken(token);
      }
   }

   protected Delimiter changeDelimiterIfNecessary(String line, Delimiter delimiter) {
      if (delimiter != null && !delimiter.equals(this.currentDelimiter)) {
         this.currentDelimiter = delimiter;
      }

      if (StringUtils.countOccurrencesOf(this.statementStart, " ") < 4) {
         this.statementStart = this.statementStart + line;
         this.statementStart = this.statementStart + " ";
      }

      return this.currentDelimiter;
   }

   protected boolean isBlockStatement() {
      return BLOCK_STATEMENT_REGEX.matcher(this.statementStart).matches();
   }

   protected boolean isBlockBeginToken(String token) {
      return "BEGIN".equals(token) || "CASE".equals(token) || "IF".equals(token) || "DO".equals(token) || "LOOP".equals(token) || "REPEAT".equals(token);
   }

   protected boolean isBlockEndToken(String token) {
      return "END".equals(token);
   }

   protected String[] tokenizeLine(String line) {
      String processedLine = line;
      if (this.previousLine.endsWith("END")) {
         if (line.startsWith("IF")) {
            processedLine = line.substring(2);
         } else if (line.startsWith("FOR")) {
            processedLine = line.substring(3);
         } else if (line.startsWith("CASE")) {
            processedLine = line.substring(4);
         } else if (line.startsWith("LOOP")) {
            processedLine = line.substring(4);
         } else if (line.startsWith("WHILE")) {
            processedLine = line.substring(5);
         } else if (line.startsWith("REPEAT")) {
            processedLine = line.substring(6);
         }
      }

      if (StringUtils.hasLength(line)) {
         this.previousLine = line;
      }

      return super.tokenizeLine(processedLine.replaceAll("END (IF|FOR|CASE|LOOP|WHILE|REPEAT)", "END"));
   }
}
