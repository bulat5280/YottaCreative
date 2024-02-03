package org.jooq.util;

public class GeneratorException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public GeneratorException(String message) {
      super(message);
   }

   public GeneratorException(String message, Throwable cause) {
      super(message, cause);
   }
}
