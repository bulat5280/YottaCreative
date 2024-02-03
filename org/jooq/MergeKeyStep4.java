package org.jooq;

import java.util.Collection;

public interface MergeKeyStep4<R extends Record, T1, T2, T3, T4> extends MergeValuesStep4<R, T1, T2, T3, T4> {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   MergeValuesStep4<R, T1, T2, T3, T4> key(Field<?>... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   MergeValuesStep4<R, T1, T2, T3, T4> key(Collection<? extends Field<?>> var1);
}
