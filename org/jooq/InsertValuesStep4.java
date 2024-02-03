package org.jooq;

import java.util.Collection;

public interface InsertValuesStep4<R extends Record, T1, T2, T3, T4> extends InsertOnDuplicateStep<R> {
   @Support
   InsertValuesStep4<R, T1, T2, T3, T4> values(T1 var1, T2 var2, T3 var3, T4 var4);

   @Support
   InsertValuesStep4<R, T1, T2, T3, T4> values(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support
   InsertValuesStep4<R, T1, T2, T3, T4> values(Collection<?> var1);

   @Support
   InsertOnDuplicateStep<R> select(Select<? extends Record4<T1, T2, T3, T4>> var1);
}
