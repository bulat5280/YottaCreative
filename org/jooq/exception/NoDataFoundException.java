package org.jooq.exception;

public class NoDataFoundException extends InvalidResultException {
   private static final long serialVersionUID = -6460945824599280420L;

   public NoDataFoundException(String message) {
      super(message);
   }
}
