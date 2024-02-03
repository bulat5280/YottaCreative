package com.mysql.cj.api;

import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.io.ServerSession;
import com.mysql.cj.api.log.Log;
import com.mysql.cj.core.ServerVersion;
import java.net.SocketAddress;
import java.util.Map;
import java.util.TimeZone;

public interface Session {
   PropertySet getPropertySet();

   void changeUser(String var1, String var2, String var3);

   ExceptionInterceptor getExceptionInterceptor();

   void setExceptionInterceptor(ExceptionInterceptor var1);

   boolean characterSetNamesMatches(String var1);

   boolean inTransactionOnServer();

   String getServerVariable(String var1);

   int getServerVariable(String var1, int var2);

   Map<String, String> getServerVariables();

   void setServerVariables(Map<String, String> var1);

   int getServerDefaultCollationIndex();

   void setServerDefaultCollationIndex(int var1);

   void abortInternal();

   void quit();

   void forceClose();

   ServerVersion getServerVersion();

   boolean versionMeetsMinimum(int var1, int var2, int var3);

   long getThreadId();

   boolean isSetNeededForAutoCommitMode(boolean var1);

   Log getLog();

   void setLog(Log var1);

   void configureTimezone();

   TimeZone getDefaultTimeZone();

   String getErrorMessageEncoding();

   void setErrorMessageEncoding(String var1);

   int getMaxBytesPerChar(String var1);

   int getMaxBytesPerChar(Integer var1, String var2);

   String getEncodingForIndex(int var1);

   ProfilerEventHandler getProfilerEventHandler();

   void setProfilerEventHandler(ProfilerEventHandler var1);

   ServerSession getServerSession();

   boolean isSSLEstablished();

   SocketAddress getRemoteSocketAddress();

   boolean serverSupportsFracSecs();
}
