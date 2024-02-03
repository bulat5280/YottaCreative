package com.mysql.cj.core.exceptions;

import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.io.ServerSession;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.util.Util;
import java.net.BindException;

public class ExceptionFactory {
   private static final long DEFAULT_WAIT_TIMEOUT_SECONDS = 28800L;
   private static final int DUE_TO_TIMEOUT_FALSE = 0;
   private static final int DUE_TO_TIMEOUT_MAYBE = 2;
   private static final int DUE_TO_TIMEOUT_TRUE = 1;

   public static CJException createException(String message) {
      return createException(CJException.class, message);
   }

   public static <T extends CJException> T createException(Class<T> clazz, String message) {
      CJException sqlEx;
      try {
         sqlEx = (CJException)clazz.getConstructor(String.class).newInstance(message);
      } catch (Throwable var4) {
         sqlEx = new CJException(message);
      }

      return sqlEx;
   }

   public static CJException createException(String message, ExceptionInterceptor interceptor) {
      return createException(CJException.class, message, interceptor);
   }

   public static <T extends CJException> T createException(Class<T> clazz, String message, ExceptionInterceptor interceptor) {
      T sqlEx = createException(clazz, message);
      return sqlEx;
   }

   public static CJException createException(String message, Throwable cause) {
      return createException(CJException.class, message, cause);
   }

   public static <T extends CJException> T createException(Class<T> clazz, String message, Throwable cause) {
      T sqlEx = createException(clazz, message);
      if (cause != null) {
         try {
            sqlEx.initCause(cause);
         } catch (Throwable var5) {
         }

         if (cause instanceof CJException) {
            sqlEx.setSQLState(((CJException)cause).getSQLState());
            sqlEx.setVendorCode(((CJException)cause).getVendorCode());
            sqlEx.setTransient(((CJException)cause).isTransient());
         }
      }

      return sqlEx;
   }

   public static CJException createException(String message, Throwable cause, ExceptionInterceptor interceptor) {
      return createException(CJException.class, message, cause, interceptor);
   }

   public static CJException createException(String message, String sqlState, int vendorErrorCode, boolean isTransient, Throwable cause, ExceptionInterceptor interceptor) {
      CJException ex = createException(CJException.class, message, cause, interceptor);
      ex.setSQLState(sqlState);
      ex.setVendorCode(vendorErrorCode);
      ex.setTransient(isTransient);
      return ex;
   }

   public static <T extends CJException> T createException(Class<T> clazz, String message, Throwable cause, ExceptionInterceptor interceptor) {
      T sqlEx = createException(clazz, message, cause);
      return sqlEx;
   }

   public static CJCommunicationsException createCommunicationsException(PropertySet propertySet, ServerSession serverSession, long lastPacketSentTimeMs, long lastPacketReceivedTimeMs, Throwable cause, ExceptionInterceptor interceptor) {
      CJCommunicationsException sqlEx = (CJCommunicationsException)createException(CJCommunicationsException.class, (String)null, cause, interceptor);
      sqlEx.init(propertySet, serverSession, lastPacketSentTimeMs, lastPacketReceivedTimeMs);
      return sqlEx;
   }

   public static String createLinkFailureMessageBasedOnHeuristics(PropertySet propertySet, ServerSession serverSession, long lastPacketSentTimeMs, long lastPacketReceivedTimeMs, Throwable underlyingException) {
      long serverTimeoutSeconds = 0L;
      boolean isInteractiveClient = false;
      if (propertySet != null) {
         isInteractiveClient = (Boolean)propertySet.getBooleanReadableProperty("interactiveClient").getValue();
         String serverTimeoutSecondsStr = null;
         if (serverSession != null) {
            if (isInteractiveClient) {
               serverTimeoutSecondsStr = serverSession.getServerVariable("interactive_timeout");
            } else {
               serverTimeoutSecondsStr = serverSession.getServerVariable("wait_timeout");
            }
         }

         if (serverTimeoutSecondsStr != null) {
            try {
               serverTimeoutSeconds = Long.parseLong(serverTimeoutSecondsStr);
            } catch (NumberFormatException var22) {
               serverTimeoutSeconds = 0L;
            }
         }
      }

      StringBuilder exceptionMessageBuf = new StringBuilder();
      long nowMs = System.currentTimeMillis();
      if (lastPacketSentTimeMs == 0L) {
         lastPacketSentTimeMs = nowMs;
      }

      long timeSinceLastPacketSentMs = nowMs - lastPacketSentTimeMs;
      long timeSinceLastPacketSeconds = timeSinceLastPacketSentMs / 1000L;
      long timeSinceLastPacketReceivedMs = nowMs - lastPacketReceivedTimeMs;
      int dueToTimeout = 0;
      StringBuilder timeoutMessageBuf = null;
      if (serverTimeoutSeconds != 0L) {
         if (timeSinceLastPacketSeconds > serverTimeoutSeconds) {
            dueToTimeout = 1;
            timeoutMessageBuf = new StringBuilder();
            timeoutMessageBuf.append(Messages.getString("CommunicationsException.2"));
            if (!isInteractiveClient) {
               timeoutMessageBuf.append(Messages.getString("CommunicationsException.3"));
            } else {
               timeoutMessageBuf.append(Messages.getString("CommunicationsException.4"));
            }
         }
      } else if (timeSinceLastPacketSeconds > 28800L) {
         dueToTimeout = 2;
         timeoutMessageBuf = new StringBuilder();
         timeoutMessageBuf.append(Messages.getString("CommunicationsException.5"));
         timeoutMessageBuf.append(Messages.getString("CommunicationsException.6"));
         timeoutMessageBuf.append(Messages.getString("CommunicationsException.7"));
         timeoutMessageBuf.append(Messages.getString("CommunicationsException.8"));
      }

      Object[] timingInfo;
      if (dueToTimeout != 1 && dueToTimeout != 2) {
         if (underlyingException instanceof BindException) {
            String localSocketAddress = (String)propertySet.getStringReadableProperty("localSocketAddress").getValue();
            if (localSocketAddress != null && !Util.interfaceExists(localSocketAddress)) {
               exceptionMessageBuf.append(Messages.getString("CommunicationsException.LocalSocketAddressNotAvailable"));
            } else {
               exceptionMessageBuf.append(Messages.getString("CommunicationsException.TooManyClientConnections"));
            }
         }
      } else {
         if (lastPacketReceivedTimeMs != 0L) {
            timingInfo = new Object[]{timeSinceLastPacketReceivedMs, timeSinceLastPacketSentMs};
            exceptionMessageBuf.append(Messages.getString("CommunicationsException.ServerPacketTimingInfo", timingInfo));
         } else {
            exceptionMessageBuf.append(Messages.getString("CommunicationsException.ServerPacketTimingInfoNoRecv", new Object[]{timeSinceLastPacketSentMs}));
         }

         if (timeoutMessageBuf != null) {
            exceptionMessageBuf.append(timeoutMessageBuf);
         }

         exceptionMessageBuf.append(Messages.getString("CommunicationsException.11"));
         exceptionMessageBuf.append(Messages.getString("CommunicationsException.12"));
         exceptionMessageBuf.append(Messages.getString("CommunicationsException.13"));
      }

      if (exceptionMessageBuf.length() == 0) {
         exceptionMessageBuf.append(Messages.getString("CommunicationsException.20"));
         if ((Boolean)propertySet.getBooleanReadableProperty("maintainTimeStats").getValue() && !(Boolean)propertySet.getBooleanReadableProperty("paranoid").getValue()) {
            exceptionMessageBuf.append("\n\n");
            if (lastPacketReceivedTimeMs != 0L) {
               timingInfo = new Object[]{timeSinceLastPacketReceivedMs, timeSinceLastPacketSentMs};
               exceptionMessageBuf.append(Messages.getString("CommunicationsException.ServerPacketTimingInfo", timingInfo));
            } else {
               exceptionMessageBuf.append(Messages.getString("CommunicationsException.ServerPacketTimingInfoNoRecv", new Object[]{timeSinceLastPacketSentMs}));
            }
         }
      }

      return exceptionMessageBuf.toString();
   }
}
