package org.jooq.tools;

import org.jooq.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JooqLogger implements Log {
   private static volatile Log.Level globalThreshold;
   private Logger slf4j;
   private org.apache.log4j.Logger log4j;
   private java.util.logging.Logger util;
   private boolean supportsTrace = true;
   private boolean supportsDebug = true;
   private boolean supportsInfo = true;

   public static JooqLogger getLogger(Class<?> clazz) {
      JooqLogger result = new JooqLogger();

      try {
         result.slf4j = LoggerFactory.getLogger(clazz);
      } catch (Throwable var8) {
         try {
            result.log4j = org.apache.log4j.Logger.getLogger(clazz);
         } catch (Throwable var7) {
            result.util = java.util.logging.Logger.getLogger(clazz.getName());
         }
      }

      try {
         result.isInfoEnabled();
      } catch (Throwable var6) {
         result.supportsInfo = false;
      }

      try {
         result.isDebugEnabled();
      } catch (Throwable var5) {
         result.supportsDebug = false;
      }

      try {
         result.isTraceEnabled();
      } catch (Throwable var4) {
         result.supportsTrace = false;
      }

      return result;
   }

   public boolean isTraceEnabled() {
      if (!globalThreshold.supports(Log.Level.TRACE)) {
         return false;
      } else if (!this.supportsTrace) {
         return false;
      } else if (this.slf4j != null) {
         return this.slf4j.isTraceEnabled();
      } else {
         return this.log4j != null ? this.log4j.isTraceEnabled() : this.util.isLoggable(java.util.logging.Level.FINER);
      }
   }

   public void trace(Object message) {
      this.trace(message, (Object)null);
   }

   public void trace(Object message, Object details) {
      if (globalThreshold.supports(Log.Level.TRACE)) {
         if (this.slf4j != null) {
            this.slf4j.trace(this.getMessage(message, details));
         } else if (this.log4j != null) {
            this.log4j.trace(this.getMessage(message, details));
         } else {
            this.util.finer("" + this.getMessage(message, details));
         }

      }
   }

   public void trace(Object message, Throwable throwable) {
      this.trace(message, (Object)null, throwable);
   }

   public void trace(Object message, Object details, Throwable throwable) {
      if (globalThreshold.supports(Log.Level.TRACE)) {
         if (this.slf4j != null) {
            this.slf4j.trace(this.getMessage(message, details), throwable);
         } else if (this.log4j != null) {
            this.log4j.trace(this.getMessage(message, details), throwable);
         } else {
            this.util.log(java.util.logging.Level.FINER, "" + this.getMessage(message, details), throwable);
         }

      }
   }

   public boolean isDebugEnabled() {
      if (!globalThreshold.supports(Log.Level.DEBUG)) {
         return false;
      } else if (!this.supportsDebug) {
         return false;
      } else if (this.slf4j != null) {
         return this.slf4j.isDebugEnabled();
      } else {
         return this.log4j != null ? this.log4j.isDebugEnabled() : this.util.isLoggable(java.util.logging.Level.FINE);
      }
   }

   public void debug(Object message) {
      this.debug(message, (Object)null);
   }

   public void debug(Object message, Object details) {
      if (globalThreshold.supports(Log.Level.DEBUG)) {
         if (this.slf4j != null) {
            this.slf4j.debug(this.getMessage(message, details));
         } else if (this.log4j != null) {
            this.log4j.debug(this.getMessage(message, details));
         } else {
            this.util.fine("" + this.getMessage(message, details));
         }

      }
   }

   public void debug(Object message, Throwable throwable) {
      this.debug(message, (Object)null, throwable);
   }

   public void debug(Object message, Object details, Throwable throwable) {
      if (globalThreshold.supports(Log.Level.DEBUG)) {
         if (this.slf4j != null) {
            this.slf4j.debug(this.getMessage(message, details), throwable);
         } else if (this.log4j != null) {
            this.log4j.debug(this.getMessage(message, details), throwable);
         } else {
            this.util.log(java.util.logging.Level.FINE, "" + this.getMessage(message, details), throwable);
         }

      }
   }

   public boolean isInfoEnabled() {
      if (!globalThreshold.supports(Log.Level.INFO)) {
         return false;
      } else if (!this.supportsInfo) {
         return false;
      } else if (this.slf4j != null) {
         return this.slf4j.isInfoEnabled();
      } else {
         return this.log4j != null ? this.log4j.isInfoEnabled() : this.util.isLoggable(java.util.logging.Level.INFO);
      }
   }

   public void info(Object message) {
      this.info(message, (Object)null);
   }

   public void info(Object message, Object details) {
      if (globalThreshold.supports(Log.Level.INFO)) {
         if (this.slf4j != null) {
            this.slf4j.info(this.getMessage(message, details));
         } else if (this.log4j != null) {
            this.log4j.info(this.getMessage(message, details));
         } else {
            this.util.info("" + this.getMessage(message, details));
         }

      }
   }

   public void info(Object message, Throwable throwable) {
      this.info(message, (Object)null, throwable);
   }

   public void info(Object message, Object details, Throwable throwable) {
      if (globalThreshold.supports(Log.Level.INFO)) {
         if (this.slf4j != null) {
            this.slf4j.info(this.getMessage(message, details), throwable);
         } else if (this.log4j != null) {
            this.log4j.info(this.getMessage(message, details), throwable);
         } else {
            this.util.log(java.util.logging.Level.INFO, "" + this.getMessage(message, details), throwable);
         }

      }
   }

   public void warn(Object message) {
      this.warn(message, (Object)null);
   }

   public void warn(Object message, Object details) {
      if (globalThreshold.supports(Log.Level.WARN)) {
         if (this.slf4j != null) {
            this.slf4j.warn(this.getMessage(message, details));
         } else if (this.log4j != null) {
            this.log4j.warn(this.getMessage(message, details));
         } else {
            this.util.warning("" + this.getMessage(message, details));
         }

      }
   }

   public void warn(Object message, Throwable throwable) {
      this.warn(message, (Object)null, throwable);
   }

   public void warn(Object message, Object details, Throwable throwable) {
      if (globalThreshold.supports(Log.Level.WARN)) {
         if (this.slf4j != null) {
            this.slf4j.warn(this.getMessage(message, details), throwable);
         } else if (this.log4j != null) {
            this.log4j.warn(this.getMessage(message, details), throwable);
         } else {
            this.util.log(java.util.logging.Level.WARNING, "" + this.getMessage(message, details), throwable);
         }

      }
   }

   public void error(Object message) {
      this.error(message, (Object)null);
   }

   public void error(Object message, Object details) {
      if (globalThreshold.supports(Log.Level.ERROR)) {
         if (this.slf4j != null) {
            this.slf4j.error(this.getMessage(message, details));
         } else if (this.log4j != null) {
            this.log4j.error(this.getMessage(message, details));
         } else {
            this.util.severe("" + this.getMessage(message, details));
         }

      }
   }

   public void error(Object message, Throwable throwable) {
      this.error(message, (Object)null, throwable);
   }

   public void error(Object message, Object details, Throwable throwable) {
      if (globalThreshold.supports(Log.Level.ERROR)) {
         if (this.slf4j != null) {
            this.slf4j.error(this.getMessage(message, details), throwable);
         } else if (this.log4j != null) {
            this.log4j.error(this.getMessage(message, details), throwable);
         } else {
            this.util.log(java.util.logging.Level.SEVERE, "" + this.getMessage(message, details), throwable);
         }

      }
   }

   private String getMessage(Object message, Object details) {
      StringBuilder sb = new StringBuilder();
      sb.append(StringUtils.rightPad("" + message, 25));
      if (details != null) {
         sb.append(": ");
         sb.append(details);
      }

      return sb.toString();
   }

   public static void globalThreshold(JooqLogger.Level level) {
      switch(level) {
      case TRACE:
         globalThreshold(Log.Level.TRACE);
         break;
      case DEBUG:
         globalThreshold(Log.Level.DEBUG);
         break;
      case INFO:
         globalThreshold(Log.Level.INFO);
         break;
      case WARN:
         globalThreshold(Log.Level.WARN);
         break;
      case ERROR:
         globalThreshold(Log.Level.ERROR);
         break;
      case FATAL:
         globalThreshold(Log.Level.FATAL);
      }

   }

   public static void globalThreshold(Log.Level level) {
      globalThreshold = level;
   }

   static {
      globalThreshold = Log.Level.TRACE;
   }

   /** @deprecated */
   @Deprecated
   public static enum Level {
      TRACE,
      DEBUG,
      INFO,
      WARN,
      ERROR,
      FATAL;

      public boolean supports(JooqLogger.Level level) {
         return this.ordinal() <= level.ordinal();
      }
   }
}
