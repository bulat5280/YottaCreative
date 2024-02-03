package org.jooq.impl;

import java.util.Map;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.StoreQuery;
import org.jooq.Table;

abstract class AbstractStoreQuery<R extends Record> extends AbstractDMLQuery<R> implements StoreQuery<R> {
   private static final long serialVersionUID = 6864591335823160569L;

   AbstractStoreQuery(Configuration configuration, WithImpl with, Table<R> table) {
      super(configuration, with, table);
   }

   protected abstract Map<Field<?>, Field<?>> getValues();

   public final void setRecord(R record) {
      for(int i = 0; i < record.size(); ++i) {
         if (record.changed(i)) {
            this.addValue(record.field(i), record.get(i));
         }
      }

   }

   final <T> void addValue(R record, Field<T> field) {
      this.addValue(field, record.get(field));
   }

   public final <T> void addValue(Field<T> field, T value) {
      this.getValues().put(field, Tools.field(value, field));
   }

   public final <T> void addValue(Field<T> field, Field<T> value) {
      this.getValues().put(field, Tools.field(value, (Field)field));
   }
}
