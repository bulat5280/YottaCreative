package com.p6spy.engine.common;

import java.sql.Connection;
import java.sql.Driver;
import java.util.concurrent.atomic.AtomicInteger;
import javax.sql.CommonDataSource;
import javax.sql.PooledConnection;

public class ConnectionInformation implements Loggable {
   private static final AtomicInteger counter = new AtomicInteger(0);
   private final int connectionId;
   private CommonDataSource dataSource;
   private Driver driver;
   private Connection connection;
   private PooledConnection pooledConnection;
   private long timeToGetConnectionNs;

   private ConnectionInformation() {
      this.connectionId = counter.getAndIncrement();
   }

   public static ConnectionInformation fromDriver(Driver driver, Connection connection, long timeToGetConnectionNs) {
      ConnectionInformation connectionInformation = new ConnectionInformation();
      connectionInformation.driver = driver;
      connectionInformation.connection = connection;
      connectionInformation.timeToGetConnectionNs = timeToGetConnectionNs;
      return connectionInformation;
   }

   public static ConnectionInformation fromDataSource(CommonDataSource dataSource, Connection connection, long timeToGetConnectionNs) {
      ConnectionInformation connectionInformation = new ConnectionInformation();
      connectionInformation.dataSource = dataSource;
      connectionInformation.connection = connection;
      connectionInformation.timeToGetConnectionNs = timeToGetConnectionNs;
      return connectionInformation;
   }

   public static ConnectionInformation fromPooledConnection(PooledConnection pooledConnection, Connection connection, long timeToGetConnectionNs) {
      ConnectionInformation connectionInformation = new ConnectionInformation();
      connectionInformation.pooledConnection = pooledConnection;
      connectionInformation.connection = connection;
      connectionInformation.timeToGetConnectionNs = timeToGetConnectionNs;
      return connectionInformation;
   }

   public static ConnectionInformation fromDriver(Driver driver) {
      ConnectionInformation connectionInformation = new ConnectionInformation();
      connectionInformation.driver = driver;
      return connectionInformation;
   }

   public static ConnectionInformation fromDataSource(CommonDataSource dataSource) {
      ConnectionInformation connectionInformation = new ConnectionInformation();
      connectionInformation.dataSource = dataSource;
      return connectionInformation;
   }

   public static ConnectionInformation fromPooledConnection(PooledConnection pooledConnection) {
      ConnectionInformation connectionInformation = new ConnectionInformation();
      connectionInformation.pooledConnection = pooledConnection;
      return connectionInformation;
   }

   public static ConnectionInformation fromTestConnection(Connection connection) {
      ConnectionInformation connectionInformation = new ConnectionInformation();
      connectionInformation.connection = connection;
      return connectionInformation;
   }

   public int getConnectionId() {
      return this.connectionId;
   }

   public String getSql() {
      return "";
   }

   public String getSqlWithValues() {
      return "";
   }

   public CommonDataSource getDataSource() {
      return this.dataSource;
   }

   public Driver getDriver() {
      return this.driver;
   }

   public Connection getConnection() {
      return this.connection;
   }

   public void setConnection(Connection connection) {
      this.connection = connection;
   }

   public PooledConnection getPooledConnection() {
      return this.pooledConnection;
   }

   public long getTimeToGetConnectionNs() {
      return this.timeToGetConnectionNs;
   }

   public void setTimeToGetConnectionNs(long timeToGetConnectionNs) {
      this.timeToGetConnectionNs = timeToGetConnectionNs;
   }

   public ConnectionInformation getConnectionInformation() {
      return this;
   }
}
