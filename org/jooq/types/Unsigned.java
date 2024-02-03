package org.jooq.types;

import java.math.BigInteger;

public final class Unsigned {
   public static UByte ubyte(String value) throws NumberFormatException {
      return value == null ? null : UByte.valueOf(value);
   }

   public static UByte ubyte(byte value) {
      return UByte.valueOf(value);
   }

   public static UByte ubyte(short value) throws NumberFormatException {
      return UByte.valueOf(value);
   }

   public static UByte ubyte(int value) throws NumberFormatException {
      return UByte.valueOf(value);
   }

   public static UByte ubyte(long value) throws NumberFormatException {
      return UByte.valueOf(value);
   }

   public static UShort ushort(String value) throws NumberFormatException {
      return value == null ? null : UShort.valueOf(value);
   }

   public static UShort ushort(short value) {
      return UShort.valueOf(value);
   }

   public static UShort ushort(int value) throws NumberFormatException {
      return UShort.valueOf(value);
   }

   public static UInteger uint(String value) throws NumberFormatException {
      return value == null ? null : UInteger.valueOf(value);
   }

   public static UInteger uint(int value) {
      return UInteger.valueOf(value);
   }

   public static UInteger uint(long value) throws NumberFormatException {
      return UInteger.valueOf(value);
   }

   public static ULong ulong(String value) throws NumberFormatException {
      return value == null ? null : ULong.valueOf(value);
   }

   public static ULong ulong(long value) {
      return ULong.valueOf(value);
   }

   public static ULong ulong(BigInteger value) throws NumberFormatException {
      return ULong.valueOf(value);
   }

   private Unsigned() {
   }
}
