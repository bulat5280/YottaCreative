package org.jooq;

public interface MergeNotMatchedWhereStep<R extends Record> extends MergeFinalStep<R> {
   @Support({SQLDialect.CUBRID})
   MergeFinalStep<R> where(Condition var1);

   @Support({SQLDialect.CUBRID})
   MergeFinalStep<R> where(Field<Boolean> var1);

   /** @deprecated */
   @Deprecated
   @Support({SQLDialect.CUBRID})
   MergeFinalStep<R> where(Boolean var1);
}
