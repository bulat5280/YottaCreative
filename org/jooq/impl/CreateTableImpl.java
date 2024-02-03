package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Constraint;
import org.jooq.Context;
import org.jooq.CreateTableAsStep;
import org.jooq.CreateTableColumnStep;
import org.jooq.CreateTableConstraintStep;
import org.jooq.CreateTableFinalStep;
import org.jooq.CreateTableOnCommitStep;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Table;

final class CreateTableImpl<R extends Record> extends AbstractQuery implements CreateTableAsStep<R>, CreateTableColumnStep {
   private static final long serialVersionUID = 8904572826501186329L;
   private final Table<?> table;
   private Select<?> select;
   private final List<Field<?>> columnFields;
   private final List<DataType<?>> columnTypes;
   private final List<Constraint> constraints;
   private final boolean temporary;
   private final boolean ifNotExists;
   private CreateTableImpl.OnCommit onCommit;

   CreateTableImpl(Configuration configuration, Table<?> table, boolean temporary, boolean ifNotExists) {
      super(configuration);
      this.table = table;
      this.temporary = temporary;
      this.ifNotExists = ifNotExists;
      this.columnFields = new ArrayList();
      this.columnTypes = new ArrayList();
      this.constraints = new ArrayList();
   }

   public final CreateTableOnCommitStep as(Select<? extends R> s) {
      this.select = s;
      return this;
   }

   public final CreateTableColumnStep column(Field<?> field) {
      return this.column(field, field.getDataType());
   }

   public final CreateTableColumnStep columns(Field<?>... fields) {
      return this.columns((Collection)Arrays.asList(fields));
   }

   public final CreateTableColumnStep columns(Collection<? extends Field<?>> fields) {
      Iterator var2 = fields.iterator();

      while(var2.hasNext()) {
         Field<?> field = (Field)var2.next();
         this.column(field);
      }

      return this;
   }

   public final <T> CreateTableColumnStep column(Field<T> field, DataType<T> type) {
      this.columnFields.add(field);
      this.columnTypes.add(type);
      return this;
   }

   public final CreateTableColumnStep column(Name field, DataType<?> type) {
      this.columnFields.add(DSL.field(field, type));
      this.columnTypes.add(type);
      return this;
   }

   public final CreateTableColumnStep column(String field, DataType<?> type) {
      return this.column(DSL.name(field), type);
   }

   public final CreateTableConstraintStep constraint(Constraint c) {
      return this.constraints((Collection)Arrays.asList(c));
   }

   public final CreateTableConstraintStep constraints(Constraint... c) {
      return this.constraints((Collection)Arrays.asList(c));
   }

   public final CreateTableConstraintStep constraints(Collection<? extends Constraint> c) {
      this.constraints.addAll(c);
      return this;
   }

   public final CreateTableFinalStep onCommitDeleteRows() {
      this.onCommit = CreateTableImpl.OnCommit.DELETE_ROWS;
      return this;
   }

   public final CreateTableFinalStep onCommitPreserveRows() {
      this.onCommit = CreateTableImpl.OnCommit.PRESERVE_ROWS;
      return this;
   }

   public final CreateTableFinalStep onCommitDrop() {
      this.onCommit = CreateTableImpl.OnCommit.DROP;
      return this;
   }

   private final boolean supportsIfNotExists(Context<?> ctx) {
      return !Arrays.asList(SQLDialect.DERBY, SQLDialect.FIREBIRD).contains(ctx.family());
   }

   public final void accept(Context<?> ctx) {
      if (this.ifNotExists && !this.supportsIfNotExists(ctx)) {
         Tools.executeImmediateBegin(ctx, DDLStatementType.CREATE_TABLE);
         this.accept0(ctx);
         Tools.executeImmediateEnd(ctx, DDLStatementType.CREATE_TABLE);
      } else {
         this.accept0(ctx);
      }

   }

   private final void accept0(Context<?> ctx) {
      if (this.select != null) {
         this.acceptCreateTableAsSelect(ctx);
      } else {
         ctx.start(Clause.CREATE_TABLE);
         this.toSQLCreateTableName(ctx);
         ctx.sql('(').start(Clause.CREATE_TABLE_COLUMNS).formatIndentStart().formatNewLine();
         boolean qualify = ctx.qualify();
         ctx.qualify(false);

         for(int i = 0; i < this.columnFields.size(); ++i) {
            DataType<?> type = (DataType)this.columnTypes.get(i);
            ctx.visit((QueryPart)this.columnFields.get(i)).sql(' ');
            Tools.toSQLDDLTypeDeclaration(ctx, type);
            if (Arrays.asList(SQLDialect.HSQLDB).contains(ctx.family())) {
               this.acceptDefault(ctx, type);
            }

            if (type.nullable()) {
               if (!Arrays.asList(SQLDialect.DERBY, SQLDialect.FIREBIRD).contains(ctx.family())) {
                  ctx.sql(' ').keyword("null");
               }
            } else {
               ctx.sql(' ').keyword("not null");
            }

            if (type.identity()) {
               switch(ctx.family()) {
               case H2:
               case MARIADB:
               case MYSQL:
                  ctx.sql(' ').keyword("auto_increment");
               }
            }

            if (!Arrays.asList(SQLDialect.HSQLDB).contains(ctx.family())) {
               this.acceptDefault(ctx, type);
            }

            if (i < this.columnFields.size() - 1) {
               ctx.sql(',').formatSeparator();
            }
         }

         ctx.qualify(qualify);
         ctx.end(Clause.CREATE_TABLE_COLUMNS).start(Clause.CREATE_TABLE_CONSTRAINTS);
         if (!this.constraints.isEmpty()) {
            Iterator var5 = this.constraints.iterator();

            while(var5.hasNext()) {
               Constraint constraint = (Constraint)var5.next();
               ctx.sql(',').formatSeparator().visit(constraint);
            }
         }

         ctx.end(Clause.CREATE_TABLE_CONSTRAINTS).formatIndentEnd().formatNewLine().sql(')');
         this.toSQLOnCommit(ctx);
         ctx.end(Clause.CREATE_TABLE);
      }

   }

   private void acceptDefault(Context<?> ctx, DataType<?> type) {
      if (type.defaulted()) {
         ctx.sql(' ').keyword("default").sql(' ').visit(type.defaultValue());
      }

   }

   private final void acceptCreateTableAsSelect(Context<?> ctx) {
      ctx.start(Clause.CREATE_TABLE);
      this.toSQLCreateTableName(ctx);
      this.toSQLOnCommit(ctx);
      ctx.formatSeparator().keyword("as");
      if (Arrays.asList(SQLDialect.HSQLDB).contains(ctx.family())) {
         ctx.sql(" (").formatIndentStart().formatNewLine();
      } else {
         ctx.formatSeparator();
      }

      ctx.start(Clause.CREATE_TABLE_AS).visit(this.select).end(Clause.CREATE_TABLE_AS);
      if (Arrays.asList(SQLDialect.HSQLDB).contains(ctx.family())) {
         ctx.formatIndentEnd().formatNewLine().sql(')');
         if (ctx.family() == SQLDialect.HSQLDB) {
            ctx.sql(' ').keyword("with data");
         }
      }

      ctx.end(Clause.CREATE_TABLE);
   }

   private final void toSQLCreateTableName(Context<?> ctx) {
      ctx.start(Clause.CREATE_TABLE_NAME).keyword("create").sql(' ');
      if (this.temporary) {
         if (Arrays.asList(SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES).contains(ctx.family())) {
            ctx.keyword("temporary").sql(' ');
         } else {
            ctx.keyword("global temporary").sql(' ');
         }
      }

      ctx.keyword("table").sql(' ');
      if (this.ifNotExists && this.supportsIfNotExists(ctx)) {
         ctx.keyword("if not exists").sql(' ');
      }

      ctx.visit(this.table).end(Clause.CREATE_TABLE_NAME);
   }

   private final void toSQLOnCommit(Context<?> ctx) {
      if (this.temporary && this.onCommit != null) {
         switch(this.onCommit) {
         case DELETE_ROWS:
            ctx.formatSeparator().keyword("on commit delete rows");
            break;
         case PRESERVE_ROWS:
            ctx.formatSeparator().keyword("on commit preserve rows");
            break;
         case DROP:
            ctx.formatSeparator().keyword("on commit drop");
         }
      }

   }

   private final void acceptSelectInto(Context<?> ctx) {
      ctx.data(Tools.DataKey.DATA_SELECT_INTO_TABLE, this.table);
      ctx.visit(this.select);
      ctx.data().remove(Tools.DataKey.DATA_SELECT_INTO_TABLE);
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   private static enum OnCommit {
      DELETE_ROWS,
      PRESERVE_ROWS,
      DROP;
   }
}
