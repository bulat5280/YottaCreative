package org.jooq;

public interface WindowOverStep<T> {
   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WindowPartitionByStep<T> over();

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowFinalStep<T> over(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowFinalStep<T> over(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowFinalStep<T> over(WindowSpecification var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowFinalStep<T> over(WindowDefinition var1);
}
