package org.jooq.impl;

import org.jooq.SQLDialect;

enum CombineOperator {
   UNION("union"),
   UNION_ALL("union all"),
   EXCEPT("except"),
   EXCEPT_ALL("except all"),
   INTERSECT("intersect"),
   INTERSECT_ALL("intersect all");

   private final String sql;

   private CombineOperator(String sql) {
      this.sql = sql;
   }

   public String toSQL(SQLDialect dialect) {
      return this.sql;
   }
}
