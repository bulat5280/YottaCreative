package org.flywaydb.core.internal.util.logging.apachecommons;

import org.apache.commons.logging.LogFactory;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogCreator;

public class ApacheCommonsLogCreator implements LogCreator {
   public Log createLogger(Class<?> clazz) {
      return new ApacheCommonsLog(LogFactory.getLog(clazz));
   }
}
