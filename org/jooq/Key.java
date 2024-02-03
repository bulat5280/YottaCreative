package org.jooq;

import java.io.Serializable;
import java.util.List;

public interface Key<R extends Record> extends Serializable {
   String getName();

   Table<R> getTable();

   List<TableField<R, ?>> getFields();

   TableField<R, ?>[] getFieldsArray();

   Constraint constraint();
}
