package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.Field;

final class Pi extends AbstractFunction<BigDecimal> {
   private static final long serialVersionUID = -420788300355442056L;

   Pi() {
      super("pi", SQLDataType.NUMERIC);
   }

   final Field<BigDecimal> getFunction0(Configuration configuration) {
      switch(configuration.dialect().family()) {
      case SQLITE:
         return DSL.inline(3.141592653589793D, (Class)BigDecimal.class);
      default:
         return DSL.function("pi", this.getDataType());
      }
   }
}
