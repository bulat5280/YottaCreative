package org.jooq.impl;

import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;

final class BoolAnd extends Function<Boolean> {
   private static final long serialVersionUID = 7292087943334025737L;
   private final Condition condition;

   BoolAnd(Condition condition) {
      super("bool_and", SQLDataType.BOOLEAN, DSL.field(condition));
      this.condition = condition;
   }

   public final void accept(Context<?> ctx) {
      switch(ctx.family()) {
      case POSTGRES:
         super.accept(ctx);
         break;
      default:
         Field<Integer> max = DSL.field("{0}", Integer.class, new CustomQueryPart() {
            public void accept(Context<?> c) {
               c.visit(DSL.max(DSL.when(BoolAnd.this.condition, (Field)DSL.zero()).otherwise((Field)DSL.one())));
               BoolAnd.this.toSQLOverClause(c);
            }
         });
         ctx.visit(DSL.when(max.eq((Field)DSL.zero()), (Field)DSL.inline(true)).otherwise((Field)DSL.inline(false)));
      }

   }
}
