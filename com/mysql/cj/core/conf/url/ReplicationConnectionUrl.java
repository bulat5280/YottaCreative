package com.mysql.cj.core.conf.url;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class ReplicationConnectionUrl extends ConnectionUrl {
   private static final String TYPE_MASTER = "MASTER";
   private static final String TYPE_SLAVE = "SLAVE";
   private List<HostInfo> masterHosts = new ArrayList();
   private List<HostInfo> slaveHosts = new ArrayList();

   protected ReplicationConnectionUrl(ConnectionUrlParser connStrParser, Properties info) {
      super(connStrParser, info);
      this.type = ConnectionUrl.Type.REPLICATION_CONNECTION;
      LinkedList<HostInfo> undefinedHosts = new LinkedList();
      Iterator var4 = this.hosts.iterator();

      while(var4.hasNext()) {
         HostInfo hi = (HostInfo)var4.next();
         Map<String, String> hostProperties = hi.getHostProperties();
         if (hostProperties.containsKey("TYPE")) {
            if ("MASTER".equalsIgnoreCase((String)hostProperties.get("TYPE"))) {
               this.masterHosts.add(hi);
            } else if ("SLAVE".equalsIgnoreCase((String)hostProperties.get("TYPE"))) {
               this.slaveHosts.add(hi);
            } else {
               undefinedHosts.add(hi);
            }
         } else {
            undefinedHosts.add(hi);
         }
      }

      if (!undefinedHosts.isEmpty()) {
         if (this.masterHosts.isEmpty()) {
            this.masterHosts.add(undefinedHosts.removeFirst());
         }

         this.slaveHosts.addAll(undefinedHosts);
      }

   }

   public ReplicationConnectionUrl(List<HostInfo> masters, List<HostInfo> slaves, Map<String, String> properties) {
      this.originalConnStr = ConnectionUrl.Type.REPLICATION_CONNECTION.getProtocol() + "//**internally_generated**" + System.currentTimeMillis() + "**";
      this.type = ConnectionUrl.Type.REPLICATION_CONNECTION;
      this.hosts.addAll(masters);
      this.hosts.addAll(slaves);
      this.masterHosts.addAll(masters);
      this.slaveHosts.addAll(slaves);
      this.properties.putAll(properties);
      this.injectPerTypeProperties(this.properties);
      this.setupPropertiesTransformer();
   }

   protected void injectPerTypeProperties(Map<String, String> hostProps) {
      hostProps.put("useLocalSessionState", "true");
   }

   public HostInfo getMasterHostOrSpawnIsolated(String hostPortPair) {
      return super.getHostOrSpawnIsolated(hostPortPair, this.masterHosts);
   }

   public List<HostInfo> getMastersList() {
      return Collections.unmodifiableList(this.masterHosts);
   }

   public List<String> getMastersListAsHostPortPairs() {
      return (List)this.masterHosts.stream().map((hi) -> {
         return hi.getHostPortPair();
      }).collect(Collectors.toList());
   }

   public List<HostInfo> getMasterHostsListFromHostPortPairs(Collection<String> hostPortPairs) {
      return (List)hostPortPairs.stream().map(this::getMasterHostOrSpawnIsolated).collect(Collectors.toList());
   }

   public HostInfo getSlaveHostOrSpawnIsolated(String hostPortPair) {
      return super.getHostOrSpawnIsolated(hostPortPair, this.slaveHosts);
   }

   public List<HostInfo> getSlavesList() {
      return Collections.unmodifiableList(this.slaveHosts);
   }

   public List<String> getSlavesListAsHostPortPairs() {
      return (List)this.slaveHosts.stream().map((hi) -> {
         return hi.getHostPortPair();
      }).collect(Collectors.toList());
   }

   public List<HostInfo> getSlaveHostsListFromHostPortPairs(Collection<String> hostPortPairs) {
      return (List)hostPortPairs.stream().map(this::getSlaveHostOrSpawnIsolated).collect(Collectors.toList());
   }
}
