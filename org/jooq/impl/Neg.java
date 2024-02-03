package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.SQLDialect;

final class Neg<T> extends AbstractField<T> {
   private static final long serialVersionUID = 7624782102883057433L;
   private final ExpressionOperator operator;
   private final Field<T> field;

   Neg(Field<T> field, ExpressionOperator operator) {
      super(operator.toSQL() + field.getName(), field.getDataType());
      this.operator = operator;
      this.field = field;
   }

   public final void accept(Context<?> ctx) {
      SQLDialect family = ctx.configuration().dialect().family();
      if (this.operator == ExpressionOperator.BIT_NOT && Arrays.asList(SQLDialect.H2, SQLDialect.HSQLDB).contains(family)) {
         ctx.sql("(0 - ").visit(this.field).sql(" - 1)");
      } else if (this.operator == ExpressionOperator.BIT_NOT && family == SQLDialect.FIREBIRD) {
         ctx.keyword("bin_not(").visit(this.field).sql(')');
      } else {
         ctx.sql(this.operator.toSQL()).sql('(').visit(this.field).sql(')');
      }

   }
}
