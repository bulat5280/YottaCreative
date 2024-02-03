package com.mysql.cj.core.conf.url;

import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.jdbc.ha.LoadBalancedAutoCommitInterceptor;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class LoadbalanceConnectionUrl extends ConnectionUrl {
   protected LoadbalanceConnectionUrl(ConnectionUrlParser connStrParser, Properties info) {
      super(connStrParser, info);
      this.type = ConnectionUrl.Type.LOADBALANCE_CONNECTION;
   }

   public LoadbalanceConnectionUrl(List<HostInfo> hosts, Map<String, String> properties) {
      this.originalConnStr = ConnectionUrl.Type.LOADBALANCE_CONNECTION.getProtocol() + "//**internally_generated**" + System.currentTimeMillis() + "**";
      this.type = ConnectionUrl.Type.LOADBALANCE_CONNECTION;
      this.hosts.addAll(hosts);
      this.properties.putAll(properties);
      this.injectPerTypeProperties(this.properties);
      this.setupPropertiesTransformer();
   }

   protected void injectPerTypeProperties(Map<String, String> props) {
      props.put("useLocalSessionState", "true");
      if (props.containsKey("loadBalanceAutoCommitStatementThreshold")) {
         try {
            int autoCommitSwapThreshold = Integer.parseInt((String)props.get("loadBalanceAutoCommitStatementThreshold"));
            if (autoCommitSwapThreshold > 0) {
               String statementInterceptors = (String)props.get("statementInterceptors");
               if (StringUtils.isNullOrEmpty(statementInterceptors)) {
                  props.put("statementInterceptors", LoadBalancedAutoCommitInterceptor.class.getName());
               } else {
                  props.put("statementInterceptors", statementInterceptors + "," + LoadBalancedAutoCommitInterceptor.class.getName());
               }
            }
         } catch (Throwable var4) {
         }
      }

   }

   public List<String> getHostInfoListAsHostPortPairs() {
      return (List)this.hosts.stream().map((hi) -> {
         return hi.getHostPortPair();
      }).collect(Collectors.toList());
   }

   public List<HostInfo> getHostInfoListFromHostPortPairs(Collection<String> hostPortPairs) {
      return (List)hostPortPairs.stream().map(this::getHostOrSpawnIsolated).collect(Collectors.toList());
   }
}
