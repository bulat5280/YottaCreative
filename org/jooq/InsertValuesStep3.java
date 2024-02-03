package org.jooq;

import java.util.Collection;

public interface InsertValuesStep3<R extends Record, T1, T2, T3> extends InsertOnDuplicateStep<R> {
   @Support
   InsertValuesStep3<R, T1, T2, T3> values(T1 var1, T2 var2, T3 var3);

   @Support
   InsertValuesStep3<R, T1, T2, T3> values(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support
   InsertValuesStep3<R, T1, T2, T3> values(Collection<?> var1);

   @Support
   InsertOnDuplicateStep<R> select(Select<? extends Record3<T1, T2, T3>> var1);
}
