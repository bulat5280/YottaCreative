package org.jooq;

public enum Operator {
   AND("and"),
   OR("or");

   private final String sql;

   private Operator(String sql) {
      this.sql = sql;
   }

   public String toSQL() {
      return this.sql;
   }
}
