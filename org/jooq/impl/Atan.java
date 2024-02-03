package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

final class Atan extends AbstractFunction<BigDecimal> {
   private static final long serialVersionUID = 3117002829857089691L;
   private final Field<? extends Number> arg;

   Atan(Field<? extends Number> arg) {
      super("atan", SQLDataType.NUMERIC);
      this.arg = arg;
   }

   final QueryPart getFunction0(Configuration configuration) {
      switch(configuration.dialect().family()) {
      default:
         return DSL.field("{atan}({0})", this.getDataType(), this.arg);
      }
   }
}
