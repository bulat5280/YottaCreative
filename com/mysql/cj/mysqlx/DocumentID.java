package com.mysql.cj.mysqlx;

import java.util.UUID;

public class DocumentID {
   public static String generate() {
      UUID uuid = UUID.randomUUID();
      return getDigits(uuid.getLeastSignificantBits(), 12) + getDigits(uuid.getLeastSignificantBits() >> 48, 4) + getDigits(uuid.getMostSignificantBits(), 4) + getDigits(uuid.getMostSignificantBits() >> 16, 4) + getDigits(uuid.getMostSignificantBits() >> 32, 8);
   }

   private static String getDigits(long val, int digits) {
      long hi = 1L << digits * 4;
      return Long.toHexString(hi | val & hi - 1L).substring(1);
   }
}
