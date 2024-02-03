package org.jooq;

public interface BetweenAndStep4<T1, T2, T3, T4> {
   @Support
   Condition and(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support
   Condition and(T1 var1, T2 var2, T3 var3, T4 var4);

   @Support
   Condition and(Row4<T1, T2, T3, T4> var1);

   @Support
   Condition and(Record4<T1, T2, T3, T4> var1);
}
