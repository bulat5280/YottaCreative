package com.p6spy.engine.spy;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.event.JdbcEventListener;
import com.p6spy.engine.wrapper.ConnectionWrapper;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.ConnectionEventListener;
import javax.sql.PooledConnection;
import javax.sql.StatementEventListener;

public class P6PooledConnection implements PooledConnection {
   protected final PooledConnection passthru;
   protected final JdbcEventListenerFactory jdbcEventListenerFactory;

   public P6PooledConnection(PooledConnection connection, JdbcEventListenerFactory jdbcEventListenerFactory) {
      this.passthru = connection;
      this.jdbcEventListenerFactory = jdbcEventListenerFactory;
   }

   public Connection getConnection() throws SQLException {
      long start = System.nanoTime();
      JdbcEventListener jdbcEventListener = this.jdbcEventListenerFactory.createJdbcEventListener();
      ConnectionInformation connectionInformation = ConnectionInformation.fromPooledConnection(this.passthru);
      jdbcEventListener.onBeforeGetConnection(connectionInformation);

      Connection conn;
      try {
         conn = this.passthru.getConnection();
         connectionInformation.setConnection(conn);
         connectionInformation.setTimeToGetConnectionNs(System.nanoTime() - start);
         jdbcEventListener.onAfterGetConnection(connectionInformation, (SQLException)null);
      } catch (SQLException var7) {
         connectionInformation.setTimeToGetConnectionNs(System.nanoTime() - start);
         jdbcEventListener.onAfterGetConnection(connectionInformation, var7);
         throw var7;
      }

      return ConnectionWrapper.wrap(conn, jdbcEventListener, connectionInformation);
   }

   public void close() throws SQLException {
      this.passthru.close();
   }

   public void addConnectionEventListener(ConnectionEventListener eventTarget) {
      this.passthru.addConnectionEventListener(eventTarget);
   }

   public void removeConnectionEventListener(ConnectionEventListener eventTarget) {
      this.passthru.removeConnectionEventListener(eventTarget);
   }

   public void addStatementEventListener(StatementEventListener listener) {
      this.passthru.addStatementEventListener(listener);
   }

   public void removeStatementEventListener(StatementEventListener listener) {
      this.passthru.removeStatementEventListener(listener);
   }
}
