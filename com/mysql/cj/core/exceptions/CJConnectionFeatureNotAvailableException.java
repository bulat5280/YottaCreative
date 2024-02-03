package com.mysql.cj.core.exceptions;

import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.io.ServerSession;
import com.mysql.cj.core.Messages;

public class CJConnectionFeatureNotAvailableException extends CJCommunicationsException {
   private static final long serialVersionUID = -4129847384681995107L;

   public CJConnectionFeatureNotAvailableException(PropertySet propertySet, ServerSession serverSession, long lastPacketSentTimeMs, Exception underlyingException) {
      super((Throwable)underlyingException);
      this.init(propertySet, serverSession, lastPacketSentTimeMs, 0L);
   }

   public String getMessage() {
      return Messages.getString("ConnectionFeatureNotAvailableException.0");
   }
}
