package org.jooq.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;
import org.jooq.Attachable;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.Cursor;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Field;
import org.jooq.FutureResult;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.RecordHandler;
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.Results;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.conf.SettingsTools;
import org.jooq.tools.Convert;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.jdbc.MockResultSet;

abstract class AbstractResultQuery<R extends Record> extends AbstractQuery implements ResultQuery<R> {
   private static final long serialVersionUID = -5588344253566055707L;
   private static final JooqLogger log = JooqLogger.getLogger(AbstractResultQuery.class);
   private int maxRows;
   private int fetchSize;
   private int resultSetConcurrency;
   private int resultSetType;
   private int resultSetHoldability;
   private transient boolean lazy;
   private transient boolean many;
   private transient Cursor<R> cursor;
   private Result<R> result;
   private ResultsImpl results;
   private final Intern intern = new Intern();

   AbstractResultQuery(Configuration configuration) {
      super(configuration);
   }

   protected abstract Field<?>[] getFields(ResultSetMetaData var1) throws SQLException;

   public final ResultQuery<R> bind(String param, Object value) {
      return (ResultQuery)super.bind(param, value);
   }

   public final ResultQuery<R> bind(int index, Object value) {
      return (ResultQuery)super.bind(index, value);
   }

   public final ResultQuery<R> queryTimeout(int timeout) {
      return (ResultQuery)super.queryTimeout(timeout);
   }

   public final ResultQuery<R> keepStatement(boolean k) {
      return (ResultQuery)super.keepStatement(k);
   }

   public final ResultQuery<R> maxRows(int rows) {
      this.maxRows = rows;
      return this;
   }

   public final ResultQuery<R> fetchSize(int rows) {
      this.fetchSize = rows;
      return this;
   }

   public final ResultQuery<R> resultSetConcurrency(int concurrency) {
      this.resultSetConcurrency = concurrency;
      return this;
   }

   public final ResultQuery<R> resultSetType(int type) {
      this.resultSetType = type;
      return this;
   }

   public final ResultQuery<R> resultSetHoldability(int holdability) {
      this.resultSetHoldability = holdability;
      return this;
   }

   public final ResultQuery<R> intern(Field<?>... fields) {
      this.intern.internFields = fields;
      return this;
   }

   public final ResultQuery<R> intern(int... fieldIndexes) {
      this.intern.internIndexes = fieldIndexes;
      return this;
   }

   public final ResultQuery<R> intern(String... fieldNameStrings) {
      this.intern.internNameStrings = fieldNameStrings;
      return this;
   }

   public final ResultQuery<R> intern(Name... fieldNames) {
      this.intern.internNames = fieldNames;
      return this;
   }

   protected final void prepare(ExecuteContext ctx) throws SQLException {
      int f;
      int m;
      if (this.resultSetConcurrency == 0 && this.resultSetType == 0 && this.resultSetHoldability == 0) {
         if (this.isForUpdate() && Arrays.asList(SQLDialect.CUBRID).contains(ctx.configuration().dialect().family())) {
            ctx.data(Tools.DataKey.DATA_LOCK_ROWS_FOR_UPDATE, true);
            ctx.statement(ctx.connection().prepareStatement(ctx.sql(), 1005, 1008));
         } else {
            ctx.statement(ctx.connection().prepareStatement(ctx.sql()));
         }
      } else {
         f = this.resultSetType != 0 ? this.resultSetType : 1003;
         m = this.resultSetConcurrency != 0 ? this.resultSetConcurrency : 1007;
         if (this.resultSetHoldability == 0) {
            ctx.statement(ctx.connection().prepareStatement(ctx.sql(), f, m));
         } else {
            ctx.statement(ctx.connection().prepareStatement(ctx.sql(), f, m, this.resultSetHoldability));
         }
      }

      f = SettingsTools.getFetchSize(this.fetchSize, ctx.settings());
      if (f != 0) {
         if (log.isDebugEnabled()) {
            log.debug("Setting fetch size", (Object)f);
         }

         ctx.statement().setFetchSize(f);
      }

      m = SettingsTools.getMaxRows(this.maxRows, ctx.settings());
      if (m != 0) {
         ctx.statement().setMaxRows(m);
      }

   }

   protected final int execute(ExecuteContext ctx, ExecuteListener listener) throws SQLException {
      listener.executeStart(ctx);
      int f = SettingsTools.getFetchSize(this.fetchSize, ctx.settings());
      if (ctx.family() == SQLDialect.POSTGRES && f != 0 && ctx.connection().getAutoCommit()) {
         log.info("Fetch Size", (Object)("A fetch size of " + f + " was set on a auto-commit PostgreSQL connection, which is not recommended. See http://jdbc.postgresql.org/documentation/head/query.html#query-with-cursor"));
      }

      if (ctx.statement().execute()) {
         ctx.resultSet(ctx.statement().getResultSet());
      }

      listener.executeEnd(ctx);
      if (!this.many) {
         if (ctx.resultSet() == null) {
            ctx.resultSet(new MockResultSet(new ResultImpl(ctx.configuration(), new Field[0])));
         }

         Field<?>[] fields = this.getFields(ctx.resultSet().getMetaData());
         this.cursor = new CursorImpl(ctx, listener, fields, this.intern.internIndexes(fields), this.keepStatement(), this.keepResultSet(), this.getRecordType(), SettingsTools.getMaxRows(this.maxRows, ctx.settings()));
         if (!this.lazy) {
            this.result = this.cursor.fetch();
            this.cursor = null;
         }
      } else {
         this.results = new ResultsImpl(ctx.configuration());
         Tools.consumeResultSets(ctx, listener, this.results, this.intern);
      }

      return this.result != null ? this.result.size() : 0;
   }

   protected final boolean keepResultSet() {
      return this.lazy;
   }

   abstract boolean isForUpdate();

   public final Result<R> fetch() {
      this.execute();
      return this.result;
   }

   public final ResultSet fetchResultSet() {
      return this.fetchLazy().resultSet();
   }

   public final Iterator<R> iterator() {
      return this.fetch().iterator();
   }

   public final CompletionStage<Result<R>> fetchAsync() {
      return this.fetchAsync(Tools.configuration((Attachable)this).executorProvider().provide());
   }

   public final CompletionStage<Result<R>> fetchAsync(Executor executor) {
      return ExecutorProviderCompletionStage.of(CompletableFuture.supplyAsync(Tools.blocking(this::fetch), executor), () -> {
         return executor;
      });
   }

   public final Stream<R> fetchStream() {
      return this.fetchLazy().stream();
   }

   public final Stream<R> stream() {
      return this.fetchLazy().stream();
   }

   public final Cursor<R> fetchLazy() {
      return this.fetchLazy(this.fetchSize);
   }

   /** @deprecated */
   @Deprecated
   public final Cursor<R> fetchLazy(int size) {
      int previousFetchSize = this.fetchSize;
      this.lazy = true;
      this.fetchSize = size;

      try {
         this.execute();
      } finally {
         this.lazy = false;
         this.fetchSize = previousFetchSize;
      }

      return this.cursor;
   }

   public final Results fetchMany() {
      this.many = true;

      try {
         this.execute();
      } finally {
         this.many = false;
      }

      return this.results;
   }

   public final <T> List<T> fetch(Field<T> field) {
      return this.fetch().getValues(field);
   }

   public final <T> List<T> fetch(Field<?> field, Class<? extends T> type) {
      return this.fetch().getValues(field, type);
   }

   public final <T, U> List<U> fetch(Field<T> field, Converter<? super T, ? extends U> converter) {
      return this.fetch().getValues(field, converter);
   }

   public final List<?> fetch(int fieldIndex) {
      return this.fetch().getValues(fieldIndex);
   }

   public final <T> List<T> fetch(int fieldIndex, Class<? extends T> type) {
      return this.fetch().getValues(fieldIndex, type);
   }

   public final <U> List<U> fetch(int fieldIndex, Converter<?, ? extends U> converter) {
      return this.fetch().getValues(fieldIndex, converter);
   }

   public final List<?> fetch(String fieldName) {
      return this.fetch().getValues(fieldName);
   }

   public final <T> List<T> fetch(String fieldName, Class<? extends T> type) {
      return this.fetch().getValues(fieldName, type);
   }

   public final <U> List<U> fetch(String fieldName, Converter<?, ? extends U> converter) {
      return this.fetch().getValues(fieldName, converter);
   }

   public final List<?> fetch(Name fieldName) {
      return this.fetch().getValues(fieldName);
   }

   public final <T> List<T> fetch(Name fieldName, Class<? extends T> type) {
      return this.fetch().getValues(fieldName, type);
   }

   public final <U> List<U> fetch(Name fieldName, Converter<?, ? extends U> converter) {
      return this.fetch().getValues(fieldName, converter);
   }

   public final <T> T fetchOne(Field<T> field) {
      R record = this.fetchOne();
      return record == null ? null : record.get(field);
   }

   public final <T> T fetchOne(Field<?> field, Class<? extends T> type) {
      return Convert.convert(this.fetchOne(field), type);
   }

   public final <T, U> U fetchOne(Field<T> field, Converter<? super T, ? extends U> converter) {
      return Convert.convert(this.fetchOne(field), converter);
   }

   public final Object fetchOne(int fieldIndex) {
      R record = this.fetchOne();
      return record == null ? null : record.get(fieldIndex);
   }

   public final <T> T fetchOne(int fieldIndex, Class<? extends T> type) {
      return Convert.convert(this.fetchOne(fieldIndex), type);
   }

   public final <U> U fetchOne(int fieldIndex, Converter<?, ? extends U> converter) {
      return Convert.convert(this.fetchOne(fieldIndex), converter);
   }

   public final Object fetchOne(String fieldName) {
      R record = this.fetchOne();
      return record == null ? null : record.get(fieldName);
   }

   public final <T> T fetchOne(String fieldName, Class<? extends T> type) {
      return Convert.convert(this.fetchOne(fieldName), type);
   }

   public final <U> U fetchOne(String fieldName, Converter<?, ? extends U> converter) {
      return Convert.convert(this.fetchOne(fieldName), converter);
   }

   public final Object fetchOne(Name fieldName) {
      R record = this.fetchOne();
      return record == null ? null : record.get(fieldName);
   }

   public final <T> T fetchOne(Name fieldName, Class<? extends T> type) {
      return Convert.convert(this.fetchOne(fieldName), type);
   }

   public final <U> U fetchOne(Name fieldName, Converter<?, ? extends U> converter) {
      return Convert.convert(this.fetchOne(fieldName), converter);
   }

   public final R fetchOne() {
      return Tools.fetchOne(this.fetchLazy());
   }

   public final <E> E fetchOne(RecordMapper<? super R, E> mapper) {
      R record = this.fetchOne();
      return record == null ? null : mapper.map(record);
   }

   public final Map<String, Object> fetchOneMap() {
      R record = this.fetchOne();
      return record == null ? null : record.intoMap();
   }

   public final Object[] fetchOneArray() {
      R record = this.fetchOne();
      return record == null ? null : record.intoArray();
   }

   public final <E> E fetchOneInto(Class<? extends E> type) {
      R record = this.fetchOne();
      return record == null ? null : record.into(type);
   }

   public final <Z extends Record> Z fetchOneInto(Table<Z> table) {
      R record = this.fetchOne();
      return record == null ? null : record.into(table);
   }

   public final <T> Optional<T> fetchOptional(Field<T> field) {
      return Optional.ofNullable(this.fetchOne(field));
   }

   public final <T> Optional<T> fetchOptional(Field<?> field, Class<? extends T> type) {
      return Optional.ofNullable(this.fetchOne(field, type));
   }

   public final <T, U> Optional<U> fetchOptional(Field<T> field, Converter<? super T, ? extends U> converter) {
      return Optional.ofNullable(this.fetchOne(field, converter));
   }

   public final Optional<?> fetchOptional(int fieldIndex) {
      return Optional.ofNullable(this.fetchOne(fieldIndex));
   }

   public final <T> Optional<T> fetchOptional(int fieldIndex, Class<? extends T> type) {
      return Optional.ofNullable(this.fetchOne(fieldIndex, type));
   }

   public final <U> Optional<U> fetchOptional(int fieldIndex, Converter<?, ? extends U> converter) {
      return Optional.ofNullable(this.fetchOne(fieldIndex, converter));
   }

   public final Optional<?> fetchOptional(String fieldName) {
      return Optional.ofNullable(this.fetchOne(fieldName));
   }

   public final <T> Optional<T> fetchOptional(String fieldName, Class<? extends T> type) {
      return Optional.ofNullable(this.fetchOne(fieldName, type));
   }

   public final <U> Optional<U> fetchOptional(String fieldName, Converter<?, ? extends U> converter) {
      return Optional.ofNullable(this.fetchOne(fieldName, converter));
   }

   public final Optional<?> fetchOptional(Name fieldName) {
      return Optional.ofNullable(this.fetchOne(fieldName));
   }

   public final <T> Optional<T> fetchOptional(Name fieldName, Class<? extends T> type) {
      return Optional.ofNullable(this.fetchOne(fieldName, type));
   }

   public final <U> Optional<U> fetchOptional(Name fieldName, Converter<?, ? extends U> converter) {
      return Optional.ofNullable(this.fetchOne(fieldName, converter));
   }

   public final Optional<R> fetchOptional() {
      return Optional.ofNullable(this.fetchOne());
   }

   public final <E> Optional<E> fetchOptional(RecordMapper<? super R, E> mapper) {
      return Optional.ofNullable(this.fetchOne(mapper));
   }

   public final Optional<Map<String, Object>> fetchOptionalMap() {
      return Optional.ofNullable(this.fetchOneMap());
   }

   public final Optional<Object[]> fetchOptionalArray() {
      return Optional.ofNullable(this.fetchOneArray());
   }

   public final <E> Optional<E> fetchOptionalInto(Class<? extends E> type) {
      return Optional.ofNullable(this.fetchOneInto(type));
   }

   public final <Z extends Record> Optional<Z> fetchOptionalInto(Table<Z> table) {
      return Optional.ofNullable(this.fetchOneInto(table));
   }

   public final <T> T fetchAny(Field<T> field) {
      R record = this.fetchAny();
      return record == null ? null : record.get(field);
   }

   public final <T> T fetchAny(Field<?> field, Class<? extends T> type) {
      return Convert.convert(this.fetchAny(field), type);
   }

   public final <T, U> U fetchAny(Field<T> field, Converter<? super T, ? extends U> converter) {
      return Convert.convert(this.fetchAny(field), converter);
   }

   public final Object fetchAny(int fieldIndex) {
      R record = this.fetchAny();
      return record == null ? null : record.get(fieldIndex);
   }

   public final <T> T fetchAny(int fieldIndex, Class<? extends T> type) {
      return Convert.convert(this.fetchAny(fieldIndex), type);
   }

   public final <U> U fetchAny(int fieldIndex, Converter<?, ? extends U> converter) {
      return Convert.convert(this.fetchAny(fieldIndex), converter);
   }

   public final Object fetchAny(String fieldName) {
      R record = this.fetchAny();
      return record == null ? null : record.get(fieldName);
   }

   public final <T> T fetchAny(String fieldName, Class<? extends T> type) {
      return Convert.convert(this.fetchAny(fieldName), type);
   }

   public final <U> U fetchAny(String fieldName, Converter<?, ? extends U> converter) {
      return Convert.convert(this.fetchAny(fieldName), converter);
   }

   public final Object fetchAny(Name fieldName) {
      R record = this.fetchAny();
      return record == null ? null : record.get(fieldName);
   }

   public final <T> T fetchAny(Name fieldName, Class<? extends T> type) {
      return Convert.convert(this.fetchAny(fieldName), type);
   }

   public final <U> U fetchAny(Name fieldName, Converter<?, ? extends U> converter) {
      return Convert.convert(this.fetchAny(fieldName), converter);
   }

   public final R fetchAny() {
      Cursor c = this.fetchLazy();

      Record var2;
      try {
         var2 = c.fetchOne();
      } finally {
         c.close();
      }

      return var2;
   }

   public final <E> E fetchAny(RecordMapper<? super R, E> mapper) {
      R record = this.fetchAny();
      return record == null ? null : mapper.map(record);
   }

   public final Map<String, Object> fetchAnyMap() {
      R record = this.fetchAny();
      return record == null ? null : record.intoMap();
   }

   public final Object[] fetchAnyArray() {
      R record = this.fetchAny();
      return record == null ? null : record.intoArray();
   }

   public final <E> E fetchAnyInto(Class<? extends E> type) {
      R record = this.fetchAny();
      return record == null ? null : record.into(type);
   }

   public final <Z extends Record> Z fetchAnyInto(Table<Z> table) {
      R record = this.fetchAny();
      return record == null ? null : record.into(table);
   }

   public final <K> Map<K, R> fetchMap(Field<K> key) {
      return this.fetch().intoMap(key);
   }

   public final Map<?, R> fetchMap(int keyFieldIndex) {
      return this.fetch().intoMap(keyFieldIndex);
   }

   public final Map<?, R> fetchMap(String keyFieldName) {
      return this.fetch().intoMap(keyFieldName);
   }

   public final Map<?, R> fetchMap(Name keyFieldName) {
      return this.fetch().intoMap(keyFieldName);
   }

   public final <K, V> Map<K, V> fetchMap(Field<K> key, Field<V> value) {
      return this.fetch().intoMap(key, value);
   }

   public final Map<?, ?> fetchMap(int keyFieldIndex, int valueFieldIndex) {
      return this.fetch().intoMap(keyFieldIndex, valueFieldIndex);
   }

   public final Map<?, ?> fetchMap(String keyFieldName, String valueFieldName) {
      return this.fetch().intoMap(keyFieldName, valueFieldName);
   }

   public final Map<?, ?> fetchMap(Name keyFieldName, Name valueFieldName) {
      return this.fetch().intoMap(keyFieldName, valueFieldName);
   }

   public final <K, E> Map<K, E> fetchMap(Field<K> key, Class<? extends E> type) {
      return this.fetch().intoMap(key, type);
   }

   public final <E> Map<?, E> fetchMap(int keyFieldIndex, Class<? extends E> type) {
      return this.fetch().intoMap(keyFieldIndex, type);
   }

   public final <E> Map<?, E> fetchMap(String keyFieldName, Class<? extends E> type) {
      return this.fetch().intoMap(keyFieldName, type);
   }

   public final <E> Map<?, E> fetchMap(Name keyFieldName, Class<? extends E> type) {
      return this.fetch().intoMap(keyFieldName, type);
   }

   public final <K, E> Map<K, E> fetchMap(Field<K> key, RecordMapper<? super R, E> mapper) {
      return this.fetch().intoMap(key, mapper);
   }

   public final <E> Map<?, E> fetchMap(int keyFieldIndex, RecordMapper<? super R, E> mapper) {
      return this.fetch().intoMap(keyFieldIndex, mapper);
   }

   public final <E> Map<?, E> fetchMap(String keyFieldName, RecordMapper<? super R, E> mapper) {
      return this.fetch().intoMap(keyFieldName, mapper);
   }

   public final <E> Map<?, E> fetchMap(Name keyFieldName, RecordMapper<? super R, E> mapper) {
      return this.fetch().intoMap(keyFieldName, mapper);
   }

   public final Map<Record, R> fetchMap(Field<?>[] keys) {
      return this.fetch().intoMap(keys);
   }

   public final Map<Record, R> fetchMap(int[] keyFieldIndexes) {
      return this.fetch().intoMap(keyFieldIndexes);
   }

   public final Map<Record, R> fetchMap(String[] keyFieldNames) {
      return this.fetch().intoMap(keyFieldNames);
   }

   public final Map<Record, R> fetchMap(Name[] keyFieldNames) {
      return this.fetch().intoMap(keyFieldNames);
   }

   public final <E> Map<List<?>, E> fetchMap(Field<?>[] keys, Class<? extends E> type) {
      return this.fetch().intoMap(keys, type);
   }

   public final <E> Map<List<?>, E> fetchMap(int[] keyFieldIndexes, Class<? extends E> type) {
      return this.fetch().intoMap(keyFieldIndexes, type);
   }

   public final <E> Map<List<?>, E> fetchMap(String[] keyFieldNames, Class<? extends E> type) {
      return this.fetch().intoMap(keyFieldNames, type);
   }

   public final <E> Map<List<?>, E> fetchMap(Name[] keyFieldNames, Class<? extends E> type) {
      return this.fetch().intoMap(keyFieldNames, type);
   }

   public final <E> Map<List<?>, E> fetchMap(Field<?>[] keys, RecordMapper<? super R, E> mapper) {
      return this.fetch().intoMap(keys, mapper);
   }

   public final <E> Map<List<?>, E> fetchMap(int[] keyFieldIndexes, RecordMapper<? super R, E> mapper) {
      return this.fetch().intoMap(keyFieldIndexes, mapper);
   }

   public final <E> Map<List<?>, E> fetchMap(String[] keyFieldNames, RecordMapper<? super R, E> mapper) {
      return this.fetch().intoMap(keyFieldNames, mapper);
   }

   public final <E> Map<List<?>, E> fetchMap(Name[] keyFieldNames, RecordMapper<? super R, E> mapper) {
      return this.fetch().intoMap(keyFieldNames, mapper);
   }

   public final <K> Map<K, R> fetchMap(Class<? extends K> keyType) {
      return this.fetch().intoMap(keyType);
   }

   public final <K, V> Map<K, V> fetchMap(Class<? extends K> keyType, Class<? extends V> valueType) {
      return this.fetch().intoMap(keyType, valueType);
   }

   public final <K, V> Map<K, V> fetchMap(Class<? extends K> keyType, RecordMapper<? super R, V> valueMapper) {
      return this.fetch().intoMap(keyType, valueMapper);
   }

   public final <K> Map<K, R> fetchMap(RecordMapper<? super R, K> keyMapper) {
      return this.fetch().intoMap(keyMapper);
   }

   public final <K, V> Map<K, V> fetchMap(RecordMapper<? super R, K> keyMapper, Class<V> valueType) {
      return this.fetch().intoMap(keyMapper, valueType);
   }

   public final <K, V> Map<K, V> fetchMap(RecordMapper<? super R, K> keyMapper, RecordMapper<? super R, V> valueMapper) {
      return this.fetch().intoMap(keyMapper, valueMapper);
   }

   public final <S extends Record> Map<S, R> fetchMap(Table<S> table) {
      return this.fetch().intoMap(table);
   }

   public final <E, S extends Record> Map<S, E> fetchMap(Table<S> table, Class<? extends E> type) {
      return this.fetch().intoMap(table, type);
   }

   public final <E, S extends Record> Map<S, E> fetchMap(Table<S> table, RecordMapper<? super R, E> mapper) {
      return this.fetch().intoMap(table, mapper);
   }

   public final List<Map<String, Object>> fetchMaps() {
      return this.fetch().intoMaps();
   }

   public final <K> Map<K, Result<R>> fetchGroups(Field<K> key) {
      return this.fetch().intoGroups(key);
   }

   public final Map<?, Result<R>> fetchGroups(int keyFieldIndex) {
      return this.fetch().intoGroups(keyFieldIndex);
   }

   public final Map<?, Result<R>> fetchGroups(String keyFieldName) {
      return this.fetch().intoGroups(keyFieldName);
   }

   public final Map<?, Result<R>> fetchGroups(Name keyFieldName) {
      return this.fetch().intoGroups(keyFieldName);
   }

   public final <K, V> Map<K, List<V>> fetchGroups(Field<K> key, Field<V> value) {
      return this.fetch().intoGroups(key, value);
   }

   public final Map<?, List<?>> fetchGroups(int keyFieldIndex, int valueFieldIndex) {
      return this.fetch().intoGroups(keyFieldIndex, valueFieldIndex);
   }

   public final Map<?, List<?>> fetchGroups(String keyFieldName, String valueFieldName) {
      return this.fetch().intoGroups(keyFieldName, valueFieldName);
   }

   public final Map<?, List<?>> fetchGroups(Name keyFieldName, Name valueFieldName) {
      return this.fetch().intoGroups(keyFieldName, valueFieldName);
   }

   public final <K, E> Map<K, List<E>> fetchGroups(Field<K> key, Class<? extends E> type) {
      return this.fetch().intoGroups(key, type);
   }

   public final <E> Map<?, List<E>> fetchGroups(int keyFieldIndex, Class<? extends E> type) {
      return this.fetch().intoGroups(keyFieldIndex, type);
   }

   public final <E> Map<?, List<E>> fetchGroups(String keyFieldName, Class<? extends E> type) {
      return this.fetch().intoGroups(keyFieldName, type);
   }

   public final <E> Map<?, List<E>> fetchGroups(Name keyFieldName, Class<? extends E> type) {
      return this.fetch().intoGroups(keyFieldName, type);
   }

   public final <K, E> Map<K, List<E>> fetchGroups(Field<K> key, RecordMapper<? super R, E> mapper) {
      return this.fetch().intoGroups(key, mapper);
   }

   public final <E> Map<?, List<E>> fetchGroups(int keyFieldIndex, RecordMapper<? super R, E> mapper) {
      return this.fetch().intoGroups(keyFieldIndex, mapper);
   }

   public final <E> Map<?, List<E>> fetchGroups(String keyFieldName, RecordMapper<? super R, E> mapper) {
      return this.fetch().intoGroups(keyFieldName, mapper);
   }

   public final <E> Map<?, List<E>> fetchGroups(Name keyFieldName, RecordMapper<? super R, E> mapper) {
      return this.fetch().intoGroups(keyFieldName, mapper);
   }

   public final Map<Record, Result<R>> fetchGroups(Field<?>[] keys) {
      return this.fetch().intoGroups(keys);
   }

   public final Map<Record, Result<R>> fetchGroups(int[] keyFieldIndexes) {
      return this.fetch().intoGroups(keyFieldIndexes);
   }

   public final Map<Record, Result<R>> fetchGroups(String[] keyFieldNames) {
      return this.fetch().intoGroups(keyFieldNames);
   }

   public final Map<Record, Result<R>> fetchGroups(Name[] keyFieldNames) {
      return this.fetch().intoGroups(keyFieldNames);
   }

   public final <E> Map<Record, List<E>> fetchGroups(Field<?>[] keys, Class<? extends E> type) {
      return this.fetch().intoGroups(keys, type);
   }

   public final <E> Map<Record, List<E>> fetchGroups(int[] keyFieldIndexes, Class<? extends E> type) {
      return this.fetch().intoGroups(keyFieldIndexes, type);
   }

   public final <E> Map<Record, List<E>> fetchGroups(String[] keyFieldNames, Class<? extends E> type) {
      return this.fetch().intoGroups(keyFieldNames, type);
   }

   public final <E> Map<Record, List<E>> fetchGroups(Name[] keyFieldNames, Class<? extends E> type) {
      return this.fetch().intoGroups(keyFieldNames, type);
   }

   public final <E> Map<Record, List<E>> fetchGroups(int[] keyFieldIndexes, RecordMapper<? super R, E> mapper) {
      return this.fetch().intoGroups(keyFieldIndexes, mapper);
   }

   public final <E> Map<Record, List<E>> fetchGroups(String[] keyFieldNames, RecordMapper<? super R, E> mapper) {
      return this.fetch().intoGroups(keyFieldNames, mapper);
   }

   public final <E> Map<Record, List<E>> fetchGroups(Name[] keyFieldNames, RecordMapper<? super R, E> mapper) {
      return this.fetch().intoGroups(keyFieldNames, mapper);
   }

   public final <E> Map<Record, List<E>> fetchGroups(Field<?>[] keys, RecordMapper<? super R, E> mapper) {
      return this.fetch().intoGroups(keys, mapper);
   }

   public final <K> Map<K, Result<R>> fetchGroups(Class<? extends K> keyType) {
      return this.fetch().intoGroups(keyType);
   }

   public final <K, V> Map<K, List<V>> fetchGroups(Class<? extends K> keyType, Class<? extends V> valueType) {
      return this.fetch().intoGroups(keyType, valueType);
   }

   public final <K, V> Map<K, List<V>> fetchGroups(Class<? extends K> keyType, RecordMapper<? super R, V> valueMapper) {
      return this.fetch().intoGroups(keyType, valueMapper);
   }

   public final <K> Map<K, Result<R>> fetchGroups(RecordMapper<? super R, K> keyMapper) {
      return this.fetch().intoGroups(keyMapper);
   }

   public final <K, V> Map<K, List<V>> fetchGroups(RecordMapper<? super R, K> keyMapper, Class<V> valueType) {
      return this.fetch().intoGroups(keyMapper, valueType);
   }

   public final <K, V> Map<K, List<V>> fetchGroups(RecordMapper<? super R, K> keyMapper, RecordMapper<? super R, V> valueMapper) {
      return this.fetch().intoGroups(keyMapper, valueMapper);
   }

   public final <S extends Record> Map<S, Result<R>> fetchGroups(Table<S> table) {
      return this.fetch().intoGroups(table);
   }

   public final <E, S extends Record> Map<S, List<E>> fetchGroups(Table<S> table, Class<? extends E> type) {
      return this.fetch().intoGroups(table, type);
   }

   public final <E, S extends Record> Map<S, List<E>> fetchGroups(Table<S> table, RecordMapper<? super R, E> mapper) {
      return this.fetch().intoGroups(table, mapper);
   }

   public final Object[][] fetchArrays() {
      return this.fetch().intoArrays();
   }

   public final R[] fetchArray() {
      Result<R> r = this.fetch();
      return (Record[])r.toArray((Record[])((Record[])java.lang.reflect.Array.newInstance(this.getRecordType(), r.size())));
   }

   public final Object[] fetchArray(int fieldIndex) {
      return this.fetch().intoArray(fieldIndex);
   }

   public final <T> T[] fetchArray(int fieldIndex, Class<? extends T> type) {
      return this.fetch().intoArray(fieldIndex, type);
   }

   public final <U> U[] fetchArray(int fieldIndex, Converter<?, ? extends U> converter) {
      return this.fetch().intoArray(fieldIndex, converter);
   }

   public final Object[] fetchArray(String fieldName) {
      return this.fetch().intoArray(fieldName);
   }

   public final <T> T[] fetchArray(String fieldName, Class<? extends T> type) {
      return this.fetch().intoArray(fieldName, type);
   }

   public final <U> U[] fetchArray(String fieldName, Converter<?, ? extends U> converter) {
      return this.fetch().intoArray(fieldName, converter);
   }

   public final Object[] fetchArray(Name fieldName) {
      return this.fetch().intoArray(fieldName);
   }

   public final <T> T[] fetchArray(Name fieldName, Class<? extends T> type) {
      return this.fetch().intoArray(fieldName, type);
   }

   public final <U> U[] fetchArray(Name fieldName, Converter<?, ? extends U> converter) {
      return this.fetch().intoArray(fieldName, converter);
   }

   public final <T> T[] fetchArray(Field<T> field) {
      return this.fetch().intoArray(field);
   }

   public final <T> T[] fetchArray(Field<?> field, Class<? extends T> type) {
      return this.fetch().intoArray(field, type);
   }

   public final <T, U> U[] fetchArray(Field<T> field, Converter<? super T, ? extends U> converter) {
      return this.fetch().intoArray(field, converter);
   }

   public final Set<?> fetchSet(int fieldIndex) {
      return this.fetch().intoSet(fieldIndex);
   }

   public final <T> Set<T> fetchSet(int fieldIndex, Class<? extends T> type) {
      return this.fetch().intoSet(fieldIndex, type);
   }

   public final <U> Set<U> fetchSet(int fieldIndex, Converter<?, ? extends U> converter) {
      return this.fetch().intoSet(fieldIndex, converter);
   }

   public final Set<?> fetchSet(String fieldName) {
      return this.fetch().intoSet(fieldName);
   }

   public final <T> Set<T> fetchSet(String fieldName, Class<? extends T> type) {
      return this.fetch().intoSet(fieldName, type);
   }

   public final <U> Set<U> fetchSet(String fieldName, Converter<?, ? extends U> converter) {
      return this.fetch().intoSet(fieldName, converter);
   }

   public final Set<?> fetchSet(Name fieldName) {
      return this.fetch().intoSet(fieldName);
   }

   public final <T> Set<T> fetchSet(Name fieldName, Class<? extends T> type) {
      return this.fetch().intoSet(fieldName, type);
   }

   public final <U> Set<U> fetchSet(Name fieldName, Converter<?, ? extends U> converter) {
      return this.fetch().intoSet(fieldName, converter);
   }

   public final <T> Set<T> fetchSet(Field<T> field) {
      return this.fetch().intoSet(field);
   }

   public final <T> Set<T> fetchSet(Field<?> field, Class<? extends T> type) {
      return this.fetch().intoSet(field, type);
   }

   public final <T, U> Set<U> fetchSet(Field<T> field, Converter<? super T, ? extends U> converter) {
      return this.fetch().intoSet(field, converter);
   }

   public Class<? extends R> getRecordType() {
      return null;
   }

   public final <T> List<T> fetchInto(Class<? extends T> type) {
      return this.fetch().into(type);
   }

   public final <Z extends Record> Result<Z> fetchInto(Table<Z> table) {
      return this.fetch().into(table);
   }

   public final <H extends RecordHandler<? super R>> H fetchInto(H handler) {
      return this.fetch().into(handler);
   }

   public final <E> List<E> fetch(RecordMapper<? super R, E> mapper) {
      return this.fetch().map(mapper);
   }

   /** @deprecated */
   @Deprecated
   public final FutureResult<R> fetchLater() {
      ExecutorService executor = Executors.newSingleThreadExecutor();
      Future<Result<R>> future = executor.submit(new AbstractResultQuery.ResultQueryCallable());
      return new FutureResultImpl(future, executor);
   }

   /** @deprecated */
   @Deprecated
   public final FutureResult<R> fetchLater(ExecutorService executor) {
      Future<Result<R>> future = executor.submit(new AbstractResultQuery.ResultQueryCallable());
      return new FutureResultImpl(future);
   }

   public final Result<R> getResult() {
      return this.result;
   }

   private final class ResultQueryCallable implements Callable<Result<R>> {
      private ResultQueryCallable() {
      }

      public final Result<R> call() throws Exception {
         return AbstractResultQuery.this.fetch();
      }

      // $FF: synthetic method
      ResultQueryCallable(Object x1) {
         this();
      }
   }
}
