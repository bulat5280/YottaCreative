package org.jooq;

public interface CaseConditionStep<T> extends Field<T> {
   @Support
   CaseConditionStep<T> when(Condition var1, T var2);

   @Support
   CaseConditionStep<T> when(Condition var1, Field<T> var2);

   @Support
   CaseConditionStep<T> when(Condition var1, Select<? extends Record1<T>> var2);

   @Support
   Field<T> otherwise(T var1);

   @Support
   Field<T> otherwise(Field<T> var1);

   @Support
   Field<T> otherwise(Select<? extends Record1<T>> var1);
}
