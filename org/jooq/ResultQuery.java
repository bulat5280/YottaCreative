package org.jooq;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataTypeException;
import org.jooq.exception.InvalidResultException;
import org.jooq.exception.MappingException;
import org.jooq.exception.TooManyRowsException;

public interface ResultQuery<R extends Record> extends Query, Iterable<R> {
   Result<R> getResult();

   Result<R> fetch() throws DataAccessException;

   ResultSet fetchResultSet() throws DataAccessException;

   Iterator<R> iterator() throws DataAccessException;

   Stream<R> fetchStream() throws DataAccessException;

   Stream<R> stream() throws DataAccessException;

   Cursor<R> fetchLazy() throws DataAccessException;

   /** @deprecated */
   @Deprecated
   Cursor<R> fetchLazy(int var1) throws DataAccessException;

   Results fetchMany() throws DataAccessException;

   <T> List<T> fetch(Field<T> var1) throws DataAccessException;

   <T> List<T> fetch(Field<?> var1, Class<? extends T> var2) throws DataAccessException;

   <T, U> List<U> fetch(Field<T> var1, Converter<? super T, ? extends U> var2) throws DataAccessException;

   List<?> fetch(int var1) throws DataAccessException;

   <T> List<T> fetch(int var1, Class<? extends T> var2) throws DataAccessException;

   <U> List<U> fetch(int var1, Converter<?, ? extends U> var2) throws DataAccessException;

   List<?> fetch(String var1) throws DataAccessException;

   <T> List<T> fetch(String var1, Class<? extends T> var2) throws DataAccessException;

   <U> List<U> fetch(String var1, Converter<?, ? extends U> var2) throws DataAccessException;

   List<?> fetch(Name var1) throws DataAccessException;

   <T> List<T> fetch(Name var1, Class<? extends T> var2) throws DataAccessException;

   <U> List<U> fetch(Name var1, Converter<?, ? extends U> var2) throws DataAccessException;

   <T> T fetchOne(Field<T> var1) throws DataAccessException, TooManyRowsException;

   <T> T fetchOne(Field<?> var1, Class<? extends T> var2) throws DataAccessException, TooManyRowsException;

   <T, U> U fetchOne(Field<T> var1, Converter<? super T, ? extends U> var2) throws DataAccessException, TooManyRowsException;

   Object fetchOne(int var1) throws DataAccessException, TooManyRowsException;

   <T> T fetchOne(int var1, Class<? extends T> var2) throws DataAccessException, TooManyRowsException;

   <U> U fetchOne(int var1, Converter<?, ? extends U> var2) throws DataAccessException, TooManyRowsException;

   Object fetchOne(String var1) throws DataAccessException, TooManyRowsException;

   <T> T fetchOne(String var1, Class<? extends T> var2) throws DataAccessException, TooManyRowsException;

   <U> U fetchOne(String var1, Converter<?, ? extends U> var2) throws DataAccessException, TooManyRowsException;

   Object fetchOne(Name var1) throws DataAccessException, TooManyRowsException;

   <T> T fetchOne(Name var1, Class<? extends T> var2) throws DataAccessException, TooManyRowsException;

   <U> U fetchOne(Name var1, Converter<?, ? extends U> var2) throws DataAccessException, TooManyRowsException;

   R fetchOne() throws DataAccessException, TooManyRowsException;

   <E> E fetchOne(RecordMapper<? super R, E> var1) throws DataAccessException, TooManyRowsException;

   Map<String, Object> fetchOneMap() throws DataAccessException, TooManyRowsException;

   Object[] fetchOneArray() throws DataAccessException, TooManyRowsException;

   <E> E fetchOneInto(Class<? extends E> var1) throws DataAccessException, MappingException, TooManyRowsException;

   <Z extends Record> Z fetchOneInto(Table<Z> var1) throws DataAccessException, TooManyRowsException;

   <T> Optional<T> fetchOptional(Field<T> var1) throws DataAccessException, TooManyRowsException;

   <T> Optional<T> fetchOptional(Field<?> var1, Class<? extends T> var2) throws DataAccessException, TooManyRowsException;

   <T, U> Optional<U> fetchOptional(Field<T> var1, Converter<? super T, ? extends U> var2) throws DataAccessException, TooManyRowsException;

   Optional<?> fetchOptional(int var1) throws DataAccessException, TooManyRowsException;

   <T> Optional<T> fetchOptional(int var1, Class<? extends T> var2) throws DataAccessException, TooManyRowsException;

   <U> Optional<U> fetchOptional(int var1, Converter<?, ? extends U> var2) throws DataAccessException, TooManyRowsException;

   Optional<?> fetchOptional(String var1) throws DataAccessException, TooManyRowsException;

   <T> Optional<T> fetchOptional(String var1, Class<? extends T> var2) throws DataAccessException, TooManyRowsException;

   <U> Optional<U> fetchOptional(String var1, Converter<?, ? extends U> var2) throws DataAccessException, TooManyRowsException;

   Optional<?> fetchOptional(Name var1) throws DataAccessException, TooManyRowsException;

   <T> Optional<T> fetchOptional(Name var1, Class<? extends T> var2) throws DataAccessException, TooManyRowsException;

   <U> Optional<U> fetchOptional(Name var1, Converter<?, ? extends U> var2) throws DataAccessException, TooManyRowsException;

   Optional<R> fetchOptional() throws DataAccessException, TooManyRowsException;

   <E> Optional<E> fetchOptional(RecordMapper<? super R, E> var1) throws DataAccessException, TooManyRowsException;

   Optional<Map<String, Object>> fetchOptionalMap() throws DataAccessException, TooManyRowsException;

   Optional<Object[]> fetchOptionalArray() throws DataAccessException, TooManyRowsException;

   <E> Optional<E> fetchOptionalInto(Class<? extends E> var1) throws DataAccessException, MappingException, TooManyRowsException;

   <Z extends Record> Optional<Z> fetchOptionalInto(Table<Z> var1) throws DataAccessException, TooManyRowsException;

   <T> T fetchAny(Field<T> var1) throws DataAccessException;

   <T> T fetchAny(Field<?> var1, Class<? extends T> var2) throws DataAccessException;

   <T, U> U fetchAny(Field<T> var1, Converter<? super T, ? extends U> var2) throws DataAccessException;

   Object fetchAny(int var1) throws DataAccessException;

   <T> T fetchAny(int var1, Class<? extends T> var2) throws DataAccessException;

   <U> U fetchAny(int var1, Converter<?, ? extends U> var2) throws DataAccessException;

   Object fetchAny(String var1) throws DataAccessException;

   <T> T fetchAny(String var1, Class<? extends T> var2) throws DataAccessException;

   <U> U fetchAny(String var1, Converter<?, ? extends U> var2) throws DataAccessException;

   Object fetchAny(Name var1) throws DataAccessException;

   <T> T fetchAny(Name var1, Class<? extends T> var2) throws DataAccessException;

   <U> U fetchAny(Name var1, Converter<?, ? extends U> var2) throws DataAccessException;

   R fetchAny() throws DataAccessException;

   <E> E fetchAny(RecordMapper<? super R, E> var1) throws DataAccessException;

   Map<String, Object> fetchAnyMap() throws DataAccessException;

   Object[] fetchAnyArray() throws DataAccessException;

   <E> E fetchAnyInto(Class<? extends E> var1) throws DataAccessException, MappingException;

   <Z extends Record> Z fetchAnyInto(Table<Z> var1) throws DataAccessException;

   List<Map<String, Object>> fetchMaps() throws DataAccessException;

   <K> Map<K, R> fetchMap(Field<K> var1) throws DataAccessException;

   Map<?, R> fetchMap(int var1) throws DataAccessException;

   Map<?, R> fetchMap(String var1) throws DataAccessException;

   Map<?, R> fetchMap(Name var1) throws DataAccessException;

   <K, V> Map<K, V> fetchMap(Field<K> var1, Field<V> var2) throws DataAccessException;

   Map<?, ?> fetchMap(int var1, int var2) throws DataAccessException;

   Map<?, ?> fetchMap(String var1, String var2) throws DataAccessException;

   Map<?, ?> fetchMap(Name var1, Name var2) throws DataAccessException;

   Map<Record, R> fetchMap(Field<?>[] var1) throws DataAccessException;

   Map<Record, R> fetchMap(int[] var1) throws DataAccessException;

   Map<Record, R> fetchMap(String[] var1) throws DataAccessException;

   Map<Record, R> fetchMap(Name[] var1) throws DataAccessException;

   <E> Map<List<?>, E> fetchMap(Field<?>[] var1, Class<? extends E> var2) throws DataAccessException, MappingException;

   <E> Map<List<?>, E> fetchMap(int[] var1, Class<? extends E> var2) throws DataAccessException, MappingException;

   <E> Map<List<?>, E> fetchMap(String[] var1, Class<? extends E> var2) throws DataAccessException, MappingException;

   <E> Map<List<?>, E> fetchMap(Name[] var1, Class<? extends E> var2) throws DataAccessException, MappingException;

   <E> Map<List<?>, E> fetchMap(Field<?>[] var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

   <E> Map<List<?>, E> fetchMap(int[] var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

   <E> Map<List<?>, E> fetchMap(String[] var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

   <E> Map<List<?>, E> fetchMap(Name[] var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

   <K> Map<K, R> fetchMap(Class<? extends K> var1) throws DataAccessException, MappingException, InvalidResultException;

   <K, V> Map<K, V> fetchMap(Class<? extends K> var1, Class<? extends V> var2) throws DataAccessException, MappingException, InvalidResultException;

   <K, V> Map<K, V> fetchMap(Class<? extends K> var1, RecordMapper<? super R, V> var2) throws DataAccessException, InvalidResultException, MappingException;

   <K> Map<K, R> fetchMap(RecordMapper<? super R, K> var1) throws DataAccessException, InvalidResultException, MappingException;

   <K, V> Map<K, V> fetchMap(RecordMapper<? super R, K> var1, Class<V> var2) throws DataAccessException, InvalidResultException, MappingException;

   <K, V> Map<K, V> fetchMap(RecordMapper<? super R, K> var1, RecordMapper<? super R, V> var2) throws DataAccessException, InvalidResultException, MappingException;

   <S extends Record> Map<S, R> fetchMap(Table<S> var1) throws DataAccessException;

   <E, S extends Record> Map<S, E> fetchMap(Table<S> var1, Class<? extends E> var2) throws DataAccessException, MappingException;

   <E, S extends Record> Map<S, E> fetchMap(Table<S> var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

   <K, E> Map<K, E> fetchMap(Field<K> var1, Class<? extends E> var2) throws DataAccessException;

   <E> Map<?, E> fetchMap(int var1, Class<? extends E> var2) throws DataAccessException;

   <E> Map<?, E> fetchMap(String var1, Class<? extends E> var2) throws DataAccessException;

   <E> Map<?, E> fetchMap(Name var1, Class<? extends E> var2) throws DataAccessException;

   <K, E> Map<K, E> fetchMap(Field<K> var1, RecordMapper<? super R, E> var2) throws DataAccessException;

   <E> Map<?, E> fetchMap(int var1, RecordMapper<? super R, E> var2) throws DataAccessException;

   <E> Map<?, E> fetchMap(String var1, RecordMapper<? super R, E> var2) throws DataAccessException;

   <E> Map<?, E> fetchMap(Name var1, RecordMapper<? super R, E> var2) throws DataAccessException;

   <K> Map<K, Result<R>> fetchGroups(Field<K> var1) throws DataAccessException;

   Map<?, Result<R>> fetchGroups(int var1) throws DataAccessException;

   Map<?, Result<R>> fetchGroups(String var1) throws DataAccessException;

   Map<?, Result<R>> fetchGroups(Name var1) throws DataAccessException;

   <K, V> Map<K, List<V>> fetchGroups(Field<K> var1, Field<V> var2) throws DataAccessException;

   Map<?, List<?>> fetchGroups(int var1, int var2) throws DataAccessException;

   Map<?, List<?>> fetchGroups(String var1, String var2) throws DataAccessException;

   Map<?, List<?>> fetchGroups(Name var1, Name var2) throws DataAccessException;

   Map<Record, Result<R>> fetchGroups(Field<?>[] var1) throws DataAccessException;

   Map<Record, Result<R>> fetchGroups(int[] var1) throws DataAccessException;

   Map<Record, Result<R>> fetchGroups(String[] var1) throws DataAccessException;

   Map<Record, Result<R>> fetchGroups(Name[] var1) throws DataAccessException;

   <E> Map<Record, List<E>> fetchGroups(Field<?>[] var1, Class<? extends E> var2) throws MappingException;

   <E> Map<Record, List<E>> fetchGroups(int[] var1, Class<? extends E> var2) throws MappingException;

   <E> Map<Record, List<E>> fetchGroups(String[] var1, Class<? extends E> var2) throws MappingException;

   <E> Map<Record, List<E>> fetchGroups(Name[] var1, Class<? extends E> var2) throws MappingException;

   <E> Map<Record, List<E>> fetchGroups(Field<?>[] var1, RecordMapper<? super R, E> var2) throws MappingException;

   <E> Map<Record, List<E>> fetchGroups(int[] var1, RecordMapper<? super R, E> var2) throws MappingException;

   <E> Map<Record, List<E>> fetchGroups(String[] var1, RecordMapper<? super R, E> var2) throws MappingException;

   <E> Map<Record, List<E>> fetchGroups(Name[] var1, RecordMapper<? super R, E> var2) throws MappingException;

   <K> Map<K, Result<R>> fetchGroups(Class<? extends K> var1) throws MappingException;

   <K, V> Map<K, List<V>> fetchGroups(Class<? extends K> var1, Class<? extends V> var2) throws MappingException;

   <K, V> Map<K, List<V>> fetchGroups(Class<? extends K> var1, RecordMapper<? super R, V> var2) throws MappingException;

   <K> Map<K, Result<R>> fetchGroups(RecordMapper<? super R, K> var1) throws MappingException;

   <K, V> Map<K, List<V>> fetchGroups(RecordMapper<? super R, K> var1, Class<V> var2) throws MappingException;

   <K, V> Map<K, List<V>> fetchGroups(RecordMapper<? super R, K> var1, RecordMapper<? super R, V> var2) throws MappingException;

   <S extends Record> Map<S, Result<R>> fetchGroups(Table<S> var1) throws DataAccessException;

   <E, S extends Record> Map<S, List<E>> fetchGroups(Table<S> var1, Class<? extends E> var2) throws DataAccessException, MappingException;

   <E, S extends Record> Map<S, List<E>> fetchGroups(Table<S> var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

   <K, E> Map<K, List<E>> fetchGroups(Field<K> var1, Class<? extends E> var2) throws DataAccessException, MappingException;

   <E> Map<?, List<E>> fetchGroups(int var1, Class<? extends E> var2) throws DataAccessException, MappingException;

   <E> Map<?, List<E>> fetchGroups(String var1, Class<? extends E> var2) throws DataAccessException, MappingException;

   <E> Map<?, List<E>> fetchGroups(Name var1, Class<? extends E> var2) throws DataAccessException, MappingException;

   <K, E> Map<K, List<E>> fetchGroups(Field<K> var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

   <E> Map<?, List<E>> fetchGroups(int var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

   <E> Map<?, List<E>> fetchGroups(String var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

   <E> Map<?, List<E>> fetchGroups(Name var1, RecordMapper<? super R, E> var2) throws DataAccessException, MappingException;

   Object[][] fetchArrays() throws DataAccessException;

   R[] fetchArray() throws DataAccessException;

   Object[] fetchArray(int var1) throws DataAccessException;

   <T> T[] fetchArray(int var1, Class<? extends T> var2) throws DataAccessException;

   <U> U[] fetchArray(int var1, Converter<?, ? extends U> var2) throws DataAccessException;

   Object[] fetchArray(String var1) throws DataAccessException;

   <T> T[] fetchArray(String var1, Class<? extends T> var2) throws DataAccessException;

   <U> U[] fetchArray(String var1, Converter<?, ? extends U> var2) throws DataAccessException;

   Object[] fetchArray(Name var1) throws DataAccessException;

   <T> T[] fetchArray(Name var1, Class<? extends T> var2) throws DataAccessException;

   <U> U[] fetchArray(Name var1, Converter<?, ? extends U> var2) throws DataAccessException;

   <T> T[] fetchArray(Field<T> var1) throws DataAccessException;

   <T> T[] fetchArray(Field<?> var1, Class<? extends T> var2) throws DataAccessException;

   <T, U> U[] fetchArray(Field<T> var1, Converter<? super T, ? extends U> var2) throws DataAccessException;

   Set<?> fetchSet(int var1) throws DataAccessException;

   <T> Set<T> fetchSet(int var1, Class<? extends T> var2) throws DataAccessException;

   <U> Set<U> fetchSet(int var1, Converter<?, ? extends U> var2) throws DataAccessException;

   Set<?> fetchSet(String var1) throws DataAccessException;

   <T> Set<T> fetchSet(String var1, Class<? extends T> var2) throws DataAccessException;

   <U> Set<U> fetchSet(String var1, Converter<?, ? extends U> var2) throws DataAccessException;

   Set<?> fetchSet(Name var1) throws DataAccessException;

   <T> Set<T> fetchSet(Name var1, Class<? extends T> var2) throws DataAccessException;

   <U> Set<U> fetchSet(Name var1, Converter<?, ? extends U> var2) throws DataAccessException;

   <T> Set<T> fetchSet(Field<T> var1) throws DataAccessException;

   <T> Set<T> fetchSet(Field<?> var1, Class<? extends T> var2) throws DataAccessException;

   <T, U> Set<U> fetchSet(Field<T> var1, Converter<? super T, ? extends U> var2) throws DataAccessException;

   <E> List<E> fetchInto(Class<? extends E> var1) throws DataAccessException, MappingException;

   <Z extends Record> Result<Z> fetchInto(Table<Z> var1) throws DataAccessException;

   <H extends RecordHandler<? super R>> H fetchInto(H var1) throws DataAccessException;

   <E> List<E> fetch(RecordMapper<? super R, E> var1) throws DataAccessException;

   CompletionStage<Result<R>> fetchAsync();

   CompletionStage<Result<R>> fetchAsync(Executor var1);

   /** @deprecated */
   @Deprecated
   FutureResult<R> fetchLater() throws DataAccessException;

   /** @deprecated */
   @Deprecated
   FutureResult<R> fetchLater(ExecutorService var1) throws DataAccessException;

   Class<? extends R> getRecordType();

   ResultQuery<R> bind(String var1, Object var2) throws IllegalArgumentException, DataTypeException;

   ResultQuery<R> bind(int var1, Object var2) throws IllegalArgumentException, DataTypeException;

   ResultQuery<R> queryTimeout(int var1);

   ResultQuery<R> keepStatement(boolean var1);

   ResultQuery<R> maxRows(int var1);

   ResultQuery<R> fetchSize(int var1);

   ResultQuery<R> resultSetConcurrency(int var1);

   ResultQuery<R> resultSetType(int var1);

   ResultQuery<R> resultSetHoldability(int var1);

   ResultQuery<R> intern(Field<?>... var1);

   ResultQuery<R> intern(int... var1);

   ResultQuery<R> intern(String... var1);

   ResultQuery<R> intern(Name... var1);
}
