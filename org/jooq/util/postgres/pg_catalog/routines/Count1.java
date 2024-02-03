package org.jooq.util.postgres.pg_catalog.routines;

import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;
import org.jooq.impl.SQLDataType;
import org.jooq.util.postgres.pg_catalog.PgCatalog;

public class Count1 extends AbstractRoutine<Long> {
   private static final long serialVersionUID = 1874146908L;
   public static final Parameter<Long> RETURN_VALUE;
   public static final Parameter<Object> _1;

   public Count1() {
      super("count", PgCatalog.PG_CATALOG, (DataType)SQLDataType.BIGINT);
      this.setReturnParameter(RETURN_VALUE);
      this.addInParameter(_1);
      this.setOverloaded(true);
   }

   public void set__1(Object value) {
      this.setValue(_1, value);
   }

   public void set__1(Field<Object> field) {
      this.setField(_1, field);
   }

   static {
      RETURN_VALUE = createParameter("RETURN_VALUE", SQLDataType.BIGINT, false);
      _1 = createParameter("_1", SQLDataType.OTHER, false);
   }
}
