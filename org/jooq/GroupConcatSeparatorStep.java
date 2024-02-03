package org.jooq;

public interface GroupConcatSeparatorStep extends AggregateFunction<String> {
   @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   AggregateFunction<String> separator(String var1);
}
