package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Table;

final class QualifiedTable extends AbstractTable<Record> {
   private static final long serialVersionUID = 6937002867156868761L;
   private static final Clause[] CLAUSES;
   private final Name name;

   QualifiedTable(Name name) {
      super(name.getName()[name.getName().length - 1]);
      this.name = name;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.name);
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
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

   final Fields<Record> fields0() {
      return new Fields(new Field[0]);
   }

   static {
      CLAUSES = new Clause[]{Clause.TABLE, Clause.TABLE_REFERENCE};
   }
}
