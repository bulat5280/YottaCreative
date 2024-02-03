package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Context;

final class NotCondition extends AbstractCondition {
   private static final long serialVersionUID = 2921001862882237932L;
   private static final Clause[] CLAUSES;
   private final Condition condition;

   NotCondition(Condition condition) {
      this.condition = condition;
   }

   public final void accept(Context<?> ctx) {
      ctx.keyword("not(").visit(this.condition).sql(')');
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_NOT};
   }
}
