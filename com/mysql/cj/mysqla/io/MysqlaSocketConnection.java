package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.io.ServerSession;
import com.mysql.cj.api.io.SocketConnection;
import com.mysql.cj.api.log.Log;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.io.AbstractSocketConnection;
import com.mysql.cj.core.io.FullReadInputStream;
import com.mysql.cj.core.io.ReadAheadInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MysqlaSocketConnection extends AbstractSocketConnection implements SocketConnection {
   public void connect(String hostName, int portNumber, Properties props, PropertySet propSet, ExceptionInterceptor excInterceptor, Log log, int loginTimeout) {
      try {
         this.port = portNumber;
         this.host = hostName;
         this.propertySet = propSet;
         this.exceptionInterceptor = excInterceptor;
         this.socketFactory = this.createSocketFactory(propSet.getStringReadableProperty("socketFactory").getStringValue());
         this.mysqlSocket = this.socketFactory.connect(this.host, this.port, props, loginTimeout);
         int socketTimeout = (Integer)propSet.getIntegerReadableProperty("socketTimeout").getValue();
         if (socketTimeout != 0) {
            try {
               this.mysqlSocket.setSoTimeout(socketTimeout);
            } catch (Exception var10) {
            }
         }

         this.mysqlSocket = this.socketFactory.beforeHandshake();
         Object rawInputStream;
         if ((Boolean)propSet.getBooleanReadableProperty("useReadAheadInput").getValue()) {
            rawInputStream = new ReadAheadInputStream(this.mysqlSocket.getInputStream(), 16384, (Boolean)propSet.getBooleanReadableProperty("traceProtocol").getValue(), log);
         } else if ((Boolean)propSet.getBooleanReadableProperty("useUnbufferedInput").getValue()) {
            rawInputStream = this.mysqlSocket.getInputStream();
         } else {
            rawInputStream = new BufferedInputStream(this.mysqlSocket.getInputStream(), 16384);
         }

         this.mysqlInput = new FullReadInputStream((InputStream)rawInputStream);
         this.mysqlOutput = new BufferedOutputStream(this.mysqlSocket.getOutputStream(), 16384);
      } catch (IOException var11) {
         throw ExceptionFactory.createCommunicationsException(propSet, (ServerSession)null, 0L, 0L, var11, this.getExceptionInterceptor());
      }
   }
}
