package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.Field;

final class CurrentDate<T> extends AbstractFunction<T> {
   private static final long serialVersionUID = -7273879239726265322L;

   CurrentDate(DataType<T> type) {
      super("current_date", type);
   }

   final Field<T> getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case DERBY:
      case FIREBIRD:
      case HSQLDB:
      case POSTGRES:
      case SQLITE:
         return DSL.field("{current_date}", this.getDataType());
      default:
         return DSL.function("current_date", this.getDataType());
      }
   }
}
