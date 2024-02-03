package org.jooq;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.MappingException;

public interface Cursor<R extends Record> extends Iterable<R>, AutoCloseable {
   RecordType<R> recordType();

   Row fieldsRow();

   <T> Field<T> field(Field<T> var1);

   Field<?> field(String var1);

   Field<?> field(Name var1);

   Field<?> field(int var1);

   Field<?>[] fields();

   Field<?>[] fields(Field<?>... var1);

   Field<?>[] fields(String... var1);

   Field<?>[] fields(Name... var1);

   Field<?>[] fields(int... var1);

   boolean hasNext() throws DataAccessException;

   Result<R> fetch() throws DataAccessException;

   Result<R> fetch(int var1) throws DataAccessException;

   <H extends RecordHandler<? super R>> H fetchInto(H var1) throws DataAccessException;

   <E> List<E> fetch(RecordMapper<? super R, E> var1) throws DataAccessException;

   <E> List<E> fetchInto(Class<? extends E> var1) throws DataAccessException, MappingException;

   <Z extends Record> Result<Z> fetchInto(Table<Z> var1) throws DataAccessException, MappingException;

   R fetchOne() throws DataAccessException;

   <H extends RecordHandler<? super R>> H fetchOneInto(H var1) throws DataAccessException;

   <E> E fetchOneInto(Class<? extends E> var1) throws DataAccessException, MappingException;

   <E> E fetchOne(RecordMapper<? super R, E> var1) throws DataAccessException;

   <Z extends Record> Z fetchOneInto(Table<Z> var1) throws DataAccessException, MappingException;

   Optional<R> fetchOptional() throws DataAccessException;

   <E> Optional<E> fetchOptionalInto(Class<? extends E> var1) throws DataAccessException, MappingException;

   <E> Optional<E> fetchOptional(RecordMapper<? super R, E> var1) throws DataAccessException;

   <Z extends Record> Optional<Z> fetchOptionalInto(Table<Z> var1) throws DataAccessException, MappingException;

   Stream<R> stream() throws DataAccessException;

   void close() throws DataAccessException;

   boolean isClosed();

   ResultSet resultSet();
}
