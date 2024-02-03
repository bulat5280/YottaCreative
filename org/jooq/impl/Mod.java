package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;

final class Mod<T> extends AbstractFunction<T> {
   private static final long serialVersionUID = -7273879239726265322L;
   private final Field<T> arg1;
   private final Field<? extends Number> arg2;

   Mod(Field<T> arg1, Field<? extends Number> arg2) {
      super("mod", arg1.getDataType(), arg1, arg2);
      this.arg1 = arg1;
      this.arg2 = arg2;
   }

   final Field<T> getFunction0(Configuration configuration) {
      switch(configuration.dialect().family()) {
      case SQLITE:
         return new Expression(ExpressionOperator.MODULO, this.arg1, new Field[]{this.arg2});
      default:
         return DSL.function("mod", this.getDataType(), this.arg1, this.arg2);
      }
   }
}
