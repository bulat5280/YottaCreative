package com.mysql.cj.jdbc.interceptors;

import com.mysql.cj.api.MysqlConnection;
import com.mysql.cj.api.jdbc.Statement;
import com.mysql.cj.api.jdbc.interceptors.StatementInterceptor;
import com.mysql.cj.api.log.Log;
import com.mysql.cj.api.mysqla.result.Resultset;
import com.mysql.cj.core.Messages;
import java.sql.SQLException;
import java.util.Properties;

public class NoSubInterceptorWrapper implements StatementInterceptor {
   private final StatementInterceptor underlyingInterceptor;

   public NoSubInterceptorWrapper(StatementInterceptor underlyingInterceptor) {
      if (underlyingInterceptor == null) {
         throw new RuntimeException(Messages.getString("NoSubInterceptorWrapper.0"));
      } else {
         this.underlyingInterceptor = underlyingInterceptor;
      }
   }

   public void destroy() {
      this.underlyingInterceptor.destroy();
   }

   public boolean executeTopLevelOnly() {
      return this.underlyingInterceptor.executeTopLevelOnly();
   }

   public StatementInterceptor init(MysqlConnection conn, Properties props, Log log) {
      this.underlyingInterceptor.init(conn, props, log);
      return this;
   }

   public <T extends Resultset> T postProcess(String sql, Statement interceptedStatement, T originalResultSet, int warningCount, boolean noIndexUsed, boolean noGoodIndexUsed, Exception statementException) throws SQLException {
      this.underlyingInterceptor.postProcess(sql, interceptedStatement, originalResultSet, warningCount, noIndexUsed, noGoodIndexUsed, statementException);
      return null;
   }

   public <T extends Resultset> T preProcess(String sql, Statement interceptedStatement) throws SQLException {
      this.underlyingInterceptor.preProcess(sql, interceptedStatement);
      return null;
   }

   public StatementInterceptor getUnderlyingInterceptor() {
      return this.underlyingInterceptor;
   }
}
