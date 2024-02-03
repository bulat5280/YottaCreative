package org.jooq.util.postgres.pg_catalog;

import org.jooq.AggregateFunction;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.util.postgres.pg_catalog.routines.Count1;
import org.jooq.util.postgres.pg_catalog.routines.Count2;
import org.jooq.util.postgres.pg_catalog.routines.FormatType;
import org.jooq.util.postgres.pg_catalog.tables.PgCursor;

public class Routines {
   public static AggregateFunction<Long> count1(Object __1) {
      Count1 f = new Count1();
      f.set__1(__1);
      return f.asAggregateFunction();
   }

   public static AggregateFunction<Long> count1(Field<Object> __1) {
      Count1 f = new Count1();
      f.set__1(__1);
      return f.asAggregateFunction();
   }

   public static AggregateFunction<Long> count2() {
      Count2 f = new Count2();
      return f.asAggregateFunction();
   }

   public static String formatType(Configuration configuration, Long __1, Integer __2) {
      FormatType f = new FormatType();
      f.set__1(__1);
      f.set__2(__2);
      f.execute(configuration);
      return (String)f.getReturnValue();
   }

   public static Field<String> formatType(Long __1, Integer __2) {
      FormatType f = new FormatType();
      f.set__1(__1);
      f.set__2(__2);
      return f.asField();
   }

   public static Field<String> formatType(Field<Long> __1, Field<Integer> __2) {
      FormatType f = new FormatType();
      f.set__1(__1);
      f.set__2(__2);
      return f.asField();
   }

   public static PgCursor pgCursor() {
      return PgCursor.PG_CURSOR.call();
   }
}
