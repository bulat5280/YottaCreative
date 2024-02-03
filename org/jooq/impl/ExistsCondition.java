package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Select;

final class ExistsCondition extends AbstractCondition {
   private static final long serialVersionUID = 5678338161136603292L;
   private static final Clause[] CLAUSES_EXISTS;
   private static final Clause[] CLAUSES_EXISTS_NOT;
   private final Select<?> query;
   private final boolean exists;

   ExistsCondition(Select<?> query, boolean exists) {
      this.query = query;
      this.exists = exists;
   }

   public final void accept(Context<?> ctx) {
      boolean subquery = ctx.subquery();
      ctx.keyword(this.exists ? "exists" : "not exists").sql(" (").subquery(true).formatIndentStart().formatNewLine().visit(this.query).formatIndentEnd().formatNewLine().subquery(subquery).sql(')');
   }

   public final Clause[] clauses(Context<?> ctx) {
      return this.exists ? CLAUSES_EXISTS : CLAUSES_EXISTS_NOT;
   }

   static {
      CLAUSES_EXISTS = new Clause[]{Clause.CONDITION, Clause.CONDITION_EXISTS};
      CLAUSES_EXISTS_NOT = new Clause[]{Clause.CONDITION, Clause.CONDITION_NOT_EXISTS};
   }
}
