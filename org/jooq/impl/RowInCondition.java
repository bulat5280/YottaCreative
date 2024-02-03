package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.QueryPartInternal;
import org.jooq.Row;
import org.jooq.SQLDialect;

final class RowInCondition extends AbstractCondition {
   private static final long serialVersionUID = -1806139685201770706L;
   private static final Clause[] CLAUSES_IN;
   private static final Clause[] CLAUSES_IN_NOT;
   private final Row left;
   private final QueryPartList<? extends Row> right;
   private final Comparator comparator;

   RowInCondition(Row left, QueryPartList<? extends Row> right, Comparator comparator) {
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
      if (!Arrays.asList(SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.SQLITE).contains(configuration.family())) {
         return new RowInCondition.Native();
      } else {
         List<Condition> conditions = new ArrayList();
         Iterator var3 = this.right.iterator();

         while(var3.hasNext()) {
            Row row = (Row)var3.next();
            conditions.add(new RowCondition(this.left, row, Comparator.EQUALS));
         }

         Condition result = DSL.or((Collection)conditions);
         if (this.comparator == Comparator.NOT_IN) {
            result = result.not();
         }

         return (QueryPartInternal)result;
      }
   }

   static {
      CLAUSES_IN = new Clause[]{Clause.CONDITION, Clause.CONDITION_IN};
      CLAUSES_IN_NOT = new Clause[]{Clause.CONDITION, Clause.CONDITION_NOT_IN};
   }

   private class Native extends AbstractCondition {
      private static final long serialVersionUID = -7019193803316281371L;

      private Native() {
      }

      public final void accept(Context<?> ctx) {
         if (RowInCondition.this.right.size() == 0) {
            if (RowInCondition.this.comparator == Comparator.IN) {
               ctx.visit(DSL.falseCondition());
            } else {
               ctx.visit(DSL.trueCondition());
            }
         } else {
            ctx.visit(RowInCondition.this.left).sql(' ').keyword(RowInCondition.this.comparator.toSQL()).sql(" (").visit(RowInCondition.this.right).sql(')');
         }

      }

      public final Clause[] clauses(Context<?> ctx) {
         return RowInCondition.this.comparator == Comparator.IN ? RowInCondition.CLAUSES_IN : RowInCondition.CLAUSES_IN_NOT;
      }

      // $FF: synthetic method
      Native(Object x1) {
         this();
      }
   }
}
