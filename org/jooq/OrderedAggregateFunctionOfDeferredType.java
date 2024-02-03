package org.jooq;

public interface OrderedAggregateFunctionOfDeferredType {
   @Support({SQLDialect.POSTGRES_9_4})
   <T> AggregateFilterStep<T> withinGroupOrderBy(Field<T> var1);

   @Support({SQLDialect.POSTGRES_9_4})
   <T> AggregateFilterStep<T> withinGroupOrderBy(SortField<T> var1);
}
