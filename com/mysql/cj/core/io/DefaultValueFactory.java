package com.mysql.cj.core.io;

import com.mysql.cj.api.io.ValueFactory;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.DataConversionException;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class DefaultValueFactory<T> implements ValueFactory<T> {
   private T unsupported(String sourceType) {
      throw new DataConversionException(Messages.getString("ResultSet.UnsupportedConversion", new Object[]{sourceType, this.getTargetTypeName()}));
   }

   public T createFromDate(int year, int month, int day) {
      return this.unsupported("DATE");
   }

   public T createFromTime(int hours, int minutes, int seconds, int nanos) {
      return this.unsupported("TIME");
   }

   public T createFromTimestamp(int year, int month, int day, int hours, int minutes, int seconds, int nanos) {
      return this.unsupported("TIMESTAMP");
   }

   public T createFromLong(long l) {
      return this.unsupported("LONG");
   }

   public T createFromBigInteger(BigInteger i) {
      return this.unsupported("BIGINT");
   }

   public T createFromDouble(double d) {
      return this.unsupported("DOUBLE");
   }

   public T createFromBigDecimal(BigDecimal d) {
      return this.unsupported("DECIMAL");
   }

   public T createFromBytes(byte[] bytes, int offset, int length) {
      return this.unsupported("VARCHAR/TEXT/BLOB");
   }

   public T createFromBit(byte[] bytes, int offset, int length) {
      return this.unsupported("BIT");
   }

   public T createFromNull() {
      return null;
   }
}
