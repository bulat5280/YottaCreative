package com.mysql.cj.api.io;

import com.mysql.cj.api.MysqlConnection;
import com.mysql.cj.core.Messages;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public interface SocketMetadata {
   default boolean isLocallyConnected(MysqlConnection conn) {
      String processHost = conn.getProcessHost();
      return this.isLocallyConnected(conn, processHost);
   }

   default boolean isLocallyConnected(MysqlConnection conn, String processHost) {
      if (processHost == null) {
         return false;
      } else {
         conn.getSession().getLog().logDebug(Messages.getString("SocketMetadata.0", new Object[]{processHost}));
         int endIndex = processHost.lastIndexOf(":");
         if (endIndex != -1) {
            processHost = processHost.substring(0, endIndex);

            try {
               InetAddress[] whereMysqlThinksIConnectedFrom = InetAddress.getAllByName(processHost);
               SocketAddress remoteSocketAddr = conn.getSession().getRemoteSocketAddress();
               if (remoteSocketAddr instanceof InetSocketAddress) {
                  InetAddress whereIConnectedTo = ((InetSocketAddress)remoteSocketAddr).getAddress();
                  InetAddress[] var7 = whereMysqlThinksIConnectedFrom;
                  int var8 = whereMysqlThinksIConnectedFrom.length;

                  for(int var9 = 0; var9 < var8; ++var9) {
                     InetAddress hostAddr = var7[var9];
                     if (hostAddr.equals(whereIConnectedTo)) {
                        conn.getSession().getLog().logDebug(Messages.getString("SocketMetadata.1", new Object[]{hostAddr, whereIConnectedTo}));
                        return true;
                     }

                     conn.getSession().getLog().logDebug(Messages.getString("SocketMetadata.2", new Object[]{hostAddr, whereIConnectedTo}));
                  }
               } else {
                  conn.getSession().getLog().logDebug(Messages.getString("SocketMetadata.3", new Object[]{remoteSocketAddr}));
               }

               return false;
            } catch (UnknownHostException var11) {
               conn.getSession().getLog().logWarn(Messages.getString("Connection.CantDetectLocalConnect", new Object[]{processHost}), var11);
               return false;
            }
         } else {
            conn.getSession().getLog().logWarn(Messages.getString("SocketMetadata.4", new Object[]{processHost}));
            return false;
         }
      }
   }
}
