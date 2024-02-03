package com.mysql.cj.core.io;

import com.mysql.cj.mysqla.MysqlaUtils;
import java.math.BigDecimal;
import java.math.BigInteger;

public class ShortValueFactory extends DefaultValueFactory<Short> {
   public Short createFromBigInteger(BigInteger i) {
      return (short)i.intValue();
   }

   public Short createFromLong(long l) {
      return (short)((int)l);
   }

   public Short createFromBigDecimal(BigDecimal d) {
      return (short)((int)d.longValue());
   }

   public Short createFromDouble(double d) {
      return (short)((int)d);
   }

   public Short createFromBit(byte[] bytes, int offset, int length) {
      return this.createFromLong(MysqlaUtils.bitToLong(bytes, offset, length));
   }

   public Short createFromNull() {
      return Short.valueOf((short)0);
   }

   public String getTargetTypeName() {
      return Short.class.getName();
   }
}
