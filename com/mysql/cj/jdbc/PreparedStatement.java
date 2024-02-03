package com.mysql.cj.jdbc;

import com.mysql.cj.api.conf.ReadableProperty;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.api.jdbc.ParameterBindings;
import com.mysql.cj.api.jdbc.result.ResultSetInternalMethods;
import com.mysql.cj.api.mysqla.io.NativeProtocol;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.api.result.Row;
import com.mysql.cj.core.CharsetMapping;
import com.mysql.cj.core.Constants;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.MysqlType;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.core.exceptions.FeatureNotAvailableException;
import com.mysql.cj.core.exceptions.StatementIsClosedException;
import com.mysql.cj.core.profiler.ProfilerEventImpl;
import com.mysql.cj.core.result.Field;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.core.util.Util;
import com.mysql.cj.jdbc.exceptions.MySQLStatementCancelledException;
import com.mysql.cj.jdbc.exceptions.MySQLTimeoutException;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.jdbc.result.CachedResultSetMetaData;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import com.mysql.cj.jdbc.util.TimeUtil;
import com.mysql.cj.mysqla.result.ByteArrayRow;
import com.mysql.cj.mysqla.result.MysqlaColumnDefinition;
import com.mysql.cj.mysqla.result.ResultsetRowsStatic;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.sql.Array;
import java.sql.JDBCType;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class PreparedStatement extends StatementImpl implements java.sql.PreparedStatement {
   private static final byte[] HEX_DIGITS = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};
   protected boolean batchHasPlainStatements;
   private java.sql.DatabaseMetaData dbmd;
   protected char firstCharOfStmt;
   protected boolean isLoadDataQuery;
   protected boolean[] isNull;
   private boolean[] isStream;
   protected int numberOfExecutions;
   protected String originalSql;
   protected int parameterCount;
   protected MysqlParameterMetadata parameterMetaData;
   private InputStream[] parameterStreams;
   private byte[][] parameterValues;
   protected MysqlType[] parameterTypes;
   protected PreparedStatement.ParseInfo parseInfo;
   private ResultSetMetaData pstmtResultMetaData;
   private byte[][] staticSqlStrings;
   private byte[] streamConvertBuf;
   private int[] streamLengths;
   private SimpleDateFormat tsdf;
   private SimpleDateFormat ddf;
   private SimpleDateFormat tdf;
   protected boolean usingAnsiMode;
   protected String batchedValuesClause;
   private boolean doPingInstead;
   private boolean compensateForOnDuplicateKeyUpdate;
   private CharsetEncoder charsetEncoder;
   protected int batchCommandIndex;
   protected ReadableProperty<Boolean> useStreamLengthsInPrepStmts;
   protected ReadableProperty<Boolean> autoClosePStmtStreams;
   protected ReadableProperty<Boolean> treatUtilDateAsTimestamp;
   protected int rewrittenBatchSize;

   protected static int readFully(Reader reader, char[] buf, int length) throws IOException {
      int numCharsRead;
      int count;
      for(numCharsRead = 0; numCharsRead < length; numCharsRead += count) {
         count = reader.read(buf, numCharsRead, length - numCharsRead);
         if (count < 0) {
            break;
         }
      }

      return numCharsRead;
   }

   protected static PreparedStatement getInstance(JdbcConnection conn, String catalog) throws SQLException {
      return new PreparedStatement(conn, catalog);
   }

   protected static PreparedStatement getInstance(JdbcConnection conn, String sql, String catalog) throws SQLException {
      return new PreparedStatement(conn, sql, catalog);
   }

   protected static PreparedStatement getInstance(JdbcConnection conn, String sql, String catalog, PreparedStatement.ParseInfo cachedParseInfo) throws SQLException {
      return new PreparedStatement(conn, sql, catalog, cachedParseInfo);
   }

   public PreparedStatement(JdbcConnection conn, String catalog) throws SQLException {
      super(conn, catalog);
      this.batchHasPlainStatements = false;
      this.dbmd = null;
      this.firstCharOfStmt = 0;
      this.isLoadDataQuery = false;
      this.isNull = null;
      this.isStream = null;
      this.numberOfExecutions = 0;
      this.originalSql = null;
      this.parameterStreams = null;
      this.parameterValues = (byte[][])null;
      this.parameterTypes = null;
      this.staticSqlStrings = (byte[][])null;
      this.streamConvertBuf = null;
      this.streamLengths = null;
      this.tsdf = null;
      this.compensateForOnDuplicateKeyUpdate = false;
      this.batchCommandIndex = -1;
      this.rewrittenBatchSize = 0;
      this.compensateForOnDuplicateKeyUpdate = (Boolean)this.session.getPropertySet().getBooleanReadableProperty("compensateOnDuplicateKeyUpdateCounts").getValue();
      this.useStreamLengthsInPrepStmts = this.session.getPropertySet().getBooleanReadableProperty("useStreamLengthsInPrepStmts");
      this.autoClosePStmtStreams = this.session.getPropertySet().getBooleanReadableProperty("autoClosePStmtStreams");
      this.treatUtilDateAsTimestamp = this.session.getPropertySet().getBooleanReadableProperty("treatUtilDateAsTimestamp");
   }

   public PreparedStatement(JdbcConnection conn, String sql, String catalog) throws SQLException {
      this(conn, catalog);
      if (sql == null) {
         throw SQLError.createSQLException(Messages.getString("PreparedStatement.0"), "S1009", this.getExceptionInterceptor());
      } else {
         this.originalSql = sql;
         if (this.originalSql.startsWith("/* ping */")) {
            this.doPingInstead = true;
         } else {
            this.doPingInstead = false;
         }

         this.dbmd = this.connection.getMetaData();
         this.parseInfo = new PreparedStatement.ParseInfo(sql, this.connection, this.dbmd, this.charEncoding);
         this.initializeFromParseInfo();
         if (conn.getRequiresEscapingEncoder()) {
            this.charsetEncoder = Charset.forName((String)conn.getPropertySet().getStringReadableProperty("characterEncoding").getValue()).newEncoder();
         }

      }
   }

   public PreparedStatement(JdbcConnection conn, String sql, String catalog, PreparedStatement.ParseInfo cachedParseInfo) throws SQLException {
      this(conn, catalog);
      if (sql == null) {
         throw SQLError.createSQLException(Messages.getString("PreparedStatement.1"), "S1009", this.getExceptionInterceptor());
      } else {
         this.originalSql = sql;
         this.dbmd = this.connection.getMetaData();
         this.parseInfo = cachedParseInfo;
         this.usingAnsiMode = !this.connection.useAnsiQuotedIdentifiers();
         this.initializeFromParseInfo();
         if (conn.getRequiresEscapingEncoder()) {
            this.charsetEncoder = Charset.forName((String)conn.getPropertySet().getStringReadableProperty("characterEncoding").getValue()).newEncoder();
         }

      }
   }

   public void addBatch() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (this.batchedArgs == null) {
               this.batchedArgs = new ArrayList();
            }

            for(int i = 0; i < this.parameterValues.length; ++i) {
               this.checkAllParametersSet(this.parameterValues[i], this.parameterStreams[i], i);
            }

            this.batchedArgs.add(new PreparedStatement.BatchParams(this.parameterValues, this.parameterStreams, this.isStream, this.streamLengths, this.isNull));
         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void addBatch(String sql) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.batchHasPlainStatements = true;
            super.addBatch(sql);
         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public String asSql() throws SQLException {
      return this.asSql(false);
   }

   public String asSql(boolean quoteStreamsAndUnknowns) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            StringBuilder buf = new StringBuilder();
            int realParameterCount = this.parameterCount + this.getParameterIndexOffset();
            Object batchArg = null;
            if (this.batchCommandIndex != -1) {
               batchArg = this.batchedArgs.get(this.batchCommandIndex);
            }

            for(int i = 0; i < realParameterCount; ++i) {
               if (this.charEncoding != null) {
                  buf.append(StringUtils.toString(this.staticSqlStrings[i], this.charEncoding));
               } else {
                  buf.append(StringUtils.toString(this.staticSqlStrings[i]));
               }

               byte[] val = null;
               if (batchArg != null && batchArg instanceof String) {
                  buf.append((String)batchArg);
               } else {
                  byte[] val;
                  if (this.batchCommandIndex == -1) {
                     val = this.parameterValues[i];
                  } else {
                     val = ((PreparedStatement.BatchParams)batchArg).parameterStrings[i];
                  }

                  boolean isStreamParam = false;
                  if (this.batchCommandIndex == -1) {
                     isStreamParam = this.isStream[i];
                  } else {
                     isStreamParam = ((PreparedStatement.BatchParams)batchArg).isStream[i];
                  }

                  if (val == null && !isStreamParam) {
                     if (quoteStreamsAndUnknowns) {
                        buf.append("'");
                     }

                     buf.append("** NOT SPECIFIED **");
                     if (quoteStreamsAndUnknowns) {
                        buf.append("'");
                     }
                  } else if (isStreamParam) {
                     if (quoteStreamsAndUnknowns) {
                        buf.append("'");
                     }

                     buf.append("** STREAM DATA **");
                     if (quoteStreamsAndUnknowns) {
                        buf.append("'");
                     }
                  } else {
                     buf.append(StringUtils.toString(val, this.charEncoding));
                  }
               }
            }

            if (this.charEncoding != null) {
               buf.append(StringUtils.toString(this.staticSqlStrings[this.parameterCount + this.getParameterIndexOffset()], this.charEncoding));
            } else {
               buf.append(StringUtils.toAsciiString(this.staticSqlStrings[this.parameterCount + this.getParameterIndexOffset()]));
            }

            return buf.toString();
         }
      } catch (CJException var12) {
         throw SQLExceptionsMapping.translateException(var12, this.getExceptionInterceptor());
      }
   }

   public void clearBatch() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.batchHasPlainStatements = false;
            super.clearBatch();
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void clearParameters() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            for(int i = 0; i < this.parameterValues.length; ++i) {
               this.parameterValues[i] = null;
               this.parameterStreams[i] = null;
               this.isStream[i] = false;
               this.isNull[i] = false;
               this.parameterTypes[i] = MysqlType.NULL;
            }

         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   private final void escapeblockFast(byte[] buf, PacketPayload packet, int size) throws SQLException {
      int lastwritten = 0;

      for(int i = 0; i < size; ++i) {
         byte b = buf[i];
         if (b == 0) {
            if (i > lastwritten) {
               packet.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, buf, lastwritten, i - lastwritten);
            }

            packet.writeInteger(NativeProtocol.IntegerDataType.INT1, 92L);
            packet.writeInteger(NativeProtocol.IntegerDataType.INT1, 48L);
            lastwritten = i + 1;
         } else if (b == 92 || b == 39 || !this.usingAnsiMode && b == 34) {
            if (i > lastwritten) {
               packet.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, buf, lastwritten, i - lastwritten);
            }

            packet.writeInteger(NativeProtocol.IntegerDataType.INT1, 92L);
            lastwritten = i;
         }
      }

      if (lastwritten < size) {
         packet.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, buf, lastwritten, size - lastwritten);
      }

   }

   private final void escapeblockFast(byte[] buf, ByteArrayOutputStream bytesOut, int size) {
      int lastwritten = 0;

      for(int i = 0; i < size; ++i) {
         byte b = buf[i];
         if (b == 0) {
            if (i > lastwritten) {
               bytesOut.write(buf, lastwritten, i - lastwritten);
            }

            bytesOut.write(92);
            bytesOut.write(48);
            lastwritten = i + 1;
         } else if (b == 92 || b == 39 || !this.usingAnsiMode && b == 34) {
            if (i > lastwritten) {
               bytesOut.write(buf, lastwritten, i - lastwritten);
            }

            bytesOut.write(92);
            lastwritten = i;
         }
      }

      if (lastwritten < size) {
         bytesOut.write(buf, lastwritten, size - lastwritten);
      }

   }

   protected boolean checkReadOnlySafeStatement() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            return this.firstCharOfStmt == 'S' || !this.connection.isReadOnly();
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public boolean execute() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            JdbcConnection locallyScopedConn = this.connection;
            if (!this.checkReadOnlySafeStatement()) {
               throw SQLError.createSQLException(Messages.getString("PreparedStatement.20") + Messages.getString("PreparedStatement.21"), "S1009", this.getExceptionInterceptor());
            } else {
               ResultSetInternalMethods rs = null;
               CachedResultSetMetaData cachedMetadata = null;
               this.lastQueryIsOnDupKeyUpdate = false;
               if (this.retrieveGeneratedKeys) {
                  this.lastQueryIsOnDupKeyUpdate = this.containsOnDuplicateKeyUpdateInSQL();
               }

               this.clearWarnings();
               this.setupStreamingTimeout(locallyScopedConn);
               this.batchedGeneratedKeys = null;
               PacketPayload sendPacket = this.fillSendPacket();
               String oldCatalog = null;
               if (!locallyScopedConn.getCatalog().equals(this.getCurrentCatalog())) {
                  oldCatalog = locallyScopedConn.getCatalog();
                  locallyScopedConn.setCatalog(this.getCurrentCatalog());
               }

               boolean cacheResultSetMetadata = (Boolean)locallyScopedConn.getPropertySet().getBooleanReadableProperty("cacheResultSetMetadata").getValue();
               if (cacheResultSetMetadata) {
                  cachedMetadata = locallyScopedConn.getCachedMetaData(this.originalSql);
               }

               boolean oldInfoMsgState = false;
               if (this.retrieveGeneratedKeys) {
                  oldInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled();
                  locallyScopedConn.setReadInfoMsgEnabled(true);
               }

               locallyScopedConn.setSessionMaxRows(this.firstCharOfStmt == 'S' ? this.maxRows : -1);
               rs = this.executeInternal(this.maxRows, sendPacket, this.createStreamingResultSet(), this.firstCharOfStmt == 'S', cachedMetadata, false);
               if (cachedMetadata != null) {
                  locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, cachedMetadata, rs);
               } else if (rs.hasRows() && cacheResultSetMetadata) {
                  locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, (CachedResultSetMetaData)null, rs);
               }

               if (this.retrieveGeneratedKeys) {
                  locallyScopedConn.setReadInfoMsgEnabled(oldInfoMsgState);
                  rs.setFirstCharOfQuery(this.firstCharOfStmt);
               }

               if (oldCatalog != null) {
                  locallyScopedConn.setCatalog(oldCatalog);
               }

               if (rs != null) {
                  this.lastInsertId = rs.getUpdateID();
                  this.results = rs;
               }

               return rs != null && rs.hasRows();
            }
         }
      } catch (CJException var12) {
         throw SQLExceptionsMapping.translateException(var12, this.getExceptionInterceptor());
      }
   }

   protected long[] executeBatchInternal() throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         if (this.connection.isReadOnly()) {
            throw new SQLException(Messages.getString("PreparedStatement.25") + Messages.getString("PreparedStatement.26"), "S1009");
         } else if (this.batchedArgs != null && this.batchedArgs.size() != 0) {
            int batchTimeout = this.timeoutInMillis;
            this.timeoutInMillis = 0;
            this.resetCancelledState();

            long[] var3;
            try {
               this.statementBegins();
               this.clearWarnings();
               if (!this.batchHasPlainStatements && (Boolean)this.rewriteBatchedStatements.getValue()) {
                  if (this.canRewriteAsMultiValueInsertAtSqlLevel()) {
                     var3 = this.executeBatchedInserts(batchTimeout);
                     return var3;
                  }

                  if (!this.batchHasPlainStatements && this.batchedArgs != null && this.batchedArgs.size() > 3) {
                     var3 = this.executePreparedBatchAsMultiStatement(batchTimeout);
                     return var3;
                  }
               }

               var3 = this.executeBatchSerially(batchTimeout);
            } finally {
               this.statementExecuting.set(false);
               this.clearBatch();
            }

            return var3;
         } else {
            return new long[0];
         }
      }
   }

   public boolean canRewriteAsMultiValueInsertAtSqlLevel() throws SQLException {
      return this.parseInfo.canRewriteAsMultiValueInsert;
   }

   protected int getLocationOfOnDuplicateKeyUpdate() throws SQLException {
      return this.parseInfo.locationOfOnDuplicateKeyUpdate;
   }

   protected long[] executePreparedBatchAsMultiStatement(int batchTimeout) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (this.batchedValuesClause == null) {
               this.batchedValuesClause = this.originalSql + ";";
            }

            JdbcConnection locallyScopedConn = this.connection;
            boolean multiQueriesEnabled = (Boolean)locallyScopedConn.getPropertySet().getBooleanReadableProperty("allowMultiQueries").getValue();
            StatementImpl.CancelTask timeoutTask = null;

            Object ex;
            try {
               this.clearWarnings();
               int numBatchedArgs = this.batchedArgs.size();
               if (this.retrieveGeneratedKeys) {
                  this.batchedGeneratedKeys = new ArrayList(numBatchedArgs);
               }

               int numValuesPerBatch = this.computeBatchSize(numBatchedArgs);
               if (numBatchedArgs < numValuesPerBatch) {
                  numValuesPerBatch = numBatchedArgs;
               }

               java.sql.PreparedStatement batchedStatement = null;
               int batchedParamIndex = 1;
               int numberToExecuteAsMultiValue = false;
               int batchCounter = 0;
               int updateCountCounter = 0;
               long[] updateCounts = new long[numBatchedArgs];
               SQLException sqlEx = null;

               try {
                  if (!multiQueriesEnabled) {
                     locallyScopedConn.getSession().enableMultiQueries();
                  }

                  if (this.retrieveGeneratedKeys) {
                     batchedStatement = (java.sql.PreparedStatement)locallyScopedConn.prepareStatement(this.generateMultiStatementForBatch(numValuesPerBatch), 1).unwrap(java.sql.PreparedStatement.class);
                  } else {
                     batchedStatement = (java.sql.PreparedStatement)locallyScopedConn.prepareStatement(this.generateMultiStatementForBatch(numValuesPerBatch)).unwrap(java.sql.PreparedStatement.class);
                  }

                  if ((Boolean)locallyScopedConn.getPropertySet().getBooleanReadableProperty("enableQueryTimeouts").getValue() && batchTimeout != 0) {
                     timeoutTask = new StatementImpl.CancelTask((StatementImpl)batchedStatement);
                     locallyScopedConn.getCancelTimer().schedule(timeoutTask, (long)batchTimeout);
                  }

                  int numberToExecuteAsMultiValue;
                  if (numBatchedArgs < numValuesPerBatch) {
                     numberToExecuteAsMultiValue = numBatchedArgs;
                  } else {
                     numberToExecuteAsMultiValue = numBatchedArgs / numValuesPerBatch;
                  }

                  ex = numberToExecuteAsMultiValue * numValuesPerBatch;

                  for(int i = 0; i < ex; ++i) {
                     if (i != 0 && i % numValuesPerBatch == 0) {
                        try {
                           batchedStatement.execute();
                        } catch (SQLException var49) {
                           sqlEx = this.handleExceptionForBatch(batchCounter, numValuesPerBatch, updateCounts, var49);
                        }

                        updateCountCounter = this.processMultiCountsAndKeys((StatementImpl)batchedStatement, updateCountCounter, updateCounts);
                        batchedStatement.clearParameters();
                        batchedParamIndex = 1;
                     }

                     batchedParamIndex = this.setOneBatchedParameterSet(batchedStatement, batchedParamIndex, this.batchedArgs.get(batchCounter++));
                  }

                  try {
                     batchedStatement.execute();
                  } catch (SQLException var48) {
                     sqlEx = this.handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, var48);
                  }

                  updateCountCounter = this.processMultiCountsAndKeys((StatementImpl)batchedStatement, updateCountCounter, updateCounts);
                  batchedStatement.clearParameters();
                  numValuesPerBatch = numBatchedArgs - batchCounter;
               } finally {
                  if (batchedStatement != null) {
                     batchedStatement.close();
                     batchedStatement = null;
                  }

               }

               try {
                  if (numValuesPerBatch > 0) {
                     if (this.retrieveGeneratedKeys) {
                        batchedStatement = locallyScopedConn.prepareStatement(this.generateMultiStatementForBatch(numValuesPerBatch), 1);
                     } else {
                        batchedStatement = locallyScopedConn.prepareStatement(this.generateMultiStatementForBatch(numValuesPerBatch));
                     }

                     if (timeoutTask != null) {
                        timeoutTask.toCancel = (StatementImpl)batchedStatement;
                     }

                     for(batchedParamIndex = 1; batchCounter < numBatchedArgs; batchedParamIndex = this.setOneBatchedParameterSet(batchedStatement, batchedParamIndex, this.batchedArgs.get(batchCounter++))) {
                     }

                     try {
                        batchedStatement.execute();
                     } catch (SQLException var47) {
                        ex = var47;
                        sqlEx = this.handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, var47);
                     }

                     this.processMultiCountsAndKeys((StatementImpl)batchedStatement, updateCountCounter, updateCounts);
                     batchedStatement.clearParameters();
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

                  ex = updateCounts;
               } finally {
                  if (batchedStatement != null) {
                     batchedStatement.close();
                  }

               }
            } finally {
               if (timeoutTask != null) {
                  timeoutTask.cancel();
                  locallyScopedConn.getCancelTimer().purge();
               }

               this.resetCancelledState();
               if (!multiQueriesEnabled) {
                  locallyScopedConn.getSession().disableMultiQueries();
               }

               this.clearBatch();
            }

            return (long[])ex;
         }
      } catch (CJException var54) {
         throw SQLExceptionsMapping.translateException(var54, this.getExceptionInterceptor());
      }
   }

   private String generateMultiStatementForBatch(int numBatches) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            StringBuilder newStatementSql = new StringBuilder((this.originalSql.length() + 1) * numBatches);
            newStatementSql.append(this.originalSql);

            for(int i = 0; i < numBatches - 1; ++i) {
               newStatementSql.append(';');
               newStatementSql.append(this.originalSql);
            }

            return newStatementSql.toString();
         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   protected long[] executeBatchedInserts(int batchTimeout) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            String valuesClause = this.getValuesClause();
            JdbcConnection locallyScopedConn = this.connection;
            if (valuesClause == null) {
               return this.executeBatchSerially(batchTimeout);
            } else {
               int numBatchedArgs = this.batchedArgs.size();
               if (this.retrieveGeneratedKeys) {
                  this.batchedGeneratedKeys = new ArrayList(numBatchedArgs);
               }

               int numValuesPerBatch = this.computeBatchSize(numBatchedArgs);
               if (numBatchedArgs < numValuesPerBatch) {
                  numValuesPerBatch = numBatchedArgs;
               }

               PreparedStatement batchedStatement = null;
               int batchedParamIndex = 1;
               long updateCountRunningTotal = 0L;
               int numberToExecuteAsMultiValue = false;
               int batchCounter = 0;
               StatementImpl.CancelTask timeoutTask = null;
               SQLException sqlEx = null;
               long[] updateCounts = new long[numBatchedArgs];

               Object ex;
               try {
                  try {
                     batchedStatement = this.prepareBatchedInsertSQL(locallyScopedConn, numValuesPerBatch);
                     if ((Boolean)locallyScopedConn.getPropertySet().getBooleanReadableProperty("enableQueryTimeouts").getValue() && batchTimeout != 0) {
                        timeoutTask = new StatementImpl.CancelTask(batchedStatement);
                        locallyScopedConn.getCancelTimer().schedule(timeoutTask, (long)batchTimeout);
                     }

                     int numberToExecuteAsMultiValue;
                     if (numBatchedArgs < numValuesPerBatch) {
                        numberToExecuteAsMultiValue = numBatchedArgs;
                     } else {
                        numberToExecuteAsMultiValue = numBatchedArgs / numValuesPerBatch;
                     }

                     ex = numberToExecuteAsMultiValue * numValuesPerBatch;

                     for(int i = 0; i < ex; ++i) {
                        if (i != 0 && i % numValuesPerBatch == 0) {
                           try {
                              updateCountRunningTotal += batchedStatement.executeLargeUpdate();
                           } catch (SQLException var50) {
                              sqlEx = this.handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, var50);
                           }

                           this.getBatchedGeneratedKeys(batchedStatement);
                           batchedStatement.clearParameters();
                           batchedParamIndex = 1;
                        }

                        batchedParamIndex = this.setOneBatchedParameterSet(batchedStatement, batchedParamIndex, this.batchedArgs.get(batchCounter++));
                     }

                     try {
                        updateCountRunningTotal += batchedStatement.executeLargeUpdate();
                     } catch (SQLException var49) {
                        sqlEx = this.handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, var49);
                     }

                     this.getBatchedGeneratedKeys(batchedStatement);
                     numValuesPerBatch = numBatchedArgs - batchCounter;
                  } finally {
                     if (batchedStatement != null) {
                        batchedStatement.close();
                        batchedStatement = null;
                     }

                  }

                  try {
                     if (numValuesPerBatch > 0) {
                        batchedStatement = this.prepareBatchedInsertSQL(locallyScopedConn, numValuesPerBatch);
                        if (timeoutTask != null) {
                           timeoutTask.toCancel = batchedStatement;
                        }

                        for(batchedParamIndex = 1; batchCounter < numBatchedArgs; batchedParamIndex = this.setOneBatchedParameterSet(batchedStatement, batchedParamIndex, this.batchedArgs.get(batchCounter++))) {
                        }

                        try {
                           updateCountRunningTotal += batchedStatement.executeLargeUpdate();
                        } catch (SQLException var48) {
                           ex = var48;
                           sqlEx = this.handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, var48);
                        }

                        this.getBatchedGeneratedKeys(batchedStatement);
                     }

                     if (sqlEx != null) {
                        throw SQLError.createBatchUpdateException(sqlEx, updateCounts, this.getExceptionInterceptor());
                     }

                     if (numBatchedArgs > 1) {
                        long updCount = updateCountRunningTotal > 0L ? -2L : 0L;

                        for(int j = 0; j < numBatchedArgs; ++j) {
                           updateCounts[j] = updCount;
                        }
                     } else {
                        updateCounts[0] = updateCountRunningTotal;
                     }

                     ex = updateCounts;
                  } finally {
                     if (batchedStatement != null) {
                        batchedStatement.close();
                     }

                  }
               } finally {
                  if (timeoutTask != null) {
                     timeoutTask.cancel();
                     locallyScopedConn.getCancelTimer().purge();
                  }

                  this.resetCancelledState();
               }

               return (long[])ex;
            }
         }
      } catch (CJException var55) {
         throw SQLExceptionsMapping.translateException(var55, this.getExceptionInterceptor());
      }
   }

   protected String getValuesClause() throws SQLException {
      return this.parseInfo.valuesClause;
   }

   protected int computeBatchSize(int numBatchedArgs) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            long[] combinedValues = this.computeMaxParameterSetSizeAndBatchSize(numBatchedArgs);
            long maxSizeOfParameterSet = combinedValues[0];
            long sizeOfEntireBatch = combinedValues[1];
            return sizeOfEntireBatch < (long)((Integer)this.maxAllowedPacket.getValue() - this.originalSql.length()) ? numBatchedArgs : (int)Math.max(1L, (long)((Integer)this.maxAllowedPacket.getValue() - this.originalSql.length()) / maxSizeOfParameterSet);
         }
      } catch (CJException var11) {
         throw SQLExceptionsMapping.translateException(var11, this.getExceptionInterceptor());
      }
   }

   protected long[] computeMaxParameterSetSizeAndBatchSize(int numBatchedArgs) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            long sizeOfEntireBatch = 0L;
            long maxSizeOfParameterSet = 0L;

            for(int i = 0; i < numBatchedArgs; ++i) {
               PreparedStatement.BatchParams paramArg = (PreparedStatement.BatchParams)this.batchedArgs.get(i);
               boolean[] isNullBatch = paramArg.isNull;
               boolean[] isStreamBatch = paramArg.isStream;
               long sizeOfParameterSet = 0L;

               for(int j = 0; j < isNullBatch.length; ++j) {
                  if (!isNullBatch[j]) {
                     if (isStreamBatch[j]) {
                        int streamLength = paramArg.streamLengths[j];
                        if (streamLength != -1) {
                           sizeOfParameterSet += (long)(streamLength * 2);
                        } else {
                           int paramLength = paramArg.parameterStrings[j].length;
                           sizeOfParameterSet += (long)paramLength;
                        }
                     } else {
                        sizeOfParameterSet += (long)paramArg.parameterStrings[j].length;
                     }
                  } else {
                     sizeOfParameterSet += 4L;
                  }
               }

               if (this.getValuesClause() != null) {
                  sizeOfParameterSet += (long)(this.getValuesClause().length() + 1);
               } else {
                  sizeOfParameterSet += (long)(this.originalSql.length() + 1);
               }

               sizeOfEntireBatch += sizeOfParameterSet;
               if (sizeOfParameterSet > maxSizeOfParameterSet) {
                  maxSizeOfParameterSet = sizeOfParameterSet;
               }
            }

            return new long[]{maxSizeOfParameterSet, sizeOfEntireBatch};
         }
      } catch (CJException var19) {
         throw SQLExceptionsMapping.translateException(var19, this.getExceptionInterceptor());
      }
   }

   protected long[] executeBatchSerially(int batchTimeout) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            JdbcConnection locallyScopedConn = this.connection;
            if (locallyScopedConn == null) {
               this.checkClosed();
            }

            long[] updateCounts = null;
            if (this.batchedArgs != null) {
               int nbrCommands = this.batchedArgs.size();
               updateCounts = new long[nbrCommands];

               for(int i = 0; i < nbrCommands; ++i) {
                  updateCounts[i] = -3L;
               }

               SQLException sqlEx = null;
               StatementImpl.CancelTask timeoutTask = null;

               try {
                  long[] newUpdateCounts;
                  try {
                     if ((Boolean)locallyScopedConn.getPropertySet().getBooleanReadableProperty("enableQueryTimeouts").getValue() && batchTimeout != 0) {
                        timeoutTask = new StatementImpl.CancelTask(this);
                        locallyScopedConn.getCancelTimer().schedule(timeoutTask, (long)batchTimeout);
                     }

                     if (this.retrieveGeneratedKeys) {
                        this.batchedGeneratedKeys = new ArrayList(nbrCommands);
                     }

                     for(this.batchCommandIndex = 0; this.batchCommandIndex < nbrCommands; ++this.batchCommandIndex) {
                        Object arg = this.batchedArgs.get(this.batchCommandIndex);

                        try {
                           if (!(arg instanceof String)) {
                              PreparedStatement.BatchParams paramArg = (PreparedStatement.BatchParams)arg;
                              updateCounts[this.batchCommandIndex] = this.executeUpdateInternal(paramArg.parameterStrings, paramArg.parameterStreams, paramArg.isStream, paramArg.streamLengths, paramArg.isNull, true);
                              this.getBatchedGeneratedKeys(this.containsOnDuplicateKeyUpdateInSQL() ? 1 : 0);
                           } else {
                              updateCounts[this.batchCommandIndex] = this.executeUpdateInternal((String)arg, true, this.retrieveGeneratedKeys);
                              this.getBatchedGeneratedKeys(this.results.getFirstCharOfQuery() == 'I' && this.containsOnDuplicateKeyInString((String)arg) ? 1 : 0);
                           }
                        } catch (SQLException var21) {
                           updateCounts[this.batchCommandIndex] = -3L;
                           if (!this.continueBatchOnError || var21 instanceof MySQLTimeoutException || var21 instanceof MySQLStatementCancelledException || this.hasDeadlockOrTimeoutRolledBackTx(var21)) {
                              newUpdateCounts = new long[this.batchCommandIndex];
                              System.arraycopy(updateCounts, 0, newUpdateCounts, 0, this.batchCommandIndex);
                              throw SQLError.createBatchUpdateException(var21, newUpdateCounts, this.getExceptionInterceptor());
                           }

                           sqlEx = var21;
                        }
                     }

                     if (sqlEx != null) {
                        throw SQLError.createBatchUpdateException(sqlEx, updateCounts, this.getExceptionInterceptor());
                     }
                  } catch (NullPointerException var22) {
                     try {
                        this.checkClosed();
                     } catch (StatementIsClosedException var20) {
                        updateCounts[this.batchCommandIndex] = -3L;
                        newUpdateCounts = new long[this.batchCommandIndex];
                        System.arraycopy(updateCounts, 0, newUpdateCounts, 0, this.batchCommandIndex);
                        throw SQLError.createBatchUpdateException(SQLExceptionsMapping.translateException(var20), newUpdateCounts, this.getExceptionInterceptor());
                     }

                     throw var22;
                  }
               } finally {
                  this.batchCommandIndex = -1;
                  if (timeoutTask != null) {
                     timeoutTask.cancel();
                     locallyScopedConn.getCancelTimer().purge();
                  }

                  this.resetCancelledState();
               }
            }

            return updateCounts != null ? updateCounts : new long[0];
         }
      } catch (CJException var25) {
         throw SQLExceptionsMapping.translateException(var25, this.getExceptionInterceptor());
      }
   }

   public String getDateTime(String pattern) {
      SimpleDateFormat sdf = new SimpleDateFormat(pattern);
      return sdf.format(new Date());
   }

   protected ResultSetInternalMethods executeInternal(int maxRowsToRetrieve, PacketPayload sendPacket, boolean createStreamingResultSet, boolean queryIsSelectOnly, ColumnDefinition metadata, boolean isBatch) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            ResultSetInternalMethods var10000;
            try {
               this.resetCancelledState();
               JdbcConnection locallyScopedConnection = this.connection;
               ++this.numberOfExecutions;
               if (this.doPingInstead) {
                  this.doPingInstead();
                  var10000 = this.results;
                  return var10000;
               }

               StatementImpl.CancelTask timeoutTask = null;

               ResultSetInternalMethods rs;
               try {
                  if ((Boolean)locallyScopedConnection.getPropertySet().getBooleanReadableProperty("enableQueryTimeouts").getValue() && this.timeoutInMillis != 0) {
                     timeoutTask = new StatementImpl.CancelTask(this);
                     locallyScopedConnection.getCancelTimer().schedule(timeoutTask, (long)this.timeoutInMillis);
                  }

                  if (!isBatch) {
                     this.statementBegins();
                  }

                  rs = locallyScopedConnection.execSQL(this, (String)null, maxRowsToRetrieve, sendPacket, createStreamingResultSet, this.getCurrentCatalog(), metadata, isBatch);
                  if (timeoutTask != null) {
                     timeoutTask.cancel();
                     locallyScopedConnection.getCancelTimer().purge();
                     if (timeoutTask.caughtWhileCancelling != null) {
                        throw timeoutTask.caughtWhileCancelling;
                     }

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
                  if (!isBatch) {
                     this.statementExecuting.set(false);
                  }

                  if (timeoutTask != null) {
                     timeoutTask.cancel();
                     locallyScopedConnection.getCancelTimer().purge();
                  }

               }

               var10000 = rs;
            } catch (NullPointerException var24) {
               this.checkClosed();
               throw var24;
            }

            return var10000;
         }
      } catch (CJException var26) {
         throw SQLExceptionsMapping.translateException(var26, this.getExceptionInterceptor());
      }
   }

   public ResultSet executeQuery() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            JdbcConnection locallyScopedConn = this.connection;
            this.checkForDml(this.originalSql, this.firstCharOfStmt);
            CachedResultSetMetaData cachedMetadata = null;
            this.clearWarnings();
            this.batchedGeneratedKeys = null;
            this.setupStreamingTimeout(locallyScopedConn);
            PacketPayload sendPacket = this.fillSendPacket();
            this.implicitlyCloseAllOpenResults();
            String oldCatalog = null;
            if (!locallyScopedConn.getCatalog().equals(this.getCurrentCatalog())) {
               oldCatalog = locallyScopedConn.getCatalog();
               locallyScopedConn.setCatalog(this.getCurrentCatalog());
            }

            boolean cacheResultSetMetadata = (Boolean)locallyScopedConn.getPropertySet().getBooleanReadableProperty("cacheResultSetMetadata").getValue();
            if (cacheResultSetMetadata) {
               cachedMetadata = locallyScopedConn.getCachedMetaData(this.originalSql);
            }

            locallyScopedConn.setSessionMaxRows(this.maxRows);
            this.results = this.executeInternal(this.maxRows, sendPacket, this.createStreamingResultSet(), true, cachedMetadata, false);
            if (oldCatalog != null) {
               locallyScopedConn.setCatalog(oldCatalog);
            }

            if (cachedMetadata != null) {
               locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, cachedMetadata, this.results);
            } else if (cacheResultSetMetadata) {
               locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, (CachedResultSetMetaData)null, this.results);
            }

            this.lastInsertId = this.results.getUpdateID();
            return this.results;
         }
      } catch (CJException var10) {
         throw SQLExceptionsMapping.translateException(var10, this.getExceptionInterceptor());
      }
   }

   public int executeUpdate() throws SQLException {
      try {
         return Util.truncateAndConvertToInt(this.executeLargeUpdate());
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   protected long executeUpdateInternal(boolean clearBatchedGeneratedKeysAndWarnings, boolean isBatch) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (clearBatchedGeneratedKeysAndWarnings) {
               this.clearWarnings();
               this.batchedGeneratedKeys = null;
            }

            return this.executeUpdateInternal(this.parameterValues, this.parameterStreams, this.isStream, this.streamLengths, this.isNull, isBatch);
         }
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   protected long executeUpdateInternal(byte[][] batchedParameterStrings, InputStream[] batchedParameterStreams, boolean[] batchedIsStream, int[] batchedStreamLengths, boolean[] batchedIsNull, boolean isReallyBatch) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            JdbcConnection locallyScopedConn = this.connection;
            if (locallyScopedConn.isReadOnly(false)) {
               throw SQLError.createSQLException(Messages.getString("PreparedStatement.34") + Messages.getString("PreparedStatement.35"), "S1009", this.getExceptionInterceptor());
            } else if (this.firstCharOfStmt == 'S' && this.isSelectQuery()) {
               throw SQLError.createSQLException(Messages.getString("PreparedStatement.37"), "01S03", this.getExceptionInterceptor());
            } else {
               this.implicitlyCloseAllOpenResults();
               ResultSetInternalMethods rs = null;
               PacketPayload sendPacket = this.fillSendPacket(batchedParameterStrings, batchedParameterStreams, batchedIsStream, batchedStreamLengths);
               String oldCatalog = null;
               if (!locallyScopedConn.getCatalog().equals(this.getCurrentCatalog())) {
                  oldCatalog = locallyScopedConn.getCatalog();
                  locallyScopedConn.setCatalog(this.getCurrentCatalog());
               }

               locallyScopedConn.setSessionMaxRows(-1);
               boolean oldInfoMsgState = false;
               if (this.retrieveGeneratedKeys) {
                  oldInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled();
                  locallyScopedConn.setReadInfoMsgEnabled(true);
               }

               rs = this.executeInternal(-1, sendPacket, false, false, (ColumnDefinition)null, isReallyBatch);
               if (this.retrieveGeneratedKeys) {
                  locallyScopedConn.setReadInfoMsgEnabled(oldInfoMsgState);
                  rs.setFirstCharOfQuery(this.firstCharOfStmt);
               }

               if (oldCatalog != null) {
                  locallyScopedConn.setCatalog(oldCatalog);
               }

               this.results = rs;
               this.updateCount = rs.getUpdateCount();
               if (this.containsOnDuplicateKeyUpdateInSQL() && this.compensateForOnDuplicateKeyUpdate && (this.updateCount == 2L || this.updateCount == 0L)) {
                  this.updateCount = 1L;
               }

               this.lastInsertId = rs.getUpdateID();
               return this.updateCount;
            }
         }
      } catch (CJException var16) {
         throw SQLExceptionsMapping.translateException(var16, this.getExceptionInterceptor());
      }
   }

   protected boolean containsOnDuplicateKeyUpdateInSQL() {
      return this.parseInfo.isOnDuplicateKeyUpdate;
   }

   protected PacketPayload fillSendPacket() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            return this.fillSendPacket(this.parameterValues, this.parameterStreams, this.isStream, this.streamLengths);
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   protected PacketPayload fillSendPacket(byte[][] batchedParameterStrings, InputStream[] batchedParameterStreams, boolean[] batchedIsStream, int[] batchedStreamLengths) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            PacketPayload sendPacket = this.session.getSharedSendPacket();
            sendPacket.writeInteger(NativeProtocol.IntegerDataType.INT1, 3L);
            boolean useStreamLengths = (Boolean)this.useStreamLengthsInPrepStmts.getValue();
            int ensurePacketSize = 0;
            String statementComment = this.connection.getStatementComment();
            byte[] commentAsBytes = null;
            if (statementComment != null) {
               commentAsBytes = StringUtils.getBytes(statementComment, this.charEncoding);
               ensurePacketSize += commentAsBytes.length;
               ensurePacketSize += 6;
            }

            int i;
            for(i = 0; i < batchedParameterStrings.length; ++i) {
               if (batchedIsStream[i] && useStreamLengths) {
                  ensurePacketSize += batchedStreamLengths[i];
               }
            }

            if (ensurePacketSize != 0) {
               sendPacket.ensureCapacity(ensurePacketSize);
            }

            if (commentAsBytes != null) {
               sendPacket.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, Constants.SLASH_STAR_SPACE_AS_BYTES);
               sendPacket.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, commentAsBytes);
               sendPacket.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, Constants.SPACE_STAR_SLASH_SPACE_AS_BYTES);
            }

            for(i = 0; i < batchedParameterStrings.length; ++i) {
               this.checkAllParametersSet(batchedParameterStrings[i], batchedParameterStreams[i], i);
               sendPacket.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, this.staticSqlStrings[i]);
               if (batchedIsStream[i]) {
                  this.streamToBytes(sendPacket, batchedParameterStreams[i], true, batchedStreamLengths[i], useStreamLengths);
               } else {
                  sendPacket.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, batchedParameterStrings[i]);
               }
            }

            sendPacket.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, this.staticSqlStrings[batchedParameterStrings.length]);
            return sendPacket;
         }
      } catch (CJException var15) {
         throw SQLExceptionsMapping.translateException(var15, this.getExceptionInterceptor());
      }
   }

   private void checkAllParametersSet(byte[] parameterString, InputStream parameterStream, int columnIndex) throws SQLException {
      if (parameterString == null && parameterStream == null) {
         throw SQLError.createSQLException(Messages.getString("PreparedStatement.40") + (columnIndex + 1), "07001", this.getExceptionInterceptor());
      }
   }

   protected PreparedStatement prepareBatchedInsertSQL(JdbcConnection localConn, int numBatches) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            PreparedStatement pstmt = new PreparedStatement(localConn, "Rewritten batch of: " + this.originalSql, this.getCurrentCatalog(), this.parseInfo.getParseInfoForBatch(numBatches));
            pstmt.setRetrieveGeneratedKeys(this.retrieveGeneratedKeys);
            pstmt.rewrittenBatchSize = numBatches;
            return pstmt;
         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   protected void setRetrieveGeneratedKeys(boolean flag) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.retrieveGeneratedKeys = flag;
         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public int getRewrittenBatchSize() {
      return this.rewrittenBatchSize;
   }

   public String getNonRewrittenSql() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            int indexOfBatch = this.originalSql.indexOf(" of: ");
            return indexOfBatch != -1 ? this.originalSql.substring(indexOfBatch + 5) : this.originalSql;
         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public byte[] getBytesRepresentation(int parameterIndex) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (this.isStream[parameterIndex]) {
               return this.streamToBytes(this.parameterStreams[parameterIndex], false, this.streamLengths[parameterIndex], (Boolean)this.useStreamLengthsInPrepStmts.getValue());
            } else {
               byte[] parameterVal = this.parameterValues[parameterIndex];
               if (parameterVal == null) {
                  return null;
               } else if (parameterVal[0] == 39 && parameterVal[parameterVal.length - 1] == 39) {
                  byte[] valNoQuotes = new byte[parameterVal.length - 2];
                  System.arraycopy(parameterVal, 1, valNoQuotes, 0, parameterVal.length - 2);
                  return valNoQuotes;
               } else {
                  return parameterVal;
               }
            }
         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   protected byte[] getBytesRepresentationForBatch(int parameterIndex, int commandIndex) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            Object batchedArg = this.batchedArgs.get(commandIndex);
            if (batchedArg instanceof String) {
               return StringUtils.getBytes((String)batchedArg, this.charEncoding);
            } else {
               PreparedStatement.BatchParams params = (PreparedStatement.BatchParams)batchedArg;
               if (params.isStream[parameterIndex]) {
                  return this.streamToBytes(params.parameterStreams[parameterIndex], false, params.streamLengths[parameterIndex], (Boolean)this.useStreamLengthsInPrepStmts.getValue());
               } else {
                  byte[] parameterVal = params.parameterStrings[parameterIndex];
                  if (parameterVal == null) {
                     return null;
                  } else if (parameterVal[0] == 39 && parameterVal[parameterVal.length - 1] == 39) {
                     byte[] valNoQuotes = new byte[parameterVal.length - 2];
                     System.arraycopy(parameterVal, 1, valNoQuotes, 0, parameterVal.length - 2);
                     return valNoQuotes;
                  } else {
                     return parameterVal;
                  }
               }
            }
         }
      } catch (CJException var11) {
         throw SQLExceptionsMapping.translateException(var11, this.getExceptionInterceptor());
      }
   }

   private final String getDateTimePattern(String dt, boolean toTime) throws IOException {
      int dtLength = dt != null ? dt.length() : 0;
      int z;
      if (dtLength >= 8 && dtLength <= 10) {
         int dashCount = 0;
         boolean isDateOnly = true;

         for(z = 0; z < dtLength; ++z) {
            char c = dt.charAt(z);
            if (!Character.isDigit(c) && c != '-') {
               isDateOnly = false;
               break;
            }

            if (c == '-') {
               ++dashCount;
            }
         }

         if (isDateOnly && dashCount == 2) {
            return "yyyy-MM-dd";
         }
      }

      boolean colonsOnly = true;

      int n;
      for(n = 0; n < dtLength; ++n) {
         char c = dt.charAt(n);
         if (!Character.isDigit(c) && c != ':') {
            colonsOnly = false;
            break;
         }
      }

      if (colonsOnly) {
         return "HH:mm:ss";
      } else {
         StringReader reader = new StringReader(dt + " ");
         ArrayList<Object[]> vec = new ArrayList();
         ArrayList<Object[]> vecRemovelist = new ArrayList();
         Object[] nv = new Object[]{'y', new StringBuilder(), 0};
         vec.add(nv);
         if (toTime) {
            nv = new Object[]{'h', new StringBuilder(), 0};
            vec.add(nv);
         }

         char c;
         Object[] v;
         int size;
         int i;
         while((z = reader.read()) != -1) {
            char separator = (char)z;
            int maxvecs = vec.size();

            for(int count = 0; count < maxvecs; ++count) {
               v = (Object[])vec.get(count);
               n = (Integer)v[2];
               c = this.getSuccessor((Character)v[0], n);
               if (!Character.isLetterOrDigit(separator)) {
                  if (c == (Character)v[0] && c != 'S') {
                     vecRemovelist.add(v);
                  } else {
                     ((StringBuilder)v[1]).append(separator);
                     if (c == 'X' || c == 'Y') {
                        v[2] = 4;
                     }
                  }
               } else {
                  if (c == 'X') {
                     c = 'y';
                     nv = new Object[]{'M', (new StringBuilder(((StringBuilder)v[1]).toString())).append('M'), 1};
                     vec.add(nv);
                  } else if (c == 'Y') {
                     c = 'M';
                     nv = new Object[]{'d', (new StringBuilder(((StringBuilder)v[1]).toString())).append('d'), 1};
                     vec.add(nv);
                  }

                  ((StringBuilder)v[1]).append(c);
                  if (c == (Character)v[0]) {
                     v[2] = n + 1;
                  } else {
                     v[0] = c;
                     v[2] = 1;
                  }
               }
            }

            size = vecRemovelist.size();

            for(i = 0; i < size; ++i) {
               v = (Object[])vecRemovelist.get(i);
               vec.remove(v);
            }

            vecRemovelist.clear();
         }

         size = vec.size();

         for(i = 0; i < size; ++i) {
            v = (Object[])vec.get(i);
            c = (Character)v[0];
            n = (Integer)v[2];
            boolean bk = this.getSuccessor(c, n) != c;
            boolean atEnd = (c == 's' || c == 'm' || c == 'h' && toTime) && bk;
            boolean finishesAtDate = bk && c == 'd' && !toTime;
            boolean containsEnd = ((StringBuilder)v[1]).toString().indexOf(87) != -1;
            if (!atEnd && !finishesAtDate || containsEnd) {
               vecRemovelist.add(v);
            }
         }

         size = vecRemovelist.size();

         for(i = 0; i < size; ++i) {
            vec.remove(vecRemovelist.get(i));
         }

         vecRemovelist.clear();
         v = (Object[])vec.get(0);
         StringBuilder format = (StringBuilder)v[1];
         format.setLength(format.length() - 1);
         return format.toString();
      }
   }

   public ResultSetMetaData getMetaData() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (!this.isSelectQuery()) {
               return null;
            } else {
               PreparedStatement mdStmt = null;
               ResultSet mdRs = null;
               if (this.pstmtResultMetaData == null) {
                  boolean var18 = false;

                  try {
                     var18 = true;
                     mdStmt = new PreparedStatement(this.connection, this.originalSql, this.getCurrentCatalog(), this.parseInfo);
                     mdStmt.setMaxRows(1);
                     int paramCount = this.parameterValues.length;

                     for(int i = 1; i <= paramCount; ++i) {
                        mdStmt.setString(i, "");
                     }

                     boolean hadResults = mdStmt.execute();
                     if (hadResults) {
                        mdRs = mdStmt.getResultSet();
                        this.pstmtResultMetaData = mdRs.getMetaData();
                        var18 = false;
                     } else {
                        this.pstmtResultMetaData = new com.mysql.cj.jdbc.result.ResultSetMetaData(this.session, new Field[0], (Boolean)this.session.getPropertySet().getBooleanReadableProperty("useOldAliasMetadataBehavior").getValue(), (Boolean)this.session.getPropertySet().getBooleanReadableProperty("yearIsDateType").getValue(), this.getExceptionInterceptor());
                        var18 = false;
                     }
                  } finally {
                     if (var18) {
                        SQLException sqlExRethrow = null;
                        if (mdRs != null) {
                           try {
                              mdRs.close();
                           } catch (SQLException var20) {
                              sqlExRethrow = var20;
                           }

                           mdRs = null;
                        }

                        if (mdStmt != null) {
                           try {
                              mdStmt.close();
                           } catch (SQLException var19) {
                              sqlExRethrow = var19;
                           }

                           mdStmt = null;
                        }

                        if (sqlExRethrow != null) {
                           throw sqlExRethrow;
                        }

                     }
                  }

                  SQLException sqlExRethrow = null;
                  if (mdRs != null) {
                     try {
                        mdRs.close();
                     } catch (SQLException var22) {
                        sqlExRethrow = var22;
                     }

                     mdRs = null;
                  }

                  if (mdStmt != null) {
                     try {
                        mdStmt.close();
                     } catch (SQLException var21) {
                        sqlExRethrow = var21;
                     }

                     mdStmt = null;
                  }

                  if (sqlExRethrow != null) {
                     throw sqlExRethrow;
                  }
               }

               return this.pstmtResultMetaData;
            }
         }
      } catch (CJException var25) {
         throw SQLExceptionsMapping.translateException(var25, this.getExceptionInterceptor());
      }
   }

   protected boolean isSelectQuery() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            return StringUtils.startsWithIgnoreCaseAndWs(StringUtils.stripComments(this.originalSql, "'\"", "'\"", true, false, true, true), "SELECT");
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public ParameterMetaData getParameterMetaData() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (this.parameterMetaData == null) {
               if ((Boolean)this.session.getPropertySet().getBooleanReadableProperty("generateSimpleParameterMetadata").getValue()) {
                  this.parameterMetaData = new MysqlParameterMetadata(this.parameterCount);
               } else {
                  this.parameterMetaData = new MysqlParameterMetadata(this.session, (Field[])null, this.parameterCount, this.getExceptionInterceptor());
               }
            }

            return this.parameterMetaData;
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   PreparedStatement.ParseInfo getParseInfo() {
      return this.parseInfo;
   }

   private final char getSuccessor(char c, int n) {
      return (char)(c == 'y' && n == 2 ? 'X' : (c == 'y' && n < 4 ? 'y' : (c == 'y' ? 'M' : (c == 'M' && n == 2 ? 'Y' : (c == 'M' && n < 3 ? 'M' : (c == 'M' ? 'd' : (c == 'd' && n < 2 ? 'd' : (c == 'd' ? 'H' : (c == 'H' && n < 2 ? 'H' : (c == 'H' ? 'm' : (c == 'm' && n < 2 ? 'm' : (c == 'm' ? 's' : (c == 's' && n < 2 ? 's' : 'W')))))))))))));
   }

   private final void hexEscapeBlock(byte[] buf, PacketPayload packet, int size) throws SQLException {
      for(int i = 0; i < size; ++i) {
         byte b = buf[i];
         int lowBits = (b & 255) / 16;
         int highBits = (b & 255) % 16;
         packet.writeInteger(NativeProtocol.IntegerDataType.INT1, (long)HEX_DIGITS[lowBits]);
         packet.writeInteger(NativeProtocol.IntegerDataType.INT1, (long)HEX_DIGITS[highBits]);
      }

   }

   private void initializeFromParseInfo() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.staticSqlStrings = this.parseInfo.staticSql;
            this.isLoadDataQuery = this.parseInfo.foundLoadData;
            this.firstCharOfStmt = this.parseInfo.firstStmtChar;
            this.parameterCount = this.staticSqlStrings.length - 1;
            this.parameterValues = new byte[this.parameterCount][];
            this.parameterStreams = new InputStream[this.parameterCount];
            this.isStream = new boolean[this.parameterCount];
            this.streamLengths = new int[this.parameterCount];
            this.isNull = new boolean[this.parameterCount];
            this.parameterTypes = new MysqlType[this.parameterCount];
            this.clearParameters();

            for(int j = 0; j < this.parameterCount; ++j) {
               this.isStream[j] = false;
            }

         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public boolean isNull(int paramIndex) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            return this.isNull[paramIndex];
         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   private final int readblock(InputStream i, byte[] b) throws SQLException {
      try {
         return i.read(b);
      } catch (Throwable var4) {
         throw SQLError.createSQLException(Messages.getString("PreparedStatement.56") + var4.getClass().getName(), "S1000", var4, this.getExceptionInterceptor());
      }
   }

   private final int readblock(InputStream i, byte[] b, int length) throws SQLException {
      try {
         int lengthToRead = length;
         if (length > b.length) {
            lengthToRead = b.length;
         }

         return i.read(b, 0, lengthToRead);
      } catch (Throwable var5) {
         throw SQLError.createSQLException(Messages.getString("PreparedStatement.56") + var5.getClass().getName(), "S1000", var5, this.getExceptionInterceptor());
      }
   }

   public void realClose(boolean calledExplicitly, boolean closeOpenResults) throws SQLException {
      JdbcConnection locallyScopedConn = this.connection;
      if (locallyScopedConn != null) {
         synchronized(locallyScopedConn.getConnectionMutex()) {
            if (!this.isClosed) {
               if (this.useUsageAdvisor && this.numberOfExecutions <= 1) {
                  String message = Messages.getString("PreparedStatement.43");
                  this.eventSink.consumeEvent(new ProfilerEventImpl((byte)0, "", this.getCurrentCatalog(), this.connectionId, this.getId(), -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, (String)null, this.pointOfOrigin, message));
               }

               super.realClose(calledExplicitly, closeOpenResults);
               this.dbmd = null;
               this.originalSql = null;
               this.staticSqlStrings = (byte[][])null;
               this.parameterValues = (byte[][])null;
               this.parameterStreams = null;
               this.isStream = null;
               this.streamLengths = null;
               this.isNull = null;
               this.streamConvertBuf = null;
               this.parameterTypes = null;
            }
         }
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
         if (x == null) {
            this.setNull(parameterIndex, MysqlType.VARCHAR);
         } else {
            this.setBinaryStream(parameterIndex, x, length);
         }

      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
      try {
         if (x == null) {
            this.setNull(parameterIndex, MysqlType.DECIMAL);
         } else {
            this.setInternal(parameterIndex, StringUtils.fixDecimalExponent(x.toPlainString()));
            this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.DECIMAL;
         }

      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (x == null) {
               this.setNull(parameterIndex, MysqlType.BINARY);
            } else {
               int parameterIndexOffset = this.getParameterIndexOffset();
               if (parameterIndex < 1 || parameterIndex > this.staticSqlStrings.length) {
                  throw SQLError.createSQLException(Messages.getString("PreparedStatement.2") + parameterIndex + Messages.getString("PreparedStatement.3") + this.staticSqlStrings.length + Messages.getString("PreparedStatement.4"), "S1009", this.getExceptionInterceptor());
               }

               if (parameterIndexOffset == -1 && parameterIndex == 1) {
                  throw SQLError.createSQLException(Messages.getString("PreparedStatement.63"), "S1009", this.getExceptionInterceptor());
               }

               this.parameterStreams[parameterIndex - 1 + parameterIndexOffset] = x;
               this.isStream[parameterIndex - 1 + parameterIndexOffset] = true;
               this.streamLengths[parameterIndex - 1 + parameterIndexOffset] = length;
               this.isNull[parameterIndex - 1 + parameterIndexOffset] = false;
               this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.BLOB;
            }

         }
      } catch (CJException var9) {
         throw SQLExceptionsMapping.translateException(var9, this.getExceptionInterceptor());
      }
   }

   public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
      try {
         this.setBinaryStream(parameterIndex, inputStream, (int)length);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void setBlob(int i, java.sql.Blob x) throws SQLException {
      try {
         if (x == null) {
            this.setNull(i, MysqlType.BLOB);
         } else {
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            bytesOut.write(39);
            this.escapeblockFast(x.getBytes(1L, (int)x.length()), bytesOut, (int)x.length());
            bytesOut.write(39);
            this.setInternal(i, bytesOut.toByteArray());
            this.parameterTypes[i - 1 + this.getParameterIndexOffset()] = MysqlType.BLOB;
         }

      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void setBoolean(int parameterIndex, boolean x) throws SQLException {
      try {
         this.setInternal(parameterIndex, x ? "1" : "0");
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setByte(int parameterIndex, byte x) throws SQLException {
      try {
         this.setInternal(parameterIndex, String.valueOf(x));
         this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.TINYINT;
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setBytes(int parameterIndex, byte[] x) throws SQLException {
      try {
         this.setBytes(parameterIndex, x, true, true);
         if (x != null) {
            this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.BINARY;
         }

      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setBytes(int parameterIndex, byte[] x, boolean checkForIntroducer, boolean escapeForMBChars) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (x == null) {
               this.setNull(parameterIndex, MysqlType.BINARY);
            } else {
               String connectionEncoding = (String)this.connection.getPropertySet().getStringReadableProperty("characterEncoding").getValue();

               int i;
               int i;
               try {
                  if (this.connection.isNoBackslashEscapesSet() || escapeForMBChars && connectionEncoding != null && CharsetMapping.isMultibyteCharset(connectionEncoding)) {
                     ByteArrayOutputStream bOut = new ByteArrayOutputStream(x.length * 2 + 3);
                     bOut.write(120);
                     bOut.write(39);

                     for(i = 0; i < x.length; ++i) {
                        int lowBits = (x[i] & 255) / 16;
                        i = (x[i] & 255) % 16;
                        bOut.write(HEX_DIGITS[lowBits]);
                        bOut.write(HEX_DIGITS[i]);
                     }

                     bOut.write(39);
                     this.setInternal(parameterIndex, bOut.toByteArray());
                     return;
                  }
               } catch (SQLException var14) {
                  throw var14;
               } catch (RuntimeException var15) {
                  throw SQLError.createSQLException(var15.toString(), "S1009", var15, (ExceptionInterceptor)null);
               }

               int numBytes = x.length;
               i = 2;
               if (checkForIntroducer) {
                  i += 7;
               }

               ByteArrayOutputStream bOut = new ByteArrayOutputStream(numBytes + i);
               if (checkForIntroducer) {
                  bOut.write(95);
                  bOut.write(98);
                  bOut.write(105);
                  bOut.write(110);
                  bOut.write(97);
                  bOut.write(114);
                  bOut.write(121);
               }

               bOut.write(39);

               for(i = 0; i < numBytes; ++i) {
                  byte b = x[i];
                  switch(b) {
                  case 0:
                     bOut.write(92);
                     bOut.write(48);
                     break;
                  case 10:
                     bOut.write(92);
                     bOut.write(110);
                     break;
                  case 13:
                     bOut.write(92);
                     bOut.write(114);
                     break;
                  case 26:
                     bOut.write(92);
                     bOut.write(90);
                     break;
                  case 34:
                     bOut.write(92);
                     bOut.write(34);
                     break;
                  case 39:
                     bOut.write(92);
                     bOut.write(39);
                     break;
                  case 92:
                     bOut.write(92);
                     bOut.write(92);
                     break;
                  default:
                     bOut.write(b);
                  }
               }

               bOut.write(39);
               this.setInternal(parameterIndex, bOut.toByteArray());
            }
         }
      } catch (CJException var17) {
         throw SQLExceptionsMapping.translateException(var17, this.getExceptionInterceptor());
      }
   }

   public void setBytesNoEscape(int parameterIndex, byte[] parameterAsBytes) throws SQLException {
      byte[] parameterWithQuotes = new byte[parameterAsBytes.length + 2];
      parameterWithQuotes[0] = 39;
      System.arraycopy(parameterAsBytes, 0, parameterWithQuotes, 1, parameterAsBytes.length);
      parameterWithQuotes[parameterAsBytes.length + 1] = 39;
      this.setInternal(parameterIndex, parameterWithQuotes);
   }

   public void setBytesNoEscapeNoQuotes(int parameterIndex, byte[] parameterAsBytes) throws SQLException {
      this.setInternal(parameterIndex, parameterAsBytes);
   }

   public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            try {
               if (reader == null) {
                  this.setNull(parameterIndex, MysqlType.TEXT);
               } else {
                  char[] c = null;
                  int len = false;
                  boolean useLength = (Boolean)this.useStreamLengthsInPrepStmts.getValue();
                  String forcedEncoding = this.session.getPropertySet().getStringReadableProperty("clobCharacterEncoding").getStringValue();
                  char[] c;
                  if (useLength && length != -1) {
                     c = new char[length];
                     int numCharsRead = readFully(reader, c, length);
                     if (forcedEncoding == null) {
                        this.setString(parameterIndex, new String(c, 0, numCharsRead));
                     } else {
                        this.setBytes(parameterIndex, StringUtils.getBytes(new String(c, 0, numCharsRead), forcedEncoding));
                     }
                  } else {
                     c = new char[4096];
                     StringBuilder buf = new StringBuilder();

                     int len;
                     while((len = reader.read(c)) != -1) {
                        buf.append(c, 0, len);
                     }

                     if (forcedEncoding == null) {
                        this.setString(parameterIndex, buf.toString());
                     } else {
                        this.setBytes(parameterIndex, StringUtils.getBytes(buf.toString(), forcedEncoding));
                     }
                  }

                  this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.TEXT;
               }
            } catch (UnsupportedEncodingException var12) {
               throw SQLError.createSQLException(var12.toString(), "S1009", var12, this.getExceptionInterceptor());
            } catch (IOException var13) {
               throw SQLError.createSQLException(var13.toString(), "S1000", var13, this.getExceptionInterceptor());
            }

         }
      } catch (CJException var15) {
         throw SQLExceptionsMapping.translateException(var15, this.getExceptionInterceptor());
      }
   }

   public void setClob(int i, java.sql.Clob x) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (x == null) {
               this.setNull(i, MysqlType.TEXT);
            } else {
               String forcedEncoding = this.session.getPropertySet().getStringReadableProperty("clobCharacterEncoding").getStringValue();
               if (forcedEncoding == null) {
                  this.setString(i, x.getSubString(1L, (int)x.length()));
               } else {
                  this.setBytes(i, StringUtils.getBytes(x.getSubString(1L, (int)x.length()), forcedEncoding));
               }

               this.parameterTypes[i - 1 + this.getParameterIndexOffset()] = MysqlType.TEXT;
            }

         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   public void setDate(int parameterIndex, java.sql.Date x) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.setDateInternal(parameterIndex, x, this.session.getDefaultTimeZone());
         }
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   public void setDate(int parameterIndex, java.sql.Date x, Calendar cal) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.setDateInternal(parameterIndex, x, cal.getTimeZone());
         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   private void setDateInternal(int parameterIndex, java.sql.Date x, TimeZone tz) throws SQLException {
      if (x == null) {
         this.setNull(parameterIndex, MysqlType.DATE);
      } else {
         if (this.ddf == null) {
            this.ddf = new SimpleDateFormat("''yyyy-MM-dd''", Locale.US);
         }

         this.ddf.setTimeZone(tz);
         this.setInternal(parameterIndex, this.ddf.format(x));
      }

   }

   public void setDouble(int parameterIndex, double x) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if ((Boolean)this.session.getPropertySet().getBooleanReadableProperty("allowNanAndInf").getValue() || x != Double.POSITIVE_INFINITY && x != Double.NEGATIVE_INFINITY && !Double.isNaN(x)) {
               this.setInternal(parameterIndex, StringUtils.fixDecimalExponent(String.valueOf(x)));
               this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.DOUBLE;
            } else {
               throw SQLError.createSQLException(Messages.getString("PreparedStatement.64", new Object[]{x}), "S1009", this.getExceptionInterceptor());
            }
         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   public void setFloat(int parameterIndex, float x) throws SQLException {
      try {
         this.setInternal(parameterIndex, StringUtils.fixDecimalExponent(String.valueOf(x)));
         this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.FLOAT;
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setInt(int parameterIndex, int x) throws SQLException {
      try {
         this.setInternal(parameterIndex, String.valueOf(x));
         this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.INT;
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   protected final void setInternal(int paramIndex, byte[] val) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            int parameterIndexOffset = this.getParameterIndexOffset();
            this.checkBounds(paramIndex, parameterIndexOffset);
            this.isStream[paramIndex - 1 + parameterIndexOffset] = false;
            this.isNull[paramIndex - 1 + parameterIndexOffset] = false;
            this.parameterStreams[paramIndex - 1 + parameterIndexOffset] = null;
            this.parameterValues[paramIndex - 1 + parameterIndexOffset] = val;
         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   protected void checkBounds(int paramIndex, int parameterIndexOffset) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (paramIndex < 1) {
               throw SQLError.createSQLException(Messages.getString("PreparedStatement.49") + paramIndex + Messages.getString("PreparedStatement.50"), "S1009", this.getExceptionInterceptor());
            } else if (paramIndex > this.parameterCount) {
               throw SQLError.createSQLException(Messages.getString("PreparedStatement.51") + paramIndex + Messages.getString("PreparedStatement.52") + this.parameterValues.length + Messages.getString("PreparedStatement.53"), "S1009", this.getExceptionInterceptor());
            } else if (parameterIndexOffset == -1 && paramIndex == 1) {
               throw SQLError.createSQLException(Messages.getString("PreparedStatement.63"), "S1009", this.getExceptionInterceptor());
            }
         }
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   protected final void setInternal(int paramIndex, String val) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            byte[] parameterAsBytes = null;
            byte[] parameterAsBytes = StringUtils.getBytes(val, this.charEncoding);
            this.setInternal(paramIndex, parameterAsBytes);
         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   public void setLong(int parameterIndex, long x) throws SQLException {
      try {
         this.setInternal(parameterIndex, String.valueOf(x));
         this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.BIGINT;
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void setBigInteger(int parameterIndex, BigInteger x) throws SQLException {
      this.setInternal(parameterIndex, x.toString());
      this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.BIGINT_UNSIGNED;
   }

   public void setNull(int parameterIndex, int sqlType) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.setInternal(parameterIndex, "null");
            this.isNull[parameterIndex - 1 + this.getParameterIndexOffset()] = true;
            this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.NULL;
         }
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   public void setNull(int parameterIndex, MysqlType mysqlType) throws SQLException {
      this.setNull(parameterIndex, mysqlType.getJdbcType());
   }

   public void setNull(int parameterIndex, int sqlType, String arg) throws SQLException {
      try {
         this.setNull(parameterIndex, sqlType);
         this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.NULL;
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   private void setNumericObject(int parameterIndex, Object parameterObj, MysqlType targetMysqlType, int scale) throws SQLException {
      Object parameterAsNum;
      if (parameterObj instanceof Boolean) {
         parameterAsNum = (Boolean)parameterObj ? 1 : 0;
      } else if (parameterObj instanceof String) {
         switch(targetMysqlType) {
         case BIT:
            if (!"1".equals(parameterObj) && !"0".equals(parameterObj)) {
               boolean parameterAsBoolean = "true".equalsIgnoreCase((String)parameterObj);
               parameterAsNum = parameterAsBoolean ? 1 : 0;
            } else {
               parameterAsNum = Integer.valueOf((String)parameterObj);
            }
            break;
         case TINYINT:
         case TINYINT_UNSIGNED:
         case SMALLINT:
         case SMALLINT_UNSIGNED:
         case INT:
         case INT_UNSIGNED:
            parameterAsNum = Integer.valueOf((String)parameterObj);
            break;
         case BIGINT:
            parameterAsNum = Long.valueOf((String)parameterObj);
            break;
         case BIGINT_UNSIGNED:
            parameterAsNum = new BigInteger((String)parameterObj);
            break;
         case FLOAT:
         case FLOAT_UNSIGNED:
            parameterAsNum = Float.valueOf((String)parameterObj);
            break;
         case DOUBLE:
         case DOUBLE_UNSIGNED:
            parameterAsNum = Double.valueOf((String)parameterObj);
            break;
         case DECIMAL:
         case DECIMAL_UNSIGNED:
         default:
            parameterAsNum = new BigDecimal((String)parameterObj);
         }
      } else {
         parameterAsNum = (Number)parameterObj;
      }

      switch(targetMysqlType) {
      case BIT:
      case TINYINT:
      case TINYINT_UNSIGNED:
      case SMALLINT:
      case SMALLINT_UNSIGNED:
      case INT:
      case INT_UNSIGNED:
         this.setInt(parameterIndex, ((Number)parameterAsNum).intValue());
         break;
      case BIGINT:
      case BIGINT_UNSIGNED:
         this.setLong(parameterIndex, ((Number)parameterAsNum).longValue());
         break;
      case FLOAT:
      case FLOAT_UNSIGNED:
         this.setFloat(parameterIndex, ((Number)parameterAsNum).floatValue());
         break;
      case DOUBLE:
      case DOUBLE_UNSIGNED:
         this.setDouble(parameterIndex, ((Number)parameterAsNum).doubleValue());
         break;
      case DECIMAL:
      case DECIMAL_UNSIGNED:
         if (parameterAsNum instanceof BigDecimal) {
            BigDecimal scaledBigDecimal = null;

            try {
               scaledBigDecimal = ((BigDecimal)parameterAsNum).setScale(scale);
            } catch (ArithmeticException var10) {
               try {
                  scaledBigDecimal = ((BigDecimal)parameterAsNum).setScale(scale, 4);
               } catch (ArithmeticException var9) {
                  throw SQLError.createSQLException(Messages.getString("PreparedStatement.65", new Object[]{scale, parameterAsNum}), "S1009", this.getExceptionInterceptor());
               }
            }

            this.setBigDecimal(parameterIndex, scaledBigDecimal);
         } else if (parameterAsNum instanceof BigInteger) {
            this.setBigDecimal(parameterIndex, new BigDecimal((BigInteger)parameterAsNum, scale));
         } else {
            this.setBigDecimal(parameterIndex, new BigDecimal(((Number)parameterAsNum).doubleValue()));
         }
      }

   }

   public void setObject(int parameterIndex, Object parameterObj) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (parameterObj == null) {
               this.setNull(parameterIndex, MysqlType.UNKNOWN);
            } else if (parameterObj instanceof Byte) {
               this.setInt(parameterIndex, ((Byte)parameterObj).intValue());
            } else if (parameterObj instanceof String) {
               this.setString(parameterIndex, (String)parameterObj);
            } else if (parameterObj instanceof BigDecimal) {
               this.setBigDecimal(parameterIndex, (BigDecimal)parameterObj);
            } else if (parameterObj instanceof Short) {
               this.setShort(parameterIndex, (Short)parameterObj);
            } else if (parameterObj instanceof Integer) {
               this.setInt(parameterIndex, (Integer)parameterObj);
            } else if (parameterObj instanceof Long) {
               this.setLong(parameterIndex, (Long)parameterObj);
            } else if (parameterObj instanceof Float) {
               this.setFloat(parameterIndex, (Float)parameterObj);
            } else if (parameterObj instanceof Double) {
               this.setDouble(parameterIndex, (Double)parameterObj);
            } else if (parameterObj instanceof byte[]) {
               this.setBytes(parameterIndex, (byte[])((byte[])parameterObj));
            } else if (parameterObj instanceof java.sql.Date) {
               this.setDate(parameterIndex, (java.sql.Date)parameterObj);
            } else if (parameterObj instanceof Time) {
               this.setTime(parameterIndex, (Time)parameterObj);
            } else if (parameterObj instanceof Timestamp) {
               this.setTimestamp(parameterIndex, (Timestamp)parameterObj);
            } else if (parameterObj instanceof Boolean) {
               this.setBoolean(parameterIndex, (Boolean)parameterObj);
            } else if (parameterObj instanceof InputStream) {
               this.setBinaryStream(parameterIndex, (InputStream)parameterObj, -1);
            } else if (parameterObj instanceof java.sql.Blob) {
               this.setBlob(parameterIndex, (java.sql.Blob)parameterObj);
            } else if (parameterObj instanceof java.sql.Clob) {
               this.setClob(parameterIndex, (java.sql.Clob)parameterObj);
            } else if ((Boolean)this.treatUtilDateAsTimestamp.getValue() && parameterObj instanceof Date) {
               this.setTimestamp(parameterIndex, new Timestamp(((Date)parameterObj).getTime()));
            } else if (parameterObj instanceof BigInteger) {
               this.setString(parameterIndex, parameterObj.toString());
            } else if (parameterObj instanceof LocalDate) {
               this.setDate(parameterIndex, java.sql.Date.valueOf((LocalDate)parameterObj));
            } else if (parameterObj instanceof LocalDateTime) {
               this.setTimestamp(parameterIndex, Timestamp.valueOf((LocalDateTime)parameterObj));
            } else if (parameterObj instanceof LocalTime) {
               this.setTime(parameterIndex, Time.valueOf((LocalTime)parameterObj));
            } else {
               this.setSerializableObject(parameterIndex, parameterObj);
            }

         }
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   public void setObject(int parameterIndex, Object parameterObj, int targetSqlType) throws SQLException {
      try {
         this.setObject(parameterIndex, parameterObj, targetSqlType, parameterObj instanceof BigDecimal ? ((BigDecimal)parameterObj).scale() : 0);
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void setObject(int parameterIndex, Object parameterObj, SQLType targetSqlType) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (targetSqlType instanceof MysqlType) {
               this.setObject(parameterIndex, parameterObj, (MysqlType)targetSqlType, parameterObj instanceof BigDecimal ? ((BigDecimal)parameterObj).scale() : 0);
            } else {
               this.setObject(parameterIndex, parameterObj, targetSqlType.getVendorTypeNumber());
            }

         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   protected void setObject(int parameterIndex, Object parameterObj, MysqlType targetMysqlType, int scaleOrLength) throws SQLException {
      synchronized(this.checkClosed().getConnectionMutex()) {
         if (parameterObj == null) {
            this.setNull(parameterIndex, MysqlType.UNKNOWN);
         } else {
            if (parameterObj instanceof LocalDate) {
               parameterObj = java.sql.Date.valueOf((LocalDate)parameterObj);
            } else if (parameterObj instanceof LocalDateTime) {
               parameterObj = Timestamp.valueOf((LocalDateTime)parameterObj);
            } else if (parameterObj instanceof LocalTime) {
               parameterObj = Time.valueOf((LocalTime)parameterObj);
            }

            try {
               switch(targetMysqlType) {
               case BIT:
               case TINYINT:
               case TINYINT_UNSIGNED:
               case SMALLINT:
               case SMALLINT_UNSIGNED:
               case INT:
               case INT_UNSIGNED:
               case BIGINT:
               case BIGINT_UNSIGNED:
               case FLOAT:
               case FLOAT_UNSIGNED:
               case DOUBLE:
               case DOUBLE_UNSIGNED:
               case DECIMAL:
               case DECIMAL_UNSIGNED:
                  this.setNumericObject(parameterIndex, parameterObj, targetMysqlType, scaleOrLength);
                  break;
               case DATE:
               case TIMESTAMP:
                  Date parameterAsDate;
                  if (parameterObj instanceof String) {
                     ParsePosition pp = new ParsePosition(0);
                     DateFormat sdf = new SimpleDateFormat(this.getDateTimePattern((String)parameterObj, false), Locale.US);
                     parameterAsDate = sdf.parse((String)parameterObj, pp);
                  } else {
                     parameterAsDate = (Date)parameterObj;
                  }

                  switch(targetMysqlType) {
                  case DATE:
                     if (parameterAsDate instanceof java.sql.Date) {
                        this.setDate(parameterIndex, (java.sql.Date)parameterAsDate);
                     } else {
                        this.setDate(parameterIndex, new java.sql.Date(parameterAsDate.getTime()));
                     }

                     return;
                  case TIMESTAMP:
                     if (parameterAsDate instanceof Timestamp) {
                        this.setTimestamp(parameterIndex, (Timestamp)parameterAsDate);
                     } else {
                        this.setTimestamp(parameterIndex, new Timestamp(parameterAsDate.getTime()));
                     }

                     return;
                  default:
                     return;
                  }
               case BOOLEAN:
                  if (parameterObj instanceof Boolean) {
                     this.setBoolean(parameterIndex, (Boolean)parameterObj);
                  } else if (parameterObj instanceof String) {
                     this.setBoolean(parameterIndex, "true".equalsIgnoreCase((String)parameterObj) || !"0".equalsIgnoreCase((String)parameterObj));
                  } else {
                     if (!(parameterObj instanceof Number)) {
                        throw SQLError.createSQLException(Messages.getString("PreparedStatement.66", new Object[]{parameterObj.getClass().getName()}), "S1009", this.getExceptionInterceptor());
                     }

                     int intValue = ((Number)parameterObj).intValue();
                     this.setBoolean(parameterIndex, intValue != 0);
                  }
                  break;
               case CHAR:
               case ENUM:
               case SET:
               case VARCHAR:
               case TINYTEXT:
               case TEXT:
               case MEDIUMTEXT:
               case LONGTEXT:
               case JSON:
                  if (parameterObj instanceof BigDecimal) {
                     this.setString(parameterIndex, StringUtils.fixDecimalExponent(((BigDecimal)parameterObj).toPlainString()));
                  } else if (parameterObj instanceof java.sql.Clob) {
                     this.setClob(parameterIndex, (java.sql.Clob)parameterObj);
                  } else {
                     this.setString(parameterIndex, parameterObj.toString());
                  }
                  break;
               case BINARY:
               case GEOMETRY:
               case VARBINARY:
               case TINYBLOB:
               case BLOB:
               case MEDIUMBLOB:
               case LONGBLOB:
                  if (parameterObj instanceof byte[]) {
                     this.setBytes(parameterIndex, (byte[])((byte[])parameterObj));
                  } else if (parameterObj instanceof java.sql.Blob) {
                     this.setBlob(parameterIndex, (java.sql.Blob)parameterObj);
                  } else {
                     this.setBytes(parameterIndex, StringUtils.getBytes(parameterObj.toString(), this.charEncoding));
                  }
                  break;
               case TIME:
                  if (parameterObj instanceof String) {
                     DateFormat sdf = new SimpleDateFormat(this.getDateTimePattern((String)parameterObj, true), Locale.US);
                     this.setTime(parameterIndex, new Time(sdf.parse((String)parameterObj).getTime()));
                  } else if (parameterObj instanceof Timestamp) {
                     Timestamp xT = (Timestamp)parameterObj;
                     this.setTime(parameterIndex, new Time(xT.getTime()));
                  } else {
                     this.setTime(parameterIndex, (Time)parameterObj);
                  }
                  break;
               case UNKNOWN:
                  this.setSerializableObject(parameterIndex, parameterObj);
                  break;
               default:
                  throw SQLError.createSQLException(Messages.getString("PreparedStatement.16"), "S1000", this.getExceptionInterceptor());
               }
            } catch (Exception var10) {
               throw SQLError.createSQLException(Messages.getString("PreparedStatement.17") + parameterObj.getClass().toString() + Messages.getString("PreparedStatement.18") + var10.getClass().getName() + Messages.getString("PreparedStatement.19") + var10.getMessage(), "S1000", var10, this.getExceptionInterceptor());
            }
         }

      }
   }

   public void setObject(int parameterIndex, Object parameterObj, int targetSqlType, int scale) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            try {
               MysqlType targetMysqlType = MysqlType.getByJdbcType(targetSqlType);
               this.setObject(parameterIndex, parameterObj, targetMysqlType, scale);
            } catch (FeatureNotAvailableException var9) {
               throw SQLError.createSQLFeatureNotSupportedException(Messages.getString("Statement.UnsupportedSQLType") + JDBCType.valueOf(targetSqlType), "S1C00", this.getExceptionInterceptor());
            }

         }
      } catch (CJException var11) {
         throw SQLExceptionsMapping.translateException(var11, this.getExceptionInterceptor());
      }
   }

   public void setObject(int parameterIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (targetSqlType instanceof MysqlType) {
               this.setObject(parameterIndex, x, (MysqlType)targetSqlType, scaleOrLength);
            } else {
               this.setObject(parameterIndex, x, targetSqlType.getVendorTypeNumber(), scaleOrLength);
            }

         }
      } catch (CJException var9) {
         throw SQLExceptionsMapping.translateException(var9, this.getExceptionInterceptor());
      }
   }

   protected int setOneBatchedParameterSet(java.sql.PreparedStatement batchedStatement, int batchedParamIndex, Object paramSet) throws SQLException {
      PreparedStatement.BatchParams paramArg = (PreparedStatement.BatchParams)paramSet;
      boolean[] isNullBatch = paramArg.isNull;
      boolean[] isStreamBatch = paramArg.isStream;

      for(int j = 0; j < isNullBatch.length; ++j) {
         if (isNullBatch[j]) {
            batchedStatement.setNull(batchedParamIndex++, MysqlType.NULL.getJdbcType());
         } else if (isStreamBatch[j]) {
            batchedStatement.setBinaryStream(batchedParamIndex++, paramArg.parameterStreams[j], paramArg.streamLengths[j]);
         } else {
            ((PreparedStatement)batchedStatement).setBytesNoEscapeNoQuotes(batchedParamIndex++, paramArg.parameterStrings[j]);
         }
      }

      return batchedParamIndex;
   }

   public void setRef(int i, Ref x) throws SQLException {
      try {
         throw SQLError.createSQLFeatureNotSupportedException();
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   private final void setSerializableObject(int parameterIndex, Object parameterObj) throws SQLException {
      try {
         ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
         ObjectOutputStream objectOut = new ObjectOutputStream(bytesOut);
         objectOut.writeObject(parameterObj);
         objectOut.flush();
         objectOut.close();
         bytesOut.flush();
         bytesOut.close();
         byte[] buf = bytesOut.toByteArray();
         ByteArrayInputStream bytesIn = new ByteArrayInputStream(buf);
         this.setBinaryStream(parameterIndex, bytesIn, buf.length);
         this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.BINARY;
      } catch (Exception var7) {
         throw SQLError.createSQLException(Messages.getString("PreparedStatement.54") + var7.getClass().getName(), "S1009", var7, this.getExceptionInterceptor());
      }
   }

   public void setShort(int parameterIndex, short x) throws SQLException {
      try {
         this.setInternal(parameterIndex, String.valueOf(x));
         this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.SMALLINT;
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setString(int parameterIndex, String x) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (x == null) {
               this.setNull(parameterIndex, MysqlType.VARCHAR);
            } else {
               this.checkClosed();
               int stringLength = x.length();
               StringBuilder buf;
               if (this.connection.isNoBackslashEscapesSet()) {
                  boolean needsHexEscape = this.isEscapeNeededForString(x, stringLength);
                  Object parameterAsBytes;
                  byte[] parameterAsBytes;
                  if (!needsHexEscape) {
                     parameterAsBytes = null;
                     buf = new StringBuilder(x.length() + 2);
                     buf.append('\'');
                     buf.append(x);
                     buf.append('\'');
                     if (!this.isLoadDataQuery) {
                        parameterAsBytes = StringUtils.getBytes(buf.toString(), this.charEncoding);
                     } else {
                        parameterAsBytes = StringUtils.getBytes(buf.toString());
                     }

                     this.setInternal(parameterIndex, parameterAsBytes);
                  } else {
                     parameterAsBytes = null;
                     if (!this.isLoadDataQuery) {
                        parameterAsBytes = StringUtils.getBytes(x, this.charEncoding);
                     } else {
                        parameterAsBytes = StringUtils.getBytes(x);
                     }

                     this.setBytes(parameterIndex, parameterAsBytes);
                  }

                  return;
               }

               String parameterAsString = x;
               boolean needsQuoted = true;
               if (this.isLoadDataQuery || this.isEscapeNeededForString(x, stringLength)) {
                  needsQuoted = false;
                  buf = new StringBuilder((int)((double)x.length() * 1.1D));
                  buf.append('\'');

                  for(int i = 0; i < stringLength; ++i) {
                     char c = x.charAt(i);
                     switch(c) {
                     case '\u0000':
                        buf.append('\\');
                        buf.append('0');
                        break;
                     case '\n':
                        buf.append('\\');
                        buf.append('n');
                        break;
                     case '\r':
                        buf.append('\\');
                        buf.append('r');
                        break;
                     case '\u001a':
                        buf.append('\\');
                        buf.append('Z');
                        break;
                     case '"':
                        if (this.usingAnsiMode) {
                           buf.append('\\');
                        }

                        buf.append('"');
                        break;
                     case '\'':
                        buf.append('\\');
                        buf.append('\'');
                        break;
                     case '\\':
                        buf.append('\\');
                        buf.append('\\');
                        break;
                     case '¥':
                     case '₩':
                        if (this.charsetEncoder != null) {
                           CharBuffer cbuf = CharBuffer.allocate(1);
                           ByteBuffer bbuf = ByteBuffer.allocate(1);
                           cbuf.put(c);
                           cbuf.position(0);
                           this.charsetEncoder.encode(cbuf, bbuf, true);
                           if (bbuf.get(0) == 92) {
                              buf.append('\\');
                           }
                        }

                        buf.append(c);
                        break;
                     default:
                        buf.append(c);
                     }
                  }

                  buf.append('\'');
                  parameterAsString = buf.toString();
               }

               buf = null;
               byte[] parameterAsBytes;
               if (!this.isLoadDataQuery) {
                  if (needsQuoted) {
                     parameterAsBytes = StringUtils.getBytesWrapped(parameterAsString, '\'', '\'', this.charEncoding);
                  } else {
                     parameterAsBytes = StringUtils.getBytes(parameterAsString, this.charEncoding);
                  }
               } else {
                  parameterAsBytes = StringUtils.getBytes(parameterAsString);
               }

               this.setInternal(parameterIndex, parameterAsBytes);
               this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.VARCHAR;
            }

         }
      } catch (CJException var15) {
         throw SQLExceptionsMapping.translateException(var15, this.getExceptionInterceptor());
      }
   }

   private boolean isEscapeNeededForString(String x, int stringLength) {
      boolean needsHexEscape = false;

      for(int i = 0; i < stringLength; ++i) {
         char c = x.charAt(i);
         switch(c) {
         case '\u0000':
            needsHexEscape = true;
            break;
         case '\n':
            needsHexEscape = true;
            break;
         case '\r':
            needsHexEscape = true;
            break;
         case '\u001a':
            needsHexEscape = true;
            break;
         case '"':
            needsHexEscape = true;
            break;
         case '\'':
            needsHexEscape = true;
            break;
         case '\\':
            needsHexEscape = true;
         }

         if (needsHexEscape) {
            break;
         }
      }

      return needsHexEscape;
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

   public void setTime(int parameterIndex, Time x) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.setTimeInternal(parameterIndex, x, this.session.getDefaultTimeZone());
         }
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   private void setTimeInternal(int parameterIndex, Time x, TimeZone tz) throws SQLException {
      if (x == null) {
         this.setNull(parameterIndex, MysqlType.TIME);
      } else {
         this.checkClosed();
         if (this.tdf == null) {
            this.tdf = new SimpleDateFormat("''HH:mm:ss''", Locale.US);
         }

         this.tdf.setTimeZone(tz);
         this.setInternal(parameterIndex, this.tdf.format(x));
         this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.TIME;
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

   public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            this.setTimestampInternal(parameterIndex, x, this.session.getDefaultTimeZone());
         }
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   private void setTimestampInternal(int parameterIndex, Timestamp x, TimeZone tz) throws SQLException {
      if (x == null) {
         this.setNull(parameterIndex, MysqlType.TIMESTAMP);
      } else {
         if (!(Boolean)this.sendFractionalSeconds.getValue()) {
            x = TimeUtil.truncateFractionalSeconds(x);
         }

         this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.TIMESTAMP;
         if (this.tsdf == null) {
            this.tsdf = new SimpleDateFormat("''yyyy-MM-dd HH:mm:ss", Locale.US);
         }

         this.tsdf.setTimeZone(tz);
         StringBuffer buf = new StringBuffer();
         buf.append(this.tsdf.format(x));
         if (this.session.serverSupportsFracSecs()) {
            buf.append('.');
            buf.append(TimeUtil.formatNanos(x.getNanos(), true));
         }

         buf.append('\'');
         this.setInternal(parameterIndex, buf.toString());
      }

   }

   /** @deprecated */
   @Deprecated
   public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
      try {
         if (x == null) {
            this.setNull(parameterIndex, MysqlType.TEXT);
         } else {
            this.setBinaryStream(parameterIndex, x, length);
            this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.TEXT;
         }

      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void setURL(int parameterIndex, URL arg) throws SQLException {
      try {
         if (arg == null) {
            this.setNull(parameterIndex, MysqlType.VARCHAR);
         } else {
            this.setString(parameterIndex, arg.toString());
            this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.VARCHAR;
         }

      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   private final void streamToBytes(PacketPayload packet, InputStream in, boolean escape, int streamLength, boolean useLength) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            try {
               if (this.streamConvertBuf == null) {
                  this.streamConvertBuf = new byte[4096];
               }

               boolean hexEscape = false;

               try {
                  if (this.connection.isNoBackslashEscapesSet()) {
                     hexEscape = true;
                  }
               } catch (RuntimeException var21) {
                  throw SQLError.createSQLException(var21.toString(), "S1009", var21, (ExceptionInterceptor)null);
               }

               if (streamLength == -1) {
                  useLength = false;
               }

               int bc = true;
               int bc;
               if (useLength) {
                  bc = this.readblock(in, this.streamConvertBuf, streamLength);
               } else {
                  bc = this.readblock(in, this.streamConvertBuf);
               }

               int lengthLeftToRead = streamLength - bc;
               if (hexEscape) {
                  packet.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, StringUtils.getBytes("x"));
               } else {
                  packet.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, StringUtils.getBytes("_binary"));
               }

               if (escape) {
                  packet.writeInteger(NativeProtocol.IntegerDataType.INT1, 39L);
               }

               while(bc > 0) {
                  if (hexEscape) {
                     this.hexEscapeBlock(this.streamConvertBuf, packet, bc);
                  } else if (escape) {
                     this.escapeblockFast(this.streamConvertBuf, packet, bc);
                  } else {
                     packet.writeBytes((NativeProtocol.StringLengthDataType)NativeProtocol.StringLengthDataType.STRING_FIXED, this.streamConvertBuf, 0, bc);
                  }

                  if (useLength) {
                     bc = this.readblock(in, this.streamConvertBuf, lengthLeftToRead);
                     if (bc > 0) {
                        lengthLeftToRead -= bc;
                     }
                  } else {
                     bc = this.readblock(in, this.streamConvertBuf);
                  }
               }

               if (escape) {
                  packet.writeInteger(NativeProtocol.IntegerDataType.INT1, 39L);
               }
            } finally {
               if ((Boolean)this.autoClosePStmtStreams.getValue()) {
                  try {
                     in.close();
                  } catch (IOException var20) {
                  }

                  in = null;
               }

            }

         }
      } catch (CJException var24) {
         throw SQLExceptionsMapping.translateException(var24, this.getExceptionInterceptor());
      }
   }

   private final byte[] streamToBytes(InputStream in, boolean escape, int streamLength, boolean useLength) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            in.mark(Integer.MAX_VALUE);

            try {
               if (this.streamConvertBuf == null) {
                  this.streamConvertBuf = new byte[4096];
               }

               if (streamLength == -1) {
                  useLength = false;
               }

               ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
               int bc = true;
               int bc;
               if (useLength) {
                  bc = this.readblock(in, this.streamConvertBuf, streamLength);
               } else {
                  bc = this.readblock(in, this.streamConvertBuf);
               }

               int lengthLeftToRead = streamLength - bc;
               if (escape) {
                  bytesOut.write(95);
                  bytesOut.write(98);
                  bytesOut.write(105);
                  bytesOut.write(110);
                  bytesOut.write(97);
                  bytesOut.write(114);
                  bytesOut.write(121);
                  bytesOut.write(39);
               }

               while(bc > 0) {
                  if (escape) {
                     this.escapeblockFast(this.streamConvertBuf, bytesOut, bc);
                  } else {
                     bytesOut.write(this.streamConvertBuf, 0, bc);
                  }

                  if (useLength) {
                     bc = this.readblock(in, this.streamConvertBuf, lengthLeftToRead);
                     if (bc > 0) {
                        lengthLeftToRead -= bc;
                     }
                  } else {
                     bc = this.readblock(in, this.streamConvertBuf);
                  }
               }

               if (escape) {
                  bytesOut.write(39);
               }

               byte[] var9 = bytesOut.toByteArray();
               return var9;
            } finally {
               try {
                  in.reset();
               } catch (IOException var23) {
               }

               if ((Boolean)this.autoClosePStmtStreams.getValue()) {
                  try {
                     in.close();
                  } catch (IOException var22) {
                  }

                  in = null;
               }

            }
         }
      } catch (CJException var26) {
         throw SQLExceptionsMapping.translateException(var26, this.getExceptionInterceptor());
      }
   }

   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append(super.toString());
      buf.append(": ");

      try {
         buf.append(this.asSql());
      } catch (SQLException var3) {
         buf.append("EXCEPTION: " + var3.toString());
      }

      return buf.toString();
   }

   protected int getParameterIndexOffset() {
      return 0;
   }

   public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
      try {
         this.setAsciiStream(parameterIndex, x, -1);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
      try {
         this.setAsciiStream(parameterIndex, x, (int)length);
         this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.TEXT;
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
      try {
         this.setBinaryStream(parameterIndex, x, -1);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
      try {
         this.setBinaryStream(parameterIndex, x, (int)length);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
      try {
         this.setBinaryStream(parameterIndex, inputStream);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
      try {
         this.setCharacterStream(parameterIndex, reader, -1);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
      try {
         this.setCharacterStream(parameterIndex, reader, (int)length);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void setClob(int parameterIndex, Reader reader) throws SQLException {
      try {
         this.setCharacterStream(parameterIndex, reader);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
      try {
         this.setCharacterStream(parameterIndex, reader, length);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
      try {
         this.setNCharacterStream(parameterIndex, value, -1L);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setNString(int parameterIndex, String x) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            if (!this.charEncoding.equalsIgnoreCase("UTF-8") && !this.charEncoding.equalsIgnoreCase("utf8")) {
               if (x == null) {
                  this.setNull(parameterIndex, MysqlType.VARCHAR);
               } else {
                  int stringLength = x.length();
                  StringBuilder buf = new StringBuilder((int)((double)x.length() * 1.1D + 4.0D));
                  buf.append("_utf8");
                  buf.append('\'');

                  for(int i = 0; i < stringLength; ++i) {
                     char c = x.charAt(i);
                     switch(c) {
                     case '\u0000':
                        buf.append('\\');
                        buf.append('0');
                        break;
                     case '\n':
                        buf.append('\\');
                        buf.append('n');
                        break;
                     case '\r':
                        buf.append('\\');
                        buf.append('r');
                        break;
                     case '\u001a':
                        buf.append('\\');
                        buf.append('Z');
                        break;
                     case '"':
                        if (this.usingAnsiMode) {
                           buf.append('\\');
                        }

                        buf.append('"');
                        break;
                     case '\'':
                        buf.append('\\');
                        buf.append('\'');
                        break;
                     case '\\':
                        buf.append('\\');
                        buf.append('\\');
                        break;
                     default:
                        buf.append(c);
                     }
                  }

                  buf.append('\'');
                  String parameterAsString = buf.toString();
                  byte[] parameterAsBytes = null;
                  byte[] parameterAsBytes;
                  if (!this.isLoadDataQuery) {
                     parameterAsBytes = StringUtils.getBytes(parameterAsString, "UTF-8");
                  } else {
                     parameterAsBytes = StringUtils.getBytes(parameterAsString);
                  }

                  this.setInternal(parameterIndex, parameterAsBytes);
                  this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.VARCHAR;
               }

            } else {
               this.setString(parameterIndex, x);
            }
         }
      } catch (CJException var11) {
         throw SQLExceptionsMapping.translateException(var11, this.getExceptionInterceptor());
      }
   }

   public void setNCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            try {
               if (reader == null) {
                  this.setNull(parameterIndex, MysqlType.TEXT);
               } else {
                  char[] c = null;
                  int len = false;
                  boolean useLength = (Boolean)this.useStreamLengthsInPrepStmts.getValue();
                  char[] c;
                  if (useLength && length != -1L) {
                     c = new char[(int)length];
                     int numCharsRead = readFully(reader, c, (int)length);
                     this.setNString(parameterIndex, new String(c, 0, numCharsRead));
                  } else {
                     c = new char[4096];
                     StringBuilder buf = new StringBuilder();

                     int len;
                     while((len = reader.read(c)) != -1) {
                        buf.append(c, 0, len);
                     }

                     this.setNString(parameterIndex, buf.toString());
                  }

                  this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = MysqlType.TEXT;
               }
            } catch (IOException var12) {
               throw SQLError.createSQLException(var12.toString(), "S1000", this.getExceptionInterceptor());
            }

         }
      } catch (CJException var14) {
         throw SQLExceptionsMapping.translateException(var14, this.getExceptionInterceptor());
      }
   }

   public void setNClob(int parameterIndex, Reader reader) throws SQLException {
      try {
         this.setNCharacterStream(parameterIndex, reader);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
      try {
         if (reader == null) {
            this.setNull(parameterIndex, MysqlType.TEXT);
         } else {
            this.setNCharacterStream(parameterIndex, reader, length);
         }

      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public ParameterBindings getParameterBindings() throws SQLException {
      try {
         synchronized(this.checkClosed().getConnectionMutex()) {
            return new PreparedStatement.EmulatedPreparedStatementBindings();
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public String getPreparedSql() {
      synchronized(this.checkClosed().getConnectionMutex()) {
         if (this.rewrittenBatchSize == 0) {
            return this.originalSql;
         } else {
            String var10000;
            try {
               var10000 = this.parseInfo.getSqlForBatch(this.parseInfo);
            } catch (UnsupportedEncodingException var4) {
               throw new RuntimeException(var4);
            }

            return var10000;
         }
      }
   }

   public int getUpdateCount() throws SQLException {
      try {
         int count = super.getUpdateCount();
         if (this.containsOnDuplicateKeyUpdateInSQL() && this.compensateForOnDuplicateKeyUpdate && (count == 2 || count == 0)) {
            count = 1;
         }

         return count;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   protected static boolean canRewrite(String sql, boolean isOnDuplicateKeyUpdate, int locationOfOnDuplicateKeyUpdate, int statementStartPos) {
      if (StringUtils.startsWithIgnoreCaseAndWs(sql, "INSERT", statementStartPos)) {
         if (StringUtils.indexOfIgnoreCase(statementStartPos, sql, "SELECT", "\"'`", "\"'`", StringUtils.SEARCH_MODE__MRK_COM_WS) != -1) {
            return false;
         } else {
            if (isOnDuplicateKeyUpdate) {
               int updateClausePos = StringUtils.indexOfIgnoreCase(locationOfOnDuplicateKeyUpdate, sql, " UPDATE ");
               if (updateClausePos != -1) {
                  return StringUtils.indexOfIgnoreCase(updateClausePos, sql, "LAST_INSERT_ID", "\"'`", "\"'`", StringUtils.SEARCH_MODE__MRK_COM_WS) == -1;
               }
            }

            return true;
         }
      } else {
         return StringUtils.startsWithIgnoreCaseAndWs(sql, "REPLACE", statementStartPos) && StringUtils.indexOfIgnoreCase(statementStartPos, sql, "SELECT", "\"'`", "\"'`", StringUtils.SEARCH_MODE__MRK_COM_WS) == -1;
      }
   }

   public void setRowId(int parameterIndex, RowId x) throws SQLException {
      try {
         throw SQLError.createSQLFeatureNotSupportedException();
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setNClob(int parameterIndex, java.sql.NClob value) throws SQLException {
      try {
         if (value == null) {
            this.setNull(parameterIndex, MysqlType.TEXT);
         } else {
            this.setNCharacterStream(parameterIndex, value.getCharacterStream(), value.length());
         }

      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
      try {
         if (xmlObject == null) {
            this.setNull(parameterIndex, MysqlType.VARCHAR);
         } else {
            this.setCharacterStream(parameterIndex, ((MysqlSQLXML)xmlObject).serializeAsCharacterStream());
         }

      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public long executeLargeUpdate() throws SQLException {
      try {
         return this.executeUpdateInternal(true, false);
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   class EmulatedPreparedStatementBindings implements ParameterBindings {
      private ResultSetImpl bindingsAsRs;
      private boolean[] parameterIsNull;

      EmulatedPreparedStatementBindings() throws SQLException {
         List<Row> rows = new ArrayList();
         this.parameterIsNull = new boolean[PreparedStatement.this.parameterCount];
         System.arraycopy(PreparedStatement.this.isNull, 0, this.parameterIsNull, 0, PreparedStatement.this.parameterCount);
         byte[][] rowData = new byte[PreparedStatement.this.parameterCount][];
         Field[] typeMetadata = new Field[PreparedStatement.this.parameterCount];

         for(int i = 0; i < PreparedStatement.this.parameterCount; ++i) {
            if (PreparedStatement.this.batchCommandIndex == -1) {
               rowData[i] = PreparedStatement.this.getBytesRepresentation(i);
            } else {
               rowData[i] = PreparedStatement.this.getBytesRepresentationForBatch(i, PreparedStatement.this.batchCommandIndex);
            }

            int charsetIndex = false;
            int charsetIndexx;
            switch(PreparedStatement.this.parameterTypes[i]) {
            case BINARY:
            case GEOMETRY:
            case VARBINARY:
            case TINYBLOB:
            case BLOB:
            case MEDIUMBLOB:
            case LONGBLOB:
            case UNKNOWN:
               charsetIndexx = 63;
               break;
            case TIME:
            default:
               try {
                  charsetIndexx = CharsetMapping.getCollationIndexForJavaEncoding((String)PreparedStatement.this.session.getPropertySet().getStringReadableProperty("characterEncoding").getValue(), PreparedStatement.this.session.getServerVersion());
               } catch (RuntimeException var8) {
                  throw SQLError.createSQLException(var8.toString(), "S1009", var8, (ExceptionInterceptor)null);
               }
            }

            Field parameterMetadata = new Field((String)null, "parameter_" + (i + 1), charsetIndexx, PreparedStatement.this.charEncoding, PreparedStatement.this.parameterTypes[i], rowData[i].length);
            typeMetadata[i] = parameterMetadata;
         }

         rows.add(new ByteArrayRow(rowData, PreparedStatement.this.getExceptionInterceptor()));
         this.bindingsAsRs = PreparedStatement.this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new MysqlaColumnDefinition(typeMetadata)));
         this.bindingsAsRs.next();
      }

      public Array getArray(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getArray(parameterIndex);
      }

      public InputStream getAsciiStream(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getAsciiStream(parameterIndex);
      }

      public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getBigDecimal(parameterIndex);
      }

      public InputStream getBinaryStream(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getBinaryStream(parameterIndex);
      }

      public java.sql.Blob getBlob(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getBlob(parameterIndex);
      }

      public boolean getBoolean(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getBoolean(parameterIndex);
      }

      public byte getByte(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getByte(parameterIndex);
      }

      public byte[] getBytes(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getBytes(parameterIndex);
      }

      public Reader getCharacterStream(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getCharacterStream(parameterIndex);
      }

      public java.sql.Clob getClob(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getClob(parameterIndex);
      }

      public java.sql.Date getDate(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getDate(parameterIndex);
      }

      public double getDouble(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getDouble(parameterIndex);
      }

      public float getFloat(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getFloat(parameterIndex);
      }

      public int getInt(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getInt(parameterIndex);
      }

      public BigInteger getBigInteger(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getBigInteger(parameterIndex);
      }

      public long getLong(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getLong(parameterIndex);
      }

      public Reader getNCharacterStream(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getCharacterStream(parameterIndex);
      }

      public Reader getNClob(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getCharacterStream(parameterIndex);
      }

      public Object getObject(int parameterIndex) throws SQLException {
         PreparedStatement.this.checkBounds(parameterIndex, 0);
         if (this.parameterIsNull[parameterIndex - 1]) {
            return null;
         } else {
            switch(PreparedStatement.this.parameterTypes[parameterIndex - 1]) {
            case TINYINT:
            case TINYINT_UNSIGNED:
               return this.getByte(parameterIndex);
            case SMALLINT:
            case SMALLINT_UNSIGNED:
               return this.getShort(parameterIndex);
            case INT:
            case INT_UNSIGNED:
               return this.getInt(parameterIndex);
            case BIGINT:
               return this.getLong(parameterIndex);
            case BIGINT_UNSIGNED:
               return this.getBigInteger(parameterIndex);
            case FLOAT:
            case FLOAT_UNSIGNED:
               return this.getFloat(parameterIndex);
            case DOUBLE:
            case DOUBLE_UNSIGNED:
               return this.getDouble(parameterIndex);
            default:
               return this.bindingsAsRs.getObject(parameterIndex);
            }
         }
      }

      public Ref getRef(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getRef(parameterIndex);
      }

      public short getShort(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getShort(parameterIndex);
      }

      public String getString(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getString(parameterIndex);
      }

      public Time getTime(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getTime(parameterIndex);
      }

      public Timestamp getTimestamp(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getTimestamp(parameterIndex);
      }

      public URL getURL(int parameterIndex) throws SQLException {
         return this.bindingsAsRs.getURL(parameterIndex);
      }

      public boolean isNull(int parameterIndex) throws SQLException {
         PreparedStatement.this.checkBounds(parameterIndex, 0);
         return this.parameterIsNull[parameterIndex - 1];
      }
   }

   static class AppendingBatchVisitor implements PreparedStatement.BatchVisitor {
      LinkedList<byte[]> statementComponents = new LinkedList();

      public PreparedStatement.BatchVisitor append(byte[] values) {
         this.statementComponents.addLast(values);
         return this;
      }

      public PreparedStatement.BatchVisitor increment() {
         return this;
      }

      public PreparedStatement.BatchVisitor decrement() {
         this.statementComponents.removeLast();
         return this;
      }

      public PreparedStatement.BatchVisitor merge(byte[] front, byte[] back) {
         int mergedLength = front.length + back.length;
         byte[] merged = new byte[mergedLength];
         System.arraycopy(front, 0, merged, 0, front.length);
         System.arraycopy(back, 0, merged, front.length, back.length);
         this.statementComponents.addLast(merged);
         return this;
      }

      public byte[][] getStaticSqlStrings() {
         byte[][] asBytes = new byte[this.statementComponents.size()][];
         this.statementComponents.toArray(asBytes);
         return asBytes;
      }

      public String toString() {
         StringBuilder buf = new StringBuilder();
         Iterator iter = this.statementComponents.iterator();

         while(iter.hasNext()) {
            buf.append(StringUtils.toString((byte[])iter.next()));
         }

         return buf.toString();
      }
   }

   interface BatchVisitor {
      PreparedStatement.BatchVisitor increment();

      PreparedStatement.BatchVisitor decrement();

      PreparedStatement.BatchVisitor append(byte[] var1);

      PreparedStatement.BatchVisitor merge(byte[] var1, byte[] var2);
   }

   public static final class ParseInfo {
      char firstStmtChar;
      boolean foundLoadData;
      long lastUsed;
      int statementLength;
      int statementStartPos;
      boolean canRewriteAsMultiValueInsert;
      byte[][] staticSql;
      boolean isOnDuplicateKeyUpdate;
      int locationOfOnDuplicateKeyUpdate;
      String valuesClause;
      boolean parametersInDuplicateKeyClause;
      String charEncoding;
      private PreparedStatement.ParseInfo batchHead;
      private PreparedStatement.ParseInfo batchValues;
      private PreparedStatement.ParseInfo batchODKUClause;

      ParseInfo(String sql, JdbcConnection conn, java.sql.DatabaseMetaData dbmd, String encoding) throws SQLException {
         this(sql, conn, dbmd, encoding, true);
      }

      public ParseInfo(String sql, JdbcConnection conn, java.sql.DatabaseMetaData dbmd, String encoding, boolean buildRewriteInfo) throws SQLException {
         this.firstStmtChar = 0;
         this.foundLoadData = false;
         this.lastUsed = 0L;
         this.statementLength = 0;
         this.statementStartPos = 0;
         this.canRewriteAsMultiValueInsert = false;
         this.staticSql = (byte[][])null;
         this.isOnDuplicateKeyUpdate = false;
         this.locationOfOnDuplicateKeyUpdate = -1;
         this.parametersInDuplicateKeyClause = false;

         try {
            if (sql == null) {
               throw SQLError.createSQLException(Messages.getString("PreparedStatement.61"), "S1009", conn.getExceptionInterceptor());
            }

            this.charEncoding = encoding;
            this.lastUsed = System.currentTimeMillis();
            String quotedIdentifierString = dbmd.getIdentifierQuoteString();
            char quotedIdentifierChar = 0;
            if (quotedIdentifierString != null && !quotedIdentifierString.equals(" ") && quotedIdentifierString.length() > 0) {
               quotedIdentifierChar = quotedIdentifierString.charAt(0);
            }

            this.statementLength = sql.length();
            ArrayList<int[]> endpointList = new ArrayList();
            boolean inQuotes = false;
            char quoteChar = 0;
            boolean inQuotedId = false;
            int lastParmEnd = 0;
            boolean noBackslashEscapes = conn.isNoBackslashEscapesSet();
            this.statementStartPos = StatementImpl.findStartOfStatement(sql);

            int i;
            int j;
            int endOfStmt;
            label216:
            for(i = this.statementStartPos; i < this.statementLength; ++i) {
               char c = sql.charAt(i);
               if (this.firstStmtChar == 0 && Character.isLetter(c)) {
                  this.firstStmtChar = Character.toUpperCase(c);
                  if (this.firstStmtChar == 'I') {
                     this.locationOfOnDuplicateKeyUpdate = StatementImpl.getOnDuplicateKeyLocation(sql, (Boolean)conn.getPropertySet().getBooleanReadableProperty("dontCheckOnDuplicateKeyUpdateInSQL").getValue(), (Boolean)conn.getPropertySet().getBooleanReadableProperty("rewriteBatchedStatements").getValue(), conn.isNoBackslashEscapesSet());
                     this.isOnDuplicateKeyUpdate = this.locationOfOnDuplicateKeyUpdate != -1;
                  }
               }

               if (!noBackslashEscapes && c == '\\' && i < this.statementLength - 1) {
                  ++i;
               } else {
                  if (!inQuotes && quotedIdentifierChar != 0 && c == quotedIdentifierChar) {
                     inQuotedId = !inQuotedId;
                  } else if (!inQuotedId) {
                     if (inQuotes) {
                        if ((c == '\'' || c == '"') && c == quoteChar) {
                           if (i < this.statementLength - 1 && sql.charAt(i + 1) == quoteChar) {
                              ++i;
                              continue;
                           }

                           inQuotes = !inQuotes;
                           quoteChar = 0;
                        } else if ((c == '\'' || c == '"') && c == quoteChar) {
                           inQuotes = !inQuotes;
                           quoteChar = 0;
                        }
                     } else {
                        if (c == '#' || c == '-' && i + 1 < this.statementLength && sql.charAt(i + 1) == '-') {
                           for(endOfStmt = this.statementLength - 1; i < endOfStmt; ++i) {
                              c = sql.charAt(i);
                              if (c == '\r' || c == '\n') {
                                 continue label216;
                              }
                           }
                           continue;
                        }

                        if (c == '/' && i + 1 < this.statementLength) {
                           char cNext = sql.charAt(i + 1);
                           if (cNext == '*') {
                              i += 2;

                              for(j = i; j < this.statementLength; ++j) {
                                 ++i;
                                 cNext = sql.charAt(j);
                                 if (cNext == '*' && j + 1 < this.statementLength && sql.charAt(j + 1) == '/') {
                                    ++i;
                                    if (i < this.statementLength) {
                                       c = sql.charAt(i);
                                    }
                                    break;
                                 }
                              }
                           }
                        } else if (c == '\'' || c == '"') {
                           inQuotes = true;
                           quoteChar = c;
                        }
                     }
                  }

                  if (c == '?' && !inQuotes && !inQuotedId) {
                     endpointList.add(new int[]{lastParmEnd, i});
                     lastParmEnd = i + 1;
                     if (this.isOnDuplicateKeyUpdate && i > this.locationOfOnDuplicateKeyUpdate) {
                        this.parametersInDuplicateKeyClause = true;
                     }
                  }
               }
            }

            if (this.firstStmtChar == 'L') {
               if (StringUtils.startsWithIgnoreCaseAndWs(sql, "LOAD DATA")) {
                  this.foundLoadData = true;
               } else {
                  this.foundLoadData = false;
               }
            } else {
               this.foundLoadData = false;
            }

            endpointList.add(new int[]{lastParmEnd, this.statementLength});
            this.staticSql = new byte[endpointList.size()][];

            for(i = 0; i < this.staticSql.length; ++i) {
               int[] ep = (int[])endpointList.get(i);
               endOfStmt = ep[1];
               j = ep[0];
               int len = endOfStmt - j;
               if (this.foundLoadData) {
                  this.staticSql[i] = StringUtils.getBytes(sql, j, len);
               } else if (encoding != null) {
                  this.staticSql[i] = StringUtils.getBytes(sql, j, len, encoding);
               } else {
                  byte[] buf = new byte[len];

                  for(int j = 0; j < len; ++j) {
                     buf[j] = (byte)sql.charAt(j + j);
                  }

                  this.staticSql[i] = buf;
               }
            }
         } catch (StringIndexOutOfBoundsException var21) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.62", new Object[]{sql}), "S1009", var21, conn.getExceptionInterceptor());
         } catch (CJException var22) {
            throw SQLExceptionsMapping.translateException(var22, conn.getExceptionInterceptor());
         }

         if (buildRewriteInfo) {
            this.canRewriteAsMultiValueInsert = PreparedStatement.canRewrite(sql, this.isOnDuplicateKeyUpdate, this.locationOfOnDuplicateKeyUpdate, this.statementStartPos) && !this.parametersInDuplicateKeyClause;
            if (this.canRewriteAsMultiValueInsert && (Boolean)conn.getPropertySet().getBooleanReadableProperty("rewriteBatchedStatements").getValue()) {
               this.buildRewriteBatchedParams(sql, conn, dbmd, encoding);
            }
         }

      }

      private void buildRewriteBatchedParams(String sql, JdbcConnection conn, java.sql.DatabaseMetaData metadata, String encoding) throws SQLException {
         this.valuesClause = this.extractValuesClause(sql, conn.getMetaData().getIdentifierQuoteString());
         String odkuClause = this.isOnDuplicateKeyUpdate ? sql.substring(this.locationOfOnDuplicateKeyUpdate) : null;
         String headSql = null;
         if (this.isOnDuplicateKeyUpdate) {
            headSql = sql.substring(0, this.locationOfOnDuplicateKeyUpdate);
         } else {
            headSql = sql;
         }

         this.batchHead = new PreparedStatement.ParseInfo(headSql, conn, metadata, encoding, false);
         this.batchValues = new PreparedStatement.ParseInfo("," + this.valuesClause, conn, metadata, encoding, false);
         this.batchODKUClause = null;
         if (odkuClause != null && odkuClause.length() > 0) {
            this.batchODKUClause = new PreparedStatement.ParseInfo("," + this.valuesClause + " " + odkuClause, conn, metadata, encoding, false);
         }

      }

      private String extractValuesClause(String sql, String quoteCharStr) throws SQLException {
         int indexOfValues = -1;
         int valuesSearchStart = this.statementStartPos;

         while(indexOfValues == -1) {
            if (quoteCharStr.length() > 0) {
               indexOfValues = StringUtils.indexOfIgnoreCase(valuesSearchStart, sql, "VALUES", quoteCharStr, quoteCharStr, StringUtils.SEARCH_MODE__MRK_COM_WS);
            } else {
               indexOfValues = StringUtils.indexOfIgnoreCase(valuesSearchStart, sql, "VALUES");
            }

            if (indexOfValues <= 0) {
               break;
            }

            char c = sql.charAt(indexOfValues - 1);
            if (!Character.isWhitespace(c) && c != ')' && c != '`') {
               valuesSearchStart = indexOfValues + 6;
               indexOfValues = -1;
            } else {
               c = sql.charAt(indexOfValues + 6);
               if (!Character.isWhitespace(c) && c != '(') {
                  valuesSearchStart = indexOfValues + 6;
                  indexOfValues = -1;
               }
            }
         }

         if (indexOfValues == -1) {
            return null;
         } else {
            int indexOfFirstParen = sql.indexOf(40, indexOfValues + 6);
            if (indexOfFirstParen == -1) {
               return null;
            } else {
               int endOfValuesClause = sql.lastIndexOf(41);
               if (endOfValuesClause == -1) {
                  return null;
               } else {
                  if (this.isOnDuplicateKeyUpdate) {
                     endOfValuesClause = this.locationOfOnDuplicateKeyUpdate - 1;
                  }

                  return sql.substring(indexOfFirstParen, endOfValuesClause + 1);
               }
            }
         }
      }

      synchronized PreparedStatement.ParseInfo getParseInfoForBatch(int numBatch) {
         PreparedStatement.AppendingBatchVisitor apv = new PreparedStatement.AppendingBatchVisitor();
         this.buildInfoForBatch(numBatch, apv);
         PreparedStatement.ParseInfo batchParseInfo = new PreparedStatement.ParseInfo(apv.getStaticSqlStrings(), this.firstStmtChar, this.foundLoadData, this.isOnDuplicateKeyUpdate, this.locationOfOnDuplicateKeyUpdate, this.statementLength, this.statementStartPos);
         return batchParseInfo;
      }

      String getSqlForBatch(int numBatch) throws UnsupportedEncodingException {
         PreparedStatement.ParseInfo batchInfo = this.getParseInfoForBatch(numBatch);
         return this.getSqlForBatch(batchInfo);
      }

      String getSqlForBatch(PreparedStatement.ParseInfo batchInfo) throws UnsupportedEncodingException {
         int size = 0;
         byte[][] sqlStrings = batchInfo.staticSql;
         int sqlStringsLength = sqlStrings.length;

         for(int i = 0; i < sqlStringsLength; ++i) {
            size += sqlStrings[i].length;
            ++size;
         }

         StringBuilder buf = new StringBuilder(size);

         for(int i = 0; i < sqlStringsLength - 1; ++i) {
            buf.append(StringUtils.toString(sqlStrings[i], this.charEncoding));
            buf.append("?");
         }

         buf.append(StringUtils.toString(sqlStrings[sqlStringsLength - 1]));
         return buf.toString();
      }

      private void buildInfoForBatch(int numBatch, PreparedStatement.BatchVisitor visitor) {
         byte[][] headStaticSql = this.batchHead.staticSql;
         int headStaticSqlLength = headStaticSql.length;
         if (headStaticSqlLength > 1) {
            for(int i = 0; i < headStaticSqlLength - 1; ++i) {
               visitor.append(headStaticSql[i]).increment();
            }
         }

         byte[] endOfHead = headStaticSql[headStaticSqlLength - 1];
         byte[][] valuesStaticSql = this.batchValues.staticSql;
         byte[] beginOfValues = valuesStaticSql[0];
         visitor.merge(endOfHead, beginOfValues).increment();
         int numValueRepeats = numBatch - 1;
         if (this.batchODKUClause != null) {
            --numValueRepeats;
         }

         int valuesStaticSqlLength = valuesStaticSql.length;
         byte[] endOfValues = valuesStaticSql[valuesStaticSqlLength - 1];

         for(int i = 0; i < numValueRepeats; ++i) {
            for(int j = 1; j < valuesStaticSqlLength - 1; ++j) {
               visitor.append(valuesStaticSql[j]).increment();
            }

            visitor.merge(endOfValues, beginOfValues).increment();
         }

         if (this.batchODKUClause != null) {
            byte[][] batchOdkuStaticSql = this.batchODKUClause.staticSql;
            byte[] beginOfOdku = batchOdkuStaticSql[0];
            visitor.decrement().merge(endOfValues, beginOfOdku).increment();
            int batchOdkuStaticSqlLength = batchOdkuStaticSql.length;
            if (numBatch > 1) {
               for(int i = 1; i < batchOdkuStaticSqlLength; ++i) {
                  visitor.append(batchOdkuStaticSql[i]).increment();
               }
            } else {
               visitor.decrement().append(batchOdkuStaticSql[batchOdkuStaticSqlLength - 1]);
            }
         } else {
            visitor.decrement().append(this.staticSql[this.staticSql.length - 1]);
         }

      }

      private ParseInfo(byte[][] staticSql, char firstStmtChar, boolean foundLoadData, boolean isOnDuplicateKeyUpdate, int locationOfOnDuplicateKeyUpdate, int statementLength, int statementStartPos) {
         this.firstStmtChar = 0;
         this.foundLoadData = false;
         this.lastUsed = 0L;
         this.statementLength = 0;
         this.statementStartPos = 0;
         this.canRewriteAsMultiValueInsert = false;
         this.staticSql = (byte[][])null;
         this.isOnDuplicateKeyUpdate = false;
         this.locationOfOnDuplicateKeyUpdate = -1;
         this.parametersInDuplicateKeyClause = false;
         this.firstStmtChar = firstStmtChar;
         this.foundLoadData = foundLoadData;
         this.isOnDuplicateKeyUpdate = isOnDuplicateKeyUpdate;
         this.locationOfOnDuplicateKeyUpdate = locationOfOnDuplicateKeyUpdate;
         this.statementLength = statementLength;
         this.statementStartPos = statementStartPos;
         this.staticSql = staticSql;
      }
   }

   class EndPoint {
      int begin;
      int end;

      EndPoint(int b, int e) {
         this.begin = b;
         this.end = e;
      }
   }

   public class BatchParams {
      public boolean[] isNull = null;
      public boolean[] isStream = null;
      public InputStream[] parameterStreams = null;
      public byte[][] parameterStrings = (byte[][])null;
      public int[] streamLengths = null;

      BatchParams(byte[][] strings, InputStream[] streams, boolean[] isStreamFlags, int[] lengths, boolean[] isNullFlags) {
         this.parameterStrings = new byte[strings.length][];
         this.parameterStreams = new InputStream[streams.length];
         this.isStream = new boolean[isStreamFlags.length];
         this.streamLengths = new int[lengths.length];
         this.isNull = new boolean[isNullFlags.length];
         System.arraycopy(strings, 0, this.parameterStrings, 0, strings.length);
         System.arraycopy(streams, 0, this.parameterStreams, 0, streams.length);
         System.arraycopy(isStreamFlags, 0, this.isStream, 0, isStreamFlags.length);
         System.arraycopy(lengths, 0, this.streamLengths, 0, lengths.length);
         System.arraycopy(isNullFlags, 0, this.isNull, 0, isNullFlags.length);
      }
   }
}
