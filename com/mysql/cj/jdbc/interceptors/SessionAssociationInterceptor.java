package com.mysql.cj.jdbc.interceptors;

import com.mysql.cj.api.MysqlConnection;
import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.api.jdbc.Statement;
import com.mysql.cj.api.jdbc.interceptors.StatementInterceptor;
import com.mysql.cj.api.log.Log;
import com.mysql.cj.api.mysqla.result.Resultset;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class SessionAssociationInterceptor implements StatementInterceptor {
   protected String currentSessionKey;
   protected static final ThreadLocal<String> sessionLocal = new ThreadLocal();
   private JdbcConnection connection;

   public static final void setSessionKey(String key) {
      sessionLocal.set(key);
   }

   public static final void resetSessionKey() {
      sessionLocal.set((Object)null);
   }

   public static final String getSessionKey() {
      return (String)sessionLocal.get();
   }

   public boolean executeTopLevelOnly() {
      return true;
   }

   public StatementInterceptor init(MysqlConnection conn, Properties props, Log log) {
      this.connection = (JdbcConnection)conn;
      return this;
   }

   public <T extends Resultset> T postProcess(String sql, Statement interceptedStatement, T originalResultSet, int warningCount, boolean noIndexUsed, boolean noGoodIndexUsed, Exception statementException) throws SQLException {
      return null;
   }

   public <T extends Resultset> T preProcess(String sql, Statement interceptedStatement) throws SQLException {
      String key = getSessionKey();
      if (key != null && !key.equals(this.currentSessionKey)) {
         PreparedStatement pstmt = this.connection.clientPrepareStatement("SET @mysql_proxy_session=?");

         try {
            pstmt.setString(1, key);
            pstmt.execute();
         } finally {
            pstmt.close();
         }

         this.currentSessionKey = key;
      }

      return null;
   }

   public void destroy() {
   }
}
