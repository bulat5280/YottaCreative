package org.jooq;

public interface MergeMatchedDeleteStep<R extends Record> extends MergeNotMatchedStep<R> {
   @Support({SQLDialect.CUBRID})
   MergeNotMatchedStep<R> deleteWhere(Condition var1);

   @Support({SQLDialect.CUBRID})
   MergeNotMatchedStep<R> deleteWhere(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support({SQLDialect.CUBRID})
   MergeNotMatchedStep<R> deleteWhere(Boolean var1);
}
