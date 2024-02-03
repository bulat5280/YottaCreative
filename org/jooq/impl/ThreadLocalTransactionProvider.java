package org.jooq.impl;

import java.sql.Connection;
import java.util.ArrayDeque;
import java.util.Deque;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.TransactionContext;
import org.jooq.TransactionProvider;

public class ThreadLocalTransactionProvider implements TransactionProvider {
   final DefaultTransactionProvider delegateTransactionProvider;
   final ThreadLocalTransactionProvider.ThreadLocalConnectionProvider localConnectionProvider;
   final ThreadLocal<Connection> localTxConnection;
   final ThreadLocal<Deque<Configuration>> localConfigurations;

   public ThreadLocalTransactionProvider(ConnectionProvider provider) {
      this(provider, true);
   }

   public ThreadLocalTransactionProvider(ConnectionProvider connectionProvider, boolean nested) {
      this.localConnectionProvider = new ThreadLocalTransactionProvider.ThreadLocalConnectionProvider(connectionProvider);
      this.delegateTransactionProvider = new DefaultTransactionProvider(this.localConnectionProvider, nested);
      this.localConfigurations = new ThreadLocal();
      this.localTxConnection = new ThreadLocal();
   }

   public void begin(TransactionContext ctx) {
      this.delegateTransactionProvider.begin(ctx);
      this.configurations().push(ctx.configuration());
      if (this.delegateTransactionProvider.nestingLevel(ctx.configuration()) == 1) {
         this.localTxConnection.set(((DefaultConnectionProvider)ctx.configuration().data(Tools.DataKey.DATA_DEFAULT_TRANSACTION_PROVIDER_CONNECTION)).connection);
      }

   }

   public void commit(TransactionContext ctx) {
      if (this.delegateTransactionProvider.nestingLevel(ctx.configuration()) == 1) {
         this.localTxConnection.remove();
      }

      this.configurations().pop();
      this.delegateTransactionProvider.commit(ctx);
   }

   public void rollback(TransactionContext ctx) {
      if (this.delegateTransactionProvider.nestingLevel(ctx.configuration()) == 1) {
         this.localTxConnection.remove();
      }

      this.configurations().pop();
      this.delegateTransactionProvider.rollback(ctx);
   }

   Configuration configuration(Configuration fallback) {
      Deque<Configuration> configurations = this.configurations();
      return configurations.isEmpty() ? fallback : (Configuration)configurations.peek();
   }

   private Deque<Configuration> configurations() {
      Deque<Configuration> result = (Deque)this.localConfigurations.get();
      if (result == null) {
         result = new ArrayDeque();
         this.localConfigurations.set(result);
      }

      return (Deque)result;
   }

   final class ThreadLocalConnectionProvider implements ConnectionProvider {
      final ConnectionProvider delegateConnectionProvider;

      public ThreadLocalConnectionProvider(ConnectionProvider delegate) {
         this.delegateConnectionProvider = delegate;
      }

      public final Connection acquire() {
         Connection local = (Connection)ThreadLocalTransactionProvider.this.localTxConnection.get();
         return local == null ? this.delegateConnectionProvider.acquire() : local;
      }

      public final void release(Connection connection) {
         Connection local = (Connection)ThreadLocalTransactionProvider.this.localTxConnection.get();
         if (local == null) {
            this.delegateConnectionProvider.release(connection);
         } else if (local != connection) {
            throw new IllegalStateException("A different connection was released than the thread-bound one that was expected");
         }

      }
   }
}
