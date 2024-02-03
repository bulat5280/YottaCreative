package org.jooq.tools.jdbc;

import java.sql.SQLException;

public abstract class JDBC41ResultSet {
   public final <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public final <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
      throw new UnsupportedOperationException("Not supported yet.");
   }
}
