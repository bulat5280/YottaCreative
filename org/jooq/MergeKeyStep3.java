package org.jooq;

import java.util.Collection;

public interface MergeKeyStep3<R extends Record, T1, T2, T3> extends MergeValuesStep3<R, T1, T2, T3> {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   MergeValuesStep3<R, T1, T2, T3> key(Field<?>... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   MergeValuesStep3<R, T1, T2, T3> key(Collection<? extends Field<?>> var1);
}
