package org.jooq;

public interface SelectUnionStep<R extends Record> extends SelectFinalStep<R> {
   @Support
   SelectOrderByStep<R> union(Select<? extends R> var1);

   @Support
   SelectOrderByStep<R> unionAll(Select<? extends R> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   SelectOrderByStep<R> except(Select<? extends R> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   SelectOrderByStep<R> exceptAll(Select<? extends R> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   SelectOrderByStep<R> intersect(Select<? extends R> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   SelectOrderByStep<R> intersectAll(Select<? extends R> var1);
}
