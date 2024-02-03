package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

final class Reverse extends AbstractFunction<String> {
   private static final long serialVersionUID = -3869043378872335516L;
   private final Field<String> field;

   Reverse(Field<String> field) {
      super("reverse", field.getDataType(), field);
      this.field = field;
   }

   QueryPart getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case CUBRID:
      case HSQLDB:
      case POSTGRES:
      case MARIADB:
      case MYSQL:
      default:
         return DSL.field("{reverse}({0})", this.getDataType(), this.field);
      }
   }
}
