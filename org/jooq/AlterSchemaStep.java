package org.jooq;

public interface AlterSchemaStep {
   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterSchemaFinalStep renameTo(Schema var1);

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterSchemaFinalStep renameTo(Name var1);

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterSchemaFinalStep renameTo(String var1);
}
