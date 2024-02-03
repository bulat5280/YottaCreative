package org.flywaydb.core.internal.util.logging.javautil;

import java.util.logging.Logger;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogCreator;

public class JavaUtilLogCreator implements LogCreator {
   public Log createLogger(Class<?> clazz) {
      return new JavaUtilLog(Logger.getLogger(clazz.getName()));
   }
}
