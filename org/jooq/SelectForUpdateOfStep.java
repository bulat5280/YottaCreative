package org.jooq;

import java.util.Collection;

public interface SelectForUpdateOfStep<R extends Record> extends SelectForUpdateWaitStep<R> {
   @Support({SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB})
   SelectForUpdateWaitStep<R> of(Field<?>... var1);

   @Support({SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB})
   SelectForUpdateWaitStep<R> of(Collection<? extends Field<?>> var1);

   @Support({SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   SelectForUpdateWaitStep<R> of(Table<?>... var1);
}
