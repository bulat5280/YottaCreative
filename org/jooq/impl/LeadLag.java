package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;

final class LeadLag<T> extends Function<T> {
   private static final long serialVersionUID = 7292087943334025737L;
   private final String function;
   private final Field<T> field;
   private final int offset;
   private final Field<T> defaultValue;

   LeadLag(String function, Field<T> field) {
      super(function, field.getDataType(), field);
      this.function = function;
      this.field = field;
      this.offset = 0;
      this.defaultValue = null;
   }

   LeadLag(String function, Field<T> field, int offset) {
      super(function, field.getDataType(), field, DSL.inline(offset));
      this.function = function;
      this.field = field;
      this.offset = offset;
      this.defaultValue = null;
   }

   LeadLag(String function, Field<T> field, int offset, Field<T> defaultValue) {
      super(function, field.getDataType(), field, DSL.inline(offset), defaultValue);
      this.function = function;
      this.field = field;
      this.offset = offset;
      this.defaultValue = defaultValue;
   }

   public final void accept(Context<?> ctx) {
      if (this.defaultValue == null) {
         super.accept(ctx);
      } else {
         switch(ctx.family()) {
         default:
            super.accept(ctx);
         }
      }

   }
}
