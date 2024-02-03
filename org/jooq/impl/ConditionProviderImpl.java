package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.ConditionProvider;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Operator;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.Select;

final class ConditionProviderImpl extends AbstractQueryPart implements ConditionProvider, Condition {
   private static final long serialVersionUID = 6073328960551062973L;
   private Condition condition;

   final Condition getWhere() {
      return this.condition == null ? DSL.trueCondition() : this.condition;
   }

   public final void addConditions(Condition... conditions) {
      this.addConditions(Operator.AND, conditions);
   }

   public final void addConditions(Collection<? extends Condition> conditions) {
      this.addConditions(Operator.AND, conditions);
   }

   public final void addConditions(Operator operator, Condition... conditions) {
      this.addConditions(operator, (Collection)Arrays.asList(conditions));
   }

   public final void addConditions(Operator operator, Collection<? extends Condition> conditions) {
      if (!conditions.isEmpty()) {
         Condition c;
         if (conditions.size() == 1) {
            c = (Condition)conditions.iterator().next();
         } else {
            c = DSL.condition(operator, conditions);
         }

         if (this.getWhere() instanceof TrueCondition) {
            this.condition = c;
         } else {
            this.condition = DSL.condition(operator, this.getWhere(), c);
         }
      }

   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.getWhere());
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   public final Condition and(Condition other) {
      return this.getWhere().and(other);
   }

   public final Condition and(Field<Boolean> other) {
      return this.getWhere().and(other);
   }

   public final Condition and(Boolean other) {
      return this.getWhere().and(other);
   }

   public final Condition and(SQL sql) {
      return this.getWhere().and(sql);
   }

   public final Condition and(String sql) {
      return this.getWhere().and(sql);
   }

   public final Condition and(String sql, Object... bindings) {
      return this.getWhere().and(sql, bindings);
   }

   public final Condition and(String sql, QueryPart... parts) {
      return this.getWhere().and(sql, parts);
   }

   public final Condition andNot(Condition other) {
      return this.getWhere().andNot(other);
   }

   public final Condition andNot(Field<Boolean> other) {
      return this.getWhere().andNot(other);
   }

   public final Condition andNot(Boolean other) {
      return this.getWhere().andNot(other);
   }

   public final Condition andExists(Select<?> select) {
      return this.getWhere().andExists(select);
   }

   public final Condition andNotExists(Select<?> select) {
      return this.getWhere().andNotExists(select);
   }

   public final Condition or(Condition other) {
      return this.getWhere().or(other);
   }

   public final Condition or(Field<Boolean> other) {
      return this.getWhere().or(other);
   }

   public final Condition or(Boolean other) {
      return this.getWhere().or(other);
   }

   public final Condition or(SQL sql) {
      return this.getWhere().or(sql);
   }

   public final Condition or(String sql) {
      return this.getWhere().or(sql);
   }

   public final Condition or(String sql, Object... bindings) {
      return this.getWhere().or(sql, bindings);
   }

   public final Condition or(String sql, QueryPart... parts) {
      return this.getWhere().or(sql, parts);
   }

   public final Condition orNot(Condition other) {
      return this.getWhere().orNot(other);
   }

   public final Condition orNot(Field<Boolean> other) {
      return this.getWhere().orNot(other);
   }

   public final Condition orNot(Boolean other) {
      return this.getWhere().orNot(other);
   }

   public final Condition orExists(Select<?> select) {
      return this.getWhere().orExists(select);
   }

   public final Condition orNotExists(Select<?> select) {
      return this.getWhere().orNotExists(select);
   }

   public final Condition not() {
      return this.getWhere().not();
   }
}
