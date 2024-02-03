package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;

final class Prior<T> extends AbstractField<T> {
   private static final long serialVersionUID = 4532570030471782063L;
   private final Field<T> field;

   Prior(Field<T> field) {
      super("prior", DSL.nullSafe(field).getDataType());
      this.field = field;
   }

   public final void accept(Context<?> ctx) {
      switch(ctx.family()) {
      case CUBRID:
      default:
         ctx.keyword("prior").sql(' ').visit(this.field);
      }
   }
}
