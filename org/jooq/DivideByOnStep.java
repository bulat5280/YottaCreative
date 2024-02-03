package org.jooq;

public interface DivideByOnStep {
   @Support
   DivideByOnConditionStep on(Condition... var1);

   @Support
   DivideByOnConditionStep on(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   DivideByOnConditionStep on(Boolean var1);

   @Support
   @PlainSQL
   DivideByOnConditionStep on(SQL var1);

   @Support
   @PlainSQL
   DivideByOnConditionStep on(String var1);

   @Support
   @PlainSQL
   DivideByOnConditionStep on(String var1, Object... var2);

   @Support
   @PlainSQL
   DivideByOnConditionStep on(String var1, QueryPart... var2);
}
