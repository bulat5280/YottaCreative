package org.jooq;

public interface SelectSeekStep5<R extends Record, T1, T2, T3, T4, T5> extends SelectLimitStep<R> {
   SelectSeekLimitStep<R> seek(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   SelectSeekLimitStep<R> seek(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   SelectSeekLimitStep<R> seekAfter(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   SelectSeekLimitStep<R> seekAfter(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   SelectSeekLimitStep<R> seekBefore(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   SelectSeekLimitStep<R> seekBefore(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);
}
