package org.jooq;

import java.util.Collection;

public interface MergeKeyStep5<R extends Record, T1, T2, T3, T4, T5> extends MergeValuesStep5<R, T1, T2, T3, T4, T5> {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   MergeValuesStep5<R, T1, T2, T3, T4, T5> key(Field<?>... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   MergeValuesStep5<R, T1, T2, T3, T4, T5> key(Collection<? extends Field<?>> var1);
}
