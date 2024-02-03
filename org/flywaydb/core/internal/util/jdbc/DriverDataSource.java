package org.flywaydb.core.internal.util.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.exception.FlywaySqlException;
import org.flywaydb.core.internal.util.ClassUtils;
import org.flywaydb.core.internal.util.ExceptionUtils;
import org.flywaydb.core.internal.util.FeatureDetector;
import org.flywaydb.core.internal.util.StringUtils;

public class DriverDataSource implements DataSource {
   private static final Log LOG = LogFactory.getLog(DriverDataSource.class);
   private static final String MARIADB_JDBC_DRIVER = "org.mariadb.jdbc.Driver";
   private static final String MYSQL_JDBC_URL_PREFIX = "jdbc:mysql:";
   private static final String ORACLE_JDBC_URL_PREFIX = "jdbc:oracle:";
   private static final String REDSHIFT_JDBC_URL_PREFIX = "jdbc:redshift:";
   private static final String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
   private static final String REDSHIFT_JDBC41_DRIVER = "com.amazon.redshift.jdbc41.Driver";
   private static final String SQLDROID_DRIVER = "org.sqldroid.SQLDroidDriver";
   private Driver driver;
   private final String url;
   private final String user;
   private final String password;
   private final String[] initSqls;
   private final Properties defaultProps;
   private final ClassLoader classLoader;
   private boolean autoCommit;

   public DriverDataSource(ClassLoader classLoader, String driverClass, String url, String user, String password) throws FlywayException {
      this(classLoader, driverClass, url, user, password, new Properties());
   }

   public DriverDataSource(ClassLoader classLoader, String driverClass, String url, String user, String password, Properties props, String... initSqls) throws FlywayException {
      this.autoCommit = true;
      this.classLoader = classLoader;
      this.url = this.detectFallbackUrl(url);
      if (!StringUtils.hasLength(driverClass)) {
         driverClass = this.detectDriverForUrl(url);
         if (!StringUtils.hasLength(driverClass)) {
            throw new FlywayException("Unable to autodetect JDBC driver for url: " + url);
         }
      }

      this.defaultProps = new Properties(props);
      this.defaultProps.putAll(this.detectPropsForUrl(url));

      try {
         this.driver = (Driver)ClassUtils.instantiate(driverClass, classLoader);
      } catch (FlywayException var12) {
         String backupDriverClass = this.detectBackupDriverForUrl(url);
         if (backupDriverClass == null) {
            throw new FlywayException("Unable to instantiate JDBC driver: " + driverClass + " => Check whether the jar file is present", var12);
         }

         try {
            this.driver = (Driver)ClassUtils.instantiate(backupDriverClass, classLoader);
         } catch (Exception var11) {
            throw new FlywayException("Unable to instantiate JDBC driver: " + driverClass + " => Check whether the jar file is present", var12);
         }
      }

      this.user = this.detectFallbackUser(user);
      this.password = this.detectFallbackPassword(password);
      this.initSqls = initSqls == null ? new String[0] : initSqls;
   }

   private String detectFallbackUrl(String url) {
      if (!StringUtils.hasText(url)) {
         String boxfuseDatabaseUrl = System.getenv("BOXFUSE_DATABASE_URL");
         if (StringUtils.hasText(boxfuseDatabaseUrl)) {
            return boxfuseDatabaseUrl;
         } else {
            throw new FlywayException("Missing required JDBC URL. Unable to create DataSource!");
         }
      } else if (!url.toLowerCase().startsWith("jdbc:")) {
         throw new FlywayException("Invalid JDBC URL (should start with jdbc:) : " + url);
      } else {
         return url;
      }
   }

   private String detectFallbackUser(String user) {
      if (!StringUtils.hasText(user)) {
         String boxfuseDatabaseUser = System.getenv("BOXFUSE_DATABASE_USER");
         if (StringUtils.hasText(boxfuseDatabaseUser)) {
            return boxfuseDatabaseUser;
         }
      }

      return user;
   }

   private String detectFallbackPassword(String password) {
      if (!StringUtils.hasText(password)) {
         String boxfuseDatabasePassword = System.getenv("BOXFUSE_DATABASE_PASSWORD");
         if (StringUtils.hasText(boxfuseDatabasePassword)) {
            return boxfuseDatabasePassword;
         }
      }

      return password;
   }

   private Properties detectPropsForUrl(String url) {
      Properties result = new Properties();
      if (url.startsWith("jdbc:oracle:")) {
         String osUser = System.getProperty("user.name");
         result.put("v$session.osuser", osUser.substring(0, Math.min(osUser.length(), 30)));
         result.put("v$session.program", "Flyway by Boxfuse");
         result.put("oracle.net.keepAlive", "true");
      }

      return result;
   }

   private String detectBackupDriverForUrl(String url) {
      if (url.startsWith("jdbc:mysql:")) {
         return ClassUtils.isPresent("com.mysql.jdbc.Driver", this.classLoader) ? "com.mysql.jdbc.Driver" : "org.mariadb.jdbc.Driver";
      } else if (url.startsWith("jdbc:redshift:")) {
         return ClassUtils.isPresent("com.amazon.redshift.jdbc41.Driver", this.classLoader) ? "com.amazon.redshift.jdbc41.Driver" : "com.amazon.redshift.jdbc4.Driver";
      } else {
         return null;
      }
   }

   private String detectDriverForUrl(String url) {
      if (url.startsWith("jdbc:tc:")) {
         return "org.testcontainers.jdbc.ContainerDatabaseDriver";
      } else if (url.startsWith("jdbc:db2:")) {
         return "com.ibm.db2.jcc.DB2Driver";
      } else if (url.startsWith("jdbc:derby://")) {
         return "org.apache.derby.jdbc.ClientDriver";
      } else if (url.startsWith("jdbc:derby:")) {
         return "org.apache.derby.jdbc.EmbeddedDriver";
      } else if (url.startsWith("jdbc:h2:")) {
         return "org.h2.Driver";
      } else if (url.startsWith("jdbc:hsqldb:")) {
         return "org.hsqldb.jdbcDriver";
      } else if (url.startsWith("jdbc:sqlite:")) {
         return (new FeatureDetector(this.classLoader)).isAndroidAvailable() ? "org.sqldroid.SQLDroidDriver" : "org.sqlite.JDBC";
      } else if (url.startsWith("jdbc:sqldroid:")) {
         return "org.sqldroid.SQLDroidDriver";
      } else if (url.startsWith("jdbc:mysql:")) {
         return "com.mysql.cj.jdbc.Driver";
      } else if (url.startsWith("jdbc:mariadb:")) {
         return "org.mariadb.jdbc.Driver";
      } else if (url.startsWith("jdbc:google:")) {
         return "com.mysql.jdbc.GoogleDriver";
      } else if (url.startsWith("jdbc:oracle:")) {
         return "oracle.jdbc.OracleDriver";
      } else if (url.startsWith("jdbc:postgresql:")) {
         return "org.postgresql.Driver";
      } else if (url.startsWith("jdbc:redshift:")) {
         return "com.amazon.redshift.jdbc42.Driver";
      } else if (url.startsWith("jdbc:jtds:")) {
         return "net.sourceforge.jtds.jdbc.Driver";
      } else if (url.startsWith("jdbc:sybase:")) {
         return "com.sybase.jdbc4.jdbc.SybDriver";
      } else if (url.startsWith("jdbc:sqlserver:")) {
         return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
      } else {
         return url.startsWith("jdbc:sap:") ? "com.sap.db.jdbc.Driver" : null;
      }
   }

   public Driver getDriver() {
      return this.driver;
   }

   public String getUrl() {
      return this.url;
   }

   public String getUser() {
      return this.user;
   }

   public String getPassword() {
      return this.password;
   }

   public String[] getInitSqls() {
      return this.initSqls;
   }

   public Connection getConnection() throws SQLException {
      return this.getConnectionFromDriver(this.getUser(), this.getPassword());
   }

   public Connection getConnection(String username, String password) throws SQLException {
      return this.getConnectionFromDriver(username, password);
   }

   protected Connection getConnectionFromDriver(String username, String password) throws SQLException {
      Properties props = new Properties(this.defaultProps);
      if (username != null) {
         props.setProperty("user", username);
      }

      if (password != null) {
         props.setProperty("password", password);
      }

      int retries = 0;
      Connection connection = null;

      do {
         try {
            connection = this.driver.connect(this.url, props);
         } catch (SQLRecoverableException var16) {
            ++retries;
            if (retries >= 10) {
               throw new FlywaySqlException("Unable to obtain connection from database (" + this.url + ") for user '" + this.user + "': " + var16.getMessage(), var16);
            }

            Throwable rootCause = ExceptionUtils.getRootCause(var16);
            String msg = "Connection error: " + var16.getMessage();
            if (rootCause != null && rootCause != var16) {
               msg = msg + " (caused by " + rootCause.getMessage() + ")";
            }

            LOG.warn(msg + " Retrying in 1 sec...");
         } catch (SQLException var17) {
            throw new FlywaySqlException("Unable to obtain connection from database (" + this.url + ") for user '" + this.user + "': " + var17.getMessage(), var17);
         }
      } while(connection == null);

      String[] var6 = this.initSqls;
      int var18 = var6.length;

      for(int var19 = 0; var19 < var18; ++var19) {
         String initSql = var6[var19];
         Statement statement = null;

         try {
            statement = connection.createStatement();
            statement.execute(initSql);
         } finally {
            JdbcUtils.closeStatement(statement);
         }
      }

      connection.setAutoCommit(this.autoCommit);
      return connection;
   }

   public boolean isAutoCommit() {
      return this.autoCommit;
   }

   public void setAutoCommit(boolean autoCommit) {
      this.autoCommit = autoCommit;
   }

   public int getLoginTimeout() throws SQLException {
      return 0;
   }

   public void setLoginTimeout(int timeout) throws SQLException {
      throw new UnsupportedOperationException("setLoginTimeout");
   }

   public PrintWriter getLogWriter() {
      throw new UnsupportedOperationException("getLogWriter");
   }

   public void setLogWriter(PrintWriter pw) throws SQLException {
      throw new UnsupportedOperationException("setLogWriter");
   }

   public <T> T unwrap(Class<T> iface) throws SQLException {
      throw new UnsupportedOperationException("unwrap");
   }

   public boolean isWrapperFor(Class<?> iface) throws SQLException {
      return DataSource.class.equals(iface);
   }

   public Logger getParentLogger() {
      throw new UnsupportedOperationException("getParentLogger");
   }
}
