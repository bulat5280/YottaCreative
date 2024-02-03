package com.mysql.cj.api.jdbc.interceptors;

import com.mysql.cj.api.MysqlConnection;
import com.mysql.cj.api.jdbc.Statement;
import com.mysql.cj.api.log.Log;
import com.mysql.cj.api.mysqla.result.Resultset;
import java.sql.SQLException;
import java.util.Properties;

public interface StatementInterceptor {
   StatementInterceptor init(MysqlConnection var1, Properties var2, Log var3);

   <T extends Resultset> T preProcess(String var1, Statement var2) throws SQLException;

   boolean executeTopLevelOnly();

   void destroy();

   <T extends Resultset> T postProcess(String var1, Statement var2, T var3, int var4, boolean var5, boolean var6, Exception var7) throws SQLException;
}
