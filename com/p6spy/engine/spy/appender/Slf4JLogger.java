package com.p6spy.engine.spy.appender;

import com.p6spy.engine.logging.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4JLogger extends FormattedLogger {
   private Logger log = LoggerFactory.getLogger("p6spy");

   public void logException(Exception e) {
      this.log.info((String)"", (Throwable)e);
   }

   public void logText(String text) {
      this.log.info(text);
   }

   public void logSQL(int connectionId, String now, long elapsed, Category category, String prepared, String sql) {
      String msg = this.strategy.formatMessage(connectionId, now, elapsed, category.toString(), prepared, sql);
      if (Category.ERROR.equals(category)) {
         this.log.error(msg);
      } else if (Category.WARN.equals(category)) {
         this.log.warn(msg);
      } else if (Category.DEBUG.equals(category)) {
         this.log.debug(msg);
      } else {
         this.log.info(msg);
      }

   }

   public boolean isCategoryEnabled(Category category) {
      if (Category.ERROR.equals(category)) {
         return this.log.isErrorEnabled();
      } else if (Category.WARN.equals(category)) {
         return this.log.isWarnEnabled();
      } else {
         return Category.DEBUG.equals(category) ? this.log.isDebugEnabled() : this.log.isInfoEnabled();
      }
   }
}
