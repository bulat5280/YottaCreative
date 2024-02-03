package org.flywaydb.core.internal.database.postgresql;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.SqlStatementBuilder;
import org.flywaydb.core.internal.sqlscript.SqlStatement;
import org.flywaydb.core.internal.util.StringUtils;

public class PostgreSQLSqlStatementBuilder extends SqlStatementBuilder {
   private static final Delimiter COPY_DELIMITER = new Delimiter("\\.", true);
   static final String DOLLAR_QUOTE_REGEX = "(\\$[A-Za-z0-9_]*\\$).*";
   private boolean firstLine = true;
   private String copyStatement;
   private boolean pgCopy;
   private String statementStart = "";

   public PostgreSQLSqlStatementBuilder(Delimiter defaultDelimiter) {
      super(defaultDelimiter);
   }

   public SqlStatement getSqlStatement() {
      return (SqlStatement)(this.pgCopy ? new PostgreSQLCopyStatement(this.lineNumber, this.statement.toString()) : super.getSqlStatement());
   }

   protected void applyStateChanges(String line) {
      super.applyStateChanges(line);
      if (this.executeInTransaction) {
         if (StringUtils.countOccurrencesOf(this.statementStart, " ") < 100) {
            this.statementStart = this.statementStart + line;
            this.statementStart = this.statementStart + " ";
            this.statementStart = this.statementStart.replaceAll("\\s+", " ");
         }

         if (this.statementStart.matches("(CREATE|DROP) (DATABASE|TABLESPACE) .*") || this.statementStart.matches("ALTER SYSTEM .*") || this.statementStart.matches("(CREATE|DROP)( UNIQUE)? INDEX CONCURRENTLY .*") || this.statementStart.matches("REINDEX( VERBOSE)? (SCHEMA|DATABASE|SYSTEM) .*") || this.statementStart.matches("VACUUM .*") || this.statementStart.matches("DISCARD ALL .*") || this.statementStart.matches("ALTER TYPE .* ADD VALUE .*")) {
            this.executeInTransaction = false;
         }

      }
   }

   protected String[] tokenizeLine(String line) {
      return StringUtils.tokenizeToStringArray(line, " @<>;:=|(),+{}\\[\\]");
   }

   protected String extractAlternateOpenQuote(String token) {
      Matcher matcher = Pattern.compile("(\\$[A-Za-z0-9_]*\\$).*").matcher(token);
      return matcher.find() ? matcher.group(1) : null;
   }

   protected Delimiter changeDelimiterIfNecessary(String line, Delimiter delimiter) {
      if (this.pgCopy) {
         return COPY_DELIMITER;
      } else {
         if (this.firstLine) {
            this.firstLine = false;
            if (line.matches("COPY|COPY\\s.*")) {
               this.copyStatement = line;
            }
         } else if (this.copyStatement != null) {
            this.copyStatement = this.copyStatement + " " + line;
         }

         if (this.copyStatement != null && this.copyStatement.contains(" FROM STDIN")) {
            this.pgCopy = true;
            return COPY_DELIMITER;
         } else if (this.statementStart.matches("CREATE( OR REPLACE)? RULE .* DO (ALSO|INSTEAD) \\(.+;\\w?\\)\\w?;")) {
            return Delimiter.SEMICOLON;
         } else {
            return this.statementStart.matches("CREATE( OR REPLACE)? RULE .* DO (ALSO|INSTEAD) \\(.*") ? null : delimiter;
         }
      }
   }

   protected String cleanToken(String token) {
      return token.startsWith("E'") ? token.substring(token.indexOf("'")) : token;
   }
}
