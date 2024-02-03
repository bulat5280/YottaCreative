package com.p6spy.engine.spy.appender;

import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.spy.P6SpyOptions;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomLineFormat implements MessageFormattingStrategy {
   private static final MessageFormattingStrategy FALLBACK_FORMATTING_STRATEGY = new SingleLineFormat();
   public static final String CONNECTION_ID = "%(connectionId)";
   public static final String CURRENT_TIME = "%(currentTime)";
   public static final String EXECUTION_TIME = "%(executionTime)";
   public static final String CATEGORY = "%(category)";
   public static final String EFFECTIVE_SQL = "%(effectiveSql)";
   public static final String EFFECTIVE_SQL_SINGLELINE = "%(effectiveSqlSingleLine)";
   public static final String SQL = "%(sql)";
   public static final String SQL_SINGLE_LINE = "%(sqlSingleLine)";

   public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql) {
      String customLogMessageFormat = P6SpyOptions.getActiveInstance().getCustomLogMessageFormat();
      return customLogMessageFormat == null ? FALLBACK_FORMATTING_STRATEGY.formatMessage(connectionId, now, elapsed, category, prepared, sql) : customLogMessageFormat.replaceAll(Pattern.quote("%(connectionId)"), Integer.toString(connectionId)).replaceAll(Pattern.quote("%(currentTime)"), now).replaceAll(Pattern.quote("%(executionTime)"), Long.toString(elapsed)).replaceAll(Pattern.quote("%(category)"), category).replaceAll(Pattern.quote("%(effectiveSql)"), Matcher.quoteReplacement(prepared)).replaceAll(Pattern.quote("%(effectiveSqlSingleLine)"), Matcher.quoteReplacement(P6Util.singleLine(prepared))).replaceAll(Pattern.quote("%(sql)"), Matcher.quoteReplacement(sql)).replaceAll(Pattern.quote("%(sqlSingleLine)"), Matcher.quoteReplacement(P6Util.singleLine(sql)));
   }
}
