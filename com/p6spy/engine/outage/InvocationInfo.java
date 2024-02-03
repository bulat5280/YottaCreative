package com.p6spy.engine.outage;

class InvocationInfo {
   public long startTime;
   public String category;
   public String preparedStmt;
   public String sql;

   public InvocationInfo(long startTime, String category, String ps, String sql) {
      this.startTime = startTime;
      this.category = category;
      this.preparedStmt = ps;
      this.sql = sql;
   }
}
