package com.p6spy.engine.event;

import com.p6spy.engine.common.CallableStatementInformation;
import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.common.ResultSetInformation;
import com.p6spy.engine.common.StatementInformation;
import java.sql.SQLException;

public class DefaultEventListener extends JdbcEventListener {
   public static final DefaultEventListener INSTANCE = new DefaultEventListener();

   private DefaultEventListener() {
   }

   public void onAfterAddBatch(StatementInformation statementInformation, long timeElapsedNanos, String sql, SQLException e) {
   }

   public void onAfterExecute(PreparedStatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
      statementInformation.incrementTimeElapsed(timeElapsedNanos);
   }

   public void onAfterExecute(StatementInformation statementInformation, long timeElapsedNanos, String sql, SQLException e) {
      statementInformation.incrementTimeElapsed(timeElapsedNanos);
   }

   public void onAfterExecuteBatch(StatementInformation statementInformation, long timeElapsedNanos, int[] updateCounts, SQLException e) {
      statementInformation.incrementTimeElapsed(timeElapsedNanos);
   }

   public void onAfterExecuteUpdate(PreparedStatementInformation statementInformation, long timeElapsedNanos, int rowCount, SQLException e) {
      statementInformation.incrementTimeElapsed(timeElapsedNanos);
   }

   public void onAfterExecuteUpdate(StatementInformation statementInformation, long timeElapsedNanos, String sql, int rowCount, SQLException e) {
      statementInformation.incrementTimeElapsed(timeElapsedNanos);
   }

   public void onAfterExecuteQuery(PreparedStatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
      statementInformation.incrementTimeElapsed(timeElapsedNanos);
   }

   public void onAfterExecuteQuery(StatementInformation statementInformation, long timeElapsedNanos, String sql, SQLException e) {
      statementInformation.incrementTimeElapsed(timeElapsedNanos);
   }

   public void onAfterGetResultSet(StatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
      statementInformation.incrementTimeElapsed(timeElapsedNanos);
   }

   public void onAfterResultSetNext(ResultSetInformation resultSetInformation, long timeElapsedNanos, boolean hasNext, SQLException e) {
      resultSetInformation.getStatementInformation().incrementTimeElapsed(timeElapsedNanos);
      if (hasNext) {
         resultSetInformation.incrementCurrRow();
      }

   }

   public void onAfterCallableStatementSet(CallableStatementInformation statementInformation, String parameterName, Object value, SQLException e) {
      statementInformation.setParameterValue(parameterName, value);
   }

   public void onAfterPreparedStatementSet(PreparedStatementInformation statementInformation, int parameterIndex, Object value, SQLException e) {
      statementInformation.setParameterValue(parameterIndex, value);
   }
}
