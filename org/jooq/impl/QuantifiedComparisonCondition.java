package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QuantifiedSelect;

final class QuantifiedComparisonCondition extends AbstractCondition {
   private static final long serialVersionUID = -402776705884329740L;
   private static final Clause[] CLAUSES;
   private final QuantifiedSelect<?> query;
   private final Field<?> field;
   private final Comparator comparator;

   QuantifiedComparisonCondition(QuantifiedSelect<?> query, Field<?> field, Comparator comparator) {
      this.query = query;
      this.field = field;
      this.comparator = comparator;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.field).sql(' ').keyword(this.comparator.toSQL()).sql(' ').visit(this.query);
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_BETWEEN};
   }
}
