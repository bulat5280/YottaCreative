package org.jooq.impl;

import java.util.Collection;
import java.util.Optional;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DeleteConditionStep;
import org.jooq.DeleteResultStep;
import org.jooq.DeleteWhereStep;
import org.jooq.Field;
import org.jooq.Operator;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQL;
import org.jooq.Select;
import org.jooq.Table;

final class DeleteImpl<R extends Record> extends AbstractDelegatingQuery<DeleteQueryImpl<R>> implements DeleteWhereStep<R>, DeleteConditionStep<R>, DeleteResultStep<R> {
   private static final long serialVersionUID = 2747566322757517382L;

   DeleteImpl(Configuration configuration, WithImpl with, Table<R> table) {
      super(new DeleteQueryImpl(configuration, with, table));
   }

   public final DeleteImpl<R> where(Condition... conditions) {
      ((DeleteQueryImpl)this.getDelegate()).addConditions(conditions);
      return this;
   }

   public final DeleteImpl<R> where(Collection<? extends Condition> conditions) {
      ((DeleteQueryImpl)this.getDelegate()).addConditions(conditions);
      return this;
   }

   public final DeleteImpl<R> where(Field<Boolean> condition) {
      return this.where(DSL.condition(condition));
   }

   /** @deprecated */
   @Deprecated
   public final DeleteImpl<R> where(Boolean condition) {
      return this.where(DSL.condition(condition));
   }

   public final DeleteImpl<R> where(SQL sql) {
      return this.where(DSL.condition(sql));
   }

   public final DeleteImpl<R> where(String sql) {
      return this.where(DSL.condition(sql));
   }

   public final DeleteImpl<R> where(String sql, Object... bindings) {
      return this.where(DSL.condition(sql, bindings));
   }

   public final DeleteImpl<R> where(String sql, QueryPart... parts) {
      return this.where(DSL.condition(sql, parts));
   }

   public final DeleteImpl<R> whereExists(Select<?> select) {
      return this.andExists(select);
   }

   public final DeleteImpl<R> whereNotExists(Select<?> select) {
      return this.andNotExists(select);
   }

   public final DeleteImpl<R> and(Condition condition) {
      ((DeleteQueryImpl)this.getDelegate()).addConditions(condition);
      return this;
   }

   public final DeleteImpl<R> and(Field<Boolean> condition) {
      return this.and(DSL.condition(condition));
   }

   /** @deprecated */
   @Deprecated
   public final DeleteImpl<R> and(Boolean condition) {
      return this.and(DSL.condition(condition));
   }

   public final DeleteImpl<R> and(SQL sql) {
      return this.and(DSL.condition(sql));
   }

   public final DeleteImpl<R> and(String sql) {
      return this.and(DSL.condition(sql));
   }

   public final DeleteImpl<R> and(String sql, Object... bindings) {
      return this.and(DSL.condition(sql, bindings));
   }

   public final DeleteImpl<R> and(String sql, QueryPart... parts) {
      return this.and(DSL.condition(sql, parts));
   }

   public final DeleteImpl<R> andNot(Condition condition) {
      return this.and(condition.not());
   }

   public final DeleteImpl<R> andNot(Field<Boolean> condition) {
      return this.andNot(DSL.condition(condition));
   }

   /** @deprecated */
   @Deprecated
   public final DeleteImpl<R> andNot(Boolean condition) {
      return this.andNot(DSL.condition(condition));
   }

   public final DeleteImpl<R> andExists(Select<?> select) {
      return this.and(DSL.exists(select));
   }

   public final DeleteImpl<R> andNotExists(Select<?> select) {
      return this.and(DSL.notExists(select));
   }

   public final DeleteImpl<R> or(Condition condition) {
      ((DeleteQueryImpl)this.getDelegate()).addConditions(Operator.OR, condition);
      return this;
   }

   public final DeleteImpl<R> or(Field<Boolean> condition) {
      return this.or(DSL.condition(condition));
   }

   /** @deprecated */
   @Deprecated
   public final DeleteImpl<R> or(Boolean condition) {
      return this.or(DSL.condition(condition));
   }

   public final DeleteImpl<R> or(SQL sql) {
      return this.or(DSL.condition(sql));
   }

   public final DeleteImpl<R> or(String sql) {
      return this.or(DSL.condition(sql));
   }

   public final DeleteImpl<R> or(String sql, Object... bindings) {
      return this.or(DSL.condition(sql, bindings));
   }

   public final DeleteImpl<R> or(String sql, QueryPart... parts) {
      return this.or(DSL.condition(sql, parts));
   }

   public final DeleteImpl<R> orNot(Condition condition) {
      return this.or(condition.not());
   }

   public final DeleteImpl<R> orNot(Field<Boolean> condition) {
      return this.orNot(DSL.condition(condition));
   }

   /** @deprecated */
   @Deprecated
   public final DeleteImpl<R> orNot(Boolean condition) {
      return this.orNot(DSL.condition(condition));
   }

   public final DeleteImpl<R> orExists(Select<?> select) {
      return this.or(DSL.exists(select));
   }

   public final DeleteImpl<R> orNotExists(Select<?> select) {
      return this.or(DSL.notExists(select));
   }

   public final DeleteImpl<R> returning() {
      ((DeleteQueryImpl)this.getDelegate()).setReturning();
      return this;
   }

   public final DeleteImpl<R> returning(Field<?>... f) {
      ((DeleteQueryImpl)this.getDelegate()).setReturning(f);
      return this;
   }

   public final DeleteImpl<R> returning(Collection<? extends Field<?>> f) {
      ((DeleteQueryImpl)this.getDelegate()).setReturning(f);
      return this;
   }

   public final Result<R> fetch() {
      ((DeleteQueryImpl)this.getDelegate()).execute();
      return ((DeleteQueryImpl)this.getDelegate()).getReturnedRecords();
   }

   public final R fetchOne() {
      ((DeleteQueryImpl)this.getDelegate()).execute();
      return ((DeleteQueryImpl)this.getDelegate()).getReturnedRecord();
   }

   public final Optional<R> fetchOptional() {
      return Optional.ofNullable(this.fetchOne());
   }
}
