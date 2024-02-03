package org.jooq;

import java.io.Serializable;

public interface Identity<R extends Record, T> extends Serializable {
   Table<R> getTable();

   TableField<R, T> getField();
}
