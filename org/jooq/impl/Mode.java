package org.jooq.impl;

import org.jooq.AggregateFilterStep;
import org.jooq.Field;
import org.jooq.OrderedAggregateFunctionOfDeferredType;
import org.jooq.QueryPart;
import org.jooq.SortField;

final class Mode implements OrderedAggregateFunctionOfDeferredType {
   public final <T> AggregateFilterStep<T> withinGroupOrderBy(Field<T> field) {
      return (new Function("mode", field.getDataType(), new QueryPart[0])).withinGroupOrderBy(field);
   }

   public final <T> AggregateFilterStep<T> withinGroupOrderBy(SortField<T> field) {
      return (new Function("mode", ((SortFieldImpl)field).getField().getDataType(), new QueryPart[0])).withinGroupOrderBy(field);
   }
}
