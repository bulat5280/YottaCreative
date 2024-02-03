package org.flywaydb.core.internal.util.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.exception.FlywaySqlException;

public class JdbcUtils {
   private static final Log LOG = LogFactory.getLog(JdbcUtils.class);

   private JdbcUtils() {
   }

   public static Connection openConnection(DataSource dataSource) throws FlywayException {
      try {
         Connection connection = dataSource.getConnection();
         if (connection == null) {
            throw new FlywayException("Unable to obtain database connection");
         } else {
            return connection;
         }
      } catch (SQLException var2) {
         throw new FlywaySqlException("Unable to obtain database connection", var2);
      }
   }

   public static void closeConnection(Connection connection) {
      if (connection != null) {
         try {
            connection.close();
         } catch (SQLException var2) {
            LOG.error("Error while closing database connection: " + var2.getMessage(), var2);
         }

      }
   }

   public static void closeStatement(Statement statement) {
      if (statement != null) {
         try {
            statement.close();
         } catch (SQLException var2) {
            LOG.error("Error while closing JDBC statement", var2);
         }

      }
   }

   public static void closeResultSet(ResultSet resultSet) {
      if (resultSet != null) {
         try {
            resultSet.close();
         } catch (SQLException var2) {
            LOG.error("Error while closing JDBC resultSet", var2);
         }

      }
   }
}
