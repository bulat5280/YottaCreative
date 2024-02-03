package org.jooq;

public interface MergeMatchedStep<R extends Record> extends MergeNotMatchedStep<R> {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   MergeMatchedSetStep<R> whenMatchedThenUpdate();
}
