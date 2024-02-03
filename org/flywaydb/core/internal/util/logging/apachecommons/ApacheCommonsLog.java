package org.flywaydb.core.internal.util.logging.apachecommons;

import org.flywaydb.core.api.logging.Log;

public class ApacheCommonsLog implements Log {
   private final org.apache.commons.logging.Log logger;

   public ApacheCommonsLog(org.apache.commons.logging.Log logger) {
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
      this.logger.error(message, e);
   }
}
