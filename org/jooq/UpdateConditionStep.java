package org.jooq;

public interface UpdateConditionStep<R extends Record> extends UpdateFinalStep<R>, UpdateReturningStep<R> {
   @Support
   UpdateConditionStep<R> and(Condition var1);

   @Support
   UpdateConditionStep<R> and(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   UpdateConditionStep<R> and(Boolean var1);

   @Support
   @PlainSQL
   UpdateConditionStep<R> and(SQL var1);

   @Support
   @PlainSQL
   UpdateConditionStep<R> and(String var1);

   @Support
   @PlainSQL
   UpdateConditionStep<R> and(String var1, Object... var2);

   @Support
   @PlainSQL
   UpdateConditionStep<R> and(String var1, QueryPart... var2);

   @Support
   UpdateConditionStep<R> andNot(Condition var1);

   @Support
   UpdateConditionStep<R> andNot(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   UpdateConditionStep<R> andNot(Boolean var1);

   @Support
   UpdateConditionStep<R> andExists(Select<?> var1);

   @Support
   UpdateConditionStep<R> andNotExists(Select<?> var1);

   @Support
   UpdateConditionStep<R> or(Condition var1);

   @Support
   UpdateConditionStep<R> or(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   UpdateConditionStep<R> or(Boolean var1);

   @Support
   @PlainSQL
   UpdateConditionStep<R> or(SQL var1);

   @Support
   @PlainSQL
   UpdateConditionStep<R> or(String var1);

   @Support
   @PlainSQL
   UpdateConditionStep<R> or(String var1, Object... var2);

   @Support
   @PlainSQL
   UpdateConditionStep<R> or(String var1, QueryPart... var2);

   @Support
   UpdateConditionStep<R> orNot(Condition var1);

   @Support
   UpdateConditionStep<R> orNot(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   UpdateConditionStep<R> orNot(Boolean var1);

   @Support
   UpdateConditionStep<R> orExists(Select<?> var1);

   @Support
   UpdateConditionStep<R> orNotExists(Select<?> var1);
}
