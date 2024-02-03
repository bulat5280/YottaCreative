package org.flywaydb.core.internal.util.logging.console;

import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogCreator;

public class ConsoleLogCreator implements LogCreator {
   private final ConsoleLog.Level level;

   public ConsoleLogCreator(ConsoleLog.Level level) {
      this.level = level;
   }

   public Log createLogger(Class<?> clazz) {
      return new ConsoleLog(this.level);
   }
}
