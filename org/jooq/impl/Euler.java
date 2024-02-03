package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.Field;

final class Euler extends AbstractFunction<BigDecimal> {
   private static final long serialVersionUID = -420788300355442056L;

   Euler() {
      super("e", SQLDataType.NUMERIC);
   }

   final Field<BigDecimal> getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case CUBRID:
      case DERBY:
      case FIREBIRD:
      case H2:
      case HSQLDB:
      case MARIADB:
      case MYSQL:
      case POSTGRES:
         return DSL.exp((Field)DSL.one());
      case SQLITE:
         return DSL.inline(2.718281828459045D, (Class)BigDecimal.class);
      default:
         return DSL.function("e", this.getDataType());
      }
   }
}
