package com.mysql.cj.core.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class BinaryStreamValueFactory extends DefaultValueFactory<InputStream> {
   public InputStream createFromBytes(byte[] bytes, int offset, int length) {
      return new ByteArrayInputStream(bytes, offset, length);
   }

   public String getTargetTypeName() {
      return InputStream.class.getName();
   }
}
