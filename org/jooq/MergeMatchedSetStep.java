package org.jooq;

import java.util.Map;

public interface MergeMatchedSetStep<R extends Record> {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   <T> MergeMatchedSetMoreStep<R> set(Field<T> var1, T var2);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   <T> MergeMatchedSetMoreStep<R> set(Field<T> var1, Field<T> var2);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   <T> MergeMatchedSetMoreStep<R> set(Field<T> var1, Select<? extends Record1<T>> var2);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   MergeMatchedSetMoreStep<R> set(Map<? extends Field<?>, ?> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   MergeMatchedSetMoreStep<R> set(Record var1);
}
