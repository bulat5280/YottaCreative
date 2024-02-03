package org.flywaydb.core.internal.database.redshift;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.SqlStatementBuilder;
import org.flywaydb.core.internal.database.StandardSqlStatement;
import org.flywaydb.core.internal.sqlscript.SqlStatement;
import org.flywaydb.core.internal.util.StringUtils;

public class RedshiftSqlStatementBuilder extends SqlStatementBuilder {
   static final String DOLLAR_QUOTE_REGEX = "(\\$[A-Za-z0-9_]*\\$).*";
   private String statementStart = "";

   RedshiftSqlStatementBuilder(Delimiter defaultDelimiter) {
      super(defaultDelimiter);
   }

   public SqlStatement getSqlStatement() {
      return new StandardSqlStatement(this.lineNumber, this.statement.toString());
   }

   protected void applyStateChanges(String line) {
      super.applyStateChanges(line);
      if (this.executeInTransaction) {
         if (StringUtils.countOccurrencesOf(this.statementStart, " ") < 8) {
            this.statementStart = this.statementStart + line;
            this.statementStart = this.statementStart + " ";
            this.statementStart = this.statementStart.replaceAll("\\s+", " ");
         }

         if (this.statementStart.matches("^(CREATE|DROP) LIBRARY .*") || this.statementStart.matches("^CREATE EXTERNAL TABLE .*") || this.statementStart.matches("^ALTER TABLE .* APPEND FROM .*") || this.statementStart.matches("^VACUUM .*")) {
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

   protected String cleanToken(String token) {
      return token.startsWith("E'") ? token.substring(token.indexOf("'")) : token;
   }
}
