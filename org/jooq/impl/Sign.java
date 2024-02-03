package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;

final class Sign extends AbstractFunction<Integer> {
   private static final long serialVersionUID = -7273879239726265322L;
   private final Field<?> argument;

   Sign(Field<?> argument) {
      super("sign", SQLDataType.INTEGER, argument);
      this.argument = argument;
   }

   final Field<Integer> getFunction0(Configuration configuration) {
      switch(configuration.dialect()) {
      case SQLITE:
         return DSL.when(this.argument.greaterThan((Field)DSL.zero()), (Field)DSL.one()).when(this.argument.lessThan((Field)DSL.zero()), DSL.one().neg()).otherwise((Field)DSL.zero());
      default:
         return DSL.function("sign", this.getDataType(), this.argument);
      }
   }
}
