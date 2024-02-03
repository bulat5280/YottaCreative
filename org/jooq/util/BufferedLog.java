package org.jooq.util;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.jooq.Log;
import org.jooq.tools.JooqLogger;

public class BufferedLog implements Log {
   private static final Queue<BufferedLog.Message> messages = new ConcurrentLinkedQueue();
   private final JooqLogger delegate;

   public static BufferedLog getLogger(Class<?> type) {
      return new BufferedLog(JooqLogger.getLogger(type));
   }

   BufferedLog(JooqLogger delegate) {
      this.delegate = delegate;
   }

   public static synchronized void flush() {
      JooqLogger delegate = JooqLogger.getLogger(BufferedLog.class);
      if (!messages.isEmpty()) {
         delegate.warn("Buffered warning and error messages:");
         delegate.warn("------------------------------------");
      }

      Iterator var1 = messages.iterator();

      while(var1.hasNext()) {
         BufferedLog.Message m = (BufferedLog.Message)var1.next();
         switch(m.level) {
         case DEBUG:
            delegate.debug(m.message, m.details, m.throwable);
            break;
         case TRACE:
            delegate.trace(m.message, m.details, m.throwable);
            break;
         case INFO:
            delegate.info(m.message, m.details, m.throwable);
            break;
         case WARN:
            delegate.warn(m.message, m.details, m.throwable);
            break;
         case ERROR:
            delegate.error(m.message, m.details, m.throwable);
            break;
         case FATAL:
            delegate.error(m.message, m.details, m.throwable);
         }
      }

      messages.clear();
   }

   static BufferedLog.Message message(Log.Level level, Object message) {
      return new BufferedLog.Message(level, message, (Object)null, (Throwable)null);
   }

   static BufferedLog.Message message(Log.Level level, Object message, Object details) {
      return new BufferedLog.Message(level, message, details, (Throwable)null);
   }

   static BufferedLog.Message message(Log.Level level, Object message, Throwable throwable) {
      return new BufferedLog.Message(level, message, (Object)null, throwable);
   }

   static BufferedLog.Message message(Log.Level level, Object message, Object details, Throwable throwable) {
      return new BufferedLog.Message(level, message, details, throwable);
   }

   public boolean isTraceEnabled() {
      return this.delegate.isTraceEnabled();
   }

   public void trace(Object message) {
      this.delegate.trace(message);
   }

   public void trace(Object message, Object details) {
      this.delegate.trace(message, details);
   }

   public void trace(Object message, Throwable throwable) {
      this.delegate.trace(message, throwable);
   }

   public void trace(Object message, Object details, Throwable throwable) {
      this.delegate.trace(message, details, throwable);
   }

   public boolean isDebugEnabled() {
      return this.delegate.isDebugEnabled();
   }

   public void debug(Object message) {
      this.delegate.debug(message);
   }

   public void debug(Object message, Object details) {
      this.delegate.debug(message, details);
   }

   public void debug(Object message, Throwable throwable) {
      this.delegate.debug(message, throwable);
   }

   public void debug(Object message, Object details, Throwable throwable) {
      this.delegate.debug(message, details, throwable);
   }

   public boolean isInfoEnabled() {
      return this.delegate.isInfoEnabled();
   }

   public void info(Object message) {
      this.delegate.info(message);
   }

   public void info(Object message, Object details) {
      this.delegate.info(message, details);
   }

   public void info(Object message, Throwable throwable) {
      this.delegate.info(message, throwable);
   }

   public void info(Object message, Object details, Throwable throwable) {
      this.delegate.info(message, details, throwable);
   }

   public void warn(Object message) {
      this.delegate.warn(message);
      messages.add(message(Log.Level.WARN, message));
   }

   public void warn(Object message, Object details) {
      this.delegate.warn(message, details);
      messages.add(message(Log.Level.WARN, message, details));
   }

   public void warn(Object message, Throwable throwable) {
      this.delegate.warn(message, throwable);
      messages.add(message(Log.Level.WARN, message, throwable));
   }

   public void warn(Object message, Object details, Throwable throwable) {
      this.delegate.warn(message, details, throwable);
      messages.add(message(Log.Level.WARN, message, details, throwable));
   }

   public void error(Object message) {
      this.delegate.error(message);
      messages.add(message(Log.Level.ERROR, message));
   }

   public void error(Object message, Object details) {
      this.delegate.error(message, details);
      messages.add(message(Log.Level.ERROR, message, details));
   }

   public void error(Object message, Throwable throwable) {
      this.delegate.error(message, throwable);
      messages.add(message(Log.Level.ERROR, message, throwable));
   }

   public void error(Object message, Object details, Throwable throwable) {
      this.delegate.error(message, details, throwable);
      messages.add(message(Log.Level.ERROR, message, details, throwable));
   }

   private static class Message {
      final Log.Level level;
      final Object message;
      final Object details;
      final Throwable throwable;

      Message(Log.Level level, Object message, Object details, Throwable throwable) {
         this.level = level;
         this.message = message;
         this.details = details;
         this.throwable = throwable;
      }
   }
}
