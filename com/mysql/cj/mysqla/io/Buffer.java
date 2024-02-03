package com.mysql.cj.mysqla.io;

import com.mysql.cj.api.mysqla.io.NativeProtocol;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.core.Constants;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.ExceptionFactory;
import com.mysql.cj.core.exceptions.WrongArgumentException;
import com.mysql.cj.core.util.StringUtils;

public class Buffer implements PacketPayload {
   private int payloadLength = 0;
   private byte[] byteBuffer;
   private int position = 0;
   static final int MAX_BYTES_TO_DUMP = 512;

   public String toString() {
      int numBytes = this.position <= this.payloadLength ? this.position : this.payloadLength;
      int numBytesToDump = numBytes < 512 ? numBytes : 512;
      this.position = 0;
      String dumped = StringUtils.dumpAsHex(this.readBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, numBytesToDump), numBytesToDump);
      return numBytesToDump < numBytes ? dumped + " ....(packet exceeds max. dump length)" : dumped;
   }

   public String toSuperString() {
      return super.toString();
   }

   public Buffer(byte[] buf) {
      this.byteBuffer = buf;
      this.payloadLength = buf.length;
   }

   public Buffer(int size) {
      this.byteBuffer = new byte[size];
      this.payloadLength = size;
   }

   public int getCapacity() {
      return this.byteBuffer.length;
   }

   public final void ensureCapacity(int additionalData) {
      if (this.position + additionalData > this.byteBuffer.length) {
         int newLength = (int)((double)this.byteBuffer.length * 1.25D);
         if (newLength < this.byteBuffer.length + additionalData) {
            newLength = this.byteBuffer.length + (int)((double)additionalData * 1.25D);
         }

         if (newLength < this.byteBuffer.length) {
            newLength = this.byteBuffer.length + additionalData;
         }

         byte[] newBytes = new byte[newLength];
         System.arraycopy(this.byteBuffer, 0, newBytes, 0, this.byteBuffer.length);
         this.byteBuffer = newBytes;
      }

   }

   public byte[] getByteBuffer() {
      return this.byteBuffer;
   }

   public void setByteBuffer(byte[] byteBufferToSet) {
      this.byteBuffer = byteBufferToSet;
   }

   public int getPayloadLength() {
      return this.payloadLength;
   }

   public void setPayloadLength(int bufLengthToSet) {
      if (bufLengthToSet > this.byteBuffer.length) {
         throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("Buffer.0"));
      } else {
         this.payloadLength = bufLengthToSet;
      }
   }

   private void adjustPayloadLength() {
      if (this.position > this.payloadLength) {
         this.payloadLength = this.position;
      }

   }

   public int getPosition() {
      return this.position;
   }

   public void setPosition(int positionToSet) {
      this.position = positionToSet;
   }

   public final boolean isEOFPacket() {
      return (this.byteBuffer[0] & 255) == 254 && this.getPayloadLength() <= 5;
   }

   public final boolean isAuthMethodSwitchRequestPacket() {
      return (this.byteBuffer[0] & 255) == 254;
   }

   public final boolean isOKPacket() {
      return (this.byteBuffer[0] & 255) == 0;
   }

   public final boolean isResultSetOKPacket() {
      return (this.byteBuffer[0] & 255) == 254 && this.getPayloadLength() < 16777215;
   }

   public final boolean isAuthMoreData() {
      return (this.byteBuffer[0] & 255) == 1;
   }

   public void writeInteger(NativeProtocol.IntegerDataType type, long l) {
      byte[] b;
      switch(type) {
      case INT1:
         this.ensureCapacity(1);
         b = this.byteBuffer;
         b[this.position++] = (byte)((int)(l & 255L));
         break;
      case INT2:
         this.ensureCapacity(2);
         b = this.byteBuffer;
         b[this.position++] = (byte)((int)(l & 255L));
         b[this.position++] = (byte)((int)(l >>> 8));
         break;
      case INT3:
         this.ensureCapacity(3);
         b = this.byteBuffer;
         b[this.position++] = (byte)((int)(l & 255L));
         b[this.position++] = (byte)((int)(l >>> 8));
         b[this.position++] = (byte)((int)(l >>> 16));
         break;
      case INT4:
         this.ensureCapacity(4);
         b = this.byteBuffer;
         b[this.position++] = (byte)((int)(l & 255L));
         b[this.position++] = (byte)((int)(l >>> 8));
         b[this.position++] = (byte)((int)(l >>> 16));
         b[this.position++] = (byte)((int)(l >>> 24));
         break;
      case INT6:
         this.ensureCapacity(6);
         b = this.byteBuffer;
         b[this.position++] = (byte)((int)(l & 255L));
         b[this.position++] = (byte)((int)(l >>> 8));
         b[this.position++] = (byte)((int)(l >>> 16));
         b[this.position++] = (byte)((int)(l >>> 24));
         b[this.position++] = (byte)((int)(l >>> 32));
         b[this.position++] = (byte)((int)(l >>> 40));
         break;
      case INT8:
         this.ensureCapacity(8);
         b = this.byteBuffer;
         b[this.position++] = (byte)((int)(l & 255L));
         b[this.position++] = (byte)((int)(l >>> 8));
         b[this.position++] = (byte)((int)(l >>> 16));
         b[this.position++] = (byte)((int)(l >>> 24));
         b[this.position++] = (byte)((int)(l >>> 32));
         b[this.position++] = (byte)((int)(l >>> 40));
         b[this.position++] = (byte)((int)(l >>> 48));
         b[this.position++] = (byte)((int)(l >>> 56));
         break;
      case INT_LENENC:
         if (l < 251L) {
            this.ensureCapacity(1);
            this.writeInteger(NativeProtocol.IntegerDataType.INT1, l);
         } else if (l < 65536L) {
            this.ensureCapacity(3);
            this.writeInteger(NativeProtocol.IntegerDataType.INT1, 252L);
            this.writeInteger(NativeProtocol.IntegerDataType.INT2, l);
         } else if (l < 16777216L) {
            this.ensureCapacity(4);
            this.writeInteger(NativeProtocol.IntegerDataType.INT1, 253L);
            this.writeInteger(NativeProtocol.IntegerDataType.INT3, l);
         } else {
            this.ensureCapacity(9);
            this.writeInteger(NativeProtocol.IntegerDataType.INT1, 254L);
            this.writeInteger(NativeProtocol.IntegerDataType.INT8, l);
         }
      }

      this.adjustPayloadLength();
   }

   public final long readInteger(NativeProtocol.IntegerDataType type) {
      byte[] b = this.byteBuffer;
      switch(type) {
      case INT1:
         return (long)(b[this.position++] & 255);
      case INT2:
         return (long)(b[this.position++] & 255 | (b[this.position++] & 255) << 8);
      case INT3:
         return (long)(b[this.position++] & 255 | (b[this.position++] & 255) << 8 | (b[this.position++] & 255) << 16);
      case INT4:
         return (long)b[this.position++] & 255L | ((long)b[this.position++] & 255L) << 8 | (long)(b[this.position++] & 255) << 16 | (long)(b[this.position++] & 255) << 24;
      case INT6:
         return (long)(b[this.position++] & 255) | (long)(b[this.position++] & 255) << 8 | (long)(b[this.position++] & 255) << 16 | (long)(b[this.position++] & 255) << 24 | (long)(b[this.position++] & 255) << 32 | (long)(b[this.position++] & 255) << 40;
      case INT8:
         return (long)(b[this.position++] & 255) | (long)(b[this.position++] & 255) << 8 | (long)(b[this.position++] & 255) << 16 | (long)(b[this.position++] & 255) << 24 | (long)(b[this.position++] & 255) << 32 | (long)(b[this.position++] & 255) << 40 | (long)(b[this.position++] & 255) << 48 | (long)(b[this.position++] & 255) << 56;
      case INT_LENENC:
         int sw = b[this.position++] & 255;
         switch(sw) {
         case 251:
            return -1L;
         case 252:
            return this.readInteger(NativeProtocol.IntegerDataType.INT2);
         case 253:
            return this.readInteger(NativeProtocol.IntegerDataType.INT3);
         case 254:
            return this.readInteger(NativeProtocol.IntegerDataType.INT8);
         default:
            return (long)sw;
         }
      default:
         return (long)(b[this.position++] & 255);
      }
   }

   public final void writeBytes(NativeProtocol.StringSelfDataType type, byte[] b) {
      this.writeBytes((NativeProtocol.StringSelfDataType)type, b, 0, b.length);
   }

   public final void writeBytes(NativeProtocol.StringLengthDataType type, byte[] b) {
      this.writeBytes((NativeProtocol.StringLengthDataType)type, b, 0, b.length);
   }

   public void writeBytes(NativeProtocol.StringSelfDataType type, byte[] b, int offset, int len) {
      switch(type) {
      case STRING_EOF:
         this.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, b, offset, len);
         break;
      case STRING_TERM:
         this.ensureCapacity(len + 1);
         this.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, b, offset, len);
         this.byteBuffer[this.position++] = 0;
         break;
      case STRING_LENENC:
         this.ensureCapacity(len + 9);
         this.writeInteger(NativeProtocol.IntegerDataType.INT_LENENC, (long)len);
         this.writeBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, b, offset, len);
      }

      this.adjustPayloadLength();
   }

   public void writeBytes(NativeProtocol.StringLengthDataType type, byte[] b, int offset, int len) {
      switch(type) {
      case STRING_FIXED:
      case STRING_VAR:
         this.ensureCapacity(len);
         System.arraycopy(b, offset, this.byteBuffer, this.position, len);
         this.position += len;
      default:
         this.adjustPayloadLength();
      }
   }

   public byte[] readBytes(NativeProtocol.StringSelfDataType type) {
      switch(type) {
      case STRING_EOF:
         return this.readBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, this.payloadLength - this.position);
      case STRING_TERM:
         int i;
         for(i = this.position; i < this.payloadLength && this.byteBuffer[i] != 0; ++i) {
         }

         byte[] b = this.readBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, i - this.position);
         ++this.position;
         return b;
      case STRING_LENENC:
         long l = this.readInteger(NativeProtocol.IntegerDataType.INT_LENENC);
         return l == -1L ? null : (l == 0L ? Constants.EMPTY_BYTE_ARRAY : this.readBytes(NativeProtocol.StringLengthDataType.STRING_FIXED, (int)l));
      default:
         return null;
      }
   }

   public void skipBytes(NativeProtocol.StringSelfDataType type) {
      switch(type) {
      case STRING_EOF:
         this.position = this.payloadLength;
         break;
      case STRING_TERM:
         while(this.position < this.payloadLength && this.byteBuffer[this.position] != 0) {
            ++this.position;
         }

         ++this.position;
         break;
      case STRING_LENENC:
         long len = this.readInteger(NativeProtocol.IntegerDataType.INT_LENENC);
         if (len != -1L && len != 0L) {
            this.position += (int)len;
         }
      }

   }

   public byte[] readBytes(NativeProtocol.StringLengthDataType type, int len) {
      switch(type) {
      case STRING_FIXED:
      case STRING_VAR:
         byte[] b = new byte[len];
         System.arraycopy(this.byteBuffer, this.position, b, 0, len);
         this.position += len;
         return b;
      default:
         return null;
      }
   }

   public String readString(NativeProtocol.StringSelfDataType type, String encoding) {
      String res = null;
      switch(type) {
      case STRING_EOF:
         return this.readString(NativeProtocol.StringLengthDataType.STRING_FIXED, encoding, this.payloadLength - this.position);
      case STRING_TERM:
         int i;
         for(i = this.position; i < this.payloadLength && this.byteBuffer[i] != 0; ++i) {
         }

         res = this.readString(NativeProtocol.StringLengthDataType.STRING_FIXED, encoding, i - this.position);
         ++this.position;
      default:
         return res;
      case STRING_LENENC:
         long l = this.readInteger(NativeProtocol.IntegerDataType.INT_LENENC);
         return l == -1L ? null : (l == 0L ? "" : this.readString(NativeProtocol.StringLengthDataType.STRING_FIXED, encoding, (int)l));
      }
   }

   public String readString(NativeProtocol.StringLengthDataType type, String encoding, int len) {
      String res = null;
      switch(type) {
      case STRING_FIXED:
      case STRING_VAR:
         if (this.position + len > this.payloadLength) {
            throw (WrongArgumentException)ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("Buffer.1"));
         } else {
            res = StringUtils.toString(this.byteBuffer, this.position, len, encoding);
            this.position += len;
         }
      default:
         return res;
      }
   }
}
