package org.jooq;

public enum Comparator {
   @Support
   IN("in", false, true),
   @Support
   NOT_IN("not in", false, true),
   @Support
   EQUALS("=", true, true),
   @Support
   NOT_EQUALS("<>", true, true),
   @Support
   LESS("<", true, true),
   @Support
   LESS_OR_EQUAL("<=", true, true),
   @Support
   GREATER(">", true, true),
   @Support
   GREATER_OR_EQUAL(">=", true, true),
   @Support
   IS_DISTINCT_FROM("is distinct from", false, false),
   @Support
   IS_NOT_DISTINCT_FROM("is not distinct from", false, false),
   @Support
   LIKE("like", false, false),
   @Support
   NOT_LIKE("not like", false, false),
   @Support
   LIKE_IGNORE_CASE("ilike", false, false),
   @Support
   NOT_LIKE_IGNORE_CASE("not ilike", false, false);

   private final String sql;
   private final boolean supportsQuantifier;
   private final boolean supportsSubselect;

   private Comparator(String sql, boolean supportsQuantifier, boolean supportsSubselect) {
      this.sql = sql;
      this.supportsQuantifier = supportsQuantifier;
      this.supportsSubselect = supportsSubselect;
   }

   public String toSQL() {
      return this.sql;
   }

   public boolean supportsQuantifier() {
      return this.supportsQuantifier;
   }

   public boolean supportsSubselect() {
      return this.supportsSubselect;
   }
}
