package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;

final class Nvl<T> extends AbstractFunction<T> {
   private static final long serialVersionUID = -7273879239726265322L;
   private final Field<T> arg1;
   private final Field<T> arg2;

   Nvl(Field<T> arg1, Field<T> arg2) {
      super("nvl", arg1.getDataType(), arg1, arg2);
      this.arg1 = arg1;
      this.arg2 = arg2;
   }

   final Field<T> getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case H2:
      case HSQLDB:
         return DSL.field("{nvl}({0}, {1})", this.getDataType(), this.arg1, this.arg2);
      case DERBY:
      case POSTGRES:
         return DSL.field("{coalesce}({0}, {1})", this.getDataType(), this.arg1, this.arg2);
      case MARIADB:
      case MYSQL:
      case SQLITE:
         return DSL.field("{ifnull}({0}, {1})", this.getDataType(), this.arg1, this.arg2);
      default:
         return DSL.when(this.arg1.isNotNull(), this.arg1).otherwise(this.arg2);
      }
   }
}
