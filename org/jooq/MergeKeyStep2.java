package org.jooq;

import java.util.Collection;

public interface MergeKeyStep2<R extends Record, T1, T2> extends MergeValuesStep2<R, T1, T2> {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   MergeValuesStep2<R, T1, T2> key(Field<?>... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   MergeValuesStep2<R, T1, T2> key(Collection<? extends Field<?>> var1);
}
