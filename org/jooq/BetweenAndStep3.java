package org.jooq;

public interface BetweenAndStep3<T1, T2, T3> {
   @Support
   Condition and(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support
   Condition and(T1 var1, T2 var2, T3 var3);

   @Support
   Condition and(Row3<T1, T2, T3> var1);

   @Support
   Condition and(Record3<T1, T2, T3> var1);
}
