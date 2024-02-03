package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.Select;

abstract class AbstractCondition extends AbstractQueryPart implements Condition {
   private static final long serialVersionUID = -6683692251799468624L;
   private static final Clause[] CLAUSES;

   public Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   public final Condition and(Condition other) {
      return DSL.and(this, other);
   }

   public Condition and(Field<Boolean> other) {
      return this.and(DSL.condition(other));
   }

   public Condition and(Boolean other) {
      return this.and(DSL.condition(other));
   }

   public final Condition or(Condition other) {
      return DSL.or(this, other);
   }

   public final Condition or(Field<Boolean> other) {
      return this.or(DSL.condition(other));
   }

   public final Condition or(Boolean other) {
      return this.or(DSL.condition(other));
   }

   public final Condition and(SQL sql) {
      return this.and(DSL.condition(sql));
   }

   public final Condition and(String sql) {
      return this.and(DSL.condition(sql));
   }

   public final Condition and(String sql, Object... bindings) {
      return this.and(DSL.condition(sql, bindings));
   }

   public final Condition and(String sql, QueryPart... parts) {
      return this.and(DSL.condition(sql, parts));
   }

   public final Condition or(SQL sql) {
      return this.or(DSL.condition(sql));
   }

   public final Condition or(String sql) {
      return this.or(DSL.condition(sql));
   }

   public final Condition or(String sql, Object... bindings) {
      return this.or(DSL.condition(sql, bindings));
   }

   public final Condition or(String sql, QueryPart... parts) {
      return this.or(DSL.condition(sql, parts));
   }

   public final Condition andNot(Condition other) {
      return this.and(other.not());
   }

   public final Condition andNot(Field<Boolean> other) {
      return this.andNot(DSL.condition(other));
   }

   public final Condition andNot(Boolean other) {
      return this.andNot(DSL.condition(other));
   }

   public final Condition orNot(Condition other) {
      return this.or(other.not());
   }

   public final Condition orNot(Field<Boolean> other) {
      return this.orNot(DSL.condition(other));
   }

   public final Condition orNot(Boolean other) {
      return this.orNot(DSL.condition(other));
   }

   public final Condition andExists(Select<?> select) {
      return this.and(DSL.exists(select));
   }

   public final Condition andNotExists(Select<?> select) {
      return this.and(DSL.notExists(select));
   }

   public final Condition orExists(Select<?> select) {
      return this.or(DSL.exists(select));
   }

   public final Condition orNotExists(Select<?> select) {
      return this.or(DSL.notExists(select));
   }

   public final Condition not() {
      return new NotCondition(this);
   }

   static {
      CLAUSES = new Clause[]{Clause.CONDITION};
   }
}
