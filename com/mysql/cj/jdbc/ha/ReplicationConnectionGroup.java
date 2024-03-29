package com.mysql.cj.jdbc.ha;

import com.mysql.cj.api.jdbc.ha.ReplicationConnection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ReplicationConnectionGroup {
   private String groupName;
   private long connections = 0L;
   private long slavesAdded = 0L;
   private long slavesRemoved = 0L;
   private long slavesPromoted = 0L;
   private long activeConnections = 0L;
   private HashMap<Long, ReplicationConnection> replicationConnections = new HashMap();
   private Set<String> slaveHostList = new CopyOnWriteArraySet();
   private boolean isInitialized = false;
   private Set<String> masterHostList = new CopyOnWriteArraySet();

   ReplicationConnectionGroup(String groupName) {
      this.groupName = groupName;
   }

   public long getConnectionCount() {
      return this.connections;
   }

   public long registerReplicationConnection(ReplicationConnection conn, List<String> localMasterList, List<String> localSlaveList) {
      long currentConnectionId;
      synchronized(this) {
         if (!this.isInitialized) {
            if (localMasterList != null) {
               this.masterHostList.addAll(localMasterList);
            }

            if (localSlaveList != null) {
               this.slaveHostList.addAll(localSlaveList);
            }

            this.isInitialized = true;
         }

         currentConnectionId = ++this.connections;
         this.replicationConnections.put(currentConnectionId, conn);
      }

      ++this.activeConnections;
      return currentConnectionId;
   }

   public String getGroupName() {
      return this.groupName;
   }

   public Collection<String> getMasterHosts() {
      return this.masterHostList;
   }

   public Collection<String> getSlaveHosts() {
      return this.slaveHostList;
   }

   public void addSlaveHost(String hostPortPair) throws SQLException {
      if (this.slaveHostList.add(hostPortPair)) {
         ++this.slavesAdded;
         Iterator var2 = this.replicationConnections.values().iterator();

         while(var2.hasNext()) {
            ReplicationConnection c = (ReplicationConnection)var2.next();
            c.addSlaveHost(hostPortPair);
         }
      }

   }

   public void handleCloseConnection(ReplicationConnection conn) {
      this.replicationConnections.remove(conn.getConnectionGroupId());
      --this.activeConnections;
   }

   public void removeSlaveHost(String hostPortPair, boolean closeGently) throws SQLException {
      if (this.slaveHostList.remove(hostPortPair)) {
         ++this.slavesRemoved;
         Iterator var3 = this.replicationConnections.values().iterator();

         while(var3.hasNext()) {
            ReplicationConnection c = (ReplicationConnection)var3.next();
            c.removeSlave(hostPortPair, closeGently);
         }
      }

   }

   public void promoteSlaveToMaster(String hostPortPair) throws SQLException {
      if (this.slaveHostList.remove(hostPortPair) | this.masterHostList.add(hostPortPair)) {
         ++this.slavesPromoted;
         Iterator var2 = this.replicationConnections.values().iterator();

         while(var2.hasNext()) {
            ReplicationConnection c = (ReplicationConnection)var2.next();
            c.promoteSlaveToMaster(hostPortPair);
         }
      }

   }

   public void removeMasterHost(String hostPortPair) throws SQLException {
      this.removeMasterHost(hostPortPair, true);
   }

   public void removeMasterHost(String hostPortPair, boolean closeGently) throws SQLException {
      if (this.masterHostList.remove(hostPortPair)) {
         Iterator var3 = this.replicationConnections.values().iterator();

         while(var3.hasNext()) {
            ReplicationConnection c = (ReplicationConnection)var3.next();
            c.removeMasterHost(hostPortPair, closeGently);
         }
      }

   }

   public int getConnectionCountWithHostAsSlave(String hostPortPair) {
      int matched = 0;
      Iterator var3 = this.replicationConnections.values().iterator();

      while(var3.hasNext()) {
         ReplicationConnection c = (ReplicationConnection)var3.next();
         if (c.isHostSlave(hostPortPair)) {
            ++matched;
         }
      }

      return matched;
   }

   public int getConnectionCountWithHostAsMaster(String hostPortPair) {
      int matched = 0;
      Iterator var3 = this.replicationConnections.values().iterator();

      while(var3.hasNext()) {
         ReplicationConnection c = (ReplicationConnection)var3.next();
         if (c.isHostMaster(hostPortPair)) {
            ++matched;
         }
      }

      return matched;
   }

   public long getNumberOfSlavesAdded() {
      return this.slavesAdded;
   }

   public long getNumberOfSlavesRemoved() {
      return this.slavesRemoved;
   }

   public long getNumberOfSlavePromotions() {
      return this.slavesPromoted;
   }

   public long getTotalConnectionCount() {
      return this.connections;
   }

   public long getActiveConnectionCount() {
      return this.activeConnections;
   }

   public String toString() {
      return "ReplicationConnectionGroup[groupName=" + this.groupName + ",masterHostList=" + this.masterHostList + ",slaveHostList=" + this.slaveHostList + "]";
   }
}
