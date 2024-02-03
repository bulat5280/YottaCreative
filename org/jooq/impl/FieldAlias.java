package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;

final class FieldAlias<T> extends AbstractField<T> {
   private static final long serialVersionUID = -85277321749681553L;
   private final Alias<Field<T>> alias;

   FieldAlias(Field<T> field, String alias) {
      super(alias, field.getDataType());
      this.alias = new Alias(field, alias, false);
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.alias);
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   public final Field<T> as(String as) {
      return ((Field)this.alias.wrapped()).as(as);
   }

   public final boolean declaresFields() {
      return true;
   }
}
