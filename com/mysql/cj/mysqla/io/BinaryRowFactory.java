package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.mysqla.io.NativeProtocol;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.io.ProtocolEntityFactory;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.api.mysqla.result.Resultset;
import com.mysql.cj.api.mysqla.result.ResultsetRow;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.io.MysqlBinaryValueDecoder;
import com.mysql.cj.core.result.Field;
import com.mysql.cj.mysqla.MysqlaUtils;
import com.mysql.cj.mysqla.result.BinaryBufferRow;
import com.mysql.cj.mysqla.result.ByteArrayRow;

public class BinaryRowFactory extends AbstractRowFactory implements ProtocolEntityFactory<ResultsetRow> {
   public BinaryRowFactory(MysqlaProtocol protocol, ColumnDefinition columnDefinition, Resultset.Concurrency resultSetConcurrency, boolean canReuseRowPacketForBufferRow) {
      this.columnDefinition = columnDefinition;
      this.resultSetConcurrency = resultSetConcurrency;
      this.canReuseRowPacketForBufferRow = canReuseRowPacketForBufferRow;
      this.useBufferRowSizeThreshold = protocol.getPropertySet().getMemorySizeReadableProperty("largeRowSizeThreshold");
      this.exceptionInterceptor = protocol.getExceptionInterceptor();
      this.valueDecoder = new MysqlBinaryValueDecoder();
   }

   public ResultsetRow createFromPacketPayload(PacketPayload rowPacket) {
      boolean useBufferRow = this.canReuseRowPacketForBufferRow || this.columnDefinition.hasLargeFields() || rowPacket.getPayloadLength() >= (Integer)this.useBufferRowSizeThreshold.getValue();
      rowPacket.setPosition(rowPacket.getPosition() + 1);
      return (ResultsetRow)(this.resultSetConcurrency != Resultset.Concurrency.UPDATABLE && useBufferRow ? new BinaryBufferRow(rowPacket, this.columnDefinition, this.exceptionInterceptor, this.valueDecoder) : this.unpackBinaryResultSetRow(this.columnDefinition.getFields(), rowPacket));
   }

   public boolean canReuseRowPacketForBufferRow() {
      return this.canReuseRowPacketForBufferRow;
   }

   private final ResultsetRow unpackBinaryResultSetRow(Field[] fields, PacketPayload binaryData) {
      int numFields = fields.length;
      byte[][] unpackedRowBytes = new byte[numFields][];
      int nullCount = (numFields + 9) / 8;
      int nullMaskPos = binaryData.getPosition();
      binaryData.setPosition(nullMaskPos + nullCount);
      int bit = 4;
      byte[] buf = binaryData.getByteBuffer();

      for(int i = 0; i < numFields; ++i) {
         if ((buf[nullMaskPos] & bit) != 0) {
            unpackedRowBytes[i] = null;
         } else {
            this.extractNativeEncodedColumn(binaryData, fields, i, unpackedRowBytes);
         }

         if (((bit <<= 1) & 255) == 0) {
            bit = 1;
            ++nullMaskPos;
         }
      }

      return new ByteArrayRow(unpackedRowBytes, this.exceptionInterceptor, new MysqlBinaryValueDecoder());
   }

   private final void extractNativeEncodedColumn(PacketPayload binaryData, Field[] fields, int columnIndex, byte[][] unpackedRowData) {
      int type = fields[columnIndex].getMysqlTypeId();
      int len = MysqlaUtils.getBinaryEncodedLength(type);
      if (type != 6) {
         if (len == 0) {
            unpackedRowData[columnIndex] = binaryData.readBytes(NativeProtocol.StringSelfDataType.STRING_LENENC);
         } else {
            if (len <= 0) {
               throw ExceptionFactory.createException(Messages.getString("MysqlIO.97") + type + Messages.getString("MysqlIO.98") + columnIndex + Messages.getString("MysqlIO.99") + fields.length + Messages.getString("MysqlIO.100"));
            }

            unpackedRowData[columnIndex] = binaryData.readBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, len);
         }
      }

   }
}
