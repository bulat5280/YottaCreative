package org.jooq;

public interface SelectForUpdateStep<R extends Record> extends SelectOptionStep<R> {
   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   SelectForUpdateOfStep<R> forUpdate();

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   SelectOptionStep<R> forShare();
}
