package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;

final class Ceil<T extends Number> extends AbstractFunction<T> {
   private static final long serialVersionUID = -7273879239726265322L;
   private final Field<T> argument;

   Ceil(Field<T> argument) {
      super("ceil", argument.getDataType(), argument);
      this.argument = argument;
   }

   final Field<T> getFunction0(Configuration configuration) {
      switch(configuration.dialect().family()) {
      case SQLITE:
         return DSL.round(this.argument.add((Number)0.499999999999999D));
      case H2:
         return DSL.field("{ceiling}({0})", this.getDataType(), this.argument);
      default:
         return DSL.field("{ceil}({0})", this.getDataType(), this.argument);
      }
   }
}
