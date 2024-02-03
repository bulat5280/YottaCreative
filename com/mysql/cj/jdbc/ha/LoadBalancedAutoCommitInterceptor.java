package com.mysql.cj.jdbc.ha;

import com.mysql.cj.api.MysqlConnection;
import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.api.jdbc.Statement;
import com.mysql.cj.api.jdbc.interceptors.StatementInterceptor;
import com.mysql.cj.api.log.Log;
import com.mysql.cj.api.mysqla.result.Resultset;
import java.sql.SQLException;
import java.util.Properties;

public class LoadBalancedAutoCommitInterceptor implements StatementInterceptor {
   private int matchingAfterStatementCount = 0;
   private int matchingAfterStatementThreshold = 0;
   private String matchingAfterStatementRegex;
   private JdbcConnection conn;
   private LoadBalancedConnectionProxy proxy = null;

   public void destroy() {
   }

   public boolean executeTopLevelOnly() {
      return false;
   }

   public StatementInterceptor init(MysqlConnection connection, Properties props, Log log) {
      this.conn = (JdbcConnection)connection;
      String autoCommitSwapThresholdAsString = props.getProperty("loadBalanceAutoCommitStatementThreshold", "0");

      try {
         this.matchingAfterStatementThreshold = Integer.parseInt(autoCommitSwapThresholdAsString);
      } catch (NumberFormatException var6) {
      }

      String autoCommitSwapRegex = props.getProperty("loadBalanceAutoCommitStatementRegex", "");
      if (!"".equals(autoCommitSwapRegex)) {
         this.matchingAfterStatementRegex = autoCommitSwapRegex;
      }

      return this;
   }

   public <T extends Resultset> T postProcess(String sql, Statement interceptedStatement, T originalResultSet, int warningCount, boolean noIndexUsed, boolean noGoodIndexUsed, Exception statementException) throws SQLException {
      if (!this.conn.getAutoCommit()) {
         this.matchingAfterStatementCount = 0;
      } else {
         if (this.proxy == null && this.conn.isProxySet()) {
            JdbcConnection lcl_proxy;
            for(lcl_proxy = this.conn.getMultiHostSafeProxy(); lcl_proxy != null && !(lcl_proxy instanceof LoadBalancedMySQLConnection); lcl_proxy = lcl_proxy.getMultiHostSafeProxy()) {
            }

            if (lcl_proxy != null) {
               this.proxy = ((LoadBalancedMySQLConnection)lcl_proxy).getThisAsProxy();
            }
         }

         if (this.proxy != null && (this.matchingAfterStatementRegex == null || sql.matches(this.matchingAfterStatementRegex))) {
            ++this.matchingAfterStatementCount;
         }

         if (this.matchingAfterStatementCount >= this.matchingAfterStatementThreshold) {
            this.matchingAfterStatementCount = 0;

            try {
               if (this.proxy != null) {
                  this.proxy.pickNewConnection();
               }
            } catch (SQLException var9) {
            }
         }
      }

      return originalResultSet;
   }

   public <T extends Resultset> T preProcess(String sql, Statement interceptedStatement) throws SQLException {
      return null;
   }
}
