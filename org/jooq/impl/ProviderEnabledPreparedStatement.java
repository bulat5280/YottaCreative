package org.jooq.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.jooq.tools.jdbc.DefaultPreparedStatement;

final class ProviderEnabledPreparedStatement extends DefaultPreparedStatement {
   private final ProviderEnabledConnection connection;

   ProviderEnabledPreparedStatement(ProviderEnabledConnection connection, PreparedStatement statement) {
      super(statement);
      this.connection = connection;
   }

   public final void close() throws SQLException {
      try {
         this.getDelegate().close();
      } finally {
         this.connection.close();
      }

   }
}
