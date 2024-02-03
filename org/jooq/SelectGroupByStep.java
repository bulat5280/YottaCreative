package org.jooq;

import java.util.Collection;

public interface SelectGroupByStep<R extends Record> extends SelectHavingStep<R> {
   @Support
   SelectHavingStep<R> groupBy(GroupField... var1);

   @Support
   SelectHavingStep<R> groupBy(Collection<? extends GroupField> var1);
}
