package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Record1;
import org.jooq.Table;

final class GenerateSeries extends AbstractTable<Record1<Integer>> {
   private static final long serialVersionUID = 2385574114457239818L;
   private final Field<Integer> from;
   private final Field<Integer> to;

   GenerateSeries(Field<Integer> from, Field<Integer> to) {
      super("generate_series");
      this.from = from;
      this.to = to;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.delegate(ctx.configuration()));
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   private final QueryPart delegate(Configuration configuration) {
      switch(configuration.family()) {
      case CUBRID:
         Field<Integer> level = this.from.add(DSL.level()).sub((Field)DSL.one());
         return DSL.table("({select} {0} {as} {1} {from} {2} {connect by} {level} <= {3})", level, DSL.name("generate_series"), new Dual(), this.to.add((Field)DSL.one()).sub(this.from));
      case POSTGRES:
      default:
         return DSL.table("{generate_series}({0}, {1})", this.from, this.to);
      }
   }

   public final Class<? extends Record1<Integer>> getRecordType() {
      return RecordImpl.class;
   }

   public final Table<Record1<Integer>> as(String alias) {
      return new TableAlias(this, alias);
   }

   public final Table<Record1<Integer>> as(String alias, String... fieldAliases) {
      return new TableAlias(this, alias, fieldAliases);
   }

   final Fields<Record1<Integer>> fields0() {
      return new Fields(new Field[]{DSL.field(DSL.name("generate_series"), Integer.class)});
   }
}
