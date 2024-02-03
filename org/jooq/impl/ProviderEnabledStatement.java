package org.jooq.impl;

import java.sql.SQLException;
import java.sql.Statement;
import org.jooq.tools.jdbc.DefaultStatement;

final class ProviderEnabledStatement extends DefaultStatement {
   private final ProviderEnabledConnection connection;

   ProviderEnabledStatement(ProviderEnabledConnection connection, Statement statement) {
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
