package com.p6spy.engine.spy.appender;

import com.p6spy.engine.logging.Category;

public class BatchFileLogger extends FileLogger {
   public static final char BATCH_SEPARATOR = ';';
   private boolean endOfStatement = true;

   public void logException(Exception e) {
   }

   public void logSQL(int connectionId, String now, long elapsed, Category category, String prepared, String sql) {
      if (this.endOfStatement) {
         this.getStream().println(';');
      }

      if (category.equals("statement")) {
         String actual = null != sql && 0 != sql.length() ? sql : prepared;
         this.getStream().print(actual);
         this.endOfStatement = true;
      } else if (!Category.COMMIT.equals(category) && !Category.ROLLBACK.equals(category)) {
         this.getStream().println("-- " + category);
         this.endOfStatement = false;
      } else {
         this.getStream().print(category);
         this.endOfStatement = true;
      }

      this.getStream().flush();
   }

   public void logText(String text) {
   }
}
