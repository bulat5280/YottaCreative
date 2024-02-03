package com.mysql.cj.jdbc;

import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.core.Constants;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.conf.url.ConnectionUrl;
import com.mysql.cj.core.conf.url.HostInfo;
import com.mysql.cj.core.conf.url.LoadbalanceConnectionUrl;
import com.mysql.cj.core.conf.url.ReplicationConnectionUrl;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.UnableToConnectException;
import com.mysql.cj.core.io.NetworkResources;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.jdbc.ha.FailoverConnectionProxy;
import com.mysql.cj.jdbc.ha.LoadBalancedConnectionProxy;
import com.mysql.cj.jdbc.ha.ReplicationConnectionProxy;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class NonRegisteringDriver implements java.sql.Driver {
   protected static final ConcurrentHashMap<NonRegisteringDriver.ConnectionPhantomReference, NonRegisteringDriver.ConnectionPhantomReference> connectionPhantomRefs = new ConcurrentHashMap();
   protected static final ReferenceQueue<ConnectionImpl> refQueue = new ReferenceQueue();

   public static String getOSName() {
      return Constants.OS_NAME;
   }

   public static String getPlatform() {
      return Constants.OS_ARCH;
   }

   static int getMajorVersionInternal() {
      return StringUtils.safeIntParse("6");
   }

   static int getMinorVersionInternal() {
      return StringUtils.safeIntParse("0");
   }

   public NonRegisteringDriver() throws SQLException {
   }

   public boolean acceptsURL(String url) throws SQLException {
      try {
         return ConnectionUrl.acceptsUrl(url);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3);
      }
   }

   public Connection connect(String url, Properties info) throws SQLException {
      try {
         try {
            ConnectionUrl conStr = ConnectionUrl.getConnectionUrlInstance(url, info);
            if (conStr.getType() == null) {
               return null;
            } else {
               switch(conStr.getType()) {
               case LOADBALANCE_CONNECTION:
                  return LoadBalancedConnectionProxy.createProxyInstance((LoadbalanceConnectionUrl)conStr);
               case FAILOVER_CONNECTION:
                  return FailoverConnectionProxy.createProxyInstance(conStr);
               case REPLICATION_CONNECTION:
                  return ReplicationConnectionProxy.createProxyInstance((ReplicationConnectionUrl)conStr);
               case MYSQLX_SESSION:
               default:
                  return ConnectionImpl.getInstance(conStr.getMainHost());
               }
            }
         } catch (CJException var5) {
            throw (UnableToConnectException)ExceptionFactory.createException((Class)UnableToConnectException.class, (String)Messages.getString("NonRegisteringDriver.17", new Object[]{var5.toString()}), (Throwable)var5);
         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6);
      }
   }

   protected static void trackConnection(JdbcConnection newConn) {
      NonRegisteringDriver.ConnectionPhantomReference phantomRef = new NonRegisteringDriver.ConnectionPhantomReference((ConnectionImpl)newConn, refQueue);
      connectionPhantomRefs.put(phantomRef, phantomRef);
   }

   public int getMajorVersion() {
      return getMajorVersionInternal();
   }

   public int getMinorVersion() {
      return getMinorVersionInternal();
   }

   public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
      try {
         String host = "";
         String port = "";
         String database = "";
         String user = "";
         String password = "";
         if (!StringUtils.isNullOrEmpty(url)) {
            ConnectionUrl connStr = ConnectionUrl.getConnectionUrlInstance(url, info);
            if (connStr.getType() == ConnectionUrl.Type.SINGLE_CONNECTION) {
               HostInfo hostInfo = connStr.getMainHost();
               info = hostInfo.exposeAsProperties();
            }
         }

         if (info != null) {
            host = info.getProperty("HOST");
            port = info.getProperty("PORT");
            database = info.getProperty("DBNAME");
            user = info.getProperty("user");
            password = info.getProperty("password");
         }

         DriverPropertyInfo hostProp = new DriverPropertyInfo("HOST", host);
         hostProp.required = true;
         hostProp.description = Messages.getString("NonRegisteringDriver.3");
         DriverPropertyInfo portProp = new DriverPropertyInfo("PORT", port);
         portProp.required = false;
         portProp.description = Messages.getString("NonRegisteringDriver.7");
         DriverPropertyInfo dbProp = new DriverPropertyInfo("DBNAME", database);
         dbProp.required = false;
         dbProp.description = Messages.getString("NonRegisteringDriver.10");
         DriverPropertyInfo userProp = new DriverPropertyInfo("user", user);
         userProp.required = true;
         userProp.description = Messages.getString("NonRegisteringDriver.13");
         DriverPropertyInfo passwordProp = new DriverPropertyInfo("password", password);
         passwordProp.required = true;
         passwordProp.description = Messages.getString("NonRegisteringDriver.16");
         DriverPropertyInfo[] dpi = (new JdbcPropertySetImpl()).exposeAsDriverPropertyInfo(info, 5);
         dpi[0] = hostProp;
         dpi[1] = portProp;
         dpi[2] = dbProp;
         dpi[3] = userProp;
         dpi[4] = passwordProp;
         return dpi;
      } catch (CJException var15) {
         throw SQLExceptionsMapping.translateException(var15);
      }
   }

   public boolean jdbcCompliant() {
      return false;
   }

   public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      throw new SQLFeatureNotSupportedException();
   }

   static {
      AbandonedConnectionCleanupThread referenceThread = new AbandonedConnectionCleanupThread();
      referenceThread.setDaemon(true);
      referenceThread.start();
   }

   static class ConnectionPhantomReference extends PhantomReference<ConnectionImpl> {
      private NetworkResources io;

      ConnectionPhantomReference(ConnectionImpl connectionImpl, ReferenceQueue<ConnectionImpl> q) {
         super(connectionImpl, q);
         this.io = connectionImpl.getSession().getNetworkResources();
      }

      void cleanup() {
         if (this.io != null) {
            try {
               this.io.forceClose();
            } finally {
               this.io = null;
            }
         }

      }
   }
}
