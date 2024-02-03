package com.mysql.cj.mysqlx.io;

import com.mysql.cj.core.exceptions.AssertionFailedException;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousByteChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Future;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;

public class TlsDecryptingByteChannel implements AsynchronousByteChannel, CompletionHandler<Integer, Void> {
   private static final ByteBuffer emptyBuffer = ByteBuffer.allocate(0);
   private AsynchronousByteChannel in;
   private SSLEngine sslEngine;
   private ByteBuffer cipherTextBuffer;
   private ByteBuffer clearTextBuffer;
   private CompletionHandler<Integer, ?> handler;
   private ByteBuffer dst;

   public TlsDecryptingByteChannel(AsynchronousByteChannel in, SSLEngine sslEngine) {
      this.in = in;
      this.sslEngine = sslEngine;
      this.cipherTextBuffer = ByteBuffer.allocate(sslEngine.getSession().getPacketBufferSize());
      this.cipherTextBuffer.flip();
      this.clearTextBuffer = ByteBuffer.allocate(sslEngine.getSession().getApplicationBufferSize());
      this.clearTextBuffer.flip();
   }

   public void completed(Integer result, Void attachment) {
      if (result < 0) {
         CompletionHandler<Integer, ?> h = this.handler;
         this.handler = null;
         h.completed(result, (Object)null);
      } else {
         this.cipherTextBuffer.flip();
         this.decryptAndDispatch();
      }
   }

   public void failed(Throwable exc, Void attachment) {
      CompletionHandler<Integer, ?> h = this.handler;
      this.handler = null;
      h.failed(exc, (Object)null);
   }

   private synchronized void decryptAndDispatch() {
      try {
         this.clearTextBuffer.clear();
         SSLEngineResult res = this.sslEngine.unwrap(this.cipherTextBuffer, this.clearTextBuffer);
         switch(res.getStatus()) {
         case BUFFER_UNDERFLOW:
            this.cipherTextBuffer.compact();
            this.in.read(this.cipherTextBuffer, (Object)null, this);
            return;
         case BUFFER_OVERFLOW:
            throw new BufferOverflowException();
         case OK:
            this.clearTextBuffer.flip();
            this.dispatchData();
            break;
         case CLOSED:
            this.handler.completed(-1, (Object)null);
         }
      } catch (Throwable var2) {
         this.failed(var2, (Void)null);
      }

   }

   public <A> void read(ByteBuffer dest, A attachment, CompletionHandler<Integer, ? super A> hdlr) {
      try {
         if (this.handler != null) {
            hdlr.completed(0, (Object)null);
         }

         this.handler = hdlr;
         this.dst = dest;
         if (this.clearTextBuffer.hasRemaining()) {
            this.dispatchData();
         } else if (this.cipherTextBuffer.hasRemaining()) {
            this.decryptAndDispatch();
         } else {
            this.cipherTextBuffer.clear();
            this.in.read(this.cipherTextBuffer, (Object)null, this);
         }
      } catch (Throwable var5) {
         hdlr.failed(var5, (Object)null);
      }

   }

   private synchronized void dispatchData() {
      final int transferred = Math.min(this.dst.remaining(), this.clearTextBuffer.remaining());
      if (this.clearTextBuffer.remaining() > this.dst.remaining()) {
         int newLimit = this.clearTextBuffer.position() + transferred;
         ByteBuffer src = this.clearTextBuffer.duplicate();
         src.limit(newLimit);
         this.dst.put(src);
         this.clearTextBuffer.position(this.clearTextBuffer.position() + transferred);
      } else {
         this.dst.put(this.clearTextBuffer);
      }

      final CompletionHandler<Integer, ?> h = this.handler;
      this.handler = null;
      if (this.in.isOpen()) {
         this.in.read(emptyBuffer, (Object)null, new CompletionHandler<Integer, Void>() {
            public void completed(Integer result, Void attachment) {
               h.completed(transferred, (Object)null);
            }

            public void failed(Throwable t, Void attachment) {
               t.printStackTrace();
               h.failed(AssertionFailedException.shouldNotHappen(new Exception(t)), (Object)null);
            }
         });
      } else {
         h.completed(transferred, (Object)null);
      }

   }

   public void close() throws IOException {
      this.in.close();
   }

   public boolean isOpen() {
      return this.in.isOpen();
   }

   public Future<Integer> read(ByteBuffer dest) {
      throw new UnsupportedOperationException("This channel does not support direct reads");
   }

   public <A> void write(ByteBuffer src, A attachment, CompletionHandler<Integer, ? super A> hdlr) {
      hdlr.failed(new UnsupportedOperationException("This channel does not support writes"), (Object)null);
   }

   public Future<Integer> write(ByteBuffer src) {
      throw new UnsupportedOperationException("This channel does not support writes");
   }
}
