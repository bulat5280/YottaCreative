package org.jooq.impl;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.CreateViewAsStep;
import org.jooq.CreateViewFinalStep;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.conf.ParamType;

final class CreateViewImpl<R extends Record> extends AbstractQuery implements CreateViewAsStep<R>, CreateViewFinalStep {
   private static final long serialVersionUID = 8904572826501186329L;
   private static final Clause[] CLAUSES;
   private final boolean ifNotExists;
   private final Table<?> view;
   private final BiFunction<? super Field<?>, ? super Integer, ? extends Field<?>> fieldNameFunction;
   private Field<?>[] fields;
   private Select<?> select;

   CreateViewImpl(Configuration configuration, Table<?> view, Field<?>[] fields, boolean ifNotExists) {
      super(configuration);
      this.view = view;
      this.fields = fields;
      this.fieldNameFunction = null;
      this.ifNotExists = ifNotExists;
   }

   CreateViewImpl(Configuration configuration, Table<?> view, BiFunction<? super Field<?>, ? super Integer, ? extends Field<?>> fieldNameFunction, boolean ifNotExists) {
      super(configuration);
      this.view = view;
      this.fields = null;
      this.fieldNameFunction = fieldNameFunction;
      this.ifNotExists = ifNotExists;
   }

   public final CreateViewFinalStep as(Select<? extends R> s) {
      this.select = s;
      if (this.fieldNameFunction != null) {
         List<Field<?>> source = s.getSelect();
         this.fields = new Field[source.size()];

         for(int i = 0; i < this.fields.length; ++i) {
            this.fields[i] = (Field)this.fieldNameFunction.apply(source.get(i), i);
         }
      }

      return this;
   }

   private final boolean supportsIfNotExists(Context<?> ctx) {
      return !Arrays.asList(SQLDialect.DERBY, SQLDialect.FIREBIRD).contains(ctx.family());
   }

   public final void accept(Context<?> ctx) {
      if (this.ifNotExists && !this.supportsIfNotExists(ctx)) {
         Tools.executeImmediateBegin(ctx, DDLStatementType.CREATE_VIEW);
         this.accept0(ctx);
         Tools.executeImmediateEnd(ctx, DDLStatementType.CREATE_VIEW);
      } else {
         this.accept0(ctx);
      }

   }

   private final void accept0(Context<?> ctx) {
      boolean rename = this.fields != null && this.fields.length > 0;
      boolean renameSupported = ctx.family() != SQLDialect.SQLITE;
      ParamType paramType = ctx.paramType();
      ctx.start(Clause.CREATE_VIEW_NAME).keyword("create view").sql(' ');
      if (this.ifNotExists && this.supportsIfNotExists(ctx)) {
         ctx.keyword("if not exists").sql(' ');
      }

      ctx.visit(this.view);
      if (rename && renameSupported) {
         boolean qualify = ctx.qualify();
         ctx.sql('(').qualify(false).visit(new QueryPartList(this.fields)).qualify(qualify).sql(')');
      }

      ctx.end(Clause.CREATE_VIEW_NAME).formatSeparator().keyword("as").formatSeparator().start(Clause.CREATE_VIEW_AS).paramType(ParamType.INLINED).visit((QueryPart)(rename && !renameSupported ? DSL.selectFrom(DSL.table(this.select).as("t", Tools.fieldNames(this.fields))) : this.select)).paramType(paramType).end(Clause.CREATE_VIEW_AS);
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.CREATE_VIEW};
   }
}
