package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.PivotForStep;
import org.jooq.PivotInStep;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Table;

final class Pivot<T> extends AbstractTable<Record> implements PivotForStep, PivotInStep<T> {
   private static final long serialVersionUID = -7918219502110473521L;
   private final Table<?> table;
   private final SelectFieldList aggregateFunctions;
   private Field<T> on;
   private SelectFieldList in;

   Pivot(Table<?> table, Field<?>... aggregateFunctions) {
      super("pivot");
      this.table = table;
      this.aggregateFunctions = new SelectFieldList(aggregateFunctions);
   }

   public final Class<? extends Record> getRecordType() {
      return RecordImpl.class;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.pivot(ctx.configuration()));
   }

   private Table<?> pivot(Configuration configuration) {
      switch(configuration.dialect()) {
      default:
         return new Pivot.DefaultPivotTable();
      }
   }

   private <Z> Condition condition(Table<?> pivot, Field<Z> field) {
      return field.equal(pivot.field(field));
   }

   public final boolean declaresTables() {
      return true;
   }

   public final Table<Record> as(String alias) {
      return new TableAlias(this, alias, true);
   }

   public final Table<Record> as(String alias, String... fieldAliases) {
      return new TableAlias(this, alias, fieldAliases, true);
   }

   final Fields<Record> fields0() {
      return new Fields(new Field[0]);
   }

   private abstract class DialectPivotTable extends AbstractTable<Record> {
      private static final long serialVersionUID = 2662639259338694177L;

      DialectPivotTable() {
         super("pivot");
      }

      public final Class<? extends Record> getRecordType() {
         return RecordImpl.class;
      }

      public final Table<Record> as(String as) {
         return new TableAlias(this, as);
      }

      public final Table<Record> as(String as, String... fieldAliases) {
         return new TableAlias(this, as, fieldAliases);
      }

      final Fields<Record> fields0() {
         return Pivot.this.fields0();
      }
   }

   private class DefaultPivotTable extends Pivot<T>.DialectPivotTable {
      private static final long serialVersionUID = -5930286639571867314L;

      private DefaultPivotTable() {
         super();
      }

      public final void accept(Context<?> ctx) {
         ctx.declareTables(true).visit(this.select(ctx.configuration())).declareTables(false);
      }

      private Table<Record> select(Configuration configuration) {
         List<Field<?>> groupingFields = new ArrayList();
         List<Field<?>> aliasedGroupingFields = new ArrayList();
         List<Field<?>> aggregatedFields = new ArrayList();
         Table<?> pivot = Pivot.this.table.as("pivot_outer");
         Iterator var6 = Pivot.this.aggregateFunctions.iterator();

         while(true) {
            Field field;
            do {
               if (!var6.hasNext()) {
                  Field[] var14 = Pivot.this.table.fields();
                  int var16 = var14.length;

                  for(int var19 = 0; var19 < var16; ++var19) {
                     Field<?> fieldxx = var14[var19];
                     if (!aggregatedFields.contains(fieldxx) && !Pivot.this.on.equals(fieldxx)) {
                        aliasedGroupingFields.add(pivot.field(fieldxx));
                        groupingFields.add(fieldxx);
                     }
                  }

                  List<Field<?>> aggregationSelects = new ArrayList();
                  Iterator var17 = Pivot.this.in.iterator();

                  while(var17.hasNext()) {
                     Field<?> inField = (Field)var17.next();
                     Iterator var22 = Pivot.this.aggregateFunctions.iterator();

                     while(var22.hasNext()) {
                        Field<?> aggregateFunction = (Field)var22.next();
                        Condition join = DSL.trueCondition();

                        Field fieldx;
                        for(Iterator var12 = groupingFields.iterator(); var12.hasNext(); join = join.and(Pivot.this.condition(pivot, fieldx))) {
                           fieldx = (Field)var12.next();
                        }

                        Select<?> aggregateSelect = DSL.using(configuration).select((SelectField)aggregateFunction).from(Pivot.this.table).where(new Condition[]{Pivot.this.on.equal(inField)}).and(join);
                        aggregationSelects.add(aggregateSelect.asField(inField.getName() + "_" + aggregateFunction.getName()));
                     }
                  }

                  Table<Record> select = DSL.using(configuration).select((Collection)aliasedGroupingFields).select((Collection)aggregationSelects).from(pivot).where(new Condition[]{pivot.field(Pivot.this.on).in((Field[])Pivot.this.in.toArray(Tools.EMPTY_FIELD))}).groupBy(aliasedGroupingFields).asTable();
                  return select;
               }

               field = (Field)var6.next();
            } while(!(field instanceof Function));

            Iterator var8 = ((Function)field).getArguments().iterator();

            while(var8.hasNext()) {
               QueryPart argument = (QueryPart)var8.next();
               if (argument instanceof Field) {
                  aggregatedFields.add((Field)argument);
               }
            }
         }
      }

      // $FF: synthetic method
      DefaultPivotTable(Object x1) {
         this();
      }
   }
}
