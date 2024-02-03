package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.QueryPart;

abstract class AbstractFunction<T> extends AbstractField<T> {
   private static final long serialVersionUID = 8771262868110746571L;
   private final Field<?>[] arguments;

   AbstractFunction(String name, DataType<T> type, Field<?>... arguments) {
      super(name, type);
      this.arguments = arguments;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.getFunction0(ctx.configuration()));
   }

   final Field<?>[] getArguments() {
      return this.arguments;
   }

   abstract QueryPart getFunction0(Configuration var1);
}
