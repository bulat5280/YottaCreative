package org.jooq;

public interface LoaderOptionsStep<R extends Record> extends LoaderSourceStep<R> {
   @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   LoaderOptionsStep<R> onDuplicateKeyUpdate();

   @Support
   LoaderOptionsStep<R> onDuplicateKeyIgnore();

   @Support
   LoaderOptionsStep<R> onDuplicateKeyError();

   @Support
   LoaderOptionsStep<R> onErrorIgnore();

   @Support
   LoaderOptionsStep<R> onErrorAbort();

   @Support
   LoaderOptionsStep<R> commitEach();

   @Support
   LoaderOptionsStep<R> commitAfter(int var1);

   @Support
   LoaderOptionsStep<R> commitAll();

   @Support
   LoaderOptionsStep<R> commitNone();

   @Support
   LoaderOptionsStep<R> batchAll();

   @Support
   LoaderOptionsStep<R> batchNone();

   @Support
   LoaderOptionsStep<R> batchAfter(int var1);

   @Support
   LoaderOptionsStep<R> bulkAll();

   @Support
   LoaderOptionsStep<R> bulkNone();

   @Support
   LoaderOptionsStep<R> bulkAfter(int var1);
}
