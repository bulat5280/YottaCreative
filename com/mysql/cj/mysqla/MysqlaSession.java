package com.mysql.cj.mysqla;

import com.mysql.cj.api.MysqlConnection;
import com.mysql.cj.api.ProfilerEventHandler;
import com.mysql.cj.api.Session;
import com.mysql.cj.api.conf.ModifiableProperty;
import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.io.SocketConnection;
import com.mysql.cj.api.io.SocketFactory;
import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.api.jdbc.Statement;
import com.mysql.cj.api.jdbc.interceptors.StatementInterceptor;
import com.mysql.cj.api.mysqla.io.NativeProtocol;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.io.ProtocolEntityFactory;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.api.mysqla.result.Resultset;
import com.mysql.cj.core.AbstractSession;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.ServerVersion;
import com.mysql.cj.core.conf.url.HostInfo;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.core.io.NetworkResources;
import com.mysql.cj.core.log.LogFactory;
import com.mysql.cj.core.profiler.ProfilerEventHandlerFactory;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.jdbc.StatementImpl;
import com.mysql.cj.jdbc.util.TimeUtil;
import com.mysql.cj.mysqla.io.MysqlaProtocol;
import com.mysql.cj.mysqla.io.MysqlaServerSession;
import com.mysql.cj.mysqla.io.MysqlaSocketConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.SocketAddress;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.Executor;

public class MysqlaSession extends AbstractSession implements Session, Serializable {
   private static final long serialVersionUID = 5323638898749073419L;
   protected transient MysqlaProtocol protocol;
   private TimeZone serverTimezoneTZ = null;
   private TimeZone defaultTimeZone = TimeZone.getDefault();
   private int sessionMaxRows = -1;
   private HostInfo hostInfo = null;
   protected ModifiableProperty<Integer> socketTimeout;
   private boolean serverHasFracSecsSupport = true;

   public MysqlaSession(HostInfo hostInfo, PropertySet propSet) {
      this.propertySet = propSet;
      this.socketTimeout = this.getPropertySet().getModifiableProperty("socketTimeout");
      this.hostInfo = hostInfo;
      this.log = LogFactory.getLogger(this.getPropertySet().getStringReadableProperty("logger").getStringValue(), "MySQL", this.getExceptionInterceptor());
   }

   public void connect(MysqlConnection conn, HostInfo hi, Properties mergedProps, String user, String password, String database, int loginTimeout) throws IOException {
      this.hostInfo = hi;
      this.setSessionMaxRows(-1);
      SocketConnection socketConnection = new MysqlaSocketConnection();
      socketConnection.connect(this.hostInfo.getHost(), this.hostInfo.getPort(), mergedProps, this.getPropertySet(), this.getExceptionInterceptor(), this.log, loginTimeout);
      this.protocol = MysqlaProtocol.getInstance(conn, socketConnection, this.propertySet, this.log);
      this.protocol.connect(user, password, database);
      this.serverHasFracSecsSupport = this.protocol.versionMeetsMinimum(5, 6, 4);
      this.setErrorMessageEncoding(this.protocol.getAuthenticationProvider().getEncodingForHandshake());
   }

   public MysqlaProtocol getProtocol() {
      return this.protocol;
   }

   public void changeUser(String userName, String password, String database) {
      this.sessionMaxRows = -1;
      this.protocol.changeUser(userName, password, database);
   }

   public boolean characterSetNamesMatches(String mysqlEncodingName) {
      return this.protocol.getServerSession().characterSetNamesMatches(mysqlEncodingName);
   }

   public String getServerVariable(String name) {
      return this.protocol.getServerSession().getServerVariable(name);
   }

   public int getServerVariable(String variableName, int fallbackValue) {
      try {
         return Integer.valueOf(this.getServerVariable(variableName));
      } catch (NumberFormatException var4) {
         this.getLog().logWarn(Messages.getString("Connection.BadValueInServerVariables", new Object[]{variableName, this.getServerVariable(variableName), fallbackValue}));
         return fallbackValue;
      }
   }

   public boolean inTransactionOnServer() {
      return this.protocol.getServerSession().inTransactionOnServer();
   }

   public int getServerDefaultCollationIndex() {
      return this.protocol.getServerSession().getServerDefaultCollationIndex();
   }

   public void setServerDefaultCollationIndex(int serverDefaultCollationIndex) {
      this.protocol.getServerSession().setServerDefaultCollationIndex(serverDefaultCollationIndex);
   }

   public Map<String, String> getServerVariables() {
      return this.protocol.getServerSession().getServerVariables();
   }

   public void setServerVariables(Map<String, String> serverVariables) {
      this.protocol.getServerSession().setServerVariables(serverVariables);
   }

   public void abortInternal() {
      if (this.protocol != null) {
         try {
            this.protocol.getSocketConnection().forceClose();
            this.protocol.releaseResources();
         } catch (Throwable var2) {
         }
      }

   }

   public void quit() {
      if (this.protocol != null) {
         try {
            this.protocol.quit();
         } catch (Exception var2) {
         }
      }

   }

   public void forceClose() {
      this.abortInternal();
   }

   public ServerVersion getServerVersion() {
      return this.protocol.getServerSession().getServerVersion();
   }

   public boolean versionMeetsMinimum(int major, int minor, int subminor) {
      return this.protocol.versionMeetsMinimum(major, minor, subminor);
   }

   public void enableMultiQueries() {
      PacketPayload buf = this.protocol.getSharedSendPacket();
      buf.writeInteger(NativeProtocol.IntegerDataType.INT1, 27L);
      buf.writeInteger(NativeProtocol.IntegerDataType.INT2, 0L);
      this.sendCommand(27, (String)null, buf, false, (String)null, 0);
   }

   public void disableMultiQueries() {
      PacketPayload buf = this.protocol.getSharedSendPacket();
      buf.writeInteger(NativeProtocol.IntegerDataType.INT1, 27L);
      buf.writeInteger(NativeProtocol.IntegerDataType.INT2, 1L);
      this.sendCommand(27, (String)null, buf, false, (String)null, 0);
   }

   public long getThreadId() {
      return this.protocol.getServerSession().getCapabilities().getThreadId();
   }

   public boolean isSetNeededForAutoCommitMode(boolean autoCommitFlag) {
      return this.protocol.getServerSession().isSetNeededForAutoCommitMode(autoCommitFlag, (Boolean)this.getPropertySet().getBooleanReadableProperty("elideSetAutoCommits").getValue());
   }

   public void configureTimezone() {
      String configuredTimeZoneOnServer = this.getServerVariable("time_zone");
      if ("SYSTEM".equalsIgnoreCase(configuredTimeZoneOnServer)) {
         configuredTimeZoneOnServer = this.getServerVariable("system_time_zone");
      }

      String canonicalTimezone = (String)this.getPropertySet().getStringReadableProperty("serverTimezone").getValue();
      if (configuredTimeZoneOnServer != null && (canonicalTimezone == null || StringUtils.isEmptyOrWhitespaceOnly(canonicalTimezone))) {
         try {
            canonicalTimezone = TimeUtil.getCanonicalTimezone(configuredTimeZoneOnServer, this.getExceptionInterceptor());
         } catch (IllegalArgumentException var4) {
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, var4.getMessage(), this.getExceptionInterceptor());
         }
      }

      if (canonicalTimezone != null && canonicalTimezone.length() > 0) {
         this.serverTimezoneTZ = TimeZone.getTimeZone(canonicalTimezone);
         if (!canonicalTimezone.equalsIgnoreCase("GMT") && this.serverTimezoneTZ.getID().equals("GMT")) {
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("Connection.9", new Object[]{canonicalTimezone}), this.getExceptionInterceptor());
         }
      }

      this.defaultTimeZone = this.serverTimezoneTZ;
   }

   public TimeZone getDefaultTimeZone() {
      return this.defaultTimeZone;
   }

   public String getErrorMessageEncoding() {
      return this.protocol.getServerSession().getErrorMessageEncoding();
   }

   public void setErrorMessageEncoding(String errorMessageEncoding) {
      this.protocol.getServerSession().setErrorMessageEncoding(errorMessageEncoding);
   }

   public String getServerCharset() {
      return this.protocol.getServerSession().getServerDefaultCharset();
   }

   public int getMaxBytesPerChar(String javaCharsetName) {
      return this.protocol.getServerSession().getMaxBytesPerChar(javaCharsetName);
   }

   public int getMaxBytesPerChar(Integer charsetIndex, String javaCharsetName) {
      return this.protocol.getServerSession().getMaxBytesPerChar(charsetIndex, javaCharsetName);
   }

   public String getEncodingForIndex(int charsetIndex) {
      return this.protocol.getServerSession().getEncodingForIndex(charsetIndex);
   }

   public int getSessionMaxRows() {
      return this.sessionMaxRows;
   }

   public void setSessionMaxRows(int sessionMaxRows) {
      this.sessionMaxRows = sessionMaxRows;
   }

   public HostInfo getHostInfo() {
      return this.hostInfo;
   }

   public void setCharsetMaps(Map<Integer, String> indexToCharset, Map<Integer, String> customCharset, Map<String, Integer> customMblen) {
      this.protocol.getServerSession().indexToMysqlCharset = Collections.unmodifiableMap(indexToCharset);
      if (customCharset != null) {
         this.protocol.getServerSession().indexToCustomMysqlCharset = Collections.unmodifiableMap(customCharset);
      }

      if (customMblen != null) {
         this.protocol.getServerSession().mysqlCharsetToCustomMblen = Collections.unmodifiableMap(customMblen);
      }

   }

   public void setStatementInterceptors(List<StatementInterceptor> statementInterceptors) {
      this.protocol.setStatementInterceptors(statementInterceptors);
   }

   public boolean isServerLocal(JdbcConnection conn) {
      synchronized(conn.getConnectionMutex()) {
         SocketFactory factory = this.protocol.getSocketConnection().getSocketFactory();
         return factory.isLocallyConnected(conn);
      }
   }

   public void shutdownServer() throws SQLException {
      this.sendCommand(8, (String)null, (PacketPayload)null, false, (String)null, 0);
   }

   public void setSocketTimeout(Executor executor, final int milliseconds) {
      executor.execute(new Runnable() {
         public void run() {
            MysqlaSession.this.socketTimeout.setValue(milliseconds);
            MysqlaSession.this.protocol.setSocketTimeout(milliseconds);
         }
      });
   }

   public int getSocketTimeout() {
      return (Integer)this.socketTimeout.getValue();
   }

   public final <T extends Resultset> T sqlQueryDirect(StatementImpl callingStatement, String query, String characterEncoding, PacketPayload queryPacket, int maxRows, boolean streamResults, String catalog, ColumnDefinition cachedMetadata, ProtocolEntityFactory<T> resultSetFactory) throws IOException {
      return this.protocol.sqlQueryDirect(callingStatement, query, characterEncoding, queryPacket, maxRows, streamResults, catalog, cachedMetadata, this::getProfilerEventHandlerInstanceFunction, resultSetFactory);
   }

   public void checkForCharsetMismatch() {
      this.protocol.checkForCharsetMismatch();
   }

   public PacketPayload getSharedSendPacket() {
      return this.protocol.getSharedSendPacket();
   }

   public void dumpPacketRingBuffer() {
      this.protocol.dumpPacketRingBuffer();
   }

   public <T extends Resultset> T invokeStatementInterceptorsPre(String sql, Statement interceptedStatement, boolean forceExecute) {
      return this.protocol.invokeStatementInterceptorsPre(sql, interceptedStatement, forceExecute);
   }

   public <T extends Resultset> T invokeStatementInterceptorsPost(String sql, Statement interceptedStatement, T originalResultSet, boolean forceExecute, Exception statementException) {
      return this.protocol.invokeStatementInterceptorsPost(sql, interceptedStatement, originalResultSet, forceExecute, statementException);
   }

   public boolean shouldIntercept() {
      return this.protocol.getStatementInterceptors() != null;
   }

   public long getCurrentTimeNanosOrMillis() {
      return this.protocol.getCurrentTimeNanosOrMillis();
   }

   public final PacketPayload sendCommand(int command, String extraData, PacketPayload queryPacket, boolean skipCheck, String extraDataCharEncoding, int timeoutMillis) {
      return this.protocol.sendCommand(command, extraData, queryPacket, skipCheck, extraDataCharEncoding, timeoutMillis);
   }

   public long getSlowQueryThreshold() {
      return this.protocol.getSlowQueryThreshold();
   }

   public String getQueryTimingUnits() {
      return this.protocol.getQueryTimingUnits();
   }

   public void explainSlowQuery(byte[] querySQL, String truncatedQuery) {
      this.protocol.explainSlowQuery(querySQL, truncatedQuery);
   }

   public boolean hadWarnings() {
      return this.protocol.hadWarnings();
   }

   public void clearInputStream() {
      this.protocol.clearInputStream();
   }

   public final PacketPayload readPacket() {
      return this.protocol.readPacket((PacketPayload)null);
   }

   public NetworkResources getNetworkResources() {
      return this.protocol.getSocketConnection().getNetworkResources();
   }

   public MysqlaServerSession getServerSession() {
      return this.protocol.getServerSession();
   }

   public boolean isSSLEstablished() {
      return this.protocol.getSocketConnection().isSSLEstablished();
   }

   public int getCommandCount() {
      return this.protocol.getCommandCount();
   }

   public SocketAddress getRemoteSocketAddress() {
      return this.protocol.getSocketConnection().getMysqlSocket().getRemoteSocketAddress();
   }

   public boolean serverSupportsFracSecs() {
      return this.serverHasFracSecsSupport;
   }

   public ProfilerEventHandler getProfilerEventHandlerInstanceFunction() {
      return ProfilerEventHandlerFactory.getInstance(this);
   }

   public InputStream getLocalInfileInputStream() {
      return this.protocol.getLocalInfileInputStream();
   }

   public void setLocalInfileInputStream(InputStream stream) {
      this.protocol.setLocalInfileInputStream(stream);
   }
}
