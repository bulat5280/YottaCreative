package com.mysql.cj.jdbc;

import com.mysql.cj.api.conf.ReadableProperty;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.jdbc.JdbcPropertySet;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.conf.PropertyDefinitions;
import com.mysql.cj.core.conf.url.ConnectionUrl;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;
import javax.sql.DataSource;

public class MysqlDataSource extends JdbcPropertySetImpl implements DataSource, Referenceable, Serializable, JdbcPropertySet {
   static final long serialVersionUID = -5515846944416881264L;
   protected static final NonRegisteringDriver mysqlDriver;
   protected transient PrintWriter logWriter = null;
   protected String databaseName = null;
   protected String encoding = null;
   protected String hostName = null;
   protected String password = null;
   protected String profileSQLString = "false";
   protected String url = null;
   protected String user = null;
   protected boolean explicitUrl = false;
   protected int port = 3306;

   public Connection getConnection() throws SQLException {
      try {
         return this.getConnection(this.user, this.password);
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2);
      }
   }

   public Connection getConnection(String userID, String pass) throws SQLException {
      try {
         Properties props = new Properties();
         if (userID != null) {
            props.setProperty("user", userID);
         }

         if (pass != null) {
            props.setProperty("password", pass);
         }

         this.exposeAsProperties(props);
         return this.getConnection(props);
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5);
      }
   }

   public void setDatabaseName(String dbName) {
      this.databaseName = dbName;
   }

   public String getDatabaseName() {
      return this.databaseName != null ? this.databaseName : "";
   }

   public void setLogWriter(PrintWriter output) throws SQLException {
      try {
         this.logWriter = output;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3);
      }
   }

   public PrintWriter getLogWriter() {
      try {
         return this.logWriter;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2);
      }
   }

   public void setLoginTimeout(int seconds) throws SQLException {
      try {
         ;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3);
      }
   }

   public int getLoginTimeout() {
      try {
         return 0;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2);
      }
   }

   public void setPassword(String pass) {
      this.password = pass;
   }

   public void setPort(int p) {
      this.port = p;
   }

   public int getPort() {
      return this.port;
   }

   public void setPortNumber(int p) {
      this.setPort(p);
   }

   public int getPortNumber() {
      return this.getPort();
   }

   public void setPropertiesViaRef(Reference ref) throws SQLException {
      Iterator var2 = PropertyDefinitions.PROPERTY_NAME_TO_PROPERTY_DEFINITION.keySet().iterator();

      while(var2.hasNext()) {
         String propName = (String)var2.next();
         ReadableProperty<?> propToSet = this.getReadableProperty(propName);
         if (ref != null) {
            propToSet.initializeFrom(ref, (ExceptionInterceptor)null);
         }
      }

      this.postInitialization();
   }

   public Reference getReference() throws NamingException {
      String factoryName = MysqlDataSourceFactory.class.getName();
      Reference ref = new Reference(this.getClass().getName(), factoryName, (String)null);
      ref.add(new StringRefAddr("user", this.getUser()));
      ref.add(new StringRefAddr("password", this.password));
      ref.add(new StringRefAddr("serverName", this.getServerName()));
      ref.add(new StringRefAddr("port", "" + this.getPort()));
      ref.add(new StringRefAddr("databaseName", this.getDatabaseName()));
      ref.add(new StringRefAddr("url", this.getUrl()));
      ref.add(new StringRefAddr("explicitUrl", String.valueOf(this.explicitUrl)));
      Iterator var3 = PropertyDefinitions.PROPERTY_NAME_TO_PROPERTY_DEFINITION.keySet().iterator();

      while(var3.hasNext()) {
         String propName = (String)var3.next();
         ReadableProperty<?> propToStore = this.getReadableProperty(propName);
         String val = propToStore.getStringValue();
         if (val != null) {
            ref.add(new StringRefAddr(propToStore.getPropertyDefinition().getName(), val));
         }
      }

      return ref;
   }

   public void setServerName(String serverName) {
      this.hostName = serverName;
   }

   public String getServerName() {
      return this.hostName != null ? this.hostName : "";
   }

   public void setURL(String url) {
      this.setUrl(url);
   }

   public String getURL() {
      return this.getUrl();
   }

   public void setUrl(String url) {
      this.url = url;
      this.explicitUrl = true;
   }

   public String getUrl() {
      if (!this.explicitUrl) {
         StringBuilder sbUrl = new StringBuilder(ConnectionUrl.Type.SINGLE_CONNECTION.getProtocol());
         sbUrl.append("//").append(this.getServerName()).append(":").append(this.getPort()).append("/").append(this.getDatabaseName());
         return sbUrl.toString();
      } else {
         return this.url;
      }
   }

   public void setUser(String userID) {
      this.user = userID;
   }

   public String getUser() {
      return this.user;
   }

   protected Connection getConnection(Properties props) throws SQLException {
      String jdbcUrlToUse = null;
      if (!this.explicitUrl) {
         jdbcUrlToUse = this.getUrl();
      } else {
         jdbcUrlToUse = this.url;
      }

      ConnectionUrl connUrl = ConnectionUrl.getConnectionUrlInstance(jdbcUrlToUse, (Properties)null);
      if (connUrl.getType() == null) {
         throw SQLError.createSQLException(Messages.getString("MysqlDataSource.BadUrl", new Object[]{jdbcUrlToUse}), "08006", (ExceptionInterceptor)null);
      } else {
         Properties urlProps = connUrl.getConnectionArgumentsAsProperties();
         urlProps.remove("DBNAME");
         urlProps.remove("HOST");
         urlProps.remove("PORT");
         urlProps.stringPropertyNames().stream().forEach((k) -> {
            props.setProperty(k, urlProps.getProperty(k));
         });
         return mysqlDriver.connect(jdbcUrlToUse, props);
      }
   }

   public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      return null;
   }

   public <T> T unwrap(Class<T> iface) throws SQLException {
      try {
         return null;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3);
      }
   }

   public boolean isWrapperFor(Class<?> iface) throws SQLException {
      try {
         return false;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3);
      }
   }

   static {
      try {
         mysqlDriver = new NonRegisteringDriver();
      } catch (Exception var1) {
         throw new RuntimeException(Messages.getString("MysqlDataSource.0"));
      }
   }
}
