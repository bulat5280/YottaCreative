package com.mysql.cj.jdbc.ha;

import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.jdbc.ha.BalanceStrategy;
import com.mysql.cj.core.Messages;
import com.mysql.cj.jdbc.ConnectionImpl;
import com.mysql.cj.jdbc.exceptions.SQLError;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RandomBalanceStrategy implements BalanceStrategy {
   public ConnectionImpl pickConnection(LoadBalancedConnectionProxy proxy, List<String> configuredHosts, Map<String, ConnectionImpl> liveConnections, long[] responseTimes, int numRetries) throws SQLException {
      int numHosts = configuredHosts.size();
      SQLException ex = null;
      List<String> whiteList = new ArrayList(numHosts);
      whiteList.addAll(configuredHosts);
      Map<String, Long> blackList = proxy.getGlobalBlacklist();
      whiteList.removeAll(blackList.keySet());
      Map<String, Integer> whiteListMap = this.getArrayIndexMap(whiteList);
      int attempts = 0;

      ConnectionImpl conn;
      while(true) {
         if (attempts >= numRetries) {
            if (ex != null) {
               throw ex;
            }

            return null;
         }

         int random = (int)Math.floor(Math.random() * (double)whiteList.size());
         if (whiteList.size() == 0) {
            throw SQLError.createSQLException(Messages.getString("RandomBalanceStrategy.0"), (ExceptionInterceptor)null);
         }

         String hostPortSpec = (String)whiteList.get(random);
         conn = (ConnectionImpl)liveConnections.get(hostPortSpec);
         if (conn != null) {
            break;
         }

         try {
            conn = proxy.createConnectionForHost(hostPortSpec);
            break;
         } catch (SQLException var19) {
            ex = var19;
            if (!proxy.shouldExceptionTriggerConnectionSwitch(var19)) {
               throw var19;
            }

            Integer whiteListIndex = (Integer)whiteListMap.get(hostPortSpec);
            if (whiteListIndex != null) {
               whiteList.remove(whiteListIndex);
               whiteListMap = this.getArrayIndexMap(whiteList);
            }

            proxy.addToGlobalBlacklist(hostPortSpec);
            if (whiteList.size() == 0) {
               ++attempts;

               try {
                  Thread.sleep(250L);
               } catch (InterruptedException var18) {
               }

               new HashMap(numHosts);
               whiteList.addAll(configuredHosts);
               blackList = proxy.getGlobalBlacklist();
               whiteList.removeAll(blackList.keySet());
               whiteListMap = this.getArrayIndexMap(whiteList);
            }
         }
      }

      return conn;
   }

   private Map<String, Integer> getArrayIndexMap(List<String> l) {
      Map<String, Integer> m = new HashMap(l.size());

      for(int i = 0; i < l.size(); ++i) {
         m.put(l.get(i), i);
      }

      return m;
   }
}
