package com.mysql.cj.jdbc.ha;

import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.api.jdbc.ha.LoadBalancedConnection;
import com.mysql.cj.api.jdbc.ha.ReplicationConnection;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Executor;

public class ReplicationMySQLConnection extends MultiHostMySQLConnection implements ReplicationConnection {
   public ReplicationMySQLConnection(MultiHostConnectionProxy proxy) {
      super(proxy);
   }

   public ReplicationConnectionProxy getThisAsProxy() {
      return (ReplicationConnectionProxy)super.getThisAsProxy();
   }

   protected JdbcConnection getActiveMySQLConnection() {
      return this.getCurrentConnection();
   }

   public synchronized JdbcConnection getCurrentConnection() {
      return this.getThisAsProxy().getCurrentConnection();
   }

   public long getConnectionGroupId() {
      return this.getThisAsProxy().getConnectionGroupId();
   }

   public synchronized JdbcConnection getMasterConnection() {
      return this.getThisAsProxy().getMasterConnection();
   }

   private JdbcConnection getValidatedMasterConnection() {
      LoadBalancedConnection conn = this.getThisAsProxy().masterConnection;

      try {
         return conn != null && !conn.isClosed() ? conn : null;
      } catch (SQLException var3) {
         return null;
      }
   }

   public void promoteSlaveToMaster(String host) throws SQLException {
      try {
         this.getThisAsProxy().promoteSlaveToMaster(host);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void removeMasterHost(String host) throws SQLException {
      try {
         this.getThisAsProxy().removeMasterHost(host);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void removeMasterHost(String host, boolean waitUntilNotInUse) throws SQLException {
      try {
         this.getThisAsProxy().removeMasterHost(host, waitUntilNotInUse);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public boolean isHostMaster(String host) {
      return this.getThisAsProxy().isHostMaster(host);
   }

   public synchronized JdbcConnection getSlavesConnection() {
      return this.getThisAsProxy().getSlavesConnection();
   }

   private JdbcConnection getValidatedSlavesConnection() {
      LoadBalancedConnection conn = this.getThisAsProxy().slavesConnection;

      try {
         return conn != null && !conn.isClosed() ? conn : null;
      } catch (SQLException var3) {
         return null;
      }
   }

   public void addSlaveHost(String host) throws SQLException {
      try {
         this.getThisAsProxy().addSlaveHost(host);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void removeSlave(String host) throws SQLException {
      try {
         this.getThisAsProxy().removeSlave(host);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void removeSlave(String host, boolean closeGently) throws SQLException {
      try {
         this.getThisAsProxy().removeSlave(host, closeGently);
      } catch (CJException var4) {
         throw SQLExceptionsMapping.translateException(var4, this.getExceptionInterceptor());
      }
   }

   public boolean isHostSlave(String host) {
      return this.getThisAsProxy().isHostSlave(host);
   }

   public void setReadOnly(boolean readOnlyFlag) throws SQLException {
      try {
         this.getThisAsProxy().setReadOnly(readOnlyFlag);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public boolean isReadOnly() throws SQLException {
      try {
         return this.getThisAsProxy().isReadOnly();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public synchronized void ping() throws SQLException {
      try {
         JdbcConnection conn;
         try {
            if ((conn = this.getValidatedMasterConnection()) != null) {
               conn.ping();
            }
         } catch (SQLException var5) {
            if (this.isMasterConnection()) {
               throw var5;
            }
         }

         try {
            if ((conn = this.getValidatedSlavesConnection()) != null) {
               conn.ping();
            }
         } catch (SQLException var4) {
            if (!this.isMasterConnection()) {
               throw var4;
            }
         }

      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.getExceptionInterceptor());
      }
   }

   public synchronized void changeUser(String userName, String newPassword) throws SQLException {
      try {
         JdbcConnection conn;
         if ((conn = this.getValidatedMasterConnection()) != null) {
            conn.changeUser(userName, newPassword);
         }

         if ((conn = this.getValidatedSlavesConnection()) != null) {
            conn.changeUser(userName, newPassword);
         }

      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   public synchronized void setStatementComment(String comment) {
      JdbcConnection conn;
      if ((conn = this.getValidatedMasterConnection()) != null) {
         conn.setStatementComment(comment);
      }

      if ((conn = this.getValidatedSlavesConnection()) != null) {
         conn.setStatementComment(comment);
      }

   }

   public boolean hasSameProperties(JdbcConnection c) {
      JdbcConnection connM = this.getValidatedMasterConnection();
      JdbcConnection connS = this.getValidatedSlavesConnection();
      if (connM == null && connS == null) {
         return false;
      } else {
         return (connM == null || connM.hasSameProperties(c)) && (connS == null || connS.hasSameProperties(c));
      }
   }

   public Properties getProperties() {
      Properties props = new Properties();
      JdbcConnection conn;
      if ((conn = this.getValidatedMasterConnection()) != null) {
         props.putAll(conn.getProperties());
      }

      if ((conn = this.getValidatedSlavesConnection()) != null) {
         props.putAll(conn.getProperties());
      }

      return props;
   }

   public void abort(Executor executor) throws SQLException {
      try {
         this.getThisAsProxy().doAbort(executor);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public void abortInternal() throws SQLException {
      try {
         this.getThisAsProxy().doAbortInternal();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.getExceptionInterceptor());
      }
   }

   public void setProxy(JdbcConnection proxy) {
      this.getThisAsProxy().setProxy(proxy);
   }

   public boolean isWrapperFor(Class<?> iface) throws SQLException {
      try {
         return iface.isInstance(this);
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.getExceptionInterceptor());
      }
   }

   public <T> T unwrap(Class<T> iface) throws SQLException {
      try {
         try {
            return iface.cast(this);
         } catch (ClassCastException var4) {
            throw SQLError.createSQLException(Messages.getString("Common.UnableToUnwrap", new Object[]{iface.toString()}), "S1009", this.getExceptionInterceptor());
         }
      } catch (CJException var5) {
         throw SQLExceptionsMapping.translateException(var5, this.getExceptionInterceptor());
      }
   }

   /** @deprecated */
   @Deprecated
   public synchronized void clearHasTriedMaster() {
      this.getThisAsProxy().masterConnection.clearHasTriedMaster();
      this.getThisAsProxy().slavesConnection.clearHasTriedMaster();
   }
}
