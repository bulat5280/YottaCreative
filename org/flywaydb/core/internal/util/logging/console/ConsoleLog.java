package org.flywaydb.core.internal.util.logging.console;

import org.flywaydb.core.api.logging.Log;

public class ConsoleLog implements Log {
   private final ConsoleLog.Level level;

   public ConsoleLog(ConsoleLog.Level level) {
      this.level = level;
   }

   public void debug(String message) {
      if (this.level == ConsoleLog.Level.DEBUG) {
         System.out.println("DEBUG: " + message);
      }

   }

   public void info(String message) {
      if (this.level.compareTo(ConsoleLog.Level.INFO) <= 0) {
         System.out.println(message);
      }

   }

   public void warn(String message) {
      System.out.println("WARNING: " + message);
   }

   public void error(String message) {
      System.err.println("ERROR: " + message);
   }

   public void error(String message, Exception e) {
      System.err.println("ERROR: " + message);
      e.printStackTrace(System.err);
   }

   public static enum Level {
      DEBUG,
      INFO,
      WARN;
   }
}
