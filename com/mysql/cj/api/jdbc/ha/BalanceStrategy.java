package com.mysql.cj.api.jdbc.ha;

import com.mysql.cj.jdbc.ConnectionImpl;
import com.mysql.cj.jdbc.ha.LoadBalancedConnectionProxy;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BalanceStrategy {
   ConnectionImpl pickConnection(LoadBalancedConnectionProxy var1, List<String> var2, Map<String, ConnectionImpl> var3, long[] var4, int var5) throws SQLException;
}
