package org.jooq;

import java.util.Collection;

public interface MergeKeyStep10<R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> extends MergeValuesStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   MergeValuesStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> key(Field<?>... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   MergeValuesStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> key(Collection<? extends Field<?>> var1);
}
