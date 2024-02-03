package org.jooq;

public interface TruncateIdentityStep<R extends Record> extends TruncateCascadeStep<R> {
   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   TruncateCascadeStep<R> restartIdentity();

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   TruncateCascadeStep<R> continueIdentity();
}
