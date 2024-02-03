package org.jooq.exception;

public class DetachedException extends DataAccessException {
   private static final long serialVersionUID = -6460945824599280420L;

   public DetachedException(String message) {
      super(message);
   }
}
