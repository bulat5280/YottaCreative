package org.jooq;

public interface TableOnConditionStep<R extends Record> extends Table<R> {
   @Support
   TableOnConditionStep<R> and(Condition var1);

   @Support
   TableOnConditionStep<R> and(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   TableOnConditionStep<R> and(Boolean var1);

   @Support
   @PlainSQL
   TableOnConditionStep<R> and(SQL var1);

   @Support
   @PlainSQL
   TableOnConditionStep<R> and(String var1);

   @Support
   @PlainSQL
   TableOnConditionStep<R> and(String var1, Object... var2);

   @Support
   @PlainSQL
   TableOnConditionStep<R> and(String var1, QueryPart... var2);

   @Support
   TableOnConditionStep<R> andNot(Condition var1);

   @Support
   TableOnConditionStep<R> andNot(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   TableOnConditionStep<R> andNot(Boolean var1);

   @Support
   TableOnConditionStep<R> andExists(Select<?> var1);

   @Support
   TableOnConditionStep<R> andNotExists(Select<?> var1);

   @Support
   TableOnConditionStep<R> or(Condition var1);

   @Support
   TableOnConditionStep<R> or(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   TableOnConditionStep<R> or(Boolean var1);

   @Support
   @PlainSQL
   TableOnConditionStep<R> or(SQL var1);

   @Support
   @PlainSQL
   TableOnConditionStep<R> or(String var1);

   @Support
   @PlainSQL
   TableOnConditionStep<R> or(String var1, Object... var2);

   @Support
   @PlainSQL
   TableOnConditionStep<R> or(String var1, QueryPart... var2);

   @Support
   TableOnConditionStep<R> orNot(Condition var1);

   @Support
   TableOnConditionStep<R> orNot(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   TableOnConditionStep<R> orNot(Boolean var1);

   @Support
   TableOnConditionStep<R> orExists(Select<?> var1);

   @Support
   TableOnConditionStep<R> orNotExists(Select<?> var1);
}
