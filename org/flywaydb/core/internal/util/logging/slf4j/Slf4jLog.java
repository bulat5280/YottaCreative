package org.flywaydb.core.internal.util.logging.slf4j;

import org.flywaydb.core.api.logging.Log;
import org.slf4j.Logger;

public class Slf4jLog implements Log {
   private final Logger logger;

   public Slf4jLog(Logger logger) {
      this.logger = logger;
   }

   public void debug(String message) {
      this.logger.debug(message);
   }

   public void info(String message) {
      this.logger.info(message);
   }

   public void warn(String message) {
      this.logger.warn(message);
   }

   public void error(String message) {
      this.logger.error(message);
   }

   public void error(String message, Exception e) {
      this.logger.error((String)message, (Throwable)e);
   }
}
