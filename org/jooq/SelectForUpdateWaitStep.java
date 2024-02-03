package org.jooq;

public interface SelectForUpdateWaitStep<R extends Record> extends SelectOptionStep<R> {
   @Support({SQLDialect.POSTGRES})
   SelectOptionStep<R> noWait();

   @Support({SQLDialect.POSTGRES_9_5})
   SelectOptionStep<R> skipLocked();
}
