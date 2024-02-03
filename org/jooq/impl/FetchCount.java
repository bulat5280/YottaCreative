package org.jooq.impl;

import java.sql.ResultSetMetaData;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Record1;
import org.jooq.Select;
import org.jooq.SelectField;

final class FetchCount extends AbstractResultQuery<Record1<Integer>> {
   private static final long serialVersionUID = -1093806354311260458L;
   private final Field<?>[] count = new Field[]{DSL.count().as("c")};
   private final Select<?> query;

   FetchCount(Configuration configuration, Select<?> query) {
      super(configuration);
      this.query = query;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.delegate(ctx.configuration()));
   }

   private final QueryPart delegate(Configuration configuration) {
      switch(configuration.family()) {
      default:
         return DSL.select((SelectField[])this.count).from(this.query.asTable("q"));
      }
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   public final Class<? extends Record1<Integer>> getRecordType() {
      return RecordImpl.class;
   }

   protected final Field<?>[] getFields(ResultSetMetaData rs) {
      return this.count;
   }

   final boolean isForUpdate() {
      return false;
   }
}
