package org.flywaydb.core.internal.util;

public class ObjectUtils {
   public static boolean nullSafeEquals(Object o1, Object o2) {
      if (o1 == o2) {
         return true;
      } else {
         return o1 != null && o2 != null ? o1.equals(o2) : false;
      }
   }
}
