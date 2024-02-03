package org.jooq.impl;

import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;

final class BoolOr extends Function<Boolean> {
   private static final long serialVersionUID = 7292087943334025737L;
   private final Condition condition;

   BoolOr(Condition condition) {
      super("bool_or", SQLDataType.BOOLEAN, DSL.field(condition));
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
               c.visit(DSL.max(DSL.when(BoolOr.this.condition, (Field)DSL.one()).otherwise((Field)DSL.zero())));
               BoolOr.this.toSQLOverClause(c);
            }
         });
         ctx.visit(DSL.when(max.eq((Field)DSL.one()), (Field)DSL.inline(true)).otherwise((Field)DSL.inline(false)));
      }

   }
}
