package org.jooq.impl;

import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;

final class Coerce<T> extends AbstractField<T> {
   private static final long serialVersionUID = -6776617606751542856L;
   private final Field<?> field;

   public Coerce(Field<?> field, DataType<T> type) {
      super(field.getName(), type);
      this.field = field instanceof Coerce ? ((Coerce)field).field : field;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.field);
   }
}
