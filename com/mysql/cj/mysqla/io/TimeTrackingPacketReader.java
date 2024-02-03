package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.io.PacketReceivedTimeHolder;
import com.mysql.cj.api.mysqla.io.PacketHeader;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.io.PacketReader;
import java.io.IOException;
import java.util.Optional;

public class TimeTrackingPacketReader implements PacketReader, PacketReceivedTimeHolder {
   private PacketReader packetReader;
   private long lastPacketReceivedTimeMs = 0L;

   public TimeTrackingPacketReader(PacketReader packetReader) {
      this.packetReader = packetReader;
   }

   public PacketHeader readHeader() throws IOException {
      return this.packetReader.readHeader();
   }

   public PacketPayload readPayload(Optional<PacketPayload> reuse, int packetLength) throws IOException {
      PacketPayload buf = this.packetReader.readPayload(reuse, packetLength);
      this.lastPacketReceivedTimeMs = System.currentTimeMillis();
      return buf;
   }

   public long getLastPacketReceivedTime() {
      return this.lastPacketReceivedTimeMs;
   }

   public byte getPacketSequence() {
      return this.packetReader.getPacketSequence();
   }

   public void resetPacketSequence() {
      this.packetReader.resetPacketSequence();
   }

   public PacketReader undecorateAll() {
      return this.packetReader.undecorateAll();
   }

   public PacketReader undecorate() {
      return this.packetReader;
   }
}
