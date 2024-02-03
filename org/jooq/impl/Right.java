package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

final class Right extends AbstractFunction<String> {
   private static final long serialVersionUID = 2200760781944082146L;
   private Field<String> field;
   private Field<? extends Number> length;

   Right(Field<String> field, Field<? extends Number> length) {
      super("right", field.getDataType());
      this.field = field;
      this.length = length;
   }

   final QueryPart getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case DERBY:
         return DSL.substring(this.field, this.field.length().add((Field)DSL.one()).sub(this.length));
      case SQLITE:
         return DSL.substring(this.field, this.length.neg());
      case CUBRID:
      case FIREBIRD:
      case H2:
      case HSQLDB:
      case MARIADB:
      case MYSQL:
      case POSTGRES:
      default:
         return DSL.field("{right}({0}, {1})", this.field, this.length);
      }
   }
}
