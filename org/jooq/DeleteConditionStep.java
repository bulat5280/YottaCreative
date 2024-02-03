package org.jooq;

public interface DeleteConditionStep<R extends Record> extends DeleteFinalStep<R>, DeleteReturningStep<R> {
   @Support
   DeleteConditionStep<R> and(Condition var1);

   @Support
   DeleteConditionStep<R> and(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   DeleteConditionStep<R> and(Boolean var1);

   @Support
   @PlainSQL
   DeleteConditionStep<R> and(SQL var1);

   @Support
   @PlainSQL
   DeleteConditionStep<R> and(String var1);

   @Support
   @PlainSQL
   DeleteConditionStep<R> and(String var1, Object... var2);

   @Support
   @PlainSQL
   DeleteConditionStep<R> and(String var1, QueryPart... var2);

   @Support
   DeleteConditionStep<R> andNot(Condition var1);

   @Support
   DeleteConditionStep<R> andNot(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   DeleteConditionStep<R> andNot(Boolean var1);

   @Support
   DeleteConditionStep<R> andExists(Select<?> var1);

   DeleteConditionStep<R> andNotExists(Select<?> var1);

   @Support
   DeleteConditionStep<R> or(Condition var1);

   @Support
   DeleteConditionStep<R> or(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   DeleteConditionStep<R> or(Boolean var1);

   @Support
   @PlainSQL
   DeleteConditionStep<R> or(SQL var1);

   @Support
   @PlainSQL
   DeleteConditionStep<R> or(String var1);

   @Support
   @PlainSQL
   DeleteConditionStep<R> or(String var1, Object... var2);

   @Support
   @PlainSQL
   DeleteConditionStep<R> or(String var1, QueryPart... var2);

   @Support
   DeleteConditionStep<R> orNot(Condition var1);

   @Support
   DeleteConditionStep<R> orNot(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   DeleteConditionStep<R> orNot(Boolean var1);

   @Support
   DeleteConditionStep<R> orExists(Select<?> var1);

   @Support
   DeleteConditionStep<R> orNotExists(Select<?> var1);
}
