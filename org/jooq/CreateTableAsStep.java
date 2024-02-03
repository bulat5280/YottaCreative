package org.jooq;

import java.util.Collection;

public interface CreateTableAsStep<R extends Record> {
   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateTableOnCommitStep as(Select<? extends R> var1);

   @Support
   CreateTableColumnStep column(Field<?> var1);

   @Support
   <T> CreateTableColumnStep column(Field<T> var1, DataType<T> var2);

   @Support
   CreateTableColumnStep column(Name var1, DataType<?> var2);

   @Support
   CreateTableColumnStep column(String var1, DataType<?> var2);

   @Support
   CreateTableColumnStep columns(Field<?>... var1);

   @Support
   CreateTableColumnStep columns(Collection<? extends Field<?>> var1);
}
