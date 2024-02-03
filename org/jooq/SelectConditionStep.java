package org.jooq;

public interface SelectConditionStep<R extends Record> extends SelectConnectByStep<R> {
   @Support
   SelectConditionStep<R> and(Condition var1);

   @Support
   SelectConditionStep<R> and(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   SelectConditionStep<R> and(Boolean var1);

   @Support
   @PlainSQL
   SelectConditionStep<R> and(SQL var1);

   @Support
   @PlainSQL
   SelectConditionStep<R> and(String var1);

   @Support
   @PlainSQL
   SelectConditionStep<R> and(String var1, Object... var2);

   @Support
   @PlainSQL
   SelectConditionStep<R> and(String var1, QueryPart... var2);

   @Support
   SelectConditionStep<R> andNot(Condition var1);

   @Support
   SelectConditionStep<R> andNot(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   SelectConditionStep<R> andNot(Boolean var1);

   @Support
   SelectConditionStep<R> andExists(Select<?> var1);

   @Support
   SelectConditionStep<R> andNotExists(Select<?> var1);

   @Support
   SelectConditionStep<R> or(Condition var1);

   @Support
   SelectConditionStep<R> or(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   SelectConditionStep<R> or(Boolean var1);

   @Support
   @PlainSQL
   SelectConditionStep<R> or(SQL var1);

   @Support
   @PlainSQL
   SelectConditionStep<R> or(String var1);

   @Support
   @PlainSQL
   SelectConditionStep<R> or(String var1, Object... var2);

   @Support
   @PlainSQL
   SelectConditionStep<R> or(String var1, QueryPart... var2);

   @Support
   SelectConditionStep<R> orNot(Condition var1);

   @Support
   SelectConditionStep<R> orNot(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   SelectConditionStep<R> orNot(Boolean var1);

   @Support
   SelectConditionStep<R> orExists(Select<?> var1);

   @Support
   SelectConditionStep<R> orNotExists(Select<?> var1);
}
