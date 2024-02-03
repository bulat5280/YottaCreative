package org.jooq;

public interface Case {
   @Support
   <V> CaseValueStep<V> value(V var1);

   @Support
   <V> CaseValueStep<V> value(Field<V> var1);

   @Support
   <T> CaseConditionStep<T> when(Condition var1, T var2);

   @Support
   <T> CaseConditionStep<T> when(Condition var1, Field<T> var2);

   @Support
   <T> CaseConditionStep<T> when(Condition var1, Select<? extends Record1<T>> var2);
}
