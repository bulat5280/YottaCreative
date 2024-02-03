package org.jooq.exception;

public class TooManyRowsException extends InvalidResultException {
   private static final long serialVersionUID = -6460945824599280420L;

   public TooManyRowsException(String message) {
      super(message);
   }
}
