package org.jooq;

import java.util.Collection;

public interface UpdateReturningStep<R extends Record> {
   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   UpdateResultStep<R> returning();

   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   UpdateResultStep<R> returning(Field<?>... var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
   UpdateResultStep<R> returning(Collection<? extends Field<?>> var1);
}
