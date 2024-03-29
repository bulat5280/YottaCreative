package com.mysql.cj.api.jdbc.ha;

import com.mysql.cj.api.jdbc.JdbcConnection;
import java.sql.SQLException;

public interface LoadBalancedConnection extends JdbcConnection {
   boolean addHost(String var1) throws SQLException;

   void removeHost(String var1) throws SQLException;

   void removeHostWhenNotInUse(String var1) throws SQLException;

   void ping(boolean var1) throws SQLException;
}
