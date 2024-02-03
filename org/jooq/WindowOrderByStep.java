package org.jooq;

import java.util.Collection;

public interface WindowOrderByStep<T> extends WindowFinalStep<T> {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowRowsStep<T> orderBy(Field<?>... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowRowsStep<T> orderBy(SortField<?>... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowRowsStep<T> orderBy(Collection<? extends SortField<?>> var1);
}
