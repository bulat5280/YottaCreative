package org.flywaydb.core.internal.util.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.concurrent.Callable;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.exception.FlywaySqlException;

public class TransactionTemplate {
   private static final Log LOG = LogFactory.getLog(TransactionTemplate.class);
   private final Connection connection;
   private final boolean rollbackOnException;

   public TransactionTemplate(Connection connection) {
      this(connection, true);
   }

   public TransactionTemplate(Connection connection, boolean rollbackOnException) {
      this.connection = connection;
      this.rollbackOnException = rollbackOnException;
   }

   public <T> T execute(Callable<T> transactionCallback) {
      boolean oldAutocommit = true;

      Object var22;
      try {
         oldAutocommit = this.connection.getAutoCommit();
         this.connection.setAutoCommit(false);
         T result = transactionCallback.call();
         this.connection.commit();
         var22 = result;
      } catch (SQLException var19) {
         throw new FlywaySqlException("Unable to commit transaction", var19);
      } catch (Exception var20) {
         Savepoint savepoint = null;
         Object rethrow;
         if (var20 instanceof RollbackWithSavepointException) {
            savepoint = ((RollbackWithSavepointException)var20).getSavepoint();
            rethrow = (RuntimeException)var20.getCause();
         } else if (var20 instanceof RuntimeException) {
            rethrow = (RuntimeException)var20;
         } else {
            rethrow = new FlywayException(var20);
         }

         if (this.rollbackOnException) {
            try {
               LOG.debug("Rolling back transaction...");
               if (savepoint == null) {
                  this.connection.rollback();
               } else {
                  this.connection.rollback(savepoint);
               }

               LOG.debug("Transaction rolled back");
            } catch (SQLException var18) {
               LOG.error("Unable to rollback transaction", var18);
            }
         } else {
            try {
               this.connection.commit();
            } catch (SQLException var17) {
               LOG.error("Unable to commit transaction", var17);
            }
         }

         throw rethrow;
      } finally {
         try {
            this.connection.setAutoCommit(oldAutocommit);
         } catch (SQLException var16) {
            LOG.error("Unable to restore autocommit to original value for connection", var16);
         }

      }

      return var22;
   }
}
