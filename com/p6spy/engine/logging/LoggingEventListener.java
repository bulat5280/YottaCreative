package com.p6spy.engine.logging;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.common.Loggable;
import com.p6spy.engine.common.P6LogQuery;
import com.p6spy.engine.common.ResultSetInformation;
import com.p6spy.engine.common.StatementInformation;
import com.p6spy.engine.event.SimpleJdbcEventListener;
import java.sql.SQLException;

public class LoggingEventListener extends SimpleJdbcEventListener {
   public static final LoggingEventListener INSTANCE = new LoggingEventListener();

   protected LoggingEventListener() {
   }

   public void onAfterAnyExecute(StatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
      this.logElapsed(statementInformation, timeElapsedNanos, Category.STATEMENT, e);
   }

   public void onAfterExecuteBatch(StatementInformation statementInformation, long timeElapsedNanos, int[] updateCounts, SQLException e) {
      this.logElapsed(statementInformation, timeElapsedNanos, Category.BATCH, e);
   }

   public void onAfterCommit(ConnectionInformation connectionInformation, long timeElapsedNanos, SQLException e) {
      this.logElapsed(connectionInformation, timeElapsedNanos, Category.COMMIT, e);
   }

   public void onAfterRollback(ConnectionInformation connectionInformation, long timeElapsedNanos, SQLException e) {
      this.logElapsed(connectionInformation, timeElapsedNanos, Category.ROLLBACK, e);
   }

   public void onAfterAnyAddBatch(StatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
      this.logElapsed(statementInformation, timeElapsedNanos, Category.BATCH, e);
   }

   public void onAfterGetResultSet(StatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
      this.logElapsed(statementInformation, timeElapsedNanos, Category.RESULTSET, e);
   }

   public void onAfterResultSetGet(ResultSetInformation resultSetInformation, int columnIndex, Object value, SQLException e) {
      resultSetInformation.setColumnValue(Integer.toString(columnIndex), value);
   }

   public void onAfterResultSetGet(ResultSetInformation resultSetInformation, String columnLabel, Object value, SQLException e) {
      resultSetInformation.setColumnValue(columnLabel, value);
   }

   public void onBeforeResultSetNext(ResultSetInformation resultSetInformation) {
      if (resultSetInformation.getCurrRow() > -1) {
         resultSetInformation.generateLogMessage();
      }

   }

   public void onAfterResultSetNext(ResultSetInformation resultSetInformation, long timeElapsedNanos, boolean hasNext, SQLException e) {
      if (hasNext) {
         this.logElapsed(resultSetInformation, timeElapsedNanos, Category.RESULT, e);
      }

   }

   public void onAfterResultSetClose(ResultSetInformation resultSetInformation, SQLException e) {
      if (resultSetInformation.getCurrRow() > -1) {
         resultSetInformation.generateLogMessage();
      }

   }

   protected void logElapsed(Loggable loggable, long timeElapsedNanos, Category category, SQLException e) {
      P6LogQuery.logElapsed(loggable.getConnectionInformation().getConnectionId(), timeElapsedNanos, category, loggable);
   }
}
