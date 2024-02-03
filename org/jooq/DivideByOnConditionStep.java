package org.jooq;

public interface DivideByOnConditionStep extends DivideByReturningStep {
   @Support
   DivideByOnConditionStep and(Condition var1);

   @Support
   DivideByOnConditionStep and(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   DivideByOnConditionStep and(Boolean var1);

   @Support
   @PlainSQL
   DivideByOnConditionStep and(SQL var1);

   @Support
   @PlainSQL
   DivideByOnConditionStep and(String var1);

   @Support
   @PlainSQL
   DivideByOnConditionStep and(String var1, Object... var2);

   @Support
   @PlainSQL
   DivideByOnConditionStep and(String var1, QueryPart... var2);

   @Support
   DivideByOnConditionStep andNot(Condition var1);

   @Support
   DivideByOnConditionStep andNot(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   DivideByOnConditionStep andNot(Boolean var1);

   @Support
   DivideByOnConditionStep andExists(Select<?> var1);

   @Support
   DivideByOnConditionStep andNotExists(Select<?> var1);

   @Support
   DivideByOnConditionStep or(Condition var1);

   @Support
   DivideByOnConditionStep or(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   DivideByOnConditionStep or(Boolean var1);

   @Support
   @PlainSQL
   DivideByOnConditionStep or(SQL var1);

   @Support
   @PlainSQL
   DivideByOnConditionStep or(String var1);

   @Support
   @PlainSQL
   DivideByOnConditionStep or(String var1, Object... var2);

   @Support
   @PlainSQL
   DivideByOnConditionStep or(String var1, QueryPart... var2);

   @Support
   DivideByOnConditionStep orNot(Condition var1);

   @Support
   DivideByOnConditionStep orNot(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   DivideByOnConditionStep orNot(Boolean var1);

   @Support
   DivideByOnConditionStep orExists(Select<?> var1);

   @Support
   DivideByOnConditionStep orNotExists(Select<?> var1);
}
