package com.p6spy.engine.spy.appender;

import com.p6spy.engine.logging.Category;
import java.io.PrintStream;

public class StdoutLogger extends FormattedLogger {
   protected PrintStream getStream() {
      return System.out;
   }

   public void logException(Exception e) {
      e.printStackTrace(this.getStream());
   }

   public void logText(String text) {
      this.getStream().println(text);
   }

   public boolean isCategoryEnabled(Category category) {
      return true;
   }
}
