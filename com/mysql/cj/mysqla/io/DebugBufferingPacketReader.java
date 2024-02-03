package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.conf.ReadableProperty;
import com.mysql.cj.api.mysqla.io.PacketHeader;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.io.PacketReader;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.util.StringUtils;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;

public class DebugBufferingPacketReader implements PacketReader {
   private static final int MAX_PACKET_DUMP_LENGTH = 1024;
   private static final int DEBUG_MSG_LEN = 96;
   private PacketReader packetReader;
   private LinkedList<StringBuilder> packetDebugBuffer;
   private ReadableProperty<Integer> packetDebugBufferSize;
   private String lastHeaderPayload = "";
   private boolean packetSequenceReset = false;

   public DebugBufferingPacketReader(PacketReader packetReader, LinkedList<StringBuilder> packetDebugBuffer, ReadableProperty<Integer> packetDebugBufferSize) {
      this.packetReader = packetReader;
      this.packetDebugBuffer = packetDebugBuffer;
      this.packetDebugBufferSize = packetDebugBufferSize;
   }

   public PacketHeader readHeader() throws IOException {
      byte prevPacketSeq = this.packetReader.getPacketSequence();
      PacketHeader hdr = this.packetReader.readHeader();
      byte currPacketSeq = hdr.getPacketSequence();
      if (!this.packetSequenceReset) {
         if (currPacketSeq == -128 && prevPacketSeq != 127) {
            throw new IOException(Messages.getString("PacketReader.9", new Object[]{"-128", currPacketSeq}));
         }

         if (prevPacketSeq == -1 && currPacketSeq != 0) {
            throw new IOException(Messages.getString("PacketReader.9", new Object[]{"-1", currPacketSeq}));
         }

         if (currPacketSeq != -128 && prevPacketSeq != -1 && currPacketSeq != prevPacketSeq + 1) {
            throw new IOException(Messages.getString("PacketReader.9", new Object[]{prevPacketSeq + 1, currPacketSeq}));
         }
      } else {
         this.packetSequenceReset = false;
      }

      this.lastHeaderPayload = StringUtils.dumpAsHex(hdr.getBuffer(), 4);
      return hdr;
   }

   public PacketPayload readPayload(Optional<PacketPayload> reuse, int packetLength) throws IOException {
      PacketPayload buf = this.packetReader.readPayload(reuse, packetLength);
      int bytesToDump = Math.min(1024, packetLength);
      String packetPayload = StringUtils.dumpAsHex(buf.getByteBuffer(), bytesToDump);
      StringBuilder packetDump = new StringBuilder(100 + packetPayload.length());
      packetDump.append("Server ");
      packetDump.append(reuse.isPresent() ? "(re-used) " : "(new) ");
      packetDump.append(buf.toString());
      packetDump.append(" --------------------> Client\n");
      packetDump.append("\nPacket payload:\n\n");
      packetDump.append(this.lastHeaderPayload);
      packetDump.append(packetPayload);
      if (bytesToDump == 1024) {
         packetDump.append("\nNote: Packet of " + packetLength + " bytes truncated to " + 1024 + " bytes.\n");
      }

      if (this.packetDebugBuffer.size() + 1 > (Integer)this.packetDebugBufferSize.getValue()) {
         this.packetDebugBuffer.removeFirst();
      }

      this.packetDebugBuffer.addLast(packetDump);
      return buf;
   }

   public byte getPacketSequence() {
      return this.packetReader.getPacketSequence();
   }

   public void resetPacketSequence() {
      this.packetReader.resetPacketSequence();
      this.packetSequenceReset = true;
   }

   public PacketReader undecorateAll() {
      return this.packetReader.undecorateAll();
   }

   public PacketReader undecorate() {
      return this.packetReader;
   }
}
