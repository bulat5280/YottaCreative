package org.jooq;

public interface DropIndexOnStep extends DropIndexFinalStep {
   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
   DropIndexFinalStep on(Table<?> var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
   DropIndexFinalStep on(String var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL})
   DropIndexFinalStep on(Name var1);
}
