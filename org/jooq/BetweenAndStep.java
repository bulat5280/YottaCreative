package org.jooq;

public interface BetweenAndStep<T> {
   @Support
   Condition and(T var1);

   @Support
   Condition and(Field<T> var1);
}
