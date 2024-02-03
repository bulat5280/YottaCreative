package com.mysql.cj.api.authentication;

import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.io.Protocol;
import com.mysql.cj.api.io.ServerSession;
import com.mysql.cj.core.CharsetMapping;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.ServerVersion;
import com.mysql.cj.core.exceptions.ExceptionFactory;

public interface AuthenticationProvider {
   void init(Protocol var1, PropertySet var2, ExceptionInterceptor var3);

   void connect(ServerSession var1, String var2, String var3, String var4);

   void changeUser(ServerSession var1, String var2, String var3, String var4);

   String getEncodingForHandshake();

   static byte getCharsetForHandshake(String enc, ServerVersion sv) {
      int charsetIndex = 0;
      if (enc != null) {
         charsetIndex = CharsetMapping.getCollationIndexForJavaEncoding(enc, sv);
      }

      if (charsetIndex == 0) {
         charsetIndex = 33;
      }

      if (charsetIndex > 255) {
         throw ExceptionFactory.createException(Messages.getString("MysqlIO.113", new Object[]{enc}));
      } else {
         return (byte)charsetIndex;
      }
   }
}
