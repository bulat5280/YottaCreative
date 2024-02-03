package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.MysqlConnection;
import com.mysql.cj.api.ProfilerEventHandler;
import com.mysql.cj.api.authentication.AuthenticationProvider;
import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.conf.ReadableProperty;
import com.mysql.cj.api.conf.RuntimeProperty;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.io.PacketReceivedTimeHolder;
import com.mysql.cj.api.io.PacketSentTimeHolder;
import com.mysql.cj.api.io.Protocol;
import com.mysql.cj.api.io.ServerSession;
import com.mysql.cj.api.io.SocketConnection;
import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.api.jdbc.Statement;
import com.mysql.cj.api.jdbc.interceptors.StatementInterceptor;
import com.mysql.cj.api.log.Log;
import com.mysql.cj.api.mysqla.io.NativeProtocol;
import com.mysql.cj.api.mysqla.io.PacketHeader;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.io.PacketReader;
import com.mysql.cj.api.mysqla.io.PacketSender;
import com.mysql.cj.api.mysqla.io.ProtocolEntityFactory;
import com.mysql.cj.api.mysqla.io.ProtocolEntityReader;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.api.mysqla.result.ProtocolEntity;
import com.mysql.cj.api.mysqla.result.Resultset;
import com.mysql.cj.api.mysqla.result.ResultsetRow;
import com.mysql.cj.api.mysqla.result.ResultsetRows;
import com.mysql.cj.core.Constants;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.MysqlType;
import com.mysql.cj.core.ServerVersion;
import com.mysql.cj.core.exceptions.CJConnectionFeatureNotAvailableException;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.core.exceptions.CJPacketTooBigException;
import com.mysql.cj.core.exceptions.CJTimeoutException;
import com.mysql.cj.core.exceptions.ClosedOnExpiredPasswordException;
import com.mysql.cj.core.exceptions.DataTruncationException;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.FeatureNotAvailableException;
import com.mysql.cj.core.exceptions.OperationCancelledException;
import com.mysql.cj.core.exceptions.PasswordExpiredException;
import com.mysql.cj.core.exceptions.UnableToConnectException;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.core.io.AbstractProtocol;
import com.mysql.cj.core.io.ExportControlled;
import com.mysql.cj.core.profiler.ProfilerEventImpl;
import com.mysql.cj.core.util.LazyString;
import com.mysql.cj.core.util.LogUtils;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.core.util.TestUtils;
import com.mysql.cj.core.util.Util;
import com.mysql.cj.jdbc.PreparedStatement;
import com.mysql.cj.jdbc.StatementImpl;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.jdbc.util.ResultSetUtil;
import com.mysql.cj.jdbc.util.TimeUtil;
import com.mysql.cj.mysqla.authentication.MysqlaAuthenticationProvider;
import com.mysql.cj.mysqla.result.OkPacket;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MysqlaProtocol extends AbstractProtocol implements NativeProtocol, RuntimeProperty.RuntimePropertyListener {
   protected static final int INITIAL_PACKET_SIZE = 1024;
   protected static final int COMP_HEADER_LENGTH = 3;
   protected static final int MAX_QUERY_SIZE_TO_EXPLAIN = 1048576;
   private static final String EXPLAINABLE_STATEMENT = "SELECT";
   private static final String[] EXPLAINABLE_STATEMENT_EXTENSION = new String[]{"INSERT", "UPDATE", "REPLACE", "DELETE"};
   protected PacketSender packetSender;
   protected PacketReader packetReader;
   protected MysqlaServerSession serverSession;
   protected CompressedPacketSender compressedPacketSender;
   private PacketPayload sendPacket = null;
   protected PacketPayload sharedSendPacket = null;
   protected PacketPayload reusablePacket = null;
   private SoftReference<PacketPayload> loadFileBufRef;
   protected byte packetSequence = 0;
   protected boolean useCompression = false;
   private ReadableProperty<Integer> maxAllowedPacket;
   private boolean needToGrabQueryFromPacket;
   private boolean autoGenerateTestcaseScript;
   private boolean logSlowQueries = false;
   private boolean useAutoSlowLog;
   private boolean profileSQL = false;
   private boolean useNanosForElapsedTime;
   private long slowQueryThreshold;
   private String queryTimingUnits;
   private int commandCount = 0;
   protected boolean hadWarnings = false;
   private int warningCount = 0;
   private boolean queryBadIndexUsed = false;
   private boolean queryNoIndexUsed = false;
   private boolean serverQueryWasSlow = false;
   protected Map<Class<? extends ProtocolEntity>, ProtocolEntityReader<? extends ProtocolEntity>> PROTOCOL_ENTITY_CLASS_TO_TEXT_READER;
   protected Map<Class<? extends ProtocolEntity>, ProtocolEntityReader<? extends ProtocolEntity>> PROTOCOL_ENTITY_CLASS_TO_BINARY_READER;
   protected boolean platformDbCharsetMatches = true;
   private int statementExecutionDepth = 0;
   private List<StatementInterceptor> statementInterceptors;
   private ReadableProperty<Boolean> maintainTimeStats;
   private ReadableProperty<Integer> maxQuerySizeToLog;
   private InputStream localInfileInputStream;
   private static String jvmPlatformCharset = null;
   private ResultsetRows streamingData = null;

   public static MysqlaProtocol getInstance(MysqlConnection conn, SocketConnection socketConnection, PropertySet propertySet, Log log) {
      MysqlaProtocol protocol = new MysqlaProtocol(log);
      protocol.init(conn, (Integer)propertySet.getIntegerReadableProperty("socketTimeout").getValue(), socketConnection, propertySet);
      return protocol;
   }

   public MysqlaProtocol(Log logger) {
      this.log = logger;
   }

   public void init(MysqlConnection conn, int socketTimeout, SocketConnection phConnection, PropertySet propSet) {
      this.connection = conn;
      this.propertySet = propSet;
      this.socketConnection = phConnection;
      this.exceptionInterceptor = this.socketConnection.getExceptionInterceptor();
      this.maintainTimeStats = this.propertySet.getBooleanReadableProperty("maintainTimeStats");
      this.maxQuerySizeToLog = this.propertySet.getIntegerReadableProperty("maxQuerySizeToLog");
      this.useAutoSlowLog = (Boolean)this.propertySet.getBooleanReadableProperty("autoSlowLog").getValue();
      this.logSlowQueries = (Boolean)this.propertySet.getBooleanReadableProperty("logSlowQueries").getValue();
      this.maxAllowedPacket = this.propertySet.getIntegerReadableProperty("maxAllowedPacket");
      this.profileSQL = (Boolean)this.propertySet.getBooleanReadableProperty("profileSQL").getValue();
      this.autoGenerateTestcaseScript = (Boolean)this.propertySet.getBooleanReadableProperty("autoGenerateTestcaseScript").getValue();
      this.reusablePacket = new Buffer(1024);
      this.sendPacket = new Buffer(1024);
      this.packetSender = new SimplePacketSender(this.socketConnection.getMysqlOutput());
      this.packetReader = new SimplePacketReader(this.socketConnection, this.maxAllowedPacket);
      this.needToGrabQueryFromPacket = this.profileSQL || this.logSlowQueries || this.autoGenerateTestcaseScript;
      if ((Boolean)this.propertySet.getBooleanReadableProperty("useNanosForElapsedTime").getValue() && TimeUtil.nanoTimeAvailable()) {
         this.useNanosForElapsedTime = true;
         this.queryTimingUnits = Messages.getString("Nanoseconds");
      } else {
         this.queryTimingUnits = Messages.getString("Milliseconds");
      }

      if ((Boolean)this.propertySet.getBooleanReadableProperty("logSlowQueries").getValue()) {
         this.calculateSlowQueryThreshold();
      }

      this.authProvider = new MysqlaAuthenticationProvider(this.log);
      this.authProvider.init(this, this.getPropertySet(), this.socketConnection.getExceptionInterceptor());
      Map<Class<? extends ProtocolEntity>, ProtocolEntityReader<? extends ProtocolEntity>> protocolEntityClassToTextReader = new HashMap();
      protocolEntityClassToTextReader.put(ColumnDefinition.class, new ColumnDefinitionReader(this));
      protocolEntityClassToTextReader.put(ResultsetRow.class, new ResultsetRowReader(this));
      protocolEntityClassToTextReader.put(Resultset.class, new TextResultsetReader(this));
      this.PROTOCOL_ENTITY_CLASS_TO_TEXT_READER = Collections.unmodifiableMap(protocolEntityClassToTextReader);
      Map<Class<? extends ProtocolEntity>, ProtocolEntityReader<? extends ProtocolEntity>> protocolEntityClassToBinaryReader = new HashMap();
      protocolEntityClassToBinaryReader.put(ColumnDefinition.class, new ColumnDefinitionReader(this));
      protocolEntityClassToBinaryReader.put(Resultset.class, new BinaryResultsetReader(this));
      this.PROTOCOL_ENTITY_CLASS_TO_BINARY_READER = Collections.unmodifiableMap(protocolEntityClassToBinaryReader);
   }

   public PacketSender getPacketSender() {
      return this.packetSender;
   }

   public PacketReader getPacketReader() {
      return this.packetReader;
   }

   public void negotiateSSLConnection(int packLength) {
      if (!ExportControlled.enabled()) {
         throw new CJConnectionFeatureNotAvailableException(this.getPropertySet(), this.serverSession, this.getPacketSentTimeHolder().getLastPacketSentTime(), (Exception)null);
      } else {
         long clientParam = this.serverSession.getClientParam();
         clientParam |= 2048L;
         this.serverSession.setClientParam(clientParam);
         PacketPayload packet = new Buffer(packLength);
         packet.writeInteger(NativeProtocol.IntegerDataType.INT4, clientParam);
         packet.writeInteger(NativeProtocol.IntegerDataType.INT4, 16777215L);
         packet.writeInteger(NativeProtocol.IntegerDataType.INT1, (long)AuthenticationProvider.getCharsetForHandshake(this.authProvider.getEncodingForHandshake(), this.serverSession.getCapabilities().getServerVersion()));
         packet.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, new byte[23]);
         this.send(packet, packet.getPosition());

         try {
            ExportControlled.transformSocketToSSLSocket(this.socketConnection, this.serverSession.getServerVersion());
         } catch (FeatureNotAvailableException var6) {
            throw new CJConnectionFeatureNotAvailableException(this.getPropertySet(), this.serverSession, this.getPacketSentTimeHolder().getLastPacketSentTime(), var6);
         } catch (IOException var7) {
            throw ExceptionFactory.createCommunicationsException(this.getConnection().getPropertySet(), this.serverSession, this.getPacketSentTimeHolder().getLastPacketSentTime(), this.getPacketReceivedTimeHolder().getLastPacketReceivedTime(), var7, this.getExceptionInterceptor());
         }

         this.packetSender = new SimplePacketSender(this.socketConnection.getMysqlOutput());
         this.packetReader = new SimplePacketReader(this.socketConnection, this.maxAllowedPacket);
      }
   }

   public void rejectConnection(String message) {
      try {
         ((JdbcConnection)this.connection).close();
      } catch (SQLException var3) {
         throw ExceptionFactory.createException((String)var3.getMessage(), (Throwable)var3, (ExceptionInterceptor)this.getExceptionInterceptor());
      }

      this.socketConnection.forceClose();
      throw (UnableToConnectException)ExceptionFactory.createException(UnableToConnectException.class, message, this.getExceptionInterceptor());
   }

   public void rejectProtocol(PacketPayload buf) {
      try {
         this.socketConnection.getMysqlSocket().close();
      } catch (Exception var7) {
      }

      int errno = true;
      int errno = (int)buf.readInteger(NativeProtocol.IntegerDataType.INT2);
      String serverErrorMessage = "";

      try {
         serverErrorMessage = buf.readString(NativeProtocol.StringSelfDataType.STRING_TERM, "ASCII");
      } catch (Exception var6) {
      }

      StringBuilder errorBuf = new StringBuilder(Messages.getString("Protocol.0"));
      errorBuf.append(serverErrorMessage);
      errorBuf.append("\"");
      String xOpen = SQLError.mysqlToSqlState(errno);
      throw ExceptionFactory.createException(SQLError.get(xOpen) + ", " + errorBuf.toString(), xOpen, errno, false, (Throwable)null, this.getExceptionInterceptor());
   }

   public void beforeHandshake() {
      this.packetReader.resetPacketSequence();
      this.serverSession = new MysqlaServerSession(this.propertySet);
      MysqlaCapabilities capabilities = this.readServerCapabilities();
      this.serverSession.setCapabilities(capabilities);
   }

   public void afterHandshake() {
      this.checkTransactionState();
      PropertySet pset = this.getPropertySet();
      if ((this.serverSession.getCapabilities().getCapabilityFlags() & 32) != 0 && (Boolean)pset.getBooleanReadableProperty("useCompression").getValue() && !(this.socketConnection.getMysqlInput().getUnderlyingStream() instanceof CompressedInputStream)) {
         this.useCompression = true;
         this.socketConnection.setMysqlInput(new CompressedInputStream(this.socketConnection.getMysqlInput(), pset.getBooleanReadableProperty("traceProtocol"), this.log));
         this.compressedPacketSender = new CompressedPacketSender(this.socketConnection.getMysqlOutput());
         this.packetSender = this.compressedPacketSender;
      }

      this.applyPacketDecorators(this.packetSender, this.packetReader);

      try {
         this.socketConnection.setMysqlSocket(this.socketConnection.getSocketFactory().afterHandshake());
      } catch (IOException var3) {
         throw ExceptionFactory.createCommunicationsException(this.getPropertySet(), this.serverSession, this.getPacketSentTimeHolder().getLastPacketSentTime(), this.getPacketReceivedTimeHolder().getLastPacketReceivedTime(), var3, this.getExceptionInterceptor());
      }

      this.maintainTimeStats.addListener(this);
      pset.getBooleanReadableProperty("traceProtocol").addListener(this);
      pset.getBooleanReadableProperty("enablePacketDebug").addListener(this);
   }

   public void handlePropertyChange(RuntimeProperty<?> prop) {
      String var2 = prop.getPropertyDefinition().getName();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -1418866947:
         if (var2.equals("traceProtocol")) {
            var3 = 1;
         }
         break;
      case 954642567:
         if (var2.equals("maintainTimeStats")) {
            var3 = 0;
         }
         break;
      case 1823404328:
         if (var2.equals("enablePacketDebug")) {
            var3 = 2;
         }
      }

      switch(var3) {
      case 0:
      case 1:
      case 2:
         this.applyPacketDecorators(this.packetSender.undecorateAll(), this.packetReader.undecorateAll());
      default:
      }
   }

   public void applyPacketDecorators(PacketSender sender, PacketReader reader) {
      TimeTrackingPacketSender ttSender = null;
      TimeTrackingPacketReader ttReader = null;
      LinkedList<StringBuilder> debugRingBuffer = null;
      if ((Boolean)this.maintainTimeStats.getValue()) {
         ttSender = new TimeTrackingPacketSender((PacketSender)sender);
         sender = ttSender;
         ttReader = new TimeTrackingPacketReader((PacketReader)reader);
         reader = ttReader;
      }

      if ((Boolean)this.propertySet.getBooleanReadableProperty("traceProtocol").getValue()) {
         sender = new TracingPacketSender((PacketSender)sender, this.log, this.socketConnection.getHost(), this.getServerSession().getCapabilities().getThreadId());
         reader = new TracingPacketReader((PacketReader)reader, this.log);
      }

      if ((Boolean)this.getPropertySet().getBooleanReadableProperty("enablePacketDebug").getValue()) {
         debugRingBuffer = new LinkedList();
         sender = new DebugBufferingPacketSender((PacketSender)sender, debugRingBuffer, this.propertySet.getIntegerReadableProperty("packetDebugBufferSize"));
         reader = new DebugBufferingPacketReader((PacketReader)reader, debugRingBuffer, this.propertySet.getIntegerReadableProperty("packetDebugBufferSize"));
      }

      PacketReader reader = new MultiPacketReader((PacketReader)reader);
      synchronized(this.packetReader) {
         this.packetReader = reader;
         this.packetDebugRingBuffer = debugRingBuffer;
         this.setPacketSentTimeHolder((PacketSentTimeHolder)(ttSender != null ? ttSender : new PacketSentTimeHolder() {
            public long getLastPacketSentTime() {
               return 0L;
            }
         }));
      }

      synchronized(this.packetSender) {
         this.packetSender = (PacketSender)sender;
         this.setPacketReceivedTimeHolder((PacketReceivedTimeHolder)(ttReader != null ? ttReader : new PacketReceivedTimeHolder() {
            public long getLastPacketReceivedTime() {
               return 0L;
            }
         }));
      }
   }

   public MysqlaCapabilities readServerCapabilities() {
      PacketPayload buf = this.readPacket((PacketPayload)null);
      MysqlaCapabilities serverCapabilities = new MysqlaCapabilities();
      serverCapabilities.setInitialHandshakePacket(buf);
      if (serverCapabilities.getProtocolVersion() == -1) {
         this.rejectProtocol(buf);
      }

      return serverCapabilities;
   }

   public MysqlaServerSession getServerSession() {
      return this.serverSession;
   }

   public void changeDatabase(String database) {
      if (database != null && database.length() != 0) {
         try {
            this.sendCommand(2, database, (PacketPayload)null, false, (String)null, 0);
         } catch (CJException var3) {
            if (!(Boolean)this.getPropertySet().getBooleanReadableProperty("createDatabaseIfNotExist").getValue()) {
               throw ExceptionFactory.createCommunicationsException(this.getPropertySet(), this.serverSession, this.getPacketSentTimeHolder().getLastPacketSentTime(), this.getPacketReceivedTimeHolder().getLastPacketReceivedTime(), var3, this.getExceptionInterceptor());
            }

            this.sendCommand(3, "CREATE DATABASE IF NOT EXISTS " + database, (PacketPayload)null, false, (String)null, 0);
            this.sendCommand(2, database, (PacketPayload)null, false, (String)null, 0);
         }

      }
   }

   public final PacketPayload readPacket(PacketPayload reuse) {
      try {
         PacketHeader header = this.packetReader.readHeader();
         PacketPayload buf = this.packetReader.readPayload(Optional.ofNullable(reuse), header.getPacketLength());
         this.packetSequence = header.getPacketSequence();
         return buf;
      } catch (IOException var5) {
         throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder().getLastPacketSentTime(), this.getPacketReceivedTimeHolder().getLastPacketReceivedTime(), var5, this.getExceptionInterceptor());
      } catch (OutOfMemoryError var6) {
         OutOfMemoryError oom = var6;

         try {
            ((JdbcConnection)this.connection).realClose(false, false, true, oom);
         } catch (Exception var4) {
         }

         throw var6;
      }
   }

   public final void send(PacketPayload packet, int packetLen) {
      try {
         if ((Integer)this.maxAllowedPacket.getValue() > 0 && packetLen > (Integer)this.maxAllowedPacket.getValue()) {
            throw new CJPacketTooBigException((long)packetLen, (long)(Integer)this.maxAllowedPacket.getValue());
         } else {
            ++this.packetSequence;
            this.packetSender.send(packet.getByteBuffer(), packetLen, this.packetSequence);
            if (packet == this.sharedSendPacket) {
               this.reclaimLargeSharedSendPacket();
            }

         }
      } catch (IOException var4) {
         throw ExceptionFactory.createCommunicationsException(this.getPropertySet(), this.serverSession, this.getPacketSentTimeHolder().getLastPacketSentTime(), this.getPacketReceivedTimeHolder().getLastPacketReceivedTime(), var4, this.getExceptionInterceptor());
      }
   }

   public final PacketPayload sendCommand(int command, String extraData, PacketPayload queryPacket, boolean skipCheck, String extraDataCharEncoding, int timeoutMillis) {
      ++this.commandCount;
      this.packetReader.resetPacketSequence();
      int oldTimeout = 0;
      if (timeoutMillis != 0) {
         try {
            oldTimeout = this.socketConnection.getMysqlSocket().getSoTimeout();
            this.socketConnection.getMysqlSocket().setSoTimeout(timeoutMillis);
         } catch (SocketException var22) {
            throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder().getLastPacketSentTime(), this.getPacketReceivedTimeHolder().getLastPacketReceivedTime(), var22, this.getExceptionInterceptor());
         }
      }

      PacketPayload var29;
      try {
         this.checkForOutstandingStreamingData();
         this.serverSession.setStatusFlags(0, true);
         this.hadWarnings = false;
         this.setWarningCount(0);
         this.queryNoIndexUsed = false;
         this.queryBadIndexUsed = false;
         this.serverQueryWasSlow = false;
         int packLength;
         if (this.useCompression) {
            packLength = this.socketConnection.getMysqlInput().available();
            if (packLength > 0) {
               this.socketConnection.getMysqlInput().skip((long)packLength);
            }
         }

         try {
            this.clearInputStream();
            if (queryPacket == null) {
               packLength = 4 + (extraData != null ? extraData.length() : 0) + 2;
               if (this.sendPacket == null) {
                  this.sendPacket = new Buffer(packLength);
               }

               this.packetSequence = -1;
               this.sendPacket.setPosition(0);
               this.sendPacket.writeInteger(NativeProtocol.IntegerDataType.INT1, (long)command);
               if (command != 2 && command != 5 && command != 6 && command != 3 && command != 22) {
                  if (command == 12) {
                     long id = Long.parseLong(extraData);
                     this.sendPacket.writeInteger(NativeProtocol.IntegerDataType.INT4, id);
                  }
               } else {
                  this.sendPacket.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, StringUtils.getBytes(extraData, extraDataCharEncoding));
               }

               this.send(this.sendPacket, this.sendPacket.getPosition());
            } else {
               this.packetSequence = -1;
               this.send(queryPacket, queryPacket.getPosition());
            }
         } catch (CJException var23) {
            throw var23;
         } catch (Exception var24) {
            throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder().getLastPacketSentTime(), this.getPacketReceivedTimeHolder().getLastPacketReceivedTime(), var24, this.getExceptionInterceptor());
         }

         PacketPayload returnPacket = null;
         if (!skipCheck) {
            if (command == 23 || command == 26) {
               this.packetReader.resetPacketSequence();
            }

            returnPacket = this.checkErrorPacket(command);
         }

         var29 = returnPacket;
      } catch (IOException var25) {
         this.serverSession.preserveOldTransactionState();
         throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder().getLastPacketSentTime(), this.getPacketReceivedTimeHolder().getLastPacketReceivedTime(), var25, this.getExceptionInterceptor());
      } catch (CJException var26) {
         this.serverSession.preserveOldTransactionState();
         throw var26;
      } finally {
         if (timeoutMillis != 0) {
            try {
               this.socketConnection.getMysqlSocket().setSoTimeout(oldTimeout);
            } catch (SocketException var21) {
               throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder().getLastPacketSentTime(), this.getPacketReceivedTimeHolder().getLastPacketReceivedTime(), var21, this.getExceptionInterceptor());
            }
         }

      }

      return var29;
   }

   public void checkTransactionState() {
      int transState = this.serverSession.getTransactionState();

      try {
         if (transState == 3) {
            ((JdbcConnection)this.connection).transactionCompleted();
         } else if (transState == 2) {
            ((JdbcConnection)this.connection).transactionBegun();
         }

      } catch (SQLException var3) {
         throw ExceptionFactory.createException((String)var3.getMessage(), (Throwable)var3, (ExceptionInterceptor)this.getExceptionInterceptor());
      }
   }

   public PacketPayload checkErrorPacket() {
      return this.checkErrorPacket(-1);
   }

   private PacketPayload checkErrorPacket(int command) {
      PacketPayload resultPacket = null;
      this.serverSession.setStatusFlags(0);

      try {
         resultPacket = this.readPacket(this.reusablePacket);
      } catch (CJException var4) {
         throw var4;
      } catch (Exception var5) {
         throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder().getLastPacketSentTime(), this.getPacketReceivedTimeHolder().getLastPacketReceivedTime(), var5, this.getExceptionInterceptor());
      }

      this.checkErrorPacket(resultPacket);
      return resultPacket;
   }

   public void checkErrorPacket(PacketPayload resultPacket) {
      resultPacket.setPosition(0);
      byte statusCode = (byte)((int)resultPacket.readInteger(NativeProtocol.IntegerDataType.INT1));
      if (statusCode == -1) {
         int errno = true;
         int errno = (int)resultPacket.readInteger(NativeProtocol.IntegerDataType.INT2);
         String xOpen = null;
         String serverErrorMessage = resultPacket.readString(NativeProtocol.StringSelfDataType.STRING_TERM, this.serverSession.getErrorMessageEncoding());
         if (serverErrorMessage.charAt(0) == '#') {
            if (serverErrorMessage.length() > 6) {
               xOpen = serverErrorMessage.substring(1, 6);
               serverErrorMessage = serverErrorMessage.substring(6);
               if (xOpen.equals("HY000")) {
                  xOpen = SQLError.mysqlToSqlState(errno);
               }
            } else {
               xOpen = SQLError.mysqlToSqlState(errno);
            }
         } else {
            xOpen = SQLError.mysqlToSqlState(errno);
         }

         this.clearInputStream();
         StringBuilder errorBuf = new StringBuilder();
         String xOpenErrorMessage = SQLError.get(xOpen);
         boolean useOnlyServerErrorMessages = (Boolean)this.propertySet.getBooleanReadableProperty("useOnlyServerErrorMessages").getValue();
         if (!useOnlyServerErrorMessages && xOpenErrorMessage != null) {
            errorBuf.append(xOpenErrorMessage);
            errorBuf.append(Messages.getString("Protocol.0"));
         }

         errorBuf.append(serverErrorMessage);
         if (!useOnlyServerErrorMessages && xOpenErrorMessage != null) {
            errorBuf.append("\"");
         }

         ResultSetUtil.appendDeadlockStatusInformation(this.connection, xOpen, errorBuf);
         if (xOpen != null) {
            if (xOpen.startsWith("22")) {
               throw new DataTruncationException(errorBuf.toString(), 0, true, false, 0, 0, errno);
            }

            if (errno == 1820) {
               throw (PasswordExpiredException)ExceptionFactory.createException(PasswordExpiredException.class, errorBuf.toString(), this.getExceptionInterceptor());
            }

            if (errno == 1862) {
               throw (ClosedOnExpiredPasswordException)ExceptionFactory.createException(ClosedOnExpiredPasswordException.class, errorBuf.toString(), this.getExceptionInterceptor());
            }
         }

         throw ExceptionFactory.createException(errorBuf.toString(), xOpen, errno, false, (Throwable)null, this.getExceptionInterceptor());
      }
   }

   private void reclaimLargeSharedSendPacket() {
      if (this.sharedSendPacket != null && this.sharedSendPacket.getCapacity() > 1048576) {
         this.sharedSendPacket = new Buffer(1024);
      }

   }

   public void clearInputStream() {
      try {
         int len;
         while((len = this.socketConnection.getMysqlInput().available()) > 0 && this.socketConnection.getMysqlInput().skip((long)len) > 0L) {
         }

      } catch (IOException var2) {
         throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder().getLastPacketSentTime(), this.getPacketReceivedTimeHolder().getLastPacketReceivedTime(), var2, this.getExceptionInterceptor());
      }
   }

   public void reclaimLargeReusablePacket() {
      if (this.reusablePacket != null && this.reusablePacket.getCapacity() > 1048576) {
         this.reusablePacket = new Buffer(1024);
      }

   }

   public final <T extends Resultset> T sqlQueryDirect(StatementImpl callingStatement, String query, String characterEncoding, PacketPayload queryPacket, int maxRows, boolean streamResults, String catalog, ColumnDefinition cachedMetadata, Protocol.GetProfilerEventHandlerInstanceFunction getProfilerEventHandlerInstanceFunction, ProtocolEntityFactory<T> resultSetFactory) throws IOException {
      ++this.statementExecutionDepth;

      try {
         if (this.statementInterceptors != null) {
            T interceptedResults = this.invokeStatementInterceptorsPre(query, callingStatement, false);
            if (interceptedResults != null) {
               Resultset var12 = interceptedResults;
               return var12;
            }
         }

         long queryStartTime = 0L;
         long queryEndTime = 0L;
         String statementComment = ((JdbcConnection)this.connection).getStatementComment();
         if ((Boolean)this.propertySet.getBooleanReadableProperty("includeThreadNamesAsStatementComment").getValue()) {
            statementComment = (statementComment != null ? statementComment + ", " : "") + "java thread: " + Thread.currentThread().getName();
         }

         if (query != null) {
            int packLength = 1 + query.length() * 3 + 2;
            byte[] commentAsBytes = null;
            if (statementComment != null) {
               commentAsBytes = StringUtils.getBytes(statementComment, characterEncoding);
               packLength += commentAsBytes.length;
               packLength += 6;
            }

            if (this.sendPacket == null) {
               this.sendPacket = new Buffer(packLength);
            }

            this.sendPacket.setPosition(0);
            this.sendPacket.writeInteger(NativeProtocol.IntegerDataType.INT1, 3L);
            if (commentAsBytes != null) {
               this.sendPacket.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, Constants.SLASH_STAR_SPACE_AS_BYTES);
               this.sendPacket.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, commentAsBytes);
               this.sendPacket.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, Constants.SPACE_STAR_SLASH_SPACE_AS_BYTES);
            }

            if (!this.platformDbCharsetMatches && StringUtils.startsWithIgnoreCaseAndWs(query, "LOAD DATA")) {
               this.sendPacket.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, StringUtils.getBytes(query));
            } else {
               this.sendPacket.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, StringUtils.getBytes(query, characterEncoding));
            }

            queryPacket = this.sendPacket;
         }

         byte[] queryBuf = null;
         int oldPacketPosition = 0;
         if (this.needToGrabQueryFromPacket) {
            queryBuf = queryPacket.getByteBuffer();
            oldPacketPosition = queryPacket.getPosition();
            queryStartTime = this.getCurrentTimeNanosOrMillis();
         }

         if (this.autoGenerateTestcaseScript) {
            String testcaseQuery = null;
            if (query != null) {
               if (statementComment != null) {
                  testcaseQuery = "/* " + statementComment + " */ " + query;
               } else {
                  testcaseQuery = query;
               }
            } else {
               testcaseQuery = StringUtils.toString(queryBuf, 1, oldPacketPosition - 1);
            }

            StringBuilder debugBuf = new StringBuilder(testcaseQuery.length() + 32);
            ((JdbcConnection)this.connection).generateConnectionCommentBlock(debugBuf);
            debugBuf.append(testcaseQuery);
            debugBuf.append(';');
            TestUtils.dumpTestcaseQuery(debugBuf.toString());
         }

         PacketPayload resultPacket = this.sendCommand(3, (String)null, queryPacket, false, (String)null, 0);
         long fetchBeginTime = 0L;
         long fetchEndTime = 0L;
         String profileQueryToLog = null;
         boolean queryWasSlow = false;
         if (this.profileSQL || this.logSlowQueries) {
            queryEndTime = this.getCurrentTimeNanosOrMillis();
            boolean shouldExtractQuery = false;
            if (this.profileSQL) {
               shouldExtractQuery = true;
            } else if (this.logSlowQueries) {
               long queryTime = queryEndTime - queryStartTime;
               boolean logSlow = false;
               if (!this.useAutoSlowLog) {
                  logSlow = queryTime > (long)(Integer)this.propertySet.getIntegerReadableProperty("slowQueryThresholdMillis").getValue();
               } else {
                  logSlow = ((JdbcConnection)this.connection).isAbonormallyLongQuery(queryTime);
                  ((JdbcConnection)this.connection).reportQueryTime(queryTime);
               }

               if (logSlow) {
                  shouldExtractQuery = true;
                  queryWasSlow = true;
               }
            }

            if (shouldExtractQuery) {
               boolean truncated = false;
               int extractPosition = oldPacketPosition;
               if (oldPacketPosition > (Integer)this.maxQuerySizeToLog.getValue()) {
                  extractPosition = (Integer)this.maxQuerySizeToLog.getValue() + 1;
                  truncated = true;
               }

               profileQueryToLog = StringUtils.toString(queryBuf, 1, extractPosition - 1);
               if (truncated) {
                  profileQueryToLog = profileQueryToLog + Messages.getString("Protocol.2");
               }
            }

            fetchBeginTime = queryEndTime;
         }

         T rs = this.readAllResults(maxRows, streamResults, resultPacket, false, cachedMetadata, resultSetFactory);
         if (queryWasSlow && !this.serverQueryWasSlow) {
            StringBuilder mesgBuf = new StringBuilder(48 + profileQueryToLog.length());
            mesgBuf.append(Messages.getString("Protocol.SlowQuery", new Object[]{String.valueOf(this.useAutoSlowLog ? " 95% of all queries " : this.slowQueryThreshold), this.queryTimingUnits, queryEndTime - queryStartTime}));
            mesgBuf.append(profileQueryToLog);
            ProfilerEventHandler eventSink = getProfilerEventHandlerInstanceFunction.apply();
            eventSink.consumeEvent(new ProfilerEventImpl((byte)6, "", catalog, this.connection.getId(), callingStatement != null ? callingStatement.getId() : 999, rs.getResultId(), System.currentTimeMillis(), (long)((int)(queryEndTime - queryStartTime)), this.queryTimingUnits, (String)null, LogUtils.findCallingClassAndMethod(new Throwable()), mesgBuf.toString()));
            if ((Boolean)this.propertySet.getBooleanReadableProperty("explainSlowQueries").getValue()) {
               if (oldPacketPosition < 1048576) {
                  queryPacket.setPosition(1);
                  this.explainSlowQuery(queryPacket.readBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, oldPacketPosition - 1), profileQueryToLog);
               } else {
                  this.log.logWarn(Messages.getString("Protocol.3", new Object[]{1048576}));
               }
            }
         }

         ProfilerEventHandler eventSink;
         if (this.logSlowQueries) {
            eventSink = getProfilerEventHandlerInstanceFunction.apply();
            if (this.queryBadIndexUsed && this.profileSQL) {
               eventSink.consumeEvent(new ProfilerEventImpl((byte)6, "", catalog, this.connection.getId(), callingStatement != null ? callingStatement.getId() : 999, rs.getResultId(), System.currentTimeMillis(), queryEndTime - queryStartTime, this.queryTimingUnits, (String)null, LogUtils.findCallingClassAndMethod(new Throwable()), Messages.getString("Protocol.4") + profileQueryToLog));
            }

            if (this.queryNoIndexUsed && this.profileSQL) {
               eventSink.consumeEvent(new ProfilerEventImpl((byte)6, "", catalog, this.connection.getId(), callingStatement != null ? callingStatement.getId() : 999, rs.getResultId(), System.currentTimeMillis(), queryEndTime - queryStartTime, this.queryTimingUnits, (String)null, LogUtils.findCallingClassAndMethod(new Throwable()), Messages.getString("Protocol.5") + profileQueryToLog));
            }

            if (this.serverQueryWasSlow && this.profileSQL) {
               eventSink.consumeEvent(new ProfilerEventImpl((byte)6, "", catalog, this.connection.getId(), callingStatement != null ? callingStatement.getId() : 999, rs.getResultId(), System.currentTimeMillis(), queryEndTime - queryStartTime, this.queryTimingUnits, (String)null, LogUtils.findCallingClassAndMethod(new Throwable()), Messages.getString("Protocol.ServerSlowQuery") + profileQueryToLog));
            }
         }

         if (this.profileSQL) {
            fetchEndTime = this.getCurrentTimeNanosOrMillis();
            eventSink = getProfilerEventHandlerInstanceFunction.apply();
            eventSink.consumeEvent(new ProfilerEventImpl((byte)3, "", catalog, this.connection.getId(), callingStatement != null ? callingStatement.getId() : 999, rs.getResultId(), System.currentTimeMillis(), queryEndTime - queryStartTime, this.queryTimingUnits, (String)null, LogUtils.findCallingClassAndMethod(new Throwable()), profileQueryToLog));
            eventSink.consumeEvent(new ProfilerEventImpl((byte)5, "", catalog, this.connection.getId(), callingStatement != null ? callingStatement.getId() : 999, rs.getResultId(), System.currentTimeMillis(), fetchEndTime - fetchBeginTime, this.queryTimingUnits, (String)null, LogUtils.findCallingClassAndMethod(new Throwable()), (String)null));
         }

         if (this.hadWarnings) {
            this.scanForAndThrowDataTruncation();
         }

         Resultset interceptedResults;
         if (this.statementInterceptors != null) {
            interceptedResults = this.invokeStatementInterceptorsPost(query, callingStatement, rs, false, (Exception)null);
            if (interceptedResults != null) {
               rs = interceptedResults;
            }
         }

         interceptedResults = rs;
         return interceptedResults;
      } catch (CJException | SQLException var37) {
         if (this.statementInterceptors != null) {
            this.invokeStatementInterceptorsPost(query, callingStatement, (Resultset)null, false, var37);
         }

         if (callingStatement != null) {
            synchronized(callingStatement.cancelTimeoutMutex) {
               if (callingStatement.wasCancelled) {
                  CJException cause = null;
                  if (callingStatement.wasCancelledByTimeout) {
                     cause = new CJTimeoutException();
                  } else {
                     cause = new OperationCancelledException();
                  }

                  try {
                     callingStatement.resetCancelledState();
                  } catch (SQLException var35) {
                     throw ExceptionFactory.createException((String)var35.getMessage(), (Throwable)var35);
                  }

                  throw cause;
               }
            }
         }

         if (var37 instanceof CJException) {
            throw (CJException)var37;
         } else {
            throw ExceptionFactory.createException((String)var37.getMessage(), (Throwable)var37);
         }
      } finally {
         --this.statementExecutionDepth;
      }
   }

   public <T extends Resultset> T invokeStatementInterceptorsPre(String sql, Statement interceptedStatement, boolean forceExecute) {
      T previousResultSet = null;
      int i = 0;

      for(int s = this.statementInterceptors.size(); i < s; ++i) {
         StatementInterceptor interceptor = (StatementInterceptor)this.statementInterceptors.get(i);
         boolean executeTopLevelOnly = interceptor.executeTopLevelOnly();
         boolean shouldExecute = executeTopLevelOnly && (this.statementExecutionDepth == 1 || forceExecute) || !executeTopLevelOnly;
         if (shouldExecute) {
            String sqlToInterceptor = sql;

            try {
               T interceptedResultSet = interceptor.preProcess(sqlToInterceptor, interceptedStatement);
               if (interceptedResultSet != null) {
                  previousResultSet = interceptedResultSet;
               }
            } catch (SQLException var12) {
               throw ExceptionFactory.createException((String)var12.getMessage(), (Throwable)var12);
            }
         }
      }

      return previousResultSet;
   }

   public <T extends Resultset> T invokeStatementInterceptorsPost(String sql, Statement interceptedStatement, T originalResultSet, boolean forceExecute, Exception statementException) {
      int i = 0;

      for(int s = this.statementInterceptors.size(); i < s; ++i) {
         StatementInterceptor interceptor = (StatementInterceptor)this.statementInterceptors.get(i);
         boolean executeTopLevelOnly = interceptor.executeTopLevelOnly();
         boolean shouldExecute = executeTopLevelOnly && (this.statementExecutionDepth == 1 || forceExecute) || !executeTopLevelOnly;
         if (shouldExecute) {
            String sqlToInterceptor = sql;

            try {
               T interceptedResultSet = interceptor.postProcess(sqlToInterceptor, interceptedStatement, originalResultSet, this.getWarningCount(), this.queryNoIndexUsed, this.queryBadIndexUsed, statementException);
               if (interceptedResultSet != null) {
                  originalResultSet = interceptedResultSet;
               }
            } catch (SQLException var13) {
               throw ExceptionFactory.createException((String)var13.getMessage(), (Throwable)var13);
            }
         }
      }

      return originalResultSet;
   }

   public long getCurrentTimeNanosOrMillis() {
      return this.useNanosForElapsedTime ? TimeUtil.getCurrentTimeNanosOrMillis() : System.currentTimeMillis();
   }

   public boolean hadWarnings() {
      return this.hadWarnings;
   }

   public void setHadWarnings(boolean hadWarnings) {
      this.hadWarnings = hadWarnings;
   }

   public void explainSlowQuery(byte[] querySQL, String truncatedQuery) {
      if (StringUtils.startsWithIgnoreCaseAndWs(truncatedQuery, "SELECT") || this.versionMeetsMinimum(5, 6, 3) && StringUtils.startsWithIgnoreCaseAndWs(truncatedQuery, EXPLAINABLE_STATEMENT_EXTENSION) != -1) {
         PreparedStatement stmt = null;
         ResultSet rs = null;

         try {
            stmt = (PreparedStatement)((JdbcConnection)this.connection).clientPrepareStatement("EXPLAIN ?");
            stmt.setBytesNoEscapeNoQuotes(1, querySQL);
            rs = stmt.executeQuery();
            StringBuilder explainResults = new StringBuilder(Messages.getString("Protocol.6"));
            explainResults.append(truncatedQuery);
            explainResults.append(Messages.getString("Protocol.7"));
            ResultSetUtil.appendResultSetSlashGStyle(explainResults, rs);
            this.log.logWarn(explainResults.toString());
         } catch (CJException | SQLException var14) {
         } catch (Exception var15) {
            throw ExceptionFactory.createException((String)var15.getMessage(), (Throwable)var15, (ExceptionInterceptor)this.getExceptionInterceptor());
         } finally {
            try {
               if (rs != null) {
                  rs.close();
               }

               if (stmt != null) {
                  stmt.close();
               }
            } catch (SQLException var16) {
               throw ExceptionFactory.createException((String)var16.getMessage(), (Throwable)var16);
            }

         }
      }

   }

   public final void skipPacket() {
      try {
         int packetLength = this.packetReader.readHeader().getPacketLength();
         this.socketConnection.getMysqlInput().skipFully((long)packetLength);
      } catch (IOException var4) {
         throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder().getLastPacketSentTime(), this.getPacketReceivedTimeHolder().getLastPacketReceivedTime(), var4, this.getExceptionInterceptor());
      } catch (OutOfMemoryError var5) {
         OutOfMemoryError oom = var5;

         try {
            ((JdbcConnection)this.connection).realClose(false, false, true, oom);
         } catch (Exception var3) {
         }

         throw var5;
      }
   }

   public final void quit() {
      try {
         try {
            if (!this.socketConnection.getMysqlSocket().isClosed()) {
               try {
                  this.socketConnection.getMysqlSocket().shutdownInput();
               } catch (UnsupportedOperationException var6) {
               }
            }
         } catch (IOException var7) {
            this.log.logWarn("Caught while disconnecting...", var7);
         }

         PacketPayload packet = new Buffer(6);
         this.packetSequence = -1;
         packet.writeInteger(NativeProtocol.IntegerDataType.INT1, 1L);
         this.send(packet, packet.getPosition());
      } finally {
         this.socketConnection.forceClose();
         this.localInfileInputStream = null;
      }

   }

   public PacketPayload getSharedSendPacket() {
      if (this.sharedSendPacket == null) {
         this.sharedSendPacket = new Buffer(1024);
      }

      this.sharedSendPacket.setPosition(0);
      return this.sharedSendPacket;
   }

   private void calculateSlowQueryThreshold() {
      this.slowQueryThreshold = (long)(Integer)this.propertySet.getIntegerReadableProperty("slowQueryThresholdMillis").getValue();
      if ((Boolean)this.propertySet.getBooleanReadableProperty("useNanosForElapsedTime").getValue()) {
         long nanosThreshold = (Long)this.propertySet.getLongReadableProperty("slowQueryThresholdNanos").getValue();
         if (nanosThreshold != 0L) {
            this.slowQueryThreshold = nanosThreshold;
         } else {
            this.slowQueryThreshold *= 1000000L;
         }
      }

   }

   public void changeUser(String user, String password, String database) {
      this.packetSequence = -1;
      this.authProvider.changeUser(this.serverSession, user, password, database);
   }

   public void checkForCharsetMismatch() {
      String characterEncoding = (String)this.propertySet.getStringReadableProperty("characterEncoding").getValue();
      if (characterEncoding != null) {
         String encodingToCheck = jvmPlatformCharset;
         if (encodingToCheck == null) {
            encodingToCheck = Constants.PLATFORM_ENCODING;
         }

         if (encodingToCheck == null) {
            this.platformDbCharsetMatches = false;
         } else {
            this.platformDbCharsetMatches = encodingToCheck.equals(characterEncoding);
         }
      }

   }

   public void setServerSlowQueryFlags() {
      ServerSession state = this.serverSession;
      this.queryBadIndexUsed = state.noGoodIndexUsed();
      this.queryNoIndexUsed = state.noIndexUsed();
      this.serverQueryWasSlow = state.queryWasSlow();
   }

   protected boolean useNanosForElapsedTime() {
      return this.useNanosForElapsedTime;
   }

   public long getSlowQueryThreshold() {
      return this.slowQueryThreshold;
   }

   public String getQueryTimingUnits() {
      return this.queryTimingUnits;
   }

   public int getCommandCount() {
      return this.commandCount;
   }

   public void setStatementInterceptors(List<StatementInterceptor> statementInterceptors) {
      this.statementInterceptors = statementInterceptors.isEmpty() ? null : statementInterceptors;
   }

   public List<StatementInterceptor> getStatementInterceptors() {
      return this.statementInterceptors;
   }

   public void setSocketTimeout(int milliseconds) {
      try {
         this.socketConnection.getMysqlSocket().setSoTimeout(milliseconds);
      } catch (SocketException var3) {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("Protocol.8"), var3, this.getExceptionInterceptor());
      }
   }

   public void releaseResources() {
      if (this.compressedPacketSender != null) {
         this.compressedPacketSender.stop();
      }

   }

   public void connect(String user, String password, String database) {
      this.beforeHandshake();
      this.authProvider.connect(this.serverSession, user, password, database);
   }

   public JdbcConnection getConnection() {
      return (JdbcConnection)this.connection;
   }

   public void setConnection(JdbcConnection connection) {
      this.connection = connection;
   }

   protected boolean isDataAvailable() {
      try {
         return this.socketConnection.getMysqlInput().available() > 0;
      } catch (IOException var2) {
         throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder().getLastPacketSentTime(), this.getPacketReceivedTimeHolder().getLastPacketReceivedTime(), var2, this.getExceptionInterceptor());
      }
   }

   public PacketPayload getReusablePacket() {
      return this.reusablePacket;
   }

   public int getWarningCount() {
      return this.warningCount;
   }

   public void setWarningCount(int warningCount) {
      this.warningCount = warningCount;
   }

   public void dumpPacketRingBuffer() {
      LinkedList<StringBuilder> localPacketDebugRingBuffer = this.packetDebugRingBuffer;
      if (localPacketDebugRingBuffer != null) {
         StringBuilder dumpBuffer = new StringBuilder();
         dumpBuffer.append("Last " + localPacketDebugRingBuffer.size() + " packets received from server, from oldest->newest:\n");
         dumpBuffer.append("\n");
         Iterator ringBufIter = localPacketDebugRingBuffer.iterator();

         while(ringBufIter.hasNext()) {
            dumpBuffer.append((CharSequence)ringBufIter.next());
            dumpBuffer.append("\n");
         }

         this.log.logTrace(dumpBuffer.toString());
      }

   }

   public boolean doesPlatformDbCharsetMatches() {
      return this.platformDbCharsetMatches;
   }

   public String getPasswordCharacterEncoding() {
      String encoding;
      if ((encoding = this.propertySet.getStringReadableProperty("passwordCharacterEncoding").getStringValue()) != null) {
         return encoding;
      } else {
         return (encoding = (String)this.propertySet.getStringReadableProperty("characterEncoding").getValue()) != null ? encoding : "UTF-8";
      }
   }

   public boolean versionMeetsMinimum(int major, int minor, int subminor) {
      return this.serverSession.getServerVersion().meetsMinimum(new ServerVersion(major, minor, subminor));
   }

   public static MysqlType findMysqlType(PropertySet propertySet, int mysqlTypeId, short colFlag, long length, LazyString tableName, LazyString originalTableName, int collationIndex, String encoding) {
      boolean isUnsigned = (colFlag & 32) > 0;
      boolean isFromFunction = originalTableName.length() == 0;
      boolean isBinary = (colFlag & 128) > 0;
      boolean isImplicitTemporaryTable = tableName.length() > 0 && tableName.toString().startsWith("#sql_");
      boolean isOpaqueBinary = !isBinary || collationIndex != 63 || mysqlTypeId != 254 && mysqlTypeId != 253 && mysqlTypeId != 15 ? "binary".equalsIgnoreCase(encoding) : !isImplicitTemporaryTable;
      switch(mysqlTypeId) {
      case 0:
      case 246:
         return isUnsigned ? MysqlType.DECIMAL_UNSIGNED : MysqlType.DECIMAL;
      case 1:
         if (length == 1L) {
            if ((Boolean)propertySet.getBooleanReadableProperty("transformedBitIsBoolean").getValue()) {
               return MysqlType.BOOLEAN;
            }

            if ((Boolean)propertySet.getBooleanReadableProperty("tinyInt1isBit").getValue()) {
               return MysqlType.BIT;
            }
         }

         return isUnsigned ? MysqlType.TINYINT_UNSIGNED : MysqlType.TINYINT;
      case 2:
         return isUnsigned ? MysqlType.SMALLINT_UNSIGNED : MysqlType.SMALLINT;
      case 3:
         return isUnsigned ? MysqlType.INT_UNSIGNED : MysqlType.INT;
      case 4:
         return isUnsigned ? MysqlType.FLOAT_UNSIGNED : MysqlType.FLOAT;
      case 5:
         return isUnsigned ? MysqlType.DOUBLE_UNSIGNED : MysqlType.DOUBLE;
      case 6:
         return MysqlType.NULL;
      case 7:
         return MysqlType.TIMESTAMP;
      case 8:
         return isUnsigned ? MysqlType.BIGINT_UNSIGNED : MysqlType.BIGINT;
      case 9:
         return isUnsigned ? MysqlType.MEDIUMINT_UNSIGNED : MysqlType.MEDIUMINT;
      case 10:
         return MysqlType.DATE;
      case 11:
         return MysqlType.TIME;
      case 12:
         return MysqlType.DATETIME;
      case 13:
         return MysqlType.YEAR;
      case 15:
      case 253:
         return !isOpaqueBinary || isFromFunction && (Boolean)propertySet.getBooleanReadableProperty("functionsNeverReturnBlobs").getValue() ? MysqlType.VARCHAR : MysqlType.VARBINARY;
      case 16:
         return MysqlType.BIT;
      case 245:
         return MysqlType.JSON;
      case 247:
         return MysqlType.ENUM;
      case 248:
         return MysqlType.SET;
      case 249:
         if (isBinary && collationIndex == 63 && !(Boolean)propertySet.getBooleanReadableProperty("blobsAreStrings").getValue() && (!isFromFunction || !(Boolean)propertySet.getBooleanReadableProperty("functionsNeverReturnBlobs").getValue())) {
            return MysqlType.TINYBLOB;
         }

         return MysqlType.TINYTEXT;
      case 250:
         if (isBinary && collationIndex == 63 && !(Boolean)propertySet.getBooleanReadableProperty("blobsAreStrings").getValue() && (!isFromFunction || !(Boolean)propertySet.getBooleanReadableProperty("functionsNeverReturnBlobs").getValue())) {
            return MysqlType.MEDIUMBLOB;
         }

         return MysqlType.MEDIUMTEXT;
      case 251:
         if (!isBinary || collationIndex != 63 || (Boolean)propertySet.getBooleanReadableProperty("blobsAreStrings").getValue() || isFromFunction && (Boolean)propertySet.getBooleanReadableProperty("functionsNeverReturnBlobs").getValue()) {
            return MysqlType.LONGTEXT;
         }

         return MysqlType.LONGBLOB;
      case 252:
         short newMysqlTypeId;
         if (length <= MysqlType.TINYBLOB.getPrecision()) {
            newMysqlTypeId = 249;
         } else {
            if (length <= MysqlType.BLOB.getPrecision()) {
               if (isBinary && collationIndex == 63 && !(Boolean)propertySet.getBooleanReadableProperty("blobsAreStrings").getValue() && (!isFromFunction || !(Boolean)propertySet.getBooleanReadableProperty("functionsNeverReturnBlobs").getValue())) {
                  return MysqlType.BLOB;
               }

               int newMysqlTypeId = true;
               return MysqlType.TEXT;
            }

            if (length <= MysqlType.MEDIUMBLOB.getPrecision()) {
               newMysqlTypeId = 250;
            } else {
               newMysqlTypeId = 251;
            }
         }

         return findMysqlType(propertySet, newMysqlTypeId, colFlag, length, tableName, originalTableName, collationIndex, encoding);
      case 254:
         if (isOpaqueBinary && !(Boolean)propertySet.getBooleanReadableProperty("blobsAreStrings").getValue()) {
            return MysqlType.BINARY;
         }

         return MysqlType.CHAR;
      case 255:
         return MysqlType.GEOMETRY;
      default:
         return MysqlType.UNKNOWN;
      }
   }

   public <T extends ProtocolEntity> T read(Class<T> requiredClass, ProtocolEntityFactory<T> protocolEntityFactory) throws IOException {
      ProtocolEntityReader<T> sr = (ProtocolEntityReader)this.PROTOCOL_ENTITY_CLASS_TO_TEXT_READER.get(requiredClass);
      if (sr == null) {
         throw (FeatureNotAvailableException)ExceptionFactory.createException(FeatureNotAvailableException.class, "ProtocolEntityReader isn't available for class " + requiredClass);
      } else {
         return sr.read(protocolEntityFactory);
      }
   }

   public <T extends ProtocolEntity> T read(Class<Resultset> requiredClass, int maxRows, boolean streamResults, PacketPayload resultPacket, boolean isBinaryEncoded, ColumnDefinition metadata, ProtocolEntityFactory<T> protocolEntityFactory) throws IOException {
      ProtocolEntityReader<T> sr = isBinaryEncoded ? (ProtocolEntityReader)this.PROTOCOL_ENTITY_CLASS_TO_BINARY_READER.get(requiredClass) : (ProtocolEntityReader)this.PROTOCOL_ENTITY_CLASS_TO_TEXT_READER.get(requiredClass);
      if (sr == null) {
         throw (FeatureNotAvailableException)ExceptionFactory.createException(FeatureNotAvailableException.class, "ProtocolEntityReader isn't available for class " + requiredClass);
      } else {
         return sr.read(maxRows, streamResults, resultPacket, metadata, protocolEntityFactory);
      }
   }

   public <T extends ProtocolEntity> T readNextResultset(T currentProtocolEntity, int maxRows, boolean streamResults, boolean isBinaryEncoded, ProtocolEntityFactory<T> resultSetFactory) throws IOException {
      T result = null;
      if (Resultset.class.isAssignableFrom(currentProtocolEntity.getClass()) && this.serverSession.useMultiResults() && this.serverSession.hasMoreResults()) {
         ProtocolEntity currentResultSet = currentProtocolEntity;

         ProtocolEntity newResultSet;
         do {
            PacketPayload fieldPacket = this.checkErrorPacket();
            fieldPacket.setPosition(0);
            newResultSet = this.read(Resultset.class, maxRows, streamResults, fieldPacket, isBinaryEncoded, (ColumnDefinition)null, resultSetFactory);
            ((Resultset)currentResultSet).setNextResultset((Resultset)newResultSet);
            currentResultSet = newResultSet;
            if (result == null) {
               result = newResultSet;
            }
         } while(streamResults && this.serverSession.hasMoreResults() && !((Resultset)newResultSet).hasRows());
      }

      return result;
   }

   public <T extends Resultset> T readAllResults(int maxRows, boolean streamResults, PacketPayload resultPacket, boolean isBinaryEncoded, ColumnDefinition metadata, ProtocolEntityFactory<T> resultSetFactory) throws IOException {
      resultPacket.setPosition(0);
      T topLevelResultSet = (Resultset)this.read(Resultset.class, maxRows, streamResults, resultPacket, isBinaryEncoded, metadata, resultSetFactory);
      if (this.serverSession.hasMoreResults()) {
         T currentResultSet = topLevelResultSet;
         if (streamResults) {
            currentResultSet = (Resultset)this.readNextResultset(topLevelResultSet, maxRows, true, isBinaryEncoded, resultSetFactory);
         } else {
            while(this.serverSession.hasMoreResults()) {
               currentResultSet = (Resultset)this.readNextResultset(currentResultSet, maxRows, false, isBinaryEncoded, resultSetFactory);
            }

            this.clearInputStream();
         }
      }

      this.reclaimLargeReusablePacket();
      return topLevelResultSet;
   }

   public final <T> T readServerStatusForResultSets(PacketPayload rowPacket, boolean saveOldStatus) {
      T result = null;
      if (rowPacket.isEOFPacket()) {
         rowPacket.readInteger(NativeProtocol.IntegerDataType.INT1);
         this.warningCount = (int)rowPacket.readInteger(NativeProtocol.IntegerDataType.INT2);
         if (this.warningCount > 0) {
            this.hadWarnings = true;
         }

         this.serverSession.setStatusFlags((int)rowPacket.readInteger(NativeProtocol.IntegerDataType.INT2), saveOldStatus);
         this.checkTransactionState();
      } else {
         OkPacket ok = OkPacket.parse(rowPacket, ((JdbcConnection)this.connection).isReadInfoMsgEnabled(), this.serverSession.getErrorMessageEncoding());
         result = ok;
         this.serverSession.setStatusFlags(ok.getStatusFlags(), saveOldStatus);
         this.checkTransactionState();
         this.warningCount = ok.getWarningCount();
         if (this.warningCount > 0) {
            this.hadWarnings = true;
         }
      }

      this.setServerSlowQueryFlags();
      return result;
   }

   public InputStream getLocalInfileInputStream() {
      return this.localInfileInputStream;
   }

   public void setLocalInfileInputStream(InputStream stream) {
      this.localInfileInputStream = stream;
   }

   public final PacketPayload sendFileToServer(String fileName) {
      PacketPayload filePacket = this.loadFileBufRef == null ? null : (PacketPayload)this.loadFileBufRef.get();
      int bigPacketLength = Math.min((Integer)this.maxAllowedPacket.getValue() - 12, this.alignPacketSize((Integer)this.maxAllowedPacket.getValue() - 16, 4096) - 12);
      int oneMeg = 1048576;
      int smallerPacketSizeAligned = Math.min(oneMeg - 12, this.alignPacketSize(oneMeg - 16, 4096) - 12);
      int packetLength = Math.min(smallerPacketSizeAligned, bigPacketLength);
      if (filePacket == null) {
         try {
            filePacket = new Buffer(packetLength);
            this.loadFileBufRef = new SoftReference(filePacket);
         } catch (OutOfMemoryError var22) {
            throw ExceptionFactory.createException(Messages.getString("MysqlIO.111", new Object[]{packetLength}), "HY001", 0, false, var22, this.exceptionInterceptor);
         }
      }

      ((PacketPayload)filePacket).setPosition(0);
      byte[] fileBuf = new byte[packetLength];
      BufferedInputStream fileIn = null;

      try {
         if (!(Boolean)this.propertySet.getBooleanReadableProperty("allowLoadLocalInfile").getValue()) {
            throw ExceptionFactory.createException(Messages.getString("MysqlIO.LoadDataLocalNotAllowed"), this.exceptionInterceptor);
         }

         InputStream hookedStream = null;
         hookedStream = this.getLocalInfileInputStream();
         if (hookedStream != null) {
            fileIn = new BufferedInputStream(hookedStream);
         } else if (!(Boolean)this.propertySet.getBooleanReadableProperty("allowUrlInLocalInfile").getValue()) {
            fileIn = new BufferedInputStream(new FileInputStream(fileName));
         } else if (fileName.indexOf(58) != -1) {
            try {
               URL urlFromFileName = new URL(fileName);
               fileIn = new BufferedInputStream(urlFromFileName.openStream());
            } catch (MalformedURLException var21) {
               fileIn = new BufferedInputStream(new FileInputStream(fileName));
            }
         } else {
            fileIn = new BufferedInputStream(new FileInputStream(fileName));
         }

         boolean var26 = false;

         int bytesRead;
         while((bytesRead = fileIn.read(fileBuf)) != -1) {
            ((PacketPayload)filePacket).setPosition(0);
            ((PacketPayload)filePacket).writeBytes((NativeProtocol.StringLengthDataType)NativeProtocol.StringLengthDataType.STRING_FIXED, fileBuf, 0, bytesRead);
            this.send((PacketPayload)filePacket, ((PacketPayload)filePacket).getPosition());
         }
      } catch (IOException var23) {
         StringBuilder messageBuf = new StringBuilder(Messages.getString("MysqlIO.60"));
         boolean isParanoid = (Boolean)this.propertySet.getBooleanReadableProperty("paranoid").getValue();
         if (fileName != null && !isParanoid) {
            messageBuf.append("'");
            messageBuf.append(fileName);
            messageBuf.append("'");
         }

         messageBuf.append(Messages.getString("MysqlIO.63"));
         if (!isParanoid) {
            messageBuf.append(Messages.getString("MysqlIO.64"));
            messageBuf.append(Util.stackTraceToString(var23));
         }

         throw ExceptionFactory.createException((String)messageBuf.toString(), (Throwable)var23, (ExceptionInterceptor)this.exceptionInterceptor);
      } finally {
         if (fileIn != null) {
            try {
               fileIn.close();
            } catch (Exception var20) {
               throw ExceptionFactory.createException((String)Messages.getString("MysqlIO.65"), (Throwable)var20, (ExceptionInterceptor)this.exceptionInterceptor);
            }

            fileIn = null;
         } else {
            ((PacketPayload)filePacket).setPosition(0);
            this.send((PacketPayload)filePacket, ((PacketPayload)filePacket).getPosition());
            this.checkErrorPacket();
         }

      }

      ((PacketPayload)filePacket).setPosition(0);
      this.send((PacketPayload)filePacket, ((PacketPayload)filePacket).getPosition());
      return this.checkErrorPacket();
   }

   private int alignPacketSize(int a, int l) {
      return a + l - 1 & ~(l - 1);
   }

   public ResultsetRows getStreamingData() {
      return this.streamingData;
   }

   public void setStreamingData(ResultsetRows streamingData) {
      this.streamingData = streamingData;
   }

   public void checkForOutstandingStreamingData() {
      try {
         if (this.streamingData != null) {
            boolean shouldClobber = (Boolean)this.propertySet.getBooleanReadableProperty("clobberStreamingResults").getValue();
            if (!shouldClobber) {
               throw SQLError.createSQLException(Messages.getString("MysqlIO.39") + this.streamingData + Messages.getString("MysqlIO.40") + Messages.getString("MysqlIO.41") + Messages.getString("MysqlIO.42"), this.exceptionInterceptor);
            }

            this.streamingData.getOwner().closeOwner(false);
            this.clearInputStream();
         }

      } catch (SQLException var2) {
         throw ExceptionFactory.createException((String)var2.getMessage(), (Throwable)var2);
      }
   }

   public void closeStreamer(ResultsetRows streamer) {
      if (this.streamingData == null) {
         throw ExceptionFactory.createException(Messages.getString("MysqlIO.17") + streamer + Messages.getString("MysqlIO.18"), this.exceptionInterceptor);
      } else if (streamer != this.streamingData) {
         throw ExceptionFactory.createException(Messages.getString("MysqlIO.19") + streamer + Messages.getString("MysqlIO.20") + Messages.getString("MysqlIO.21") + Messages.getString("MysqlIO.22"), this.exceptionInterceptor);
      } else {
         this.streamingData = null;
      }
   }

   public void scanForAndThrowDataTruncation() throws SQLException {
      if (this.streamingData == null && (Boolean)this.propertySet.getBooleanReadableProperty("jdbcCompliantTruncation").getValue() && this.getWarningCount() > 0) {
         ResultSetUtil.convertShowWarningsToSQLWarnings(this.connection, this.getWarningCount(), true);
      }

   }

   static {
      OutputStreamWriter outWriter = null;

      try {
         outWriter = new OutputStreamWriter(new ByteArrayOutputStream());
         jvmPlatformCharset = outWriter.getEncoding();
      } finally {
         try {
            if (outWriter != null) {
               outWriter.close();
            }
         } catch (IOException var7) {
         }

      }

   }
}
