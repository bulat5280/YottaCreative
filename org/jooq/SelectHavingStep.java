package org.jooq;

import java.util.Collection;

public interface SelectHavingStep<R extends Record> extends SelectWindowStep<R> {
   @Support
   SelectHavingConditionStep<R> having(Condition... var1);

   @Support
   SelectHavingConditionStep<R> having(Collection<? extends Condition> var1);

   @Support
   SelectHavingConditionStep<R> having(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   SelectHavingConditionStep<R> having(Boolean var1);

   @Support
   @PlainSQL
   SelectHavingConditionStep<R> having(SQL var1);

   @Support
   @PlainSQL
   SelectHavingConditionStep<R> having(String var1);

   @Support
   @PlainSQL
   SelectHavingConditionStep<R> having(String var1, Object... var2);

   @Support
   @PlainSQL
   SelectHavingConditionStep<R> having(String var1, QueryPart... var2);
}
