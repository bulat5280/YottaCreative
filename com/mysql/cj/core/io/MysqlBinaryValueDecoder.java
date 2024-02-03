package com.mysql.cj.core.io;

import com.mysql.cj.api.io.ValueDecoder;
import com.mysql.cj.api.io.ValueFactory;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.DataReadException;
import com.mysql.cj.core.util.StringUtils;
import java.math.BigDecimal;
import java.math.BigInteger;

public class MysqlBinaryValueDecoder implements ValueDecoder {
   public <T> T decodeTimestamp(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
      if (length == 0) {
         return vf.createFromTimestamp(0, 0, 0, 0, 0, 0, 0);
      } else if (length != 4 && length != 11 && length != 7) {
         throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[]{length, "TIMESTAMP"}));
      } else {
         int year = false;
         int month = false;
         int day = false;
         int hours = 0;
         int minutes = 0;
         int seconds = 0;
         int nanos = 0;
         int year = bytes[offset + 0] & 255 | (bytes[offset + 1] & 255) << 8;
         int month = bytes[offset + 2];
         int day = bytes[offset + 3];
         if (length > 4) {
            hours = bytes[offset + 4];
            minutes = bytes[offset + 5];
            seconds = bytes[offset + 6];
         }

         if (length > 7) {
            nanos = 1000 * (bytes[offset + 7] & 255 | (bytes[offset + 8] & 255) << 8 | (bytes[offset + 9] & 255) << 16 | (bytes[offset + 10] & 255) << 24);
         }

         return vf.createFromTimestamp(year, month, day, hours, minutes, seconds, nanos);
      }
   }

   public <T> T decodeTime(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
      if (length == 0) {
         return vf.createFromTime(0, 0, 0, 0);
      } else if (length != 8 && length != 12) {
         throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[]{length, "TIME"}));
      } else {
         int days = false;
         int hours = false;
         int minutes = false;
         int seconds = false;
         int nanos = 0;
         boolean negative = bytes[offset] == 1;
         int days = bytes[offset + 1] & 255 | (bytes[offset + 2] & 255) << 8 | (bytes[offset + 3] & 255) << 16 | (bytes[offset + 4] & 255) << 24;
         int hours = bytes[offset + 5];
         int minutes = bytes[offset + 6];
         int seconds = bytes[offset + 7];
         if (negative) {
            days *= -1;
         }

         if (length > 7) {
            nanos = 1000 * (bytes[offset + 1] & 255) | (bytes[offset + 2] & 255) << 8 | (bytes[offset + 3] & 255) << 16 | (bytes[offset + 4] & 255) << 24;
         }

         return vf.createFromTime(days * 24 + hours, minutes, seconds, nanos);
      }
   }

   public <T> T decodeDate(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
      if (length == 0) {
         return vf.createFromDate(0, 0, 0);
      } else if (length != 4) {
         throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[]{length, "DATE"}));
      } else {
         int year = bytes[offset] & 255 | (bytes[offset + 1] & 255) << 8;
         int month = bytes[offset + 2];
         int day = bytes[offset + 3];
         return vf.createFromDate(year, month, day);
      }
   }

   public <T> T decodeUInt1(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
      if (length != 1) {
         throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[]{length, "BYTE"}));
      } else {
         return vf.createFromLong((long)(bytes[offset] & 255));
      }
   }

   public <T> T decodeInt1(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
      if (length != 1) {
         throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[]{length, "BYTE"}));
      } else {
         return vf.createFromLong((long)bytes[offset]);
      }
   }

   public <T> T decodeUInt2(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
      if (length != 2) {
         throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[]{length, "SHORT"}));
      } else {
         int asInt = bytes[offset] & 255 | (bytes[offset + 1] & 255) << 8;
         return vf.createFromLong((long)asInt);
      }
   }

   public <T> T decodeInt2(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
      if (length != 2) {
         throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[]{length, "SHORT"}));
      } else {
         short asShort = (short)(bytes[offset] & 255 | (bytes[offset + 1] & 255) << 8);
         return vf.createFromLong((long)asShort);
      }
   }

   public <T> T decodeUInt4(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
      if (length != 4) {
         throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[]{length, "INT"}));
      } else {
         long asLong = (long)(bytes[offset] & 255 | (bytes[offset + 1] & 255) << 8 | (bytes[offset + 2] & 255) << 16) | (long)(bytes[offset + 3] & 255) << 24;
         return vf.createFromLong(asLong);
      }
   }

   public <T> T decodeInt4(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
      if (length != 4) {
         throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[]{length, "SHORT"}));
      } else {
         int asInt = bytes[offset] & 255 | (bytes[offset + 1] & 255) << 8 | (bytes[offset + 2] & 255) << 16 | (bytes[offset + 3] & 255) << 24;
         return vf.createFromLong((long)asInt);
      }
   }

   public <T> T decodeInt8(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
      if (length != 8) {
         throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[]{length, "LONG"}));
      } else {
         long asLong = (long)(bytes[offset] & 255) | (long)(bytes[offset + 1] & 255) << 8 | (long)(bytes[offset + 2] & 255) << 16 | (long)(bytes[offset + 3] & 255) << 24 | (long)(bytes[offset + 4] & 255) << 32 | (long)(bytes[offset + 5] & 255) << 40 | (long)(bytes[offset + 6] & 255) << 48 | (long)(bytes[offset + 7] & 255) << 56;
         return vf.createFromLong(asLong);
      }
   }

   public <T> T decodeUInt8(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
      if (length != 8) {
         throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[]{length, "LONG"}));
      } else if ((bytes[offset + 7] & 128) == 0) {
         return this.decodeInt8(bytes, offset, length, vf);
      } else {
         byte[] bigEndian = new byte[]{0, bytes[offset + 7], bytes[offset + 6], bytes[offset + 5], bytes[offset + 4], bytes[offset + 3], bytes[offset + 2], bytes[offset + 1], bytes[offset]};
         BigInteger bigInt = new BigInteger(bigEndian);
         return vf.createFromBigInteger(bigInt);
      }
   }

   public <T> T decodeFloat(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
      if (length != 4) {
         throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[]{length, "FLOAT"}));
      } else {
         int asInt = bytes[offset] & 255 | (bytes[offset + 1] & 255) << 8 | (bytes[offset + 2] & 255) << 16 | (bytes[offset + 3] & 255) << 24;
         return vf.createFromDouble((double)Float.intBitsToFloat(asInt));
      }
   }

   public <T> T decodeDouble(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
      if (length != 8) {
         throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[]{length, "DOUBLE"}));
      } else {
         long valueAsLong = (long)(bytes[offset + 0] & 255) | (long)(bytes[offset + 1] & 255) << 8 | (long)(bytes[offset + 2] & 255) << 16 | (long)(bytes[offset + 3] & 255) << 24 | (long)(bytes[offset + 4] & 255) << 32 | (long)(bytes[offset + 5] & 255) << 40 | (long)(bytes[offset + 6] & 255) << 48 | (long)(bytes[offset + 7] & 255) << 56;
         return vf.createFromDouble(Double.longBitsToDouble(valueAsLong));
      }
   }

   public <T> T decodeDecimal(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
      BigDecimal d = new BigDecimal(StringUtils.toAsciiString(bytes, offset, length));
      return vf.createFromBigDecimal(d);
   }

   public <T> T decodeByteArray(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
      return vf.createFromBytes(bytes, offset, length);
   }

   public <T> T decodeBit(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
      return vf.createFromBit(bytes, offset, length);
   }
}
