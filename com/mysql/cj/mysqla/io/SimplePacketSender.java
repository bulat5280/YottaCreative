package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.mysqla.io.PacketSender;
import com.mysql.cj.mysqla.MysqlaUtils;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class SimplePacketSender implements PacketSender {
   private BufferedOutputStream outputStream;

   public SimplePacketSender(BufferedOutputStream outputStream) {
      this.outputStream = outputStream;
   }

   public void send(byte[] packet, int packetLen, byte packetSequence) throws IOException {
      PacketSplitter packetSplitter = new PacketSplitter(packetLen);

      while(packetSplitter.nextPacket()) {
         this.outputStream.write(MysqlaUtils.encodeMysqlThreeByteInteger(packetSplitter.getPacketLen()));
         this.outputStream.write(packetSequence++);
         this.outputStream.write(packet, packetSplitter.getOffset(), packetSplitter.getPacketLen());
      }

      this.outputStream.flush();
   }

   public PacketSender undecorateAll() {
      return this;
   }

   public PacketSender undecorate() {
      return this;
   }
}
