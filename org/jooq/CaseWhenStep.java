package org.jooq;

import java.util.Map;

public interface CaseWhenStep<V, T> extends Field<T> {
   @Support
   CaseWhenStep<V, T> when(V var1, T var2);

   @Support
   CaseWhenStep<V, T> when(V var1, Field<T> var2);

   @Support
   CaseWhenStep<V, T> when(Field<V> var1, T var2);

   @Support
   CaseWhenStep<V, T> when(Field<V> var1, Field<T> var2);

   CaseWhenStep<V, T> mapValues(Map<V, T> var1);

   CaseWhenStep<V, T> mapFields(Map<? extends Field<V>, ? extends Field<T>> var1);

   @Support
   Field<T> otherwise(T var1);

   @Support
   Field<T> otherwise(Field<T> var1);
}
