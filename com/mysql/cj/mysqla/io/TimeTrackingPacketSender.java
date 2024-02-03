package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.io.PacketSentTimeHolder;
import com.mysql.cj.api.mysqla.io.PacketSender;
import java.io.IOException;

public class TimeTrackingPacketSender implements PacketSender, PacketSentTimeHolder {
   private PacketSender packetSender;
   private long lastPacketSentTime = 0L;

   public TimeTrackingPacketSender(PacketSender packetSender) {
      this.packetSender = packetSender;
   }

   public void send(byte[] packet, int packetLen, byte packetSequence) throws IOException {
      this.packetSender.send(packet, packetLen, packetSequence);
      this.lastPacketSentTime = System.currentTimeMillis();
   }

   public long getLastPacketSentTime() {
      return this.lastPacketSentTime;
   }

   public PacketSender undecorateAll() {
      return this.packetSender.undecorateAll();
   }

   public PacketSender undecorate() {
      return this.packetSender;
   }
}
