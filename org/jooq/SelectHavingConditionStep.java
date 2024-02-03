package org.jooq;

public interface SelectHavingConditionStep<R extends Record> extends SelectWindowStep<R> {
   @Support
   SelectHavingConditionStep<R> and(Condition var1);

   @Support
   SelectHavingConditionStep<R> and(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   SelectHavingConditionStep<R> and(Boolean var1);

   @Support
   @PlainSQL
   SelectHavingConditionStep<R> and(SQL var1);

   @Support
   @PlainSQL
   SelectHavingConditionStep<R> and(String var1);

   @Support
   @PlainSQL
   SelectHavingConditionStep<R> and(String var1, Object... var2);

   @Support
   @PlainSQL
   SelectHavingConditionStep<R> and(String var1, QueryPart... var2);

   @Support
   SelectHavingConditionStep<R> andNot(Condition var1);

   @Support
   SelectHavingConditionStep<R> andNot(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   SelectHavingConditionStep<R> andNot(Boolean var1);

   @Support
   SelectHavingConditionStep<R> andExists(Select<?> var1);

   @Support
   SelectHavingConditionStep<R> andNotExists(Select<?> var1);

   @Support
   SelectHavingConditionStep<R> or(Condition var1);

   @Support
   SelectHavingConditionStep<R> or(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   SelectHavingConditionStep<R> or(Boolean var1);

   @Support
   @PlainSQL
   SelectHavingConditionStep<R> or(SQL var1);

   @Support
   @PlainSQL
   SelectHavingConditionStep<R> or(String var1);

   @Support
   @PlainSQL
   SelectHavingConditionStep<R> or(String var1, Object... var2);

   @Support
   @PlainSQL
   SelectHavingConditionStep<R> or(String var1, QueryPart... var2);

   @Support
   SelectHavingConditionStep<R> orNot(Condition var1);

   @Support
   SelectHavingConditionStep<R> orNot(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   SelectHavingConditionStep<R> orNot(Boolean var1);

   @Support
   SelectHavingConditionStep<R> orExists(Select<?> var1);

   @Support
   SelectHavingConditionStep<R> orNotExists(Select<?> var1);
}
