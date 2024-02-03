package com.p6spy.engine.spy;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.common.P6LogQuery;
import com.p6spy.engine.event.JdbcEventListener;
import com.p6spy.engine.wrapper.ConnectionWrapper;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class P6SpyDriver implements Driver {
   private static Driver INSTANCE = new P6SpyDriver();
   private static JdbcEventListenerFactory jdbcEventListenerFactory;

   public boolean acceptsURL(String url) {
      return url != null && url.startsWith("jdbc:p6spy:");
   }

   private String extractRealUrl(String url) {
      return this.acceptsURL(url) ? url.replace("p6spy:", "") : url;
   }

   static List<Driver> registeredDrivers() {
      List<Driver> result = new ArrayList();
      Enumeration driverEnumeration = DriverManager.getDrivers();

      while(driverEnumeration.hasMoreElements()) {
         result.add(driverEnumeration.nextElement());
      }

      return result;
   }

   public Connection connect(String url, Properties properties) throws SQLException {
      if (url == null) {
         throw new SQLException("url is required");
      } else if (!this.acceptsURL(url)) {
         return null;
      } else {
         Driver passThru = this.findPassthru(url);
         P6LogQuery.debug("this is " + this + " and passthru is " + passThru);
         long start = System.nanoTime();
         if (jdbcEventListenerFactory == null) {
            jdbcEventListenerFactory = JdbcEventListenerFactoryLoader.load();
         }

         JdbcEventListener jdbcEventListener = jdbcEventListenerFactory.createJdbcEventListener();
         ConnectionInformation connectionInformation = ConnectionInformation.fromDriver(passThru);
         jdbcEventListener.onBeforeGetConnection(connectionInformation);

         Connection conn;
         try {
            conn = passThru.connect(this.extractRealUrl(url), properties);
            connectionInformation.setConnection(conn);
            connectionInformation.setTimeToGetConnectionNs(System.nanoTime() - start);
            jdbcEventListener.onAfterGetConnection(connectionInformation, (SQLException)null);
         } catch (SQLException var10) {
            connectionInformation.setTimeToGetConnectionNs(System.nanoTime() - start);
            jdbcEventListener.onAfterGetConnection(connectionInformation, var10);
            throw var10;
         }

         return ConnectionWrapper.wrap(conn, jdbcEventListener, connectionInformation);
      }
   }

   protected Driver findPassthru(String url) throws SQLException {
      P6ModuleManager.getInstance();
      String realUrl = this.extractRealUrl(url);
      Driver passthru = null;
      Iterator var4 = registeredDrivers().iterator();

      while(var4.hasNext()) {
         Driver driver = (Driver)var4.next();

         try {
            if (driver.acceptsURL(this.extractRealUrl(url))) {
               passthru = driver;
               break;
            }
         } catch (SQLException var7) {
         }
      }

      if (passthru == null) {
         throw new SQLException("Unable to find a driver that accepts " + realUrl);
      } else {
         return passthru;
      }
   }

   public DriverPropertyInfo[] getPropertyInfo(String url, Properties properties) throws SQLException {
      return this.findPassthru(url).getPropertyInfo(url, properties);
   }

   public int getMajorVersion() {
      return 2;
   }

   public int getMinorVersion() {
      return 0;
   }

   public boolean jdbcCompliant() {
      return true;
   }

   public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      throw new SQLFeatureNotSupportedException("Feature not supported");
   }

   public static void setJdbcEventListenerFactory(JdbcEventListenerFactory jdbcEventListenerFactory) {
      P6SpyDriver.jdbcEventListenerFactory = jdbcEventListenerFactory;
   }

   static {
      try {
         DriverManager.registerDriver(INSTANCE);
      } catch (SQLException var1) {
         throw new IllegalStateException("Could not register P6SpyDriver with DriverManager", var1);
      }
   }
}
