package com.mysql.cj.mysqlx.io;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.MessageLite;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.CJCommunicationsException;
import com.mysql.cj.core.exceptions.CJPacketTooBigException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncMessageWriter implements MessageWriter {
   private static final int HEADER_LEN = 5;
   private int maxAllowedPacket = -1;
   private SerializingBufferWriter bufferWriter;

   public AsyncMessageWriter(AsynchronousSocketChannel channel) {
      this.bufferWriter = new SerializingBufferWriter(channel);
   }

   public void write(MessageLite msg) {
      CompletableFuture<Void> f = new CompletableFuture();
      this.writeAsync(msg, new ErrorToFutureCompletionHandler(f, () -> {
         f.complete((Object)null);
      }));

      try {
         f.get();
      } catch (ExecutionException var4) {
         throw new CJCommunicationsException("Failed to write message", var4.getCause());
      } catch (InterruptedException var5) {
         throw new CJCommunicationsException("Failed to write message", var5);
      }
   }

   public void writeAsync(MessageLite msg, CompletionHandler<Long, Void> callback) {
      int type = MessageWriter.getTypeForMessageClass(msg.getClass());
      int size = msg.getSerializedSize();
      int payloadSize = size + 1;
      if (this.maxAllowedPacket > 0 && payloadSize > this.maxAllowedPacket) {
         throw new CJPacketTooBigException(Messages.getString("PacketTooBigException.1", new Object[]{size, this.maxAllowedPacket}));
      } else {
         ByteBuffer messageBuf = ByteBuffer.allocate(5 + size).order(ByteOrder.LITTLE_ENDIAN).putInt(payloadSize);
         messageBuf.put((byte)type);

         try {
            msg.writeTo(CodedOutputStream.newInstance(messageBuf.array(), 5, size + 5));
            messageBuf.position(messageBuf.limit());
         } catch (IOException var8) {
            throw new CJCommunicationsException("Unable to write message", var8);
         }

         messageBuf.flip();
         this.bufferWriter.queueBuffer(messageBuf, callback);
      }
   }

   public void setMaxAllowedPacket(int maxAllowedPacket) {
      this.maxAllowedPacket = maxAllowedPacket;
   }

   public void setChannel(AsynchronousSocketChannel channel) {
      this.bufferWriter.setChannel(channel);
   }
}
