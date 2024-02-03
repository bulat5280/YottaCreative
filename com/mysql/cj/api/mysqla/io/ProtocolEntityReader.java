package com.mysql.cj.api.mysqla.io;

import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.api.mysqla.result.ProtocolEntity;
import com.mysql.cj.core.exceptions.CJOperationNotSupportedException;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import java.io.IOException;

public interface ProtocolEntityReader<T extends ProtocolEntity> {
   default T read(ProtocolEntityFactory<T> sf) throws IOException {
      throw (CJOperationNotSupportedException)ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not allowed");
   }

   default T read(int maxRows, boolean streamResults, PacketPayload resultPacket, ColumnDefinition metadata, ProtocolEntityFactory<T> protocolEntityFactory) throws IOException {
      throw (CJOperationNotSupportedException)ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not allowed");
   }
}
