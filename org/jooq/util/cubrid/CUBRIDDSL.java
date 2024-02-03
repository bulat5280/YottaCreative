package org.jooq.util.cubrid;

import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.impl.DSL;

public class CUBRIDDSL extends DSL {
   protected CUBRIDDSL() {
   }

   public static <T> Field<T> incr(Field<T> field) {
      return field("{incr}({0})", field.getDataType(), new QueryPart[]{field});
   }

   public static <T> Field<T> decr(Field<T> field) {
      return field("{decr}({0})", field.getDataType(), new QueryPart[]{field});
   }
}
