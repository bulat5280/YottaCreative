package com.mysql.cj.jdbc.ha;

import com.mysql.cj.api.jdbc.ha.BalanceStrategy;
import com.mysql.cj.jdbc.ConnectionImpl;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BestResponseTimeBalanceStrategy implements BalanceStrategy {
   public ConnectionImpl pickConnection(LoadBalancedConnectionProxy proxy, List<String> configuredHosts, Map<String, ConnectionImpl> liveConnections, long[] responseTimes, int numRetries) throws SQLException {
      Map<String, Long> blackList = proxy.getGlobalBlacklist();
      SQLException ex = null;
      int attempts = 0;

      ConnectionImpl conn;
      while(true) {
         if (attempts >= numRetries) {
            if (ex != null) {
               throw ex;
            }

            return null;
         }

         long minResponseTime = Long.MAX_VALUE;
         int bestHostIndex = 0;
         if (blackList.size() == configuredHosts.size()) {
            blackList = proxy.getGlobalBlacklist();
         }

         for(int i = 0; i < responseTimes.length; ++i) {
            long candidateResponseTime = responseTimes[i];
            if (candidateResponseTime < minResponseTime && !blackList.containsKey(configuredHosts.get(i))) {
               if (candidateResponseTime == 0L) {
                  bestHostIndex = i;
                  break;
               }

               bestHostIndex = i;
               minResponseTime = candidateResponseTime;
            }
         }

         String bestHost = (String)configuredHosts.get(bestHostIndex);
         conn = (ConnectionImpl)liveConnections.get(bestHost);
         if (conn != null) {
            break;
         }

         try {
            conn = proxy.createConnectionForHost(bestHost);
            break;
         } catch (SQLException var17) {
            ex = var17;
            if (!proxy.shouldExceptionTriggerConnectionSwitch(var17)) {
               throw var17;
            }

            proxy.addToGlobalBlacklist(bestHost);
            blackList.put(bestHost, (Object)null);
            if (blackList.size() == configuredHosts.size()) {
               ++attempts;

               try {
                  Thread.sleep(250L);
               } catch (InterruptedException var16) {
               }

               blackList = proxy.getGlobalBlacklist();
            }
         }
      }

      return conn;
   }
}
