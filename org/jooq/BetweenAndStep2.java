package org.jooq;

public interface BetweenAndStep2<T1, T2> {
   @Support
   Condition and(Field<T1> var1, Field<T2> var2);

   @Support
   Condition and(T1 var1, T2 var2);

   @Support
   Condition and(Row2<T1, T2> var1);

   @Support
   Condition and(Record2<T1, T2> var1);
}
