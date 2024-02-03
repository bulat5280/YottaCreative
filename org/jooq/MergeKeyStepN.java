package org.jooq;

import java.util.Collection;

public interface MergeKeyStepN<R extends Record> extends MergeValuesStepN<R> {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB})
   MergeValuesStepN<R> key(Field<?>... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB})
   MergeValuesStepN<R> key(Collection<? extends Field<?>> var1);
}
