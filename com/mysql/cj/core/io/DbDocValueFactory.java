package com.mysql.cj.core.io;

import com.mysql.cj.core.exceptions.AssertionFailedException;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.x.json.DbDoc;
import com.mysql.cj.x.json.JsonParser;
import java.io.IOException;
import java.io.StringReader;

public class DbDocValueFactory extends DefaultValueFactory<DbDoc> {
   private String encoding;

   public DbDocValueFactory() {
   }

   public DbDocValueFactory(String encoding) {
      this.encoding = encoding;
   }

   public DbDoc createFromBytes(byte[] bytes, int offset, int length) {
      try {
         return JsonParser.parseDoc(new StringReader(StringUtils.toString(bytes, offset, length, this.encoding)));
      } catch (IOException var5) {
         throw AssertionFailedException.shouldNotHappen((Exception)var5);
      }
   }

   public DbDoc createFromNull() {
      return null;
   }

   public String getTargetTypeName() {
      return DbDoc.class.getName();
   }
}
