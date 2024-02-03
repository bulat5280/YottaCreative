package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Table;

final class SQLTable extends AbstractTable<Record> {
   private static final long serialVersionUID = -5122023013463718796L;
   private final QueryPart delegate;

   SQLTable(QueryPart delegate) {
      super(delegate.toString());
      this.delegate = delegate;
   }

   public final Class<? extends Record> getRecordType() {
      return RecordImpl.class;
   }

   public final Table<Record> as(String alias) {
      return new TableAlias(this, alias);
   }

   public final Table<Record> as(String alias, String... fieldAliases) {
      return new TableAlias(this, alias, fieldAliases);
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.delegate);
   }

   final Fields<Record> fields0() {
      return new Fields(new Field[0]);
   }
}
