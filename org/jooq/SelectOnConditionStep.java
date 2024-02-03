package org.jooq;

public interface SelectOnConditionStep<R extends Record> extends SelectJoinStep<R> {
   @Support
   SelectOnConditionStep<R> and(Condition var1);

   @Support
   SelectOnConditionStep<R> and(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   SelectOnConditionStep<R> and(Boolean var1);

   @Support
   @PlainSQL
   SelectOnConditionStep<R> and(SQL var1);

   @Support
   @PlainSQL
   SelectOnConditionStep<R> and(String var1);

   @Support
   @PlainSQL
   SelectOnConditionStep<R> and(String var1, Object... var2);

   @Support
   @PlainSQL
   SelectOnConditionStep<R> and(String var1, QueryPart... var2);

   @Support
   SelectOnConditionStep<R> andNot(Condition var1);

   @Support
   SelectOnConditionStep<R> andNot(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   SelectOnConditionStep<R> andNot(Boolean var1);

   @Support
   SelectOnConditionStep<R> andExists(Select<?> var1);

   @Support
   SelectOnConditionStep<R> andNotExists(Select<?> var1);

   @Support
   SelectOnConditionStep<R> or(Condition var1);

   @Support
   SelectOnConditionStep<R> or(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   SelectOnConditionStep<R> or(Boolean var1);

   @Support
   @PlainSQL
   SelectOnConditionStep<R> or(SQL var1);

   @Support
   @PlainSQL
   SelectOnConditionStep<R> or(String var1);

   @Support
   @PlainSQL
   SelectOnConditionStep<R> or(String var1, Object... var2);

   @Support
   @PlainSQL
   SelectOnConditionStep<R> or(String var1, QueryPart... var2);

   @Support
   SelectOnConditionStep<R> orNot(Condition var1);

   @Support
   SelectOnConditionStep<R> orNot(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   SelectOnConditionStep<R> orNot(Boolean var1);

   @Support
   SelectOnConditionStep<R> orExists(Select<?> var1);

   @Support
   SelectOnConditionStep<R> orNotExists(Select<?> var1);
}
