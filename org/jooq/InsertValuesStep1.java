package org.jooq;

import java.util.Collection;

public interface InsertValuesStep1<R extends Record, T1> extends InsertOnDuplicateStep<R> {
   @Support
   InsertValuesStep1<R, T1> values(T1 var1);

   @Support
   InsertValuesStep1<R, T1> values(Field<T1> var1);

   @Support
   InsertValuesStep1<R, T1> values(Collection<?> var1);

   @Support
   InsertOnDuplicateStep<R> select(Select<? extends Record1<T1>> var1);
}
