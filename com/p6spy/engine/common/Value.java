package com.p6spy.engine.common;

import com.p6spy.engine.logging.P6LogOptions;
import com.p6spy.engine.spy.P6SpyOptions;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Value {
   private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
   private Object value;

   public Value(Object valueToSet) {
      this();
      this.value = valueToSet;
   }

   public Value() {
   }

   public Object getValue() {
      return this.value;
   }

   public void setValue(Object value) {
      this.value = value;
   }

   public String toString() {
      return this.convertToString(this.value);
   }

   public String convertToString(Object value) {
      String result;
      if (value == null) {
         result = "NULL";
      } else {
         if (value instanceof Date) {
            result = (new SimpleDateFormat(P6SpyOptions.getActiveInstance().getDatabaseDialectDateFormat())).format(value);
         } else if (value instanceof Boolean) {
            if ("numeric".equals(P6SpyOptions.getActiveInstance().getDatabaseDialectBooleanFormat())) {
               result = Boolean.FALSE.equals(value) ? "0" : "1";
            } else {
               result = value.toString();
            }
         } else if (value instanceof byte[]) {
            if (P6LogOptions.getActiveInstance().getExcludebinary()) {
               result = "[binary]";
            } else {
               result = this.toHexString((byte[])((byte[])value));
            }
         } else {
            result = value.toString();
         }

         result = this.quoteIfNeeded(result, value);
      }

      return result;
   }

   private String toHexString(byte[] bytes) {
      StringBuilder sb = new StringBuilder();
      byte[] var3 = bytes;
      int var4 = bytes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         byte b = var3[var5];
         int temp = b & 255;
         sb.append(HEX_CHARS[temp / 16]);
         sb.append(HEX_CHARS[temp % 16]);
      }

      return sb.toString();
   }

   private String quoteIfNeeded(String stringValue, Object obj) {
      if (stringValue == null) {
         return null;
      } else {
         return !Number.class.isAssignableFrom(obj.getClass()) && !Boolean.class.isAssignableFrom(obj.getClass()) ? "'" + this.escape(stringValue) + "'" : stringValue;
      }
   }

   private String escape(String stringValue) {
      return stringValue.replaceAll("'", "''");
   }
}
