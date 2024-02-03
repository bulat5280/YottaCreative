package org.jooq;

import java.util.Collection;

public interface MergeNotMatchedValuesStep3<R extends Record, T1, T2, T3> {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   MergeNotMatchedWhereStep<R> values(T1 var1, T2 var2, T3 var3);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   MergeNotMatchedWhereStep<R> values(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB})
   MergeNotMatchedWhereStep<R> values(Collection<?> var1);
}
