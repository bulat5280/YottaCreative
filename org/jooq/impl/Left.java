package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

final class Left extends AbstractFunction<String> {
   private static final long serialVersionUID = 2200760781944082146L;
   private Field<String> field;
   private Field<? extends Number> length;

   Left(Field<String> field, Field<? extends Number> length) {
      super("left", field.getDataType());
      this.field = field;
      this.length = length;
   }

   final QueryPart getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case DERBY:
      case SQLITE:
         return DSL.substring(this.field, DSL.inline((int)1), this.length);
      case CUBRID:
      case FIREBIRD:
      case H2:
      case HSQLDB:
      case MARIADB:
      case MYSQL:
      case POSTGRES:
      default:
         return DSL.field("{left}({0}, {1})", this.field, this.length);
      }
   }
}
