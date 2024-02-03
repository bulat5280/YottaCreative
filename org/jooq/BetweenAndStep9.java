package org.jooq;

public interface BetweenAndStep9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
   @Support
   Condition and(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   @Support
   Condition and(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);

   @Support
   Condition and(Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);

   @Support
   Condition and(Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> var1);
}
