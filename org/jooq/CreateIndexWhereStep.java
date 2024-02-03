package org.jooq;

import java.util.Collection;

public interface CreateIndexWhereStep extends CreateIndexFinalStep {
   @Support({SQLDialect.POSTGRES})
   CreateIndexFinalStep where(Condition... var1);

   @Support({SQLDialect.POSTGRES})
   CreateIndexFinalStep where(Collection<? extends Condition> var1);

   @Support({SQLDialect.POSTGRES})
   CreateIndexFinalStep where(Field<Boolean> var1);

   @Support({SQLDialect.POSTGRES})
   @PlainSQL
   CreateIndexFinalStep where(SQL var1);

   @Support({SQLDialect.POSTGRES})
   @PlainSQL
   CreateIndexFinalStep where(String var1);

   @Support({SQLDialect.POSTGRES})
   @PlainSQL
   CreateIndexFinalStep where(String var1, Object... var2);

   @Support({SQLDialect.POSTGRES})
   @PlainSQL
   CreateIndexFinalStep where(String var1, QueryPart... var2);
}
