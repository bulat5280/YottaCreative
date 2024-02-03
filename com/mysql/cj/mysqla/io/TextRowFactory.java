package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.conf.ReadableProperty;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.mysqla.io.NativeProtocol;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.io.ProtocolEntityFactory;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.api.mysqla.result.Resultset;
import com.mysql.cj.api.mysqla.result.ResultsetRow;
import com.mysql.cj.core.io.MysqlTextValueDecoder;
import com.mysql.cj.mysqla.result.ByteArrayRow;
import com.mysql.cj.mysqla.result.TextBufferRow;

public class TextRowFactory extends AbstractRowFactory implements ProtocolEntityFactory<ResultsetRow> {
   protected ColumnDefinition columnDefinition;
   protected Resultset.Concurrency resultSetConcurrency;
   protected boolean canReuseRowPacketForBufferRow;
   protected ReadableProperty<Integer> useBufferRowSizeThreshold;
   protected ExceptionInterceptor exceptionInterceptor;

   public TextRowFactory(MysqlaProtocol protocol, ColumnDefinition columnDefinition, Resultset.Concurrency resultSetConcurrency, boolean canReuseRowPacketForBufferRow) {
      this.columnDefinition = columnDefinition;
      this.resultSetConcurrency = resultSetConcurrency;
      this.canReuseRowPacketForBufferRow = canReuseRowPacketForBufferRow;
      this.useBufferRowSizeThreshold = protocol.getPropertySet().getMemorySizeReadableProperty("largeRowSizeThreshold");
      this.exceptionInterceptor = protocol.getExceptionInterceptor();
      this.valueDecoder = new MysqlTextValueDecoder();
   }

   public ResultsetRow createFromPacketPayload(PacketPayload rowPacket) {
      boolean useBufferRow = this.canReuseRowPacketForBufferRow || this.columnDefinition.hasLargeFields() || rowPacket.getPayloadLength() >= (Integer)this.useBufferRowSizeThreshold.getValue();
      if (this.resultSetConcurrency != Resultset.Concurrency.UPDATABLE && useBufferRow) {
         return new TextBufferRow(rowPacket, this.columnDefinition, this.exceptionInterceptor, this.valueDecoder);
      } else {
         byte[][] rowBytes = new byte[this.columnDefinition.getFields().length][];

         for(int i = 0; i < this.columnDefinition.getFields().length; ++i) {
            rowBytes[i] = rowPacket.readBytes(NativeProtocol.StringSelfDataType.STRING_LENENC);
         }

         return new ByteArrayRow(rowBytes, this.exceptionInterceptor);
      }
   }

   public boolean canReuseRowPacketForBufferRow() {
      return this.canReuseRowPacketForBufferRow;
   }
}
