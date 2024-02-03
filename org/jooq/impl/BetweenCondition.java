package org.jooq.impl;

import java.util.Arrays;
import org.jooq.BetweenAndStep;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPartInternal;
import org.jooq.SQLDialect;

final class BetweenCondition<T> extends AbstractCondition implements BetweenAndStep<T> {
   private static final long serialVersionUID = -4666251100802237878L;
   private static final Clause[] CLAUSES_BETWEEN;
   private static final Clause[] CLAUSES_BETWEEN_SYMMETRIC;
   private static final Clause[] CLAUSES_NOT_BETWEEN;
   private static final Clause[] CLAUSES_NOT_BETWEEN_SYMMETRIC;
   private final boolean symmetric;
   private final boolean not;
   private final Field<T> field;
   private final Field<T> minValue;
   private Field<T> maxValue;

   BetweenCondition(Field<T> field, Field<T> minValue, boolean not, boolean symmetric) {
      this.field = field;
      this.minValue = minValue;
      this.not = not;
      this.symmetric = symmetric;
   }

   public final Condition and(T value) {
      return this.and((Field)DSL.val(value, this.field.getDataType()));
   }

   public final Condition and(Field f) {
      if (this.maxValue == null) {
         this.maxValue = DSL.nullSafe(f, this.field.getDataType());
         return this;
      } else {
         return super.and(f);
      }
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.delegate(ctx.configuration()));
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   private final QueryPartInternal delegate(Configuration configuration) {
      if (this.symmetric && Arrays.asList(SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.SQLITE).contains(configuration.family())) {
         return this.not ? (QueryPartInternal)this.field.notBetween(this.minValue, this.maxValue).and(this.field.notBetween(this.maxValue, this.minValue)) : (QueryPartInternal)this.field.between(this.minValue, this.maxValue).or(this.field.between(this.maxValue, this.minValue));
      } else {
         return new BetweenCondition.Native();
      }
   }

   static {
      CLAUSES_BETWEEN = new Clause[]{Clause.CONDITION, Clause.CONDITION_BETWEEN};
      CLAUSES_BETWEEN_SYMMETRIC = new Clause[]{Clause.CONDITION, Clause.CONDITION_BETWEEN_SYMMETRIC};
      CLAUSES_NOT_BETWEEN = new Clause[]{Clause.CONDITION, Clause.CONDITION_NOT_BETWEEN};
      CLAUSES_NOT_BETWEEN_SYMMETRIC = new Clause[]{Clause.CONDITION, Clause.CONDITION_NOT_BETWEEN_SYMMETRIC};
   }

   private class Native extends AbstractQueryPart {
      private static final long serialVersionUID = 2915703568738921575L;

      private Native() {
      }

      public final void accept(Context<?> ctx) {
         ctx.visit(BetweenCondition.this.field);
         if (BetweenCondition.this.not) {
            ctx.sql(' ').keyword("not");
         }

         ctx.sql(' ').keyword("between");
         if (BetweenCondition.this.symmetric) {
            ctx.sql(' ').keyword("symmetric");
         }

         ctx.sql(' ').visit(BetweenCondition.this.minValue);
         ctx.sql(' ').keyword("and");
         ctx.sql(' ').visit(BetweenCondition.this.maxValue);
      }

      public final Clause[] clauses(Context<?> ctx) {
         return BetweenCondition.this.not ? (BetweenCondition.this.symmetric ? BetweenCondition.CLAUSES_NOT_BETWEEN_SYMMETRIC : BetweenCondition.CLAUSES_NOT_BETWEEN) : (BetweenCondition.this.symmetric ? BetweenCondition.CLAUSES_BETWEEN_SYMMETRIC : BetweenCondition.CLAUSES_BETWEEN);
      }

      // $FF: synthetic method
      Native(Object x1) {
         this();
      }
   }
}
