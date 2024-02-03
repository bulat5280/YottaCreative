package org.jooq.impl;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.jooq.AlterIndexStep;
import org.jooq.AlterSchemaStep;
import org.jooq.AlterSequenceStep;
import org.jooq.AlterTableStep;
import org.jooq.AlterViewStep;
import org.jooq.Attachable;
import org.jooq.Batch;
import org.jooq.BatchBindStep;
import org.jooq.BindContext;
import org.jooq.Catalog;
import org.jooq.CommonTableExpression;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.ConnectionCallable;
import org.jooq.ConnectionProvider;
import org.jooq.ConnectionRunnable;
import org.jooq.ContextTransactionalCallable;
import org.jooq.ContextTransactionalRunnable;
import org.jooq.CreateIndexStep;
import org.jooq.CreateSchemaFinalStep;
import org.jooq.CreateSequenceFinalStep;
import org.jooq.CreateTableAsStep;
import org.jooq.CreateViewAsStep;
import org.jooq.Cursor;
import org.jooq.DDLFlag;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.DeleteQuery;
import org.jooq.DeleteWhereStep;
import org.jooq.DropIndexOnStep;
import org.jooq.DropSchemaStep;
import org.jooq.DropSequenceFinalStep;
import org.jooq.DropTableStep;
import org.jooq.DropViewFinalStep;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.ExecuteListenerProvider;
import org.jooq.Field;
import org.jooq.InsertQuery;
import org.jooq.InsertSetStep;
import org.jooq.InsertValuesStep1;
import org.jooq.InsertValuesStep10;
import org.jooq.InsertValuesStep11;
import org.jooq.InsertValuesStep12;
import org.jooq.InsertValuesStep13;
import org.jooq.InsertValuesStep14;
import org.jooq.InsertValuesStep15;
import org.jooq.InsertValuesStep16;
import org.jooq.InsertValuesStep17;
import org.jooq.InsertValuesStep18;
import org.jooq.InsertValuesStep19;
import org.jooq.InsertValuesStep2;
import org.jooq.InsertValuesStep20;
import org.jooq.InsertValuesStep21;
import org.jooq.InsertValuesStep22;
import org.jooq.InsertValuesStep3;
import org.jooq.InsertValuesStep4;
import org.jooq.InsertValuesStep5;
import org.jooq.InsertValuesStep6;
import org.jooq.InsertValuesStep7;
import org.jooq.InsertValuesStep8;
import org.jooq.InsertValuesStep9;
import org.jooq.InsertValuesStepN;
import org.jooq.LoaderOptionsStep;
import org.jooq.MergeKeyStep1;
import org.jooq.MergeKeyStep10;
import org.jooq.MergeKeyStep11;
import org.jooq.MergeKeyStep12;
import org.jooq.MergeKeyStep13;
import org.jooq.MergeKeyStep14;
import org.jooq.MergeKeyStep15;
import org.jooq.MergeKeyStep16;
import org.jooq.MergeKeyStep17;
import org.jooq.MergeKeyStep18;
import org.jooq.MergeKeyStep19;
import org.jooq.MergeKeyStep2;
import org.jooq.MergeKeyStep20;
import org.jooq.MergeKeyStep21;
import org.jooq.MergeKeyStep22;
import org.jooq.MergeKeyStep3;
import org.jooq.MergeKeyStep4;
import org.jooq.MergeKeyStep5;
import org.jooq.MergeKeyStep6;
import org.jooq.MergeKeyStep7;
import org.jooq.MergeKeyStep8;
import org.jooq.MergeKeyStep9;
import org.jooq.MergeKeyStepN;
import org.jooq.MergeUsingStep;
import org.jooq.Meta;
import org.jooq.Name;
import org.jooq.Param;
import org.jooq.Parser;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Record11;
import org.jooq.Record12;
import org.jooq.Record13;
import org.jooq.Record14;
import org.jooq.Record15;
import org.jooq.Record16;
import org.jooq.Record17;
import org.jooq.Record18;
import org.jooq.Record19;
import org.jooq.Record2;
import org.jooq.Record20;
import org.jooq.Record21;
import org.jooq.Record22;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Record8;
import org.jooq.Record9;
import org.jooq.RecordListenerProvider;
import org.jooq.RecordMapperProvider;
import org.jooq.RenderContext;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.Results;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.SelectQuery;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TableRecord;
import org.jooq.TransactionProvider;
import org.jooq.TransactionalCallable;
import org.jooq.TransactionalRunnable;
import org.jooq.TruncateIdentityStep;
import org.jooq.UDT;
import org.jooq.UDTRecord;
import org.jooq.UpdatableRecord;
import org.jooq.UpdateQuery;
import org.jooq.UpdateSetFirstStep;
import org.jooq.VisitListenerProvider;
import org.jooq.WithAsStep;
import org.jooq.WithAsStep1;
import org.jooq.WithAsStep10;
import org.jooq.WithAsStep11;
import org.jooq.WithAsStep12;
import org.jooq.WithAsStep13;
import org.jooq.WithAsStep14;
import org.jooq.WithAsStep15;
import org.jooq.WithAsStep16;
import org.jooq.WithAsStep17;
import org.jooq.WithAsStep18;
import org.jooq.WithAsStep19;
import org.jooq.WithAsStep2;
import org.jooq.WithAsStep20;
import org.jooq.WithAsStep21;
import org.jooq.WithAsStep22;
import org.jooq.WithAsStep3;
import org.jooq.WithAsStep4;
import org.jooq.WithAsStep5;
import org.jooq.WithAsStep6;
import org.jooq.WithAsStep7;
import org.jooq.WithAsStep8;
import org.jooq.WithAsStep9;
import org.jooq.WithStep;
import org.jooq.conf.ParamType;
import org.jooq.conf.Settings;
import org.jooq.exception.ConfigurationException;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.InvalidResultException;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.tools.csv.CSVReader;
import org.jooq.tools.jdbc.JDBCUtils;
import org.jooq.tools.jdbc.MockCallable;
import org.jooq.tools.jdbc.MockConfiguration;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockRunnable;
import org.jooq.util.xml.jaxb.InformationSchema;

public class DefaultDSLContext extends AbstractScope implements DSLContext, Serializable {
   private static final long serialVersionUID = 2681360188806309513L;

   public DefaultDSLContext(SQLDialect dialect) {
      this((SQLDialect)dialect, (Settings)null);
   }

   public DefaultDSLContext(SQLDialect dialect, Settings settings) {
      this((Configuration)(new DefaultConfiguration(new NoConnectionProvider(), (TransactionProvider)null, (RecordMapperProvider)null, (RecordListenerProvider[])null, (ExecuteListenerProvider[])null, (VisitListenerProvider[])null, dialect, settings, (Map)null)));
   }

   public DefaultDSLContext(Connection connection, SQLDialect dialect) {
      this((Connection)connection, dialect, (Settings)null);
   }

   public DefaultDSLContext(Connection connection, SQLDialect dialect, Settings settings) {
      this((Configuration)(new DefaultConfiguration(new DefaultConnectionProvider(connection), (TransactionProvider)null, (RecordMapperProvider)null, (RecordListenerProvider[])null, (ExecuteListenerProvider[])null, (VisitListenerProvider[])null, dialect, settings, (Map)null)));
   }

   public DefaultDSLContext(DataSource datasource, SQLDialect dialect) {
      this((DataSource)datasource, dialect, (Settings)null);
   }

   public DefaultDSLContext(DataSource datasource, SQLDialect dialect, Settings settings) {
      this((Configuration)(new DefaultConfiguration(new DataSourceConnectionProvider(datasource), (TransactionProvider)null, (RecordMapperProvider)null, (RecordListenerProvider[])null, (ExecuteListenerProvider[])null, (VisitListenerProvider[])null, dialect, settings, (Map)null)));
   }

   public DefaultDSLContext(ConnectionProvider connectionProvider, SQLDialect dialect) {
      this((ConnectionProvider)connectionProvider, dialect, (Settings)null);
   }

   public DefaultDSLContext(ConnectionProvider connectionProvider, SQLDialect dialect, Settings settings) {
      this((Configuration)(new DefaultConfiguration(connectionProvider, (TransactionProvider)null, (RecordMapperProvider)null, (RecordListenerProvider[])null, (ExecuteListenerProvider[])null, (VisitListenerProvider[])null, dialect, settings, (Map)null)));
   }

   public DefaultDSLContext(Configuration configuration) {
      super(configuration, configuration == null ? null : configuration.data());
   }

   public void close() {
      ConnectionProvider cp = this.configuration().connectionProvider();
      if (cp instanceof DefaultConnectionProvider) {
         DefaultConnectionProvider dcp = (DefaultConnectionProvider)cp;
         if (dcp.finalize) {
            JDBCUtils.safeClose(dcp.connection);
            dcp.connection = null;
         }
      }

   }

   public Schema map(Schema schema) {
      return Tools.getMappedSchema(this.configuration(), schema);
   }

   public <R extends Record> Table<R> map(Table<R> table) {
      return Tools.getMappedTable(this.configuration(), table);
   }

   /** @deprecated */
   @Deprecated
   public Parser parser() {
      return new ParserImpl(this.configuration());
   }

   public Meta meta() {
      return new MetaImpl(this.configuration());
   }

   public Meta meta(InformationSchema schema) {
      return new InformationSchemaMetaImpl(this.configuration(), schema);
   }

   public InformationSchema informationSchema(Catalog catalog) {
      return InformationSchemaExport.exportSchemas(this.configuration(), catalog.getSchemas());
   }

   public InformationSchema informationSchema(Catalog... catalogs) {
      List<Schema> schemas = new ArrayList();
      Catalog[] var3 = catalogs;
      int var4 = catalogs.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Catalog catalog = var3[var5];
         schemas.addAll(catalog.getSchemas());
      }

      return InformationSchemaExport.exportSchemas(this.configuration(), schemas);
   }

   public InformationSchema informationSchema(Schema schema) {
      return InformationSchemaExport.exportSchemas(this.configuration(), Arrays.asList(schema));
   }

   public InformationSchema informationSchema(Schema... schemas) {
      return InformationSchemaExport.exportSchemas(this.configuration(), Arrays.asList(schemas));
   }

   public InformationSchema informationSchema(Table<?> table) {
      return InformationSchemaExport.exportTables(this.configuration(), Arrays.asList(table));
   }

   public InformationSchema informationSchema(Table<?>... tables) {
      return InformationSchemaExport.exportTables(this.configuration(), Arrays.asList(tables));
   }

   public <T> T transactionResult(final ContextTransactionalCallable<T> transactional) {
      TransactionProvider tp = this.configuration().transactionProvider();
      if (!(tp instanceof ThreadLocalTransactionProvider)) {
         throw new ConfigurationException("Cannot use ThreadLocalTransactionalCallable with TransactionProvider of type " + tp.getClass());
      } else {
         return transactionResult0(new TransactionalCallable<T>() {
            public T run(Configuration c) throws Exception {
               return transactional.run();
            }
         }, ((ThreadLocalTransactionProvider)tp).configuration(this.configuration()), true);
      }
   }

   public <T> T transactionResult(TransactionalCallable<T> transactional) {
      return transactionResult0(transactional, this.configuration(), false);
   }

   private static <T> T transactionResult0(TransactionalCallable<T> transactional, Configuration configuration, boolean threadLocal) {
      return Tools.blocking(() -> {
         T result = null;
         DefaultTransactionContext ctx = new DefaultTransactionContext(configuration.derive());
         TransactionProvider provider = ctx.configuration().transactionProvider();
         TransactionListeners listeners = new TransactionListeners(ctx.configuration());

         try {
            try {
               listeners.beginStart(ctx);
               provider.begin(ctx);
            } finally {
               listeners.beginEnd(ctx);
            }

            result = transactional.run(ctx.configuration());

            try {
               listeners.commitStart(ctx);
               provider.commit(ctx);
            } finally {
               listeners.commitEnd(ctx);
            }

            return result;
         } catch (Exception var19) {
            ctx.cause(var19);
            listeners.rollbackStart(ctx);

            try {
               provider.rollback(ctx);
            } catch (Exception var16) {
               var19.addSuppressed(var16);
            }

            listeners.rollbackEnd(ctx);
            if (var19 instanceof RuntimeException) {
               throw (RuntimeException)var19;
            } else {
               throw new DataAccessException("Rollback caused", var19);
            }
         }
      }, threadLocal).get();
   }

   public void transaction(final ContextTransactionalRunnable transactional) {
      this.transactionResult(new ContextTransactionalCallable<Void>() {
         public Void run() throws Exception {
            transactional.run();
            return null;
         }
      });
   }

   public void transaction(final TransactionalRunnable transactional) {
      this.transactionResult(new TransactionalCallable<Void>() {
         public Void run(Configuration c) throws Exception {
            transactional.run(c);
            return null;
         }
      });
   }

   public CompletionStage<Void> transactionAsync(TransactionalRunnable transactional) {
      return this.transactionAsync(Tools.configuration(this.configuration()).executorProvider().provide(), transactional);
   }

   public CompletionStage<Void> transactionAsync(Executor executor, TransactionalRunnable transactional) {
      if (this.configuration().transactionProvider() instanceof ThreadLocalTransactionProvider) {
         throw new ConfigurationException("Cannot use TransactionalCallable with ThreadLocalTransactionProvider");
      } else {
         return ExecutorProviderCompletionStage.of(CompletableFuture.supplyAsync(() -> {
            this.transaction(transactional);
            return null;
         }, executor), () -> {
            return executor;
         });
      }
   }

   public <T> CompletionStage<T> transactionResultAsync(TransactionalCallable<T> transactional) {
      return this.transactionResultAsync(Tools.configuration(this.configuration()).executorProvider().provide(), transactional);
   }

   public <T> CompletionStage<T> transactionResultAsync(Executor executor, TransactionalCallable<T> transactional) {
      if (this.configuration().transactionProvider() instanceof ThreadLocalTransactionProvider) {
         throw new ConfigurationException("Cannot use TransactionalCallable with ThreadLocalTransactionProvider");
      } else {
         return ExecutorProviderCompletionStage.of(CompletableFuture.supplyAsync(() -> {
            return this.transactionResult(transactional);
         }, executor), () -> {
            return executor;
         });
      }
   }

   public <T> T connectionResult(ConnectionCallable<T> callable) {
      Connection connection = this.configuration().connectionProvider().acquire();

      Object var3;
      try {
         var3 = callable.run(connection);
      } catch (Exception var7) {
         throw new DataAccessException("Error while running ConnectionCallable", var7);
      } finally {
         this.configuration().connectionProvider().release(connection);
      }

      return var3;
   }

   public void connection(final ConnectionRunnable runnable) {
      this.connectionResult(new ConnectionCallable<Void>() {
         public Void run(Connection connection) throws Exception {
            runnable.run(connection);
            return null;
         }
      });
   }

   public <T> T mockResult(MockDataProvider provider, MockCallable<T> mockable) {
      try {
         return mockable.run(new MockConfiguration(this.configuration, provider));
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new DataAccessException("Mock failed", var5);
      }
   }

   public void mock(MockDataProvider provider, final MockRunnable mockable) {
      this.mockResult(provider, new MockCallable<Void>() {
         public Void run(Configuration c) throws Exception {
            mockable.run(c);
            return null;
         }
      });
   }

   public RenderContext renderContext() {
      return new DefaultRenderContext(this.configuration());
   }

   public String render(QueryPart part) {
      return ((RenderContext)this.renderContext().visit(part)).render();
   }

   public String renderNamedParams(QueryPart part) {
      return ((RenderContext)this.renderContext().paramType(ParamType.NAMED).visit(part)).render();
   }

   public String renderNamedOrInlinedParams(QueryPart part) {
      return ((RenderContext)this.renderContext().paramType(ParamType.NAMED_OR_INLINED).visit(part)).render();
   }

   public String renderInlined(QueryPart part) {
      return ((RenderContext)this.renderContext().paramType(ParamType.INLINED).visit(part)).render();
   }

   public List<Object> extractBindValues(QueryPart part) {
      List<Object> result = new ArrayList();
      ParamCollector collector = new ParamCollector(this.configuration(), false);
      collector.visit(part);
      Iterator var4 = collector.resultList.iterator();

      while(var4.hasNext()) {
         Entry<String, Param<?>> entry = (Entry)var4.next();
         result.add(((Param)entry.getValue()).getValue());
      }

      return Collections.unmodifiableList(result);
   }

   public Map<String, Param<?>> extractParams(QueryPart part) {
      return this.extractParams0(part, true);
   }

   final Map<String, Param<?>> extractParams0(QueryPart part, boolean includeInlinedParams) {
      ParamCollector collector = new ParamCollector(this.configuration(), includeInlinedParams);
      collector.visit(part);
      return Collections.unmodifiableMap(collector.resultFlat);
   }

   public Param<?> extractParam(QueryPart part, String name) {
      return (Param)this.extractParams(part).get(name);
   }

   public BindContext bindContext(PreparedStatement stmt) {
      return new DefaultBindContext(this.configuration(), stmt);
   }

   /** @deprecated */
   @Deprecated
   public int bind(QueryPart part, PreparedStatement stmt) {
      return ((BindContext)this.bindContext(stmt).visit(part)).peekIndex();
   }

   public void attach(Attachable... attachables) {
      this.attach((Collection)Arrays.asList(attachables));
   }

   public void attach(Collection<? extends Attachable> attachables) {
      Iterator var2 = attachables.iterator();

      while(var2.hasNext()) {
         Attachable attachable = (Attachable)var2.next();
         attachable.attach(this.configuration());
      }

   }

   public <R extends Record> LoaderOptionsStep<R> loadInto(Table<R> table) {
      return new LoaderImpl(this.configuration(), table);
   }

   public Query query(SQL sql) {
      return new SQLQuery(this.configuration(), sql);
   }

   public Query query(String sql) {
      return this.query(sql);
   }

   public Query query(String sql, Object... bindings) {
      return this.query(DSL.sql(sql, bindings));
   }

   public Query query(String sql, QueryPart... parts) {
      return this.query(sql, (Object[])parts);
   }

   public Result<Record> fetch(SQL sql) {
      return this.resultQuery(sql).fetch();
   }

   public Result<Record> fetch(String sql) {
      return this.resultQuery(sql).fetch();
   }

   public Result<Record> fetch(String sql, Object... bindings) {
      return this.resultQuery(sql, bindings).fetch();
   }

   public Result<Record> fetch(String sql, QueryPart... parts) {
      return this.resultQuery(sql, parts).fetch();
   }

   public Cursor<Record> fetchLazy(SQL sql) {
      return this.resultQuery(sql).fetchLazy();
   }

   public Cursor<Record> fetchLazy(String sql) {
      return this.resultQuery(sql).fetchLazy();
   }

   public Cursor<Record> fetchLazy(String sql, Object... bindings) {
      return this.resultQuery(sql, bindings).fetchLazy();
   }

   public Cursor<Record> fetchLazy(String sql, QueryPart... parts) {
      return this.resultQuery(sql, parts).fetchLazy();
   }

   public CompletionStage<Result<Record>> fetchAsync(SQL sql) {
      return this.resultQuery(sql).fetchAsync();
   }

   public CompletionStage<Result<Record>> fetchAsync(String sql) {
      return this.resultQuery(sql).fetchAsync();
   }

   public CompletionStage<Result<Record>> fetchAsync(String sql, Object... bindings) {
      return this.resultQuery(sql, bindings).fetchAsync();
   }

   public CompletionStage<Result<Record>> fetchAsync(String sql, QueryPart... parts) {
      return this.resultQuery(sql, parts).fetchAsync();
   }

   public CompletionStage<Result<Record>> fetchAsync(Executor executor, SQL sql) {
      return this.resultQuery(sql).fetchAsync(executor);
   }

   public CompletionStage<Result<Record>> fetchAsync(Executor executor, String sql) {
      return this.resultQuery(sql).fetchAsync(executor);
   }

   public CompletionStage<Result<Record>> fetchAsync(Executor executor, String sql, Object... bindings) {
      return this.resultQuery(sql, bindings).fetchAsync(executor);
   }

   public CompletionStage<Result<Record>> fetchAsync(Executor executor, String sql, QueryPart... parts) {
      return this.resultQuery(sql, parts).fetchAsync(executor);
   }

   public Stream<Record> fetchStream(SQL sql) {
      return this.resultQuery(sql).stream();
   }

   public Stream<Record> fetchStream(String sql) {
      return this.resultQuery(sql).stream();
   }

   public Stream<Record> fetchStream(String sql, Object... bindings) {
      return this.resultQuery(sql, bindings).stream();
   }

   public Stream<Record> fetchStream(String sql, QueryPart... parts) {
      return this.resultQuery(sql, parts).stream();
   }

   public Results fetchMany(SQL sql) {
      return this.resultQuery(sql).fetchMany();
   }

   public Results fetchMany(String sql) {
      return this.resultQuery(sql).fetchMany();
   }

   public Results fetchMany(String sql, Object... bindings) {
      return this.resultQuery(sql, bindings).fetchMany();
   }

   public Results fetchMany(String sql, QueryPart... parts) {
      return this.resultQuery(sql, parts).fetchMany();
   }

   public Record fetchOne(SQL sql) {
      return this.resultQuery(sql).fetchOne();
   }

   public Record fetchOne(String sql) {
      return this.resultQuery(sql).fetchOne();
   }

   public Record fetchOne(String sql, Object... bindings) {
      return this.resultQuery(sql, bindings).fetchOne();
   }

   public Record fetchOne(String sql, QueryPart... parts) {
      return this.resultQuery(sql, parts).fetchOne();
   }

   public Optional<Record> fetchOptional(SQL sql) {
      return Optional.ofNullable(this.fetchOne(sql));
   }

   public Optional<Record> fetchOptional(String sql) {
      return Optional.ofNullable(this.fetchOne(sql));
   }

   public Optional<Record> fetchOptional(String sql, Object... bindings) {
      return Optional.ofNullable(this.fetchOne(sql, bindings));
   }

   public Optional<Record> fetchOptional(String sql, QueryPart... parts) {
      return Optional.ofNullable(this.fetchOne(sql, parts));
   }

   public Object fetchValue(SQL sql) {
      return this.fetchValue(this.resultQuery(sql));
   }

   public Object fetchValue(String sql) {
      return this.fetchValue(this.resultQuery(sql));
   }

   public Object fetchValue(String sql, Object... bindings) {
      return this.fetchValue(this.resultQuery(sql, bindings));
   }

   public Object fetchValue(String sql, QueryPart... parts) {
      return this.fetchValue(this.resultQuery(sql, parts));
   }

   public Optional<?> fetchOptionalValue(SQL sql) {
      return Optional.ofNullable(this.fetchValue(sql));
   }

   public Optional<?> fetchOptionalValue(String sql) {
      return Optional.ofNullable(this.fetchValue(sql));
   }

   public Optional<?> fetchOptionalValue(String sql, Object... bindings) {
      return Optional.ofNullable(this.fetchValue(sql, bindings));
   }

   public Optional<?> fetchOptionalValue(String sql, QueryPart... parts) {
      return Optional.ofNullable(this.fetchValue(sql, parts));
   }

   public List<?> fetchValues(SQL sql) {
      return this.fetchValues(this.resultQuery(sql));
   }

   public List<?> fetchValues(String sql) {
      return this.fetchValues(this.resultQuery(sql));
   }

   public List<?> fetchValues(String sql, Object... bindings) {
      return this.fetchValues(this.resultQuery(sql, bindings));
   }

   public List<?> fetchValues(String sql, QueryPart... parts) {
      return this.fetchValues(this.resultQuery(sql, parts));
   }

   public int execute(SQL sql) {
      return this.query(sql).execute();
   }

   public int execute(String sql) {
      return this.query(sql).execute();
   }

   public int execute(String sql, Object... bindings) {
      return this.query(sql, bindings).execute();
   }

   public int execute(String sql, QueryPart... parts) {
      return this.query(sql, (Object[])parts).execute();
   }

   public ResultQuery<Record> resultQuery(SQL sql) {
      return new SQLResultQuery(this.configuration(), sql);
   }

   public ResultQuery<Record> resultQuery(String sql) {
      return this.resultQuery(sql);
   }

   public ResultQuery<Record> resultQuery(String sql, Object... bindings) {
      return this.resultQuery(DSL.sql(sql, bindings));
   }

   public ResultQuery<Record> resultQuery(String sql, QueryPart... parts) {
      return this.resultQuery(sql, (Object[])parts);
   }

   public Result<Record> fetch(ResultSet rs) {
      return this.fetchLazy(rs).fetch();
   }

   public Result<Record> fetch(ResultSet rs, Field<?>... fields) {
      return this.fetchLazy(rs, fields).fetch();
   }

   public Result<Record> fetch(ResultSet rs, DataType<?>... types) {
      return this.fetchLazy(rs, types).fetch();
   }

   public Result<Record> fetch(ResultSet rs, Class<?>... types) {
      return this.fetchLazy(rs, types).fetch();
   }

   public Record fetchOne(ResultSet rs) {
      return Tools.fetchOne(this.fetchLazy(rs));
   }

   public Record fetchOne(ResultSet rs, Field<?>... fields) {
      return Tools.fetchOne(this.fetchLazy(rs, fields));
   }

   public Record fetchOne(ResultSet rs, DataType<?>... types) {
      return Tools.fetchOne(this.fetchLazy(rs, types));
   }

   public Record fetchOne(ResultSet rs, Class<?>... types) {
      return Tools.fetchOne(this.fetchLazy(rs, types));
   }

   public Optional<Record> fetchOptional(ResultSet rs) {
      return Optional.ofNullable(this.fetchOne(rs));
   }

   public Optional<Record> fetchOptional(ResultSet rs, Field<?>... fields) {
      return Optional.ofNullable(this.fetchOne(rs, fields));
   }

   public Optional<Record> fetchOptional(ResultSet rs, DataType<?>... types) {
      return Optional.ofNullable(this.fetchOne(rs, types));
   }

   public Optional<Record> fetchOptional(ResultSet rs, Class<?>... types) {
      return Optional.ofNullable(this.fetchOne(rs, types));
   }

   public Object fetchValue(ResultSet rs) {
      return this.value1((Record1)this.fetchOne(rs));
   }

   public <T> T fetchValue(ResultSet rs, Field<T> field) {
      return this.value1((Record1)this.fetchOne(rs, field));
   }

   public <T> T fetchValue(ResultSet rs, DataType<T> type) {
      return this.value1((Record1)this.fetchOne(rs, type));
   }

   public <T> T fetchValue(ResultSet rs, Class<T> type) {
      return this.value1((Record1)this.fetchOne(rs, type));
   }

   public Optional<?> fetchOptionalValue(ResultSet rs) {
      return Optional.ofNullable(this.fetchValue(rs));
   }

   public <T> Optional<T> fetchOptionalValue(ResultSet rs, Field<T> field) {
      return Optional.ofNullable(this.fetchValue(rs, field));
   }

   public <T> Optional<T> fetchOptionalValue(ResultSet rs, DataType<T> type) {
      return Optional.ofNullable(this.fetchValue(rs, type));
   }

   public <T> Optional<T> fetchOptionalValue(ResultSet rs, Class<T> type) {
      return Optional.ofNullable(this.fetchValue(rs, type));
   }

   public List<?> fetchValues(ResultSet rs) {
      return this.fetch(rs).getValues(0);
   }

   public <T> List<T> fetchValues(ResultSet rs, Field<T> field) {
      return this.fetch(rs).getValues(field);
   }

   public <T> List<T> fetchValues(ResultSet rs, DataType<T> type) {
      return this.fetch(rs).getValues(0, (Class)type.getType());
   }

   public <T> List<T> fetchValues(ResultSet rs, Class<T> type) {
      return this.fetch(rs).getValues(0, (Class)type);
   }

   public Cursor<Record> fetchLazy(ResultSet rs) {
      try {
         return this.fetchLazy(rs, (new MetaDataFieldProvider(this.configuration(), rs.getMetaData())).getFields());
      } catch (SQLException var3) {
         throw new DataAccessException("Error while accessing ResultSet meta data", var3);
      }
   }

   public Cursor<Record> fetchLazy(ResultSet rs, Field<?>... fields) {
      ExecuteContext ctx = new DefaultExecuteContext(this.configuration());
      ExecuteListener listener = new ExecuteListeners(ctx);
      ctx.resultSet(rs);
      return new CursorImpl(ctx, listener, fields, (int[])null, false, true);
   }

   public Cursor<Record> fetchLazy(ResultSet rs, DataType<?>... types) {
      try {
         Field<?>[] fields = new Field[types.length];
         ResultSetMetaData meta = rs.getMetaData();
         int columns = meta.getColumnCount();

         for(int i = 0; i < types.length && i < columns; ++i) {
            fields[i] = DSL.field(meta.getColumnLabel(i + 1), types[i]);
         }

         return this.fetchLazy(rs, fields);
      } catch (SQLException var7) {
         throw new DataAccessException("Error while accessing ResultSet meta data", var7);
      }
   }

   public Cursor<Record> fetchLazy(ResultSet rs, Class<?>... types) {
      return this.fetchLazy(rs, Tools.dataTypes(types));
   }

   public CompletionStage<Result<Record>> fetchAsync(ResultSet rs) {
      return this.fetchAsync(Tools.configuration(this.configuration()).executorProvider().provide(), rs);
   }

   public CompletionStage<Result<Record>> fetchAsync(ResultSet rs, Field<?>... fields) {
      return this.fetchAsync(Tools.configuration(this.configuration()).executorProvider().provide(), rs, fields);
   }

   public CompletionStage<Result<Record>> fetchAsync(ResultSet rs, DataType<?>... types) {
      return this.fetchAsync(Tools.configuration(this.configuration()).executorProvider().provide(), rs, types);
   }

   public CompletionStage<Result<Record>> fetchAsync(ResultSet rs, Class<?>... types) {
      return this.fetchAsync(Tools.configuration(this.configuration()).executorProvider().provide(), rs, types);
   }

   public CompletionStage<Result<Record>> fetchAsync(Executor executor, ResultSet rs) {
      return ExecutorProviderCompletionStage.of(CompletableFuture.supplyAsync(Tools.blocking(() -> {
         return this.fetch(rs);
      }), executor), () -> {
         return executor;
      });
   }

   public CompletionStage<Result<Record>> fetchAsync(Executor executor, ResultSet rs, Field<?>... fields) {
      return ExecutorProviderCompletionStage.of(CompletableFuture.supplyAsync(Tools.blocking(() -> {
         return this.fetch(rs, fields);
      }), executor), () -> {
         return executor;
      });
   }

   public CompletionStage<Result<Record>> fetchAsync(Executor executor, ResultSet rs, DataType<?>... types) {
      return ExecutorProviderCompletionStage.of(CompletableFuture.supplyAsync(Tools.blocking(() -> {
         return this.fetch(rs, types);
      }), executor), () -> {
         return executor;
      });
   }

   public CompletionStage<Result<Record>> fetchAsync(Executor executor, ResultSet rs, Class<?>... types) {
      return ExecutorProviderCompletionStage.of(CompletableFuture.supplyAsync(Tools.blocking(() -> {
         return this.fetch(rs, types);
      }), executor), () -> {
         return executor;
      });
   }

   public Stream<Record> fetchStream(ResultSet rs) {
      return this.fetchLazy(rs).stream();
   }

   public Stream<Record> fetchStream(ResultSet rs, Field<?>... fields) {
      return this.fetchLazy(rs, fields).stream();
   }

   public Stream<Record> fetchStream(ResultSet rs, DataType<?>... types) {
      return this.fetchLazy(rs, types).stream();
   }

   public Stream<Record> fetchStream(ResultSet rs, Class<?>... types) {
      return this.fetchLazy(rs, types).stream();
   }

   public Result<Record> fetchFromTXT(String string) {
      return this.fetchFromTXT(string, "{null}");
   }

   public Result<Record> fetchFromTXT(String string, String nullLiteral) {
      return this.fetchFromStringData(Tools.parseTXT(string, nullLiteral));
   }

   public Result<Record> fetchFromHTML(String string) {
      return this.fetchFromStringData(Tools.parseHTML(string));
   }

   public Result<Record> fetchFromCSV(String string) {
      return this.fetchFromCSV(string, true, ',');
   }

   public Result<Record> fetchFromCSV(String string, char delimiter) {
      return this.fetchFromCSV(string, true, delimiter);
   }

   public Result<Record> fetchFromCSV(String string, boolean header) {
      return this.fetchFromCSV(string, header, ',');
   }

   public Result<Record> fetchFromCSV(String string, boolean header, char delimiter) {
      CSVReader reader = new CSVReader(new StringReader(string), delimiter);
      List list = null;

      try {
         list = reader.readAll();
      } catch (IOException var14) {
         throw new DataAccessException("Could not read the CSV string", var14);
      } finally {
         try {
            reader.close();
         } catch (IOException var13) {
         }

      }

      return this.fetchFromStringData(list, header);
   }

   public Result<Record> fetchFromJSON(String string) {
      List<String[]> list = new LinkedList();
      JSONReader reader = null;

      try {
         reader = new JSONReader(new StringReader(string));
         List<String[]> records = reader.readAll();
         String[] fields = reader.getFields();
         list.add(fields);
         list.addAll(records);
      } catch (IOException var13) {
         throw new DataAccessException("Could not read the JSON string", var13);
      } finally {
         try {
            if (reader != null) {
               reader.close();
            }
         } catch (IOException var12) {
         }

      }

      return this.fetchFromStringData((List)list);
   }

   public Result<Record> fetchFromStringData(String[]... strings) {
      return this.fetchFromStringData(Tools.list(strings), true);
   }

   public Result<Record> fetchFromStringData(List<String[]> strings) {
      return this.fetchFromStringData(strings, true);
   }

   public Result<Record> fetchFromStringData(List<String[]> strings, boolean header) {
      if (strings.size() == 0) {
         return new ResultImpl(this.configuration(), new Field[0]);
      } else {
         List<Field<?>> fields = new ArrayList();
         byte firstRow;
         if (header) {
            firstRow = 1;
            String[] var5 = (String[])strings.get(0);
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               String name = var5[var7];
               fields.add(DSL.field(DSL.name(name), String.class));
            }
         } else {
            firstRow = 0;

            for(int i = 0; i < ((String[])strings.get(0)).length; ++i) {
               fields.add(DSL.field(DSL.name("COL" + (i + 1)), String.class));
            }
         }

         Result<Record> result = new ResultImpl(this.configuration(), fields);
         if (strings.size() > firstRow) {
            Iterator var12 = strings.subList(firstRow, strings.size()).iterator();

            while(var12.hasNext()) {
               String[] values = (String[])var12.next();
               RecordImpl record = new RecordImpl(fields);

               for(int i = 0; i < Math.min(values.length, fields.size()); ++i) {
                  record.values[i] = values[i];
                  record.originals[i] = values[i];
               }

               result.add(record);
            }
         }

         return result;
      }
   }

   public WithAsStep with(String alias) {
      return (new WithImpl(this.configuration(), false)).with(alias);
   }

   public WithAsStep with(String alias, String... fieldAliases) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAliases);
   }

   public WithAsStep with(String alias, java.util.function.Function<? super Field<?>, ? extends String> fieldNameFunction) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldNameFunction);
   }

   public WithAsStep with(String alias, BiFunction<? super Field<?>, ? super Integer, ? extends String> fieldNameFunction) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldNameFunction);
   }

   public WithAsStep1 with(String alias, String fieldAlias1) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1);
   }

   public WithAsStep2 with(String alias, String fieldAlias1, String fieldAlias2) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2);
   }

   public WithAsStep3 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3);
   }

   public WithAsStep4 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4);
   }

   public WithAsStep5 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5);
   }

   public WithAsStep6 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6);
   }

   public WithAsStep7 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7);
   }

   public WithAsStep8 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8);
   }

   public WithAsStep9 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9);
   }

   public WithAsStep10 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10);
   }

   public WithAsStep11 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11);
   }

   public WithAsStep12 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12);
   }

   public WithAsStep13 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13);
   }

   public WithAsStep14 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14);
   }

   public WithAsStep15 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15);
   }

   public WithAsStep16 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16);
   }

   public WithAsStep17 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17);
   }

   public WithAsStep18 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18);
   }

   public WithAsStep19 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19);
   }

   public WithAsStep20 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20);
   }

   public WithAsStep21 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20, String fieldAlias21) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21);
   }

   public WithAsStep22 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20, String fieldAlias21, String fieldAlias22) {
      return (new WithImpl(this.configuration(), false)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21, fieldAlias22);
   }

   public WithStep with(CommonTableExpression<?>... tables) {
      return (new WithImpl(this.configuration(), false)).with(tables);
   }

   public WithAsStep withRecursive(String alias) {
      return (new WithImpl(this.configuration(), true)).with(alias);
   }

   public WithAsStep withRecursive(String alias, String... fieldAliases) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAliases);
   }

   public WithAsStep withRecursive(String alias, java.util.function.Function<? super Field<?>, ? extends String> fieldNameFunction) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldNameFunction);
   }

   public WithAsStep withRecursive(String alias, BiFunction<? super Field<?>, ? super Integer, ? extends String> fieldNameFunction) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldNameFunction);
   }

   public WithAsStep1 withRecursive(String alias, String fieldAlias1) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1);
   }

   public WithAsStep2 withRecursive(String alias, String fieldAlias1, String fieldAlias2) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2);
   }

   public WithAsStep3 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3);
   }

   public WithAsStep4 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4);
   }

   public WithAsStep5 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5);
   }

   public WithAsStep6 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6);
   }

   public WithAsStep7 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7);
   }

   public WithAsStep8 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8);
   }

   public WithAsStep9 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9);
   }

   public WithAsStep10 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10);
   }

   public WithAsStep11 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11);
   }

   public WithAsStep12 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12);
   }

   public WithAsStep13 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13);
   }

   public WithAsStep14 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14);
   }

   public WithAsStep15 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15);
   }

   public WithAsStep16 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16);
   }

   public WithAsStep17 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17);
   }

   public WithAsStep18 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18);
   }

   public WithAsStep19 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19);
   }

   public WithAsStep20 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20);
   }

   public WithAsStep21 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20, String fieldAlias21) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21);
   }

   public WithAsStep22 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20, String fieldAlias21, String fieldAlias22) {
      return (new WithImpl(this.configuration(), true)).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21, fieldAlias22);
   }

   public WithStep withRecursive(CommonTableExpression<?>... tables) {
      return (new WithImpl(this.configuration(), true)).with(tables);
   }

   public <R extends Record> SelectWhereStep<R> selectFrom(Table<R> table) {
      SelectWhereStep<R> result = DSL.selectFrom(table);
      result.attach(this.configuration());
      return result;
   }

   public SelectSelectStep<Record> select(Collection<? extends SelectField<?>> fields) {
      SelectSelectStep<Record> result = DSL.select(fields);
      result.attach(this.configuration());
      return result;
   }

   public SelectSelectStep<Record> select(SelectField<?>... fields) {
      SelectSelectStep<Record> result = DSL.select(fields);
      result.attach(this.configuration());
      return result;
   }

   public <T1> SelectSelectStep<Record1<T1>> select(SelectField<T1> field1) {
      return this.select(field1);
   }

   public <T1, T2> SelectSelectStep<Record2<T1, T2>> select(SelectField<T1> field1, SelectField<T2> field2) {
      return this.select(field1, field2);
   }

   public <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3) {
      return this.select(field1, field2, field3);
   }

   public <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4) {
      return this.select(field1, field2, field3, field4);
   }

   public <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5) {
      return this.select(field1, field2, field3, field4, field5);
   }

   public <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6) {
      return this.select(field1, field2, field3, field4, field5, field6);
   }

   public <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7) {
      return this.select(field1, field2, field3, field4, field5, field6, field7);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8) {
      return this.select(field1, field2, field3, field4, field5, field6, field7, field8);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9) {
      return this.select(field1, field2, field3, field4, field5, field6, field7, field8, field9);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10) {
      return this.select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11) {
      return this.select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12) {
      return this.select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13) {
      return this.select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14) {
      return this.select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15) {
      return this.select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16) {
      return this.select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17) {
      return this.select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18) {
      return this.select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19) {
      return this.select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20) {
      return this.select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21) {
      return this.select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21, SelectField<T22> field22) {
      return this.select(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
   }

   public SelectSelectStep<Record> selectDistinct(Collection<? extends SelectField<?>> fields) {
      SelectSelectStep<Record> result = DSL.selectDistinct(fields);
      result.attach(this.configuration());
      return result;
   }

   public SelectSelectStep<Record> selectDistinct(SelectField<?>... fields) {
      SelectSelectStep<Record> result = DSL.selectDistinct(fields);
      result.attach(this.configuration());
      return result;
   }

   public <T1> SelectSelectStep<Record1<T1>> selectDistinct(SelectField<T1> field1) {
      return this.selectDistinct(field1);
   }

   public <T1, T2> SelectSelectStep<Record2<T1, T2>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2) {
      return this.selectDistinct(field1, field2);
   }

   public <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3) {
      return this.selectDistinct(field1, field2, field3);
   }

   public <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4) {
      return this.selectDistinct(field1, field2, field3, field4);
   }

   public <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5) {
      return this.selectDistinct(field1, field2, field3, field4, field5);
   }

   public <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6) {
      return this.selectDistinct(field1, field2, field3, field4, field5, field6);
   }

   public <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7) {
      return this.selectDistinct(field1, field2, field3, field4, field5, field6, field7);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8) {
      return this.selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9) {
      return this.selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10) {
      return this.selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11) {
      return this.selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12) {
      return this.selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13) {
      return this.selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14) {
      return this.selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15) {
      return this.selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16) {
      return this.selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17) {
      return this.selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18) {
      return this.selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19) {
      return this.selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20) {
      return this.selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21) {
      return this.selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21, SelectField<T22> field22) {
      return this.selectDistinct(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
   }

   public SelectSelectStep<Record1<Integer>> selectZero() {
      SelectSelectStep<Record1<Integer>> result = DSL.selectZero();
      result.attach(this.configuration());
      return result;
   }

   public SelectSelectStep<Record1<Integer>> selectOne() {
      SelectSelectStep<Record1<Integer>> result = DSL.selectOne();
      result.attach(this.configuration());
      return result;
   }

   public SelectSelectStep<Record1<Integer>> selectCount() {
      SelectSelectStep<Record1<Integer>> result = DSL.selectCount();
      result.attach(this.configuration());
      return result;
   }

   public SelectQuery<Record> selectQuery() {
      return new SelectQueryImpl(this.configuration(), (WithImpl)null);
   }

   public <R extends Record> SelectQuery<R> selectQuery(TableLike<R> table) {
      return new SelectQueryImpl(this.configuration(), (WithImpl)null, table);
   }

   public <R extends Record> InsertQuery<R> insertQuery(Table<R> into) {
      return new InsertQueryImpl(this.configuration(), (WithImpl)null, into);
   }

   public <R extends Record> InsertSetStep<R> insertInto(Table<R> into) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Collections.emptyList());
   }

   public <R extends Record, T1> InsertValuesStep1<R, T1> insertInto(Table<R> into, Field<T1> field1) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1));
   }

   public <R extends Record, T1, T2> InsertValuesStep2<R, T1, T2> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2));
   }

   public <R extends Record, T1, T2, T3> InsertValuesStep3<R, T1, T2, T3> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3));
   }

   public <R extends Record, T1, T2, T3, T4> InsertValuesStep4<R, T1, T2, T3, T4> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3, field4));
   }

   public <R extends Record, T1, T2, T3, T4, T5> InsertValuesStep5<R, T1, T2, T3, T4, T5> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3, field4, field5));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6> InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3, field4, field5, field6));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7> InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> InsertValuesStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> InsertValuesStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> InsertValuesStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> InsertValuesStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> InsertValuesStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> InsertValuesStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> InsertValuesStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> InsertValuesStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> InsertValuesStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> InsertValuesStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> InsertValuesStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> InsertValuesStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> InsertValuesStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22));
   }

   public <R extends Record> InsertValuesStepN<R> insertInto(Table<R> into, Field<?>... fields) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, Arrays.asList(fields));
   }

   public <R extends Record> InsertValuesStepN<R> insertInto(Table<R> into, Collection<? extends Field<?>> fields) {
      return new InsertImpl(this.configuration(), (WithImpl)null, into, fields);
   }

   public <R extends Record> UpdateQuery<R> updateQuery(Table<R> table) {
      return new UpdateQueryImpl(this.configuration(), (WithImpl)null, table);
   }

   public <R extends Record> UpdateSetFirstStep<R> update(Table<R> table) {
      return new UpdateImpl(this.configuration(), (WithImpl)null, table);
   }

   public <R extends Record> MergeUsingStep<R> mergeInto(Table<R> table) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table);
   }

   public <R extends Record, T1> MergeKeyStep1<R, T1> mergeInto(Table<R> table, Field<T1> field1) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1));
   }

   public <R extends Record, T1, T2> MergeKeyStep2<R, T1, T2> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2));
   }

   public <R extends Record, T1, T2, T3> MergeKeyStep3<R, T1, T2, T3> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3));
   }

   public <R extends Record, T1, T2, T3, T4> MergeKeyStep4<R, T1, T2, T3, T4> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3, field4));
   }

   public <R extends Record, T1, T2, T3, T4, T5> MergeKeyStep5<R, T1, T2, T3, T4, T5> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3, field4, field5));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6> MergeKeyStep6<R, T1, T2, T3, T4, T5, T6> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3, field4, field5, field6));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7> MergeKeyStep7<R, T1, T2, T3, T4, T5, T6, T7> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> MergeKeyStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> MergeKeyStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> MergeKeyStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> MergeKeyStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> MergeKeyStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> MergeKeyStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> MergeKeyStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> MergeKeyStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> MergeKeyStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> MergeKeyStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> MergeKeyStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> MergeKeyStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> MergeKeyStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> MergeKeyStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21));
   }

   public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> MergeKeyStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22));
   }

   public <R extends Record> MergeKeyStepN<R> mergeInto(Table<R> table, Field<?>... fields) {
      return this.mergeInto(table, (Collection)Arrays.asList(fields));
   }

   public <R extends Record> MergeKeyStepN<R> mergeInto(Table<R> table, Collection<? extends Field<?>> fields) {
      return new MergeImpl(this.configuration(), (WithImpl)null, table, fields);
   }

   public <R extends Record> DeleteQuery<R> deleteQuery(Table<R> table) {
      return new DeleteQueryImpl(this.configuration(), (WithImpl)null, table);
   }

   public <R extends Record> DeleteWhereStep<R> delete(Table<R> table) {
      return this.deleteFrom(table);
   }

   public <R extends Record> DeleteWhereStep<R> deleteFrom(Table<R> table) {
      return new DeleteImpl(this.configuration(), (WithImpl)null, table);
   }

   public Batch batch(Query... queries) {
      return new BatchMultiple(this.configuration(), queries);
   }

   public Batch batch(Queries queries) {
      return this.batch(queries.queries());
   }

   public Batch batch(String... queries) {
      Query[] result = new Query[queries.length];

      for(int i = 0; i < queries.length; ++i) {
         result[i] = this.query(queries[i]);
      }

      return this.batch(result);
   }

   public Batch batch(Collection<? extends Query> queries) {
      return this.batch((Query[])queries.toArray(Tools.EMPTY_QUERY));
   }

   public BatchBindStep batch(Query query) {
      return new BatchSingle(this.configuration(), query);
   }

   public BatchBindStep batch(String sql) {
      return this.batch(this.query(sql));
   }

   public Batch batch(Query query, Object[]... bindings) {
      return this.batch(query).bind(bindings);
   }

   public Batch batch(String sql, Object[]... bindings) {
      return this.batch(this.query(sql), bindings);
   }

   public Batch batchStore(UpdatableRecord<?>... records) {
      return new BatchCRUD(this.configuration(), BatchCRUD.Action.STORE, records);
   }

   public Batch batchStore(Collection<? extends UpdatableRecord<?>> records) {
      return this.batchStore((UpdatableRecord[])records.toArray(Tools.EMPTY_UPDATABLE_RECORD));
   }

   public Batch batchInsert(TableRecord<?>... records) {
      return new BatchCRUD(this.configuration(), BatchCRUD.Action.INSERT, records);
   }

   public Batch batchInsert(Collection<? extends TableRecord<?>> records) {
      return this.batchInsert((TableRecord[])records.toArray(Tools.EMPTY_TABLE_RECORD));
   }

   public Batch batchUpdate(UpdatableRecord<?>... records) {
      return new BatchCRUD(this.configuration(), BatchCRUD.Action.UPDATE, records);
   }

   public Batch batchUpdate(Collection<? extends UpdatableRecord<?>> records) {
      return this.batchUpdate((UpdatableRecord[])records.toArray(Tools.EMPTY_UPDATABLE_RECORD));
   }

   public Batch batchDelete(UpdatableRecord<?>... records) {
      return new BatchCRUD(this.configuration(), BatchCRUD.Action.DELETE, records);
   }

   public Batch batchDelete(Collection<? extends UpdatableRecord<?>> records) {
      return this.batchDelete((UpdatableRecord[])records.toArray(Tools.EMPTY_UPDATABLE_RECORD));
   }

   public Queries ddl(Catalog catalog) {
      return this.ddl(catalog, DDLFlag.values());
   }

   public Queries ddl(Catalog schema, DDLFlag... flags) {
      return (new DDL(this, flags)).queries(schema);
   }

   public Queries ddl(Schema schema) {
      return this.ddl(schema, DDLFlag.values());
   }

   public Queries ddl(Schema schema, DDLFlag... flags) {
      return (new DDL(this, flags)).queries(schema);
   }

   public Queries ddl(Table<?> table) {
      return this.ddl(table, DDLFlag.values());
   }

   public Queries ddl(Table<?> table, DDLFlag... flags) {
      return (new DDL(this, flags)).queries(table);
   }

   public CreateViewAsStep<Record> createView(String view, String... fields) {
      return this.createView(DSL.table(DSL.name(view)), Tools.fieldsByName(view, fields));
   }

   public CreateViewAsStep<Record> createView(Name view, Name... fields) {
      return this.createView(DSL.table(view), Tools.fieldsByName(fields));
   }

   public CreateViewAsStep<Record> createView(Table<?> view, Field<?>... fields) {
      return new CreateViewImpl(this.configuration(), view, fields, false);
   }

   public CreateViewAsStep<Record> createView(String view, java.util.function.Function<? super Field<?>, ? extends String> fieldNameFunction) {
      return this.createView(DSL.table(DSL.name(view)), (f, i) -> {
         return DSL.field(DSL.name((String)fieldNameFunction.apply(f)));
      });
   }

   public CreateViewAsStep<Record> createView(String view, BiFunction<? super Field<?>, ? super Integer, ? extends String> fieldNameFunction) {
      return this.createView(DSL.table(DSL.name(view)), (f, i) -> {
         return DSL.field(DSL.name((String)fieldNameFunction.apply(f, i)));
      });
   }

   public CreateViewAsStep<Record> createView(Name view, java.util.function.Function<? super Field<?>, ? extends Name> fieldNameFunction) {
      return this.createView(DSL.table(view), (f, i) -> {
         return DSL.field((Name)fieldNameFunction.apply(f));
      });
   }

   public CreateViewAsStep<Record> createView(Name view, BiFunction<? super Field<?>, ? super Integer, ? extends Name> fieldNameFunction) {
      return this.createView(DSL.table(view), (f, i) -> {
         return DSL.field((Name)fieldNameFunction.apply(f, i));
      });
   }

   public CreateViewAsStep<Record> createView(Table<?> view, java.util.function.Function<? super Field<?>, ? extends Field<?>> fieldNameFunction) {
      return this.createView(view, (f, i) -> {
         return (Field)fieldNameFunction.apply(f);
      });
   }

   public CreateViewAsStep<Record> createView(Table<?> view, BiFunction<? super Field<?>, ? super Integer, ? extends Field<?>> fieldNameFunction) {
      return new CreateViewImpl(this.configuration(), view, fieldNameFunction, false);
   }

   public CreateViewAsStep<Record> createViewIfNotExists(String view, String... fields) {
      return this.createViewIfNotExists(DSL.table(DSL.name(view)), Tools.fieldsByName(view, fields));
   }

   public CreateViewAsStep<Record> createViewIfNotExists(Name view, Name... fields) {
      return this.createViewIfNotExists(DSL.table(view), Tools.fieldsByName(fields));
   }

   public CreateViewAsStep<Record> createViewIfNotExists(Table<?> view, Field<?>... fields) {
      return new CreateViewImpl(this.configuration(), view, fields, true);
   }

   public CreateViewAsStep<Record> createViewIfNotExists(String view, java.util.function.Function<? super Field<?>, ? extends String> fieldNameFunction) {
      return this.createViewIfNotExists(DSL.table(DSL.name(view)), (f, i) -> {
         return DSL.field(DSL.name((String)fieldNameFunction.apply(f)));
      });
   }

   public CreateViewAsStep<Record> createViewIfNotExists(String view, BiFunction<? super Field<?>, ? super Integer, ? extends String> fieldNameFunction) {
      return this.createViewIfNotExists(DSL.table(DSL.name(view)), (f, i) -> {
         return DSL.field(DSL.name((String)fieldNameFunction.apply(f, i)));
      });
   }

   public CreateViewAsStep<Record> createViewIfNotExists(Name view, java.util.function.Function<? super Field<?>, ? extends Name> fieldNameFunction) {
      return this.createViewIfNotExists(DSL.table(view), (f, i) -> {
         return DSL.field((Name)fieldNameFunction.apply(f));
      });
   }

   public CreateViewAsStep<Record> createViewIfNotExists(Name view, BiFunction<? super Field<?>, ? super Integer, ? extends Name> fieldNameFunction) {
      return this.createViewIfNotExists(DSL.table(view), (f, i) -> {
         return DSL.field((Name)fieldNameFunction.apply(f, i));
      });
   }

   public CreateViewAsStep<Record> createViewIfNotExists(Table<?> view, java.util.function.Function<? super Field<?>, ? extends Field<?>> fieldNameFunction) {
      return this.createViewIfNotExists(view, (f, i) -> {
         return (Field)fieldNameFunction.apply(f);
      });
   }

   public CreateViewAsStep<Record> createViewIfNotExists(Table<?> view, BiFunction<? super Field<?>, ? super Integer, ? extends Field<?>> fieldNameFunction) {
      return new CreateViewImpl(this.configuration(), view, fieldNameFunction, true);
   }

   public CreateSchemaFinalStep createSchema(String schema) {
      return this.createSchema(DSL.name(schema));
   }

   public CreateSchemaFinalStep createSchema(Name schema) {
      return this.createSchema(DSL.schema(schema));
   }

   public CreateSchemaFinalStep createSchema(Schema schema) {
      return new CreateSchemaImpl(this.configuration(), schema, false);
   }

   public CreateSchemaFinalStep createSchemaIfNotExists(String schema) {
      return this.createSchemaIfNotExists(DSL.name(schema));
   }

   public CreateSchemaFinalStep createSchemaIfNotExists(Name schema) {
      return this.createSchemaIfNotExists(DSL.schema(schema));
   }

   public CreateSchemaFinalStep createSchemaIfNotExists(Schema schema) {
      return new CreateSchemaImpl(this.configuration(), schema, true);
   }

   public CreateTableAsStep<Record> createTable(String table) {
      return this.createTable(DSL.name(table));
   }

   public CreateTableAsStep<Record> createTable(Name table) {
      return this.createTable(DSL.table(table));
   }

   public CreateTableAsStep<Record> createTable(Table<?> table) {
      return new CreateTableImpl(this.configuration(), table, false, false);
   }

   public CreateTableAsStep<Record> createTableIfNotExists(String table) {
      return this.createTableIfNotExists(DSL.name(table));
   }

   public CreateTableAsStep<Record> createTableIfNotExists(Name table) {
      return this.createTableIfNotExists(DSL.table(table));
   }

   public CreateTableAsStep<Record> createTableIfNotExists(Table<?> table) {
      return new CreateTableImpl(this.configuration(), table, false, true);
   }

   public CreateTableAsStep<Record> createTemporaryTable(String table) {
      return this.createTemporaryTable(DSL.name(table));
   }

   public CreateTableAsStep<Record> createTemporaryTable(Name table) {
      return this.createTemporaryTable(DSL.table(table));
   }

   public CreateTableAsStep<Record> createTemporaryTable(Table<?> table) {
      return new CreateTableImpl(this.configuration(), table, true, false);
   }

   public CreateTableAsStep<Record> createGlobalTemporaryTable(String table) {
      return this.createGlobalTemporaryTable(DSL.name(table));
   }

   public CreateTableAsStep<Record> createGlobalTemporaryTable(Name table) {
      return this.createGlobalTemporaryTable(DSL.table(table));
   }

   public CreateTableAsStep<Record> createGlobalTemporaryTable(Table<?> table) {
      return new CreateTableImpl(this.configuration(), table, true, false);
   }

   public CreateIndexStep createIndex(String index) {
      return this.createIndex(DSL.name(index));
   }

   public CreateIndexStep createIndex(Name index) {
      return new CreateIndexImpl(this.configuration(), index, false, false);
   }

   public CreateIndexStep createIndexIfNotExists(String index) {
      return this.createIndexIfNotExists(DSL.name(index));
   }

   public CreateIndexStep createIndexIfNotExists(Name index) {
      return new CreateIndexImpl(this.configuration(), index, false, true);
   }

   public CreateIndexStep createUniqueIndex(String index) {
      return this.createUniqueIndex(DSL.name(index));
   }

   public CreateIndexStep createUniqueIndex(Name index) {
      return new CreateIndexImpl(this.configuration(), index, true, false);
   }

   public CreateIndexStep createUniqueIndexIfNotExists(String index) {
      return this.createUniqueIndexIfNotExists(DSL.name(index));
   }

   public CreateIndexStep createUniqueIndexIfNotExists(Name index) {
      return new CreateIndexImpl(this.configuration(), index, true, true);
   }

   public CreateSequenceFinalStep createSequence(String sequence) {
      return this.createSequence(DSL.name(sequence));
   }

   public CreateSequenceFinalStep createSequence(Name sequence) {
      return this.createSequence(DSL.sequence(sequence));
   }

   public CreateSequenceFinalStep createSequence(Sequence<?> sequence) {
      return new CreateSequenceImpl(this.configuration(), sequence, false);
   }

   public CreateSequenceFinalStep createSequenceIfNotExists(String sequence) {
      return this.createSequenceIfNotExists(DSL.name(sequence));
   }

   public CreateSequenceFinalStep createSequenceIfNotExists(Name sequence) {
      return this.createSequenceIfNotExists(DSL.sequence(sequence));
   }

   public CreateSequenceFinalStep createSequenceIfNotExists(Sequence<?> sequence) {
      return new CreateSequenceImpl(this.configuration(), sequence, true);
   }

   public AlterSequenceStep<BigInteger> alterSequence(String sequence) {
      return this.alterSequence(DSL.name(sequence));
   }

   public AlterSequenceStep<BigInteger> alterSequence(Name sequence) {
      return this.alterSequence(DSL.sequence(sequence));
   }

   public <T extends Number> AlterSequenceStep<T> alterSequence(Sequence<T> sequence) {
      return new AlterSequenceImpl(this.configuration(), sequence);
   }

   public AlterSequenceStep<BigInteger> alterSequenceIfExists(String sequence) {
      return this.alterSequenceIfExists(DSL.name(sequence));
   }

   public AlterSequenceStep<BigInteger> alterSequenceIfExists(Name sequence) {
      return this.alterSequenceIfExists(DSL.sequence(sequence));
   }

   public <T extends Number> AlterSequenceStep<T> alterSequenceIfExists(Sequence<T> sequence) {
      return new AlterSequenceImpl(this.configuration(), sequence, true);
   }

   public AlterTableStep alterTable(String table) {
      return this.alterTable(DSL.name(table));
   }

   public AlterTableStep alterTable(Name table) {
      return this.alterTable(DSL.table(table));
   }

   public AlterTableStep alterTable(Table<?> table) {
      return new AlterTableImpl(this.configuration(), table);
   }

   public AlterTableStep alterTableIfExists(String table) {
      return this.alterTableIfExists(DSL.name(table));
   }

   public AlterTableStep alterTableIfExists(Name table) {
      return this.alterTableIfExists(DSL.table(table));
   }

   public AlterTableStep alterTableIfExists(Table<?> table) {
      return new AlterTableImpl(this.configuration(), table, true);
   }

   public AlterSchemaStep alterSchema(String schema) {
      return this.alterSchema(DSL.name(schema));
   }

   public AlterSchemaStep alterSchema(Name schema) {
      return this.alterSchema(DSL.schema(schema));
   }

   public AlterSchemaStep alterSchema(Schema schema) {
      return new AlterSchemaImpl(this.configuration(), schema);
   }

   public AlterSchemaStep alterSchemaIfExists(String schema) {
      return this.alterSchemaIfExists(DSL.name(schema));
   }

   public AlterSchemaStep alterSchemaIfExists(Name schema) {
      return this.alterSchemaIfExists(DSL.schema(schema));
   }

   public AlterSchemaStep alterSchemaIfExists(Schema schema) {
      return new AlterSchemaImpl(this.configuration(), schema, true);
   }

   public AlterViewStep alterView(String table) {
      return this.alterView(DSL.name(table));
   }

   public AlterViewStep alterView(Name table) {
      return this.alterView(DSL.table(table));
   }

   public AlterViewStep alterView(Table<?> table) {
      return new AlterViewImpl(this.configuration(), table);
   }

   public AlterViewStep alterViewIfExists(String table) {
      return this.alterViewIfExists(DSL.name(table));
   }

   public AlterViewStep alterViewIfExists(Name table) {
      return this.alterViewIfExists(DSL.table(table));
   }

   public AlterViewStep alterViewIfExists(Table<?> table) {
      return new AlterViewImpl(this.configuration(), table, true);
   }

   public AlterIndexStep alterIndex(String index) {
      return this.alterIndex(DSL.name(index));
   }

   public AlterIndexStep alterIndex(Name index) {
      return new AlterIndexImpl(this.configuration(), index);
   }

   public AlterIndexStep alterIndexIfExists(String index) {
      return this.alterIndexIfExists(DSL.name(index));
   }

   public AlterIndexStep alterIndexIfExists(Name index) {
      return new AlterIndexImpl(this.configuration(), index, true);
   }

   public DropSchemaStep dropSchema(String schema) {
      return this.dropSchema(DSL.name(schema));
   }

   public DropSchemaStep dropSchema(Name schema) {
      return this.dropSchema(DSL.schema(schema));
   }

   public DropSchemaStep dropSchema(Schema schema) {
      return new DropSchemaImpl(this.configuration(), schema);
   }

   public DropSchemaStep dropSchemaIfExists(String schema) {
      return this.dropSchemaIfExists(DSL.name(schema));
   }

   public DropSchemaStep dropSchemaIfExists(Name schema) {
      return this.dropSchemaIfExists(DSL.schema(schema));
   }

   public DropSchemaStep dropSchemaIfExists(Schema schema) {
      return new DropSchemaImpl(this.configuration(), schema, true);
   }

   public DropViewFinalStep dropView(String view) {
      return this.dropView(DSL.name(view));
   }

   public DropViewFinalStep dropView(Name view) {
      return this.dropView(DSL.table(view));
   }

   public DropViewFinalStep dropView(Table<?> view) {
      return new DropViewImpl(this.configuration(), view);
   }

   public DropViewFinalStep dropViewIfExists(String view) {
      return this.dropViewIfExists(DSL.name(view));
   }

   public DropViewFinalStep dropViewIfExists(Name view) {
      return this.dropViewIfExists(DSL.table(view));
   }

   public DropViewFinalStep dropViewIfExists(Table<?> view) {
      return new DropViewImpl(this.configuration(), view, true);
   }

   public DropTableStep dropTable(String table) {
      return this.dropTable(DSL.name(table));
   }

   public DropTableStep dropTable(Name table) {
      return this.dropTable(DSL.table(table));
   }

   public DropTableStep dropTable(Table<?> table) {
      return new DropTableImpl(this.configuration(), table);
   }

   public DropTableStep dropTableIfExists(String table) {
      return this.dropTableIfExists(DSL.name(table));
   }

   public DropTableStep dropTableIfExists(Name table) {
      return this.dropTableIfExists(DSL.table(table));
   }

   public DropTableStep dropTableIfExists(Table<?> table) {
      return new DropTableImpl(this.configuration(), table, true);
   }

   public DropIndexOnStep dropIndex(String index) {
      return this.dropIndex(DSL.name(index));
   }

   public DropIndexOnStep dropIndex(Name index) {
      return new DropIndexImpl(this.configuration(), index);
   }

   public DropIndexOnStep dropIndexIfExists(String index) {
      return this.dropIndexIfExists(DSL.name(index));
   }

   public DropIndexOnStep dropIndexIfExists(Name index) {
      return new DropIndexImpl(this.configuration(), index, true);
   }

   public DropSequenceFinalStep dropSequence(String sequence) {
      return this.dropSequence(DSL.name(sequence));
   }

   public DropSequenceFinalStep dropSequence(Name sequence) {
      return this.dropSequence(DSL.sequence(sequence));
   }

   public DropSequenceFinalStep dropSequence(Sequence<?> sequence) {
      return new DropSequenceImpl(this.configuration(), sequence);
   }

   public DropSequenceFinalStep dropSequenceIfExists(String sequence) {
      return this.dropSequenceIfExists(DSL.name(sequence));
   }

   public DropSequenceFinalStep dropSequenceIfExists(Name sequence) {
      return this.dropSequenceIfExists(DSL.sequence(sequence));
   }

   public DropSequenceFinalStep dropSequenceIfExists(Sequence<?> sequence) {
      return new DropSequenceImpl(this.configuration(), sequence, true);
   }

   public final TruncateIdentityStep<Record> truncate(String table) {
      return this.truncate(DSL.name(table));
   }

   public final TruncateIdentityStep<Record> truncate(Name table) {
      return this.truncate(DSL.table(table));
   }

   public <R extends Record> TruncateIdentityStep<R> truncate(Table<R> table) {
      return new TruncateImpl(this.configuration(), table);
   }

   public BigInteger lastID() {
      Field field;
      switch(this.configuration().dialect().family()) {
      case DERBY:
         field = DSL.field("identity_val_local()", BigInteger.class);
         return (BigInteger)this.select((SelectField)field).fetchOne(field);
      case H2:
      case HSQLDB:
         field = DSL.field("identity()", BigInteger.class);
         return (BigInteger)this.select((SelectField)field).fetchOne(field);
      case CUBRID:
      case MARIADB:
      case MYSQL:
         field = DSL.field("last_insert_id()", BigInteger.class);
         return (BigInteger)this.select((SelectField)field).fetchOne(field);
      case SQLITE:
         field = DSL.field("last_insert_rowid()", BigInteger.class);
         return (BigInteger)this.select((SelectField)field).fetchOne(field);
      case POSTGRES:
         field = DSL.field("lastval()", BigInteger.class);
         return (BigInteger)this.select((SelectField)field).fetchOne(field);
      default:
         throw new SQLDialectNotSupportedException("identity functionality not supported by " + this.configuration().dialect());
      }
   }

   public BigInteger nextval(String sequence) {
      return (BigInteger)this.nextval(DSL.sequence(DSL.name(sequence)));
   }

   public <T extends Number> T nextval(Sequence<T> sequence) {
      Field<T> nextval = sequence.nextval();
      return (Number)this.select((SelectField)nextval).fetchOne(nextval);
   }

   public BigInteger currval(String sequence) {
      return (BigInteger)this.currval(DSL.sequence(DSL.name(sequence)));
   }

   public <T extends Number> T currval(Sequence<T> sequence) {
      Field<T> currval = sequence.currval();
      return (Number)this.select((SelectField)currval).fetchOne(currval);
   }

   public Record newRecord(Field<?>... fields) {
      return Tools.newRecord(false, RecordImpl.class, fields, this.configuration()).operate((RecordOperation)null);
   }

   public <T1> Record1<T1> newRecord(Field<T1> field1) {
      return (Record1)this.newRecord(field1);
   }

   public <T1, T2> Record2<T1, T2> newRecord(Field<T1> field1, Field<T2> field2) {
      return (Record2)this.newRecord(field1, field2);
   }

   public <T1, T2, T3> Record3<T1, T2, T3> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
      return (Record3)this.newRecord(field1, field2, field3);
   }

   public <T1, T2, T3, T4> Record4<T1, T2, T3, T4> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
      return (Record4)this.newRecord(field1, field2, field3, field4);
   }

   public <T1, T2, T3, T4, T5> Record5<T1, T2, T3, T4, T5> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
      return (Record5)this.newRecord(field1, field2, field3, field4, field5);
   }

   public <T1, T2, T3, T4, T5, T6> Record6<T1, T2, T3, T4, T5, T6> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
      return (Record6)this.newRecord(field1, field2, field3, field4, field5, field6);
   }

   public <T1, T2, T3, T4, T5, T6, T7> Record7<T1, T2, T3, T4, T5, T6, T7> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
      return (Record7)this.newRecord(field1, field2, field3, field4, field5, field6, field7);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8> Record8<T1, T2, T3, T4, T5, T6, T7, T8> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
      return (Record8)this.newRecord(field1, field2, field3, field4, field5, field6, field7, field8);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
      return (Record9)this.newRecord(field1, field2, field3, field4, field5, field6, field7, field8, field9);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
      return (Record10)this.newRecord(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
      return (Record11)this.newRecord(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
      return (Record12)this.newRecord(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
      return (Record13)this.newRecord(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
      return (Record14)this.newRecord(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
      return (Record15)this.newRecord(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
      return (Record16)this.newRecord(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
      return (Record17)this.newRecord(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
      return (Record18)this.newRecord(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
      return (Record19)this.newRecord(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
      return (Record20)this.newRecord(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
      return (Record21)this.newRecord(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
      return (Record22)this.newRecord(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
   }

   public <R extends UDTRecord<R>> R newRecord(UDT<R> type) {
      return (UDTRecord)Tools.newRecord(false, type, this.configuration()).operate((RecordOperation)null);
   }

   public <R extends Record> R newRecord(Table<R> table) {
      return Tools.newRecord(false, table, this.configuration()).operate((RecordOperation)null);
   }

   public <R extends Record> R newRecord(Table<R> table, final Object source) {
      return Tools.newRecord(false, table, this.configuration()).operate(new RecordOperation<R, RuntimeException>() {
         public R operate(R record) {
            record.from(source);
            return record;
         }
      });
   }

   public <R extends Record> Result<R> newResult(Table<R> table) {
      return new ResultImpl(this.configuration(), table.fields());
   }

   public Result<Record> newResult(Field<?>... fields) {
      return new ResultImpl(this.configuration(), fields);
   }

   public <T1> Result<Record1<T1>> newResult(Field<T1> field1) {
      return this.newResult(field1);
   }

   public <T1, T2> Result<Record2<T1, T2>> newResult(Field<T1> field1, Field<T2> field2) {
      return this.newResult(field1, field2);
   }

   public <T1, T2, T3> Result<Record3<T1, T2, T3>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
      return this.newResult(field1, field2, field3);
   }

   public <T1, T2, T3, T4> Result<Record4<T1, T2, T3, T4>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
      return this.newResult(field1, field2, field3, field4);
   }

   public <T1, T2, T3, T4, T5> Result<Record5<T1, T2, T3, T4, T5>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
      return this.newResult(field1, field2, field3, field4, field5);
   }

   public <T1, T2, T3, T4, T5, T6> Result<Record6<T1, T2, T3, T4, T5, T6>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
      return this.newResult(field1, field2, field3, field4, field5, field6);
   }

   public <T1, T2, T3, T4, T5, T6, T7> Result<Record7<T1, T2, T3, T4, T5, T6, T7>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
      return this.newResult(field1, field2, field3, field4, field5, field6, field7);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8> Result<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
      return this.newResult(field1, field2, field3, field4, field5, field6, field7, field8);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Result<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
      return this.newResult(field1, field2, field3, field4, field5, field6, field7, field8, field9);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Result<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
      return this.newResult(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Result<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
      return this.newResult(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Result<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
      return this.newResult(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Result<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
      return this.newResult(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Result<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
      return this.newResult(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Result<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
      return this.newResult(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Result<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
      return this.newResult(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Result<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
      return this.newResult(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Result<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
      return this.newResult(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Result<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
      return this.newResult(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Result<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
      return this.newResult(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Result<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
      return this.newResult(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21);
   }

   public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Result<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
      return this.newResult(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22);
   }

   public <R extends Record> Result<R> fetch(ResultQuery<R> query) {
      Configuration previous = Tools.getConfiguration(query);

      Result var3;
      try {
         query.attach(this.configuration());
         var3 = query.fetch();
      } finally {
         query.attach(previous);
      }

      return var3;
   }

   public <R extends Record> Cursor<R> fetchLazy(ResultQuery<R> query) {
      Configuration previous = Tools.getConfiguration(query);

      Cursor var3;
      try {
         query.attach(this.configuration());
         var3 = query.fetchLazy();
      } finally {
         query.attach(previous);
      }

      return var3;
   }

   public <R extends Record> CompletionStage<Result<R>> fetchAsync(ResultQuery<R> query) {
      Configuration previous = Tools.getConfiguration(query);

      CompletionStage var3;
      try {
         query.attach(this.configuration());
         var3 = query.fetchAsync();
      } finally {
         query.attach(previous);
      }

      return var3;
   }

   public <R extends Record> CompletionStage<Result<R>> fetchAsync(Executor executor, ResultQuery<R> query) {
      Configuration previous = Tools.getConfiguration(query);

      CompletionStage var4;
      try {
         query.attach(this.configuration());
         var4 = query.fetchAsync(executor);
      } finally {
         query.attach(previous);
      }

      return var4;
   }

   public <R extends Record> Stream<R> fetchStream(ResultQuery<R> query) {
      Configuration previous = Tools.getConfiguration(query);

      Stream var3;
      try {
         query.attach(this.configuration());
         var3 = query.stream();
      } finally {
         query.attach(previous);
      }

      return var3;
   }

   public <R extends Record> Results fetchMany(ResultQuery<R> query) {
      Configuration previous = Tools.getConfiguration(query);

      Results var3;
      try {
         query.attach(this.configuration());
         var3 = query.fetchMany();
      } finally {
         query.attach(previous);
      }

      return var3;
   }

   public <R extends Record> R fetchOne(ResultQuery<R> query) {
      Configuration previous = Tools.getConfiguration(query);

      Record var3;
      try {
         query.attach(this.configuration());
         var3 = query.fetchOne();
      } finally {
         query.attach(previous);
      }

      return var3;
   }

   public <R extends Record> Optional<R> fetchOptional(ResultQuery<R> query) {
      return Optional.ofNullable(this.fetchOne(query));
   }

   public <T, R extends Record1<T>> T fetchValue(ResultQuery<R> query) {
      Configuration previous = Tools.getConfiguration(query);

      Object var3;
      try {
         query.attach(this.configuration());
         var3 = this.value1((Record1)this.fetchOne(query));
      } finally {
         query.attach(previous);
      }

      return var3;
   }

   public <T> T fetchValue(TableField<?, T> field) {
      return this.fetchValue((ResultQuery)this.select((SelectField)field).from(field.getTable()));
   }

   public <T, R extends Record1<T>> Optional<T> fetchOptionalValue(ResultQuery<R> query) {
      return Optional.ofNullable(this.fetchValue(query));
   }

   public <T> Optional<T> fetchOptionalValue(TableField<?, T> field) {
      return Optional.ofNullable(this.fetchValue(field));
   }

   public <T, R extends Record1<T>> List<T> fetchValues(ResultQuery<R> query) {
      return this.fetch(query).getValues(0);
   }

   public <T> List<T> fetchValues(TableField<?, T> field) {
      return this.fetchValues((ResultQuery)this.select((SelectField)field).from(field.getTable()));
   }

   private final <T, R extends Record1<T>> T value1(R record) {
      if (record == null) {
         return null;
      } else if (record.size() != 1) {
         throw new InvalidResultException("Record contains more than one value : " + record);
      } else {
         return record.value1();
      }
   }

   public int fetchCount(Select<?> query) {
      return (Integer)((Record1)(new FetchCount(this.configuration(), query)).fetchOne()).value1();
   }

   public int fetchCount(Table<?> table) {
      return this.fetchCount(table, DSL.trueCondition());
   }

   public int fetchCount(Table<?> table, Condition condition) {
      return (Integer)this.selectCount().from(table).where(new Condition[]{condition}).fetchOne(0, Integer.TYPE);
   }

   public boolean fetchExists(Select<?> query) throws DataAccessException {
      return this.selectOne().whereExists(query).fetchOne() != null;
   }

   public boolean fetchExists(Table<?> table) throws DataAccessException {
      return this.fetchExists(table, DSL.trueCondition());
   }

   public boolean fetchExists(Table<?> table, Condition condition) throws DataAccessException {
      return this.fetchExists((Select)this.selectOne().from(table).where(new Condition[]{condition}));
   }

   public int execute(Query query) {
      Configuration previous = Tools.getConfiguration(query);

      int var3;
      try {
         query.attach(this.configuration());
         var3 = query.execute();
      } finally {
         query.attach(previous);
      }

      return var3;
   }

   public <R extends Record> Result<R> fetch(Table<R> table) {
      return this.fetch(table, DSL.trueCondition());
   }

   public <R extends Record> Result<R> fetch(Table<R> table, Condition condition) {
      return this.selectFrom(table).where(condition).fetch();
   }

   public <R extends Record> R fetchOne(Table<R> table) {
      return Tools.fetchOne(this.fetchLazy(table));
   }

   public <R extends Record> R fetchOne(Table<R> table, Condition condition) {
      return Tools.fetchOne(this.fetchLazy(table, condition));
   }

   public <R extends Record> Optional<R> fetchOptional(Table<R> table) {
      return Optional.ofNullable(this.fetchOne(table));
   }

   public <R extends Record> Optional<R> fetchOptional(Table<R> table, Condition condition) {
      return Optional.ofNullable(this.fetchOne(table, condition));
   }

   public <R extends Record> R fetchAny(Table<R> table) {
      return Tools.filterOne(this.selectFrom(table).limit(1).fetch());
   }

   public <R extends Record> R fetchAny(Table<R> table, Condition condition) {
      return Tools.filterOne(this.selectFrom(table).where(condition).limit(1).fetch());
   }

   public <R extends Record> Cursor<R> fetchLazy(Table<R> table) {
      return this.fetchLazy(table, DSL.trueCondition());
   }

   public <R extends Record> Cursor<R> fetchLazy(Table<R> table, Condition condition) {
      return this.selectFrom(table).where(condition).fetchLazy();
   }

   public <R extends Record> CompletionStage<Result<R>> fetchAsync(Table<R> table) {
      return this.selectFrom(table).fetchAsync();
   }

   public <R extends Record> CompletionStage<Result<R>> fetchAsync(Table<R> table, Condition condition) {
      return this.selectFrom(table).where(condition).fetchAsync();
   }

   public <R extends Record> CompletionStage<Result<R>> fetchAsync(Executor executor, Table<R> table) {
      return this.selectFrom(table).fetchAsync(executor);
   }

   public <R extends Record> CompletionStage<Result<R>> fetchAsync(Executor executor, Table<R> table, Condition condition) {
      return this.selectFrom(table).where(condition).fetchAsync(executor);
   }

   public <R extends Record> Stream<R> fetchStream(Table<R> table) {
      return this.fetchStream(table, DSL.trueCondition());
   }

   public <R extends Record> Stream<R> fetchStream(Table<R> table, Condition condition) {
      return this.selectFrom(table).where(condition).stream();
   }

   public <R extends TableRecord<R>> int executeInsert(R record) {
      InsertQuery<R> insert = this.insertQuery(record.getTable());
      insert.setRecord(record);
      return insert.execute();
   }

   public <R extends UpdatableRecord<R>> int executeUpdate(R record) {
      UpdateQuery<R> update = this.updateQuery(record.getTable());
      Tools.addConditions(update, record, record.getTable().getPrimaryKey().getFieldsArray());
      update.setRecord(record);
      return update.execute();
   }

   public <R extends TableRecord<R>, T> int executeUpdate(R record, Condition condition) {
      UpdateQuery<R> update = this.updateQuery(record.getTable());
      update.addConditions(condition);
      update.setRecord(record);
      return update.execute();
   }

   public <R extends UpdatableRecord<R>> int executeDelete(R record) {
      DeleteQuery<R> delete = this.deleteQuery(record.getTable());
      Tools.addConditions(delete, record, record.getTable().getPrimaryKey().getFieldsArray());
      return delete.execute();
   }

   public <R extends TableRecord<R>, T> int executeDelete(R record, Condition condition) {
      DeleteQuery<R> delete = this.deleteQuery(record.getTable());
      delete.addConditions(condition);
      return delete.execute();
   }

   public String toString() {
      return this.configuration().toString();
   }

   static {
      try {
         Class.forName(SQLDataType.class.getName());
      } catch (Exception var1) {
      }

   }
}
