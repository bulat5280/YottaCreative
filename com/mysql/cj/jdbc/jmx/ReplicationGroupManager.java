package com.mysql.cj.jdbc.jmx;

import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.core.Messages;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.jdbc.ha.ReplicationConnectionGroup;
import com.mysql.cj.jdbc.ha.ReplicationConnectionGroupManager;
import java.lang.management.ManagementFactory;
import java.sql.SQLException;
import java.util.Iterator;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class ReplicationGroupManager implements ReplicationGroupManagerMBean {
   private boolean isJmxRegistered = false;

   public synchronized void registerJmx() throws SQLException {
      if (!this.isJmxRegistered) {
         MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

         try {
            ObjectName name = new ObjectName("com.mysql.cj.jdbc.jmx:type=ReplicationGroupManager");
            mbs.registerMBean(this, name);
            this.isJmxRegistered = true;
         } catch (Exception var3) {
            throw SQLError.createSQLException(Messages.getString("ReplicationGroupManager.0"), (String)null, var3, (ExceptionInterceptor)null);
         }
      }
   }

   public void addSlaveHost(String groupFilter, String host) throws SQLException {
      ReplicationConnectionGroupManager.addSlaveHost(groupFilter, host);
   }

   public void removeSlaveHost(String groupFilter, String host) throws SQLException {
      ReplicationConnectionGroupManager.removeSlaveHost(groupFilter, host);
   }

   public void promoteSlaveToMaster(String groupFilter, String host) throws SQLException {
      ReplicationConnectionGroupManager.promoteSlaveToMaster(groupFilter, host);
   }

   public void removeMasterHost(String groupFilter, String host) throws SQLException {
      ReplicationConnectionGroupManager.removeMasterHost(groupFilter, host);
   }

   public String getMasterHostsList(String group) {
      StringBuilder sb = new StringBuilder("");
      boolean found = false;
      Iterator var4 = ReplicationConnectionGroupManager.getMasterHosts(group).iterator();

      while(var4.hasNext()) {
         String host = (String)var4.next();
         if (found) {
            sb.append(",");
         }

         found = true;
         sb.append(host);
      }

      return sb.toString();
   }

   public String getSlaveHostsList(String group) {
      StringBuilder sb = new StringBuilder("");
      boolean found = false;
      Iterator var4 = ReplicationConnectionGroupManager.getSlaveHosts(group).iterator();

      while(var4.hasNext()) {
         String host = (String)var4.next();
         if (found) {
            sb.append(",");
         }

         found = true;
         sb.append(host);
      }

      return sb.toString();
   }

   public String getRegisteredConnectionGroups() {
      StringBuilder sb = new StringBuilder("");
      boolean found = false;
      Iterator var3 = ReplicationConnectionGroupManager.getGroupsMatching((String)null).iterator();

      while(var3.hasNext()) {
         ReplicationConnectionGroup group = (ReplicationConnectionGroup)var3.next();
         if (found) {
            sb.append(",");
         }

         found = true;
         sb.append(group.getGroupName());
      }

      return sb.toString();
   }

   public int getActiveMasterHostCount(String group) {
      return ReplicationConnectionGroupManager.getMasterHosts(group).size();
   }

   public int getActiveSlaveHostCount(String group) {
      return ReplicationConnectionGroupManager.getSlaveHosts(group).size();
   }

   public int getSlavePromotionCount(String group) {
      return ReplicationConnectionGroupManager.getNumberOfMasterPromotion(group);
   }

   public long getTotalLogicalConnectionCount(String group) {
      return ReplicationConnectionGroupManager.getTotalConnectionCount(group);
   }

   public long getActiveLogicalConnectionCount(String group) {
      return ReplicationConnectionGroupManager.getActiveConnectionCount(group);
   }
}
