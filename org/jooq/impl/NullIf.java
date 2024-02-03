package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.QueryPart;

public class NullIf<T> extends AbstractFunction<T> {
   private static final long serialVersionUID = 409629290052619844L;
   private final Field<T> arg1;
   private final Field<T> arg2;

   NullIf(Field<T> arg1, Field<T> arg2) {
      super("nullif", arg1.getDataType(), arg1, arg2);
      this.arg1 = arg1;
      this.arg2 = arg2;
   }

   final QueryPart getFunction0(Configuration configuration) {
      switch(configuration.dialect().family()) {
      default:
         return DSL.field("{nullif}({0}, {1})", this.getDataType(), this.arg1, this.arg2);
      }
   }
}
