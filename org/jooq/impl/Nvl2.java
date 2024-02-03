package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;

final class Nvl2<T> extends AbstractFunction<T> {
   private static final long serialVersionUID = -7273879239726265322L;
   private final Field<?> arg1;
   private final Field<T> arg2;
   private final Field<T> arg3;

   Nvl2(Field<?> arg1, Field<T> arg2, Field<T> arg3) {
      super("nvl2", arg2.getDataType(), arg1, arg2, arg3);
      this.arg1 = arg1;
      this.arg2 = arg2;
      this.arg3 = arg3;
   }

   final Field<T> getFunction0(Configuration configuration) {
      switch(configuration.dialect().family()) {
      case H2:
      case HSQLDB:
         return DSL.field("{nvl2}({0}, {1}, {2})", this.getDataType(), this.arg1, this.arg2, this.arg3);
      default:
         return DSL.when(this.arg1.isNotNull(), this.arg2).otherwise(this.arg3);
      }
   }
}
