package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Operator;
import org.jooq.QueryPart;

final class CombinedCondition extends AbstractCondition {
   private static final long serialVersionUID = -7373293246207052549L;
   private static final Clause[] CLAUSES_AND;
   private static final Clause[] CLAUSES_OR;
   private final Operator operator;
   private final List<Condition> conditions;

   CombinedCondition(Operator operator, Collection<? extends Condition> conditions) {
      if (operator == null) {
         throw new IllegalArgumentException("The argument 'operator' must not be null");
      } else if (conditions == null) {
         throw new IllegalArgumentException("The argument 'conditions' must not be null");
      } else {
         Iterator var3 = conditions.iterator();

         Condition condition;
         do {
            if (!var3.hasNext()) {
               this.operator = operator;
               this.conditions = new ArrayList(conditions.size());
               this.init(operator, conditions);
               return;
            }

            condition = (Condition)var3.next();
         } while(condition != null);

         throw new IllegalArgumentException("The argument 'conditions' must not contain null");
      }
   }

   private final void init(Operator op, Collection<? extends Condition> cond) {
      Iterator var3 = cond.iterator();

      while(var3.hasNext()) {
         Condition condition = (Condition)var3.next();
         if (condition instanceof CombinedCondition) {
            CombinedCondition combinedCondition = (CombinedCondition)condition;
            if (combinedCondition.operator == op) {
               this.conditions.addAll(combinedCondition.conditions);
            } else {
               this.conditions.add(condition);
            }
         } else {
            this.conditions.add(condition);
         }
      }

   }

   public final Clause[] clauses(Context<?> ctx) {
      return this.operator == Operator.AND ? CLAUSES_AND : CLAUSES_OR;
   }

   public final void accept(Context<?> ctx) {
      if (this.conditions.isEmpty()) {
         if (this.operator == Operator.AND) {
            ctx.visit(DSL.trueCondition());
         } else {
            ctx.visit(DSL.falseCondition());
         }
      } else if (this.conditions.size() == 1) {
         ctx.visit((QueryPart)this.conditions.get(0));
      } else {
         ctx.sql('(').formatIndentStart().formatNewLine();
         String operatorName = this.operator == Operator.AND ? "and" : "or";
         String separator = null;

         for(int i = 0; i < this.conditions.size(); ++i) {
            if (i > 0) {
               ctx.formatSeparator();
            }

            if (separator != null) {
               ctx.keyword(separator).sql(' ');
            }

            ctx.visit((QueryPart)this.conditions.get(i));
            separator = operatorName;
         }

         ctx.formatIndentEnd().formatNewLine().sql(')');
      }

   }

   static {
      CLAUSES_AND = new Clause[]{Clause.CONDITION, Clause.CONDITION_AND};
      CLAUSES_OR = new Clause[]{Clause.CONDITION, Clause.CONDITION_OR};
   }
}
