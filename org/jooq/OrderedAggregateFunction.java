package org.jooq;

import java.util.Collection;

public interface OrderedAggregateFunction<T> {
   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   AggregateFilterStep<T> withinGroupOrderBy(Field<?>... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   AggregateFilterStep<T> withinGroupOrderBy(SortField<?>... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   AggregateFilterStep<T> withinGroupOrderBy(Collection<? extends SortField<?>> var1);
}
