package org.jooq;

import java.util.Collection;

public interface UpdateWhereStep<R extends Record> extends UpdateFinalStep<R>, UpdateReturningStep<R> {
   @Support
   UpdateConditionStep<R> where(Condition... var1);

   @Support
   UpdateConditionStep<R> where(Collection<? extends Condition> var1);

   @Support
   UpdateConditionStep<R> where(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   UpdateConditionStep<R> where(Boolean var1);

   @Support
   @PlainSQL
   UpdateConditionStep<R> where(SQL var1);

   @Support
   @PlainSQL
   UpdateConditionStep<R> where(String var1);

   @Support
   @PlainSQL
   UpdateConditionStep<R> where(String var1, Object... var2);

   @Support
   @PlainSQL
   UpdateConditionStep<R> where(String var1, QueryPart... var2);

   @Support
   UpdateConditionStep<R> whereExists(Select<?> var1);

   @Support
   UpdateConditionStep<R> whereNotExists(Select<?> var1);
}
