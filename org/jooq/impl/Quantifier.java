package org.jooq.impl;

enum Quantifier {
   ANY("any"),
   ALL("all");

   private final String sql;

   private Quantifier(String sql) {
      this.sql = sql;
   }

   public String toSQL() {
      return this.sql;
   }
}
