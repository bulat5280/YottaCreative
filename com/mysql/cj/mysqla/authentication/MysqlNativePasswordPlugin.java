package com.mysql.cj.mysqla.authentication;

import com.mysql.cj.api.io.Protocol;
import com.mysql.cj.api.mysqla.authentication.AuthenticationPlugin;
import com.mysql.cj.api.mysqla.io.NativeProtocol;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.core.authentication.Security;
import com.mysql.cj.mysqla.io.Buffer;
import java.util.List;

public class MysqlNativePasswordPlugin implements AuthenticationPlugin {
   private Protocol protocol;
   private String password = null;

   public void init(Protocol prot) {
      this.protocol = prot;
   }

   public void destroy() {
      this.password = null;
   }

   public String getProtocolPluginName() {
      return "mysql_native_password";
   }

   public boolean requiresConfidentiality() {
      return false;
   }

   public boolean isReusable() {
      return true;
   }

   public void setAuthenticationParameters(String user, String password) {
      this.password = password;
   }

   public boolean nextAuthenticationStep(PacketPayload fromServer, List<PacketPayload> toServer) {
      toServer.clear();
      PacketPayload bresp = null;
      String pwd = this.password;
      if (fromServer != null && pwd != null && pwd.length() != 0) {
         bresp = new Buffer(Security.scramble411(pwd, fromServer.readBytes(NativeProtocol.StringSelfDataType.STRING_TERM), this.protocol.getPasswordCharacterEncoding()));
      } else {
         bresp = new Buffer(new byte[0]);
      }

      toServer.add(bresp);
      return true;
   }
}
