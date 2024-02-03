package com.p6spy.engine.common;

public class StatementInformation implements Loggable {
   private final ConnectionInformation connectionInformation;
   private String statementQuery;
   private long totalTimeElapsed;

   public StatementInformation(ConnectionInformation connectionInformation) {
      this.connectionInformation = connectionInformation;
   }

   public String getStatementQuery() {
      return this.statementQuery;
   }

   public void setStatementQuery(String statementQuery) {
      this.statementQuery = statementQuery;
   }

   public ConnectionInformation getConnectionInformation() {
      return this.connectionInformation;
   }

   public String getSqlWithValues() {
      return this.getSql();
   }

   public String getSql() {
      return this.statementQuery;
   }

   public long getTotalTimeElapsed() {
      return this.totalTimeElapsed;
   }

   public void incrementTimeElapsed(long timeElapsedNanos) {
      this.totalTimeElapsed += timeElapsedNanos;
   }
}
