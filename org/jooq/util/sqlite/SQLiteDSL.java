package org.jooq.util.sqlite;

import org.jooq.Field;
import org.jooq.impl.DSL;

public class SQLiteDSL extends DSL {
   protected SQLiteDSL() {
   }

   public static Field<Long> rowid() {
      return field("_rowid_", Long.class);
   }
}
