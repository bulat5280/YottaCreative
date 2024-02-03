package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.tools.StringUtils;

public class TableImpl<R extends Record> extends AbstractTable<R> {
   private static final long serialVersionUID = 261033315221985068L;
   private static final Clause[] CLAUSES_TABLE_REFERENCE;
   private static final Clause[] CLAUSES_TABLE_ALIAS;
   final Fields<R> fields;
   final Alias<Table<R>> alias;
   protected final Field<?>[] parameters;

   public TableImpl(String name) {
      this(name, (Schema)null, (Table)null, (Field[])null, (String)null);
   }

   public TableImpl(String name, Schema schema) {
      this(name, schema, (Table)null, (Field[])null, (String)null);
   }

   public TableImpl(String name, Schema schema, Table<R> aliased) {
      this(name, schema, aliased, (Field[])null, (String)null);
   }

   public TableImpl(String name, Schema schema, Table<R> aliased, Field<?>[] parameters) {
      this(name, schema, aliased, parameters, (String)null);
   }

   public TableImpl(String name, Schema schema, Table<R> aliased, Field<?>[] parameters, String comment) {
      super(name, schema, comment);
      this.fields = new Fields(new Field[0]);
      if (aliased != null) {
         this.alias = new Alias(aliased, name);
      } else {
         this.alias = null;
      }

      this.parameters = parameters;
   }

   Table<R> getAliasedTable() {
      return this.alias != null ? (Table)this.alias.wrapped() : null;
   }

   final Fields<R> fields0() {
      return this.fields;
   }

   public final Clause[] clauses(Context<?> ctx) {
      return this.alias != null ? CLAUSES_TABLE_ALIAS : CLAUSES_TABLE_REFERENCE;
   }

   public final void accept(Context<?> ctx) {
      if (this.alias != null) {
         ctx.visit(this.alias);
      } else {
         this.accept0(ctx);
      }

   }

   private void accept0(Context<?> ctx) {
      if (ctx.qualify() && (!Arrays.asList(SQLDialect.POSTGRES).contains(ctx.family()) || this.parameters == null || ctx.declareTables())) {
         Schema mappedSchema = Tools.getMappedSchema(ctx.configuration(), this.getSchema());
         if (mappedSchema != null) {
            ctx.visit(mappedSchema);
            ctx.sql('.');
         }
      }

      ctx.literal(Tools.getMappedTable(ctx.configuration(), this).getName());
      if (this.parameters != null && ctx.declareTables()) {
         if (ctx.family() == SQLDialect.FIREBIRD && this.parameters.length == 0) {
            ctx.visit(new QueryPartList(this.parameters));
         } else {
            ctx.sql('(').visit(new QueryPartList(this.parameters)).sql(')');
         }
      }

   }

   public Table<R> as(String as) {
      return (Table)(this.alias != null ? ((Table)this.alias.wrapped()).as(as) : new TableAlias(this, as));
   }

   public Table<R> as(String as, String... fieldAliases) {
      return (Table)(this.alias != null ? ((Table)this.alias.wrapped()).as(as, fieldAliases) : new TableAlias(this, as, fieldAliases));
   }

   public Table<R> rename(String rename) {
      return new TableImpl(rename, this.getSchema());
   }

   public Class<? extends R> getRecordType() {
      return RecordImpl.class;
   }

   public boolean declaresTables() {
      return this.alias != null || this.parameters != null || super.declaresTables();
   }

   public boolean equals(Object that) {
      if (this == that) {
         return true;
      } else if (!(that instanceof TableImpl)) {
         return super.equals(that);
      } else {
         TableImpl<?> other = (TableImpl)that;
         return StringUtils.equals(this.getSchema(), other.getSchema()) && StringUtils.equals(this.getName(), other.getName()) && Arrays.equals(this.parameters, other.parameters);
      }
   }

   static {
      CLAUSES_TABLE_REFERENCE = new Clause[]{Clause.TABLE, Clause.TABLE_REFERENCE};
      CLAUSES_TABLE_ALIAS = new Clause[]{Clause.TABLE, Clause.TABLE_ALIAS};
   }
}
