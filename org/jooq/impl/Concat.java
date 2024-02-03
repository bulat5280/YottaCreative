package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;

final class Concat extends AbstractFunction<String> {
   private static final long serialVersionUID = -7273879239726265322L;

   Concat(Field<?>... arguments) {
      super("concat", SQLDataType.VARCHAR, arguments);
   }

   final Field<String> getFunction0(Configuration configuration) {
      Field<String>[] cast = DSL.castAll(String.class, this.getArguments());
      if (cast.length == 1) {
         return cast[0];
      } else {
         Field<String> first = cast[0];
         Field<String>[] others = new Field[cast.length - 1];
         System.arraycopy(cast, 1, others, 0, others.length);
         switch(configuration.family()) {
         case MARIADB:
         case MYSQL:
            return DSL.function("concat", SQLDataType.VARCHAR, cast);
         default:
            return new Expression(ExpressionOperator.CONCAT, first, others);
         }
      }
   }
}
