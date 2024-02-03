package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;

final class Contains<T> extends AbstractCondition {
   private static final long serialVersionUID = 6146303086487338550L;
   private static final Clause[] CLAUSES;
   private final Field<T> lhs;
   private final Field<T> rhs;
   private final T value;

   Contains(Field<T> field, T value) {
      this.lhs = field;
      this.rhs = null;
      this.value = value;
   }

   Contains(Field<T> field, Field<T> rhs) {
      this.lhs = field;
      this.rhs = rhs;
      this.value = null;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.condition(ctx.configuration()));
   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   private final Condition condition(Configuration configuration) {
      if (this.lhs.getDataType().isArray()) {
         return new Contains.PostgresArrayContains();
      } else {
         Field concat;
         if (this.rhs == null) {
            concat = DSL.concat(DSL.inline("%"), Tools.escapeForLike(this.value, configuration), DSL.inline("%"));
         } else {
            concat = DSL.concat(DSL.inline("%"), Tools.escapeForLike(this.rhs, configuration), DSL.inline("%"));
         }

         return this.lhs.like(concat, '!');
      }
   }

   static {
      CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_COMPARISON};
   }

   private class PostgresArrayContains extends AbstractCondition {
      private static final long serialVersionUID = 8083622843635168388L;

      private PostgresArrayContains() {
      }

      public final void accept(Context<?> ctx) {
         ctx.visit(Contains.this.lhs).sql(" @> ").visit(this.rhs());
      }

      public final Clause[] clauses(Context<?> ctx) {
         return Contains.CLAUSES;
      }

      private final Field<T> rhs() {
         return (Field)(Contains.this.rhs == null ? DSL.val(Contains.this.value, Contains.this.lhs) : Contains.this.rhs);
      }

      // $FF: synthetic method
      PostgresArrayContains(Object x1) {
         this();
      }
   }
}
