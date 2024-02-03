package org.jooq.util.postgres.pg_catalog.routines;

import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;
import org.jooq.impl.SQLDataType;
import org.jooq.util.postgres.pg_catalog.PgCatalog;

public class FormatType extends AbstractRoutine<String> {
   private static final long serialVersionUID = -626180672L;
   public static final Parameter<String> RETURN_VALUE;
   public static final Parameter<Long> _1;
   public static final Parameter<Integer> _2;

   public FormatType() {
      super("format_type", PgCatalog.PG_CATALOG, (DataType)SQLDataType.CLOB);
      this.setReturnParameter(RETURN_VALUE);
      this.addInParameter(_1);
      this.addInParameter(_2);
   }

   public void set__1(Long value) {
      this.setValue(_1, value);
   }

   public void set__1(Field<Long> field) {
      this.setField(_1, field);
   }

   public void set__2(Integer value) {
      this.setValue(_2, value);
   }

   public void set__2(Field<Integer> field) {
      this.setField(_2, field);
   }

   static {
      RETURN_VALUE = createParameter("RETURN_VALUE", SQLDataType.CLOB, false);
      _1 = createParameter("_1", SQLDataType.BIGINT, false);
      _2 = createParameter("_2", SQLDataType.INTEGER, false);
   }
}
