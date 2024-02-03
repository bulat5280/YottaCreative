package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.mysqla.io.NativeProtocol;
import com.mysql.cj.api.mysqla.io.PacketHeader;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.io.PacketReader;
import com.mysql.cj.core.Messages;
import java.io.IOException;
import java.util.Optional;

public class MultiPacketReader implements PacketReader {
   private PacketReader packetReader;

   public MultiPacketReader(PacketReader packetReader) {
      this.packetReader = packetReader;
   }

   public PacketHeader readHeader() throws IOException {
      return this.packetReader.readHeader();
   }

   public PacketPayload readPayload(Optional<PacketPayload> reuse, int packetLength) throws IOException {
      PacketPayload buf = this.packetReader.readPayload(reuse, packetLength);
      if (packetLength == 16777215) {
         buf.setPosition(16777215);
         PacketPayload multiPacket = null;
         int multiPacketLength = true;
         byte multiPacketSeq = this.getPacketSequence();

         int multiPacketLength;
         do {
            PacketHeader hdr = this.readHeader();
            multiPacketLength = hdr.getPacketLength();
            if (multiPacket == null) {
               multiPacket = new Buffer(multiPacketLength);
            }

            ++multiPacketSeq;
            if (multiPacketSeq != hdr.getPacketSequence()) {
               throw new IOException(Messages.getString("PacketReader.10"));
            }

            this.packetReader.readPayload(Optional.of(multiPacket), multiPacketLength);
            buf.writeBytes((NativeProtocol.StringLengthDataType)NativeProtocol.StringLengthDataType.STRING_FIXED, multiPacket.getByteBuffer(), 0, multiPacketLength);
         } while(multiPacketLength == 16777215);

         buf.setPosition(0);
      }

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
