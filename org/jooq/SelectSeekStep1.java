package org.jooq;

public interface SelectSeekStep1<R extends Record, T1> extends SelectLimitStep<R> {
   SelectSeekLimitStep<R> seek(T1 var1);

   SelectSeekLimitStep<R> seek(Field<T1> var1);

   SelectSeekLimitStep<R> seekAfter(T1 var1);

   SelectSeekLimitStep<R> seekAfter(Field<T1> var1);

   SelectSeekLimitStep<R> seekBefore(T1 var1);

   SelectSeekLimitStep<R> seekBefore(Field<T1> var1);
}
