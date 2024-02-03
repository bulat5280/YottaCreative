package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.Field;

final class Least<T> extends AbstractFunction<T> {
   private static final long serialVersionUID = -7273879239726265322L;

   Least(DataType<T> type, Field<?>... arguments) {
      super("least", type, arguments);
   }

   final Field<T> getFunction0(Configuration configuration) {
      if (this.getArguments().length == 1) {
         return this.getArguments()[0];
      } else {
         switch(configuration.family()) {
         case DERBY:
            Field<T> first = this.getArguments()[0];
            Field<T> other = this.getArguments()[1];
            if (this.getArguments().length > 2) {
               Field<?>[] remaining = new Field[this.getArguments().length - 2];
               System.arraycopy(this.getArguments(), 2, remaining, 0, remaining.length);
               return DSL.when(first.lessThan(other), DSL.least(first, remaining)).otherwise(DSL.least(other, remaining));
            }

            return DSL.when(first.lessThan(other), first).otherwise(other);
         case FIREBIRD:
            return DSL.function("minvalue", this.getDataType(), this.getArguments());
         case SQLITE:
            return DSL.function("min", this.getDataType(), this.getArguments());
         default:
            return DSL.function("least", this.getDataType(), this.getArguments());
         }
      }
   }
}
