package com.mysql.cj.jdbc.util;

import com.mysql.cj.api.MysqlConnection;
import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.util.Util;
import com.mysql.cj.jdbc.StatementImpl;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.jdbc.io.ResultSetFactory;
import com.mysql.cj.mysqla.MysqlaSession;
import com.mysql.cj.mysqla.io.MysqlaProtocol;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.sql.DataTruncation;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ResultSetUtil {
   public static StringBuilder appendResultSetSlashGStyle(StringBuilder appendTo, ResultSet rs) throws SQLException {
      ResultSetMetaData rsmd = rs.getMetaData();
      int numFields = rsmd.getColumnCount();
      int maxWidth = 0;
      String[] fieldNames = new String[numFields];

      int i;
      for(i = 0; i < numFields; ++i) {
         fieldNames[i] = rsmd.getColumnLabel(i + 1);
         if (fieldNames[i].length() > maxWidth) {
            maxWidth = fieldNames[i].length();
         }
      }

      i = 1;

      while(rs.next()) {
         appendTo.append("*************************** ");
         appendTo.append(i++);
         appendTo.append(". row ***************************\n");

         for(int i = 0; i < numFields; ++i) {
            int leftPad = maxWidth - fieldNames[i].length();

            for(int j = 0; j < leftPad; ++j) {
               appendTo.append(" ");
            }

            appendTo.append(fieldNames[i]);
            appendTo.append(": ");
            String stringVal = rs.getString(i + 1);
            if (stringVal != null) {
               appendTo.append(stringVal);
            } else {
               appendTo.append("NULL");
            }

            appendTo.append("\n");
         }

         appendTo.append("\n");
      }

      return appendTo;
   }

   public static void resultSetToMap(Map mappedValues, ResultSet rs) throws SQLException {
      while(rs.next()) {
         mappedValues.put(rs.getObject(1), rs.getObject(2));
      }

   }

   public static void resultSetToMap(Map mappedValues, ResultSet rs, int key, int value) throws SQLException {
      while(rs.next()) {
         mappedValues.put(rs.getObject(key), rs.getObject(value));
      }

   }

   public static Object readObject(ResultSet resultSet, int index) throws IOException, SQLException, ClassNotFoundException {
      ObjectInputStream objIn = new ObjectInputStream(resultSet.getBinaryStream(index));
      Object obj = objIn.readObject();
      objIn.close();
      return obj;
   }

   public static SQLWarning convertShowWarningsToSQLWarnings(MysqlConnection connection) throws SQLException {
      return convertShowWarningsToSQLWarnings(connection, 0, false);
   }

   public static SQLWarning convertShowWarningsToSQLWarnings(MysqlConnection connection, int warningCountIfKnown, boolean forTruncationOnly) throws SQLException {
      Statement stmt = null;
      ResultSet warnRs = null;
      Object currentWarning = null;
      boolean var17 = false;

      Object var23;
      try {
         var17 = true;
         if (warningCountIfKnown < 100) {
            stmt = ((JdbcConnection)connection).createStatement();
            if (stmt.getMaxRows() != 0) {
               stmt.setMaxRows(0);
            }
         } else {
            stmt = ((JdbcConnection)connection).createStatement(1003, 1007);
            stmt.setFetchSize(Integer.MIN_VALUE);
         }

         warnRs = stmt.executeQuery("SHOW WARNINGS");

         while(true) {
            if (!warnRs.next()) {
               if (forTruncationOnly && currentWarning != null) {
                  throw currentWarning;
               }

               var23 = currentWarning;
               var17 = false;
               break;
            }

            int code = warnRs.getInt("Code");
            if (forTruncationOnly) {
               if (code == 1265 || code == 1264) {
                  DataTruncation newTruncation = new MysqlDataTruncation(warnRs.getString("Message"), 0, false, false, 0, 0, code);
                  if (currentWarning == null) {
                     currentWarning = newTruncation;
                  } else {
                     ((SQLWarning)currentWarning).setNextWarning(newTruncation);
                  }
               }
            } else {
               String message = warnRs.getString("Message");
               SQLWarning newWarning = new SQLWarning(message, SQLError.mysqlToSqlState(code), code);
               if (currentWarning == null) {
                  currentWarning = newWarning;
               } else {
                  ((SQLWarning)currentWarning).setNextWarning(newWarning);
               }
            }
         }
      } finally {
         if (var17) {
            SQLException reThrow = null;
            if (warnRs != null) {
               try {
                  warnRs.close();
               } catch (SQLException var19) {
                  reThrow = var19;
               }
            }

            if (stmt != null) {
               try {
                  stmt.close();
               } catch (SQLException var18) {
                  reThrow = var18;
               }
            }

            if (reThrow != null) {
               throw reThrow;
            }

         }
      }

      SQLException reThrow = null;
      if (warnRs != null) {
         try {
            warnRs.close();
         } catch (SQLException var21) {
            reThrow = var21;
         }
      }

      if (stmt != null) {
         try {
            stmt.close();
         } catch (SQLException var20) {
            reThrow = var20;
         }
      }

      if (reThrow != null) {
         throw reThrow;
      } else {
         return (SQLWarning)var23;
      }
   }

   public static void appendDeadlockStatusInformation(MysqlConnection connection, String xOpen, StringBuilder errorBuf) {
      MysqlaSession session = (MysqlaSession)connection.getSession();
      MysqlaProtocol protocol = session.getProtocol();
      if ((Boolean)session.getPropertySet().getBooleanReadableProperty("includeInnodbStatusInDeadlockExceptions").getValue() && xOpen != null && (xOpen.startsWith("40") || xOpen.startsWith("41")) && protocol.getStreamingData() == null) {
         ResultSet rs = null;

         try {
            rs = (ResultSet)protocol.sqlQueryDirect((StatementImpl)null, "SHOW ENGINE INNODB STATUS", (String)session.getPropertySet().getStringReadableProperty("characterEncoding").getValue(), (PacketPayload)null, -1, false, ((JdbcConnection)connection).getCatalog(), (ColumnDefinition)null, session::getProfilerEventHandlerInstanceFunction, new ResultSetFactory((JdbcConnection)connection, (StatementImpl)null));
            if (rs.next()) {
               errorBuf.append("\n\n");
               errorBuf.append(rs.getString("Status"));
            } else {
               errorBuf.append("\n\n");
               errorBuf.append(Messages.getString("MysqlIO.NoInnoDBStatusFound"));
            }
         } catch (SQLException | CJException | IOException var20) {
            errorBuf.append("\n\n");
            errorBuf.append(Messages.getString("MysqlIO.InnoDBStatusFailed"));
            errorBuf.append("\n\n");
            errorBuf.append(Util.stackTraceToString(var20));
         } finally {
            if (rs != null) {
               try {
                  rs.close();
               } catch (SQLException var19) {
                  throw ExceptionFactory.createException((String)var19.getMessage(), (Throwable)var19);
               }
            }

         }
      }

      if ((Boolean)session.getPropertySet().getBooleanReadableProperty("includeThreadDumpInDeadlockExceptions").getValue()) {
         errorBuf.append("\n\n*** Java threads running at time of deadlock ***\n\n");
         ThreadMXBean threadMBean = ManagementFactory.getThreadMXBean();
         long[] threadIds = threadMBean.getAllThreadIds();
         ThreadInfo[] threads = threadMBean.getThreadInfo(threadIds, Integer.MAX_VALUE);
         List<ThreadInfo> activeThreads = new ArrayList();
         ThreadInfo[] var9 = threads;
         int var10 = threads.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            ThreadInfo info = var9[var11];
            if (info != null) {
               activeThreads.add(info);
            }
         }

         Iterator var23 = activeThreads.iterator();

         while(var23.hasNext()) {
            ThreadInfo threadInfo = (ThreadInfo)var23.next();
            errorBuf.append('"');
            errorBuf.append(threadInfo.getThreadName());
            errorBuf.append("\" tid=");
            errorBuf.append(threadInfo.getThreadId());
            errorBuf.append(" ");
            errorBuf.append(threadInfo.getThreadState());
            if (threadInfo.getLockName() != null) {
               errorBuf.append(" on lock=" + threadInfo.getLockName());
            }

            if (threadInfo.isSuspended()) {
               errorBuf.append(" (suspended)");
            }

            if (threadInfo.isInNative()) {
               errorBuf.append(" (running in native)");
            }

            StackTraceElement[] stackTrace = threadInfo.getStackTrace();
            if (stackTrace.length > 0) {
               errorBuf.append(" in ");
               errorBuf.append(stackTrace[0].getClassName());
               errorBuf.append(".");
               errorBuf.append(stackTrace[0].getMethodName());
               errorBuf.append("()");
            }

            errorBuf.append("\n");
            if (threadInfo.getLockOwnerName() != null) {
               errorBuf.append("\t owned by " + threadInfo.getLockOwnerName() + " Id=" + threadInfo.getLockOwnerId());
               errorBuf.append("\n");
            }

            for(int j = 0; j < stackTrace.length; ++j) {
               StackTraceElement ste = stackTrace[j];
               errorBuf.append("\tat " + ste.toString());
               errorBuf.append("\n");
            }
         }
      }

   }
}
