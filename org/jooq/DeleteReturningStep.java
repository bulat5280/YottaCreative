package org.jooq;

import java.util.Collection;

public interface DeleteReturningStep<R extends Record> {
   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   DeleteResultStep<R> returning();

   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   DeleteResultStep<R> returning(Field<?>... var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   DeleteResultStep<R> returning(Collection<? extends Field<?>> var1);
}
