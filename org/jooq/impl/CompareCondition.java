package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.LikeEscapeStep;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;

final class CompareCondition extends AbstractCondition implements LikeEscapeStep {
   private static final long serialVersionUID = -747240442279619486L;
   private static final Clause[] CLAUSES;
   private final Field<?> field1;
   private final Field<?> field2;
   private final Comparator comparator;
   private Character escape;

   CompareCondition(Field<?> field1, Field<?> field2, Comparator comparator) {
      this.field1 = field1;
      this.field2 = field2;
      this.comparator = comparator;
   }

   public final Condition escape(char c) {
      this.escape = c;
      return this;
   }

   public final void accept(Context<?> ctx) {
      SQLDialect family = ctx.family();
      Field<?> lhs = this.field1;
      Field<?> rhs = this.field2;
      Comparator op = this.comparator;
      if ((op == Comparator.LIKE || op == Comparator.NOT_LIKE) && this.field1.getType() != String.class && Arrays.asList(SQLDialect.DERBY, SQLDialect.POSTGRES).contains(family)) {
         lhs = lhs.cast(String.class);
      } else if ((op == Comparator.LIKE_IGNORE_CASE || op == Comparator.NOT_LIKE_IGNORE_CASE) && SQLDialect.POSTGRES != family) {
         lhs = lhs.lower();
         rhs = rhs.lower();
         op = op == Comparator.LIKE_IGNORE_CASE ? Comparator.LIKE : Comparator.NOT_LIKE;
      }

      ctx.visit(lhs).sql(' ');
      boolean castRhs = false;
      ParamType previousParamType = ctx.paramType();
      ctx.keyword(op.toSQL()).sql(' ');
      if (castRhs) {
         ctx.keyword("cast").sql('(');
      }

      ctx.paramType(previousParamType).visit(rhs).paramType(previousParamType);
      if (castRhs) {
         ctx.sql(' ').keyword("as").sql(' ').keyword("varchar").sql("(4000))");
      }

      if (this.escape != null) {
         ctx.sql(' ').keyword("escape").sql(' ').visit(DSL.inline(this.escape));
      }

   }

   public final Clause[] clauses(Context<?> ctx) {
      return CLAUSES;
   }

   static {
      CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_COMPARISON};
   }
}
