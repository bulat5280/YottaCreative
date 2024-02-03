package com.mysql.cj.jdbc;

import com.mysql.cj.api.PingTarget;
import com.mysql.cj.api.ProfilerEventHandler;
import com.mysql.cj.api.conf.ReadableProperty;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.api.jdbc.Statement;
import com.mysql.cj.api.jdbc.result.ResultSetInternalMethods;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.api.result.Row;
import com.mysql.cj.core.CharsetMapping;
import com.mysql.cj.core.Constants;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.MysqlType;
import com.mysql.cj.core.conf.PropertyDefinitions;
import com.mysql.cj.core.exceptions.AssertionFailedException;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.core.exceptions.CJOperationNotSupportedException;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.StatementIsClosedException;
import com.mysql.cj.core.profiler.ProfilerEventHandlerFactory;
import com.mysql.cj.core.profiler.ProfilerEventImpl;
import com.mysql.cj.core.result.Field;
import com.mysql.cj.core.util.LogUtils;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.core.util.Util;
import com.mysql.cj.jdbc.exceptions.MySQLStatementCancelledException;
import com.mysql.cj.jdbc.exceptions.MySQLTimeoutException;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.jdbc.io.ResultSetFactory;
import com.mysql.cj.jdbc.result.CachedResultSetMetaData;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import com.mysql.cj.jdbc.util.ResultSetUtil;
import com.mysql.cj.mysqla.MysqlaSession;
import com.mysql.cj.mysqla.result.ByteArrayRow;
import com.mysql.cj.mysqla.result.MysqlaColumnDefinition;
import com.mysql.cj.mysqla.result.ResultsetRowsStatic;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class StatementImpl implements Statement {
   protected static final String PING_MARKER = "/* ping */";
   protected static final String[] ON_DUPLICATE_KEY_UPDATE_CLAUSE = new String[]{"ON", "DUPLICATE", "KEY", "UPDATE"};
   public Object cancelTimeoutMutex = new Object();
   static int statementCounter = 1;
   public static final byte USES_VARIABLES_FALSE = 0;
   public static final byte USES_VARIABLES_TRUE = 1;
   public static final byte USES_VARIABLES_UNKNOWN = -1;
   public boolean wasCancelled = false;
   public boolean wasCancelledByTimeout = false;
   protected List<Object> batchedArgs;
   protected String charEncoding = null;
   protected volatile JdbcConnection connection = null;
   protected MysqlaSession session = null;
   protected long connectionId = 0L;
   private String currentCatalog = null;
   protected boolean doEscapeProcessing = true;
   protected ProfilerEventHandler eventSink = null;
   private int fetchSize = 0;
   protected boolean isClosed = false;
   protected long lastInsertId = -1L;
   protected int maxFieldSize = (Integer)PropertyDefinitions.getPropertyDefinition("maxAllowedPacket").getDefaultValue();
   public int maxRows = -1;
   protected Set<ResultSetInternalMethods> openResults = new HashSet();
   protected boolean pedantic = false;
   protected String pointOfOrigin;
   protected boolean profileSQL = false;
   protected ResultSetInternalMethods results = null;
   protected ResultSetInternalMethods generatedKeysResults = null;
   protected int resultSetConcurrency = 0;
   protected int resultSetType = 0;
   protected int statementId;
   protected int timeoutInMillis = 0;
   protected long updateCount = -1L;
   protected boolean useUsageAdvisor = false;
   protected SQLWarning warningChain = null;
   protected boolean clearWarningsCalled = false;
   protected boolean holdResultsOpenOverClose = false;
   protected ArrayList<Row> batchedGeneratedKeys = null;
   protected boolean retrieveGeneratedKeys = false;
   protected boolean continueBatchOnError = false;
   protected PingTarget pingTarget = null;
   private ExceptionInterceptor exceptionInterceptor;
   protected boolean lastQueryIsOnDupKeyUpdate = false;
   protected final AtomicBoolean statementExecuting = new AtomicBoolean(false);
   private boolean isImplicitlyClosingResults = false;
   protected ReadableProperty<Boolean> autoGenerateTestcaseScript;
   protected ReadableProperty<Boolean> dontTrackOpenResources;
   protected ReadableProperty<Boolean> dumpQueriesOnException;
   protected ReadableProperty<Boolean> explainSlowQueries;
   protected ReadableProperty<Boolean> gatherPerfMetrics;
   protected boolean logSlowQueries = false;
   protected ReadableProperty<Integer> slowQueryThresholdMillis;
   protected boolean useCursorFetch = false;
   protected ReadableProperty<Boolean> rewriteBatchedStatements;
   protected ReadableProperty<Integer> maxAllowedPacket;
   protected boolean dontCheckOnDuplicateKeyUpdateInSQL;
   protected ReadableProperty<Boolean> sendFractionalSeconds;
   protected ResultSetFactory resultSetFactory;
   private int originalResultSetType = 0;
   private int originalFetchSize = 0;
   private boolean isPoolable = true;
   private boolean closeOnCompletion = false;

   public StatementImpl(JdbcConnection c, String catalog) throws SQLException {
      if (c != null && !c.isClosed()) {
         this.connection = c;
         this.session = c.getSession();
         this.connectionId = c.getId();
         this.exceptionInterceptor = c.getExceptionInterceptor();
         this.currentCatalog = catalog;
         this.autoGenerateTestcaseScript = c.getPropertySet().getReadableProperty("autoGenerateTestcaseScript");
         this.dontTrackOpenResources = c.getPropertySet().getBooleanReadableProperty("dontTrackOpenResources");
         this.dumpQueriesOnException = c.getPropertySet().getBooleanReadableProperty("dumpQueriesOnException");
         this.explainSlowQueries = c.getPropertySet().getBooleanReadableProperty("explainSlowQueries");
         this.gatherPerfMetrics = c.getPropertySet().getBooleanReadableProperty("gatherPerfMetrics");
         this.continueBatchOnError = (Boolean)c.getPropertySet().getBooleanReadableProperty("continueBatchOnError").getValue();
         this.pedantic = (Boolean)c.getPropertySet().getBooleanReadableProperty("pedantic").getValue();
         this.slowQueryThresholdMillis = c.getPropertySet().getIntegerReadableProperty("slowQueryThresholdMillis");
         this.rewriteBatchedStatements = c.getPropertySet().getBooleanReadableProperty("rewriteBatchedStatements");
         this.charEncoding = (String)c.getPropertySet().getStringReadableProperty("characterEncoding").getValue();
         this.profileSQL = (Boolean)c.getPropertySet().getBooleanReadableProperty("profileSQL").getValue();
         this.useUsageAdvisor = (Boolean)c.getPropertySet().getBooleanReadableProperty("useUsageAdvisor").getValue();
         this.logSlowQueries = (Boolean)c.getPropertySet().getBooleanReadableProperty("logSlowQueries").getValue();
         this.useCursorFetch = (Boolean)c.getPropertySet().getBooleanReadableProperty("useCursorFetch").getValue();
         this.maxAllowedPacket = c.getPropertySet().getIntegerReadableProperty("maxAllowedPacket");
         this.dontCheckOnDuplicateKeyUpdateInSQL = (Boolean)c.getPropertySet().getBooleanReadableProperty("dontCheckOnDuplicateKeyUpdateInSQL").getValue();
         this.sendFractionalSeconds = c.getPropertySet().getBooleanReadableProperty("sendFractionalSeconds");
         this.doEscapeProcessing = (Boolean)c.getPropertySet().getBooleanReadableProperty("enableEscapeProcessing").getValue();
         this.maxFieldSize = (Integer)this.maxAllowedPacket.getValue();
         if (!(Boolean)this.dontTrackOpenResources.getValue()) {
            c.registerStatement(this);
         }

         int defaultFetchSize = (Integer)this.connection.getPropertySet().getIntegerReadableProperty("defaultFetchSize").getValue();
         if (defaultFetchSize != 0) {
            this.setFetchSize(defaultFetchSize);
         }

         boolean profiling = this.profileSQL || this.useUsageAdvisor || this.logSlowQueries;
         if ((Boolean)this.autoGenerateTestcaseScript.getValue() || profiling) {
            this.statementId = statementCounter++;
         }

         if (profiling) {
            this.pointOfOrigin = LogUtils.findCallingClassAndMethod(new Throwable());

            try {
               this.eventSink = ProfilerEventHandlerFactory.getInstance(this.session);
            } catch (CJException var6) {
               throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
            }
         }

         int maxRowsConn = (Integer)this.connection.getPropertySet().getIntegerReadableProperty("maxRows").getValue();
         if (maxRowsConn != -1) {
            this.setMaxRows(maxRowsConn);
         }

         this.holdResultsOpenOverClose = (Boolean)this.connection.getPropertySet().getModifiableProperty("holdResultsOpenOverStatementClose").getValue();
         this.resultSetFactory = new ResultSetFactory(this.connection, this);
      } else {
         throw SQLError.createSQLException(Messages.getString("Statement.0"), "08003", (ExceptionInterceptor)null);
      }
   }

   public void addBatch(String sql) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (this.batchedArgs == null) {
               this.batchedArgs = new ArrayList();
            }

            if (sql != null) {
               this.batchedArgs.add(sql);
            }

         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public List<Object> getBatchedArgs() {
      return this.batchedArgs == null ? null : Collections.unmodifiableList(this.batchedArgs);
   }

   public void cancel() throws SQLException {
      try {
         if (this.statementExecuting.get()) {
            if (!this.isClosed && this.connection != null) {
               JdbcConnection cancelConn = null;
               java.sql.Statement cancelStmt = null;

               try {
                  cancelConn = this.connection.duplicate();
                  cancelStmt = cancelConn.createStatement();
                  cancelStmt.execute("KILL QUERY " + this.session.getThreadId());
                  this.wasCancelled = true;
               } finally {
                  if (cancelStmt != null) {
                     cancelStmt.close();
                  }

                  if (cancelConn != null) {
                     cancelConn.close();
                  }

               }
            }

         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   protected JdbcConnection checkClosed() {
      JdbcConnection c = this.connection;
      if (c == null) {
         throw (StatementIsClosedException)ExceptionFactory.createException(StatementIsClosedException.class, Messages.getString("Statement.AlreadyClosed"), this.getExceptionInterceptor());
      } else {
         return c;
      }
   }

   protected void checkForDml(String sql, char firstStatementChar) throws SQLException {
      if (firstStatementChar == 'I' || firstStatementChar == 'U' || firstStatementChar == 'D' || firstStatementChar == 'A' || firstStatementChar == 'C' || firstStatementChar == 'T' || firstStatementChar == 'R') {
         String noCommentSql = StringUtils.stripComments(sql, "'\"", "'\"", true, false, true, true);
         if (StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "INSERT") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "UPDATE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "DELETE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "DROP") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "CREATE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "ALTER") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "TRUNCATE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "RENAME")) {
            throw SQLError.createSQLException(Messages.getString("Statement.57"), "S1009", this.getExceptionInterceptor());
         }
      }

   }

   protected void checkNullOrEmptyQuery(String sql) throws SQLException {
      if (sql == null) {
         throw SQLError.createSQLException(Messages.getString("Statement.59"), "S1009", this.getExceptionInterceptor());
      } else if (sql.length() == 0) {
         throw SQLError.createSQLException(Messages.getString("Statement.61"), "S1009", this.getExceptionInterceptor());
      }
   }

   public void clearBatch() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (this.batchedArgs != null) {
               this.batchedArgs.clear();
            }

         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void clearWarnings() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.clearWarningsCalled = true;
            this.warningChain = null;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void close() throws SQLException {
      try {
         this.realClose(true, true);
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   protected void closeAllOpenResults() throws SQLException {
      JdbcConnection locallyScopedConn = this.connection;
      if (locallyScopedConn != null) {
         synchronized(locallyScopedConn.getConnectionMutex()) {
            if (this.openResults != null) {
               Iterator var3 = this.openResults.iterator();

               while(var3.hasNext()) {
                  ResultSetInternalMethods element = (ResultSetInternalMethods)var3.next();

                  try {
                     element.realClose(false);
                  } catch (SQLException var7) {
                     AssertionFailedException.shouldNotHappen((Exception)var7);
                  }
               }

               this.openResults.clear();
            }

         }
      }
   }

   protected void implicitlyCloseAllOpenResults() throws SQLException {
      this.isImplicitlyClosingResults = true;

      try {
         if (!this.holdResultsOpenOverClose && !(Boolean)this.dontTrackOpenResources.getValue()) {
            if (this.results != null) {
               this.results.realClose(false);
            }

            if (this.generatedKeysResults != null) {
               this.generatedKeysResults.realClose(false);
            }

            this.closeAllOpenResults();
         }
      } finally {
         this.isImplicitlyClosingResults = false;
      }

   }

   public void removeOpenResultSet(ResultSetInternalMethods rs) {
      try {
         try {
            synchronized(this.checkClosed().getConnectionMutex()) {
               if (this.openResults != null) {
                  this.openResults.remove(rs);
               }

               boolean hasMoreResults = rs.getNextResultset() != null;
               if (this.results == rs && !hasMoreResults) {
                  this.results = null;
               }

               if (this.generatedKeysResults == rs) {
                  this.generatedKeysResults = null;
               }

               if (!this.isImplicitlyClosingResults && !hasMoreResults) {
                  this.checkAndPerformCloseOnCompletionAction();
               }
            }
         } catch (StatementIsClosedException var7) {
         }

      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   public int getOpenResultSetCount() {
      try {
         try {
            synchronized(this.checkClosed().getConnectionMutex()) {
               return this.openResults != null ? this.openResults.size() : 0;
            }
         } catch (StatementIsClosedException var5) {
            return 0;
         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   private void checkAndPerformCloseOnCompletionAction() {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (this.isCloseOnCompletion() && !(Boolean)this.dontTrackOpenResources.getValue() && this.getOpenResultSetCount() == 0 && (this.results == null || !this.results.hasRows() || this.results.isClosed()) && (this.generatedKeysResults == null || !this.generatedKeysResults.hasRows() || this.generatedKeysResults.isClosed())) {
               this.realClose(false, false);
            }
         }
      } catch (SQLException var4) {
      }

   }

   private ResultSetInternalMethods createResultSetUsingServerFetch(String sql) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            java.sql.PreparedStatement pStmt = this.connection.prepareStatement(sql, this.resultSetType, this.resultSetConcurrency);
            pStmt.setFetchSize(this.fetchSize);
            if (this.maxRows > -1) {
               pStmt.setMaxRows(this.maxRows);
            }

            this.statementBegins();
            pStmt.execute();
            ResultSetInternalMethods rs = ((StatementImpl)pStmt).getResultSetInternal();
            rs.setStatementUsedForFetchingRows((PreparedStatement)pStmt);
            this.results = rs;
            return rs;
         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   protected boolean createStreamingResultSet() {
      return this.resultSetType == 1003 && this.resultSetConcurrency == 1007 && this.fetchSize == Integer.MIN_VALUE;
   }

   public void enableStreamingResults() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.originalResultSetType = this.resultSetType;
            this.originalFetchSize = this.fetchSize;
            this.setFetchSize(Integer.MIN_VALUE);
            this.setResultSetType(1003);
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void disableStreamingResults() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (this.fetchSize == Integer.MIN_VALUE && this.resultSetType == 1003) {
               this.setFetchSize(this.originalFetchSize);
               this.setResultSetType(this.originalResultSetType);
            }

         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   protected void setupStreamingTimeout(JdbcConnection con) throws SQLException {
      int netTimeoutForStreamingResults = (Integer)this.session.getPropertySet().getIntegerReadableProperty("netTimeoutForStreamingResults").getValue();
      if (this.createStreamingResultSet() && netTimeoutForStreamingResults > 0) {
         this.executeSimpleNonQuery(con, "SET net_write_timeout=" + netTimeoutForStreamingResults);
      }

   }

   public boolean execute(String sql) throws SQLException {
      try {
         return this.executeInternal(sql, false);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   private boolean executeInternal(String sql, boolean returnGeneratedKeys) throws SQLException {
      try {
         JdbcConnection locallyScopedConn = this.checkClosed();
         synchronized(locallyScopedConn.getConnectionMutex()) {
            this.checkClosed();
            this.checkNullOrEmptyQuery(sql);
            this.resetCancelledState();
            char firstNonWsChar = StringUtils.firstAlphaCharUc(sql, findStartOfStatement(sql));
            boolean maybeSelect = firstNonWsChar == 'S';
            this.retrieveGeneratedKeys = returnGeneratedKeys;
            this.lastQueryIsOnDupKeyUpdate = returnGeneratedKeys && firstNonWsChar == 'I' && this.containsOnDuplicateKeyInString(sql);
            if (!maybeSelect && locallyScopedConn.isReadOnly()) {
               throw SQLError.createSQLException(Messages.getString("Statement.27") + Messages.getString("Statement.28"), "S1009", this.getExceptionInterceptor());
            } else {
               boolean readInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled();
               if (returnGeneratedKeys && firstNonWsChar == 'R') {
                  locallyScopedConn.setReadInfoMsgEnabled(true);
               }

               boolean var36;
               try {
                  this.setupStreamingTimeout(locallyScopedConn);
                  if (this.doEscapeProcessing) {
                     Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, this.session.getDefaultTimeZone(), this.session.serverSupportsFracSecs(), this.getExceptionInterceptor());
                     if (escapedSqlResult instanceof String) {
                        sql = (String)escapedSqlResult;
                     } else {
                        sql = ((EscapeProcessorResult)escapedSqlResult).escapedSql;
                     }
                  }

                  this.implicitlyCloseAllOpenResults();
                  if (sql.charAt(0) == '/' && sql.startsWith("/* ping */")) {
                     this.doPingInstead();
                     boolean var35 = true;
                     return var35;
                  }

                  CachedResultSetMetaData cachedMetaData = null;
                  ResultSetInternalMethods rs = null;
                  this.batchedGeneratedKeys = null;
                  if (this.useServerFetch()) {
                     rs = this.createResultSetUsingServerFetch(sql);
                  } else {
                     StatementImpl.CancelTask timeoutTask = null;
                     String oldCatalog = null;

                     try {
                        if ((Boolean)locallyScopedConn.getPropertySet().getBooleanReadableProperty("enableQueryTimeouts").getValue() && this.timeoutInMillis != 0) {
                           timeoutTask = new StatementImpl.CancelTask(this);
                           locallyScopedConn.getCancelTimer().schedule(timeoutTask, (long)this.timeoutInMillis);
                        }

                        if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) {
                           oldCatalog = locallyScopedConn.getCatalog();
                           locallyScopedConn.setCatalog(this.currentCatalog);
                        }

                        if ((Boolean)locallyScopedConn.getPropertySet().getBooleanReadableProperty("cacheResultSetMetadata").getValue()) {
                           cachedMetaData = locallyScopedConn.getCachedMetaData(sql);
                        }

                        locallyScopedConn.setSessionMaxRows(maybeSelect ? this.maxRows : -1);
                        this.statementBegins();
                        rs = locallyScopedConn.execSQL(this, sql, this.maxRows, (PacketPayload)null, this.createStreamingResultSet(), this.currentCatalog, cachedMetaData);
                        if (timeoutTask != null) {
                           if (timeoutTask.caughtWhileCancelling != null) {
                              throw timeoutTask.caughtWhileCancelling;
                           }

                           timeoutTask.cancel();
                           timeoutTask = null;
                        }

                        synchronized(this.cancelTimeoutMutex) {
                           if (this.wasCancelled) {
                              SQLException cause = null;
                              if (this.wasCancelledByTimeout) {
                                 cause = new MySQLTimeoutException();
                              } else {
                                 cause = new MySQLStatementCancelledException();
                              }

                              this.resetCancelledState();
                              throw cause;
                           }
                        }
                     } finally {
                        if (timeoutTask != null) {
                           timeoutTask.cancel();
                           locallyScopedConn.getCancelTimer().purge();
                        }

                        if (oldCatalog != null) {
                           locallyScopedConn.setCatalog(oldCatalog);
                        }

                     }
                  }

                  if (rs != null) {
                     this.lastInsertId = rs.getUpdateID();
                     this.results = rs;
                     rs.setFirstCharOfQuery(firstNonWsChar);
                     if (rs.hasRows()) {
                        if (cachedMetaData != null) {
                           locallyScopedConn.initializeResultsMetadataFromCache(sql, cachedMetaData, this.results);
                        } else if ((Boolean)this.session.getPropertySet().getBooleanReadableProperty("cacheResultSetMetadata").getValue()) {
                           locallyScopedConn.initializeResultsMetadataFromCache(sql, (CachedResultSetMetaData)null, this.results);
                        }
                     }
                  }

                  var36 = rs != null && rs.hasRows();
               } finally {
                  locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
                  this.statementExecuting.set(false);
               }

               return var36;
            }
         }
      } catch (CJException var33) {
         throw SQLExceptionsMapping.translateException(var33, this.getExceptionInterceptor());
      }
   }

   protected void statementBegins() {
      this.clearWarningsCalled = false;
      this.statementExecuting.set(true);
   }

   public void resetCancelledState() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (this.cancelTimeoutMutex != null) {
               synchronized(this.cancelTimeoutMutex) {
                  this.wasCancelled = false;
                  this.wasCancelledByTimeout = false;
               }

            }
         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   public boolean execute(String sql, int returnGeneratedKeys) throws SQLException {
      try {
         return this.executeInternal(sql, returnGeneratedKeys == 1);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public boolean execute(String sql, int[] generatedKeyIndices) throws SQLException {
      try {
         return this.executeInternal(sql, generatedKeyIndices != null && generatedKeyIndices.length > 0);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public boolean execute(String sql, String[] generatedKeyNames) throws SQLException {
      try {
         return this.executeInternal(sql, generatedKeyNames != null && generatedKeyNames.length > 0);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public int[] executeBatch() throws SQLException {
      try {
         return Util.truncateAndConvertToInt(this.executeBatchInternal());
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   protected long[] executeBatchInternal() throws SQLException {
      JdbcConnection locallyScopedConn = this.checkClosed();
      synchronized(locallyScopedConn.getConnectionMutex()) {
         if (locallyScopedConn.isReadOnly()) {
            throw SQLError.createSQLException(Messages.getString("Statement.34") + Messages.getString("Statement.35"), "S1009", this.getExceptionInterceptor());
         } else {
            this.implicitlyCloseAllOpenResults();
            if (this.batchedArgs != null && this.batchedArgs.size() != 0) {
               int individualStatementTimeout = this.timeoutInMillis;
               this.timeoutInMillis = 0;
               StatementImpl.CancelTask timeoutTask = null;

               try {
                  this.resetCancelledState();
                  this.statementBegins();

                  try {
                     this.retrieveGeneratedKeys = true;
                     long[] updateCounts = null;
                     long[] nbrCommands;
                     if (this.batchedArgs != null) {
                        nbrCommands = (long[])this.batchedArgs.size();
                        this.batchedGeneratedKeys = new ArrayList(this.batchedArgs.size());
                        boolean multiQueriesEnabled = (Boolean)locallyScopedConn.getPropertySet().getBooleanReadableProperty("allowMultiQueries").getValue();
                        Object sqlEx;
                        if (multiQueriesEnabled || (Boolean)locallyScopedConn.getPropertySet().getBooleanReadableProperty("rewriteBatchedStatements").getValue() && nbrCommands > 4) {
                           sqlEx = this.executeBatchUsingMultiQueries(multiQueriesEnabled, (int)nbrCommands, individualStatementTimeout);
                           return (long[])sqlEx;
                        }

                        if ((Boolean)locallyScopedConn.getPropertySet().getBooleanReadableProperty("enableQueryTimeouts").getValue() && individualStatementTimeout != 0) {
                           timeoutTask = new StatementImpl.CancelTask(this);
                           locallyScopedConn.getCancelTimer().schedule(timeoutTask, (long)individualStatementTimeout);
                        }

                        updateCounts = new long[nbrCommands];

                        for(int i = 0; i < nbrCommands; ++i) {
                           updateCounts[i] = -3L;
                        }

                        sqlEx = null;
                        int commandIndex = false;
                        int commandIndex = 0;

                        while(true) {
                           if (commandIndex < nbrCommands) {
                              label480: {
                                 try {
                                    String sql = (String)this.batchedArgs.get(commandIndex);
                                    updateCounts[commandIndex] = this.executeUpdateInternal(sql, true, true);
                                    this.getBatchedGeneratedKeys(this.results.getFirstCharOfQuery() == 'I' && this.containsOnDuplicateKeyInString(sql) ? 1 : 0);
                                 } catch (SQLException var24) {
                                    updateCounts[commandIndex] = -3L;
                                    if (!this.continueBatchOnError || var24 instanceof MySQLTimeoutException || var24 instanceof MySQLStatementCancelledException || this.hasDeadlockOrTimeoutRolledBackTx(var24)) {
                                       long[] newUpdateCounts = new long[commandIndex];
                                       if (this.hasDeadlockOrTimeoutRolledBackTx(var24)) {
                                          for(int i = 0; i < newUpdateCounts.length; ++i) {
                                             newUpdateCounts[i] = -3L;
                                          }
                                       } else {
                                          System.arraycopy(updateCounts, 0, newUpdateCounts, 0, commandIndex);
                                       }

                                       sqlEx = var24;
                                       break label480;
                                    }

                                    sqlEx = var24;
                                 }

                                 ++commandIndex;
                                 continue;
                              }
                           }

                           if (sqlEx != null) {
                              throw SQLError.createBatchUpdateException((SQLException)sqlEx, updateCounts, this.getExceptionInterceptor());
                           }
                           break;
                        }
                     }

                     if (timeoutTask != null) {
                        if (timeoutTask.caughtWhileCancelling != null) {
                           throw timeoutTask.caughtWhileCancelling;
                        }

                        timeoutTask.cancel();
                        locallyScopedConn.getCancelTimer().purge();
                        timeoutTask = null;
                     }

                     nbrCommands = updateCounts != null ? updateCounts : new long[0];
                     return nbrCommands;
                  } finally {
                     this.statementExecuting.set(false);
                  }
               } finally {
                  if (timeoutTask != null) {
                     timeoutTask.cancel();
                     locallyScopedConn.getCancelTimer().purge();
                  }

                  this.resetCancelledState();
                  this.timeoutInMillis = individualStatementTimeout;
                  this.clearBatch();
               }
            } else {
               return new long[0];
            }
         }
      }
   }

   protected final boolean hasDeadlockOrTimeoutRolledBackTx(SQLException ex) {
      int vendorCode = ex.getErrorCode();
      switch(vendorCode) {
      case 1205:
         return false;
      case 1206:
      case 1213:
         return true;
      default:
         return false;
      }
   }

   private long[] executeBatchUsingMultiQueries(boolean multiQueriesEnabled, int nbrCommands, int individualStatementTimeout) throws SQLException {
      try {
         JdbcConnection locallyScopedConn = this.checkClosed();
         synchronized(locallyScopedConn.getConnectionMutex()) {
            if (!multiQueriesEnabled) {
               this.session.enableMultiQueries();
            }

            java.sql.Statement batchStmt = null;
            StatementImpl.CancelTask timeoutTask = null;

            long[] var52;
            try {
               long[] updateCounts = new long[nbrCommands];

               int commandIndex;
               for(commandIndex = 0; commandIndex < nbrCommands; ++commandIndex) {
                  updateCounts[commandIndex] = -3L;
               }

               int commandIndex = false;
               StringBuilder queryBuf = new StringBuilder();
               batchStmt = locallyScopedConn.createStatement();
               if ((Boolean)locallyScopedConn.getPropertySet().getBooleanReadableProperty("enableQueryTimeouts").getValue() && individualStatementTimeout != 0) {
                  timeoutTask = new StatementImpl.CancelTask((StatementImpl)batchStmt);
                  locallyScopedConn.getCancelTimer().schedule(timeoutTask, (long)individualStatementTimeout);
               }

               int counter = 0;
               int numberOfBytesPerChar = 1;
               String connectionEncoding = (String)locallyScopedConn.getPropertySet().getStringReadableProperty("characterEncoding").getValue();
               if (StringUtils.startsWithIgnoreCase(connectionEncoding, "utf")) {
                  numberOfBytesPerChar = 3;
               } else if (CharsetMapping.isMultibyteCharset(connectionEncoding)) {
                  numberOfBytesPerChar = 2;
               }

               int escapeAdjust = 1;
               batchStmt.setEscapeProcessing(this.doEscapeProcessing);
               if (this.doEscapeProcessing) {
                  escapeAdjust = 2;
               }

               SQLException sqlEx = null;
               int argumentSetsInBatchSoFar = 0;

               for(commandIndex = 0; commandIndex < nbrCommands; ++commandIndex) {
                  String nextQuery = (String)this.batchedArgs.get(commandIndex);
                  if (((queryBuf.length() + nextQuery.length()) * numberOfBytesPerChar + 1 + 4) * escapeAdjust + 32 > (Integer)this.maxAllowedPacket.getValue()) {
                     try {
                        batchStmt.execute(queryBuf.toString(), 1);
                     } catch (SQLException var46) {
                        sqlEx = this.handleExceptionForBatch(commandIndex, argumentSetsInBatchSoFar, updateCounts, var46);
                     }

                     counter = this.processMultiCountsAndKeys((StatementImpl)batchStmt, counter, updateCounts);
                     queryBuf = new StringBuilder();
                     argumentSetsInBatchSoFar = 0;
                  }

                  queryBuf.append(nextQuery);
                  queryBuf.append(";");
                  ++argumentSetsInBatchSoFar;
               }

               if (queryBuf.length() > 0) {
                  try {
                     batchStmt.execute(queryBuf.toString(), 1);
                  } catch (SQLException var45) {
                     sqlEx = this.handleExceptionForBatch(commandIndex - 1, argumentSetsInBatchSoFar, updateCounts, var45);
                  }

                  this.processMultiCountsAndKeys((StatementImpl)batchStmt, counter, updateCounts);
               }

               if (timeoutTask != null) {
                  if (timeoutTask.caughtWhileCancelling != null) {
                     throw timeoutTask.caughtWhileCancelling;
                  }

                  timeoutTask.cancel();
                  locallyScopedConn.getCancelTimer().purge();
                  timeoutTask = null;
               }

               if (sqlEx != null) {
                  throw SQLError.createBatchUpdateException(sqlEx, updateCounts, this.getExceptionInterceptor());
               }

               var52 = updateCounts != null ? updateCounts : new long[0];
            } finally {
               if (timeoutTask != null) {
                  timeoutTask.cancel();
                  locallyScopedConn.getCancelTimer().purge();
               }

               this.resetCancelledState();

               try {
                  if (batchStmt != null) {
                     batchStmt.close();
                  }
               } finally {
                  if (!multiQueriesEnabled) {
                     this.session.disableMultiQueries();
                  }

               }

            }

            return var52;
         }
      } catch (CJException var50) {
         throw SQLExceptionsMapping.translateException(var50, this.getExceptionInterceptor());
      }
   }

   protected int processMultiCountsAndKeys(StatementImpl batchedStatement, int updateCountCounter, long[] updateCounts) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            updateCounts[updateCountCounter++] = batchedStatement.getLargeUpdateCount();
            boolean doGenKeys = this.batchedGeneratedKeys != null;
            byte[][] row = (byte[][])null;
            long generatedKey;
            if (doGenKeys) {
               generatedKey = batchedStatement.getLastInsertID();
               row = new byte[][]{StringUtils.getBytes(Long.toString(generatedKey))};
               this.batchedGeneratedKeys.add(new ByteArrayRow(row, this.getExceptionInterceptor()));
            }

            while(batchedStatement.getMoreResults() || batchedStatement.getLargeUpdateCount() != -1L) {
               updateCounts[updateCountCounter++] = batchedStatement.getLargeUpdateCount();
               if (doGenKeys) {
                  generatedKey = batchedStatement.getLastInsertID();
                  row = new byte[][]{StringUtils.getBytes(Long.toString(generatedKey))};
                  this.batchedGeneratedKeys.add(new ByteArrayRow(row, this.getExceptionInterceptor()));
               }
            }

            return updateCountCounter;
         }
      } catch (CJException var12) {
         throw SQLExceptionsMapping.translateException(var12, this.getExceptionInterceptor());
      }
   }

   protected SQLException handleExceptionForBatch(int endOfBatchIndex, int numValuesPerBatch, long[] updateCounts, SQLException ex) throws BatchUpdateException, SQLException {
      for(int j = endOfBatchIndex; j > endOfBatchIndex - numValuesPerBatch; --j) {
         updateCounts[j] = -3L;
      }

      if (this.continueBatchOnError && !(ex instanceof MySQLTimeoutException) && !(ex instanceof MySQLStatementCancelledException) && !this.hasDeadlockOrTimeoutRolledBackTx(ex)) {
         return ex;
      } else {
         long[] newUpdateCounts = new long[endOfBatchIndex];
         System.arraycopy(updateCounts, 0, newUpdateCounts, 0, endOfBatchIndex);
         throw SQLError.createBatchUpdateException(ex, newUpdateCounts, this.getExceptionInterceptor());
      }
   }

   public ResultSet executeQuery(String sql) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            JdbcConnection locallyScopedConn = this.connection;
            this.retrieveGeneratedKeys = false;
            this.resetCancelledState();
            this.checkNullOrEmptyQuery(sql);
            this.setupStreamingTimeout(locallyScopedConn);
            if (this.doEscapeProcessing) {
               Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, this.session.getDefaultTimeZone(), this.session.serverSupportsFracSecs(), this.getExceptionInterceptor());
               if (escapedSqlResult instanceof String) {
                  sql = (String)escapedSqlResult;
               } else {
                  sql = ((EscapeProcessorResult)escapedSqlResult).escapedSql;
               }
            }

            char firstStatementChar = StringUtils.firstAlphaCharUc(sql, findStartOfStatement(sql));
            if (sql.charAt(0) == '/' && sql.startsWith("/* ping */")) {
               this.doPingInstead();
               return this.results;
            } else {
               this.checkForDml(sql, firstStatementChar);
               this.implicitlyCloseAllOpenResults();
               CachedResultSetMetaData cachedMetaData = null;
               if (this.useServerFetch()) {
                  this.results = this.createResultSetUsingServerFetch(sql);
                  return this.results;
               } else {
                  StatementImpl.CancelTask timeoutTask = null;
                  String oldCatalog = null;

                  try {
                     if ((Boolean)locallyScopedConn.getPropertySet().getBooleanReadableProperty("enableQueryTimeouts").getValue() && this.timeoutInMillis != 0) {
                        timeoutTask = new StatementImpl.CancelTask(this);
                        locallyScopedConn.getCancelTimer().schedule(timeoutTask, (long)this.timeoutInMillis);
                     }

                     if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) {
                        oldCatalog = locallyScopedConn.getCatalog();
                        locallyScopedConn.setCatalog(this.currentCatalog);
                     }

                     if ((Boolean)locallyScopedConn.getPropertySet().getBooleanReadableProperty("cacheResultSetMetadata").getValue()) {
                        cachedMetaData = locallyScopedConn.getCachedMetaData(sql);
                     }

                     locallyScopedConn.setSessionMaxRows(this.maxRows);
                     this.statementBegins();
                     this.results = locallyScopedConn.execSQL(this, sql, this.maxRows, (PacketPayload)null, this.createStreamingResultSet(), this.currentCatalog, cachedMetaData);
                     if (timeoutTask != null) {
                        if (timeoutTask.caughtWhileCancelling != null) {
                           throw timeoutTask.caughtWhileCancelling;
                        }

                        timeoutTask.cancel();
                        locallyScopedConn.getCancelTimer().purge();
                        timeoutTask = null;
                     }

                     synchronized(this.cancelTimeoutMutex) {
                        if (this.wasCancelled) {
                           SQLException cause = null;
                           if (this.wasCancelledByTimeout) {
                              cause = new MySQLTimeoutException();
                           } else {
                              cause = new MySQLStatementCancelledException();
                           }

                           this.resetCancelledState();
                           throw cause;
                        }
                     }
                  } finally {
                     this.statementExecuting.set(false);
                     if (timeoutTask != null) {
                        timeoutTask.cancel();
                        locallyScopedConn.getCancelTimer().purge();
                     }

                     if (oldCatalog != null) {
                        locallyScopedConn.setCatalog(oldCatalog);
                     }

                  }

                  this.lastInsertId = this.results.getUpdateID();
                  if (cachedMetaData != null) {
                     locallyScopedConn.initializeResultsMetadataFromCache(sql, cachedMetaData, this.results);
                  } else if ((Boolean)this.connection.getPropertySet().getBooleanReadableProperty("cacheResultSetMetadata").getValue()) {
                     locallyScopedConn.initializeResultsMetadataFromCache(sql, (CachedResultSetMetaData)null, this.results);
                  }

                  return this.results;
               }
            }
         }
      } catch (CJException var21) {
         throw SQLExceptionsMapping.translateException(var21, this.getExceptionInterceptor());
      }
   }

   protected void doPingInstead() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (this.pingTarget != null) {
               try {
                  this.pingTarget.doPing();
               } catch (SQLException var5) {
                  throw var5;
               } catch (Exception var6) {
                  throw SQLError.createSQLException(var6.getMessage(), "08S01", var6, this.getExceptionInterceptor());
               }
            } else {
               this.connection.ping();
            }

            ResultSetInternalMethods fakeSelectOneResultSet = this.generatePingResultSet();
            this.results = fakeSelectOneResultSet;
         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   protected ResultSetInternalMethods generatePingResultSet() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            String encoding = this.session.getServerSession().getCharacterSetMetadata();
            int collationIndex = this.session.getServerSession().getMetadataCollationIndex();
            Field[] fields = new Field[]{new Field((String)null, "1", collationIndex, encoding, MysqlType.BIGINT, 1)};
            ArrayList<Row> rows = new ArrayList();
            byte[] colVal = new byte[]{49};
            rows.add(new ByteArrayRow(new byte[][]{colVal}, this.getExceptionInterceptor()));
            return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new MysqlaColumnDefinition(fields)));
         }
      } catch (CJException var10) {
         throw SQLExceptionsMapping.translateException(var10, this.getExceptionInterceptor());
      }
   }

   public void executeSimpleNonQuery(JdbcConnection c, String nonQuery) throws SQLException {
      c.execSQL(this, nonQuery, -1, (PacketPayload)null, false, this.currentCatalog, (ColumnDefinition)null, false).close();
   }

   public int executeUpdate(String sql) throws SQLException {
      try {
         return Util.truncateAndConvertToInt(this.executeLargeUpdate(sql));
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   protected long executeUpdateInternal(String sql, boolean isBatch, boolean returnGeneratedKeys) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            JdbcConnection locallyScopedConn = this.connection;
            this.checkNullOrEmptyQuery(sql);
            this.resetCancelledState();
            char firstStatementChar = StringUtils.firstAlphaCharUc(sql, findStartOfStatement(sql));
            this.retrieveGeneratedKeys = returnGeneratedKeys;
            this.lastQueryIsOnDupKeyUpdate = returnGeneratedKeys && firstStatementChar == 'I' && this.containsOnDuplicateKeyInString(sql);
            ResultSetInternalMethods rs = null;
            if (this.doEscapeProcessing) {
               Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, this.session.getDefaultTimeZone(), this.session.serverSupportsFracSecs(), this.getExceptionInterceptor());
               if (escapedSqlResult instanceof String) {
                  sql = (String)escapedSqlResult;
               } else {
                  sql = ((EscapeProcessorResult)escapedSqlResult).escapedSql;
               }
            }

            if (locallyScopedConn.isReadOnly(false)) {
               throw SQLError.createSQLException(Messages.getString("Statement.42") + Messages.getString("Statement.43"), "S1009", this.getExceptionInterceptor());
            } else if (StringUtils.startsWithIgnoreCaseAndWs(sql, "select")) {
               throw SQLError.createSQLException(Messages.getString("Statement.46"), "01S03", this.getExceptionInterceptor());
            } else {
               this.implicitlyCloseAllOpenResults();
               StatementImpl.CancelTask timeoutTask = null;
               String oldCatalog = null;
               boolean readInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled();
               if (returnGeneratedKeys && firstStatementChar == 'R') {
                  locallyScopedConn.setReadInfoMsgEnabled(true);
               }

               try {
                  if ((Boolean)locallyScopedConn.getPropertySet().getBooleanReadableProperty("enableQueryTimeouts").getValue() && this.timeoutInMillis != 0) {
                     timeoutTask = new StatementImpl.CancelTask(this);
                     locallyScopedConn.getCancelTimer().schedule(timeoutTask, (long)this.timeoutInMillis);
                  }

                  if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) {
                     oldCatalog = locallyScopedConn.getCatalog();
                     locallyScopedConn.setCatalog(this.currentCatalog);
                  }

                  locallyScopedConn.setSessionMaxRows(-1);
                  this.statementBegins();
                  rs = locallyScopedConn.execSQL(this, sql, -1, (PacketPayload)null, false, this.currentCatalog, (ColumnDefinition)null, isBatch);
                  if (timeoutTask != null) {
                     if (timeoutTask.caughtWhileCancelling != null) {
                        throw timeoutTask.caughtWhileCancelling;
                     }

                     timeoutTask.cancel();
                     locallyScopedConn.getCancelTimer().purge();
                     timeoutTask = null;
                  }

                  synchronized(this.cancelTimeoutMutex) {
                     if (this.wasCancelled) {
                        SQLException cause = null;
                        if (this.wasCancelledByTimeout) {
                           cause = new MySQLTimeoutException();
                        } else {
                           cause = new MySQLStatementCancelledException();
                        }

                        this.resetCancelledState();
                        throw cause;
                     }
                  }
               } finally {
                  locallyScopedConn.setReadInfoMsgEnabled(readInfoMsgState);
                  if (timeoutTask != null) {
                     timeoutTask.cancel();
                     locallyScopedConn.getCancelTimer().purge();
                  }

                  if (oldCatalog != null) {
                     locallyScopedConn.setCatalog(oldCatalog);
                  }

                  if (!isBatch) {
                     this.statementExecuting.set(false);
                  }

               }

               this.results = rs;
               rs.setFirstCharOfQuery(firstStatementChar);
               this.updateCount = rs.getUpdateCount();
               this.lastInsertId = rs.getUpdateID();
               return this.updateCount;
            }
         }
      } catch (CJException var24) {
         throw SQLExceptionsMapping.translateException(var24, this.getExceptionInterceptor());
      }
   }

   public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
      try {
         return Util.truncateAndConvertToInt(this.executeLargeUpdate(sql, autoGeneratedKeys));
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
      try {
         return Util.truncateAndConvertToInt(this.executeLargeUpdate(sql, columnIndexes));
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public int executeUpdate(String sql, String[] columnNames) throws SQLException {
      try {
         return Util.truncateAndConvertToInt(this.executeLargeUpdate(sql, columnNames));
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public Connection getConnection() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            return this.connection;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public int getFetchDirection() throws SQLException {
      try {
         return 1000;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getFetchSize() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            return this.fetchSize;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public ResultSet getGeneratedKeys() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (!this.retrieveGeneratedKeys) {
               throw SQLError.createSQLException(Messages.getString("Statement.GeneratedKeysNotRequested"), "S1009", this.getExceptionInterceptor());
            } else if (this.batchedGeneratedKeys == null) {
               return this.lastQueryIsOnDupKeyUpdate ? (this.generatedKeysResults = this.getGeneratedKeysInternal(1L)) : (this.generatedKeysResults = this.getGeneratedKeysInternal());
            } else {
               String encoding = this.session.getServerSession().getCharacterSetMetadata();
               int collationIndex = this.session.getServerSession().getMetadataCollationIndex();
               Field[] fields = new Field[]{new Field("", "GENERATED_KEY", collationIndex, encoding, MysqlType.BIGINT_UNSIGNED, 20)};
               this.generatedKeysResults = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(this.batchedGeneratedKeys, new MysqlaColumnDefinition(fields)));
               return this.generatedKeysResults;
            }
         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   protected ResultSetInternalMethods getGeneratedKeysInternal() throws SQLException {
      long numKeys = this.getLargeUpdateCount();
      return this.getGeneratedKeysInternal(numKeys);
   }

   protected ResultSetInternalMethods getGeneratedKeysInternal(long numKeys) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            String encoding = this.session.getServerSession().getCharacterSetMetadata();
            int collationIndex = this.session.getServerSession().getMetadataCollationIndex();
            Field[] fields = new Field[]{new Field("", "GENERATED_KEY", collationIndex, encoding, MysqlType.BIGINT_UNSIGNED, 20)};
            ArrayList<Row> rowSet = new ArrayList();
            long beginAt = this.getLastInsertID();
            if (this.results != null) {
               String serverInfo = this.results.getServerInfo();
               if (numKeys > 0L && this.results.getFirstCharOfQuery() == 'R' && serverInfo != null && serverInfo.length() > 0) {
                  numKeys = this.getRecordCountFromInfo(serverInfo);
               }

               if (beginAt != 0L && numKeys > 0L) {
                  for(int i = 0; (long)i < numKeys; ++i) {
                     byte[][] row = new byte[1][];
                     if (beginAt > 0L) {
                        row[0] = StringUtils.getBytes(Long.toString(beginAt));
                     } else {
                        byte[] asBytes = new byte[]{(byte)((int)(beginAt >>> 56)), (byte)((int)(beginAt >>> 48)), (byte)((int)(beginAt >>> 40)), (byte)((int)(beginAt >>> 32)), (byte)((int)(beginAt >>> 24)), (byte)((int)(beginAt >>> 16)), (byte)((int)(beginAt >>> 8)), (byte)((int)(beginAt & 255L))};
                        BigInteger val = new BigInteger(1, asBytes);
                        row[0] = val.toString().getBytes();
                     }

                     rowSet.add(new ByteArrayRow(row, this.getExceptionInterceptor()));
                     beginAt += (long)this.connection.getAutoIncrementIncrement();
                  }
               }
            }

            ResultSetImpl gkRs = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rowSet, new MysqlaColumnDefinition(fields)));
            return gkRs;
         }
      } catch (CJException var18) {
         throw SQLExceptionsMapping.translateException(var18, this.getExceptionInterceptor());
      }
   }

   public int getId() {
      return this.statementId;
   }

   public long getLastInsertID() {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            return this.lastInsertId;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public long getLongUpdateCount() {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (this.results == null) {
               return -1L;
            } else {
               return this.results.hasRows() ? -1L : this.updateCount;
            }
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public int getMaxFieldSize() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            return this.maxFieldSize;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public int getMaxRows() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            return this.maxRows <= 0 ? 0 : this.maxRows;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public boolean getMoreResults() throws SQLException {
      try {
         return this.getMoreResults(1);
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean getMoreResults(int current) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (this.results == null) {
               return false;
            } else {
               boolean streamingMode = this.createStreamingResultSet();
               if (streamingMode && this.results.hasRows()) {
                  while(true) {
                     if (this.results.next()) {
                        continue;
                     }
                  }
               }

               ResultSetInternalMethods nextResultSet = (ResultSetInternalMethods)this.results.getNextResultset();
               switch(current) {
               case 1:
                  if (this.results != null) {
                     if (!streamingMode && !(Boolean)this.dontTrackOpenResources.getValue()) {
                        this.results.realClose(false);
                     }

                     this.results.clearNextResultset();
                  }
                  break;
               case 2:
                  if (!(Boolean)this.dontTrackOpenResources.getValue()) {
                     this.openResults.add(this.results);
                  }

                  this.results.clearNextResultset();
                  break;
               case 3:
                  if (this.results != null) {
                     if (!streamingMode && !(Boolean)this.dontTrackOpenResources.getValue()) {
                        this.results.realClose(false);
                     }

                     this.results.clearNextResultset();
                  }

                  this.closeAllOpenResults();
                  break;
               default:
                  throw SQLError.createSQLException(Messages.getString("Statement.19"), "S1009", this.getExceptionInterceptor());
               }

               this.results = nextResultSet;
               if (this.results == null) {
                  this.updateCount = -1L;
                  this.lastInsertId = -1L;
               } else if (this.results.hasRows()) {
                  this.updateCount = -1L;
                  this.lastInsertId = -1L;
               } else {
                  this.updateCount = this.results.getUpdateCount();
                  this.lastInsertId = this.results.getUpdateID();
               }

               boolean moreResults = this.results != null && this.results.hasRows();
               if (!moreResults) {
                  this.checkAndPerformCloseOnCompletionAction();
               }

               return moreResults;
            }
         }
      } catch (CJException var9) {
         throw SQLExceptionsMapping.translateException(var9, this.getExceptionInterceptor());
      }
   }

   public int getQueryTimeout() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            return this.timeoutInMillis / 1000;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   private long getRecordCountFromInfo(String serverInfo) {
      StringBuilder recordsBuf = new StringBuilder();
      long recordsCount = 0L;
      long duplicatesCount = 0L;
      char c = 0;
      int length = serverInfo.length();

      int i;
      for(i = 0; i < length; ++i) {
         c = serverInfo.charAt(i);
         if (Character.isDigit(c)) {
            break;
         }
      }

      recordsBuf.append(c);
      ++i;

      while(i < length) {
         c = serverInfo.charAt(i);
         if (!Character.isDigit(c)) {
            break;
         }

         recordsBuf.append(c);
         ++i;
      }

      recordsCount = Long.parseLong(recordsBuf.toString());

      StringBuilder duplicatesBuf;
      for(duplicatesBuf = new StringBuilder(); i < length; ++i) {
         c = serverInfo.charAt(i);
         if (Character.isDigit(c)) {
            break;
         }
      }

      duplicatesBuf.append(c);
      ++i;

      while(i < length) {
         c = serverInfo.charAt(i);
         if (!Character.isDigit(c)) {
            break;
         }

         duplicatesBuf.append(c);
         ++i;
      }

      duplicatesCount = Long.parseLong(duplicatesBuf.toString());
      return recordsCount - duplicatesCount;
   }

   public ResultSet getResultSet() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            return this.results != null && this.results.hasRows() ? this.results : null;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public int getResultSetConcurrency() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            return this.resultSetConcurrency;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public int getResultSetHoldability() throws SQLException {
      try {
         return 1;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   protected ResultSetInternalMethods getResultSetInternal() {
      try {
         try {
            synchronized(this.checkClosed().getConnectionMutex()) {
               return this.results;
            }
         } catch (StatementIsClosedException var5) {
            return this.results;
         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public int getResultSetType() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            return this.resultSetType;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public int getUpdateCount() throws SQLException {
      try {
         return Util.truncateAndConvertToInt(this.getLargeUpdateCount());
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public SQLWarning getWarnings() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (this.clearWarningsCalled) {
               return null;
            } else {
               SQLWarning pendingWarningsFromServer = ResultSetUtil.convertShowWarningsToSQLWarnings(this.connection);
               if (this.warningChain != null) {
                  this.warningChain.setNextWarning(pendingWarningsFromServer);
               } else {
                  this.warningChain = pendingWarningsFromServer;
               }

               return this.warningChain;
            }
         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   protected void realClose(boolean calledExplicitly, boolean closeOpenResults) throws SQLException {
      JdbcConnection locallyScopedConn = this.connection;
      if (locallyScopedConn != null && !this.isClosed) {
         if (!(Boolean)this.dontTrackOpenResources.getValue()) {
            locallyScopedConn.unregisterStatement(this);
         }

         if (this.useUsageAdvisor && !calledExplicitly) {
            String message = Messages.getString("Statement.63") + Messages.getString("Statement.64");
            this.eventSink.consumeEvent(new ProfilerEventImpl((byte)0, "", this.currentCatalog, this.connectionId, this.getId(), -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, (String)null, this.pointOfOrigin, message));
         }

         if (closeOpenResults) {
            closeOpenResults = !this.holdResultsOpenOverClose && !(Boolean)this.dontTrackOpenResources.getValue();
         }

         if (closeOpenResults) {
            if (this.results != null) {
               try {
                  this.results.close();
               } catch (Exception var6) {
               }
            }

            if (this.generatedKeysResults != null) {
               try {
                  this.generatedKeysResults.close();
               } catch (Exception var5) {
               }
            }

            this.closeAllOpenResults();
         }

         this.isClosed = true;
         this.results = null;
         this.generatedKeysResults = null;
         this.connection = null;
         this.session = null;
         this.warningChain = null;
         this.openResults = null;
         this.batchedGeneratedKeys = null;
         this.pingTarget = null;
         this.resultSetFactory = null;
      }
   }

   public void setCursorName(String name) throws SQLException {
      try {
         ;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void setEscapeProcessing(boolean enable) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.doEscapeProcessing = enable;
         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void setFetchDirection(int direction) throws SQLException {
      try {
         switch(direction) {
         case 1000:
         case 1001:
         case 1002:
            return;
         default:
            throw SQLError.createSQLException(Messages.getString("Statement.5"), "S1009", this.getExceptionInterceptor());
         }
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void setFetchSize(int rows) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if ((rows >= 0 || rows == Integer.MIN_VALUE) && (this.maxRows <= 0 || rows <= this.getMaxRows())) {
               this.fetchSize = rows;
            } else {
               throw SQLError.createSQLException(Messages.getString("Statement.7"), "S1009", this.getExceptionInterceptor());
            }
         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void setHoldResultsOpenOverClose(boolean holdResultsOpenOverClose) {
      try {
         try {
            synchronized(this.checkClosed().getConnectionMutex()) {
               this.holdResultsOpenOverClose = holdResultsOpenOverClose;
            }
         } catch (StatementIsClosedException var6) {
         }

      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   public void setMaxFieldSize(int max) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (max < 0) {
               throw SQLError.createSQLException(Messages.getString("Statement.11"), "S1009", this.getExceptionInterceptor());
            } else {
               int maxBuf = (Integer)this.maxAllowedPacket.getValue();
               if (max > maxBuf) {
                  throw SQLError.createSQLException(Messages.getString("Statement.13", new Object[]{(long)maxBuf}), "S1009", this.getExceptionInterceptor());
               } else {
                  this.maxFieldSize = max;
               }
            }
         }
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   public void setMaxRows(int max) throws SQLException {
      try {
         this.setLargeMaxRows((long)max);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void setQueryTimeout(int seconds) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (seconds < 0) {
               throw SQLError.createSQLException(Messages.getString("Statement.21"), "S1009", this.getExceptionInterceptor());
            } else {
               this.timeoutInMillis = seconds * 1000;
            }
         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   void setResultSetConcurrency(int concurrencyFlag) throws SQLException {
      try {
         try {
            synchronized(this.checkClosed().getConnectionMutex()) {
               this.resultSetConcurrency = concurrencyFlag;
               this.resultSetFactory = new ResultSetFactory(this.connection, this);
            }
         } catch (StatementIsClosedException var6) {
         }

      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   void setResultSetType(int typeFlag) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.resultSetType = typeFlag;
            this.resultSetFactory = new ResultSetFactory(this.connection, this);
         }
      } catch (StatementIsClosedException var5) {
      }

   }

   protected void getBatchedGeneratedKeys(java.sql.Statement batchedStatement) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (this.retrieveGeneratedKeys) {
               ResultSet rs = null;

               try {
                  rs = batchedStatement.getGeneratedKeys();

                  while(rs.next()) {
                     this.batchedGeneratedKeys.add(new ByteArrayRow(new byte[][]{rs.getBytes(1)}, this.getExceptionInterceptor()));
                  }
               } finally {
                  if (rs != null) {
                     rs.close();
                  }

               }
            }

         }
      } catch (CJException var12) {
         throw SQLExceptionsMapping.translateException(var12, this.getExceptionInterceptor());
      }
   }

   protected void getBatchedGeneratedKeys(int maxKeys) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (this.retrieveGeneratedKeys) {
               ResultSetInternalMethods rs = null;

               try {
                  if (maxKeys == 0) {
                     rs = this.getGeneratedKeysInternal();
                  } else {
                     rs = this.getGeneratedKeysInternal((long)maxKeys);
                  }

                  while(rs.next()) {
                     this.batchedGeneratedKeys.add(new ByteArrayRow(new byte[][]{rs.getBytes(1)}, this.getExceptionInterceptor()));
                  }
               } finally {
                  this.isImplicitlyClosingResults = true;

                  try {
                     if (rs != null) {
                        rs.close();
                     }
                  } finally {
                     this.isImplicitlyClosingResults = false;
                  }

               }
            }

         }
      } catch (CJException var27) {
         throw SQLExceptionsMapping.translateException(var27, this.getExceptionInterceptor());
      }
   }

   private boolean useServerFetch() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            return this.useCursorFetch && this.fetchSize > 0 && this.resultSetConcurrency == 1007 && this.resultSetType == 1003;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public boolean isClosed() throws SQLException {
      try {
         JdbcConnection locallyScopedConn = this.connection;
         if (locallyScopedConn == null) {
            return true;
         } else {
            synchronized(locallyScopedConn.getConnectionMutex()) {
               return this.isClosed;
            }
         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public boolean isPoolable() throws SQLException {
      try {
         return this.isPoolable;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public void setPoolable(boolean poolable) throws SQLException {
      try {
         this.isPoolable = poolable;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
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

   protected static int findStartOfStatement(String sql) {
      int statementStartPos = 0;
      if (StringUtils.startsWithIgnoreCaseAndWs(sql, "/*")) {
         statementStartPos = sql.indexOf("*/");
         if (statementStartPos == -1) {
            statementStartPos = 0;
         } else {
            statementStartPos += 2;
         }
      } else if (StringUtils.startsWithIgnoreCaseAndWs(sql, "--") || StringUtils.startsWithIgnoreCaseAndWs(sql, "#")) {
         statementStartPos = sql.indexOf(10);
         if (statementStartPos == -1) {
            statementStartPos = sql.indexOf(13);
            if (statementStartPos == -1) {
               statementStartPos = 0;
            }
         }
      }

      return statementStartPos;
   }

   public InputStream getLocalInfileInputStream() {
      return this.session.getLocalInfileInputStream();
   }

   public void setLocalInfileInputStream(InputStream stream) {
      this.session.setLocalInfileInputStream(stream);
   }

   public void setPingTarget(PingTarget pingTarget) {
      this.pingTarget = pingTarget;
   }

   public ExceptionInterceptor getExceptionInterceptor() {
      return this.exceptionInterceptor;
   }

   protected boolean containsOnDuplicateKeyInString(String sql) {
      return getOnDuplicateKeyLocation(sql, this.dontCheckOnDuplicateKeyUpdateInSQL, (Boolean)this.rewriteBatchedStatements.getValue(), this.connection.isNoBackslashEscapesSet()) != -1;
   }

   protected static int getOnDuplicateKeyLocation(String sql, boolean dontCheckOnDuplicateKeyUpdateInSQL, boolean rewriteBatchedStatements, boolean noBackslashEscapes) {
      return dontCheckOnDuplicateKeyUpdateInSQL && !rewriteBatchedStatements ? -1 : StringUtils.indexOfIgnoreCase(0, sql, (String[])ON_DUPLICATE_KEY_UPDATE_CLAUSE, "\"'`", "\"'`", noBackslashEscapes ? StringUtils.SEARCH_MODE__MRK_COM_WS : StringUtils.SEARCH_MODE__ALL);
   }

   public void closeOnCompletion() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.closeOnCompletion = true;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public boolean isCloseOnCompletion() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            return this.closeOnCompletion;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public long[] executeLargeBatch() throws SQLException {
      try {
         return this.executeBatchInternal();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public long executeLargeUpdate(String sql) throws SQLException {
      try {
         return this.executeUpdateInternal(sql, false, false);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public long executeLargeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
      try {
         return this.executeUpdateInternal(sql, false, autoGeneratedKeys == 1);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public long executeLargeUpdate(String sql, int[] columnIndexes) throws SQLException {
      try {
         return this.executeUpdateInternal(sql, false, columnIndexes != null && columnIndexes.length > 0);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public long executeLargeUpdate(String sql, String[] columnNames) throws SQLException {
      try {
         return this.executeUpdateInternal(sql, false, columnNames != null && columnNames.length > 0);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public long getLargeMaxRows() throws SQLException {
      try {
         return (long)this.getMaxRows();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public long getLargeUpdateCount() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (this.results == null) {
               return -1L;
            } else {
               return this.results.hasRows() ? -1L : this.results.getUpdateCount();
            }
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void setLargeMaxRows(long max) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (max <= 50000000L && max >= 0L) {
               if (max == 0L) {
                  max = -1L;
               }

               this.maxRows = (int)max;
            } else {
               throw SQLError.createSQLException(Messages.getString("Statement.15") + max + " > " + 50000000 + ".", "S1009", this.getExceptionInterceptor());
            }
         }
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   public String getCurrentCatalog() {
      return this.currentCatalog;
   }

   public long getServerStatementId() {
      throw (CJOperationNotSupportedException)ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("Statement.65"));
   }

   boolean isCursorRequired() throws SQLException {
      return false;
   }

   public ResultSetFactory getResultSetFactory() {
      return this.resultSetFactory;
   }

   class CancelTask extends TimerTask {
      long connectionId = 0L;
      SQLException caughtWhileCancelling = null;
      StatementImpl toCancel;
      Properties origConnProps = null;
      String origConnURL = "";

      CancelTask(StatementImpl cancellee) throws SQLException {
         this.connectionId = cancellee.connectionId;
         this.toCancel = cancellee;
         this.origConnProps = new Properties();
         Properties props = StatementImpl.this.connection.getProperties();
         Enumeration keys = props.propertyNames();

         while(keys.hasMoreElements()) {
            String key = keys.nextElement().toString();
            this.origConnProps.setProperty(key, props.getProperty(key));
         }

         this.origConnURL = StatementImpl.this.connection.getURL();
      }

      public void run() {
         Thread cancelThread = new Thread() {
            public void run() {
               JdbcConnection cancelConn = null;
               java.sql.Statement cancelStmt = null;

               try {
                  if ((Boolean)StatementImpl.this.connection.getPropertySet().getBooleanReadableProperty("queryTimeoutKillsConnection").getValue()) {
                     CancelTask.this.toCancel.wasCancelled = true;
                     CancelTask.this.toCancel.wasCancelledByTimeout = true;
                     StatementImpl.this.connection.realClose(false, false, true, new MySQLStatementCancelledException(Messages.getString("Statement.ConnectionKilledDueToTimeout")));
                  } else {
                     synchronized(StatementImpl.this.cancelTimeoutMutex) {
                        if (CancelTask.this.origConnURL.equals(StatementImpl.this.connection.getURL())) {
                           cancelConn = StatementImpl.this.connection.duplicate();
                           cancelStmt = cancelConn.createStatement();
                           cancelStmt.execute("KILL QUERY " + CancelTask.this.connectionId);
                        } else {
                           try {
                              cancelConn = (JdbcConnection)DriverManager.getConnection(CancelTask.this.origConnURL, CancelTask.this.origConnProps);
                              cancelStmt = cancelConn.createStatement();
                              cancelStmt.execute("KILL QUERY " + CancelTask.this.connectionId);
                           } catch (NullPointerException var23) {
                           }
                        }

                        CancelTask.this.toCancel.wasCancelled = true;
                        CancelTask.this.toCancel.wasCancelledByTimeout = true;
                     }
                  }
               } catch (SQLException var25) {
                  CancelTask.this.caughtWhileCancelling = var25;
               } catch (NullPointerException var26) {
               } finally {
                  if (cancelStmt != null) {
                     try {
                        cancelStmt.close();
                     } catch (SQLException var22) {
                        throw new RuntimeException(var22.toString());
                     }
                  }

                  if (cancelConn != null) {
                     try {
                        cancelConn.close();
                     } catch (SQLException var21) {
                        throw new RuntimeException(var21.toString());
                     }
                  }

                  CancelTask.this.toCancel = null;
                  CancelTask.this.origConnProps = null;
                  CancelTask.this.origConnURL = null;
               }

            }
         };
         cancelThread.start();
      }
   }
}
