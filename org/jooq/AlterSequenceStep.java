package org.jooq;

public interface AlterSequenceStep<T extends Number> extends AlterSequenceRestartStep<T> {
   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterSequenceFinalStep restart();

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterSequenceFinalStep restartWith(T var1);

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterSequenceFinalStep renameTo(Sequence<?> var1);

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterSequenceFinalStep renameTo(Name var1);

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterSequenceFinalStep renameTo(String var1);
}
