package org.jooq.tools.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLXML;
import java.sql.Statement;
import org.jooq.SQLDialect;
import org.jooq.tools.JooqLogger;

public class JDBCUtils {
   private static final JooqLogger log = JooqLogger.getLogger(JDBCUtils.class);

   public static final SQLDialect dialect(Connection connection) {
      SQLDialect result = SQLDialect.DEFAULT;
      if (connection != null) {
         try {
            DatabaseMetaData m = connection.getMetaData();
            String url = m.getURL();
            result = dialect(url);
         } catch (SQLException var4) {
         }
      }

      if (result == SQLDialect.DEFAULT) {
      }

      return result;
   }

   public static final SQLDialect dialect(String url) {
      if (url == null) {
         return SQLDialect.DEFAULT;
      } else if (url.startsWith("jdbc:cubrid:")) {
         return SQLDialect.CUBRID;
      } else if (url.startsWith("jdbc:derby:")) {
         return SQLDialect.DERBY;
      } else if (url.startsWith("jdbc:firebirdsql:")) {
         return SQLDialect.FIREBIRD;
      } else if (url.startsWith("jdbc:h2:")) {
         return SQLDialect.H2;
      } else if (url.startsWith("jdbc:hsqldb:")) {
         return SQLDialect.HSQLDB;
      } else if (url.startsWith("jdbc:mariadb:")) {
         return SQLDialect.MARIADB;
      } else if (!url.startsWith("jdbc:mysql:") && !url.startsWith("jdbc:google:")) {
         if (!url.startsWith("jdbc:postgresql:") && !url.startsWith("jdbc:pgsql:")) {
            return !url.startsWith("jdbc:sqlite:") && !url.startsWith("jdbc:sqldroid:") ? SQLDialect.DEFAULT : SQLDialect.SQLITE;
         } else {
            return SQLDialect.POSTGRES;
         }
      } else {
         return SQLDialect.MYSQL;
      }
   }

   public static final String driver(String url) {
      switch(dialect(url).family()) {
      case CUBRID:
         return "cubrid.jdbc.driver.CUBRIDDriver";
      case DERBY:
         return "org.apache.derby.jdbc.ClientDriver";
      case FIREBIRD:
         return "org.firebirdsql.jdbc.FBDriver";
      case H2:
         return "org.h2.Driver";
      case HSQLDB:
         return "org.hsqldb.jdbcDriver";
      case MARIADB:
         return "org.mariadb.jdbc.Driver";
      case MYSQL:
         return "com.mysql.jdbc.Driver";
      case POSTGRES:
         return "org.postgresql.Driver";
      case SQLITE:
         return "org.sqlite.JDBC";
      default:
         return "java.sql.Driver";
      }
   }

   public static final void safeClose(Connection connection) {
      if (connection != null) {
         try {
            connection.close();
         } catch (Exception var2) {
         }
      }

   }

   public static final void safeClose(Statement statement) {
      if (statement != null) {
         try {
            statement.close();
         } catch (Exception var2) {
         }
      }

   }

   public static final void safeClose(ResultSet resultSet) {
      if (resultSet != null) {
         try {
            resultSet.close();
         } catch (Exception var2) {
         }
      }

   }

   public static final void safeClose(ResultSet resultSet, PreparedStatement statement) {
      safeClose(resultSet);
      safeClose((Statement)statement);
   }

   public static final void safeFree(Blob blob) {
      if (blob != null) {
         try {
            blob.free();
         } catch (Exception var2) {
            log.warn("Error while freeing resource", (Throwable)var2);
         } catch (AbstractMethodError var3) {
         }
      }

   }

   public static final void safeFree(Clob clob) {
      if (clob != null) {
         try {
            clob.free();
         } catch (Exception var2) {
            log.warn("Error while freeing resource", (Throwable)var2);
         } catch (AbstractMethodError var3) {
         }
      }

   }

   public static final void safeFree(SQLXML xml) {
      if (xml != null) {
         try {
            xml.free();
         } catch (Exception var2) {
            log.warn("Error while freeing resource", (Throwable)var2);
         } catch (AbstractMethodError var3) {
         }
      }

   }

   public static final void safeFree(Array array) {
      if (array != null) {
         try {
            array.free();
         } catch (Exception var2) {
            log.warn("Error while freeing resource", (Throwable)var2);
         } catch (AbstractMethodError var3) {
         }
      }

   }

   public static final <T> T wasNull(SQLInput stream, T value) throws SQLException {
      return value != null && !stream.wasNull() ? value : null;
   }

   public static final <T extends Number> T wasNull(SQLInput stream, T value) throws SQLException {
      return value != null && (value.intValue() != 0 || !stream.wasNull()) ? value : null;
   }

   public static final Boolean wasNull(SQLInput stream, Boolean value) throws SQLException {
      return value != null && (value || !stream.wasNull()) ? value : null;
   }

   public static final <T> T wasNull(ResultSet rs, T value) throws SQLException {
      return value != null && !rs.wasNull() ? value : null;
   }

   public static final <T extends Number> T wasNull(ResultSet rs, T value) throws SQLException {
      return value != null && (value.intValue() != 0 || !rs.wasNull()) ? value : null;
   }

   public static final Boolean wasNull(ResultSet rs, Boolean value) throws SQLException {
      return value != null && (value || !rs.wasNull()) ? value : null;
   }

   public static final <T> T wasNull(CallableStatement statement, T value) throws SQLException {
      return value != null && !statement.wasNull() ? value : null;
   }

   public static final <T extends Number> T wasNull(CallableStatement statement, T value) throws SQLException {
      return value != null && (value.intValue() != 0 || !statement.wasNull()) ? value : null;
   }

   public static final Boolean wasNull(CallableStatement statement, Boolean value) throws SQLException {
      return value != null && (value || !statement.wasNull()) ? value : null;
   }

   private JDBCUtils() {
   }
}
