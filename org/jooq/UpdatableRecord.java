package org.jooq;

import java.util.Collection;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataChangedException;
import org.jooq.exception.NoDataFoundException;
import org.jooq.exception.TooManyRowsException;

public interface UpdatableRecord<R extends UpdatableRecord<R>> extends TableRecord<R> {
   Record key();

   int store() throws DataAccessException, DataChangedException;

   int store(Field<?>... var1) throws DataAccessException, DataChangedException;

   int store(Collection<? extends Field<?>> var1) throws DataAccessException, DataChangedException;

   int insert() throws DataAccessException;

   int insert(Field<?>... var1) throws DataAccessException;

   int insert(Collection<? extends Field<?>> var1) throws DataAccessException;

   int update() throws DataAccessException, DataChangedException;

   int update(Field<?>... var1) throws DataAccessException, DataChangedException;

   int update(Collection<? extends Field<?>> var1) throws DataAccessException, DataChangedException;

   int delete() throws DataAccessException, DataChangedException;

   void refresh() throws DataAccessException;

   void refresh(Field<?>... var1) throws DataAccessException, NoDataFoundException;

   void refresh(Collection<? extends Field<?>> var1) throws DataAccessException, NoDataFoundException;

   R copy();

   <O extends TableRecord<O>> O fetchChild(ForeignKey<O, R> var1) throws TooManyRowsException, DataAccessException;

   <O extends TableRecord<O>> Result<O> fetchChildren(ForeignKey<O, R> var1) throws DataAccessException;
}
