package com.mysql.cj.mysqlx.io;

import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.log.Log;
import com.mysql.cj.core.exceptions.CJCommunicationsException;
import com.mysql.cj.core.io.ExportControlled;
import com.mysql.cj.mysqla.io.MysqlaSocketConnection;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;

public class MysqlxProtocolFactory {
   public static MysqlxProtocol getInstance(String host, int port, PropertySet propertySet) {
      if ((Boolean)propertySet.getBooleanReadableProperty("mysqlx.useAsyncProtocol").getValue()) {
         return getAsyncInstance(host, port, propertySet);
      } else {
         MysqlaSocketConnection socketConnection = new MysqlaSocketConnection();
         Properties socketFactoryProperties = new Properties();
         socketConnection.connect(host, port, socketFactoryProperties, propertySet, (ExceptionInterceptor)null, (Log)null, 0);
         MessageReader messageReader = new SyncMessageReader(socketConnection.getMysqlInput());
         MessageWriter messageWriter = new SyncMessageWriter(socketConnection.getMysqlOutput());
         return new MysqlxProtocol(messageReader, messageWriter, socketConnection.getMysqlSocket(), propertySet);
      }
   }

   public static MysqlxProtocol getAsyncInstance(String host, int port, PropertySet propertySet) {
      try {
         AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
         channel.setOption(StandardSocketOptions.SO_SNDBUF, 131072);
         channel.setOption(StandardSocketOptions.SO_RCVBUF, 131072);
         Future<Void> connectPromise = channel.connect(new InetSocketAddress(host, port));
         connectPromise.get();
         AsyncMessageReader messageReader = new AsyncMessageReader(channel);
         messageReader.start();
         AsyncMessageWriter messageWriter = new AsyncMessageWriter(channel);
         MysqlxProtocol protocol = new MysqlxProtocol(messageReader, messageWriter, channel, propertySet);
         if ((Boolean)propertySet.getBooleanReadableProperty("useSSL").getValue()) {
            messageReader.stopAfterNextMessage();
            protocol.setCapability("tls", true);
            SSLContext sslContext = ExportControlled.getSSLContext(propertySet, (ExceptionInterceptor)null);
            SSLEngine sslEngine = sslContext.createSSLEngine();
            sslEngine.setUseClientMode(true);
            sslEngine.setEnabledProtocols(new String[]{"TLSv1.1", "TLSv1"});
            performTlsHandshake(sslEngine, channel);
            messageReader.setChannel(new TlsDecryptingByteChannel(channel, sslEngine));
            messageWriter.setChannel(new TlsEncryptingByteChannel(channel, sslEngine));
            messageReader.start();
         }

         return protocol;
      } catch (InterruptedException | ExecutionException | RuntimeException | IOException var10) {
         throw new CJCommunicationsException(var10);
      }
   }

   private static void performTlsHandshake(SSLEngine sslEngine, AsynchronousSocketChannel channel) throws SSLException {
      sslEngine.beginHandshake();
      ByteBuffer clear = ByteBuffer.allocate(16916);
      ByteBuffer cipher = ByteBuffer.allocate(sslEngine.getSession().getPacketBufferSize());
      HandshakeStatus handshakeStatus = sslEngine.getHandshakeStatus();

      while(handshakeStatus != HandshakeStatus.FINISHED && handshakeStatus != HandshakeStatus.NOT_HANDSHAKING) {
         SSLEngineResult res;
         if (handshakeStatus == HandshakeStatus.NEED_WRAP) {
            cipher.clear();
            res = sslEngine.wrap(clear, cipher);
            if (res.getStatus() != Status.OK) {
               throw new CJCommunicationsException("Unacceptable SSLEngine result: " + res);
            }

            handshakeStatus = sslEngine.getHandshakeStatus();
            cipher.flip();
            write(channel, cipher);
         } else if (handshakeStatus == HandshakeStatus.NEED_UNWRAP) {
            cipher.clear();
            read(channel, cipher);
            cipher.flip();

            while(handshakeStatus == HandshakeStatus.NEED_UNWRAP) {
               res = sslEngine.unwrap(cipher, clear);
               if (res.getStatus() != Status.OK) {
                  throw new CJCommunicationsException("Unacceptable SSLEngine result: " + res);
               }

               handshakeStatus = sslEngine.getHandshakeStatus();
               if (handshakeStatus == HandshakeStatus.NEED_TASK) {
                  sslEngine.getDelegatedTask().run();
                  handshakeStatus = sslEngine.getHandshakeStatus();
               }
            }
         }
      }

   }

   private static void write(final AsynchronousSocketChannel channel, final ByteBuffer data) {
      final CompletableFuture<Void> f = new CompletableFuture();
      final int bytesToWrite = data.limit();
      CompletionHandler<Integer, Void> handler = new CompletionHandler<Integer, Void>() {
         public void completed(Integer bytesWritten, Void nothing) {
            if (bytesWritten < bytesToWrite) {
               channel.write(data, (Object)null, this);
            } else {
               f.complete((Object)null);
            }

         }

         public void failed(Throwable exc, Void nothing) {
            f.completeExceptionally(exc);
         }
      };
      channel.write(data, (Object)null, handler);

      try {
         f.get();
      } catch (ExecutionException | InterruptedException var6) {
         throw new CJCommunicationsException(var6);
      }
   }

   private static void read(AsynchronousSocketChannel channel, ByteBuffer data) {
      Future f = channel.read(data);

      try {
         f.get();
      } catch (ExecutionException | InterruptedException var4) {
         throw new CJCommunicationsException(var4);
      }
   }
}
