package org.jooq.exception;

public class DataTypeException extends DataAccessException {
   private static final long serialVersionUID = -6460945824599280420L;

   public DataTypeException(String message) {
      super(message);
   }

   public DataTypeException(String message, Throwable cause) {
      super(message, cause);
   }
}
