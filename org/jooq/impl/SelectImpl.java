package org.jooq.impl;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.Cursor;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.FutureResult;
import org.jooq.GroupField;
import org.jooq.JoinType;
import org.jooq.Name;
import org.jooq.Operator;
import org.jooq.Param;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.RecordHandler;
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.Results;
import org.jooq.Row;
import org.jooq.SQL;
import org.jooq.Select;
import org.jooq.SelectConditionStep;
import org.jooq.SelectConnectByConditionStep;
import org.jooq.SelectField;
import org.jooq.SelectForUpdateOfStep;
import org.jooq.SelectHavingConditionStep;
import org.jooq.SelectIntoStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectLimitAfterOffsetStep;
import org.jooq.SelectOffsetStep;
import org.jooq.SelectOnConditionStep;
import org.jooq.SelectOnStep;
import org.jooq.SelectOptionalOnStep;
import org.jooq.SelectQuery;
import org.jooq.SelectSeekLimitStep;
import org.jooq.SelectSeekStep1;
import org.jooq.SelectSeekStep10;
import org.jooq.SelectSeekStep11;
import org.jooq.SelectSeekStep12;
import org.jooq.SelectSeekStep13;
import org.jooq.SelectSeekStep14;
import org.jooq.SelectSeekStep15;
import org.jooq.SelectSeekStep16;
import org.jooq.SelectSeekStep17;
import org.jooq.SelectSeekStep18;
import org.jooq.SelectSeekStep19;
import org.jooq.SelectSeekStep2;
import org.jooq.SelectSeekStep20;
import org.jooq.SelectSeekStep21;
import org.jooq.SelectSeekStep22;
import org.jooq.SelectSeekStep3;
import org.jooq.SelectSeekStep4;
import org.jooq.SelectSeekStep5;
import org.jooq.SelectSeekStep6;
import org.jooq.SelectSeekStep7;
import org.jooq.SelectSeekStep8;
import org.jooq.SelectSeekStep9;
import org.jooq.SelectSeekStepN;
import org.jooq.SelectSelectStep;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.WindowDefinition;
import org.jooq.exception.MappingException;

final class SelectImpl<R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> extends AbstractDelegatingQuery<Select<R>> implements SelectSelectStep<R>, SelectOptionalOnStep<R>, SelectOnConditionStep<R>, SelectConditionStep<R>, SelectConnectByConditionStep<R>, SelectHavingConditionStep<R>, SelectSeekStep1<R, T1>, SelectSeekStep2<R, T1, T2>, SelectSeekStep3<R, T1, T2, T3>, SelectSeekStep4<R, T1, T2, T3, T4>, SelectSeekStep5<R, T1, T2, T3, T4, T5>, SelectSeekStep6<R, T1, T2, T3, T4, T5, T6>, SelectSeekStep7<R, T1, T2, T3, T4, T5, T6, T7>, SelectSeekStep8<R, T1, T2, T3, T4, T5, T6, T7, T8>, SelectSeekStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9>, SelectSeekStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, SelectSeekStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>, SelectSeekStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>, SelectSeekStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>, SelectSeekStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>, SelectSeekStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>, SelectSeekStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>, SelectSeekStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>, SelectSeekStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>, SelectSeekStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>, SelectSeekStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>, SelectSeekStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>, SelectSeekStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>, SelectSeekStepN<R>, SelectSeekLimitStep<R>, SelectOffsetStep<R>, SelectLimitAfterOffsetStep<R>, SelectForUpdateOfStep<R> {
   private static final long serialVersionUID = -5425308887382166448L;
   private transient TableLike<?> joinTable;
   private transient Field<?>[] joinPartitionBy;
   private transient JoinType joinType;
   private transient ConditionProviderImpl joinConditions;
   private transient SelectImpl.ConditionStep conditionStep;
   private transient Integer limit;
   private transient Param<Integer> limitParam;
   private transient Integer offset;
   private transient Param<Integer> offsetParam;

   SelectImpl(Configuration configuration, WithImpl with) {
      this(configuration, with, false);
   }

   SelectImpl(Configuration configuration, WithImpl with, boolean distinct) {
      this(new SelectQueryImpl(configuration, with, distinct));
   }

   SelectImpl(Select<R> query) {
      super(query);
   }

   public final SelectQuery<R> getQuery() {
      return (SelectQuery)this.getDelegate();
   }

   /** @deprecated */
   @Deprecated
   public final int fetchCount() {
      return ((Select)this.getDelegate()).fetchCount();
   }

   public final SelectImpl select(SelectField<?>... fields) {
      this.getQuery().addSelect(fields);
      return this;
   }

   public final SelectImpl select(Collection<? extends SelectField<?>> fields) {
      this.getQuery().addSelect(fields);
      return this;
   }

   public final SelectIntoStep<R> on(SelectField<?>... fields) {
      return this.distinctOn((Collection)Arrays.asList(fields));
   }

   public final SelectIntoStep<R> on(Collection<? extends SelectField<?>> fields) {
      return this.distinctOn(fields);
   }

   public final SelectIntoStep<R> distinctOn(SelectField<?>... fields) {
      this.getQuery().addDistinctOn(fields);
      return this;
   }

   public final SelectIntoStep<R> distinctOn(Collection<? extends SelectField<?>> fields) {
      this.getQuery().addDistinctOn(fields);
      return this;
   }

   public final SelectImpl into(Table<?> table) {
      this.getQuery().setInto(table);
      return this;
   }

   public final SelectImpl hint(String hint) {
      this.getQuery().addHint(hint);
      return this;
   }

   public final SelectImpl option(String hint) {
      this.getQuery().addOption(hint);
      return this;
   }

   public final SelectImpl from(TableLike<?> table) {
      this.getQuery().addFrom(table);
      return this;
   }

   public final SelectImpl from(TableLike<?>... tables) {
      this.getQuery().addFrom(tables);
      return this;
   }

   public final SelectImpl from(Collection<? extends TableLike<?>> tables) {
      this.getQuery().addFrom(tables);
      return this;
   }

   public final SelectImpl from(SQL sql) {
      return this.from((TableLike)DSL.table(sql));
   }

   public final SelectImpl from(String sql) {
      return this.from((TableLike)DSL.table(sql));
   }

   public final SelectImpl from(String sql, Object... bindings) {
      return this.from((TableLike)DSL.table(sql, bindings));
   }

   public final SelectImpl from(String sql, QueryPart... parts) {
      return this.from((TableLike)DSL.table(sql, parts));
   }

   public final SelectJoinStep<R> from(Name name) {
      return this.from((TableLike)DSL.table(name));
   }

   public final SelectImpl where(Condition... conditions) {
      this.conditionStep = SelectImpl.ConditionStep.WHERE;
      this.getQuery().addConditions(conditions);
      return this;
   }

   public final SelectImpl where(Collection<? extends Condition> conditions) {
      this.conditionStep = SelectImpl.ConditionStep.WHERE;
      this.getQuery().addConditions(conditions);
      return this;
   }

   public final SelectImpl where(Field<Boolean> condition) {
      return this.where(DSL.condition(condition));
   }

   /** @deprecated */
   @Deprecated
   public final SelectImpl where(Boolean condition) {
      return this.where(DSL.condition(condition));
   }

   public final SelectImpl where(SQL sql) {
      return this.where(DSL.condition(sql));
   }

   public final SelectImpl where(String sql) {
      return this.where(DSL.condition(sql));
   }

   public final SelectImpl where(String sql, Object... bindings) {
      return this.where(DSL.condition(sql, bindings));
   }

   public final SelectImpl where(String sql, QueryPart... parts) {
      return this.where(DSL.condition(sql, parts));
   }

   public final SelectImpl whereExists(Select<?> select) {
      this.conditionStep = SelectImpl.ConditionStep.WHERE;
      return this.andExists(select);
   }

   public final SelectImpl whereNotExists(Select<?> select) {
      this.conditionStep = SelectImpl.ConditionStep.WHERE;
      return this.andNotExists(select);
   }

   public final SelectImpl and(Condition condition) {
      switch(this.conditionStep) {
      case WHERE:
         this.getQuery().addConditions(condition);
         break;
      case CONNECT_BY:
         this.getQuery().addConnectBy(condition);
         break;
      case HAVING:
         this.getQuery().addHaving(condition);
         break;
      case ON:
         this.joinConditions.addConditions(condition);
      }

      return this;
   }

   public final SelectImpl and(Field<Boolean> condition) {
      return this.and(DSL.condition(condition));
   }

   /** @deprecated */
   @Deprecated
   public final SelectImpl and(Boolean condition) {
      return this.and(DSL.condition(condition));
   }

   public final SelectImpl and(SQL sql) {
      return this.and(DSL.condition(sql));
   }

   public final SelectImpl and(String sql) {
      return this.and(DSL.condition(sql));
   }

   public final SelectImpl and(String sql, Object... bindings) {
      return this.and(DSL.condition(sql, bindings));
   }

   public final SelectImpl and(String sql, QueryPart... parts) {
      return this.and(DSL.condition(sql, parts));
   }

   public final SelectImpl andNot(Condition condition) {
      return this.and(condition.not());
   }

   public final SelectImpl andNot(Field<Boolean> condition) {
      return this.andNot(DSL.condition(condition));
   }

   /** @deprecated */
   @Deprecated
   public final SelectImpl andNot(Boolean condition) {
      return this.andNot(DSL.condition(condition));
   }

   public final SelectImpl andExists(Select<?> select) {
      return this.and(DSL.exists(select));
   }

   public final SelectImpl andNotExists(Select<?> select) {
      return this.and(DSL.notExists(select));
   }

   public final SelectImpl or(Condition condition) {
      switch(this.conditionStep) {
      case WHERE:
         this.getQuery().addConditions(Operator.OR, condition);
         break;
      case CONNECT_BY:
         throw new IllegalStateException("Cannot connect conditions for the CONNECT BY clause using the OR operator");
      case HAVING:
         this.getQuery().addHaving(Operator.OR, condition);
         break;
      case ON:
         this.joinConditions.addConditions(Operator.OR, condition);
      }

      return this;
   }

   public final SelectImpl or(Field<Boolean> condition) {
      return this.or(DSL.condition(condition));
   }

   /** @deprecated */
   @Deprecated
   public final SelectImpl or(Boolean condition) {
      return this.or(DSL.condition(condition));
   }

   public final SelectImpl or(SQL sql) {
      return this.or(DSL.condition(sql));
   }

   public final SelectImpl or(String sql) {
      return this.or(DSL.condition(sql));
   }

   public final SelectImpl or(String sql, Object... bindings) {
      return this.or(DSL.condition(sql, bindings));
   }

   public final SelectImpl or(String sql, QueryPart... parts) {
      return this.or(DSL.condition(sql, parts));
   }

   public final SelectImpl orNot(Condition condition) {
      return this.or(condition.not());
   }

   public final SelectImpl orNot(Field<Boolean> condition) {
      return this.orNot(DSL.condition(condition));
   }

   /** @deprecated */
   @Deprecated
   public final SelectImpl orNot(Boolean condition) {
      return this.orNot(DSL.condition(condition));
   }

   public final SelectImpl orExists(Select<?> select) {
      return this.or(DSL.exists(select));
   }

   public final SelectImpl orNotExists(Select<?> select) {
      return this.or(DSL.notExists(select));
   }

   public final SelectImpl connectBy(Condition condition) {
      this.conditionStep = SelectImpl.ConditionStep.CONNECT_BY;
      this.getQuery().addConnectBy(condition);
      return this;
   }

   public final SelectImpl connectBy(Field<Boolean> condition) {
      return this.connectBy(DSL.condition(condition));
   }

   /** @deprecated */
   @Deprecated
   public final SelectImpl connectBy(Boolean condition) {
      return this.connectBy(DSL.condition(condition));
   }

   public final SelectImpl connectBy(SQL sql) {
      return this.connectBy(DSL.condition(sql));
   }

   public final SelectImpl connectBy(String sql) {
      return this.connectBy(DSL.condition(sql));
   }

   public final SelectImpl connectBy(String sql, Object... bindings) {
      return this.connectBy(DSL.condition(sql, bindings));
   }

   public final SelectImpl connectBy(String sql, QueryPart... parts) {
      return this.connectBy(DSL.condition(sql, parts));
   }

   public final SelectImpl connectByNoCycle(Condition condition) {
      this.conditionStep = SelectImpl.ConditionStep.CONNECT_BY;
      this.getQuery().addConnectByNoCycle(condition);
      return this;
   }

   public final SelectImpl connectByNoCycle(Field<Boolean> condition) {
      return this.connectByNoCycle(DSL.condition(condition));
   }

   /** @deprecated */
   @Deprecated
   public final SelectImpl connectByNoCycle(Boolean condition) {
      return this.connectByNoCycle(DSL.condition(condition));
   }

   public final SelectImpl connectByNoCycle(SQL sql) {
      return this.connectByNoCycle(DSL.condition(sql));
   }

   public final SelectImpl connectByNoCycle(String sql) {
      return this.connectByNoCycle(DSL.condition(sql));
   }

   public final SelectImpl connectByNoCycle(String sql, Object... bindings) {
      return this.connectByNoCycle(DSL.condition(sql, bindings));
   }

   public final SelectImpl connectByNoCycle(String sql, QueryPart... parts) {
      return this.connectByNoCycle(DSL.condition(sql, parts));
   }

   public final SelectImpl startWith(Condition condition) {
      this.getQuery().setConnectByStartWith(condition);
      return this;
   }

   public final SelectImpl startWith(Field<Boolean> condition) {
      return this.startWith(DSL.condition(condition));
   }

   /** @deprecated */
   @Deprecated
   public final SelectImpl startWith(Boolean condition) {
      return this.startWith(DSL.condition(condition));
   }

   public final SelectImpl startWith(SQL sql) {
      return this.startWith(DSL.condition(sql));
   }

   public final SelectImpl startWith(String sql) {
      return this.startWith(DSL.condition(sql));
   }

   public final SelectImpl startWith(String sql, Object... bindings) {
      return this.startWith(DSL.condition(sql, bindings));
   }

   public final SelectImpl startWith(String sql, QueryPart... parts) {
      return this.startWith(DSL.condition(sql, parts));
   }

   public final SelectImpl groupBy(GroupField... fields) {
      this.getQuery().addGroupBy(fields);
      return this;
   }

   public final SelectImpl groupBy(Collection<? extends GroupField> fields) {
      this.getQuery().addGroupBy(fields);
      return this;
   }

   public final SelectSeekStep1 orderBy(Field t1) {
      return this.orderBy(t1);
   }

   public final SelectSeekStep2 orderBy(Field t1, Field t2) {
      return this.orderBy(t1, t2);
   }

   public final SelectSeekStep3 orderBy(Field t1, Field t2, Field t3) {
      return this.orderBy(t1, t2, t3);
   }

   public final SelectSeekStep4 orderBy(Field t1, Field t2, Field t3, Field t4) {
      return this.orderBy(t1, t2, t3, t4);
   }

   public final SelectSeekStep5 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5) {
      return this.orderBy(t1, t2, t3, t4, t5);
   }

   public final SelectSeekStep6 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6) {
      return this.orderBy(t1, t2, t3, t4, t5, t6);
   }

   public final SelectSeekStep7 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7);
   }

   public final SelectSeekStep8 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final SelectSeekStep9 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final SelectSeekStep10 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final SelectSeekStep11 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final SelectSeekStep12 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final SelectSeekStep13 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final SelectSeekStep14 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final SelectSeekStep15 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final SelectSeekStep16 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final SelectSeekStep17 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final SelectSeekStep18 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final SelectSeekStep19 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final SelectSeekStep20 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final SelectSeekStep21 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final SelectSeekStep22 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21, Field t22) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final SelectImpl orderBy(Field<?>... fields) {
      this.getQuery().addOrderBy(fields);
      return this;
   }

   public final SelectSeekStep1 orderBy(SortField t1) {
      return this.orderBy(t1);
   }

   public final SelectSeekStep2 orderBy(SortField t1, SortField t2) {
      return this.orderBy(t1, t2);
   }

   public final SelectSeekStep3 orderBy(SortField t1, SortField t2, SortField t3) {
      return this.orderBy(t1, t2, t3);
   }

   public final SelectSeekStep4 orderBy(SortField t1, SortField t2, SortField t3, SortField t4) {
      return this.orderBy(t1, t2, t3, t4);
   }

   public final SelectSeekStep5 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5) {
      return this.orderBy(t1, t2, t3, t4, t5);
   }

   public final SelectSeekStep6 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6) {
      return this.orderBy(t1, t2, t3, t4, t5, t6);
   }

   public final SelectSeekStep7 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7);
   }

   public final SelectSeekStep8 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final SelectSeekStep9 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final SelectSeekStep10 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final SelectSeekStep11 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final SelectSeekStep12 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final SelectSeekStep13 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final SelectSeekStep14 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13, SortField t14) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final SelectSeekStep15 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13, SortField t14, SortField t15) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final SelectSeekStep16 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13, SortField t14, SortField t15, SortField t16) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final SelectSeekStep17 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13, SortField t14, SortField t15, SortField t16, SortField t17) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final SelectSeekStep18 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13, SortField t14, SortField t15, SortField t16, SortField t17, SortField t18) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final SelectSeekStep19 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13, SortField t14, SortField t15, SortField t16, SortField t17, SortField t18, SortField t19) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final SelectSeekStep20 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13, SortField t14, SortField t15, SortField t16, SortField t17, SortField t18, SortField t19, SortField t20) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final SelectSeekStep21 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13, SortField t14, SortField t15, SortField t16, SortField t17, SortField t18, SortField t19, SortField t20, SortField t21) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final SelectSeekStep22 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13, SortField t14, SortField t15, SortField t16, SortField t17, SortField t18, SortField t19, SortField t20, SortField t21, SortField t22) {
      return this.orderBy(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final SelectImpl orderBy(SortField<?>... fields) {
      this.getQuery().addOrderBy(fields);
      return this;
   }

   public final SelectImpl orderBy(Collection<? extends SortField<?>> fields) {
      this.getQuery().addOrderBy(fields);
      return this;
   }

   public final SelectImpl orderBy(int... fieldIndexes) {
      this.getQuery().addOrderBy(fieldIndexes);
      return this;
   }

   public final SelectImpl orderSiblingsBy(Field<?>... fields) {
      this.getQuery().addOrderBy(fields);
      this.getQuery().setOrderBySiblings(true);
      return this;
   }

   public final SelectImpl orderSiblingsBy(SortField<?>... fields) {
      this.getQuery().addOrderBy(fields);
      this.getQuery().setOrderBySiblings(true);
      return this;
   }

   public final SelectImpl orderSiblingsBy(Collection<? extends SortField<?>> fields) {
      this.getQuery().addOrderBy(fields);
      this.getQuery().setOrderBySiblings(true);
      return this;
   }

   public final SelectImpl orderSiblingsBy(int... fieldIndexes) {
      this.getQuery().addOrderBy(fieldIndexes);
      this.getQuery().setOrderBySiblings(true);
      return this;
   }

   public final SelectSeekLimitStep<R> seek(Object t1) {
      return this.seek(t1);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1) {
      return this.seekBefore(t1);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1) {
      return this.seekAfter(t1);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2) {
      return this.seek(t1, t2);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2) {
      return this.seekBefore(t1, t2);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2) {
      return this.seekAfter(t1, t2);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3) {
      return this.seek(t1, t2, t3);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3) {
      return this.seekBefore(t1, t2, t3);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3) {
      return this.seekAfter(t1, t2, t3);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4) {
      return this.seek(t1, t2, t3, t4);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4) {
      return this.seekBefore(t1, t2, t3, t4);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4) {
      return this.seekAfter(t1, t2, t3, t4);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5) {
      return this.seek(t1, t2, t3, t4, t5);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5) {
      return this.seekBefore(t1, t2, t3, t4, t5);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5) {
      return this.seekAfter(t1, t2, t3, t4, t5);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6) {
      return this.seek(t1, t2, t3, t4, t5, t6);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20, Object t21) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20, Object t21) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20, Object t21) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20, Object t21, Object t22) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20, Object t21, Object t22) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20, Object t21, Object t22) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final SelectSeekLimitStep<R> seek(Field t1) {
      return this.seek(t1);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1) {
      return this.seekBefore(t1);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1) {
      return this.seekAfter(t1);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2) {
      return this.seek(t1, t2);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2) {
      return this.seekBefore(t1, t2);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2) {
      return this.seekAfter(t1, t2);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3) {
      return this.seek(t1, t2, t3);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3) {
      return this.seekBefore(t1, t2, t3);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3) {
      return this.seekAfter(t1, t2, t3);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4) {
      return this.seek(t1, t2, t3, t4);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4) {
      return this.seekBefore(t1, t2, t3, t4);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4) {
      return this.seekAfter(t1, t2, t3, t4);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5) {
      return this.seek(t1, t2, t3, t4, t5);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5) {
      return this.seekBefore(t1, t2, t3, t4, t5);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5) {
      return this.seekAfter(t1, t2, t3, t4, t5);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6) {
      return this.seek(t1, t2, t3, t4, t5, t6);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21);
   }

   public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21, Field t22) {
      return this.seek(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21, Field t22) {
      return this.seekBefore(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21, Field t22) {
      return this.seekAfter(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22);
   }

   public final SelectSeekLimitStep<R> seek(Object... values) {
      this.getQuery().addSeekAfter((Collection)Tools.fields(values));
      return this;
   }

   public final SelectSeekLimitStep<R> seek(Field<?>... fields) {
      this.getQuery().addSeekAfter(fields);
      return this;
   }

   public SelectSeekLimitStep<R> seekAfter(Object... values) {
      this.getQuery().addSeekAfter((Collection)Tools.fields(values));
      return this;
   }

   public SelectSeekLimitStep<R> seekAfter(Field<?>... fields) {
      this.getQuery().addSeekAfter(fields);
      return this;
   }

   public SelectSeekLimitStep<R> seekBefore(Object... values) {
      this.getQuery().addSeekBefore((Collection)Tools.fields(values));
      return this;
   }

   public SelectSeekLimitStep<R> seekBefore(Field<?>... fields) {
      this.getQuery().addSeekBefore(fields);
      return this;
   }

   public final SelectImpl limit(int l) {
      this.limit = l;
      this.limitParam = null;
      return this.limitOffset();
   }

   public final SelectImpl limit(Param<Integer> l) {
      this.limit = null;
      this.limitParam = l;
      return this.limitOffset();
   }

   public final SelectImpl limit(int o, int l) {
      this.offset = o;
      this.offsetParam = null;
      this.limit = l;
      this.limitParam = null;
      return this.limitOffset();
   }

   public final SelectImpl limit(int o, Param<Integer> l) {
      this.offset = o;
      this.offsetParam = null;
      this.limit = null;
      this.limitParam = l;
      return this.limitOffset();
   }

   public final SelectImpl limit(Param<Integer> o, int l) {
      this.offset = null;
      this.offsetParam = o;
      this.limit = l;
      this.limitParam = null;
      return this.limitOffset();
   }

   public final SelectImpl limit(Param<Integer> o, Param<Integer> l) {
      this.offset = null;
      this.offsetParam = o;
      this.limit = null;
      this.limitParam = l;
      return this.limitOffset();
   }

   public final SelectImpl offset(int o) {
      this.offset = o;
      this.offsetParam = null;
      return this.limitOffset();
   }

   public final SelectImpl offset(Param<Integer> o) {
      this.offset = null;
      this.offsetParam = o;
      return this.limitOffset();
   }

   private final SelectImpl limitOffset() {
      if (this.limit != null) {
         if (this.offset != null) {
            this.getQuery().addLimit(this.offset, this.limit);
         } else if (this.offsetParam != null) {
            this.getQuery().addLimit(this.offsetParam, this.limit);
         } else {
            this.getQuery().addLimit(this.limit);
         }
      } else if (this.limitParam != null) {
         if (this.offset != null) {
            this.getQuery().addLimit(this.offset, this.limitParam);
         } else if (this.offsetParam != null) {
            this.getQuery().addLimit(this.offsetParam, this.limitParam);
         } else {
            this.getQuery().addLimit(this.limitParam);
         }
      } else if (this.offset != null) {
         this.getQuery().addOffset(this.offset);
      } else if (this.offsetParam != null) {
         this.getQuery().addOffset(this.offsetParam);
      }

      return this;
   }

   public final SelectImpl forUpdate() {
      this.getQuery().setForUpdate(true);
      return this;
   }

   public final SelectImpl of(Field<?>... fields) {
      this.getQuery().setForUpdateOf(fields);
      return this;
   }

   public final SelectImpl of(Collection<? extends Field<?>> fields) {
      this.getQuery().setForUpdateOf(fields);
      return this;
   }

   public final SelectImpl of(Table<?>... tables) {
      this.getQuery().setForUpdateOf(tables);
      return this;
   }

   public final SelectImpl noWait() {
      this.getQuery().setForUpdateNoWait();
      return this;
   }

   public final SelectImpl skipLocked() {
      this.getQuery().setForUpdateSkipLocked();
      return this;
   }

   public final SelectImpl forShare() {
      this.getQuery().setForShare(true);
      return this;
   }

   public final SelectImpl union(Select<? extends R> select) {
      return new SelectImpl(((Select)this.getDelegate()).union(select));
   }

   public final SelectImpl unionAll(Select<? extends R> select) {
      return new SelectImpl(((Select)this.getDelegate()).unionAll(select));
   }

   public final SelectImpl except(Select<? extends R> select) {
      return new SelectImpl(((Select)this.getDelegate()).except(select));
   }

   public final SelectImpl exceptAll(Select<? extends R> select) {
      return new SelectImpl(((Select)this.getDelegate()).exceptAll(select));
   }

   public final SelectImpl intersect(Select<? extends R> select) {
      return new SelectImpl(((Select)this.getDelegate()).intersect(select));
   }

   public final SelectImpl intersectAll(Select<? extends R> select) {
      return new SelectImpl(((Select)this.getDelegate()).intersectAll(select));
   }

   public final SelectImpl having(Condition... conditions) {
      this.conditionStep = SelectImpl.ConditionStep.HAVING;
      this.getQuery().addHaving(conditions);
      return this;
   }

   public final SelectImpl having(Collection<? extends Condition> conditions) {
      this.conditionStep = SelectImpl.ConditionStep.HAVING;
      this.getQuery().addHaving(conditions);
      return this;
   }

   public final SelectImpl having(Field<Boolean> condition) {
      return this.having(DSL.condition(condition));
   }

   /** @deprecated */
   @Deprecated
   public final SelectImpl having(Boolean condition) {
      return this.having(DSL.condition(condition));
   }

   public final SelectImpl having(SQL sql) {
      return this.having(DSL.condition(sql));
   }

   public final SelectImpl having(String sql) {
      return this.having(DSL.condition(sql));
   }

   public final SelectImpl having(String sql, Object... bindings) {
      return this.having(DSL.condition(sql, bindings));
   }

   public final SelectImpl having(String sql, QueryPart... parts) {
      return this.having(DSL.condition(sql, parts));
   }

   public final SelectImpl window(WindowDefinition... definitions) {
      this.getQuery().addWindow(definitions);
      return this;
   }

   public final SelectImpl window(Collection<? extends WindowDefinition> definitions) {
      this.getQuery().addWindow(definitions);
      return this;
   }

   public final SelectImpl on(Condition... conditions) {
      this.conditionStep = SelectImpl.ConditionStep.ON;
      this.joinConditions = new ConditionProviderImpl();
      this.joinConditions.addConditions(conditions);
      this.getQuery().addJoin(this.joinTable, this.joinType, this.joinConditions);
      this.joinTable = null;
      this.joinPartitionBy = null;
      this.joinType = null;
      return this;
   }

   public final SelectImpl on(Field<Boolean> condition) {
      return this.on(DSL.condition(condition));
   }

   /** @deprecated */
   @Deprecated
   public final SelectImpl on(Boolean condition) {
      return this.on(DSL.condition(condition));
   }

   public final SelectImpl on(SQL sql) {
      return this.on(DSL.condition(sql));
   }

   public final SelectImpl on(String sql) {
      return this.on(DSL.condition(sql));
   }

   public final SelectImpl on(String sql, Object... bindings) {
      return this.on(DSL.condition(sql, bindings));
   }

   public final SelectImpl on(String sql, QueryPart... parts) {
      return this.on(DSL.condition(sql, parts));
   }

   public final SelectImpl onKey() {
      this.conditionStep = SelectImpl.ConditionStep.ON;
      this.getQuery().addJoinOnKey(this.joinTable, this.joinType);
      this.joinTable = null;
      this.joinPartitionBy = null;
      this.joinType = null;
      return this;
   }

   public final SelectImpl onKey(TableField<?, ?>... keyFields) {
      this.conditionStep = SelectImpl.ConditionStep.ON;
      this.getQuery().addJoinOnKey(this.joinTable, this.joinType, keyFields);
      this.joinTable = null;
      this.joinPartitionBy = null;
      this.joinType = null;
      return this;
   }

   public final SelectImpl onKey(ForeignKey<?, ?> key) {
      this.conditionStep = SelectImpl.ConditionStep.ON;
      this.getQuery().addJoinOnKey(this.joinTable, this.joinType, key);
      this.joinTable = null;
      this.joinPartitionBy = null;
      this.joinType = null;
      return this;
   }

   public final SelectImpl using(Field<?>... fields) {
      return this.using((Collection)Arrays.asList(fields));
   }

   public final SelectImpl using(Collection<? extends Field<?>> fields) {
      this.getQuery().addJoinUsing(this.joinTable, this.joinType, fields);
      this.joinTable = null;
      this.joinPartitionBy = null;
      this.joinType = null;
      return this;
   }

   public final SelectImpl join(TableLike<?> table) {
      return this.innerJoin(table);
   }

   public final SelectImpl innerJoin(TableLike<?> table) {
      return this.join(table, JoinType.JOIN);
   }

   public final SelectImpl leftJoin(TableLike<?> table) {
      return this.leftOuterJoin(table);
   }

   public final SelectImpl leftOuterJoin(TableLike<?> table) {
      return this.join(table, JoinType.LEFT_OUTER_JOIN);
   }

   public final SelectImpl rightJoin(TableLike<?> table) {
      return this.rightOuterJoin(table);
   }

   public final SelectImpl rightOuterJoin(TableLike<?> table) {
      return this.join(table, JoinType.RIGHT_OUTER_JOIN);
   }

   public final SelectImpl fullOuterJoin(TableLike<?> table) {
      return this.join(table, JoinType.FULL_OUTER_JOIN);
   }

   public final SelectImpl join(TableLike<?> table, JoinType type) {
      switch(type) {
      case CROSS_JOIN:
      case NATURAL_JOIN:
      case NATURAL_LEFT_OUTER_JOIN:
      case NATURAL_RIGHT_OUTER_JOIN:
      case CROSS_APPLY:
      case OUTER_APPLY:
         this.getQuery().addJoin(table, type);
         this.joinTable = null;
         this.joinPartitionBy = null;
         this.joinType = null;
         return this;
      default:
         this.conditionStep = SelectImpl.ConditionStep.ON;
         this.joinTable = table;
         this.joinType = type;
         this.joinPartitionBy = null;
         this.joinConditions = null;
         return this;
      }
   }

   public final SelectImpl crossJoin(TableLike<?> table) {
      return this.join(table, JoinType.CROSS_JOIN);
   }

   public final SelectImpl naturalJoin(TableLike<?> table) {
      return this.join(table, JoinType.NATURAL_JOIN);
   }

   public final SelectImpl naturalLeftOuterJoin(TableLike<?> table) {
      return this.join(table, JoinType.NATURAL_LEFT_OUTER_JOIN);
   }

   public final SelectImpl naturalRightOuterJoin(TableLike<?> table) {
      return this.join(table, JoinType.NATURAL_RIGHT_OUTER_JOIN);
   }

   public final SelectImpl leftSemiJoin(TableLike<?> table) {
      return this.join(table, JoinType.LEFT_SEMI_JOIN);
   }

   public final SelectImpl leftAntiJoin(TableLike<?> table) {
      return this.join(table, JoinType.LEFT_ANTI_JOIN);
   }

   public final SelectImpl crossApply(TableLike<?> table) {
      return this.join(table, JoinType.CROSS_APPLY);
   }

   public final SelectImpl outerApply(TableLike<?> table) {
      return this.join(table, JoinType.OUTER_APPLY);
   }

   public final SelectImpl straightJoin(TableLike<?> table) {
      return this.join(table, JoinType.STRAIGHT_JOIN);
   }

   public final SelectImpl join(SQL sql) {
      return this.innerJoin(sql);
   }

   public final SelectImpl join(String sql) {
      return this.innerJoin(sql);
   }

   public final SelectImpl join(String sql, Object... bindings) {
      return this.innerJoin(sql, bindings);
   }

   public final SelectImpl join(String sql, QueryPart... parts) {
      return this.innerJoin(sql, parts);
   }

   public final SelectImpl join(Name name) {
      return this.innerJoin((TableLike)DSL.table(name));
   }

   public final SelectImpl innerJoin(SQL sql) {
      return this.innerJoin((TableLike)DSL.table(sql));
   }

   public final SelectImpl innerJoin(String sql) {
      return this.innerJoin((TableLike)DSL.table(sql));
   }

   public final SelectImpl innerJoin(String sql, Object... bindings) {
      return this.innerJoin((TableLike)DSL.table(sql, bindings));
   }

   public final SelectImpl innerJoin(String sql, QueryPart... parts) {
      return this.innerJoin((TableLike)DSL.table(sql, parts));
   }

   public final SelectImpl innerJoin(Name name) {
      return this.innerJoin((TableLike)DSL.table(name));
   }

   public final SelectImpl leftJoin(SQL sql) {
      return this.leftOuterJoin(sql);
   }

   public final SelectImpl leftJoin(String sql) {
      return this.leftOuterJoin(sql);
   }

   public final SelectImpl leftJoin(String sql, Object... bindings) {
      return this.leftOuterJoin(sql, bindings);
   }

   public final SelectImpl leftJoin(String sql, QueryPart... parts) {
      return this.leftOuterJoin(sql, parts);
   }

   public final SelectImpl leftJoin(Name name) {
      return this.leftOuterJoin((TableLike)DSL.table(name));
   }

   public final SelectImpl leftOuterJoin(SQL sql) {
      return this.leftOuterJoin((TableLike)DSL.table(sql));
   }

   public final SelectImpl leftOuterJoin(String sql) {
      return this.leftOuterJoin((TableLike)DSL.table(sql));
   }

   public final SelectImpl leftOuterJoin(String sql, Object... bindings) {
      return this.leftOuterJoin((TableLike)DSL.table(sql, bindings));
   }

   public final SelectImpl leftOuterJoin(String sql, QueryPart... parts) {
      return this.leftOuterJoin((TableLike)DSL.table(sql, parts));
   }

   public final SelectImpl leftOuterJoin(Name name) {
      return this.leftOuterJoin((TableLike)DSL.table(name));
   }

   public final SelectImpl rightJoin(SQL sql) {
      return this.rightOuterJoin(sql);
   }

   public final SelectImpl rightJoin(String sql) {
      return this.rightOuterJoin(sql);
   }

   public final SelectImpl rightJoin(String sql, Object... bindings) {
      return this.rightOuterJoin(sql, bindings);
   }

   public final SelectImpl rightJoin(String sql, QueryPart... parts) {
      return this.rightOuterJoin(sql, parts);
   }

   public final SelectImpl rightJoin(Name name) {
      return this.rightOuterJoin((TableLike)DSL.table(name));
   }

   public final SelectImpl rightOuterJoin(SQL sql) {
      return this.rightOuterJoin((TableLike)DSL.table(sql));
   }

   public final SelectImpl rightOuterJoin(String sql) {
      return this.rightOuterJoin((TableLike)DSL.table(sql));
   }

   public final SelectImpl rightOuterJoin(String sql, Object... bindings) {
      return this.rightOuterJoin((TableLike)DSL.table(sql, bindings));
   }

   public final SelectImpl rightOuterJoin(String sql, QueryPart... parts) {
      return this.rightOuterJoin((TableLike)DSL.table(sql, parts));
   }

   public final SelectImpl rightOuterJoin(Name name) {
      return this.rightOuterJoin((TableLike)DSL.table(name));
   }

   public final SelectOnStep<R> fullOuterJoin(SQL sql) {
      return this.fullOuterJoin((TableLike)DSL.table(sql));
   }

   public final SelectOnStep<R> fullOuterJoin(String sql) {
      return this.fullOuterJoin((TableLike)DSL.table(sql));
   }

   public final SelectOnStep<R> fullOuterJoin(String sql, Object... bindings) {
      return this.fullOuterJoin((TableLike)DSL.table(sql, bindings));
   }

   public final SelectOnStep<R> fullOuterJoin(String sql, QueryPart... parts) {
      return this.fullOuterJoin((TableLike)DSL.table(sql, parts));
   }

   public final SelectImpl fullOuterJoin(Name name) {
      return this.fullOuterJoin((TableLike)DSL.table(name));
   }

   public final SelectJoinStep<R> crossJoin(SQL sql) {
      return this.crossJoin((TableLike)DSL.table(sql));
   }

   public final SelectJoinStep<R> crossJoin(String sql) {
      return this.crossJoin((TableLike)DSL.table(sql));
   }

   public final SelectJoinStep<R> crossJoin(String sql, Object... bindings) {
      return this.crossJoin((TableLike)DSL.table(sql, bindings));
   }

   public final SelectJoinStep<R> crossJoin(String sql, QueryPart... parts) {
      return this.crossJoin((TableLike)DSL.table(sql, parts));
   }

   public final SelectImpl crossJoin(Name name) {
      return this.crossJoin((TableLike)DSL.table(name));
   }

   public final SelectImpl naturalJoin(SQL sql) {
      return this.naturalJoin((TableLike)DSL.table(sql));
   }

   public final SelectImpl naturalJoin(String sql) {
      return this.naturalJoin((TableLike)DSL.table(sql));
   }

   public final SelectImpl naturalJoin(String sql, Object... bindings) {
      return this.naturalJoin((TableLike)DSL.table(sql, bindings));
   }

   public final SelectImpl naturalJoin(String sql, QueryPart... parts) {
      return this.naturalJoin((TableLike)DSL.table(sql, parts));
   }

   public final SelectImpl naturalJoin(Name name) {
      return this.naturalJoin((TableLike)DSL.table(name));
   }

   public final SelectImpl naturalLeftOuterJoin(SQL sql) {
      return this.naturalLeftOuterJoin((TableLike)DSL.table(sql));
   }

   public final SelectImpl naturalLeftOuterJoin(String sql) {
      return this.naturalLeftOuterJoin((TableLike)DSL.table(sql));
   }

   public final SelectImpl naturalLeftOuterJoin(String sql, Object... bindings) {
      return this.naturalLeftOuterJoin((TableLike)DSL.table(sql, bindings));
   }

   public final SelectImpl naturalLeftOuterJoin(String sql, QueryPart... parts) {
      return this.naturalLeftOuterJoin((TableLike)DSL.table(sql, parts));
   }

   public final SelectImpl naturalLeftOuterJoin(Name name) {
      return this.naturalLeftOuterJoin((TableLike)DSL.table(name));
   }

   public final SelectImpl naturalRightOuterJoin(SQL sql) {
      return this.naturalRightOuterJoin((TableLike)DSL.table(sql));
   }

   public final SelectImpl naturalRightOuterJoin(String sql) {
      return this.naturalRightOuterJoin((TableLike)DSL.table(sql));
   }

   public final SelectImpl naturalRightOuterJoin(String sql, Object... bindings) {
      return this.naturalRightOuterJoin((TableLike)DSL.table(sql, bindings));
   }

   public final SelectImpl naturalRightOuterJoin(String sql, QueryPart... parts) {
      return this.naturalRightOuterJoin((TableLike)DSL.table(sql, parts));
   }

   public final SelectImpl naturalRightOuterJoin(Name name) {
      return this.naturalRightOuterJoin((TableLike)DSL.table(name));
   }

   public final SelectImpl crossApply(SQL sql) {
      return this.crossApply((TableLike)DSL.table(sql));
   }

   public final SelectImpl crossApply(String sql) {
      return this.crossApply((TableLike)DSL.table(sql));
   }

   public final SelectImpl crossApply(String sql, Object... bindings) {
      return this.crossApply((TableLike)DSL.table(sql, bindings));
   }

   public final SelectImpl crossApply(String sql, QueryPart... parts) {
      return this.crossApply((TableLike)DSL.table(sql, parts));
   }

   public final SelectImpl crossApply(Name name) {
      return this.crossApply((TableLike)DSL.table(name));
   }

   public final SelectImpl outerApply(SQL sql) {
      return this.outerApply((TableLike)DSL.table(sql));
   }

   public final SelectImpl outerApply(String sql) {
      return this.outerApply((TableLike)DSL.table(sql));
   }

   public final SelectImpl outerApply(String sql, Object... bindings) {
      return this.outerApply((TableLike)DSL.table(sql, bindings));
   }

   public final SelectImpl outerApply(String sql, QueryPart... parts) {
      return this.outerApply((TableLike)DSL.table(sql, parts));
   }

   public final SelectImpl outerApply(Name name) {
      return this.outerApply((TableLike)DSL.table(name));
   }

   public final SelectImpl straightJoin(SQL sql) {
      return this.straightJoin((TableLike)DSL.table(sql));
   }

   public final SelectImpl straightJoin(String sql) {
      return this.straightJoin((TableLike)DSL.table(sql));
   }

   public final SelectImpl straightJoin(String sql, Object... bindings) {
      return this.straightJoin((TableLike)DSL.table(sql, bindings));
   }

   public final SelectImpl straightJoin(String sql, QueryPart... parts) {
      return this.straightJoin((TableLike)DSL.table(sql, parts));
   }

   public final SelectImpl straightJoin(Name name) {
      return this.straightJoin((TableLike)DSL.table(name));
   }

   public final ResultQuery<R> maxRows(int rows) {
      return ((Select)this.getDelegate()).maxRows(rows);
   }

   public final ResultQuery<R> fetchSize(int rows) {
      return ((Select)this.getDelegate()).fetchSize(rows);
   }

   public final ResultQuery<R> resultSetConcurrency(int resultSetConcurrency) {
      return ((Select)this.getDelegate()).resultSetConcurrency(resultSetConcurrency);
   }

   public final ResultQuery<R> resultSetType(int resultSetType) {
      return ((Select)this.getDelegate()).resultSetType(resultSetType);
   }

   public final ResultQuery<R> resultSetHoldability(int resultSetHoldability) {
      return ((Select)this.getDelegate()).resultSetHoldability(resultSetHoldability);
   }

   public final ResultQuery<R> intern(Field<?>... fields) {
      return ((Select)this.getDelegate()).intern(fields);
   }

   public final ResultQuery<R> intern(int... fieldIndexes) {
      return ((Select)this.getDelegate()).intern(fieldIndexes);
   }

   public final ResultQuery<R> intern(String... fieldNames) {
      return ((Select)this.getDelegate()).intern(fieldNames);
   }

   public final ResultQuery<R> intern(Name... fieldNames) {
      return ((Select)this.getDelegate()).intern(fieldNames);
   }

   public final Class<? extends R> getRecordType() {
      return ((Select)this.getDelegate()).getRecordType();
   }

   public final List<Field<?>> getSelect() {
      return ((Select)this.getDelegate()).getSelect();
   }

   public final Result<R> getResult() {
      return ((Select)this.getDelegate()).getResult();
   }

   public final Result<R> fetch() {
      return ((Select)this.getDelegate()).fetch();
   }

   public final ResultSet fetchResultSet() {
      return ((Select)this.getDelegate()).fetchResultSet();
   }

   public final Iterator<R> iterator() {
      return ((Select)this.getDelegate()).iterator();
   }

   public final Stream<R> fetchStream() {
      return ((Select)this.getDelegate()).fetchStream();
   }

   public final Stream<R> stream() {
      return ((Select)this.getDelegate()).stream();
   }

   public final Cursor<R> fetchLazy() {
      return ((Select)this.getDelegate()).fetchLazy();
   }

   /** @deprecated */
   @Deprecated
   public final Cursor<R> fetchLazy(int fetchSize) {
      return ((Select)this.getDelegate()).fetchLazy(fetchSize);
   }

   public final Results fetchMany() {
      return ((Select)this.getDelegate()).fetchMany();
   }

   public final <T> List<T> fetch(Field<T> field) {
      return ((Select)this.getDelegate()).fetch(field);
   }

   public final <T> List<T> fetch(Field<?> field, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetch(field, type);
   }

   public final <T, U> List<U> fetch(Field<T> field, Converter<? super T, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetch(field, converter);
   }

   public final List<?> fetch(int fieldIndex) {
      return ((Select)this.getDelegate()).fetch(fieldIndex);
   }

   public final <T> List<T> fetch(int fieldIndex, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetch(fieldIndex, type);
   }

   public final <U> List<U> fetch(int fieldIndex, Converter<?, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetch(fieldIndex, converter);
   }

   public final List<?> fetch(String fieldName) {
      return ((Select)this.getDelegate()).fetch(fieldName);
   }

   public final <T> List<T> fetch(String fieldName, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetch(fieldName, type);
   }

   public final <U> List<U> fetch(String fieldName, Converter<?, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetch(fieldName, converter);
   }

   public final List<?> fetch(Name fieldName) {
      return ((Select)this.getDelegate()).fetch(fieldName);
   }

   public final <T> List<T> fetch(Name fieldName, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetch(fieldName, type);
   }

   public final <U> List<U> fetch(Name fieldName, Converter<?, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetch(fieldName, converter);
   }

   public final <T> T fetchOne(Field<T> field) {
      return ((Select)this.getDelegate()).fetchOne(field);
   }

   public final <T> T fetchOne(Field<?> field, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchOne(field, type);
   }

   public final <T, U> U fetchOne(Field<T> field, Converter<? super T, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchOne(field, converter);
   }

   public final Object fetchOne(int fieldIndex) {
      return ((Select)this.getDelegate()).fetchOne(fieldIndex);
   }

   public final <T> T fetchOne(int fieldIndex, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchOne(fieldIndex, type);
   }

   public final <U> U fetchOne(int fieldIndex, Converter<?, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchOne(fieldIndex, converter);
   }

   public final Object fetchOne(String fieldName) {
      return ((Select)this.getDelegate()).fetchOne(fieldName);
   }

   public final <T> T fetchOne(String fieldName, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchOne(fieldName, type);
   }

   public final <U> U fetchOne(String fieldName, Converter<?, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchOne(fieldName, converter);
   }

   public final Object fetchOne(Name fieldName) {
      return ((Select)this.getDelegate()).fetchOne(fieldName);
   }

   public final <T> T fetchOne(Name fieldName, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchOne(fieldName, type);
   }

   public final <U> U fetchOne(Name fieldName, Converter<?, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchOne(fieldName, converter);
   }

   public final R fetchOne() {
      return ((Select)this.getDelegate()).fetchOne();
   }

   public final <E> E fetchOne(RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchOne(mapper);
   }

   public final Map<String, Object> fetchOneMap() {
      return ((Select)this.getDelegate()).fetchOneMap();
   }

   public final Object[] fetchOneArray() {
      return ((Select)this.getDelegate()).fetchOneArray();
   }

   public final <E> E fetchOneInto(Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchOneInto(type);
   }

   public final <Z extends Record> Z fetchOneInto(Table<Z> table) {
      return ((Select)this.getDelegate()).fetchOneInto(table);
   }

   public final <T> Optional<T> fetchOptional(Field<T> field) {
      return ((Select)this.getDelegate()).fetchOptional(field);
   }

   public final <T> Optional<T> fetchOptional(Field<?> field, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchOptional(field, type);
   }

   public final <T, U> Optional<U> fetchOptional(Field<T> field, Converter<? super T, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchOptional(field, converter);
   }

   public final Optional<?> fetchOptional(int fieldIndex) {
      return ((Select)this.getDelegate()).fetchOptional(fieldIndex);
   }

   public final <T> Optional<T> fetchOptional(int fieldIndex, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchOptional(fieldIndex, type);
   }

   public final <U> Optional<U> fetchOptional(int fieldIndex, Converter<?, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchOptional(fieldIndex, converter);
   }

   public final Optional<?> fetchOptional(String fieldName) {
      return ((Select)this.getDelegate()).fetchOptional(fieldName);
   }

   public final <T> Optional<T> fetchOptional(String fieldName, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchOptional(fieldName, type);
   }

   public final <U> Optional<U> fetchOptional(String fieldName, Converter<?, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchOptional(fieldName, converter);
   }

   public final Optional<?> fetchOptional(Name fieldName) {
      return ((Select)this.getDelegate()).fetchOptional(fieldName);
   }

   public final <T> Optional<T> fetchOptional(Name fieldName, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchOptional(fieldName, type);
   }

   public final <U> Optional<U> fetchOptional(Name fieldName, Converter<?, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchOptional(fieldName, converter);
   }

   public final Optional<R> fetchOptional() {
      return ((Select)this.getDelegate()).fetchOptional();
   }

   public final <E> Optional<E> fetchOptional(RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchOptional(mapper);
   }

   public final Optional<Map<String, Object>> fetchOptionalMap() {
      return ((Select)this.getDelegate()).fetchOptionalMap();
   }

   public final Optional<Object[]> fetchOptionalArray() {
      return ((Select)this.getDelegate()).fetchOptionalArray();
   }

   public final <E> Optional<E> fetchOptionalInto(Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchOptionalInto(type);
   }

   public final <Z extends Record> Optional<Z> fetchOptionalInto(Table<Z> table) {
      return ((Select)this.getDelegate()).fetchOptionalInto(table);
   }

   public final <T> T fetchAny(Field<T> field) {
      return ((Select)this.getDelegate()).fetchAny(field);
   }

   public final <T> T fetchAny(Field<?> field, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchAny(field, type);
   }

   public final <T, U> U fetchAny(Field<T> field, Converter<? super T, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchAny(field, converter);
   }

   public final Object fetchAny(int fieldIndex) {
      return ((Select)this.getDelegate()).fetchAny(fieldIndex);
   }

   public final <T> T fetchAny(int fieldIndex, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchAny(fieldIndex, type);
   }

   public final <U> U fetchAny(int fieldIndex, Converter<?, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchAny(fieldIndex, converter);
   }

   public final Object fetchAny(String fieldName) {
      return ((Select)this.getDelegate()).fetchAny(fieldName);
   }

   public final <T> T fetchAny(String fieldName, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchAny(fieldName, type);
   }

   public final <U> U fetchAny(String fieldName, Converter<?, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchAny(fieldName, converter);
   }

   public final Object fetchAny(Name fieldName) {
      return ((Select)this.getDelegate()).fetchAny(fieldName);
   }

   public final <T> T fetchAny(Name fieldName, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchAny(fieldName, type);
   }

   public final <U> U fetchAny(Name fieldName, Converter<?, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchAny(fieldName, converter);
   }

   public final R fetchAny() {
      return ((Select)this.getDelegate()).fetchAny();
   }

   public final <E> E fetchAny(RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchAny(mapper);
   }

   public final Map<String, Object> fetchAnyMap() {
      return ((Select)this.getDelegate()).fetchAnyMap();
   }

   public final Object[] fetchAnyArray() {
      return ((Select)this.getDelegate()).fetchAnyArray();
   }

   public final <E> E fetchAnyInto(Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchAnyInto(type);
   }

   public final <Z extends Record> Z fetchAnyInto(Table<Z> table) {
      return ((Select)this.getDelegate()).fetchAnyInto(table);
   }

   public final <K> Map<K, R> fetchMap(Field<K> key) {
      return ((Select)this.getDelegate()).fetchMap(key);
   }

   public final Map<?, R> fetchMap(int keyFieldIndex) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldIndex);
   }

   public final Map<?, R> fetchMap(String keyFieldName) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldName);
   }

   public final Map<?, R> fetchMap(Name keyFieldName) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldName);
   }

   public final <K, V> Map<K, V> fetchMap(Field<K> key, Field<V> value) {
      return ((Select)this.getDelegate()).fetchMap(key, value);
   }

   public final Map<?, ?> fetchMap(int keyFieldIndex, int valueFieldIndex) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldIndex, valueFieldIndex);
   }

   public final Map<?, ?> fetchMap(String keyFieldName, String valueFieldName) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldName, valueFieldName);
   }

   public final Map<?, ?> fetchMap(Name keyFieldName, Name valueFieldName) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldName, valueFieldName);
   }

   public final <K, E> Map<K, E> fetchMap(Field<K> key, Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchMap(key, type);
   }

   public final <E> Map<?, E> fetchMap(int keyFieldIndex, Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldIndex, type);
   }

   public final <E> Map<?, E> fetchMap(String keyFieldName, Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldName, type);
   }

   public final <E> Map<?, E> fetchMap(Name keyFieldName, Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldName, type);
   }

   public final <K, E> Map<K, E> fetchMap(Field<K> key, RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchMap(key, mapper);
   }

   public final <E> Map<?, E> fetchMap(int keyFieldIndex, RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldIndex, mapper);
   }

   public final <E> Map<?, E> fetchMap(String keyFieldName, RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldName, mapper);
   }

   public final <E> Map<?, E> fetchMap(Name keyFieldName, RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldName, mapper);
   }

   public final Map<Record, R> fetchMap(Field<?>[] keys) {
      return ((Select)this.getDelegate()).fetchMap(keys);
   }

   public final Map<Record, R> fetchMap(int[] keyFieldIndexes) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldIndexes);
   }

   public final Map<Record, R> fetchMap(String[] keyFieldNames) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldNames);
   }

   public final Map<Record, R> fetchMap(Name[] keyFieldNames) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldNames);
   }

   public final <E> Map<List<?>, E> fetchMap(Field<?>[] keys, Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchMap(keys, type);
   }

   public final <E> Map<List<?>, E> fetchMap(int[] keyFieldIndexes, Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldIndexes, type);
   }

   public final <E> Map<List<?>, E> fetchMap(String[] keyFieldNames, Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldNames, type);
   }

   public final <E> Map<List<?>, E> fetchMap(Name[] keyFieldNames, Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldNames, type);
   }

   public final <E> Map<List<?>, E> fetchMap(Field<?>[] keys, RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchMap(keys, mapper);
   }

   public final <E> Map<List<?>, E> fetchMap(int[] keyFieldIndexes, RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldIndexes, mapper);
   }

   public final <E> Map<List<?>, E> fetchMap(String[] keyFieldNames, RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldNames, mapper);
   }

   public final <E> Map<List<?>, E> fetchMap(Name[] keyFieldNames, RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchMap(keyFieldNames, mapper);
   }

   public final <K> Map<K, R> fetchMap(Class<? extends K> keyType) {
      return ((Select)this.getDelegate()).fetchMap(keyType);
   }

   public final <K, V> Map<K, V> fetchMap(Class<? extends K> keyType, Class<? extends V> valueType) {
      return ((Select)this.getDelegate()).fetchMap(keyType, valueType);
   }

   public final <K, V> Map<K, V> fetchMap(Class<? extends K> keyType, RecordMapper<? super R, V> valueMapper) {
      return ((Select)this.getDelegate()).fetchMap(keyType, valueMapper);
   }

   public final <K> Map<K, R> fetchMap(RecordMapper<? super R, K> keyMapper) {
      return ((Select)this.getDelegate()).fetchMap(keyMapper);
   }

   public final <K, V> Map<K, V> fetchMap(RecordMapper<? super R, K> keyMapper, Class<V> valueType) {
      return ((Select)this.getDelegate()).fetchMap(keyMapper, valueType);
   }

   public final <K, V> Map<K, V> fetchMap(RecordMapper<? super R, K> keyMapper, RecordMapper<? super R, V> valueMapper) {
      return ((Select)this.getDelegate()).fetchMap(keyMapper, valueMapper);
   }

   public final <S extends Record> Map<S, R> fetchMap(Table<S> table) {
      return ((Select)this.getDelegate()).fetchMap(table);
   }

   public final <E, S extends Record> Map<S, E> fetchMap(Table<S> table, Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchMap(table, type);
   }

   public final <E, S extends Record> Map<S, E> fetchMap(Table<S> table, RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchMap(table, mapper);
   }

   public final List<Map<String, Object>> fetchMaps() {
      return ((Select)this.getDelegate()).fetchMaps();
   }

   public final <K> Map<K, Result<R>> fetchGroups(Field<K> key) {
      return ((Select)this.getDelegate()).fetchGroups(key);
   }

   public final Map<?, Result<R>> fetchGroups(int keyFieldIndex) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldIndex);
   }

   public final Map<?, Result<R>> fetchGroups(String keyFieldName) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldName);
   }

   public final Map<?, Result<R>> fetchGroups(Name keyFieldName) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldName);
   }

   public final <K, V> Map<K, List<V>> fetchGroups(Field<K> key, Field<V> value) {
      return ((Select)this.getDelegate()).fetchGroups(key, value);
   }

   public final Map<?, List<?>> fetchGroups(int keyFieldIndex, int valueFieldIndex) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldIndex, valueFieldIndex);
   }

   public final Map<?, List<?>> fetchGroups(String keyFieldName, String valueFieldName) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldName, valueFieldName);
   }

   public final Map<?, List<?>> fetchGroups(Name keyFieldName, Name valueFieldName) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldName, valueFieldName);
   }

   public final <K, E> Map<K, List<E>> fetchGroups(Field<K> key, Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchGroups(key, type);
   }

   public final <E> Map<?, List<E>> fetchGroups(int keyFieldIndex, Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldIndex, type);
   }

   public final <E> Map<?, List<E>> fetchGroups(String keyFieldName, Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldName, type);
   }

   public final <E> Map<?, List<E>> fetchGroups(Name keyFieldName, Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldName, type);
   }

   public final <K, E> Map<K, List<E>> fetchGroups(Field<K> key, RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchGroups(key, mapper);
   }

   public final <E> Map<?, List<E>> fetchGroups(int keyFieldIndex, RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldIndex, mapper);
   }

   public final <E> Map<?, List<E>> fetchGroups(String keyFieldName, RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldName, mapper);
   }

   public final <E> Map<?, List<E>> fetchGroups(Name keyFieldName, RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldName, mapper);
   }

   public final Map<Record, Result<R>> fetchGroups(Field<?>[] keys) {
      return ((Select)this.getDelegate()).fetchGroups(keys);
   }

   public final Map<Record, Result<R>> fetchGroups(int[] keyFieldIndexes) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldIndexes);
   }

   public final Map<Record, Result<R>> fetchGroups(String[] keyFieldNames) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldNames);
   }

   public final Map<Record, Result<R>> fetchGroups(Name[] keyFieldNames) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldNames);
   }

   public final <E> Map<Record, List<E>> fetchGroups(Field<?>[] keys, Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchGroups(keys, type);
   }

   public final <E> Map<Record, List<E>> fetchGroups(int[] keyFieldIndexes, Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldIndexes, type);
   }

   public final <E> Map<Record, List<E>> fetchGroups(String[] keyFieldNames, Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldNames, type);
   }

   public final <E> Map<Record, List<E>> fetchGroups(Name[] keyFieldNames, Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldNames, type);
   }

   public final <E> Map<Record, List<E>> fetchGroups(Field<?>[] keys, RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchGroups(keys, mapper);
   }

   public final <E> Map<Record, List<E>> fetchGroups(int[] keyFieldIndexes, RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldIndexes, mapper);
   }

   public final <E> Map<Record, List<E>> fetchGroups(String[] keyFieldNames, RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldNames, mapper);
   }

   public final <E> Map<Record, List<E>> fetchGroups(Name[] keyFieldNames, RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchGroups(keyFieldNames, mapper);
   }

   public final <K> Map<K, Result<R>> fetchGroups(Class<? extends K> keyType) {
      return ((Select)this.getDelegate()).fetchGroups(keyType);
   }

   public final <K, V> Map<K, List<V>> fetchGroups(Class<? extends K> keyType, Class<? extends V> valueType) {
      return ((Select)this.getDelegate()).fetchGroups(keyType, valueType);
   }

   public final <K, V> Map<K, List<V>> fetchGroups(Class<? extends K> keyType, RecordMapper<? super R, V> valueMapper) {
      return ((Select)this.getDelegate()).fetchGroups(keyType, valueMapper);
   }

   public final <K> Map<K, Result<R>> fetchGroups(RecordMapper<? super R, K> keyMapper) throws MappingException {
      return ((Select)this.getDelegate()).fetchGroups(keyMapper);
   }

   public final <K, V> Map<K, List<V>> fetchGroups(RecordMapper<? super R, K> keyMapper, Class<V> valueType) {
      return ((Select)this.getDelegate()).fetchGroups(keyMapper, valueType);
   }

   public final <K, V> Map<K, List<V>> fetchGroups(RecordMapper<? super R, K> keyMapper, RecordMapper<? super R, V> valueMapper) {
      return ((Select)this.getDelegate()).fetchGroups(keyMapper, valueMapper);
   }

   public final <S extends Record> Map<S, Result<R>> fetchGroups(Table<S> table) {
      return ((Select)this.getDelegate()).fetchGroups(table);
   }

   public final <E, S extends Record> Map<S, List<E>> fetchGroups(Table<S> table, Class<? extends E> type) {
      return ((Select)this.getDelegate()).fetchGroups(table, type);
   }

   public final <E, S extends Record> Map<S, List<E>> fetchGroups(Table<S> table, RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetchGroups(table, mapper);
   }

   public final Object[][] fetchArrays() {
      return ((Select)this.getDelegate()).fetchArrays();
   }

   public final R[] fetchArray() {
      return ((Select)this.getDelegate()).fetchArray();
   }

   public final Object[] fetchArray(int fieldIndex) {
      return ((Select)this.getDelegate()).fetchArray(fieldIndex);
   }

   public final <T> T[] fetchArray(int fieldIndex, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchArray(fieldIndex, type);
   }

   public final <U> U[] fetchArray(int fieldIndex, Converter<?, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchArray(fieldIndex, converter);
   }

   public final Object[] fetchArray(String fieldName) {
      return ((Select)this.getDelegate()).fetchArray(fieldName);
   }

   public final <T> T[] fetchArray(String fieldName, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchArray(fieldName, type);
   }

   public final <U> U[] fetchArray(String fieldName, Converter<?, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchArray(fieldName, converter);
   }

   public final Object[] fetchArray(Name fieldName) {
      return ((Select)this.getDelegate()).fetchArray(fieldName);
   }

   public final <T> T[] fetchArray(Name fieldName, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchArray(fieldName, type);
   }

   public final <U> U[] fetchArray(Name fieldName, Converter<?, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchArray(fieldName, converter);
   }

   public final <T> T[] fetchArray(Field<T> field) {
      return ((Select)this.getDelegate()).fetchArray(field);
   }

   public final <T> T[] fetchArray(Field<?> field, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchArray(field, type);
   }

   public final <T, U> U[] fetchArray(Field<T> field, Converter<? super T, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchArray(field, converter);
   }

   public final Set<?> fetchSet(int fieldIndex) {
      return ((Select)this.getDelegate()).fetchSet(fieldIndex);
   }

   public final <T> Set<T> fetchSet(int fieldIndex, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchSet(fieldIndex, type);
   }

   public final <U> Set<U> fetchSet(int fieldIndex, Converter<?, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchSet(fieldIndex, converter);
   }

   public final Set<?> fetchSet(String fieldName) {
      return ((Select)this.getDelegate()).fetchSet(fieldName);
   }

   public final <T> Set<T> fetchSet(String fieldName, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchSet(fieldName, type);
   }

   public final <U> Set<U> fetchSet(String fieldName, Converter<?, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchSet(fieldName, converter);
   }

   public final Set<?> fetchSet(Name fieldName) {
      return ((Select)this.getDelegate()).fetchSet(fieldName);
   }

   public final <T> Set<T> fetchSet(Name fieldName, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchSet(fieldName, type);
   }

   public final <U> Set<U> fetchSet(Name fieldName, Converter<?, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchSet(fieldName, converter);
   }

   public final <T> Set<T> fetchSet(Field<T> field) {
      return ((Select)this.getDelegate()).fetchSet(field);
   }

   public final <T> Set<T> fetchSet(Field<?> field, Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchSet(field, type);
   }

   public final <T, U> Set<U> fetchSet(Field<T> field, Converter<? super T, ? extends U> converter) {
      return ((Select)this.getDelegate()).fetchSet(field, converter);
   }

   public final <T> List<T> fetchInto(Class<? extends T> type) {
      return ((Select)this.getDelegate()).fetchInto(type);
   }

   public final <Z extends Record> Result<Z> fetchInto(Table<Z> table) {
      return ((Select)this.getDelegate()).fetchInto(table);
   }

   public final <H extends RecordHandler<? super R>> H fetchInto(H handler) {
      return ((Select)this.getDelegate()).fetchInto(handler);
   }

   public final <E> List<E> fetch(RecordMapper<? super R, E> mapper) {
      return ((Select)this.getDelegate()).fetch(mapper);
   }

   public final CompletionStage<Result<R>> fetchAsync() {
      return ((Select)this.getDelegate()).fetchAsync();
   }

   public final CompletionStage<Result<R>> fetchAsync(Executor executor) {
      return ((Select)this.getDelegate()).fetchAsync(executor);
   }

   /** @deprecated */
   @Deprecated
   public final FutureResult<R> fetchLater() {
      return ((Select)this.getDelegate()).fetchLater();
   }

   /** @deprecated */
   @Deprecated
   public final FutureResult<R> fetchLater(ExecutorService executor) {
      return ((Select)this.getDelegate()).fetchLater(executor);
   }

   public final Table<R> asTable() {
      return ((Select)this.getDelegate()).asTable();
   }

   public final Table<R> asTable(String alias) {
      return ((Select)this.getDelegate()).asTable(alias);
   }

   public final Table<R> asTable(String alias, String... fieldAliases) {
      return ((Select)this.getDelegate()).asTable(alias, fieldAliases);
   }

   public final Table<R> asTable(String alias, java.util.function.Function<? super Field<?>, ? extends String> aliasFunction) {
      return ((Select)this.getDelegate()).asTable(alias, aliasFunction);
   }

   public final Table<R> asTable(String alias, BiFunction<? super Field<?>, ? super Integer, ? extends String> aliasFunction) {
      return ((Select)this.getDelegate()).asTable(alias, aliasFunction);
   }

   public final <T> Field<T> asField() {
      return ((Select)this.getDelegate()).asField();
   }

   public final <T> Field<T> asField(String alias) {
      return ((Select)this.getDelegate()).asField(alias);
   }

   public final <T> Field<T> asField(java.util.function.Function<? super Field<T>, ? extends String> aliasFunction) {
      return ((Select)this.getDelegate()).asField(aliasFunction);
   }

   public final Row fieldsRow() {
      return ((Select)this.getDelegate()).fieldsRow();
   }

   public final Stream<Field<?>> fieldStream() {
      return Stream.of(this.fields());
   }

   public final <T> Field<T> field(Field<T> field) {
      return ((Select)this.getDelegate()).field(field);
   }

   public final Field<?> field(String string) {
      return ((Select)this.getDelegate()).field(string);
   }

   public final <T> Field<T> field(String name, Class<T> type) {
      return ((Select)this.getDelegate()).field(name, type);
   }

   public final <T> Field<T> field(String name, DataType<T> dataType) {
      return ((Select)this.getDelegate()).field(name, dataType);
   }

   public final Field<?> field(Name string) {
      return ((Select)this.getDelegate()).field(string);
   }

   public final <T> Field<T> field(Name name, Class<T> type) {
      return ((Select)this.getDelegate()).field(name, type);
   }

   public final <T> Field<T> field(Name name, DataType<T> dataType) {
      return ((Select)this.getDelegate()).field(name, dataType);
   }

   public final Field<?> field(int index) {
      return ((Select)this.getDelegate()).field(index);
   }

   public final <T> Field<T> field(int index, Class<T> type) {
      return ((Select)this.getDelegate()).field(index, type);
   }

   public final <T> Field<T> field(int index, DataType<T> dataType) {
      return ((Select)this.getDelegate()).field(index, dataType);
   }

   public final Field<?>[] fields() {
      return ((Select)this.getDelegate()).fields();
   }

   public final Field<?>[] fields(Field<?>... fields) {
      return ((Select)this.getDelegate()).fields(fields);
   }

   public final Field<?>[] fields(String... fieldNames) {
      return ((Select)this.getDelegate()).fields(fieldNames);
   }

   public final Field<?>[] fields(Name... fieldNames) {
      return ((Select)this.getDelegate()).fields(fieldNames);
   }

   public final Field<?>[] fields(int... fieldIndexes) {
      return ((Select)this.getDelegate()).fields(fieldIndexes);
   }

   private static enum ConditionStep {
      ON,
      WHERE,
      CONNECT_BY,
      HAVING;
   }
}
