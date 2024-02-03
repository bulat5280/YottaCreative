package com.mysql.cj.jdbc.result;

import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.api.mysqla.result.ResultsetRows;
import com.mysql.cj.api.result.Row;
import com.mysql.cj.core.Constants;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.MysqlType;
import com.mysql.cj.core.exceptions.AssertionFailedException;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.core.exceptions.FeatureNotAvailableException;
import com.mysql.cj.core.profiler.ProfilerEventHandlerFactory;
import com.mysql.cj.core.profiler.ProfilerEventImpl;
import com.mysql.cj.core.result.Field;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.jdbc.MysqlSQLXML;
import com.mysql.cj.jdbc.PreparedStatement;
import com.mysql.cj.jdbc.StatementImpl;
import com.mysql.cj.jdbc.exceptions.NotUpdatable;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.mysqla.result.ByteArrayRow;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.JDBCType;
import java.sql.NClob;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class UpdatableResultSet extends ResultSetImpl {
   static final byte[] STREAM_DATA_MARKER = StringUtils.getBytes("** STREAM DATA **");
   private String charEncoding;
   private byte[][] defaultColumnValue;
   private PreparedStatement deleter = null;
   private String deleteSQL = null;
   protected PreparedStatement inserter = null;
   private String insertSQL = null;
   private boolean isUpdatable = false;
   private String notUpdatableReason = null;
   private List<Integer> primaryKeyIndicies = null;
   private String qualifiedAndQuotedTableName;
   private String quotedIdChar = null;
   private PreparedStatement refresher;
   private String refreshSQL = null;
   private Row savedCurrentRow;
   protected PreparedStatement updater = null;
   private String updateSQL = null;
   private boolean populateInserterWithDefaultValues = false;
   private boolean hasLongColumnInfo = false;
   private Map<String, Map<String, Map<String, Integer>>> databasesUsedToTablesUsed = null;

   public UpdatableResultSet(ResultsetRows tuples, JdbcConnection conn, StatementImpl creatorStmt) throws SQLException {
      super(tuples, conn, creatorStmt);
      this.checkUpdatability();
      this.populateInserterWithDefaultValues = (Boolean)this.getConnection().getPropertySet().getBooleanReadableProperty("populateInsertRowWithDefaultValues").getValue();
      this.hasLongColumnInfo = this.getConnection().getSession().getServerSession().hasLongColumnInfo();
   }

   public synchronized boolean absolute(int row) throws SQLException {
      try {
         return super.absolute(row);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public synchronized void afterLast() throws SQLException {
      try {
         super.afterLast();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public synchronized void beforeFirst() throws SQLException {
      try {
         super.beforeFirst();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public synchronized void cancelRowUpdates() throws SQLException {
      try {
         this.checkClosed();
         if (this.doingUpdates) {
            this.doingUpdates = false;
            this.updater.clearParameters();
         }

      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   protected synchronized void checkRowPos() throws SQLException {
      this.checkClosed();
      if (!this.onInsertRow) {
         super.checkRowPos();
      }

   }

   public void checkUpdatability() throws SQLException {
      try {
         if (this.getMetadata() != null) {
            String singleTableName = null;
            String catalogName = null;
            int primaryKeyCount = 0;
            Field[] fields = this.getMetadata().getFields();
            if (this.catalog == null || this.catalog.length() == 0) {
               this.catalog = fields[0].getDatabaseName();
               if (this.catalog == null || this.catalog.length() == 0) {
                  throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.43"), "S1009", this.getExceptionInterceptor());
               }
            }

            if (fields.length <= 0) {
               this.isUpdatable = false;
               this.notUpdatableReason = Messages.getString("NotUpdatableReason.3");
            } else {
               singleTableName = fields[0].getOriginalTableName();
               catalogName = fields[0].getDatabaseName();
               if (singleTableName == null) {
                  singleTableName = fields[0].getTableName();
                  catalogName = this.catalog;
               }

               if (singleTableName == null) {
                  this.isUpdatable = false;
                  this.notUpdatableReason = Messages.getString("NotUpdatableReason.3");
               } else {
                  if (fields[0].isPrimaryKey()) {
                     ++primaryKeyCount;
                  }

                  int i = 1;

                  while(true) {
                     if (i < fields.length) {
                        String otherTableName = fields[i].getOriginalTableName();
                        String otherCatalogName = fields[i].getDatabaseName();
                        if (otherTableName == null) {
                           otherTableName = fields[i].getTableName();
                           otherCatalogName = this.catalog;
                        }

                        if (otherTableName == null) {
                           this.isUpdatable = false;
                           this.notUpdatableReason = Messages.getString("NotUpdatableReason.3");
                           return;
                        }

                        if (!otherTableName.equals(singleTableName)) {
                           this.isUpdatable = false;
                           this.notUpdatableReason = Messages.getString("NotUpdatableReason.0");
                           return;
                        }

                        if (catalogName != null && otherCatalogName.equals(catalogName)) {
                           if (fields[i].isPrimaryKey()) {
                              ++primaryKeyCount;
                           }

                           ++i;
                           continue;
                        }

                        this.isUpdatable = false;
                        this.notUpdatableReason = Messages.getString("NotUpdatableReason.1");
                        return;
                     }

                     if ((Boolean)this.getConnection().getPropertySet().getBooleanReadableProperty("strictUpdates").getValue()) {
                        DatabaseMetaData dbmd = this.getConnection().getMetaData();
                        ResultSet rs = null;
                        HashMap primaryKeyNames = new HashMap();

                        try {
                           rs = dbmd.getPrimaryKeys(catalogName, (String)null, singleTableName);

                           while(rs.next()) {
                              String keyName = rs.getString(4);
                              keyName = keyName.toUpperCase();
                              primaryKeyNames.put(keyName, keyName);
                           }
                        } finally {
                           if (rs != null) {
                              try {
                                 rs.close();
                              } catch (Exception var16) {
                                 AssertionFailedException.shouldNotHappen(var16);
                              }

                              rs = null;
                           }

                        }

                        int existingPrimaryKeysCount = primaryKeyNames.size();
                        if (existingPrimaryKeysCount == 0) {
                           this.isUpdatable = false;
                           this.notUpdatableReason = Messages.getString("NotUpdatableReason.5");
                           return;
                        }

                        for(int i = 0; i < fields.length; ++i) {
                           if (fields[i].isPrimaryKey()) {
                              String columnNameUC = fields[i].getName().toUpperCase();
                              if (primaryKeyNames.remove(columnNameUC) == null) {
                                 String originalName = fields[i].getOriginalName();
                                 if (originalName != null && primaryKeyNames.remove(originalName.toUpperCase()) == null) {
                                    this.isUpdatable = false;
                                    this.notUpdatableReason = Messages.getString("NotUpdatableReason.6", new Object[]{originalName});
                                    return;
                                 }
                              }
                           }
                        }

                        this.isUpdatable = primaryKeyNames.isEmpty();
                        if (!this.isUpdatable) {
                           if (existingPrimaryKeysCount > 1) {
                              this.notUpdatableReason = Messages.getString("NotUpdatableReason.7");
                           } else {
                              this.notUpdatableReason = Messages.getString("NotUpdatableReason.4");
                           }

                           return;
                        }
                     }

                     if (primaryKeyCount == 0) {
                        this.isUpdatable = false;
                        this.notUpdatableReason = Messages.getString("NotUpdatableReason.4");
                        return;
                     }

                     this.isUpdatable = true;
                     this.notUpdatableReason = null;
                     return;
                  }
               }
            }
         }
      } catch (SQLException var18) {
         this.isUpdatable = false;
         this.notUpdatableReason = var18.getMessage();
      }
   }

   public synchronized void deleteRow() throws SQLException {
      try {
         this.checkClosed();
         if (!this.isUpdatable) {
            throw new NotUpdatable(this.notUpdatableReason);
         } else if (this.onInsertRow) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.1"), this.getExceptionInterceptor());
         } else if (this.rowData.size() == 0) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.2"), this.getExceptionInterceptor());
         } else if (this.isBeforeFirst()) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.3"), this.getExceptionInterceptor());
         } else if (this.isAfterLast()) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.4"), this.getExceptionInterceptor());
         } else {
            if (this.deleter == null) {
               if (this.deleteSQL == null) {
                  this.generateStatements();
               }

               this.deleter = (PreparedStatement)this.getConnection().clientPrepareStatement(this.deleteSQL);
            }

            this.deleter.clearParameters();
            int numKeys = this.primaryKeyIndicies.size();
            int i;
            if (numKeys == 1) {
               i = (Integer)this.primaryKeyIndicies.get(0);
               this.setParamValue(this.deleter, 1, this.thisRow, i, this.getMetadata().getFields()[i].getMysqlType());
            } else {
               for(i = 0; i < numKeys; ++i) {
                  int index = (Integer)this.primaryKeyIndicies.get(i);
                  this.setParamValue(this.deleter, i + 1, this.thisRow, index, this.getMetadata().getFields()[index].getMysqlType());
               }
            }

            this.deleter.executeUpdate();
            this.rowData.remove();
            this.previous();
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   private synchronized void setParamValue(PreparedStatement ps, int psIdx, Row row, int rsIdx, MysqlType mysqlType) throws SQLException {
      byte[] val = row.getBytes(rsIdx);
      if (val == null) {
         ps.setNull(psIdx, MysqlType.NULL);
      } else {
         switch(mysqlType) {
         case NULL:
            ps.setNull(psIdx, MysqlType.NULL);
            break;
         case TINYINT:
         case TINYINT_UNSIGNED:
         case SMALLINT:
         case SMALLINT_UNSIGNED:
         case MEDIUMINT:
         case MEDIUMINT_UNSIGNED:
         case INT:
         case INT_UNSIGNED:
         case YEAR:
            ps.setInt(psIdx, this.getInt(rsIdx + 1));
            break;
         case BIGINT:
            ps.setLong(psIdx, this.getLong(rsIdx + 1));
            break;
         case BIGINT_UNSIGNED:
            ps.setBigInteger(psIdx, this.getBigInteger(rsIdx + 1));
            break;
         case CHAR:
         case ENUM:
         case SET:
         case VARCHAR:
         case JSON:
         case TINYTEXT:
         case TEXT:
         case MEDIUMTEXT:
         case LONGTEXT:
         case DECIMAL:
         case DECIMAL_UNSIGNED:
            ps.setString(psIdx, this.getString(rsIdx + 1));
            break;
         case DATE:
            ps.setDate(psIdx, this.getDate(rsIdx + 1));
            break;
         case TIMESTAMP:
         case DATETIME:
            ps.setTimestamp(psIdx, this.getTimestamp(rsIdx + 1));
            break;
         case TIME:
            ps.setTime(psIdx, this.getTime(rsIdx + 1));
            break;
         case DOUBLE:
         case DOUBLE_UNSIGNED:
         case FLOAT:
         case FLOAT_UNSIGNED:
         case BOOLEAN:
            ps.setBytesNoEscapeNoQuotes(psIdx, val);
            break;
         default:
            ps.setBytes(psIdx, val);
         }

      }
   }

   private synchronized void extractDefaultValues() throws SQLException {
      DatabaseMetaData dbmd = this.getConnection().getMetaData();
      this.defaultColumnValue = new byte[this.getMetadata().getFields().length][];
      ResultSet columnsResultSet = null;
      Iterator var3 = this.databasesUsedToTablesUsed.entrySet().iterator();

      while(var3.hasNext()) {
         Entry<String, Map<String, Map<String, Integer>>> dbEntry = (Entry)var3.next();
         Iterator var5 = ((Map)dbEntry.getValue()).entrySet().iterator();

         while(var5.hasNext()) {
            Entry<String, Map<String, Integer>> tableEntry = (Entry)var5.next();
            String tableName = (String)tableEntry.getKey();
            Map columnNamesToIndices = (Map)tableEntry.getValue();

            try {
               columnsResultSet = dbmd.getColumns(this.catalog, (String)null, tableName, "%");

               while(columnsResultSet.next()) {
                  String columnName = columnsResultSet.getString("COLUMN_NAME");
                  byte[] defaultValue = columnsResultSet.getBytes("COLUMN_DEF");
                  if (columnNamesToIndices.containsKey(columnName)) {
                     int localColumnIndex = (Integer)columnNamesToIndices.get(columnName);
                     this.defaultColumnValue[localColumnIndex] = defaultValue;
                  }
               }
            } finally {
               if (columnsResultSet != null) {
                  columnsResultSet.close();
                  columnsResultSet = null;
               }

            }
         }
      }

   }

   public synchronized boolean first() throws SQLException {
      try {
         return super.first();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   protected synchronized void generateStatements() throws SQLException {
      try {
         if (!this.isUpdatable) {
            this.doingUpdates = false;
            this.onInsertRow = false;
            throw new NotUpdatable(this.notUpdatableReason);
         } else {
            String quotedId = this.getQuotedIdChar();
            Map<String, String> tableNamesSoFar = null;
            if (this.getConnection().lowerCaseTableNames()) {
               tableNamesSoFar = new TreeMap(String.CASE_INSENSITIVE_ORDER);
               this.databasesUsedToTablesUsed = new TreeMap(String.CASE_INSENSITIVE_ORDER);
            } else {
               tableNamesSoFar = new TreeMap();
               this.databasesUsedToTablesUsed = new TreeMap();
            }

            this.primaryKeyIndicies = new ArrayList();
            StringBuilder fieldValues = new StringBuilder();
            StringBuilder keyValues = new StringBuilder();
            StringBuilder columnNames = new StringBuilder();
            StringBuilder insertPlaceHolders = new StringBuilder();
            StringBuilder allTablesBuf = new StringBuilder();
            Map<Integer, String> columnIndicesToTable = new HashMap();
            boolean firstTime = true;
            boolean keysFirstTime = true;
            Field[] fields = this.getMetadata().getFields();

            for(int i = 0; i < fields.length; ++i) {
               StringBuilder tableNameBuffer = new StringBuilder();
               Map<String, Integer> updColumnNameToIndex = null;
               String databaseName;
               String columnName;
               String originalTableName;
               if (fields[i].getOriginalTableName() != null) {
                  databaseName = fields[i].getDatabaseName();
                  if (databaseName != null && databaseName.length() > 0) {
                     tableNameBuffer.append(quotedId);
                     tableNameBuffer.append(databaseName);
                     tableNameBuffer.append(quotedId);
                     tableNameBuffer.append('.');
                  }

                  columnName = fields[i].getOriginalTableName();
                  tableNameBuffer.append(quotedId);
                  tableNameBuffer.append(columnName);
                  tableNameBuffer.append(quotedId);
                  originalTableName = tableNameBuffer.toString();
                  if (!tableNamesSoFar.containsKey(originalTableName)) {
                     if (!tableNamesSoFar.isEmpty()) {
                        allTablesBuf.append(',');
                     }

                     allTablesBuf.append(originalTableName);
                     tableNamesSoFar.put(originalTableName, originalTableName);
                  }

                  columnIndicesToTable.put(i, originalTableName);
                  updColumnNameToIndex = this.getColumnsToIndexMapForTableAndDB(databaseName, columnName);
               } else {
                  databaseName = fields[i].getTableName();
                  if (databaseName != null) {
                     tableNameBuffer.append(quotedId);
                     tableNameBuffer.append(databaseName);
                     tableNameBuffer.append(quotedId);
                     columnName = tableNameBuffer.toString();
                     if (!tableNamesSoFar.containsKey(columnName)) {
                        if (!tableNamesSoFar.isEmpty()) {
                           allTablesBuf.append(',');
                        }

                        allTablesBuf.append(columnName);
                        tableNamesSoFar.put(columnName, columnName);
                     }

                     columnIndicesToTable.put(i, columnName);
                     updColumnNameToIndex = this.getColumnsToIndexMapForTableAndDB(this.catalog, databaseName);
                  }
               }

               databaseName = fields[i].getOriginalName();
               columnName = null;
               if (this.hasLongColumnInfo && databaseName != null && databaseName.length() > 0) {
                  columnName = databaseName;
               } else {
                  columnName = fields[i].getName();
               }

               if (updColumnNameToIndex != null && columnName != null) {
                  updColumnNameToIndex.put(columnName, i);
               }

               originalTableName = fields[i].getOriginalTableName();
               String tableName = null;
               if (this.hasLongColumnInfo && originalTableName != null && originalTableName.length() > 0) {
                  tableName = originalTableName;
               } else {
                  tableName = fields[i].getTableName();
               }

               StringBuilder fqcnBuf = new StringBuilder();
               String databaseName = fields[i].getDatabaseName();
               if (databaseName != null && databaseName.length() > 0) {
                  fqcnBuf.append(quotedId);
                  fqcnBuf.append(databaseName);
                  fqcnBuf.append(quotedId);
                  fqcnBuf.append('.');
               }

               fqcnBuf.append(quotedId);
               fqcnBuf.append(tableName);
               fqcnBuf.append(quotedId);
               fqcnBuf.append('.');
               fqcnBuf.append(quotedId);
               fqcnBuf.append(columnName);
               fqcnBuf.append(quotedId);
               String qualifiedColumnName = fqcnBuf.toString();
               if (fields[i].isPrimaryKey()) {
                  this.primaryKeyIndicies.add(i);
                  if (!keysFirstTime) {
                     keyValues.append(" AND ");
                  } else {
                     keysFirstTime = false;
                  }

                  keyValues.append(qualifiedColumnName);
                  keyValues.append("<=>");
                  keyValues.append("?");
               }

               if (firstTime) {
                  firstTime = false;
                  fieldValues.append("SET ");
               } else {
                  fieldValues.append(",");
                  columnNames.append(",");
                  insertPlaceHolders.append(",");
               }

               insertPlaceHolders.append("?");
               columnNames.append(qualifiedColumnName);
               fieldValues.append(qualifiedColumnName);
               fieldValues.append("=?");
            }

            this.qualifiedAndQuotedTableName = allTablesBuf.toString();
            this.updateSQL = "UPDATE " + this.qualifiedAndQuotedTableName + " " + fieldValues.toString() + " WHERE " + keyValues.toString();
            this.insertSQL = "INSERT INTO " + this.qualifiedAndQuotedTableName + " (" + columnNames.toString() + ") VALUES (" + insertPlaceHolders.toString() + ")";
            this.refreshSQL = "SELECT " + columnNames.toString() + " FROM " + this.qualifiedAndQuotedTableName + " WHERE " + keyValues.toString();
            this.deleteSQL = "DELETE FROM " + this.qualifiedAndQuotedTableName + " WHERE " + keyValues.toString();
         }
      } catch (CJException var23) {
         throw SQLExceptionsMapping.translateException(var23, this.getExceptionInterceptor());
      }
   }

   private Map<String, Integer> getColumnsToIndexMapForTableAndDB(String databaseName, String tableName) {
      Map<String, Map<String, Integer>> tablesUsedToColumnsMap = (Map)this.databasesUsedToTablesUsed.get(databaseName);
      if (tablesUsedToColumnsMap == null) {
         if (this.getConnection().lowerCaseTableNames()) {
            tablesUsedToColumnsMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
         } else {
            tablesUsedToColumnsMap = new TreeMap();
         }

         this.databasesUsedToTablesUsed.put(databaseName, tablesUsedToColumnsMap);
      }

      Map<String, Integer> nameToIndex = (Map)((Map)tablesUsedToColumnsMap).get(tableName);
      if (nameToIndex == null) {
         nameToIndex = new HashMap();
         ((Map)tablesUsedToColumnsMap).put(tableName, nameToIndex);
      }

      return (Map)nameToIndex;
   }

   public int getConcurrency() throws SQLException {
      try {
         return this.isUpdatable ? 1008 : 1007;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   private synchronized String getQuotedIdChar() throws SQLException {
      if (this.quotedIdChar == null) {
         DatabaseMetaData dbmd = this.getConnection().getMetaData();
         this.quotedIdChar = dbmd.getIdentifierQuoteString();
      }

      return this.quotedIdChar;
   }

   public synchronized void insertRow() throws SQLException {
      try {
         this.checkClosed();
         if (!this.onInsertRow) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.7"), this.getExceptionInterceptor());
         } else {
            this.inserter.executeUpdate();
            long autoIncrementId = this.inserter.getLastInsertID();
            Field[] fields = this.getMetadata().getFields();
            int numFields = fields.length;
            byte[][] newRow = new byte[numFields][];

            for(int i = 0; i < numFields; ++i) {
               if (this.inserter.isNull(i)) {
                  newRow[i] = null;
               } else {
                  newRow[i] = this.inserter.getBytesRepresentation(i);
               }

               if (fields[i].isAutoIncrement() && autoIncrementId > 0L) {
                  newRow[i] = StringUtils.getBytes(String.valueOf(autoIncrementId));
                  this.inserter.setBytesNoEscapeNoQuotes(i + 1, newRow[i]);
               }
            }

            Row resultSetRow = new ByteArrayRow(newRow, this.getExceptionInterceptor());
            this.refreshRow(this.inserter, resultSetRow);
            this.rowData.addRow(resultSetRow);
            this.resetInserter();
         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   public synchronized boolean isAfterLast() throws SQLException {
      try {
         return super.isAfterLast();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public synchronized boolean isBeforeFirst() throws SQLException {
      try {
         return super.isBeforeFirst();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public synchronized boolean isFirst() throws SQLException {
      try {
         return super.isFirst();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public synchronized boolean isLast() throws SQLException {
      try {
         return super.isLast();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   boolean isUpdatable() {
      return this.isUpdatable;
   }

   public synchronized boolean last() throws SQLException {
      try {
         return super.last();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public synchronized void moveToCurrentRow() throws SQLException {
      try {
         this.checkClosed();
         if (!this.isUpdatable) {
            throw new NotUpdatable(this.notUpdatableReason);
         } else {
            if (this.onInsertRow) {
               this.onInsertRow = false;
               this.thisRow = this.savedCurrentRow;
            }

         }
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public synchronized void moveToInsertRow() throws SQLException {
      try {
         this.checkClosed();
         if (!this.isUpdatable) {
            throw new NotUpdatable(this.notUpdatableReason);
         } else {
            if (this.inserter == null) {
               if (this.insertSQL == null) {
                  this.generateStatements();
               }

               this.inserter = (PreparedStatement)this.getConnection().clientPrepareStatement(this.insertSQL);
               if (this.populateInserterWithDefaultValues) {
                  this.extractDefaultValues();
               }

               this.resetInserter();
            } else {
               this.resetInserter();
            }

            Field[] fields = this.getMetadata().getFields();
            int numFields = fields.length;
            this.onInsertRow = true;
            this.doingUpdates = false;
            this.savedCurrentRow = this.thisRow;
            byte[][] newRowData = new byte[numFields][];
            this.thisRow = new ByteArrayRow(newRowData, this.getExceptionInterceptor());
            this.thisRow.setMetadata(this.getMetadata());

            for(int i = 0; i < numFields; ++i) {
               if (!this.populateInserterWithDefaultValues) {
                  this.inserter.setBytesNoEscapeNoQuotes(i + 1, StringUtils.getBytes("DEFAULT"));
                  newRowData = (byte[][])null;
               } else if (this.defaultColumnValue[i] == null) {
                  this.inserter.setNull(i + 1, MysqlType.NULL);
                  newRowData[i] = null;
               } else {
                  Field f = fields[i];
                  switch(f.getMysqlTypeId()) {
                  case 7:
                  case 10:
                  case 11:
                  case 12:
                     if (this.defaultColumnValue[i].length > 7 && this.defaultColumnValue[i][0] == 67 && this.defaultColumnValue[i][1] == 85 && this.defaultColumnValue[i][2] == 82 && this.defaultColumnValue[i][3] == 82 && this.defaultColumnValue[i][4] == 69 && this.defaultColumnValue[i][5] == 78 && this.defaultColumnValue[i][6] == 84 && this.defaultColumnValue[i][7] == 95) {
                        this.inserter.setBytesNoEscapeNoQuotes(i + 1, this.defaultColumnValue[i]);
                     } else {
                        this.inserter.setBytes(i + 1, this.defaultColumnValue[i], false, false);
                     }
                     break;
                  case 8:
                  case 9:
                  default:
                     this.inserter.setBytes(i + 1, this.defaultColumnValue[i], false, false);
                  }

                  byte[] defaultValueCopy = new byte[this.defaultColumnValue[i].length];
                  System.arraycopy(this.defaultColumnValue[i], 0, defaultValueCopy, 0, defaultValueCopy.length);
                  newRowData[i] = defaultValueCopy;
               }
            }

         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   public synchronized boolean next() throws SQLException {
      try {
         return super.next();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public synchronized boolean prev() throws SQLException {
      return super.prev();
   }

   public synchronized boolean previous() throws SQLException {
      try {
         return super.previous();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public synchronized void realClose(boolean calledExplicitly) throws SQLException {
      try {
         if (!this.isClosed) {
            SQLException sqlEx = null;
            if (this.useUsageAdvisor && this.deleter == null && this.inserter == null && this.refresher == null && this.updater == null) {
               this.eventSink = ProfilerEventHandlerFactory.getInstance(this.session);
               String message = Messages.getString("UpdatableResultSet.34");
               this.eventSink.consumeEvent(new ProfilerEventImpl((byte)0, "", this.getOwningStatement() == null ? "N/A" : this.getOwningStatement().getCurrentCatalog(), this.getConnectionId(), this.getOwningStatement() == null ? -1 : this.getOwningStatement().getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, (String)null, this.getPointOfOrigin(), message));
            }

            try {
               if (this.deleter != null) {
                  this.deleter.close();
               }
            } catch (SQLException var8) {
               sqlEx = var8;
            }

            try {
               if (this.inserter != null) {
                  this.inserter.close();
               }
            } catch (SQLException var7) {
               sqlEx = var7;
            }

            try {
               if (this.refresher != null) {
                  this.refresher.close();
               }
            } catch (SQLException var6) {
               sqlEx = var6;
            }

            try {
               if (this.updater != null) {
                  this.updater.close();
               }
            } catch (SQLException var5) {
               sqlEx = var5;
            }

            super.realClose(calledExplicitly);
            if (sqlEx != null) {
               throw sqlEx;
            }
         }
      } catch (CJException var9) {
         throw SQLExceptionsMapping.translateException(var9, this.getExceptionInterceptor());
      }
   }

   public synchronized void refreshRow() throws SQLException {
      try {
         this.checkClosed();
         if (!this.isUpdatable) {
            throw SQLError.notUpdatable();
         } else if (this.onInsertRow) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.8"), this.getExceptionInterceptor());
         } else if (this.rowData.size() == 0) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.9"), this.getExceptionInterceptor());
         } else if (this.isBeforeFirst()) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.10"), this.getExceptionInterceptor());
         } else if (this.isAfterLast()) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.11"), this.getExceptionInterceptor());
         } else {
            this.refreshRow(this.updater, this.thisRow);
         }
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   private synchronized void refreshRow(PreparedStatement updateInsertStmt, Row rowToRefresh) throws SQLException {
      if (this.refresher == null) {
         if (this.refreshSQL == null) {
            this.generateStatements();
         }

         this.refresher = (PreparedStatement)this.getConnection().clientPrepareStatement(this.refreshSQL);
      }

      this.refresher.clearParameters();
      int numKeys = this.primaryKeyIndicies.size();
      ResultSet rs;
      int numCols;
      int i;
      if (numKeys == 1) {
         rs = null;
         numCols = (Integer)this.primaryKeyIndicies.get(0);
         byte[] dataFrom;
         if (!this.doingUpdates && !this.onInsertRow) {
            dataFrom = rowToRefresh.getBytes(numCols);
         } else {
            dataFrom = updateInsertStmt.getBytesRepresentation(numCols);
            if (!updateInsertStmt.isNull(numCols) && dataFrom.length != 0) {
               dataFrom = this.stripBinaryPrefix(dataFrom);
            } else {
               dataFrom = rowToRefresh.getBytes(numCols);
            }
         }

         if (this.getMetadata().getFields()[numCols].getValueNeedsQuoting()) {
            this.refresher.setBytesNoEscape(1, dataFrom);
         } else {
            this.refresher.setBytesNoEscapeNoQuotes(1, dataFrom);
         }
      } else {
         for(int i = 0; i < numKeys; ++i) {
            byte[] dataFrom = null;
            i = (Integer)this.primaryKeyIndicies.get(i);
            byte[] dataFrom;
            if (!this.doingUpdates && !this.onInsertRow) {
               dataFrom = rowToRefresh.getBytes(i);
            } else {
               dataFrom = updateInsertStmt.getBytesRepresentation(i);
               if (!updateInsertStmt.isNull(i) && dataFrom.length != 0) {
                  dataFrom = this.stripBinaryPrefix(dataFrom);
               } else {
                  dataFrom = rowToRefresh.getBytes(i);
               }
            }

            this.refresher.setBytesNoEscape(i + 1, dataFrom);
         }
      }

      rs = null;

      try {
         rs = this.refresher.executeQuery();
         numCols = rs.getMetaData().getColumnCount();
         if (!rs.next()) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.12"), "S1000", this.getExceptionInterceptor());
         }

         for(i = 0; i < numCols; ++i) {
            byte[] val = rs.getBytes(i + 1);
            if (val != null && !rs.wasNull()) {
               rowToRefresh.setBytes(i, rs.getBytes(i + 1));
            } else {
               rowToRefresh.setBytes(i, (byte[])null);
            }
         }
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException var13) {
            }
         }

      }

   }

   public synchronized boolean relative(int rows) throws SQLException {
      try {
         return super.relative(rows);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   private void resetInserter() throws SQLException {
      this.inserter.clearParameters();

      for(int i = 0; i < this.getMetadata().getFields().length; ++i) {
         this.inserter.setNull(i + 1, 0);
      }

   }

   public synchronized boolean rowDeleted() throws SQLException {
      try {
         throw SQLError.createSQLFeatureNotSupportedException();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public synchronized boolean rowInserted() throws SQLException {
      try {
         throw SQLError.createSQLFeatureNotSupportedException();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public synchronized boolean rowUpdated() throws SQLException {
      try {
         throw SQLError.createSQLFeatureNotSupportedException();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public void setResultSetConcurrency(int concurrencyFlag) {
      super.setResultSetConcurrency(concurrencyFlag);
   }

   private byte[] stripBinaryPrefix(byte[] dataFrom) {
      return StringUtils.stripEnclosure(dataFrom, "_binary'", "'");
   }

   protected synchronized void syncUpdate() throws SQLException {
      if (this.updater == null) {
         if (this.updateSQL == null) {
            this.generateStatements();
         }

         this.updater = (PreparedStatement)this.getConnection().clientPrepareStatement(this.updateSQL);
      }

      Field[] fields = this.getMetadata().getFields();
      int numFields = fields.length;
      this.updater.clearParameters();

      int i;
      for(i = 0; i < numFields; ++i) {
         if (this.thisRow.getBytes(i) != null) {
            if (fields[i].getValueNeedsQuoting()) {
               this.updater.setBytes(i + 1, this.thisRow.getBytes(i), fields[i].isBinary(), false);
            } else {
               this.updater.setBytesNoEscapeNoQuotes(i + 1, this.thisRow.getBytes(i));
            }
         } else {
            this.updater.setNull(i + 1, 0);
         }
      }

      i = this.primaryKeyIndicies.size();
      int i;
      if (i == 1) {
         i = (Integer)this.primaryKeyIndicies.get(0);
         this.setParamValue(this.updater, numFields + 1, this.thisRow, i, fields[i].getMysqlType());
      } else {
         for(i = 0; i < i; ++i) {
            int idx = (Integer)this.primaryKeyIndicies.get(i);
            this.setParamValue(this.updater, numFields + i + 1, this.thisRow, idx, fields[idx].getMysqlType());
         }
      }

   }

   public synchronized void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
      try {
         if (!this.onInsertRow) {
            if (!this.doingUpdates) {
               this.doingUpdates = true;
               this.syncUpdate();
            }

            this.updater.setAsciiStream(columnIndex, x, length);
         } else {
            this.inserter.setAsciiStream(columnIndex, x, length);
            this.thisRow.setBytes(columnIndex - 1, STREAM_DATA_MARKER);
         }

      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateAsciiStream(String columnName, InputStream x, int length) throws SQLException {
      try {
         this.updateAsciiStream(this.findColumn(columnName), x, length);
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
      try {
         if (!this.onInsertRow) {
            if (!this.doingUpdates) {
               this.doingUpdates = true;
               this.syncUpdate();
            }

            this.updater.setBigDecimal(columnIndex, x);
         } else {
            this.inserter.setBigDecimal(columnIndex, x);
            if (x == null) {
               this.thisRow.setBytes(columnIndex - 1, (byte[])null);
            } else {
               this.thisRow.setBytes(columnIndex - 1, StringUtils.getBytes(x.toString()));
            }
         }

      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateBigDecimal(String columnName, BigDecimal x) throws SQLException {
      try {
         this.updateBigDecimal(this.findColumn(columnName), x);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
      try {
         if (!this.onInsertRow) {
            if (!this.doingUpdates) {
               this.doingUpdates = true;
               this.syncUpdate();
            }

            this.updater.setBinaryStream(columnIndex, x, length);
         } else {
            this.inserter.setBinaryStream(columnIndex, x, length);
            if (x == null) {
               this.thisRow.setBytes(columnIndex - 1, (byte[])null);
            } else {
               this.thisRow.setBytes(columnIndex - 1, STREAM_DATA_MARKER);
            }
         }

      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateBinaryStream(String columnName, InputStream x, int length) throws SQLException {
      try {
         this.updateBinaryStream(this.findColumn(columnName), x, length);
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateBlob(int columnIndex, Blob blob) throws SQLException {
      try {
         if (!this.onInsertRow) {
            if (!this.doingUpdates) {
               this.doingUpdates = true;
               this.syncUpdate();
            }

            this.updater.setBlob(columnIndex, blob);
         } else {
            this.inserter.setBlob(columnIndex, blob);
            if (blob == null) {
               this.thisRow.setBytes(columnIndex - 1, (byte[])null);
            } else {
               this.thisRow.setBytes(columnIndex - 1, STREAM_DATA_MARKER);
            }
         }

      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateBlob(String columnName, Blob blob) throws SQLException {
      try {
         this.updateBlob(this.findColumn(columnName), blob);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateBoolean(int columnIndex, boolean x) throws SQLException {
      try {
         if (!this.onInsertRow) {
            if (!this.doingUpdates) {
               this.doingUpdates = true;
               this.syncUpdate();
            }

            this.updater.setBoolean(columnIndex, x);
         } else {
            this.inserter.setBoolean(columnIndex, x);
            this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
         }

      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateBoolean(String columnName, boolean x) throws SQLException {
      try {
         this.updateBoolean(this.findColumn(columnName), x);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateByte(int columnIndex, byte x) throws SQLException {
      try {
         if (!this.onInsertRow) {
            if (!this.doingUpdates) {
               this.doingUpdates = true;
               this.syncUpdate();
            }

            this.updater.setByte(columnIndex, x);
         } else {
            this.inserter.setByte(columnIndex, x);
            this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
         }

      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateByte(String columnName, byte x) throws SQLException {
      try {
         this.updateByte(this.findColumn(columnName), x);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateBytes(int columnIndex, byte[] x) throws SQLException {
      try {
         if (!this.onInsertRow) {
            if (!this.doingUpdates) {
               this.doingUpdates = true;
               this.syncUpdate();
            }

            this.updater.setBytes(columnIndex, x);
         } else {
            this.inserter.setBytes(columnIndex, x);
            this.thisRow.setBytes(columnIndex - 1, x);
         }

      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateBytes(String columnName, byte[] x) throws SQLException {
      try {
         this.updateBytes(this.findColumn(columnName), x);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
      try {
         if (!this.onInsertRow) {
            if (!this.doingUpdates) {
               this.doingUpdates = true;
               this.syncUpdate();
            }

            this.updater.setCharacterStream(columnIndex, x, length);
         } else {
            this.inserter.setCharacterStream(columnIndex, x, length);
            if (x == null) {
               this.thisRow.setBytes(columnIndex - 1, (byte[])null);
            } else {
               this.thisRow.setBytes(columnIndex - 1, STREAM_DATA_MARKER);
            }
         }

      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateCharacterStream(String columnName, Reader reader, int length) throws SQLException {
      try {
         this.updateCharacterStream(this.findColumn(columnName), reader, length);
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void updateClob(int columnIndex, Clob clob) throws SQLException {
      try {
         if (clob == null) {
            this.updateNull(columnIndex);
         } else {
            this.updateCharacterStream(columnIndex, clob.getCharacterStream(), (int)clob.length());
         }

      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateDate(int columnIndex, Date x) throws SQLException {
      try {
         if (!this.onInsertRow) {
            if (!this.doingUpdates) {
               this.doingUpdates = true;
               this.syncUpdate();
            }

            this.updater.setDate(columnIndex, x);
         } else {
            this.inserter.setDate(columnIndex, x);
            this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
         }

      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateDate(String columnName, Date x) throws SQLException {
      try {
         this.updateDate(this.findColumn(columnName), x);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateDouble(int columnIndex, double x) throws SQLException {
      try {
         if (!this.onInsertRow) {
            if (!this.doingUpdates) {
               this.doingUpdates = true;
               this.syncUpdate();
            }

            this.updater.setDouble(columnIndex, x);
         } else {
            this.inserter.setDouble(columnIndex, x);
            this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
         }

      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateDouble(String columnName, double x) throws SQLException {
      try {
         this.updateDouble(this.findColumn(columnName), x);
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateFloat(int columnIndex, float x) throws SQLException {
      try {
         if (!this.onInsertRow) {
            if (!this.doingUpdates) {
               this.doingUpdates = true;
               this.syncUpdate();
            }

            this.updater.setFloat(columnIndex, x);
         } else {
            this.inserter.setFloat(columnIndex, x);
            this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
         }

      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateFloat(String columnName, float x) throws SQLException {
      try {
         this.updateFloat(this.findColumn(columnName), x);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateInt(int columnIndex, int x) throws SQLException {
      try {
         if (!this.onInsertRow) {
            if (!this.doingUpdates) {
               this.doingUpdates = true;
               this.syncUpdate();
            }

            this.updater.setInt(columnIndex, x);
         } else {
            this.inserter.setInt(columnIndex, x);
            this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
         }

      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateInt(String columnName, int x) throws SQLException {
      try {
         this.updateInt(this.findColumn(columnName), x);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateLong(int columnIndex, long x) throws SQLException {
      try {
         if (!this.onInsertRow) {
            if (!this.doingUpdates) {
               this.doingUpdates = true;
               this.syncUpdate();
            }

            this.updater.setLong(columnIndex, x);
         } else {
            this.inserter.setLong(columnIndex, x);
            this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
         }

      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateLong(String columnName, long x) throws SQLException {
      try {
         this.updateLong(this.findColumn(columnName), x);
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateNull(int columnIndex) throws SQLException {
      try {
         if (!this.onInsertRow) {
            if (!this.doingUpdates) {
               this.doingUpdates = true;
               this.syncUpdate();
            }

            this.updater.setNull(columnIndex, 0);
         } else {
            this.inserter.setNull(columnIndex, 0);
            this.thisRow.setBytes(columnIndex - 1, (byte[])null);
         }

      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateNull(String columnName) throws SQLException {
      try {
         this.updateNull(this.findColumn(columnName));
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateObject(int columnIndex, Object x) throws SQLException {
      try {
         this.updateObjectInternal(columnIndex, x, (Integer)((Integer)null), 0);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateObject(int columnIndex, Object x, int scale) throws SQLException {
      try {
         this.updateObjectInternal(columnIndex, x, (Integer)null, scale);
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   protected synchronized void updateObjectInternal(int columnIndex, Object x, Integer targetType, int scaleOrLength) throws SQLException {
      try {
         MysqlType targetMysqlType = targetType == null ? null : MysqlType.getByJdbcType(targetType);
         this.updateObjectInternal(columnIndex, x, (SQLType)targetMysqlType, scaleOrLength);
      } catch (FeatureNotAvailableException var6) {
         throw SQLError.createSQLFeatureNotSupportedException(Messages.getString("Statement.UnsupportedSQLType") + JDBCType.valueOf(targetType), "S1C00", this.getExceptionInterceptor());
      }
   }

   protected synchronized void updateObjectInternal(int columnIndex, Object x, SQLType targetType, int scaleOrLength) throws SQLException {
      if (!this.onInsertRow) {
         if (!this.doingUpdates) {
            this.doingUpdates = true;
            this.syncUpdate();
         }

         if (targetType == null) {
            this.updater.setObject(columnIndex, x);
         } else {
            this.updater.setObject(columnIndex, x, targetType);
         }
      } else {
         if (targetType == null) {
            this.inserter.setObject(columnIndex, x);
         } else {
            this.inserter.setObject(columnIndex, x, targetType);
         }

         this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
      }

   }

   public synchronized void updateObject(String columnName, Object x) throws SQLException {
      try {
         this.updateObject(this.findColumn(columnName), x);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateObject(String columnName, Object x, int scale) throws SQLException {
      try {
         this.updateObject(this.findColumn(columnName), x);
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateObject(int columnIndex, Object x, SQLType targetSqlType) throws SQLException {
      try {
         this.updateObjectInternal(columnIndex, x, (SQLType)targetSqlType, 0);
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateObject(int columnIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
      try {
         this.updateObjectInternal(columnIndex, x, targetSqlType, scaleOrLength);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateObject(String columnLabel, Object x, SQLType targetSqlType) throws SQLException {
      try {
         this.updateObjectInternal(this.findColumn(columnLabel), x, (SQLType)targetSqlType, 0);
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateObject(String columnLabel, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
      try {
         this.updateObjectInternal(this.findColumn(columnLabel), x, targetSqlType, scaleOrLength);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateRow() throws SQLException {
      try {
         if (!this.isUpdatable) {
            throw new NotUpdatable(this.notUpdatableReason);
         } else {
            if (this.doingUpdates) {
               this.updater.executeUpdate();
               this.refreshRow();
               this.doingUpdates = false;
            } else if (this.onInsertRow) {
               throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.44"), this.getExceptionInterceptor());
            }

            this.syncUpdate();
         }
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateShort(int columnIndex, short x) throws SQLException {
      try {
         if (!this.onInsertRow) {
            if (!this.doingUpdates) {
               this.doingUpdates = true;
               this.syncUpdate();
            }

            this.updater.setShort(columnIndex, x);
         } else {
            this.inserter.setShort(columnIndex, x);
            this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
         }

      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateShort(String columnName, short x) throws SQLException {
      try {
         this.updateShort(this.findColumn(columnName), x);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateString(int columnIndex, String x) throws SQLException {
      try {
         this.checkClosed();
         if (!this.onInsertRow) {
            if (!this.doingUpdates) {
               this.doingUpdates = true;
               this.syncUpdate();
            }

            this.updater.setString(columnIndex, x);
         } else {
            this.inserter.setString(columnIndex, x);
            if (x == null) {
               this.thisRow.setBytes(columnIndex - 1, (byte[])null);
            } else {
               this.thisRow.setBytes(columnIndex - 1, StringUtils.getBytes(x, this.charEncoding));
            }
         }

      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateString(String columnName, String x) throws SQLException {
      try {
         this.updateString(this.findColumn(columnName), x);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateTime(int columnIndex, Time x) throws SQLException {
      try {
         if (!this.onInsertRow) {
            if (!this.doingUpdates) {
               this.doingUpdates = true;
               this.syncUpdate();
            }

            this.updater.setTime(columnIndex, x);
         } else {
            this.inserter.setTime(columnIndex, x);
            this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
         }

      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateTime(String columnName, Time x) throws SQLException {
      try {
         this.updateTime(this.findColumn(columnName), x);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
      try {
         if (!this.onInsertRow) {
            if (!this.doingUpdates) {
               this.doingUpdates = true;
               this.syncUpdate();
            }

            this.updater.setTimestamp(columnIndex, x);
         } else {
            this.inserter.setTimestamp(columnIndex, x);
            this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
         }

      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateTimestamp(String columnName, Timestamp x) throws SQLException {
      try {
         this.updateTimestamp(this.findColumn(columnName), x);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
      try {
         throw SQLError.notUpdatable();
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
      try {
         throw SQLError.notUpdatable();
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
      try {
         throw SQLError.notUpdatable();
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
      try {
         throw SQLError.notUpdatable();
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
      try {
         throw SQLError.notUpdatable();
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
      try {
         throw SQLError.notUpdatable();
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
      try {
         throw SQLError.notUpdatable();
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
      try {
         throw SQLError.notUpdatable();
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void updateClob(int columnIndex, Reader reader) throws SQLException {
      try {
         throw SQLError.notUpdatable();
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
      try {
         throw SQLError.notUpdatable();
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
      try {
         throw SQLError.notUpdatable();
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
      try {
         this.updateNCharacterStream(columnIndex, x, (int)length);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void updateNClob(int columnIndex, Reader reader) throws SQLException {
      try {
         throw SQLError.notUpdatable();
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
      try {
         throw SQLError.notUpdatable();
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
      try {
         throw SQLError.notUpdatable();
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void updateRowId(int columnIndex, RowId x) throws SQLException {
      try {
         throw SQLError.notUpdatable();
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
      try {
         this.updateAsciiStream(this.findColumn(columnLabel), x);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
      try {
         this.updateAsciiStream(this.findColumn(columnLabel), x, length);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
      try {
         this.updateBinaryStream(this.findColumn(columnLabel), x);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
      try {
         this.updateBinaryStream(this.findColumn(columnLabel), x, length);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
      try {
         this.updateBlob(this.findColumn(columnLabel), inputStream);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
      try {
         this.updateBlob(this.findColumn(columnLabel), inputStream, length);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
      try {
         this.updateCharacterStream(this.findColumn(columnLabel), reader);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
      try {
         this.updateCharacterStream(this.findColumn(columnLabel), reader, length);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void updateClob(String columnLabel, Reader reader) throws SQLException {
      try {
         this.updateClob(this.findColumn(columnLabel), reader);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
      try {
         this.updateClob(this.findColumn(columnLabel), reader, length);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
      try {
         this.updateNCharacterStream(this.findColumn(columnLabel), reader);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
      try {
         this.updateNCharacterStream(this.findColumn(columnLabel), reader, length);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void updateNClob(String columnLabel, Reader reader) throws SQLException {
      try {
         this.updateNClob(this.findColumn(columnLabel), reader);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
      try {
         this.updateNClob(this.findColumn(columnLabel), reader, length);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
      try {
         this.updateSQLXML(this.findColumn(columnLabel), xmlObject);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateNCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
      String fieldEncoding = this.getMetadata().getFields()[columnIndex - 1].getEncoding();
      if (fieldEncoding != null && fieldEncoding.equals("UTF-8")) {
         if (!this.onInsertRow) {
            if (!this.doingUpdates) {
               this.doingUpdates = true;
               this.syncUpdate();
            }

            this.updater.setNCharacterStream(columnIndex, x, (long)length);
         } else {
            this.inserter.setNCharacterStream(columnIndex, x, (long)length);
            if (x == null) {
               this.thisRow.setBytes(columnIndex - 1, (byte[])null);
            } else {
               this.thisRow.setBytes(columnIndex - 1, STREAM_DATA_MARKER);
            }
         }

      } else {
         throw new SQLException(Messages.getString("ResultSet.16"));
      }
   }

   public synchronized void updateNCharacterStream(String columnName, Reader reader, int length) throws SQLException {
      this.updateNCharacterStream(this.findColumn(columnName), reader, length);
   }

   public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
      try {
         String fieldEncoding = this.getMetadata().getFields()[columnIndex - 1].getEncoding();
         if (fieldEncoding != null && fieldEncoding.equals("UTF-8")) {
            if (nClob == null) {
               this.updateNull(columnIndex);
            } else {
               this.updateNCharacterStream(columnIndex, nClob.getCharacterStream(), (int)nClob.length());
            }

         } else {
            throw new SQLException(Messages.getString("ResultSet.17"));
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public void updateNClob(String columnName, NClob nClob) throws SQLException {
      try {
         this.updateNClob(this.findColumn(columnName), nClob);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateNString(int columnIndex, String x) throws SQLException {
      try {
         String fieldEncoding = this.getMetadata().getFields()[columnIndex - 1].getEncoding();
         if (fieldEncoding != null && fieldEncoding.equals("UTF-8")) {
            if (!this.onInsertRow) {
               if (!this.doingUpdates) {
                  this.doingUpdates = true;
                  this.syncUpdate();
               }

               this.updater.setNString(columnIndex, x);
            } else {
               this.inserter.setNString(columnIndex, x);
               if (x == null) {
                  this.thisRow.setBytes(columnIndex - 1, (byte[])null);
               } else {
                  this.thisRow.setBytes(columnIndex - 1, StringUtils.getBytes(x, fieldEncoding));
               }
            }

         } else {
            throw new SQLException(Messages.getString("ResultSet.18"));
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public synchronized void updateNString(String columnName, String x) throws SQLException {
      try {
         this.updateNString(this.findColumn(columnName), x);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public int getHoldability() throws SQLException {
      try {
         throw SQLError.createSQLFeatureNotSupportedException();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public Reader getNCharacterStream(int columnIndex) throws SQLException {
      try {
         String fieldEncoding = this.getMetadata().getFields()[columnIndex - 1].getEncoding();
         if (fieldEncoding != null && fieldEncoding.equals("UTF-8")) {
            return this.getCharacterStream(columnIndex);
         } else {
            throw new SQLException(Messages.getString("ResultSet.11"));
         }
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public Reader getNCharacterStream(String columnName) throws SQLException {
      try {
         return this.getNCharacterStream(this.findColumn(columnName));
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public NClob getNClob(int columnIndex) throws SQLException {
      try {
         String fieldEncoding = this.getMetadata().getFields()[columnIndex - 1].getEncoding();
         if (fieldEncoding != null && fieldEncoding.equals("UTF-8")) {
            String asString = this.getStringForNClob(columnIndex);
            return asString == null ? null : new com.mysql.cj.jdbc.NClob(asString, this.getExceptionInterceptor());
         } else {
            throw new SQLException("Can not call getNClob() when field's charset isn't UTF-8");
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public NClob getNClob(String columnName) throws SQLException {
      try {
         return this.getNClob(this.findColumn(columnName));
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public String getNString(int columnIndex) throws SQLException {
      try {
         String fieldEncoding = this.getMetadata().getFields()[columnIndex - 1].getEncoding();
         if (fieldEncoding != null && fieldEncoding.equals("UTF-8")) {
            return this.getString(columnIndex);
         } else {
            throw new SQLException("Can not call getNString() when field's charset isn't UTF-8");
         }
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public String getNString(String columnName) throws SQLException {
      try {
         return this.getNString(this.findColumn(columnName));
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public RowId getRowId(int columnIndex) throws SQLException {
      try {
         throw SQLError.createSQLFeatureNotSupportedException();
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public RowId getRowId(String columnLabel) throws SQLException {
      try {
         return this.getRowId(this.findColumn(columnLabel));
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public SQLXML getSQLXML(int columnIndex) throws SQLException {
      try {
         return new MysqlSQLXML(this, columnIndex, this.getExceptionInterceptor());
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public SQLXML getSQLXML(String columnLabel) throws SQLException {
      try {
         return this.getSQLXML(this.findColumn(columnLabel));
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   private String getStringForNClob(int columnIndex) throws SQLException {
      String asString = null;
      String forcedEncoding = "UTF-8";

      try {
         byte[] asBytes = null;
         byte[] asBytes = this.getBytes(columnIndex);
         if (asBytes != null) {
            asString = new String(asBytes, forcedEncoding);
         }

         return asString;
      } catch (UnsupportedEncodingException var5) {
         throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009", this.getExceptionInterceptor());
      }
   }

   public synchronized boolean isClosed() throws SQLException {
      try {
         return this.isClosed;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
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
            throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.getExceptionInterceptor());
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }
}
