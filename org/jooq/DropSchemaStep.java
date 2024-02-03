package org.jooq;

public interface DropSchemaStep extends DropSchemaFinalStep {
   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   DropSchemaFinalStep cascade();

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   DropSchemaFinalStep restrict();
}
