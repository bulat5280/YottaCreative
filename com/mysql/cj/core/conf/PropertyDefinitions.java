package com.mysql.cj.core.conf;

import com.mysql.cj.api.conf.PropertyDefinition;
import com.mysql.cj.api.conf.RuntimeProperty;
import com.mysql.cj.api.log.Log;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.core.io.SocksProxySocketFactory;
import com.mysql.cj.core.io.StandardSocketFactory;
import com.mysql.cj.core.log.StandardLogger;
import com.mysql.cj.core.profiler.LoggingProfilerEventHandler;
import com.mysql.cj.core.util.PerVmServerConfigCacheFactory;
import com.mysql.cj.jdbc.ha.StandardLoadBalanceExceptionChecker;
import com.mysql.cj.jdbc.util.PerConnectionLRUFactory;
import com.mysql.cj.mysqla.authentication.MysqlNativePasswordPlugin;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class PropertyDefinitions {
   public static final String SYSP_line_separator = "line.separator";
   public static final String SYSP_java_vendor = "java.vendor";
   public static final String SYSP_java_version = "java.version";
   public static final String SYSP_java_vm_vendor = "java.vm.vendor";
   public static final String SYSP_os_name = "os.name";
   public static final String SYSP_os_arch = "os.arch";
   public static final String SYSP_os_version = "os.version";
   public static final String SYSP_file_encoding = "file.encoding";
   public static final String SYSP_testsuite_url = "com.mysql.cj.testsuite.url";
   public static final String SYSP_testsuite_url_admin = "com.mysql.cj.testsuite.url.admin";
   public static final String SYSP_testsuite_url_cluster = "com.mysql.cj.testsuite.url.cluster";
   public static final String SYSP_testsuite_url_openssl = "com.mysql.cj.testsuite.url.openssl";
   public static final String SYSP_testsuite_url_mysqlx = "com.mysql.cj.testsuite.mysqlx.url";
   public static final String SYSP_testsuite_cantGrant = "com.mysql.cj.testsuite.cantGrant";
   public static final String SYSP_testsuite_disable_multihost_tests = "com.mysql.cj.testsuite.disable.multihost.tests";
   public static final String SYSP_testsuite_ds_host = "com.mysql.cj.testsuite.ds.host";
   public static final String SYSP_testsuite_ds_port = "com.mysql.cj.testsuite.ds.port";
   public static final String SYSP_testsuite_ds_db = "com.mysql.cj.testsuite.ds.db";
   public static final String SYSP_testsuite_ds_user = "com.mysql.cj.testsuite.ds.user";
   public static final String SYSP_testsuite_ds_password = "com.mysql.cj.testsuite.ds.password";
   public static final String SYSP_testsuite_loadstoreperf_tabletype = "com.mysql.cj.testsuite.loadstoreperf.tabletype";
   public static final String SYSP_testsuite_loadstoreperf_useBigResults = "com.mysql.cj.testsuite.loadstoreperf.useBigResults";
   public static final String SYSP_testsuite_miniAdminTest_runShutdown = "com.mysql.cj.testsuite.miniAdminTest.runShutdown";
   public static final String SYSP_testsuite_noDebugOutput = "com.mysql.cj.testsuite.noDebugOutput";
   public static final String SYSP_testsuite_retainArtifacts = "com.mysql.cj.testsuite.retainArtifacts";
   public static final String SYSP_testsuite_runLongTests = "com.mysql.cj.testsuite.runLongTests";
   public static final String SYSP_testsuite_serverController_basedir = "com.mysql.cj.testsuite.serverController.basedir";
   public static final String PROTOCOL_PROPERTY_KEY = "PROTOCOL";
   public static final String PATH_PROPERTY_KEY = "PATH";
   public static final String TYPE_PROPERTY_KEY = "TYPE";
   public static final String HOST_PROPERTY_KEY = "HOST";
   public static final String PORT_PROPERTY_KEY = "PORT";
   public static final String DBNAME_PROPERTY_KEY = "DBNAME";
   public static final String ADDRESS_PROPERTY_KEY = "ADDRESS";
   public static final String PRIORITY_PROPERTY_KEY = "PRIORITY";
   public static final String CATEGORY_AUTH = Messages.getString("ConnectionProperties.categoryAuthentication");
   public static final String CATEGORY_CONNECTION = Messages.getString("ConnectionProperties.categoryConnection");
   public static final String CATEGORY_SESSION = Messages.getString("ConnectionProperties.categorySession");
   public static final String CATEGORY_NETWORK = Messages.getString("ConnectionProperties.categoryNetworking");
   public static final String CATEGORY_SECURITY = Messages.getString("ConnectionProperties.categorySecurity");
   public static final String CATEGORY_STATEMENTS = Messages.getString("ConnectionProperties.categoryStatements");
   public static final String CATEGORY_PREPARED_STATEMENTS = Messages.getString("ConnectionProperties.categoryPreparedStatements");
   public static final String CATEGORY_RESULT_SETS = Messages.getString("ConnectionProperties.categoryResultSets");
   public static final String CATEGORY_METADATA = Messages.getString("ConnectionProperties.categoryMetadata");
   public static final String CATEGORY_BLOBS = Messages.getString("ConnectionProperties.categoryBlobs");
   public static final String CATEGORY_DATETIMES = Messages.getString("ConnectionProperties.categoryDatetimes");
   public static final String CATEGORY_HA = Messages.getString("ConnectionProperties.categoryHA");
   public static final String CATEGORY_PERFORMANCE = Messages.getString("ConnectionProperties.categoryPerformance");
   public static final String CATEGORY_DEBUGING_PROFILING = Messages.getString("ConnectionProperties.categoryDebuggingProfiling");
   public static final String CATEGORY_EXCEPTIONS = Messages.getString("ConnectionProperties.categoryExceptions");
   public static final String CATEGORY_INTEGRATION = Messages.getString("ConnectionProperties.categoryIntegration");
   public static final String CATEGORY_JDBC = Messages.getString("ConnectionProperties.categoryJDBC");
   public static final String CATEGORY_MYSQLX = Messages.getString("ConnectionProperties.categoryMysqlx");
   public static final String[] PROPERTY_CATEGORIES;
   public static final boolean DEFAULT_VALUE_TRUE = true;
   public static final boolean DEFAULT_VALUE_FALSE = false;
   public static final String DEFAULT_VALUE_NULL_STRING;
   public static final boolean RUNTIME_MODIFIABLE = true;
   public static final boolean RUNTIME_NOT_MODIFIABLE = false;
   public static final String ZERO_DATETIME_BEHAVIOR_CONVERT_TO_NULL = "convertToNull";
   public static final String ZERO_DATETIME_BEHAVIOR_EXCEPTION = "exception";
   public static final String ZERO_DATETIME_BEHAVIOR_ROUND = "round";
   public static final Map<String, PropertyDefinition<?>> PROPERTY_NAME_TO_PROPERTY_DEFINITION;
   public static final Map<String, String> PROPERTY_NAME_TO_ALIAS;
   public static final String PNAME_paranoid = "paranoid";
   public static final String PNAME_passwordCharacterEncoding = "passwordCharacterEncoding";
   public static final String PNAME_serverRSAPublicKeyFile = "serverRSAPublicKeyFile";
   public static final String PNAME_allowPublicKeyRetrieval = "allowPublicKeyRetrieval";
   public static final String PNAME_clientCertificateKeyStoreUrl = "clientCertificateKeyStoreUrl";
   public static final String PNAME_trustCertificateKeyStoreUrl = "trustCertificateKeyStoreUrl";
   public static final String PNAME_clientCertificateKeyStoreType = "clientCertificateKeyStoreType";
   public static final String PNAME_clientCertificateKeyStorePassword = "clientCertificateKeyStorePassword";
   public static final String PNAME_trustCertificateKeyStoreType = "trustCertificateKeyStoreType";
   public static final String PNAME_trustCertificateKeyStorePassword = "trustCertificateKeyStorePassword";
   public static final String PNAME_verifyServerCertificate = "verifyServerCertificate";
   public static final String PNAME_enabledSSLCipherSuites = "enabledSSLCipherSuites";
   public static final String PNAME_useUnbufferedInput = "useUnbufferedInput";
   public static final String PNAME_profilerEventHandler = "profilerEventHandler";
   public static final String PNAME_allowLoadLocalInfile = "allowLoadLocalInfile";
   public static final String PNAME_allowMultiQueries = "allowMultiQueries";
   public static final String PNAME_allowNanAndInf = "allowNanAndInf";
   public static final String PNAME_allowUrlInLocalInfile = "allowUrlInLocalInfile";
   public static final String PNAME_alwaysSendSetIsolation = "alwaysSendSetIsolation";
   public static final String PNAME_autoClosePStmtStreams = "autoClosePStmtStreams";
   public static final String PNAME_allowMasterDownConnections = "allowMasterDownConnections";
   public static final String PNAME_allowSlaveDownConnections = "allowSlaveDownConnections";
   public static final String PNAME_readFromMasterWhenNoSlaves = "readFromMasterWhenNoSlaves";
   public static final String PNAME_autoDeserialize = "autoDeserialize";
   public static final String PNAME_autoGenerateTestcaseScript = "autoGenerateTestcaseScript";
   public static final String PNAME_autoReconnect = "autoReconnect";
   public static final String PNAME_autoReconnectForPools = "autoReconnectForPools";
   public static final String PNAME_blobSendChunkSize = "blobSendChunkSize";
   public static final String PNAME_autoSlowLog = "autoSlowLog";
   public static final String PNAME_blobsAreStrings = "blobsAreStrings";
   public static final String PNAME_functionsNeverReturnBlobs = "functionsNeverReturnBlobs";
   public static final String PNAME_cacheCallableStmts = "cacheCallableStmts";
   public static final String PNAME_cachePrepStmts = "cachePrepStmts";
   public static final String PNAME_cacheResultSetMetadata = "cacheResultSetMetadata";
   public static final String PNAME_serverConfigCacheFactory = "serverConfigCacheFactory";
   public static final String PNAME_cacheServerConfiguration = "cacheServerConfiguration";
   public static final String PNAME_callableStmtCacheSize = "callableStmtCacheSize";
   public static final String PNAME_characterEncoding = "characterEncoding";
   public static final String PNAME_characterSetResults = "characterSetResults";
   public static final String PNAME_connectionAttributes = "connectionAttributes";
   public static final String PNAME_clientInfoProvider = "clientInfoProvider";
   public static final String PNAME_clobberStreamingResults = "clobberStreamingResults";
   public static final String PNAME_clobCharacterEncoding = "clobCharacterEncoding";
   public static final String PNAME_compensateOnDuplicateKeyUpdateCounts = "compensateOnDuplicateKeyUpdateCounts";
   public static final String PNAME_connectionCollation = "connectionCollation";
   public static final String PNAME_connectionLifecycleInterceptors = "connectionLifecycleInterceptors";
   public static final String PNAME_connectTimeout = "connectTimeout";
   public static final String PNAME_continueBatchOnError = "continueBatchOnError";
   public static final String PNAME_createDatabaseIfNotExist = "createDatabaseIfNotExist";
   public static final String PNAME_defaultFetchSize = "defaultFetchSize";
   public static final String PNAME_useServerPrepStmts = "useServerPrepStmts";
   public static final String PNAME_dontTrackOpenResources = "dontTrackOpenResources";
   public static final String PNAME_dumpQueriesOnException = "dumpQueriesOnException";
   public static final String PNAME_elideSetAutoCommits = "elideSetAutoCommits";
   public static final String PNAME_emptyStringsConvertToZero = "emptyStringsConvertToZero";
   public static final String PNAME_emulateLocators = "emulateLocators";
   public static final String PNAME_emulateUnsupportedPstmts = "emulateUnsupportedPstmts";
   public static final String PNAME_enablePacketDebug = "enablePacketDebug";
   public static final String PNAME_enableQueryTimeouts = "enableQueryTimeouts";
   public static final String PNAME_explainSlowQueries = "explainSlowQueries";
   public static final String PNAME_exceptionInterceptors = "exceptionInterceptors";
   public static final String PNAME_failOverReadOnly = "failOverReadOnly";
   public static final String PNAME_gatherPerfMetrics = "gatherPerfMetrics";
   public static final String PNAME_generateSimpleParameterMetadata = "generateSimpleParameterMetadata";
   public static final String PNAME_holdResultsOpenOverStatementClose = "holdResultsOpenOverStatementClose";
   public static final String PNAME_includeInnodbStatusInDeadlockExceptions = "includeInnodbStatusInDeadlockExceptions";
   public static final String PNAME_includeThreadDumpInDeadlockExceptions = "includeThreadDumpInDeadlockExceptions";
   public static final String PNAME_includeThreadNamesAsStatementComment = "includeThreadNamesAsStatementComment";
   public static final String PNAME_ignoreNonTxTables = "ignoreNonTxTables";
   public static final String PNAME_initialTimeout = "initialTimeout";
   public static final String PNAME_interactiveClient = "interactiveClient";
   public static final String PNAME_jdbcCompliantTruncation = "jdbcCompliantTruncation";
   public static final String PNAME_largeRowSizeThreshold = "largeRowSizeThreshold";
   public static final String PNAME_loadBalanceStrategy = "ha.loadBalanceStrategy";
   public static final String PNAME_loadBalanceBlacklistTimeout = "loadBalanceBlacklistTimeout";
   public static final String PNAME_loadBalancePingTimeout = "loadBalancePingTimeout";
   public static final String PNAME_loadBalanceValidateConnectionOnSwapServer = "loadBalanceValidateConnectionOnSwapServer";
   public static final String PNAME_loadBalanceConnectionGroup = "loadBalanceConnectionGroup";
   public static final String PNAME_loadBalanceExceptionChecker = "loadBalanceExceptionChecker";
   public static final String PNAME_loadBalanceSQLStateFailover = "loadBalanceSQLStateFailover";
   public static final String PNAME_loadBalanceSQLExceptionSubclassFailover = "loadBalanceSQLExceptionSubclassFailover";
   public static final String PNAME_loadBalanceAutoCommitStatementRegex = "loadBalanceAutoCommitStatementRegex";
   public static final String PNAME_loadBalanceAutoCommitStatementThreshold = "loadBalanceAutoCommitStatementThreshold";
   public static final String PNAME_localSocketAddress = "localSocketAddress";
   public static final String PNAME_locatorFetchBufferSize = "locatorFetchBufferSize";
   public static final String PNAME_logger = "logger";
   public static final String PNAME_logSlowQueries = "logSlowQueries";
   public static final String PNAME_logXaCommands = "logXaCommands";
   public static final String PNAME_maintainTimeStats = "maintainTimeStats";
   public static final String PNAME_maxQuerySizeToLog = "maxQuerySizeToLog";
   public static final String PNAME_maxReconnects = "maxReconnects";
   public static final String PNAME_retriesAllDown = "retriesAllDown";
   public static final String PNAME_maxRows = "maxRows";
   public static final String PNAME_metadataCacheSize = "metadataCacheSize";
   public static final String PNAME_netTimeoutForStreamingResults = "netTimeoutForStreamingResults";
   public static final String PNAME_noAccessToProcedureBodies = "noAccessToProcedureBodies";
   public static final String PNAME_noDatetimeStringSync = "noDatetimeStringSync";
   public static final String PNAME_nullCatalogMeansCurrent = "nullCatalogMeansCurrent";
   public static final String PNAME_nullNamePatternMatchesAll = "nullNamePatternMatchesAll";
   public static final String PNAME_packetDebugBufferSize = "packetDebugBufferSize";
   public static final String PNAME_padCharsWithSpace = "padCharsWithSpace";
   public static final String PNAME_pedantic = "pedantic";
   public static final String PNAME_pinGlobalTxToPhysicalConnection = "pinGlobalTxToPhysicalConnection";
   public static final String PNAME_populateInsertRowWithDefaultValues = "populateInsertRowWithDefaultValues";
   public static final String PNAME_prepStmtCacheSize = "prepStmtCacheSize";
   public static final String PNAME_prepStmtCacheSqlLimit = "prepStmtCacheSqlLimit";
   public static final String PNAME_parseInfoCacheFactory = "parseInfoCacheFactory";
   public static final String PNAME_processEscapeCodesForPrepStmts = "processEscapeCodesForPrepStmts";
   public static final String PNAME_profileSQL = "profileSQL";
   public static final String PNAME_propertiesTransform = "propertiesTransform";
   public static final String PNAME_queriesBeforeRetryMaster = "queriesBeforeRetryMaster";
   public static final String PNAME_queryTimeoutKillsConnection = "queryTimeoutKillsConnection";
   public static final String PNAME_reconnectAtTxEnd = "reconnectAtTxEnd";
   public static final String PNAME_reportMetricsIntervalMillis = "reportMetricsIntervalMillis";
   public static final String PNAME_requireSSL = "requireSSL";
   public static final String PNAME_resourceId = "resourceId";
   public static final String PNAME_resultSetSizeThreshold = "resultSetSizeThreshold";
   public static final String PNAME_rewriteBatchedStatements = "rewriteBatchedStatements";
   public static final String PNAME_rollbackOnPooledClose = "rollbackOnPooledClose";
   public static final String PNAME_secondsBeforeRetryMaster = "secondsBeforeRetryMaster";
   public static final String PNAME_selfDestructOnPingSecondsLifetime = "selfDestructOnPingSecondsLifetime";
   public static final String PNAME_selfDestructOnPingMaxOperations = "selfDestructOnPingMaxOperations";
   public static final String PNAME_ha_enableJMX = "ha.enableJMX";
   public static final String PNAME_loadBalanceHostRemovalGracePeriod = "loadBalanceHostRemovalGracePeriod";
   public static final String PNAME_serverTimezone = "serverTimezone";
   public static final String PNAME_sessionVariables = "sessionVariables";
   public static final String PNAME_slowQueryThresholdMillis = "slowQueryThresholdMillis";
   public static final String PNAME_slowQueryThresholdNanos = "slowQueryThresholdNanos";
   public static final String PNAME_socketFactory = "socketFactory";
   public static final String PNAME_socksProxyHost = "socksProxyHost";
   public static final String PNAME_socksProxyPort = "socksProxyPort";
   public static final String PNAME_socketTimeout = "socketTimeout";
   public static final String PNAME_statementInterceptors = "statementInterceptors";
   public static final String PNAME_strictUpdates = "strictUpdates";
   public static final String PNAME_overrideSupportsIntegrityEnhancementFacility = "overrideSupportsIntegrityEnhancementFacility";
   public static final String PNAME_tcpNoDelay = "tcpNoDelay";
   public static final String PNAME_tcpKeepAlive = "tcpKeepAlive";
   public static final String PNAME_tcpRcvBuf = "tcpRcvBuf";
   public static final String PNAME_tcpSndBuf = "tcpSndBuf";
   public static final String PNAME_tcpTrafficClass = "tcpTrafficClass";
   public static final String PNAME_tinyInt1isBit = "tinyInt1isBit";
   public static final String PNAME_traceProtocol = "traceProtocol";
   public static final String PNAME_treatUtilDateAsTimestamp = "treatUtilDateAsTimestamp";
   public static final String PNAME_transformedBitIsBoolean = "transformedBitIsBoolean";
   public static final String PNAME_useCompression = "useCompression";
   public static final String PNAME_useColumnNamesInFindColumn = "useColumnNamesInFindColumn";
   public static final String PNAME_useConfigs = "useConfigs";
   public static final String PNAME_useCursorFetch = "useCursorFetch";
   public static final String PNAME_useHostsInPrivileges = "useHostsInPrivileges";
   public static final String PNAME_useInformationSchema = "useInformationSchema";
   public static final String PNAME_useLocalSessionState = "useLocalSessionState";
   public static final String PNAME_useLocalTransactionState = "useLocalTransactionState";
   public static final String PNAME_sendFractionalSeconds = "sendFractionalSeconds";
   public static final String PNAME_useNanosForElapsedTime = "useNanosForElapsedTime";
   public static final String PNAME_useOldAliasMetadataBehavior = "useOldAliasMetadataBehavior";
   public static final String PNAME_useOldUTF8Behavior = "useOldUTF8Behavior";
   public static final String PNAME_useOnlyServerErrorMessages = "useOnlyServerErrorMessages";
   public static final String PNAME_useReadAheadInput = "useReadAheadInput";
   public static final String PNAME_useSSL = "useSSL";
   public static final String PNAME_useStreamLengthsInPrepStmts = "useStreamLengthsInPrepStmts";
   public static final String PNAME_ultraDevHack = "ultraDevHack";
   public static final String PNAME_useUsageAdvisor = "useUsageAdvisor";
   public static final String PNAME_yearIsDateType = "yearIsDateType";
   public static final String PNAME_zeroDateTimeBehavior = "zeroDateTimeBehavior";
   public static final String PNAME_useAffectedRows = "useAffectedRows";
   public static final String PNAME_maxAllowedPacket = "maxAllowedPacket";
   public static final String PNAME_authenticationPlugins = "authenticationPlugins";
   public static final String PNAME_disabledAuthenticationPlugins = "disabledAuthenticationPlugins";
   public static final String PNAME_defaultAuthenticationPlugin = "defaultAuthenticationPlugin";
   public static final String PNAME_disconnectOnExpiredPasswords = "disconnectOnExpiredPasswords";
   public static final String PNAME_getProceduresReturnsFunctions = "getProceduresReturnsFunctions";
   public static final String PNAME_detectCustomCollations = "detectCustomCollations";
   public static final String PNAME_dontCheckOnDuplicateKeyUpdateInSQL = "dontCheckOnDuplicateKeyUpdateInSQL";
   public static final String PNAME_readOnlyPropagatesToServer = "readOnlyPropagatesToServer";
   public static final String PNAME_useAsyncProtocol = "mysqlx.useAsyncProtocol";
   public static final String PNAME_enableEscapeProcessing = "enableEscapeProcessing";
   public static final String PNAME_user = "user";
   public static final String PNAME_password = "password";
   public static final String PNAME_replicationConnectionGroup = "replicationConnectionGroup";
   public static final String PNAME_resultSetScannerRegex = "resultSetScannerRegex";
   public static final String PNAME_clientInfoSetSPName = "clientInfoSetSPName";
   public static final String PNAME_clientInfoGetSPName = "clientInfoGetSPName";
   public static final String PNAME_clientInfoGetBulkSPName = "clientInfoGetBulkSPName";
   public static final String PNAME_clientInfoCatalog = "clientInfoCatalog";
   public static final String PNAME_autoConfigureForColdFusion = "autoConfigureForColdFusion";
   public static final String PNAME_testsuite_faultInjection_serverCharsetIndex = "com.mysql.cj.testsuite.faultInjection.serverCharsetIndex";
   private static final String STANDARD_LOGGER_NAME;

   public static PropertyDefinition<?> getPropertyDefinition(String propertyName) {
      return (PropertyDefinition)PROPERTY_NAME_TO_PROPERTY_DEFINITION.get(propertyName);
   }

   public static RuntimeProperty<?> createRuntimeProperty(String propertyName) {
      PropertyDefinition<?> pdef = getPropertyDefinition(propertyName);
      if (pdef != null) {
         return pdef.createRuntimeProperty();
      } else {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, "Connection property definition is not found for '" + propertyName + "'");
      }
   }

   public static String exposeAsXml() {
      StringBuilder xmlBuf = new StringBuilder();
      xmlBuf.append("<ConnectionProperties>");
      int numCategories = PROPERTY_CATEGORIES.length;
      Map<String, PropertyDefinitions.XmlMap> propertyListByCategory = new HashMap();

      for(int i = 0; i < numCategories; ++i) {
         propertyListByCategory.put(PROPERTY_CATEGORIES[i], new PropertyDefinitions.XmlMap());
      }

      StringPropertyDefinition userDef = new StringPropertyDefinition("user", DEFAULT_VALUE_NULL_STRING, false, Messages.getString("ConnectionProperties.Username"), Messages.getString("ConnectionProperties.allVersions"), CATEGORY_AUTH, -2147483647);
      StringPropertyDefinition passwordDef = new StringPropertyDefinition("password", DEFAULT_VALUE_NULL_STRING, false, Messages.getString("ConnectionProperties.Password"), Messages.getString("ConnectionProperties.allVersions"), CATEGORY_AUTH, -2147483646);
      PropertyDefinitions.XmlMap connectionSortMaps = (PropertyDefinitions.XmlMap)propertyListByCategory.get(CATEGORY_AUTH);
      TreeMap<String, PropertyDefinition<?>> userMap = new TreeMap();
      userMap.put(userDef.getName(), userDef);
      connectionSortMaps.ordered.put(userDef.getOrder(), userMap);
      TreeMap<String, PropertyDefinition<?>> passwordMap = new TreeMap();
      passwordMap.put(passwordDef.getName(), passwordDef);
      connectionSortMaps.ordered.put(new Integer(passwordDef.getOrder()), passwordMap);
      Iterator var8 = PROPERTY_NAME_TO_PROPERTY_DEFINITION.values().iterator();

      while(var8.hasNext()) {
         PropertyDefinition<?> pdef = (PropertyDefinition)var8.next();
         PropertyDefinitions.XmlMap sortMaps = (PropertyDefinitions.XmlMap)propertyListByCategory.get(pdef.getCategory());
         int orderInCategory = pdef.getOrder();
         if (orderInCategory == Integer.MIN_VALUE) {
            sortMaps.alpha.put(pdef.getName(), pdef);
         } else {
            Integer order = orderInCategory;
            Map<String, PropertyDefinition<?>> orderMap = (Map)sortMaps.ordered.get(order);
            if (orderMap == null) {
               orderMap = new TreeMap();
               sortMaps.ordered.put(order, orderMap);
            }

            ((Map)orderMap).put(pdef.getName(), pdef);
         }
      }

      for(int j = 0; j < numCategories; ++j) {
         PropertyDefinitions.XmlMap sortMaps = (PropertyDefinitions.XmlMap)propertyListByCategory.get(PROPERTY_CATEGORIES[j]);
         xmlBuf.append("\n <PropertyCategory name=\"");
         xmlBuf.append(PROPERTY_CATEGORIES[j]);
         xmlBuf.append("\">");
         Iterator var18 = sortMaps.ordered.values().iterator();

         while(var18.hasNext()) {
            Map<String, PropertyDefinition<?>> orderedEl = (Map)var18.next();
            Iterator var21 = orderedEl.values().iterator();

            while(var21.hasNext()) {
               PropertyDefinition<?> pdef = (PropertyDefinition)var21.next();
               xmlBuf.append("\n  <Property name=\"");
               xmlBuf.append(pdef.getName());
               xmlBuf.append("\" default=\"");
               if (pdef.getDefaultValue() != null) {
                  xmlBuf.append(pdef.getDefaultValue());
               }

               xmlBuf.append("\" sortOrder=\"");
               xmlBuf.append(pdef.getOrder());
               xmlBuf.append("\" since=\"");
               xmlBuf.append(pdef.getSinceVersion());
               xmlBuf.append("\">\n");
               xmlBuf.append("    ");
               String escapedDescription = pdef.getDescription();
               escapedDescription = escapedDescription.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
               xmlBuf.append(escapedDescription);
               xmlBuf.append("\n  </Property>");
            }
         }

         var18 = sortMaps.alpha.values().iterator();

         while(var18.hasNext()) {
            PropertyDefinition<?> pdef = (PropertyDefinition)var18.next();
            xmlBuf.append("\n  <Property name=\"");
            xmlBuf.append(pdef.getName());
            xmlBuf.append("\" default=\"");
            if (pdef.getDefaultValue() != null) {
               xmlBuf.append(pdef.getDefaultValue());
            }

            xmlBuf.append("\" sortOrder=\"alpha\" since=\"");
            xmlBuf.append(pdef.getSinceVersion());
            xmlBuf.append("\">\n");
            xmlBuf.append("    ");
            xmlBuf.append(pdef.getDescription());
            xmlBuf.append("\n  </Property>");
         }

         xmlBuf.append("\n </PropertyCategory>");
      }

      xmlBuf.append("\n</ConnectionProperties>");
      return xmlBuf.toString();
   }

   static {
      PROPERTY_CATEGORIES = new String[]{CATEGORY_AUTH, CATEGORY_CONNECTION, CATEGORY_SESSION, CATEGORY_NETWORK, CATEGORY_SECURITY, CATEGORY_STATEMENTS, CATEGORY_PREPARED_STATEMENTS, CATEGORY_RESULT_SETS, CATEGORY_METADATA, CATEGORY_BLOBS, CATEGORY_DATETIMES, CATEGORY_HA, CATEGORY_PERFORMANCE, CATEGORY_DEBUGING_PROFILING, CATEGORY_EXCEPTIONS, CATEGORY_INTEGRATION, CATEGORY_JDBC, CATEGORY_MYSQLX};
      DEFAULT_VALUE_NULL_STRING = null;
      STANDARD_LOGGER_NAME = StandardLogger.class.getName();
      PropertyDefinition<?>[] pdefs = new PropertyDefinition[]{new BooleanPropertyDefinition("paranoid", false, false, Messages.getString("ConnectionProperties.paranoid"), "3.0.1", CATEGORY_SECURITY, Integer.MIN_VALUE), new StringPropertyDefinition("passwordCharacterEncoding", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.passwordCharacterEncoding"), "5.1.7", CATEGORY_CONNECTION, Integer.MIN_VALUE), new StringPropertyDefinition("serverRSAPublicKeyFile", DEFAULT_VALUE_NULL_STRING, false, Messages.getString("ConnectionProperties.serverRSAPublicKeyFile"), "5.1.31", CATEGORY_SECURITY, Integer.MIN_VALUE), new BooleanPropertyDefinition("allowPublicKeyRetrieval", false, false, Messages.getString("ConnectionProperties.allowPublicKeyRetrieval"), "5.1.31", CATEGORY_SECURITY, Integer.MIN_VALUE), new StringPropertyDefinition("clientCertificateKeyStoreUrl", DEFAULT_VALUE_NULL_STRING, false, Messages.getString("ConnectionProperties.clientCertificateKeyStoreUrl"), "5.1.0", CATEGORY_SECURITY, 5), new StringPropertyDefinition("trustCertificateKeyStoreUrl", DEFAULT_VALUE_NULL_STRING, false, Messages.getString("ConnectionProperties.trustCertificateKeyStoreUrl"), "5.1.0", CATEGORY_SECURITY, 8), new StringPropertyDefinition("clientCertificateKeyStoreType", "JKS", false, Messages.getString("ConnectionProperties.clientCertificateKeyStoreType"), "5.1.0", CATEGORY_SECURITY, 6), new StringPropertyDefinition("clientCertificateKeyStorePassword", DEFAULT_VALUE_NULL_STRING, false, Messages.getString("ConnectionProperties.clientCertificateKeyStorePassword"), "5.1.0", CATEGORY_SECURITY, 7), new StringPropertyDefinition("trustCertificateKeyStoreType", "JKS", false, Messages.getString("ConnectionProperties.trustCertificateKeyStoreType"), "5.1.0", CATEGORY_SECURITY, 9), new StringPropertyDefinition("trustCertificateKeyStorePassword", DEFAULT_VALUE_NULL_STRING, false, Messages.getString("ConnectionProperties.trustCertificateKeyStorePassword"), "5.1.0", CATEGORY_SECURITY, 10), new BooleanPropertyDefinition("verifyServerCertificate", true, true, Messages.getString("ConnectionProperties.verifyServerCertificate"), "5.1.6", CATEGORY_SECURITY, 4), new StringPropertyDefinition("enabledSSLCipherSuites", DEFAULT_VALUE_NULL_STRING, false, Messages.getString("ConnectionProperties.enabledSSLCipherSuites"), "5.1.35", CATEGORY_SECURITY, 11), new BooleanPropertyDefinition("useUnbufferedInput", true, true, Messages.getString("ConnectionProperties.useUnbufferedInput"), "3.0.11", CATEGORY_NETWORK, Integer.MIN_VALUE), new StringPropertyDefinition("profilerEventHandler", LoggingProfilerEventHandler.class.getName(), true, Messages.getString("ConnectionProperties.profilerEventHandler"), "5.1.6", CATEGORY_DEBUGING_PROFILING, Integer.MIN_VALUE), new BooleanPropertyDefinition("allowLoadLocalInfile", true, true, Messages.getString("ConnectionProperties.loadDataLocal"), "3.0.3", CATEGORY_SECURITY, Integer.MAX_VALUE), new BooleanPropertyDefinition("allowMultiQueries", false, true, Messages.getString("ConnectionProperties.allowMultiQueries"), "3.1.1", CATEGORY_SECURITY, 1), new BooleanPropertyDefinition("allowNanAndInf", false, true, Messages.getString("ConnectionProperties.allowNANandINF"), "3.1.5", CATEGORY_PREPARED_STATEMENTS, Integer.MIN_VALUE), new BooleanPropertyDefinition("allowUrlInLocalInfile", false, true, Messages.getString("ConnectionProperties.allowUrlInLoadLocal"), "3.1.4", CATEGORY_SECURITY, Integer.MAX_VALUE), new BooleanPropertyDefinition("alwaysSendSetIsolation", true, true, Messages.getString("ConnectionProperties.alwaysSendSetIsolation"), "3.1.7", CATEGORY_PERFORMANCE, Integer.MAX_VALUE), new BooleanPropertyDefinition("autoClosePStmtStreams", false, true, Messages.getString("ConnectionProperties.autoClosePstmtStreams"), "3.1.12", CATEGORY_PREPARED_STATEMENTS, Integer.MIN_VALUE), new BooleanPropertyDefinition("allowMasterDownConnections", false, true, Messages.getString("ConnectionProperties.allowMasterDownConnections"), "5.1.27", CATEGORY_HA, Integer.MAX_VALUE), new BooleanPropertyDefinition("allowSlaveDownConnections", false, true, Messages.getString("ConnectionProperties.allowSlaveDownConnections"), "6.0.2", CATEGORY_HA, Integer.MAX_VALUE), new BooleanPropertyDefinition("readFromMasterWhenNoSlaves", false, true, Messages.getString("ConnectionProperties.readFromMasterWhenNoSlaves"), "6.0.2", CATEGORY_HA, Integer.MAX_VALUE), new BooleanPropertyDefinition("autoDeserialize", false, true, Messages.getString("ConnectionProperties.autoDeserialize"), "3.1.5", CATEGORY_BLOBS, Integer.MIN_VALUE), new BooleanPropertyDefinition("autoGenerateTestcaseScript", false, true, Messages.getString("ConnectionProperties.autoGenerateTestcaseScript"), "3.1.9", CATEGORY_DEBUGING_PROFILING, Integer.MIN_VALUE), new BooleanPropertyDefinition("autoReconnect", false, true, Messages.getString("ConnectionProperties.autoReconnect"), "1.1", CATEGORY_HA, 0), new BooleanPropertyDefinition("autoReconnectForPools", false, true, Messages.getString("ConnectionProperties.autoReconnectForPools"), "3.1.3", CATEGORY_HA, 1), new MemorySizePropertyDefinition("blobSendChunkSize", 1048576, true, Messages.getString("ConnectionProperties.blobSendChunkSize"), "3.1.9", CATEGORY_BLOBS, Integer.MIN_VALUE, 0, 0), new BooleanPropertyDefinition("autoSlowLog", true, true, Messages.getString("ConnectionProperties.autoSlowLog"), "5.1.4", CATEGORY_DEBUGING_PROFILING, Integer.MIN_VALUE), new BooleanPropertyDefinition("blobsAreStrings", false, true, Messages.getString("ConnectionProperties.blobsAreStrings"), "5.0.8", CATEGORY_BLOBS, Integer.MIN_VALUE), new BooleanPropertyDefinition("functionsNeverReturnBlobs", false, true, Messages.getString("ConnectionProperties.functionsNeverReturnBlobs"), "5.0.8", CATEGORY_BLOBS, Integer.MIN_VALUE), new BooleanPropertyDefinition("cacheCallableStmts", false, true, Messages.getString("ConnectionProperties.cacheCallableStatements"), "3.1.2", CATEGORY_PERFORMANCE, Integer.MIN_VALUE), new BooleanPropertyDefinition("cachePrepStmts", false, true, Messages.getString("ConnectionProperties.cachePrepStmts"), "3.0.10", CATEGORY_PERFORMANCE, Integer.MIN_VALUE), new BooleanPropertyDefinition("cacheResultSetMetadata", false, true, Messages.getString("ConnectionProperties.cacheRSMetadata"), "3.1.1", CATEGORY_PERFORMANCE, Integer.MIN_VALUE), new StringPropertyDefinition("serverConfigCacheFactory", PerVmServerConfigCacheFactory.class.getName(), true, Messages.getString("ConnectionProperties.serverConfigCacheFactory"), "5.1.1", CATEGORY_PERFORMANCE, 12), new BooleanPropertyDefinition("cacheServerConfiguration", false, true, Messages.getString("ConnectionProperties.cacheServerConfiguration"), "3.1.5", CATEGORY_PERFORMANCE, Integer.MIN_VALUE), new IntegerPropertyDefinition("callableStmtCacheSize", 100, true, Messages.getString("ConnectionProperties.callableStmtCacheSize"), "3.1.2", CATEGORY_PERFORMANCE, 5, 0, Integer.MAX_VALUE), new StringPropertyDefinition("characterEncoding", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.characterEncoding"), "1.1g", CATEGORY_SESSION, 5), new StringPropertyDefinition("characterSetResults", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.characterSetResults"), "3.0.13", CATEGORY_SESSION, 6), new StringPropertyDefinition("connectionAttributes", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.connectionAttributes"), "5.1.25", CATEGORY_CONNECTION, 7), new StringPropertyDefinition("clientInfoProvider", "com.mysql.cj.jdbc.CommentClientInfoProvider", true, Messages.getString("ConnectionProperties.clientInfoProvider"), "5.1.0", CATEGORY_DEBUGING_PROFILING, Integer.MIN_VALUE), new BooleanPropertyDefinition("clobberStreamingResults", false, true, Messages.getString("ConnectionProperties.clobberStreamingResults"), "3.0.9", CATEGORY_RESULT_SETS, Integer.MIN_VALUE), new StringPropertyDefinition("clobCharacterEncoding", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.clobCharacterEncoding"), "5.0.0", CATEGORY_BLOBS, Integer.MIN_VALUE), new BooleanPropertyDefinition("compensateOnDuplicateKeyUpdateCounts", false, true, Messages.getString("ConnectionProperties.compensateOnDuplicateKeyUpdateCounts"), "5.1.7", CATEGORY_PREPARED_STATEMENTS, Integer.MIN_VALUE), new StringPropertyDefinition("connectionCollation", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.connectionCollation"), "3.0.13", CATEGORY_SESSION, 7), new StringPropertyDefinition("connectionLifecycleInterceptors", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.connectionLifecycleInterceptors"), "5.1.4", CATEGORY_CONNECTION, Integer.MAX_VALUE), new IntegerPropertyDefinition("connectTimeout", 0, true, Messages.getString("ConnectionProperties.connectTimeout"), "3.0.1", CATEGORY_NETWORK, 9, 0, Integer.MAX_VALUE), new BooleanPropertyDefinition("continueBatchOnError", true, true, Messages.getString("ConnectionProperties.continueBatchOnError"), "3.0.3", CATEGORY_STATEMENTS, Integer.MIN_VALUE), new BooleanPropertyDefinition("createDatabaseIfNotExist", false, true, Messages.getString("ConnectionProperties.createDatabaseIfNotExist"), "3.1.9", CATEGORY_CONNECTION, Integer.MIN_VALUE), new IntegerPropertyDefinition("defaultFetchSize", 0, true, Messages.getString("ConnectionProperties.defaultFetchSize"), "3.1.9", CATEGORY_PERFORMANCE, Integer.MIN_VALUE), new BooleanPropertyDefinition("useServerPrepStmts", false, true, Messages.getString("ConnectionProperties.useServerPrepStmts"), "3.1.0", CATEGORY_PREPARED_STATEMENTS, Integer.MIN_VALUE), new BooleanPropertyDefinition("dontTrackOpenResources", false, true, Messages.getString("ConnectionProperties.dontTrackOpenResources"), "3.1.7", CATEGORY_STATEMENTS, Integer.MIN_VALUE), new BooleanPropertyDefinition("dumpQueriesOnException", false, true, Messages.getString("ConnectionProperties.dumpQueriesOnException"), "3.1.3", CATEGORY_EXCEPTIONS, Integer.MIN_VALUE), new BooleanPropertyDefinition("elideSetAutoCommits", false, true, Messages.getString("ConnectionProperties.eliseSetAutoCommit"), "3.1.3", CATEGORY_PERFORMANCE, Integer.MIN_VALUE), new BooleanPropertyDefinition("emptyStringsConvertToZero", true, true, Messages.getString("ConnectionProperties.emptyStringsConvertToZero"), "3.1.8", CATEGORY_RESULT_SETS, Integer.MIN_VALUE), new BooleanPropertyDefinition("emulateLocators", false, true, Messages.getString("ConnectionProperties.emulateLocators"), "3.1.0", CATEGORY_BLOBS, Integer.MIN_VALUE), new BooleanPropertyDefinition("emulateUnsupportedPstmts", true, true, Messages.getString("ConnectionProperties.emulateUnsupportedPstmts"), "3.1.7", CATEGORY_PREPARED_STATEMENTS, Integer.MIN_VALUE), new BooleanPropertyDefinition("enablePacketDebug", false, true, Messages.getString("ConnectionProperties.enablePacketDebug"), "3.1.3", CATEGORY_DEBUGING_PROFILING, Integer.MIN_VALUE), new BooleanPropertyDefinition("enableQueryTimeouts", true, true, Messages.getString("ConnectionProperties.enableQueryTimeouts"), "5.0.6", CATEGORY_PERFORMANCE, Integer.MIN_VALUE), new BooleanPropertyDefinition("explainSlowQueries", false, true, Messages.getString("ConnectionProperties.explainSlowQueries"), "3.1.2", CATEGORY_DEBUGING_PROFILING, Integer.MIN_VALUE), new StringPropertyDefinition("exceptionInterceptors", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.exceptionInterceptors"), "5.1.8", CATEGORY_EXCEPTIONS, Integer.MIN_VALUE), new BooleanPropertyDefinition("failOverReadOnly", true, true, Messages.getString("ConnectionProperties.failoverReadOnly"), "3.0.12", CATEGORY_HA, 2), new BooleanPropertyDefinition("gatherPerfMetrics", false, true, Messages.getString("ConnectionProperties.gatherPerfMetrics"), "3.1.2", CATEGORY_DEBUGING_PROFILING, 1), new BooleanPropertyDefinition("generateSimpleParameterMetadata", false, true, Messages.getString("ConnectionProperties.generateSimpleParameterMetadata"), "5.0.5", CATEGORY_PREPARED_STATEMENTS, Integer.MIN_VALUE), new BooleanPropertyDefinition("holdResultsOpenOverStatementClose", false, true, Messages.getString("ConnectionProperties.holdRSOpenOverStmtClose"), "3.1.7", CATEGORY_RESULT_SETS, Integer.MIN_VALUE), new BooleanPropertyDefinition("includeInnodbStatusInDeadlockExceptions", false, true, Messages.getString("ConnectionProperties.includeInnodbStatusInDeadlockExceptions"), "5.0.7", CATEGORY_EXCEPTIONS, Integer.MIN_VALUE), new BooleanPropertyDefinition("includeThreadDumpInDeadlockExceptions", false, true, Messages.getString("ConnectionProperties.includeThreadDumpInDeadlockExceptions"), "5.1.15", CATEGORY_EXCEPTIONS, Integer.MIN_VALUE), new BooleanPropertyDefinition("includeThreadNamesAsStatementComment", false, true, Messages.getString("ConnectionProperties.includeThreadNamesAsStatementComment"), "5.1.15", CATEGORY_EXCEPTIONS, Integer.MIN_VALUE), new BooleanPropertyDefinition("ignoreNonTxTables", false, true, Messages.getString("ConnectionProperties.ignoreNonTxTables"), "3.0.9", CATEGORY_EXCEPTIONS, Integer.MIN_VALUE), new IntegerPropertyDefinition("initialTimeout", 2, false, Messages.getString("ConnectionProperties.initialTimeout"), "1.1", CATEGORY_HA, 5, 1, Integer.MAX_VALUE), new BooleanPropertyDefinition("interactiveClient", false, false, Messages.getString("ConnectionProperties.interactiveClient"), "3.1.0", CATEGORY_CONNECTION, Integer.MIN_VALUE), new BooleanPropertyDefinition("jdbcCompliantTruncation", true, true, Messages.getString("ConnectionProperties.jdbcCompliantTruncation"), "3.1.2", CATEGORY_RESULT_SETS, Integer.MIN_VALUE), new MemorySizePropertyDefinition("largeRowSizeThreshold", 2048, true, Messages.getString("ConnectionProperties.largeRowSizeThreshold"), "5.1.1", CATEGORY_PERFORMANCE, Integer.MIN_VALUE, 0, Integer.MAX_VALUE), new StringPropertyDefinition("ha.loadBalanceStrategy", "random", true, Messages.getString("ConnectionProperties.loadBalanceStrategy"), "5.0.6", CATEGORY_HA, Integer.MIN_VALUE), new IntegerPropertyDefinition("loadBalanceBlacklistTimeout", 0, true, Messages.getString("ConnectionProperties.loadBalanceBlacklistTimeout"), "5.1.0", CATEGORY_HA, Integer.MIN_VALUE, 0, Integer.MAX_VALUE), new IntegerPropertyDefinition("loadBalancePingTimeout", 0, true, Messages.getString("ConnectionProperties.loadBalancePingTimeout"), "5.1.13", CATEGORY_HA, Integer.MIN_VALUE, 0, Integer.MAX_VALUE), new BooleanPropertyDefinition("loadBalanceValidateConnectionOnSwapServer", false, true, Messages.getString("ConnectionProperties.loadBalanceValidateConnectionOnSwapServer"), "5.1.13", CATEGORY_HA, Integer.MIN_VALUE), new StringPropertyDefinition("loadBalanceConnectionGroup", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.loadBalanceConnectionGroup"), "5.1.13", CATEGORY_HA, Integer.MIN_VALUE), new StringPropertyDefinition("loadBalanceExceptionChecker", StandardLoadBalanceExceptionChecker.class.getName(), true, Messages.getString("ConnectionProperties.loadBalanceExceptionChecker"), "5.1.13", CATEGORY_HA, Integer.MIN_VALUE), new StringPropertyDefinition("loadBalanceSQLStateFailover", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.loadBalanceSQLStateFailover"), "5.1.13", CATEGORY_HA, Integer.MIN_VALUE), new StringPropertyDefinition("loadBalanceSQLExceptionSubclassFailover", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.loadBalanceSQLExceptionSubclassFailover"), "5.1.13", CATEGORY_HA, Integer.MIN_VALUE), new StringPropertyDefinition("loadBalanceAutoCommitStatementRegex", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.loadBalanceAutoCommitStatementRegex"), "5.1.15", CATEGORY_HA, Integer.MIN_VALUE), new IntegerPropertyDefinition("loadBalanceAutoCommitStatementThreshold", 0, true, Messages.getString("ConnectionProperties.loadBalanceAutoCommitStatementThreshold"), "5.1.15", CATEGORY_HA, Integer.MIN_VALUE, 0, Integer.MAX_VALUE), new StringPropertyDefinition("localSocketAddress", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.localSocketAddress"), "5.0.5", CATEGORY_NETWORK, Integer.MIN_VALUE), new MemorySizePropertyDefinition("locatorFetchBufferSize", 1048576, true, Messages.getString("ConnectionProperties.locatorFetchBufferSize"), "3.2.1", CATEGORY_BLOBS, Integer.MIN_VALUE, 0, Integer.MAX_VALUE), new StringPropertyDefinition("logger", STANDARD_LOGGER_NAME, true, Messages.getString("ConnectionProperties.logger", new Object[]{Log.class.getName(), STANDARD_LOGGER_NAME}), "3.1.1", CATEGORY_DEBUGING_PROFILING, 0), new BooleanPropertyDefinition("logSlowQueries", false, true, Messages.getString("ConnectionProperties.logSlowQueries"), "3.1.2", CATEGORY_DEBUGING_PROFILING, Integer.MIN_VALUE), new BooleanPropertyDefinition("logXaCommands", false, true, Messages.getString("ConnectionProperties.logXaCommands"), "5.0.5", CATEGORY_DEBUGING_PROFILING, Integer.MIN_VALUE), new BooleanPropertyDefinition("maintainTimeStats", true, true, Messages.getString("ConnectionProperties.maintainTimeStats"), "3.1.9", CATEGORY_PERFORMANCE, Integer.MAX_VALUE), new IntegerPropertyDefinition("maxQuerySizeToLog", 2048, true, Messages.getString("ConnectionProperties.maxQuerySizeToLog"), "3.1.3", CATEGORY_DEBUGING_PROFILING, 4, 0, Integer.MAX_VALUE), new IntegerPropertyDefinition("maxReconnects", 3, true, Messages.getString("ConnectionProperties.maxReconnects"), "1.1", CATEGORY_HA, 4, 1, Integer.MAX_VALUE), new IntegerPropertyDefinition("retriesAllDown", 120, true, Messages.getString("ConnectionProperties.retriesAllDown"), "5.1.6", CATEGORY_HA, 4, 0, Integer.MAX_VALUE), new IntegerPropertyDefinition("maxRows", -1, true, Messages.getString("ConnectionProperties.maxRows"), Messages.getString("ConnectionProperties.allVersions"), CATEGORY_RESULT_SETS, Integer.MIN_VALUE, -1, Integer.MAX_VALUE), new IntegerPropertyDefinition("metadataCacheSize", 50, true, Messages.getString("ConnectionProperties.metadataCacheSize"), "3.1.1", CATEGORY_PERFORMANCE, 5, 1, Integer.MAX_VALUE), new IntegerPropertyDefinition("netTimeoutForStreamingResults", 600, true, Messages.getString("ConnectionProperties.netTimeoutForStreamingResults"), "5.1.0", CATEGORY_RESULT_SETS, Integer.MIN_VALUE, 0, Integer.MAX_VALUE), new BooleanPropertyDefinition("noAccessToProcedureBodies", false, true, Messages.getString("ConnectionProperties.noAccessToProcedureBodies"), "5.0.3", CATEGORY_METADATA, Integer.MIN_VALUE), new BooleanPropertyDefinition("noDatetimeStringSync", false, true, Messages.getString("ConnectionProperties.noDatetimeStringSync"), "3.1.7", CATEGORY_DATETIMES, Integer.MIN_VALUE), new BooleanPropertyDefinition("nullCatalogMeansCurrent", false, true, Messages.getString("ConnectionProperties.nullCatalogMeansCurrent"), "3.1.8", CATEGORY_METADATA, Integer.MIN_VALUE), new BooleanPropertyDefinition("nullNamePatternMatchesAll", false, true, Messages.getString("ConnectionProperties.nullNamePatternMatchesAll"), "3.1.8", CATEGORY_METADATA, Integer.MIN_VALUE), new IntegerPropertyDefinition("packetDebugBufferSize", 20, true, Messages.getString("ConnectionProperties.packetDebugBufferSize"), "3.1.3", CATEGORY_DEBUGING_PROFILING, 7, 0, Integer.MAX_VALUE), new BooleanPropertyDefinition("padCharsWithSpace", false, true, Messages.getString("ConnectionProperties.padCharsWithSpace"), "5.0.6", CATEGORY_RESULT_SETS, Integer.MIN_VALUE), new BooleanPropertyDefinition("pedantic", false, true, Messages.getString("ConnectionProperties.pedantic"), "3.0.0", CATEGORY_JDBC, Integer.MIN_VALUE), new BooleanPropertyDefinition("pinGlobalTxToPhysicalConnection", false, true, Messages.getString("ConnectionProperties.pinGlobalTxToPhysicalConnection"), "5.0.1", CATEGORY_HA, Integer.MIN_VALUE), new BooleanPropertyDefinition("populateInsertRowWithDefaultValues", false, true, Messages.getString("ConnectionProperties.populateInsertRowWithDefaultValues"), "5.0.5", CATEGORY_RESULT_SETS, Integer.MIN_VALUE), new IntegerPropertyDefinition("prepStmtCacheSize", 25, true, Messages.getString("ConnectionProperties.prepStmtCacheSize"), "3.0.10", CATEGORY_PERFORMANCE, 10, 0, Integer.MAX_VALUE), new IntegerPropertyDefinition("prepStmtCacheSqlLimit", 256, true, Messages.getString("ConnectionProperties.prepStmtCacheSqlLimit"), "3.0.10", CATEGORY_PERFORMANCE, 11, 1, Integer.MAX_VALUE), new StringPropertyDefinition("parseInfoCacheFactory", PerConnectionLRUFactory.class.getName(), true, Messages.getString("ConnectionProperties.parseInfoCacheFactory"), "5.1.1", CATEGORY_PERFORMANCE, 12), new BooleanPropertyDefinition("processEscapeCodesForPrepStmts", true, true, Messages.getString("ConnectionProperties.processEscapeCodesForPrepStmts"), "3.1.12", CATEGORY_PREPARED_STATEMENTS, Integer.MIN_VALUE), new BooleanPropertyDefinition("profileSQL", false, true, Messages.getString("ConnectionProperties.profileSQL"), "3.1.0", CATEGORY_DEBUGING_PROFILING, 1), new StringPropertyDefinition("propertiesTransform", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.connectionPropertiesTransform"), "3.1.4", CATEGORY_CONNECTION, Integer.MIN_VALUE), new IntegerPropertyDefinition("queriesBeforeRetryMaster", 50, true, Messages.getString("ConnectionProperties.queriesBeforeRetryMaster"), "3.0.2", CATEGORY_HA, 7, 0, Integer.MAX_VALUE), new BooleanPropertyDefinition("queryTimeoutKillsConnection", false, true, Messages.getString("ConnectionProperties.queryTimeoutKillsConnection"), "5.1.9", CATEGORY_STATEMENTS, Integer.MIN_VALUE), new BooleanPropertyDefinition("reconnectAtTxEnd", false, true, Messages.getString("ConnectionProperties.reconnectAtTxEnd"), "3.0.10", CATEGORY_HA, 4), new IntegerPropertyDefinition("reportMetricsIntervalMillis", 30000, true, Messages.getString("ConnectionProperties.reportMetricsIntervalMillis"), "3.1.2", CATEGORY_DEBUGING_PROFILING, 3, 0, Integer.MAX_VALUE), new BooleanPropertyDefinition("requireSSL", false, true, Messages.getString("ConnectionProperties.requireSSL"), "3.1.0", CATEGORY_SECURITY, 3), new StringPropertyDefinition("resourceId", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.resourceId"), "5.0.1", CATEGORY_HA, Integer.MIN_VALUE), new IntegerPropertyDefinition("resultSetSizeThreshold", 100, true, Messages.getString("ConnectionProperties.resultSetSizeThreshold"), "5.0.5", CATEGORY_DEBUGING_PROFILING, Integer.MIN_VALUE), new BooleanPropertyDefinition("rewriteBatchedStatements", false, true, Messages.getString("ConnectionProperties.rewriteBatchedStatements"), "3.1.13", CATEGORY_PERFORMANCE, Integer.MIN_VALUE), new BooleanPropertyDefinition("rollbackOnPooledClose", true, true, Messages.getString("ConnectionProperties.rollbackOnPooledClose"), "3.0.15", CATEGORY_CONNECTION, Integer.MIN_VALUE), new IntegerPropertyDefinition("secondsBeforeRetryMaster", 30, true, Messages.getString("ConnectionProperties.secondsBeforeRetryMaster"), "3.0.2", CATEGORY_HA, 8, 0, Integer.MAX_VALUE), new IntegerPropertyDefinition("selfDestructOnPingSecondsLifetime", 0, true, Messages.getString("ConnectionProperties.selfDestructOnPingSecondsLifetime"), "5.1.6", CATEGORY_HA, Integer.MAX_VALUE, 0, Integer.MAX_VALUE), new IntegerPropertyDefinition("selfDestructOnPingMaxOperations", 0, true, Messages.getString("ConnectionProperties.selfDestructOnPingMaxOperations"), "5.1.6", CATEGORY_HA, Integer.MAX_VALUE, 0, Integer.MAX_VALUE), new BooleanPropertyDefinition("ha.enableJMX", false, true, Messages.getString("ConnectionProperties.ha.enableJMX"), "5.1.27", CATEGORY_HA, Integer.MAX_VALUE), new IntegerPropertyDefinition("loadBalanceHostRemovalGracePeriod", 15000, true, Messages.getString("ConnectionProperties.loadBalanceHostRemovalGracePeriod"), "6.0.3", CATEGORY_HA, Integer.MAX_VALUE, 0, Integer.MAX_VALUE), new StringPropertyDefinition("serverTimezone", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.serverTimezone"), "3.0.2", CATEGORY_DATETIMES, Integer.MIN_VALUE), new StringPropertyDefinition("sessionVariables", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.sessionVariables"), "3.1.8", CATEGORY_SESSION, Integer.MAX_VALUE), new IntegerPropertyDefinition("slowQueryThresholdMillis", 2000, true, Messages.getString("ConnectionProperties.slowQueryThresholdMillis"), "3.1.2", CATEGORY_DEBUGING_PROFILING, 9, 0, Integer.MAX_VALUE), new LongPropertyDefinition("slowQueryThresholdNanos", 0L, true, Messages.getString("ConnectionProperties.slowQueryThresholdNanos"), "5.0.7", CATEGORY_DEBUGING_PROFILING, 10), new StringPropertyDefinition("socketFactory", StandardSocketFactory.class.getName(), true, Messages.getString("ConnectionProperties.socketFactory"), "3.0.3", CATEGORY_NETWORK, 4), new StringPropertyDefinition("socksProxyHost", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.socksProxyHost"), "5.1.34", CATEGORY_NETWORK, 1), new IntegerPropertyDefinition("socksProxyPort", SocksProxySocketFactory.SOCKS_DEFAULT_PORT, true, Messages.getString("ConnectionProperties.socksProxyPort"), "5.1.34", CATEGORY_NETWORK, 2, 0, 65535), new IntegerPropertyDefinition("socketTimeout", 0, true, Messages.getString("ConnectionProperties.socketTimeout"), "3.0.1", CATEGORY_NETWORK, 10, 0, Integer.MAX_VALUE), new StringPropertyDefinition("statementInterceptors", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.statementInterceptors"), "5.1.1", CATEGORY_STATEMENTS, Integer.MIN_VALUE), new BooleanPropertyDefinition("strictUpdates", true, true, Messages.getString("ConnectionProperties.strictUpdates"), "3.0.4", CATEGORY_RESULT_SETS, Integer.MIN_VALUE), new BooleanPropertyDefinition("overrideSupportsIntegrityEnhancementFacility", false, true, Messages.getString("ConnectionProperties.overrideSupportsIEF"), "3.1.12", CATEGORY_INTEGRATION, Integer.MIN_VALUE), new BooleanPropertyDefinition("tcpNoDelay", Boolean.valueOf("true"), true, Messages.getString("ConnectionProperties.tcpNoDelay"), "5.0.7", CATEGORY_NETWORK, Integer.MIN_VALUE), new BooleanPropertyDefinition("tcpKeepAlive", Boolean.valueOf("true"), true, Messages.getString("ConnectionProperties.tcpKeepAlive"), "5.0.7", CATEGORY_NETWORK, Integer.MIN_VALUE), new IntegerPropertyDefinition("tcpRcvBuf", Integer.parseInt("0"), true, Messages.getString("ConnectionProperties.tcpSoRcvBuf"), "5.0.7", CATEGORY_NETWORK, Integer.MIN_VALUE, 0, Integer.MAX_VALUE), new IntegerPropertyDefinition("tcpSndBuf", Integer.parseInt("0"), true, Messages.getString("ConnectionProperties.tcpSoSndBuf"), "5.0.7", CATEGORY_NETWORK, Integer.MIN_VALUE, 0, Integer.MAX_VALUE), new IntegerPropertyDefinition("tcpTrafficClass", Integer.parseInt("0"), true, Messages.getString("ConnectionProperties.tcpTrafficClass"), "5.0.7", CATEGORY_NETWORK, Integer.MIN_VALUE, 0, 255), new BooleanPropertyDefinition("tinyInt1isBit", true, true, Messages.getString("ConnectionProperties.tinyInt1isBit"), "3.0.16", CATEGORY_RESULT_SETS, Integer.MIN_VALUE), new BooleanPropertyDefinition("traceProtocol", false, true, Messages.getString("ConnectionProperties.traceProtocol"), "3.1.2", CATEGORY_DEBUGING_PROFILING, Integer.MIN_VALUE), new BooleanPropertyDefinition("treatUtilDateAsTimestamp", true, true, Messages.getString("ConnectionProperties.treatUtilDateAsTimestamp"), "5.0.5", CATEGORY_DATETIMES, Integer.MIN_VALUE), new BooleanPropertyDefinition("transformedBitIsBoolean", false, true, Messages.getString("ConnectionProperties.transformedBitIsBoolean"), "3.1.9", CATEGORY_RESULT_SETS, Integer.MIN_VALUE), new BooleanPropertyDefinition("useCompression", false, true, Messages.getString("ConnectionProperties.useCompression"), "3.0.17", CATEGORY_NETWORK, Integer.MIN_VALUE), new BooleanPropertyDefinition("useColumnNamesInFindColumn", false, true, Messages.getString("ConnectionProperties.useColumnNamesInFindColumn"), "5.1.7", CATEGORY_JDBC, Integer.MAX_VALUE), new StringPropertyDefinition("useConfigs", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.useConfigs"), "3.1.5", CATEGORY_CONNECTION, Integer.MAX_VALUE), new BooleanPropertyDefinition("useCursorFetch", false, true, Messages.getString("ConnectionProperties.useCursorFetch"), "5.0.0", CATEGORY_PERFORMANCE, Integer.MAX_VALUE), new BooleanPropertyDefinition("useHostsInPrivileges", true, true, Messages.getString("ConnectionProperties.useHostsInPrivileges"), "3.0.2", CATEGORY_METADATA, Integer.MIN_VALUE), new BooleanPropertyDefinition("useInformationSchema", false, true, Messages.getString("ConnectionProperties.useInformationSchema"), "5.0.0", CATEGORY_METADATA, Integer.MIN_VALUE), new BooleanPropertyDefinition("useLocalSessionState", false, true, Messages.getString("ConnectionProperties.useLocalSessionState"), "3.1.7", CATEGORY_PERFORMANCE, 5), new BooleanPropertyDefinition("useLocalTransactionState", false, true, Messages.getString("ConnectionProperties.useLocalTransactionState"), "5.1.7", CATEGORY_PERFORMANCE, 6), new BooleanPropertyDefinition("sendFractionalSeconds", true, true, Messages.getString("ConnectionProperties.sendFractionalSeconds"), "5.1.37", CATEGORY_DATETIMES, Integer.MIN_VALUE), new BooleanPropertyDefinition("useNanosForElapsedTime", false, true, Messages.getString("ConnectionProperties.useNanosForElapsedTime"), "5.0.7", CATEGORY_DEBUGING_PROFILING, Integer.MIN_VALUE), new BooleanPropertyDefinition("useOldAliasMetadataBehavior", false, true, Messages.getString("ConnectionProperties.useOldAliasMetadataBehavior"), "5.0.4", CATEGORY_JDBC, Integer.MIN_VALUE), new BooleanPropertyDefinition("useOldUTF8Behavior", false, true, Messages.getString("ConnectionProperties.useOldUtf8Behavior"), "3.1.6", CATEGORY_SESSION, Integer.MIN_VALUE), new BooleanPropertyDefinition("useOnlyServerErrorMessages", true, true, Messages.getString("ConnectionProperties.useOnlyServerErrorMessages"), "3.0.15", CATEGORY_DEBUGING_PROFILING, Integer.MIN_VALUE), new BooleanPropertyDefinition("useReadAheadInput", true, true, Messages.getString("ConnectionProperties.useReadAheadInput"), "3.1.5", CATEGORY_PERFORMANCE, Integer.MIN_VALUE), new BooleanPropertyDefinition("useSSL", false, true, Messages.getString("ConnectionProperties.useSSL"), "3.0.2", CATEGORY_SECURITY, 2), new BooleanPropertyDefinition("useStreamLengthsInPrepStmts", true, true, Messages.getString("ConnectionProperties.useStreamLengthsInPrepStmts"), "3.0.2", CATEGORY_PREPARED_STATEMENTS, Integer.MIN_VALUE), new BooleanPropertyDefinition("ultraDevHack", false, true, Messages.getString("ConnectionProperties.ultraDevHack"), "2.0.3", CATEGORY_INTEGRATION, Integer.MIN_VALUE), new BooleanPropertyDefinition("useUsageAdvisor", false, true, Messages.getString("ConnectionProperties.useUsageAdvisor"), "3.1.1", CATEGORY_DEBUGING_PROFILING, 10), new BooleanPropertyDefinition("yearIsDateType", true, true, Messages.getString("ConnectionProperties.yearIsDateType"), "3.1.9", CATEGORY_DATETIMES, Integer.MIN_VALUE), new StringPropertyDefinition("zeroDateTimeBehavior", "exception", true, Messages.getString("ConnectionProperties.zeroDateTimeBehavior", new Object[]{"exception", "round", "convertToNull"}), "3.1.4", CATEGORY_DATETIMES, Integer.MIN_VALUE, new String[]{"exception", "round", "convertToNull"}), new BooleanPropertyDefinition("useAffectedRows", false, true, Messages.getString("ConnectionProperties.useAffectedRows"), "5.1.7", CATEGORY_CONNECTION, Integer.MIN_VALUE), new IntegerPropertyDefinition("maxAllowedPacket", 65535, true, Messages.getString("ConnectionProperties.maxAllowedPacket"), "5.1.8", CATEGORY_NETWORK, Integer.MIN_VALUE), new StringPropertyDefinition("authenticationPlugins", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.authenticationPlugins"), "5.1.19", CATEGORY_CONNECTION, Integer.MIN_VALUE), new StringPropertyDefinition("disabledAuthenticationPlugins", DEFAULT_VALUE_NULL_STRING, true, Messages.getString("ConnectionProperties.disabledAuthenticationPlugins"), "5.1.19", CATEGORY_CONNECTION, Integer.MIN_VALUE), new StringPropertyDefinition("defaultAuthenticationPlugin", MysqlNativePasswordPlugin.class.getName(), true, Messages.getString("ConnectionProperties.defaultAuthenticationPlugin"), "5.1.19", CATEGORY_CONNECTION, Integer.MIN_VALUE), new BooleanPropertyDefinition("disconnectOnExpiredPasswords", true, true, Messages.getString("ConnectionProperties.disconnectOnExpiredPasswords"), "5.1.23", CATEGORY_CONNECTION, Integer.MIN_VALUE), new BooleanPropertyDefinition("getProceduresReturnsFunctions", true, true, Messages.getString("ConnectionProperties.getProceduresReturnsFunctions"), "5.1.26", CATEGORY_METADATA, Integer.MIN_VALUE), new BooleanPropertyDefinition("detectCustomCollations", false, true, Messages.getString("ConnectionProperties.detectCustomCollations"), "5.1.29", CATEGORY_CONNECTION, Integer.MIN_VALUE), new BooleanPropertyDefinition("dontCheckOnDuplicateKeyUpdateInSQL", false, true, Messages.getString("ConnectionProperties.dontCheckOnDuplicateKeyUpdateInSQL"), "5.1.32", CATEGORY_PERFORMANCE, Integer.MIN_VALUE), new BooleanPropertyDefinition("readOnlyPropagatesToServer", true, true, Messages.getString("ConnectionProperties.readOnlyPropagatesToServer"), "5.1.35", CATEGORY_PERFORMANCE, Integer.MIN_VALUE), new BooleanPropertyDefinition("mysqlx.useAsyncProtocol", true, false, Messages.getString("ConnectionProperties.useAsyncProtocol"), "6.0.0", CATEGORY_MYSQLX, Integer.MIN_VALUE), new BooleanPropertyDefinition("enableEscapeProcessing", true, true, Messages.getString("ConnectionProperties.enableEscapeProcessing"), "6.0.1", CATEGORY_PERFORMANCE, Integer.MIN_VALUE)};
      HashMap<String, PropertyDefinition<?>> propertyNameToPropertyDefinitionMap = new HashMap();
      PropertyDefinition[] var2 = pdefs;
      int var3 = pdefs.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         PropertyDefinition<?> pdef = var2[var4];
         String pname = pdef.getName();
         propertyNameToPropertyDefinitionMap.put(pname, pdef);
      }

      PROPERTY_NAME_TO_PROPERTY_DEFINITION = Collections.unmodifiableMap(propertyNameToPropertyDefinitionMap);
      Map<String, String> aliases = new HashMap<String, String>() {
         private static final long serialVersionUID = -3808474490779118979L;

         {
            this.put("paranoid", "paranoid");
            this.put("passwordCharacterEncoding", "passwordCharacterEncoding");
            this.put("serverRSAPublicKeyFile", "serverRSAPublicKeyFile");
            this.put("allowPublicKeyRetrieval", "allowPublicKeyRetrieval");
            this.put("clientCertificateKeyStoreUrl", "clientCertificateKeyStoreUrl");
            this.put("trustCertificateKeyStoreUrl", "trustCertificateKeyStoreUrl");
            this.put("clientCertificateKeyStoreType", "clientCertificateKeyStoreType");
            this.put("clientCertificateKeyStorePassword", "clientCertificateKeyStorePassword");
            this.put("trustCertificateKeyStoreType", "trustCertificateKeyStoreType");
            this.put("trustCertificateKeyStorePassword", "trustCertificateKeyStorePassword");
            this.put("verifyServerCertificate", "verifyServerCertificate");
            this.put("enabledSSLCipherSuites", "enabledSSLCipherSuites");
            this.put("useUnbufferedInput", "useUnbufferedInput");
            this.put("profilerEventHandler", "profilerEventHandler");
            this.put("allowLoadLocalInfile", "allowLoadLocalInfile");
            this.put("allowMultiQueries", "allowMultiQueries");
            this.put("allowNanAndInf", "allowNanAndInf");
            this.put("allowUrlInLocalInfile", "allowUrlInLocalInfile");
            this.put("alwaysSendSetIsolation", "alwaysSendSetIsolation");
            this.put("autoClosePStmtStreams", "autoClosePStmtStreams");
            this.put("allowMasterDownConnections", "allowMasterDownConnections");
            this.put("allowSlaveDownConnections", "allowSlaveDownConnections");
            this.put("readFromMasterWhenNoSlaves", "readFromMasterWhenNoSlaves");
            this.put("autoDeserialize", "autoDeserialize");
            this.put("autoGenerateTestcaseScript", "autoGenerateTestcaseScript");
            this.put("autoReconnect", "autoReconnect");
            this.put("autoReconnectForPools", "autoReconnectForPools");
            this.put("blobSendChunkSize", "blobSendChunkSize");
            this.put("autoSlowLog", "autoSlowLog");
            this.put("blobsAreStrings", "blobsAreStrings");
            this.put("functionsNeverReturnBlobs", "functionsNeverReturnBlobs");
            this.put("cacheCallableStmts", "cacheCallableStmts");
            this.put("cachePrepStmts", "cachePrepStmts");
            this.put("cacheResultSetMetadata", "cacheResultSetMetadata");
            this.put("serverConfigCacheFactory", "serverConfigCacheFactory");
            this.put("cacheServerConfiguration", "cacheServerConfiguration");
            this.put("callableStmtCacheSize", "callableStmtCacheSize");
            this.put("characterEncoding", "characterEncoding");
            this.put("characterSetResults", "characterSetResults");
            this.put("connectionAttributes", "connectionAttributes");
            this.put("clientInfoProvider", "clientInfoProvider");
            this.put("clobberStreamingResults", "clobberStreamingResults");
            this.put("clobCharacterEncoding", "clobCharacterEncoding");
            this.put("compensateOnDuplicateKeyUpdateCounts", "compensateOnDuplicateKeyUpdateCounts");
            this.put("connectionCollation", "connectionCollation");
            this.put("connectionLifecycleInterceptors", "connectionLifecycleInterceptors");
            this.put("connectTimeout", "connectTimeout");
            this.put("continueBatchOnError", "continueBatchOnError");
            this.put("createDatabaseIfNotExist", "createDatabaseIfNotExist");
            this.put("defaultFetchSize", "defaultFetchSize");
            this.put("useServerPrepStmts", "useServerPrepStmts");
            this.put("dontTrackOpenResources", "dontTrackOpenResources");
            this.put("dumpQueriesOnException", "dumpQueriesOnException");
            this.put("elideSetAutoCommits", "elideSetAutoCommits");
            this.put("emptyStringsConvertToZero", "emptyStringsConvertToZero");
            this.put("emulateLocators", "emulateLocators");
            this.put("emulateUnsupportedPstmts", "emulateUnsupportedPstmts");
            this.put("enablePacketDebug", "enablePacketDebug");
            this.put("enableQueryTimeouts", "enableQueryTimeouts");
            this.put("explainSlowQueries", "explainSlowQueries");
            this.put("exceptionInterceptors", "exceptionInterceptors");
            this.put("failOverReadOnly", "failOverReadOnly");
            this.put("gatherPerfMetrics", "gatherPerfMetrics");
            this.put("generateSimpleParameterMetadata", "generateSimpleParameterMetadata");
            this.put("holdResultsOpenOverStatementClose", "holdResultsOpenOverStatementClose");
            this.put("includeInnodbStatusInDeadlockExceptions", "includeInnodbStatusInDeadlockExceptions");
            this.put("includeThreadDumpInDeadlockExceptions", "includeThreadDumpInDeadlockExceptions");
            this.put("includeThreadNamesAsStatementComment", "includeThreadNamesAsStatementComment");
            this.put("ignoreNonTxTables", "ignoreNonTxTables");
            this.put("initialTimeout", "initialTimeout");
            this.put("interactiveClient", "interactiveClient");
            this.put("jdbcCompliantTruncation", "jdbcCompliantTruncation");
            this.put("largeRowSizeThreshold", "largeRowSizeThreshold");
            this.put("ha.loadBalanceStrategy", "loadBalanceStrategy");
            this.put("loadBalanceBlacklistTimeout", "loadBalanceBlacklistTimeout");
            this.put("loadBalancePingTimeout", "loadBalancePingTimeout");
            this.put("loadBalanceValidateConnectionOnSwapServer", "loadBalanceValidateConnectionOnSwapServer");
            this.put("loadBalanceConnectionGroup", "loadBalanceConnectionGroup");
            this.put("loadBalanceExceptionChecker", "loadBalanceExceptionChecker");
            this.put("loadBalanceSQLStateFailover", "loadBalanceSQLStateFailover");
            this.put("loadBalanceSQLExceptionSubclassFailover", "loadBalanceSQLExceptionSubclassFailover");
            this.put("loadBalanceAutoCommitStatementRegex", "loadBalanceAutoCommitStatementRegex");
            this.put("loadBalanceAutoCommitStatementThreshold", "loadBalanceAutoCommitStatementThreshold");
            this.put("localSocketAddress", "localSocketAddress");
            this.put("locatorFetchBufferSize", "locatorFetchBufferSize");
            this.put("logger", "logger");
            this.put("logSlowQueries", "logSlowQueries");
            this.put("logXaCommands", "logXaCommands");
            this.put("maintainTimeStats", "maintainTimeStats");
            this.put("maxQuerySizeToLog", "maxQuerySizeToLog");
            this.put("maxReconnects", "maxReconnects");
            this.put("retriesAllDown", "retriesAllDown");
            this.put("maxRows", "maxRows");
            this.put("metadataCacheSize", "metadataCacheSize");
            this.put("netTimeoutForStreamingResults", "netTimeoutForStreamingResults");
            this.put("noAccessToProcedureBodies", "noAccessToProcedureBodies");
            this.put("noDatetimeStringSync", "noDatetimeStringSync");
            this.put("nullCatalogMeansCurrent", "nullCatalogMeansCurrent");
            this.put("nullNamePatternMatchesAll", "nullNamePatternMatchesAll");
            this.put("packetDebugBufferSize", "packetDebugBufferSize");
            this.put("padCharsWithSpace", "padCharsWithSpace");
            this.put("pedantic", "pedantic");
            this.put("pinGlobalTxToPhysicalConnection", "pinGlobalTxToPhysicalConnection");
            this.put("populateInsertRowWithDefaultValues", "populateInsertRowWithDefaultValues");
            this.put("prepStmtCacheSize", "prepStmtCacheSize");
            this.put("prepStmtCacheSqlLimit", "prepStmtCacheSqlLimit");
            this.put("parseInfoCacheFactory", "parseInfoCacheFactory");
            this.put("processEscapeCodesForPrepStmts", "processEscapeCodesForPrepStmts");
            this.put("profileSQL", "profileSQL");
            this.put("propertiesTransform", "propertiesTransform");
            this.put("queriesBeforeRetryMaster", "queriesBeforeRetryMaster");
            this.put("queryTimeoutKillsConnection", "queryTimeoutKillsConnection");
            this.put("reconnectAtTxEnd", "reconnectAtTxEnd");
            this.put("reportMetricsIntervalMillis", "reportMetricsIntervalMillis");
            this.put("requireSSL", "requireSSL");
            this.put("resourceId", "resourceId");
            this.put("resultSetSizeThreshold", "resultSetSizeThreshold");
            this.put("rewriteBatchedStatements", "rewriteBatchedStatements");
            this.put("rollbackOnPooledClose", "rollbackOnPooledClose");
            this.put("secondsBeforeRetryMaster", "secondsBeforeRetryMaster");
            this.put("selfDestructOnPingSecondsLifetime", "selfDestructOnPingSecondsLifetime");
            this.put("selfDestructOnPingMaxOperations", "selfDestructOnPingMaxOperations");
            this.put("ha.enableJMX", "replicationEnableJMX");
            this.put("loadBalanceHostRemovalGracePeriod", "loadBalanceHostRemovalGracePeriod");
            this.put("serverTimezone", "serverTimezone");
            this.put("sessionVariables", "sessionVariables");
            this.put("slowQueryThresholdMillis", "slowQueryThresholdMillis");
            this.put("slowQueryThresholdNanos", "slowQueryThresholdNanos");
            this.put("socketFactory", "socketFactory");
            this.put("socksProxyHost", "socksProxyHost");
            this.put("socksProxyPort", "socksProxyPort");
            this.put("socketTimeout", "socketTimeout");
            this.put("statementInterceptors", "statementInterceptors");
            this.put("strictUpdates", "strictUpdates");
            this.put("overrideSupportsIntegrityEnhancementFacility", "overrideSupportsIntegrityEnhancementFacility");
            this.put("tcpNoDelay", "tcpNoDelay");
            this.put("tcpKeepAlive", "tcpKeepAlive");
            this.put("tcpRcvBuf", "tcpRcvBuf");
            this.put("tcpSndBuf", "tcpSndBuf");
            this.put("tcpTrafficClass", "tcpTrafficClass");
            this.put("tinyInt1isBit", "tinyInt1isBit");
            this.put("traceProtocol", "traceProtocol");
            this.put("treatUtilDateAsTimestamp", "treatUtilDateAsTimestamp");
            this.put("transformedBitIsBoolean", "transformedBitIsBoolean");
            this.put("useCompression", "useCompression");
            this.put("useColumnNamesInFindColumn", "useColumnNamesInFindColumn");
            this.put("useConfigs", "useConfigs");
            this.put("useCursorFetch", "useCursorFetch");
            this.put("useHostsInPrivileges", "useHostsInPrivileges");
            this.put("useInformationSchema", "useInformationSchema");
            this.put("useLocalSessionState", "useLocalSessionState");
            this.put("useLocalTransactionState", "useLocalTransactionState");
            this.put("sendFractionalSeconds", "sendFractionalSeconds");
            this.put("useNanosForElapsedTime", "useNanosForElapsedTime");
            this.put("useOldAliasMetadataBehavior", "useOldAliasMetadataBehavior");
            this.put("useOldUTF8Behavior", "useOldUTF8Behavior");
            this.put("useOnlyServerErrorMessages", "useOnlyServerErrorMessages");
            this.put("useReadAheadInput", "useReadAheadInput");
            this.put("useSSL", "useSSL");
            this.put("useStreamLengthsInPrepStmts", "useStreamLengthsInPrepStmts");
            this.put("ultraDevHack", "ultraDevHack");
            this.put("useUsageAdvisor", "useUsageAdvisor");
            this.put("yearIsDateType", "yearIsDateType");
            this.put("zeroDateTimeBehavior", "zeroDateTimeBehavior");
            this.put("useAffectedRows", "useAffectedRows");
            this.put("maxAllowedPacket", "maxAllowedPacket");
            this.put("authenticationPlugins", "authenticationPlugins");
            this.put("disabledAuthenticationPlugins", "disabledAuthenticationPlugins");
            this.put("defaultAuthenticationPlugin", "defaultAuthenticationPlugin");
            this.put("disconnectOnExpiredPasswords", "disconnectOnExpiredPasswords");
            this.put("getProceduresReturnsFunctions", "getProceduresReturnsFunctions");
            this.put("detectCustomCollations", "detectCustomCollations");
            this.put("dontCheckOnDuplicateKeyUpdateInSQL", "dontCheckOnDuplicateKeyUpdateInSQL");
            this.put("readOnlyPropagatesToServer", "readOnlyPropagatesToServer");
         }
      };
      PROPERTY_NAME_TO_ALIAS = Collections.unmodifiableMap(aliases);
   }

   static class XmlMap {
      protected Map<Integer, Map<String, PropertyDefinition<?>>> ordered = new TreeMap();
      protected Map<String, PropertyDefinition<?>> alpha = new TreeMap();
   }
}
