package com.mysql.cj.mysqla.result;

import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.mysqla.io.PacketPayload;

public abstract class AbstractBufferRow extends AbstractResultsetRow {
   protected PacketPayload rowFromServer;
   protected int homePosition = 0;
   protected int lastRequestedIndex = -1;
   protected int lastRequestedPos;

   protected AbstractBufferRow(ExceptionInterceptor exceptionInterceptor) {
      super(exceptionInterceptor);
   }

   abstract int findAndSeekToOffset(int var1);
}
