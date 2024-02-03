package org.jooq.exception;

public class DataChangedException extends DataAccessException {
   private static final long serialVersionUID = -6460945824599280420L;

   public DataChangedException(String message) {
      super(message);
   }

   public DataChangedException(String message, Throwable cause) {
      super(message, cause);
   }
}
