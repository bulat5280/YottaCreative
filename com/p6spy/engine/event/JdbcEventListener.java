package com.p6spy.engine.event;

import com.p6spy.engine.common.CallableStatementInformation;
import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.common.ResultSetInformation;
import com.p6spy.engine.common.StatementInformation;
import java.sql.SQLException;

public abstract class JdbcEventListener {
   public void onBeforeGetConnection(ConnectionInformation connectionInformation) {
   }

   public void onAfterGetConnection(ConnectionInformation connectionInformation, SQLException e) {
   }

   /** @deprecated */
   public void onConnectionWrapped(ConnectionInformation connectionInformation) {
   }

   public void onBeforeAddBatch(PreparedStatementInformation statementInformation) {
   }

   public void onAfterAddBatch(PreparedStatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
   }

   public void onBeforeAddBatch(StatementInformation statementInformation, String sql) {
   }

   public void onAfterAddBatch(StatementInformation statementInformation, long timeElapsedNanos, String sql, SQLException e) {
   }

   public void onBeforeExecute(PreparedStatementInformation statementInformation) {
   }

   public void onAfterExecute(PreparedStatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
   }

   public void onBeforeExecute(StatementInformation statementInformation, String sql) {
   }

   public void onAfterExecute(StatementInformation statementInformation, long timeElapsedNanos, String sql, SQLException e) {
   }

   public void onBeforeExecuteBatch(StatementInformation statementInformation) {
   }

   public void onAfterExecuteBatch(StatementInformation statementInformation, long timeElapsedNanos, int[] updateCounts, SQLException e) {
   }

   public void onBeforeExecuteUpdate(PreparedStatementInformation statementInformation) {
   }

   public void onAfterExecuteUpdate(PreparedStatementInformation statementInformation, long timeElapsedNanos, int rowCount, SQLException e) {
   }

   public void onBeforeExecuteUpdate(StatementInformation statementInformation, String sql) {
   }

   public void onAfterExecuteUpdate(StatementInformation statementInformation, long timeElapsedNanos, String sql, int rowCount, SQLException e) {
   }

   public void onBeforeExecuteQuery(PreparedStatementInformation statementInformation) {
   }

   public void onAfterExecuteQuery(PreparedStatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
   }

   public void onBeforeExecuteQuery(StatementInformation statementInformation, String sql) {
   }

   public void onAfterExecuteQuery(StatementInformation statementInformation, long timeElapsedNanos, String sql, SQLException e) {
   }

   public void onAfterPreparedStatementSet(PreparedStatementInformation statementInformation, int parameterIndex, Object value, SQLException e) {
   }

   public void onAfterCallableStatementSet(CallableStatementInformation statementInformation, String parameterName, Object value, SQLException e) {
   }

   public void onAfterGetResultSet(StatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
   }

   public void onBeforeResultSetNext(ResultSetInformation resultSetInformation) {
   }

   public void onAfterResultSetNext(ResultSetInformation resultSetInformation, long timeElapsedNanos, boolean hasNext, SQLException e) {
   }

   public void onAfterResultSetClose(ResultSetInformation resultSetInformation, SQLException e) {
   }

   public void onAfterResultSetGet(ResultSetInformation resultSetInformation, String columnLabel, Object value, SQLException e) {
   }

   public void onAfterResultSetGet(ResultSetInformation resultSetInformation, int columnIndex, Object value, SQLException e) {
   }

   public void onBeforeCommit(ConnectionInformation connectionInformation) {
   }

   public void onAfterCommit(ConnectionInformation connectionInformation, long timeElapsedNanos, SQLException e) {
   }

   public void onAfterConnectionClose(ConnectionInformation connectionInformation, SQLException e) {
   }

   public void onBeforeRollback(ConnectionInformation connectionInformation) {
   }

   public void onAfterRollback(ConnectionInformation connectionInformation, long timeElapsedNanos, SQLException e) {
   }

   public void onAfterStatementClose(StatementInformation statementInformation, SQLException e) {
   }
}
