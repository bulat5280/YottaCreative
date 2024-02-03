package org.jooq.tools.jdbc;

import java.sql.SQLException;
import java.util.concurrent.Executor;

public abstract class JDBC41Connection {
   public final void setSchema(String s) throws SQLException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public final String getSchema() throws SQLException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public final void abort(Executor executor) throws SQLException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public final void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public final int getNetworkTimeout() throws SQLException {
      throw new UnsupportedOperationException("Not supported yet.");
   }
}
