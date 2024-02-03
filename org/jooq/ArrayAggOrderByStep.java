package org.jooq;

import java.util.Collection;

public interface ArrayAggOrderByStep<T> extends AggregateFilterStep<T> {
   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AggregateFilterStep<T> orderBy(Field<?>... var1);

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AggregateFilterStep<T> orderBy(SortField<?>... var1);

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AggregateFilterStep<T> orderBy(Collection<? extends SortField<?>> var1);
}
