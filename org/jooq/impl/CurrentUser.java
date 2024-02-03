package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;

final class CurrentUser extends AbstractFunction<String> {
   private static final long serialVersionUID = -7273879239726265322L;

   CurrentUser() {
      super("current_user", SQLDataType.VARCHAR);
   }

   final Field<String> getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case DERBY:
      case FIREBIRD:
      case HSQLDB:
      case POSTGRES:
         return DSL.field("{current_user}", String.class);
      case SQLITE:
         return DSL.inline("");
      default:
         return DSL.function("current_user", SQLDataType.VARCHAR);
      }
   }
}
