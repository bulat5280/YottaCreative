package org.jooq;

public interface WindowPartitionByStep<T> extends WindowOrderByStep<T> {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowOrderByStep<T> partitionBy(Field<?>... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowOrderByStep<T> partitionByOne();
}
