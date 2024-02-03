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
import org.jooq.Operator;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Record11;
import org.jooq.Record12;
import org.jooq.Record13;
import org.jooq.Record14;
import org.jooq.Record15;
import org.jooq.Record16;
import org.jooq.Record17;
import org.jooq.Record18;
import org.jooq.Record19;
import org.jooq.Record2;
import org.jooq.Record20;
import org.jooq.Record21;
import org.jooq.Record22;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Record8;
import org.jooq.Record9;
import org.jooq.Row;
import org.jooq.Row1;
import org.jooq.Row10;
import org.jooq.Row11;
import org.jooq.Row12;
import org.jooq.Row13;
import org.jooq.Row14;
import org.jooq.Row15;
import org.jooq.Row16;
import org.jooq.Row17;
import org.jooq.Row18;
import org.jooq.Row19;
import org.jooq.Row2;
import org.jooq.Row20;
import org.jooq.Row21;
import org.jooq.Row22;
import org.jooq.Row3;
import org.jooq.Row4;
import org.jooq.Row5;
import org.jooq.Row6;
import org.jooq.Row7;
import org.jooq.Row8;
import org.jooq.Row9;
import org.jooq.RowN;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.UpdateQuery;

final class UpdateQueryImpl<R extends Record> extends AbstractStoreQuery<R> implements UpdateQuery<R> {
   private static final long serialVersionUID = -660460731970074719L;
   private static final Clause[] CLAUSES;
   private final FieldMapForUpdate updateMap;
   private final TableList from;
   private final ConditionProviderImpl condition;
   private Row multiRow;
   private Row multiValue;
   private Select<?> multiSelect;

   UpdateQueryImpl(Configuration configuration, WithImpl with, Table<R> table) {
      super(configuration, with, table);
      this.updateMap = new FieldMapForUpdate(Clause.UPDATE_SET_ASSIGNMENT);
      this.from = new TableList();
      this.condition = new ConditionProviderImpl();
   }

   protected final FieldMapForUpdate getValues() {
      return this.updateMap;
   }

   public final <T1> void addValues(Row1<T1> row, Row1<T1> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2> void addValues(Row2<T1, T2> row, Row2<T1, T2> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3> void addValues(Row3<T1, T2, T3> row, Row3<T1, T2, T3> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3, T4> void addValues(Row4<T1, T2, T3, T4> row, Row4<T1, T2, T3, T4> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3, T4, T5> void addValues(Row5<T1, T2, T3, T4, T5> row, Row5<T1, T2, T3, T4, T5> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3, T4, T5, T6> void addValues(Row6<T1, T2, T3, T4, T5, T6> row, Row6<T1, T2, T3, T4, T5, T6> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3, T4, T5, T6, T7> void addValues(Row7<T1, T2, T3, T4, T5, T6, T7> row, Row7<T1, T2, T3, T4, T5, T6, T7> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8> void addValues(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row, Row8<T1, T2, T3, T4, T5, T6, T7, T8> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> void addValues(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row, Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> void addValues(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row, Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> void addValues(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row, Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> void addValues(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row, Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> void addValues(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row, Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> void addValues(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row, Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> void addValues(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row, Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> void addValues(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row, Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> void addValues(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row, Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> void addValues(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row, Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> void addValues(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row, Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> void addValues(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row, Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> void addValues(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row, Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> void addValues(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row, Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> value) {
      this.addValues0(row, (Row)value);
   }

   public final void addValues(RowN row, RowN value) {
      this.addValues0(row, (Row)value);
   }

   public final <T1> void addValues(Row1<T1> row, Select<? extends Record1<T1>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2> void addValues(Row2<T1, T2> row, Select<? extends Record2<T1, T2>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3> void addValues(Row3<T1, T2, T3> row, Select<? extends Record3<T1, T2, T3>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3, T4> void addValues(Row4<T1, T2, T3, T4> row, Select<? extends Record4<T1, T2, T3, T4>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3, T4, T5> void addValues(Row5<T1, T2, T3, T4, T5> row, Select<? extends Record5<T1, T2, T3, T4, T5>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3, T4, T5, T6> void addValues(Row6<T1, T2, T3, T4, T5, T6> row, Select<? extends Record6<T1, T2, T3, T4, T5, T6>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3, T4, T5, T6, T7> void addValues(Row7<T1, T2, T3, T4, T5, T6, T7> row, Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8> void addValues(Row8<T1, T2, T3, T4, T5, T6, T7, T8> row, Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> void addValues(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> row, Select<? extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> void addValues(Row10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> row, Select<? extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> void addValues(Row11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> row, Select<? extends Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> void addValues(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> row, Select<? extends Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> void addValues(Row13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> row, Select<? extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> void addValues(Row14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> row, Select<? extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> void addValues(Row15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> row, Select<? extends Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> void addValues(Row16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> row, Select<? extends Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> void addValues(Row17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> row, Select<? extends Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> void addValues(Row18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> row, Select<? extends Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> void addValues(Row19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> row, Select<? extends Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> void addValues(Row20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> row, Select<? extends Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> void addValues(Row21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> row, Select<? extends Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> select) {
      this.addValues0(row, (Select)select);
   }

   public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> void addValues(Row22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> row, Select<? extends Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> select) {
      this.addValues0(row, (Select)select);
   }

   public final void addValues(RowN row, Select<?> select) {
      this.addValues0(row, (Select)select);
   }

   private final void addValues0(Row row, Row value) {
      this.multiRow = row;
      this.multiValue = value;
   }

   private final void addValues0(Row row, Select<?> select) {
      this.multiRow = row;
      this.multiSelect = select;
   }

   public final void addValues(Map<? extends Field<?>, ?> map) {
      this.updateMap.set(map);
   }

   public final void addFrom(Collection<? extends TableLike<?>> f) {
      Iterator var2 = f.iterator();

      while(var2.hasNext()) {
         TableLike<?> provider = (TableLike)var2.next();
         this.from.add(provider.asTable());
      }

   }

   public final void addFrom(TableLike<?> f) {
      this.addFrom((Collection)Arrays.asList(f));
   }

   public final void addFrom(TableLike<?>... f) {
      this.addFrom((Collection)Arrays.asList(f));
   }

   public final void addConditions(Collection<? extends Condition> conditions) {
      this.condition.addConditions(conditions);
   }

   public final void addConditions(Condition... conditions) {
      this.condition.addConditions(conditions);
   }

   public final void addConditions(Operator operator, Condition... conditions) {
      this.condition.addConditions(operator, conditions);
   }

   public final void addConditions(Operator operator, Collection<? extends Condition> conditions) {
      this.condition.addConditions(operator, conditions);
   }

   final Condition getWhere() {
      return this.condition.getWhere();
   }

   final void accept0(Context<?> ctx) {
      boolean declareTables = ctx.declareTables();
      ctx.start(Clause.UPDATE_UPDATE).keyword("update").sql(' ').declareTables(!Arrays.asList().contains(ctx.family())).visit(this.table).declareTables(declareTables).end(Clause.UPDATE_UPDATE);
      ctx.formatSeparator().start(Clause.UPDATE_SET).keyword("set").sql(' ');
      if (this.multiRow != null) {
         boolean qualify = ctx.qualify();
         ctx.start(Clause.UPDATE_SET_ASSIGNMENT).qualify(false).visit(this.multiRow).qualify(qualify).sql(" = ");
         if (this.multiValue != null && !Arrays.asList().contains(ctx.family())) {
            ctx.visit(this.multiValue);
         } else {
            Select<?> select = this.multiSelect;
            if (this.multiValue != null) {
               select = DSL.select((SelectField[])this.multiValue.fields());
            }

            ctx.sql('(').formatIndentStart().formatNewLine().subquery(true).visit((QueryPart)select).subquery(false).formatIndentEnd().formatNewLine().sql(')');
         }

         ctx.end(Clause.UPDATE_SET_ASSIGNMENT);
      } else {
         ctx.formatIndentLockStart().visit(this.updateMap).formatIndentLockEnd();
      }

      ctx.end(Clause.UPDATE_SET);
      switch(ctx.family()) {
      default:
         ctx.start(Clause.UPDATE_FROM);
         if (!this.from.isEmpty()) {
            ctx.formatSeparator().keyword("from").sql(' ').declareTables(true).visit(this.from).declareTables(false);
         }

         ctx.end(Clause.UPDATE_FROM);
         ctx.start(Clause.UPDATE_WHERE);
         if (!(this.getWhere() instanceof TrueCondition)) {
            ctx.formatSeparator().keyword("where").sql(' ').visit(this.getWhere());
         }

         ctx.end(Clause.UPDATE_WHERE).start(Clause.UPDATE_RETURNING);
         this.toSQLReturning(ctx);
         ctx.end(Clause.UPDATE_RETURNING);
      }
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   public final boolean isExecutable() {
      return this.updateMap.size() > 0 || this.multiRow != null;
   }

   static {
      CLAUSES = new Clause[]{Clause.UPDATE};
   }
}
