package org.jooq;

import java.util.Collection;
import org.jooq.exception.DataAccessException;

public interface ForeignKey<R extends Record, O extends Record> extends Key<R> {
   UniqueKey<O> getKey();

   O fetchParent(R var1) throws DataAccessException;

   Result<O> fetchParents(R... var1) throws DataAccessException;

   Result<O> fetchParents(Collection<? extends R> var1) throws DataAccessException;

   Result<R> fetchChildren(O var1) throws DataAccessException;

   Result<R> fetchChildren(O... var1) throws DataAccessException;

   Result<R> fetchChildren(Collection<? extends O> var1) throws DataAccessException;
}
