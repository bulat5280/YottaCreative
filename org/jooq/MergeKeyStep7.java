package org.jooq;

import java.util.Collection;

public interface MergeKeyStep7<R extends Record, T1, T2, T3, T4, T5, T6, T7> extends MergeValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   MergeValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> key(Field<?>... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   MergeValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> key(Collection<? extends Field<?>> var1);
}
