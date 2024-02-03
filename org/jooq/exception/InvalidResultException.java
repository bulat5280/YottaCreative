package org.jooq.exception;

public class InvalidResultException extends DataAccessException {
   private static final long serialVersionUID = -6460945824599280420L;

   public InvalidResultException(String message) {
      super(message);
   }
}
