package com.mysql.cj.api.jdbc;

import com.mysql.cj.api.MysqlConnection;
import com.mysql.cj.api.jdbc.interceptors.StatementInterceptor;
import com.mysql.cj.api.jdbc.result.ResultSetInternalMethods;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.core.ServerVersion;
import com.mysql.cj.jdbc.ServerPreparedStatement;
import com.mysql.cj.jdbc.StatementImpl;
import com.mysql.cj.jdbc.result.CachedResultSetMetaData;
import com.mysql.cj.mysqla.MysqlaSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;

public interface JdbcConnection extends Connection, MysqlConnection {
   JdbcPropertySet getPropertySet();

   MysqlaSession getSession();

   void changeUser(String var1, String var2) throws SQLException;

   /** @deprecated */
   @Deprecated
   void clearHasTriedMaster();

   PreparedStatement clientPrepareStatement(String var1) throws SQLException;

   PreparedStatement clientPrepareStatement(String var1, int var2) throws SQLException;

   PreparedStatement clientPrepareStatement(String var1, int var2, int var3) throws SQLException;

   PreparedStatement clientPrepareStatement(String var1, int[] var2) throws SQLException;

   PreparedStatement clientPrepareStatement(String var1, int var2, int var3, int var4) throws SQLException;

   PreparedStatement clientPrepareStatement(String var1, String[] var2) throws SQLException;

   int getActiveStatementCount();

   long getIdleFor();

   String getStatementComment();

   /** @deprecated */
   @Deprecated
   boolean hasTriedMaster();

   boolean isInGlobalTx();

   void setInGlobalTx(boolean var1);

   boolean isMasterConnection();

   boolean isNoBackslashEscapesSet();

   boolean isSameResource(JdbcConnection var1);

   boolean lowerCaseTableNames();

   void ping() throws SQLException;

   void resetServerState() throws SQLException;

   PreparedStatement serverPrepareStatement(String var1) throws SQLException;

   PreparedStatement serverPrepareStatement(String var1, int var2) throws SQLException;

   PreparedStatement serverPrepareStatement(String var1, int var2, int var3) throws SQLException;

   PreparedStatement serverPrepareStatement(String var1, int var2, int var3, int var4) throws SQLException;

   PreparedStatement serverPrepareStatement(String var1, int[] var2) throws SQLException;

   PreparedStatement serverPrepareStatement(String var1, String[] var2) throws SQLException;

   void setFailedOver(boolean var1);

   void setStatementComment(String var1);

   void shutdownServer() throws SQLException;

   void reportQueryTime(long var1);

   boolean isAbonormallyLongQuery(long var1);

   int getAutoIncrementIncrement();

   boolean hasSameProperties(JdbcConnection var1);

   String getHost();

   String getHostPortPair();

   void setProxy(JdbcConnection var1);

   boolean isServerLocal() throws SQLException;

   int getSessionMaxRows();

   void setSessionMaxRows(int var1) throws SQLException;

   void setSchema(String var1) throws SQLException;

   void abortInternal() throws SQLException;

   void checkClosed();

   boolean isProxySet();

   JdbcConnection duplicate() throws SQLException;

   ResultSetInternalMethods execSQL(StatementImpl var1, String var2, int var3, PacketPayload var4, boolean var5, String var6, ColumnDefinition var7) throws SQLException;

   ResultSetInternalMethods execSQL(StatementImpl var1, String var2, int var3, PacketPayload var4, boolean var5, String var6, ColumnDefinition var7, boolean var8) throws SQLException;

   StringBuilder generateConnectionCommentBlock(StringBuilder var1);

   CachedResultSetMetaData getCachedMetaData(String var1);

   Timer getCancelTimer();

   String getCharacterSetMetadata();

   java.sql.Statement getMetadataSafeStatement() throws SQLException;

   boolean getRequiresEscapingEncoder();

   ServerVersion getServerVersion();

   List<StatementInterceptor> getStatementInterceptorsInstances();

   void incrementNumberOfPreparedExecutes();

   void incrementNumberOfPrepares();

   void incrementNumberOfResultSetsCreated();

   void initializeResultsMetadataFromCache(String var1, CachedResultSetMetaData var2, ResultSetInternalMethods var3) throws SQLException;

   void initializeSafeStatementInterceptors() throws SQLException;

   boolean isReadInfoMsgEnabled();

   boolean isReadOnly(boolean var1) throws SQLException;

   void pingInternal(boolean var1, int var2) throws SQLException;

   void realClose(boolean var1, boolean var2, boolean var3, Throwable var4) throws SQLException;

   void recachePreparedStatement(ServerPreparedStatement var1) throws SQLException;

   void decachePreparedStatement(ServerPreparedStatement var1) throws SQLException;

   void registerQueryExecutionTime(long var1);

   void registerStatement(Statement var1);

   void reportNumberOfTablesAccessed(int var1);

   void setReadInfoMsgEnabled(boolean var1);

   void setReadOnlyInternal(boolean var1) throws SQLException;

   boolean storesLowerCaseTableName();

   void throwConnectionClosedException() throws SQLException;

   void transactionBegun() throws SQLException;

   void transactionCompleted() throws SQLException;

   void unregisterStatement(Statement var1);

   void unSafeStatementInterceptors() throws SQLException;

   boolean useAnsiQuotedIdentifiers();

   JdbcConnection getMultiHostSafeProxy();

   ClientInfoProvider getClientInfoProviderImpl() throws SQLException;
}
