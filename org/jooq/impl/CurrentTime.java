package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.Field;

final class CurrentTime<T> extends AbstractFunction<T> {
   private static final long serialVersionUID = -7273879239726265322L;

   CurrentTime(DataType<T> type) {
      super("current_time", type);
   }

   final Field<T> getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case DERBY:
      case FIREBIRD:
      case HSQLDB:
      case POSTGRES:
      case SQLITE:
         return DSL.field("{current_time}", this.getDataType());
      default:
         return DSL.function("current_time", this.getDataType());
      }
   }
}
