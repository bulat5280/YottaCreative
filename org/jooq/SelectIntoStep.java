package org.jooq;

public interface SelectIntoStep<R extends Record> extends SelectFromStep<R> {
   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   SelectIntoStep<Record> into(Table<?> var1);
}
