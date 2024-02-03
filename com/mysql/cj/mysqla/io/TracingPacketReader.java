package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.log.Log;
import com.mysql.cj.api.mysqla.io.PacketHeader;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.io.PacketReader;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.util.StringUtils;
import java.io.IOException;
import java.util.Optional;

public class TracingPacketReader implements PacketReader {
   private static final int MAX_PACKET_DUMP_LENGTH = 1024;
   private PacketReader packetReader;
   private Log log;

   public TracingPacketReader(PacketReader packetReader, Log log) {
      this.packetReader = packetReader;
      this.log = log;
   }

   public PacketHeader readHeader() throws IOException {
      PacketHeader hdr = this.packetReader.readHeader();
      StringBuilder traceMessageBuf = new StringBuilder();
      traceMessageBuf.append(Messages.getString("PacketReader.3"));
      traceMessageBuf.append(hdr.getPacketLength());
      traceMessageBuf.append(Messages.getString("PacketReader.4"));
      traceMessageBuf.append(StringUtils.dumpAsHex(hdr.getBuffer(), 4));
      this.log.logTrace(traceMessageBuf.toString());
      return hdr;
   }

   public PacketPayload readPayload(Optional<PacketPayload> reuse, int packetLength) throws IOException {
      PacketPayload buf = this.packetReader.readPayload(reuse, packetLength);
      StringBuilder traceMessageBuf = new StringBuilder();
      traceMessageBuf.append(Messages.getString(reuse.isPresent() ? "PacketReader.5" : "PacketReader.6"));
      traceMessageBuf.append(StringUtils.dumpAsHex(buf.getByteBuffer(), packetLength < 1024 ? packetLength : 1024));
      if (packetLength > 1024) {
         traceMessageBuf.append(Messages.getString("PacketReader.7"));
         traceMessageBuf.append(1024);
         traceMessageBuf.append(Messages.getString("PacketReader.8"));
      }

      this.log.logTrace(traceMessageBuf.toString());
      return buf;
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
