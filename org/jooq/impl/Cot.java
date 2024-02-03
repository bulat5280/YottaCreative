package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.Field;

final class Cot extends AbstractFunction<BigDecimal> {
   private static final long serialVersionUID = -7273879239726265322L;
   private final Field<? extends Number> argument;

   Cot(Field<? extends Number> argument) {
      super("cot", SQLDataType.NUMERIC, argument);
      this.argument = argument;
   }

   final Field<BigDecimal> getFunction0(Configuration configuration) {
      switch(configuration.dialect().family()) {
      default:
         return DSL.function("cot", SQLDataType.NUMERIC, this.argument);
      }
   }
}
