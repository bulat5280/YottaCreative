package org.jooq;

public enum JoinType {
   @Support
   JOIN("join"),
   @Support
   CROSS_JOIN("cross join"),
   @Support
   LEFT_OUTER_JOIN("left outer join"),
   @Support
   RIGHT_OUTER_JOIN("right outer join"),
   @Support
   FULL_OUTER_JOIN("full outer join"),
   @Support
   NATURAL_JOIN("natural join"),
   @Support
   NATURAL_LEFT_OUTER_JOIN("natural left outer join"),
   @Support
   NATURAL_RIGHT_OUTER_JOIN("natural right outer join"),
   @Support({})
   CROSS_APPLY("cross apply"),
   @Support({})
   OUTER_APPLY("outer apply"),
   @Support({SQLDialect.MYSQL})
   STRAIGHT_JOIN("straight_join"),
   @Support
   LEFT_SEMI_JOIN("left semi join"),
   @Support
   LEFT_ANTI_JOIN("left anti join");

   private final String sql;

   private JoinType(String sql) {
      this.sql = sql;
   }

   public final String toSQL() {
      return this.sql;
   }
}
