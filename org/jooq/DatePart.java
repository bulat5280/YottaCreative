package org.jooq;

public enum DatePart {
   YEAR("year"),
   MONTH("month"),
   DAY("day"),
   HOUR("hour"),
   MINUTE("minute"),
   SECOND("second");

   private final String sql;

   private DatePart(String sql) {
      this.sql = sql;
   }

   public String toSQL() {
      return this.sql;
   }
}
