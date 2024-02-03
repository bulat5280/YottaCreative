package com.mysql.cj.core.util;

public class LazyString {
   private String string;
   private byte[] buffer;
   private int offset;
   private int length;
   private String encoding;

   public LazyString(String string) {
      this.string = string;
   }

   public LazyString(byte[] buffer, int offset, int length, String encoding) {
      this.buffer = buffer;
      this.offset = offset;
      this.length = length;
      this.encoding = encoding;
   }

   private String createAndCacheString() {
      if (this.length > 0) {
         this.string = StringUtils.toString(this.buffer, this.offset, this.length, this.encoding);
      }

      return this.string;
   }

   public String toString() {
      return this.string != null ? this.string : this.createAndCacheString();
   }

   public int length() {
      return this.string != null ? this.string.length() : this.length;
   }
}
