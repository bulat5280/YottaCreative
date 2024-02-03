package org.jooq.impl;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayDeque;
import java.util.Deque;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.TransactionContext;
import org.jooq.TransactionProvider;
import org.jooq.exception.DataAccessException;

public class DefaultTransactionProvider implements TransactionProvider {
   private static final Savepoint UNSUPPORTED_SAVEPOINT = new DefaultTransactionProvider.DefaultSavepoint();
   private static final Savepoint IGNORED_SAVEPOINT = new DefaultTransactionProvider.DefaultSavepoint();
   private final ConnectionProvider connectionProvider;
   private final boolean nested;

   public DefaultTransactionProvider(ConnectionProvider connectionProvider) {
      this(connectionProvider, true);
   }

   public DefaultTransactionProvider(ConnectionProvider connectionProvider, boolean nested) {
      this.connectionProvider = connectionProvider;
      this.nested = nested;
   }

   public final boolean nested() {
      return this.nested;
   }

   final int nestingLevel(Configuration configuration) {
      return this.savepoints(configuration).size();
   }

   private final Deque<Savepoint> savepoints(Configuration configuration) {
      Deque<Savepoint> savepoints = (Deque)configuration.data(Tools.DataKey.DATA_DEFAULT_TRANSACTION_PROVIDER_SAVEPOINTS);
      if (savepoints == null) {
         savepoints = new ArrayDeque();
         configuration.data(Tools.DataKey.DATA_DEFAULT_TRANSACTION_PROVIDER_SAVEPOINTS, savepoints);
      }

      return (Deque)savepoints;
   }

   private final boolean autoCommit(Configuration configuration) {
      Boolean autoCommit = (Boolean)configuration.data(Tools.DataKey.DATA_DEFAULT_TRANSACTION_PROVIDER_AUTOCOMMIT);
      if (autoCommit == null) {
         autoCommit = this.connection(configuration).getAutoCommit();
         configuration.data(Tools.DataKey.DATA_DEFAULT_TRANSACTION_PROVIDER_AUTOCOMMIT, autoCommit);
      }

      return autoCommit;
   }

   private final DefaultConnectionProvider connection(Configuration configuration) {
      DefaultConnectionProvider connectionWrapper = (DefaultConnectionProvider)configuration.data(Tools.DataKey.DATA_DEFAULT_TRANSACTION_PROVIDER_CONNECTION);
      if (connectionWrapper == null) {
         connectionWrapper = new DefaultConnectionProvider(this.connectionProvider.acquire());
         configuration.data(Tools.DataKey.DATA_DEFAULT_TRANSACTION_PROVIDER_CONNECTION, connectionWrapper);
      }

      return connectionWrapper;
   }

   public final void begin(TransactionContext ctx) {
      Deque<Savepoint> savepoints = this.savepoints(ctx.configuration());
      if (savepoints.isEmpty()) {
         this.brace(ctx.configuration(), true);
      }

      Savepoint savepoint = this.setSavepoint(ctx.configuration());
      if (savepoint == UNSUPPORTED_SAVEPOINT && !savepoints.isEmpty()) {
         throw new DataAccessException("Cannot nest transactions because Savepoints are not supported");
      } else {
         savepoints.push(savepoint);
      }
   }

   private final Savepoint setSavepoint(Configuration configuration) {
      if (!this.nested()) {
         return IGNORED_SAVEPOINT;
      } else {
         switch(configuration.family()) {
         case CUBRID:
            return UNSUPPORTED_SAVEPOINT;
         default:
            return this.connection(configuration).setSavepoint();
         }
      }
   }

   public final void commit(TransactionContext ctx) {
      Deque<Savepoint> savepoints = this.savepoints(ctx.configuration());
      Savepoint savepoint = (Savepoint)savepoints.pop();
      if (savepoint != null && savepoint != UNSUPPORTED_SAVEPOINT && savepoint != IGNORED_SAVEPOINT) {
         try {
            this.connection(ctx.configuration()).releaseSavepoint(savepoint);
         } catch (DataAccessException var5) {
         }
      }

      if (savepoints.isEmpty()) {
         this.connection(ctx.configuration()).commit();
         this.brace(ctx.configuration(), false);
      }

   }

   public final void rollback(TransactionContext ctx) {
      Deque<Savepoint> savepoints = this.savepoints(ctx.configuration());
      Savepoint savepoint = null;
      if (!savepoints.isEmpty()) {
         savepoint = (Savepoint)savepoints.pop();
      }

      try {
         if (savepoint != null && savepoint != UNSUPPORTED_SAVEPOINT) {
            if (savepoint == IGNORED_SAVEPOINT) {
               if (savepoints.isEmpty()) {
                  this.connection(ctx.configuration()).rollback();
               }
            } else {
               this.connection(ctx.configuration()).rollback(savepoint);
            }
         } else {
            this.connection(ctx.configuration()).rollback();
         }
      } finally {
         if (savepoints.isEmpty()) {
            this.brace(ctx.configuration(), false);
         }

      }

   }

   private final void brace(Configuration configuration, boolean start) {
      DefaultConnectionProvider connection = this.connection(configuration);

      try {
         boolean autoCommit = this.autoCommit(configuration);
         if (autoCommit) {
            connection.setAutoCommit(!start);
         }
      } finally {
         if (!start) {
            this.connectionProvider.release(connection.connection);
            configuration.data().remove(Tools.DataKey.DATA_DEFAULT_TRANSACTION_PROVIDER_CONNECTION);
         }

      }

   }

   private static class DefaultSavepoint implements Savepoint {
      private DefaultSavepoint() {
      }

      public int getSavepointId() throws SQLException {
         return 0;
      }

      public String getSavepointName() throws SQLException {
         return null;
      }

      // $FF: synthetic method
      DefaultSavepoint(Object x0) {
         this();
      }
   }
}
