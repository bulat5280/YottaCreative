package com.mysql.cj.jdbc.interceptors;

import com.mysql.cj.api.MysqlConnection;
import com.mysql.cj.api.jdbc.Statement;
import com.mysql.cj.api.jdbc.interceptors.StatementInterceptor;
import com.mysql.cj.api.log.Log;
import com.mysql.cj.api.mysqla.result.Resultset;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultSetScannerInterceptor implements StatementInterceptor {
   protected Pattern regexP;

   public StatementInterceptor init(MysqlConnection conn, Properties props, Log log) {
      String regexFromUser = props.getProperty("resultSetScannerRegex");
      if (regexFromUser != null && regexFromUser.length() != 0) {
         try {
            this.regexP = Pattern.compile(regexFromUser);
            return this;
         } catch (Throwable var6) {
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ResultSetScannerInterceptor.1"), var6);
         }
      } else {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ResultSetScannerInterceptor.0"));
      }
   }

   public <T extends Resultset> T postProcess(String sql, Statement interceptedStatement, final T originalResultSet, int warningCount, boolean noIndexUsed, boolean noGoodIndexUsed, Exception statementException) throws SQLException {
      return (Resultset)Proxy.newProxyInstance(originalResultSet.getClass().getClassLoader(), new Class[]{Resultset.class}, new InvocationHandler() {
         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object invocationResult = method.invoke(originalResultSet, args);
            String methodName = method.getName();
            if (invocationResult != null && invocationResult instanceof String || "getString".equals(methodName) || "getObject".equals(methodName) || "getObjectStoredProc".equals(methodName)) {
               Matcher matcher = ResultSetScannerInterceptor.this.regexP.matcher(invocationResult.toString());
               if (matcher.matches()) {
                  throw new SQLException(Messages.getString("ResultSetScannerInterceptor.2"));
               }
            }

            return invocationResult;
         }
      });
   }

   public <T extends Resultset> T preProcess(String sql, Statement interceptedStatement) throws SQLException {
      return null;
   }

   public boolean executeTopLevelOnly() {
      return false;
   }

   public void destroy() {
   }
}
