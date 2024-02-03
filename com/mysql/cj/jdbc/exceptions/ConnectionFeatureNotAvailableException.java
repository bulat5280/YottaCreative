package com.mysql.cj.jdbc.exceptions;

import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.core.Messages;

public class ConnectionFeatureNotAvailableException extends CommunicationsException {
   private static final long serialVersionUID = 8315412078945570018L;

   public ConnectionFeatureNotAvailableException(JdbcConnection conn, long lastPacketSentTimeMs, Exception underlyingException) {
      super(conn, lastPacketSentTimeMs, 0L, underlyingException);
   }

   public ConnectionFeatureNotAvailableException(String message, Throwable underlyingException) {
      super(message, underlyingException);
   }

   public String getMessage() {
      return Messages.getString("ConnectionFeatureNotAvailableException.0");
   }

   public String getSQLState() {
      return "01S00";
   }
}
