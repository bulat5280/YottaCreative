package org.jooq;

public interface SelectSeekStep2<R extends Record, T1, T2> extends SelectLimitStep<R> {
   SelectSeekLimitStep<R> seek(T1 var1, T2 var2);

   SelectSeekLimitStep<R> seek(Field<T1> var1, Field<T2> var2);

   SelectSeekLimitStep<R> seekAfter(T1 var1, T2 var2);

   SelectSeekLimitStep<R> seekAfter(Field<T1> var1, Field<T2> var2);

   SelectSeekLimitStep<R> seekBefore(T1 var1, T2 var2);

   SelectSeekLimitStep<R> seekBefore(Field<T1> var1, Field<T2> var2);
}
