package org.jooq;

import java.util.Collection;

public interface MergeKeyStep12<R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> extends MergeValuesStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   MergeValuesStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> key(Field<?>... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   MergeValuesStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> key(Collection<? extends Field<?>> var1);
}
