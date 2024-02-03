package com.mysql.cj.jdbc;

import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.api.jdbc.result.ResultSetInternalMethods;
import com.mysql.cj.api.mysqla.io.NativeProtocol;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.api.mysqla.result.Resultset;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.MysqlType;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.profiler.ProfilerEventHandlerFactory;
import com.mysql.cj.core.profiler.ProfilerEventImpl;
import com.mysql.cj.core.result.Field;
import com.mysql.cj.core.util.LogUtils;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.core.util.TestUtils;
import com.mysql.cj.jdbc.exceptions.MySQLStatementCancelledException;
import com.mysql.cj.jdbc.exceptions.MySQLTimeoutException;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.jdbc.util.TimeUtil;
import com.mysql.cj.mysqla.io.Buffer;
import com.mysql.cj.mysqla.io.ColumnDefinitionFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class ServerPreparedStatement extends PreparedStatement {
   protected static final int BLOB_STREAM_READ_BUF_SIZE = 8192;
   private boolean hasOnDuplicateKeyUpdate = false;
   private boolean detectedLongParameterSwitch = false;
   private int fieldCount;
   private boolean invalid = false;
   private CJException invalidationException;
   private PacketPayload outByteBuffer;
   private ServerPreparedStatement.BindValue[] parameterBindings;
   private Field[] parameterFields;
   private Field[] resultFields;
   private boolean sendTypesToServer = false;
   private long serverStatementId;
   private int netBufferLength = 16384;
   protected boolean isCached = false;
   private boolean useAutoSlowLog;
   private boolean hasCheckedRewrite = false;
   private boolean canRewrite = false;
   private int locationOfOnDuplicateKeyUpdate = -2;

   private void storeTime(PacketPayload intoBuf, Time tm, TimeZone tz) throws SQLException {
      intoBuf.ensureCapacity(9);
      intoBuf.writeInteger(NativeProtocol.IntegerDataType.INT1, 8L);
      intoBuf.writeInteger(NativeProtocol.IntegerDataType.INT1, 0L);
      intoBuf.writeInteger(NativeProtocol.IntegerDataType.INT4, 0L);
      Calendar cal = Calendar.getInstance(tz);
      cal.setTime(tm);
      intoBuf.writeInteger(NativeProtocol.IntegerDataType.INT1, (long)cal.get(11));
      intoBuf.writeInteger(NativeProtocol.IntegerDataType.INT1, (long)cal.get(12));
      intoBuf.writeInteger(NativeProtocol.IntegerDataType.INT1, (long)cal.get(13));
   }

   protected static ServerPreparedStatement getInstance(JdbcConnection conn, String sql, String catalog, int resultSetType, int resultSetConcurrency) throws SQLException {
      return new ServerPreparedStatement(conn, sql, catalog, resultSetType, resultSetConcurrency);
   }

   protected ServerPreparedStatement(JdbcConnection conn, String sql, String catalog, int resultSetType, int resultSetConcurrency) throws SQLException {
      super(conn, catalog);
      this.checkNullOrEmptyQuery(sql);
      int startOfStatement = findStartOfStatement(sql);
      this.firstCharOfStmt = StringUtils.firstAlphaCharUc(sql, startOfStatement);
      this.hasOnDuplicateKeyUpdate = this.firstCharOfStmt == 'I' && this.containsOnDuplicateKeyInString(sql);
      this.useAutoSlowLog = (Boolean)this.session.getPropertySet().getBooleanReadableProperty("autoSlowLog").getValue();
      this.netBufferLength = this.session.getServerVariable("net_buffer_length", 16384);
      String statementComment = this.connection.getStatementComment();
      this.originalSql = statementComment == null ? sql : "/* " + statementComment + " */ " + sql;

      try {
         this.serverPrepare(sql);
      } catch (SQLException | CJException var9) {
         this.realClose(false, true);
         throw SQLExceptionsMapping.translateException(var9, this.getExceptionInterceptor());
      }

      this.setResultSetType(resultSetType);
      this.setResultSetConcurrency(resultSetConcurrency);
      this.parameterTypes = new MysqlType[this.parameterCount];
   }

   public void addBatch() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (this.batchedArgs == null) {
               this.batchedArgs = new ArrayList();
            }

            this.batchedArgs.add(new ServerPreparedStatement.BatchedBindValues(this.parameterBindings));
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public String asSql(boolean quoteStreamsAndUnknowns) throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         PreparedStatement pStmtForSub = null;

         String var18;
         try {
            pStmtForSub = PreparedStatement.getInstance(this.connection, this.originalSql, this.getCurrentCatalog());
            int numParameters = pStmtForSub.parameterCount;
            int ourNumParameters = this.parameterCount;

            for(int i = 0; i < numParameters && i < ourNumParameters; ++i) {
               if (this.parameterBindings[i] != null) {
                  if (this.parameterBindings[i].isNull) {
                     pStmtForSub.setNull(i + 1, MysqlType.NULL);
                  } else {
                     ServerPreparedStatement.BindValue bindValue = this.parameterBindings[i];
                     switch(bindValue.bufferType) {
                     case 1:
                        pStmtForSub.setByte(i + 1, (byte)((int)bindValue.longBinding));
                        break;
                     case 2:
                        pStmtForSub.setShort(i + 1, (short)((int)bindValue.longBinding));
                        break;
                     case 3:
                        pStmtForSub.setInt(i + 1, (int)bindValue.longBinding);
                        break;
                     case 4:
                        pStmtForSub.setFloat(i + 1, bindValue.floatBinding);
                        break;
                     case 5:
                        pStmtForSub.setDouble(i + 1, bindValue.doubleBinding);
                        break;
                     case 6:
                     case 7:
                     default:
                        pStmtForSub.setObject(i + 1, this.parameterBindings[i].value);
                        break;
                     case 8:
                        pStmtForSub.setLong(i + 1, bindValue.longBinding);
                     }
                  }
               }
            }

            var18 = pStmtForSub.asSql(quoteStreamsAndUnknowns);
         } finally {
            if (pStmtForSub != null) {
               try {
                  pStmtForSub.close();
               } catch (SQLException var15) {
               }
            }

         }

         return var18;
      }
   }

   protected JdbcConnection checkClosed() {
      if (this.invalid) {
         throw this.invalidationException;
      } else {
         return super.checkClosed();
      }
   }

   public void clearParameters() {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.clearParametersInternal(true);
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   private void clearParametersInternal(boolean clearServerParameters) {
      boolean hadLongData = false;
      if (this.parameterBindings != null) {
         for(int i = 0; i < this.parameterCount; ++i) {
            if (this.parameterBindings[i] != null && this.parameterBindings[i].isLongData) {
               hadLongData = true;
            }

            this.parameterBindings[i].reset();
         }
      }

      if (clearServerParameters && hadLongData) {
         this.serverResetStatement();
         this.detectedLongParameterSwitch = false;
      }

   }

   protected void setClosed(boolean flag) {
      this.isClosed = flag;
   }

   public void close() throws SQLException {
      try {
         JdbcConnection locallyScopedConn = this.connection;
         if (locallyScopedConn != null) {
            synchronized(locallyScopedConn.getConnectionMutex()) {
               if (this.isCached && this.isPoolable() && !this.isClosed) {
                  this.clearParameters();
                  this.isClosed = true;
                  this.connection.recachePreparedStatement(this);
               } else {
                  this.realClose(true, true);
               }
            }
         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   private void dumpCloseForTestcase() throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         StringBuilder buf = new StringBuilder();
         this.connection.generateConnectionCommentBlock(buf);
         buf.append("DEALLOCATE PREPARE debug_stmt_");
         buf.append(this.statementId);
         buf.append(";\n");
         TestUtils.dumpTestcaseQuery(buf.toString());
      }
   }

   private void dumpExecuteForTestcase() throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         StringBuilder buf = new StringBuilder();

         int i;
         for(i = 0; i < this.parameterCount; ++i) {
            this.connection.generateConnectionCommentBlock(buf);
            buf.append("SET @debug_stmt_param");
            buf.append(this.statementId);
            buf.append("_");
            buf.append(i);
            buf.append("=");
            if (this.parameterBindings[i].isNull) {
               buf.append("NULL");
            } else {
               buf.append(this.parameterBindings[i].toString(true));
            }

            buf.append(";\n");
         }

         this.connection.generateConnectionCommentBlock(buf);
         buf.append("EXECUTE debug_stmt_");
         buf.append(this.statementId);
         if (this.parameterCount > 0) {
            buf.append(" USING ");

            for(i = 0; i < this.parameterCount; ++i) {
               if (i > 0) {
                  buf.append(", ");
               }

               buf.append("@debug_stmt_param");
               buf.append(this.statementId);
               buf.append("_");
               buf.append(i);
            }
         }

         buf.append(";\n");
         TestUtils.dumpTestcaseQuery(buf.toString());
      }
   }

   private void dumpPrepareForTestcase() throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         StringBuilder buf = new StringBuilder(this.originalSql.length() + 64);
         this.connection.generateConnectionCommentBlock(buf);
         buf.append("PREPARE debug_stmt_");
         buf.append(this.statementId);
         buf.append(" FROM \"");
         buf.append(this.originalSql);
         buf.append("\";\n");
         TestUtils.dumpTestcaseQuery(buf.toString());
      }
   }

   protected long[] executeBatchSerially(int batchTimeout) throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         JdbcConnection locallyScopedConn = this.connection;
         if (locallyScopedConn.isReadOnly()) {
            throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.2") + Messages.getString("ServerPreparedStatement.3"), "S1009", this.getExceptionInterceptor());
         } else {
            this.clearWarnings();
            ServerPreparedStatement.BindValue[] oldBindValues = this.parameterBindings;

            long[] var37;
            try {
               long[] updateCounts = null;
               if (this.batchedArgs != null) {
                  int nbrCommands = this.batchedArgs.size();
                  updateCounts = new long[nbrCommands];
                  if (this.retrieveGeneratedKeys) {
                     this.batchedGeneratedKeys = new ArrayList(nbrCommands);
                  }

                  for(int i = 0; i < nbrCommands; ++i) {
                     updateCounts[i] = -3L;
                  }

                  SQLException sqlEx = null;
                  int commandIndex = false;
                  ServerPreparedStatement.BindValue[] previousBindValuesForBatch = null;
                  StatementImpl.CancelTask timeoutTask = null;

                  try {
                     if ((Boolean)locallyScopedConn.getPropertySet().getBooleanReadableProperty("enableQueryTimeouts").getValue() && batchTimeout != 0) {
                        timeoutTask = new StatementImpl.CancelTask(this);
                        locallyScopedConn.getCancelTimer().schedule(timeoutTask, (long)batchTimeout);
                     }

                     for(int commandIndex = 0; commandIndex < nbrCommands; ++commandIndex) {
                        Object arg = this.batchedArgs.get(commandIndex);

                        try {
                           if (arg instanceof String) {
                              updateCounts[commandIndex] = this.executeUpdateInternal((String)arg, true, this.retrieveGeneratedKeys);
                              this.getBatchedGeneratedKeys(this.results.getFirstCharOfQuery() == 'I' && this.containsOnDuplicateKeyInString((String)arg) ? 1 : 0);
                           } else {
                              this.parameterBindings = ((ServerPreparedStatement.BatchedBindValues)arg).batchedParameterValues;
                              if (previousBindValuesForBatch != null) {
                                 for(int j = 0; j < this.parameterBindings.length; ++j) {
                                    if (this.parameterBindings[j].bufferType != previousBindValuesForBatch[j].bufferType) {
                                       this.sendTypesToServer = true;
                                       break;
                                    }
                                 }
                              }

                              try {
                                 updateCounts[commandIndex] = this.executeUpdateInternal(false, true);
                              } finally {
                                 previousBindValuesForBatch = this.parameterBindings;
                              }

                              this.getBatchedGeneratedKeys(this.containsOnDuplicateKeyUpdateInSQL() ? 1 : 0);
                           }
                        } catch (SQLException var33) {
                           updateCounts[commandIndex] = -3L;
                           if (!this.continueBatchOnError || var33 instanceof MySQLTimeoutException || var33 instanceof MySQLStatementCancelledException || this.hasDeadlockOrTimeoutRolledBackTx(var33)) {
                              long[] newUpdateCounts = new long[commandIndex];
                              System.arraycopy(updateCounts, 0, newUpdateCounts, 0, commandIndex);
                              throw SQLError.createBatchUpdateException(var33, newUpdateCounts, this.getExceptionInterceptor());
                           }

                           sqlEx = var33;
                        }
                     }
                  } finally {
                     if (timeoutTask != null) {
                        timeoutTask.cancel();
                        locallyScopedConn.getCancelTimer().purge();
                     }

                     this.resetCancelledState();
                  }

                  if (sqlEx != null) {
                     throw SQLError.createBatchUpdateException(sqlEx, updateCounts, this.getExceptionInterceptor());
                  }
               }

               var37 = updateCounts != null ? updateCounts : new long[0];
            } finally {
               this.parameterBindings = oldBindValues;
               this.sendTypesToServer = true;
               this.clearBatch();
            }

            return var37;
         }
      }
   }

   protected ResultSetInternalMethods executeInternal(int maxRowsToRetrieve, PacketPayload sendPacket, boolean createStreamingResultSet, boolean queryIsSelectOnly, ColumnDefinition metadata, boolean isBatch) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            ++this.numberOfExecutions;

            ResultSetInternalMethods var10000;
            try {
               var10000 = this.serverExecute(maxRowsToRetrieve, createStreamingResultSet, metadata);
            } catch (SQLException var14) {
               SQLException sqlEx = var14;
               if ((Boolean)this.session.getPropertySet().getBooleanReadableProperty("enablePacketDebug").getValue()) {
                  this.session.dumpPacketRingBuffer();
               }

               if ((Boolean)this.dumpQueriesOnException.getValue()) {
                  String extractedSql = this.toString();
                  StringBuilder messageBuf = new StringBuilder(extractedSql.length() + 32);
                  messageBuf.append("\n\nQuery being executed when exception was thrown:\n");
                  messageBuf.append(extractedSql);
                  messageBuf.append("\n\n");
                  sqlEx = ConnectionImpl.appendMessageToException(var14, messageBuf.toString(), this.getExceptionInterceptor());
               }

               throw sqlEx;
            } catch (Exception var15) {
               if ((Boolean)this.session.getPropertySet().getBooleanReadableProperty("enablePacketDebug").getValue()) {
                  this.session.dumpPacketRingBuffer();
               }

               SQLException sqlEx = SQLError.createSQLException(var15.toString(), "S1000", var15, this.getExceptionInterceptor());
               if ((Boolean)this.dumpQueriesOnException.getValue()) {
                  String extractedSql = this.toString();
                  StringBuilder messageBuf = new StringBuilder(extractedSql.length() + 32);
                  messageBuf.append("\n\nQuery being executed when exception was thrown:\n");
                  messageBuf.append(extractedSql);
                  messageBuf.append("\n\n");
                  sqlEx = ConnectionImpl.appendMessageToException(sqlEx, messageBuf.toString(), this.getExceptionInterceptor());
               }

               throw sqlEx;
            }

            return var10000;
         }
      } catch (CJException var17) {
         throw SQLExceptionsMapping.translateException(var17, this.getExceptionInterceptor());
      }
   }

   protected PacketPayload fillSendPacket() throws SQLException {
      return null;
   }

   protected PacketPayload fillSendPacket(byte[][] batchedParameterStrings, InputStream[] batchedParameterStreams, boolean[] batchedIsStream, int[] batchedStreamLengths) throws SQLException {
      return null;
   }

   protected ServerPreparedStatement.BindValue getBinding(int parameterIndex, boolean forLongData) throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         if (this.parameterBindings.length == 0) {
            throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.8"), "S1009", this.getExceptionInterceptor());
         } else {
            --parameterIndex;
            if (parameterIndex >= 0 && parameterIndex < this.parameterBindings.length) {
               if (this.parameterBindings[parameterIndex] == null) {
                  this.parameterBindings[parameterIndex] = new ServerPreparedStatement.BindValue();
               } else if (this.parameterBindings[parameterIndex].isLongData && !forLongData) {
                  this.detectedLongParameterSwitch = true;
               }

               return this.parameterBindings[parameterIndex];
            } else {
               throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.9") + (parameterIndex + 1) + Messages.getString("ServerPreparedStatement.10") + this.parameterBindings.length, "S1009", this.getExceptionInterceptor());
            }
         }
      }
   }

   public ServerPreparedStatement.BindValue[] getParameterBindValues() {
      return this.parameterBindings;
   }

   byte[] getBytes(int parameterIndex) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            ServerPreparedStatement.BindValue bindValue = this.getBinding(parameterIndex, false);
            if (bindValue.isNull) {
               return null;
            } else if (bindValue.isLongData) {
               throw SQLError.createSQLFeatureNotSupportedException();
            } else {
               if (this.outByteBuffer == null) {
                  this.outByteBuffer = new Buffer(this.netBufferLength);
               }

               this.outByteBuffer.setPosition(4);
               int originalPosition = this.outByteBuffer.getPosition();
               this.storeBinding(this.outByteBuffer, bindValue);
               int newPosition = this.outByteBuffer.getPosition();
               int length = newPosition - originalPosition;
               byte[] valueAsBytes = new byte[length];
               System.arraycopy(this.outByteBuffer.getByteBuffer(), originalPosition, valueAsBytes, 0, length);
               return valueAsBytes;
            }
         }
      } catch (CJException var11) {
         throw SQLExceptionsMapping.translateException(var11, this.getExceptionInterceptor());
      }
   }

   public ResultSetMetaData getMetaData() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            return this.resultFields == null ? null : new com.mysql.cj.jdbc.result.ResultSetMetaData(this.session, this.resultFields, (Boolean)this.session.getPropertySet().getBooleanReadableProperty("useOldAliasMetadataBehavior").getValue(), (Boolean)this.session.getPropertySet().getBooleanReadableProperty("yearIsDateType").getValue(), this.getExceptionInterceptor());
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public ParameterMetaData getParameterMetaData() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (this.parameterMetaData == null) {
               this.parameterMetaData = new MysqlParameterMetadata(this.session, this.parameterFields, this.parameterCount, this.getExceptionInterceptor());
            }

            return this.parameterMetaData;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public boolean isNull(int paramIndex) {
      throw new IllegalArgumentException(Messages.getString("ServerPreparedStatement.7"));
   }

   public void realClose(boolean calledExplicitly, boolean closeOpenResults) throws SQLException {
      try {
         JdbcConnection locallyScopedConn = this.connection;
         if (locallyScopedConn != null) {
            synchronized(locallyScopedConn.getConnectionMutex()) {
               if (this.connection != null) {
                  if ((Boolean)this.autoGenerateTestcaseScript.getValue()) {
                     this.dumpCloseForTestcase();
                  }

                  CJException exceptionDuringClose = null;
                  if (calledExplicitly && !this.connection.isClosed()) {
                     synchronized(this.connection.getConnectionMutex()) {
                        try {
                           PacketPayload packet = this.session.getSharedSendPacket();
                           packet.writeInteger(NativeProtocol.IntegerDataType.INT1, 25L);
                           packet.writeInteger(NativeProtocol.IntegerDataType.INT4, this.serverStatementId);
                           this.session.sendCommand(25, (String)null, packet, true, (String)null, 0);
                        } catch (CJException var11) {
                           exceptionDuringClose = var11;
                        }
                     }
                  }

                  if (this.isCached) {
                     this.connection.decachePreparedStatement(this);
                  }

                  super.realClose(calledExplicitly, closeOpenResults);
                  this.clearParametersInternal(false);
                  this.parameterBindings = null;
                  this.parameterFields = null;
                  this.resultFields = null;
                  if (exceptionDuringClose != null) {
                     throw exceptionDuringClose;
                  }
               }

            }
         }
      } catch (CJException var14) {
         throw SQLExceptionsMapping.translateException(var14, this.getExceptionInterceptor());
      }
   }

   protected void rePrepare() {
      synchronized(this.checkClosed().getConnectionMutex()) {
         this.invalidationException = null;

         try {
            this.serverPrepare(this.originalSql);
         } catch (SQLException var7) {
            this.invalidationException = ExceptionFactory.createException((String)var7.getMessage(), (Throwable)var7);
         } catch (Exception var8) {
            this.invalidationException = ExceptionFactory.createException((String)var8.getMessage(), (Throwable)var8);
         }

         if (this.invalidationException != null) {
            this.invalid = true;
            this.parameterBindings = null;
            this.parameterFields = null;
            this.resultFields = null;
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

            try {
               this.closeAllOpenResults();
            } catch (Exception var4) {
            }

            if (this.connection != null && !(Boolean)this.dontTrackOpenResources.getValue()) {
               this.connection.unregisterStatement(this);
            }
         }

      }
   }

   boolean isCursorRequired() throws SQLException {
      return this.resultFields != null && (Boolean)this.session.getPropertySet().getBooleanReadableProperty("useCursorFetch").getValue() && this.getResultSetType() == 1003 && this.getResultSetConcurrency() == 1007 && this.getFetchSize() > 0;
   }

   private ResultSetInternalMethods serverExecute(int maxRowsToRetrieve, boolean createStreamingResultSet, ColumnDefinition metadata) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (this.session.shouldIntercept()) {
               ResultSetInternalMethods interceptedResults = (ResultSetInternalMethods)this.session.invokeStatementInterceptorsPre(this.originalSql, this, true);
               if (interceptedResults != null) {
                  return interceptedResults;
               }
            }

            int i;
            if (this.detectedLongParameterSwitch) {
               boolean firstFound = false;
               long boundTimeToCheck = 0L;

               for(i = 0; i < this.parameterCount - 1; ++i) {
                  if (this.parameterBindings[i].isLongData) {
                     if (firstFound && boundTimeToCheck != this.parameterBindings[i].boundBeforeExecutionNum) {
                        throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.11") + Messages.getString("ServerPreparedStatement.12"), "S1C00", this.getExceptionInterceptor());
                     }

                     firstFound = true;
                     boundTimeToCheck = this.parameterBindings[i].boundBeforeExecutionNum;
                  }
               }

               this.serverResetStatement();
            }

            int i;
            for(i = 0; i < this.parameterCount; ++i) {
               if (!this.parameterBindings[i].isSet) {
                  throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.13") + (i + 1) + Messages.getString("ServerPreparedStatement.14"), "S1009", this.getExceptionInterceptor());
               }
            }

            for(i = 0; i < this.parameterCount; ++i) {
               if (this.parameterBindings[i].isLongData) {
                  this.serverLongData(i, this.parameterBindings[i]);
               }
            }

            if ((Boolean)this.autoGenerateTestcaseScript.getValue()) {
               this.dumpExecuteForTestcase();
            }

            PacketPayload packet = this.session.getSharedSendPacket();
            packet.writeInteger(NativeProtocol.IntegerDataType.INT1, 23L);
            packet.writeInteger(NativeProtocol.IntegerDataType.INT4, this.serverStatementId);
            if (this.resultFields != null && this.useCursorFetch && this.getResultSetType() == 1003 && this.getFetchSize() > 0) {
               packet.writeInteger(NativeProtocol.IntegerDataType.INT1, 1L);
            } else {
               packet.writeInteger(NativeProtocol.IntegerDataType.INT1, 0L);
            }

            packet.writeInteger(NativeProtocol.IntegerDataType.INT4, 1L);
            int nullCount = (this.parameterCount + 7) / 8;
            int nullBitsPosition = packet.getPosition();

            for(i = 0; i < nullCount; ++i) {
               packet.writeInteger(NativeProtocol.IntegerDataType.INT1, 0L);
            }

            byte[] nullBitsBuffer = new byte[nullCount];
            packet.writeInteger(NativeProtocol.IntegerDataType.INT1, this.sendTypesToServer ? 1L : 0L);
            int i;
            if (this.sendTypesToServer) {
               for(i = 0; i < this.parameterCount; ++i) {
                  packet.writeInteger(NativeProtocol.IntegerDataType.INT2, (long)this.parameterBindings[i].bufferType);
               }
            }

            for(i = 0; i < this.parameterCount; ++i) {
               if (!this.parameterBindings[i].isLongData) {
                  if (!this.parameterBindings[i].isNull) {
                     this.storeBinding(packet, this.parameterBindings[i]);
                  } else {
                     nullBitsBuffer[i / 8] = (byte)(nullBitsBuffer[i / 8] | 1 << (i & 7));
                  }
               }
            }

            i = packet.getPosition();
            packet.setPosition(nullBitsPosition);
            packet.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, nullBitsBuffer);
            packet.setPosition(i);
            long begin = 0L;
            boolean gatherPerformanceMetrics = (Boolean)this.gatherPerfMetrics.getValue();
            if (this.profileSQL || this.logSlowQueries || gatherPerformanceMetrics) {
               begin = this.session.getCurrentTimeNanosOrMillis();
            }

            this.resetCancelledState();
            StatementImpl.CancelTask timeoutTask = null;

            ResultSetInternalMethods interceptedResults;
            try {
               String queryAsString = "";
               if (this.profileSQL || this.logSlowQueries || gatherPerformanceMetrics) {
                  queryAsString = this.asSql(true);
               }

               if ((Boolean)this.connection.getPropertySet().getBooleanReadableProperty("enableQueryTimeouts").getValue() && this.timeoutInMillis != 0) {
                  timeoutTask = new StatementImpl.CancelTask(this);
                  this.connection.getCancelTimer().schedule(timeoutTask, (long)this.timeoutInMillis);
               }

               this.statementBegins();
               PacketPayload resultPacket = this.session.sendCommand(23, (String)null, packet, false, (String)null, 0);
               long queryEndTime = 0L;
               if (this.logSlowQueries || gatherPerformanceMetrics || this.profileSQL) {
                  queryEndTime = this.session.getCurrentTimeNanosOrMillis();
               }

               if (timeoutTask != null) {
                  timeoutTask.cancel();
                  this.connection.getCancelTimer().purge();
                  if (timeoutTask.caughtWhileCancelling != null) {
                     throw timeoutTask.caughtWhileCancelling;
                  }

                  timeoutTask = null;
               }

               ResultSetInternalMethods rs;
               synchronized(this.cancelTimeoutMutex) {
                  if (this.wasCancelled) {
                     rs = null;
                     Object cause;
                     if (this.wasCancelledByTimeout) {
                        cause = new MySQLTimeoutException();
                     } else {
                        cause = new MySQLStatementCancelledException();
                     }

                     this.resetCancelledState();
                     throw cause;
                  }
               }

               boolean queryWasSlow = false;
               if (this.logSlowQueries || gatherPerformanceMetrics) {
                  long elapsedTime = queryEndTime - begin;
                  if (this.logSlowQueries) {
                     if (this.useAutoSlowLog) {
                        queryWasSlow = elapsedTime > (long)(Integer)this.slowQueryThresholdMillis.getValue();
                     } else {
                        queryWasSlow = this.connection.isAbonormallyLongQuery(elapsedTime);
                        this.connection.reportQueryTime(elapsedTime);
                     }
                  }

                  if (queryWasSlow) {
                     StringBuilder mesgBuf = new StringBuilder(48 + this.originalSql.length());
                     mesgBuf.append(Messages.getString("ServerPreparedStatement.15"));
                     mesgBuf.append(this.session.getSlowQueryThreshold());
                     mesgBuf.append(Messages.getString("ServerPreparedStatement.15a"));
                     mesgBuf.append(elapsedTime);
                     mesgBuf.append(Messages.getString("ServerPreparedStatement.16"));
                     mesgBuf.append("as prepared: ");
                     mesgBuf.append(this.originalSql);
                     mesgBuf.append("\n\n with parameters bound:\n\n");
                     mesgBuf.append(queryAsString);
                     this.eventSink.consumeEvent(new ProfilerEventImpl((byte)6, "", this.getCurrentCatalog(), this.connection.getId(), this.getId(), 0, System.currentTimeMillis(), elapsedTime, this.session.getQueryTimingUnits(), (String)null, LogUtils.findCallingClassAndMethod(new Throwable()), mesgBuf.toString()));
                  }

                  if (gatherPerformanceMetrics) {
                     this.connection.registerQueryExecutionTime(elapsedTime);
                  }
               }

               this.connection.incrementNumberOfPreparedExecutes();
               if (this.profileSQL) {
                  this.eventSink = ProfilerEventHandlerFactory.getInstance(this.session);
                  this.eventSink.consumeEvent(new ProfilerEventImpl((byte)4, "", this.getCurrentCatalog(), this.connectionId, this.statementId, -1, System.currentTimeMillis(), this.session.getCurrentTimeNanosOrMillis() - begin, this.session.getQueryTimingUnits(), (String)null, LogUtils.findCallingClassAndMethod(new Throwable()), this.truncateQueryToLog(queryAsString)));
               }

               rs = (ResultSetInternalMethods)this.session.getProtocol().readAllResults(maxRowsToRetrieve, createStreamingResultSet, resultPacket, true, metadata, this.resultSetFactory);
               if (this.session.shouldIntercept()) {
                  interceptedResults = (ResultSetInternalMethods)this.session.invokeStatementInterceptorsPost(this.originalSql, this, rs, true, (Exception)null);
                  if (interceptedResults != null) {
                     rs = interceptedResults;
                  }
               }

               if (this.profileSQL) {
                  long fetchEndTime = this.session.getCurrentTimeNanosOrMillis();
                  this.eventSink.consumeEvent(new ProfilerEventImpl((byte)5, "", this.getCurrentCatalog(), this.connection.getId(), this.getId(), 0, System.currentTimeMillis(), fetchEndTime - queryEndTime, this.session.getQueryTimingUnits(), (String)null, LogUtils.findCallingClassAndMethod(new Throwable()), (String)null));
               }

               if (queryWasSlow && (Boolean)this.explainSlowQueries.getValue()) {
                  this.session.explainSlowQuery(StringUtils.getBytes(queryAsString), queryAsString);
               }

               this.sendTypesToServer = false;
               this.results = rs;
               if (this.session.hadWarnings()) {
                  this.session.getProtocol().scanForAndThrowDataTruncation();
               }

               interceptedResults = rs;
            } catch (IOException var32) {
               throw SQLError.createCommunicationsException(this.connection, this.session.getProtocol().getPacketSentTimeHolder().getLastPacketSentTime(), this.session.getProtocol().getPacketReceivedTimeHolder().getLastPacketReceivedTime(), var32, this.getExceptionInterceptor());
            } catch (CJException | SQLException var33) {
               if (this.session.shouldIntercept()) {
                  this.session.invokeStatementInterceptorsPost(this.originalSql, this, (Resultset)null, true, var33);
               }

               throw var33;
            } finally {
               this.statementExecuting.set(false);
               if (timeoutTask != null) {
                  timeoutTask.cancel();
                  this.connection.getCancelTimer().purge();
               }

            }

            return interceptedResults;
         }
      } catch (CJException var36) {
         throw SQLExceptionsMapping.translateException(var36, this.getExceptionInterceptor());
      }
   }

   private void serverLongData(int parameterIndex, ServerPreparedStatement.BindValue longData) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            PacketPayload packet = this.session.getSharedSendPacket();
            Object value = longData.value;
            if (value instanceof byte[]) {
               packet.writeInteger(NativeProtocol.IntegerDataType.INT1, 24L);
               packet.writeInteger(NativeProtocol.IntegerDataType.INT4, this.serverStatementId);
               packet.writeInteger(NativeProtocol.IntegerDataType.INT2, (long)parameterIndex);
               packet.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, (byte[])((byte[])longData.value));
               this.session.sendCommand(24, (String)null, packet, true, (String)null, 0);
            } else if (value instanceof InputStream) {
               this.storeStream(parameterIndex, packet, (InputStream)value);
            } else if (value instanceof java.sql.Blob) {
               this.storeStream(parameterIndex, packet, ((java.sql.Blob)value).getBinaryStream());
            } else {
               if (!(value instanceof Reader)) {
                  throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.18") + value.getClass().getName() + "'", "S1009", this.getExceptionInterceptor());
               }

               this.storeReader(parameterIndex, packet, (Reader)value);
            }

         }
      } catch (CJException var9) {
         throw SQLExceptionsMapping.translateException(var9, this.getExceptionInterceptor());
      }
   }

   private void serverPrepare(String sql) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if ((Boolean)this.autoGenerateTestcaseScript.getValue()) {
               this.dumpPrepareForTestcase();
            }

            try {
               long begin = 0L;
               if (StringUtils.startsWithIgnoreCaseAndWs(sql, "LOAD DATA")) {
                  this.isLoadDataQuery = true;
               } else {
                  this.isLoadDataQuery = false;
               }

               if (this.profileSQL) {
                  begin = System.currentTimeMillis();
               }

               String characterEncoding = null;
               String connectionEncoding = (String)this.session.getPropertySet().getStringReadableProperty("characterEncoding").getValue();
               if (!this.isLoadDataQuery && connectionEncoding != null) {
                  characterEncoding = connectionEncoding;
               }

               PacketPayload prepareResultPacket = this.session.sendCommand(22, sql, (PacketPayload)null, false, characterEncoding, 0);
               prepareResultPacket.setPosition(1);
               this.serverStatementId = prepareResultPacket.readInteger(NativeProtocol.IntegerDataType.INT4);
               this.fieldCount = (int)prepareResultPacket.readInteger(NativeProtocol.IntegerDataType.INT2);
               this.parameterCount = (int)prepareResultPacket.readInteger(NativeProtocol.IntegerDataType.INT2);
               this.parameterBindings = new ServerPreparedStatement.BindValue[this.parameterCount];

               for(int i = 0; i < this.parameterCount; ++i) {
                  this.parameterBindings[i] = new ServerPreparedStatement.BindValue();
               }

               this.connection.incrementNumberOfPrepares();
               if (this.profileSQL) {
                  this.eventSink.consumeEvent(new ProfilerEventImpl((byte)2, "", this.getCurrentCatalog(), this.connectionId, this.statementId, -1, System.currentTimeMillis(), this.session.getCurrentTimeNanosOrMillis() - begin, this.session.getQueryTimingUnits(), (String)null, LogUtils.findCallingClassAndMethod(new Throwable()), this.truncateQueryToLog(sql)));
               }

               boolean checkEOF = !this.session.getServerSession().isEOFDeprecated();
               if (this.parameterCount > 0) {
                  if (checkEOF) {
                     this.session.readPacket();
                  }

                  this.parameterFields = ((ColumnDefinition)this.session.getProtocol().read(ColumnDefinition.class, new ColumnDefinitionFactory((long)this.parameterCount, (ColumnDefinition)null))).getFields();
               }

               if (this.fieldCount > 0) {
                  this.resultFields = ((ColumnDefinition)this.session.getProtocol().read(ColumnDefinition.class, new ColumnDefinitionFactory((long)this.fieldCount, (ColumnDefinition)null))).getFields();
               }
            } catch (IOException var17) {
               throw SQLError.createCommunicationsException(this.session.getProtocol().getConnection(), this.session.getProtocol().getPacketSentTimeHolder().getLastPacketSentTime(), this.session.getProtocol().getPacketReceivedTimeHolder().getLastPacketReceivedTime(), var17, this.session.getExceptionInterceptor());
            } catch (CJException | SQLException var18) {
               SQLException ex = var18 instanceof SQLException ? (SQLException)var18 : SQLExceptionsMapping.translateException(var18);
               if ((Boolean)this.dumpQueriesOnException.getValue()) {
                  StringBuilder messageBuf = new StringBuilder(this.originalSql.length() + 32);
                  messageBuf.append("\n\nQuery being prepared when exception was thrown:\n\n");
                  messageBuf.append(this.originalSql);
                  ex = ConnectionImpl.appendMessageToException(ex, messageBuf.toString(), this.getExceptionInterceptor());
               }

               throw ex;
            } finally {
               this.session.clearInputStream();
            }

         }
      } catch (CJException var21) {
         throw SQLExceptionsMapping.translateException(var21, this.getExceptionInterceptor());
      }
   }

   private String truncateQueryToLog(String sql) throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         String query = null;
         int maxQuerySizeToLog = (Integer)this.session.getPropertySet().getIntegerReadableProperty("maxQuerySizeToLog").getValue();
         if (sql.length() > maxQuerySizeToLog) {
            StringBuilder queryBuf = new StringBuilder(maxQuerySizeToLog + 12);
            queryBuf.append(sql.substring(0, maxQuerySizeToLog));
            queryBuf.append(Messages.getString("MysqlIO.25"));
            query = queryBuf.toString();
         } else {
            query = sql;
         }

         return query;
      }
   }

   private void serverResetStatement() {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            PacketPayload packet = this.session.getSharedSendPacket();
            packet.writeInteger(NativeProtocol.IntegerDataType.INT1, 26L);
            packet.writeInteger(NativeProtocol.IntegerDataType.INT4, this.serverStatementId);

            try {
               this.session.sendCommand(26, (String)null, packet, false, (String)null, 0);
            } finally {
               this.session.clearInputStream();
            }

         }
      } catch (CJException var11) {
         throw SQLExceptionsMapping.translateException(var11, this.getExceptionInterceptor());
      }
   }

   public void setArray(int i, Array x) throws SQLException {
      try {
         throw SQLError.createSQLFeatureNotSupportedException();
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (x == null) {
               this.setNull(parameterIndex, MysqlType.BINARY);
            } else {
               ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, true);
               this.resetToType(binding, 252);
               binding.value = x;
               binding.isLongData = true;
               if ((Boolean)this.useStreamLengthsInPrepStmts.getValue()) {
                  binding.bindLength = (long)length;
               } else {
                  binding.bindLength = -1L;
               }
            }

         }
      } catch (CJException var9) {
         throw SQLExceptionsMapping.translateException(var9, this.getExceptionInterceptor());
      }
   }

   public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (x == null) {
               this.setNull(parameterIndex, MysqlType.DECIMAL);
            } else {
               ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, false);
               this.resetToType(binding, 246);
               binding.value = StringUtils.fixDecimalExponent(x.toPlainString());
            }

         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (x == null) {
               this.setNull(parameterIndex, MysqlType.BINARY);
            } else {
               ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, true);
               this.resetToType(binding, 252);
               binding.value = x;
               binding.isLongData = true;
               if ((Boolean)this.useStreamLengthsInPrepStmts.getValue()) {
                  binding.bindLength = (long)length;
               } else {
                  binding.bindLength = -1L;
               }
            }

         }
      } catch (CJException var9) {
         throw SQLExceptionsMapping.translateException(var9, this.getExceptionInterceptor());
      }
   }

   public void setBlob(int parameterIndex, java.sql.Blob x) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (x == null) {
               this.setNull(parameterIndex, MysqlType.BINARY);
            } else {
               ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, true);
               this.resetToType(binding, 252);
               binding.value = x;
               binding.isLongData = true;
               if ((Boolean)this.useStreamLengthsInPrepStmts.getValue()) {
                  binding.bindLength = x.length();
               } else {
                  binding.bindLength = -1L;
               }
            }

         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   public void setBoolean(int parameterIndex, boolean x) throws SQLException {
      try {
         this.setByte(parameterIndex, (byte)(x ? 1 : 0));
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setByte(int parameterIndex, byte x) throws SQLException {
      try {
         this.checkClosed();
         ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, false);
         this.resetToType(binding, 1);
         binding.longBinding = (long)x;
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void setBytes(int parameterIndex, byte[] x) throws SQLException {
      try {
         this.checkClosed();
         if (x == null) {
            this.setNull(parameterIndex, MysqlType.BINARY);
         } else {
            ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, false);
            this.resetToType(binding, 253);
            binding.value = x;
         }

      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (reader == null) {
               this.setNull(parameterIndex, MysqlType.BINARY);
            } else {
               ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, true);
               this.resetToType(binding, 252);
               binding.value = reader;
               binding.isLongData = true;
               if ((Boolean)this.useStreamLengthsInPrepStmts.getValue()) {
                  binding.bindLength = (long)length;
               } else {
                  binding.bindLength = -1L;
               }
            }

         }
      } catch (CJException var9) {
         throw SQLExceptionsMapping.translateException(var9, this.getExceptionInterceptor());
      }
   }

   public void setClob(int parameterIndex, java.sql.Clob x) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (x == null) {
               this.setNull(parameterIndex, MysqlType.BINARY);
            } else {
               ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, true);
               this.resetToType(binding, 252);
               binding.value = x.getCharacterStream();
               binding.isLongData = true;
               if ((Boolean)this.useStreamLengthsInPrepStmts.getValue()) {
                  binding.bindLength = x.length();
               } else {
                  binding.bindLength = -1L;
               }
            }

         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   public void setDate(int parameterIndex, Date x) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.setDateInternal(parameterIndex, x, this.session.getDefaultTimeZone());
         }
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.setDateInternal(parameterIndex, x, cal.getTimeZone());
         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   private void setDateInternal(int parameterIndex, Date x, TimeZone tz) throws SQLException {
      if (x == null) {
         this.setNull(parameterIndex, MysqlType.DATE);
      } else {
         ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, false);
         this.resetToType(binding, 10);
         binding.value = x;
         binding.tz = tz;
      }

   }

   public void setDouble(int parameterIndex, double x) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if ((Boolean)this.session.getPropertySet().getBooleanReadableProperty("allowNanAndInf").getValue() || x != Double.POSITIVE_INFINITY && x != Double.NEGATIVE_INFINITY && !Double.isNaN(x)) {
               ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, false);
               this.resetToType(binding, 5);
               binding.doubleBinding = x;
            } else {
               throw SQLError.createSQLException(Messages.getString("PreparedStatement.64", new Object[]{x}), "S1009", this.getExceptionInterceptor());
            }
         }
      } catch (CJException var9) {
         throw SQLExceptionsMapping.translateException(var9, this.getExceptionInterceptor());
      }
   }

   public void setFloat(int parameterIndex, float x) throws SQLException {
      try {
         this.checkClosed();
         ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, false);
         this.resetToType(binding, 4);
         binding.floatBinding = x;
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void setInt(int parameterIndex, int x) throws SQLException {
      try {
         this.checkClosed();
         ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, false);
         this.resetToType(binding, 3);
         binding.longBinding = (long)x;
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void setLong(int parameterIndex, long x) throws SQLException {
      try {
         this.checkClosed();
         ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, false);
         this.resetToType(binding, 8);
         binding.longBinding = x;
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void setNull(int parameterIndex, int sqlType) throws SQLException {
      try {
         this.checkClosed();
         ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, false);
         this.resetToType(binding, 6);
         binding.isNull = true;
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
      try {
         this.checkClosed();
         ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, false);
         this.resetToType(binding, 6);
         binding.isNull = true;
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void setRef(int i, Ref x) throws SQLException {
      try {
         throw SQLError.createSQLFeatureNotSupportedException();
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setShort(int parameterIndex, short x) throws SQLException {
      try {
         this.checkClosed();
         ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, false);
         this.resetToType(binding, 2);
         binding.longBinding = (long)x;
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void setString(int parameterIndex, String x) throws SQLException {
      try {
         this.checkClosed();
         if (x == null) {
            this.setNull(parameterIndex, MysqlType.VARCHAR);
         } else {
            ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, false);
            this.resetToType(binding, 253);
            binding.value = x;
         }

      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void setTime(int parameterIndex, Time x) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.setTimeInternal(parameterIndex, x, this.session.getDefaultTimeZone());
         }
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.setTimeInternal(parameterIndex, x, cal.getTimeZone());
         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   private void setTimeInternal(int parameterIndex, Time x, TimeZone tz) throws SQLException {
      if (x == null) {
         this.setNull(parameterIndex, MysqlType.TIME);
      } else {
         ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, false);
         this.resetToType(binding, 11);
         binding.value = x;
         binding.tz = tz;
      }

   }

   public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.setTimestampInternal(parameterIndex, x, this.session.getDefaultTimeZone());
         }
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.setTimestampInternal(parameterIndex, x, cal.getTimeZone());
         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   private void setTimestampInternal(int parameterIndex, Timestamp x, TimeZone tz) throws SQLException {
      if (x == null) {
         this.setNull(parameterIndex, MysqlType.TIMESTAMP);
      } else {
         ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, false);
         this.resetToType(binding, 12);
         if (!(Boolean)this.sendFractionalSeconds.getValue()) {
            x = TimeUtil.truncateFractionalSeconds(x);
         }

         binding.value = x;
         binding.tz = tz;
      }

   }

   protected void resetToType(ServerPreparedStatement.BindValue oldValue, int bufferType) throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         oldValue.reset();
         if ((bufferType != 6 || oldValue.bufferType == 0) && oldValue.bufferType != bufferType) {
            this.sendTypesToServer = true;
            oldValue.bufferType = bufferType;
         }

         oldValue.isSet = true;
         oldValue.boundBeforeExecutionNum = (long)this.numberOfExecutions;
      }
   }

   /** @deprecated */
   @Deprecated
   public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
      try {
         this.checkClosed();
         throw SQLError.createSQLFeatureNotSupportedException();
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void setURL(int parameterIndex, URL x) throws SQLException {
      try {
         this.checkClosed();
         this.setString(parameterIndex, x.toString());
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   private void storeBinding(PacketPayload packet, ServerPreparedStatement.BindValue bindValue) throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         try {
            Object value = bindValue.value;
            switch(bindValue.bufferType) {
            case 0:
            case 15:
            case 246:
            case 253:
            case 254:
               if (value instanceof byte[]) {
                  packet.writeBytes(NativeProtocol.StringSelfDataType.STRING_LENENC, (byte[])((byte[])value));
                  return;
               } else {
                  if (!this.isLoadDataQuery) {
                     packet.writeBytes(NativeProtocol.StringSelfDataType.STRING_LENENC, StringUtils.getBytes((String)value, this.charEncoding));
                  } else {
                     packet.writeBytes(NativeProtocol.StringSelfDataType.STRING_LENENC, StringUtils.getBytes((String)value));
                  }
                  break;
               }
            case 1:
               packet.writeInteger(NativeProtocol.IntegerDataType.INT1, bindValue.longBinding);
               return;
            case 2:
               packet.writeInteger(NativeProtocol.IntegerDataType.INT2, bindValue.longBinding);
               return;
            case 3:
               packet.writeInteger(NativeProtocol.IntegerDataType.INT4, bindValue.longBinding);
               return;
            case 4:
               packet.writeInteger(NativeProtocol.IntegerDataType.INT4, (long)Float.floatToIntBits(bindValue.floatBinding));
               return;
            case 5:
               packet.writeInteger(NativeProtocol.IntegerDataType.INT8, Double.doubleToLongBits(bindValue.doubleBinding));
               return;
            case 7:
            case 10:
            case 12:
               this.storeDateTime(packet, (java.util.Date)value, bindValue.tz, bindValue.bufferType);
               return;
            case 8:
               packet.writeInteger(NativeProtocol.IntegerDataType.INT8, bindValue.longBinding);
               return;
            case 11:
               this.storeTime(packet, (Time)value, bindValue.tz);
               return;
            default:
               return;
            }
         } catch (CJException | SQLException var6) {
            throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.22") + (String)this.session.getPropertySet().getStringReadableProperty("characterEncoding").getValue() + "'", "S1000", var6, this.getExceptionInterceptor());
         }

      }
   }

   private void storeDateTime(PacketPayload intoBuf, java.util.Date dt, TimeZone tz, int bufferType) throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         Calendar cal = Calendar.getInstance(tz);
         cal.setTime(dt);
         if (dt instanceof Date) {
            cal.set(11, 0);
            cal.set(12, 0);
            cal.set(13, 0);
         }

         byte length = 7;
         if (dt instanceof Timestamp) {
            length = 11;
         }

         intoBuf.ensureCapacity(length);
         intoBuf.writeInteger(NativeProtocol.IntegerDataType.INT1, (long)length);
         int year = cal.get(1);
         int month = cal.get(2) + 1;
         int date = cal.get(5);
         intoBuf.writeInteger(NativeProtocol.IntegerDataType.INT2, (long)year);
         intoBuf.writeInteger(NativeProtocol.IntegerDataType.INT1, (long)month);
         intoBuf.writeInteger(NativeProtocol.IntegerDataType.INT1, (long)date);
         if (dt instanceof Date) {
            intoBuf.writeInteger(NativeProtocol.IntegerDataType.INT1, 0L);
            intoBuf.writeInteger(NativeProtocol.IntegerDataType.INT1, 0L);
            intoBuf.writeInteger(NativeProtocol.IntegerDataType.INT1, 0L);
         } else {
            intoBuf.writeInteger(NativeProtocol.IntegerDataType.INT1, (long)cal.get(11));
            intoBuf.writeInteger(NativeProtocol.IntegerDataType.INT1, (long)cal.get(12));
            intoBuf.writeInteger(NativeProtocol.IntegerDataType.INT1, (long)cal.get(13));
         }

         if (length == 11) {
            intoBuf.writeInteger(NativeProtocol.IntegerDataType.INT4, (long)(((Timestamp)dt).getNanos() / 1000));
         }

      }
   }

   private void storeReader(int parameterIndex, PacketPayload packet, Reader inStream) throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         String forcedEncoding = this.session.getPropertySet().getStringReadableProperty("clobCharacterEncoding").getStringValue();
         String clobEncoding = forcedEncoding == null ? (String)this.session.getPropertySet().getStringReadableProperty("characterEncoding").getValue() : forcedEncoding;
         int maxBytesChar = 2;
         if (clobEncoding != null) {
            if (!clobEncoding.equals("UTF-16")) {
               maxBytesChar = this.session.getMaxBytesPerChar(clobEncoding);
               if (maxBytesChar == 1) {
                  maxBytesChar = 2;
               }
            } else {
               maxBytesChar = 4;
            }
         }

         char[] buf = new char[8192 / maxBytesChar];
         int numRead = false;
         int bytesInPacket = 0;
         int totalBytesRead = 0;
         int bytesReadAtLastSend = 0;
         int packetIsFullAt = (Integer)this.session.getPropertySet().getMemorySizeReadableProperty("blobSendChunkSize").getValue();

         try {
            packet.setPosition(0);
            packet.writeInteger(NativeProtocol.IntegerDataType.INT1, 24L);
            packet.writeInteger(NativeProtocol.IntegerDataType.INT4, this.serverStatementId);
            packet.writeInteger(NativeProtocol.IntegerDataType.INT2, (long)parameterIndex);
            boolean readAny = false;

            int numRead;
            while((numRead = inStream.read(buf)) != -1) {
               readAny = true;
               byte[] valueAsBytes = StringUtils.getBytes((char[])buf, 0, numRead, clobEncoding);
               packet.writeBytes(NativeProtocol.StringSelfDataType.STRING_EOF, valueAsBytes);
               bytesInPacket += valueAsBytes.length;
               totalBytesRead += valueAsBytes.length;
               if (bytesInPacket >= packetIsFullAt) {
                  bytesReadAtLastSend = totalBytesRead;
                  this.session.sendCommand(24, (String)null, packet, true, (String)null, 0);
                  bytesInPacket = 0;
                  packet.setPosition(0);
                  packet.writeInteger(NativeProtocol.IntegerDataType.INT1, 24L);
                  packet.writeInteger(NativeProtocol.IntegerDataType.INT4, this.serverStatementId);
                  packet.writeInteger(NativeProtocol.IntegerDataType.INT2, (long)parameterIndex);
               }
            }

            if (totalBytesRead != bytesReadAtLastSend) {
               this.session.sendCommand(24, (String)null, packet, true, (String)null, 0);
            }

            if (!readAny) {
               this.session.sendCommand(24, (String)null, packet, true, (String)null, 0);
            }
         } catch (IOException var25) {
            SQLException sqlEx = SQLError.createSQLException(Messages.getString("ServerPreparedStatement.24") + var25.toString(), "S1000", this.getExceptionInterceptor());
            sqlEx.initCause(var25);
            throw sqlEx;
         } finally {
            if ((Boolean)this.autoClosePStmtStreams.getValue() && inStream != null) {
               try {
                  inStream.close();
               } catch (IOException var24) {
               }
            }

         }

      }
   }

   private void storeStream(int parameterIndex, PacketPayload packet, InputStream inStream) throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         byte[] buf = new byte[8192];
         boolean var6 = false;

         try {
            int bytesInPacket = 0;
            int totalBytesRead = 0;
            int bytesReadAtLastSend = 0;
            int packetIsFullAt = (Integer)this.session.getPropertySet().getMemorySizeReadableProperty("blobSendChunkSize").getValue();
            packet.setPosition(0);
            packet.writeInteger(NativeProtocol.IntegerDataType.INT1, 24L);
            packet.writeInteger(NativeProtocol.IntegerDataType.INT4, this.serverStatementId);
            packet.writeInteger(NativeProtocol.IntegerDataType.INT2, (long)parameterIndex);
            boolean readAny = false;

            int numRead;
            while((numRead = inStream.read(buf)) != -1) {
               readAny = true;
               packet.writeBytes((NativeProtocol.StringLengthDataType)NativeProtocol.StringLengthDataType.STRING_FIXED, buf, 0, numRead);
               bytesInPacket += numRead;
               totalBytesRead += numRead;
               if (bytesInPacket >= packetIsFullAt) {
                  bytesReadAtLastSend = totalBytesRead;
                  this.session.sendCommand(24, (String)null, packet, true, (String)null, 0);
                  bytesInPacket = 0;
                  packet.setPosition(0);
                  packet.writeInteger(NativeProtocol.IntegerDataType.INT1, 24L);
                  packet.writeInteger(NativeProtocol.IntegerDataType.INT4, this.serverStatementId);
                  packet.writeInteger(NativeProtocol.IntegerDataType.INT2, (long)parameterIndex);
               }
            }

            if (totalBytesRead != bytesReadAtLastSend) {
               this.session.sendCommand(24, (String)null, packet, true, (String)null, 0);
            }

            if (!readAny) {
               this.session.sendCommand(24, (String)null, packet, true, (String)null, 0);
            }
         } catch (IOException var21) {
            SQLException sqlEx = SQLError.createSQLException(Messages.getString("ServerPreparedStatement.25") + var21.toString(), "S1000", this.getExceptionInterceptor());
            sqlEx.initCause(var21);
            throw sqlEx;
         } finally {
            if ((Boolean)this.autoClosePStmtStreams.getValue() && inStream != null) {
               try {
                  inStream.close();
               } catch (IOException var20) {
               }
            }

         }

      }
   }

   public String toString() {
      StringBuilder toStringBuf = new StringBuilder();
      toStringBuf.append("com.mysql.cj.jdbc.ServerPreparedStatement[");
      toStringBuf.append(this.serverStatementId);
      toStringBuf.append("] - ");

      try {
         toStringBuf.append(this.asSql());
      } catch (SQLException var3) {
         toStringBuf.append(Messages.getString("ServerPreparedStatement.6"));
         toStringBuf.append(var3);
      }

      return toStringBuf.toString();
   }

   public long getServerStatementId() {
      return this.serverStatementId;
   }

   public boolean canRewriteAsMultiValueInsertAtSqlLevel() throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         if (!this.hasCheckedRewrite) {
            this.hasCheckedRewrite = true;
            this.canRewrite = canRewrite(this.originalSql, this.isOnDuplicateKeyUpdate(), this.getLocationOfOnDuplicateKeyUpdate(), 0);
            this.parseInfo = new PreparedStatement.ParseInfo(this.originalSql, this.connection, this.connection.getMetaData(), this.charEncoding);
         }

         return this.canRewrite;
      }
   }

   public boolean canRewriteAsMultivalueInsertStatement() throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         if (!this.canRewriteAsMultiValueInsertAtSqlLevel()) {
            return false;
         } else {
            ServerPreparedStatement.BindValue[] currentBindValues = null;
            ServerPreparedStatement.BindValue[] previousBindValues = null;
            int nbrCommands = this.batchedArgs.size();

            for(int commandIndex = 0; commandIndex < nbrCommands; ++commandIndex) {
               Object arg = this.batchedArgs.get(commandIndex);
               if (!(arg instanceof String)) {
                  currentBindValues = ((ServerPreparedStatement.BatchedBindValues)arg).batchedParameterValues;
                  if (previousBindValues != null) {
                     for(int j = 0; j < this.parameterBindings.length; ++j) {
                        if (currentBindValues[j].bufferType != ((ServerPreparedStatement.BindValue)((Object[])previousBindValues)[j]).bufferType) {
                           return false;
                        }
                     }
                  }
               }
            }

            return true;
         }
      }
   }

   protected int getLocationOfOnDuplicateKeyUpdate() throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         if (this.locationOfOnDuplicateKeyUpdate == -2) {
            this.locationOfOnDuplicateKeyUpdate = getOnDuplicateKeyLocation(this.originalSql, this.dontCheckOnDuplicateKeyUpdateInSQL, (Boolean)this.rewriteBatchedStatements.getValue(), this.connection.isNoBackslashEscapesSet());
         }

         return this.locationOfOnDuplicateKeyUpdate;
      }
   }

   protected boolean isOnDuplicateKeyUpdate() throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         return this.getLocationOfOnDuplicateKeyUpdate() != -1;
      }
   }

   protected long[] computeMaxParameterSetSizeAndBatchSize(int numBatchedArgs) throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         long sizeOfEntireBatch = 10L;
         long maxSizeOfParameterSet = 0L;

         for(int i = 0; i < numBatchedArgs; ++i) {
            ServerPreparedStatement.BindValue[] paramArg = ((ServerPreparedStatement.BatchedBindValues)this.batchedArgs.get(i)).batchedParameterValues;
            long sizeOfParameterSet = 0L;
            sizeOfParameterSet += (long)((this.parameterCount + 7) / 8);
            sizeOfParameterSet += (long)(this.parameterCount * 2);

            for(int j = 0; j < this.parameterBindings.length; ++j) {
               if (!paramArg[j].isNull) {
                  long size = paramArg[j].getBoundLength();
                  if (paramArg[j].isLongData) {
                     if (size != -1L) {
                        sizeOfParameterSet += size;
                     }
                  } else {
                     sizeOfParameterSet += size;
                  }
               }
            }

            sizeOfEntireBatch += sizeOfParameterSet;
            if (sizeOfParameterSet > maxSizeOfParameterSet) {
               maxSizeOfParameterSet = sizeOfParameterSet;
            }
         }

         return new long[]{maxSizeOfParameterSet, sizeOfEntireBatch};
      }
   }

   protected int setOneBatchedParameterSet(java.sql.PreparedStatement batchedStatement, int batchedParamIndex, Object paramSet) throws SQLException {
      ServerPreparedStatement.BindValue[] paramArg = ((ServerPreparedStatement.BatchedBindValues)paramSet).batchedParameterValues;

      for(int j = 0; j < paramArg.length; ++j) {
         if (paramArg[j].isNull) {
            batchedStatement.setNull(batchedParamIndex++, MysqlType.NULL.getJdbcType());
         } else {
            Object value;
            if (paramArg[j].isLongData) {
               value = paramArg[j].value;
               if (value instanceof InputStream) {
                  batchedStatement.setBinaryStream(batchedParamIndex++, (InputStream)value, (int)paramArg[j].bindLength);
               } else {
                  batchedStatement.setCharacterStream(batchedParamIndex++, (Reader)value, (int)paramArg[j].bindLength);
               }
            } else {
               switch(paramArg[j].bufferType) {
               case 0:
               case 15:
               case 246:
               case 253:
               case 254:
                  value = paramArg[j].value;
                  if (value instanceof byte[]) {
                     batchedStatement.setBytes(batchedParamIndex, (byte[])((byte[])value));
                  } else {
                     batchedStatement.setString(batchedParamIndex, (String)value);
                  }

                  if (batchedStatement instanceof ServerPreparedStatement) {
                     ServerPreparedStatement.BindValue asBound = ((ServerPreparedStatement)batchedStatement).getBinding(batchedParamIndex, false);
                     asBound.bufferType = paramArg[j].bufferType;
                  }

                  ++batchedParamIndex;
                  break;
               case 1:
                  batchedStatement.setByte(batchedParamIndex++, (byte)((int)paramArg[j].longBinding));
                  break;
               case 2:
                  batchedStatement.setShort(batchedParamIndex++, (short)((int)paramArg[j].longBinding));
                  break;
               case 3:
                  batchedStatement.setInt(batchedParamIndex++, (int)paramArg[j].longBinding);
                  break;
               case 4:
                  batchedStatement.setFloat(batchedParamIndex++, paramArg[j].floatBinding);
                  break;
               case 5:
                  batchedStatement.setDouble(batchedParamIndex++, paramArg[j].doubleBinding);
                  break;
               case 7:
               case 12:
                  batchedStatement.setTimestamp(batchedParamIndex++, (Timestamp)paramArg[j].value);
                  break;
               case 8:
                  batchedStatement.setLong(batchedParamIndex++, paramArg[j].longBinding);
                  break;
               case 10:
                  batchedStatement.setDate(batchedParamIndex++, (Date)paramArg[j].value);
                  break;
               case 11:
                  batchedStatement.setTime(batchedParamIndex++, (Time)paramArg[j].value);
                  break;
               default:
                  throw new IllegalArgumentException(Messages.getString("ServerPreparedStatement.26", new Object[]{batchedParamIndex}));
               }
            }
         }
      }

      return batchedParamIndex;
   }

   protected boolean containsOnDuplicateKeyUpdateInSQL() {
      return this.hasOnDuplicateKeyUpdate;
   }

   protected PreparedStatement prepareBatchedInsertSQL(JdbcConnection localConn, int numBatches) throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         PreparedStatement var10000;
         try {
            PreparedStatement pstmt = (PreparedStatement)localConn.prepareStatement(this.parseInfo.getSqlForBatch(numBatches), this.resultSetConcurrency, this.resultSetType).unwrap(PreparedStatement.class);
            pstmt.setRetrieveGeneratedKeys(this.retrieveGeneratedKeys);
            var10000 = pstmt;
         } catch (UnsupportedEncodingException var7) {
            SQLException sqlEx = SQLError.createSQLException(Messages.getString("ServerPreparedStatement.27"), "S1000", this.getExceptionInterceptor());
            sqlEx.initCause(var7);
            throw sqlEx;
         }

         return var10000;
      }
   }

   public void setNCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
      try {
         if (!this.charEncoding.equalsIgnoreCase("UTF-8") && !this.charEncoding.equalsIgnoreCase("utf8")) {
            throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.28"), this.getExceptionInterceptor());
         } else {
            this.checkClosed();
            if (reader == null) {
               this.setNull(parameterIndex, MysqlType.BINARY);
            } else {
               ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, true);
               this.resetToType(binding, 252);
               binding.value = reader;
               binding.isLongData = true;
               if ((Boolean)this.useStreamLengthsInPrepStmts.getValue()) {
                  binding.bindLength = length;
               } else {
                  binding.bindLength = -1L;
               }
            }

         }
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   public void setNClob(int parameterIndex, java.sql.NClob x) throws SQLException {
      try {
         this.setNClob(parameterIndex, x.getCharacterStream(), (Boolean)this.useStreamLengthsInPrepStmts.getValue() ? x.length() : -1L);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
      try {
         if (!this.charEncoding.equalsIgnoreCase("UTF-8") && !this.charEncoding.equalsIgnoreCase("utf8")) {
            throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.29"), this.getExceptionInterceptor());
         } else {
            this.checkClosed();
            if (reader == null) {
               this.setNull(parameterIndex, MysqlType.TEXT);
            } else {
               ServerPreparedStatement.BindValue binding = this.getBinding(parameterIndex, true);
               this.resetToType(binding, 252);
               binding.value = reader;
               binding.isLongData = true;
               if ((Boolean)this.useStreamLengthsInPrepStmts.getValue()) {
                  binding.bindLength = length;
               } else {
                  binding.bindLength = -1L;
               }
            }

         }
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   public void setNString(int parameterIndex, String x) throws SQLException {
      try {
         if (!this.charEncoding.equalsIgnoreCase("UTF-8") && !this.charEncoding.equalsIgnoreCase("utf8")) {
            throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.30"), this.getExceptionInterceptor());
         } else {
            this.setString(parameterIndex, x);
         }
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
      try {
         super.setSQLXML(parameterIndex, xmlObject);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setPoolable(boolean poolable) throws SQLException {
      try {
         if (!poolable) {
            this.connection.decachePreparedStatement(this);
         }

         super.setPoolable(poolable);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public static class BindValue {
      public long boundBeforeExecutionNum = 0L;
      public long bindLength;
      public int bufferType;
      public double doubleBinding;
      public float floatBinding;
      public boolean isLongData;
      public boolean isNull;
      public boolean isSet = false;
      public long longBinding;
      public Object value;
      public TimeZone tz;

      BindValue() {
      }

      BindValue(ServerPreparedStatement.BindValue copyMe) {
         this.value = copyMe.value;
         this.isSet = copyMe.isSet;
         this.isLongData = copyMe.isLongData;
         this.isNull = copyMe.isNull;
         this.bufferType = copyMe.bufferType;
         this.bindLength = copyMe.bindLength;
         this.longBinding = copyMe.longBinding;
         this.floatBinding = copyMe.floatBinding;
         this.doubleBinding = copyMe.doubleBinding;
         this.tz = copyMe.tz;
      }

      void reset() {
         this.isNull = false;
         this.isSet = false;
         this.value = null;
         this.isLongData = false;
         this.longBinding = 0L;
         this.floatBinding = 0.0F;
         this.doubleBinding = 0.0D;
         this.tz = null;
      }

      public String toString() {
         return this.toString(false);
      }

      public String toString(boolean quoteIfNeeded) {
         if (this.isLongData) {
            return "' STREAM DATA '";
         } else if (this.isNull) {
            return "NULL";
         } else {
            switch(this.bufferType) {
            case 1:
            case 2:
            case 3:
            case 8:
               return String.valueOf(this.longBinding);
            case 4:
               return String.valueOf(this.floatBinding);
            case 5:
               return String.valueOf(this.doubleBinding);
            case 7:
            case 10:
            case 11:
            case 12:
            case 15:
            case 253:
            case 254:
               if (quoteIfNeeded) {
                  return "'" + String.valueOf(this.value) + "'";
               }

               return String.valueOf(this.value);
            default:
               if (this.value instanceof byte[]) {
                  return "byte data";
               } else {
                  return quoteIfNeeded ? "'" + String.valueOf(this.value) + "'" : String.valueOf(this.value);
               }
            }
         }
      }

      long getBoundLength() {
         if (this.isNull) {
            return 0L;
         } else if (this.isLongData) {
            return this.bindLength;
         } else {
            switch(this.bufferType) {
            case 0:
            case 15:
            case 246:
            case 253:
            case 254:
               if (this.value instanceof byte[]) {
                  return (long)((byte[])((byte[])this.value)).length;
               }

               return (long)((String)this.value).length();
            case 1:
               return 1L;
            case 2:
               return 2L;
            case 3:
               return 4L;
            case 4:
               return 4L;
            case 5:
               return 8L;
            case 7:
            case 12:
               return 11L;
            case 8:
               return 8L;
            case 10:
               return 7L;
            case 11:
               return 9L;
            default:
               return 0L;
            }
         }
      }
   }

   public static class BatchedBindValues {
      public ServerPreparedStatement.BindValue[] batchedParameterValues;

      BatchedBindValues(ServerPreparedStatement.BindValue[] paramVals) {
         int numParams = paramVals.length;
         this.batchedParameterValues = new ServerPreparedStatement.BindValue[numParams];

         for(int i = 0; i < numParams; ++i) {
            this.batchedParameterValues[i] = new ServerPreparedStatement.BindValue(paramVals[i]);
         }

      }
   }
}
