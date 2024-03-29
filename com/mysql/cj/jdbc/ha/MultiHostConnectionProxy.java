package com.mysql.cj.jdbc.ha;

import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.core.conf.url.ConnectionUrl;
import com.mysql.cj.core.conf.url.HostInfo;
import com.mysql.cj.core.util.Util;
import com.mysql.cj.jdbc.ConnectionImpl;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

public abstract class MultiHostConnectionProxy implements InvocationHandler {
   private static final String METHOD_GET_MULTI_HOST_SAFE_PROXY = "getMultiHostSafeProxy";
   private static final String METHOD_EQUALS = "equals";
   private static final String METHOD_HASH_CODE = "hashCode";
   private static final String METHOD_CLOSE = "close";
   private static final String METHOD_ABORT_INTERNAL = "abortInternal";
   private static final String METHOD_ABORT = "abort";
   private static final String METHOD_IS_CLOSED = "isClosed";
   private static final String METHOD_GET_AUTO_COMMIT = "getAutoCommit";
   private static final String METHOD_GET_CATALOG = "getCatalog";
   private static final String METHOD_GET_TRANSACTION_ISOLATION = "getTransactionIsolation";
   private static final String METHOD_GET_SESSION_MAX_ROWS = "getSessionMaxRows";
   List<HostInfo> hostsList;
   protected ConnectionUrl connectionUrl;
   boolean autoReconnect;
   JdbcConnection thisAsConnection;
   JdbcConnection proxyConnection;
   JdbcConnection currentConnection;
   boolean isClosed;
   boolean closedExplicitly;
   String closedReason;
   protected Throwable lastExceptionDealtWith;

   MultiHostConnectionProxy() throws SQLException {
      this.autoReconnect = false;
      this.thisAsConnection = null;
      this.proxyConnection = null;
      this.currentConnection = null;
      this.isClosed = false;
      this.closedExplicitly = false;
      this.closedReason = null;
      this.lastExceptionDealtWith = null;
      this.thisAsConnection = this.getNewWrapperForThisAsConnection();
   }

   MultiHostConnectionProxy(ConnectionUrl connectionUrl) throws SQLException {
      this();
      this.initializeHostsSpecs(connectionUrl, connectionUrl.getHostsList());
   }

   int initializeHostsSpecs(ConnectionUrl connUrl, List<HostInfo> hosts) {
      this.connectionUrl = connUrl;
      Properties props = connUrl.getConnectionArgumentsAsProperties();
      this.autoReconnect = "true".equalsIgnoreCase(props.getProperty("autoReconnect")) || "true".equalsIgnoreCase(props.getProperty("autoReconnectForPools"));
      this.hostsList = new ArrayList(hosts);
      int numHosts = this.hostsList.size();
      return numHosts;
   }

   protected JdbcConnection getProxy() {
      return this.proxyConnection != null ? this.proxyConnection : this.thisAsConnection;
   }

   protected final void setProxy(JdbcConnection proxyConn) {
      this.proxyConnection = proxyConn;
      this.propagateProxyDown(proxyConn);
   }

   protected void propagateProxyDown(JdbcConnection proxyConn) {
      this.currentConnection.setProxy(proxyConn);
   }

   JdbcConnection getNewWrapperForThisAsConnection() throws SQLException {
      return new MultiHostMySQLConnection(this);
   }

   Object proxyIfReturnTypeIsJdbcInterface(Class<?> returnType, Object toProxy) {
      if (toProxy != null && Util.isJdbcInterface(returnType)) {
         Class<?> toProxyClass = toProxy.getClass();
         return Proxy.newProxyInstance(toProxyClass.getClassLoader(), Util.getImplementedInterfaces(toProxyClass), this.getNewJdbcInterfaceProxy(toProxy));
      } else {
         return toProxy;
      }
   }

   InvocationHandler getNewJdbcInterfaceProxy(Object toProxy) {
      return new MultiHostConnectionProxy.JdbcInterfaceProxy(toProxy);
   }

   void dealWithInvocationException(InvocationTargetException e) throws SQLException, Throwable, InvocationTargetException {
      Throwable t = e.getTargetException();
      if (t != null) {
         if (this.lastExceptionDealtWith != t && this.shouldExceptionTriggerConnectionSwitch(t)) {
            this.invalidateCurrentConnection();
            this.pickNewConnection();
            this.lastExceptionDealtWith = t;
         }

         throw t;
      } else {
         throw e;
      }
   }

   abstract boolean shouldExceptionTriggerConnectionSwitch(Throwable var1);

   abstract boolean isMasterConnection();

   synchronized void invalidateCurrentConnection() throws SQLException {
      this.invalidateConnection(this.currentConnection);
   }

   synchronized void invalidateConnection(JdbcConnection conn) throws SQLException {
      try {
         if (conn != null && !conn.isClosed()) {
            conn.realClose(true, !conn.getAutoCommit(), true, (Throwable)null);
         }
      } catch (SQLException var3) {
      }

   }

   abstract void pickNewConnection() throws SQLException;

   synchronized ConnectionImpl createConnectionForHost(HostInfo hostInfo) throws SQLException {
      ConnectionImpl conn = (ConnectionImpl)ConnectionImpl.getInstance(hostInfo);
      conn.setProxy(this.getProxy());
      return conn;
   }

   static void syncSessionState(JdbcConnection source, JdbcConnection target) throws SQLException {
      if (source != null && target != null) {
         syncSessionState(source, target, source.isReadOnly());
      }
   }

   static void syncSessionState(JdbcConnection source, JdbcConnection target, boolean readOnly) throws SQLException {
      if (target != null) {
         target.setReadOnly(readOnly);
      }

      if (source != null && target != null) {
         target.setAutoCommit(source.getAutoCommit());
         target.setCatalog(source.getCatalog());
         target.setTransactionIsolation(source.getTransactionIsolation());
         target.setSessionMaxRows(source.getSessionMaxRows());
      }
   }

   abstract void doClose() throws SQLException;

   abstract void doAbortInternal() throws SQLException;

   abstract void doAbort(Executor var1) throws SQLException;

   public synchronized Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      String methodName = method.getName();
      if ("getMultiHostSafeProxy".equals(methodName)) {
         return this.thisAsConnection;
      } else if ("equals".equals(methodName)) {
         return args[0].equals(this);
      } else if ("hashCode".equals(methodName)) {
         return this.hashCode();
      } else if ("close".equals(methodName)) {
         this.doClose();
         this.isClosed = true;
         this.closedReason = "Connection explicitly closed.";
         this.closedExplicitly = true;
         return null;
      } else if ("abortInternal".equals(methodName)) {
         this.doAbortInternal();
         this.currentConnection.abortInternal();
         this.isClosed = true;
         this.closedReason = "Connection explicitly closed.";
         return null;
      } else if ("abort".equals(methodName) && args.length == 1) {
         this.doAbort((Executor)args[0]);
         this.isClosed = true;
         this.closedReason = "Connection explicitly closed.";
         return null;
      } else if ("isClosed".equals(methodName)) {
         return this.isClosed;
      } else {
         try {
            return this.invokeMore(proxy, method, args);
         } catch (InvocationTargetException var11) {
            throw (Throwable)(var11.getCause() != null ? var11.getCause() : var11);
         } catch (Exception var12) {
            Exception e = var12;
            Class<?>[] declaredException = method.getExceptionTypes();
            Class[] var7 = declaredException;
            int var8 = declaredException.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               Class<?> declEx = var7[var9];
               if (declEx.isAssignableFrom(e.getClass())) {
                  throw e;
               }
            }

            throw new IllegalStateException(e.getMessage(), e);
         }
      }
   }

   abstract Object invokeMore(Object var1, Method var2, Object[] var3) throws Throwable;

   protected boolean allowedOnClosedConnection(Method method) {
      String methodName = method.getName();
      return methodName.equals("getAutoCommit") || methodName.equals("getCatalog") || methodName.equals("getTransactionIsolation") || methodName.equals("getSessionMaxRows");
   }

   class JdbcInterfaceProxy implements InvocationHandler {
      Object invokeOn = null;

      JdbcInterfaceProxy(Object toInvokeOn) {
         this.invokeOn = toInvokeOn;
      }

      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
         synchronized(MultiHostConnectionProxy.this) {
            Object result = null;

            try {
               result = method.invoke(this.invokeOn, args);
               result = MultiHostConnectionProxy.this.proxyIfReturnTypeIsJdbcInterface(method.getReturnType(), result);
            } catch (InvocationTargetException var8) {
               MultiHostConnectionProxy.this.dealWithInvocationException(var8);
            }

            return result;
         }
      }
   }
}
