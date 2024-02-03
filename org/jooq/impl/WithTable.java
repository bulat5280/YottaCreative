package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Record;
import org.jooq.Table;

final class WithTable<R extends Record> extends AbstractTable<R> {
   private static final long serialVersionUID = -3905775637768497535L;
   private final AbstractTable<R> delegate;
   private final String hint;

   WithTable(AbstractTable<R> delegate, String hint) {
      super(delegate.getName(), delegate.getSchema());
      this.delegate = delegate;
      this.hint = hint;
   }

   public final boolean declaresTables() {
      return true;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.delegate).sql(' ').keyword("with").sql(" (").sql(this.hint).sql(')');
   }

   public final Class<? extends R> getRecordType() {
      return this.delegate.getRecordType();
   }

   public final Table<R> as(String alias) {
      return new WithTable(new TableAlias(this.delegate, alias), this.hint);
   }

   public final Table<R> as(String alias, String... fieldAliases) {
      return new WithTable(new TableAlias(this.delegate, alias, fieldAliases), this.hint);
   }

   final Fields<R> fields0() {
      return this.delegate.fields0();
   }
}
