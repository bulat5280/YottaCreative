package org.jooq;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import org.jooq.exception.ConfigurationException;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.InvalidResultException;
import org.jooq.exception.TooManyRowsException;
import org.jooq.tools.jdbc.MockCallable;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockRunnable;
import org.jooq.util.xml.jaxb.InformationSchema;

public interface DSLContext extends Scope, AutoCloseable {
   void close() throws DataAccessException;

   Schema map(Schema var1);

   <R extends Record> Table<R> map(Table<R> var1);

   /** @deprecated */
   @Deprecated
   Parser parser();

   Meta meta();

   Meta meta(InformationSchema var1);

   InformationSchema informationSchema(Catalog var1);

   InformationSchema informationSchema(Catalog... var1);

   InformationSchema informationSchema(Schema var1);

   InformationSchema informationSchema(Schema... var1);

   InformationSchema informationSchema(Table<?> var1);

   InformationSchema informationSchema(Table<?>... var1);

   <T> T transactionResult(TransactionalCallable<T> var1);

   <T> T transactionResult(ContextTransactionalCallable<T> var1) throws ConfigurationException;

   void transaction(TransactionalRunnable var1);

   void transaction(ContextTransactionalRunnable var1) throws ConfigurationException;

   <T> CompletionStage<T> transactionResultAsync(TransactionalCallable<T> var1) throws ConfigurationException;

   CompletionStage<Void> transactionAsync(TransactionalRunnable var1) throws ConfigurationException;

   <T> CompletionStage<T> transactionResultAsync(Executor var1, TransactionalCallable<T> var2) throws ConfigurationException;

   CompletionStage<Void> transactionAsync(Executor var1, TransactionalRunnable var2) throws ConfigurationException;

   <T> T connectionResult(ConnectionCallable<T> var1);

   void connection(ConnectionRunnable var1);

   <T> T mockResult(MockDataProvider var1, MockCallable<T> var2);

   void mock(MockDataProvider var1, MockRunnable var2);

   RenderContext renderContext();

   String render(QueryPart var1);

   String renderNamedParams(QueryPart var1);

   String renderNamedOrInlinedParams(QueryPart var1);

   String renderInlined(QueryPart var1);

   List<Object> extractBindValues(QueryPart var1);

   Map<String, Param<?>> extractParams(QueryPart var1);

   Param<?> extractParam(QueryPart var1, String var2);

   BindContext bindContext(PreparedStatement var1);

   /** @deprecated */
   @Deprecated
   int bind(QueryPart var1, PreparedStatement var2);

   void attach(Attachable... var1);

   void attach(Collection<? extends Attachable> var1);

   @Support
   <R extends Record> LoaderOptionsStep<R> loadInto(Table<R> var1);

   @Support
   @PlainSQL
   Query query(SQL var1);

   @Support
   @PlainSQL
   Query query(String var1);

   @Support
   @PlainSQL
   Query query(String var1, Object... var2);

   @Support
   @PlainSQL
   Query query(String var1, QueryPart... var2);

   @Support
   @PlainSQL
   Result<Record> fetch(SQL var1) throws DataAccessException;

   @Support
   @PlainSQL
   Result<Record> fetch(String var1) throws DataAccessException;

   @Support
   @PlainSQL
   Result<Record> fetch(String var1, Object... var2) throws DataAccessException;

   @Support
   @PlainSQL
   Result<Record> fetch(String var1, QueryPart... var2) throws DataAccessException;

   @Support
   @PlainSQL
   Cursor<Record> fetchLazy(SQL var1) throws DataAccessException;

   @Support
   @PlainSQL
   Cursor<Record> fetchLazy(String var1) throws DataAccessException;

   @Support
   @PlainSQL
   Cursor<Record> fetchLazy(String var1, Object... var2) throws DataAccessException;

   @Support
   @PlainSQL
   Cursor<Record> fetchLazy(String var1, QueryPart... var2) throws DataAccessException;

   @Support
   @PlainSQL
   CompletionStage<Result<Record>> fetchAsync(SQL var1);

   @Support
   @PlainSQL
   CompletionStage<Result<Record>> fetchAsync(String var1);

   @Support
   @PlainSQL
   CompletionStage<Result<Record>> fetchAsync(String var1, Object... var2);

   @Support
   @PlainSQL
   CompletionStage<Result<Record>> fetchAsync(String var1, QueryPart... var2);

   @Support
   @PlainSQL
   CompletionStage<Result<Record>> fetchAsync(Executor var1, SQL var2);

   @Support
   @PlainSQL
   CompletionStage<Result<Record>> fetchAsync(Executor var1, String var2);

   @Support
   @PlainSQL
   CompletionStage<Result<Record>> fetchAsync(Executor var1, String var2, Object... var3);

   @Support
   @PlainSQL
   CompletionStage<Result<Record>> fetchAsync(Executor var1, String var2, QueryPart... var3);

   @Support
   @PlainSQL
   Stream<Record> fetchStream(SQL var1) throws DataAccessException;

   @Support
   @PlainSQL
   Stream<Record> fetchStream(String var1) throws DataAccessException;

   @Support
   @PlainSQL
   Stream<Record> fetchStream(String var1, Object... var2) throws DataAccessException;

   @Support
   @PlainSQL
   Stream<Record> fetchStream(String var1, QueryPart... var2) throws DataAccessException;

   @Support
   @PlainSQL
   Results fetchMany(SQL var1) throws DataAccessException;

   @Support
   @PlainSQL
   Results fetchMany(String var1) throws DataAccessException;

   @Support
   @PlainSQL
   Results fetchMany(String var1, Object... var2) throws DataAccessException;

   @Support
   @PlainSQL
   Results fetchMany(String var1, QueryPart... var2) throws DataAccessException;

   @Support
   @PlainSQL
   Record fetchOne(SQL var1) throws DataAccessException, TooManyRowsException;

   @Support
   @PlainSQL
   Record fetchOne(String var1) throws DataAccessException, TooManyRowsException;

   @Support
   @PlainSQL
   Record fetchOne(String var1, Object... var2) throws DataAccessException, TooManyRowsException;

   @Support
   @PlainSQL
   Record fetchOne(String var1, QueryPart... var2) throws DataAccessException, TooManyRowsException;

   @Support
   @PlainSQL
   Optional<Record> fetchOptional(SQL var1) throws DataAccessException, TooManyRowsException;

   @Support
   @PlainSQL
   Optional<Record> fetchOptional(String var1) throws DataAccessException, TooManyRowsException;

   @Support
   @PlainSQL
   Optional<Record> fetchOptional(String var1, Object... var2) throws DataAccessException, TooManyRowsException;

   @Support
   @PlainSQL
   Optional<Record> fetchOptional(String var1, QueryPart... var2) throws DataAccessException, TooManyRowsException;

   @Support
   @PlainSQL
   Object fetchValue(SQL var1) throws DataAccessException, TooManyRowsException, InvalidResultException;

   @Support
   @PlainSQL
   Object fetchValue(String var1) throws DataAccessException, TooManyRowsException, InvalidResultException;

   @Support
   @PlainSQL
   Object fetchValue(String var1, Object... var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

   @Support
   @PlainSQL
   Object fetchValue(String var1, QueryPart... var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

   @Support
   @PlainSQL
   Optional<?> fetchOptionalValue(SQL var1) throws DataAccessException, TooManyRowsException, InvalidResultException;

   @Support
   @PlainSQL
   Optional<?> fetchOptionalValue(String var1) throws DataAccessException, TooManyRowsException, InvalidResultException;

   @Support
   @PlainSQL
   Optional<?> fetchOptionalValue(String var1, Object... var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

   @Support
   @PlainSQL
   Optional<?> fetchOptionalValue(String var1, QueryPart... var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

   @Support
   @PlainSQL
   List<?> fetchValues(SQL var1) throws DataAccessException;

   @Support
   @PlainSQL
   List<?> fetchValues(String var1) throws DataAccessException;

   @Support
   @PlainSQL
   List<?> fetchValues(String var1, Object... var2) throws DataAccessException;

   @Support
   @PlainSQL
   List<?> fetchValues(String var1, QueryPart... var2) throws DataAccessException;

   @Support
   @PlainSQL
   int execute(SQL var1) throws DataAccessException;

   @Support
   @PlainSQL
   int execute(String var1) throws DataAccessException;

   @Support
   @PlainSQL
   int execute(String var1, Object... var2) throws DataAccessException;

   @Support
   @PlainSQL
   int execute(String var1, QueryPart... var2) throws DataAccessException;

   @Support
   @PlainSQL
   ResultQuery<Record> resultQuery(SQL var1);

   @Support
   @PlainSQL
   ResultQuery<Record> resultQuery(String var1);

   @Support
   @PlainSQL
   ResultQuery<Record> resultQuery(String var1, Object... var2);

   @Support
   @PlainSQL
   ResultQuery<Record> resultQuery(String var1, QueryPart... var2);

   @Support
   Result<Record> fetch(ResultSet var1) throws DataAccessException;

   @Support
   Result<Record> fetch(ResultSet var1, Field<?>... var2) throws DataAccessException;

   @Support
   Result<Record> fetch(ResultSet var1, DataType<?>... var2) throws DataAccessException;

   @Support
   Result<Record> fetch(ResultSet var1, Class<?>... var2) throws DataAccessException;

   @Support
   Record fetchOne(ResultSet var1) throws DataAccessException, TooManyRowsException;

   @Support
   Record fetchOne(ResultSet var1, Field<?>... var2) throws DataAccessException, TooManyRowsException;

   @Support
   Record fetchOne(ResultSet var1, DataType<?>... var2) throws DataAccessException, TooManyRowsException;

   @Support
   Record fetchOne(ResultSet var1, Class<?>... var2) throws DataAccessException, TooManyRowsException;

   @Support
   Optional<Record> fetchOptional(ResultSet var1) throws DataAccessException, TooManyRowsException;

   @Support
   Optional<Record> fetchOptional(ResultSet var1, Field<?>... var2) throws DataAccessException, TooManyRowsException;

   @Support
   Optional<Record> fetchOptional(ResultSet var1, DataType<?>... var2) throws DataAccessException, TooManyRowsException;

   @Support
   Optional<Record> fetchOptional(ResultSet var1, Class<?>... var2) throws DataAccessException, TooManyRowsException;

   @Support
   Object fetchValue(ResultSet var1) throws DataAccessException, TooManyRowsException, InvalidResultException;

   @Support
   <T> T fetchValue(ResultSet var1, Field<T> var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

   @Support
   <T> T fetchValue(ResultSet var1, DataType<T> var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

   @Support
   <T> T fetchValue(ResultSet var1, Class<T> var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

   @Support
   Optional<?> fetchOptionalValue(ResultSet var1) throws DataAccessException, TooManyRowsException, InvalidResultException;

   @Support
   <T> Optional<T> fetchOptionalValue(ResultSet var1, Field<T> var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

   @Support
   <T> Optional<T> fetchOptionalValue(ResultSet var1, DataType<T> var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

   @Support
   <T> Optional<T> fetchOptionalValue(ResultSet var1, Class<T> var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

   @Support
   List<?> fetchValues(ResultSet var1) throws DataAccessException;

   @Support
   <T> List<T> fetchValues(ResultSet var1, Field<T> var2) throws DataAccessException;

   @Support
   <T> List<T> fetchValues(ResultSet var1, DataType<T> var2) throws DataAccessException;

   @Support
   <T> List<T> fetchValues(ResultSet var1, Class<T> var2) throws DataAccessException;

   @Support
   Cursor<Record> fetchLazy(ResultSet var1) throws DataAccessException;

   @Support
   Cursor<Record> fetchLazy(ResultSet var1, Field<?>... var2) throws DataAccessException;

   @Support
   Cursor<Record> fetchLazy(ResultSet var1, DataType<?>... var2) throws DataAccessException;

   @Support
   Cursor<Record> fetchLazy(ResultSet var1, Class<?>... var2) throws DataAccessException;

   @Support
   CompletionStage<Result<Record>> fetchAsync(ResultSet var1);

   @Support
   CompletionStage<Result<Record>> fetchAsync(ResultSet var1, Field<?>... var2);

   @Support
   CompletionStage<Result<Record>> fetchAsync(ResultSet var1, DataType<?>... var2);

   @Support
   CompletionStage<Result<Record>> fetchAsync(ResultSet var1, Class<?>... var2);

   @Support
   CompletionStage<Result<Record>> fetchAsync(Executor var1, ResultSet var2);

   @Support
   CompletionStage<Result<Record>> fetchAsync(Executor var1, ResultSet var2, Field<?>... var3);

   @Support
   CompletionStage<Result<Record>> fetchAsync(Executor var1, ResultSet var2, DataType<?>... var3);

   @Support
   CompletionStage<Result<Record>> fetchAsync(Executor var1, ResultSet var2, Class<?>... var3);

   @Support
   Stream<Record> fetchStream(ResultSet var1) throws DataAccessException;

   @Support
   Stream<Record> fetchStream(ResultSet var1, Field<?>... var2) throws DataAccessException;

   @Support
   Stream<Record> fetchStream(ResultSet var1, DataType<?>... var2) throws DataAccessException;

   @Support
   Stream<Record> fetchStream(ResultSet var1, Class<?>... var2) throws DataAccessException;

   @Support
   Result<Record> fetchFromTXT(String var1) throws DataAccessException;

   @Support
   Result<Record> fetchFromTXT(String var1, String var2) throws DataAccessException;

   @Support
   Result<Record> fetchFromHTML(String var1) throws DataAccessException;

   @Support
   Result<Record> fetchFromCSV(String var1) throws DataAccessException;

   @Support
   Result<Record> fetchFromCSV(String var1, char var2) throws DataAccessException;

   @Support
   Result<Record> fetchFromCSV(String var1, boolean var2) throws DataAccessException;

   @Support
   Result<Record> fetchFromCSV(String var1, boolean var2, char var3) throws DataAccessException;

   @Support
   Result<Record> fetchFromJSON(String var1);

   Result<Record> fetchFromStringData(String[]... var1);

   Result<Record> fetchFromStringData(List<String[]> var1);

   Result<Record> fetchFromStringData(List<String[]> var1, boolean var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep with(String var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep with(String var1, String... var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep with(String var1, Function<? super Field<?>, ? extends String> var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep with(String var1, BiFunction<? super Field<?>, ? super Integer, ? extends String> var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep1 with(String var1, String var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep2 with(String var1, String var2, String var3);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep3 with(String var1, String var2, String var3, String var4);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep4 with(String var1, String var2, String var3, String var4, String var5);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep5 with(String var1, String var2, String var3, String var4, String var5, String var6);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep6 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep7 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep8 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep9 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep10 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep11 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep12 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep13 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep14 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep15 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep16 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep17 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep18 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep19 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep20 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20, String var21);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep21 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20, String var21, String var22);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep22 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20, String var21, String var22, String var23);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithStep with(CommonTableExpression<?>... var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep withRecursive(String var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep withRecursive(String var1, String... var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep withRecursive(String var1, Function<? super Field<?>, ? extends String> var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep withRecursive(String var1, BiFunction<? super Field<?>, ? super Integer, ? extends String> var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep1 withRecursive(String var1, String var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep2 withRecursive(String var1, String var2, String var3);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep3 withRecursive(String var1, String var2, String var3, String var4);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep4 withRecursive(String var1, String var2, String var3, String var4, String var5);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep5 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep6 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep7 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep8 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep9 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep10 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep11 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep12 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep13 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep14 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep15 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep16 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep17 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep18 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep19 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep20 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20, String var21);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep21 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20, String var21, String var22);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithAsStep22 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20, String var21, String var22, String var23);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   WithStep withRecursive(CommonTableExpression<?>... var1);

   @Support
   <R extends Record> SelectWhereStep<R> selectFrom(Table<R> var1);

   @Support
   SelectSelectStep<Record> select(Collection<? extends SelectField<?>> var1);

   @Support
   SelectSelectStep<Record> select(SelectField<?>... var1);

   @Support
   <T1> SelectSelectStep<Record1<T1>> select(SelectField<T1> var1);

   @Support
   <T1, T2> SelectSelectStep<Record2<T1, T2>> select(SelectField<T1> var1, SelectField<T2> var2);

   @Support
   <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3);

   @Support
   <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4);

   @Support
   <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5);

   @Support
   <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6);

   @Support
   <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20, SelectField<T21> var21);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20, SelectField<T21> var21, SelectField<T22> var22);

   @Support
   SelectSelectStep<Record> selectDistinct(Collection<? extends SelectField<?>> var1);

   @Support
   SelectSelectStep<Record> selectDistinct(SelectField<?>... var1);

   @Support
   <T1> SelectSelectStep<Record1<T1>> selectDistinct(SelectField<T1> var1);

   @Support
   <T1, T2> SelectSelectStep<Record2<T1, T2>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2);

   @Support
   <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3);

   @Support
   <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4);

   @Support
   <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5);

   @Support
   <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6);

   @Support
   <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20, SelectField<T21> var21);

   @Support
   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20, SelectField<T21> var21, SelectField<T22> var22);

   @Support
   SelectSelectStep<Record1<Integer>> selectZero();

   @Support
   SelectSelectStep<Record1<Integer>> selectOne();

   @Support
   SelectSelectStep<Record1<Integer>> selectCount();

   @Support
   SelectQuery<Record> selectQuery();

   @Support
   <R extends Record> SelectQuery<R> selectQuery(TableLike<R> var1);

   @Support
   <R extends Record> InsertQuery<R> insertQuery(Table<R> var1);

   @Support
   <R extends Record> InsertSetStep<R> insertInto(Table<R> var1);

   @Support
   <R extends Record, T1> InsertValuesStep1<R, T1> insertInto(Table<R> var1, Field<T1> var2);

   @Support
   <R extends Record, T1, T2> InsertValuesStep2<R, T1, T2> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3);

   @Support
   <R extends Record, T1, T2, T3> InsertValuesStep3<R, T1, T2, T3> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4);

   @Support
   <R extends Record, T1, T2, T3, T4> InsertValuesStep4<R, T1, T2, T3, T4> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5);

   @Support
   <R extends Record, T1, T2, T3, T4, T5> InsertValuesStep5<R, T1, T2, T3, T4, T5> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6);

   @Support
   <R extends Record, T1, T2, T3, T4, T5, T6> InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7);

   @Support
   <R extends Record, T1, T2, T3, T4, T5, T6, T7> InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8);

   @Support
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9);

   @Support
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> InsertValuesStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10);

   @Support
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> InsertValuesStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11);

   @Support
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> InsertValuesStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12);

   @Support
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> InsertValuesStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13);

   @Support
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14);

   @Support
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> InsertValuesStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15);

   @Support
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> InsertValuesStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16);

   @Support
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> InsertValuesStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17);

   @Support
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> InsertValuesStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18);

   @Support
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> InsertValuesStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19);

   @Support
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> InsertValuesStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20);

   @Support
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> InsertValuesStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20, Field<T20> var21);

   @Support
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> InsertValuesStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20, Field<T20> var21, Field<T21> var22);

   @Support
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> InsertValuesStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20, Field<T20> var21, Field<T21> var22, Field<T22> var23);

   @Support
   <R extends Record> InsertValuesStepN<R> insertInto(Table<R> var1, Field<?>... var2);

   @Support
   <R extends Record> InsertValuesStepN<R> insertInto(Table<R> var1, Collection<? extends Field<?>> var2);

   @Support
   <R extends Record> UpdateQuery<R> updateQuery(Table<R> var1);

   @Support
   <R extends Record> UpdateSetFirstStep<R> update(Table<R> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL})
   <R extends Record> MergeUsingStep<R> mergeInto(Table<R> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1> MergeKeyStep1<R, T1> mergeInto(Table<R> var1, Field<T1> var2);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2> MergeKeyStep2<R, T1, T2> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3> MergeKeyStep3<R, T1, T2, T3> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3, T4> MergeKeyStep4<R, T1, T2, T3, T4> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3, T4, T5> MergeKeyStep5<R, T1, T2, T3, T4, T5> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3, T4, T5, T6> MergeKeyStep6<R, T1, T2, T3, T4, T5, T6> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3, T4, T5, T6, T7> MergeKeyStep7<R, T1, T2, T3, T4, T5, T6, T7> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> MergeKeyStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> MergeKeyStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> MergeKeyStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> MergeKeyStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> MergeKeyStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> MergeKeyStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> MergeKeyStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> MergeKeyStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> MergeKeyStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> MergeKeyStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> MergeKeyStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> MergeKeyStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> MergeKeyStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20, Field<T20> var21);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> MergeKeyStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20, Field<T20> var21, Field<T21> var22);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES_9_5})
   <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> MergeKeyStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20, Field<T20> var21, Field<T21> var22, Field<T22> var23);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB})
   <R extends Record> MergeKeyStepN<R> mergeInto(Table<R> var1, Field<?>... var2);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HSQLDB})
   <R extends Record> MergeKeyStepN<R> mergeInto(Table<R> var1, Collection<? extends Field<?>> var2);

   @Support
   <R extends Record> DeleteQuery<R> deleteQuery(Table<R> var1);

   @Support
   <R extends Record> DeleteWhereStep<R> deleteFrom(Table<R> var1);

   @Support
   <R extends Record> DeleteWhereStep<R> delete(Table<R> var1);

   @Support
   Batch batch(Query... var1);

   @Support
   Batch batch(Queries var1);

   @Support
   Batch batch(String... var1);

   @Support
   Batch batch(Collection<? extends Query> var1);

   @Support
   BatchBindStep batch(Query var1);

   @Support
   BatchBindStep batch(String var1);

   @Support
   Batch batch(Query var1, Object[]... var2);

   @Support
   Batch batch(String var1, Object[]... var2);

   @Support
   Batch batchStore(UpdatableRecord<?>... var1);

   @Support
   Batch batchStore(Collection<? extends UpdatableRecord<?>> var1);

   @Support
   Batch batchInsert(TableRecord<?>... var1);

   @Support
   Batch batchInsert(Collection<? extends TableRecord<?>> var1);

   @Support
   Batch batchUpdate(UpdatableRecord<?>... var1);

   @Support
   Batch batchUpdate(Collection<? extends UpdatableRecord<?>> var1);

   @Support
   Batch batchDelete(UpdatableRecord<?>... var1);

   @Support
   Batch batchDelete(Collection<? extends UpdatableRecord<?>> var1);

   Queries ddl(Catalog var1);

   Queries ddl(Catalog var1, DDLFlag... var2);

   Queries ddl(Schema var1);

   Queries ddl(Schema var1, DDLFlag... var2);

   Queries ddl(Table<?> var1);

   Queries ddl(Table<?> var1, DDLFlag... var2);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   CreateSchemaFinalStep createSchema(String var1);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   CreateSchemaFinalStep createSchema(Name var1);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   CreateSchemaFinalStep createSchema(Schema var1);

   @Support({SQLDialect.H2, SQLDialect.POSTGRES})
   CreateSchemaFinalStep createSchemaIfNotExists(String var1);

   @Support({SQLDialect.H2, SQLDialect.POSTGRES})
   CreateSchemaFinalStep createSchemaIfNotExists(Name var1);

   @Support({SQLDialect.H2, SQLDialect.POSTGRES})
   CreateSchemaFinalStep createSchemaIfNotExists(Schema var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateTableAsStep<Record> createTable(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateTableAsStep<Record> createTable(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateTableAsStep<Record> createTable(Table<?> var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateTableAsStep<Record> createTableIfNotExists(String var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateTableAsStep<Record> createTableIfNotExists(Name var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateTableAsStep<Record> createTableIfNotExists(Table<?> var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   CreateTableAsStep<Record> createTemporaryTable(String var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   CreateTableAsStep<Record> createTemporaryTable(Name var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   CreateTableAsStep<Record> createTemporaryTable(Table<?> var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   CreateTableAsStep<Record> createGlobalTemporaryTable(String var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   CreateTableAsStep<Record> createGlobalTemporaryTable(Name var1);

   @Support({SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
   CreateTableAsStep<Record> createGlobalTemporaryTable(Table<?> var1);

   @Support
   CreateViewAsStep<Record> createView(String var1, String... var2);

   @Support
   CreateViewAsStep<Record> createView(Name var1, Name... var2);

   @Support
   CreateViewAsStep<Record> createView(Table<?> var1, Field<?>... var2);

   @Support
   CreateViewAsStep<Record> createView(String var1, Function<? super Field<?>, ? extends String> var2);

   @Support
   CreateViewAsStep<Record> createView(String var1, BiFunction<? super Field<?>, ? super Integer, ? extends String> var2);

   @Support
   CreateViewAsStep<Record> createView(Name var1, Function<? super Field<?>, ? extends Name> var2);

   @Support
   CreateViewAsStep<Record> createView(Name var1, BiFunction<? super Field<?>, ? super Integer, ? extends Name> var2);

   @Support
   CreateViewAsStep<Record> createView(Table<?> var1, Function<? super Field<?>, ? extends Field<?>> var2);

   @Support
   CreateViewAsStep<Record> createView(Table<?> var1, BiFunction<? super Field<?>, ? super Integer, ? extends Field<?>> var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateViewAsStep<Record> createViewIfNotExists(String var1, String... var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateViewAsStep<Record> createViewIfNotExists(Name var1, Name... var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateViewAsStep<Record> createViewIfNotExists(Table<?> var1, Field<?>... var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateViewAsStep<Record> createViewIfNotExists(String var1, Function<? super Field<?>, ? extends String> var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateViewAsStep<Record> createViewIfNotExists(String var1, BiFunction<? super Field<?>, ? super Integer, ? extends String> var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateViewAsStep<Record> createViewIfNotExists(Name var1, Function<? super Field<?>, ? extends Name> var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateViewAsStep<Record> createViewIfNotExists(Name var1, BiFunction<? super Field<?>, ? super Integer, ? extends Name> var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateViewAsStep<Record> createViewIfNotExists(Table<?> var1, Function<? super Field<?>, ? extends Field<?>> var2);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateViewAsStep<Record> createViewIfNotExists(Table<?> var1, BiFunction<? super Field<?>, ? super Integer, ? extends Field<?>> var2);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateIndexStep createIndex(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateIndexStep createIndex(Name var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateIndexStep createIndexIfNotExists(String var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateIndexStep createIndexIfNotExists(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateIndexStep createUniqueIndex(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateIndexStep createUniqueIndex(Name var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateIndexStep createUniqueIndexIfNotExists(String var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   CreateIndexStep createUniqueIndexIfNotExists(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   CreateSequenceFinalStep createSequence(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   CreateSequenceFinalStep createSequence(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   CreateSequenceFinalStep createSequence(Sequence<?> var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   CreateSequenceFinalStep createSequenceIfNotExists(String var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   CreateSequenceFinalStep createSequenceIfNotExists(Name var1);

   @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   CreateSequenceFinalStep createSequenceIfNotExists(Sequence<?> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterSequenceStep<BigInteger> alterSequence(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterSequenceStep<BigInteger> alterSequence(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T extends Number> AlterSequenceStep<T> alterSequence(Sequence<T> var1);

   @Support({SQLDialect.POSTGRES})
   AlterSequenceStep<BigInteger> alterSequenceIfExists(String var1);

   @Support({SQLDialect.POSTGRES})
   AlterSequenceStep<BigInteger> alterSequenceIfExists(Name var1);

   @Support({SQLDialect.POSTGRES})
   <T extends Number> AlterSequenceStep<T> alterSequenceIfExists(Sequence<T> var1);

   @Support
   AlterTableStep alterTable(String var1);

   @Support
   AlterTableStep alterTable(Name var1);

   @Support
   AlterTableStep alterTable(Table<?> var1);

   @Support({SQLDialect.H2, SQLDialect.POSTGRES})
   AlterTableStep alterTableIfExists(String var1);

   @Support({SQLDialect.H2, SQLDialect.POSTGRES})
   AlterTableStep alterTableIfExists(Name var1);

   @Support({SQLDialect.H2, SQLDialect.POSTGRES})
   AlterTableStep alterTableIfExists(Table<?> var1);

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterSchemaStep alterSchema(String var1);

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterSchemaStep alterSchema(Name var1);

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterSchemaStep alterSchema(Schema var1);

   @Support({SQLDialect.POSTGRES})
   AlterSchemaStep alterSchemaIfExists(String var1);

   @Support({SQLDialect.POSTGRES})
   AlterSchemaStep alterSchemaIfExists(Name var1);

   @Support({SQLDialect.POSTGRES})
   AlterSchemaStep alterSchemaIfExists(Schema var1);

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterViewStep alterView(String var1);

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterViewStep alterView(Name var1);

   @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterViewStep alterView(Table<?> var1);

   @Support({SQLDialect.POSTGRES})
   AlterViewStep alterViewIfExists(String var1);

   @Support({SQLDialect.POSTGRES})
   AlterViewStep alterViewIfExists(Name var1);

   @Support({SQLDialect.POSTGRES})
   AlterViewStep alterViewIfExists(Table<?> var1);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterIndexStep alterIndex(String var1);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   AlterIndexStep alterIndex(Name var1);

   @Support({SQLDialect.H2, SQLDialect.POSTGRES})
   AlterIndexStep alterIndexIfExists(String var1);

   @Support({SQLDialect.H2, SQLDialect.POSTGRES})
   AlterIndexStep alterIndexIfExists(Name var1);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   DropSchemaStep dropSchema(String var1);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   DropSchemaStep dropSchema(Name var1);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   DropSchemaStep dropSchema(Schema var1);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   DropSchemaStep dropSchemaIfExists(String var1);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   DropSchemaStep dropSchemaIfExists(Name var1);

   @Support({SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   DropSchemaStep dropSchemaIfExists(Schema var1);

   @Support
   DropViewFinalStep dropView(String var1);

   @Support
   DropViewFinalStep dropView(Name var1);

   @Support
   DropViewFinalStep dropView(Table<?> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   DropViewFinalStep dropViewIfExists(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   DropViewFinalStep dropViewIfExists(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   DropViewFinalStep dropViewIfExists(Table<?> var1);

   @Support
   DropTableStep dropTable(String var1);

   @Support
   DropTableStep dropTable(Name var1);

   @Support
   DropTableStep dropTable(Table<?> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   DropTableStep dropTableIfExists(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   DropTableStep dropTableIfExists(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   DropTableStep dropTableIfExists(Table<?> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   DropIndexOnStep dropIndex(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   DropIndexOnStep dropIndex(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   DropIndexOnStep dropIndexIfExists(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   DropIndexOnStep dropIndexIfExists(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   DropSequenceFinalStep dropSequence(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   DropSequenceFinalStep dropSequence(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   DropSequenceFinalStep dropSequence(Sequence<?> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   DropSequenceFinalStep dropSequenceIfExists(String var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   DropSequenceFinalStep dropSequenceIfExists(Name var1);

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   DropSequenceFinalStep dropSequenceIfExists(Sequence<?> var1);

   @Support
   TruncateIdentityStep<Record> truncate(String var1);

   @Support
   TruncateIdentityStep<Record> truncate(Name var1);

   @Support
   <R extends Record> TruncateIdentityStep<R> truncate(Table<R> var1);

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
   BigInteger lastID() throws DataAccessException;

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   BigInteger nextval(String var1) throws DataAccessException;

   @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
   <T extends Number> T nextval(Sequence<T> var1) throws DataAccessException;

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.POSTGRES})
   BigInteger currval(String var1) throws DataAccessException;

   @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.POSTGRES})
   <T extends Number> T currval(Sequence<T> var1) throws DataAccessException;

   <R extends UDTRecord<R>> R newRecord(UDT<R> var1);

   <R extends Record> R newRecord(Table<R> var1);

   <R extends Record> R newRecord(Table<R> var1, Object var2);

   Record newRecord(Field<?>... var1);

   <T1> Record1<T1> newRecord(Field<T1> var1);

   <T1, T2> Record2<T1, T2> newRecord(Field<T1> var1, Field<T2> var2);

   <T1, T2, T3> Record3<T1, T2, T3> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   <T1, T2, T3, T4> Record4<T1, T2, T3, T4> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   <T1, T2, T3, T4, T5> Record5<T1, T2, T3, T4, T5> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   <T1, T2, T3, T4, T5, T6> Record6<T1, T2, T3, T4, T5, T6> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   <T1, T2, T3, T4, T5, T6, T7> Record7<T1, T2, T3, T4, T5, T6, T7> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   <T1, T2, T3, T4, T5, T6, T7, T8> Record8<T1, T2, T3, T4, T5, T6, T7, T8> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9> Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21, Field<T22> var22);

   <R extends Record> Result<R> newResult(Table<R> var1);

   Result<Record> newResult(Field<?>... var1);

   <T1> Result<Record1<T1>> newResult(Field<T1> var1);

   <T1, T2> Result<Record2<T1, T2>> newResult(Field<T1> var1, Field<T2> var2);

   <T1, T2, T3> Result<Record3<T1, T2, T3>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3);

   <T1, T2, T3, T4> Result<Record4<T1, T2, T3, T4>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

   <T1, T2, T3, T4, T5> Result<Record5<T1, T2, T3, T4, T5>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

   <T1, T2, T3, T4, T5, T6> Result<Record6<T1, T2, T3, T4, T5, T6>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

   <T1, T2, T3, T4, T5, T6, T7> Result<Record7<T1, T2, T3, T4, T5, T6, T7>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

   <T1, T2, T3, T4, T5, T6, T7, T8> Result<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9> Result<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Result<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Result<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Result<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Result<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Result<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Result<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Result<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Result<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Result<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Result<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Result<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Result<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21);

   <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Result<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21, Field<T22> var22);

   <R extends Record> Result<R> fetch(ResultQuery<R> var1) throws DataAccessException;

   <R extends Record> Cursor<R> fetchLazy(ResultQuery<R> var1) throws DataAccessException;

   <R extends Record> CompletionStage<Result<R>> fetchAsync(ResultQuery<R> var1);

   <R extends Record> CompletionStage<Result<R>> fetchAsync(Executor var1, ResultQuery<R> var2);

   <R extends Record> Stream<R> fetchStream(ResultQuery<R> var1) throws DataAccessException;

   <R extends Record> Results fetchMany(ResultQuery<R> var1) throws DataAccessException;

   <R extends Record> R fetchOne(ResultQuery<R> var1) throws DataAccessException, TooManyRowsException;

   <R extends Record> Optional<R> fetchOptional(ResultQuery<R> var1) throws DataAccessException, TooManyRowsException;

   <T, R extends Record1<T>> T fetchValue(ResultQuery<R> var1) throws DataAccessException, TooManyRowsException, InvalidResultException;

   <T> T fetchValue(TableField<?, T> var1) throws DataAccessException, TooManyRowsException, InvalidResultException;

   <T, R extends Record1<T>> Optional<T> fetchOptionalValue(ResultQuery<R> var1) throws DataAccessException, TooManyRowsException, InvalidResultException;

   <T> Optional<T> fetchOptionalValue(TableField<?, T> var1) throws DataAccessException, TooManyRowsException, InvalidResultException;

   <T, R extends Record1<T>> List<T> fetchValues(ResultQuery<R> var1) throws DataAccessException;

   <T> List<T> fetchValues(TableField<?, T> var1) throws DataAccessException;

   int fetchCount(Select<?> var1) throws DataAccessException;

   int fetchCount(Table<?> var1) throws DataAccessException;

   int fetchCount(Table<?> var1, Condition var2) throws DataAccessException;

   boolean fetchExists(Select<?> var1) throws DataAccessException;

   boolean fetchExists(Table<?> var1) throws DataAccessException;

   boolean fetchExists(Table<?> var1, Condition var2) throws DataAccessException;

   int execute(Query var1) throws DataAccessException;

   @Support
   <R extends Record> Result<R> fetch(Table<R> var1) throws DataAccessException;

   @Support
   <R extends Record> Result<R> fetch(Table<R> var1, Condition var2) throws DataAccessException;

   @Support
   <R extends Record> R fetchOne(Table<R> var1) throws DataAccessException, TooManyRowsException;

   @Support
   <R extends Record> R fetchOne(Table<R> var1, Condition var2) throws DataAccessException, TooManyRowsException;

   @Support
   <R extends Record> Optional<R> fetchOptional(Table<R> var1) throws DataAccessException, TooManyRowsException;

   @Support
   <R extends Record> Optional<R> fetchOptional(Table<R> var1, Condition var2) throws DataAccessException, TooManyRowsException;

   @Support
   <R extends Record> R fetchAny(Table<R> var1) throws DataAccessException;

   @Support
   <R extends Record> R fetchAny(Table<R> var1, Condition var2) throws DataAccessException;

   @Support
   <R extends Record> Cursor<R> fetchLazy(Table<R> var1) throws DataAccessException;

   @Support
   <R extends Record> Cursor<R> fetchLazy(Table<R> var1, Condition var2) throws DataAccessException;

   @Support
   <R extends Record> CompletionStage<Result<R>> fetchAsync(Table<R> var1);

   @Support
   <R extends Record> CompletionStage<Result<R>> fetchAsync(Table<R> var1, Condition var2);

   @Support
   <R extends Record> CompletionStage<Result<R>> fetchAsync(Executor var1, Table<R> var2);

   @Support
   <R extends Record> CompletionStage<Result<R>> fetchAsync(Executor var1, Table<R> var2, Condition var3);

   @Support
   <R extends Record> Stream<R> fetchStream(Table<R> var1) throws DataAccessException;

   @Support
   <R extends Record> Stream<R> fetchStream(Table<R> var1, Condition var2) throws DataAccessException;

   @Support
   <R extends TableRecord<R>> int executeInsert(R var1) throws DataAccessException;

   @Support
   <R extends UpdatableRecord<R>> int executeUpdate(R var1) throws DataAccessException;

   @Support
   <R extends TableRecord<R>, T> int executeUpdate(R var1, Condition var2) throws DataAccessException;

   @Support
   <R extends UpdatableRecord<R>> int executeDelete(R var1) throws DataAccessException;

   @Support
   <R extends TableRecord<R>, T> int executeDelete(R var1, Condition var2) throws DataAccessException;
}
