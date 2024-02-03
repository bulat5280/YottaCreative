package com.mysql.cj.jdbc.ha;

import com.mysql.cj.api.jdbc.ha.BalanceStrategy;
import com.mysql.cj.jdbc.ConnectionImpl;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SequentialBalanceStrategy implements BalanceStrategy {
   private int currentHostIndex = -1;

   public ConnectionImpl pickConnection(LoadBalancedConnectionProxy proxy, List<String> configuredHosts, Map<String, ConnectionImpl> liveConnections, long[] responseTimes, int numRetries) throws SQLException {
      int numHosts = configuredHosts.size();
      SQLException ex = null;
      Map<String, Long> blackList = proxy.getGlobalBlacklist();
      byte attempts = 0;

      ConnectionImpl conn;
      while(true) {
         while(true) {
            if (attempts >= numRetries) {
               if (ex != null) {
                  throw ex;
               }

               return null;
            }

            if (numHosts == 1) {
               this.currentHostIndex = 0;
               break;
            }

            int i;
            if (this.currentHostIndex == -1) {
               i = (int)Math.floor(Math.random() * (double)numHosts);

               int i;
               for(i = i; i < numHosts; ++i) {
                  if (!blackList.containsKey(configuredHosts.get(i))) {
                     this.currentHostIndex = i;
                     break;
                  }
               }

               if (this.currentHostIndex == -1) {
                  for(i = 0; i < i; ++i) {
                     if (!blackList.containsKey(configuredHosts.get(i))) {
                        this.currentHostIndex = i;
                        break;
                     }
                  }
               }

               if (this.currentHostIndex != -1) {
                  break;
               }

               blackList = proxy.getGlobalBlacklist();

               try {
                  Thread.sleep(250L);
               } catch (InterruptedException var16) {
               }
            } else {
               i = this.currentHostIndex + 1;

               boolean foundGoodHost;
               for(foundGoodHost = false; i < numHosts; ++i) {
                  if (!blackList.containsKey(configuredHosts.get(i))) {
                     this.currentHostIndex = i;
                     foundGoodHost = true;
                     break;
                  }
               }

               if (!foundGoodHost) {
                  for(i = 0; i < this.currentHostIndex; ++i) {
                     if (!blackList.containsKey(configuredHosts.get(i))) {
                        this.currentHostIndex = i;
                        foundGoodHost = true;
                        break;
                     }
                  }
               }

               if (foundGoodHost) {
                  break;
               }

               blackList = proxy.getGlobalBlacklist();

               try {
                  Thread.sleep(250L);
               } catch (InterruptedException var15) {
               }
            }
         }

         String hostPortSpec = (String)configuredHosts.get(this.currentHostIndex);
         conn = (ConnectionImpl)liveConnections.get(hostPortSpec);
         if (conn != null) {
            break;
         }

         try {
            conn = proxy.createConnectionForHost(hostPortSpec);
            break;
         } catch (SQLException var17) {
            ex = var17;
            if (!proxy.shouldExceptionTriggerConnectionSwitch(var17)) {
               throw var17;
            }

            proxy.addToGlobalBlacklist(hostPortSpec);

            try {
               Thread.sleep(250L);
            } catch (InterruptedException var14) {
            }
         }
      }

      return conn;
   }
}
