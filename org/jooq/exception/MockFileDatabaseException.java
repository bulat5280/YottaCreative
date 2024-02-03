package org.jooq.exception;

public class MockFileDatabaseException extends DataAccessException {
   private static final long serialVersionUID = -6460945824599280420L;

   public MockFileDatabaseException(String message) {
      super(message);
   }

   public MockFileDatabaseException(String message, Throwable cause) {
      super(message, cause);
   }
}
