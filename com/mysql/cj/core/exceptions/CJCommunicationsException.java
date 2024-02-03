package com.mysql.cj.core.exceptions;

import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.io.ServerSession;

public class CJCommunicationsException extends CJException {
   private static final long serialVersionUID = 344035358493554245L;
   private String exceptionMessage;

   public CJCommunicationsException() {
   }

   public CJCommunicationsException(String message) {
      super(message);
   }

   public CJCommunicationsException(String message, Throwable cause) {
      super(message, cause);
   }

   public CJCommunicationsException(Throwable cause) {
      super(cause);
   }

   protected CJCommunicationsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
   }

   public String getMessage() {
      return this.exceptionMessage != null ? this.exceptionMessage : super.getMessage();
   }

   public void init(PropertySet propertySet, ServerSession serverSession, long lastPacketSentTimeMs, long lastPacketReceivedTimeMs) {
      this.exceptionMessage = ExceptionFactory.createLinkFailureMessageBasedOnHeuristics(propertySet, serverSession, lastPacketSentTimeMs, lastPacketReceivedTimeMs, this.getCause());
   }
}
