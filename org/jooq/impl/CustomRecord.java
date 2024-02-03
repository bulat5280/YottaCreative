package org.jooq.impl;

import org.jooq.Table;
import org.jooq.TableRecord;

public abstract class CustomRecord<R extends TableRecord<R>> extends TableRecordImpl<R> {
   private static final long serialVersionUID = 5287021930962460241L;

   protected CustomRecord(Table<R> table) {
      super(table);
   }
}
