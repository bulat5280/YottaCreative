package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.CreateIndexStep;
import org.jooq.CreateIndexWhereStep;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.SortField;
import org.jooq.Table;

final class CreateIndexImpl extends AbstractQuery implements CreateIndexStep, CreateIndexWhereStep {
   private static final long serialVersionUID = 8904572826501186329L;
   private static final Clause[] CLAUSES;
   private final Name index;
   private final boolean unique;
   private final boolean ifNotExists;
   private Table<?> table;
   private Field<?>[] fields;
   private SortField<?>[] sortFields;
   private Condition where;

   CreateIndexImpl(Configuration configuration, Name index, boolean unique, boolean ifNotExists) {
      super(configuration);
      this.index = index;
      this.unique = unique;
      this.ifNotExists = ifNotExists;
   }

   public final CreateIndexImpl on(Table<?> t, SortField<?>... f) {
      this.table = t;
      this.sortFields = f;
      return this;
   }

   public final CreateIndexImpl on(Table<?> t, Field<?>... f) {
      this.table = t;
      this.fields = f;
      return this;
   }

   public final CreateIndexImpl on(String tableName, String... fieldNames) {
      Field<?>[] f = new Field[fieldNames.length];

      for(int i = 0; i < f.length; ++i) {
         f[i] = DSL.field(DSL.name(fieldNames[i]));
      }

      return this.on(DSL.table(DSL.name(tableName)), f);
   }

   public final CreateIndexImpl where(Condition... conditions) {
      this.where = DSL.and(conditions);
      return this;
   }

   public final CreateIndexImpl where(Collection<? extends Condition> conditions) {
      this.where = DSL.and(conditions);
      return this;
   }

   public final CreateIndexImpl where(Field<Boolean> field) {
      return this.where(DSL.condition(field));
   }

   public final CreateIndexImpl where(SQL sql) {
      return this.where(DSL.condition(sql));
   }

   public final CreateIndexImpl where(String sql) {
      return this.where(DSL.condition(sql));
   }

   public final CreateIndexImpl where(String sql, Object... bindings) {
      return this.where(DSL.condition(sql, bindings));
   }

   public final CreateIndexImpl where(String sql, QueryPart... parts) {
      return this.where(DSL.condition(sql, parts));
   }

   private final boolean supportsIfNotExists(Context<?> ctx) {
      return !Arrays.asList(SQLDialect.DERBY, SQLDialect.FIREBIRD).contains(ctx.family());
   }

   public final void accept(Context<?> ctx) {
      if (this.ifNotExists && !this.supportsIfNotExists(ctx)) {
         Tools.executeImmediateBegin(ctx, DDLStatementType.CREATE_INDEX);
         this.accept0(ctx);
         Tools.executeImmediateEnd(ctx, DDLStatementType.CREATE_INDEX);
      } else {
         this.accept0(ctx);
      }

   }

   private final void accept0(Context<?> ctx) {
      ctx.keyword("create");
      if (this.unique) {
         ctx.sql(' ').keyword("unique");
      }

      ctx.sql(' ').keyword("index").sql(' ');
      if (this.ifNotExists && this.supportsIfNotExists(ctx)) {
         ctx.keyword("if not exists").sql(' ');
      }

      ctx.visit(this.index).sql(' ').keyword("on").sql(' ').visit(this.table).sql('(').qualify(false).visit(this.fields != null ? new QueryPartList(this.fields) : new QueryPartList(this.sortFields)).qualify(true).sql(')');
      if (this.where != null) {
         ctx.sql(' ').keyword("where").sql(' ').qualify(false).visit(this.where).qualify(true);
      }

   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.CREATE_INDEX};
   }
}
