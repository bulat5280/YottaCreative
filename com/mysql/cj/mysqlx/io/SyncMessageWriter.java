package com.mysql.cj.mysqlx.io;

import com.google.protobuf.MessageLite;
import com.mysql.cj.api.io.PacketSentTimeHolder;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.CJCommunicationsException;
import com.mysql.cj.core.exceptions.CJPacketTooBigException;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SyncMessageWriter implements MessageWriter, PacketSentTimeHolder {
   static final int HEADER_LEN = 5;
   private BufferedOutputStream outputStream;
   private long lastPacketSentTime = 0L;
   private int maxAllowedPacket = -1;

   public SyncMessageWriter(BufferedOutputStream os) {
      this.outputStream = os;
   }

   public void write(MessageLite msg) {
      try {
         int type = MessageWriter.getTypeForMessageClass(msg.getClass());
         int size = 1 + msg.getSerializedSize();
         if (this.maxAllowedPacket > 0 && size > this.maxAllowedPacket) {
            throw new CJPacketTooBigException(Messages.getString("PacketTooBigException.1", new Object[]{size, this.maxAllowedPacket}));
         } else {
            byte[] sizeHeader = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(size).array();
            this.outputStream.write(sizeHeader);
            this.outputStream.write(type);
            msg.writeTo(this.outputStream);
            this.outputStream.flush();
            this.lastPacketSentTime = System.currentTimeMillis();
         }
      } catch (IOException var5) {
         throw new CJCommunicationsException("Unable to write message", var5);
      }
   }

   public long getLastPacketSentTime() {
      return this.lastPacketSentTime;
   }

   public void setMaxAllowedPacket(int maxAllowedPacket) {
      this.maxAllowedPacket = maxAllowedPacket;
   }
}
