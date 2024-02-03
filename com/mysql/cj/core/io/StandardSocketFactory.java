package com.mysql.cj.core.io;

import com.mysql.cj.api.io.SocketFactory;
import com.mysql.cj.core.Messages;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;

public class StandardSocketFactory implements SocketFactory {
   public static final String TCP_KEEP_ALIVE_DEFAULT_VALUE = "true";
   public static final String TCP_RCV_BUF_DEFAULT_VALUE = "0";
   public static final String TCP_SND_BUF_DEFAULT_VALUE = "0";
   public static final String TCP_TRAFFIC_CLASS_DEFAULT_VALUE = "0";
   public static final String TCP_NO_DELAY_DEFAULT_VALUE = "true";
   protected String host = null;
   protected int port = 3306;
   protected Socket rawSocket = null;
   protected int loginTimeoutCountdown = 0;
   protected long loginTimeoutCheckTimestamp = System.currentTimeMillis();
   protected int socketTimeoutBackup = 0;

   public Socket afterHandshake() throws SocketException, IOException {
      this.resetLoginTimeCountdown();
      this.rawSocket.setSoTimeout(this.socketTimeoutBackup);
      return this.rawSocket;
   }

   public Socket beforeHandshake() throws SocketException, IOException {
      this.resetLoginTimeCountdown();
      this.socketTimeoutBackup = this.rawSocket.getSoTimeout();
      this.rawSocket.setSoTimeout(this.getRealTimeout(this.socketTimeoutBackup));
      return this.rawSocket;
   }

   protected Socket createSocket(Properties props) {
      return new Socket();
   }

   private void configureSocket(Socket sock, Properties props) throws SocketException, IOException {
      sock.setTcpNoDelay(Boolean.valueOf(props.getProperty("tcpNoDelay", "true")));
      String keepAlive = props.getProperty("tcpKeepAlive", "true");
      if (keepAlive != null && keepAlive.length() > 0) {
         sock.setKeepAlive(Boolean.valueOf(keepAlive));
      }

      int receiveBufferSize = Integer.parseInt(props.getProperty("tcpRcvBuf", "0"));
      if (receiveBufferSize > 0) {
         sock.setReceiveBufferSize(receiveBufferSize);
      }

      int sendBufferSize = Integer.parseInt(props.getProperty("tcpSndBuf", "0"));
      if (sendBufferSize > 0) {
         sock.setSendBufferSize(sendBufferSize);
      }

      int trafficClass = Integer.parseInt(props.getProperty("tcpTrafficClass", "0"));
      if (trafficClass > 0) {
         sock.setTrafficClass(trafficClass);
      }

   }

   public Socket connect(String hostname, int portNumber, Properties props, int loginTimeout) throws SocketException, IOException {
      this.loginTimeoutCountdown = loginTimeout;
      if (props != null) {
         this.host = hostname;
         this.port = portNumber;
         String localSocketHostname = props.getProperty("localSocketAddress");
         InetSocketAddress localSockAddr = null;
         if (localSocketHostname != null && localSocketHostname.length() > 0) {
            localSockAddr = new InetSocketAddress(InetAddress.getByName(localSocketHostname), 0);
         }

         String connectTimeoutStr = props.getProperty("connectTimeout");
         int connectTimeout = 0;
         if (connectTimeoutStr != null) {
            try {
               connectTimeout = Integer.parseInt(connectTimeoutStr);
            } catch (NumberFormatException var13) {
               throw new SocketException("Illegal value '" + connectTimeoutStr + "' for connectTimeout");
            }
         }

         if (this.host != null) {
            InetAddress[] possibleAddresses = InetAddress.getAllByName(this.host);
            if (possibleAddresses.length == 0) {
               throw new SocketException("No addresses for host");
            }

            SocketException lastException = null;
            int i = 0;

            while(i < possibleAddresses.length) {
               try {
                  this.rawSocket = this.createSocket(props);
                  this.configureSocket(this.rawSocket, props);
                  InetSocketAddress sockAddr = new InetSocketAddress(possibleAddresses[i], this.port);
                  if (localSockAddr != null) {
                     this.rawSocket.bind(localSockAddr);
                  }

                  this.rawSocket.connect(sockAddr, this.getRealTimeout(connectTimeout));
                  break;
               } catch (SocketException var14) {
                  lastException = var14;
                  this.resetLoginTimeCountdown();
                  this.rawSocket = null;
                  ++i;
               }
            }

            if (this.rawSocket == null && lastException != null) {
               throw lastException;
            }

            this.resetLoginTimeCountdown();
            return this.rawSocket;
         }
      }

      throw new SocketException("Unable to create socket");
   }

   protected void resetLoginTimeCountdown() throws SocketException {
      if (this.loginTimeoutCountdown > 0) {
         long now = System.currentTimeMillis();
         this.loginTimeoutCountdown = (int)((long)this.loginTimeoutCountdown - (now - this.loginTimeoutCheckTimestamp));
         if (this.loginTimeoutCountdown <= 0) {
            throw new SocketException(Messages.getString("Connection.LoginTimeout"));
         }

         this.loginTimeoutCheckTimestamp = now;
      }

   }

   protected int getRealTimeout(int expectedTimeout) {
      return this.loginTimeoutCountdown <= 0 || expectedTimeout != 0 && expectedTimeout <= this.loginTimeoutCountdown ? expectedTimeout : this.loginTimeoutCountdown;
   }
}
