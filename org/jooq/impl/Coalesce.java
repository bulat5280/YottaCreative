package org.jooq.impl;

import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;

final class Coalesce<T> extends AbstractField<T> {
   private static final long serialVersionUID = -4546488210418866103L;
   private final Field<T>[] fields;

   Coalesce(DataType<T> dataType, Field<?>[] fields) {
      super("coalesce", dataType);
      this.fields = (Field[])fields;
   }

   public final void accept(Context<?> ctx) {
      switch(ctx.family()) {
      default:
         ctx.visit(DSL.function("coalesce", this.getDataType(), this.fields));
      }
   }
}
