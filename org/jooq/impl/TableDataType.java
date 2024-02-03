package org.jooq.impl;

import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Table;

final class TableDataType<R extends Record> extends DefaultDataType<R> {
   private static final long serialVersionUID = 3262508265391094581L;

   TableDataType(Table<R> table) {
      super(SQLDialect.DEFAULT, table.getRecordType(), getQualifiedName(table));
   }

   private static String getQualifiedName(Table<?> table) {
      StringBuilder sb = new StringBuilder();
      if (table.getSchema() != null) {
         sb.append(table.getSchema().getName());
         sb.append(".");
      }

      sb.append(table.getName());
      return sb.toString();
   }
}
