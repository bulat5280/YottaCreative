package com.p6spy.engine.outage;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.common.StatementInformation;
import com.p6spy.engine.event.SimpleJdbcEventListener;
import java.sql.SQLException;

public class OutageJdbcEventListener extends SimpleJdbcEventListener {
   public static final OutageJdbcEventListener INSTANCE = new OutageJdbcEventListener();

   private OutageJdbcEventListener() {
   }

   public void onBeforeCommit(ConnectionInformation connectionInformation) {
      if (P6OutageOptions.getActiveInstance().getOutageDetection()) {
         P6OutageDetector.INSTANCE.registerInvocation(this, System.nanoTime(), "commit", "", "");
      }

   }

   public void onAfterCommit(ConnectionInformation connectionInformation, long timeElapsedNanos, SQLException e) {
      if (P6OutageOptions.getActiveInstance().getOutageDetection()) {
         P6OutageDetector.INSTANCE.unregisterInvocation(this);
      }

   }

   public void onBeforeRollback(ConnectionInformation connectionInformation) {
      if (P6OutageOptions.getActiveInstance().getOutageDetection()) {
         P6OutageDetector.INSTANCE.registerInvocation(this, System.nanoTime(), "rollback", "", "");
      }

   }

   public void onAfterRollback(ConnectionInformation connectionInformation, long timeElapsedNanos, SQLException e) {
      if (P6OutageOptions.getActiveInstance().getOutageDetection()) {
         P6OutageDetector.INSTANCE.unregisterInvocation(this);
      }

   }

   public void onBeforeAnyAddBatch(StatementInformation statementInformation) {
      if (P6OutageOptions.getActiveInstance().getOutageDetection()) {
         P6OutageDetector.INSTANCE.registerInvocation(this, System.nanoTime(), "batch", statementInformation.getSqlWithValues(), statementInformation.getStatementQuery());
      }

   }

   public void onAfterAnyAddBatch(StatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
      if (P6OutageOptions.getActiveInstance().getOutageDetection()) {
         P6OutageDetector.INSTANCE.unregisterInvocation(this);
      }

   }

   public void onBeforeAnyExecute(StatementInformation statementInformation) {
      if (P6OutageOptions.getActiveInstance().getOutageDetection()) {
         P6OutageDetector.INSTANCE.registerInvocation(this, System.nanoTime(), "statement", statementInformation.getSqlWithValues(), statementInformation.getStatementQuery());
      }

   }

   public void onAfterAnyExecute(StatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
      if (P6OutageOptions.getActiveInstance().getOutageDetection()) {
         P6OutageDetector.INSTANCE.unregisterInvocation(this);
      }

   }
}
