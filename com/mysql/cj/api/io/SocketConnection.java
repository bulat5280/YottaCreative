package com.mysql.cj.api.io;

import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.log.Log;
import com.mysql.cj.core.io.FullReadInputStream;
import com.mysql.cj.core.io.NetworkResources;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;

public interface SocketConnection {
   void connect(String var1, int var2, Properties var3, PropertySet var4, ExceptionInterceptor var5, Log var6, int var7);

   void forceClose();

   NetworkResources getNetworkResources();

   String getHost();

   int getPort();

   Socket getMysqlSocket();

   void setMysqlSocket(Socket var1);

   FullReadInputStream getMysqlInput();

   void setMysqlInput(InputStream var1);

   BufferedOutputStream getMysqlOutput();

   void setMysqlOutput(BufferedOutputStream var1);

   boolean isSSLEstablished();

   SocketFactory getSocketFactory();

   void setSocketFactory(SocketFactory var1);

   ExceptionInterceptor getExceptionInterceptor();

   PropertySet getPropertySet();
}
