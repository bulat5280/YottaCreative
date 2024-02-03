package org.jooq;

public interface TruncateCascadeStep<R extends Record> extends TruncateFinalStep<R> {
   @Support({SQLDialect.POSTGRES})
   TruncateFinalStep<R> cascade();

   @Support({SQLDialect.POSTGRES})
   TruncateFinalStep<R> restrict();
}
