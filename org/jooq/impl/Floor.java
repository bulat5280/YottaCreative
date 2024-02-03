package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;

final class Floor<T extends Number> extends AbstractFunction<T> {
   private static final long serialVersionUID = -7273879239726265322L;
   private final Field<T> argument;

   Floor(Field<T> argument) {
      super("floor", argument.getDataType(), argument);
      this.argument = argument;
   }

   final Field<T> getFunction0(Configuration configuration) {
      switch(configuration.dialect()) {
      case SQLITE:
         return DSL.round(this.argument.sub((Number)0.499999999999999D));
      default:
         return DSL.field("{floor}({0})", this.getDataType(), this.argument);
      }
   }
}
