package com.mysql.cj.mysqlx.io;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.mysql.cj.core.exceptions.AssertionFailedException;
import com.mysql.cj.core.exceptions.CJCommunicationsException;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.mysqlx.MysqlxError;
import com.mysql.cj.mysqlx.protobuf.Mysqlx;
import com.mysql.cj.mysqlx.protobuf.MysqlxNotice;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.AsynchronousByteChannel;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AsyncMessageReader implements CompletionHandler<Integer, Void>, MessageReader {
   private int messageSize;
   private int messageType;
   private ByteBuffer headerBuf;
   private ByteBuffer messageBuf;
   private AsynchronousByteChannel channel;
   private AsyncMessageReader.MessageListener currentMessageListener;
   private BlockingQueue<AsyncMessageReader.MessageListener> messageListenerQueue;
   private CompletableFuture<Class<? extends GeneratedMessage>> pendingMsgClass;
   private Object pendingMsgMonitor;
   private boolean stopAfterNextMessage;
   private AsyncMessageReader.ReadingState state;

   public AsyncMessageReader(AsynchronousByteChannel channel) {
      this.headerBuf = ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN);
      this.messageListenerQueue = new LinkedBlockingQueue();
      this.pendingMsgMonitor = new Object();
      this.stopAfterNextMessage = false;
      this.channel = channel;
   }

   public void start() {
      this.readHeader();
   }

   public void stopAfterNextMessage() {
      this.stopAfterNextMessage = true;
   }

   public void pushMessageListener(AsyncMessageReader.MessageListener l) {
      if (!this.channel.isOpen()) {
         throw new CJCommunicationsException("async closed");
      } else {
         this.messageListenerQueue.add(l);
      }
   }

   private AsyncMessageReader.MessageListener getMessageListener(boolean block) {
      if (this.currentMessageListener == null) {
         if (block) {
            try {
               this.currentMessageListener = (AsyncMessageReader.MessageListener)this.messageListenerQueue.take();
            } catch (InterruptedException var3) {
               throw new CJCommunicationsException(var3);
            }
         } else {
            this.currentMessageListener = (AsyncMessageReader.MessageListener)this.messageListenerQueue.poll();
         }
      }

      return this.currentMessageListener;
   }

   private void readHeader() {
      this.state = AsyncMessageReader.ReadingState.READING_HEADER;
      if (this.headerBuf.position() < 5) {
         this.channel.read(this.headerBuf, (Object)null, this);
      } else {
         this.headerBuf.flip();
         this.messageSize = this.headerBuf.getInt() - 1;
         this.messageType = this.headerBuf.get();
         this.headerBuf.clear();
         this.state = AsyncMessageReader.ReadingState.READING_MESSAGE;
         this.messageBuf = ByteBuffer.allocate(this.messageSize);
         if (this.messageSize > 0) {
            synchronized(this) {
               this.channel.read(this.messageBuf, (Object)null, this);
            }
         } else {
            this.readMessage();
         }

      }
   }

   private void readMessage() {
      if (this.messageBuf.position() < this.messageSize) {
         this.channel.read(this.messageBuf, (Object)null, this);
      } else {
         int type = this.messageType;
         ByteBuffer buf = this.messageBuf;
         this.messageType = 0;
         this.messageBuf = null;
         Class<? extends GeneratedMessage> messageClass = (Class)MessageConstants.MESSAGE_TYPE_TO_CLASS.get(type);
         if (messageClass == null) {
            Mysqlx.ServerMessages.Type serverMessageMapping = Mysqlx.ServerMessages.Type.valueOf(type);
            throw AssertionFailedException.shouldNotHappen("Unknown message type: " + type + " (server messages mapping: " + serverMessageMapping + ")");
         } else {
            buf.flip();
            this.dispatchMessage(messageClass, this.parseMessage(messageClass, buf));
            if (this.stopAfterNextMessage) {
               this.stopAfterNextMessage = false;
               this.headerBuf.clear();
            } else {
               this.readHeader();
            }
         }
      }
   }

   private GeneratedMessage parseMessage(Class<? extends GeneratedMessage> messageClass, ByteBuffer buf) {
      try {
         Parser<? extends GeneratedMessage> parser = (Parser)MessageConstants.MESSAGE_CLASS_TO_PARSER.get(messageClass);
         return (GeneratedMessage)parser.parseFrom(CodedInputStream.newInstance(buf));
      } catch (InvalidProtocolBufferException var4) {
         throw AssertionFailedException.shouldNotHappen((Exception)var4);
      }
   }

   private void dispatchMessage(Class<? extends GeneratedMessage> messageClass, GeneratedMessage message) {
      if (messageClass == MysqlxNotice.Frame.class && ((MysqlxNotice.Frame)message).getScope() == MysqlxNotice.Frame.Scope.GLOBAL) {
         throw new RuntimeException("TODO: implement me");
      } else {
         if (this.getMessageListener(false) == null) {
            synchronized(this.pendingMsgMonitor) {
               this.pendingMsgClass = CompletableFuture.completedFuture(messageClass);
               this.pendingMsgMonitor.notify();
            }
         }

         this.getMessageListener(true);
         synchronized(this.pendingMsgMonitor) {
            boolean currentListenerDone = (Boolean)this.currentMessageListener.apply(messageClass, message);
            if (currentListenerDone) {
               this.currentMessageListener = null;
            }

            this.pendingMsgClass = null;
         }
      }
   }

   public void completed(Integer bytesRead, Void v) {
      if (bytesRead < 0) {
         boolean var17 = false;

         try {
            var17 = true;
            this.channel.close();
            var17 = false;
         } catch (IOException var20) {
            throw AssertionFailedException.shouldNotHappen((Exception)var20);
         } finally {
            if (var17) {
               if (this.currentMessageListener == null) {
                  this.currentMessageListener = (AsyncMessageReader.MessageListener)this.messageListenerQueue.poll();
               }

               if (this.currentMessageListener != null) {
                  this.currentMessageListener.closed();
               }

               this.currentMessageListener = null;
               synchronized(this.pendingMsgMonitor) {
                  this.pendingMsgClass = new CompletableFuture();
                  this.pendingMsgClass.completeExceptionally(new CJCommunicationsException("Socket closed"));
                  this.pendingMsgMonitor.notify();
               }
            }
         }

         if (this.currentMessageListener == null) {
            this.currentMessageListener = (AsyncMessageReader.MessageListener)this.messageListenerQueue.poll();
         }

         if (this.currentMessageListener != null) {
            this.currentMessageListener.closed();
         }

         this.currentMessageListener = null;
         synchronized(this.pendingMsgMonitor) {
            this.pendingMsgClass = new CompletableFuture();
            this.pendingMsgClass.completeExceptionally(new CJCommunicationsException("Socket closed"));
            this.pendingMsgMonitor.notify();
         }
      } else {
         try {
            if (this.state == AsyncMessageReader.ReadingState.READING_HEADER) {
               this.readHeader();
            } else {
               this.readMessage();
            }
         } catch (Throwable var24) {
            Throwable t = var24;

            try {
               this.channel.close();
            } catch (Exception var23) {
            }

            if (this.currentMessageListener != null) {
               try {
                  this.currentMessageListener.error(t);
               } catch (Exception var22) {
               }
            }

            this.messageListenerQueue.forEach((l) -> {
               try {
                  l.error(var24);
               } catch (Exception var3) {
               }

            });
            synchronized(this.pendingMsgMonitor) {
               this.pendingMsgClass = new CompletableFuture();
               this.pendingMsgClass.completeExceptionally(t);
               this.pendingMsgMonitor.notify();
            }

            this.messageListenerQueue.clear();
         }

      }
   }

   public void failed(Throwable exc, Void v) {
      if (this.getMessageListener(false) != null) {
         synchronized(this.pendingMsgMonitor) {
            this.pendingMsgMonitor.notify();
         }

         if (AsynchronousCloseException.class.equals(exc.getClass())) {
            this.currentMessageListener.closed();
         } else {
            this.currentMessageListener.error(exc);
         }
      }

      this.currentMessageListener = null;
   }

   public Class<? extends GeneratedMessage> getNextMessageClass() {
      Class msgClass;
      synchronized(this.pendingMsgMonitor) {
         if (!this.channel.isOpen()) {
            throw new CJCommunicationsException("async closed");
         }

         while(this.pendingMsgClass == null) {
            try {
               this.pendingMsgMonitor.wait();
            } catch (InterruptedException var7) {
               throw new CJCommunicationsException(var7);
            }
         }

         try {
            msgClass = (Class)this.pendingMsgClass.get();
         } catch (ExecutionException var5) {
            throw new CJCommunicationsException("Failed to peek pending message", var5.getCause());
         } catch (InterruptedException var6) {
            throw new CJCommunicationsException(var6);
         }
      }

      if (Mysqlx.Error.class.equals(msgClass)) {
         this.read(msgClass);
      }

      return msgClass;
   }

   public <T extends GeneratedMessage> T read(Class<T> expectedClass) {
      AsyncMessageReader.SyncReader<T> r = new AsyncMessageReader.SyncReader(this, expectedClass);
      return (GeneratedMessage)r.read();
   }

   public void setChannel(AsynchronousByteChannel channel) {
      this.channel = channel;
   }

   private static final class SyncReader<T> implements AsyncMessageReader.MessageListener {
      private CompletableFuture<Function<BiFunction<Class<? extends GeneratedMessage>, GeneratedMessage, T>, T>> future = new CompletableFuture();
      private Class<T> expectedClass;

      public SyncReader(AsyncMessageReader rdr, Class<T> expectedClass) {
         this.expectedClass = expectedClass;
         rdr.pushMessageListener(this);
      }

      public Boolean apply(Class<? extends GeneratedMessage> msgClass, GeneratedMessage msg) {
         this.future.complete((c) -> {
            return c.apply(msgClass, msg);
         });
         return true;
      }

      public void error(Throwable ex) {
         this.future.completeExceptionally(ex);
      }

      public void closed() {
         this.future.completeExceptionally(new CJCommunicationsException("Socket closed"));
      }

      public T read() {
         try {
            return this.future.thenApply((f) -> {
               return f.apply((msgClass, msg) -> {
                  if (Mysqlx.Error.class.equals(msgClass)) {
                     throw new MysqlxError((Mysqlx.Error)Mysqlx.Error.class.cast(msg));
                  } else if (!msgClass.equals(this.expectedClass)) {
                     throw new WrongArgumentException("Unexpected message class. Expected '" + this.expectedClass.getSimpleName() + "' but actually received '" + msgClass.getSimpleName() + "'");
                  } else {
                     return this.expectedClass.cast(msg);
                  }
               });
            }).get();
         } catch (ExecutionException var2) {
            if (MysqlxError.class.equals(var2.getCause().getClass())) {
               throw new MysqlxError((MysqlxError)var2.getCause());
            } else {
               throw new CJCommunicationsException(var2.getCause().getMessage(), var2.getCause());
            }
         } catch (InterruptedException var3) {
            throw new CJCommunicationsException(var3);
         }
      }
   }

   private static enum ReadingState {
      READING_HEADER,
      READING_MESSAGE;
   }

   @FunctionalInterface
   public interface MessageListener extends BiFunction<Class<? extends GeneratedMessage>, GeneratedMessage, Boolean> {
      default void closed() {
      }

      default void error(Throwable ex) {
         ex.printStackTrace();
      }
   }
}
