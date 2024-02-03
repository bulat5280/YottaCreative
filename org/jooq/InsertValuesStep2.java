package org.jooq;

import java.util.Collection;

public interface InsertValuesStep2<R extends Record, T1, T2> extends InsertOnDuplicateStep<R> {
   @Support
   InsertValuesStep2<R, T1, T2> values(T1 var1, T2 var2);

   @Support
   InsertValuesStep2<R, T1, T2> values(Field<T1> var1, Field<T2> var2);

   @Support
   InsertValuesStep2<R, T1, T2> values(Collection<?> var1);

   @Support
   InsertOnDuplicateStep<R> select(Select<? extends Record2<T1, T2>> var1);
}
