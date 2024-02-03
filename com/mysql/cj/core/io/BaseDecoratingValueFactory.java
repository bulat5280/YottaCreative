package com.mysql.cj.core.io;

import com.mysql.cj.api.io.ValueFactory;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class BaseDecoratingValueFactory<T> implements ValueFactory<T> {
   protected ValueFactory<T> targetVf;

   public BaseDecoratingValueFactory(ValueFactory<T> targetVf) {
      this.targetVf = targetVf;
   }

   public T createFromDate(int year, int month, int day) {
      return this.targetVf.createFromDate(year, month, day);
   }

   public T createFromTime(int hours, int minutes, int seconds, int nanos) {
      return this.targetVf.createFromTime(hours, minutes, seconds, nanos);
   }

   public T createFromTimestamp(int year, int month, int day, int hours, int minutes, int seconds, int nanos) {
      return this.targetVf.createFromTimestamp(year, month, day, hours, minutes, seconds, nanos);
   }

   public T createFromLong(long l) {
      return this.targetVf.createFromLong(l);
   }

   public T createFromBigInteger(BigInteger i) {
      return this.targetVf.createFromBigInteger(i);
   }

   public T createFromDouble(double d) {
      return this.targetVf.createFromDouble(d);
   }

   public T createFromBigDecimal(BigDecimal d) {
      return this.targetVf.createFromBigDecimal(d);
   }

   public T createFromBytes(byte[] bytes, int offset, int length) {
      return this.targetVf.createFromBytes(bytes, offset, length);
   }

   public T createFromBit(byte[] bytes, int offset, int length) {
      return this.targetVf.createFromBit(bytes, offset, length);
   }

   public T createFromNull() {
      return this.targetVf.createFromNull();
   }

   public String getTargetTypeName() {
      return this.targetVf.getTargetTypeName();
   }
}
