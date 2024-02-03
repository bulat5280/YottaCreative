package org.jooq.impl;

import java.util.ArrayList;
import java.util.List;
import org.jooq.CaseConditionStep;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Record1;
import org.jooq.Select;

final class CaseConditionStepImpl<T> extends AbstractFunction<T> implements CaseConditionStep<T> {
   private static final long serialVersionUID = -1735676153683257465L;
   private final List<Condition> conditions = new ArrayList();
   private final List<Field<T>> results = new ArrayList();
   private Field<T> otherwise;

   CaseConditionStepImpl(Condition condition, Field<T> result) {
      super("case", result.getDataType());
      this.when(condition, result);
   }

   public final CaseConditionStep<T> when(Condition condition, T result) {
      return this.when(condition, Tools.field(result));
   }

   public final CaseConditionStep<T> when(Condition condition, Field<T> result) {
      this.conditions.add(condition);
      this.results.add(result);
      return this;
   }

   public final CaseConditionStep<T> when(Condition condition, Select<? extends Record1<T>> result) {
      return this.when(condition, DSL.field(result));
   }

   public final Field<T> otherwise(T result) {
      return this.otherwise(Tools.field(result));
   }

   public final Field<T> otherwise(Field<T> result) {
      this.otherwise = result;
      return this;
   }

   public final Field<T> otherwise(Select<? extends Record1<T>> result) {
      return this.otherwise(DSL.field(result));
   }

   final QueryPart getFunction0(Configuration configuration) {
      switch(configuration.dialect().family()) {
      default:
         return new CaseConditionStepImpl.Native();
      }
   }

   private class Native extends CaseConditionStepImpl<T>.Base {
      private static final long serialVersionUID = 7850713333675233736L;

      private Native() {
         super(null);
      }

      public final void accept(Context<?> ctx) {
         ctx.formatIndentLockStart().keyword("case").formatIndentLockStart();
         int size = CaseConditionStepImpl.this.conditions.size();

         for(int i = 0; i < size; ++i) {
            if (i > 0) {
               ctx.formatNewLine();
            }

            ctx.sql(' ').keyword("when").sql(' ').visit((QueryPart)CaseConditionStepImpl.this.conditions.get(i)).sql(' ').keyword("then").sql(' ').visit((QueryPart)CaseConditionStepImpl.this.results.get(i));
         }

         if (CaseConditionStepImpl.this.otherwise != null) {
            ctx.formatNewLine().sql(' ').keyword("else").sql(' ').visit(CaseConditionStepImpl.this.otherwise);
         }

         ctx.formatIndentLockEnd();
         if (size <= 1 && CaseConditionStepImpl.this.otherwise == null) {
            ctx.sql(' ');
         } else {
            ctx.formatSeparator();
         }

         ctx.keyword("end").formatIndentLockEnd();
      }

      // $FF: synthetic method
      Native(Object x1) {
         this();
      }
   }

   private abstract class Base extends AbstractQueryPart {
      private static final long serialVersionUID = 6146002888421945901L;

      private Base() {
      }

      public final Clause[] clauses(Context<?> ctx) {
         return null;
      }

      // $FF: synthetic method
      Base(Object x1) {
         this();
      }
   }
}
