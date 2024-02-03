package org.jooq;

import java.util.Collection;

public interface InsertReturningStep<R extends Record> extends InsertFinalStep<R> {
   @Support
   InsertResultStep<R> returning();

   @Support
   InsertResultStep<R> returning(Field<?>... var1);

   @Support
   InsertResultStep<R> returning(Collection<? extends Field<?>> var1);
}
