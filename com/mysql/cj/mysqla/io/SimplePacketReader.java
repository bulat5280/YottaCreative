package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.conf.ReadableProperty;
import com.mysql.cj.api.io.SocketConnection;
import com.mysql.cj.api.mysqla.io.PacketHeader;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.io.PacketReader;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.CJPacketTooBigException;
import java.io.IOException;
import java.util.Optional;

public class SimplePacketReader implements PacketReader {
   protected SocketConnection socketConnection;
   protected ReadableProperty<Integer> maxAllowedPacket;
   private byte readPacketSequence = -1;

   public SimplePacketReader(SocketConnection socketConnection, ReadableProperty<Integer> maxAllowedPacket) {
      this.socketConnection = socketConnection;
      this.maxAllowedPacket = maxAllowedPacket;
   }

   public PacketHeader readHeader() throws IOException {
      DefaultPacketHeader hdr = new DefaultPacketHeader();

      try {
         this.socketConnection.getMysqlInput().readFully(hdr.getBuffer(), 0, 4);
         int packetLength = hdr.getPacketLength();
         if (packetLength > (Integer)this.maxAllowedPacket.getValue()) {
            throw new CJPacketTooBigException((long)packetLength, (long)(Integer)this.maxAllowedPacket.getValue());
         }
      } catch (CJPacketTooBigException | IOException var5) {
         try {
            this.socketConnection.forceClose();
         } catch (Exception var4) {
         }

         throw var5;
      }

      this.readPacketSequence = hdr.getPacketSequence();
      return hdr;
   }

   public PacketPayload readPayload(Optional<PacketPayload> reuse, int packetLength) throws IOException {
      try {
         Object buf;
         if (reuse.isPresent()) {
            buf = (PacketPayload)reuse.get();
            ((PacketPayload)buf).setPosition(0);
            if (((PacketPayload)buf).getByteBuffer().length < packetLength) {
               ((PacketPayload)buf).setByteBuffer(new byte[packetLength]);
            }

            ((PacketPayload)buf).setPayloadLength(packetLength);
         } else {
            buf = new Buffer(new byte[packetLength]);
         }

         int numBytesRead = this.socketConnection.getMysqlInput().readFully(((PacketPayload)buf).getByteBuffer(), 0, packetLength);
         if (numBytesRead != packetLength) {
            throw new IOException(Messages.getString("PacketReader.1", new Object[]{packetLength, numBytesRead}));
         } else {
            return (PacketPayload)buf;
         }
      } catch (IOException var6) {
         try {
            this.socketConnection.forceClose();
         } catch (Exception var5) {
         }

         throw var6;
      }
   }

   public byte getPacketSequence() {
      return this.readPacketSequence;
   }

   public void resetPacketSequence() {
      this.readPacketSequence = 0;
   }

   public PacketReader undecorateAll() {
      return this;
   }

   public PacketReader undecorate() {
      return this;
   }
}
