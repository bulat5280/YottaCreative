package org.jooq;

import java.util.Map;

public interface InsertOnDuplicateSetStep<R extends Record> {
   @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL})
   <T> InsertOnDuplicateSetMoreStep<R> set(Field<T> var1, T var2);

   @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL})
   <T> InsertOnDuplicateSetMoreStep<R> set(Field<T> var1, Field<T> var2);

   @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL})
   <T> InsertOnDuplicateSetMoreStep<R> set(Field<T> var1, Select<? extends Record1<T>> var2);

   @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL})
   InsertOnDuplicateSetMoreStep<R> set(Map<? extends Field<?>, ?> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL})
   InsertOnDuplicateSetMoreStep<R> set(Record var1);
}
