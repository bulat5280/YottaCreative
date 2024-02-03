package org.jooq;

import java.util.Collection;

public interface AggregateFilterStep<T> extends WindowBeforeOverStep<T> {
   @Support
   WindowBeforeOverStep<T> filterWhere(Condition... var1);

   @Support
   WindowBeforeOverStep<T> filterWhere(Collection<? extends Condition> var1);

   @Support
   WindowBeforeOverStep<T> filterWhere(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support
   WindowBeforeOverStep<T> filterWhere(Boolean var1);

   @Support
   @PlainSQL
   WindowBeforeOverStep<T> filterWhere(SQL var1);

   @Support
   @PlainSQL
   WindowBeforeOverStep<T> filterWhere(String var1);

   @Support
   @PlainSQL
   WindowBeforeOverStep<T> filterWhere(String var1, Object... var2);

   @Support
   @PlainSQL
   WindowBeforeOverStep<T> filterWhere(String var1, QueryPart... var2);
}
