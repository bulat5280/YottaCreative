package org.jooq.impl;

import org.jooq.Comparator;
import org.jooq.Context;
import org.jooq.Record;
import org.jooq.Table;

final class TableComparison<R extends Record> extends AbstractCondition {
   private static final long serialVersionUID = 7033304130029726093L;
   private final Table<R> lhs;
   private final Table<R> rhs;
   private final Comparator comparator;

   TableComparison(Table<R> lhs, Table<R> rhs, Comparator comparator) {
      this.lhs = lhs;
      this.rhs = rhs;
      this.comparator = comparator;
   }

   public final void accept(Context<?> ctx) {
      switch(ctx.family()) {
      case POSTGRES:
         ctx.visit(DSL.condition("{0} {1} {2}", this.lhs, DSL.sql(this.comparator.toSQL()), this.rhs));
         break;
      default:
         ctx.visit(DSL.row(this.lhs.fields()).compare(this.comparator, DSL.row(this.rhs.fields())));
      }

   }
}
