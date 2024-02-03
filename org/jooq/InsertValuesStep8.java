package org.jooq;

import java.util.Collection;

public interface InsertValuesStep8<R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> extends InsertOnDuplicateStep<R> {
   @Support
   InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> values(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

   @Support
   InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> values(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   @Support
   InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> values(Collection<?> var1);

   @Support
   InsertOnDuplicateStep<R> select(Select<? extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>> var1);
}
