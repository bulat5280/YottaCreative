package org.jooq;

public enum SortOrder {
   @Support
   ASC("asc"),
   @Support
   DESC("desc");

   private final String sql;

   private SortOrder(String sql) {
      this.sql = sql;
   }

   public String toSQL() {
      return this.sql;
   }
}
