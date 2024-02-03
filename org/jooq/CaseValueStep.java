package org.jooq;

import java.util.Map;

public interface CaseValueStep<V> {
   @Support
   <T> CaseWhenStep<V, T> when(V var1, T var2);

   @Support
   <T> CaseWhenStep<V, T> when(V var1, Field<T> var2);

   @Support
   <T> CaseWhenStep<V, T> when(V var1, Select<? extends Record1<T>> var2);

   @Support
   <T> CaseWhenStep<V, T> when(Field<V> var1, T var2);

   @Support
   <T> CaseWhenStep<V, T> when(Field<V> var1, Field<T> var2);

   @Support
   <T> CaseWhenStep<V, T> when(Field<V> var1, Select<? extends Record1<T>> var2);

   <T> CaseWhenStep<V, T> mapValues(Map<V, T> var1);

   <T> CaseWhenStep<V, T> mapFields(Map<? extends Field<V>, ? extends Field<T>> var1);
}
