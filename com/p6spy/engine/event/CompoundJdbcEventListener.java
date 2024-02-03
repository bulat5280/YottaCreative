package com.p6spy.engine.event;

import com.p6spy.engine.common.CallableStatementInformation;
import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.common.ResultSetInformation;
import com.p6spy.engine.common.StatementInformation;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CompoundJdbcEventListener extends JdbcEventListener {
   private final List<JdbcEventListener> eventListeners;

   public CompoundJdbcEventListener() {
      this.eventListeners = new ArrayList();
   }

   public CompoundJdbcEventListener(List<JdbcEventListener> eventListeners) {
      this.eventListeners = eventListeners;
   }

   public void addListender(JdbcEventListener listener) {
      this.eventListeners.add(listener);
   }

   public List<JdbcEventListener> getEventListeners() {
      return Collections.unmodifiableList(this.eventListeners);
   }

   public void onBeforeGetConnection(ConnectionInformation connectionInformation) {
      Iterator var2 = this.eventListeners.iterator();

      while(var2.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var2.next();
         eventListener.onBeforeGetConnection(connectionInformation);
      }

   }

   public void onAfterGetConnection(ConnectionInformation connectionInformation, SQLException e) {
      Iterator var3 = this.eventListeners.iterator();

      while(var3.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var3.next();
         eventListener.onAfterGetConnection(connectionInformation, e);
      }

   }

   /** @deprecated */
   @Deprecated
   public void onConnectionWrapped(ConnectionInformation connectionInformation) {
      Iterator var2 = this.eventListeners.iterator();

      while(var2.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var2.next();
         eventListener.onConnectionWrapped(connectionInformation);
      }

   }

   public void onBeforeAddBatch(PreparedStatementInformation statementInformation) {
      Iterator var2 = this.eventListeners.iterator();

      while(var2.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var2.next();
         eventListener.onBeforeAddBatch(statementInformation);
      }

   }

   public void onAfterAddBatch(PreparedStatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
      Iterator var5 = this.eventListeners.iterator();

      while(var5.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var5.next();
         eventListener.onAfterAddBatch(statementInformation, timeElapsedNanos, e);
      }

   }

   public void onBeforeAddBatch(StatementInformation statementInformation, String sql) {
      Iterator var3 = this.eventListeners.iterator();

      while(var3.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var3.next();
         eventListener.onBeforeAddBatch(statementInformation, sql);
      }

   }

   public void onAfterAddBatch(StatementInformation statementInformation, long timeElapsedNanos, String sql, SQLException e) {
      Iterator var6 = this.eventListeners.iterator();

      while(var6.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var6.next();
         eventListener.onAfterAddBatch(statementInformation, timeElapsedNanos, sql, e);
      }

   }

   public void onBeforeExecute(PreparedStatementInformation statementInformation) {
      Iterator var2 = this.eventListeners.iterator();

      while(var2.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var2.next();
         eventListener.onBeforeExecute(statementInformation);
      }

   }

   public void onAfterExecute(PreparedStatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
      Iterator var5 = this.eventListeners.iterator();

      while(var5.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var5.next();
         eventListener.onAfterExecute(statementInformation, timeElapsedNanos, e);
      }

   }

   public void onBeforeExecute(StatementInformation statementInformation, String sql) {
      Iterator var3 = this.eventListeners.iterator();

      while(var3.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var3.next();
         eventListener.onBeforeExecute(statementInformation, sql);
      }

   }

   public void onAfterExecute(StatementInformation statementInformation, long timeElapsedNanos, String sql, SQLException e) {
      Iterator var6 = this.eventListeners.iterator();

      while(var6.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var6.next();
         eventListener.onAfterExecute(statementInformation, timeElapsedNanos, sql, e);
      }

   }

   public void onBeforeExecuteBatch(StatementInformation statementInformation) {
      Iterator var2 = this.eventListeners.iterator();

      while(var2.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var2.next();
         eventListener.onBeforeExecuteBatch(statementInformation);
      }

   }

   public void onAfterExecuteBatch(StatementInformation statementInformation, long timeElapsedNanos, int[] updateCounts, SQLException e) {
      Iterator var6 = this.eventListeners.iterator();

      while(var6.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var6.next();
         eventListener.onAfterExecuteBatch(statementInformation, timeElapsedNanos, updateCounts, e);
      }

   }

   public void onBeforeExecuteUpdate(PreparedStatementInformation statementInformation) {
      Iterator var2 = this.eventListeners.iterator();

      while(var2.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var2.next();
         eventListener.onBeforeExecuteUpdate(statementInformation);
      }

   }

   public void onAfterExecuteUpdate(PreparedStatementInformation statementInformation, long timeElapsedNanos, int rowCount, SQLException e) {
      Iterator var6 = this.eventListeners.iterator();

      while(var6.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var6.next();
         eventListener.onAfterExecuteUpdate(statementInformation, timeElapsedNanos, rowCount, e);
      }

   }

   public void onBeforeExecuteUpdate(StatementInformation statementInformation, String sql) {
      Iterator var3 = this.eventListeners.iterator();

      while(var3.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var3.next();
         eventListener.onBeforeExecuteUpdate(statementInformation, sql);
      }

   }

   public void onAfterExecuteUpdate(StatementInformation statementInformation, long timeElapsedNanos, String sql, int rowCount, SQLException e) {
      Iterator var7 = this.eventListeners.iterator();

      while(var7.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var7.next();
         eventListener.onAfterExecuteUpdate(statementInformation, timeElapsedNanos, sql, rowCount, e);
      }

   }

   public void onBeforeExecuteQuery(PreparedStatementInformation statementInformation) {
      Iterator var2 = this.eventListeners.iterator();

      while(var2.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var2.next();
         eventListener.onBeforeExecuteQuery(statementInformation);
      }

   }

   public void onAfterExecuteQuery(PreparedStatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
      Iterator var5 = this.eventListeners.iterator();

      while(var5.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var5.next();
         eventListener.onAfterExecuteQuery(statementInformation, timeElapsedNanos, e);
      }

   }

   public void onBeforeExecuteQuery(StatementInformation statementInformation, String sql) {
      Iterator var3 = this.eventListeners.iterator();

      while(var3.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var3.next();
         eventListener.onBeforeExecuteQuery(statementInformation, sql);
      }

   }

   public void onAfterExecuteQuery(StatementInformation statementInformation, long timeElapsedNanos, String sql, SQLException e) {
      Iterator var6 = this.eventListeners.iterator();

      while(var6.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var6.next();
         eventListener.onAfterExecuteQuery(statementInformation, timeElapsedNanos, sql, e);
      }

   }

   public void onAfterPreparedStatementSet(PreparedStatementInformation statementInformation, int parameterIndex, Object value, SQLException e) {
      Iterator var5 = this.eventListeners.iterator();

      while(var5.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var5.next();
         eventListener.onAfterPreparedStatementSet(statementInformation, parameterIndex, value, e);
      }

   }

   public void onAfterCallableStatementSet(CallableStatementInformation statementInformation, String parameterName, Object value, SQLException e) {
      Iterator var5 = this.eventListeners.iterator();

      while(var5.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var5.next();
         eventListener.onAfterCallableStatementSet(statementInformation, parameterName, value, e);
      }

   }

   public void onAfterGetResultSet(StatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
      Iterator var5 = this.eventListeners.iterator();

      while(var5.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var5.next();
         eventListener.onAfterGetResultSet(statementInformation, timeElapsedNanos, e);
      }

   }

   public void onBeforeResultSetNext(ResultSetInformation resultSetInformation) {
      Iterator var2 = this.eventListeners.iterator();

      while(var2.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var2.next();
         eventListener.onBeforeResultSetNext(resultSetInformation);
      }

   }

   public void onAfterResultSetNext(ResultSetInformation resultSetInformation, long timeElapsedNanos, boolean hasNext, SQLException e) {
      Iterator var6 = this.eventListeners.iterator();

      while(var6.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var6.next();
         eventListener.onAfterResultSetNext(resultSetInformation, timeElapsedNanos, hasNext, e);
      }

   }

   public void onAfterResultSetClose(ResultSetInformation resultSetInformation, SQLException e) {
      Iterator var3 = this.eventListeners.iterator();

      while(var3.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var3.next();
         eventListener.onAfterResultSetClose(resultSetInformation, e);
      }

   }

   public void onAfterResultSetGet(ResultSetInformation resultSetInformation, String columnLabel, Object value, SQLException e) {
      Iterator var5 = this.eventListeners.iterator();

      while(var5.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var5.next();
         eventListener.onAfterResultSetGet(resultSetInformation, columnLabel, value, e);
      }

   }

   public void onAfterResultSetGet(ResultSetInformation resultSetInformation, int columnIndex, Object value, SQLException e) {
      Iterator var5 = this.eventListeners.iterator();

      while(var5.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var5.next();
         eventListener.onAfterResultSetGet(resultSetInformation, columnIndex, value, e);
      }

   }

   public void onBeforeCommit(ConnectionInformation connectionInformation) {
      Iterator var2 = this.eventListeners.iterator();

      while(var2.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var2.next();
         eventListener.onBeforeCommit(connectionInformation);
      }

   }

   public void onAfterCommit(ConnectionInformation connectionInformation, long timeElapsedNanos, SQLException e) {
      Iterator var5 = this.eventListeners.iterator();

      while(var5.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var5.next();
         eventListener.onAfterCommit(connectionInformation, timeElapsedNanos, e);
      }

   }

   public void onAfterConnectionClose(ConnectionInformation connectionInformation, SQLException e) {
      Iterator var3 = this.eventListeners.iterator();

      while(var3.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var3.next();
         eventListener.onAfterConnectionClose(connectionInformation, e);
      }

   }

   public void onBeforeRollback(ConnectionInformation connectionInformation) {
      Iterator var2 = this.eventListeners.iterator();

      while(var2.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var2.next();
         eventListener.onBeforeRollback(connectionInformation);
      }

   }

   public void onAfterRollback(ConnectionInformation connectionInformation, long timeElapsedNanos, SQLException e) {
      Iterator var5 = this.eventListeners.iterator();

      while(var5.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var5.next();
         eventListener.onAfterRollback(connectionInformation, timeElapsedNanos, e);
      }

   }

   public void onAfterStatementClose(StatementInformation statementInformation, SQLException e) {
      Iterator var3 = this.eventListeners.iterator();

      while(var3.hasNext()) {
         JdbcEventListener eventListener = (JdbcEventListener)var3.next();
         eventListener.onAfterStatementClose(statementInformation, e);
      }

   }
}
