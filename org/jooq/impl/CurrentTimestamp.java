package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.Field;

final class CurrentTimestamp<T> extends AbstractFunction<T> {
   private static final long serialVersionUID = -7273879239726265322L;

   CurrentTimestamp(DataType<T> type) {
      super("current_timestamp", type);
   }

   final Field<T> getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case DERBY:
      case FIREBIRD:
      case HSQLDB:
      case POSTGRES:
      case SQLITE:
         return DSL.field("{current_timestamp}", this.getDataType());
      default:
         return DSL.function("current_timestamp", this.getDataType());
      }
   }
}
