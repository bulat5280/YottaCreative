package org.jooq;

public interface Condition extends QueryPart {
   @Support
   Condition and(Condition var1);

   @Support
   Condition and(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   Condition and(Boolean var1);

   @Support
   @PlainSQL
   Condition and(SQL var1);

   @Support
   @PlainSQL
   Condition and(String var1);

   @Support
   @PlainSQL
   Condition and(String var1, Object... var2);

   @Support
   @PlainSQL
   Condition and(String var1, QueryPart... var2);

   @Support
   Condition andNot(Condition var1);

   @Support
   Condition andNot(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   Condition andNot(Boolean var1);

   @Support
   Condition andExists(Select<?> var1);

   @Support
   Condition andNotExists(Select<?> var1);

   @Support
   Condition or(Condition var1);

   @Support
   Condition or(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   Condition or(Boolean var1);

   @Support
   @PlainSQL
   Condition or(SQL var1);

   @Support
   @PlainSQL
   Condition or(String var1);

   @Support
   @PlainSQL
   Condition or(String var1, Object... var2);

   @Support
   @PlainSQL
   Condition or(String var1, QueryPart... var2);

   @Support
   Condition orNot(Condition var1);

   @Support
   Condition orNot(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   Condition orNot(Boolean var1);

   @Support
   Condition orExists(Select<?> var1);

   @Support
   Condition orNotExists(Select<?> var1);

   @Support
   Condition not();
}
