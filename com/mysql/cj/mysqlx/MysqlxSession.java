package com.mysql.cj.mysqlx;

import com.mysql.cj.api.ProfilerEventHandler;
import com.mysql.cj.api.Session;
import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.io.Protocol;
import com.mysql.cj.api.io.ServerSession;
import com.mysql.cj.api.log.Log;
import com.mysql.cj.api.result.Row;
import com.mysql.cj.api.result.RowList;
import com.mysql.cj.api.x.DataStatement;
import com.mysql.cj.api.x.DatabaseObject;
import com.mysql.cj.api.x.DocResult;
import com.mysql.cj.api.x.RowResult;
import com.mysql.cj.api.x.SqlResult;
import com.mysql.cj.core.ServerVersion;
import com.mysql.cj.core.conf.DefaultPropertySet;
import com.mysql.cj.core.exceptions.CJCommunicationsException;
import com.mysql.cj.core.io.DbDocValueFactory;
import com.mysql.cj.core.io.LongValueFactory;
import com.mysql.cj.core.io.StatementExecuteOk;
import com.mysql.cj.core.io.StringValueFactory;
import com.mysql.cj.core.result.Field;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.mysqlx.devapi.DatabaseObjectDescription;
import com.mysql.cj.mysqlx.devapi.DevapiRowFactory;
import com.mysql.cj.mysqlx.devapi.DocResultImpl;
import com.mysql.cj.mysqlx.devapi.RowResultImpl;
import com.mysql.cj.mysqlx.devapi.SqlDataResult;
import com.mysql.cj.mysqlx.devapi.SqlResultImpl;
import com.mysql.cj.mysqlx.devapi.SqlUpdateResult;
import com.mysql.cj.mysqlx.io.MysqlxProtocol;
import com.mysql.cj.mysqlx.io.MysqlxProtocolFactory;
import com.mysql.cj.mysqlx.io.ResultListener;
import com.mysql.cj.mysqlx.io.ResultStreamer;
import com.mysql.cj.mysqlx.io.StatementExecuteOkBuilder;
import com.mysql.cj.mysqlx.result.MysqlxRowInputStream;
import com.mysql.cj.x.json.DbDoc;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Spliterators;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MysqlxSession implements Session {
   private MysqlxProtocol protocol;
   private ResultStreamer currentResult;
   private String host;
   private int port;
   private TimeZone defaultTimeZone = TimeZone.getDefault();

   public MysqlxSession(Properties properties) {
      PropertySet pset = new DefaultPropertySet();
      pset.initializeProperties(properties);
      this.host = properties.getProperty("HOST");
      if (this.host == null || StringUtils.isEmptyOrWhitespaceOnly(this.host)) {
         this.host = "localhost";
      }

      this.port = Integer.parseInt(properties.getProperty("PORT", "33060"));
      this.protocol = MysqlxProtocolFactory.getInstance(this.host, this.port, pset);
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public PropertySet getPropertySet() {
      return this.protocol.getPropertySet();
   }

   public Protocol getProtocol() {
      throw new NullPointerException("TODO: You are not allowed to have my protocol");
   }

   public void changeUser(String user, String password, String database) {
      this.protocol.sendSaslMysql41AuthStart();
      byte[] salt = this.protocol.readAuthenticateContinue();
      this.protocol.sendSaslMysql41AuthContinue(user, password, salt, database);
      this.protocol.readAuthenticateOk();
      this.setupInternalState();
   }

   private void setupInternalState() {
      this.protocol.setMaxAllowedPacket((int)this.queryForLong("select @@mysqlx_max_allowed_packet"));
   }

   public ExceptionInterceptor getExceptionInterceptor() {
      throw new NullPointerException("TODO: You are not allowed to have this");
   }

   public void setExceptionInterceptor(ExceptionInterceptor exceptionInterceptor) {
      throw new NullPointerException("TODO: I don't need your stinkin exception interceptor");
   }

   public boolean characterSetNamesMatches(String mysqlEncodingName) {
      throw new NullPointerException("TODO: don't implement this method here");
   }

   public boolean inTransactionOnServer() {
      throw new NullPointerException("TODO: who wants to know? Also, check NEW tx state in OK packet extensions");
   }

   public String getServerVariable(String name) {
      throw new NullPointerException("TODO: ");
   }

   public Map<String, String> getServerVariables() {
      throw new NullPointerException("TODO: ");
   }

   public void setServerVariables(Map<String, String> serverVariables) {
      throw new NullPointerException("TODO: ");
   }

   public void abortInternal() {
      throw new NullPointerException("TODO: REPLACE ME WITH close() unless there's different semantics here");
   }

   public void quit() {
      throw new NullPointerException("TODO: REPLACE ME WITH close() unless there's different semantics here");
   }

   public void forceClose() {
      throw new NullPointerException("TODO: REPLACE ME WITH close() unless there's different semantics here");
   }

   public ServerVersion getServerVersion() {
      throw new NullPointerException("TODO: isn't this in server session?");
   }

   public boolean versionMeetsMinimum(int major, int minor, int subminor) {
      throw new NullPointerException("TODO: ");
   }

   public long getThreadId() {
      return this.protocol.getClientId();
   }

   public boolean isSetNeededForAutoCommitMode(boolean autoCommitFlag) {
      throw new NullPointerException("TODO: ");
   }

   private void newCommand() {
      if (this.currentResult != null) {
         try {
            this.currentResult.finishStreaming();
         } finally {
            this.currentResult = null;
         }
      }

   }

   public StatementExecuteOk addDocs(String schemaName, String collectionName, List<String> jsonStrings) {
      this.newCommand();
      this.protocol.sendDocInsert(schemaName, collectionName, jsonStrings);
      return this.protocol.readStatementExecuteOk();
   }

   public StatementExecuteOk insertRows(String schemaName, String tableName, InsertParams insertParams) {
      this.newCommand();
      this.protocol.sendRowInsert(schemaName, tableName, insertParams);
      return this.protocol.readStatementExecuteOk();
   }

   public StatementExecuteOk updateDocs(FilterParams filterParams, List<UpdateSpec> updates) {
      this.newCommand();
      this.protocol.sendDocUpdates(filterParams, updates);
      return this.protocol.readStatementExecuteOk();
   }

   public StatementExecuteOk updateRows(FilterParams filterParams, UpdateParams updateParams) {
      this.newCommand();
      this.protocol.sendRowUpdates(filterParams, updateParams);
      return this.protocol.readStatementExecuteOk();
   }

   public StatementExecuteOk deleteDocs(FilterParams filterParams) {
      this.newCommand();
      this.protocol.sendDocDelete(filterParams);
      return this.protocol.readStatementExecuteOk();
   }

   public StatementExecuteOk deleteRows(FilterParams filterParams) {
      this.newCommand();
      this.protocol.sendDocDelete(filterParams);
      return this.protocol.readStatementExecuteOk();
   }

   private <T extends ResultStreamer> T findInternal(FindParams findParams, MysqlxSession.ResultCtor<T> resultCtor) {
      this.newCommand();
      this.protocol.sendFind(findParams);
      ArrayList<Field> metadata = this.protocol.readMetadata("latin1");
      BiFunction var10000 = (BiFunction)resultCtor.apply(metadata);
      MysqlxRowInputStream var10001 = this.protocol.getRowInputStream(metadata);
      MysqlxProtocol var10002 = this.protocol;
      var10002.getClass();
      T res = (ResultStreamer)var10000.apply(var10001, var10002::readStatementExecuteOk);
      this.currentResult = res;
      return res;
   }

   public DocResultImpl findDocs(FindParams findParams) {
      return (DocResultImpl)this.findInternal(findParams, (metadata) -> {
         return (rows, task) -> {
            return new DocResultImpl(rows, task);
         };
      });
   }

   public RowResultImpl selectRows(FindParams findParams) {
      return (RowResultImpl)this.findInternal(findParams, (metadata) -> {
         return (rows, task) -> {
            return new RowResultImpl(metadata, this.defaultTimeZone, rows, task);
         };
      });
   }

   public void createCollection(String schemaName, String collectionName) {
      this.newCommand();
      this.protocol.sendCreateCollection(schemaName, collectionName);
      this.protocol.readStatementExecuteOk();
   }

   public void dropCollection(String schemaName, String collectionName) {
      this.newCommand();
      this.protocol.sendDropCollection(schemaName, collectionName);
      this.protocol.readStatementExecuteOk();
   }

   public void dropCollectionIfExists(String schemaName, String collectionName) {
      if (this.tableExists(schemaName, collectionName)) {
         this.dropCollection(schemaName, collectionName);
      }

   }

   public StatementExecuteOk createCollectionIndex(String schemaName, String collectionName, CreateIndexParams params) {
      this.newCommand();
      this.protocol.sendCreateCollectionIndex(schemaName, collectionName, params);
      return this.protocol.readStatementExecuteOk();
   }

   public StatementExecuteOk dropCollectionIndex(String schemaName, String collectionName, String indexName) {
      this.newCommand();
      this.protocol.sendDropCollectionIndex(schemaName, collectionName, indexName);
      return this.protocol.readStatementExecuteOk();
   }

   private long queryForLong(String sql) {
      this.newCommand();
      this.protocol.sendSqlStatement(sql);
      ArrayList<Field> metadata = this.protocol.readMetadata("latin1");
      long count = (Long)this.protocol.getRowInputStream(metadata).next().getValue(0, new LongValueFactory());
      this.protocol.readStatementExecuteOk();
      return count;
   }

   public long tableCount(String schemaName, String tableName) {
      StringBuilder stmt = new StringBuilder("select count(*) from ");
      stmt.append(ExprUnparser.quoteIdentifier(schemaName));
      stmt.append(".");
      stmt.append(ExprUnparser.quoteIdentifier(tableName));
      return this.queryForLong(stmt.toString());
   }

   public boolean schemaExists(String schemaName) {
      StringBuilder stmt = new StringBuilder("select count(*) from information_schema.schemata where schema_name = '");
      stmt.append(schemaName.replaceAll("'", "\\'"));
      stmt.append("'");
      return 1L == this.queryForLong(stmt.toString());
   }

   public boolean tableExists(String schemaName, String tableName) {
      StringBuilder stmt = new StringBuilder("select count(*) from information_schema.tables where table_schema = '");
      stmt.append(schemaName.replaceAll("'", "\\'"));
      stmt.append("' and table_name = '");
      stmt.append(tableName.replaceAll("'", "\\'"));
      stmt.append("'");
      return 1L == this.queryForLong(stmt.toString());
   }

   public List<String> getObjectNamesOfType(String schemaName, DatabaseObject.DbObjectType type) {
      return this.getObjectNamesOfType(schemaName, type, (String)null);
   }

   public List<String> getObjectNamesOfType(String schemaName, DatabaseObject.DbObjectType type, String pattern) {
      this.newCommand();
      if (pattern == null) {
         this.protocol.sendListObjects(schemaName);
      } else {
         this.protocol.sendListObjects(schemaName, pattern);
      }

      ArrayList<Field> metadata = this.protocol.readMetadata("latin1");
      Iterator<Row> ris = this.protocol.getRowInputStream(metadata);
      List<String> objectNames = (List)StreamSupport.stream(Spliterators.spliteratorUnknownSize(ris, 0), false).filter((r) -> {
         return ((String)r.getValue(1, new StringValueFactory())).equals(type.toString());
      }).map((r) -> {
         return (String)r.getValue(0, new StringValueFactory());
      }).collect(Collectors.toList());
      this.protocol.readStatementExecuteOk();
      return objectNames;
   }

   public List<DatabaseObjectDescription> listObjects(String schemaName) {
      return this.listObjects(schemaName, (String)null);
   }

   public List<DatabaseObjectDescription> listObjects(String schemaName, String pattern) {
      this.newCommand();
      if (pattern == null) {
         this.protocol.sendListObjects(schemaName);
      } else {
         this.protocol.sendListObjects(schemaName, pattern);
      }

      ArrayList<Field> metadata = this.protocol.readMetadata("latin1");
      Iterator<Row> ris = this.protocol.getRowInputStream(metadata);
      List<DatabaseObjectDescription> objects = (List)StreamSupport.stream(Spliterators.spliteratorUnknownSize(ris, 0), false).map((r) -> {
         return new DatabaseObjectDescription((String)r.getValue(0, new StringValueFactory()), (String)r.getValue(1, new StringValueFactory()));
      }).collect(Collectors.toList());
      this.protocol.readStatementExecuteOk();
      return objects;
   }

   public <RES_T, R> RES_T query(String sql, Function<Row, R> eachRow, Collector<R, ?, RES_T> collector) {
      this.newCommand();
      this.protocol.sendSqlStatement(sql);
      ArrayList<Field> metadata = this.protocol.readMetadata("latin1");
      Iterator<Row> ris = this.protocol.getRowInputStream(metadata);
      RES_T result = StreamSupport.stream(Spliterators.spliteratorUnknownSize(ris, 0), false).map(eachRow).collect(collector);
      this.protocol.readStatementExecuteOk();
      return result;
   }

   public SqlResult executeSql(String sql, Object args) {
      this.newCommand();
      this.protocol.sendSqlStatement(sql, args);
      boolean[] readLastResult = new boolean[1];
      Supplier<StatementExecuteOk> okReader = () -> {
         if (readLastResult[0]) {
            throw new CJCommunicationsException("Invalid state attempting to read ok packet");
         } else if (this.protocol.hasMoreResults()) {
            return (new StatementExecuteOkBuilder()).build();
         } else {
            readLastResult[0] = true;
            return this.protocol.readStatementExecuteOk();
         }
      };
      Supplier<SqlResult> resultStream = () -> {
         if (readLastResult[0]) {
            return null;
         } else if (this.protocol.isSqlResultPending()) {
            ArrayList<Field> metadata = this.protocol.readMetadata("latin1");
            return new SqlDataResult(metadata, this.defaultTimeZone, this.protocol.getRowInputStream(metadata), okReader);
         } else {
            readLastResult[0] = true;
            return new SqlUpdateResult(this.protocol.readStatementExecuteOk());
         }
      };
      SqlResultImpl res = new SqlResultImpl(resultStream);
      this.currentResult = res;
      return res;
   }

   public CompletableFuture<SqlResult> asyncExecuteSql(String sql, Object args) {
      this.newCommand();
      return this.protocol.asyncExecuteSql(sql, args, "latin1", this.defaultTimeZone);
   }

   public StatementExecuteOk update(String sql) {
      this.newCommand();
      this.protocol.sendSqlStatement(sql);
      return this.protocol.readStatementExecuteOk();
   }

   public boolean isOpen() {
      return this.protocol.isOpen();
   }

   public void close() {
      try {
         this.newCommand();
         this.protocol.sendSessionClose();
         this.protocol.readOk();
      } finally {
         try {
            this.protocol.close();
         } catch (IOException var7) {
            throw new CJCommunicationsException(var7);
         }
      }

   }

   private <RES_T> CompletableFuture<RES_T> asyncFindInternal(FindParams findParams, MysqlxSession.ResultCtor<? extends RES_T> resultCtor) {
      CompletableFuture<RES_T> f = new CompletableFuture();
      ResultListener l = new ResultCreatingResultListener(resultCtor, f);
      this.newCommand();
      this.protocol.asyncFind(findParams, "latin1", l, f);
      return f;
   }

   public CompletableFuture<DocResult> asyncFindDocs(FindParams findParams) {
      return this.asyncFindInternal(findParams, (metadata) -> {
         return (rows, task) -> {
            return new DocResultImpl(rows, task);
         };
      });
   }

   public CompletableFuture<RowResult> asyncSelectRows(FindParams findParams) {
      return this.asyncFindInternal(findParams, (metadata) -> {
         return (rows, task) -> {
            return new RowResultImpl(metadata, this.defaultTimeZone, rows, task);
         };
      });
   }

   public <R> CompletableFuture<R> asyncFindDocsReduce(FindParams findParams, R id, DataStatement.Reducer<DbDoc, R> reducer) {
      CompletableFuture<R> f = new CompletableFuture();
      ResultListener l = new RowWiseReducingResultListener(id, reducer, f, (_ignored_metadata) -> {
         return (r) -> {
            return (DbDoc)r.getValue(0, new DbDocValueFactory());
         };
      });
      this.newCommand();
      this.protocol.asyncFind(findParams, "latin1", l, f);
      return f;
   }

   public <R> CompletableFuture<R> asyncSelectRowsReduce(FindParams findParams, R id, DataStatement.Reducer<com.mysql.cj.api.x.Row, R> reducer) {
      CompletableFuture<R> f = new CompletableFuture();
      RowWiseReducingResultListener.MetadataToRowToElement<com.mysql.cj.api.x.Row> rowFactory = (metadata) -> {
         return new DevapiRowFactory(metadata, this.defaultTimeZone);
      };
      ResultListener l = new RowWiseReducingResultListener(id, reducer, f, rowFactory);
      this.newCommand();
      this.protocol.asyncFind(findParams, "latin1", l, f);
      return f;
   }

   public CompletableFuture<StatementExecuteOk> asyncAddDocs(String schemaName, String collectionName, List<String> jsonStrings) {
      this.newCommand();
      return this.protocol.asyncAddDocs(schemaName, collectionName, jsonStrings);
   }

   public CompletableFuture<StatementExecuteOk> asyncInsertRows(String schemaName, String tableName, InsertParams insertParams) {
      this.newCommand();
      return this.protocol.asyncInsertRows(schemaName, tableName, insertParams);
   }

   public CompletableFuture<StatementExecuteOk> asyncUpdateDocs(FilterParams filterParams, List<UpdateSpec> updates) {
      this.newCommand();
      return this.protocol.asyncUpdateDocs(filterParams, updates);
   }

   public CompletableFuture<StatementExecuteOk> asyncUpdateRows(FilterParams filterParams, UpdateParams updateParams) {
      this.newCommand();
      return this.protocol.asyncUpdateRows(filterParams, updateParams);
   }

   public CompletableFuture<StatementExecuteOk> asyncDeleteDocs(FilterParams filterParams) {
      this.newCommand();
      return this.protocol.asyncDeleteDocs(filterParams);
   }

   public CompletableFuture<StatementExecuteOk> asyncDeleteRows(FilterParams filterParams) {
      this.newCommand();
      return this.protocol.asyncDeleteDocs(filterParams);
   }

   public CompletableFuture<StatementExecuteOk> asyncCreateCollectionIndex(String schemaName, String collectionName, CreateIndexParams params) {
      this.newCommand();
      return this.protocol.asyncCreateCollectionIndex(schemaName, collectionName, params);
   }

   public CompletableFuture<StatementExecuteOk> asyncDropCollectionIndex(String schemaName, String collectionName, String indexName) {
      this.newCommand();
      return this.protocol.asyncDropCollectionIndex(schemaName, collectionName, indexName);
   }

   public int getServerVariable(String variableName, int fallbackValue) {
      return 0;
   }

   public int getServerDefaultCollationIndex() {
      return 0;
   }

   public void setServerDefaultCollationIndex(int serverDefaultCollationIndex) {
   }

   public Log getLog() {
      return null;
   }

   public void setLog(Log log) {
   }

   public void configureTimezone() {
   }

   public TimeZone getDefaultTimeZone() {
      return null;
   }

   public String getErrorMessageEncoding() {
      return null;
   }

   public void setErrorMessageEncoding(String errorMessageEncoding) {
   }

   public int getMaxBytesPerChar(String javaCharsetName) {
      return 0;
   }

   public int getMaxBytesPerChar(Integer charsetIndex, String javaCharsetName) {
      return 0;
   }

   public String getEncodingForIndex(int collationIndex) {
      return null;
   }

   public ProfilerEventHandler getProfilerEventHandler() {
      return null;
   }

   public void setProfilerEventHandler(ProfilerEventHandler h) {
   }

   public ServerSession getServerSession() {
      return null;
   }

   public boolean isSSLEstablished() {
      return false;
   }

   public SocketAddress getRemoteSocketAddress() {
      return null;
   }

   public boolean serverSupportsFracSecs() {
      return true;
   }

   public interface ResultCtor<T> extends Function<ArrayList<Field>, BiFunction<RowList, Supplier<StatementExecuteOk>, T>> {
   }
}
