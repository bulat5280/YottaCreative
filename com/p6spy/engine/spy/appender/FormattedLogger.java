package com.p6spy.engine.spy.appender;

import com.p6spy.engine.logging.Category;

public abstract class FormattedLogger implements P6Logger {
   protected MessageFormattingStrategy strategy = new SingleLineFormat();

   protected FormattedLogger() {
   }

   public void logSQL(int connectionId, String now, long elapsed, Category category, String prepared, String sql) {
      this.logText(this.strategy.formatMessage(connectionId, now, elapsed, category.toString(), prepared, sql));
   }

   public void setStrategy(MessageFormattingStrategy strategy) {
      this.strategy = strategy;
   }
}
