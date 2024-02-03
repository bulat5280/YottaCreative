package com.mysql.cj.core.io;

import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.io.SocketConnection;
import com.mysql.cj.api.io.SocketFactory;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.UnableToConnectException;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.Socket;

public abstract class AbstractSocketConnection implements SocketConnection {
   protected String host = null;
   protected int port = 3306;
   protected SocketFactory socketFactory = null;
   protected Socket mysqlSocket = null;
   protected FullReadInputStream mysqlInput = null;
   protected BufferedOutputStream mysqlOutput = null;
   protected ExceptionInterceptor exceptionInterceptor;
   protected PropertySet propertySet;

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public Socket getMysqlSocket() {
      return this.mysqlSocket;
   }

   public void setMysqlSocket(Socket mysqlSocket) {
      this.mysqlSocket = mysqlSocket;
   }

   public FullReadInputStream getMysqlInput() {
      return this.mysqlInput;
   }

   public void setMysqlInput(InputStream mysqlInput) {
      this.mysqlInput = new FullReadInputStream(mysqlInput);
   }

   public BufferedOutputStream getMysqlOutput() {
      return this.mysqlOutput;
   }

   public void setMysqlOutput(BufferedOutputStream mysqlOutput) {
      this.mysqlOutput = mysqlOutput;
   }

   public boolean isSSLEstablished() {
      return ExportControlled.enabled() && ExportControlled.isSSLEstablished(this.getMysqlSocket());
   }

   public SocketFactory getSocketFactory() {
      return this.socketFactory;
   }

   public void setSocketFactory(SocketFactory socketFactory) {
      this.socketFactory = socketFactory;
   }

   public final void forceClose() {
      try {
         this.getNetworkResources().forceClose();
      } finally {
         this.mysqlSocket = null;
         this.mysqlInput = null;
         this.mysqlOutput = null;
      }

   }

   public NetworkResources getNetworkResources() {
      return new NetworkResources(this.mysqlSocket, this.mysqlInput, this.mysqlOutput);
   }

   public ExceptionInterceptor getExceptionInterceptor() {
      return this.exceptionInterceptor;
   }

   public PropertySet getPropertySet() {
      return this.propertySet;
   }

   protected SocketFactory createSocketFactory(String socketFactoryClassName) {
      try {
         if (socketFactoryClassName == null) {
            throw (UnableToConnectException)ExceptionFactory.createException(UnableToConnectException.class, Messages.getString("SocketConnection.0"), this.getExceptionInterceptor());
         } else {
            return (SocketFactory)((SocketFactory)Class.forName(socketFactoryClassName).newInstance());
         }
      } catch (IllegalAccessException | ClassNotFoundException | CJException | InstantiationException var3) {
         throw (UnableToConnectException)ExceptionFactory.createException(UnableToConnectException.class, Messages.getString("SocketConnection.1", new String[]{socketFactoryClassName}), this.getExceptionInterceptor());
      }
   }
}
