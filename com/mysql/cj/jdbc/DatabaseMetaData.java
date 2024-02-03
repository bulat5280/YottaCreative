package com.mysql.cj.jdbc;

import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.api.result.Row;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.MysqlType;
import com.mysql.cj.core.exceptions.AssertionFailedException;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.core.result.Field;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.jdbc.io.ResultSetFactory;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import com.mysql.cj.mysqla.result.ByteArrayRow;
import com.mysql.cj.mysqla.result.MysqlaColumnDefinition;
import com.mysql.cj.mysqla.result.ResultsetRowsStatic;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class DatabaseMetaData implements java.sql.DatabaseMetaData {
   protected static int maxBufferSize = 65535;
   protected static final int MAX_IDENTIFIER_LENGTH = 64;
   private static final int DEFERRABILITY = 13;
   private static final int DELETE_RULE = 10;
   private static final int FK_NAME = 11;
   private static final int FKCOLUMN_NAME = 7;
   private static final int FKTABLE_CAT = 4;
   private static final int FKTABLE_NAME = 6;
   private static final int FKTABLE_SCHEM = 5;
   private static final int KEY_SEQ = 8;
   private static final int PK_NAME = 12;
   private static final int PKCOLUMN_NAME = 3;
   private static final int PKTABLE_CAT = 0;
   private static final int PKTABLE_NAME = 2;
   private static final int PKTABLE_SCHEM = 1;
   private static final String SUPPORTS_FK = "SUPPORTS_FK";
   protected static final byte[] TABLE_AS_BYTES = "TABLE".getBytes();
   protected static final byte[] SYSTEM_TABLE_AS_BYTES = "SYSTEM TABLE".getBytes();
   private static final int UPDATE_RULE = 9;
   protected static final byte[] VIEW_AS_BYTES = "VIEW".getBytes();
   private static final String[] MYSQL_KEYWORDS = new String[]{"ACCESSIBLE", "ADD", "ALL", "ALTER", "ANALYZE", "AND", "AS", "ASC", "ASENSITIVE", "BEFORE", "BETWEEN", "BIGINT", "BINARY", "BLOB", "BOTH", "BY", "CALL", "CASCADE", "CASE", "CHANGE", "CHAR", "CHARACTER", "CHECK", "COLLATE", "COLUMN", "CONDITION", "CONSTRAINT", "CONTINUE", "CONVERT", "CREATE", "CROSS", "CURRENT_DATE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", "CURSOR", "DATABASE", "DATABASES", "DAY_HOUR", "DAY_MICROSECOND", "DAY_MINUTE", "DAY_SECOND", "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DELAYED", "DELETE", "DESC", "DESCRIBE", "DETERMINISTIC", "DISTINCT", "DISTINCTROW", "DIV", "DOUBLE", "DROP", "DUAL", "EACH", "ELSE", "ELSEIF", "ENCLOSED", "ESCAPED", "EXISTS", "EXIT", "EXPLAIN", "FALSE", "FETCH", "FLOAT", "FLOAT4", "FLOAT8", "FOR", "FORCE", "FOREIGN", "FROM", "FULLTEXT", "GENERATED", "GET", "GRANT", "GROUP", "HAVING", "HIGH_PRIORITY", "HOUR_MICROSECOND", "HOUR_MINUTE", "HOUR_SECOND", "IF", "IGNORE", "IN", "INDEX", "INFILE", "INNER", "INOUT", "INSENSITIVE", "INSERT", "INT", "INT1", "INT2", "INT3", "INT4", "INT8", "INTEGER", "INTERVAL", "INTO", "IO_AFTER_GTIDS", "IO_BEFORE_GTIDS", "IS", "ITERATE", "JOIN", "KEY", "KEYS", "KILL", "LEADING", "LEAVE", "LEFT", "LIKE", "LIMIT", "LINEAR", "LINES", "LOAD", "LOCALTIME", "LOCALTIMESTAMP", "LOCK", "LONG", "LONGBLOB", "LONGTEXT", "LOOP", "LOW_PRIORITY", "MASTER_BIND", "MASTER_SSL_VERIFY_SERVER_CERT", "MATCH", "MAXVALUE", "MEDIUMBLOB", "MEDIUMINT", "MEDIUMTEXT", "MIDDLEINT", "MINUTE_MICROSECOND", "MINUTE_SECOND", "MOD", "MODIFIES", "NATURAL", "NOT", "NO_WRITE_TO_BINLOG", "NULL", "NUMERIC", "ON", "OPTIMIZE", "OPTIMIZER_COSTS", "OPTION", "OPTIONALLY", "OR", "ORDER", "OUT", "OUTER", "OUTFILE", "PARTITION", "PRECISION", "PRIMARY", "PROCEDURE", "PURGE", "RANGE", "READ", "READS", "READ_WRITE", "REAL", "REFERENCES", "REGEXP", "RELEASE", "RENAME", "REPEAT", "REPLACE", "REQUIRE", "RESIGNAL", "RESTRICT", "RETURN", "REVOKE", "RIGHT", "RLIKE", "SCHEMA", "SCHEMAS", "SECOND_MICROSECOND", "SELECT", "SENSITIVE", "SEPARATOR", "SET", "SHOW", "SIGNAL", "SMALLINT", "SPATIAL", "SPECIFIC", "SQL", "SQLEXCEPTION", "SQLSTATE", "SQLWARNING", "SQL_BIG_RESULT", "SQL_CALC_FOUND_ROWS", "SQL_SMALL_RESULT", "SSL", "STARTING", "STORED", "STRAIGHT_JOIN", "TABLE", "TERMINATED", "THEN", "TINYBLOB", "TINYINT", "TINYTEXT", "TO", "TRAILING", "TRIGGER", "TRUE", "UNDO", "UNION", "UNIQUE", "UNLOCK", "UNSIGNED", "UPDATE", "USAGE", "USE", "USING", "UTC_DATE", "UTC_TIME", "UTC_TIMESTAMP", "VALUES", "VARBINARY", "VARCHAR", "VARCHARACTER", "VARYING", "VIRTUAL", "WHEN", "WHERE", "WHILE", "WITH", "WRITE", "XOR", "YEAR_MONTH", "ZEROFILL"};
   private static final String[] SQL2003_KEYWORDS = new String[]{"ABS", "ALL", "ALLOCATE", "ALTER", "AND", "ANY", "ARE", "ARRAY", "AS", "ASENSITIVE", "ASYMMETRIC", "AT", "ATOMIC", "AUTHORIZATION", "AVG", "BEGIN", "BETWEEN", "BIGINT", "BINARY", "BLOB", "BOOLEAN", "BOTH", "BY", "CALL", "CALLED", "CARDINALITY", "CASCADED", "CASE", "CAST", "CEIL", "CEILING", "CHAR", "CHARACTER", "CHARACTER_LENGTH", "CHAR_LENGTH", "CHECK", "CLOB", "CLOSE", "COALESCE", "COLLATE", "COLLECT", "COLUMN", "COMMIT", "CONDITION", "CONNECT", "CONSTRAINT", "CONVERT", "CORR", "CORRESPONDING", "COUNT", "COVAR_POP", "COVAR_SAMP", "CREATE", "CROSS", "CUBE", "CUME_DIST", "CURRENT", "CURRENT_DATE", "CURRENT_DEFAULT_TRANSFORM_GROUP", "CURRENT_PATH", "CURRENT_ROLE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_TRANSFORM_GROUP_FOR_TYPE", "CURRENT_USER", "CURSOR", "CYCLE", "DATE", "DAY", "DEALLOCATE", "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DELETE", "DENSE_RANK", "DEREF", "DESCRIBE", "DETERMINISTIC", "DISCONNECT", "DISTINCT", "DOUBLE", "DROP", "DYNAMIC", "EACH", "ELEMENT", "ELSE", "END", "END-EXEC", "ESCAPE", "EVERY", "EXCEPT", "EXEC", "EXECUTE", "EXISTS", "EXP", "EXTERNAL", "EXTRACT", "FALSE", "FETCH", "FILTER", "FLOAT", "FLOOR", "FOR", "FOREIGN", "FREE", "FROM", "FULL", "FUNCTION", "FUSION", "GET", "GLOBAL", "GRANT", "GROUP", "GROUPING", "HAVING", "HOLD", "HOUR", "IDENTITY", "IN", "INDICATOR", "INNER", "INOUT", "INSENSITIVE", "INSERT", "INT", "INTEGER", "INTERSECT", "INTERSECTION", "INTERVAL", "INTO", "IS", "JOIN", "LANGUAGE", "LARGE", "LATERAL", "LEADING", "LEFT", "LIKE", "LN", "LOCAL", "LOCALTIME", "LOCALTIMESTAMP", "LOWER", "MATCH", "MAX", "MEMBER", "MERGE", "METHOD", "MIN", "MINUTE", "MOD", "MODIFIES", "MODULE", "MONTH", "MULTISET", "NATIONAL", "NATURAL", "NCHAR", "NCLOB", "NEW", "NO", "NONE", "NORMALIZE", "NOT", "NULL", "NULLIF", "NUMERIC", "OCTET_LENGTH", "OF", "OLD", "ON", "ONLY", "OPEN", "OR", "ORDER", "OUT", "OUTER", "OVER", "OVERLAPS", "OVERLAY", "PARAMETER", "PARTITION", "PERCENTILE_CONT", "PERCENTILE_DISC", "PERCENT_RANK", "POSITION", "POWER", "PRECISION", "PREPARE", "PRIMARY", "PROCEDURE", "RANGE", "RANK", "READS", "REAL", "RECURSIVE", "REF", "REFERENCES", "REFERENCING", "REGR_AVGX", "REGR_AVGY", "REGR_COUNT", "REGR_INTERCEPT", "REGR_R2", "REGR_SLOPE", "REGR_SXX", "REGR_SXY", "REGR_SYY", "RELEASE", "RESULT", "RETURN", "RETURNS", "REVOKE", "RIGHT", "ROLLBACK", "ROLLUP", "ROW", "ROWS", "ROW_NUMBER", "SAVEPOINT", "SCOPE", "SCROLL", "SEARCH", "SECOND", "SELECT", "SENSITIVE", "SESSION_USER", "SET", "SIMILAR", "SMALLINT", "SOME", "SPECIFIC", "SPECIFICTYPE", "SQL", "SQLEXCEPTION", "SQLSTATE", "SQLWARNING", "SQRT", "START", "STATIC", "STDDEV_POP", "STDDEV_SAMP", "SUBMULTISET", "SUBSTRING", "SUM", "SYMMETRIC", "SYSTEM", "SYSTEM_USER", "TABLE", "TABLESAMPLE", "THEN", "TIME", "TIMESTAMP", "TIMEZONE_HOUR", "TIMEZONE_MINUTE", "TO", "TRAILING", "TRANSLATE", "TRANSLATION", "TREAT", "TRIGGER", "TRIM", "TRUE", "UESCAPE", "UNION", "UNIQUE", "UNKNOWN", "UNNEST", "UPDATE", "UPPER", "USER", "USING", "VALUE", "VALUES", "VARCHAR", "VARYING", "VAR_POP", "VAR_SAMP", "WHEN", "WHENEVER", "WHERE", "WIDTH_BUCKET", "WINDOW", "WITH", "WITHIN", "WITHOUT", "YEAR"};
   private static volatile String mysqlKeywords = null;
   protected JdbcConnection conn;
   protected String database = null;
   protected final String quotedId;
   protected boolean nullCatalogMeansCurrent;
   protected boolean nullNamePatternMatchesAll;
   protected boolean pedantic;
   protected boolean tinyInt1isBit;
   protected boolean transformedBitIsBoolean;
   protected boolean useHostsInPrivileges;
   protected ResultSetFactory resultSetFactory;
   private String metadataEncoding;
   private int metadataCollationIndex;
   private ExceptionInterceptor exceptionInterceptor;

   protected static DatabaseMetaData getInstance(JdbcConnection connToSet, String databaseToSet, boolean checkForInfoSchema, ResultSetFactory resultSetFactory) throws SQLException {
      return (DatabaseMetaData)(checkForInfoSchema && (Boolean)connToSet.getPropertySet().getBooleanReadableProperty("useInformationSchema").getValue() ? new DatabaseMetaDataUsingInfoSchema(connToSet, databaseToSet, resultSetFactory) : new DatabaseMetaData(connToSet, databaseToSet, resultSetFactory));
   }

   protected DatabaseMetaData(JdbcConnection connToSet, String databaseToSet, ResultSetFactory resultSetFactory) {
      this.conn = connToSet;
      this.database = databaseToSet;
      this.resultSetFactory = resultSetFactory;
      this.exceptionInterceptor = this.conn.getExceptionInterceptor();
      this.nullCatalogMeansCurrent = (Boolean)this.conn.getPropertySet().getBooleanReadableProperty("nullCatalogMeansCurrent").getValue();
      this.nullNamePatternMatchesAll = (Boolean)this.conn.getPropertySet().getBooleanReadableProperty("nullNamePatternMatchesAll").getValue();
      this.pedantic = (Boolean)this.conn.getPropertySet().getBooleanReadableProperty("pedantic").getValue();
      this.tinyInt1isBit = (Boolean)this.conn.getPropertySet().getBooleanReadableProperty("tinyInt1isBit").getValue();
      this.transformedBitIsBoolean = (Boolean)this.conn.getPropertySet().getBooleanReadableProperty("transformedBitIsBoolean").getValue();
      this.useHostsInPrivileges = (Boolean)this.conn.getPropertySet().getBooleanReadableProperty("useHostsInPrivileges").getValue();
      String identifierQuote = null;

      try {
         identifierQuote = this.getIdentifierQuoteString();
      } catch (SQLException var9) {
         AssertionFailedException.shouldNotHappen((Exception)var9);
      } finally {
         this.quotedId = identifierQuote;
      }

   }

   public boolean allProceduresAreCallable() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean allTablesAreSelectable() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   protected void convertToJdbcFunctionList(String catalog, ResultSet proceduresRs, boolean needsClientFiltering, String db, List<DatabaseMetaData.ComparableWrapper<String, Row>> procedureRows, int nameIndex, Field[] fields) throws SQLException {
      while(proceduresRs.next()) {
         boolean shouldAdd = true;
         String functionName;
         if (needsClientFiltering) {
            shouldAdd = false;
            functionName = proceduresRs.getString(1);
            if (db == null && functionName == null) {
               shouldAdd = true;
            } else if (db != null && db.equals(functionName)) {
               shouldAdd = true;
            }
         }

         if (shouldAdd) {
            functionName = proceduresRs.getString(nameIndex);
            byte[][] rowData = (byte[][])null;
            if (fields != null && fields.length == 9) {
               rowData = new byte[][]{catalog == null ? null : this.s2b(catalog), null, this.s2b(functionName), null, null, null, this.s2b(proceduresRs.getString("comment")), this.s2b(Integer.toString(2)), this.s2b(functionName)};
            } else {
               rowData = new byte[][]{catalog == null ? null : this.s2b(catalog), null, this.s2b(functionName), this.s2b(proceduresRs.getString("comment")), this.s2b(Integer.toString(this.getFunctionNoTableConstant())), this.s2b(functionName)};
            }

            procedureRows.add(new DatabaseMetaData.ComparableWrapper(this.getFullyQualifiedName(catalog, functionName), new ByteArrayRow(rowData, this.getExceptionInterceptor())));
         }
      }

   }

   protected String getFullyQualifiedName(String catalog, String entity) {
      StringBuilder fullyQualifiedName = new StringBuilder(StringUtils.quoteIdentifier(catalog == null ? "" : catalog, this.quotedId, this.pedantic));
      fullyQualifiedName.append('.');
      fullyQualifiedName.append(StringUtils.quoteIdentifier(entity, this.quotedId, this.pedantic));
      return fullyQualifiedName.toString();
   }

   protected int getFunctionNoTableConstant() {
      return 1;
   }

   protected void convertToJdbcProcedureList(boolean fromSelect, String catalog, ResultSet proceduresRs, boolean needsClientFiltering, String db, List<DatabaseMetaData.ComparableWrapper<String, Row>> procedureRows, int nameIndex) throws SQLException {
      while(proceduresRs.next()) {
         boolean shouldAdd = true;
         String procDb;
         if (needsClientFiltering) {
            shouldAdd = false;
            procDb = proceduresRs.getString(1);
            if (db == null && procDb == null) {
               shouldAdd = true;
            } else if (db != null && db.equals(procDb)) {
               shouldAdd = true;
            }
         }

         if (shouldAdd) {
            procDb = proceduresRs.getString(nameIndex);
            byte[][] rowData = new byte[][]{catalog == null ? null : this.s2b(catalog), null, this.s2b(procDb), null, null, null, this.s2b(proceduresRs.getString("comment")), null, null};
            boolean isFunction = fromSelect ? "FUNCTION".equalsIgnoreCase(proceduresRs.getString("type")) : false;
            rowData[7] = this.s2b(isFunction ? Integer.toString(2) : Integer.toString(1));
            rowData[8] = this.s2b(procDb);
            procedureRows.add(new DatabaseMetaData.ComparableWrapper(this.getFullyQualifiedName(catalog, procDb), new ByteArrayRow(rowData, this.getExceptionInterceptor())));
         }
      }

   }

   private Row convertTypeDescriptorToProcedureRow(byte[] procNameAsBytes, byte[] procCatAsBytes, String paramName, boolean isOutParam, boolean isInParam, boolean isReturnParam, DatabaseMetaData.TypeDescriptor typeDesc, boolean forGetFunctionColumns, int ordinal) throws SQLException {
      byte[][] row = forGetFunctionColumns ? new byte[17][] : new byte[20][];
      row[0] = procCatAsBytes;
      row[1] = null;
      row[2] = procNameAsBytes;
      row[3] = this.s2b(paramName);
      row[4] = this.s2b(String.valueOf(this.getColumnType(isOutParam, isInParam, isReturnParam, forGetFunctionColumns)));
      row[5] = this.s2b(Short.toString((short)typeDesc.mysqlType.getJdbcType()));
      row[6] = this.s2b(typeDesc.mysqlType.getName());
      row[7] = typeDesc.columnSize == null ? null : this.s2b(typeDesc.columnSize.toString());
      row[8] = row[7];
      row[9] = typeDesc.decimalDigits == null ? null : this.s2b(typeDesc.decimalDigits.toString());
      row[10] = this.s2b(Integer.toString(typeDesc.numPrecRadix));
      switch(typeDesc.nullability) {
      case 0:
         row[11] = this.s2b(String.valueOf(0));
         break;
      case 1:
         row[11] = this.s2b(String.valueOf(1));
         break;
      case 2:
         row[11] = this.s2b(String.valueOf(2));
         break;
      default:
         throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.1"), "S1000", this.getExceptionInterceptor());
      }

      row[12] = null;
      if (forGetFunctionColumns) {
         row[13] = null;
         row[14] = this.s2b(String.valueOf(ordinal));
         row[15] = this.s2b(typeDesc.isNullable);
         row[16] = procNameAsBytes;
      } else {
         row[13] = null;
         row[14] = null;
         row[15] = null;
         row[16] = null;
         row[17] = this.s2b(String.valueOf(ordinal));
         row[18] = this.s2b(typeDesc.isNullable);
         row[19] = procNameAsBytes;
      }

      return new ByteArrayRow(row, this.getExceptionInterceptor());
   }

   protected int getColumnType(boolean isOutParam, boolean isInParam, boolean isReturnParam, boolean forGetFunctionColumns) {
      return getProcedureOrFunctionColumnType(isOutParam, isInParam, isReturnParam, forGetFunctionColumns);
   }

   protected static int getProcedureOrFunctionColumnType(boolean isOutParam, boolean isInParam, boolean isReturnParam, boolean forGetFunctionColumns) {
      if (isInParam && isOutParam) {
         return forGetFunctionColumns ? 2 : 2;
      } else if (isInParam) {
         return forGetFunctionColumns ? 1 : 1;
      } else if (isOutParam) {
         return forGetFunctionColumns ? 3 : 4;
      } else if (isReturnParam) {
         return forGetFunctionColumns ? 4 : 5;
      } else {
         return forGetFunctionColumns ? 0 : 0;
      }
   }

   protected ExceptionInterceptor getExceptionInterceptor() {
      return this.exceptionInterceptor;
   }

   public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean deletesAreDetected(int type) throws SQLException {
      try {
         return false;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public List<Row> extractForeignKeyForTable(ArrayList<Row> rows, ResultSet rs, String catalog) throws SQLException {
      byte[][] row = new byte[][]{rs.getBytes(1), this.s2b("SUPPORTS_FK"), null};
      String createTableString = rs.getString(2);
      StringTokenizer lineTokenizer = new StringTokenizer(createTableString, "\n");
      StringBuilder commentBuf = new StringBuilder("comment; ");
      boolean firstTime = true;

      while(lineTokenizer.hasMoreTokens()) {
         String line = lineTokenizer.nextToken().trim();
         String constraintName = null;
         if (StringUtils.startsWithIgnoreCase(line, "CONSTRAINT")) {
            boolean usingBackTicks = true;
            int beginPos = StringUtils.indexOfQuoteDoubleAware(line, this.quotedId, 0);
            if (beginPos == -1) {
               beginPos = line.indexOf("\"");
               usingBackTicks = false;
            }

            if (beginPos != -1) {
               int endPos = true;
               int endPos;
               if (usingBackTicks) {
                  endPos = StringUtils.indexOfQuoteDoubleAware(line, this.quotedId, beginPos + 1);
               } else {
                  endPos = StringUtils.indexOfQuoteDoubleAware(line, "\"", beginPos + 1);
               }

               if (endPos != -1) {
                  constraintName = line.substring(beginPos + 1, endPos);
                  line = line.substring(endPos + 1, line.length()).trim();
               }
            }
         }

         if (line.startsWith("FOREIGN KEY")) {
            if (line.endsWith(",")) {
               line = line.substring(0, line.length() - 1);
            }

            int indexOfFK = line.indexOf("FOREIGN KEY");
            String localColumnName = null;
            String referencedCatalogName = StringUtils.quoteIdentifier(catalog, this.quotedId, this.pedantic);
            String referencedTableName = null;
            String referencedColumnName = null;
            int lastParenIndex;
            if (indexOfFK != -1) {
               lastParenIndex = indexOfFK + "FOREIGN KEY".length();
               int indexOfRef = StringUtils.indexOfIgnoreCase(lastParenIndex, line, "REFERENCES", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__ALL);
               if (indexOfRef != -1) {
                  int indexOfParenOpen = line.indexOf(40, lastParenIndex);
                  int indexOfParenClose = StringUtils.indexOfIgnoreCase(indexOfParenOpen, line, ")", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__ALL);
                  if (indexOfParenOpen != -1 && indexOfParenClose == -1) {
                  }

                  localColumnName = line.substring(indexOfParenOpen + 1, indexOfParenClose);
                  int afterRef = indexOfRef + "REFERENCES".length();
                  int referencedColumnBegin = StringUtils.indexOfIgnoreCase(afterRef, line, "(", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__ALL);
                  if (referencedColumnBegin != -1) {
                     referencedTableName = line.substring(afterRef, referencedColumnBegin);
                     int referencedColumnEnd = StringUtils.indexOfIgnoreCase(referencedColumnBegin + 1, line, ")", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__ALL);
                     if (referencedColumnEnd != -1) {
                        referencedColumnName = line.substring(referencedColumnBegin + 1, referencedColumnEnd);
                     }

                     int indexOfCatalogSep = StringUtils.indexOfIgnoreCase(0, referencedTableName, (String)".", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__ALL);
                     if (indexOfCatalogSep != -1) {
                        referencedCatalogName = referencedTableName.substring(0, indexOfCatalogSep);
                        referencedTableName = referencedTableName.substring(indexOfCatalogSep + 1);
                     }
                  }
               }
            }

            if (!firstTime) {
               commentBuf.append("; ");
            } else {
               firstTime = false;
            }

            if (constraintName != null) {
               commentBuf.append(constraintName);
            } else {
               commentBuf.append("not_available");
            }

            commentBuf.append("(");
            commentBuf.append(localColumnName);
            commentBuf.append(") REFER ");
            commentBuf.append(referencedCatalogName);
            commentBuf.append("/");
            commentBuf.append(referencedTableName);
            commentBuf.append("(");
            commentBuf.append(referencedColumnName);
            commentBuf.append(")");
            lastParenIndex = line.lastIndexOf(")");
            if (lastParenIndex != line.length() - 1) {
               String cascadeOptions = line.substring(lastParenIndex + 1);
               commentBuf.append(" ");
               commentBuf.append(cascadeOptions);
            }
         }
      }

      row[2] = this.s2b(commentBuf.toString());
      rows.add(new ByteArrayRow(row, this.getExceptionInterceptor()));
      return rows;
   }

   public ResultSet extractForeignKeyFromCreateTable(String catalog, String tableName) throws SQLException {
      ArrayList<String> tableList = new ArrayList();
      ResultSet rs = null;
      Statement stmt = null;
      if (tableName != null) {
         tableList.add(tableName);
      } else {
         try {
            rs = this.getTables(catalog, "", "%", new String[]{"TABLE"});

            while(rs.next()) {
               tableList.add(rs.getString("TABLE_NAME"));
            }
         } finally {
            if (rs != null) {
               rs.close();
            }

            rs = null;
         }
      }

      ArrayList<Row> rows = new ArrayList();
      Field[] fields = new Field[]{new Field("", "Name", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, Integer.MAX_VALUE), new Field("", "Type", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "Comment", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, Integer.MAX_VALUE)};
      int numTables = tableList.size();
      stmt = this.conn.getMetadataSafeStatement();

      try {
         for(int i = 0; i < numTables; ++i) {
            String tableToExtract = (String)tableList.get(i);
            String query = "SHOW CREATE TABLE " + this.getFullyQualifiedName(catalog, tableToExtract);

            try {
               rs = stmt.executeQuery(query);
            } catch (SQLException var21) {
               String sqlState = var21.getSQLState();
               if (!"42S02".equals(sqlState) && var21.getErrorCode() != 1146) {
                  throw var21;
               }
               continue;
            }

            while(rs.next()) {
               this.extractForeignKeyForTable(rows, rs, catalog);
            }
         }
      } finally {
         if (rs != null) {
            rs.close();
         }

         rs = null;
         if (stmt != null) {
            stmt.close();
         }

         stmt = null;
      }

      return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new MysqlaColumnDefinition(fields)));
   }

   public ResultSet getAttributes(String arg0, String arg1, String arg2, String arg3) throws SQLException {
      try {
         Field[] fields = new Field[]{new Field("", "TYPE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "TYPE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "ATTR_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 32), new Field("", "ATTR_TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "ATTR_SIZE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "DECIMAL_DIGITS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "NUM_PREC_RADIX", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "NULLABLE ", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "REMARKS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "ATTR_DEF", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "SQL_DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "SQL_DATETIME_SUB", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "CHAR_OCTET_LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "ORDINAL_POSITION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "IS_NULLABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "SCOPE_CATALOG", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "SCOPE_SCHEMA", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "SCOPE_TABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "SOURCE_DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 32)};
         return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(new ArrayList(), new MysqlaColumnDefinition(fields)));
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   public ResultSet getBestRowIdentifier(String catalog, String schema, final String table, int scope, boolean nullable) throws SQLException {
      try {
         if (table == null) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.2"), "S1009", this.getExceptionInterceptor());
         } else {
            Field[] fields = new Field[]{new Field("", "SCOPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 5), new Field("", "COLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "COLUMN_SIZE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "BUFFER_LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "DECIMAL_DIGITS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 10), new Field("", "PSEUDO_COLUMN", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 5)};
            final ArrayList<Row> rows = new ArrayList();
            final Statement stmt = this.conn.getMetadataSafeStatement();

            try {
               (new IterateBlock<String>(this.getCatalogIterator(catalog)) {
                  void forEach(String catalogStr) throws SQLException {
                     ResultSet results = null;

                     try {
                        StringBuilder queryBuf = new StringBuilder("SHOW COLUMNS FROM ");
                        queryBuf.append(StringUtils.quoteIdentifier(table, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                        queryBuf.append(" FROM ");
                        queryBuf.append(StringUtils.quoteIdentifier(catalogStr, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                        results = stmt.executeQuery(queryBuf.toString());

                        while(true) {
                           String keyType;
                           do {
                              do {
                                 if (!results.next()) {
                                    return;
                                 }

                                 keyType = results.getString("Key");
                              } while(keyType == null);
                           } while(!StringUtils.startsWithIgnoreCase(keyType, "PRI"));

                           byte[][] rowVal = new byte[8][];
                           rowVal[0] = Integer.toString(2).getBytes();
                           rowVal[1] = results.getBytes("Field");
                           String type = results.getString("Type");
                           int size = stmt.getMaxFieldSize();
                           int decimals = 0;
                           if (type.indexOf("enum") == -1) {
                              if (type.indexOf("(") != -1) {
                                 if (type.indexOf(",") != -1) {
                                    size = Integer.parseInt(type.substring(type.indexOf("(") + 1, type.indexOf(",")));
                                    decimals = Integer.parseInt(type.substring(type.indexOf(",") + 1, type.indexOf(")")));
                                 } else {
                                    size = Integer.parseInt(type.substring(type.indexOf("(") + 1, type.indexOf(")")));
                                 }

                                 type = type.substring(0, type.indexOf("("));
                              }
                           } else {
                              String temp = type.substring(type.indexOf("("), type.indexOf(")"));
                              StringTokenizer tokenizer = new StringTokenizer(temp, ",");

                              int maxLength;
                              for(maxLength = 0; tokenizer.hasMoreTokens(); maxLength = Math.max(maxLength, tokenizer.nextToken().length() - 2)) {
                              }

                              size = maxLength;
                              decimals = 0;
                              type = "enum";
                           }

                           MysqlType ft = MysqlType.getByName(type.toUpperCase());
                           rowVal[2] = DatabaseMetaData.this.s2b(String.valueOf(ft.getJdbcType()));
                           rowVal[3] = DatabaseMetaData.this.s2b(type);
                           rowVal[4] = Integer.toString(size + decimals).getBytes();
                           rowVal[5] = Integer.toString(size + decimals).getBytes();
                           rowVal[6] = Integer.toString(decimals).getBytes();
                           rowVal[7] = Integer.toString(1).getBytes();
                           rows.add(new ByteArrayRow(rowVal, DatabaseMetaData.this.getExceptionInterceptor()));
                        }
                     } catch (SQLException var20) {
                        if (!"42S02".equals(var20.getSQLState())) {
                           throw var20;
                        }
                     } finally {
                        if (results != null) {
                           try {
                              results.close();
                           } catch (Exception var19) {
                           }

                           results = null;
                        }

                     }

                  }
               }).doForAll();
            } finally {
               if (stmt != null) {
                  stmt.close();
               }

            }

            ResultSet results = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new MysqlaColumnDefinition(fields)));
            return results;
         }
      } catch (CJException var14) {
         throw SQLExceptionsMapping.translateException(var14, this.getExceptionInterceptor());
      }
   }

   private void getCallStmtParameterTypes(String catalog, String quotedProcName, DatabaseMetaData.ProcedureType procType, String parameterNamePattern, List<Row> resultRows, boolean forGetFunctionColumns) throws SQLException {
      Statement paramRetrievalStmt = null;
      ResultSet paramRetrievalRs = null;
      if (parameterNamePattern == null) {
         if (!this.nullNamePatternMatchesAll) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.3"), "S1009", this.getExceptionInterceptor());
         }

         parameterNamePattern = "%";
      }

      String parameterDef = null;
      byte[] procNameAsBytes = null;
      byte[] procCatAsBytes = null;
      boolean isProcedureInAnsiMode = false;
      String storageDefnDelims = null;
      String storageDefnClosures = null;
      boolean var41 = false;

      String paramName;
      int wildCompareRes;
      byte[] procNameAsBytes;
      byte[] procCatAsBytes;
      try {
         var41 = true;
         paramRetrievalStmt = this.conn.getMetadataSafeStatement();
         String oldCatalog = this.conn.getCatalog();
         if (this.conn.lowerCaseTableNames() && catalog != null && catalog.length() != 0 && oldCatalog != null && oldCatalog.length() != 0) {
            ResultSet rs = null;

            try {
               this.conn.setCatalog(StringUtils.unQuoteIdentifier(catalog, this.quotedId));
               rs = paramRetrievalStmt.executeQuery("SELECT DATABASE()");
               rs.next();
               catalog = rs.getString(1);
            } finally {
               this.conn.setCatalog(oldCatalog);
               if (rs != null) {
                  rs.close();
               }

            }
         }

         if (paramRetrievalStmt.getMaxRows() != 0) {
            paramRetrievalStmt.setMaxRows(0);
         }

         int dotIndex = true;
         int dotIndex;
         if (!" ".equals(this.quotedId)) {
            dotIndex = StringUtils.indexOfIgnoreCase(0, quotedProcName, (String)".", this.quotedId, this.quotedId, this.conn.isNoBackslashEscapesSet() ? StringUtils.SEARCH_MODE__MRK_COM_WS : StringUtils.SEARCH_MODE__ALL);
         } else {
            dotIndex = quotedProcName.indexOf(".");
         }

         String dbName = null;
         if (dotIndex != -1 && dotIndex + 1 < quotedProcName.length()) {
            dbName = quotedProcName.substring(0, dotIndex);
            quotedProcName = quotedProcName.substring(dotIndex + 1);
         } else {
            dbName = StringUtils.quoteIdentifier(catalog, this.quotedId, this.pedantic);
         }

         String tmpProcName = StringUtils.unQuoteIdentifier(quotedProcName, this.quotedId);
         procNameAsBytes = StringUtils.getBytes(tmpProcName, "UTF-8");
         tmpProcName = StringUtils.unQuoteIdentifier(dbName, this.quotedId);
         procCatAsBytes = StringUtils.getBytes(tmpProcName, "UTF-8");
         StringBuilder procNameBuf = new StringBuilder();
         procNameBuf.append(dbName);
         procNameBuf.append('.');
         procNameBuf.append(quotedProcName);
         String fieldName = null;
         if (procType == DatabaseMetaData.ProcedureType.PROCEDURE) {
            paramRetrievalRs = paramRetrievalStmt.executeQuery("SHOW CREATE PROCEDURE " + procNameBuf.toString());
            fieldName = "Create Procedure";
         } else {
            paramRetrievalRs = paramRetrievalStmt.executeQuery("SHOW CREATE FUNCTION " + procNameBuf.toString());
            fieldName = "Create Function";
         }

         if (!paramRetrievalRs.next()) {
            var41 = false;
         } else {
            paramName = paramRetrievalRs.getString(fieldName);
            if (!(Boolean)this.conn.getPropertySet().getBooleanReadableProperty("noAccessToProcedureBodies").getValue() && (paramName == null || paramName.length() == 0)) {
               throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.4"), "S1000", this.getExceptionInterceptor());
            }

            String identifierMarkers;
            try {
               identifierMarkers = paramRetrievalRs.getString("sql_mode");
               if (StringUtils.indexOfIgnoreCase(identifierMarkers, "ANSI") != -1) {
                  isProcedureInAnsiMode = true;
               }
            } catch (SQLException var53) {
            }

            identifierMarkers = isProcedureInAnsiMode ? "`\"" : "`";
            String identifierAndStringMarkers = "'" + identifierMarkers;
            storageDefnDelims = "(" + identifierMarkers;
            storageDefnClosures = ")" + identifierMarkers;
            if (paramName == null) {
               var41 = false;
            } else if (paramName.length() != 0) {
               paramName = StringUtils.stripComments(paramName, identifierAndStringMarkers, identifierAndStringMarkers, true, false, true, true);
               int openParenIndex = StringUtils.indexOfIgnoreCase(0, paramName, (String)"(", this.quotedId, this.quotedId, this.conn.isNoBackslashEscapesSet() ? StringUtils.SEARCH_MODE__MRK_COM_WS : StringUtils.SEARCH_MODE__ALL);
               int endOfParamDeclarationIndex = false;
               int endOfParamDeclarationIndex = this.endPositionOfParameterDeclaration(openParenIndex, paramName, this.quotedId);
               if (procType == DatabaseMetaData.ProcedureType.FUNCTION) {
                  wildCompareRes = StringUtils.indexOfIgnoreCase(0, paramName, (String)" RETURNS ", this.quotedId, this.quotedId, this.conn.isNoBackslashEscapesSet() ? StringUtils.SEARCH_MODE__MRK_COM_WS : StringUtils.SEARCH_MODE__ALL);
                  int endReturnsDef = this.findEndOfReturnsClause(paramName, wildCompareRes);

                  int declarationStart;
                  for(declarationStart = wildCompareRes + "RETURNS ".length(); declarationStart < paramName.length() && Character.isWhitespace(paramName.charAt(declarationStart)); ++declarationStart) {
                  }

                  String returnsDefn = paramName.substring(declarationStart, endReturnsDef).trim();
                  DatabaseMetaData.TypeDescriptor returnDescriptor = new DatabaseMetaData.TypeDescriptor(returnsDefn, "YES");
                  resultRows.add(this.convertTypeDescriptorToProcedureRow(procNameAsBytes, procCatAsBytes, "", false, false, true, returnDescriptor, forGetFunctionColumns, 0));
               }

               if (openParenIndex == -1 || endOfParamDeclarationIndex == -1) {
                  throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.5"), "S1000", this.getExceptionInterceptor());
               }

               parameterDef = paramName.substring(openParenIndex + 1, endOfParamDeclarationIndex);
               var41 = false;
            } else {
               var41 = false;
            }
         }
      } finally {
         if (var41) {
            SQLException sqlExRethrow = null;
            if (paramRetrievalRs != null) {
               try {
                  paramRetrievalRs.close();
               } catch (SQLException var50) {
                  sqlExRethrow = var50;
               }

               paramRetrievalRs = null;
            }

            if (paramRetrievalStmt != null) {
               try {
                  paramRetrievalStmt.close();
               } catch (SQLException var49) {
                  sqlExRethrow = var49;
               }

               paramRetrievalStmt = null;
            }

            if (sqlExRethrow != null) {
               throw sqlExRethrow;
            }

         }
      }

      SQLException sqlExRethrow = null;
      if (paramRetrievalRs != null) {
         try {
            paramRetrievalRs.close();
         } catch (SQLException var52) {
            sqlExRethrow = var52;
         }

         paramRetrievalRs = null;
      }

      if (paramRetrievalStmt != null) {
         try {
            paramRetrievalStmt.close();
         } catch (SQLException var51) {
            sqlExRethrow = var51;
         }

         paramRetrievalStmt = null;
      }

      if (sqlExRethrow != null) {
         throw sqlExRethrow;
      } else {
         if (parameterDef != null) {
            int ordinal = 1;
            List<String> parseList = StringUtils.split(parameterDef, ",", storageDefnDelims, storageDefnClosures, true);
            int parseListLen = parseList.size();

            for(int i = 0; i < parseListLen; ++i) {
               String declaration = (String)parseList.get(i);
               if (declaration.trim().length() == 0) {
                  break;
               }

               declaration = declaration.replaceAll("[\\t\\n\\x0B\\f\\r]", " ");
               StringTokenizer declarationTok = new StringTokenizer(declaration, " \t");
               paramName = null;
               boolean isOutParam = false;
               boolean isInParam = false;
               if (!declarationTok.hasMoreTokens()) {
                  throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.8"), "S1000", this.getExceptionInterceptor());
               }

               String possibleParamName = declarationTok.nextToken();
               if (possibleParamName.equalsIgnoreCase("OUT")) {
                  isOutParam = true;
                  if (!declarationTok.hasMoreTokens()) {
                     throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.6"), "S1000", this.getExceptionInterceptor());
                  }

                  paramName = declarationTok.nextToken();
               } else if (possibleParamName.equalsIgnoreCase("INOUT")) {
                  isOutParam = true;
                  isInParam = true;
                  if (!declarationTok.hasMoreTokens()) {
                     throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.6"), "S1000", this.getExceptionInterceptor());
                  }

                  paramName = declarationTok.nextToken();
               } else if (possibleParamName.equalsIgnoreCase("IN")) {
                  isOutParam = false;
                  isInParam = true;
                  if (!declarationTok.hasMoreTokens()) {
                     throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.6"), "S1000", this.getExceptionInterceptor());
                  }

                  paramName = declarationTok.nextToken();
               } else {
                  isOutParam = false;
                  isInParam = true;
                  paramName = possibleParamName;
               }

               DatabaseMetaData.TypeDescriptor typeDesc = null;
               if (!declarationTok.hasMoreTokens()) {
                  throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.7"), "S1000", this.getExceptionInterceptor());
               }

               StringBuilder typeInfoBuf = new StringBuilder(declarationTok.nextToken());

               while(declarationTok.hasMoreTokens()) {
                  typeInfoBuf.append(" ");
                  typeInfoBuf.append(declarationTok.nextToken());
               }

               String typeInfo = typeInfoBuf.toString();
               typeDesc = new DatabaseMetaData.TypeDescriptor(typeInfo, "YES");
               if (paramName.startsWith("`") && paramName.endsWith("`") || isProcedureInAnsiMode && paramName.startsWith("\"") && paramName.endsWith("\"")) {
                  paramName = paramName.substring(1, paramName.length() - 1);
               }

               wildCompareRes = StringUtils.wildCompare(paramName, parameterNamePattern);
               if (wildCompareRes != -1) {
                  Row row = this.convertTypeDescriptorToProcedureRow(procNameAsBytes, procCatAsBytes, paramName, isOutParam, isInParam, false, typeDesc, forGetFunctionColumns, ordinal++);
                  resultRows.add(row);
               }
            }
         }

      }
   }

   private int endPositionOfParameterDeclaration(int beginIndex, String procedureDef, String quoteChar) throws SQLException {
      int currentPos = beginIndex + 1;
      int parenDepth = 1;

      while(true) {
         if (parenDepth > 0 && currentPos < procedureDef.length()) {
            int closedParenIndex = StringUtils.indexOfIgnoreCase(currentPos, procedureDef, ")", quoteChar, quoteChar, this.conn.isNoBackslashEscapesSet() ? StringUtils.SEARCH_MODE__MRK_COM_WS : StringUtils.SEARCH_MODE__ALL);
            if (closedParenIndex != -1) {
               int nextOpenParenIndex = StringUtils.indexOfIgnoreCase(currentPos, procedureDef, "(", quoteChar, quoteChar, this.conn.isNoBackslashEscapesSet() ? StringUtils.SEARCH_MODE__MRK_COM_WS : StringUtils.SEARCH_MODE__ALL);
               if (nextOpenParenIndex != -1 && nextOpenParenIndex < closedParenIndex) {
                  ++parenDepth;
                  currentPos = closedParenIndex + 1;
                  continue;
               }

               --parenDepth;
               currentPos = closedParenIndex;
               continue;
            }

            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.5"), "S1000", this.getExceptionInterceptor());
         }

         return currentPos;
      }
   }

   private int findEndOfReturnsClause(String procedureDefn, int positionOfReturnKeyword) throws SQLException {
      String openingMarkers = this.quotedId + "(";
      String closingMarkers = this.quotedId + ")";
      String[] tokens = new String[]{"LANGUAGE", "NOT", "DETERMINISTIC", "CONTAINS", "NO", "READ", "MODIFIES", "SQL", "COMMENT", "BEGIN", "RETURN"};
      int startLookingAt = positionOfReturnKeyword + "RETURNS".length() + 1;
      int endOfReturn = -1;

      int i;
      for(i = 0; i < tokens.length; ++i) {
         int nextEndOfReturn = StringUtils.indexOfIgnoreCase(startLookingAt, procedureDefn, tokens[i], openingMarkers, closingMarkers, this.conn.isNoBackslashEscapesSet() ? StringUtils.SEARCH_MODE__MRK_COM_WS : StringUtils.SEARCH_MODE__ALL);
         if (nextEndOfReturn != -1 && (endOfReturn == -1 || nextEndOfReturn < endOfReturn)) {
            endOfReturn = nextEndOfReturn;
         }
      }

      if (endOfReturn != -1) {
         return endOfReturn;
      } else {
         endOfReturn = StringUtils.indexOfIgnoreCase(startLookingAt, procedureDefn, ":", openingMarkers, closingMarkers, this.conn.isNoBackslashEscapesSet() ? StringUtils.SEARCH_MODE__MRK_COM_WS : StringUtils.SEARCH_MODE__ALL);
         if (endOfReturn != -1) {
            for(i = endOfReturn; i > 0; --i) {
               if (Character.isWhitespace(procedureDefn.charAt(i))) {
                  return i;
               }
            }
         }

         throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.5"), "S1000", this.getExceptionInterceptor());
      }
   }

   private int getCascadeDeleteOption(String cascadeOptions) {
      int onDeletePos = cascadeOptions.indexOf("ON DELETE");
      if (onDeletePos != -1) {
         String deleteOptions = cascadeOptions.substring(onDeletePos, cascadeOptions.length());
         if (deleteOptions.startsWith("ON DELETE CASCADE")) {
            return 0;
         }

         if (deleteOptions.startsWith("ON DELETE SET NULL")) {
            return 2;
         }

         if (deleteOptions.startsWith("ON DELETE RESTRICT")) {
            return 1;
         }

         if (deleteOptions.startsWith("ON DELETE NO ACTION")) {
            return 3;
         }
      }

      return 3;
   }

   private int getCascadeUpdateOption(String cascadeOptions) {
      int onUpdatePos = cascadeOptions.indexOf("ON UPDATE");
      if (onUpdatePos != -1) {
         String updateOptions = cascadeOptions.substring(onUpdatePos, cascadeOptions.length());
         if (updateOptions.startsWith("ON UPDATE CASCADE")) {
            return 0;
         }

         if (updateOptions.startsWith("ON UPDATE SET NULL")) {
            return 2;
         }

         if (updateOptions.startsWith("ON UPDATE RESTRICT")) {
            return 1;
         }

         if (updateOptions.startsWith("ON UPDATE NO ACTION")) {
            return 3;
         }
      }

      return 3;
   }

   protected DatabaseMetaData.IteratorWithCleanup<String> getCatalogIterator(String catalogSpec) throws SQLException {
      Object allCatalogsIter;
      if (catalogSpec != null) {
         if (!catalogSpec.equals("")) {
            if (this.pedantic) {
               allCatalogsIter = new DatabaseMetaData.SingleStringIterator(catalogSpec);
            } else {
               allCatalogsIter = new DatabaseMetaData.SingleStringIterator(StringUtils.unQuoteIdentifier(catalogSpec, this.quotedId));
            }
         } else {
            allCatalogsIter = new DatabaseMetaData.SingleStringIterator(this.database);
         }
      } else if (this.nullCatalogMeansCurrent) {
         allCatalogsIter = new DatabaseMetaData.SingleStringIterator(this.database);
      } else {
         allCatalogsIter = new DatabaseMetaData.ResultSetIterator(this.getCatalogs(), 1);
      }

      return (DatabaseMetaData.IteratorWithCleanup)allCatalogsIter;
   }

   public ResultSet getCatalogs() throws SQLException {
      try {
         ResultSet results = null;
         Statement stmt = null;

         try {
            stmt = this.conn.getMetadataSafeStatement();
            results = stmt.executeQuery("SHOW DATABASES");
            int catalogsCount = 0;
            if (results.last()) {
               catalogsCount = results.getRow();
               results.beforeFirst();
            }

            ArrayList resultsAsList = new ArrayList(catalogsCount);

            while(results.next()) {
               resultsAsList.add(results.getString(1));
            }

            Collections.sort(resultsAsList);
            Field[] fields = new Field[]{new Field("", "TABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, results.getMetaData().getColumnDisplaySize(1))};
            ArrayList<Row> tuples = new ArrayList(catalogsCount);
            Iterator var7 = resultsAsList.iterator();

            while(var7.hasNext()) {
               String cat = (String)var7.next();
               byte[][] rowVal = new byte[][]{this.s2b(cat)};
               tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
            }

            ResultSetImpl var23 = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(tuples, new MysqlaColumnDefinition(fields)));
            return var23;
         } finally {
            if (results != null) {
               try {
                  results.close();
               } catch (SQLException var20) {
                  AssertionFailedException.shouldNotHappen((Exception)var20);
               }

               results = null;
            }

            if (stmt != null) {
               try {
                  stmt.close();
               } catch (SQLException var19) {
                  AssertionFailedException.shouldNotHappen((Exception)var19);
               }

               stmt = null;
            }

         }
      } catch (CJException var22) {
         throw SQLExceptionsMapping.translateException(var22, this.getExceptionInterceptor());
      }
   }

   public String getCatalogSeparator() throws SQLException {
      try {
         return ".";
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public String getCatalogTerm() throws SQLException {
      try {
         return "database";
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) throws SQLException {
      try {
         Field[] fields = new Field[]{new Field("", "TABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 64), new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 1), new Field("", "TABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 64), new Field("", "COLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 64), new Field("", "GRANTOR", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 77), new Field("", "GRANTEE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 77), new Field("", "PRIVILEGE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 64), new Field("", "IS_GRANTABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 3)};
         String grantQuery = "SELECT c.host, c.db, t.grantor, c.user, c.table_name, c.column_name, c.column_priv FROM mysql.columns_priv c, mysql.tables_priv t WHERE c.host = t.host AND c.db = t.db AND c.table_name = t.table_name AND c.db LIKE ? AND c.table_name = ? AND c.column_name LIKE ?";
         java.sql.PreparedStatement pStmt = null;
         ResultSet results = null;
         ArrayList grantRows = new ArrayList();

         try {
            pStmt = this.prepareMetaDataSafeStatement(grantQuery);
            pStmt.setString(1, catalog != null && catalog.length() != 0 ? catalog : "%");
            pStmt.setString(2, table);
            pStmt.setString(3, columnNamePattern);
            results = pStmt.executeQuery();

            while(true) {
               String db;
               String grantor;
               StringBuilder fullUser;
               String columnName;
               String allPrivileges;
               do {
                  if (!results.next()) {
                     return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(grantRows, new MysqlaColumnDefinition(fields)));
                  }

                  String host = results.getString(1);
                  db = results.getString(2);
                  grantor = results.getString(3);
                  String user = results.getString(4);
                  if (user == null || user.length() == 0) {
                     user = "%";
                  }

                  fullUser = new StringBuilder(user);
                  if (host != null && this.useHostsInPrivileges) {
                     fullUser.append("@");
                     fullUser.append(host);
                  }

                  columnName = results.getString(6);
                  allPrivileges = results.getString(7);
               } while(allPrivileges == null);

               allPrivileges = allPrivileges.toUpperCase(Locale.ENGLISH);
               StringTokenizer st = new StringTokenizer(allPrivileges, ",");

               while(st.hasMoreTokens()) {
                  String privilege = st.nextToken().trim();
                  byte[][] tuple = new byte[][]{this.s2b(db), null, this.s2b(table), this.s2b(columnName), grantor != null ? this.s2b(grantor) : null, this.s2b(fullUser.toString()), this.s2b(privilege), null};
                  grantRows.add(new ByteArrayRow(tuple, this.getExceptionInterceptor()));
               }
            }
         } finally {
            if (results != null) {
               try {
                  results.close();
               } catch (Exception var30) {
               }

               results = null;
            }

            if (pStmt != null) {
               try {
                  pStmt.close();
               } catch (Exception var29) {
               }

               pStmt = null;
            }

         }
      } catch (CJException var32) {
         throw SQLExceptionsMapping.translateException(var32, this.getExceptionInterceptor());
      }
   }

   public ResultSet getColumns(String catalog, final String schemaPattern, final String tableNamePattern, String columnNamePattern) throws SQLException {
      try {
         if (columnNamePattern == null) {
            if (!this.nullNamePatternMatchesAll) {
               throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.9"), "S1009", this.getExceptionInterceptor());
            }

            columnNamePattern = "%";
         }

         final String colPattern = columnNamePattern;
         Field[] fields = this.createColumnsFields();
         final ArrayList<Row> rows = new ArrayList();
         final Statement stmt = this.conn.getMetadataSafeStatement();

         try {
            (new IterateBlock<String>(this.getCatalogIterator(catalog)) {
               void forEach(String catalogStr) throws SQLException {
                  ArrayList<String> tableNameList = new ArrayList();
                  ResultSet tables;
                  String tableName;
                  if (tableNamePattern == null) {
                     tables = null;

                     try {
                        tables = DatabaseMetaData.this.getTables(catalogStr, schemaPattern, "%", new String[0]);

                        while(tables.next()) {
                           tableName = tables.getString("TABLE_NAME");
                           tableNameList.add(tableName);
                        }
                     } finally {
                        if (tables != null) {
                           try {
                              tables.close();
                           } catch (Exception var43) {
                              AssertionFailedException.shouldNotHappen(var43);
                           }

                           tables = null;
                        }

                     }
                  } else {
                     tables = null;

                     try {
                        tables = DatabaseMetaData.this.getTables(catalogStr, schemaPattern, tableNamePattern, new String[0]);

                        while(tables.next()) {
                           tableName = tables.getString("TABLE_NAME");
                           tableNameList.add(tableName);
                        }
                     } finally {
                        if (tables != null) {
                           try {
                              tables.close();
                           } catch (SQLException var45) {
                              AssertionFailedException.shouldNotHappen((Exception)var45);
                           }

                           tables = null;
                        }

                     }
                  }

                  Iterator var50 = tableNameList.iterator();

                  while(var50.hasNext()) {
                     tableName = (String)var50.next();
                     ResultSet results = null;

                     try {
                        StringBuilder queryBuf = new StringBuilder("SHOW FULL COLUMNS FROM ");
                        queryBuf.append(StringUtils.quoteIdentifier(tableName, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                        queryBuf.append(" FROM ");
                        queryBuf.append(StringUtils.quoteIdentifier(catalogStr, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                        queryBuf.append(" LIKE ");
                        queryBuf.append(StringUtils.quoteIdentifier(colPattern, "'", true));
                        boolean fixUpOrdinalsRequired = false;
                        Map<String, Integer> ordinalFixUpMap = null;
                        if (!colPattern.equals("%")) {
                           fixUpOrdinalsRequired = true;
                           StringBuilder fullColumnQueryBuf = new StringBuilder("SHOW FULL COLUMNS FROM ");
                           fullColumnQueryBuf.append(StringUtils.quoteIdentifier(tableName, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                           fullColumnQueryBuf.append(" FROM ");
                           fullColumnQueryBuf.append(StringUtils.quoteIdentifier(catalogStr, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                           results = stmt.executeQuery(fullColumnQueryBuf.toString());
                           ordinalFixUpMap = new HashMap();
                           int var10 = 1;

                           while(results.next()) {
                              String fullOrdColName = results.getString("Field");
                              ordinalFixUpMap.put(fullOrdColName, var10++);
                           }

                           results.close();
                        }

                        results = stmt.executeQuery(queryBuf.toString());

                        byte[][] rowVal;
                        for(int var51 = 1; results.next(); rows.add(new ByteArrayRow(rowVal, DatabaseMetaData.this.getExceptionInterceptor()))) {
                           DatabaseMetaData.TypeDescriptor typeDesc = DatabaseMetaData.this.new TypeDescriptor(results.getString("Type"), results.getString("Null"));
                           rowVal = new byte[24][];
                           rowVal[0] = DatabaseMetaData.this.s2b(catalogStr);
                           rowVal[1] = null;
                           rowVal[2] = DatabaseMetaData.this.s2b(tableName);
                           rowVal[3] = results.getBytes("Field");
                           rowVal[4] = Short.toString((short)typeDesc.mysqlType.getJdbcType()).getBytes();
                           rowVal[5] = DatabaseMetaData.this.s2b(typeDesc.mysqlType.getName());
                           String extra;
                           if (typeDesc.columnSize == null) {
                              rowVal[6] = null;
                           } else {
                              extra = results.getString("Collation");
                              int mbminlen = 1;
                              if (extra != null) {
                                 if (extra.indexOf("ucs2") <= -1 && extra.indexOf("utf16") <= -1) {
                                    if (extra.indexOf("utf32") > -1) {
                                       mbminlen = 4;
                                    }
                                 } else {
                                    mbminlen = 2;
                                 }
                              }

                              rowVal[6] = mbminlen == 1 ? DatabaseMetaData.this.s2b(typeDesc.columnSize.toString()) : DatabaseMetaData.this.s2b(Integer.valueOf(typeDesc.columnSize / mbminlen).toString());
                           }

                           rowVal[7] = DatabaseMetaData.this.s2b(Integer.toString(typeDesc.bufferLength));
                           rowVal[8] = typeDesc.decimalDigits == null ? null : DatabaseMetaData.this.s2b(typeDesc.decimalDigits.toString());
                           rowVal[9] = DatabaseMetaData.this.s2b(Integer.toString(typeDesc.numPrecRadix));
                           rowVal[10] = DatabaseMetaData.this.s2b(Integer.toString(typeDesc.nullability));

                           try {
                              rowVal[11] = results.getBytes("Comment");
                           } catch (Exception var46) {
                              rowVal[11] = new byte[0];
                           }

                           rowVal[12] = results.getBytes("Default");
                           rowVal[13] = new byte[]{48};
                           rowVal[14] = new byte[]{48};
                           if (StringUtils.indexOfIgnoreCase(typeDesc.mysqlType.getName(), "CHAR") == -1 && StringUtils.indexOfIgnoreCase(typeDesc.mysqlType.getName(), "BLOB") == -1 && StringUtils.indexOfIgnoreCase(typeDesc.mysqlType.getName(), "TEXT") == -1 && StringUtils.indexOfIgnoreCase(typeDesc.mysqlType.getName(), "ENUM") == -1 && StringUtils.indexOfIgnoreCase(typeDesc.mysqlType.getName(), "SET") == -1 && StringUtils.indexOfIgnoreCase(typeDesc.mysqlType.getName(), "BINARY") == -1) {
                              rowVal[15] = null;
                           } else {
                              rowVal[15] = rowVal[6];
                           }

                           if (!fixUpOrdinalsRequired) {
                              rowVal[16] = Integer.toString(var51++).getBytes();
                           } else {
                              extra = results.getString("Field");
                              Integer realOrdinal = (Integer)ordinalFixUpMap.get(extra);
                              if (realOrdinal == null) {
                                 throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.10"), "S1000", DatabaseMetaData.this.getExceptionInterceptor());
                              }

                              rowVal[16] = realOrdinal.toString().getBytes();
                           }

                           rowVal[17] = DatabaseMetaData.this.s2b(typeDesc.isNullable);
                           rowVal[18] = null;
                           rowVal[19] = null;
                           rowVal[20] = null;
                           rowVal[21] = null;
                           rowVal[22] = DatabaseMetaData.this.s2b("");
                           extra = results.getString("Extra");
                           if (extra != null) {
                              rowVal[22] = DatabaseMetaData.this.s2b(StringUtils.indexOfIgnoreCase(extra, "auto_increment") != -1 ? "YES" : "NO");
                              rowVal[23] = DatabaseMetaData.this.s2b(StringUtils.indexOfIgnoreCase(extra, "generated") != -1 ? "YES" : "NO");
                           }
                        }
                     } finally {
                        if (results != null) {
                           try {
                              results.close();
                           } catch (Exception var44) {
                           }

                           results = null;
                        }

                     }
                  }

               }
            }).doForAll();
         } finally {
            if (stmt != null) {
               stmt.close();
            }

         }

         ResultSet results = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new MysqlaColumnDefinition(fields)));
         return results;
      } catch (CJException var14) {
         throw SQLExceptionsMapping.translateException(var14, this.getExceptionInterceptor());
      }
   }

   protected Field[] createColumnsFields() {
      Field[] fields = new Field[]{new Field("", "TABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "TABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "COLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 5), new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 16), new Field("", "COLUMN_SIZE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, Integer.toString(Integer.MAX_VALUE).length()), new Field("", "BUFFER_LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "DECIMAL_DIGITS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "NUM_PREC_RADIX", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "NULLABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "REMARKS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "COLUMN_DEF", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "SQL_DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "SQL_DATETIME_SUB", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "CHAR_OCTET_LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, Integer.toString(Integer.MAX_VALUE).length()), new Field("", "ORDINAL_POSITION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "IS_NULLABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 3), new Field("", "SCOPE_CATALOG", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "SCOPE_SCHEMA", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "SCOPE_TABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "SOURCE_DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 10), new Field("", "IS_AUTOINCREMENT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 3), new Field("", "IS_GENERATEDCOLUMN", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 3)};
      return fields;
   }

   public Connection getConnection() throws SQLException {
      try {
         return this.conn;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public ResultSet getCrossReference(final String primaryCatalog, final String primarySchema, final String primaryTable, final String foreignCatalog, final String foreignSchema, final String foreignTable) throws SQLException {
      try {
         if (primaryTable == null) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.2"), "S1009", this.getExceptionInterceptor());
         } else {
            Field[] fields = this.createFkMetadataFields();
            final ArrayList<Row> tuples = new ArrayList();
            Statement stmt = this.conn.getMetadataSafeStatement();

            try {
               (new IterateBlock<String>(this.getCatalogIterator(foreignCatalog)) {
                  void forEach(String catalogStr) throws SQLException {
                     ResultSet fkresults = null;

                     try {
                        fkresults = DatabaseMetaData.this.extractForeignKeyFromCreateTable(catalogStr, (String)null);
                        String foreignTableWithCase = DatabaseMetaData.this.getTableNameWithCase(foreignTable);
                        String primaryTableWithCase = DatabaseMetaData.this.getTableNameWithCase(primaryTable);

                        while(true) {
                           String comment;
                           do {
                              String tableType;
                              do {
                                 do {
                                    if (!fkresults.next()) {
                                       return;
                                    }

                                    tableType = fkresults.getString("Type");
                                 } while(tableType == null);
                              } while(!tableType.equalsIgnoreCase("innodb") && !tableType.equalsIgnoreCase("SUPPORTS_FK"));

                              comment = fkresults.getString("Comment").trim();
                           } while(comment == null);

                           StringTokenizer commentTokens = new StringTokenizer(comment, ";", false);
                           String dummy;
                           if (commentTokens.hasMoreTokens()) {
                              dummy = commentTokens.nextToken();
                           }

                           while(commentTokens.hasMoreTokens()) {
                              String keys = commentTokens.nextToken();
                              DatabaseMetaData.LocalAndReferencedColumns parsedInfo = DatabaseMetaData.this.parseTableStatusIntoLocalAndReferencedColumns(keys);
                              int keySeq = 0;
                              Iterator<String> referencingColumns = parsedInfo.localColumnsList.iterator();
                              Iterator referencedColumns = parsedInfo.referencedColumnsList.iterator();

                              while(referencingColumns.hasNext()) {
                                 String referencingColumn = StringUtils.unQuoteIdentifier((String)referencingColumns.next(), DatabaseMetaData.this.quotedId);
                                 byte[][] tuple = new byte[14][];
                                 tuple[4] = foreignCatalog == null ? null : DatabaseMetaData.this.s2b(foreignCatalog);
                                 tuple[5] = foreignSchema == null ? null : DatabaseMetaData.this.s2b(foreignSchema);
                                 dummy = fkresults.getString("Name");
                                 if (dummy.compareTo(foreignTableWithCase) == 0) {
                                    tuple[6] = DatabaseMetaData.this.s2b(dummy);
                                    tuple[7] = DatabaseMetaData.this.s2b(referencingColumn);
                                    tuple[0] = primaryCatalog == null ? null : DatabaseMetaData.this.s2b(primaryCatalog);
                                    tuple[1] = primarySchema == null ? null : DatabaseMetaData.this.s2b(primarySchema);
                                    if (parsedInfo.referencedTable.compareTo(primaryTableWithCase) == 0) {
                                       tuple[2] = DatabaseMetaData.this.s2b(parsedInfo.referencedTable);
                                       tuple[3] = DatabaseMetaData.this.s2b(StringUtils.unQuoteIdentifier((String)referencedColumns.next(), DatabaseMetaData.this.quotedId));
                                       tuple[8] = Integer.toString(keySeq).getBytes();
                                       int[] actions = DatabaseMetaData.this.getForeignKeyActions(keys);
                                       tuple[9] = Integer.toString(actions[1]).getBytes();
                                       tuple[10] = Integer.toString(actions[0]).getBytes();
                                       tuple[11] = null;
                                       tuple[12] = null;
                                       tuple[13] = Integer.toString(7).getBytes();
                                       tuples.add(new ByteArrayRow(tuple, DatabaseMetaData.this.getExceptionInterceptor()));
                                       ++keySeq;
                                    }
                                 }
                              }
                           }
                        }
                     } finally {
                        if (fkresults != null) {
                           try {
                              fkresults.close();
                           } catch (Exception var22) {
                              AssertionFailedException.shouldNotHappen(var22);
                           }

                           fkresults = null;
                        }

                     }
                  }
               }).doForAll();
            } finally {
               if (stmt != null) {
                  stmt.close();
               }

            }

            ResultSet results = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(tuples, new MysqlaColumnDefinition(fields)));
            return results;
         }
      } catch (CJException var15) {
         throw SQLExceptionsMapping.translateException(var15, this.getExceptionInterceptor());
      }
   }

   protected Field[] createFkMetadataFields() {
      Field[] fields = new Field[]{new Field("", "PKTABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "PKTABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "PKTABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "PKCOLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "FKTABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "FKTABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "FKTABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "FKCOLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "KEY_SEQ", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 2), new Field("", "UPDATE_RULE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 2), new Field("", "DELETE_RULE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 2), new Field("", "FK_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "PK_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "DEFERRABILITY", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 2)};
      return fields;
   }

   public int getDatabaseMajorVersion() throws SQLException {
      try {
         return this.conn.getServerVersion().getMajor();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getDatabaseMinorVersion() throws SQLException {
      try {
         return this.conn.getServerVersion().getMinor();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public String getDatabaseProductName() throws SQLException {
      try {
         return "MySQL";
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public String getDatabaseProductVersion() throws SQLException {
      try {
         return this.conn.getServerVersion().toString();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getDefaultTransactionIsolation() throws SQLException {
      try {
         return 2;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getDriverMajorVersion() {
      return NonRegisteringDriver.getMajorVersionInternal();
   }

   public int getDriverMinorVersion() {
      return NonRegisteringDriver.getMinorVersionInternal();
   }

   public String getDriverName() throws SQLException {
      try {
         return "MySQL Connector Java";
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public String getDriverVersion() throws SQLException {
      try {
         return "mysql-connector-java-6.0.5 ( Revision: 6e477e32dff18fb4dc102e53f5a68037a9f8762e )";
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public ResultSet getExportedKeys(String catalog, String schema, final String table) throws SQLException {
      try {
         if (table == null) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.2"), "S1009", this.getExceptionInterceptor());
         } else {
            Field[] fields = this.createFkMetadataFields();
            final ArrayList<Row> rows = new ArrayList();
            Statement stmt = this.conn.getMetadataSafeStatement();

            try {
               (new IterateBlock<String>(this.getCatalogIterator(catalog)) {
                  void forEach(String catalogStr) throws SQLException {
                     ResultSet fkresults = null;

                     try {
                        fkresults = DatabaseMetaData.this.extractForeignKeyFromCreateTable(catalogStr, (String)null);
                        String tableNameWithCase = DatabaseMetaData.this.getTableNameWithCase(table);

                        while(fkresults.next()) {
                           String tableType = fkresults.getString("Type");
                           if (tableType != null && (tableType.equalsIgnoreCase("innodb") || tableType.equalsIgnoreCase("SUPPORTS_FK"))) {
                              String comment = fkresults.getString("Comment").trim();
                              if (comment != null) {
                                 StringTokenizer commentTokens = new StringTokenizer(comment, ";", false);
                                 if (commentTokens.hasMoreTokens()) {
                                    commentTokens.nextToken();

                                    while(commentTokens.hasMoreTokens()) {
                                       String keys = commentTokens.nextToken();
                                       DatabaseMetaData.this.getExportKeyResults(catalogStr, tableNameWithCase, keys, rows, fkresults.getString("Name"));
                                    }
                                 }
                              }
                           }
                        }
                     } finally {
                        if (fkresults != null) {
                           try {
                              fkresults.close();
                           } catch (SQLException var13) {
                              AssertionFailedException.shouldNotHappen((Exception)var13);
                           }

                           fkresults = null;
                        }

                     }

                  }
               }).doForAll();
            } finally {
               if (stmt != null) {
                  stmt.close();
               }

            }

            ResultSet results = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new MysqlaColumnDefinition(fields)));
            return results;
         }
      } catch (CJException var12) {
         throw SQLExceptionsMapping.translateException(var12, this.getExceptionInterceptor());
      }
   }

   protected void getExportKeyResults(String catalog, String exportingTable, String keysComment, List<Row> tuples, String fkTableName) throws SQLException {
      this.getResultsImpl(catalog, exportingTable, keysComment, tuples, fkTableName, true);
   }

   public String getExtraNameCharacters() throws SQLException {
      try {
         return "#@";
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   protected int[] getForeignKeyActions(String commentString) {
      int[] actions = new int[]{3, 3};
      int lastParenIndex = commentString.lastIndexOf(")");
      if (lastParenIndex != commentString.length() - 1) {
         String cascadeOptions = commentString.substring(lastParenIndex + 1).trim().toUpperCase(Locale.ENGLISH);
         actions[0] = this.getCascadeDeleteOption(cascadeOptions);
         actions[1] = this.getCascadeUpdateOption(cascadeOptions);
      }

      return actions;
   }

   public String getIdentifierQuoteString() throws SQLException {
      try {
         return this.conn.useAnsiQuotedIdentifiers() ? "\"" : "`";
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public ResultSet getImportedKeys(String catalog, String schema, final String table) throws SQLException {
      try {
         if (table == null) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.2"), "S1009", this.getExceptionInterceptor());
         } else {
            Field[] fields = this.createFkMetadataFields();
            final ArrayList<Row> rows = new ArrayList();
            Statement stmt = this.conn.getMetadataSafeStatement();

            try {
               (new IterateBlock<String>(this.getCatalogIterator(catalog)) {
                  void forEach(String catalogStr) throws SQLException {
                     ResultSet fkresults = null;

                     try {
                        fkresults = DatabaseMetaData.this.extractForeignKeyFromCreateTable(catalogStr, table);

                        while(fkresults.next()) {
                           String tableType = fkresults.getString("Type");
                           if (tableType != null && (tableType.equalsIgnoreCase("innodb") || tableType.equalsIgnoreCase("SUPPORTS_FK"))) {
                              String comment = fkresults.getString("Comment").trim();
                              if (comment != null) {
                                 StringTokenizer commentTokens = new StringTokenizer(comment, ";", false);
                                 if (commentTokens.hasMoreTokens()) {
                                    commentTokens.nextToken();

                                    while(commentTokens.hasMoreTokens()) {
                                       String keys = commentTokens.nextToken();
                                       DatabaseMetaData.this.getImportKeyResults(catalogStr, table, keys, rows);
                                    }
                                 }
                              }
                           }
                        }
                     } finally {
                        if (fkresults != null) {
                           try {
                              fkresults.close();
                           } catch (SQLException var12) {
                              AssertionFailedException.shouldNotHappen((Exception)var12);
                           }

                           fkresults = null;
                        }

                     }

                  }
               }).doForAll();
            } finally {
               if (stmt != null) {
                  stmt.close();
               }

            }

            ResultSet results = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new MysqlaColumnDefinition(fields)));
            return results;
         }
      } catch (CJException var12) {
         throw SQLExceptionsMapping.translateException(var12, this.getExceptionInterceptor());
      }
   }

   protected void getImportKeyResults(String catalog, String importingTable, String keysComment, List<Row> tuples) throws SQLException {
      this.getResultsImpl(catalog, importingTable, keysComment, tuples, (String)null, false);
   }

   public ResultSet getIndexInfo(String catalog, String schema, final String table, final boolean unique, boolean approximate) throws SQLException {
      try {
         Field[] fields = this.createIndexInfoFields();
         final SortedMap<DatabaseMetaData.IndexMetaDataKey, Row> sortedRows = new TreeMap();
         ArrayList<Row> rows = new ArrayList();
         final Statement stmt = this.conn.getMetadataSafeStatement();

         try {
            (new IterateBlock<String>(this.getCatalogIterator(catalog)) {
               void forEach(String catalogStr) throws SQLException {
                  ResultSet results = null;

                  try {
                     StringBuilder queryBuf = new StringBuilder("SHOW INDEX FROM ");
                     queryBuf.append(StringUtils.quoteIdentifier(table, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                     queryBuf.append(" FROM ");
                     queryBuf.append(StringUtils.quoteIdentifier(catalogStr, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));

                     try {
                        results = stmt.executeQuery(queryBuf.toString());
                     } catch (SQLException var17) {
                        int errorCode = var17.getErrorCode();
                        if (!"42S02".equals(var17.getSQLState()) && errorCode != 1146) {
                           throw var17;
                        }
                     }

                     while(results != null && results.next()) {
                        byte[][] row = new byte[14][];
                        row[0] = catalogStr == null ? new byte[0] : DatabaseMetaData.this.s2b(catalogStr);
                        row[1] = null;
                        row[2] = results.getBytes("Table");
                        boolean indexIsUnique = results.getInt("Non_unique") == 0;
                        row[3] = !indexIsUnique ? DatabaseMetaData.this.s2b("true") : DatabaseMetaData.this.s2b("false");
                        row[4] = new byte[0];
                        row[5] = results.getBytes("Key_name");
                        short indexType = 3;
                        row[6] = Integer.toString(indexType).getBytes();
                        row[7] = results.getBytes("Seq_in_index");
                        row[8] = results.getBytes("Column_name");
                        row[9] = results.getBytes("Collation");
                        long cardinality = results.getLong("Cardinality");
                        row[10] = DatabaseMetaData.this.s2b(String.valueOf(cardinality));
                        row[11] = DatabaseMetaData.this.s2b("0");
                        row[12] = null;
                        DatabaseMetaData.IndexMetaDataKey indexInfoKey = DatabaseMetaData.this.new IndexMetaDataKey(!indexIsUnique, indexType, results.getString("Key_name").toLowerCase(), results.getShort("Seq_in_index"));
                        if (unique) {
                           if (indexIsUnique) {
                              sortedRows.put(indexInfoKey, new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                           }
                        } else {
                           sortedRows.put(indexInfoKey, new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                        }
                     }
                  } finally {
                     if (results != null) {
                        try {
                           results.close();
                        } catch (Exception var16) {
                        }

                        results = null;
                     }

                  }

               }
            }).doForAll();
            Iterator sortedRowsIterator = sortedRows.values().iterator();

            while(sortedRowsIterator.hasNext()) {
               rows.add(sortedRowsIterator.next());
            }

            ResultSet indexInfo = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new MysqlaColumnDefinition(fields)));
            ResultSetImpl var12 = indexInfo;
            return var12;
         } finally {
            if (stmt != null) {
               stmt.close();
            }

         }
      } catch (CJException var18) {
         throw SQLExceptionsMapping.translateException(var18, this.getExceptionInterceptor());
      }
   }

   protected Field[] createIndexInfoFields() {
      Field[] fields = new Field[]{new Field("", "TABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "TABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "NON_UNIQUE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.BOOLEAN, 4), new Field("", "INDEX_QUALIFIER", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 1), new Field("", "INDEX_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 32), new Field("", "ORDINAL_POSITION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 5), new Field("", "COLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "ASC_OR_DESC", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 1), new Field("", "CARDINALITY", this.metadataCollationIndex, this.metadataEncoding, MysqlType.BIGINT, 20), new Field("", "PAGES", this.metadataCollationIndex, this.metadataEncoding, MysqlType.BIGINT, 20), new Field("", "FILTER_CONDITION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32)};
      return fields;
   }

   public int getJDBCMajorVersion() throws SQLException {
      try {
         return 4;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getJDBCMinorVersion() throws SQLException {
      try {
         return 1;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxBinaryLiteralLength() throws SQLException {
      try {
         return 16777208;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxCatalogNameLength() throws SQLException {
      try {
         return 32;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxCharLiteralLength() throws SQLException {
      try {
         return 16777208;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxColumnNameLength() throws SQLException {
      try {
         return 64;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxColumnsInGroupBy() throws SQLException {
      try {
         return 64;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxColumnsInIndex() throws SQLException {
      try {
         return 16;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxColumnsInOrderBy() throws SQLException {
      try {
         return 64;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxColumnsInSelect() throws SQLException {
      try {
         return 256;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxColumnsInTable() throws SQLException {
      try {
         return 512;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxConnections() throws SQLException {
      try {
         return 0;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxCursorNameLength() throws SQLException {
      try {
         return 64;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxIndexLength() throws SQLException {
      try {
         return 256;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxProcedureNameLength() throws SQLException {
      try {
         return 0;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxRowSize() throws SQLException {
      try {
         return 2147483639;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxSchemaNameLength() throws SQLException {
      try {
         return 0;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxStatementLength() throws SQLException {
      try {
         return maxBufferSize - 4;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxStatements() throws SQLException {
      try {
         return 0;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxTableNameLength() throws SQLException {
      try {
         return 64;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxTablesInSelect() throws SQLException {
      try {
         return 256;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getMaxUserNameLength() throws SQLException {
      try {
         return 16;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public String getNumericFunctions() throws SQLException {
      try {
         return "ABS,ACOS,ASIN,ATAN,ATAN2,BIT_COUNT,CEILING,COS,COT,DEGREES,EXP,FLOOR,LOG,LOG10,MAX,MIN,MOD,PI,POW,POWER,RADIANS,RAND,ROUND,SIN,SQRT,TAN,TRUNCATE";
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public ResultSet getPrimaryKeys(String catalog, String schema, final String table) throws SQLException {
      try {
         Field[] fields = new Field[]{new Field("", "TABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "TABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "COLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "KEY_SEQ", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 5), new Field("", "PK_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32)};
         if (table == null) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.2"), "S1009", this.getExceptionInterceptor());
         } else {
            final ArrayList<Row> rows = new ArrayList();
            final Statement stmt = this.conn.getMetadataSafeStatement();

            try {
               (new IterateBlock<String>(this.getCatalogIterator(catalog)) {
                  void forEach(String catalogStr) throws SQLException {
                     ResultSet rs = null;

                     try {
                        StringBuilder queryBuf = new StringBuilder("SHOW KEYS FROM ");
                        queryBuf.append(StringUtils.quoteIdentifier(table, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                        queryBuf.append(" FROM ");
                        queryBuf.append(StringUtils.quoteIdentifier(catalogStr, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                        rs = stmt.executeQuery(queryBuf.toString());
                        TreeMap sortMap = new TreeMap();

                        label119:
                        while(true) {
                           String keyType;
                           do {
                              do {
                                 if (!rs.next()) {
                                    Iterator sortedIterator = sortMap.values().iterator();

                                    while(sortedIterator.hasNext()) {
                                       rows.add(new ByteArrayRow((byte[][])sortedIterator.next(), DatabaseMetaData.this.getExceptionInterceptor()));
                                    }
                                    break label119;
                                 }

                                 keyType = rs.getString("Key_name");
                              } while(keyType == null);
                           } while(!keyType.equalsIgnoreCase("PRIMARY") && !keyType.equalsIgnoreCase("PRI"));

                           byte[][] tuple = new byte[][]{catalogStr == null ? new byte[0] : DatabaseMetaData.this.s2b(catalogStr), null, DatabaseMetaData.this.s2b(table), null, null, null};
                           String columnName = rs.getString("Column_name");
                           tuple[3] = DatabaseMetaData.this.s2b(columnName);
                           tuple[4] = DatabaseMetaData.this.s2b(rs.getString("Seq_in_index"));
                           tuple[5] = DatabaseMetaData.this.s2b(keyType);
                           sortMap.put(columnName, tuple);
                        }
                     } finally {
                        if (rs != null) {
                           try {
                              rs.close();
                           } catch (Exception var13) {
                           }

                           rs = null;
                        }

                     }

                  }
               }).doForAll();
            } finally {
               if (stmt != null) {
                  stmt.close();
               }

            }

            ResultSet results = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new MysqlaColumnDefinition(fields)));
            return results;
         }
      } catch (CJException var12) {
         throw SQLExceptionsMapping.translateException(var12, this.getExceptionInterceptor());
      }
   }

   public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) throws SQLException {
      try {
         Field[] fields = this.createProcedureColumnsFields();
         return this.getProcedureOrFunctionColumns(fields, catalog, schemaPattern, procedureNamePattern, columnNamePattern, true, (Boolean)this.conn.getPropertySet().getBooleanReadableProperty("getProceduresReturnsFunctions").getValue());
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   protected Field[] createProcedureColumnsFields() {
      Field[] fields = new Field[]{new Field("", "PROCEDURE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 512), new Field("", "PROCEDURE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 512), new Field("", "PROCEDURE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 512), new Field("", "COLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 512), new Field("", "COLUMN_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 64), new Field("", "DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 6), new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 64), new Field("", "PRECISION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "SCALE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 12), new Field("", "RADIX", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 6), new Field("", "NULLABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 6), new Field("", "REMARKS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 512), new Field("", "COLUMN_DEF", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 512), new Field("", "SQL_DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "SQL_DATETIME_SUB", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "CHAR_OCTET_LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "ORDINAL_POSITION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "IS_NULLABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 512), new Field("", "SPECIFIC_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 512)};
      return fields;
   }

   protected ResultSet getProcedureOrFunctionColumns(Field[] fields, String catalog, String schemaPattern, String procedureOrFunctionNamePattern, String columnNamePattern, boolean returnProcedures, boolean returnFunctions) throws SQLException {
      List<DatabaseMetaData.ComparableWrapper<String, DatabaseMetaData.ProcedureType>> procsOrFuncsToExtractList = new ArrayList();
      ResultSet procsAndOrFuncsRs = null;
      boolean var20 = false;

      boolean idx;
      try {
         var20 = true;
         String tmpProcedureOrFunctionNamePattern = null;
         if (procedureOrFunctionNamePattern != null && !procedureOrFunctionNamePattern.equals("%")) {
            tmpProcedureOrFunctionNamePattern = StringUtils.sanitizeProcOrFuncName(procedureOrFunctionNamePattern);
         }

         if (tmpProcedureOrFunctionNamePattern == null) {
            tmpProcedureOrFunctionNamePattern = procedureOrFunctionNamePattern;
         } else {
            List<String> parseList = StringUtils.splitDBdotName(tmpProcedureOrFunctionNamePattern, catalog, this.quotedId, this.conn.isNoBackslashEscapesSet());
            if (parseList.size() == 2) {
               String tmpCatalog = (String)parseList.get(0);
               tmpProcedureOrFunctionNamePattern = (String)parseList.get(1);
            }
         }

         procsAndOrFuncsRs = this.getProceduresAndOrFunctions(this.createFieldMetadataForGetProcedures(), catalog, schemaPattern, tmpProcedureOrFunctionNamePattern, returnProcedures, returnFunctions);

         for(idx = false; procsAndOrFuncsRs.next(); idx = true) {
            procsOrFuncsToExtractList.add(new DatabaseMetaData.ComparableWrapper(this.getFullyQualifiedName(procsAndOrFuncsRs.getString(1), procsAndOrFuncsRs.getString(3)), procsAndOrFuncsRs.getShort(8) == 1 ? DatabaseMetaData.ProcedureType.PROCEDURE : DatabaseMetaData.ProcedureType.FUNCTION));
         }

         if (!idx) {
            var20 = false;
         } else {
            Collections.sort(procsOrFuncsToExtractList);
            var20 = false;
         }
      } finally {
         if (var20) {
            SQLException rethrowSqlEx = null;
            if (procsAndOrFuncsRs != null) {
               try {
                  procsAndOrFuncsRs.close();
               } catch (SQLException var21) {
                  rethrowSqlEx = var21;
               }
            }

            if (rethrowSqlEx != null) {
               throw rethrowSqlEx;
            }

         }
      }

      SQLException rethrowSqlEx = null;
      if (procsAndOrFuncsRs != null) {
         try {
            procsAndOrFuncsRs.close();
         } catch (SQLException var22) {
            rethrowSqlEx = var22;
         }
      }

      if (rethrowSqlEx != null) {
         throw rethrowSqlEx;
      } else {
         ArrayList<Row> resultRows = new ArrayList();
         idx = false;
         String procNameToCall = "";

         DatabaseMetaData.ProcedureType procType;
         for(Iterator var13 = procsOrFuncsToExtractList.iterator(); var13.hasNext(); this.getCallStmtParameterTypes(catalog, procNameToCall, procType, columnNamePattern, resultRows, fields.length == 17)) {
            DatabaseMetaData.ComparableWrapper<String, DatabaseMetaData.ProcedureType> procOrFunc = (DatabaseMetaData.ComparableWrapper)var13.next();
            String procName = (String)procOrFunc.getKey();
            procType = (DatabaseMetaData.ProcedureType)procOrFunc.getValue();
            int idx;
            if (!" ".equals(this.quotedId)) {
               idx = StringUtils.indexOfIgnoreCase(0, procName, (String)".", this.quotedId, this.quotedId, this.conn.isNoBackslashEscapesSet() ? StringUtils.SEARCH_MODE__MRK_COM_WS : StringUtils.SEARCH_MODE__ALL);
            } else {
               idx = procName.indexOf(".");
            }

            if (idx > 0) {
               catalog = StringUtils.unQuoteIdentifier(procName.substring(0, idx), this.quotedId);
               procNameToCall = procName;
            } else {
               procNameToCall = procName;
            }
         }

         return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(resultRows, new MysqlaColumnDefinition(fields)));
      }
   }

   public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
      try {
         Field[] fields = this.createFieldMetadataForGetProcedures();
         return this.getProceduresAndOrFunctions(fields, catalog, schemaPattern, procedureNamePattern, true, (Boolean)this.conn.getPropertySet().getBooleanReadableProperty("getProceduresReturnsFunctions").getValue());
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   protected Field[] createFieldMetadataForGetProcedures() {
      Field[] fields = new Field[]{new Field("", "PROCEDURE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "PROCEDURE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "PROCEDURE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "reserved1", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "reserved2", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "reserved3", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "REMARKS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "PROCEDURE_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 6), new Field("", "SPECIFIC_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255)};
      return fields;
   }

   protected ResultSet getProceduresAndOrFunctions(final Field[] fields, String catalog, String schemaPattern, final String procedureNamePattern, final boolean returnProcedures, final boolean returnFunctions) throws SQLException {
      if (procedureNamePattern == null || procedureNamePattern.length() == 0) {
         if (!this.nullNamePatternMatchesAll) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.11"), "S1009", this.getExceptionInterceptor());
         }

         procedureNamePattern = "%";
      }

      ArrayList<Row> procedureRows = new ArrayList();
      final List<DatabaseMetaData.ComparableWrapper<String, Row>> procedureRowsToSort = new ArrayList();
      (new IterateBlock<String>(this.getCatalogIterator(catalog)) {
         void forEach(String catalogStr) throws SQLException {
            String db = catalogStr;
            ResultSet proceduresRs = null;
            boolean needsClientFiltering = true;
            StringBuilder selectFromMySQLProcSQL = new StringBuilder();
            selectFromMySQLProcSQL.append("SELECT name, type, comment FROM mysql.proc WHERE ");
            if (returnProcedures && !returnFunctions) {
               selectFromMySQLProcSQL.append("type = 'PROCEDURE' AND ");
            } else if (!returnProcedures && returnFunctions) {
               selectFromMySQLProcSQL.append("type = 'FUNCTION' AND ");
            }

            selectFromMySQLProcSQL.append("name LIKE ? AND db <=> ? ORDER BY name, type");
            java.sql.PreparedStatement proceduresStmt = DatabaseMetaData.this.prepareMetaDataSafeStatement(selectFromMySQLProcSQL.toString());
            boolean var18 = false;

            try {
               var18 = true;
               if (db != null) {
                  if (DatabaseMetaData.this.conn.lowerCaseTableNames()) {
                     db = db.toLowerCase();
                  }

                  proceduresStmt.setString(2, db);
               } else {
                  proceduresStmt.setNull(2, MysqlType.VARCHAR.getJdbcType());
               }

               int nameIndex = 1;
               proceduresStmt.setString(1, procedureNamePattern);

               try {
                  proceduresRs = proceduresStmt.executeQuery();
                  needsClientFiltering = false;
                  if (returnProcedures) {
                     DatabaseMetaData.this.convertToJdbcProcedureList(true, db, proceduresRs, needsClientFiltering, db, procedureRowsToSort, nameIndex);
                  }

                  if (returnFunctions) {
                     DatabaseMetaData.this.convertToJdbcFunctionList(db, proceduresRs, needsClientFiltering, db, procedureRowsToSort, nameIndex, fields);
                     var18 = false;
                  } else {
                     var18 = false;
                  }
               } catch (SQLException var23) {
                  nameIndex = 2;
                  if (returnFunctions) {
                     proceduresStmt.close();
                     proceduresStmt = DatabaseMetaData.this.prepareMetaDataSafeStatement("SHOW FUNCTION STATUS LIKE ?");
                     proceduresStmt.setString(1, procedureNamePattern);
                     proceduresRs = proceduresStmt.executeQuery();
                     DatabaseMetaData.this.convertToJdbcFunctionList(db, proceduresRs, needsClientFiltering, db, procedureRowsToSort, nameIndex, fields);
                  }

                  if (returnProcedures) {
                     proceduresStmt.close();
                     proceduresStmt = DatabaseMetaData.this.prepareMetaDataSafeStatement("SHOW PROCEDURE STATUS LIKE ?");
                     proceduresStmt.setString(1, procedureNamePattern);
                     proceduresRs = proceduresStmt.executeQuery();
                     DatabaseMetaData.this.convertToJdbcProcedureList(false, db, proceduresRs, needsClientFiltering, db, procedureRowsToSort, nameIndex);
                     var18 = false;
                  } else {
                     var18 = false;
                  }
               }
            } finally {
               if (var18) {
                  SQLException rethrowSqlExx = null;
                  if (proceduresRs != null) {
                     try {
                        proceduresRs.close();
                     } catch (SQLException var20) {
                        rethrowSqlExx = var20;
                     }
                  }

                  if (proceduresStmt != null) {
                     try {
                        proceduresStmt.close();
                     } catch (SQLException var19) {
                        rethrowSqlExx = var19;
                     }
                  }

                  if (rethrowSqlExx != null) {
                     throw rethrowSqlExx;
                  }

               }
            }

            SQLException rethrowSqlEx = null;
            if (proceduresRs != null) {
               try {
                  proceduresRs.close();
               } catch (SQLException var22) {
                  rethrowSqlEx = var22;
               }
            }

            if (proceduresStmt != null) {
               try {
                  proceduresStmt.close();
               } catch (SQLException var21) {
                  rethrowSqlEx = var21;
               }
            }

            if (rethrowSqlEx != null) {
               throw rethrowSqlEx;
            }
         }
      }).doForAll();
      Collections.sort(procedureRowsToSort);
      Iterator var10 = procedureRowsToSort.iterator();

      while(var10.hasNext()) {
         DatabaseMetaData.ComparableWrapper<String, Row> procRow = (DatabaseMetaData.ComparableWrapper)var10.next();
         procedureRows.add(procRow.getValue());
      }

      return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(procedureRows, new MysqlaColumnDefinition(fields)));
   }

   public String getProcedureTerm() throws SQLException {
      try {
         return "PROCEDURE";
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public int getResultSetHoldability() throws SQLException {
      try {
         return 1;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   private void getResultsImpl(String catalog, String table, String keysComment, List<Row> tuples, String fkTableName, boolean isExport) throws SQLException {
      DatabaseMetaData.LocalAndReferencedColumns parsedInfo = this.parseTableStatusIntoLocalAndReferencedColumns(keysComment);
      if (!isExport || parsedInfo.referencedTable.equals(table)) {
         if (parsedInfo.localColumnsList.size() != parsedInfo.referencedColumnsList.size()) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.12"), "S1000", this.getExceptionInterceptor());
         } else {
            Iterator<String> localColumnNames = parsedInfo.localColumnsList.iterator();
            Iterator<String> referColumnNames = parsedInfo.referencedColumnsList.iterator();
            int var10 = 1;

            while(localColumnNames.hasNext()) {
               byte[][] tuple = new byte[14][];
               String lColumnName = StringUtils.unQuoteIdentifier((String)localColumnNames.next(), this.quotedId);
               String rColumnName = StringUtils.unQuoteIdentifier((String)referColumnNames.next(), this.quotedId);
               tuple[4] = catalog == null ? new byte[0] : this.s2b(catalog);
               tuple[5] = null;
               tuple[6] = this.s2b(isExport ? fkTableName : table);
               tuple[7] = this.s2b(lColumnName);
               tuple[0] = this.s2b(parsedInfo.referencedCatalog);
               tuple[1] = null;
               tuple[2] = this.s2b(isExport ? table : parsedInfo.referencedTable);
               tuple[3] = this.s2b(rColumnName);
               tuple[8] = this.s2b(Integer.toString(var10++));
               int[] actions = this.getForeignKeyActions(keysComment);
               tuple[9] = this.s2b(Integer.toString(actions[1]));
               tuple[10] = this.s2b(Integer.toString(actions[0]));
               tuple[11] = this.s2b(parsedInfo.constraintName);
               tuple[12] = null;
               tuple[13] = this.s2b(Integer.toString(7));
               tuples.add(new ByteArrayRow(tuple, this.getExceptionInterceptor()));
            }

         }
      }
   }

   public ResultSet getSchemas() throws SQLException {
      try {
         Field[] fields = new Field[]{new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "TABLE_CATALOG", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0)};
         ArrayList<Row> tuples = new ArrayList();
         ResultSet results = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(tuples, new MysqlaColumnDefinition(fields)));
         return results;
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public String getSchemaTerm() throws SQLException {
      try {
         return "";
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public String getSearchStringEscape() throws SQLException {
      try {
         return "\\";
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public String getSQLKeywords() throws SQLException {
      try {
         if (mysqlKeywords != null) {
            return mysqlKeywords;
         } else {
            Class var1 = DatabaseMetaData.class;
            synchronized(DatabaseMetaData.class) {
               if (mysqlKeywords != null) {
                  return mysqlKeywords;
               } else {
                  Set<String> mysqlKeywordSet = new TreeSet();
                  StringBuilder mysqlKeywordsBuffer = new StringBuilder();
                  Collections.addAll(mysqlKeywordSet, MYSQL_KEYWORDS);
                  mysqlKeywordSet.removeAll(Arrays.asList(SQL2003_KEYWORDS));
                  Iterator var4 = mysqlKeywordSet.iterator();

                  while(var4.hasNext()) {
                     String keyword = (String)var4.next();
                     mysqlKeywordsBuffer.append(",").append(keyword);
                  }

                  mysqlKeywords = mysqlKeywordsBuffer.substring(1);
                  return mysqlKeywords;
               }
            }
         }
      } catch (CJException var9) {
         throw SQLExceptionsMapping.translateException(var9, this.getExceptionInterceptor());
      }
   }

   public int getSQLStateType() throws SQLException {
      try {
         return 2;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public String getStringFunctions() throws SQLException {
      try {
         return "ASCII,BIN,BIT_LENGTH,CHAR,CHARACTER_LENGTH,CHAR_LENGTH,CONCAT,CONCAT_WS,CONV,ELT,EXPORT_SET,FIELD,FIND_IN_SET,HEX,INSERT,INSTR,LCASE,LEFT,LENGTH,LOAD_FILE,LOCATE,LOCATE,LOWER,LPAD,LTRIM,MAKE_SET,MATCH,MID,OCT,OCTET_LENGTH,ORD,POSITION,QUOTE,REPEAT,REPLACE,REVERSE,RIGHT,RPAD,RTRIM,SOUNDEX,SPACE,STRCMP,SUBSTRING,SUBSTRING,SUBSTRING,SUBSTRING,SUBSTRING_INDEX,TRIM,UCASE,UPPER";
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public ResultSet getSuperTables(String arg0, String arg1, String arg2) throws SQLException {
      try {
         Field[] fields = new Field[]{new Field("", "TABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "TABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "SUPERTABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32)};
         return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(new ArrayList(), new MysqlaColumnDefinition(fields)));
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public ResultSet getSuperTypes(String arg0, String arg1, String arg2) throws SQLException {
      try {
         Field[] fields = new Field[]{new Field("", "TYPE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "TYPE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "SUPERTYPE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "SUPERTYPE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "SUPERTYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32)};
         return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(new ArrayList(), new MysqlaColumnDefinition(fields)));
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public String getSystemFunctions() throws SQLException {
      try {
         return "DATABASE,USER,SYSTEM_USER,SESSION_USER,PASSWORD,ENCRYPT,LAST_INSERT_ID,VERSION";
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   protected String getTableNameWithCase(String table) {
      String tableNameWithCase = this.conn.lowerCaseTableNames() ? table.toLowerCase() : table;
      return tableNameWithCase;
   }

   public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
      try {
         if (tableNamePattern == null) {
            if (!this.nullNamePatternMatchesAll) {
               throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.13"), "S1009", this.getExceptionInterceptor());
            }

            tableNamePattern = "%";
         }

         Field[] fields = new Field[]{new Field("", "TABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 64), new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 1), new Field("", "TABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 64), new Field("", "GRANTOR", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 77), new Field("", "GRANTEE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 77), new Field("", "PRIVILEGE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 64), new Field("", "IS_GRANTABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 3)};
         String grantQuery = "SELECT host,db,table_name,grantor,user,table_priv FROM mysql.tables_priv WHERE db LIKE ? AND table_name LIKE ?";
         ResultSet results = null;
         ArrayList<Row> grantRows = new ArrayList();
         java.sql.PreparedStatement pStmt = null;

         try {
            pStmt = this.prepareMetaDataSafeStatement(grantQuery);
            pStmt.setString(1, catalog != null && catalog.length() != 0 ? catalog : "%");
            pStmt.setString(2, tableNamePattern);
            results = pStmt.executeQuery();

            while(true) {
               String db;
               String table;
               String grantor;
               StringBuilder fullUser;
               String allPrivileges;
               do {
                  if (!results.next()) {
                     return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(grantRows, new MysqlaColumnDefinition(fields)));
                  }

                  String host = results.getString(1);
                  db = results.getString(2);
                  table = results.getString(3);
                  grantor = results.getString(4);
                  String user = results.getString(5);
                  if (user == null || user.length() == 0) {
                     user = "%";
                  }

                  fullUser = new StringBuilder(user);
                  if (host != null && this.useHostsInPrivileges) {
                     fullUser.append("@");
                     fullUser.append(host);
                  }

                  allPrivileges = results.getString(6);
               } while(allPrivileges == null);

               allPrivileges = allPrivileges.toUpperCase(Locale.ENGLISH);
               StringTokenizer st = new StringTokenizer(allPrivileges, ",");

               while(st.hasMoreTokens()) {
                  String privilege = st.nextToken().trim();
                  ResultSet columnResults = null;

                  try {
                     columnResults = this.getColumns(catalog, schemaPattern, table, "%");

                     while(columnResults.next()) {
                        byte[][] tuple = new byte[][]{this.s2b(db), null, this.s2b(table), grantor != null ? this.s2b(grantor) : null, this.s2b(fullUser.toString()), this.s2b(privilege), null, null};
                        grantRows.add(new ByteArrayRow(tuple, this.getExceptionInterceptor()));
                     }
                  } finally {
                     if (columnResults != null) {
                        try {
                           columnResults.close();
                        } catch (Exception var43) {
                        }
                     }

                  }
               }
            }
         } finally {
            if (results != null) {
               try {
                  results.close();
               } catch (Exception var42) {
               }

               results = null;
            }

            if (pStmt != null) {
               try {
                  pStmt.close();
               } catch (Exception var41) {
               }

               pStmt = null;
            }

         }
      } catch (CJException var46) {
         throw SQLExceptionsMapping.translateException(var46, this.getExceptionInterceptor());
      }
   }

   public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, final String[] types) throws SQLException {
      try {
         if (tableNamePattern == null) {
            if (!this.nullNamePatternMatchesAll) {
               throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.13"), "S1009", this.getExceptionInterceptor());
            }

            tableNamePattern = "%";
         }

         final SortedMap<DatabaseMetaData.TableMetaDataKey, Row> sortedRows = new TreeMap();
         ArrayList<Row> tuples = new ArrayList();
         final Statement stmt = this.conn.getMetadataSafeStatement();
         String tmpCat = "";
         if (catalog != null && catalog.length() != 0) {
            tmpCat = catalog;
         } else if (this.nullCatalogMeansCurrent) {
            tmpCat = this.database;
         }

         List<String> parseList = StringUtils.splitDBdotName(tableNamePattern, tmpCat, this.quotedId, this.conn.isNoBackslashEscapesSet());
         final String tableNamePat;
         if (parseList.size() == 2) {
            tableNamePat = (String)parseList.get(1);
         } else {
            tableNamePat = tableNamePattern;
         }

         try {
            (new IterateBlock<String>(this.getCatalogIterator(catalog)) {
               void forEach(String catalogStr) throws SQLException {
                  boolean operatingOnSystemDB = "information_schema".equalsIgnoreCase(catalogStr) || "mysql".equalsIgnoreCase(catalogStr) || "performance_schema".equalsIgnoreCase(catalogStr);
                  ResultSet results = null;

                  try {
                     try {
                        results = stmt.executeQuery("SHOW FULL TABLES FROM " + StringUtils.quoteIdentifier(catalogStr, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic) + " LIKE " + StringUtils.quoteIdentifier(tableNamePat, "'", true));
                     } catch (SQLException var27) {
                        if (!"08S01".equals(var27.getSQLState())) {
                           return;
                        }

                        throw var27;
                     }

                     boolean shouldReportTables = false;
                     boolean shouldReportViews = false;
                     boolean shouldReportSystemTables = false;
                     boolean shouldReportSystemViews = false;
                     boolean shouldReportLocalTemporaries = false;
                     int i;
                     if (types != null && types.length != 0) {
                        for(i = 0; i < types.length; ++i) {
                           if (DatabaseMetaData.TableType.TABLE.equalsTo(types[i])) {
                              shouldReportTables = true;
                           } else if (DatabaseMetaData.TableType.VIEW.equalsTo(types[i])) {
                              shouldReportViews = true;
                           } else if (DatabaseMetaData.TableType.SYSTEM_TABLE.equalsTo(types[i])) {
                              shouldReportSystemTables = true;
                           } else if (DatabaseMetaData.TableType.SYSTEM_VIEW.equalsTo(types[i])) {
                              shouldReportSystemViews = true;
                           } else if (DatabaseMetaData.TableType.LOCAL_TEMPORARY.equalsTo(types[i])) {
                              shouldReportLocalTemporaries = true;
                           }
                        }
                     } else {
                        shouldReportTables = true;
                        shouldReportViews = true;
                        shouldReportSystemTables = true;
                        shouldReportSystemViews = true;
                        shouldReportLocalTemporaries = true;
                     }

                     i = 0;
                     boolean hasTableTypes = false;

                     try {
                        i = results.findColumn("table_type");
                        hasTableTypes = true;
                     } catch (SQLException var26) {
                        try {
                           i = results.findColumn("Type");
                           hasTableTypes = true;
                        } catch (SQLException var25) {
                           hasTableTypes = false;
                        }
                     }

                     while(results.next()) {
                        byte[][] row = new byte[][]{catalogStr == null ? null : DatabaseMetaData.this.s2b(catalogStr), null, results.getBytes(1), null, new byte[0], null, null, null, null, null};
                        if (hasTableTypes) {
                           String tableType = results.getString(i);
                           switch(DatabaseMetaData.TableType.getTableTypeCompliantWith(tableType)) {
                           case TABLE:
                              boolean reportTable = false;
                              DatabaseMetaData.TableMetaDataKey tablesKey = null;
                              if (operatingOnSystemDB && shouldReportSystemTables) {
                                 row[3] = DatabaseMetaData.TableType.SYSTEM_TABLE.asBytes();
                                 tablesKey = DatabaseMetaData.this.new TableMetaDataKey(DatabaseMetaData.TableType.SYSTEM_TABLE.getName(), catalogStr, (String)null, results.getString(1));
                                 reportTable = true;
                              } else if (!operatingOnSystemDB && shouldReportTables) {
                                 row[3] = DatabaseMetaData.TableType.TABLE.asBytes();
                                 tablesKey = DatabaseMetaData.this.new TableMetaDataKey(DatabaseMetaData.TableType.TABLE.getName(), catalogStr, (String)null, results.getString(1));
                                 reportTable = true;
                              }

                              if (reportTable) {
                                 sortedRows.put(tablesKey, new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                              }
                              break;
                           case VIEW:
                              if (shouldReportViews) {
                                 row[3] = DatabaseMetaData.TableType.VIEW.asBytes();
                                 sortedRows.put(DatabaseMetaData.this.new TableMetaDataKey(DatabaseMetaData.TableType.VIEW.getName(), catalogStr, (String)null, results.getString(1)), new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                              }
                              break;
                           case SYSTEM_TABLE:
                              if (shouldReportSystemTables) {
                                 row[3] = DatabaseMetaData.TableType.SYSTEM_TABLE.asBytes();
                                 sortedRows.put(DatabaseMetaData.this.new TableMetaDataKey(DatabaseMetaData.TableType.SYSTEM_TABLE.getName(), catalogStr, (String)null, results.getString(1)), new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                              }
                              break;
                           case SYSTEM_VIEW:
                              if (shouldReportSystemViews) {
                                 row[3] = DatabaseMetaData.TableType.SYSTEM_VIEW.asBytes();
                                 sortedRows.put(DatabaseMetaData.this.new TableMetaDataKey(DatabaseMetaData.TableType.SYSTEM_VIEW.getName(), catalogStr, (String)null, results.getString(1)), new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                              }
                              break;
                           case LOCAL_TEMPORARY:
                              if (shouldReportLocalTemporaries) {
                                 row[3] = DatabaseMetaData.TableType.LOCAL_TEMPORARY.asBytes();
                                 sortedRows.put(DatabaseMetaData.this.new TableMetaDataKey(DatabaseMetaData.TableType.LOCAL_TEMPORARY.getName(), catalogStr, (String)null, results.getString(1)), new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                              }
                              break;
                           default:
                              row[3] = DatabaseMetaData.TableType.TABLE.asBytes();
                              sortedRows.put(DatabaseMetaData.this.new TableMetaDataKey(DatabaseMetaData.TableType.TABLE.getName(), catalogStr, (String)null, results.getString(1)), new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                           }
                        } else if (shouldReportTables) {
                           row[3] = DatabaseMetaData.TableType.TABLE.asBytes();
                           sortedRows.put(DatabaseMetaData.this.new TableMetaDataKey(DatabaseMetaData.TableType.TABLE.getName(), catalogStr, (String)null, results.getString(1)), new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                        }
                     }

                  } finally {
                     if (results != null) {
                        try {
                           results.close();
                        } catch (Exception var24) {
                        }

                        results = null;
                     }

                  }
               }
            }).doForAll();
         } finally {
            if (stmt != null) {
               stmt.close();
            }

         }

         tuples.addAll(sortedRows.values());
         ResultSet tables = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(tuples, this.createTablesFields()));
         return tables;
      } catch (CJException var16) {
         throw SQLExceptionsMapping.translateException(var16, this.getExceptionInterceptor());
      }
   }

   protected ColumnDefinition createTablesFields() {
      Field[] fields = new Field[]{new Field("", "TABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 255), new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 0), new Field("", "TABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 255), new Field("", "TABLE_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 5), new Field("", "REMARKS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 0), new Field("", "TYPE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 0), new Field("", "TYPE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 0), new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 0), new Field("", "SELF_REFERENCING_COL_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 0), new Field("", "REF_GENERATION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 0)};
      return new MysqlaColumnDefinition(fields);
   }

   public ResultSet getTableTypes() throws SQLException {
      try {
         ArrayList<Row> tuples = new ArrayList();
         Field[] fields = new Field[]{new Field("", "TABLE_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 256)};
         tuples.add(new ByteArrayRow(new byte[][]{DatabaseMetaData.TableType.LOCAL_TEMPORARY.asBytes()}, this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(new byte[][]{DatabaseMetaData.TableType.SYSTEM_TABLE.asBytes()}, this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(new byte[][]{DatabaseMetaData.TableType.SYSTEM_VIEW.asBytes()}, this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(new byte[][]{DatabaseMetaData.TableType.TABLE.asBytes()}, this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(new byte[][]{DatabaseMetaData.TableType.VIEW.asBytes()}, this.getExceptionInterceptor()));
         return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(tuples, new MysqlaColumnDefinition(fields)));
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public String getTimeDateFunctions() throws SQLException {
      try {
         return "DAYOFWEEK,WEEKDAY,DAYOFMONTH,DAYOFYEAR,MONTH,DAYNAME,MONTHNAME,QUARTER,WEEK,YEAR,HOUR,MINUTE,SECOND,PERIOD_ADD,PERIOD_DIFF,TO_DAYS,FROM_DAYS,DATE_FORMAT,TIME_FORMAT,CURDATE,CURRENT_DATE,CURTIME,CURRENT_TIME,NOW,SYSDATE,CURRENT_TIMESTAMP,UNIX_TIMESTAMP,FROM_UNIXTIME,SEC_TO_TIME,TIME_TO_SEC";
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   private byte[][] getTypeInfo(String mysqlTypeName) throws SQLException {
      MysqlType mt = MysqlType.getByName(mysqlTypeName);
      byte[][] rowVal = new byte[18][];
      rowVal[0] = this.s2b(mysqlTypeName);
      rowVal[1] = Integer.toString(mt.getJdbcType()).getBytes();
      rowVal[2] = Integer.toString(mt.getPrecision() > 2147483647L ? Integer.MAX_VALUE : mt.getPrecision().intValue()).getBytes();
      switch(mt) {
      case ENUM:
      case SET:
      case CHAR:
      case VARCHAR:
      case TINYTEXT:
      case MEDIUMTEXT:
      case LONGTEXT:
      case JSON:
      case TEXT:
      case TINYBLOB:
      case MEDIUMBLOB:
      case LONGBLOB:
      case BLOB:
      case BINARY:
      case VARBINARY:
      case GEOMETRY:
      case UNKNOWN:
      case DATE:
      case TIME:
      case DATETIME:
      case TIMESTAMP:
         rowVal[3] = this.s2b("'");
         rowVal[4] = this.s2b("'");
         break;
      case BIT:
      case TINYINT:
      case TINYINT_UNSIGNED:
      case BOOLEAN:
      case NULL:
      case YEAR:
      default:
         rowVal[3] = this.s2b("");
         rowVal[4] = this.s2b("");
      }

      rowVal[5] = this.s2b(mt.getCreateParams());
      rowVal[6] = Integer.toString(1).getBytes();
      rowVal[7] = this.s2b("true");
      rowVal[8] = Integer.toString(3).getBytes();
      rowVal[9] = this.s2b(mt.isAllowed(32) ? "true" : "false");
      rowVal[10] = this.s2b("false");
      rowVal[11] = this.s2b("false");
      rowVal[12] = this.s2b(mt.getName());
      switch(mt) {
      case DECIMAL:
      case DECIMAL_UNSIGNED:
      case DOUBLE:
      case DOUBLE_UNSIGNED:
         rowVal[13] = this.s2b("-308");
         rowVal[14] = this.s2b("308");
         break;
      case FLOAT:
      case FLOAT_UNSIGNED:
         rowVal[13] = this.s2b("-38");
         rowVal[14] = this.s2b("38");
         break;
      default:
         rowVal[13] = this.s2b("0");
         rowVal[14] = this.s2b("0");
      }

      rowVal[15] = this.s2b("0");
      rowVal[16] = this.s2b("0");
      rowVal[17] = this.s2b("10");
      return rowVal;
   }

   public ResultSet getTypeInfo() throws SQLException {
      try {
         Field[] fields = new Field[]{new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 5), new Field("", "PRECISION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "LITERAL_PREFIX", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 4), new Field("", "LITERAL_SUFFIX", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 4), new Field("", "CREATE_PARAMS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "NULLABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 5), new Field("", "CASE_SENSITIVE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.BOOLEAN, 3), new Field("", "SEARCHABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 3), new Field("", "UNSIGNED_ATTRIBUTE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.BOOLEAN, 3), new Field("", "FIXED_PREC_SCALE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.BOOLEAN, 3), new Field("", "AUTO_INCREMENT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.BOOLEAN, 3), new Field("", "LOCAL_TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "MINIMUM_SCALE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 5), new Field("", "MAXIMUM_SCALE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 5), new Field("", "SQL_DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "SQL_DATETIME_SUB", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "NUM_PREC_RADIX", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10)};
         ArrayList<Row> tuples = new ArrayList();
         tuples.add(new ByteArrayRow(this.getTypeInfo("BIT"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("BOOL"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("TINYINT"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("TINYINT UNSIGNED"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("BIGINT"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("BIGINT UNSIGNED"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("LONG VARBINARY"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("MEDIUMBLOB"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("LONGBLOB"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("BLOB"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("VARBINARY"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("TINYBLOB"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("BINARY"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("LONG VARCHAR"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("MEDIUMTEXT"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("LONGTEXT"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("TEXT"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("CHAR"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("ENUM"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("SET"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("DECIMAL"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("NUMERIC"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("INTEGER"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("INTEGER UNSIGNED"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("INT"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("INT UNSIGNED"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("MEDIUMINT"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("MEDIUMINT UNSIGNED"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("SMALLINT"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("SMALLINT UNSIGNED"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("FLOAT"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("DOUBLE"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("DOUBLE PRECISION"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("REAL"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("VARCHAR"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("TINYTEXT"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("DATE"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("YEAR"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("TIME"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("DATETIME"), this.getExceptionInterceptor()));
         tuples.add(new ByteArrayRow(this.getTypeInfo("TIMESTAMP"), this.getExceptionInterceptor()));
         return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(tuples, new MysqlaColumnDefinition(fields)));
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) throws SQLException {
      try {
         Field[] fields = new Field[]{new Field("", "TYPE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 32), new Field("", "TYPE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 32), new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 32), new Field("", "CLASS_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 32), new Field("", "DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "REMARKS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 32), new Field("", "BASE_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 10)};
         ArrayList<Row> tuples = new ArrayList();
         return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(tuples, new MysqlaColumnDefinition(fields)));
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.getExceptionInterceptor());
      }
   }

   public String getURL() throws SQLException {
      try {
         return this.conn.getURL();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public String getUserName() throws SQLException {
      try {
         if (this.useHostsInPrivileges) {
            Statement stmt = null;
            ResultSet rs = null;

            String var3;
            try {
               stmt = this.conn.getMetadataSafeStatement();
               rs = stmt.executeQuery("SELECT USER()");
               rs.next();
               var3 = rs.getString(1);
            } finally {
               if (rs != null) {
                  try {
                     rs.close();
                  } catch (Exception var15) {
                     AssertionFailedException.shouldNotHappen(var15);
                  }

                  rs = null;
               }

               if (stmt != null) {
                  try {
                     stmt.close();
                  } catch (Exception var14) {
                     AssertionFailedException.shouldNotHappen(var14);
                  }

                  stmt = null;
               }

            }

            return var3;
         } else {
            return this.conn.getUser();
         }
      } catch (CJException var17) {
         throw SQLExceptionsMapping.translateException(var17, this.getExceptionInterceptor());
      }
   }

   public ResultSet getVersionColumns(String catalog, String schema, final String table) throws SQLException {
      try {
         if (table == null) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.2"), "S1009", this.getExceptionInterceptor());
         } else {
            Field[] fields = new Field[]{new Field("", "SCOPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 5), new Field("", "COLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 5), new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 16), new Field("", "COLUMN_SIZE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 16), new Field("", "BUFFER_LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 16), new Field("", "DECIMAL_DIGITS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 16), new Field("", "PSEUDO_COLUMN", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 5)};
            final ArrayList<Row> rows = new ArrayList();
            final Statement stmt = this.conn.getMetadataSafeStatement();

            try {
               (new IterateBlock<String>(this.getCatalogIterator(catalog)) {
                  void forEach(String catalogStr) throws SQLException {
                     ResultSet results = null;

                     try {
                        StringBuilder whereBuf = new StringBuilder(" Extra LIKE '%on update CURRENT_TIMESTAMP%'");
                        List<String> rsFields = new ArrayList();
                        if (whereBuf.length() > 0 || rsFields.size() > 0) {
                           StringBuilder queryBuf = new StringBuilder("SHOW COLUMNS FROM ");
                           queryBuf.append(StringUtils.quoteIdentifier(table, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                           queryBuf.append(" FROM ");
                           queryBuf.append(StringUtils.quoteIdentifier(catalogStr, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                           queryBuf.append(" WHERE");
                           queryBuf.append(whereBuf.toString());
                           results = stmt.executeQuery(queryBuf.toString());

                           while(results.next()) {
                              DatabaseMetaData.TypeDescriptor typeDesc = DatabaseMetaData.this.new TypeDescriptor(results.getString("Type"), results.getString("Null"));
                              byte[][] rowVal = new byte[][]{null, results.getBytes("Field"), Short.toString((short)typeDesc.mysqlType.getJdbcType()).getBytes(), DatabaseMetaData.this.s2b(typeDesc.mysqlType.getName()), typeDesc.columnSize == null ? null : DatabaseMetaData.this.s2b(typeDesc.columnSize.toString()), DatabaseMetaData.this.s2b(Integer.toString(typeDesc.bufferLength)), typeDesc.decimalDigits == null ? null : DatabaseMetaData.this.s2b(typeDesc.decimalDigits.toString()), Integer.toString(1).getBytes()};
                              rows.add(new ByteArrayRow(rowVal, DatabaseMetaData.this.getExceptionInterceptor()));
                           }
                        }
                     } catch (SQLException var16) {
                        if (!"42S02".equals(var16.getSQLState())) {
                           throw var16;
                        }
                     } finally {
                        if (results != null) {
                           try {
                              results.close();
                           } catch (Exception var15) {
                           }

                           results = null;
                        }

                     }

                  }
               }).doForAll();
            } finally {
               if (stmt != null) {
                  stmt.close();
               }

            }

            return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new MysqlaColumnDefinition(fields)));
         }
      } catch (CJException var12) {
         throw SQLExceptionsMapping.translateException(var12, this.getExceptionInterceptor());
      }
   }

   public boolean insertsAreDetected(int type) throws SQLException {
      try {
         return false;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public boolean isCatalogAtStart() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean isReadOnly() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean locatorsUpdateCopy() throws SQLException {
      try {
         return !(Boolean)this.conn.getPropertySet().getBooleanReadableProperty("emulateLocators").getValue();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean nullPlusNonNullIsNull() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean nullsAreSortedAtEnd() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean nullsAreSortedAtStart() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean nullsAreSortedHigh() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean nullsAreSortedLow() throws SQLException {
      try {
         return !this.nullsAreSortedHigh();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean othersDeletesAreVisible(int type) throws SQLException {
      try {
         return false;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public boolean othersInsertsAreVisible(int type) throws SQLException {
      try {
         return false;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public boolean othersUpdatesAreVisible(int type) throws SQLException {
      try {
         return false;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public boolean ownDeletesAreVisible(int type) throws SQLException {
      try {
         return false;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public boolean ownInsertsAreVisible(int type) throws SQLException {
      try {
         return false;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public boolean ownUpdatesAreVisible(int type) throws SQLException {
      try {
         return false;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   protected DatabaseMetaData.LocalAndReferencedColumns parseTableStatusIntoLocalAndReferencedColumns(String keysComment) throws SQLException {
      String columnsDelimitter = ",";
      int indexOfOpenParenLocalColumns = StringUtils.indexOfIgnoreCase(0, keysComment, (String)"(", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__ALL);
      if (indexOfOpenParenLocalColumns == -1) {
         throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.14"), "S1000", this.getExceptionInterceptor());
      } else {
         String constraintName = StringUtils.unQuoteIdentifier(keysComment.substring(0, indexOfOpenParenLocalColumns).trim(), this.quotedId);
         keysComment = keysComment.substring(indexOfOpenParenLocalColumns, keysComment.length());
         String keysCommentTrimmed = keysComment.trim();
         int indexOfCloseParenLocalColumns = StringUtils.indexOfIgnoreCase(0, keysCommentTrimmed, (String)")", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__ALL);
         if (indexOfCloseParenLocalColumns == -1) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.15"), "S1000", this.getExceptionInterceptor());
         } else {
            String localColumnNamesString = keysCommentTrimmed.substring(1, indexOfCloseParenLocalColumns);
            int indexOfRefer = StringUtils.indexOfIgnoreCase(0, keysCommentTrimmed, (String)"REFER ", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__ALL);
            if (indexOfRefer == -1) {
               throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.16"), "S1000", this.getExceptionInterceptor());
            } else {
               int indexOfOpenParenReferCol = StringUtils.indexOfIgnoreCase(indexOfRefer, keysCommentTrimmed, "(", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__MRK_COM_WS);
               if (indexOfOpenParenReferCol == -1) {
                  throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.17"), "S1000", this.getExceptionInterceptor());
               } else {
                  String referCatalogTableString = keysCommentTrimmed.substring(indexOfRefer + "REFER ".length(), indexOfOpenParenReferCol);
                  int indexOfSlash = StringUtils.indexOfIgnoreCase(0, referCatalogTableString, (String)"/", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__MRK_COM_WS);
                  if (indexOfSlash == -1) {
                     throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.18"), "S1000", this.getExceptionInterceptor());
                  } else {
                     String referCatalog = StringUtils.unQuoteIdentifier(referCatalogTableString.substring(0, indexOfSlash), this.quotedId);
                     String referTable = StringUtils.unQuoteIdentifier(referCatalogTableString.substring(indexOfSlash + 1).trim(), this.quotedId);
                     int indexOfCloseParenRefer = StringUtils.indexOfIgnoreCase(indexOfOpenParenReferCol, keysCommentTrimmed, ")", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__ALL);
                     if (indexOfCloseParenRefer == -1) {
                        throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.19"), "S1000", this.getExceptionInterceptor());
                     } else {
                        String referColumnNamesString = keysCommentTrimmed.substring(indexOfOpenParenReferCol + 1, indexOfCloseParenRefer);
                        List<String> referColumnsList = StringUtils.split(referColumnNamesString, columnsDelimitter, this.quotedId, this.quotedId, false);
                        List<String> localColumnsList = StringUtils.split(localColumnNamesString, columnsDelimitter, this.quotedId, this.quotedId, false);
                        return new DatabaseMetaData.LocalAndReferencedColumns(localColumnsList, referColumnsList, constraintName, referCatalog, referTable);
                     }
                  }
               }
            }
         }
      }
   }

   protected byte[] s2b(String s) throws SQLException {
      if (s == null) {
         return null;
      } else {
         try {
            return StringUtils.getBytes(s, this.conn.getCharacterSetMetadata());
         } catch (CJException var3) {
            throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
         }
      }
   }

   public boolean storesLowerCaseIdentifiers() throws SQLException {
      try {
         return this.conn.storesLowerCaseTableName();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
      try {
         return this.conn.storesLowerCaseTableName();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean storesMixedCaseIdentifiers() throws SQLException {
      try {
         return !this.conn.storesLowerCaseTableName();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
      try {
         return !this.conn.storesLowerCaseTableName();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean storesUpperCaseIdentifiers() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsAlterTableWithAddColumn() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsAlterTableWithDropColumn() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsANSI92EntryLevelSQL() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsANSI92FullSQL() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsANSI92IntermediateSQL() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsBatchUpdates() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsCatalogsInDataManipulation() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsCatalogsInProcedureCalls() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsCatalogsInTableDefinitions() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsColumnAliasing() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsConvert() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsConvert(int fromType, int toType) throws SQLException {
      try {
         return MysqlType.supportsConvert(fromType, toType);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public boolean supportsCoreSQLGrammar() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsCorrelatedSubqueries() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsDifferentTableCorrelationNames() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsExpressionsInOrderBy() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsExtendedSQLGrammar() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsFullOuterJoins() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsGetGeneratedKeys() {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsGroupBy() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsGroupByBeyondSelect() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsGroupByUnrelated() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsIntegrityEnhancementFacility() throws SQLException {
      try {
         return (Boolean)this.conn.getPropertySet().getBooleanReadableProperty("overrideSupportsIntegrityEnhancementFacility").getValue();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsLikeEscapeClause() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsLimitedOuterJoins() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsMinimumSQLGrammar() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsMixedCaseIdentifiers() throws SQLException {
      try {
         return !this.conn.lowerCaseTableNames();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
      try {
         return !this.conn.lowerCaseTableNames();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsMultipleOpenResults() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsMultipleResultSets() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsMultipleTransactions() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsNamedParameters() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsNonNullableColumns() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsOrderByUnrelated() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsOuterJoins() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsPositionedDelete() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsPositionedUpdate() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
      try {
         switch(type) {
         case 1003:
            if (concurrency != 1007 && concurrency != 1008) {
               throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.20"), "S1009", this.getExceptionInterceptor());
            }

            return true;
         case 1004:
            if (concurrency != 1007 && concurrency != 1008) {
               throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.20"), "S1009", this.getExceptionInterceptor());
            }

            return true;
         case 1005:
            return false;
         default:
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.20"), "S1009", this.getExceptionInterceptor());
         }
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public boolean supportsResultSetHoldability(int holdability) throws SQLException {
      try {
         return holdability == 1;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public boolean supportsResultSetType(int type) throws SQLException {
      try {
         return type == 1004;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public boolean supportsSavepoints() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsSchemasInDataManipulation() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsSchemasInIndexDefinitions() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsSchemasInProcedureCalls() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsSchemasInTableDefinitions() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsSelectForUpdate() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsStatementPooling() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsStoredProcedures() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsSubqueriesInComparisons() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsSubqueriesInExists() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsSubqueriesInIns() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsSubqueriesInQuantifieds() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsTableCorrelationNames() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
      try {
         switch(level) {
         case 1:
         case 2:
         case 4:
         case 8:
            return true;
         case 3:
         case 5:
         case 6:
         case 7:
         default:
            return false;
         }
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public boolean supportsTransactions() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsUnion() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean supportsUnionAll() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean updatesAreDetected(int type) throws SQLException {
      try {
         return false;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public boolean usesLocalFilePerTable() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean usesLocalFiles() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public ResultSet getClientInfoProperties() throws SQLException {
      try {
         Field[] fields = new Field[]{new Field("", "NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 255), new Field("", "MAX_LEN", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "DEFAULT_VALUE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 255), new Field("", "DESCRIPTION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 255)};
         return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(new ArrayList(), new MysqlaColumnDefinition(fields)));
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern) throws SQLException {
      try {
         Field[] fields = this.createFunctionColumnsFields();
         return this.getProcedureOrFunctionColumns(fields, catalog, schemaPattern, functionNamePattern, columnNamePattern, false, true);
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   protected Field[] createFunctionColumnsFields() {
      Field[] fields = new Field[]{new Field("", "FUNCTION_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "FUNCTION_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "FUNCTION_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "COLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "COLUMN_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 64), new Field("", "DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 6), new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 64), new Field("", "PRECISION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "SCALE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 12), new Field("", "RADIX", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 6), new Field("", "NULLABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 6), new Field("", "REMARKS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "CHAR_OCTET_LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "ORDINAL_POSITION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "IS_NULLABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 12), new Field("", "SPECIFIC_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 64)};
      return fields;
   }

   public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
      try {
         Field[] fields = new Field[]{new Field("", "FUNCTION_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "FUNCTION_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "FUNCTION_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "REMARKS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "FUNCTION_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 6), new Field("", "SPECIFIC_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255)};
         return this.getProceduresAndOrFunctions(fields, catalog, schemaPattern, functionNamePattern, false, true);
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public boolean providesQueryObjectGenerator() throws SQLException {
      return false;
   }

   public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
      try {
         Field[] fields = new Field[]{new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 255), new Field("", "TABLE_CATALOG", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 255)};
         return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(new ArrayList(), new MysqlaColumnDefinition(fields)));
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   protected java.sql.PreparedStatement prepareMetaDataSafeStatement(String sql) throws SQLException {
      java.sql.PreparedStatement pStmt = this.conn.clientPrepareStatement(sql);
      if (pStmt.getMaxRows() != 0) {
         pStmt.setMaxRows(0);
      }

      ((com.mysql.cj.api.jdbc.Statement)pStmt).setHoldResultsOpenOverClose(true);
      return pStmt;
   }

   public ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
      try {
         Field[] fields = new Field[]{new Field("", "TABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "TABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "COLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "COLUMN_SIZE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "DECIMAL_DIGITS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "NUM_PREC_RADIX", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "COLUMN_USAGE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "REMARKS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "CHAR_OCTET_LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "IS_NULLABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512)};
         return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(new ArrayList(), new MysqlaColumnDefinition(fields)));
      } catch (CJException var7) {
         throw SQLExceptionsMapping.translateException(var7, this.getExceptionInterceptor());
      }
   }

   public boolean generatedKeyAlwaysReturned() throws SQLException {
      try {
         return true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public <T> T unwrap(Class<T> iface) throws SQLException {
      try {
         try {
            return iface.cast(this);
         } catch (ClassCastException var4) {
            throw SQLError.createSQLException(Messages.getString("Common.UnableToUnwrap", new Object[]{iface.toString()}), "S1009", this.conn.getExceptionInterceptor());
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public boolean isWrapperFor(Class<?> iface) throws SQLException {
      try {
         return iface.isInstance(this);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public RowIdLifetime getRowIdLifetime() throws SQLException {
      try {
         return RowIdLifetime.ROWID_UNSUPPORTED;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
      try {
         return false;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public String getMetadataEncoding() {
      return this.metadataEncoding;
   }

   public void setMetadataEncoding(String metadataEncoding) {
      this.metadataEncoding = metadataEncoding;
   }

   public int getMetadataCollationIndex() {
      return this.metadataCollationIndex;
   }

   public void setMetadataCollationIndex(int metadataCollationIndex) {
      this.metadataCollationIndex = metadataCollationIndex;
   }

   protected static enum ProcedureType {
      PROCEDURE,
      FUNCTION;
   }

   protected static enum TableType {
      LOCAL_TEMPORARY("LOCAL TEMPORARY"),
      SYSTEM_TABLE("SYSTEM TABLE"),
      SYSTEM_VIEW("SYSTEM VIEW"),
      TABLE("TABLE", new String[]{"BASE TABLE"}),
      VIEW("VIEW"),
      UNKNOWN("UNKNOWN");

      private String name;
      private byte[] nameAsBytes;
      private String[] synonyms;

      private TableType(String tableTypeName) {
         this(tableTypeName, (String[])null);
      }

      private TableType(String tableTypeName, String[] tableTypeSynonyms) {
         this.name = tableTypeName;
         this.nameAsBytes = tableTypeName.getBytes();
         this.synonyms = tableTypeSynonyms;
      }

      String getName() {
         return this.name;
      }

      byte[] asBytes() {
         return this.nameAsBytes;
      }

      boolean equalsTo(String tableTypeName) {
         return this.name.equalsIgnoreCase(tableTypeName);
      }

      static DatabaseMetaData.TableType getTableTypeEqualTo(String tableTypeName) {
         DatabaseMetaData.TableType[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            DatabaseMetaData.TableType tableType = var1[var3];
            if (tableType.equalsTo(tableTypeName)) {
               return tableType;
            }
         }

         return UNKNOWN;
      }

      boolean compliesWith(String tableTypeName) {
         if (this.equalsTo(tableTypeName)) {
            return true;
         } else {
            if (this.synonyms != null) {
               String[] var2 = this.synonyms;
               int var3 = var2.length;

               for(int var4 = 0; var4 < var3; ++var4) {
                  String synonym = var2[var4];
                  if (synonym.equalsIgnoreCase(tableTypeName)) {
                     return true;
                  }
               }
            }

            return false;
         }
      }

      static DatabaseMetaData.TableType getTableTypeCompliantWith(String tableTypeName) {
         DatabaseMetaData.TableType[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            DatabaseMetaData.TableType tableType = var1[var3];
            if (tableType.compliesWith(tableTypeName)) {
               return tableType;
            }
         }

         return UNKNOWN;
      }
   }

   protected class ComparableWrapper<K extends Object & Comparable<? super K>, V> implements Comparable<DatabaseMetaData.ComparableWrapper<K, V>> {
      K key;
      V value;

      public ComparableWrapper(K key, V value) {
         this.key = key;
         this.value = value;
      }

      public K getKey() {
         return this.key;
      }

      public V getValue() {
         return this.value;
      }

      public int compareTo(DatabaseMetaData.ComparableWrapper<K, V> other) {
         return ((Comparable)this.getKey()).compareTo(other.getKey());
      }

      public boolean equals(Object obj) {
         if (obj == null) {
            return false;
         } else if (obj == this) {
            return true;
         } else if (!(obj instanceof DatabaseMetaData.ComparableWrapper)) {
            return false;
         } else {
            Object otherKey = ((DatabaseMetaData.ComparableWrapper)obj).getKey();
            return this.key.equals(otherKey);
         }
      }

      public int hashCode() {
         assert false : "hashCode not designed";

         return 0;
      }

      public String toString() {
         return "{KEY:" + this.key + "; VALUE:" + this.value + "}";
      }
   }

   protected class TableMetaDataKey implements Comparable<DatabaseMetaData.TableMetaDataKey> {
      String tableType;
      String tableCat;
      String tableSchem;
      String tableName;

      TableMetaDataKey(String tableType, String tableCat, String tableSchem, String tableName) {
         this.tableType = tableType == null ? "" : tableType;
         this.tableCat = tableCat == null ? "" : tableCat;
         this.tableSchem = tableSchem == null ? "" : tableSchem;
         this.tableName = tableName == null ? "" : tableName;
      }

      public int compareTo(DatabaseMetaData.TableMetaDataKey tablesKey) {
         int compareResult;
         if ((compareResult = this.tableType.compareTo(tablesKey.tableType)) != 0) {
            return compareResult;
         } else if ((compareResult = this.tableCat.compareTo(tablesKey.tableCat)) != 0) {
            return compareResult;
         } else {
            return (compareResult = this.tableSchem.compareTo(tablesKey.tableSchem)) != 0 ? compareResult : this.tableName.compareTo(tablesKey.tableName);
         }
      }

      public boolean equals(Object obj) {
         if (obj == null) {
            return false;
         } else if (obj == this) {
            return true;
         } else if (!(obj instanceof DatabaseMetaData.TableMetaDataKey)) {
            return false;
         } else {
            return this.compareTo((DatabaseMetaData.TableMetaDataKey)obj) == 0;
         }
      }

      public int hashCode() {
         assert false : "hashCode not designed";

         return 0;
      }
   }

   protected class IndexMetaDataKey implements Comparable<DatabaseMetaData.IndexMetaDataKey> {
      Boolean columnNonUnique;
      Short columnType;
      String columnIndexName;
      Short columnOrdinalPosition;

      IndexMetaDataKey(boolean columnNonUnique, short columnType, String columnIndexName, short columnOrdinalPosition) {
         this.columnNonUnique = columnNonUnique;
         this.columnType = columnType;
         this.columnIndexName = columnIndexName;
         this.columnOrdinalPosition = columnOrdinalPosition;
      }

      public int compareTo(DatabaseMetaData.IndexMetaDataKey indexInfoKey) {
         int compareResult;
         if ((compareResult = this.columnNonUnique.compareTo(indexInfoKey.columnNonUnique)) != 0) {
            return compareResult;
         } else if ((compareResult = this.columnType.compareTo(indexInfoKey.columnType)) != 0) {
            return compareResult;
         } else {
            return (compareResult = this.columnIndexName.compareTo(indexInfoKey.columnIndexName)) != 0 ? compareResult : this.columnOrdinalPosition.compareTo(indexInfoKey.columnOrdinalPosition);
         }
      }

      public boolean equals(Object obj) {
         if (obj == null) {
            return false;
         } else if (obj == this) {
            return true;
         } else if (!(obj instanceof DatabaseMetaData.IndexMetaDataKey)) {
            return false;
         } else {
            return this.compareTo((DatabaseMetaData.IndexMetaDataKey)obj) == 0;
         }
      }

      public int hashCode() {
         assert false : "hashCode not designed";

         return 0;
      }
   }

   class TypeDescriptor {
      int bufferLength;
      int charOctetLength;
      Integer columnSize = null;
      Integer decimalDigits = null;
      String isNullable;
      int nullability;
      int numPrecRadix = 10;
      String mysqlTypeName;
      MysqlType mysqlType;

      TypeDescriptor(String typeInfo, String nullabilityInfo) throws SQLException {
         if (typeInfo == null) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.0"), "S1009", DatabaseMetaData.this.getExceptionInterceptor());
         } else {
            this.mysqlType = MysqlType.getByName(typeInfo);
            int maxLength = 0;
            String temp;
            StringTokenizer tokenizer;
            label105:
            switch(this.mysqlType) {
            case DECIMAL:
            case DECIMAL_UNSIGNED:
            case FLOAT:
            case FLOAT_UNSIGNED:
            case DOUBLE:
            case DOUBLE_UNSIGNED:
               if (typeInfo.indexOf(",") != -1) {
                  this.columnSize = Integer.valueOf(typeInfo.substring(typeInfo.indexOf("(") + 1, typeInfo.indexOf(",")).trim());
                  this.decimalDigits = Integer.valueOf(typeInfo.substring(typeInfo.indexOf(",") + 1, typeInfo.indexOf(")")).trim());
               } else {
                  switch(this.mysqlType) {
                  case DECIMAL:
                  case DECIMAL_UNSIGNED:
                     this.columnSize = 65;
                     break;
                  case FLOAT:
                  case FLOAT_UNSIGNED:
                     this.columnSize = 12;
                     break;
                  case DOUBLE:
                  case DOUBLE_UNSIGNED:
                     this.columnSize = 22;
                  }

                  this.decimalDigits = 0;
               }
               break;
            case ENUM:
               temp = typeInfo.substring(typeInfo.indexOf("(") + 1, typeInfo.lastIndexOf(")"));

               String nextToken;
               for(tokenizer = new StringTokenizer(temp, ","); tokenizer.hasMoreTokens(); maxLength = Math.max(maxLength, nextToken.length() - 2)) {
                  nextToken = tokenizer.nextToken();
               }

               this.columnSize = maxLength;
               break;
            case SET:
               temp = typeInfo.substring(typeInfo.indexOf("(") + 1, typeInfo.lastIndexOf(")"));
               tokenizer = new StringTokenizer(temp, ",");
               int numElements = tokenizer.countTokens();
               if (numElements > 0) {
                  maxLength += numElements - 1;
               }

               while(true) {
                  while(tokenizer.hasMoreTokens()) {
                     String setMember = tokenizer.nextToken().trim();
                     if (setMember.startsWith("'") && setMember.endsWith("'")) {
                        maxLength += setMember.length() - 2;
                     } else {
                        maxLength += setMember.length();
                     }
                  }

                  this.columnSize = maxLength;
                  break label105;
               }
            case CHAR:
            case VARCHAR:
            case TINYTEXT:
            case MEDIUMTEXT:
            case LONGTEXT:
            case JSON:
            case TEXT:
            case TINYBLOB:
            case MEDIUMBLOB:
            case LONGBLOB:
            case BLOB:
            case BINARY:
            case VARBINARY:
            case BIT:
               if (this.mysqlType == MysqlType.CHAR) {
                  this.columnSize = 1;
               }

               if (typeInfo.indexOf("(") != -1) {
                  int endParenIndex = typeInfo.indexOf(")");
                  if (endParenIndex == -1) {
                     endParenIndex = typeInfo.length();
                  }

                  this.columnSize = Integer.valueOf(typeInfo.substring(typeInfo.indexOf("(") + 1, endParenIndex).trim());
                  if (DatabaseMetaData.this.tinyInt1isBit && this.columnSize == 1 && StringUtils.startsWithIgnoreCase(typeInfo, 0, "tinyint")) {
                     if (DatabaseMetaData.this.transformedBitIsBoolean) {
                        this.mysqlType = MysqlType.BOOLEAN;
                     } else {
                        this.mysqlType = MysqlType.BIT;
                     }
                  }
               }
               break;
            case TINYINT:
            case TINYINT_UNSIGNED:
               if (DatabaseMetaData.this.tinyInt1isBit && typeInfo.indexOf("(1)") != -1) {
                  if (DatabaseMetaData.this.transformedBitIsBoolean) {
                     this.mysqlType = MysqlType.BOOLEAN;
                  } else {
                     this.mysqlType = MysqlType.BIT;
                  }
               } else {
                  this.columnSize = 3;
               }
            case BOOLEAN:
            case GEOMETRY:
            case NULL:
            case UNKNOWN:
            case YEAR:
            }

            if (this.columnSize == null) {
               this.columnSize = this.mysqlType.getPrecision() > 2147483647L ? Integer.MAX_VALUE : this.mysqlType.getPrecision().intValue();
            }

            this.bufferLength = DatabaseMetaData.maxBufferSize;
            this.numPrecRadix = 10;
            if (nullabilityInfo != null) {
               if (nullabilityInfo.equals("YES")) {
                  this.nullability = 1;
                  this.isNullable = "YES";
               } else if (nullabilityInfo.equals("UNKNOWN")) {
                  this.nullability = 2;
                  this.isNullable = "";
               } else {
                  this.nullability = 0;
                  this.isNullable = "NO";
               }
            } else {
               this.nullability = 0;
               this.isNullable = "NO";
            }

         }
      }
   }

   protected class SingleStringIterator extends DatabaseMetaData.IteratorWithCleanup<String> {
      boolean onFirst = true;
      String value;

      SingleStringIterator(String s) {
         super();
         this.value = s;
      }

      void close() throws SQLException {
      }

      boolean hasNext() throws SQLException {
         return this.onFirst;
      }

      String next() throws SQLException {
         this.onFirst = false;
         return this.value;
      }
   }

   protected class ResultSetIterator extends DatabaseMetaData.IteratorWithCleanup<String> {
      int colIndex;
      ResultSet resultSet;

      ResultSetIterator(ResultSet rs, int index) {
         super();
         this.resultSet = rs;
         this.colIndex = index;
      }

      void close() throws SQLException {
         this.resultSet.close();
      }

      boolean hasNext() throws SQLException {
         return this.resultSet.next();
      }

      String next() throws SQLException {
         return this.resultSet.getObject(this.colIndex).toString();
      }
   }

   class LocalAndReferencedColumns {
      String constraintName;
      List<String> localColumnsList;
      String referencedCatalog;
      List<String> referencedColumnsList;
      String referencedTable;

      LocalAndReferencedColumns(List<String> localColumns, List<String> refColumns, String constName, String refCatalog, String refTable) {
         this.localColumnsList = localColumns;
         this.referencedColumnsList = refColumns;
         this.constraintName = constName;
         this.referencedTable = refTable;
         this.referencedCatalog = refCatalog;
      }
   }

   protected abstract class IteratorWithCleanup<T> {
      abstract void close() throws SQLException;

      abstract boolean hasNext() throws SQLException;

      abstract T next() throws SQLException;
   }
}
