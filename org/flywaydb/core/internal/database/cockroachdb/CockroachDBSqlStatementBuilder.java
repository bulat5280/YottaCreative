package org.flywaydb.core.internal.database.cockroachdb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.flywaydb.core.internal.database.Delimiter;
import org.flywaydb.core.internal.database.SqlStatementBuilder;
import org.flywaydb.core.internal.util.StringUtils;

public class CockroachDBSqlStatementBuilder extends SqlStatementBuilder {
   static final String DOLLAR_QUOTE_REGEX = "(\\$[A-Za-z0-9_]*\\$).*";

   CockroachDBSqlStatementBuilder(Delimiter defaultDelimiter) {
      super(defaultDelimiter);
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
