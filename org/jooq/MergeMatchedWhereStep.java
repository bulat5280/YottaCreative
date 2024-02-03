package org.jooq;

public interface MergeMatchedWhereStep<R extends Record> extends MergeNotMatchedStep<R> {
   @Support({SQLDialect.CUBRID})
   MergeMatchedDeleteStep<R> where(Condition var1);

   @Support({SQLDialect.CUBRID})
   MergeMatchedDeleteStep<R> where(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support({SQLDialect.CUBRID})
   MergeMatchedDeleteStep<R> where(Boolean var1);
}
