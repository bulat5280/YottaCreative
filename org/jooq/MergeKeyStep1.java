package org.jooq;

import java.util.Collection;

public interface MergeKeyStep1<R extends Record, T1> extends MergeValuesStep1<R, T1> {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   MergeValuesStep1<R, T1> key(Field<?>... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES_9_5})
   MergeValuesStep1<R, T1> key(Collection<? extends Field<?>> var1);
}
