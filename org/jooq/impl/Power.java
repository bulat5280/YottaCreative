package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.QueryPart;

final class Power extends AbstractFunction<BigDecimal> {
   private static final long serialVersionUID = -7273879239726265322L;
   private final Field<? extends Number> arg1;
   private final Field<? extends Number> arg2;

   Power(Field<? extends Number> arg1, Field<? extends Number> arg2) {
      super("power", SQLDataType.NUMERIC, arg1, arg2);
      this.arg1 = arg1;
      this.arg2 = arg2;
   }

   final Field<BigDecimal> getFunction0(Configuration configuration) {
      switch(configuration.dialect().family()) {
      case DERBY:
      case SQLITE:
         return DSL.exp(DSL.ln(this.arg1).mul(this.arg2));
      default:
         return DSL.field("{power}({0}, {1})", (DataType)SQLDataType.NUMERIC, (QueryPart[])this.getArguments());
      }
   }
}
