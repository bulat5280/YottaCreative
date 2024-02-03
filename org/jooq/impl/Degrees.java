package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.Field;

final class Degrees extends AbstractFunction<BigDecimal> {
   private static final long serialVersionUID = -7273879239726265322L;
   private final Field<?> argument;

   Degrees(Field<?> argument) {
      super("degrees", SQLDataType.NUMERIC, argument);
      this.argument = argument;
   }

   final Field<BigDecimal> getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case FIREBIRD:
      case SQLITE:
         return this.argument.cast(BigDecimal.class).mul((Field)DSL.inline((int)180)).div(DSL.pi());
      default:
         return DSL.field("{degrees}({0})", SQLDataType.NUMERIC, this.argument);
      }
   }
}
