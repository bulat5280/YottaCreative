package org.jooq;

import java.util.Collection;

public interface DeleteWhereStep<R extends Record> extends DeleteFinalStep<R>, DeleteReturningStep<R> {
   @Support
   DeleteConditionStep<R> where(Condition... var1);

   @Support
   DeleteConditionStep<R> where(Collection<? extends Condition> var1);

   @Support
   DeleteConditionStep<R> where(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   DeleteConditionStep<R> where(Boolean var1);

   @Support
   @PlainSQL
   DeleteConditionStep<R> where(SQL var1);

   @Support
   @PlainSQL
   DeleteConditionStep<R> where(String var1);

   @Support
   @PlainSQL
   DeleteConditionStep<R> where(String var1, Object... var2);

   @Support
   @PlainSQL
   DeleteConditionStep<R> where(String var1, QueryPart... var2);

   @Support
   DeleteConditionStep<R> whereExists(Select<?> var1);

   @Support
   DeleteConditionStep<R> whereNotExists(Select<?> var1);
}
