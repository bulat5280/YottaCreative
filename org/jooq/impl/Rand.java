package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.Field;

final class Rand extends AbstractFunction<BigDecimal> {
   private static final long serialVersionUID = -7273879239726265322L;

   Rand() {
      super("rand", SQLDataType.NUMERIC);
   }

   final Field<BigDecimal> getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case DERBY:
      case POSTGRES:
      case SQLITE:
         return DSL.function("random", SQLDataType.NUMERIC);
      default:
         return DSL.function("rand", SQLDataType.NUMERIC);
      }
   }
}
