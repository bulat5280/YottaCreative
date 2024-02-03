package com.mysql.cj.mysqlx.io;

import com.google.protobuf.MessageLite;
import com.mysql.cj.core.exceptions.WrongArgumentException;

public interface MessageWriter {
   void write(MessageLite var1);

   void setMaxAllowedPacket(int var1);

   static int getTypeForMessageClass(Class<? extends MessageLite> msgClass) {
      Integer tag = (Integer)MessageConstants.MESSAGE_CLASS_TO_CLIENT_MESSAGE_TYPE.get(msgClass);
      if (tag == null) {
         throw new WrongArgumentException("No mapping to ClientMessages for message class " + msgClass.getSimpleName());
      } else {
         return tag;
      }
   }
}
