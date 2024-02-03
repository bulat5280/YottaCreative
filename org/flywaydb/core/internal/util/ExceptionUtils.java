package org.flywaydb.core.internal.util;

public class ExceptionUtils {
   private ExceptionUtils() {
   }

   public static Throwable getRootCause(Throwable throwable) {
      if (throwable == null) {
         return null;
      } else {
         Throwable cause;
         Throwable rootCause;
         for(cause = throwable; (rootCause = cause.getCause()) != null; cause = rootCause) {
         }

         return cause;
      }
   }
}
