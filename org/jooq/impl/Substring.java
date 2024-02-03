package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.QueryPart;

final class Substring extends AbstractFunction<String> {
   private static final long serialVersionUID = -7273879239726265322L;

   Substring(Field<?>... arguments) {
      super("substring", SQLDataType.VARCHAR, arguments);
   }

   final Field<String> getFunction0(Configuration configuration) {
      String functionName = "substring";
      switch(configuration.family()) {
      case FIREBIRD:
         if (this.getArguments().length == 2) {
            return DSL.field("{substring}({0} {from} {1})", (DataType)SQLDataType.VARCHAR, (QueryPart[])this.getArguments());
         }

         return DSL.field("{substring}({0} {from} {1} {for} {2})", (DataType)SQLDataType.VARCHAR, (QueryPart[])this.getArguments());
      case DERBY:
      case SQLITE:
         functionName = "substr";
      default:
         return DSL.function(functionName, SQLDataType.VARCHAR, this.getArguments());
      }
   }
}
