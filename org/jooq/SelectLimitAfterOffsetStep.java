package org.jooq;

public interface SelectLimitAfterOffsetStep<R extends Record> extends SelectForUpdateStep<R> {
   @Support
   SelectForUpdateStep<R> limit(int var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   SelectForUpdateStep<R> limit(Param<Integer> var1);
}
