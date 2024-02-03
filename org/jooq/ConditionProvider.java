package org.jooq;

import java.util.Collection;

/** @deprecated */
@Deprecated
public interface ConditionProvider {
   @Support
   void addConditions(Condition... var1);

   @Support
   void addConditions(Collection<? extends Condition> var1);

   @Support
   void addConditions(Operator var1, Condition... var2);

   @Support
   void addConditions(Operator var1, Collection<? extends Condition> var2);
}
