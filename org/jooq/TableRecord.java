package org.jooq;

import java.util.Collection;
import org.jooq.exception.DataAccessException;

public interface TableRecord<R extends TableRecord<R>> extends Record {
   Table<R> getTable();

   R original();

   int insert() throws DataAccessException;

   int insert(Field<?>... var1) throws DataAccessException;

   int insert(Collection<? extends Field<?>> var1) throws DataAccessException;

   <O extends UpdatableRecord<O>> O fetchParent(ForeignKey<R, O> var1) throws DataAccessException;

   <T> R with(Field<T> var1, T var2);

   <T, U> R with(Field<T> var1, U var2, Converter<? extends T, ? super U> var3);
}
