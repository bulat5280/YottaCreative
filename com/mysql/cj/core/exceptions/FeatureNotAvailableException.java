package com.mysql.cj.core.exceptions;

public class FeatureNotAvailableException extends CJException {
   private static final long serialVersionUID = -6649508222074639690L;

   public FeatureNotAvailableException() {
   }

   public FeatureNotAvailableException(String message) {
      super(message);
   }

   public FeatureNotAvailableException(String message, Throwable cause) {
      super(message, cause);
   }

   public FeatureNotAvailableException(Throwable cause) {
      super(cause);
   }

   public FeatureNotAvailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
   }
}
