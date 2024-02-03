package org.jooq;

public interface CreateViewAsStep<R extends Record> {
   @Support
   CreateViewFinalStep as(Select<? extends R> var1);
}
