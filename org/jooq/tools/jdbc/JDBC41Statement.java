package org.jooq.tools.jdbc;

import java.sql.SQLException;

public abstract class JDBC41Statement {
   public final void closeOnCompletion() throws SQLException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public final boolean isCloseOnCompletion() throws SQLException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public final <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public final <T> T getObject(String parameterName, Class<T> type) throws SQLException {
      throw new UnsupportedOperationException("Not supported yet.");
   }
}
