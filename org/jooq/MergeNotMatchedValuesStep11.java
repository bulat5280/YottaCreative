package org.jooq;

import java.util.Collection;

public interface MergeNotMatchedValuesStep11<R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   MergeNotMatchedWhereStep<R> values(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   MergeNotMatchedWhereStep<R> values(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   MergeNotMatchedWhereStep<R> values(Collection<?> var1);
}
