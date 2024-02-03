package org.jooq;

import java.util.Collection;

public interface InsertValuesStep5<R extends Record, T1, T2, T3, T4, T5> extends InsertOnDuplicateStep<R> {
   @Support
   InsertValuesStep5<R, T1, T2, T3, T4, T5> values(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   @Support
   InsertValuesStep5<R, T1, T2, T3, T4, T5> values(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   @Support
   InsertValuesStep5<R, T1, T2, T3, T4, T5> values(Collection<?> var1);

   @Support
   InsertOnDuplicateStep<R> select(Select<? extends Record5<T1, T2, T3, T4, T5>> var1);
}
