package org.jooq.types;

import java.math.BigInteger;

public final class ULong extends UNumber implements Comparable<ULong> {
   private static final long serialVersionUID = -6821055240959745390L;
   public static final BigInteger MIN_VALUE;
   public static final BigInteger MAX_VALUE;
   public static final BigInteger MAX_VALUE_LONG;
   private final BigInteger value;

   public static ULong valueOf(String value) throws NumberFormatException {
      return new ULong(value);
   }

   public static ULong valueOf(long value) {
      return new ULong(value);
   }

   public static ULong valueOf(BigInteger value) throws NumberFormatException {
      return new ULong(value);
   }

   private ULong(BigInteger value) throws NumberFormatException {
      this.value = value;
      this.rangeCheck();
   }

   private ULong(long value) {
      if (value >= 0L) {
         this.value = BigInteger.valueOf(value);
      } else {
         this.value = BigInteger.valueOf(value & Long.MAX_VALUE).add(MAX_VALUE_LONG);
      }

   }

   private ULong(String value) throws NumberFormatException {
      this.value = new BigInteger(value);
      this.rangeCheck();
   }

   private void rangeCheck() throws NumberFormatException {
      if (this.value.compareTo(MIN_VALUE) < 0 || this.value.compareTo(MAX_VALUE) > 0) {
         throw new NumberFormatException("Value is out of range : " + this.value);
      }
   }

   public int intValue() {
      return this.value.intValue();
   }

   public long longValue() {
      return this.value.longValue();
   }

   public float floatValue() {
      return this.value.floatValue();
   }

   public double doubleValue() {
      return this.value.doubleValue();
   }

   public int hashCode() {
      return this.value.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else {
         return obj instanceof ULong ? this.value.equals(((ULong)obj).value) : false;
      }
   }

   public String toString() {
      return this.value.toString();
   }

   public int compareTo(ULong o) {
      return this.value.compareTo(o.value);
   }

   static {
      MIN_VALUE = BigInteger.ZERO;
      MAX_VALUE = new BigInteger("18446744073709551615");
      MAX_VALUE_LONG = new BigInteger("9223372036854775808");
   }
}
