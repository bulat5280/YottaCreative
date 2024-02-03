package org.jooq.util.postgres.pg_catalog.routines;

import org.jooq.DataType;
import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;
import org.jooq.impl.SQLDataType;
import org.jooq.util.postgres.pg_catalog.PgCatalog;

public class Count2 extends AbstractRoutine<Long> {
   private static final long serialVersionUID = -853008897L;
   public static final Parameter<Long> RETURN_VALUE;

   public Count2() {
      super("count", PgCatalog.PG_CATALOG, (DataType)SQLDataType.BIGINT);
      this.setReturnParameter(RETURN_VALUE);
      this.setOverloaded(true);
   }

   static {
      RETURN_VALUE = createParameter("RETURN_VALUE", SQLDataType.BIGINT, false);
   }
}
