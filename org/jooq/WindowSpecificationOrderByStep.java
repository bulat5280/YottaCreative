package org.jooq;

import java.util.Collection;

public interface WindowSpecificationOrderByStep extends WindowSpecificationRowsStep {
   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowSpecificationRowsStep orderBy(Field<?>... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowSpecificationRowsStep orderBy(SortField<?>... var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.POSTGRES})
   WindowSpecificationRowsStep orderBy(Collection<? extends SortField<?>> var1);
}
