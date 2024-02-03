package org.jooq;

import java.util.Collection;

public interface WindowSpecificationPartitionByStep extends WindowSpecificationOrderByStep {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowSpecificationOrderByStep partitionBy(Field<?>... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowSpecificationOrderByStep partitionBy(Collection<? extends Field<?>> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowSpecificationOrderByStep partitionByOne();
}
