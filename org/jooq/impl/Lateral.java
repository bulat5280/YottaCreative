package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Record;
import org.jooq.Table;

final class Lateral<R extends Record> extends AbstractTable<R> {
   private static final long serialVersionUID = -3665347156501299297L;
   private final Table<R> table;

   Lateral(Table<R> table) {
      super(table.getName(), table.getSchema());
      this.table = table;
   }

   public final boolean declaresTables() {
      return true;
   }

   public final Class<? extends R> getRecordType() {
      return this.table.getRecordType();
   }

   public final Table<R> as(String alias) {
      return new Lateral(this.table.as(alias));
   }

   public final Table<R> as(String alias, String... fieldAliases) {
      return new Lateral(this.table.as(alias, fieldAliases));
   }

   public final void accept(Context<?> ctx) {
      ctx.keyword("lateral").sql(' ').visit(this.table);
   }

   final Fields<R> fields0() {
      return new Fields(this.table.fields());
   }
}
