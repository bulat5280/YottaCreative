package org.jooq.exception;

public class MappingException extends DataAccessException {
   private static final long serialVersionUID = -6460945824599280420L;

   public MappingException(String message) {
      super(message);
   }

   public MappingException(String message, Throwable cause) {
      super(message, cause);
   }
}
