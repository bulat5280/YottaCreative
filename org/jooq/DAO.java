package org.jooq;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.jooq.conf.Settings;
import org.jooq.exception.DataAccessException;

public interface DAO<R extends TableRecord<R>, P, T> {
   Configuration configuration();

   Settings settings();

   SQLDialect dialect();

   SQLDialect family();

   RecordMapper<R, P> mapper();

   void insert(P var1) throws DataAccessException;

   void insert(P... var1) throws DataAccessException;

   void insert(Collection<P> var1) throws DataAccessException;

   void update(P var1) throws DataAccessException;

   void update(P... var1) throws DataAccessException;

   void update(Collection<P> var1) throws DataAccessException;

   void delete(P var1) throws DataAccessException;

   void delete(P... var1) throws DataAccessException;

   void delete(Collection<P> var1) throws DataAccessException;

   void deleteById(T... var1) throws DataAccessException;

   void deleteById(Collection<T> var1) throws DataAccessException;

   boolean exists(P var1) throws DataAccessException;

   boolean existsById(T var1) throws DataAccessException;

   long count() throws DataAccessException;

   List<P> findAll() throws DataAccessException;

   P findById(T var1) throws DataAccessException;

   <Z> List<P> fetch(Field<Z> var1, Z... var2) throws DataAccessException;

   <Z> P fetchOne(Field<Z> var1, Z var2) throws DataAccessException;

   <Z> Optional<P> fetchOptional(Field<Z> var1, Z var2) throws DataAccessException;

   Table<R> getTable();

   Class<P> getType();
}
