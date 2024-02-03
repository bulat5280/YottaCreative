package com.mysql.cj.mysqlx.io;

import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.mysql.cj.core.exceptions.CJCommunicationsException;

public interface MessageReader {
   Class<? extends GeneratedMessage> getNextMessageClass();

   <T extends GeneratedMessage> T read(Class<T> var1);

   static <T extends GeneratedMessage> T parseNotice(ByteString payload, Class<T> noticeClass) {
      try {
         Parser<T> parser = (Parser)MessageConstants.MESSAGE_CLASS_TO_PARSER.get(noticeClass);
         return (GeneratedMessage)parser.parseFrom(payload);
      } catch (InvalidProtocolBufferException var3) {
         throw new CJCommunicationsException(var3);
      }
   }
}
