package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.conf.ReadableProperty;
import com.mysql.cj.api.mysqla.io.PacketSender;
import com.mysql.cj.core.util.StringUtils;
import java.io.IOException;
import java.util.LinkedList;

public class DebugBufferingPacketSender implements PacketSender {
   private PacketSender packetSender;
   private LinkedList<StringBuilder> packetDebugBuffer;
   private ReadableProperty<Integer> packetDebugBufferSize;
   private int maxPacketDumpLength = 1024;
   private static final int DEBUG_MSG_LEN = 64;

   public DebugBufferingPacketSender(PacketSender packetSender, LinkedList<StringBuilder> packetDebugBuffer, ReadableProperty<Integer> packetDebugBufferSize) {
      this.packetSender = packetSender;
      this.packetDebugBuffer = packetDebugBuffer;
      this.packetDebugBufferSize = packetDebugBufferSize;
   }

   public void setMaxPacketDumpLength(int maxPacketDumpLength) {
      this.maxPacketDumpLength = maxPacketDumpLength;
   }

   private void pushPacketToDebugBuffer(byte[] packet, int packetLen) {
      int bytesToDump = Math.min(this.maxPacketDumpLength, packetLen);
      String packetPayload = StringUtils.dumpAsHex(packet, bytesToDump);
      StringBuilder packetDump = new StringBuilder(68 + packetPayload.length());
      packetDump.append("Client ");
      packetDump.append(packet.toString());
      packetDump.append("--------------------> Server\n");
      packetDump.append("\nPacket payload:\n\n");
      packetDump.append(packetPayload);
      if (packetLen > this.maxPacketDumpLength) {
         packetDump.append("\nNote: Packet of " + packetLen + " bytes truncated to " + this.maxPacketDumpLength + " bytes.\n");
      }

      if (this.packetDebugBuffer.size() + 1 > (Integer)this.packetDebugBufferSize.getValue()) {
         this.packetDebugBuffer.removeFirst();
      }

      this.packetDebugBuffer.addLast(packetDump);
   }

   public void send(byte[] packet, int packetLen, byte packetSequence) throws IOException {
      this.pushPacketToDebugBuffer(packet, packetLen);
      this.packetSender.send(packet, packetLen, packetSequence);
   }

   public PacketSender undecorateAll() {
      return this.packetSender.undecorateAll();
   }

   public PacketSender undecorate() {
      return this.packetSender;
   }
}
