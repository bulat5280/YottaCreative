package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jooq.Condition;
import org.jooq.ConditionProvider;
import org.jooq.DivideByOnConditionStep;
import org.jooq.DivideByOnStep;
import org.jooq.Field;
import org.jooq.Operator;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Select;
import org.jooq.Table;

final class DivideBy implements DivideByOnStep, DivideByOnConditionStep {
   private final Table<?> dividend;
   private final Table<?> divisor;
   private final ConditionProviderImpl condition;
   private final QueryPartList<Field<?>> returning;

   DivideBy(Table<?> dividend, Table<?> divisor) {
      this.dividend = dividend;
      this.divisor = divisor;
      this.condition = new ConditionProviderImpl();
      this.returning = new QueryPartList();
   }

   private final Table<Record> table() {
      ConditionProviderImpl selfJoin = new ConditionProviderImpl();
      List<Field<?>> select = new ArrayList();
      Table<?> outer = this.dividend.as("dividend");
      Iterator var4 = this.returning.iterator();

      while(var4.hasNext()) {
         Field<?> field = (Field)var4.next();
         Field<?> outerField = outer.field(field);
         if (outerField == null) {
            select.add(field);
         } else {
            select.add(outerField);
            this.selfJoin(selfJoin, outer, this.dividend, field);
         }
      }

      return DSL.selectDistinct((Collection)select).from(outer).whereNotExists(DSL.selectOne().from(this.divisor).whereNotExists(DSL.selectOne().from(this.dividend).where(new Condition[]{selfJoin}).and((Condition)this.condition))).asTable();
   }

   private final <T> void selfJoin(ConditionProvider selfJoin, Table<?> outer, Table<?> inner, Field<T> field) {
      Field<T> outerField = outer.field(field);
      Field<T> innerField = inner.field(field);
      if (outerField != null && innerField != null) {
         selfJoin.addConditions(outerField.equal(innerField));
      }
   }

   public final DivideByOnConditionStep on(Condition... conditions) {
      this.condition.addConditions(conditions);
      return this;
   }

   public final DivideByOnConditionStep on(Field<Boolean> c) {
      return this.on(DSL.condition(c));
   }

   public final DivideByOnConditionStep on(Boolean c) {
      return this.on(DSL.condition(c));
   }

   public final DivideByOnConditionStep on(SQL sql) {
      this.and(sql);
      return this;
   }

   public final DivideByOnConditionStep on(String sql) {
      this.and(sql);
      return this;
   }

   public final DivideByOnConditionStep on(String sql, Object... bindings) {
      this.and(sql, bindings);
      return this;
   }

   public final DivideByOnConditionStep on(String sql, QueryPart... parts) {
      this.and(sql, parts);
      return this;
   }

   public final Table<Record> returning(Field<?>... fields) {
      return this.returning((Collection)Arrays.asList(fields));
   }

   public final Table<Record> returning(Collection<? extends Field<?>> fields) {
      this.returning.addAll(fields);
      return this.table();
   }

   public final DivideByOnConditionStep and(Condition c) {
      this.condition.addConditions(c);
      return this;
   }

   public final DivideByOnConditionStep and(Field<Boolean> c) {
      return this.and(DSL.condition(c));
   }

   public final DivideByOnConditionStep and(Boolean c) {
      return this.and(DSL.condition(c));
   }

   public final DivideByOnConditionStep and(SQL sql) {
      return this.and(DSL.condition(sql));
   }

   public final DivideByOnConditionStep and(String sql) {
      return this.and(DSL.condition(sql));
   }

   public final DivideByOnConditionStep and(String sql, Object... bindings) {
      return this.and(DSL.condition(sql, bindings));
   }

   public final DivideByOnConditionStep and(String sql, QueryPart... parts) {
      return this.and(DSL.condition(sql, parts));
   }

   public final DivideByOnConditionStep andNot(Condition c) {
      return this.and(c.not());
   }

   public final DivideByOnConditionStep andNot(Field<Boolean> c) {
      return this.andNot(DSL.condition(c));
   }

   public final DivideByOnConditionStep andNot(Boolean c) {
      return this.andNot(DSL.condition(c));
   }

   public final DivideByOnConditionStep andExists(Select<?> select) {
      return this.and(DSL.exists(select));
   }

   public final DivideByOnConditionStep andNotExists(Select<?> select) {
      return this.and(DSL.notExists(select));
   }

   public final DivideByOnConditionStep or(Condition c) {
      this.condition.addConditions(Operator.OR, c);
      return this;
   }

   public final DivideByOnConditionStep or(Field<Boolean> c) {
      return this.or(DSL.condition(c));
   }

   public final DivideByOnConditionStep or(Boolean c) {
      return this.or(DSL.condition(c));
   }

   public final DivideByOnConditionStep or(SQL sql) {
      return this.or(DSL.condition(sql));
   }

   public final DivideByOnConditionStep or(String sql) {
      return this.or(DSL.condition(sql));
   }

   public final DivideByOnConditionStep or(String sql, Object... bindings) {
      return this.or(DSL.condition(sql, bindings));
   }

   public final DivideByOnConditionStep or(String sql, QueryPart... parts) {
      return this.or(DSL.condition(sql, parts));
   }

   public final DivideByOnConditionStep orNot(Condition c) {
      return this.or(c.not());
   }

   public final DivideByOnConditionStep orNot(Field<Boolean> c) {
      return this.orNot(DSL.condition(c));
   }

   public final DivideByOnConditionStep orNot(Boolean c) {
      return this.orNot(DSL.condition(c));
   }

   public final DivideByOnConditionStep orExists(Select<?> select) {
      return this.or(DSL.exists(select));
   }

   public final DivideByOnConditionStep orNotExists(Select<?> select) {
      return this.or(DSL.notExists(select));
   }
}
