package com.mysql.cj.mysqlx.io;

import com.mysql.cj.core.exceptions.CJCommunicationsException;
import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLEngineResult.Status;

public class TlsEncryptingByteChannel extends AsynchronousSocketChannel {
   private AsynchronousSocketChannel channel;
   private SerializingBufferWriter bufferWriter;
   private SSLEngine sslEngine;
   private LinkedBlockingQueue<ByteBuffer> cipherTextBuffers = new LinkedBlockingQueue();

   public TlsEncryptingByteChannel(AsynchronousSocketChannel channel, SSLEngine sslEngine) {
      super((AsynchronousChannelProvider)null);
      this.channel = channel;
      this.bufferWriter = new SerializingBufferWriter(channel);
      this.sslEngine = sslEngine;
   }

   private boolean isDrained(ByteBuffer[] buffers) {
      ByteBuffer[] var2 = buffers;
      int var3 = buffers.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ByteBuffer b = var2[var4];
         if (b.hasRemaining()) {
            return false;
         }
      }

      return true;
   }

   public <A> void write(ByteBuffer[] srcs, int offset, int length, long timeout, TimeUnit unit, A attachment, CompletionHandler<Long, ? super A> handler) {
      try {
         long totalWriteSize = 0L;

         while(true) {
            ByteBuffer cipherText = this.getCipherTextBuffer();
            SSLEngineResult res = this.sslEngine.wrap(srcs, offset, length, cipherText);
            if (res.getStatus() != Status.OK) {
               handler.failed(new CJCommunicationsException("Unacceptable SSLEngine result: " + res), (Object)null);
            }

            totalWriteSize += (long)res.bytesConsumed();
            cipherText.flip();
            if (this.isDrained(srcs)) {
               Runnable successHandler = () -> {
                  handler.completed(totalWriteSize, (Object)null);
                  this.putCipherTextBuffer(cipherText);
               };
               this.bufferWriter.queueBuffer(cipherText, new TlsEncryptingByteChannel.ErrorPropagatingCompletionHandler(handler, successHandler));
               break;
            }

            this.bufferWriter.queueBuffer(cipherText, new TlsEncryptingByteChannel.ErrorPropagatingCompletionHandler(handler, () -> {
               this.putCipherTextBuffer(cipherText);
            }));
         }
      } catch (SSLException var16) {
         handler.failed(new CJCommunicationsException(var16), (Object)null);
      } catch (Throwable var17) {
         handler.failed(var17, (Object)null);
      }

   }

   private ByteBuffer getCipherTextBuffer() {
      ByteBuffer buf = (ByteBuffer)this.cipherTextBuffers.poll();
      if (buf == null) {
         return ByteBuffer.allocate(this.sslEngine.getSession().getPacketBufferSize());
      } else {
         buf.clear();
         return buf;
      }
   }

   private void putCipherTextBuffer(ByteBuffer buf) {
      if (this.cipherTextBuffers.size() < 10) {
         this.cipherTextBuffers.offer(buf);
      }

   }

   public AsynchronousSocketChannel bind(SocketAddress local) throws IOException {
      throw new UnsupportedOperationException();
   }

   public Set<SocketOption<?>> supportedOptions() {
      throw new UnsupportedOperationException();
   }

   public <T> T getOption(SocketOption<T> name) {
      throw new UnsupportedOperationException();
   }

   public <T> AsynchronousSocketChannel setOption(SocketOption<T> name, T value) throws IOException {
      throw new UnsupportedOperationException();
   }

   public AsynchronousSocketChannel shutdownInput() throws IOException {
      return this.channel.shutdownInput();
   }

   public AsynchronousSocketChannel shutdownOutput() throws IOException {
      return this.channel.shutdownOutput();
   }

   public SocketAddress getRemoteAddress() throws IOException {
      return this.channel.getRemoteAddress();
   }

   public <A> void connect(SocketAddress remote, A attachment, CompletionHandler<Void, ? super A> handler) {
      handler.failed(new UnsupportedOperationException(), (Object)null);
   }

   public Future<Void> connect(SocketAddress remote) {
      throw new UnsupportedOperationException();
   }

   public <A> void read(ByteBuffer dst, long timeout, TimeUnit unit, A attachment, CompletionHandler<Integer, ? super A> handler) {
      handler.failed(new UnsupportedOperationException(), (Object)null);
   }

   public Future<Integer> read(ByteBuffer dst) {
      throw new UnsupportedOperationException();
   }

   public <A> void read(ByteBuffer[] dsts, int offset, int length, long timeout, TimeUnit unit, A attachment, CompletionHandler<Long, ? super A> handler) {
      handler.failed(new UnsupportedOperationException(), (Object)null);
   }

   public <A> void write(ByteBuffer src, long timeout, TimeUnit unit, A attachment, CompletionHandler<Integer, ? super A> handler) {
      handler.failed(new UnsupportedOperationException(), (Object)null);
   }

   public Future<Integer> write(ByteBuffer src) {
      throw new UnsupportedOperationException();
   }

   public SocketAddress getLocalAddress() throws IOException {
      return this.channel.getLocalAddress();
   }

   public void close() throws IOException {
      this.channel.close();
   }

   public boolean isOpen() {
      return this.channel.isOpen();
   }

   private static class ErrorPropagatingCompletionHandler<V> implements CompletionHandler<V, Void> {
      private CompletionHandler<Long, ?> target;
      private Runnable success;

      public ErrorPropagatingCompletionHandler(CompletionHandler<Long, ?> target, Runnable success) {
         this.target = target;
         this.success = success;
      }

      public void completed(V result, Void attachment) {
         this.success.run();
      }

      public void failed(Throwable ex, Void attachment) {
         this.target.failed(ex, (Object)null);
      }
   }
}
