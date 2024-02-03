package com.mysql.cj.jdbc.exceptions;

import com.mysql.cj.api.exceptions.StreamingNotifiable;
import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import java.sql.SQLRecoverableException;

public class CommunicationsException extends SQLRecoverableException implements StreamingNotifiable {
   private static final long serialVersionUID = 4317904269000988676L;
   private String exceptionMessage;

   public CommunicationsException(JdbcConnection conn, long lastPacketSentTimeMs, long lastPacketReceivedTimeMs, Exception underlyingException) {
      this(ExceptionFactory.createLinkFailureMessageBasedOnHeuristics(conn.getPropertySet(), conn.getSession().getServerSession(), lastPacketSentTimeMs, lastPacketReceivedTimeMs, underlyingException), underlyingException);
   }

   public CommunicationsException(String message, Throwable underlyingException) {
      this.exceptionMessage = message;
      if (underlyingException != null) {
         this.initCause(underlyingException);
      }

   }

   public String getMessage() {
      return this.exceptionMessage;
   }

   public String getSQLState() {
      return "08S01";
   }

   public void setWasStreamingResults() {
      this.exceptionMessage = Messages.getString("CommunicationsException.ClientWasStreaming");
   }
}
