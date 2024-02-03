package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;

final class FalseCondition extends AbstractCondition {
   private static final long serialVersionUID = -3972466479081463547L;
   private static final Clause[] CLAUSES;

   public final void accept(Context<?> ctx) {
      ctx.sql("1 = 0");
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_COMPARISON};
   }
}
