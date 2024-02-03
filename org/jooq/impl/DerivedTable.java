package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.Table;

final class DerivedTable<R extends Record> extends AbstractTable<R> {
   private static final long serialVersionUID = 6272398035926615668L;
   private final Select<R> query;

   DerivedTable(Select<R> query) {
      super("select");
      this.query = query;
   }

   final Select<R> query() {
      return this.query;
   }

   public final Table<R> as(String alias) {
      return new TableAlias(this, alias, true);
   }

   public final Table<R> as(String alias, String... fieldAliases) {
      return new TableAlias(this, alias, fieldAliases, true);
   }

   final Fields<R> fields0() {
      return new Fields(this.query.getSelect());
   }

   public final Class<? extends R> getRecordType() {
      return this.query.getRecordType();
   }

   public final void accept(Context<?> ctx) {
      boolean subquery = ctx.subquery();
      ctx.subquery(true).formatIndentStart().formatNewLine().visit(this.query).formatIndentEnd().formatNewLine().subquery(subquery);
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }
}
