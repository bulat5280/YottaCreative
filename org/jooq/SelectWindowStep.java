package org.jooq;

import java.util.Collection;

public interface SelectWindowStep<R extends Record> extends SelectOrderByStep<R> {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   SelectOrderByStep<R> window(WindowDefinition... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   SelectOrderByStep<R> window(Collection<? extends WindowDefinition> var1);
}
