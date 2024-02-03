package org.jooq;

public interface BetweenAndStepN {
   @Support
   Condition and(Field<?>... var1);

   @Support
   Condition and(Object... var1);

   @Support
   Condition and(RowN var1);

   @Support
   Condition and(Record var1);
}
