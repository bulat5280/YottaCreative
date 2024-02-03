package com.mysql.cj.core.io;

import com.mysql.cj.mysqla.MysqlaUtils;
import java.math.BigDecimal;
import java.math.BigInteger;

public class IntegerValueFactory extends DefaultValueFactory<Integer> {
   public Integer createFromBigInteger(BigInteger i) {
      return i.intValue();
   }

   public Integer createFromLong(long l) {
      return (int)l;
   }

   public Integer createFromBigDecimal(BigDecimal d) {
      return (int)d.longValue();
   }

   public Integer createFromDouble(double d) {
      return (int)d;
   }

   public Integer createFromBit(byte[] bytes, int offset, int length) {
      return (int)MysqlaUtils.bitToLong(bytes, offset, length);
   }

   public Integer createFromNull() {
      return 0;
   }

   public String getTargetTypeName() {
      return Integer.class.getName();
   }
}
