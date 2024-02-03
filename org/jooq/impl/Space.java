package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

final class Space extends AbstractFunction<String> {
   private static final long serialVersionUID = -4239524454814412161L;
   private final Field<Integer> count;

   Space(Field<Integer> count) {
      super("space", SQLDataType.VARCHAR, count);
      this.count = count;
   }

   final QueryPart getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case DERBY:
      case FIREBIRD:
      case HSQLDB:
      case POSTGRES:
      case SQLITE:
         return DSL.repeat((Field)DSL.inline(" "), this.count);
      case CUBRID:
      case MARIADB:
      case MYSQL:
      case H2:
      default:
         return DSL.field("{space}({0})", this.getDataType(), this.count);
      }
   }
}
