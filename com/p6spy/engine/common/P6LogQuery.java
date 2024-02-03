package com.p6spy.engine.common;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.logging.P6LogLoadableOptions;
import com.p6spy.engine.logging.P6LogOptions;
import com.p6spy.engine.spy.P6ModuleManager;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.FileLogger;
import com.p6spy.engine.spy.appender.FormattedLogger;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import com.p6spy.engine.spy.appender.P6Logger;
import com.p6spy.engine.spy.option.P6OptionChangedListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class P6LogQuery implements P6OptionChangedListener {
   private static final Set<Category> CATEGORIES_IMPLICITLY_INCLUDED;
   private static final Set<String> ON_CHANGE;
   protected static P6Logger logger;

   public void optionChanged(String key, Object oldValue, Object newValue) {
      if (ON_CHANGE.contains(key)) {
         initialize();
      }

   }

   public static synchronized void initialize() {
      P6ModuleManager moduleManager = P6ModuleManager.getInstance();
      if (null != moduleManager) {
         P6SpyOptions opts = (P6SpyOptions)moduleManager.getOptions(P6SpyOptions.class);
         logger = opts.getAppenderInstance();
         if (logger != null) {
            if (logger instanceof FileLogger) {
               String logfile = opts.getLogfile();
               ((FileLogger)logger).setLogfile(logfile);
            }

            if (logger instanceof FormattedLogger) {
               MessageFormattingStrategy strategy = opts.getLogMessageFormatInstance();
               if (strategy != null) {
                  ((FormattedLogger)logger).setStrategy(strategy);
               }
            }
         }

      }
   }

   protected static void doLog(long elapsed, Category category, String prepared, String sql) {
      doLog(-1, elapsed, category, prepared, sql);
   }

   protected static void doLogElapsed(int connectionId, long timeElapsedNanos, Category category, String prepared, String sql) {
      doLog(connectionId, timeElapsedNanos, category, prepared, sql);
   }

   protected static void doLog(int connectionId, long elapsedNanos, Category category, String prepared, String sql) {
      if (logger == null) {
         initialize();
         if (logger == null) {
            return;
         }
      }

      String format = P6SpyOptions.getActiveInstance().getDateformat();
      String stringNow;
      if (format == null) {
         stringNow = Long.toString(System.currentTimeMillis());
      } else {
         stringNow = (new SimpleDateFormat(format)).format(new Date()).trim();
      }

      logger.logSQL(connectionId, stringNow, TimeUnit.NANOSECONDS.toMillis(elapsedNanos), category, prepared, sql);
      boolean stackTrace = P6SpyOptions.getActiveInstance().getStackTrace();
      if (stackTrace) {
         String stackTraceClass = P6SpyOptions.getActiveInstance().getStackTraceClass();
         Exception e = new Exception();
         if (stackTraceClass != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stack = sw.toString();
            if (stack.indexOf(stackTraceClass) == -1) {
               e = null;
            }
         }

         if (e != null) {
            logger.logException(e);
         }
      }

   }

   static boolean isLoggable(String sql) {
      if (null == sql) {
         return false;
      } else {
         P6LogLoadableOptions opts = P6LogOptions.getActiveInstance();
         if (!opts.getFilter()) {
            return true;
         } else {
            Pattern sqlExpressionPattern = opts.getSQLExpressionPattern();
            Pattern includeExcludePattern = opts.getIncludeExcludePattern();
            return (sqlExpressionPattern == null || sqlExpressionPattern != null && sqlExpressionPattern.matcher(sql).matches()) && (includeExcludePattern == null || includeExcludePattern != null && includeExcludePattern.matcher(sql).matches());
         }
      }
   }

   static boolean isCategoryOk(Category category) {
      P6LogLoadableOptions opts = P6LogOptions.getActiveInstance();
      if (null == opts) {
         return CATEGORIES_IMPLICITLY_INCLUDED.contains(category);
      } else {
         Set<Category> excludeCategories = opts.getExcludeCategoriesSet();
         return logger != null && logger.isCategoryEnabled(category) && (excludeCategories == null || !excludeCategories.contains(category));
      }
   }

   public static void log(Category category, String prepared, String sql) {
      if (logger != null && isCategoryOk(category)) {
         doLog(-1L, category, prepared, sql);
      }

   }

   public static void log(Category category, Loggable loggable) {
      if (logger != null && isCategoryOk(category) && isLoggable(loggable.getSql())) {
         doLog(-1L, category, loggable.getSql(), loggable.getSqlWithValues());
      }

   }

   public static void logElapsed(int connectionId, long timeElapsedNanos, Category category, String prepared, String sql) {
      if (logger != null && meetsThresholdRequirement(timeElapsedNanos) && isCategoryOk(category) && isLoggable(sql)) {
         doLogElapsed(connectionId, timeElapsedNanos, category, prepared, sql);
      } else if (isDebugEnabled()) {
         debug("P6Spy intentionally did not log category: " + category + ", statement: " + sql + "  Reason: logger=" + logger + ", isLoggable=" + isLoggable(sql) + ", isCategoryOk=" + isCategoryOk(category) + ", meetsTreshold=" + meetsThresholdRequirement(timeElapsedNanos));
      }

   }

   public static void logElapsed(int connectionId, long timeElapsedNanos, Category category, Loggable loggable) {
      String sql;
      if (logger != null && meetsThresholdRequirement(timeElapsedNanos) && isCategoryOk(category) && isLoggable(sql = loggable.getSql())) {
         doLogElapsed(connectionId, timeElapsedNanos, category, sql, loggable.getSqlWithValues());
      } else if (isDebugEnabled()) {
         sql = loggable.getSqlWithValues();
         debug("P6Spy intentionally did not log category: " + category + ", statement: " + sql + "  Reason: logger=" + logger + ", isLoggable=" + isLoggable(sql) + ", isCategoryOk=" + isCategoryOk(category) + ", meetsTreshold=" + meetsThresholdRequirement(timeElapsedNanos));
      }

   }

   private static boolean meetsThresholdRequirement(long timeTaken) {
      P6LogLoadableOptions opts = P6LogOptions.getActiveInstance();
      long executionThreshold = null != opts ? opts.getExecutionThreshold() : 0L;
      return executionThreshold <= 0L || TimeUnit.NANOSECONDS.toMillis(timeTaken) > executionThreshold;
   }

   public static void info(String sql) {
      if (logger != null && isCategoryOk(Category.INFO)) {
         doLog(-1L, Category.INFO, "", sql);
      }

   }

   public static boolean isDebugEnabled() {
      return isCategoryOk(Category.DEBUG);
   }

   public static void debug(String sql) {
      if (isDebugEnabled()) {
         if (logger != null) {
            doLog(-1L, Category.DEBUG, "", sql);
         } else {
            System.err.println(sql);
         }
      }

   }

   public static void error(String sql) {
      System.err.println("Warning: " + sql);
      if (logger != null) {
         doLog(-1L, Category.ERROR, "", sql);
      }

   }

   public static P6Logger getLogger() {
      return logger;
   }

   static {
      CATEGORIES_IMPLICITLY_INCLUDED = new HashSet(Arrays.asList(Category.ERROR, Category.OUTAGE));
      ON_CHANGE = new HashSet(Arrays.asList("appenderInstance", "logfile", "logMessageFormatInstance"));
      initialize();
   }
}
