package com.mysql.cj.mysqla.result;

import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.io.ValueDecoder;
import com.mysql.cj.api.io.ValueFactory;
import com.mysql.cj.api.mysqla.io.NativeProtocol;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.api.result.Row;

public class TextBufferRow extends AbstractBufferRow {
   public TextBufferRow(PacketPayload buf, ColumnDefinition cd, ExceptionInterceptor exceptionInterceptor, ValueDecoder valueDecoder) {
      super(exceptionInterceptor);
      this.rowFromServer = buf;
      this.homePosition = this.rowFromServer.getPosition();
      this.valueDecoder = valueDecoder;
      if (cd.getFields() != null) {
         this.setMetadata(cd);
      }

   }

   protected int findAndSeekToOffset(int index) {
      if (index == 0) {
         this.lastRequestedIndex = 0;
         this.lastRequestedPos = this.homePosition;
         this.rowFromServer.setPosition(this.homePosition);
         return 0;
      } else if (index == this.lastRequestedIndex) {
         this.rowFromServer.setPosition(this.lastRequestedPos);
         return this.lastRequestedPos;
      } else {
         int startingIndex = 0;
         if (index > this.lastRequestedIndex) {
            if (this.lastRequestedIndex >= 0) {
               startingIndex = this.lastRequestedIndex;
            } else {
               startingIndex = 0;
            }

            this.rowFromServer.setPosition(this.lastRequestedPos);
         } else {
            this.rowFromServer.setPosition(this.homePosition);
         }

         for(int i = startingIndex; i < index; ++i) {
            this.rowFromServer.skipBytes(NativeProtocol.StringSelfDataType.STRING_LENENC);
         }

         this.lastRequestedIndex = index;
         this.lastRequestedPos = this.rowFromServer.getPosition();
         return this.lastRequestedPos;
      }
   }

   public byte[] getBytes(int index) {
      this.findAndSeekToOffset(index);
      return this.rowFromServer.readBytes(NativeProtocol.StringSelfDataType.STRING_LENENC);
   }

   public boolean getNull(int columnIndex) {
      this.findAndSeekToOffset(columnIndex);
      this.wasNull = this.rowFromServer.readInteger(NativeProtocol.IntegerDataType.INT_LENENC) == -1L;
      return this.wasNull;
   }

   public Row setMetadata(ColumnDefinition f) {
      super.setMetadata(f);
      return this;
   }

   public <T> T getValue(int columnIndex, ValueFactory<T> vf) {
      this.findAndSeekToOffset(columnIndex);
      int length = (int)this.rowFromServer.readInteger(NativeProtocol.IntegerDataType.INT_LENENC);
      return this.getValueFromBytes(columnIndex, this.rowFromServer.getByteBuffer(), this.rowFromServer.getPosition(), length, vf);
   }
}
