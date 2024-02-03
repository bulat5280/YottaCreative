package org.jooq.impl;

import org.jooq.Case;
import org.jooq.CaseConditionStep;
import org.jooq.CaseValueStep;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Select;

final class CaseImpl implements Case {
   public final <V> CaseValueStep<V> value(V value) {
      return this.value(Tools.field(value));
   }

   public final <V> CaseValueStep<V> value(Field<V> value) {
      return new CaseValueStepImpl(value);
   }

   public final <T> CaseConditionStep<T> when(Condition condition, T result) {
      return this.when(condition, Tools.field(result));
   }

   public final <T> CaseConditionStep<T> when(Condition condition, Field<T> result) {
      return new CaseConditionStepImpl(condition, result);
   }

   public final <T> CaseConditionStep<T> when(Condition condition, Select<? extends Record1<T>> result) {
      return this.when(condition, DSL.field(result));
   }
}
