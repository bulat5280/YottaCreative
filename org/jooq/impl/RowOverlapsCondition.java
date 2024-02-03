package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.QueryPartInternal;
import org.jooq.Row2;
import org.jooq.SQLDialect;

final class RowOverlapsCondition<T1, T2> extends AbstractCondition {
   private static final long serialVersionUID = 85887551884667824L;
   private static final Clause[] CLAUSES;
   private final Row2<T1, T2> left;
   private final Row2<T1, T2> right;

   RowOverlapsCondition(Row2<T1, T2> left, Row2<T1, T2> right) {
      this.left = left;
      this.right = right;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.delegate(ctx.configuration()));
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   private final QueryPartInternal delegate(Configuration configuration) {
      Field<T1> left1 = this.left.field1();
      Field<T2> left2 = this.left.field2();
      Field<T1> right1 = this.right.field1();
      Field<T2> right2 = this.right.field2();
      DataType<?> type0 = left1.getDataType();
      DataType<?> type1 = left2.getDataType();
      boolean standardOverlaps = type0.isDateTime() && type1.isTemporal();
      boolean intervalOverlaps = type0.isDateTime() && (type1.isInterval() || type1.isNumeric());
      if (standardOverlaps && !Arrays.asList(SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.SQLITE).contains(configuration.family())) {
         return (QueryPartInternal)(intervalOverlaps && Arrays.asList(SQLDialect.HSQLDB).contains(configuration.dialect()) ? (QueryPartInternal)right1.le(left1.add(left2)).and(left1.le(right1.add(right2))) : new RowOverlapsCondition.Native());
      } else {
         return intervalOverlaps ? (QueryPartInternal)right1.le(left1.add(left2)).and(left1.le(right1.add(right2))) : (QueryPartInternal)right1.le(left2.cast(right1)).and(left1.le(right2.cast(left1)));
      }
   }

   static {
      CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_OVERLAPS};
   }

   private class Native extends AbstractCondition {
      private static final long serialVersionUID = -1552476981094856727L;

      private Native() {
      }

      public final void accept(Context<?> ctx) {
         ctx.sql('(').visit(RowOverlapsCondition.this.left).sql(' ').keyword("overlaps").sql(' ').visit(RowOverlapsCondition.this.right).sql(')');
      }

      public final Clause[] clauses(Context<?> ctx) {
         return RowOverlapsCondition.CLAUSES;
      }

      // $FF: synthetic method
      Native(Object x1) {
         this();
      }
   }
}
