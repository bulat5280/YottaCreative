package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.Field;

final class Sqrt extends AbstractFunction<BigDecimal> {
   private static final long serialVersionUID = -7273879239726265322L;
   private final Field<? extends Number> argument;

   Sqrt(Field<? extends Number> argument) {
      super("sqrt", SQLDataType.NUMERIC, argument);
      this.argument = argument;
   }

   final Field<BigDecimal> getFunction0(Configuration configuration) {
      switch(configuration.dialect().family()) {
      case SQLITE:
         return DSL.power((Field)this.argument, (Number)0.5D);
      default:
         return DSL.field("{sqrt}({0})", SQLDataType.NUMERIC, this.argument);
      }
   }
}
