package org.jooq.util;

public enum InOutDefinition {
   IN,
   OUT,
   INOUT,
   RETURN;

   public static final InOutDefinition getFromString(String string) {
      if (string == null) {
         return IN;
      } else {
         return "IN/OUT".equalsIgnoreCase(string) ? INOUT : valueOf(string.toUpperCase());
      }
   }
}
