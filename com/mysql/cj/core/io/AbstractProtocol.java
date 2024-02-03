package com.mysql.cj.core.io;

import com.mysql.cj.api.MysqlConnection;
import com.mysql.cj.api.authentication.AuthenticationProvider;
import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.io.PacketReceivedTimeHolder;
import com.mysql.cj.api.io.PacketSentTimeHolder;
import com.mysql.cj.api.io.Protocol;
import com.mysql.cj.api.io.SocketConnection;
import com.mysql.cj.api.log.Log;
import java.util.LinkedList;

public abstract class AbstractProtocol implements Protocol {
   protected MysqlConnection connection;
   protected SocketConnection socketConnection;
   protected PropertySet propertySet;
   protected transient Log log;
   protected ExceptionInterceptor exceptionInterceptor;
   protected AuthenticationProvider authProvider;
   private PacketSentTimeHolder packetSentTimeHolder = new PacketSentTimeHolder() {
      public long getLastPacketSentTime() {
         return 0L;
      }
   };
   private PacketReceivedTimeHolder packetReceivedTimeHolder = new PacketReceivedTimeHolder() {
      public long getLastPacketReceivedTime() {
         return 0L;
      }
   };
   protected LinkedList<StringBuilder> packetDebugRingBuffer = null;

   public MysqlConnection getConnection() {
      return this.connection;
   }

   public void setConnection(MysqlConnection connection) {
      this.connection = connection;
   }

   public SocketConnection getSocketConnection() {
      return this.socketConnection;
   }

   public AuthenticationProvider getAuthenticationProvider() {
      return this.authProvider;
   }

   public ExceptionInterceptor getExceptionInterceptor() {
      return this.exceptionInterceptor;
   }

   public PacketSentTimeHolder getPacketSentTimeHolder() {
      return this.packetSentTimeHolder;
   }

   public void setPacketSentTimeHolder(PacketSentTimeHolder packetSentTimeHolder) {
      this.packetSentTimeHolder = packetSentTimeHolder;
   }

   public PacketReceivedTimeHolder getPacketReceivedTimeHolder() {
      return this.packetReceivedTimeHolder;
   }

   public void setPacketReceivedTimeHolder(PacketReceivedTimeHolder packetReceivedTimeHolder) {
      this.packetReceivedTimeHolder = packetReceivedTimeHolder;
   }

   public PropertySet getPropertySet() {
      return this.propertySet;
   }

   public void setPropertySet(PropertySet propertySet) {
      this.propertySet = propertySet;
   }
}
