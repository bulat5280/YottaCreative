package com.mysql.cj.api.io;

import com.mysql.cj.api.MysqlConnection;
import com.mysql.cj.api.ProfilerEventHandler;
import com.mysql.cj.api.authentication.AuthenticationProvider;
import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;

public interface Protocol {
   void init(MysqlConnection var1, int var2, SocketConnection var3, PropertySet var4);

   PropertySet getPropertySet();

   void setPropertySet(PropertySet var1);

   ServerCapabilities readServerCapabilities();

   ServerSession getServerSession();

   MysqlConnection getConnection();

   void setConnection(MysqlConnection var1);

   SocketConnection getSocketConnection();

   AuthenticationProvider getAuthenticationProvider();

   ExceptionInterceptor getExceptionInterceptor();

   PacketSentTimeHolder getPacketSentTimeHolder();

   void setPacketSentTimeHolder(PacketSentTimeHolder var1);

   PacketReceivedTimeHolder getPacketReceivedTimeHolder();

   void setPacketReceivedTimeHolder(PacketReceivedTimeHolder var1);

   void connect(String var1, String var2, String var3);

   void negotiateSSLConnection(int var1);

   void rejectConnection(String var1);

   void beforeHandshake();

   void afterHandshake();

   void changeDatabase(String var1);

   void changeUser(String var1, String var2, String var3);

   String getPasswordCharacterEncoding();

   boolean versionMeetsMinimum(int var1, int var2, int var3);

   @FunctionalInterface
   public interface GetProfilerEventHandlerInstanceFunction {
      ProfilerEventHandler apply();
   }
}
