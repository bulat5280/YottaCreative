package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

final class Asin extends AbstractFunction<BigDecimal> {
   private static final long serialVersionUID = 3117002829857089691L;
   private final Field<? extends Number> arg;

   Asin(Field<? extends Number> arg) {
      super("asin", SQLDataType.NUMERIC);
      this.arg = arg;
   }

   final QueryPart getFunction0(Configuration configuration) {
      switch(configuration.dialect().family()) {
      default:
         return DSL.field("{asin}({0})", this.getDataType(), this.arg);
      }
   }
}
