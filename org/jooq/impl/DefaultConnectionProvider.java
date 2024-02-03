package org.jooq.impl;

import java.sql.Connection;
import java.sql.Savepoint;
import org.jooq.ConnectionProvider;
import org.jooq.exception.DataAccessException;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.jdbc.JDBCUtils;

public class DefaultConnectionProvider implements ConnectionProvider {
   private static final JooqLogger log = JooqLogger.getLogger(DefaultConnectionProvider.class);
   Connection connection;
   final boolean finalize;

   public DefaultConnectionProvider(Connection connection) {
      this(connection, false);
   }

   DefaultConnectionProvider(Connection connection, boolean finalize) {
      this.connection = connection;
      this.finalize = finalize;
   }

   public final Connection acquire() {
      return this.connection;
   }

   public final void release(Connection released) {
   }

   protected void finalize() throws Throwable {
      if (this.finalize) {
         JDBCUtils.safeClose(this.connection);
      }

      super.finalize();
   }

   public final void setConnection(Connection connection) {
      this.connection = connection;
   }

   public final void commit() throws DataAccessException {
      try {
         log.debug("commit");
         this.connection.commit();
      } catch (Exception var2) {
         throw new DataAccessException("Cannot commit transaction", var2);
      }
   }

   public final void rollback() throws DataAccessException {
      try {
         log.debug("rollback");
         this.connection.rollback();
      } catch (Exception var2) {
         throw new DataAccessException("Cannot rollback transaction", var2);
      }
   }

   public final void rollback(Savepoint savepoint) throws DataAccessException {
      try {
         log.debug("rollback to savepoint");
         this.connection.rollback(savepoint);
      } catch (Exception var3) {
         throw new DataAccessException("Cannot rollback transaction", var3);
      }
   }

   public final Savepoint setSavepoint() throws DataAccessException {
      try {
         log.debug("set savepoint");
         return this.connection.setSavepoint();
      } catch (Exception var2) {
         throw new DataAccessException("Cannot set savepoint", var2);
      }
   }

   public final Savepoint setSavepoint(String name) throws DataAccessException {
      try {
         log.debug("set savepoint", (Object)name);
         return this.connection.setSavepoint(name);
      } catch (Exception var3) {
         throw new DataAccessException("Cannot set savepoint", var3);
      }
   }

   public final void releaseSavepoint(Savepoint savepoint) throws DataAccessException {
      try {
         log.debug("release savepoint");
         this.connection.releaseSavepoint(savepoint);
      } catch (Exception var3) {
         throw new DataAccessException("Cannot release savepoint", var3);
      }
   }

   public final void setReadOnly(boolean readOnly) throws DataAccessException {
      try {
         log.debug("setting read only", (Object)readOnly);
         this.connection.setReadOnly(readOnly);
      } catch (Exception var3) {
         throw new DataAccessException("Cannot set readOnly", var3);
      }
   }

   public final boolean isReadOnly() throws DataAccessException {
      try {
         return this.connection.isReadOnly();
      } catch (Exception var2) {
         throw new DataAccessException("Cannot get readOnly", var2);
      }
   }

   public final void setAutoCommit(boolean autoCommit) throws DataAccessException {
      try {
         log.debug("setting auto commit", (Object)autoCommit);
         this.connection.setAutoCommit(autoCommit);
      } catch (Exception var3) {
         throw new DataAccessException("Cannot set autoCommit", var3);
      }
   }

   public final boolean getAutoCommit() throws DataAccessException {
      try {
         return this.connection.getAutoCommit();
      } catch (Exception var2) {
         throw new DataAccessException("Cannot get autoCommit", var2);
      }
   }

   public final void setHoldability(int holdability) throws DataAccessException {
      try {
         log.debug("setting holdability", (Object)holdability);
         this.connection.setHoldability(holdability);
      } catch (Exception var3) {
         throw new DataAccessException("Cannot set holdability", var3);
      }
   }

   public final int getHoldability() throws DataAccessException {
      try {
         return this.connection.getHoldability();
      } catch (Exception var2) {
         throw new DataAccessException("Cannot get holdability", var2);
      }
   }

   public final void setTransactionIsolation(int level) throws DataAccessException {
      try {
         log.debug("setting tx isolation", (Object)level);
         this.connection.setTransactionIsolation(level);
      } catch (Exception var3) {
         throw new DataAccessException("Cannot set transactionIsolation", var3);
      }
   }

   public final int getTransactionIsolation() throws DataAccessException {
      try {
         return this.connection.getTransactionIsolation();
      } catch (Exception var2) {
         throw new DataAccessException("Cannot get transactionIsolation", var2);
      }
   }
}
