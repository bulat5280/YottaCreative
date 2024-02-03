package org.jooq.tools.jdbc;

import java.sql.Connection;
import org.jooq.ConnectionProvider;

public class MockConnectionProvider implements ConnectionProvider {
   private final ConnectionProvider delegate;
   private final MockDataProvider provider;

   public MockConnectionProvider(ConnectionProvider delegate, MockDataProvider provider) {
      this.delegate = delegate;
      this.provider = provider;
   }

   public final Connection acquire() {
      return new MockConnectionProvider.MockConnectionWrapper(this.delegate.acquire());
   }

   public final void release(Connection connection) {
      if (connection instanceof MockConnectionProvider.MockConnectionWrapper) {
         this.delegate.release(((MockConnectionProvider.MockConnectionWrapper)connection).connection);
      } else {
         throw new IllegalArgumentException("Argument connection must be a MockConnectionWrapper");
      }
   }

   private class MockConnectionWrapper extends MockConnection {
      final Connection connection;

      public MockConnectionWrapper(Connection connection) {
         super(MockConnectionProvider.this.provider);
         this.connection = connection;
      }
   }
}
