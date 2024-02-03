package com.mysql.cj.mysqlx.io;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.ReadPendingException;
import java.nio.channels.WritePendingException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class SerializingBufferWriter implements CompletionHandler<Long, Void> {
   protected AsynchronousSocketChannel channel;
   private Queue<ByteBuffer> pendingWrites = new LinkedList();
   private Map<Integer, CompletionHandler<Long, Void>> bufToHandler = new ConcurrentHashMap();

   public SerializingBufferWriter(AsynchronousSocketChannel channel) {
      this.channel = channel;
   }

   private void initiateWrite() {
      try {
         ByteBuffer[] bufs = (ByteBuffer[])this.pendingWrites.toArray(new ByteBuffer[this.pendingWrites.size()]);
         this.channel.write(bufs, 0, this.pendingWrites.size(), 0L, TimeUnit.MILLISECONDS, (Object)null, this);
      } catch (WritePendingException | ReadPendingException var2) {
         return;
      } catch (Throwable var3) {
         this.failed(var3, (Void)null);
      }

   }

   public void queueBuffer(ByteBuffer buf, CompletionHandler<Long, Void> callback) {
      if (callback != null) {
         this.bufToHandler.put(System.identityHashCode(buf), callback);
      }

      synchronized(this.pendingWrites) {
         this.pendingWrites.add(buf);
         if (this.pendingWrites.size() == 1) {
            this.initiateWrite();
         }

      }
   }

   public void completed(Long bytesWritten, Void v) {
      LinkedList<ByteBuffer> completedWrites = new LinkedList();
      synchronized(this.pendingWrites) {
         while(this.pendingWrites.peek() != null && !((ByteBuffer)this.pendingWrites.peek()).hasRemaining()) {
            completedWrites.add(this.pendingWrites.remove());
         }

         Stream var10000 = completedWrites.stream().map(System::identityHashCode);
         Map var10001 = this.bufToHandler;
         var10001.getClass();
         var10000.map(var10001::remove).filter(Objects::nonNull).forEach((l) -> {
            try {
               l.completed(0L, (Object)null);
            } catch (Throwable var4) {
               Throwable ex = var4;

               try {
                  l.failed(ex, (Object)null);
               } catch (Throwable var3) {
                  var3.printStackTrace();
               }
            }

         });
         if (this.pendingWrites.size() > 0) {
            this.initiateWrite();
         }

      }
   }

   public void failed(Throwable t, Void v) {
      try {
         this.channel.close();
      } catch (Exception var6) {
      }

      this.bufToHandler.values().forEach((l) -> {
         try {
            l.failed(t, (Object)null);
         } catch (Exception var3) {
         }

      });
      this.bufToHandler.clear();
      synchronized(this.pendingWrites) {
         this.pendingWrites.clear();
      }
   }

   public void setChannel(AsynchronousSocketChannel channel) {
      this.channel = channel;
   }
}
