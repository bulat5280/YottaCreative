package org.flywaydb.core.internal.database;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.database.cockroachdb.CockroachDBDatabase;
import org.flywaydb.core.internal.database.db2.DB2Database;
import org.flywaydb.core.internal.database.derby.DerbyDatabase;
import org.flywaydb.core.internal.database.h2.H2Database;
import org.flywaydb.core.internal.database.hsqldb.HSQLDBDatabase;
import org.flywaydb.core.internal.database.mysql.MySQLDatabase;
import org.flywaydb.core.internal.database.oracle.OracleDatabase;
import org.flywaydb.core.internal.database.postgresql.PostgreSQLDatabase;
import org.flywaydb.core.internal.database.redshift.RedshiftDatabase;
import org.flywaydb.core.internal.database.saphana.SAPHANADatabase;
import org.flywaydb.core.internal.database.sqlite.SQLiteDatabase;
import org.flywaydb.core.internal.database.sqlserver.SQLServerDatabase;
import org.flywaydb.core.internal.database.sybasease.SybaseASEDatabase;
import org.flywaydb.core.internal.exception.FlywaySqlException;
import org.flywaydb.core.internal.util.jdbc.JdbcUtils;

public class DatabaseFactory {
   private static final Log LOG = LogFactory.getLog(DatabaseFactory.class);

   private DatabaseFactory() {
   }

   public static Database createDatabase(FlywayConfiguration configuration, boolean printInfo) {
      java.sql.Connection connection = JdbcUtils.openConnection(configuration.getDataSource());
      String databaseProductName = getDatabaseProductName(connection);
      if (printInfo) {
         LOG.info("Database: " + getJdbcUrl(connection) + " (" + databaseProductName + ")");
      }

      if (databaseProductName.startsWith("Apache Derby")) {
         return new DerbyDatabase(configuration, connection);
      } else if (databaseProductName.startsWith("SQLite")) {
         return new SQLiteDatabase(configuration, connection);
      } else if (databaseProductName.startsWith("H2")) {
         return new H2Database(configuration, connection);
      } else if (databaseProductName.contains("HSQL Database Engine")) {
         return new HSQLDBDatabase(configuration, connection);
      } else if (databaseProductName.startsWith("Microsoft SQL Server")) {
         return new SQLServerDatabase(configuration, connection);
      } else if (databaseProductName.contains("MySQL")) {
         return new MySQLDatabase(configuration, connection);
      } else if (databaseProductName.startsWith("Oracle")) {
         return new OracleDatabase(configuration, connection);
      } else if (databaseProductName.startsWith("PostgreSQL 8") && RedshiftDatabase.isRedshift(connection)) {
         return new RedshiftDatabase(configuration, connection);
      } else if (databaseProductName.startsWith("PostgreSQL")) {
         return (Database)(CockroachDBDatabase.isCockroachDB(connection) ? new CockroachDBDatabase(configuration, connection) : new PostgreSQLDatabase(configuration, connection));
      } else if (databaseProductName.startsWith("DB2")) {
         return new DB2Database(configuration, connection);
      } else if (databaseProductName.startsWith("ASE")) {
         return new SybaseASEDatabase(configuration, connection, false);
      } else if (databaseProductName.startsWith("Adaptive Server Enterprise")) {
         return new SybaseASEDatabase(configuration, connection, true);
      } else if (databaseProductName.startsWith("HDB")) {
         return new SAPHANADatabase(configuration, connection);
      } else {
         throw new FlywayException("Unsupported Database: " + databaseProductName);
      }
   }

   private static String getJdbcUrl(java.sql.Connection connection) {
      try {
         return filterUrl(connection.getMetaData().getURL());
      } catch (SQLException var2) {
         throw new FlywaySqlException("Unable to retrieve the Jdbc connection Url!", var2);
      }
   }

   static String filterUrl(String url) {
      int questionMark = url.indexOf("?");
      if (questionMark >= 0 && !url.contains("?databaseName=")) {
         url = url.substring(0, questionMark);
      }

      url = url.replaceAll("://.*:.*@", "://");
      return url;
   }

   private static String getDatabaseProductName(java.sql.Connection connection) {
      try {
         DatabaseMetaData databaseMetaData = connection.getMetaData();
         if (databaseMetaData == null) {
            throw new FlywayException("Unable to read database metadata while it is null!");
         } else {
            String databaseProductName = databaseMetaData.getDatabaseProductName();
            if (databaseProductName == null) {
               throw new FlywayException("Unable to determine database. Product name is null.");
            } else {
               int databaseMajorVersion = databaseMetaData.getDatabaseMajorVersion();
               int databaseMinorVersion = databaseMetaData.getDatabaseMinorVersion();
               return databaseProductName + " " + databaseMajorVersion + "." + databaseMinorVersion;
            }
         }
      } catch (SQLException var5) {
         throw new FlywaySqlException("Error while determining database product name", var5);
      }
   }
}
