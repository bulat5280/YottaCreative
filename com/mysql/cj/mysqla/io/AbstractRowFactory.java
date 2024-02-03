package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.conf.ReadableProperty;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.io.ValueDecoder;
import com.mysql.cj.api.mysqla.io.ProtocolEntityFactory;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.api.mysqla.result.Resultset;
import com.mysql.cj.api.mysqla.result.ResultsetRow;

public abstract class AbstractRowFactory implements ProtocolEntityFactory<ResultsetRow> {
   protected ColumnDefinition columnDefinition;
   protected Resultset.Concurrency resultSetConcurrency;
   protected boolean canReuseRowPacketForBufferRow;
   protected ReadableProperty<Integer> useBufferRowSizeThreshold;
   protected ExceptionInterceptor exceptionInterceptor;
   protected ValueDecoder valueDecoder;

   public boolean canReuseRowPacketForBufferRow() {
      return this.canReuseRowPacketForBufferRow;
   }
}
