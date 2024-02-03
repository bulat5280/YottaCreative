package org.jooq;

public interface InsertOnConflictDoUpdateStep<R extends Record> {
   @Support({SQLDialect.POSTGRES_9_5})
   InsertOnDuplicateSetStep<R> doUpdate();

   @Support({SQLDialect.POSTGRES_9_5})
   InsertFinalStep<R> doNothing();
}
