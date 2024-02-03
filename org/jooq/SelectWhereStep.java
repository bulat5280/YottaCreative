package org.jooq;

import java.util.Collection;

public interface SelectWhereStep<R extends Record> extends SelectConnectByStep<R> {
   @Support
   SelectConditionStep<R> where(Condition... var1);

   @Support
   SelectConditionStep<R> where(Collection<? extends Condition> var1);

   @Support
   SelectConditionStep<R> where(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   SelectConditionStep<R> where(Boolean var1);

   @Support
   @PlainSQL
   SelectConditionStep<R> where(SQL var1);

   @Support
   @PlainSQL
   SelectConditionStep<R> where(String var1);

   @Support
   @PlainSQL
   SelectConditionStep<R> where(String var1, Object... var2);

   @Support
   @PlainSQL
   SelectConditionStep<R> where(String var1, QueryPart... var2);

   @Support
   SelectConditionStep<R> whereExists(Select<?> var1);

   @Support
   SelectConditionStep<R> whereNotExists(Select<?> var1);
}
