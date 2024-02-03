package org.flywaydb.core.internal.database.oracle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.SqlStatementBuilder;
import org.flywaydb.core.internal.util.StringUtils;

public class OracleSqlStatementBuilder extends SqlStatementBuilder {
   private static final Log LOG = LogFactory.getLog(SqlStatementBuilder.class);
   private static final Pattern KEYWORDS_BEFORE_STRING_LITERAL_REGEX = Pattern.compile("^(N|DATE|IF|ELSIF|SELECT|IMMEDIATE|RETURN|IS)('.*)");
   private static final Pattern KEYWORDS_AFTER_STRING_LITERAL_REGEX = Pattern.compile("(.*')(USING|THEN|FROM|AND|OR|AS)(?!.)");
   private static final Pattern DECLARE_BEGIN_REGEX = toRegex("DECLARE|BEGIN");
   private static final Pattern PLSQL_REGEX = Pattern.compile("^CREATE(\\s+OR\\s+REPLACE)?(\\s+(NON)?EDITIONABLE)?\\s+(FUNCTION|PROCEDURE|PACKAGE|TYPE|TRIGGER).*");
   private static final Pattern JAVA_REGEX = Pattern.compile("^CREATE(\\s+OR\\s+REPLACE)?(\\s+AND\\s+(RESOLVE|COMPILE))?(\\s+NOFORCE)?\\s+JAVA\\s+(SOURCE|RESOURCE|CLASS).*");
   private static final Delimiter PLSQL_DELIMITER = new Delimiter("/", true);
   private String statementStart = "";

   private static Pattern toRegex(String... commands) {
      return Pattern.compile("^(" + StringUtils.arrayToDelimitedString("|", commands) + ")(\\s.*)?");
   }

   public OracleSqlStatementBuilder(Delimiter defaultDelimiter) {
      super(defaultDelimiter);
   }

   protected void applyStateChanges(String line) {
      super.applyStateChanges(line);
      if (StringUtils.countOccurrencesOf(this.statementStart, " ") < 8) {
         this.statementStart = this.statementStart + line;
         this.statementStart = this.statementStart + " ";
         this.statementStart = this.statementStart.replaceAll("\\s+", " ");
      }

   }

   protected Delimiter changeDelimiterIfNecessary(String line, Delimiter delimiter) {
      if (DECLARE_BEGIN_REGEX.matcher(line).matches()) {
         return PLSQL_DELIMITER;
      } else {
         return !PLSQL_REGEX.matcher(this.statementStart).matches() && !JAVA_REGEX.matcher(this.statementStart).matches() ? delimiter : PLSQL_DELIMITER;
      }
   }

   protected String cleanToken(String token) {
      if (token.startsWith("'") && token.endsWith("'")) {
         return token;
      } else {
         Matcher beforeMatcher = KEYWORDS_BEFORE_STRING_LITERAL_REGEX.matcher(token);
         if (beforeMatcher.find()) {
            token = beforeMatcher.group(2);
         }

         Matcher afterMatcher = KEYWORDS_AFTER_STRING_LITERAL_REGEX.matcher(token);
         if (afterMatcher.find()) {
            token = afterMatcher.group(1);
         }

         return token;
      }
   }

   protected String simplifyLine(String line) {
      String simplifiedQQuotes = StringUtils.replaceAll(StringUtils.replaceAll(line, "q'(", "q'["), ")'", "]'");
      return super.simplifyLine(simplifiedQQuotes);
   }

   protected String extractAlternateOpenQuote(String token) {
      return token.startsWith("Q'") && token.length() >= 3 ? token.substring(0, 3) : null;
   }

   protected String computeAlternateCloseQuote(String openQuote) {
      char specialChar = openQuote.charAt(2);
      switch(specialChar) {
      case '(':
         return ")'";
      case '<':
         return ">'";
      case '[':
         return "]'";
      case '{':
         return "}'";
      default:
         return specialChar + "'";
      }
   }

   public boolean canDiscard() {
      return super.canDiscard() || this.statementStart.equals("/ ");
   }
}
