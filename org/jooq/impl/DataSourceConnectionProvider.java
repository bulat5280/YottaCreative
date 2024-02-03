package org.jooq.impl;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.jooq.ConnectionProvider;
import org.jooq.exception.DataAccessException;

public class DataSourceConnectionProvider implements ConnectionProvider {
   private final DataSource dataSource;

   public DataSourceConnectionProvider(DataSource dataSource) {
      this.dataSource = dataSource;
   }

   public DataSource dataSource() {
      return this.dataSource;
   }

   public Connection acquire() {
      try {
         return this.dataSource.getConnection();
      } catch (SQLException var2) {
         throw new DataAccessException("Error getting connection from data source " + this.dataSource, var2);
      }
   }

   public void release(Connection connection) {
      try {
         connection.close();
      } catch (SQLException var3) {
         throw new DataAccessException("Error closing connection " + connection, var3);
      }
   }
}
