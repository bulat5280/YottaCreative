package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.InsertQuery;
import org.jooq.Merge;
import org.jooq.MergeNotMatchedStep;
import org.jooq.MergeOnConditionStep;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.exception.SQLDialectNotSupportedException;

final class InsertQueryImpl<R extends Record> extends AbstractStoreQuery<R> implements InsertQuery<R> {
   private static final long serialVersionUID = 4466005417945353842L;
   private static final Clause[] CLAUSES;
   private final FieldMapForUpdate updateMap;
   private final FieldMapsForInsert insertMaps;
   private Select<?> select;
   private boolean defaultValues;
   private boolean onDuplicateKeyUpdate;
   private boolean onDuplicateKeyIgnore;
   private QueryPartList<Field<?>> onConflict;

   InsertQueryImpl(Configuration configuration, WithImpl with, Table<R> into) {
      super(configuration, with, into);
      this.updateMap = new FieldMapForUpdate(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE_ASSIGNMENT);
      this.insertMaps = new FieldMapsForInsert();
   }

   public final void newRecord() {
      this.insertMaps.newRecord();
   }

   protected final FieldMapForInsert getValues() {
      return this.insertMaps.getMap();
   }

   public final void addRecord(R record) {
      this.newRecord();
      this.setRecord(record);
   }

   public final void onConflict(Field<?>... fields) {
      this.onConflict((Collection)Arrays.asList(fields));
   }

   public final void onConflict(Collection<? extends Field<?>> fields) {
      this.onConflict = new QueryPartList(fields);
   }

   public final void onDuplicateKeyUpdate(boolean flag) {
      this.onDuplicateKeyIgnore = false;
      this.onDuplicateKeyUpdate = flag;
   }

   public final void onDuplicateKeyIgnore(boolean flag) {
      this.onDuplicateKeyUpdate = false;
      this.onDuplicateKeyIgnore = flag;
   }

   public final <T> void addValueForUpdate(Field<T> field, T value) {
      this.updateMap.put(field, Tools.field(value, field));
   }

   public final <T> void addValueForUpdate(Field<T> field, Field<T> value) {
      this.updateMap.put(field, Tools.field(value, (Field)field));
   }

   public final void addValuesForUpdate(Map<? extends Field<?>, ?> map) {
      this.updateMap.set(map);
   }

   public final void setDefaultValues() {
      this.defaultValues = true;
   }

   public final void setSelect(Field<?>[] f, Select<?> s) {
      this.insertMaps.getMap().putFields(Arrays.asList(f));
      this.select = s;
   }

   public final void addValues(Map<? extends Field<?>, ?> map) {
      this.insertMaps.getMap().set(map);
   }

   final void accept0(Context<?> ctx) {
      boolean qualify;
      if (this.onDuplicateKeyUpdate) {
         switch(ctx.family()) {
         case CUBRID:
         case MARIADB:
         case MYSQL:
            this.toSQLInsert(ctx);
            ctx.formatSeparator().start(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE).keyword("on duplicate key update").formatIndentStart().formatSeparator().visit(this.updateMap).formatIndentEnd().end(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE);
            break;
         case POSTGRES:
            this.toSQLInsert(ctx);
            ctx.formatSeparator().start(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE).keyword("on conflict").sql(" (");
            if (this.onConflict != null && this.onConflict.size() > 0) {
               qualify = ctx.qualify();
               ctx.qualify(false).visit(this.onConflict).qualify(qualify);
            } else if (this.table.getPrimaryKey() == null) {
               ctx.sql("[unknown primary key]");
            } else {
               qualify = ctx.qualify();
               ctx.qualify(false).visit(new Fields(this.table.getPrimaryKey().getFields())).qualify(qualify);
            }

            ctx.sql(") ").keyword("do update").formatSeparator().keyword("set").sql(' ').formatIndentLockStart().visit(this.updateMap).formatIndentLockEnd().end(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE);
            break;
         case H2:
            throw new SQLDialectNotSupportedException("The ON DUPLICATE KEY UPDATE clause cannot be emulated for " + ctx.dialect());
         case HSQLDB:
            ctx.visit(this.toMerge(ctx.configuration()));
            break;
         default:
            throw new SQLDialectNotSupportedException("The ON DUPLICATE KEY UPDATE clause cannot be emulated for " + ctx.dialect());
         }
      } else if (this.onDuplicateKeyIgnore) {
         switch(ctx.dialect()) {
         case CUBRID:
            FieldMapForUpdate update = new FieldMapForUpdate(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE_ASSIGNMENT);
            Field<?> field = this.table.field(0);
            update.put(field, field);
            this.toSQLInsert(ctx);
            ctx.formatSeparator().start(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE).keyword("on duplicate key update").sql(' ').visit(update).end(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE);
            break;
         case MARIADB:
         case MYSQL:
         case SQLITE:
            this.toSQLInsert(ctx);
            ctx.start(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE).end(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE);
            break;
         case POSTGRES:
         case POSTGRES_9_5:
            this.toSQLInsert(ctx);
            ctx.formatSeparator().start(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE).keyword("on conflict").sql(' ');
            if (this.onConflict != null && this.onConflict.size() > 0) {
               qualify = ctx.qualify();
               ctx.sql('(').qualify(false).visit(this.onConflict).qualify(qualify).sql(") ");
            }

            ctx.keyword("do nothing").end(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE);
            break;
         case H2:
         default:
            ctx.visit(this.toInsertSelect(ctx.configuration()));
            break;
         case HSQLDB:
            ctx.visit(this.toMerge(ctx.configuration()));
         }
      } else {
         this.toSQLInsert(ctx);
         ctx.start(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE).end(Clause.INSERT_ON_DUPLICATE_KEY_UPDATE);
      }

      ctx.start(Clause.INSERT_RETURNING);
      this.toSQLReturning(ctx);
      ctx.end(Clause.INSERT_RETURNING);
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   private final void toSQLInsert(Context<?> ctx) {
      boolean declareTables = ctx.declareTables();
      ctx.start(Clause.INSERT_INSERT_INTO).keyword("insert").sql(' ').keyword(this.onDuplicateKeyIgnore && Arrays.asList(SQLDialect.MARIADB, SQLDialect.MYSQL).contains(ctx.family()) ? "ignore " : (this.onDuplicateKeyIgnore && SQLDialect.SQLITE == ctx.family() ? "or ignore " : "")).keyword("into").sql(' ').declareTables(true).visit(this.table).declareTables(declareTables);
      if (this.insertMaps.isExecutable()) {
         ((FieldMapForInsert)this.insertMaps.insertMaps.get(0)).toSQLReferenceKeys(ctx);
      }

      ctx.end(Clause.INSERT_INSERT_INTO);
      if (this.select != null) {
         if (((FieldMapForInsert)this.insertMaps.insertMaps.get(0)).size() == 0) {
            ctx.data(Tools.DataKey.DATA_INSERT_SELECT_WITHOUT_INSERT_COLUMN_LIST, true);
         }

         ctx.formatSeparator().start(Clause.INSERT_SELECT).visit(this.select).end(Clause.INSERT_SELECT);
         ctx.data().remove(Tools.DataKey.DATA_INSERT_SELECT_WITHOUT_INSERT_COLUMN_LIST);
      } else if (this.defaultValues) {
         switch(ctx.family()) {
         case MARIADB:
         case MYSQL:
         case DERBY:
            ctx.formatSeparator().keyword("values").sql('(');
            int count = this.table.fields().length;
            String separator = "";

            for(int i = 0; i < count; ++i) {
               ctx.sql(separator);
               ctx.keyword("default");
               separator = ", ";
            }

            ctx.sql(')');
            break;
         default:
            ctx.formatSeparator().keyword("default values");
         }
      } else {
         ctx.visit(this.insertMaps);
      }

   }

   private final QueryPart toInsertSelect(Configuration configuration) {
      if (this.table.getPrimaryKey() != null) {
         Select<Record> rows = null;
         String[] aliases = Tools.fieldNames((Field[])this.insertMaps.getMap().keySet().toArray(Tools.EMPTY_FIELD));
         Iterator var4 = this.insertMaps.insertMaps.iterator();

         while(var4.hasNext()) {
            FieldMapForInsert map = (FieldMapForInsert)var4.next();
            Select<Record> row = DSL.select((SelectField[])Tools.aliasedFields((Field[])map.values().toArray(Tools.EMPTY_FIELD), aliases)).whereNotExists(DSL.selectOne().from(this.table).where(new Condition[]{this.matchByPrimaryKey(map)}));
            if (rows == null) {
               rows = row;
            } else {
               rows = ((Select)rows).unionAll(row);
            }
         }

         return this.create(configuration).insertInto(this.table).columns((Collection)this.insertMaps.getMap().keySet()).select(DSL.selectFrom(DSL.table((Select)rows).as("t")));
      } else {
         throw new IllegalStateException("The ON DUPLICATE KEY IGNORE/UPDATE clause cannot be emulated when inserting into non-updatable tables : " + this.table);
      }
   }

   private final Merge<R> toMerge(Configuration configuration) {
      if (this.table.getPrimaryKey() != null) {
         MergeOnConditionStep<R> on = this.create(configuration).mergeInto(this.table).usingDual().on(this.matchByPrimaryKey(this.insertMaps.getMap()));
         MergeNotMatchedStep<R> notMatched = on;
         if (this.onDuplicateKeyUpdate) {
            notMatched = on.whenMatchedThenUpdate().set((Map)this.updateMap);
         }

         return ((MergeNotMatchedStep)notMatched).whenNotMatchedThenInsert((Collection)this.insertMaps.getMap().keySet()).values(this.insertMaps.getMap().values());
      } else {
         throw new IllegalStateException("The ON DUPLICATE KEY IGNORE/UPDATE clause cannot be emulated when inserting into non-updatable tables : " + this.table);
      }
   }

   private final Condition matchByPrimaryKey(FieldMapForInsert map) {
      Condition condition = null;

      Condition other;
      for(Iterator var3 = this.table.getPrimaryKey().getFields().iterator(); var3.hasNext(); condition = condition == null ? other : condition.and(other)) {
         Field<?> f = (Field)var3.next();
         Field<Object> value = (Field)map.get(f);
         other = f.equal(value);
      }

      return condition;
   }

   public final boolean isExecutable() {
      return this.insertMaps.isExecutable() || this.defaultValues || this.select != null;
   }

   static {
      CLAUSES = new Clause[]{Clause.INSERT};
   }
}
