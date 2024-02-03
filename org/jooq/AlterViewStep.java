package org.jooq;

public interface AlterViewStep {
   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterViewFinalStep renameTo(Table<?> var1);

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterViewFinalStep renameTo(Name var1);

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterViewFinalStep renameTo(String var1);
}
