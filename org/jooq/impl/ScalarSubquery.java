package org.jooq.impl;

import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Select;

final class ScalarSubquery<T> extends AbstractField<T> {
   private static final long serialVersionUID = 3463144434073231750L;
   private final Select<?> query;

   ScalarSubquery(Select<?> query, DataType<T> type) {
      super("select", type);
      this.query = query;
   }

   public final Field<T> as(String alias) {
      return new FieldAlias(this, alias);
   }

   public final void accept(Context<?> ctx) {
      boolean subquery = ctx.subquery();
      ctx.sql('(').subquery(true).formatIndentStart().formatNewLine().visit(this.query).formatIndentEnd().formatNewLine().subquery(subquery).sql(')');
   }
}
