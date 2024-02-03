package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;

final class Ascii extends AbstractFunction<Integer> {
   private static final long serialVersionUID = -7273879239726265322L;
   private final Field<?> string;

   Ascii(Field<?> string) {
      super("ascii", SQLDataType.INTEGER, string);
      this.string = string;
   }

   final Field<Integer> getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case FIREBIRD:
         return DSL.field("{ascii_val}({0})", SQLDataType.INTEGER, this.string);
      case DERBY:
      case SQLITE:
      default:
         return DSL.field("{ascii}({0})", SQLDataType.INTEGER, this.string);
      }
   }
}
