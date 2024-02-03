package org.jooq;

public interface TableField<R extends Record, T> extends Field<T> {
   Table<R> getTable();
}
