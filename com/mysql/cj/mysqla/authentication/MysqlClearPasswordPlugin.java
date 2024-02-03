package com.mysql.cj.mysqla.authentication;

import com.mysql.cj.api.io.Protocol;
import com.mysql.cj.api.mysqla.authentication.AuthenticationPlugin;
import com.mysql.cj.api.mysqla.io.NativeProtocol;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import com.mysql.cj.core.util.StringUtils;
import com.mysql.cj.mysqla.io.Buffer;
import java.util.List;

public class MysqlClearPasswordPlugin implements AuthenticationPlugin {
   private Protocol protocol;
   private String password = null;

   public void init(Protocol prot) {
      this.protocol = prot;
   }

   public void destroy() {
      this.password = null;
   }

   public String getProtocolPluginName() {
      return "mysql_clear_password";
   }

   public boolean requiresConfidentiality() {
      return true;
   }

   public boolean isReusable() {
      return true;
   }

   public void setAuthenticationParameters(String user, String password) {
      this.password = password;
   }

   public boolean nextAuthenticationStep(PacketPayload fromServer, List<PacketPayload> toServer) {
      toServer.clear();
      String encoding = this.protocol.versionMeetsMinimum(5, 7, 6) ? this.protocol.getPasswordCharacterEncoding() : "UTF-8";
      PacketPayload bresp = new Buffer(StringUtils.getBytes(this.password != null ? this.password : "", encoding));
      bresp.setPosition(bresp.getPayloadLength());
      bresp.writeInteger(NativeProtocol.IntegerDataType.INT1, 0L);
      bresp.setPosition(0);
      toServer.add(bresp);
      return true;
   }
}
