package com.mysql.cj.mysqlx.io;

import com.google.protobuf.MessageLite;
import com.mysql.cj.api.MysqlConnection;
import com.mysql.cj.api.authentication.AuthenticationProvider;
import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.io.PacketReceivedTimeHolder;
import com.mysql.cj.api.io.PacketSentTimeHolder;
import com.mysql.cj.api.io.Protocol;
import com.mysql.cj.api.io.ServerCapabilities;
import com.mysql.cj.api.io.ServerSession;
import com.mysql.cj.api.io.SocketConnection;
import com.mysql.cj.api.x.SqlResult;
import com.mysql.cj.core.CharsetMapping;
import com.mysql.cj.core.MysqlType;
import com.mysql.cj.core.exceptions.AssertionFailedException;
import com.mysql.cj.core.exceptions.ConnectionIsClosedException;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.core.io.StatementExecuteOk;
import com.mysql.cj.core.result.Field;
import com.mysql.cj.core.util.LazyString;
import com.mysql.cj.mysqlx.CreateIndexParams;
import com.mysql.cj.mysqlx.ExprUtil;
import com.mysql.cj.mysqlx.FilterParams;
import com.mysql.cj.mysqlx.FindParams;
import com.mysql.cj.mysqlx.InsertParams;
import com.mysql.cj.mysqlx.UpdateParams;
import com.mysql.cj.mysqlx.UpdateSpec;
import com.mysql.cj.mysqlx.protobuf.Mysqlx;
import com.mysql.cj.mysqlx.protobuf.MysqlxConnection;
import com.mysql.cj.mysqlx.protobuf.MysqlxDatatypes;
import com.mysql.cj.mysqlx.protobuf.MysqlxNotice;
import com.mysql.cj.mysqlx.protobuf.MysqlxResultset;
import com.mysql.cj.mysqlx.protobuf.MysqlxSession;
import com.mysql.cj.mysqlx.protobuf.MysqlxSql;
import com.mysql.cj.mysqlx.result.MysqlxRow;
import com.mysql.cj.mysqlx.result.MysqlxRowInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class MysqlxProtocol implements Protocol {
   private static final int MYSQLX_COLUMN_BYTES_CONTENT_TYPE_GEOMETRY = 1;
   public static final int MYSQLX_COLUMN_BYTES_CONTENT_TYPE_JSON = 2;
   private static final int MYSQLX_COLUMN_FLAGS_UINT_ZEROFILL = 1;
   private static final int MYSQLX_COLUMN_FLAGS_DOUBLE_UNSIGNED = 1;
   private static final int MYSQLX_COLUMN_FLAGS_FLOAT_UNSIGNED = 1;
   private static final int MYSQLX_COLUMN_FLAGS_DECIMAL_UNSIGNED = 1;
   private static final int MYSQLX_COLUMN_FLAGS_BYTES_RIGHTPAD = 1;
   private static final int MYSQLX_COLUMN_FLAGS_DATETIME_TIMESTAMP = 1;
   private static final int MYSQLX_COLUMN_FLAGS_NOT_NULL = 16;
   private static final int MYSQLX_COLUMN_FLAGS_PRIMARY_KEY = 32;
   private static final int MYSQLX_COLUMN_FLAGS_UNIQUE_KEY = 64;
   private static final int MYSQLX_COLUMN_FLAGS_MULTIPLE_KEY = 128;
   private static final int MYSQLX_COLUMN_FLAGS_AUTO_INCREMENT = 256;
   public static final int MysqlxNoticeFrameType_WARNING = 1;
   public static final int MysqlxNoticeFrameType_SESS_VAR_CHANGED = 2;
   public static final int MysqlxNoticeFrameType_SESS_STATE_CHANGED = 3;
   private MessageReader reader;
   private MessageWriter writer;
   private Closeable managedResource;
   private PropertySet propertySet;
   private Map<String, MysqlxDatatypes.Any> capabilities;
   private long clientId = -1L;
   private MessageBuilder msgBuilder = new MessageBuilder();
   public static Map<String, Integer> COLLATION_NAME_TO_COLLATION_INDEX = new HashMap();

   public MysqlxProtocol(MessageReader reader, MessageWriter writer, Closeable network, PropertySet propSet) {
      this.reader = reader;
      this.writer = writer;
      this.managedResource = network;
      this.propertySet = propSet;
      this.capabilities = this.getCapabilities();
   }

   public void init(MysqlConnection conn, int socketTimeout, SocketConnection socketConnection, PropertySet propSet) {
      throw new NullPointerException("TODO: this implementation uses a constructor");
   }

   public PropertySet getPropertySet() {
      return this.propertySet;
   }

   public void setPropertySet(PropertySet propertySet) {
      this.propertySet = propertySet;
   }

   public ServerCapabilities readServerCapabilities() {
      throw new NullPointerException("TODO");
   }

   public ServerSession getServerSession() {
      throw new NullPointerException("TODO");
   }

   public MysqlConnection getConnection() {
      throw new NullPointerException("TODO");
   }

   public void setConnection(MysqlConnection connection) {
      throw new NullPointerException("TODO");
   }

   public SocketConnection getSocketConnection() {
      throw new NullPointerException("TODO");
   }

   public AuthenticationProvider getAuthenticationProvider() {
      throw new NullPointerException("TODO");
   }

   public ExceptionInterceptor getExceptionInterceptor() {
      throw new NullPointerException("TODO");
   }

   public PacketSentTimeHolder getPacketSentTimeHolder() {
      throw new NullPointerException("TODO");
   }

   public void setPacketSentTimeHolder(PacketSentTimeHolder packetSentTimeHolder) {
      throw new NullPointerException("TODO");
   }

   public PacketReceivedTimeHolder getPacketReceivedTimeHolder() {
      throw new NullPointerException("TODO");
   }

   public void setPacketReceivedTimeHolder(PacketReceivedTimeHolder packetReceivedTimeHolder) {
      throw new NullPointerException("TODO");
   }

   private Map<String, MysqlxDatatypes.Any> getCapabilities() {
      this.writer.write(MysqlxConnection.CapabilitiesGet.getDefaultInstance());
      return (Map)((MysqlxConnection.Capabilities)this.reader.read(MysqlxConnection.Capabilities.class)).getCapabilitiesList().stream().collect(Collectors.toMap(MysqlxConnection.Capability::getName, MysqlxConnection.Capability::getValue));
   }

   public void setCapability(String name, Object value) {
      this.writer.write(this.msgBuilder.buildCapabilitiesSet(name, value));
      this.readOk();
   }

   public void sendSaslMysql41AuthStart() {
      MysqlxSession.AuthenticateStart.Builder builder = MysqlxSession.AuthenticateStart.newBuilder().setMechName("MYSQL41");
      this.writer.write(builder.build());
   }

   public void sendSaslMysql41AuthContinue(String user, String password, byte[] salt, String database) {
      this.writer.write(this.msgBuilder.buildMysql41AuthContinue(user, password, salt, database));
   }

   public void sendSaslAuthStart(String user, String password, String database) {
      this.writer.write(this.msgBuilder.buildPlainAuthStart(user, password, database));
   }

   public void negotiateSSLConnection(int packLength) {
      throw new NullPointerException("TODO: SSL is not yet supported in this X Protocol client");
   }

   public void rejectConnection(String message) {
      throw new NullPointerException("TODO");
   }

   public void beforeHandshake() {
      throw new NullPointerException("TODO");
   }

   public void afterHandshake() {
      throw new NullPointerException("TODO");
   }

   public void changeDatabase(String database) {
      throw new NullPointerException("TODO: Figure out how this is relevant for X Protocol client Session");
   }

   public void changeUser(String user, String password, String database) {
      throw new NullPointerException("TODO");
   }

   public String getPasswordCharacterEncoding() {
      throw new NullPointerException("TODO");
   }

   public boolean versionMeetsMinimum(int major, int minor, int subminor) {
      throw new NullPointerException("TODO: expose this via ServerVersion so calls look like x.getServerVersion().meetsMinimum(major, minor, subminor)");
   }

   public void readOk() {
      this.reader.read(Mysqlx.Ok.class);
   }

   public void readAuthenticateOk() {
      while(true) {
         if (this.reader.getNextMessageClass() == MysqlxNotice.Frame.class) {
            MysqlxNotice.Frame notice = (MysqlxNotice.Frame)this.reader.read(MysqlxNotice.Frame.class);
            if (notice.getType() == 3) {
               MysqlxNotice.SessionStateChanged msg = (MysqlxNotice.SessionStateChanged)MessageReader.parseNotice(notice.getPayload(), MysqlxNotice.SessionStateChanged.class);
               switch(msg.getParam()) {
               case CLIENT_ID_ASSIGNED:
                  this.clientId = msg.getValue().getVUnsignedInt();
                  continue;
               case ACCOUNT_EXPIRED:
               default:
                  throw new WrongArgumentException("Unknown SessionStateChanged notice received during authentication: " + msg);
               }
            }

            throw new WrongArgumentException("Unknown notice received during authentication: " + notice);
         }

         this.reader.read(MysqlxSession.AuthenticateOk.class);
         return;
      }
   }

   public byte[] readAuthenticateContinue() {
      MysqlxSession.AuthenticateContinue msg = (MysqlxSession.AuthenticateContinue)this.reader.read(MysqlxSession.AuthenticateContinue.class);
      byte[] data = msg.getAuthData().toByteArray();
      if (data.length != 20) {
         throw AssertionFailedException.shouldNotHappen("Salt length should be 20, but is " + data.length);
      } else {
         return data;
      }
   }

   public void sendCreateCollection(String schemaName, String collectionName) {
      this.writer.write(this.msgBuilder.buildXpluginCommand(MessageBuilder.XpluginStatementCommand.XPLUGIN_STMT_CREATE_COLLECTION, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("name").setValue(ExprUtil.buildAny(collectionName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(schemaName)))).build()));
   }

   public void sendDropCollection(String schemaName, String collectionName) {
      this.writer.write(this.msgBuilder.buildXpluginCommand(MessageBuilder.XpluginStatementCommand.XPLUGIN_STMT_DROP_COLLECTION, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("name").setValue(ExprUtil.buildAny(collectionName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(schemaName)))).build()));
   }

   public void sendListObjects(String schemaName, String pattern) {
      this.writer.write(this.msgBuilder.buildXpluginCommand(MessageBuilder.XpluginStatementCommand.XPLUGIN_STMT_LIST_OBJECTS, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(schemaName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("pattern").setValue(ExprUtil.buildAny(pattern)))).build()));
   }

   public void sendListObjects(String schemaName) {
      this.writer.write(this.msgBuilder.buildXpluginCommand(MessageBuilder.XpluginStatementCommand.XPLUGIN_STMT_LIST_OBJECTS, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(schemaName)))).build()));
   }

   public void sendListNotices() {
      this.writer.write(this.msgBuilder.buildXpluginCommand(MessageBuilder.XpluginStatementCommand.XPLUGIN_STMT_LIST_NOTICES));
   }

   public void sendEnableNotices(String... notices) {
      MysqlxDatatypes.Array.Builder abuilder = MysqlxDatatypes.Array.newBuilder();
      String[] var3 = notices;
      int var4 = notices.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String notice = var3[var5];
         abuilder.addValue(ExprUtil.buildAny(notice));
      }

      this.writer.write(this.msgBuilder.buildXpluginCommand(MessageBuilder.XpluginStatementCommand.XPLUGIN_STMT_ENABLE_NOTICES, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("notice").setValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.ARRAY).setArray(abuilder)))).build()));
   }

   public void sendDisableNotices(String... notices) {
      MysqlxDatatypes.Array.Builder abuilder = MysqlxDatatypes.Array.newBuilder();
      String[] var3 = notices;
      int var4 = notices.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String notice = var3[var5];
         abuilder.addValue(ExprUtil.buildAny(notice));
      }

      this.writer.write(this.msgBuilder.buildXpluginCommand(MessageBuilder.XpluginStatementCommand.XPLUGIN_STMT_DISABLE_NOTICES, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("notice").setValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.ARRAY).setArray(abuilder)))).build()));
   }

   public boolean hasMoreResults() {
      if (this.reader.getNextMessageClass() == MysqlxResultset.FetchDoneMoreResultsets.class) {
         this.reader.read(MysqlxResultset.FetchDoneMoreResultsets.class);
         return this.reader.getNextMessageClass() != MysqlxResultset.FetchDone.class;
      } else {
         return false;
      }
   }

   public StatementExecuteOk readStatementExecuteOk() {
      StatementExecuteOkBuilder builder = new StatementExecuteOkBuilder();
      if (this.reader.getNextMessageClass() == MysqlxResultset.FetchDone.class) {
         this.reader.read(MysqlxResultset.FetchDone.class);
      }

      while(this.reader.getNextMessageClass() == MysqlxNotice.Frame.class) {
         builder.addNotice((MysqlxNotice.Frame)this.reader.read(MysqlxNotice.Frame.class));
      }

      this.reader.read(MysqlxSql.StmtExecuteOk.class);
      return builder.build();
   }

   public void sendSqlStatement(String statement) {
      this.sendSqlStatement(statement, (Object)null);
   }

   public void sendSqlStatement(String statement, Object args) {
      this.writer.write(this.msgBuilder.buildSqlStatement(statement, (List)args));
   }

   public boolean hasResults() {
      return this.reader.getNextMessageClass() == MysqlxResultset.ColumnMetaData.class;
   }

   public void drainRows() {
      while(this.reader.getNextMessageClass() == MysqlxResultset.Row.class) {
         this.reader.read(MysqlxResultset.Row.class);
      }

   }

   private static int mysqlxTypeToMysqlType(MysqlxResultset.ColumnMetaData.FieldType type, int contentType) {
      switch(type) {
      case SINT:
         return 8;
      case UINT:
         return 8;
      case FLOAT:
         return 4;
      case DOUBLE:
         return 5;
      case DECIMAL:
         return 246;
      case BYTES:
         switch(contentType) {
         case 1:
            return 255;
         case 2:
            return 245;
         default:
            return 15;
         }
      case TIME:
         return 11;
      case DATETIME:
         return 12;
      case SET:
         return 248;
      case ENUM:
         return 247;
      case BIT:
         return 16;
      default:
         throw new WrongArgumentException("TODO: unknown field type: " + type);
      }
   }

   public static MysqlType findMysqlType(MysqlxResultset.ColumnMetaData.FieldType type, int contentType, int flags, int collationIndex) {
      switch(type) {
      case SINT:
         return MysqlType.BIGINT;
      case UINT:
         return MysqlType.BIGINT_UNSIGNED;
      case FLOAT:
         return 0 < (flags & 1) ? MysqlType.FLOAT_UNSIGNED : MysqlType.FLOAT;
      case DOUBLE:
         return 0 < (flags & 1) ? MysqlType.DOUBLE_UNSIGNED : MysqlType.DOUBLE;
      case DECIMAL:
         return 0 < (flags & 1) ? MysqlType.DECIMAL_UNSIGNED : MysqlType.DECIMAL;
      case BYTES:
         switch(contentType) {
         case 1:
            return MysqlType.GEOMETRY;
         case 2:
            return MysqlType.JSON;
         default:
            if (collationIndex == 33) {
               return MysqlType.VARBINARY;
            }

            return MysqlType.VARCHAR;
         }
      case TIME:
         return MysqlType.TIME;
      case DATETIME:
         return MysqlType.DATETIME;
      case SET:
         return MysqlType.SET;
      case ENUM:
         return MysqlType.ENUM;
      case BIT:
         return MysqlType.BIT;
      default:
         throw new WrongArgumentException("TODO: unknown field type: " + type);
      }
   }

   private static Field columnMetaDataToField(PropertySet propertySet, MysqlxResultset.ColumnMetaData col, String characterSet) {
      try {
         LazyString databaseName = new LazyString(col.getSchema().toString(characterSet));
         LazyString tableName = new LazyString(col.getTable().toString(characterSet));
         LazyString originalTableName = new LazyString(col.getOriginalTable().toString(characterSet));
         LazyString columnName = new LazyString(col.getName().toString(characterSet));
         LazyString originalColumnName = new LazyString(col.getOriginalName().toString(characterSet));
         long length = Integer.toUnsignedLong(col.getLength());
         int decimals = col.getFractionalDigits();
         int collationIndex = 0;
         if (col.hasCollation()) {
            collationIndex = (int)col.getCollation();
         }

         String encoding = CharsetMapping.COLLATION_INDEX_TO_COLLATION_NAME[collationIndex];
         MysqlType mysqlType = findMysqlType(col.getType(), col.getContentType(), col.getFlags(), collationIndex);
         int mysqlTypeId = mysqlxTypeToMysqlType(col.getType(), col.getContentType());
         short flags = 0;
         if (col.getType().equals(MysqlxResultset.ColumnMetaData.FieldType.UINT) && 0 < (col.getFlags() & 1)) {
            flags = (short)(flags | 64);
         } else if (col.getType().equals(MysqlxResultset.ColumnMetaData.FieldType.BYTES) && 0 < (col.getFlags() & 1)) {
            mysqlType = MysqlType.CHAR;
         } else if (col.getType().equals(MysqlxResultset.ColumnMetaData.FieldType.DATETIME) && 0 < (col.getFlags() & 1)) {
            mysqlType = MysqlType.TIMESTAMP;
         }

         if ((col.getFlags() & 16) > 0) {
            flags = (short)(flags | 1);
         }

         if ((col.getFlags() & 32) > 0) {
            flags = (short)(flags | 2);
         }

         if ((col.getFlags() & 64) > 0) {
            flags = (short)(flags | 4);
         }

         if ((col.getFlags() & 128) > 0) {
            flags = (short)(flags | 8);
         }

         if ((col.getFlags() & 256) > 0) {
            flags = (short)(flags | 512);
         }

         Field f = new Field(databaseName, tableName, originalTableName, columnName, originalColumnName, length, mysqlTypeId, flags, decimals, collationIndex, encoding, mysqlType);
         return f;
      } catch (UnsupportedEncodingException var17) {
         throw new WrongArgumentException("Unable to decode metadata strings", var17);
      }
   }

   public ArrayList<Field> readMetadata(String characterSet) {
      while(this.reader.getNextMessageClass() == MysqlxNotice.Frame.class) {
         this.reader.read(MysqlxNotice.Frame.class);
      }

      LinkedList fromServer = new LinkedList();

      do {
         fromServer.add(this.reader.read(MysqlxResultset.ColumnMetaData.class));
      } while(this.reader.getNextMessageClass() == MysqlxResultset.ColumnMetaData.class);

      ArrayList<Field> metadata = new ArrayList(fromServer.size());
      fromServer.forEach((col) -> {
         metadata.add(columnMetaDataToField(this.propertySet, col, characterSet));
      });
      return metadata;
   }

   public MysqlxRow readRowOrNull(ArrayList<Field> metadata) {
      if (this.reader.getNextMessageClass() == MysqlxResultset.Row.class) {
         MysqlxResultset.Row r = (MysqlxResultset.Row)this.reader.read(MysqlxResultset.Row.class);
         return new MysqlxRow(metadata, r);
      } else {
         return null;
      }
   }

   public MysqlxRowInputStream getRowInputStream(ArrayList<Field> metadata) {
      return new MysqlxRowInputStream(metadata, this);
   }

   public CompletableFuture<SqlResult> asyncExecuteSql(String sql, Object args, String metadataCharacterSet, TimeZone defaultTimeZone) {
      CompletableFuture<SqlResult> f = new CompletableFuture();
      AsyncMessageReader.MessageListener l = new SqlResultMessageListener(f, (col) -> {
         return columnMetaDataToField(this.propertySet, col, metadataCharacterSet);
      }, defaultTimeZone);
      CompletionHandler<Long, Void> resultHandler = new ErrorToFutureCompletionHandler(f, () -> {
         ((AsyncMessageReader)this.reader).pushMessageListener(l);
      });
      ((AsyncMessageWriter)this.writer).writeAsync(this.msgBuilder.buildSqlStatement(sql, (List)args), resultHandler);
      return f;
   }

   public void asyncFind(FindParams findParams, String metadataCharacterSet, ResultListener callbacks, CompletableFuture<?> errorFuture) {
      AsyncMessageReader.MessageListener l = new ResultMessageListener((col) -> {
         return columnMetaDataToField(this.propertySet, col, metadataCharacterSet);
      }, callbacks);
      CompletionHandler<Long, Void> resultHandler = new ErrorToFutureCompletionHandler(errorFuture, () -> {
         ((AsyncMessageReader)this.reader).pushMessageListener(l);
      });
      ((AsyncMessageWriter)this.writer).writeAsync(this.msgBuilder.buildFind(findParams), resultHandler);
   }

   private CompletableFuture<StatementExecuteOk> asyncUpdate(MessageLite commandMessage) {
      CompletableFuture<StatementExecuteOk> f = new CompletableFuture();
      StatementExecuteOkMessageListener l = new StatementExecuteOkMessageListener(f);
      CompletionHandler<Long, Void> resultHandler = new ErrorToFutureCompletionHandler(f, () -> {
         ((AsyncMessageReader)this.reader).pushMessageListener(l);
      });
      ((AsyncMessageWriter)this.writer).writeAsync(commandMessage, resultHandler);
      return f;
   }

   public CompletableFuture<StatementExecuteOk> asyncAddDocs(String schemaName, String collectionName, List<String> jsonStrings) {
      return this.asyncUpdate(this.msgBuilder.buildDocInsert(schemaName, collectionName, jsonStrings));
   }

   public CompletableFuture<StatementExecuteOk> asyncInsertRows(String schemaName, String tableName, InsertParams insertParams) {
      return this.asyncUpdate(this.msgBuilder.buildRowInsert(schemaName, tableName, insertParams));
   }

   public CompletableFuture<StatementExecuteOk> asyncUpdateDocs(FilterParams filterParams, List<UpdateSpec> updates) {
      return this.asyncUpdate(this.msgBuilder.buildDocUpdate(filterParams, updates));
   }

   public CompletableFuture<StatementExecuteOk> asyncUpdateRows(FilterParams filterParams, UpdateParams updateParams) {
      return this.asyncUpdate(this.msgBuilder.buildRowUpdate(filterParams, updateParams));
   }

   public CompletableFuture<StatementExecuteOk> asyncDeleteDocs(FilterParams filterParams) {
      return this.asyncUpdate(this.msgBuilder.buildDelete(filterParams));
   }

   public CompletableFuture<StatementExecuteOk> asyncCreateCollectionIndex(String schemaName, String collectionName, CreateIndexParams params) {
      return this.asyncUpdate(this.msgBuilder.buildCreateCollectionIndex(schemaName, collectionName, params));
   }

   public CompletableFuture<StatementExecuteOk> asyncDropCollectionIndex(String schemaName, String collectionName, String indexName) {
      return this.asyncUpdate(this.msgBuilder.buildDropCollectionIndex(schemaName, collectionName, indexName));
   }

   public void sendFind(FindParams findParams) {
      this.writer.write(this.msgBuilder.buildFind(findParams));
   }

   public void sendDocUpdates(FilterParams filterParams, List<UpdateSpec> updates) {
      this.writer.write(this.msgBuilder.buildDocUpdate(filterParams, updates));
   }

   public void sendRowUpdates(FilterParams filterParams, UpdateParams updateParams) {
      this.writer.write(this.msgBuilder.buildRowUpdate(filterParams, updateParams));
   }

   public void sendDocDelete(FilterParams filterParams) {
      this.writer.write(this.msgBuilder.buildDelete(filterParams));
   }

   public void sendDocInsert(String schemaName, String collectionName, List<String> jsonStrings) {
      this.writer.write(this.msgBuilder.buildDocInsert(schemaName, collectionName, jsonStrings));
   }

   public void sendRowInsert(String schemaName, String tableName, InsertParams insertParams) {
      this.writer.write(this.msgBuilder.buildRowInsert(schemaName, tableName, insertParams));
   }

   public void sendSessionClose() {
      this.writer.write(MysqlxSession.Close.getDefaultInstance());
   }

   public String getPluginVersion() {
      return ((MysqlxDatatypes.Any)this.capabilities.get("plugin.version")).getScalar().getVString().getValue().toStringUtf8();
   }

   public void sendCreateCollectionIndex(String schemaName, String collectionName, CreateIndexParams params) {
      this.writer.write(this.msgBuilder.buildCreateCollectionIndex(schemaName, collectionName, params));
   }

   public void sendDropCollectionIndex(String schemaName, String collectionName, String indexName) {
      this.writer.write(this.msgBuilder.buildDropCollectionIndex(schemaName, collectionName, indexName));
   }

   public boolean isOpen() {
      return this.managedResource != null;
   }

   public void close() throws IOException {
      if (this.managedResource == null) {
         throw new ConnectionIsClosedException();
      } else {
         this.managedResource.close();
         this.managedResource = null;
      }
   }

   public boolean isSqlResultPending() {
      Class<?> nextMessageClass = this.reader.getNextMessageClass();
      if (nextMessageClass == MysqlxResultset.ColumnMetaData.class) {
         return true;
      } else {
         if (nextMessageClass == MysqlxResultset.FetchDoneMoreResultsets.class) {
            this.reader.read(MysqlxResultset.FetchDoneMoreResultsets.class);
         }

         return false;
      }
   }

   public long getClientId() {
      return this.clientId;
   }

   public void connect(String user, String password, String database) {
   }

   public void setMaxAllowedPacket(int maxAllowedPacket) {
      this.writer.setMaxAllowedPacket(maxAllowedPacket);
   }

   static {
      for(int i = 0; i < CharsetMapping.COLLATION_INDEX_TO_COLLATION_NAME.length; ++i) {
         COLLATION_NAME_TO_COLLATION_INDEX.put(CharsetMapping.COLLATION_INDEX_TO_COLLATION_NAME[i], i);
      }

   }
}
