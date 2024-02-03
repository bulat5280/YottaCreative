package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPartInternal;
import org.jooq.Row;
import org.jooq.SQLDialect;

final class RowCondition extends AbstractCondition {
   private static final long serialVersionUID = -1806139685201770706L;
   private static final Clause[] CLAUSES;
   private final Row left;
   private final Row right;
   private final Comparator comparator;

   RowCondition(Row left, Row right, Comparator comparator) {
      this.left = left;
      this.right = right;
      this.comparator = comparator;
   }

   public final void accept(Context<?> ctx) {
      ctx.visit(this.delegate(ctx.configuration()));
   }

   public final Clause[] clauses(Context<?> ctx) {
      return null;
   }

   private final QueryPartInternal delegate(Configuration configuration) {
      SQLDialect dialect = configuration.dialect();
      if (Arrays.asList(Comparator.EQUALS, Comparator.NOT_EQUALS).contains(this.comparator) && Arrays.asList(SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.SQLITE).contains(dialect.family())) {
         List<Condition> conditions = new ArrayList();
         Field<?>[] leftFields = this.left.fields();
         Field<?>[] rightFields = this.right.fields();

         for(int i = 0; i < leftFields.length; ++i) {
            conditions.add(leftFields[i].equal(rightFields[i]));
         }

         Condition result = DSL.and((Collection)conditions);
         if (this.comparator == Comparator.NOT_EQUALS) {
            result = result.not();
         }

         return (QueryPartInternal)result;
      } else if (Arrays.asList(Comparator.GREATER, Comparator.GREATER_OR_EQUAL, Comparator.LESS, Comparator.LESS_OR_EQUAL).contains(this.comparator) && Arrays.asList(SQLDialect.DERBY, SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.SQLITE).contains(dialect.family())) {
         Comparator order = this.comparator == Comparator.GREATER ? Comparator.GREATER : (this.comparator == Comparator.GREATER_OR_EQUAL ? Comparator.GREATER : (this.comparator == Comparator.LESS ? Comparator.LESS : (this.comparator == Comparator.LESS_OR_EQUAL ? Comparator.LESS : null)));
         Comparator factoredOrder = this.comparator == Comparator.GREATER ? Comparator.GREATER_OR_EQUAL : (this.comparator == Comparator.GREATER_OR_EQUAL ? Comparator.GREATER_OR_EQUAL : (this.comparator == Comparator.LESS ? Comparator.LESS_OR_EQUAL : (this.comparator == Comparator.LESS_OR_EQUAL ? Comparator.LESS_OR_EQUAL : null)));
         boolean equal = this.comparator == Comparator.GREATER_OR_EQUAL || this.comparator == Comparator.LESS_OR_EQUAL;
         List<Condition> outer = new ArrayList();
         Field<?>[] leftFields = this.left.fields();
         Field<?>[] rightFields = this.right.fields();

         for(int i = 0; i < leftFields.length; ++i) {
            List<Condition> inner = new ArrayList();

            for(int j = 0; j < i; ++j) {
               inner.add(leftFields[j].equal(rightFields[j]));
            }

            inner.add(leftFields[i].compare(order, rightFields[i]));
            outer.add(DSL.and((Collection)inner));
         }

         if (equal) {
            outer.add(new RowCondition(this.left, this.right, Comparator.EQUALS));
         }

         Condition result = DSL.or((Collection)outer);
         if (leftFields.length > 1) {
            result = leftFields[0].compare(factoredOrder, rightFields[0]).and(result);
         }

         return (QueryPartInternal)result;
      } else {
         return new RowCondition.Native();
      }
   }

   static {
      CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_COMPARISON};
   }

   private class Native extends AbstractCondition {
      private static final long serialVersionUID = -2977241780111574353L;

      private Native() {
      }

      public final void accept(Context<?> ctx) {
         if (RowCondition.this.comparator == Comparator.NOT_EQUALS && Arrays.asList().contains(ctx.configuration().dialect().family())) {
            ctx.keyword("not").sql('(').visit(RowCondition.this.left).sql(" = ").visit(RowCondition.this.right).sql(')');
         } else {
            boolean extraParentheses = Arrays.asList().contains(ctx.configuration().dialect().family());
            ctx.visit(RowCondition.this.left).sql(' ').sql(RowCondition.this.comparator.toSQL()).sql(' ').sql(extraParentheses ? "(" : "").visit(RowCondition.this.right).sql(extraParentheses ? ")" : "");
         }

      }

      public final Clause[] clauses(Context<?> ctx) {
         return RowCondition.CLAUSES;
      }

      // $FF: synthetic method
      Native(Object x1) {
         this();
      }
   }
}
