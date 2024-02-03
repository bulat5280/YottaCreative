package org.jooq;

public interface AlterIndexStep {
   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterIndexFinalStep renameTo(Name var1);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterIndexFinalStep renameTo(String var1);
}
