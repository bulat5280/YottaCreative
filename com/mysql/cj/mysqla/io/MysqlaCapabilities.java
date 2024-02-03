package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.io.ServerCapabilities;
import com.mysql.cj.api.mysqla.io.NativeProtocol;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.core.ServerVersion;

public class MysqlaCapabilities implements ServerCapabilities {
   private PacketPayload initialHandshakePacket;
   private byte protocolVersion = 0;
   private ServerVersion serverVersion;
   private long threadId = -1L;
   private String seed;
   private int capabilityFlags;
   private int serverDefaultCollationIndex;
   private int statusFlags = 0;
   private int authPluginDataLength = 0;

   public PacketPayload getInitialHandshakePacket() {
      return this.initialHandshakePacket;
   }

   public void setInitialHandshakePacket(PacketPayload initialHandshakePacket) {
      this.initialHandshakePacket = initialHandshakePacket;
      this.setProtocolVersion((byte)((int)initialHandshakePacket.readInteger(NativeProtocol.IntegerDataType.INT1)));
      this.setServerVersion(ServerVersion.parseVersion(initialHandshakePacket.readString(NativeProtocol.StringSelfDataType.STRING_TERM, "ASCII")));
      this.setThreadId(initialHandshakePacket.readInteger(NativeProtocol.IntegerDataType.INT4));
      this.setSeed(initialHandshakePacket.readString(NativeProtocol.StringLengthDataType.STRING_FIXED, "ASCII", 8));
      initialHandshakePacket.readInteger(NativeProtocol.IntegerDataType.INT1);
      int flags = 0;
      if (initialHandshakePacket.getPosition() < initialHandshakePacket.getPayloadLength()) {
         flags = (int)initialHandshakePacket.readInteger(NativeProtocol.IntegerDataType.INT2);
      }

      this.setServerDefaultCollationIndex((int)initialHandshakePacket.readInteger(NativeProtocol.IntegerDataType.INT1));
      this.setStatusFlags((int)initialHandshakePacket.readInteger(NativeProtocol.IntegerDataType.INT2));
      flags |= (int)initialHandshakePacket.readInteger(NativeProtocol.IntegerDataType.INT2) << 16;
      this.setCapabilityFlags(flags);
      if ((flags & 524288) != 0) {
         this.authPluginDataLength = (int)initialHandshakePacket.readInteger(NativeProtocol.IntegerDataType.INT1);
      } else {
         initialHandshakePacket.readInteger(NativeProtocol.IntegerDataType.INT1);
      }

      initialHandshakePacket.setPosition(initialHandshakePacket.getPosition() + 10);
   }

   public int getCapabilityFlags() {
      return this.capabilityFlags;
   }

   public void setCapabilityFlags(int capabilityFlags) {
      this.capabilityFlags = capabilityFlags;
   }

   public byte getProtocolVersion() {
      return this.protocolVersion;
   }

   public void setProtocolVersion(byte protocolVersion) {
      this.protocolVersion = protocolVersion;
   }

   public ServerVersion getServerVersion() {
      return this.serverVersion;
   }

   public void setServerVersion(ServerVersion serverVersion) {
      this.serverVersion = serverVersion;
   }

   public long getThreadId() {
      return this.threadId;
   }

   public void setThreadId(long threadId) {
      this.threadId = threadId;
   }

   public String getSeed() {
      return this.seed;
   }

   public void setSeed(String seed) {
      this.seed = seed;
   }

   public int getServerDefaultCollationIndex() {
      return this.serverDefaultCollationIndex;
   }

   public void setServerDefaultCollationIndex(int serverDefaultCollationIndex) {
      this.serverDefaultCollationIndex = serverDefaultCollationIndex;
   }

   public int getStatusFlags() {
      return this.statusFlags;
   }

   public void setStatusFlags(int statusFlags) {
      this.statusFlags = statusFlags;
   }

   public int getAuthPluginDataLength() {
      return this.authPluginDataLength;
   }

   public void setAuthPluginDataLength(int authPluginDataLength) {
      this.authPluginDataLength = authPluginDataLength;
   }
}
