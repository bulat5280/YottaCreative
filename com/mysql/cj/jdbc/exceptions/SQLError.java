package com.mysql.cj.jdbc.exceptions;

import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.MysqlErrorNumbers;
import com.mysql.cj.core.util.Util;
import java.lang.reflect.Field;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLSyntaxErrorException;
import java.sql.SQLTransientConnectionException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class SQLError {
   public static final int ER_WARNING_NOT_COMPLETE_ROLLBACK = 1196;
   public static final String SQL_STATE_BAD_SSL_PARAMS = "08000";
   private static Map<Integer, String> mysqlToSql99State;
   public static final String SQL_STATE_WARNING = "01000";
   public static final String SQL_STATE_DISCONNECT_ERROR = "01002";
   public static final String SQL_STATE_DATE_TRUNCATED = "01004";
   public static final String SQL_STATE_PRIVILEGE_NOT_REVOKED = "01006";
   public static final String SQL_STATE_NO_DATA = "02000";
   public static final String SQL_STATE_WRONG_NO_OF_PARAMETERS = "07001";
   public static final String SQL_STATE_UNABLE_TO_CONNECT_TO_DATASOURCE = "08001";
   public static final String SQL_STATE_CONNECTION_IN_USE = "08002";
   public static final String SQL_STATE_CONNECTION_NOT_OPEN = "08003";
   public static final String SQL_STATE_CONNECTION_REJECTED = "08004";
   public static final String SQL_STATE_CONNECTION_FAILURE = "08006";
   public static final String SQL_STATE_TRANSACTION_RESOLUTION_UNKNOWN = "08007";
   public static final String SQL_STATE_COMMUNICATION_LINK_FAILURE = "08S01";
   public static final String SQL_STATE_FEATURE_NOT_SUPPORTED = "0A000";
   public static final String SQL_STATE_CARDINALITY_VIOLATION = "21000";
   public static final String SQL_STATE_INSERT_VALUE_LIST_NO_MATCH_COL_LIST = "21S01";
   public static final String SQL_STATE_STRING_DATA_RIGHT_TRUNCATION = "22001";
   public static final String SQL_STATE_NUMERIC_VALUE_OUT_OF_RANGE = "22003";
   public static final String SQL_STATE_INVALID_DATETIME_FORMAT = "22007";
   public static final String SQL_STATE_DATETIME_FIELD_OVERFLOW = "22008";
   public static final String SQL_STATE_DIVISION_BY_ZERO = "22012";
   public static final String SQL_STATE_INVALID_CHARACTER_VALUE_FOR_CAST = "22018";
   public static final String SQL_STATE_INTEGRITY_CONSTRAINT_VIOLATION = "23000";
   public static final String SQL_STATE_INVALID_CURSOR_STATE = "24000";
   public static final String SQL_STATE_INVALID_TRANSACTION_STATE = "25000";
   public static final String SQL_STATE_INVALID_AUTH_SPEC = "28000";
   public static final String SQL_STATE_INVALID_TRANSACTION_TERMINATION = "2D000";
   public static final String SQL_STATE_INVALID_CONDITION_NUMBER = "35000";
   public static final String SQL_STATE_INVALID_CATALOG_NAME = "3D000";
   public static final String SQL_STATE_ROLLBACK_SERIALIZATION_FAILURE = "40001";
   public static final String SQL_STATE_SYNTAX_ERROR = "42000";
   public static final String SQL_STATE_ER_TABLE_EXISTS_ERROR = "42S01";
   public static final String SQL_STATE_BASE_TABLE_OR_VIEW_NOT_FOUND = "42S02";
   public static final String SQL_STATE_ER_NO_SUCH_INDEX = "42S12";
   public static final String SQL_STATE_ER_DUP_FIELDNAME = "42S21";
   public static final String SQL_STATE_ER_BAD_FIELD_ERROR = "42S22";
   public static final String SQL_STATE_INVALID_CONNECTION_ATTRIBUTE = "01S00";
   public static final String SQL_STATE_ERROR_IN_ROW = "01S01";
   public static final String SQL_STATE_NO_ROWS_UPDATED_OR_DELETED = "01S03";
   public static final String SQL_STATE_MORE_THAN_ONE_ROW_UPDATED_OR_DELETED = "01S04";
   public static final String SQL_STATE_RESIGNAL_WHEN_HANDLER_NOT_ACTIVE = "0K000";
   public static final String SQL_STATE_STACKED_DIAGNOSTICS_ACCESSED_WITHOUT_ACTIVE_HANDLER = "0Z002";
   public static final String SQL_STATE_CASE_NOT_FOUND_FOR_CASE_STATEMENT = "20000";
   public static final String SQL_STATE_NULL_VALUE_NOT_ALLOWED = "22004";
   public static final String SQL_STATE_INVALID_LOGARITHM_ARGUMENT = "2201E";
   public static final String SQL_STATE_ACTIVE_SQL_TRANSACTION = "25001";
   public static final String SQL_STATE_READ_ONLY_SQL_TRANSACTION = "25006";
   public static final String SQL_STATE_SRE_PROHIBITED_SQL_STATEMENT_ATTEMPTED = "2F003";
   public static final String SQL_STATE_SRE_FUNCTION_EXECUTED_NO_RETURN_STATEMENT = "2F005";
   public static final String SQL_STATE_ER_QUERY_INTERRUPTED = "70100";
   public static final String SQL_STATE_BASE_TABLE_OR_VIEW_ALREADY_EXISTS = "S0001";
   public static final String SQL_STATE_BASE_TABLE_NOT_FOUND = "S0002";
   public static final String SQL_STATE_INDEX_ALREADY_EXISTS = "S0011";
   public static final String SQL_STATE_INDEX_NOT_FOUND = "S0012";
   public static final String SQL_STATE_COLUMN_ALREADY_EXISTS = "S0021";
   public static final String SQL_STATE_COLUMN_NOT_FOUND = "S0022";
   public static final String SQL_STATE_NO_DEFAULT_FOR_COLUMN = "S0023";
   public static final String SQL_STATE_GENERAL_ERROR = "S1000";
   public static final String SQL_STATE_MEMORY_ALLOCATION_FAILURE = "S1001";
   public static final String SQL_STATE_INVALID_COLUMN_NUMBER = "S1002";
   public static final String SQL_STATE_ILLEGAL_ARGUMENT = "S1009";
   public static final String SQL_STATE_DRIVER_NOT_CAPABLE = "S1C00";
   public static final String SQL_STATE_TIMEOUT_EXPIRED = "S1T00";
   public static final String SQL_STATE_CLI_SPECIFIC_CONDITION = "HY000";
   public static final String SQL_STATE_MEMORY_ALLOCATION_ERROR = "HY001";
   public static final String SQL_STATE_XA_RBROLLBACK = "XA100";
   public static final String SQL_STATE_XA_RBDEADLOCK = "XA102";
   public static final String SQL_STATE_XA_RBTIMEOUT = "XA106";
   public static final String SQL_STATE_XA_RMERR = "XAE03";
   public static final String SQL_STATE_XAER_NOTA = "XAE04";
   public static final String SQL_STATE_XAER_INVAL = "XAE05";
   public static final String SQL_STATE_XAER_RMFAIL = "XAE07";
   public static final String SQL_STATE_XAER_DUPID = "XAE08";
   public static final String SQL_STATE_XAER_OUTSIDE = "XAE09";
   private static Map<String, String> sqlStateMessages = new HashMap();

   public static void dumpSqlStatesMappingsAsXml() throws Exception {
      TreeMap<Integer, Integer> allErrorNumbers = new TreeMap();
      Map<Object, String> mysqlErrorNumbersToNames = new HashMap();
      Iterator var2 = mysqlToSql99State.keySet().iterator();

      while(var2.hasNext()) {
         Integer errorNumber = (Integer)var2.next();
         allErrorNumbers.put(errorNumber, errorNumber);
      }

      Field[] possibleFields = MysqlErrorNumbers.class.getDeclaredFields();

      for(int i = 0; i < possibleFields.length; ++i) {
         String fieldName = possibleFields[i].getName();
         if (fieldName.startsWith("ER_")) {
            mysqlErrorNumbersToNames.put(possibleFields[i].get((Object)null), fieldName);
         }
      }

      System.out.println("<ErrorMappings>");
      Iterator var8 = allErrorNumbers.keySet().iterator();

      while(var8.hasNext()) {
         Integer errorNumber = (Integer)var8.next();
         String sql92State = mysqlToSql99(errorNumber);
         System.out.println("   <ErrorMapping mysqlErrorNumber=\"" + errorNumber + "\" mysqlErrorName=\"" + (String)mysqlErrorNumbersToNames.get(errorNumber) + "\" legacySqlState=\"" + "" + "\" sql92SqlState=\"" + (sql92State == null ? "" : sql92State) + "\"/>");
      }

      System.out.println("</ErrorMappings>");
   }

   public static String get(String stateCode) {
      return (String)sqlStateMessages.get(stateCode);
   }

   private static String mysqlToSql99(int errno) {
      Integer err = errno;
      return mysqlToSql99State.containsKey(err) ? (String)mysqlToSql99State.get(err) : "HY000";
   }

   public static String mysqlToSqlState(int errno) {
      return mysqlToSql99(errno);
   }

   public static SQLException createSQLException(String message, String sqlState, ExceptionInterceptor interceptor) {
      return createSQLException(message, sqlState, 0, interceptor);
   }

   public static SQLException createSQLException(String message, ExceptionInterceptor interceptor) {
      SQLException sqlEx = new SQLException(message);
      return runThroughExceptionInterceptor(interceptor, sqlEx);
   }

   public static SQLException createSQLException(String message, String sqlState, Throwable cause, ExceptionInterceptor interceptor) {
      SQLException sqlEx = createSQLException(message, sqlState, (ExceptionInterceptor)null);
      if (sqlEx.getCause() == null && cause != null) {
         try {
            sqlEx.initCause(cause);
         } catch (Throwable var6) {
         }
      }

      return runThroughExceptionInterceptor(interceptor, sqlEx);
   }

   public static SQLException createSQLException(String message, String sqlState, int vendorErrorCode, ExceptionInterceptor interceptor) {
      return createSQLException(message, sqlState, vendorErrorCode, false, interceptor);
   }

   public static SQLException createSQLException(String message, String sqlState, int vendorErrorCode, Throwable cause, ExceptionInterceptor interceptor) {
      return createSQLException(message, sqlState, vendorErrorCode, false, cause, interceptor);
   }

   public static SQLException createSQLException(String message, String sqlState, int vendorErrorCode, boolean isTransient, ExceptionInterceptor interceptor) {
      return createSQLException(message, sqlState, vendorErrorCode, isTransient, (Throwable)null, interceptor);
   }

   public static SQLException createSQLException(String message, String sqlState, int vendorErrorCode, boolean isTransient, Throwable cause, ExceptionInterceptor interceptor) {
      try {
         SQLException sqlEx = null;
         if (sqlState != null) {
            if (sqlState.startsWith("08")) {
               if (isTransient) {
                  sqlEx = new SQLTransientConnectionException(message, sqlState, vendorErrorCode);
               } else {
                  sqlEx = new SQLNonTransientConnectionException(message, sqlState, vendorErrorCode);
               }
            } else if (sqlState.startsWith("22")) {
               sqlEx = new SQLDataException(message, sqlState, vendorErrorCode);
            } else if (sqlState.startsWith("23")) {
               sqlEx = new SQLIntegrityConstraintViolationException(message, sqlState, vendorErrorCode);
            } else if (sqlState.startsWith("42")) {
               sqlEx = new SQLSyntaxErrorException(message, sqlState, vendorErrorCode);
            } else if (sqlState.startsWith("40")) {
               sqlEx = new MySQLTransactionRollbackException(message, sqlState, vendorErrorCode);
            } else if (sqlState.startsWith("70100")) {
               sqlEx = new MySQLQueryInterruptedException(message, sqlState, vendorErrorCode);
            } else {
               sqlEx = new SQLException(message, sqlState, vendorErrorCode);
            }
         } else {
            sqlEx = new SQLException(message, sqlState, vendorErrorCode);
         }

         if (cause != null) {
            try {
               ((SQLException)sqlEx).initCause(cause);
            } catch (Throwable var8) {
            }
         }

         return runThroughExceptionInterceptor(interceptor, (SQLException)sqlEx);
      } catch (Exception var9) {
         SQLException unexpectedEx = new SQLException("Unable to create correct SQLException class instance, error class/codes may be incorrect. Reason: " + Util.stackTraceToString(var9), "S1000");
         return runThroughExceptionInterceptor(interceptor, unexpectedEx);
      }
   }

   public static SQLException createCommunicationsException(JdbcConnection conn, long lastPacketSentTimeMs, long lastPacketReceivedTimeMs, Exception underlyingException, ExceptionInterceptor interceptor) {
      SQLException exToReturn = new CommunicationsException(conn, lastPacketSentTimeMs, lastPacketReceivedTimeMs, underlyingException);
      if (underlyingException != null) {
         try {
            exToReturn.initCause(underlyingException);
         } catch (Throwable var9) {
         }
      }

      return runThroughExceptionInterceptor(interceptor, exToReturn);
   }

   public static SQLException createCommunicationsException(String message, Throwable underlyingException, ExceptionInterceptor interceptor) {
      SQLException exToReturn = null;
      exToReturn = new CommunicationsException(message, underlyingException);
      if (underlyingException != null) {
         try {
            exToReturn.initCause(underlyingException);
         } catch (Throwable var5) {
         }
      }

      return runThroughExceptionInterceptor(interceptor, exToReturn);
   }

   public static NotUpdatable notUpdatable() {
      return new NotUpdatable();
   }

   private static SQLException runThroughExceptionInterceptor(ExceptionInterceptor exInterceptor, SQLException sqlEx) {
      if (exInterceptor != null) {
         SQLException interceptedEx = (SQLException)exInterceptor.interceptException(sqlEx);
         if (interceptedEx != null) {
            return interceptedEx;
         }
      }

      return sqlEx;
   }

   public static SQLException createBatchUpdateException(SQLException underlyingEx, long[] updateCounts, ExceptionInterceptor interceptor) throws SQLException {
      SQLException newEx = (SQLException)Util.getInstance("java.sql.BatchUpdateException", new Class[]{String.class, String.class, Integer.TYPE, long[].class, Throwable.class}, new Object[]{underlyingEx.getMessage(), underlyingEx.getSQLState(), underlyingEx.getErrorCode(), updateCounts, underlyingEx}, interceptor);
      return runThroughExceptionInterceptor(interceptor, newEx);
   }

   public static SQLException createSQLFeatureNotSupportedException() {
      return new SQLFeatureNotSupportedException();
   }

   public static SQLException createSQLFeatureNotSupportedException(String message, String sqlState, ExceptionInterceptor interceptor) throws SQLException {
      SQLException newEx = new SQLFeatureNotSupportedException(message, sqlState);
      return runThroughExceptionInterceptor(interceptor, newEx);
   }

   static {
      sqlStateMessages.put("01002", Messages.getString("SQLError.35"));
      sqlStateMessages.put("01004", Messages.getString("SQLError.36"));
      sqlStateMessages.put("01006", Messages.getString("SQLError.37"));
      sqlStateMessages.put("01S00", Messages.getString("SQLError.38"));
      sqlStateMessages.put("01S01", Messages.getString("SQLError.39"));
      sqlStateMessages.put("01S03", Messages.getString("SQLError.40"));
      sqlStateMessages.put("01S04", Messages.getString("SQLError.41"));
      sqlStateMessages.put("07001", Messages.getString("SQLError.42"));
      sqlStateMessages.put("08001", Messages.getString("SQLError.43"));
      sqlStateMessages.put("08002", Messages.getString("SQLError.44"));
      sqlStateMessages.put("08003", Messages.getString("SQLError.45"));
      sqlStateMessages.put("08004", Messages.getString("SQLError.46"));
      sqlStateMessages.put("08007", Messages.getString("SQLError.47"));
      sqlStateMessages.put("08S01", Messages.getString("SQLError.48"));
      sqlStateMessages.put("21S01", Messages.getString("SQLError.49"));
      sqlStateMessages.put("22003", Messages.getString("SQLError.50"));
      sqlStateMessages.put("22008", Messages.getString("SQLError.51"));
      sqlStateMessages.put("22012", Messages.getString("SQLError.52"));
      sqlStateMessages.put("40001", Messages.getString("SQLError.53"));
      sqlStateMessages.put("28000", Messages.getString("SQLError.54"));
      sqlStateMessages.put("42000", Messages.getString("SQLError.55"));
      sqlStateMessages.put("42S02", Messages.getString("SQLError.56"));
      sqlStateMessages.put("S0001", Messages.getString("SQLError.57"));
      sqlStateMessages.put("S0002", Messages.getString("SQLError.58"));
      sqlStateMessages.put("S0011", Messages.getString("SQLError.59"));
      sqlStateMessages.put("S0012", Messages.getString("SQLError.60"));
      sqlStateMessages.put("S0021", Messages.getString("SQLError.61"));
      sqlStateMessages.put("S0022", Messages.getString("SQLError.62"));
      sqlStateMessages.put("S0023", Messages.getString("SQLError.63"));
      sqlStateMessages.put("S1000", Messages.getString("SQLError.64"));
      sqlStateMessages.put("S1001", Messages.getString("SQLError.65"));
      sqlStateMessages.put("S1002", Messages.getString("SQLError.66"));
      sqlStateMessages.put("S1009", Messages.getString("SQLError.67"));
      sqlStateMessages.put("S1C00", Messages.getString("SQLError.68"));
      sqlStateMessages.put("S1T00", Messages.getString("SQLError.69"));
      mysqlToSql99State = new HashMap();
      mysqlToSql99State.put(1249, "01000");
      mysqlToSql99State.put(1261, "01000");
      mysqlToSql99State.put(1262, "01000");
      mysqlToSql99State.put(1265, "01000");
      mysqlToSql99State.put(1263, "22004");
      mysqlToSql99State.put(1264, "22003");
      mysqlToSql99State.put(1311, "01000");
      mysqlToSql99State.put(1642, "01000");
      mysqlToSql99State.put(1329, "02000");
      mysqlToSql99State.put(1643, "02000");
      mysqlToSql99State.put(1040, "08004");
      mysqlToSql99State.put(1251, "08004");
      mysqlToSql99State.put(1042, "08S01");
      mysqlToSql99State.put(1043, "08S01");
      mysqlToSql99State.put(1047, "08S01");
      mysqlToSql99State.put(1053, "08S01");
      mysqlToSql99State.put(1080, "08S01");
      mysqlToSql99State.put(1081, "08S01");
      mysqlToSql99State.put(1152, "08S01");
      mysqlToSql99State.put(1153, "08S01");
      mysqlToSql99State.put(1154, "08S01");
      mysqlToSql99State.put(1155, "08S01");
      mysqlToSql99State.put(1156, "08S01");
      mysqlToSql99State.put(1157, "08S01");
      mysqlToSql99State.put(1158, "08S01");
      mysqlToSql99State.put(1159, "08S01");
      mysqlToSql99State.put(1160, "08S01");
      mysqlToSql99State.put(1161, "08S01");
      mysqlToSql99State.put(1184, "08S01");
      mysqlToSql99State.put(1189, "08S01");
      mysqlToSql99State.put(1190, "08S01");
      mysqlToSql99State.put(1218, "08S01");
      mysqlToSql99State.put(1312, "0A000");
      mysqlToSql99State.put(1314, "0A000");
      mysqlToSql99State.put(1335, "0A000");
      mysqlToSql99State.put(1336, "0A000");
      mysqlToSql99State.put(1415, "0A000");
      mysqlToSql99State.put(1845, "0A000");
      mysqlToSql99State.put(1846, "0A000");
      mysqlToSql99State.put(1044, "42000");
      mysqlToSql99State.put(1049, "42000");
      mysqlToSql99State.put(1055, "42000");
      mysqlToSql99State.put(1056, "42000");
      mysqlToSql99State.put(1057, "42000");
      mysqlToSql99State.put(1059, "42000");
      mysqlToSql99State.put(1061, "42000");
      mysqlToSql99State.put(1063, "42000");
      mysqlToSql99State.put(1064, "42000");
      mysqlToSql99State.put(1065, "42000");
      mysqlToSql99State.put(1066, "42000");
      mysqlToSql99State.put(1067, "42000");
      mysqlToSql99State.put(1068, "42000");
      mysqlToSql99State.put(1069, "42000");
      mysqlToSql99State.put(1070, "42000");
      mysqlToSql99State.put(1071, "42000");
      mysqlToSql99State.put(1072, "42000");
      mysqlToSql99State.put(1073, "42000");
      mysqlToSql99State.put(1074, "42000");
      mysqlToSql99State.put(1075, "42000");
      mysqlToSql99State.put(1083, "42000");
      mysqlToSql99State.put(1084, "42000");
      mysqlToSql99State.put(1090, "42000");
      mysqlToSql99State.put(1091, "42000");
      mysqlToSql99State.put(1101, "42000");
      mysqlToSql99State.put(1102, "42000");
      mysqlToSql99State.put(1103, "42000");
      mysqlToSql99State.put(1104, "42000");
      mysqlToSql99State.put(1106, "42000");
      mysqlToSql99State.put(1107, "42000");
      mysqlToSql99State.put(1110, "42000");
      mysqlToSql99State.put(1112, "42000");
      mysqlToSql99State.put(1113, "42000");
      mysqlToSql99State.put(1115, "42000");
      mysqlToSql99State.put(1118, "42000");
      mysqlToSql99State.put(1120, "42000");
      mysqlToSql99State.put(1121, "42000");
      mysqlToSql99State.put(1131, "42000");
      mysqlToSql99State.put(1132, "42000");
      mysqlToSql99State.put(1133, "42000");
      mysqlToSql99State.put(1139, "42000");
      mysqlToSql99State.put(1140, "42000");
      mysqlToSql99State.put(1141, "42000");
      mysqlToSql99State.put(1142, "42000");
      mysqlToSql99State.put(1143, "42000");
      mysqlToSql99State.put(1144, "42000");
      mysqlToSql99State.put(1145, "42000");
      mysqlToSql99State.put(1147, "42000");
      mysqlToSql99State.put(1148, "42000");
      mysqlToSql99State.put(1149, "42000");
      mysqlToSql99State.put(1162, "42000");
      mysqlToSql99State.put(1163, "42000");
      mysqlToSql99State.put(1164, "42000");
      mysqlToSql99State.put(1166, "42000");
      mysqlToSql99State.put(1167, "42000");
      mysqlToSql99State.put(1170, "42000");
      mysqlToSql99State.put(1171, "42000");
      mysqlToSql99State.put(1172, "42000");
      mysqlToSql99State.put(1173, "42000");
      mysqlToSql99State.put(1176, "42000");
      mysqlToSql99State.put(1177, "42000");
      mysqlToSql99State.put(1178, "42000");
      mysqlToSql99State.put(1203, "42000");
      mysqlToSql99State.put(1211, "42000");
      mysqlToSql99State.put(1226, "42000");
      mysqlToSql99State.put(1227, "42000");
      mysqlToSql99State.put(1230, "42000");
      mysqlToSql99State.put(1231, "42000");
      mysqlToSql99State.put(1232, "42000");
      mysqlToSql99State.put(1234, "42000");
      mysqlToSql99State.put(1235, "42000");
      mysqlToSql99State.put(1239, "42000");
      mysqlToSql99State.put(1248, "42000");
      mysqlToSql99State.put(1250, "42000");
      mysqlToSql99State.put(1252, "42000");
      mysqlToSql99State.put(1253, "42000");
      mysqlToSql99State.put(1280, "42000");
      mysqlToSql99State.put(1281, "42000");
      mysqlToSql99State.put(1286, "42000");
      mysqlToSql99State.put(1304, "42000");
      mysqlToSql99State.put(1305, "42000");
      mysqlToSql99State.put(1308, "42000");
      mysqlToSql99State.put(1309, "42000");
      mysqlToSql99State.put(1310, "42000");
      mysqlToSql99State.put(1313, "42000");
      mysqlToSql99State.put(1315, "42000");
      mysqlToSql99State.put(1316, "42000");
      mysqlToSql99State.put(1318, "42000");
      mysqlToSql99State.put(1319, "42000");
      mysqlToSql99State.put(1320, "42000");
      mysqlToSql99State.put(1322, "42000");
      mysqlToSql99State.put(1323, "42000");
      mysqlToSql99State.put(1324, "42000");
      mysqlToSql99State.put(1327, "42000");
      mysqlToSql99State.put(1330, "42000");
      mysqlToSql99State.put(1331, "42000");
      mysqlToSql99State.put(1332, "42000");
      mysqlToSql99State.put(1333, "42000");
      mysqlToSql99State.put(1337, "42000");
      mysqlToSql99State.put(1338, "42000");
      mysqlToSql99State.put(1370, "42000");
      mysqlToSql99State.put(1403, "42000");
      mysqlToSql99State.put(1407, "42000");
      mysqlToSql99State.put(1410, "42000");
      mysqlToSql99State.put(1413, "42000");
      mysqlToSql99State.put(1414, "42000");
      mysqlToSql99State.put(1425, "42000");
      mysqlToSql99State.put(1426, "42000");
      mysqlToSql99State.put(1427, "42000");
      mysqlToSql99State.put(1437, "42000");
      mysqlToSql99State.put(1439, "42000");
      mysqlToSql99State.put(1453, "42000");
      mysqlToSql99State.put(1458, "42000");
      mysqlToSql99State.put(1460, "42000");
      mysqlToSql99State.put(1461, "42000");
      mysqlToSql99State.put(1463, "42000");
      mysqlToSql99State.put(1582, "42000");
      mysqlToSql99State.put(1583, "42000");
      mysqlToSql99State.put(1584, "42000");
      mysqlToSql99State.put(1630, "42000");
      mysqlToSql99State.put(1641, "42000");
      mysqlToSql99State.put(1687, "42000");
      mysqlToSql99State.put(1701, "42000");
      mysqlToSql99State.put(1222, "21000");
      mysqlToSql99State.put(1241, "21000");
      mysqlToSql99State.put(1242, "21000");
      mysqlToSql99State.put(1022, "23000");
      mysqlToSql99State.put(1048, "23000");
      mysqlToSql99State.put(1052, "23000");
      mysqlToSql99State.put(1062, "23000");
      mysqlToSql99State.put(1169, "23000");
      mysqlToSql99State.put(1216, "23000");
      mysqlToSql99State.put(1217, "23000");
      mysqlToSql99State.put(1451, "23000");
      mysqlToSql99State.put(1452, "23000");
      mysqlToSql99State.put(1557, "23000");
      mysqlToSql99State.put(1586, "23000");
      mysqlToSql99State.put(1761, "23000");
      mysqlToSql99State.put(1762, "23000");
      mysqlToSql99State.put(1859, "23000");
      mysqlToSql99State.put(1406, "22001");
      mysqlToSql99State.put(1416, "22003");
      mysqlToSql99State.put(1690, "22003");
      mysqlToSql99State.put(1292, "22007");
      mysqlToSql99State.put(1367, "22007");
      mysqlToSql99State.put(1441, "22008");
      mysqlToSql99State.put(1365, "22012");
      mysqlToSql99State.put(1325, "24000");
      mysqlToSql99State.put(1326, "24000");
      mysqlToSql99State.put(1179, "25000");
      mysqlToSql99State.put(1207, "25000");
      mysqlToSql99State.put(1045, "28000");
      mysqlToSql99State.put(1698, "28000");
      mysqlToSql99State.put(1873, "28000");
      mysqlToSql99State.put(1758, "35000");
      mysqlToSql99State.put(1046, "3D000");
      mysqlToSql99State.put(1645, "0K000");
      mysqlToSql99State.put(1887, "0Z002");
      mysqlToSql99State.put(1339, "20000");
      mysqlToSql99State.put(1058, "21S01");
      mysqlToSql99State.put(1136, "21S01");
      mysqlToSql99State.put(1138, "22004");
      mysqlToSql99State.put(1903, "2201E");
      mysqlToSql99State.put(1568, "25001");
      mysqlToSql99State.put(1792, "25006");
      mysqlToSql99State.put(1303, "2F003");
      mysqlToSql99State.put(1321, "2F005");
      mysqlToSql99State.put(1050, "42S01");
      mysqlToSql99State.put(1051, "42S02");
      mysqlToSql99State.put(1109, "42S02");
      mysqlToSql99State.put(1146, "42S02");
      mysqlToSql99State.put(1082, "42S12");
      mysqlToSql99State.put(1060, "42S21");
      mysqlToSql99State.put(1054, "42S22");
      mysqlToSql99State.put(1247, "42S22");
      mysqlToSql99State.put(1317, "70100");
      mysqlToSql99State.put(1037, "HY001");
      mysqlToSql99State.put(1038, "HY001");
      mysqlToSql99State.put(1402, "XA100");
      mysqlToSql99State.put(1614, "XA102");
      mysqlToSql99State.put(1613, "XA106");
      mysqlToSql99State.put(1401, "XAE03");
      mysqlToSql99State.put(1397, "XAE04");
      mysqlToSql99State.put(1398, "XAE05");
      mysqlToSql99State.put(1399, "XAE07");
      mysqlToSql99State.put(1440, "XAE08");
      mysqlToSql99State.put(1400, "XAE09");
      mysqlToSql99State.put(1205, "40001");
      mysqlToSql99State.put(1213, "40001");
   }
}
