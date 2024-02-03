package com.mysql.cj.mysqla.result;

import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.io.ValueDecoder;
import com.mysql.cj.api.io.ValueFactory;
import com.mysql.cj.api.mysqla.io.NativeProtocol;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.api.mysqla.result.ColumnDefinition;
import com.mysql.cj.api.result.Row;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.mysqla.MysqlaUtils;

public class BinaryBufferRow extends AbstractBufferRow {
   private int preNullBitmaskHomePosition = 0;
   private boolean[] isNull;

   public BinaryBufferRow(PacketPayload buf, ColumnDefinition cd, ExceptionInterceptor exceptionInterceptor, ValueDecoder valueDecoder) {
      super(exceptionInterceptor);
      this.rowFromServer = buf;
      this.homePosition = this.rowFromServer.getPosition();
      this.preNullBitmaskHomePosition = this.homePosition;
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
               this.lastRequestedPos = this.homePosition;
            }

            this.rowFromServer.setPosition(this.lastRequestedPos);
         } else {
            this.rowFromServer.setPosition(this.homePosition);
         }

         for(int i = startingIndex; i < index; ++i) {
            if (!this.isNull[i]) {
               int type = this.metadata.getFields()[i].getMysqlTypeId();
               if (type != 6) {
                  int length = MysqlaUtils.getBinaryEncodedLength(this.metadata.getFields()[i].getMysqlTypeId());
                  if (length == 0) {
                     this.rowFromServer.skipBytes(NativeProtocol.StringSelfDataType.STRING_LENENC);
                  } else {
                     if (length == -1) {
                        throw ExceptionFactory.createException(Messages.getString("MysqlIO.97") + type + Messages.getString("MysqlIO.98") + (i + 1) + Messages.getString("MysqlIO.99") + this.metadata.getFields().length + Messages.getString("MysqlIO.100"), this.exceptionInterceptor);
                     }

                     int curPosition = this.rowFromServer.getPosition();
                     this.rowFromServer.setPosition(curPosition + length);
                  }
               }
            }
         }

         this.lastRequestedIndex = index;
         this.lastRequestedPos = this.rowFromServer.getPosition();
         return this.lastRequestedPos;
      }
   }

   public byte[] getBytes(int index) {
      this.findAndSeekToOffset(index);
      if (this.getNull(index)) {
         return null;
      } else {
         int type = this.metadata.getFields()[index].getMysqlTypeId();
         switch(type) {
         case 1:
            return this.rowFromServer.readBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, 1);
         case 6:
            return null;
         default:
            int length = MysqlaUtils.getBinaryEncodedLength(type);
            if (length == 0) {
               return this.rowFromServer.readBytes(NativeProtocol.StringSelfDataType.STRING_LENENC);
            } else if (length == -1) {
               throw ExceptionFactory.createException(Messages.getString("MysqlIO.97") + type + Messages.getString("MysqlIO.98") + (index + 1) + Messages.getString("MysqlIO.99") + this.metadata.getFields().length + Messages.getString("MysqlIO.100"), this.exceptionInterceptor);
            } else {
               return this.rowFromServer.readBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, length);
            }
         }
      }
   }

   public boolean getNull(int columnIndex) {
      this.wasNull = this.isNull[columnIndex];
      return this.wasNull;
   }

   public Row setMetadata(ColumnDefinition f) {
      super.setMetadata(f);
      this.setupIsNullBitmask();
      return this;
   }

   private void setupIsNullBitmask() {
      if (this.isNull == null) {
         this.rowFromServer.setPosition(this.preNullBitmaskHomePosition);
         int len = this.metadata.getFields().length;
         int nullCount = (len + 9) / 8;
         byte[] nullBitMask = this.rowFromServer.readBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, nullCount);
         this.homePosition = this.rowFromServer.getPosition();
         this.isNull = new boolean[len];
         int nullMaskPos = 0;
         int bit = 4;

         for(int i = 0; i < len; ++i) {
            this.isNull[i] = (nullBitMask[nullMaskPos] & bit) != 0;
            if (((bit <<= 1) & 255) == 0) {
               bit = 1;
               ++nullMaskPos;
            }
         }

      }
   }

   public <T> T getValue(int columnIndex, ValueFactory<T> vf) {
      this.findAndSeekToOffset(columnIndex);
      int type = this.metadata.getFields()[columnIndex].getMysqlTypeId();
      int length = MysqlaUtils.getBinaryEncodedLength(type);
      if (length == 0) {
         length = (int)this.rowFromServer.readInteger(NativeProtocol.IntegerDataType.INT_LENENC);
      } else if (length == -1) {
         throw ExceptionFactory.createException(Messages.getString("MysqlIO.97") + type + Messages.getString("MysqlIO.98") + (columnIndex + 1) + Messages.getString("MysqlIO.99") + this.metadata.getFields().length + Messages.getString("MysqlIO.100"), this.exceptionInterceptor);
      }

      return this.getValueFromBytes(columnIndex, this.rowFromServer.getByteBuffer(), this.rowFromServer.getPosition(), length, vf);
   }
}
