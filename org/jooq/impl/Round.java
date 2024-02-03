package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.Field;

final class Round<T extends Number> extends AbstractFunction<T> {
   private static final long serialVersionUID = -7273879239726265322L;
   private final Field<T> argument;
   private final int decimals;

   Round(Field<T> argument) {
      this(argument, 0);
   }

   Round(Field<T> argument, int decimals) {
      super("round", argument.getDataType(), argument);
      this.argument = argument;
      this.decimals = decimals;
   }

   final Field<T> getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case DERBY:
         if (this.decimals == 0) {
            return DSL.when(this.argument.sub(DSL.floor(this.argument)).lessThan((Object)0.5D), DSL.floor(this.argument)).otherwise(DSL.ceil(this.argument));
         }

         Field<BigDecimal> factor = DSL.val(BigDecimal.ONE.movePointRight(this.decimals));
         Field<T> mul = this.argument.mul((Field)factor);
         return DSL.when(mul.sub(DSL.floor(mul)).lessThan((Object)0.5D), DSL.floor(mul).div((Field)factor)).otherwise(DSL.ceil(mul).div((Field)factor));
      case POSTGRES:
         if (this.decimals == 0) {
            return DSL.function("round", this.getDataType(), this.argument);
         }

         return DSL.function("round", this.getDataType(), this.argument.cast(BigDecimal.class), DSL.val(this.decimals));
      default:
         return this.decimals == 0 ? DSL.function("round", this.getDataType(), this.argument) : DSL.function("round", this.getDataType(), this.argument, DSL.val(this.decimals));
      }
   }
}
