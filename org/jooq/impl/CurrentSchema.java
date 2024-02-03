package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;

final class CurrentSchema extends AbstractFunction<String> {
   private static final long serialVersionUID = -7273879239726265322L;

   CurrentSchema() {
      super("current_schema", SQLDataType.VARCHAR);
   }

   final Field<String> getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case CUBRID:
      case FIREBIRD:
      case SQLITE:
         return DSL.inline("");
      case DERBY:
         return DSL.field("{current schema}", String.class);
      case H2:
         return DSL.field("{schema}()", String.class);
      case MARIADB:
      case MYSQL:
         return DSL.field("{database}()", String.class);
      case HSQLDB:
      case POSTGRES:
         return DSL.field("{current_schema}", String.class);
      default:
         return DSL.function("current_schema", String.class);
      }
   }
}
