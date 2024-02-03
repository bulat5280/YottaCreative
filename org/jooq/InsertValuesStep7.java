package org.jooq;

import java.util.Collection;

public interface InsertValuesStep7<R extends Record, T1, T2, T3, T4, T5, T6, T7> extends InsertOnDuplicateStep<R> {
   @Support
   InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> values(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

   @Support
   InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> values(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   @Support
   InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> values(Collection<?> var1);

   @Support
   InsertOnDuplicateStep<R> select(Select<? extends Record7<T1, T2, T3, T4, T5, T6, T7>> var1);
}
