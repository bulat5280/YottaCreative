package com.mysql.cj.api.jdbc;

import com.mysql.cj.api.PingTarget;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.jdbc.result.ResultSetInternalMethods;
import java.io.InputStream;
import java.sql.SQLException;

public interface Statement extends java.sql.Statement {
   int MAX_ROWS = 50000000;
   byte OPEN_CURSOR_FLAG = 1;

   void enableStreamingResults() throws SQLException;

   void disableStreamingResults() throws SQLException;

   void setLocalInfileInputStream(InputStream var1);

   InputStream getLocalInfileInputStream();

   void setPingTarget(PingTarget var1);

   ExceptionInterceptor getExceptionInterceptor();

   void removeOpenResultSet(ResultSetInternalMethods var1);

   int getOpenResultSetCount();

   void setHoldResultsOpenOverClose(boolean var1);
}
