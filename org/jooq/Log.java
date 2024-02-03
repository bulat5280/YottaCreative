package org.jooq;

public interface Log {
   boolean isTraceEnabled();

   void trace(Object var1);

   void trace(Object var1, Object var2);

   void trace(Object var1, Throwable var2);

   void trace(Object var1, Object var2, Throwable var3);

   boolean isDebugEnabled();

   void debug(Object var1);

   void debug(Object var1, Object var2);

   void debug(Object var1, Throwable var2);

   void debug(Object var1, Object var2, Throwable var3);

   boolean isInfoEnabled();

   void info(Object var1);

   void info(Object var1, Object var2);

   void info(Object var1, Throwable var2);

   void info(Object var1, Object var2, Throwable var3);

   void warn(Object var1);

   void warn(Object var1, Object var2);

   void warn(Object var1, Throwable var2);

   void warn(Object var1, Object var2, Throwable var3);

   void error(Object var1);

   void error(Object var1, Object var2);

   void error(Object var1, Throwable var2);

   void error(Object var1, Object var2, Throwable var3);

   public static enum Level {
      TRACE,
      DEBUG,
      INFO,
      WARN,
      ERROR,
      FATAL;

      public boolean supports(Log.Level level) {
         return this.ordinal() <= level.ordinal();
      }
   }
}
