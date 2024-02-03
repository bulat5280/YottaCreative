package com.mysql.cj.jdbc.interceptors;

import com.mysql.cj.api.MysqlConnection;
import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.api.jdbc.Statement;
import com.mysql.cj.api.jdbc.interceptors.StatementInterceptor;
import com.mysql.cj.api.log.Log;
import com.mysql.cj.api.mysqla.result.Resultset;
import com.mysql.cj.core.util.Util;
import com.mysql.cj.jdbc.util.ResultSetUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServerStatusDiffInterceptor implements StatementInterceptor {
   private Map<String, String> preExecuteValues = new HashMap();
   private Map<String, String> postExecuteValues = new HashMap();
   private JdbcConnection connection;
   private Log log;

   public StatementInterceptor init(MysqlConnection conn, Properties props, Log l) {
      this.connection = (JdbcConnection)conn;
      this.log = l;
      return this;
   }

   public <T extends Resultset> T postProcess(String sql, Statement interceptedStatement, T originalResultSet, int warningCount, boolean noIndexUsed, boolean noGoodIndexUsed, Exception statementException) throws SQLException {
      this.populateMapWithSessionStatusValues(this.postExecuteValues);
      this.log.logInfo("Server status change for statement:\n" + Util.calculateDifferences(this.preExecuteValues, this.postExecuteValues));
      return null;
   }

   private void populateMapWithSessionStatusValues(Map<String, String> toPopulate) throws SQLException {
      java.sql.Statement stmt = null;
      ResultSet rs = null;

      try {
         toPopulate.clear();
         stmt = this.connection.createStatement();
         rs = stmt.executeQuery("SHOW SESSION STATUS");
         ResultSetUtil.resultSetToMap(toPopulate, rs);
      } finally {
         if (rs != null) {
            rs.close();
         }

         if (stmt != null) {
            stmt.close();
         }

      }

   }

   public <T extends Resultset> T preProcess(String sql, Statement interceptedStatement) throws SQLException {
      this.populateMapWithSessionStatusValues(this.preExecuteValues);
      return null;
   }

   public boolean executeTopLevelOnly() {
      return true;
   }

   public void destroy() {
   }
}
