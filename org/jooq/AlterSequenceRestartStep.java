package org.jooq;

public interface AlterSequenceRestartStep<T extends Number> {
   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterSequenceFinalStep restart();

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterSequenceFinalStep restartWith(T var1);
}
