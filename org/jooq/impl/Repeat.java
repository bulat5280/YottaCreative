package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;

final class Repeat extends AbstractFunction<String> {
   private static final long serialVersionUID = -7273879239726265322L;
   private final Field<String> string;
   private final Field<? extends Number> count;

   Repeat(Field<String> string, Field<? extends Number> count) {
      super("rpad", SQLDataType.VARCHAR, string, count);
      this.string = string;
      this.count = count;
   }

   final Field<String> getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case FIREBIRD:
         return DSL.rpad(this.string, DSL.length(this.string).mul(this.count), this.string);
      case SQLITE:
         return DSL.field("replace(substr(quote(zeroblob(({0} + 1) / 2)), 3, {0}), '0', {1})", String.class, this.count, this.string);
      default:
         return DSL.function("repeat", SQLDataType.VARCHAR, this.string, this.count);
      }
   }
}
