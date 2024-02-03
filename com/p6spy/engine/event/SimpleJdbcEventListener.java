package com.p6spy.engine.event;

import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.common.StatementInformation;
import java.sql.SQLException;

public abstract class SimpleJdbcEventListener extends JdbcEventListener {
   public void onBeforeAnyExecute(StatementInformation statementInformation) {
   }

   public void onAfterAnyExecute(StatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
   }

   public void onBeforeAnyAddBatch(StatementInformation statementInformation) {
   }

   public void onAfterAnyAddBatch(StatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
   }

   public void onBeforeExecute(PreparedStatementInformation statementInformation) {
      this.onBeforeAnyExecute(statementInformation);
   }

   public void onBeforeExecute(StatementInformation statementInformation, String sql) {
      this.onBeforeAnyExecute(statementInformation);
   }

   public void onBeforeExecuteBatch(StatementInformation statementInformation) {
      this.onBeforeAnyExecute(statementInformation);
   }

   public void onBeforeExecuteUpdate(PreparedStatementInformation statementInformation) {
      this.onBeforeAnyExecute(statementInformation);
   }

   public void onBeforeExecuteUpdate(StatementInformation statementInformation, String sql) {
      this.onBeforeAnyExecute(statementInformation);
   }

   public void onBeforeExecuteQuery(PreparedStatementInformation statementInformation) {
      this.onBeforeAnyExecute(statementInformation);
   }

   public void onBeforeExecuteQuery(StatementInformation statementInformation, String sql) {
      this.onBeforeAnyExecute(statementInformation);
   }

   public void onAfterExecute(PreparedStatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
      this.onAfterAnyExecute(statementInformation, timeElapsedNanos, e);
   }

   public void onAfterExecute(StatementInformation statementInformation, long timeElapsedNanos, String sql, SQLException e) {
      this.onAfterAnyExecute(statementInformation, timeElapsedNanos, e);
   }

   public void onAfterExecuteBatch(StatementInformation statementInformation, long timeElapsedNanos, int[] updateCounts, SQLException e) {
      this.onAfterAnyExecute(statementInformation, timeElapsedNanos, e);
   }

   public void onAfterExecuteUpdate(PreparedStatementInformation statementInformation, long timeElapsedNanos, int rowCount, SQLException e) {
      this.onAfterAnyExecute(statementInformation, timeElapsedNanos, e);
   }

   public void onAfterExecuteUpdate(StatementInformation statementInformation, long timeElapsedNanos, String sql, int rowCount, SQLException e) {
      this.onAfterAnyExecute(statementInformation, timeElapsedNanos, e);
   }

   public void onAfterExecuteQuery(PreparedStatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
      this.onAfterAnyExecute(statementInformation, timeElapsedNanos, e);
   }

   public void onAfterExecuteQuery(StatementInformation statementInformation, long timeElapsedNanos, String sql, SQLException e) {
      this.onAfterAnyExecute(statementInformation, timeElapsedNanos, e);
   }

   public void onBeforeAddBatch(PreparedStatementInformation statementInformation) {
      this.onBeforeAnyAddBatch(statementInformation);
   }

   public void onBeforeAddBatch(StatementInformation statementInformation, String sql) {
      this.onBeforeAnyAddBatch(statementInformation);
   }

   public void onAfterAddBatch(PreparedStatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
      this.onAfterAnyAddBatch(statementInformation, timeElapsedNanos, e);
   }

   public void onAfterAddBatch(StatementInformation statementInformation, long timeElapsedNanos, String sql, SQLException e) {
      this.onAfterAnyAddBatch(statementInformation, timeElapsedNanos, e);
   }
}
