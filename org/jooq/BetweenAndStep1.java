package org.jooq;

public interface BetweenAndStep1<T1> {
   @Support
   Condition and(Field<T1> var1);

   @Support
   Condition and(T1 var1);

   @Support
   Condition and(Row1<T1> var1);

   @Support
   Condition and(Record1<T1> var1);
}
