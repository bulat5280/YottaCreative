package org.jooq.exception;

public class IOException extends DataAccessException {
   private static final long serialVersionUID = 491834858363345767L;

   public IOException(String message, java.io.IOException cause) {
      super(message, cause);
   }

   public synchronized java.io.IOException getCause() {
      return (java.io.IOException)super.getCause();
   }

   public synchronized Throwable initCause(Throwable cause) {
      if (!(cause instanceof java.io.IOException)) {
         throw new IllegalArgumentException("Can only wrap java.io.IOException: " + cause);
      } else {
         return super.initCause(cause);
      }
   }
}
