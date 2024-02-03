package org.jooq.impl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import org.jooq.tools.jdbc.DefaultCallableStatement;

final class ProviderEnabledCallableStatement extends DefaultCallableStatement {
   private final ProviderEnabledConnection connection;

   ProviderEnabledCallableStatement(ProviderEnabledConnection connection, CallableStatement statement) {
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
