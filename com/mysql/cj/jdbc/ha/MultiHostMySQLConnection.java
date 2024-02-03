package com.mysql.cj.jdbc.ha;

import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.jdbc.ClientInfoProvider;
import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.api.jdbc.JdbcPropertySet;
import com.mysql.cj.api.jdbc.interceptors.StatementInterceptor;
import com.mysql.cj.api.jdbc.result.ResultSetInternalMethods;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.ServerVersion;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.jdbc.ServerPreparedStatement;
import com.mysql.cj.jdbc.StatementImpl;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.jdbc.result.CachedResultSetMetaData;
import com.mysql.cj.mysqla.MysqlaSession;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.Executor;

public class MultiHostMySQLConnection implements JdbcConnection {
   protected MultiHostConnectionProxy thisAsProxy;

   public MultiHostMySQLConnection(MultiHostConnectionProxy proxy) {
      this.thisAsProxy = proxy;
   }

   public MultiHostConnectionProxy getThisAsProxy() {
      return this.thisAsProxy;
   }

   protected JdbcConnection getActiveMySQLConnection() {
      synchronized(this.thisAsProxy) {
         return this.thisAsProxy.currentConnection;
      }
   }

   public void abortInternal() throws SQLException {
      try {
         this.getActiveMySQLConnection().abortInternal();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public void changeUser(String userName, String newPassword) throws SQLException {
      try {
         this.getActiveMySQLConnection().changeUser(userName, newPassword);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void checkClosed() {
      this.getActiveMySQLConnection().checkClosed();
   }

   /** @deprecated */
   @Deprecated
   public void clearHasTriedMaster() {
      this.getActiveMySQLConnection().clearHasTriedMaster();
   }

   public void clearWarnings() throws SQLException {
      try {
         this.getActiveMySQLConnection().clearWarnings();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      try {
         return this.getActiveMySQLConnection().clientPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      try {
         return this.getActiveMySQLConnection().clientPrepareStatement(sql, resultSetType, resultSetConcurrency);
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public PreparedStatement clientPrepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
      try {
         return this.getActiveMySQLConnection().clientPrepareStatement(sql, autoGenKeyIndex);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public PreparedStatement clientPrepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
      try {
         return this.getActiveMySQLConnection().clientPrepareStatement(sql, autoGenKeyIndexes);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public PreparedStatement clientPrepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
      try {
         return this.getActiveMySQLConnection().clientPrepareStatement(sql, autoGenKeyColNames);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public PreparedStatement clientPrepareStatement(String sql) throws SQLException {
      try {
         return this.getActiveMySQLConnection().clientPrepareStatement(sql);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void close() throws SQLException {
      try {
         this.getActiveMySQLConnection().close();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public void commit() throws SQLException {
      try {
         this.getActiveMySQLConnection().commit();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public void createNewIO(boolean isForReconnect) {
      this.getActiveMySQLConnection().createNewIO(isForReconnect);
   }

   public Statement createStatement() throws SQLException {
      try {
         return this.getActiveMySQLConnection().createStatement();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      try {
         return this.getActiveMySQLConnection().createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
      try {
         return this.getActiveMySQLConnection().createStatement(resultSetType, resultSetConcurrency);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public JdbcConnection duplicate() throws SQLException {
      try {
         return this.getActiveMySQLConnection().duplicate();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public ResultSetInternalMethods execSQL(StatementImpl callingStatement, String sql, int maxRows, PacketPayload packet, boolean streamResults, String catalog, ColumnDefinition cachedMetadata, boolean isBatch) throws SQLException {
      try {
         return this.getActiveMySQLConnection().execSQL(callingStatement, sql, maxRows, packet, streamResults, catalog, cachedMetadata, isBatch);
      } catch (CJException var10) {
         throw SQLExceptionsMapping.translateException(var10, this.getExceptionInterceptor());
      }
   }

   public ResultSetInternalMethods execSQL(StatementImpl callingStatement, String sql, int maxRows, PacketPayload packet, boolean streamResults, String catalog, ColumnDefinition cachedMetadata) throws SQLException {
      try {
         return this.getActiveMySQLConnection().execSQL(callingStatement, sql, maxRows, packet, streamResults, catalog, cachedMetadata);
      } catch (CJException var9) {
         throw SQLExceptionsMapping.translateException(var9, this.getExceptionInterceptor());
      }
   }

   public StringBuilder generateConnectionCommentBlock(StringBuilder buf) {
      return this.getActiveMySQLConnection().generateConnectionCommentBlock(buf);
   }

   public int getActiveStatementCount() {
      return this.getActiveMySQLConnection().getActiveStatementCount();
   }

   public boolean getAutoCommit() throws SQLException {
      try {
         return this.getActiveMySQLConnection().getAutoCommit();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getAutoIncrementIncrement() {
      return this.getActiveMySQLConnection().getAutoIncrementIncrement();
   }

   public CachedResultSetMetaData getCachedMetaData(String sql) {
      return this.getActiveMySQLConnection().getCachedMetaData(sql);
   }

   public Timer getCancelTimer() {
      return this.getActiveMySQLConnection().getCancelTimer();
   }

   public String getCatalog() throws SQLException {
      try {
         return this.getActiveMySQLConnection().getCatalog();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public String getCharacterSetMetadata() {
      return this.getActiveMySQLConnection().getCharacterSetMetadata();
   }

   public ExceptionInterceptor getExceptionInterceptor() {
      return this.getActiveMySQLConnection().getExceptionInterceptor();
   }

   public int getHoldability() throws SQLException {
      try {
         return this.getActiveMySQLConnection().getHoldability();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public String getHost() {
      return this.getActiveMySQLConnection().getHost();
   }

   public long getId() {
      return this.getActiveMySQLConnection().getId();
   }

   public long getIdleFor() {
      return this.getActiveMySQLConnection().getIdleFor();
   }

   public JdbcConnection getMultiHostSafeProxy() {
      return this.getThisAsProxy().getProxy();
   }

   public DatabaseMetaData getMetaData() throws SQLException {
      try {
         return this.getActiveMySQLConnection().getMetaData();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public Statement getMetadataSafeStatement() throws SQLException {
      try {
         return this.getActiveMySQLConnection().getMetadataSafeStatement();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public Properties getProperties() {
      return this.getActiveMySQLConnection().getProperties();
   }

   public boolean getRequiresEscapingEncoder() {
      return this.getActiveMySQLConnection().getRequiresEscapingEncoder();
   }

   public ServerVersion getServerVersion() {
      return this.getActiveMySQLConnection().getServerVersion();
   }

   public MysqlaSession getSession() {
      return this.getActiveMySQLConnection().getSession();
   }

   public String getStatementComment() {
      return this.getActiveMySQLConnection().getStatementComment();
   }

   public List<StatementInterceptor> getStatementInterceptorsInstances() {
      return this.getActiveMySQLConnection().getStatementInterceptorsInstances();
   }

   public int getTransactionIsolation() throws SQLException {
      try {
         return this.getActiveMySQLConnection().getTransactionIsolation();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public Map<String, Class<?>> getTypeMap() throws SQLException {
      try {
         return this.getActiveMySQLConnection().getTypeMap();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public String getURL() {
      return this.getActiveMySQLConnection().getURL();
   }

   public String getUser() {
      return this.getActiveMySQLConnection().getUser();
   }

   public SQLWarning getWarnings() throws SQLException {
      try {
         return this.getActiveMySQLConnection().getWarnings();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean hasSameProperties(JdbcConnection c) {
      return this.getActiveMySQLConnection().hasSameProperties(c);
   }

   /** @deprecated */
   @Deprecated
   public boolean hasTriedMaster() {
      return this.getActiveMySQLConnection().hasTriedMaster();
   }

   public void incrementNumberOfPreparedExecutes() {
      this.getActiveMySQLConnection().incrementNumberOfPreparedExecutes();
   }

   public void incrementNumberOfPrepares() {
      this.getActiveMySQLConnection().incrementNumberOfPrepares();
   }

   public void incrementNumberOfResultSetsCreated() {
      this.getActiveMySQLConnection().incrementNumberOfResultSetsCreated();
   }

   public void initializeResultsMetadataFromCache(String sql, CachedResultSetMetaData cachedMetaData, ResultSetInternalMethods resultSet) throws SQLException {
      try {
         this.getActiveMySQLConnection().initializeResultsMetadataFromCache(sql, cachedMetaData, resultSet);
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void initializeSafeStatementInterceptors() throws SQLException {
      try {
         this.getActiveMySQLConnection().initializeSafeStatementInterceptors();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean isAbonormallyLongQuery(long millisOrNanos) {
      return this.getActiveMySQLConnection().isAbonormallyLongQuery(millisOrNanos);
   }

   public boolean isInGlobalTx() {
      return this.getActiveMySQLConnection().isInGlobalTx();
   }

   public boolean isMasterConnection() {
      return this.getThisAsProxy().isMasterConnection();
   }

   public boolean isNoBackslashEscapesSet() {
      return this.getActiveMySQLConnection().isNoBackslashEscapesSet();
   }

   public boolean isReadInfoMsgEnabled() {
      return this.getActiveMySQLConnection().isReadInfoMsgEnabled();
   }

   public boolean isReadOnly() throws SQLException {
      try {
         return this.getActiveMySQLConnection().isReadOnly();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean isReadOnly(boolean useSessionStatus) throws SQLException {
      try {
         return this.getActiveMySQLConnection().isReadOnly(useSessionStatus);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public boolean isSameResource(JdbcConnection otherConnection) {
      return this.getActiveMySQLConnection().isSameResource(otherConnection);
   }

   public boolean lowerCaseTableNames() {
      return this.getActiveMySQLConnection().lowerCaseTableNames();
   }

   public String nativeSQL(String sql) throws SQLException {
      try {
         return this.getActiveMySQLConnection().nativeSQL(sql);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void ping() throws SQLException {
      try {
         this.getActiveMySQLConnection().ping();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public void pingInternal(boolean checkForClosedConnection, int timeoutMillis) throws SQLException {
      try {
         this.getActiveMySQLConnection().pingInternal(checkForClosedConnection, timeoutMillis);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      try {
         return this.getActiveMySQLConnection().prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      try {
         return this.getActiveMySQLConnection().prepareCall(sql, resultSetType, resultSetConcurrency);
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public CallableStatement prepareCall(String sql) throws SQLException {
      try {
         return this.getActiveMySQLConnection().prepareCall(sql);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      try {
         return this.getActiveMySQLConnection().prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      try {
         return this.getActiveMySQLConnection().prepareStatement(sql, resultSetType, resultSetConcurrency);
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public PreparedStatement prepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
      try {
         return this.getActiveMySQLConnection().prepareStatement(sql, autoGenKeyIndex);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public PreparedStatement prepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
      try {
         return this.getActiveMySQLConnection().prepareStatement(sql, autoGenKeyIndexes);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public PreparedStatement prepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
      try {
         return this.getActiveMySQLConnection().prepareStatement(sql, autoGenKeyColNames);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public PreparedStatement prepareStatement(String sql) throws SQLException {
      try {
         return this.getActiveMySQLConnection().prepareStatement(sql);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void realClose(boolean calledExplicitly, boolean issueRollback, boolean skipLocalTeardown, Throwable reason) throws SQLException {
      try {
         this.getActiveMySQLConnection().realClose(calledExplicitly, issueRollback, skipLocalTeardown, reason);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void recachePreparedStatement(ServerPreparedStatement pstmt) throws SQLException {
      try {
         this.getActiveMySQLConnection().recachePreparedStatement(pstmt);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void decachePreparedStatement(ServerPreparedStatement pstmt) throws SQLException {
      try {
         this.getActiveMySQLConnection().decachePreparedStatement(pstmt);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void registerQueryExecutionTime(long queryTimeMs) {
      this.getActiveMySQLConnection().registerQueryExecutionTime(queryTimeMs);
   }

   public void registerStatement(com.mysql.cj.api.jdbc.Statement stmt) {
      this.getActiveMySQLConnection().registerStatement(stmt);
   }

   public void releaseSavepoint(Savepoint arg0) throws SQLException {
      try {
         this.getActiveMySQLConnection().releaseSavepoint(arg0);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void reportNumberOfTablesAccessed(int numTablesAccessed) {
      this.getActiveMySQLConnection().reportNumberOfTablesAccessed(numTablesAccessed);
   }

   public void reportQueryTime(long millisOrNanos) {
      this.getActiveMySQLConnection().reportQueryTime(millisOrNanos);
   }

   public void resetServerState() throws SQLException {
      try {
         this.getActiveMySQLConnection().resetServerState();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public void rollback() throws SQLException {
      try {
         this.getActiveMySQLConnection().rollback();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public void rollback(Savepoint savepoint) throws SQLException {
      try {
         this.getActiveMySQLConnection().rollback(savepoint);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public PreparedStatement serverPrepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      try {
         return this.getActiveMySQLConnection().serverPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public PreparedStatement serverPrepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      try {
         return this.getActiveMySQLConnection().serverPrepareStatement(sql, resultSetType, resultSetConcurrency);
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public PreparedStatement serverPrepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
      try {
         return this.getActiveMySQLConnection().serverPrepareStatement(sql, autoGenKeyIndex);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public PreparedStatement serverPrepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
      try {
         return this.getActiveMySQLConnection().serverPrepareStatement(sql, autoGenKeyIndexes);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public PreparedStatement serverPrepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
      try {
         return this.getActiveMySQLConnection().serverPrepareStatement(sql, autoGenKeyColNames);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public PreparedStatement serverPrepareStatement(String sql) throws SQLException {
      try {
         return this.getActiveMySQLConnection().serverPrepareStatement(sql);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void setAutoCommit(boolean autoCommitFlag) throws SQLException {
      try {
         this.getActiveMySQLConnection().setAutoCommit(autoCommitFlag);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void setCatalog(String catalog) throws SQLException {
      try {
         this.getActiveMySQLConnection().setCatalog(catalog);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void setFailedOver(boolean flag) {
      this.getActiveMySQLConnection().setFailedOver(flag);
   }

   public void setHoldability(int arg0) throws SQLException {
      try {
         this.getActiveMySQLConnection().setHoldability(arg0);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void setInGlobalTx(boolean flag) {
      this.getActiveMySQLConnection().setInGlobalTx(flag);
   }

   public void setProxy(JdbcConnection proxy) {
      this.getThisAsProxy().setProxy(proxy);
   }

   public void setReadInfoMsgEnabled(boolean flag) {
      this.getActiveMySQLConnection().setReadInfoMsgEnabled(flag);
   }

   public void setReadOnly(boolean readOnlyFlag) throws SQLException {
      try {
         this.getActiveMySQLConnection().setReadOnly(readOnlyFlag);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void setReadOnlyInternal(boolean readOnlyFlag) throws SQLException {
      try {
         this.getActiveMySQLConnection().setReadOnlyInternal(readOnlyFlag);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public Savepoint setSavepoint() throws SQLException {
      try {
         return this.getActiveMySQLConnection().setSavepoint();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public Savepoint setSavepoint(String name) throws SQLException {
      try {
         return this.getActiveMySQLConnection().setSavepoint(name);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void setStatementComment(String comment) {
      this.getActiveMySQLConnection().setStatementComment(comment);
   }

   public void setTransactionIsolation(int level) throws SQLException {
      try {
         this.getActiveMySQLConnection().setTransactionIsolation(level);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void shutdownServer() throws SQLException {
      try {
         this.getActiveMySQLConnection().shutdownServer();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean storesLowerCaseTableName() {
      return this.getActiveMySQLConnection().storesLowerCaseTableName();
   }

   public void throwConnectionClosedException() throws SQLException {
      try {
         this.getActiveMySQLConnection().throwConnectionClosedException();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public void transactionBegun() throws SQLException {
      try {
         this.getActiveMySQLConnection().transactionBegun();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public void transactionCompleted() throws SQLException {
      try {
         this.getActiveMySQLConnection().transactionCompleted();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public void unregisterStatement(com.mysql.cj.api.jdbc.Statement stmt) {
      this.getActiveMySQLConnection().unregisterStatement(stmt);
   }

   public void unSafeStatementInterceptors() throws SQLException {
      try {
         this.getActiveMySQLConnection().unSafeStatementInterceptors();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean useAnsiQuotedIdentifiers() {
      return this.getActiveMySQLConnection().useAnsiQuotedIdentifiers();
   }

   public boolean isClosed() throws SQLException {
      try {
         return this.getThisAsProxy().isClosed;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean isProxySet() {
      return this.getActiveMySQLConnection().isProxySet();
   }

   public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
      try {
         this.getActiveMySQLConnection().setTypeMap(map);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public boolean isServerLocal() throws SQLException {
      try {
         return this.getActiveMySQLConnection().isServerLocal();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public void setSchema(String schema) throws SQLException {
      try {
         this.getActiveMySQLConnection().setSchema(schema);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public String getSchema() throws SQLException {
      try {
         return this.getActiveMySQLConnection().getSchema();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public void abort(Executor executor) throws SQLException {
      try {
         this.getActiveMySQLConnection().abort(executor);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
      try {
         this.getActiveMySQLConnection().setNetworkTimeout(executor, milliseconds);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public int getNetworkTimeout() throws SQLException {
      try {
         return this.getActiveMySQLConnection().getNetworkTimeout();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public Object getConnectionMutex() {
      return this.getActiveMySQLConnection().getConnectionMutex();
   }

   public int getSessionMaxRows() {
      return this.getActiveMySQLConnection().getSessionMaxRows();
   }

   public void setSessionMaxRows(int max) throws SQLException {
      try {
         this.getActiveMySQLConnection().setSessionMaxRows(max);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public SQLXML createSQLXML() throws SQLException {
      try {
         return this.getActiveMySQLConnection().createSQLXML();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
      try {
         return this.getActiveMySQLConnection().createArrayOf(typeName, elements);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
      try {
         return this.getActiveMySQLConnection().createStruct(typeName, attributes);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public Properties getClientInfo() throws SQLException {
      try {
         return this.getActiveMySQLConnection().getClientInfo();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public String getClientInfo(String name) throws SQLException {
      try {
         return this.getActiveMySQLConnection().getClientInfo(name);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public boolean isValid(int timeout) throws SQLException {
      try {
         return this.getActiveMySQLConnection().isValid(timeout);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void setClientInfo(Properties properties) throws SQLClientInfoException {
      this.getActiveMySQLConnection().setClientInfo(properties);
   }

   public void setClientInfo(String name, String value) throws SQLClientInfoException {
      this.getActiveMySQLConnection().setClientInfo(name, value);
   }

   public boolean isWrapperFor(Class<?> iface) throws SQLException {
      try {
         this.checkClosed();
         return iface.isInstance(this);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public <T> T unwrap(Class<T> iface) throws SQLException {
      try {
         try {
            return iface.cast(this);
         } catch (ClassCastException var4) {
            throw SQLError.createSQLException(Messages.getString("Common.UnableToUnwrap", new Object[]{iface.toString()}), "S1009", this.getExceptionInterceptor());
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public Blob createBlob() throws SQLException {
      try {
         return this.getActiveMySQLConnection().createBlob();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public Clob createClob() throws SQLException {
      try {
         return this.getActiveMySQLConnection().createClob();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public NClob createNClob() throws SQLException {
      try {
         return this.getActiveMySQLConnection().createNClob();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public ClientInfoProvider getClientInfoProviderImpl() throws SQLException {
      try {
         synchronized(this.getThisAsProxy()) {
            return this.getActiveMySQLConnection().getClientInfoProviderImpl();
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public String getProcessHost() {
      return this.getActiveMySQLConnection().getProcessHost();
   }

   public JdbcPropertySet getPropertySet() {
      return this.getActiveMySQLConnection().getPropertySet();
   }

   public String getHostPortPair() {
      return this.getActiveMySQLConnection().getHostPortPair();
   }
}
