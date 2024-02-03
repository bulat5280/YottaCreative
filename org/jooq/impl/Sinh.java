package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Configuration;
import org.jooq.Field;

final class Sinh extends AbstractFunction<BigDecimal> {
   private static final long serialVersionUID = -7273879239726265322L;
   private final Field<? extends Number> argument;

   Sinh(Field<? extends Number> argument) {
      super("sinh", SQLDataType.NUMERIC, argument);
      this.argument = argument;
   }

   final Field<BigDecimal> getFunction0(Configuration configuration) {
      switch(configuration.family()) {
      case CUBRID:
      case HSQLDB:
      case MARIADB:
      case MYSQL:
      case POSTGRES:
         return DSL.exp(this.argument.mul((Field)DSL.two())).sub((Field)DSL.one()).div(DSL.exp(this.argument).mul((Field)DSL.two()));
      default:
         return DSL.function("sinh", SQLDataType.NUMERIC, this.argument);
      }
   }
}
