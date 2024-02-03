package org.flywaydb.core.api;

public class FlywayException extends RuntimeException {
   public FlywayException(String message, Throwable cause) {
      super(message, cause);
   }

   public FlywayException(Throwable cause) {
      super(cause);
   }

   public FlywayException(String message) {
      super(message);
   }

   public FlywayException() {
   }
}
