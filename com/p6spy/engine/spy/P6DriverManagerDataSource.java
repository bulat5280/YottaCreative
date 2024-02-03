package com.p6spy.engine.spy;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class P6DriverManagerDataSource implements DataSource {
   protected DataSource rds;
   protected String url;
   protected String user;
   protected String password;

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String inVar) {
      this.password = inVar;
   }

   public String getUser() {
      return this.user;
   }

   public void setUser(String inVar) {
      this.user = inVar;
   }

   public String getUrl() {
      return this.url;
   }

   public void setUrl(String inVar) {
      this.url = inVar;
   }

   public int getLoginTimeout() throws SQLException {
      return DriverManager.getLoginTimeout();
   }

   public void setLoginTimeout(int inVar) throws SQLException {
      DriverManager.setLoginTimeout(inVar);
   }

   public PrintWriter getLogWriter() throws SQLException {
      return DriverManager.getLogWriter();
   }

   public void setLogWriter(PrintWriter inVar) throws SQLException {
      DriverManager.setLogWriter(inVar);
   }

   public Connection getConnection() throws SQLException {
      return this.getConnection(this.url, this.user, this.password);
   }

   public Connection getConnection(String p0, String p1) throws SQLException {
      return this.getConnection(this.url, p0, p1);
   }

   private Connection getConnection(String p0, String p1, String p2) throws SQLException {
      return DriverManager.getConnection(p0, p1, p2);
   }

   public boolean isWrapperFor(Class<?> iface) throws SQLException {
      return this.rds.isWrapperFor(iface);
   }

   public <T> T unwrap(Class<T> iface) throws SQLException {
      return this.rds.unwrap(iface);
   }

   public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      return this.rds.getParentLogger();
   }
}
