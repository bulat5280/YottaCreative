package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Context;
import org.jooq.Field;

final class RatioToReport extends Function<BigDecimal> {
   private static final long serialVersionUID = 7292087943334025737L;
   private final Field<? extends Number> field;

   RatioToReport(Field<? extends Number> field) {
      super("ratio_to_report", SQLDataType.DECIMAL, field);
      this.field = field;
   }

   public final void accept(Context<?> ctx) {
      switch(ctx.family()) {
      default:
         ctx.visit(this.field.cast(SQLDataType.DECIMAL)).sql(" / ").visit(DSL.sum(this.field));
         this.toSQLOverClause(ctx);
      }
   }
}
