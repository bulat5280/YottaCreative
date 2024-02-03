package com.mysql.cj.jdbc;

import com.mysql.cj.api.CacheAdapter;
import com.mysql.cj.api.CacheAdapterFactory;
import com.mysql.cj.api.conf.ModifiableProperty;
import com.mysql.cj.api.conf.ReadableProperty;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.jdbc.ClientInfoProvider;
import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.api.jdbc.Statement;
import com.mysql.cj.api.jdbc.interceptors.ConnectionLifecycleInterceptor;
import com.mysql.cj.api.jdbc.interceptors.StatementInterceptor;
import com.mysql.cj.api.jdbc.result.ResultSetInternalMethods;
import com.mysql.cj.api.log.Log;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.core.CharsetMapping;
import com.mysql.cj.core.Constants;
import com.mysql.cj.core.LicenseConfiguration;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.ServerVersion;
import com.mysql.cj.core.conf.url.HostInfo;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.core.exceptions.ConnectionIsClosedException;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.PasswordExpiredException;
import com.mysql.cj.core.exceptions.UnableToConnectException;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.core.io.SocksProxySocketFactory;
import com.mysql.cj.core.log.LogFactory;
import com.mysql.cj.core.log.StandardLogger;
import com.mysql.cj.core.profiler.ProfilerEventHandlerFactory;
import com.mysql.cj.core.profiler.ProfilerEventImpl;
import com.mysql.cj.core.util.LRUCache;
import com.mysql.cj.core.util.LogUtils;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.core.util.Util;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.jdbc.ha.MultiHostMySQLConnection;
import com.mysql.cj.jdbc.interceptors.NoSubInterceptorWrapper;
import com.mysql.cj.jdbc.io.ResultSetFactory;
import com.mysql.cj.jdbc.result.CachedResultSetMetaData;
import com.mysql.cj.jdbc.result.UpdatableResultSet;
import com.mysql.cj.jdbc.util.ResultSetUtil;
import com.mysql.cj.mysqla.MysqlaSession;
import com.mysql.cj.mysqla.MysqlaUtils;
import java.lang.reflect.InvocationHandler;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.UnsupportedCharsetException;
import java.sql.Array;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLPermission;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Struct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

public class ConnectionImpl extends AbstractJdbcConnection implements JdbcConnection {
   private static final long serialVersionUID = 2877471301981509474L;
   private static final SQLPermission SET_NETWORK_TIMEOUT_PERM = new SQLPermission("setNetworkTimeout");
   private static final SQLPermission ABORT_PERM = new SQLPermission("abort");
   private JdbcConnection proxy = null;
   private InvocationHandler realProxy = null;
   public static Map<?, ?> charsetMap;
   protected static final String DEFAULT_LOGGER_CLASS = StandardLogger.class.getName();
   private static final int HISTOGRAM_BUCKETS = 20;
   private static Map<String, Integer> mapTransIsolationNameToValue = null;
   protected static Map<?, ?> roundRobinStatsMap;
   private static final Map<String, Map<Number, String>> dynamicIndexToCollationMapByUrl = new HashMap();
   private static final Map<String, Map<Integer, String>> dynamicIndexToCharsetMapByUrl = new HashMap();
   private static final Map<String, Map<Integer, String>> customIndexToCharsetMapByUrl = new HashMap();
   private static final Map<String, Map<String, Integer>> customCharsetToMblenMapByUrl = new HashMap();
   private CacheAdapter<String, Map<String, String>> serverConfigCache;
   private long queryTimeCount;
   private double queryTimeSum;
   private double queryTimeSumSquares;
   private double queryTimeMean;
   private transient Timer cancelTimer;
   private List<ConnectionLifecycleInterceptor> connectionLifecycleInterceptors;
   private static final int DEFAULT_RESULT_SET_TYPE = 1003;
   private static final int DEFAULT_RESULT_SET_CONCURRENCY = 1007;
   private static final Random random;
   private boolean autoCommit = true;
   private CacheAdapter<String, PreparedStatement.ParseInfo> cachedPreparedStatementParams;
   private long connectionCreationTimeMillis = 0L;
   private long connectionId;
   private String database = null;
   private java.sql.DatabaseMetaData dbmd = null;
   private Throwable forceClosedReason;
   private MysqlaSession session = null;
   private boolean isClosed = true;
   private boolean isInGlobalTx = false;
   private int isolationLevel = 2;
   private long lastQueryFinishedTime = 0L;
   private long longestQueryTimeMs = 0L;
   private boolean lowerCaseTableNames = false;
   private long maximumNumberTablesAccessed = 0L;
   private long metricsLastReportedMs;
   private long minimumNumberTablesAccessed = Long.MAX_VALUE;
   private boolean needsPing = false;
   private boolean noBackslashEscapes = false;
   private long numberOfPreparedExecutes = 0L;
   private long numberOfPrepares = 0L;
   private long numberOfQueriesIssued = 0L;
   private long numberOfResultSetsCreated = 0L;
   private long[] numTablesMetricsHistBreakpoints;
   private int[] numTablesMetricsHistCounts;
   private long[] oldHistBreakpoints = null;
   private int[] oldHistCounts = null;
   private final CopyOnWriteArrayList<Statement> openStatements = new CopyOnWriteArrayList();
   private LRUCache parsedCallableStatementCache;
   private String password = null;
   private long[] perfMetricsHistBreakpoints;
   private int[] perfMetricsHistCounts;
   private String pointOfOrigin;
   protected Properties props = null;
   private boolean readInfoMsg = false;
   private boolean readOnly = false;
   protected LRUCache resultSetMetadataCache;
   private long shortestQueryTimeMs = Long.MAX_VALUE;
   private double totalQueryTimeMs = 0.0D;
   private Map<String, Class<?>> typeMap;
   private boolean useAnsiQuotes = false;
   private String user = null;
   private boolean useServerPreparedStmts = false;
   private LRUCache serverSideStatementCheckCache;
   private LRUCache serverSideStatementCache;
   private HostInfo origHostInfo;
   private String origHostToConnectTo;
   private int origPortToConnectTo;
   private boolean hasTriedMasterFlag = false;
   private String statementComment = null;
   private boolean storesLowerCaseTableName;
   private List<StatementInterceptor> statementInterceptors;
   private boolean requiresEscapingEncoder;
   private ModifiableProperty<String> characterEncoding;
   private ReadableProperty<Boolean> autoReconnectForPools;
   private ReadableProperty<Boolean> cachePrepStmts;
   private ReadableProperty<Boolean> cacheServerConfiguration;
   private ModifiableProperty<Boolean> autoReconnect;
   private ModifiableProperty<Boolean> profileSQL;
   private ReadableProperty<Boolean> useUsageAdvisor;
   private ReadableProperty<Boolean> reconnectAtTxEnd;
   private ReadableProperty<Boolean> useOldUTF8Behavior;
   private ReadableProperty<Boolean> maintainTimeStats;
   private ReadableProperty<Boolean> emulateUnsupportedPstmts;
   private ReadableProperty<Boolean> gatherPerfMetrics;
   private ReadableProperty<Boolean> ignoreNonTxTables;
   private ReadableProperty<Boolean> pedantic;
   private ReadableProperty<Integer> prepStmtCacheSqlLimit;
   private ReadableProperty<Boolean> useLocalSessionState;
   private ReadableProperty<Boolean> useServerPrepStmts;
   private ReadableProperty<Boolean> processEscapeCodesForPrepStmts;
   private ReadableProperty<Boolean> useLocalTransactionState;
   protected ModifiableProperty<Integer> maxAllowedPacket;
   private ReadableProperty<Boolean> disconnectOnExpiredPasswords;
   private ReadableProperty<Boolean> readOnlyPropagatesToServer;
   protected ResultSetFactory nullStatementResultSetFactory;
   private static final String SERVER_VERSION_STRING_VAR_NAME = "server_version_string";
   private int autoIncrementIncrement = 0;
   private ExceptionInterceptor exceptionInterceptor;
   private ClientInfoProvider infoProvider;

   public String getHost() {
      return this.session.getHostInfo().getHost();
   }

   public boolean isProxySet() {
      return this.proxy != null;
   }

   public void setProxy(JdbcConnection proxy) {
      this.proxy = proxy;
      this.realProxy = this.proxy instanceof MultiHostMySQLConnection ? ((MultiHostMySQLConnection)proxy).getThisAsProxy() : null;
   }

   private JdbcConnection getProxy() {
      return (JdbcConnection)(this.proxy != null ? this.proxy : this);
   }

   public JdbcConnection getMultiHostSafeProxy() {
      return this.getProxy();
   }

   public Object getConnectionMutex() {
      return this.realProxy != null ? this.realProxy : this.getProxy();
   }

   protected static SQLException appendMessageToException(SQLException sqlEx, String messageToAppend, ExceptionInterceptor interceptor) {
      String origMessage = sqlEx.getMessage();
      String sqlState = sqlEx.getSQLState();
      int vendorErrorCode = sqlEx.getErrorCode();
      StringBuilder messageBuf = new StringBuilder(origMessage.length() + messageToAppend.length());
      messageBuf.append(origMessage);
      messageBuf.append(messageToAppend);
      SQLException sqlExceptionWithNewMessage = SQLError.createSQLException(messageBuf.toString(), sqlState, vendorErrorCode, interceptor);
      sqlExceptionWithNewMessage.setStackTrace(sqlEx.getStackTrace());
      return sqlExceptionWithNewMessage;
   }

   public Timer getCancelTimer() {
      synchronized(this.getConnectionMutex()) {
         if (this.cancelTimer == null) {
            this.cancelTimer = new Timer("MySQL Statement Cancellation Timer", Boolean.TRUE);
         }

         return this.cancelTimer;
      }
   }

   public static JdbcConnection getInstance(HostInfo hostInfo) throws SQLException {
      return new ConnectionImpl(hostInfo);
   }

   protected static synchronized int getNextRoundRobinHostIndex(String url, List<?> hostList) {
      int indexRange = hostList.size();
      int index = random.nextInt(indexRange);
      return index;
   }

   private static boolean nullSafeCompare(String s1, String s2) {
      if (s1 == null && s2 == null) {
         return true;
      } else if (s1 == null && s2 != null) {
         return false;
      } else {
         return s1 != null && s1.equals(s2);
      }
   }

   protected ConnectionImpl() {
   }

   public ConnectionImpl(HostInfo hostInfo) throws SQLException {
      try {
         this.connectionCreationTimeMillis = System.currentTimeMillis();
         this.origHostInfo = hostInfo;
         this.origHostToConnectTo = hostInfo.getHost();
         this.origPortToConnectTo = hostInfo.getPort();
         this.nullStatementResultSetFactory = new ResultSetFactory(this, (StatementImpl)null);
         this.session = new MysqlaSession(hostInfo, this.getPropertySet());
         this.characterEncoding = this.getPropertySet().getJdbcModifiableProperty("characterEncoding");
         this.autoReconnectForPools = this.getPropertySet().getBooleanReadableProperty("autoReconnectForPools");
         this.cachePrepStmts = this.getPropertySet().getBooleanReadableProperty("cachePrepStmts");
         this.cacheServerConfiguration = this.getPropertySet().getBooleanReadableProperty("cacheServerConfiguration");
         this.autoReconnect = this.getPropertySet().getModifiableProperty("autoReconnect");
         this.profileSQL = this.getPropertySet().getModifiableProperty("profileSQL");
         this.useUsageAdvisor = this.getPropertySet().getBooleanReadableProperty("useUsageAdvisor");
         this.reconnectAtTxEnd = this.getPropertySet().getBooleanReadableProperty("reconnectAtTxEnd");
         this.useOldUTF8Behavior = this.getPropertySet().getBooleanReadableProperty("useOldUTF8Behavior");
         this.maintainTimeStats = this.getPropertySet().getBooleanReadableProperty("maintainTimeStats");
         this.emulateUnsupportedPstmts = this.getPropertySet().getBooleanReadableProperty("emulateUnsupportedPstmts");
         this.gatherPerfMetrics = this.getPropertySet().getBooleanReadableProperty("gatherPerfMetrics");
         this.ignoreNonTxTables = this.getPropertySet().getBooleanReadableProperty("ignoreNonTxTables");
         this.pedantic = this.getPropertySet().getBooleanReadableProperty("pedantic");
         this.prepStmtCacheSqlLimit = this.getPropertySet().getIntegerReadableProperty("prepStmtCacheSqlLimit");
         this.useLocalSessionState = this.getPropertySet().getBooleanReadableProperty("useLocalSessionState");
         this.useServerPrepStmts = this.getPropertySet().getBooleanReadableProperty("useServerPrepStmts");
         this.processEscapeCodesForPrepStmts = this.getPropertySet().getBooleanReadableProperty("processEscapeCodesForPrepStmts");
         this.useLocalTransactionState = this.getPropertySet().getBooleanReadableProperty("useLocalTransactionState");
         this.maxAllowedPacket = this.getPropertySet().getModifiableProperty("maxAllowedPacket");
         this.disconnectOnExpiredPasswords = this.getPropertySet().getBooleanReadableProperty("disconnectOnExpiredPasswords");
         this.readOnlyPropagatesToServer = this.getPropertySet().getBooleanReadableProperty("readOnlyPropagatesToServer");
         this.database = hostInfo.getDatabase();
         this.user = StringUtils.isNullOrEmpty(hostInfo.getUser()) ? "" : hostInfo.getUser();
         this.password = StringUtils.isNullOrEmpty(hostInfo.getPassword()) ? "" : hostInfo.getPassword();
         this.props = hostInfo.exposeAsProperties();
         this.initializeDriverProperties(this.props);
         this.pointOfOrigin = (Boolean)this.useUsageAdvisor.getValue() ? LogUtils.findCallingClassAndMethod(new Throwable()) : "";
         this.dbmd = this.getMetaData(false, false);
         this.initializeSafeStatementInterceptors();
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }

      try {
         this.createNewIO(false);
         this.unSafeStatementInterceptors();
         NonRegisteringDriver.trackConnection(this);
      } catch (SQLException var4) {
         this.cleanup(var4);
         throw var4;
      } catch (Exception var5) {
         this.cleanup(var5);
         throw SQLError.createSQLException((Boolean)this.propertySet.getBooleanReadableProperty("paranoid").getValue() ? Messages.getString("Connection.0") : Messages.getString("Connection.1", new Object[]{this.session.getHostInfo().getHost(), this.session.getHostInfo().getPort()}), "08S01", var5, this.getExceptionInterceptor());
      }
   }

   public void unSafeStatementInterceptors() throws SQLException {
      try {
         this.statementInterceptors = (List)this.statementInterceptors.stream().map((u) -> {
            return ((NoSubInterceptorWrapper)u).getUnderlyingInterceptor();
         }).collect(Collectors.toList());
         if (this.session != null) {
            this.session.setStatementInterceptors(this.statementInterceptors);
         }

      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public void initializeSafeStatementInterceptors() throws SQLException {
      try {
         this.isClosed = false;
         this.statementInterceptors = (List)Util.loadClasses(this.getPropertySet().getStringReadableProperty("statementInterceptors").getStringValue(), "MysqlIo.BadStatementInterceptor", this.getExceptionInterceptor()).stream().map((o) -> {
            return new NoSubInterceptorWrapper(o.init(this, this.props, this.session.getLog()));
         }).collect(Collectors.toList());
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public List<StatementInterceptor> getStatementInterceptorsInstances() {
      return this.statementInterceptors;
   }

   private void addToHistogram(int[] histogramCounts, long[] histogramBreakpoints, long value, int numberOfTimes, long currentLowerBound, long currentUpperBound) {
      if (histogramCounts == null) {
         this.createInitialHistogram(histogramBreakpoints, currentLowerBound, currentUpperBound);
      } else {
         for(int i = 0; i < 20; ++i) {
            if (histogramBreakpoints[i] >= value) {
               histogramCounts[i] += numberOfTimes;
               break;
            }
         }
      }

   }

   private void addToPerformanceHistogram(long value, int numberOfTimes) {
      this.checkAndCreatePerformanceHistogram();
      this.addToHistogram(this.perfMetricsHistCounts, this.perfMetricsHistBreakpoints, value, numberOfTimes, this.shortestQueryTimeMs == Long.MAX_VALUE ? 0L : this.shortestQueryTimeMs, this.longestQueryTimeMs);
   }

   private void addToTablesAccessedHistogram(long value, int numberOfTimes) {
      this.checkAndCreateTablesAccessedHistogram();
      this.addToHistogram(this.numTablesMetricsHistCounts, this.numTablesMetricsHistBreakpoints, value, numberOfTimes, this.minimumNumberTablesAccessed == Long.MAX_VALUE ? 0L : this.minimumNumberTablesAccessed, this.maximumNumberTablesAccessed);
   }

   private void buildCollationMapping() throws SQLException {
      Map<Integer, String> indexToCharset = null;
      Map<Number, String> sortedCollationMap = null;
      Map<Integer, String> customCharset = null;
      Map<String, Integer> customMblen = null;
      if ((Boolean)this.cacheServerConfiguration.getValue()) {
         synchronized(dynamicIndexToCharsetMapByUrl) {
            indexToCharset = (Map)dynamicIndexToCharsetMapByUrl.get(this.getURL());
            Map var10000 = (Map)dynamicIndexToCollationMapByUrl.get(this.getURL());
            customCharset = (Map)customIndexToCharsetMapByUrl.get(this.getURL());
            customMblen = (Map)customCharsetToMblenMapByUrl.get(this.getURL());
         }
      }

      if (indexToCharset == null) {
         indexToCharset = new HashMap();
         if ((Boolean)this.getPropertySet().getBooleanReadableProperty("detectCustomCollations").getValue()) {
            java.sql.Statement stmt = null;
            ResultSet results = null;

            try {
               sortedCollationMap = new TreeMap();
               customCharset = new HashMap();
               customMblen = new HashMap();
               stmt = this.getMetadataSafeStatement();

               try {
                  results = stmt.executeQuery("SHOW COLLATION");
                  ResultSetUtil.resultSetToMap(sortedCollationMap, results, 3, 2);
               } catch (PasswordExpiredException var36) {
                  if ((Boolean)this.disconnectOnExpiredPasswords.getValue()) {
                     throw var36;
                  }
               } catch (SQLException var37) {
                  if (var37.getErrorCode() != 1820 || (Boolean)this.disconnectOnExpiredPasswords.getValue()) {
                     throw var37;
                  }
               }

               Iterator indexIter = sortedCollationMap.entrySet().iterator();

               while(true) {
                  if (!indexIter.hasNext()) {
                     if (((Map)customMblen).size() > 0) {
                        try {
                           results = stmt.executeQuery("SHOW CHARACTER SET");

                           while(results.next()) {
                              String charsetName = results.getString("Charset");
                              if (((Map)customMblen).containsKey(charsetName)) {
                                 ((Map)customMblen).put(charsetName, results.getInt("Maxlen"));
                              }
                           }
                        } catch (PasswordExpiredException var34) {
                           if ((Boolean)this.disconnectOnExpiredPasswords.getValue()) {
                              throw var34;
                           }
                        } catch (SQLException var35) {
                           if (var35.getErrorCode() != 1820 || (Boolean)this.disconnectOnExpiredPasswords.getValue()) {
                              throw var35;
                           }
                        }
                     }

                     if ((Boolean)this.cacheServerConfiguration.getValue()) {
                        synchronized(dynamicIndexToCharsetMapByUrl) {
                           dynamicIndexToCharsetMapByUrl.put(this.getURL(), indexToCharset);
                           dynamicIndexToCollationMapByUrl.put(this.getURL(), sortedCollationMap);
                           customIndexToCharsetMapByUrl.put(this.getURL(), customCharset);
                           customCharsetToMblenMapByUrl.put(this.getURL(), customMblen);
                        }
                     }
                     break;
                  }

                  Entry<Number, String> indexEntry = (Entry)indexIter.next();
                  int collationIndex = ((Number)indexEntry.getKey()).intValue();
                  String charsetName = (String)indexEntry.getValue();
                  ((Map)indexToCharset).put(collationIndex, charsetName);
                  if (collationIndex >= 255 || !charsetName.equals(CharsetMapping.getMysqlCharsetNameForCollationIndex(collationIndex))) {
                     ((Map)customCharset).put(collationIndex, charsetName);
                  }

                  if (!CharsetMapping.CHARSET_NAME_TO_CHARSET.containsKey(charsetName)) {
                     ((Map)customMblen).put(charsetName, (Object)null);
                  }
               }
            } catch (SQLException var38) {
               throw var38;
            } catch (RuntimeException var39) {
               SQLException sqlEx = SQLError.createSQLException(var39.toString(), "S1009", (ExceptionInterceptor)null);
               sqlEx.initCause(var39);
               throw sqlEx;
            } finally {
               if (results != null) {
                  try {
                     results.close();
                  } catch (SQLException var30) {
                  }
               }

               if (stmt != null) {
                  try {
                     stmt.close();
                  } catch (SQLException var29) {
                  }
               }

            }
         } else {
            for(int i = 1; i < 255; ++i) {
               ((Map)indexToCharset).put(i, CharsetMapping.getMysqlCharsetNameForCollationIndex(i));
            }

            if ((Boolean)this.cacheServerConfiguration.getValue()) {
               synchronized(dynamicIndexToCharsetMapByUrl) {
                  dynamicIndexToCharsetMapByUrl.put(this.getURL(), indexToCharset);
               }
            }
         }
      }

      this.session.setCharsetMaps((Map)indexToCharset, (Map)customCharset, (Map)customMblen);
   }

   private boolean canHandleAsServerPreparedStatement(String sql) throws SQLException {
      if (sql != null && sql.length() != 0) {
         if (!this.useServerPreparedStmts) {
            return false;
         } else if ((Boolean)this.cachePrepStmts.getValue()) {
            synchronized(this.serverSideStatementCheckCache) {
               Boolean flag = (Boolean)this.serverSideStatementCheckCache.get(sql);
               if (flag != null) {
                  return flag;
               } else {
                  boolean canHandle = StringUtils.canHandleAsServerPreparedStatementNoCache(sql, this.getServerVersion());
                  if (sql.length() < (Integer)this.prepStmtCacheSqlLimit.getValue()) {
                     this.serverSideStatementCheckCache.put(sql, canHandle ? Boolean.TRUE : Boolean.FALSE);
                  }

                  return canHandle;
               }
            }
         } else {
            return StringUtils.canHandleAsServerPreparedStatementNoCache(sql, this.getServerVersion());
         }
      } else {
         return true;
      }
   }

   public void changeUser(String userName, String newPassword) throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            this.checkClosed();
            if (userName == null || userName.equals("")) {
               userName = "";
            }

            if (newPassword == null) {
               newPassword = "";
            }

            try {
               this.session.changeUser(userName, newPassword, this.database);
            } catch (CJException var7) {
               if ("28000".equals(var7.getSQLState())) {
                  this.cleanup(var7);
               }

               throw var7;
            }

            this.user = userName;
            this.password = newPassword;
            this.configureClientCharacterSet(true);
            this.setSessionVariables();
            this.setupServerForTruncationChecks();
         }
      } catch (CJException var9) {
         throw SQLExceptionsMapping.translateException(var9, this.getExceptionInterceptor());
      }
   }

   private void checkAndCreatePerformanceHistogram() {
      if (this.perfMetricsHistCounts == null) {
         this.perfMetricsHistCounts = new int[20];
      }

      if (this.perfMetricsHistBreakpoints == null) {
         this.perfMetricsHistBreakpoints = new long[20];
      }

   }

   private void checkAndCreateTablesAccessedHistogram() {
      if (this.numTablesMetricsHistCounts == null) {
         this.numTablesMetricsHistCounts = new int[20];
      }

      if (this.numTablesMetricsHistBreakpoints == null) {
         this.numTablesMetricsHistBreakpoints = new long[20];
      }

   }

   public void checkClosed() {
      if (this.isClosed) {
         throw (ConnectionIsClosedException)ExceptionFactory.createException(ConnectionIsClosedException.class, Messages.getString("Connection.2"), this.forceClosedReason, this.getExceptionInterceptor());
      }
   }

   public void throwConnectionClosedException() throws SQLException {
      try {
         SQLException ex = SQLError.createSQLException(Messages.getString("Connection.2"), "08003", this.getExceptionInterceptor());
         if (this.forceClosedReason != null) {
            ex.initCause(this.forceClosedReason);
         }

         throw ex;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   private void checkTransactionIsolationLevel() throws SQLException {
      String s = this.session.getServerVariable("tx_isolation");
      if (s != null) {
         Integer intTI = (Integer)mapTransIsolationNameToValue.get(s);
         if (intTI != null) {
            this.isolationLevel = intTI;
         }
      }

   }

   public void abortInternal() throws SQLException {
      try {
         this.session.abortInternal();
         this.isClosed = true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   private void cleanup(Throwable whyCleanedUp) {
      try {
         if (this.session != null) {
            if (this.isClosed()) {
               this.session.forceClose();
            } else {
               this.realClose(false, false, false, whyCleanedUp);
            }
         }
      } catch (CJException | SQLException var3) {
      }

      this.isClosed = true;
   }

   /** @deprecated */
   @Deprecated
   public void clearHasTriedMaster() {
      this.hasTriedMasterFlag = false;
   }

   public void clearWarnings() throws SQLException {
      try {
         ;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public java.sql.PreparedStatement clientPrepareStatement(String sql) throws SQLException {
      try {
         return this.clientPrepareStatement(sql, 1003, 1007);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public java.sql.PreparedStatement clientPrepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
      try {
         java.sql.PreparedStatement pStmt = this.clientPrepareStatement(sql);
         ((PreparedStatement)pStmt).setRetrieveGeneratedKeys(autoGenKeyIndex == 1);
         return pStmt;
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public java.sql.PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      try {
         return this.clientPrepareStatement(sql, resultSetType, resultSetConcurrency, true);
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public java.sql.PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency, boolean processEscapeCodesIfNeeded) throws SQLException {
      try {
         this.checkClosed();
         String nativeSql = processEscapeCodesIfNeeded && (Boolean)this.processEscapeCodesForPrepStmts.getValue() ? this.nativeSQL(sql) : sql;
         PreparedStatement pStmt = null;
         if ((Boolean)this.cachePrepStmts.getValue()) {
            PreparedStatement.ParseInfo pStmtInfo = (PreparedStatement.ParseInfo)this.cachedPreparedStatementParams.get(nativeSql);
            if (pStmtInfo == null) {
               pStmt = PreparedStatement.getInstance(this.getMultiHostSafeProxy(), nativeSql, this.database);
               this.cachedPreparedStatementParams.put(nativeSql, pStmt.getParseInfo());
            } else {
               pStmt = PreparedStatement.getInstance(this.getMultiHostSafeProxy(), nativeSql, this.database, pStmtInfo);
            }
         } else {
            pStmt = PreparedStatement.getInstance(this.getMultiHostSafeProxy(), nativeSql, this.database);
         }

         pStmt.setResultSetType(resultSetType);
         pStmt.setResultSetConcurrency(resultSetConcurrency);
         return pStmt;
      } catch (CJException var9) {
         throw SQLExceptionsMapping.translateException(var9, this.getExceptionInterceptor());
      }
   }

   public java.sql.PreparedStatement clientPrepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
      try {
         PreparedStatement pStmt = (PreparedStatement)this.clientPrepareStatement(sql);
         pStmt.setRetrieveGeneratedKeys(autoGenKeyIndexes != null && autoGenKeyIndexes.length > 0);
         return pStmt;
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public java.sql.PreparedStatement clientPrepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
      try {
         PreparedStatement pStmt = (PreparedStatement)this.clientPrepareStatement(sql);
         pStmt.setRetrieveGeneratedKeys(autoGenKeyColNames != null && autoGenKeyColNames.length > 0);
         return pStmt;
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public java.sql.PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      try {
         return this.clientPrepareStatement(sql, resultSetType, resultSetConcurrency, true);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void close() throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            if (this.connectionLifecycleInterceptors != null) {
               (new IterateBlock<ConnectionLifecycleInterceptor>(this.connectionLifecycleInterceptors.iterator()) {
                  void forEach(ConnectionLifecycleInterceptor each) throws SQLException {
                     each.close();
                  }
               }).doForAll();
            }

            this.realClose(true, true, false, (Throwable)null);
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   private void closeAllOpenStatements() throws SQLException {
      SQLException postponedException = null;
      Iterator var2 = this.openStatements.iterator();

      while(var2.hasNext()) {
         Statement stmt = (Statement)var2.next();

         try {
            ((StatementImpl)stmt).realClose(false, true);
         } catch (SQLException var5) {
            postponedException = var5;
         }
      }

      if (postponedException != null) {
         throw postponedException;
      }
   }

   private void closeStatement(java.sql.Statement stmt) {
      if (stmt != null) {
         try {
            stmt.close();
         } catch (SQLException var3) {
         }

         stmt = null;
      }

   }

   public void commit() throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            this.checkClosed();

            try {
               if (this.connectionLifecycleInterceptors != null) {
                  IterateBlock<ConnectionLifecycleInterceptor> iter = new IterateBlock<ConnectionLifecycleInterceptor>(this.connectionLifecycleInterceptors.iterator()) {
                     void forEach(ConnectionLifecycleInterceptor each) throws SQLException {
                        if (!each.commit()) {
                           this.stopIterating = true;
                        }

                     }
                  };
                  iter.doForAll();
                  if (!iter.fullIteration()) {
                     return;
                  }
               }

               if (this.autoCommit) {
                  throw SQLError.createSQLException(Messages.getString("Connection.3"), this.getExceptionInterceptor());
               } else if (!(Boolean)this.useLocalTransactionState.getValue() || this.session.inTransactionOnServer()) {
                  this.execSQL((StatementImpl)null, "commit", -1, (PacketPayload)null, false, this.database, (ColumnDefinition)null, false);
               }
            } catch (SQLException var10) {
               if ("08S01".equals(var10.getSQLState())) {
                  throw SQLError.createSQLException(Messages.getString("Connection.4"), "08007", this.getExceptionInterceptor());
               } else {
                  throw var10;
               }
            } finally {
               this.needsPing = (Boolean)this.reconnectAtTxEnd.getValue();
            }
         }
      } catch (CJException var13) {
         throw SQLExceptionsMapping.translateException(var13, this.getExceptionInterceptor());
      }
   }

   private void configureCharsetProperties() throws SQLException {
      if (this.characterEncoding.getValue() != null) {
         try {
            String testString = "abc";
            StringUtils.getBytes(testString, (String)this.characterEncoding.getValue());
         } catch (WrongArgumentException var6) {
            String oldEncoding = (String)this.characterEncoding.getValue();

            try {
               this.characterEncoding.setValue(CharsetMapping.getJavaEncodingForMysqlCharset(oldEncoding));
            } catch (RuntimeException var5) {
               throw SQLError.createSQLException(var5.toString(), "S1009", var5, (ExceptionInterceptor)null);
            }

            if (this.characterEncoding.getValue() == null) {
               throw SQLError.createSQLException(Messages.getString("Connection.5", new Object[]{oldEncoding}), "01S00", this.getExceptionInterceptor());
            }

            try {
               String testString = "abc";
               StringUtils.getBytes(testString, (String)this.characterEncoding.getValue());
            } catch (WrongArgumentException var4) {
               throw SQLError.createSQLException(var4.getMessage(), "01S00", this.getExceptionInterceptor());
            }
         }
      }

   }

   private boolean configureClientCharacterSet(boolean dontCheckServerMatch) throws SQLException {
      String realJavaEncoding = (String)this.characterEncoding.getValue();
      ModifiableProperty<String> characterSetResults = this.getPropertySet().getModifiableProperty("characterSetResults");
      boolean characterSetAlreadyConfigured = false;

      try {
         characterSetAlreadyConfigured = true;
         this.configureCharsetProperties();
         realJavaEncoding = (String)this.characterEncoding.getValue();

         String onServer;
         try {
            if (this.props != null && this.props.getProperty("com.mysql.cj.testsuite.faultInjection.serverCharsetIndex") != null) {
               this.session.setServerDefaultCollationIndex(Integer.parseInt(this.props.getProperty("com.mysql.cj.testsuite.faultInjection.serverCharsetIndex")));
            }

            onServer = CharsetMapping.getJavaEncodingForCollationIndex(this.session.getServerDefaultCollationIndex());
            if (onServer == null || onServer.length() == 0) {
               if (realJavaEncoding == null) {
                  throw SQLError.createSQLException(Messages.getString("Connection.6", new Object[]{this.session.getServerDefaultCollationIndex()}), "S1000", this.getExceptionInterceptor());
               }

               this.characterEncoding.setValue(realJavaEncoding);
            }

            if ("ISO8859_1".equalsIgnoreCase(onServer)) {
               onServer = "Cp1252";
            }

            if ("UnicodeBig".equalsIgnoreCase(onServer) || "UTF-16".equalsIgnoreCase(onServer) || "UTF-16LE".equalsIgnoreCase(onServer) || "UTF-32".equalsIgnoreCase(onServer)) {
               onServer = "UTF-8";
            }

            this.characterEncoding.setValue(onServer);
         } catch (ArrayIndexOutOfBoundsException var28) {
            if (realJavaEncoding == null) {
               throw SQLError.createSQLException(Messages.getString("Connection.6", new Object[]{this.session.getServerDefaultCollationIndex()}), "S1000", this.getExceptionInterceptor());
            }

            this.characterEncoding.setValue(realJavaEncoding);
         } catch (SQLException var29) {
            throw var29;
         } catch (RuntimeException var30) {
            SQLException sqlEx = SQLError.createSQLException(var30.toString(), "S1009", (ExceptionInterceptor)null);
            sqlEx.initCause(var30);
            throw sqlEx;
         }

         if (this.characterEncoding.getValue() == null) {
            this.characterEncoding.setValue("ISO8859_1");
         }

         boolean isNullOnServer;
         if (realJavaEncoding != null) {
            if (!realJavaEncoding.equalsIgnoreCase("UTF-8") && !realJavaEncoding.equalsIgnoreCase("UTF8")) {
               onServer = CharsetMapping.getMysqlCharsetForJavaEncoding(realJavaEncoding.toUpperCase(Locale.ENGLISH), this.getServerVersion());
               if (onServer != null && (dontCheckServerMatch || !this.session.characterSetNamesMatches(onServer))) {
                  this.execSQL((StatementImpl)null, "SET NAMES " + onServer, -1, (PacketPayload)null, false, this.database, (ColumnDefinition)null, false);
                  this.session.getServerVariables().put("character_set_client", onServer);
                  this.session.getServerVariables().put("character_set_connection", onServer);
               }

               this.characterEncoding.setValue(realJavaEncoding);
            } else {
               boolean useutf8mb4 = CharsetMapping.UTF8MB4_INDEXES.contains(this.session.getServerDefaultCollationIndex());
               if (!(Boolean)this.useOldUTF8Behavior.getValue()) {
                  if (dontCheckServerMatch || !this.session.characterSetNamesMatches("utf8") || !this.session.characterSetNamesMatches("utf8mb4")) {
                     this.execSQL((StatementImpl)null, "SET NAMES " + (useutf8mb4 ? "utf8mb4" : "utf8"), -1, (PacketPayload)null, false, this.database, (ColumnDefinition)null, false);
                     this.session.getServerVariables().put("character_set_client", useutf8mb4 ? "utf8mb4" : "utf8");
                     this.session.getServerVariables().put("character_set_connection", useutf8mb4 ? "utf8mb4" : "utf8");
                  }
               } else {
                  this.execSQL((StatementImpl)null, "SET NAMES latin1", -1, (PacketPayload)null, false, this.database, (ColumnDefinition)null, false);
                  this.session.getServerVariables().put("character_set_client", "latin1");
                  this.session.getServerVariables().put("character_set_connection", "latin1");
               }

               this.characterEncoding.setValue(realJavaEncoding);
            }
         } else if (this.characterEncoding.getValue() != null) {
            onServer = this.getSession().getServerCharset();
            if ((Boolean)this.useOldUTF8Behavior.getValue()) {
               onServer = "latin1";
            }

            isNullOnServer = false;
            if ("ucs2".equalsIgnoreCase(onServer) || "utf16".equalsIgnoreCase(onServer) || "utf16le".equalsIgnoreCase(onServer) || "utf32".equalsIgnoreCase(onServer)) {
               onServer = "utf8";
               isNullOnServer = true;
               if (characterSetResults.getValue() == null) {
                  characterSetResults.setValue("UTF-8");
               }
            }

            if (dontCheckServerMatch || !this.session.characterSetNamesMatches(onServer) || isNullOnServer) {
               try {
                  this.execSQL((StatementImpl)null, "SET NAMES " + onServer, -1, (PacketPayload)null, false, this.database, (ColumnDefinition)null, false);
                  this.session.getServerVariables().put("character_set_client", onServer);
                  this.session.getServerVariables().put("character_set_connection", onServer);
               } catch (PasswordExpiredException var31) {
                  if ((Boolean)this.disconnectOnExpiredPasswords.getValue()) {
                     throw var31;
                  }
               } catch (SQLException var32) {
                  if (var32.getErrorCode() != 1820 || (Boolean)this.disconnectOnExpiredPasswords.getValue()) {
                     throw var32;
                  }
               }
            }

            realJavaEncoding = (String)this.characterEncoding.getValue();
         }

         onServer = null;
         isNullOnServer = false;
         if (this.session.getServerVariables() != null) {
            onServer = this.session.getServerVariable("character_set_results");
            isNullOnServer = onServer == null || "NULL".equalsIgnoreCase(onServer) || onServer.length() == 0;
         }

         String charsetResults;
         if (characterSetResults.getValue() == null) {
            if (!isNullOnServer) {
               try {
                  this.execSQL((StatementImpl)null, "SET character_set_results = NULL", -1, (PacketPayload)null, false, this.database, (ColumnDefinition)null, false);
               } catch (PasswordExpiredException var33) {
                  if ((Boolean)this.disconnectOnExpiredPasswords.getValue()) {
                     throw var33;
                  }
               } catch (SQLException var34) {
                  if (var34.getErrorCode() != 1820 || (Boolean)this.disconnectOnExpiredPasswords.getValue()) {
                     throw var34;
                  }
               }

               this.session.getServerVariables().put("jdbc.local.character_set_results", (Object)null);
            } else {
               this.session.getServerVariables().put("jdbc.local.character_set_results", onServer);
            }
         } else {
            if ((Boolean)this.useOldUTF8Behavior.getValue()) {
               try {
                  this.execSQL((StatementImpl)null, "SET NAMES latin1", -1, (PacketPayload)null, false, this.database, (ColumnDefinition)null, false);
                  this.session.getServerVariables().put("character_set_client", "latin1");
                  this.session.getServerVariables().put("character_set_connection", "latin1");
               } catch (PasswordExpiredException var37) {
                  if ((Boolean)this.disconnectOnExpiredPasswords.getValue()) {
                     throw var37;
                  }
               } catch (SQLException var38) {
                  if (var38.getErrorCode() != 1820 || (Boolean)this.disconnectOnExpiredPasswords.getValue()) {
                     throw var38;
                  }
               }
            }

            charsetResults = (String)characterSetResults.getValue();
            String mysqlEncodingName = null;
            if (!"UTF-8".equalsIgnoreCase(charsetResults) && !"UTF8".equalsIgnoreCase(charsetResults)) {
               if ("null".equalsIgnoreCase(charsetResults)) {
                  mysqlEncodingName = "NULL";
               } else {
                  mysqlEncodingName = CharsetMapping.getMysqlCharsetForJavaEncoding(charsetResults.toUpperCase(Locale.ENGLISH), this.getServerVersion());
               }
            } else {
               mysqlEncodingName = "utf8";
            }

            if (mysqlEncodingName == null) {
               throw SQLError.createSQLException(Messages.getString("Connection.7", new Object[]{charsetResults}), "S1009", this.getExceptionInterceptor());
            }

            if (!mysqlEncodingName.equalsIgnoreCase(this.session.getServerVariable("character_set_results"))) {
               StringBuilder setBuf = new StringBuilder("SET character_set_results = ".length() + mysqlEncodingName.length());
               setBuf.append("SET character_set_results = ").append(mysqlEncodingName);

               try {
                  this.execSQL((StatementImpl)null, setBuf.toString(), -1, (PacketPayload)null, false, this.database, (ColumnDefinition)null, false);
               } catch (PasswordExpiredException var35) {
                  if ((Boolean)this.disconnectOnExpiredPasswords.getValue()) {
                     throw var35;
                  }
               } catch (SQLException var36) {
                  if (var36.getErrorCode() != 1820 || (Boolean)this.disconnectOnExpiredPasswords.getValue()) {
                     throw var36;
                  }
               }

               this.session.getServerVariables().put("jdbc.local.character_set_results", mysqlEncodingName);
               this.session.setErrorMessageEncoding(charsetResults);
            } else {
               this.session.getServerVariables().put("jdbc.local.character_set_results", onServer);
            }
         }

         charsetResults = this.getPropertySet().getStringReadableProperty("connectionCollation").getStringValue();
         if (charsetResults != null) {
            StringBuilder setBuf = new StringBuilder("SET collation_connection = ".length() + charsetResults.length());
            setBuf.append("SET collation_connection = ").append(charsetResults);

            try {
               this.execSQL((StatementImpl)null, setBuf.toString(), -1, (PacketPayload)null, false, this.database, (ColumnDefinition)null, false);
            } catch (PasswordExpiredException var39) {
               if ((Boolean)this.disconnectOnExpiredPasswords.getValue()) {
                  throw var39;
               }
            } catch (SQLException var40) {
               if (var40.getErrorCode() != 1820 || (Boolean)this.disconnectOnExpiredPasswords.getValue()) {
                  throw var40;
               }
            }
         }
      } finally {
         this.characterEncoding.setValue(realJavaEncoding);
      }

      try {
         CharsetEncoder enc = Charset.forName((String)this.characterEncoding.getValue()).newEncoder();
         CharBuffer cbuf = CharBuffer.allocate(1);
         ByteBuffer bbuf = ByteBuffer.allocate(1);
         cbuf.put("¥");
         cbuf.position(0);
         enc.encode(cbuf, bbuf, true);
         if (bbuf.get(0) == 92) {
            this.requiresEscapingEncoder = true;
         } else {
            cbuf.clear();
            bbuf.clear();
            cbuf.put("₩");
            cbuf.position(0);
            enc.encode(cbuf, bbuf, true);
            if (bbuf.get(0) == 92) {
               this.requiresEscapingEncoder = true;
            }
         }
      } catch (UnsupportedCharsetException var27) {
         byte[] bbuf = StringUtils.getBytes("¥", (String)this.characterEncoding.getValue());
         if (bbuf[0] == 92) {
            this.requiresEscapingEncoder = true;
         } else {
            bbuf = StringUtils.getBytes("₩", (String)this.characterEncoding.getValue());
            if (bbuf[0] == 92) {
               this.requiresEscapingEncoder = true;
            }
         }
      }

      return characterSetAlreadyConfigured;
   }

   private void createInitialHistogram(long[] breakpoints, long lowerBound, long upperBound) {
      double bucketSize = ((double)upperBound - (double)lowerBound) / 20.0D * 1.25D;
      if (bucketSize < 1.0D) {
         bucketSize = 1.0D;
      }

      for(int i = 0; i < 20; ++i) {
         breakpoints[i] = lowerBound;
         lowerBound = (long)((double)lowerBound + bucketSize);
      }

   }

   public void createNewIO(boolean isForReconnect) {
      try {
         synchronized(this.getConnectionMutex()) {
            try {
               Properties mergedProps = this.getPropertySet().exposeAsProperties(this.props);
               if (!(Boolean)this.autoReconnect.getValue()) {
                  this.connectOneTryOnly(isForReconnect, mergedProps);
                  return;
               }

               this.connectWithRetries(isForReconnect, mergedProps);
            } catch (SQLException var6) {
               throw (UnableToConnectException)ExceptionFactory.createException((Class)UnableToConnectException.class, (String)var6.getMessage(), (Throwable)var6);
            }

         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   private void connectWithRetries(boolean isForReconnect, Properties mergedProps) throws SQLException {
      double timeout = (double)(Integer)this.getPropertySet().getIntegerReadableProperty("initialTimeout").getValue();
      boolean connectionGood = false;
      Exception connectionException = null;
      int attemptCount = 0;

      while(attemptCount < (Integer)this.getPropertySet().getIntegerReadableProperty("maxReconnects").getValue() && !connectionGood) {
         try {
            this.session.forceClose();
            this.session.connect(this.getProxy(), this.origHostInfo, mergedProps, this.user, this.password, this.database, DriverManager.getLoginTimeout() * 1000);
            this.pingInternal(false, 0);
            boolean oldAutoCommit;
            int oldIsolationLevel;
            boolean oldReadOnly;
            String oldCatalog;
            synchronized(this.getConnectionMutex()) {
               this.connectionId = this.session.getThreadId();
               this.isClosed = false;
               oldAutoCommit = this.getAutoCommit();
               oldIsolationLevel = this.isolationLevel;
               oldReadOnly = this.isReadOnly(false);
               oldCatalog = this.getCatalog();
               this.session.setStatementInterceptors(this.statementInterceptors);
            }

            this.initializePropsFromServer();
            if (isForReconnect) {
               this.setAutoCommit(oldAutoCommit);
               this.setTransactionIsolation(oldIsolationLevel);
               this.setCatalog(oldCatalog);
               this.setReadOnly(oldReadOnly);
            }

            connectionGood = true;
            break;
         } catch (Exception var16) {
            connectionException = var16;
            connectionGood = false;
            if (connectionGood) {
               break;
            }

            if (attemptCount > 0) {
               try {
                  Thread.sleep((long)timeout * 1000L);
               } catch (InterruptedException var14) {
               }
            }

            ++attemptCount;
         }
      }

      if (!connectionGood) {
         SQLException chainedEx = SQLError.createSQLException(Messages.getString("Connection.UnableToConnectWithRetries", new Object[]{this.getPropertySet().getIntegerReadableProperty("maxReconnects").getValue()}), "08001", connectionException, this.getExceptionInterceptor());
         throw chainedEx;
      } else {
         if ((Boolean)this.propertySet.getBooleanReadableProperty("paranoid").getValue() && !(Boolean)this.autoReconnect.getValue()) {
            this.password = null;
            this.user = null;
         }

         if (isForReconnect) {
            Iterator<Statement> statementIter = this.openStatements.iterator();
            Stack serverPreparedStatements = null;

            while(statementIter.hasNext()) {
               Statement statementObj = (Statement)statementIter.next();
               if (statementObj instanceof ServerPreparedStatement) {
                  if (serverPreparedStatements == null) {
                     serverPreparedStatements = new Stack();
                  }

                  serverPreparedStatements.add(statementObj);
               }
            }

            if (serverPreparedStatements != null) {
               while(!serverPreparedStatements.isEmpty()) {
                  ((ServerPreparedStatement)serverPreparedStatements.pop()).rePrepare();
               }
            }
         }

      }
   }

   private void connectOneTryOnly(boolean isForReconnect, Properties mergedProps) throws SQLException {
      Object var3 = null;

      try {
         this.session.connect(this.getProxy(), this.origHostInfo, mergedProps, this.user, this.password, this.database, DriverManager.getLoginTimeout() * 1000);
         this.connectionId = this.session.getThreadId();
         this.isClosed = false;
         boolean oldAutoCommit = this.getAutoCommit();
         int oldIsolationLevel = this.isolationLevel;
         boolean oldReadOnly = this.isReadOnly(false);
         String oldCatalog = this.getCatalog();
         this.session.setStatementInterceptors(this.statementInterceptors);
         this.initializePropsFromServer();
         if (isForReconnect) {
            this.setAutoCommit(oldAutoCommit);
            this.setTransactionIsolation(oldIsolationLevel);
            this.setCatalog(oldCatalog);
            this.setReadOnly(oldReadOnly);
         }

      } catch (Exception var8) {
         if (!(var8 instanceof PasswordExpiredException) && (!(var8 instanceof SQLException) || ((SQLException)var8).getErrorCode() != 1820) || (Boolean)this.disconnectOnExpiredPasswords.getValue()) {
            if (this.session != null) {
               this.session.forceClose();
            }

            if (var8 instanceof SQLException) {
               throw (SQLException)var8;
            } else if (var8.getCause() != null && var8.getCause() instanceof SQLException) {
               throw (SQLException)var8.getCause();
            } else if (var8 instanceof CJException) {
               throw (CJException)var8;
            } else {
               SQLException chainedEx = SQLError.createSQLException(Messages.getString("Connection.UnableToConnect"), "08001", this.getExceptionInterceptor());
               chainedEx.initCause(var8);
               throw chainedEx;
            }
         }
      }
   }

   private void createPreparedStatementCaches() throws SQLException {
      synchronized(this.getConnectionMutex()) {
         int cacheSize = (Integer)this.getPropertySet().getIntegerReadableProperty("prepStmtCacheSize").getValue();
         String parseInfoCacheFactory = (String)this.getPropertySet().getStringReadableProperty("parseInfoCacheFactory").getValue();

         SQLException sqlEx;
         try {
            Class<?> factoryClass = Class.forName(parseInfoCacheFactory);
            CacheAdapterFactory<String, PreparedStatement.ParseInfo> cacheFactory = (CacheAdapterFactory)factoryClass.newInstance();
            this.cachedPreparedStatementParams = cacheFactory.getInstance(this, this.origHostInfo.getDatabaseUrl(), cacheSize, (Integer)this.prepStmtCacheSqlLimit.getValue(), this.props);
         } catch (InstantiationException | IllegalAccessException | ClassNotFoundException var7) {
            sqlEx = SQLError.createSQLException(Messages.getString("Connection.CantFindCacheFactory", new Object[]{parseInfoCacheFactory, "parseInfoCacheFactory"}), this.getExceptionInterceptor());
            sqlEx.initCause(var7);
            throw sqlEx;
         } catch (Exception var8) {
            sqlEx = SQLError.createSQLException(Messages.getString("Connection.CantLoadCacheFactory", new Object[]{parseInfoCacheFactory, "parseInfoCacheFactory"}), this.getExceptionInterceptor());
            sqlEx.initCause(var8);
            throw sqlEx;
         }

         if ((Boolean)this.useServerPrepStmts.getValue()) {
            this.serverSideStatementCheckCache = new LRUCache(cacheSize);
            this.serverSideStatementCache = new LRUCache(cacheSize) {
               private static final long serialVersionUID = 7692318650375988114L;

               protected boolean removeEldestEntry(Entry<Object, Object> eldest) {
                  if (this.maxElements <= 1) {
                     return false;
                  } else {
                     boolean removeIt = super.removeEldestEntry(eldest);
                     if (removeIt) {
                        ServerPreparedStatement ps = (ServerPreparedStatement)eldest.getValue();
                        ps.isCached = false;
                        ps.setClosed(false);

                        try {
                           ps.close();
                        } catch (SQLException var5) {
                        }
                     }

                     return removeIt;
                  }
               }
            };
         }

      }
   }

   public java.sql.Statement createStatement() throws SQLException {
      try {
         return this.createStatement(1003, 1007);
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
      try {
         this.checkClosed();
         StatementImpl stmt = new StatementImpl(this.getMultiHostSafeProxy(), this.database);
         stmt.setResultSetType(resultSetType);
         stmt.setResultSetConcurrency(resultSetConcurrency);
         return stmt;
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      try {
         if ((Boolean)this.pedantic.getValue() && resultSetHoldability != 1) {
            throw SQLError.createSQLException("HOLD_CUSRORS_OVER_COMMIT is only supported holdability level", "S1009", this.getExceptionInterceptor());
         } else {
            return this.createStatement(resultSetType, resultSetConcurrency);
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public JdbcConnection duplicate() throws SQLException {
      try {
         return new ConnectionImpl(this.origHostInfo);
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public ResultSetInternalMethods execSQL(StatementImpl callingStatement, String sql, int maxRows, PacketPayload packet, boolean streamResults, String catalog, ColumnDefinition cachedMetadata) throws SQLException {
      try {
         return this.execSQL(callingStatement, sql, maxRows, packet, streamResults, catalog, cachedMetadata, false);
      } catch (CJException var9) {
         throw SQLExceptionsMapping.translateException(var9, this.getExceptionInterceptor());
      }
   }

   public ResultSetInternalMethods execSQL(StatementImpl callingStatement, String sql, int maxRows, PacketPayload packet, boolean streamResults, String catalog, ColumnDefinition cachedMetadata, boolean isBatch) throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            long queryStartTime = 0L;
            int endOfQueryPacketPosition = 0;
            if (packet != null) {
               endOfQueryPacketPosition = packet.getPosition();
            }

            if ((Boolean)this.gatherPerfMetrics.getValue()) {
               queryStartTime = System.currentTimeMillis();
            }

            this.lastQueryFinishedTime = 0L;
            if ((Boolean)this.autoReconnect.getValue() && (this.autoCommit || (Boolean)this.autoReconnectForPools.getValue()) && this.needsPing && !isBatch) {
               try {
                  this.pingInternal(false, 0);
                  this.needsPing = false;
               } catch (Exception var29) {
                  this.createNewIO(true);
               }
            }

            boolean var28 = false;

            ResultSetInternalMethods var36;
            label242: {
               ResultSetInternalMethods var13;
               try {
                  SQLException cause;
                  try {
                     var28 = true;
                     if (packet == null) {
                        String encoding = (String)this.characterEncoding.getValue();
                        var36 = (ResultSetInternalMethods)this.session.sqlQueryDirect(callingStatement, sql, encoding, (PacketPayload)null, maxRows, streamResults, catalog, cachedMetadata, callingStatement != null ? callingStatement.getResultSetFactory() : this.nullStatementResultSetFactory);
                        var28 = false;
                        break label242;
                     }

                     var13 = (ResultSetInternalMethods)this.session.sqlQueryDirect(callingStatement, (String)null, (String)null, packet, maxRows, streamResults, catalog, cachedMetadata, callingStatement != null ? callingStatement.getResultSetFactory() : this.nullStatementResultSetFactory);
                     var28 = false;
                  } catch (CJException var30) {
                     cause = SQLExceptionsMapping.translateException(var30, this.getExceptionInterceptor());
                     String sqlState;
                     if ((Boolean)this.getPropertySet().getBooleanReadableProperty("dumpQueriesOnException").getValue()) {
                        sqlState = MysqlaUtils.extractSqlFromPacket(sql, packet, endOfQueryPacketPosition, (Integer)this.getPropertySet().getIntegerReadableProperty("maxQuerySizeToLog").getValue());
                        StringBuilder messageBuf = new StringBuilder(sqlState.length() + 32);
                        messageBuf.append("\n\nQuery being executed when exception was thrown:\n");
                        messageBuf.append(sqlState);
                        messageBuf.append("\n\n");
                        cause = appendMessageToException(cause, messageBuf.toString(), this.getExceptionInterceptor());
                     }

                     if ((Boolean)this.autoReconnect.getValue()) {
                        this.needsPing = true;
                     } else {
                        sqlState = cause.getSQLState();
                        if (sqlState != null && sqlState.equals("08S01")) {
                           this.cleanup(cause);
                        }
                     }

                     throw cause;
                  } catch (Exception var31) {
                     if ((Boolean)this.autoReconnect.getValue()) {
                        this.needsPing = true;
                     }

                     cause = SQLError.createSQLException(Messages.getString("Connection.UnexpectedException"), "S1000", this.getExceptionInterceptor());
                     cause.initCause(var31);
                     throw cause;
                  }
               } finally {
                  if (var28) {
                     if ((Boolean)this.maintainTimeStats.getValue()) {
                        this.lastQueryFinishedTime = System.currentTimeMillis();
                     }

                     if ((Boolean)this.gatherPerfMetrics.getValue()) {
                        long queryTime = System.currentTimeMillis() - queryStartTime;
                        this.registerQueryExecutionTime(queryTime);
                     }

                  }
               }

               if ((Boolean)this.maintainTimeStats.getValue()) {
                  this.lastQueryFinishedTime = System.currentTimeMillis();
               }

               if ((Boolean)this.gatherPerfMetrics.getValue()) {
                  long queryTime = System.currentTimeMillis() - queryStartTime;
                  this.registerQueryExecutionTime(queryTime);
               }

               return var13;
            }

            if ((Boolean)this.maintainTimeStats.getValue()) {
               this.lastQueryFinishedTime = System.currentTimeMillis();
            }

            if ((Boolean)this.gatherPerfMetrics.getValue()) {
               long queryTime = System.currentTimeMillis() - queryStartTime;
               this.registerQueryExecutionTime(queryTime);
            }

            return var36;
         }
      } catch (CJException var34) {
         throw SQLExceptionsMapping.translateException(var34, this.getExceptionInterceptor());
      }
   }

   public StringBuilder generateConnectionCommentBlock(StringBuilder buf) {
      buf.append("/* conn id ");
      buf.append(this.getId());
      buf.append(" clock: ");
      buf.append(System.currentTimeMillis());
      buf.append(" */ ");
      return buf;
   }

   public int getActiveStatementCount() {
      return this.openStatements.size();
   }

   public boolean getAutoCommit() throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            return this.autoCommit;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public String getCatalog() throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            return this.database;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public String getCharacterSetMetadata() {
      synchronized(this.getConnectionMutex()) {
         return this.session.getServerSession().getCharacterSetMetadata();
      }
   }

   public int getHoldability() throws SQLException {
      try {
         return 2;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public long getId() {
      return this.connectionId;
   }

   public long getIdleFor() {
      synchronized(this.getConnectionMutex()) {
         if (this.lastQueryFinishedTime == 0L) {
            return 0L;
         } else {
            long now = System.currentTimeMillis();
            long idleTime = now - this.lastQueryFinishedTime;
            return idleTime;
         }
      }
   }

   public java.sql.DatabaseMetaData getMetaData() throws SQLException {
      try {
         return this.getMetaData(true, true);
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   private java.sql.DatabaseMetaData getMetaData(boolean checkClosed, boolean checkForInfoSchema) throws SQLException {
      try {
         if (checkClosed) {
            this.checkClosed();
         }

         DatabaseMetaData dbmeta = DatabaseMetaData.getInstance(this.getMultiHostSafeProxy(), this.database, checkForInfoSchema, this.nullStatementResultSetFactory);
         if (this.getSession() != null && this.getSession().getProtocol() != null) {
            dbmeta.setMetadataEncoding(this.getSession().getServerSession().getCharacterSetMetadata());
            dbmeta.setMetadataCollationIndex(this.getSession().getServerSession().getMetadataCollationIndex());
         }

         return dbmeta;
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public java.sql.Statement getMetadataSafeStatement() throws SQLException {
      try {
         java.sql.Statement stmt = this.createStatement();
         if (stmt.getMaxRows() != 0) {
            stmt.setMaxRows(0);
         }

         stmt.setEscapeProcessing(false);
         if (stmt.getFetchSize() != 0) {
            stmt.setFetchSize(0);
         }

         return stmt;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public ServerVersion getServerVersion() {
      return this.session.getServerVersion();
   }

   public int getTransactionIsolation() throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            if (!(Boolean)this.useLocalSessionState.getValue()) {
               java.sql.Statement stmt = null;
               ResultSet rs = null;

               try {
                  stmt = this.getMetadataSafeStatement();
                  rs = stmt.executeQuery("SELECT @@session.tx_isolation");
                  if (!rs.next()) {
                     throw SQLError.createSQLException(Messages.getString("Connection.13"), "S1000", this.getExceptionInterceptor());
                  } else {
                     String s = rs.getString(1);
                     if (s != null) {
                        Integer intTI = (Integer)mapTransIsolationNameToValue.get(s);
                        if (intTI != null) {
                           int var6 = intTI;
                           return var6;
                        }
                     }

                     throw SQLError.createSQLException(Messages.getString("Connection.12", new Object[]{s}), "S1000", this.getExceptionInterceptor());
                  }
               } finally {
                  if (rs != null) {
                     try {
                        rs.close();
                     } catch (Exception var20) {
                     }

                     rs = null;
                  }

                  if (stmt != null) {
                     try {
                        stmt.close();
                     } catch (Exception var19) {
                     }

                     stmt = null;
                  }

               }
            } else {
               return this.isolationLevel;
            }
         }
      } catch (CJException var23) {
         throw SQLExceptionsMapping.translateException(var23, this.getExceptionInterceptor());
      }
   }

   public Map<String, Class<?>> getTypeMap() throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            if (this.typeMap == null) {
               this.typeMap = new HashMap();
            }

            return this.typeMap;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public String getURL() {
      return this.origHostInfo.getDatabaseUrl();
   }

   public String getUser() {
      return this.user;
   }

   public SQLWarning getWarnings() throws SQLException {
      try {
         return null;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean hasSameProperties(JdbcConnection c) {
      return this.props.equals(c.getProperties());
   }

   public Properties getProperties() {
      return this.props;
   }

   /** @deprecated */
   @Deprecated
   public boolean hasTriedMaster() {
      return this.hasTriedMasterFlag;
   }

   public void incrementNumberOfPreparedExecutes() {
      if ((Boolean)this.gatherPerfMetrics.getValue()) {
         ++this.numberOfPreparedExecutes;
         ++this.numberOfQueriesIssued;
      }

   }

   public void incrementNumberOfPrepares() {
      if ((Boolean)this.gatherPerfMetrics.getValue()) {
         ++this.numberOfPrepares;
      }

   }

   public void incrementNumberOfResultSetsCreated() {
      if ((Boolean)this.gatherPerfMetrics.getValue()) {
         ++this.numberOfResultSetsCreated;
      }

   }

   private void initializeDriverProperties(Properties info) throws SQLException {
      this.getPropertySet().initializeProperties(info);
      String exceptionInterceptorClasses = this.getPropertySet().getStringReadableProperty("exceptionInterceptors").getStringValue();
      if (exceptionInterceptorClasses != null && !"".equals(exceptionInterceptorClasses)) {
         this.exceptionInterceptor = new ConnectionImpl.ExceptionInterceptorChain(exceptionInterceptorClasses);
      }

      this.session.setLog(LogFactory.getLogger(this.getPropertySet().getStringReadableProperty("logger").getStringValue(), "MySQL", this.getExceptionInterceptor()));
      if ((Boolean)this.profileSQL.getValue() || (Boolean)this.useUsageAdvisor.getValue()) {
         ProfilerEventHandlerFactory.getInstance(this.session);
      }

      if ((Boolean)this.cachePrepStmts.getValue()) {
         this.createPreparedStatementCaches();
      }

      if ((Boolean)this.getPropertySet().getBooleanReadableProperty("cacheCallableStmts").getValue()) {
         this.parsedCallableStatementCache = new LRUCache((Integer)this.getPropertySet().getIntegerReadableProperty("callableStmtCacheSize").getValue());
      }

      if ((Boolean)this.getPropertySet().getBooleanReadableProperty("allowMultiQueries").getValue()) {
         this.getPropertySet().getModifiableProperty("cacheResultSetMetadata").setValue(false);
      }

      if ((Boolean)this.getPropertySet().getBooleanReadableProperty("cacheResultSetMetadata").getValue()) {
         this.resultSetMetadataCache = new LRUCache((Integer)this.getPropertySet().getIntegerReadableProperty("metadataCacheSize").getValue());
      }

      if (this.getPropertySet().getStringReadableProperty("socksProxyHost").getStringValue() != null) {
         this.getPropertySet().getJdbcModifiableProperty("socketFactory").setValue(SocksProxySocketFactory.class.getName());
      }

   }

   private void initializePropsFromServer() throws SQLException {
      String connectionInterceptorClasses = this.getPropertySet().getStringReadableProperty("connectionLifecycleInterceptors").getStringValue();
      this.connectionLifecycleInterceptors = null;
      if (connectionInterceptorClasses != null) {
         try {
            this.connectionLifecycleInterceptors = (List)Util.loadClasses(this.getPropertySet().getStringReadableProperty("connectionLifecycleInterceptors").getStringValue(), "Connection.badLifecycleInterceptor", this.getExceptionInterceptor()).stream().map((o) -> {
               return o.init(this, this.props, this.session.getLog());
            }).collect(Collectors.toList());
         } catch (CJException var9) {
            throw SQLExceptionsMapping.translateException(var9, this.getExceptionInterceptor());
         }
      }

      this.setSessionVariables();
      if ((Boolean)this.useServerPrepStmts.getValue()) {
         this.useServerPreparedStmts = true;
      }

      this.loadServerVariables();
      this.autoIncrementIncrement = this.session.getServerVariable("auto_increment_increment", 1);
      this.buildCollationMapping();

      try {
         LicenseConfiguration.checkLicenseType(this.session.getServerVariables());
      } catch (CJException var8) {
         throw SQLError.createSQLException(var8.getMessage(), "08001", this.getExceptionInterceptor());
      }

      String lowerCaseTables = this.session.getServerVariable("lower_case_table_names");
      this.lowerCaseTableNames = "on".equalsIgnoreCase(lowerCaseTables) || "1".equalsIgnoreCase(lowerCaseTables) || "2".equalsIgnoreCase(lowerCaseTables);
      this.storesLowerCaseTableName = "1".equalsIgnoreCase(lowerCaseTables) || "on".equalsIgnoreCase(lowerCaseTables);
      this.session.configureTimezone();
      if (this.session.getServerVariables().containsKey("max_allowed_packet")) {
         int serverMaxAllowedPacket = this.session.getServerVariable("max_allowed_packet", -1);
         if (serverMaxAllowedPacket != -1 && (!this.maxAllowedPacket.isExplicitlySet() || serverMaxAllowedPacket < (Integer)this.maxAllowedPacket.getValue())) {
            this.maxAllowedPacket.setValue(serverMaxAllowedPacket);
         }

         if ((Boolean)this.useServerPrepStmts.getValue()) {
            ModifiableProperty<Integer> blobSendChunkSize = this.getPropertySet().getModifiableProperty("blobSendChunkSize");
            int preferredBlobSendChunkSize = (Integer)blobSendChunkSize.getValue();
            int packetHeaderSize = 8203;
            int allowedBlobSendChunkSize = Math.min(preferredBlobSendChunkSize, (Integer)this.maxAllowedPacket.getValue()) - packetHeaderSize;
            if (allowedBlobSendChunkSize <= 0) {
               throw SQLError.createSQLException(Messages.getString("Connection.15", new Object[]{Integer.valueOf(packetHeaderSize)}), "01S00", this.getExceptionInterceptor());
            }

            blobSendChunkSize.setValue(allowedBlobSendChunkSize);
         }
      }

      this.checkTransactionIsolationLevel();
      this.session.checkForCharsetMismatch();
      if (this.session.getServerVariables().containsKey("sql_mode")) {
         String sqlModeAsString = this.session.getServerVariable("sql_mode");
         if (StringUtils.isStrictlyNumeric(sqlModeAsString)) {
            this.useAnsiQuotes = (Integer.parseInt(sqlModeAsString) & 4) > 0;
         } else if (sqlModeAsString != null) {
            this.useAnsiQuotes = sqlModeAsString.indexOf("ANSI_QUOTES") != -1;
            this.noBackslashEscapes = sqlModeAsString.indexOf("NO_BACKSLASH_ESCAPES") != -1;
         }
      }

      boolean overrideDefaultAutocommit = this.isAutoCommitNonDefaultOnServer();
      this.configureClientCharacterSet(false);
      if (!overrideDefaultAutocommit) {
         try {
            this.setAutoCommit(true);
         } catch (PasswordExpiredException var10) {
            if ((Boolean)this.disconnectOnExpiredPasswords.getValue()) {
               throw var10;
            }
         } catch (SQLException var11) {
            if (var11.getErrorCode() != 1820 || (Boolean)this.disconnectOnExpiredPasswords.getValue()) {
               throw var11;
            }
         }
      }

      this.session.getServerSession().configureCharacterSets();
      ((DatabaseMetaData)this.dbmd).setMetadataEncoding(this.getSession().getServerSession().getCharacterSetMetadata());
      ((DatabaseMetaData)this.dbmd).setMetadataCollationIndex(this.getSession().getServerSession().getMetadataCollationIndex());
      this.setupServerForTruncationChecks();
   }

   private boolean isAutoCommitNonDefaultOnServer() throws SQLException {
      try {
         boolean overrideDefaultAutocommit = false;
         String initConnectValue = this.session.getServerVariable("init_connect");
         if (initConnectValue != null && initConnectValue.length() > 0) {
            if (!(Boolean)this.getPropertySet().getBooleanReadableProperty("elideSetAutoCommits").getValue()) {
               ResultSet rs = null;
               java.sql.Statement stmt = null;

               try {
                  stmt = this.getMetadataSafeStatement();
                  rs = stmt.executeQuery("SELECT @@session.autocommit");
                  if (rs.next()) {
                     this.autoCommit = rs.getBoolean(1);
                     if (!this.autoCommit) {
                        overrideDefaultAutocommit = true;
                     }
                  }
               } finally {
                  if (rs != null) {
                     try {
                        rs.close();
                     } catch (SQLException var16) {
                     }
                  }

                  if (stmt != null) {
                     try {
                        stmt.close();
                     } catch (SQLException var15) {
                     }
                  }

               }
            } else if (this.getSession().isSetNeededForAutoCommitMode(true)) {
               this.autoCommit = false;
               overrideDefaultAutocommit = true;
            }
         }

         return overrideDefaultAutocommit;
      } catch (CJException var18) {
         throw SQLExceptionsMapping.translateException(var18, this.getExceptionInterceptor());
      }
   }

   public boolean isClosed() {
      try {
         return this.isClosed;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean isInGlobalTx() {
      return this.isInGlobalTx;
   }

   public boolean isMasterConnection() {
      return false;
   }

   public boolean isNoBackslashEscapesSet() {
      return this.noBackslashEscapes;
   }

   public boolean isReadInfoMsgEnabled() {
      return this.readInfoMsg;
   }

   public boolean isReadOnly() throws SQLException {
      try {
         return this.isReadOnly(true);
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean isReadOnly(boolean useSessionStatus) throws SQLException {
      try {
         if (useSessionStatus && !this.isClosed && this.versionMeetsMinimum(5, 6, 5) && !(Boolean)this.useLocalSessionState.getValue() && (Boolean)this.readOnlyPropagatesToServer.getValue()) {
            java.sql.Statement stmt = null;
            ResultSet rs = null;

            try {
               stmt = this.getMetadataSafeStatement();
               rs = stmt.executeQuery("select @@session.tx_read_only");
               if (rs.next()) {
                  boolean var4 = rs.getInt(1) != 0;
                  return var4;
               }
            } catch (PasswordExpiredException var21) {
               if ((Boolean)this.disconnectOnExpiredPasswords.getValue()) {
                  throw SQLError.createSQLException(Messages.getString("Connection.16"), "S1000", var21, this.getExceptionInterceptor());
               }
            } catch (SQLException var22) {
               if (var22.getErrorCode() != 1820 || (Boolean)this.disconnectOnExpiredPasswords.getValue()) {
                  throw SQLError.createSQLException(Messages.getString("Connection.16"), "S1000", var22, this.getExceptionInterceptor());
               }
            } finally {
               if (rs != null) {
                  try {
                     rs.close();
                  } catch (Exception var20) {
                  }

                  rs = null;
               }

               if (stmt != null) {
                  try {
                     stmt.close();
                  } catch (Exception var19) {
                  }

                  stmt = null;
               }

            }
         }

         return this.readOnly;
      } catch (CJException var24) {
         throw SQLExceptionsMapping.translateException(var24, this.getExceptionInterceptor());
      }
   }

   public boolean isSameResource(JdbcConnection otherConnection) {
      synchronized(this.getConnectionMutex()) {
         if (otherConnection == null) {
            return false;
         } else {
            boolean directCompare = true;
            String otherHost = ((ConnectionImpl)otherConnection).origHostToConnectTo;
            String otherOrigDatabase = ((ConnectionImpl)otherConnection).origHostInfo.getDatabase();
            String otherCurrentCatalog = ((ConnectionImpl)otherConnection).database;
            if (!nullSafeCompare(otherHost, this.origHostToConnectTo)) {
               directCompare = false;
            } else if (otherHost != null && otherHost.indexOf(44) == -1 && otherHost.indexOf(58) == -1) {
               directCompare = ((ConnectionImpl)otherConnection).origPortToConnectTo == this.origPortToConnectTo;
            }

            if (directCompare && (!nullSafeCompare(otherOrigDatabase, this.origHostInfo.getDatabase()) || !nullSafeCompare(otherCurrentCatalog, this.database))) {
               directCompare = false;
            }

            if (directCompare) {
               return true;
            } else {
               String otherResourceId = (String)((ConnectionImpl)otherConnection).getPropertySet().getStringReadableProperty("resourceId").getValue();
               String myResourceId = (String)this.getPropertySet().getStringReadableProperty("resourceId").getValue();
               if (otherResourceId != null || myResourceId != null) {
                  directCompare = nullSafeCompare(otherResourceId, myResourceId);
                  if (directCompare) {
                     return true;
                  }
               }

               return false;
            }
         }
      }
   }

   private void createConfigCacheIfNeeded() throws SQLException {
      synchronized(this.getConnectionMutex()) {
         if (this.serverConfigCache == null) {
            SQLException sqlEx;
            try {
               Class<?> factoryClass = Class.forName(this.getPropertySet().getStringReadableProperty("serverConfigCacheFactory").getStringValue());
               CacheAdapterFactory<String, Map<String, String>> cacheFactory = (CacheAdapterFactory)factoryClass.newInstance();
               this.serverConfigCache = cacheFactory.getInstance(this, this.origHostInfo.getDatabaseUrl(), Integer.MAX_VALUE, Integer.MAX_VALUE, this.props);
               ExceptionInterceptor evictOnCommsError = new ExceptionInterceptor() {
                  public ExceptionInterceptor init(Properties config, Log log) {
                     return this;
                  }

                  public void destroy() {
                  }

                  public Exception interceptException(Exception sqlEx) {
                     if (sqlEx instanceof SQLException && ((SQLException)sqlEx).getSQLState() != null && ((SQLException)sqlEx).getSQLState().startsWith("08")) {
                        ConnectionImpl.this.serverConfigCache.invalidate(ConnectionImpl.this.getURL());
                     }

                     return null;
                  }
               };
               if (this.exceptionInterceptor == null) {
                  this.exceptionInterceptor = evictOnCommsError;
               } else {
                  ((ConnectionImpl.ExceptionInterceptorChain)this.exceptionInterceptor).addRingZero(evictOnCommsError);
               }
            } catch (ClassNotFoundException var6) {
               sqlEx = SQLError.createSQLException(Messages.getString("Connection.CantFindCacheFactory", new Object[]{this.getPropertySet().getStringReadableProperty("parseInfoCacheFactory").getValue(), "parseInfoCacheFactory"}), this.getExceptionInterceptor());
               sqlEx.initCause(var6);
               throw sqlEx;
            } catch (IllegalAccessException | CJException | InstantiationException var7) {
               sqlEx = SQLError.createSQLException(Messages.getString("Connection.CantLoadCacheFactory", new Object[]{this.getPropertySet().getStringReadableProperty("parseInfoCacheFactory").getValue(), "parseInfoCacheFactory"}), this.getExceptionInterceptor());
               sqlEx.initCause(var7);
               throw sqlEx;
            }

         }
      }
   }

   private void loadServerVariables() throws SQLException {
      String results;
      if ((Boolean)this.cacheServerConfiguration.getValue()) {
         this.createConfigCacheIfNeeded();
         Map<String, String> cachedVariableMap = (Map)this.serverConfigCache.get(this.getURL());
         if (cachedVariableMap != null) {
            results = (String)cachedVariableMap.get("server_version_string");
            if (results != null && this.getServerVersion() != null && results.equals(this.getServerVersion().toString())) {
               this.session.setServerVariables(cachedVariableMap);
               return;
            }

            this.serverConfigCache.invalidate(this.getURL());
         }
      }

      java.sql.Statement stmt = null;
      results = null;

      try {
         stmt = this.getMetadataSafeStatement();
         String version = this.dbmd.getDriverVersion();
         if (version != null && version.indexOf(42) != -1) {
            StringBuilder buf = new StringBuilder(version.length() + 10);

            for(int i = 0; i < version.length(); ++i) {
               char c = version.charAt(i);
               if (c == '*') {
                  buf.append("[star]");
               } else {
                  buf.append(c);
               }
            }

            version = buf.toString();
         }

         String versionComment = !(Boolean)this.propertySet.getBooleanReadableProperty("paranoid").getValue() && version != null ? "/* " + version + " */" : "";
         this.session.setServerVariables(new HashMap());

         try {
            ResultSet results;
            if (this.versionMeetsMinimum(5, 1, 0)) {
               StringBuilder queryBuf = (new StringBuilder(versionComment)).append("SELECT");
               queryBuf.append("  @@session.auto_increment_increment AS auto_increment_increment");
               queryBuf.append(", @@character_set_client AS character_set_client");
               queryBuf.append(", @@character_set_connection AS character_set_connection");
               queryBuf.append(", @@character_set_results AS character_set_results");
               queryBuf.append(", @@character_set_server AS character_set_server");
               queryBuf.append(", @@init_connect AS init_connect");
               queryBuf.append(", @@interactive_timeout AS interactive_timeout");
               if (!this.versionMeetsMinimum(5, 5, 0)) {
                  queryBuf.append(", @@language AS language");
               }

               queryBuf.append(", @@license AS license");
               queryBuf.append(", @@lower_case_table_names AS lower_case_table_names");
               queryBuf.append(", @@max_allowed_packet AS max_allowed_packet");
               queryBuf.append(", @@net_buffer_length AS net_buffer_length");
               queryBuf.append(", @@net_write_timeout AS net_write_timeout");
               queryBuf.append(", @@query_cache_size AS query_cache_size");
               queryBuf.append(", @@query_cache_type AS query_cache_type");
               queryBuf.append(", @@sql_mode AS sql_mode");
               queryBuf.append(", @@system_time_zone AS system_time_zone");
               queryBuf.append(", @@time_zone AS time_zone");
               queryBuf.append(", @@tx_isolation AS tx_isolation");
               queryBuf.append(", @@wait_timeout AS wait_timeout");
               results = stmt.executeQuery(queryBuf.toString());
               if (results.next()) {
                  ResultSetMetaData rsmd = results.getMetaData();

                  for(int i = 1; i <= rsmd.getColumnCount(); ++i) {
                     this.session.getServerVariables().put(rsmd.getColumnLabel(i), results.getString(i));
                  }
               }
            } else {
               results = stmt.executeQuery(versionComment + "SHOW VARIABLES");

               while(results.next()) {
                  this.session.getServerVariables().put(results.getString(1), results.getString(2));
               }
            }

            results.close();
            results = null;
         } catch (PasswordExpiredException var20) {
            if ((Boolean)this.disconnectOnExpiredPasswords.getValue()) {
               throw var20;
            }
         } catch (SQLException var21) {
            if (var21.getErrorCode() != 1820 || (Boolean)this.disconnectOnExpiredPasswords.getValue()) {
               throw var21;
            }
         }

         if ((Boolean)this.cacheServerConfiguration.getValue()) {
            this.session.getServerVariables().put("server_version_string", this.getServerVersion().toString());
            this.serverConfigCache.put(this.getURL(), this.session.getServerVariables());
         }
      } catch (SQLException var22) {
         throw var22;
      } finally {
         if (results != null) {
            try {
               results.close();
            } catch (SQLException var19) {
            }
         }

         if (stmt != null) {
            try {
               stmt.close();
            } catch (SQLException var18) {
            }
         }

      }

   }

   public int getAutoIncrementIncrement() {
      return this.autoIncrementIncrement;
   }

   public boolean lowerCaseTableNames() {
      return this.lowerCaseTableNames;
   }

   public String nativeSQL(String sql) throws SQLException {
      try {
         if (sql == null) {
            return null;
         } else {
            Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, this.getMultiHostSafeProxy().getSession().getDefaultTimeZone(), this.getMultiHostSafeProxy().getSession().serverSupportsFracSecs(), this.getExceptionInterceptor());
            return escapedSqlResult instanceof String ? (String)escapedSqlResult : ((EscapeProcessorResult)escapedSqlResult).escapedSql;
         }
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   private CallableStatement parseCallableStatement(String sql) throws SQLException {
      Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, this.getMultiHostSafeProxy().getSession().getDefaultTimeZone(), this.getMultiHostSafeProxy().getSession().serverSupportsFracSecs(), this.getExceptionInterceptor());
      boolean isFunctionCall = false;
      String parsedSql = null;
      if (escapedSqlResult instanceof EscapeProcessorResult) {
         parsedSql = ((EscapeProcessorResult)escapedSqlResult).escapedSql;
         isFunctionCall = ((EscapeProcessorResult)escapedSqlResult).callingStoredFunction;
      } else {
         parsedSql = (String)escapedSqlResult;
         isFunctionCall = false;
      }

      return CallableStatement.getInstance(this.getMultiHostSafeProxy(), parsedSql, this.database, isFunctionCall);
   }

   public void ping() throws SQLException {
      try {
         this.pingInternal(true, 0);
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public void pingInternal(boolean checkForClosedConnection, int timeoutMillis) throws SQLException {
      try {
         if (checkForClosedConnection) {
            this.checkClosed();
         }

         long pingMillisLifetime = (long)(Integer)this.getPropertySet().getIntegerReadableProperty("selfDestructOnPingSecondsLifetime").getValue();
         int pingMaxOperations = (Integer)this.getPropertySet().getIntegerReadableProperty("selfDestructOnPingMaxOperations").getValue();
         if ((pingMillisLifetime <= 0L || System.currentTimeMillis() - this.connectionCreationTimeMillis <= pingMillisLifetime) && (pingMaxOperations <= 0 || pingMaxOperations > this.session.getCommandCount())) {
            this.session.sendCommand(14, (String)null, (PacketPayload)null, false, (String)null, timeoutMillis);
         } else {
            this.close();
            throw SQLError.createSQLException(Messages.getString("Connection.exceededConnectionLifetime"), "08S01", this.getExceptionInterceptor());
         }
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   public java.sql.CallableStatement prepareCall(String sql) throws SQLException {
      try {
         return this.prepareCall(sql, 1003, 1007);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      try {
         CallableStatement cStmt = null;
         if (!(Boolean)this.getPropertySet().getBooleanReadableProperty("cacheCallableStmts").getValue()) {
            cStmt = this.parseCallableStatement(sql);
         } else {
            synchronized(this.parsedCallableStatementCache) {
               ConnectionImpl.CompoundCacheKey key = new ConnectionImpl.CompoundCacheKey(this.getCatalog(), sql);
               CallableStatement.CallableStatementParamInfo cachedParamInfo = (CallableStatement.CallableStatementParamInfo)this.parsedCallableStatementCache.get(key);
               if (cachedParamInfo != null) {
                  cStmt = CallableStatement.getInstance(this.getMultiHostSafeProxy(), cachedParamInfo);
               } else {
                  cStmt = this.parseCallableStatement(sql);
                  synchronized(cStmt) {
                     cachedParamInfo = cStmt.paramInfo;
                  }

                  this.parsedCallableStatementCache.put(key, cachedParamInfo);
               }
            }
         }

         cStmt.setResultSetType(resultSetType);
         cStmt.setResultSetConcurrency(resultSetConcurrency);
         return cStmt;
      } catch (CJException var14) {
         throw SQLExceptionsMapping.translateException(var14, this.getExceptionInterceptor());
      }
   }

   public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      try {
         if ((Boolean)this.pedantic.getValue() && resultSetHoldability != 1) {
            throw SQLError.createSQLException(Messages.getString("Connection.17"), "S1009", this.getExceptionInterceptor());
         } else {
            CallableStatement cStmt = (CallableStatement)this.prepareCall(sql, resultSetType, resultSetConcurrency);
            return cStmt;
         }
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   public java.sql.PreparedStatement prepareStatement(String sql) throws SQLException {
      try {
         return this.prepareStatement(sql, 1003, 1007);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public java.sql.PreparedStatement prepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
      try {
         java.sql.PreparedStatement pStmt = this.prepareStatement(sql);
         ((PreparedStatement)pStmt).setRetrieveGeneratedKeys(autoGenKeyIndex == 1);
         return pStmt;
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public java.sql.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            this.checkClosed();
            PreparedStatement pStmt = null;
            boolean canServerPrepare = true;
            String nativeSql = (Boolean)this.processEscapeCodesForPrepStmts.getValue() ? this.nativeSQL(sql) : sql;
            if (this.useServerPreparedStmts && (Boolean)this.emulateUnsupportedPstmts.getValue()) {
               canServerPrepare = this.canHandleAsServerPreparedStatement(nativeSql);
            }

            if (this.useServerPreparedStmts && canServerPrepare) {
               if ((Boolean)this.cachePrepStmts.getValue()) {
                  synchronized(this.serverSideStatementCache) {
                     pStmt = (ServerPreparedStatement)this.serverSideStatementCache.remove(sql);
                     if (pStmt != null) {
                        ((ServerPreparedStatement)pStmt).setClosed(false);
                        ((PreparedStatement)pStmt).clearParameters();
                     }

                     if (pStmt == null) {
                        try {
                           pStmt = ServerPreparedStatement.getInstance(this.getMultiHostSafeProxy(), nativeSql, this.database, resultSetType, resultSetConcurrency);
                           if (sql.length() < (Integer)this.prepStmtCacheSqlLimit.getValue()) {
                              ((ServerPreparedStatement)pStmt).isCached = true;
                           }

                           ((PreparedStatement)pStmt).setResultSetType(resultSetType);
                           ((PreparedStatement)pStmt).setResultSetConcurrency(resultSetConcurrency);
                        } catch (SQLException var14) {
                           if (!(Boolean)this.emulateUnsupportedPstmts.getValue()) {
                              throw var14;
                           }

                           pStmt = (PreparedStatement)this.clientPrepareStatement(nativeSql, resultSetType, resultSetConcurrency, false);
                           if (sql.length() < (Integer)this.prepStmtCacheSqlLimit.getValue()) {
                              this.serverSideStatementCheckCache.put(sql, Boolean.FALSE);
                           }
                        }
                     }
                  }
               } else {
                  try {
                     pStmt = ServerPreparedStatement.getInstance(this.getMultiHostSafeProxy(), nativeSql, this.database, resultSetType, resultSetConcurrency);
                     ((PreparedStatement)pStmt).setResultSetType(resultSetType);
                     ((PreparedStatement)pStmt).setResultSetConcurrency(resultSetConcurrency);
                  } catch (SQLException var13) {
                     if (!(Boolean)this.emulateUnsupportedPstmts.getValue()) {
                        throw var13;
                     }

                     pStmt = (PreparedStatement)this.clientPrepareStatement(nativeSql, resultSetType, resultSetConcurrency, false);
                  }
               }
            } else {
               pStmt = (PreparedStatement)this.clientPrepareStatement(nativeSql, resultSetType, resultSetConcurrency, false);
            }

            return (java.sql.PreparedStatement)pStmt;
         }
      } catch (CJException var17) {
         throw SQLExceptionsMapping.translateException(var17, this.getExceptionInterceptor());
      }
   }

   public java.sql.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      try {
         if ((Boolean)this.pedantic.getValue() && resultSetHoldability != 1) {
            throw SQLError.createSQLException(Messages.getString("Connection.17"), "S1009", this.getExceptionInterceptor());
         } else {
            return this.prepareStatement(sql, resultSetType, resultSetConcurrency);
         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public java.sql.PreparedStatement prepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
      try {
         java.sql.PreparedStatement pStmt = this.prepareStatement(sql);
         ((PreparedStatement)pStmt).setRetrieveGeneratedKeys(autoGenKeyIndexes != null && autoGenKeyIndexes.length > 0);
         return pStmt;
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public java.sql.PreparedStatement prepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
      try {
         java.sql.PreparedStatement pStmt = this.prepareStatement(sql);
         ((PreparedStatement)pStmt).setRetrieveGeneratedKeys(autoGenKeyColNames != null && autoGenKeyColNames.length > 0);
         return pStmt;
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void realClose(boolean calledExplicitly, boolean issueRollback, boolean skipLocalTeardown, Throwable reason) throws SQLException {
      try {
         SQLException sqlEx = null;
         if (!this.isClosed()) {
            this.forceClosedReason = reason;
            boolean var19 = false;

            try {
               var19 = true;
               if (!skipLocalTeardown) {
                  if (!this.getAutoCommit() && issueRollback) {
                     try {
                        this.rollback();
                     } catch (SQLException var21) {
                        sqlEx = var21;
                     }
                  }

                  this.reportMetrics();
                  if ((Boolean)this.useUsageAdvisor.getValue()) {
                     if (!calledExplicitly) {
                        this.session.getProfilerEventHandler().consumeEvent(new ProfilerEventImpl((byte)0, "", this.getCatalog(), this.getId(), -1, -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, (String)null, this.pointOfOrigin, Messages.getString("Connection.18")));
                     }

                     long connectionLifeTime = System.currentTimeMillis() - this.connectionCreationTimeMillis;
                     if (connectionLifeTime < 500L) {
                        this.session.getProfilerEventHandler().consumeEvent(new ProfilerEventImpl((byte)0, "", this.getCatalog(), this.getId(), -1, -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, (String)null, this.pointOfOrigin, Messages.getString("Connection.19")));
                     }
                  }

                  try {
                     this.closeAllOpenStatements();
                  } catch (SQLException var20) {
                     sqlEx = var20;
                  }

                  this.session.quit();
               } else {
                  this.session.forceClose();
               }

               if (this.statementInterceptors != null) {
                  for(int i = 0; i < this.statementInterceptors.size(); ++i) {
                     ((StatementInterceptor)this.statementInterceptors.get(i)).destroy();
                  }
               }

               if (this.exceptionInterceptor != null) {
                  this.exceptionInterceptor.destroy();
                  var19 = false;
               } else {
                  var19 = false;
               }
            } finally {
               if (var19) {
                  ProfilerEventHandlerFactory.removeInstance(this.session);
                  this.openStatements.clear();
                  this.statementInterceptors = null;
                  this.exceptionInterceptor = null;
                  this.nullStatementResultSetFactory = null;
                  synchronized(this.getConnectionMutex()) {
                     if (this.cancelTimer != null) {
                        this.cancelTimer.cancel();
                     }
                  }

                  this.isClosed = true;
               }
            }

            ProfilerEventHandlerFactory.removeInstance(this.session);
            this.openStatements.clear();
            this.statementInterceptors = null;
            this.exceptionInterceptor = null;
            this.nullStatementResultSetFactory = null;
            synchronized(this.getConnectionMutex()) {
               if (this.cancelTimer != null) {
                  this.cancelTimer.cancel();
               }
            }

            this.isClosed = true;
            if (sqlEx != null) {
               throw sqlEx;
            }
         }
      } catch (CJException var25) {
         throw SQLExceptionsMapping.translateException(var25, this.getExceptionInterceptor());
      }
   }

   public void recachePreparedStatement(ServerPreparedStatement pstmt) throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            if ((Boolean)this.cachePrepStmts.getValue() && pstmt.isPoolable()) {
               synchronized(this.serverSideStatementCache) {
                  this.serverSideStatementCache.put(pstmt.originalSql, pstmt);
               }
            }

         }
      } catch (CJException var9) {
         throw SQLExceptionsMapping.translateException(var9, this.getExceptionInterceptor());
      }
   }

   public void decachePreparedStatement(ServerPreparedStatement pstmt) throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            if ((Boolean)this.cachePrepStmts.getValue() && pstmt.isPoolable()) {
               synchronized(this.serverSideStatementCache) {
                  this.serverSideStatementCache.remove(pstmt.originalSql);
               }
            }

         }
      } catch (CJException var9) {
         throw SQLExceptionsMapping.translateException(var9, this.getExceptionInterceptor());
      }
   }

   public void registerQueryExecutionTime(long queryTimeMs) {
      if (queryTimeMs > this.longestQueryTimeMs) {
         this.longestQueryTimeMs = queryTimeMs;
         this.repartitionPerformanceHistogram();
      }

      this.addToPerformanceHistogram(queryTimeMs, 1);
      if (queryTimeMs < this.shortestQueryTimeMs) {
         this.shortestQueryTimeMs = queryTimeMs == 0L ? 1L : queryTimeMs;
      }

      ++this.numberOfQueriesIssued;
      this.totalQueryTimeMs += (double)queryTimeMs;
   }

   public void registerStatement(Statement stmt) {
      this.openStatements.addIfAbsent(stmt);
   }

   public void releaseSavepoint(Savepoint arg0) throws SQLException {
      try {
         ;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   private void repartitionHistogram(int[] histCounts, long[] histBreakpoints, long currentLowerBound, long currentUpperBound) {
      if (this.oldHistCounts == null) {
         this.oldHistCounts = new int[histCounts.length];
         this.oldHistBreakpoints = new long[histBreakpoints.length];
      }

      System.arraycopy(histCounts, 0, this.oldHistCounts, 0, histCounts.length);
      System.arraycopy(histBreakpoints, 0, this.oldHistBreakpoints, 0, histBreakpoints.length);
      this.createInitialHistogram(histBreakpoints, currentLowerBound, currentUpperBound);

      for(int i = 0; i < 20; ++i) {
         this.addToHistogram(histCounts, histBreakpoints, this.oldHistBreakpoints[i], this.oldHistCounts[i], currentLowerBound, currentUpperBound);
      }

   }

   private void repartitionPerformanceHistogram() {
      this.checkAndCreatePerformanceHistogram();
      this.repartitionHistogram(this.perfMetricsHistCounts, this.perfMetricsHistBreakpoints, this.shortestQueryTimeMs == Long.MAX_VALUE ? 0L : this.shortestQueryTimeMs, this.longestQueryTimeMs);
   }

   private void repartitionTablesAccessedHistogram() {
      this.checkAndCreateTablesAccessedHistogram();
      this.repartitionHistogram(this.numTablesMetricsHistCounts, this.numTablesMetricsHistBreakpoints, this.minimumNumberTablesAccessed == Long.MAX_VALUE ? 0L : this.minimumNumberTablesAccessed, this.maximumNumberTablesAccessed);
   }

   private void reportMetrics() {
      if ((Boolean)this.gatherPerfMetrics.getValue()) {
         StringBuilder logMessage = new StringBuilder(256);
         logMessage.append("** Performance Metrics Report **\n");
         logMessage.append("\nLongest reported query: " + this.longestQueryTimeMs + " ms");
         logMessage.append("\nShortest reported query: " + this.shortestQueryTimeMs + " ms");
         logMessage.append("\nAverage query execution time: " + this.totalQueryTimeMs / (double)this.numberOfQueriesIssued + " ms");
         logMessage.append("\nNumber of statements executed: " + this.numberOfQueriesIssued);
         logMessage.append("\nNumber of result sets created: " + this.numberOfResultSetsCreated);
         logMessage.append("\nNumber of statements prepared: " + this.numberOfPrepares);
         logMessage.append("\nNumber of prepared statement executions: " + this.numberOfPreparedExecutes);
         byte maxNumPoints;
         int highestCount;
         int i;
         int numPointsToGraph;
         int j;
         if (this.perfMetricsHistBreakpoints != null) {
            logMessage.append("\n\n\tTiming Histogram:\n");
            maxNumPoints = 20;
            highestCount = Integer.MIN_VALUE;

            for(i = 0; i < 20; ++i) {
               if (this.perfMetricsHistCounts[i] > highestCount) {
                  highestCount = this.perfMetricsHistCounts[i];
               }
            }

            if (highestCount == 0) {
               highestCount = 1;
            }

            for(i = 0; i < 19; ++i) {
               if (i == 0) {
                  logMessage.append("\n\tless than " + this.perfMetricsHistBreakpoints[i + 1] + " ms: \t" + this.perfMetricsHistCounts[i]);
               } else {
                  logMessage.append("\n\tbetween " + this.perfMetricsHistBreakpoints[i] + " and " + this.perfMetricsHistBreakpoints[i + 1] + " ms: \t" + this.perfMetricsHistCounts[i]);
               }

               logMessage.append("\t");
               numPointsToGraph = (int)((double)maxNumPoints * ((double)this.perfMetricsHistCounts[i] / (double)highestCount));

               for(j = 0; j < numPointsToGraph; ++j) {
                  logMessage.append("*");
               }

               if (this.longestQueryTimeMs < (long)this.perfMetricsHistCounts[i + 1]) {
                  break;
               }
            }

            if (this.perfMetricsHistBreakpoints[18] < this.longestQueryTimeMs) {
               logMessage.append("\n\tbetween ");
               logMessage.append(this.perfMetricsHistBreakpoints[18]);
               logMessage.append(" and ");
               logMessage.append(this.perfMetricsHistBreakpoints[19]);
               logMessage.append(" ms: \t");
               logMessage.append(this.perfMetricsHistCounts[19]);
            }
         }

         if (this.numTablesMetricsHistBreakpoints != null) {
            logMessage.append("\n\n\tTable Join Histogram:\n");
            maxNumPoints = 20;
            highestCount = Integer.MIN_VALUE;

            for(i = 0; i < 20; ++i) {
               if (this.numTablesMetricsHistCounts[i] > highestCount) {
                  highestCount = this.numTablesMetricsHistCounts[i];
               }
            }

            if (highestCount == 0) {
               highestCount = 1;
            }

            for(i = 0; i < 19; ++i) {
               if (i == 0) {
                  logMessage.append("\n\t" + this.numTablesMetricsHistBreakpoints[i + 1] + " tables or less: \t\t" + this.numTablesMetricsHistCounts[i]);
               } else {
                  logMessage.append("\n\tbetween " + this.numTablesMetricsHistBreakpoints[i] + " and " + this.numTablesMetricsHistBreakpoints[i + 1] + " tables: \t" + this.numTablesMetricsHistCounts[i]);
               }

               logMessage.append("\t");
               numPointsToGraph = (int)((double)maxNumPoints * ((double)this.numTablesMetricsHistCounts[i] / (double)highestCount));

               for(j = 0; j < numPointsToGraph; ++j) {
                  logMessage.append("*");
               }

               if (this.maximumNumberTablesAccessed < this.numTablesMetricsHistBreakpoints[i + 1]) {
                  break;
               }
            }

            if (this.numTablesMetricsHistBreakpoints[18] < this.maximumNumberTablesAccessed) {
               logMessage.append("\n\tbetween ");
               logMessage.append(this.numTablesMetricsHistBreakpoints[18]);
               logMessage.append(" and ");
               logMessage.append(this.numTablesMetricsHistBreakpoints[19]);
               logMessage.append(" tables: ");
               logMessage.append(this.numTablesMetricsHistCounts[19]);
            }
         }

         this.session.getLog().logInfo(logMessage);
         this.metricsLastReportedMs = System.currentTimeMillis();
      }

   }

   protected void reportMetricsIfNeeded() {
      if ((Boolean)this.gatherPerfMetrics.getValue() && System.currentTimeMillis() - this.metricsLastReportedMs > (long)(Integer)this.getPropertySet().getIntegerReadableProperty("reportMetricsIntervalMillis").getValue()) {
         this.reportMetrics();
      }

   }

   public void reportNumberOfTablesAccessed(int numTablesAccessed) {
      if ((long)numTablesAccessed < this.minimumNumberTablesAccessed) {
         this.minimumNumberTablesAccessed = (long)numTablesAccessed;
      }

      if ((long)numTablesAccessed > this.maximumNumberTablesAccessed) {
         this.maximumNumberTablesAccessed = (long)numTablesAccessed;
         this.repartitionTablesAccessedHistogram();
      }

      this.addToTablesAccessedHistogram((long)numTablesAccessed, 1);
   }

   public void resetServerState() throws SQLException {
      try {
         if (!(Boolean)this.propertySet.getBooleanReadableProperty("paranoid").getValue() && this.session != null) {
            this.changeUser(this.user, this.password);
         }

      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public void rollback() throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            this.checkClosed();

            try {
               if (this.connectionLifecycleInterceptors != null) {
                  IterateBlock<ConnectionLifecycleInterceptor> iter = new IterateBlock<ConnectionLifecycleInterceptor>(this.connectionLifecycleInterceptors.iterator()) {
                     void forEach(ConnectionLifecycleInterceptor each) throws SQLException {
                        if (!each.rollback()) {
                           this.stopIterating = true;
                        }

                     }
                  };
                  iter.doForAll();
                  if (!iter.fullIteration()) {
                     return;
                  }
               }

               if (this.autoCommit) {
                  throw SQLError.createSQLException(Messages.getString("Connection.20"), "08003", this.getExceptionInterceptor());
               } else {
                  try {
                     this.rollbackNoChecks();
                  } catch (SQLException var11) {
                     if (!(Boolean)this.ignoreNonTxTables.getInitialValue() || var11.getErrorCode() != 1196) {
                        throw var11;
                     }
                  }
               }
            } catch (SQLException var12) {
               if ("08S01".equals(var12.getSQLState())) {
                  throw SQLError.createSQLException(Messages.getString("Connection.21"), "08007", this.getExceptionInterceptor());
               } else {
                  throw var12;
               }
            } finally {
               this.needsPing = (Boolean)this.reconnectAtTxEnd.getValue();
            }
         }
      } catch (CJException var15) {
         throw SQLExceptionsMapping.translateException(var15, this.getExceptionInterceptor());
      }
   }

   public void rollback(final Savepoint savepoint) throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            this.checkClosed();

            try {
               if (this.connectionLifecycleInterceptors != null) {
                  IterateBlock<ConnectionLifecycleInterceptor> iter = new IterateBlock<ConnectionLifecycleInterceptor>(this.connectionLifecycleInterceptors.iterator()) {
                     void forEach(ConnectionLifecycleInterceptor each) throws SQLException {
                        if (!each.rollback(savepoint)) {
                           this.stopIterating = true;
                        }

                     }
                  };
                  iter.doForAll();
                  if (!iter.fullIteration()) {
                     return;
                  }
               }

               StringBuilder rollbackQuery = new StringBuilder("ROLLBACK TO SAVEPOINT ");
               rollbackQuery.append('`');
               rollbackQuery.append(savepoint.getSavepointName());
               rollbackQuery.append('`');
               java.sql.Statement stmt = null;

               try {
                  stmt = this.getMetadataSafeStatement();
                  stmt.executeUpdate(rollbackQuery.toString());
               } catch (SQLException var23) {
                  int errno = var23.getErrorCode();
                  if (errno == 1181) {
                     String msg = var23.getMessage();
                     if (msg != null) {
                        int indexOfError153 = msg.indexOf("153");
                        if (indexOfError153 != -1) {
                           throw SQLError.createSQLException(Messages.getString("Connection.22", new Object[]{savepoint.getSavepointName()}), "S1009", errno, this.getExceptionInterceptor());
                        }
                     }
                  }

                  if ((Boolean)this.ignoreNonTxTables.getValue() && var23.getErrorCode() != 1196) {
                     throw var23;
                  }

                  if ("08S01".equals(var23.getSQLState())) {
                     throw SQLError.createSQLException(Messages.getString("Connection.23"), "08007", this.getExceptionInterceptor());
                  }

                  throw var23;
               } finally {
                  this.closeStatement(stmt);
               }
            } finally {
               this.needsPing = (Boolean)this.reconnectAtTxEnd.getValue();
            }

         }
      } catch (CJException var27) {
         throw SQLExceptionsMapping.translateException(var27, this.getExceptionInterceptor());
      }
   }

   private void rollbackNoChecks() throws SQLException {
      if (!(Boolean)this.useLocalTransactionState.getValue() || this.session.inTransactionOnServer()) {
         this.execSQL((StatementImpl)null, "rollback", -1, (PacketPayload)null, false, this.database, (ColumnDefinition)null, false);
      }
   }

   public java.sql.PreparedStatement serverPrepareStatement(String sql) throws SQLException {
      try {
         String nativeSql = (Boolean)this.processEscapeCodesForPrepStmts.getValue() ? this.nativeSQL(sql) : sql;
         return ServerPreparedStatement.getInstance(this.getMultiHostSafeProxy(), nativeSql, this.getCatalog(), 1003, 1007);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public java.sql.PreparedStatement serverPrepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
      try {
         String nativeSql = (Boolean)this.processEscapeCodesForPrepStmts.getValue() ? this.nativeSQL(sql) : sql;
         PreparedStatement pStmt = ServerPreparedStatement.getInstance(this.getMultiHostSafeProxy(), nativeSql, this.getCatalog(), 1003, 1007);
         pStmt.setRetrieveGeneratedKeys(autoGenKeyIndex == 1);
         return pStmt;
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public java.sql.PreparedStatement serverPrepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      try {
         String nativeSql = (Boolean)this.processEscapeCodesForPrepStmts.getValue() ? this.nativeSQL(sql) : sql;
         return ServerPreparedStatement.getInstance(this.getMultiHostSafeProxy(), nativeSql, this.getCatalog(), resultSetType, resultSetConcurrency);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public java.sql.PreparedStatement serverPrepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      try {
         if ((Boolean)this.pedantic.getValue() && resultSetHoldability != 1) {
            throw SQLError.createSQLException(Messages.getString("Connection.17"), "S1009", this.getExceptionInterceptor());
         } else {
            return this.serverPrepareStatement(sql, resultSetType, resultSetConcurrency);
         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public java.sql.PreparedStatement serverPrepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
      try {
         PreparedStatement pStmt = (PreparedStatement)this.serverPrepareStatement(sql);
         pStmt.setRetrieveGeneratedKeys(autoGenKeyIndexes != null && autoGenKeyIndexes.length > 0);
         return pStmt;
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public java.sql.PreparedStatement serverPrepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
      try {
         PreparedStatement pStmt = (PreparedStatement)this.serverPrepareStatement(sql);
         pStmt.setRetrieveGeneratedKeys(autoGenKeyColNames != null && autoGenKeyColNames.length > 0);
         return pStmt;
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void setAutoCommit(final boolean autoCommitFlag) throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            this.checkClosed();
            if (this.connectionLifecycleInterceptors != null) {
               IterateBlock<ConnectionLifecycleInterceptor> iter = new IterateBlock<ConnectionLifecycleInterceptor>(this.connectionLifecycleInterceptors.iterator()) {
                  void forEach(ConnectionLifecycleInterceptor each) throws SQLException {
                     if (!each.setAutoCommit(autoCommitFlag)) {
                        this.stopIterating = true;
                     }

                  }
               };
               iter.doForAll();
               if (!iter.fullIteration()) {
                  return;
               }
            }

            if ((Boolean)this.autoReconnectForPools.getValue()) {
               this.autoReconnect.setValue(true);
            }

            try {
               boolean needsSetOnServer = true;
               if ((Boolean)this.useLocalSessionState.getValue() && this.autoCommit == autoCommitFlag) {
                  needsSetOnServer = false;
               } else if (!(Boolean)this.autoReconnect.getValue()) {
                  needsSetOnServer = this.getSession().isSetNeededForAutoCommitMode(autoCommitFlag);
               }

               this.autoCommit = autoCommitFlag;
               if (needsSetOnServer) {
                  this.execSQL((StatementImpl)null, autoCommitFlag ? "SET autocommit=1" : "SET autocommit=0", -1, (PacketPayload)null, false, this.database, (ColumnDefinition)null, false);
               }
            } finally {
               if ((Boolean)this.autoReconnectForPools.getValue()) {
                  this.autoReconnect.setValue(false);
               }

            }

         }
      } catch (CJException var12) {
         throw SQLExceptionsMapping.translateException(var12, this.getExceptionInterceptor());
      }
   }

   public void setCatalog(final String catalog) throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            this.checkClosed();
            if (catalog == null) {
               throw SQLError.createSQLException("Catalog can not be null", "S1009", this.getExceptionInterceptor());
            } else {
               if (this.connectionLifecycleInterceptors != null) {
                  IterateBlock<ConnectionLifecycleInterceptor> iter = new IterateBlock<ConnectionLifecycleInterceptor>(this.connectionLifecycleInterceptors.iterator()) {
                     void forEach(ConnectionLifecycleInterceptor each) throws SQLException {
                        if (!each.setCatalog(catalog)) {
                           this.stopIterating = true;
                        }

                     }
                  };
                  iter.doForAll();
                  if (!iter.fullIteration()) {
                     return;
                  }
               }

               if ((Boolean)this.useLocalSessionState.getValue()) {
                  if (this.lowerCaseTableNames) {
                     if (this.database.equalsIgnoreCase(catalog)) {
                        return;
                     }
                  } else if (this.database.equals(catalog)) {
                     return;
                  }
               }

               String quotedId = this.dbmd.getIdentifierQuoteString();
               if (quotedId == null || quotedId.equals(" ")) {
                  quotedId = "";
               }

               StringBuilder query = new StringBuilder("USE ");
               query.append(StringUtils.quoteIdentifier(catalog, quotedId, (Boolean)this.pedantic.getValue()));
               this.execSQL((StatementImpl)null, query.toString(), -1, (PacketPayload)null, false, this.database, (ColumnDefinition)null, false);
               this.database = catalog;
            }
         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   public void setFailedOver(boolean flag) {
   }

   public void setHoldability(int arg0) throws SQLException {
      try {
         ;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void setInGlobalTx(boolean flag) {
      this.isInGlobalTx = flag;
   }

   public void setReadInfoMsgEnabled(boolean flag) {
      this.readInfoMsg = flag;
   }

   public void setReadOnly(boolean readOnlyFlag) throws SQLException {
      try {
         this.checkClosed();
         this.setReadOnlyInternal(readOnlyFlag);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void setReadOnlyInternal(boolean readOnlyFlag) throws SQLException {
      try {
         if ((Boolean)this.readOnlyPropagatesToServer.getValue() && this.versionMeetsMinimum(5, 6, 5) && (!(Boolean)this.useLocalSessionState.getValue() || readOnlyFlag != this.readOnly)) {
            this.execSQL((StatementImpl)null, "set session transaction " + (readOnlyFlag ? "read only" : "read write"), -1, (PacketPayload)null, false, this.database, (ColumnDefinition)null, false);
         }

         this.readOnly = readOnlyFlag;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public Savepoint setSavepoint() throws SQLException {
      try {
         MysqlSavepoint savepoint = new MysqlSavepoint(this.getExceptionInterceptor());
         this.setSavepoint(savepoint);
         return savepoint;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   private void setSavepoint(MysqlSavepoint savepoint) throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            this.checkClosed();
            StringBuilder savePointQuery = new StringBuilder("SAVEPOINT ");
            savePointQuery.append('`');
            savePointQuery.append(savepoint.getSavepointName());
            savePointQuery.append('`');
            java.sql.Statement stmt = null;

            try {
               stmt = this.getMetadataSafeStatement();
               stmt.executeUpdate(savePointQuery.toString());
            } finally {
               this.closeStatement(stmt);
            }

         }
      } catch (CJException var13) {
         throw SQLExceptionsMapping.translateException(var13, this.getExceptionInterceptor());
      }
   }

   public Savepoint setSavepoint(String name) throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            MysqlSavepoint savepoint = new MysqlSavepoint(name, this.getExceptionInterceptor());
            this.setSavepoint(savepoint);
            return savepoint;
         }
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   private void setSessionVariables() throws SQLException {
      String sessionVariables = (String)this.getPropertySet().getStringReadableProperty("sessionVariables").getValue();
      if (sessionVariables != null) {
         List<String> variablesToSet = StringUtils.split(sessionVariables, ",", "\"'", "\"'", false);
         int numVariablesToSet = variablesToSet.size();
         java.sql.Statement stmt = null;

         try {
            stmt = this.getMetadataSafeStatement();

            for(int i = 0; i < numVariablesToSet; ++i) {
               String variableValuePair = (String)variablesToSet.get(i);
               if (variableValuePair.startsWith("@")) {
                  stmt.executeUpdate("SET " + variableValuePair);
               } else {
                  stmt.executeUpdate("SET SESSION " + variableValuePair);
               }
            }
         } finally {
            if (stmt != null) {
               stmt.close();
            }

         }
      }

   }

   public void setTransactionIsolation(int level) throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            this.checkClosed();
            String sql = null;
            boolean shouldSendSet = false;
            if ((Boolean)this.getPropertySet().getBooleanReadableProperty("alwaysSendSetIsolation").getValue()) {
               shouldSendSet = true;
            } else if (level != this.isolationLevel) {
               shouldSendSet = true;
            }

            if ((Boolean)this.useLocalSessionState.getValue()) {
               shouldSendSet = this.isolationLevel != level;
            }

            if (shouldSendSet) {
               switch(level) {
               case 0:
                  throw SQLError.createSQLException(Messages.getString("Connection.24"), this.getExceptionInterceptor());
               case 1:
                  sql = "SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED";
                  break;
               case 2:
                  sql = "SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED";
                  break;
               case 3:
               case 5:
               case 6:
               case 7:
               default:
                  throw SQLError.createSQLException(Messages.getString("Connection.25", new Object[]{level}), "S1C00", this.getExceptionInterceptor());
               case 4:
                  sql = "SET SESSION TRANSACTION ISOLATION LEVEL REPEATABLE READ";
                  break;
               case 8:
                  sql = "SET SESSION TRANSACTION ISOLATION LEVEL SERIALIZABLE";
               }

               this.execSQL((StatementImpl)null, sql, -1, (PacketPayload)null, false, this.database, (ColumnDefinition)null, false);
               this.isolationLevel = level;
            }

         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            this.typeMap = map;
         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   private void setupServerForTruncationChecks() throws SQLException {
      ModifiableProperty<Boolean> jdbcCompliantTruncation = this.getPropertySet().getModifiableProperty("jdbcCompliantTruncation");
      if ((Boolean)jdbcCompliantTruncation.getValue()) {
         String currentSqlMode = this.session.getServerVariable("sql_mode");
         boolean strictTransTablesIsSet = StringUtils.indexOfIgnoreCase(currentSqlMode, "STRICT_TRANS_TABLES") != -1;
         if (currentSqlMode != null && currentSqlMode.length() != 0 && strictTransTablesIsSet) {
            if (strictTransTablesIsSet) {
               jdbcCompliantTruncation.setValue(false);
            }
         } else {
            StringBuilder commandBuf = new StringBuilder("SET sql_mode='");
            if (currentSqlMode != null && currentSqlMode.length() > 0) {
               commandBuf.append(currentSqlMode);
               commandBuf.append(",");
            }

            commandBuf.append("STRICT_TRANS_TABLES'");
            this.execSQL((StatementImpl)null, commandBuf.toString(), -1, (PacketPayload)null, false, this.database, (ColumnDefinition)null, false);
            jdbcCompliantTruncation.setValue(false);
         }
      }

   }

   public void shutdownServer() throws SQLException {
      try {
         try {
            this.session.shutdownServer();
         } catch (CJException var4) {
            SQLException sqlEx = SQLError.createSQLException(Messages.getString("Connection.UnhandledExceptionDuringShutdown"), "S1000", this.getExceptionInterceptor());
            sqlEx.initCause(var4);
            throw sqlEx;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void unregisterStatement(Statement stmt) {
      this.openStatements.remove(stmt);
   }

   public boolean useAnsiQuotedIdentifiers() {
      synchronized(this.getConnectionMutex()) {
         return this.useAnsiQuotes;
      }
   }

   public boolean versionMeetsMinimum(int major, int minor, int subminor) {
      try {
         this.checkClosed();
         return this.session.versionMeetsMinimum(major, minor, subminor);
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public CachedResultSetMetaData getCachedMetaData(String sql) {
      if (this.resultSetMetadataCache != null) {
         synchronized(this.resultSetMetadataCache) {
            return (CachedResultSetMetaData)this.resultSetMetadataCache.get(sql);
         }
      } else {
         return null;
      }
   }

   public void initializeResultsMetadataFromCache(String sql, CachedResultSetMetaData cachedMetaData, ResultSetInternalMethods resultSet) throws SQLException {
      try {
         if (cachedMetaData == null) {
            cachedMetaData = new CachedResultSetMetaData();
            resultSet.getColumnDefinition().buildIndexMapping();
            resultSet.initializeWithMetadata();
            if (resultSet instanceof UpdatableResultSet) {
               ((UpdatableResultSet)resultSet).checkUpdatability();
            }

            resultSet.populateCachedMetaData(cachedMetaData);
            this.resultSetMetadataCache.put(sql, cachedMetaData);
         } else {
            resultSet.getColumnDefinition().initializeFrom(cachedMetaData);
            resultSet.initializeWithMetadata();
            if (resultSet instanceof UpdatableResultSet) {
               ((UpdatableResultSet)resultSet).checkUpdatability();
            }
         }

      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public String getStatementComment() {
      return this.statementComment;
   }

   public void setStatementComment(String comment) {
      this.statementComment = comment;
   }

   public void reportQueryTime(long millisOrNanos) {
      synchronized(this.getConnectionMutex()) {
         ++this.queryTimeCount;
         this.queryTimeSum += (double)millisOrNanos;
         this.queryTimeSumSquares += (double)(millisOrNanos * millisOrNanos);
         this.queryTimeMean = (this.queryTimeMean * (double)(this.queryTimeCount - 1L) + (double)millisOrNanos) / (double)this.queryTimeCount;
      }
   }

   public boolean isAbonormallyLongQuery(long millisOrNanos) {
      synchronized(this.getConnectionMutex()) {
         if (this.queryTimeCount < 15L) {
            return false;
         } else {
            double stddev = Math.sqrt((this.queryTimeSumSquares - this.queryTimeSum * this.queryTimeSum / (double)this.queryTimeCount) / (double)(this.queryTimeCount - 1L));
            return (double)millisOrNanos > this.queryTimeMean + 5.0D * stddev;
         }
      }
   }

   public void transactionBegun() throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            if (this.connectionLifecycleInterceptors != null) {
               IterateBlock<ConnectionLifecycleInterceptor> iter = new IterateBlock<ConnectionLifecycleInterceptor>(this.connectionLifecycleInterceptors.iterator()) {
                  void forEach(ConnectionLifecycleInterceptor each) throws SQLException {
                     each.transactionBegun();
                  }
               };
               iter.doForAll();
            }

         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void transactionCompleted() throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            if (this.connectionLifecycleInterceptors != null) {
               IterateBlock<ConnectionLifecycleInterceptor> iter = new IterateBlock<ConnectionLifecycleInterceptor>(this.connectionLifecycleInterceptors.iterator()) {
                  void forEach(ConnectionLifecycleInterceptor each) throws SQLException {
                     each.transactionCompleted();
                  }
               };
               iter.doForAll();
            }

         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public boolean storesLowerCaseTableName() {
      return this.storesLowerCaseTableName;
   }

   public ExceptionInterceptor getExceptionInterceptor() {
      return this.exceptionInterceptor;
   }

   public boolean getRequiresEscapingEncoder() {
      return this.requiresEscapingEncoder;
   }

   public boolean isServerLocal() throws SQLException {
      try {
         try {
            return this.session.isServerLocal(this);
         } catch (CJException var4) {
            SQLException sqlEx = SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
            throw sqlEx;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public int getSessionMaxRows() {
      synchronized(this.getConnectionMutex()) {
         return this.session.getSessionMaxRows();
      }
   }

   public void setSessionMaxRows(int max) throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            if (this.session.getSessionMaxRows() != max) {
               this.session.setSessionMaxRows(max);
               this.execSQL((StatementImpl)null, "SET SQL_SELECT_LIMIT=" + (this.session.getSessionMaxRows() == -1 ? "DEFAULT" : this.session.getSessionMaxRows()), -1, (PacketPayload)null, false, this.database, (ColumnDefinition)null, false);
            }

         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void setSchema(String schema) throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            this.checkClosed();
         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public String getSchema() throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            this.checkClosed();
            return null;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void abort(Executor executor) throws SQLException {
      try {
         SecurityManager sec = System.getSecurityManager();
         if (sec != null) {
            sec.checkPermission(ABORT_PERM);
         }

         if (executor == null) {
            throw SQLError.createSQLException(Messages.getString("Connection.26"), "S1009", this.getExceptionInterceptor());
         } else {
            executor.execute(new Runnable() {
               public void run() {
                  try {
                     ConnectionImpl.this.abortInternal();
                  } catch (SQLException var2) {
                     throw new RuntimeException(var2);
                  }
               }
            });
         }
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            SecurityManager sec = System.getSecurityManager();
            if (sec != null) {
               sec.checkPermission(SET_NETWORK_TIMEOUT_PERM);
            }

            if (executor == null) {
               throw SQLError.createSQLException(Messages.getString("Connection.26"), "S1009", this.getExceptionInterceptor());
            } else {
               this.checkClosed();
               this.session.setSocketTimeout(executor, milliseconds);
            }
         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   public int getNetworkTimeout() throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            this.checkClosed();
            return this.session.getSocketTimeout();
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public java.sql.Clob createClob() {
      try {
         return new Clob(this.getExceptionInterceptor());
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public java.sql.Blob createBlob() {
      try {
         return new Blob(this.getExceptionInterceptor());
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public java.sql.NClob createNClob() {
      try {
         return new NClob(this.getExceptionInterceptor());
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public SQLXML createSQLXML() throws SQLException {
      try {
         return new MysqlSQLXML(this.getExceptionInterceptor());
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean isValid(int timeout) throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            if (this.isClosed()) {
               return false;
            } else {
               boolean var10000;
               try {
                  try {
                     this.pingInternal(false, timeout * 1000);
                     return true;
                  } catch (Throwable var8) {
                     try {
                        this.abortInternal();
                     } catch (Throwable var7) {
                     }

                     var10000 = false;
                  }
               } catch (Throwable var9) {
                  return false;
               }

               return var10000;
            }
         }
      } catch (CJException var11) {
         throw SQLExceptionsMapping.translateException(var11, this.getExceptionInterceptor());
      }
   }

   public ClientInfoProvider getClientInfoProviderImpl() throws SQLException {
      try {
         synchronized(this.getConnectionMutex()) {
            if (this.infoProvider == null) {
               String clientInfoProvider = this.getPropertySet().getStringReadableProperty("clientInfoProvider").getStringValue();

               try {
                  try {
                     this.infoProvider = (ClientInfoProvider)Util.getInstance(clientInfoProvider, new Class[0], new Object[0], this.getExceptionInterceptor());
                  } catch (CJException var8) {
                     if (var8.getCause() instanceof ClassCastException) {
                        try {
                           this.infoProvider = (ClientInfoProvider)Util.getInstance("com.mysql.cj.jdbc." + clientInfoProvider, new Class[0], new Object[0], this.getExceptionInterceptor());
                        } catch (CJException var7) {
                           throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
                        }
                     }
                  }
               } catch (ClassCastException var9) {
                  throw SQLError.createSQLException(Messages.getString("Connection.ClientInfoNotImplemented", new Object[]{clientInfoProvider}), "S1009", this.getExceptionInterceptor());
               }

               this.infoProvider.initialize(this, this.props);
            }

            return this.infoProvider;
         }
      } catch (CJException var11) {
         throw SQLExceptionsMapping.translateException(var11, this.getExceptionInterceptor());
      }
   }

   public void setClientInfo(String name, String value) throws SQLClientInfoException {
      try {
         this.getClientInfoProviderImpl().setClientInfo(this, name, value);
      } catch (SQLClientInfoException var5) {
         throw var5;
      } catch (CJException | SQLException var6) {
         SQLClientInfoException clientInfoEx = new SQLClientInfoException();
         clientInfoEx.initCause(var6);
         throw clientInfoEx;
      }
   }

   public void setClientInfo(Properties properties) throws SQLClientInfoException {
      try {
         this.getClientInfoProviderImpl().setClientInfo(this, properties);
      } catch (SQLClientInfoException var4) {
         throw var4;
      } catch (CJException | SQLException var5) {
         SQLClientInfoException clientInfoEx = new SQLClientInfoException();
         clientInfoEx.initCause(var5);
         throw clientInfoEx;
      }
   }

   public String getClientInfo(String name) throws SQLException {
      try {
         return this.getClientInfoProviderImpl().getClientInfo(this, name);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public Properties getClientInfo() throws SQLException {
      try {
         return this.getClientInfoProviderImpl().getClientInfo(this);
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
      try {
         throw SQLError.createSQLFeatureNotSupportedException();
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
      try {
         throw SQLError.createSQLFeatureNotSupportedException();
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public <T> T unwrap(Class<T> iface) throws SQLException {
      try {
         try {
            return iface.cast(this);
         } catch (ClassCastException var4) {
            throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.getExceptionInterceptor());
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
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

   public String getProcessHost() {
      try {
         long threadId = this.getId();
         java.sql.Statement processListStmt = this.getMetadataSafeStatement();
         String processHost = null;

         try {
            processHost = findProcessHost(threadId, processListStmt);
            if (processHost == null) {
               this.session.getLog().logWarn(String.format("Connection id %d not found in \"SHOW PROCESSLIST\", assuming 32-bit overflow, using SELECT CONNECTION_ID() instead", threadId));
               ResultSet rs = processListStmt.executeQuery("SELECT CONNECTION_ID()");
               if (rs.next()) {
                  threadId = rs.getLong(1);
                  processHost = findProcessHost(threadId, processListStmt);
               } else {
                  this.session.getLog().logError("No rows returned for statement \"SELECT CONNECTION_ID()\", local connection check will most likely be incorrect");
               }
            }
         } finally {
            processListStmt.close();
         }

         if (processHost == null) {
            this.session.getLog().logWarn(String.format("Cannot find process listing for connection %d in SHOW PROCESSLIST output, unable to determine if locally connected", threadId));
         }

         return processHost;
      } catch (SQLException var10) {
         throw ExceptionFactory.createException((String)var10.getMessage(), (Throwable)var10, (ExceptionInterceptor)this.exceptionInterceptor);
      }
   }

   private static String findProcessHost(long threadId, java.sql.Statement processListStmt) throws SQLException {
      String processHost = null;
      ResultSet rs = processListStmt.executeQuery("SHOW PROCESSLIST");

      while(rs.next()) {
         long id = rs.getLong(1);
         if (threadId == id) {
            processHost = rs.getString(3);
            break;
         }
      }

      return processHost;
   }

   public MysqlaSession getSession() {
      return this.session;
   }

   public String getHostPortPair() {
      return this.origHostInfo.getHostPortPair();
   }

   static {
      mapTransIsolationNameToValue = new HashMap(8);
      mapTransIsolationNameToValue.put("READ-UNCOMMITED", 1);
      mapTransIsolationNameToValue.put("READ-UNCOMMITTED", 1);
      mapTransIsolationNameToValue.put("READ-COMMITTED", 2);
      mapTransIsolationNameToValue.put("REPEATABLE-READ", 4);
      mapTransIsolationNameToValue.put("SERIALIZABLE", 8);
      random = new Random();
   }

   static class CompoundCacheKey {
      String componentOne;
      String componentTwo;
      int hashCode;

      CompoundCacheKey(String partOne, String partTwo) {
         this.componentOne = partOne;
         this.componentTwo = partTwo;
         this.hashCode = ((this.componentOne != null ? this.componentOne : "") + this.componentTwo).hashCode();
      }

      public boolean equals(Object obj) {
         if (obj instanceof ConnectionImpl.CompoundCacheKey) {
            ConnectionImpl.CompoundCacheKey another = (ConnectionImpl.CompoundCacheKey)obj;
            boolean firstPartEqual = false;
            if (this.componentOne == null) {
               firstPartEqual = another.componentOne == null;
            } else {
               firstPartEqual = this.componentOne.equals(another.componentOne);
            }

            return firstPartEqual && this.componentTwo.equals(another.componentTwo);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.hashCode;
      }
   }

   public class ExceptionInterceptorChain implements ExceptionInterceptor {
      List<ExceptionInterceptor> interceptors;

      ExceptionInterceptorChain(String interceptorClasses) {
         this.interceptors = (List)Util.loadClasses(interceptorClasses, "Connection.BadExceptionInterceptor", this).stream().map((o) -> {
            return o.init(ConnectionImpl.this.props, ConnectionImpl.this.getSession().getLog());
         }).collect(Collectors.toList());
      }

      void addRingZero(ExceptionInterceptor interceptor) throws SQLException {
         this.interceptors.add(0, interceptor);
      }

      public Exception interceptException(Exception sqlEx) {
         if (this.interceptors != null) {
            for(Iterator iter = this.interceptors.iterator(); iter.hasNext(); sqlEx = ((ExceptionInterceptor)iter.next()).interceptException(sqlEx)) {
            }
         }

         return sqlEx;
      }

      public void destroy() {
         if (this.interceptors != null) {
            Iterator iter = this.interceptors.iterator();

            while(iter.hasNext()) {
               ((ExceptionInterceptor)iter.next()).destroy();
            }
         }

      }

      public ExceptionInterceptor init(Properties properties, Log log) {
         if (this.interceptors != null) {
            Iterator iter = this.interceptors.iterator();

            while(iter.hasNext()) {
               ((ExceptionInterceptor)iter.next()).init(properties, log);
            }
         }

         return this;
      }

      public List<ExceptionInterceptor> getInterceptors() {
         return this.interceptors;
      }
   }
}
