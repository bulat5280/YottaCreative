package com.mysql.cj.mysqlx.io;

import com.google.protobuf.CodedInputStream;
import com.mysql.cj.api.io.ValueFactory;
import com.mysql.cj.core.exceptions.AssertionFailedException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MysqlxDecoder {
   public static MysqlxDecoder instance = new MysqlxDecoder();
   public static final Map<Integer, MysqlxDecoder.DecoderFunction> MYSQL_TYPE_TO_DECODER_FUNCTION;

   public <T> T decodeBit(CodedInputStream inputStream, ValueFactory<T> vf) throws IOException {
      byte[] bytes = ByteBuffer.allocate(9).put((byte)0).putLong(inputStream.readUInt64()).array();
      return vf.createFromBit(bytes, 0, 9);
   }

   public <T> T decodeString(CodedInputStream inputStream, ValueFactory<T> vf) throws IOException {
      int size = inputStream.getBytesUntilLimit();
      --size;
      return vf.createFromBytes(inputStream.readRawBytes(size), 0, size);
   }

   public <T> T decodeSet(CodedInputStream inputStream, ValueFactory<T> vf) throws IOException {
      StringBuilder vals = new StringBuilder();

      while(inputStream.getBytesUntilLimit() > 0) {
         if (vals.length() > 0) {
            vals.append(",");
         }

         long valLen = inputStream.readUInt64();
         vals.append(new String(inputStream.readRawBytes((int)valLen)));
      }

      byte[] bytes = vals.toString().getBytes();
      return vf.createFromBytes(bytes, 0, bytes.length);
   }

   public <T> T decodeDateOrTimestamp(CodedInputStream inputStream, ValueFactory<T> vf) throws IOException {
      int year = (int)inputStream.readUInt64();
      int month = (int)inputStream.readUInt64();
      int day = (int)inputStream.readUInt64();
      if (inputStream.getBytesUntilLimit() > 0) {
         int hours = 0;
         int minutes = 0;
         int seconds = 0;
         int nanos = 0;
         if (!inputStream.isAtEnd()) {
            hours = (int)inputStream.readInt64();
            if (!inputStream.isAtEnd()) {
               minutes = (int)inputStream.readInt64();
               if (!inputStream.isAtEnd()) {
                  seconds = (int)inputStream.readInt64();
                  if (!inputStream.isAtEnd()) {
                     nanos = 1000 * (int)inputStream.readInt64();
                  }
               }
            }
         }

         return vf.createFromTimestamp(year, month, day, hours, minutes, seconds, nanos);
      } else {
         return vf.createFromDate(year, month, day);
      }
   }

   public <T> T decodeTime(CodedInputStream inputStream, ValueFactory<T> vf) throws IOException {
      boolean negative = inputStream.readRawByte() > 0;
      int hours = 0;
      int minutes = 0;
      int seconds = 0;
      int nanos = 0;
      if (!inputStream.isAtEnd()) {
         hours = (int)inputStream.readInt64();
         if (!inputStream.isAtEnd()) {
            minutes = (int)inputStream.readInt64();
            if (!inputStream.isAtEnd()) {
               seconds = (int)inputStream.readInt64();
               if (!inputStream.isAtEnd()) {
                  nanos = 1000 * (int)inputStream.readInt64();
               }
            }
         }
      }

      return vf.createFromTime(negative ? -1 * hours : hours, minutes, seconds, nanos);
   }

   public <T> T decodeFloat(CodedInputStream inputStream, ValueFactory<T> vf) throws IOException {
      return vf.createFromDouble((double)inputStream.readFloat());
   }

   public <T> T decodeDouble(CodedInputStream inputStream, ValueFactory<T> vf) throws IOException {
      return vf.createFromDouble(inputStream.readDouble());
   }

   public <T> T decodeSignedLong(CodedInputStream inputStream, ValueFactory<T> vf) throws IOException {
      return vf.createFromLong(inputStream.readSInt64());
   }

   public <T> T decodeUnsignedLong(CodedInputStream inputStream, ValueFactory<T> vf) throws IOException {
      BigInteger v = new BigInteger(ByteBuffer.allocate(9).put((byte)0).putLong(inputStream.readUInt64()).array());
      return vf.createFromBigInteger(v);
   }

   public <T> T decodeDecimal(CodedInputStream inputStream, ValueFactory<T> vf) throws IOException {
      byte scale = inputStream.readRawByte();
      CharBuffer unscaledString = CharBuffer.allocate(2 * inputStream.getBytesUntilLimit());
      unscaledString.position(1);
      boolean var5 = false;

      int characters;
      byte sign;
      while(true) {
         characters = 255 & inputStream.readRawByte();
         if (characters >> 4 > 9) {
            sign = (byte)(characters >> 4);
            break;
         }

         unscaledString.append((char)((characters >> 4) + 48));
         if ((characters & 15) > 9) {
            sign = (byte)(characters & 15);
            break;
         }

         unscaledString.append((char)((characters & 15) + 48));
      }

      if (inputStream.getBytesUntilLimit() > 0) {
         throw AssertionFailedException.shouldNotHappen("Did not read all bytes while decoding decimal. Bytes left: " + inputStream.getBytesUntilLimit());
      } else {
         switch(sign) {
         case 10:
         case 12:
         case 14:
         case 15:
            unscaledString.put(0, '+');
            break;
         case 11:
         case 13:
            unscaledString.put(0, '-');
         }

         characters = unscaledString.position();
         unscaledString.clear();
         BigInteger unscaled = new BigInteger(unscaledString.subSequence(0, characters).toString());
         return vf.createFromBigDecimal(new BigDecimal(unscaled, scale));
      }
   }

   static {
      Map<Integer, MysqlxDecoder.DecoderFunction> mysqlTypeToDecoderFunction = new HashMap();
      Integer var10001 = 16;
      MysqlxDecoder var10002 = instance;
      mysqlTypeToDecoderFunction.put(var10001, var10002::decodeBit);
      var10001 = 12;
      var10002 = instance;
      mysqlTypeToDecoderFunction.put(var10001, var10002::decodeDateOrTimestamp);
      var10001 = 5;
      var10002 = instance;
      mysqlTypeToDecoderFunction.put(var10001, var10002::decodeDouble);
      var10001 = 247;
      var10002 = instance;
      mysqlTypeToDecoderFunction.put(var10001, var10002::decodeString);
      var10001 = 4;
      var10002 = instance;
      mysqlTypeToDecoderFunction.put(var10001, var10002::decodeFloat);
      var10001 = 245;
      var10002 = instance;
      mysqlTypeToDecoderFunction.put(var10001, var10002::decodeString);
      var10001 = 8;
      var10002 = instance;
      mysqlTypeToDecoderFunction.put(var10001, var10002::decodeSignedLong);
      var10001 = 246;
      var10002 = instance;
      mysqlTypeToDecoderFunction.put(var10001, var10002::decodeDecimal);
      var10001 = 248;
      var10002 = instance;
      mysqlTypeToDecoderFunction.put(var10001, var10002::decodeSet);
      var10001 = 11;
      var10002 = instance;
      mysqlTypeToDecoderFunction.put(var10001, var10002::decodeTime);
      var10001 = 15;
      var10002 = instance;
      mysqlTypeToDecoderFunction.put(var10001, var10002::decodeString);
      var10001 = 253;
      var10002 = instance;
      mysqlTypeToDecoderFunction.put(var10001, var10002::decodeString);
      MYSQL_TYPE_TO_DECODER_FUNCTION = Collections.unmodifiableMap(mysqlTypeToDecoderFunction);
   }

   @FunctionalInterface
   public interface DecoderFunction {
      <T> T apply(CodedInputStream var1, ValueFactory<T> var2) throws IOException;
   }
}
