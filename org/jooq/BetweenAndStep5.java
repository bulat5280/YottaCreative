package org.jooq;

public interface BetweenAndStep5<T1, T2, T3, T4, T5> {
   @Support
   Condition and(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   @Support
   Condition and(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

   @Support
   Condition and(Row5<T1, T2, T3, T4, T5> var1);

   @Support
   Condition and(Record5<T1, T2, T3, T4, T5> var1);
}
