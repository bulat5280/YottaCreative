package org.jooq;

import java.util.Collection;

public interface MergeValuesStep4<R extends Record, T1, T2, T3, T4> {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   Merge<R> values(T1 var1, T2 var2, T3 var3, T4 var4);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   Merge<R> values(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   Merge<R> values(Collection<?> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB})
   Merge<R> select(Select<? extends Record4<T1, T2, T3, T4>> var1);
}
