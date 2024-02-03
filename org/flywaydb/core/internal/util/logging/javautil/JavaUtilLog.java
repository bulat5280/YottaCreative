package org.flywaydb.core.internal.util.logging.javautil;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.flywaydb.core.api.logging.Log;

public class JavaUtilLog implements Log {
   private final Logger logger;

   public JavaUtilLog(Logger logger) {
      this.logger = logger;
   }

   public void debug(String message) {
      this.log(Level.FINE, message, (Exception)null);
   }

   public void info(String message) {
      this.log(Level.INFO, message, (Exception)null);
   }

   public void warn(String message) {
      this.log(Level.WARNING, message, (Exception)null);
   }

   public void error(String message) {
      this.log(Level.SEVERE, message, (Exception)null);
   }

   public void error(String message, Exception e) {
      this.log(Level.SEVERE, message, e);
   }

   private void log(Level level, String message, Exception e) {
      LogRecord record = new LogRecord(level, message);
      record.setLoggerName(this.logger.getName());
      record.setThrown(e);
      record.setSourceClassName(this.logger.getName());
      record.setSourceMethodName(this.getMethodName());
      this.logger.log(record);
   }

   private String getMethodName() {
      StackTraceElement[] steArray = (new Throwable()).getStackTrace();
      StackTraceElement[] var2 = steArray;
      int var3 = steArray.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         StackTraceElement stackTraceElement = var2[var4];
         if (this.logger.getName().equals(stackTraceElement.getClassName())) {
            return stackTraceElement.getMethodName();
         }
      }

      return null;
   }
}
