package com.mysql.cj.core.io;

import com.mysql.cj.mysqla.MysqlaUtils;
import java.math.BigDecimal;
import java.math.BigInteger;

public class LongValueFactory extends DefaultValueFactory<Long> {
   public Long createFromBigInteger(BigInteger i) {
      return i.longValue();
   }

   public Long createFromLong(long l) {
      return l;
   }

   public Long createFromBigDecimal(BigDecimal d) {
      return d.longValue();
   }

   public Long createFromDouble(double d) {
      return (long)d;
   }

   public Long createFromBit(byte[] bytes, int offset, int length) {
      return MysqlaUtils.bitToLong(bytes, offset, length);
   }

   public Long createFromNull() {
      return 0L;
   }

   public String getTargetTypeName() {
      return Long.class.getName();
   }
}
