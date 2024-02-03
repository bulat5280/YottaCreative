package com.mysql.cj.mysqlx.io;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.mysql.cj.core.exceptions.AssertionFailedException;
import com.mysql.cj.core.exceptions.CJCommunicationsException;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.core.io.FullReadInputStream;
import com.mysql.cj.mysqlx.MysqlxError;
import com.mysql.cj.mysqlx.protobuf.Mysqlx;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SyncMessageReader implements MessageReader {
   private FullReadInputStream inputStream;
   private boolean hasReadHeader = false;
   private int messageType = -1;
   private int payloadSize = -1;

   public SyncMessageReader(FullReadInputStream inputStream) {
      this.inputStream = inputStream;
   }

   private void readHeader() throws IOException {
      byte[] len = new byte[4];
      this.inputStream.readFully(len);
      this.payloadSize = ByteBuffer.wrap(len).order(ByteOrder.LITTLE_ENDIAN).getInt();
      this.messageType = this.inputStream.read();
      this.hasReadHeader = true;
   }

   private void clearHeader() {
      this.hasReadHeader = false;
      this.messageType = -1;
      this.payloadSize = -1;
   }

   private int getNextMessageType() {
      if (!this.hasReadHeader) {
         try {
            this.readHeader();
         } catch (IOException var2) {
            throw new CJCommunicationsException("Cannot read packet header", var2);
         }
      }

      return this.messageType;
   }

   public Class<? extends GeneratedMessage> getNextMessageClass() {
      int type = this.getNextMessageType();
      Class<? extends GeneratedMessage> messageClass = (Class)MessageConstants.MESSAGE_TYPE_TO_CLASS.get(type);
      if (messageClass == null) {
         Mysqlx.ServerMessages.Type serverMessageMapping = Mysqlx.ServerMessages.Type.valueOf(type);
         throw AssertionFailedException.shouldNotHappen("Unknown message type: " + type + " (server messages mapping: " + serverMessageMapping + ")");
      } else if (messageClass == Mysqlx.Error.class) {
         throw new MysqlxError((Mysqlx.Error)this.readAndParse((Parser)MessageConstants.MESSAGE_CLASS_TO_PARSER.get(Mysqlx.Error.class)));
      } else {
         return messageClass;
      }
   }

   private <T extends GeneratedMessage> T readAndParse(Parser<T> parser) {
      byte[] packet = new byte[this.payloadSize - 1];

      try {
         this.inputStream.readFully(packet);
      } catch (IOException var10) {
         throw new CJCommunicationsException("Cannot read packet payload", var10);
      }

      GeneratedMessage var3;
      try {
         var3 = (GeneratedMessage)parser.parseFrom(packet);
      } catch (InvalidProtocolBufferException var8) {
         throw new WrongArgumentException(var8);
      } finally {
         this.clearHeader();
      }

      return var3;
   }

   public <T extends GeneratedMessage> T read(Class<T> expectedClass) {
      Class<? extends GeneratedMessage> messageClass = this.getNextMessageClass();
      if (expectedClass != messageClass) {
         throw new WrongArgumentException("Unexpected message class. Expected '" + expectedClass.getSimpleName() + "' but actually received '" + messageClass.getSimpleName() + "'");
      } else {
         return this.readAndParse((Parser)MessageConstants.MESSAGE_CLASS_TO_PARSER.get(messageClass));
      }
   }
}
