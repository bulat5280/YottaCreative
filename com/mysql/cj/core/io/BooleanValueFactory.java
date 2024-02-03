package com.mysql.cj.core.io;

import com.mysql.cj.mysqla.MysqlaUtils;
import java.math.BigDecimal;
import java.math.BigInteger;

public class BooleanValueFactory extends DefaultValueFactory<Boolean> {
   public Boolean createFromLong(long l) {
      return l == -1L || l > 0L;
   }

   public Boolean createFromBigInteger(BigInteger i) {
      return i.compareTo(BigInteger.valueOf(0L)) > 0 || i.compareTo(BigInteger.valueOf(-1L)) == 0;
   }

   public Boolean createFromDouble(double d) {
      return d > 0.0D || d == -1.0D;
   }

   public Boolean createFromBigDecimal(BigDecimal d) {
      return d.compareTo(BigDecimal.valueOf(0L)) > 0 || d.compareTo(BigDecimal.valueOf(-1L)) == 0;
   }

   public Boolean createFromBit(byte[] bytes, int offset, int length) {
      return this.createFromLong(MysqlaUtils.bitToLong(bytes, offset, length));
   }

   public Boolean createFromNull() {
      return false;
   }

   public String getTargetTypeName() {
      return Boolean.class.getName();
   }
}
