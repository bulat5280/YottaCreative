package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.mysqla.io.PacketHeader;

public class DefaultPacketHeader implements PacketHeader {
   protected byte[] packetHeaderBuf = new byte[4];

   public byte[] getBuffer() {
      return this.packetHeaderBuf;
   }

   public int getPacketLength() {
      return (this.packetHeaderBuf[0] & 255) + ((this.packetHeaderBuf[1] & 255) << 8) + ((this.packetHeaderBuf[2] & 255) << 16);
   }

   public byte getPacketSequence() {
      return this.packetHeaderBuf[3];
   }
}
