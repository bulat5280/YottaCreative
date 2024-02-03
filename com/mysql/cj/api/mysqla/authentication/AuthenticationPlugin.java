package com.mysql.cj.api.mysqla.authentication;

import com.mysql.cj.api.io.Protocol;
import com.mysql.cj.api.mysqla.io.PacketPayload;
import java.util.List;

public interface AuthenticationPlugin {
   default void init(Protocol protocol) {
   }

   default void destroy() {
   }

   String getProtocolPluginName();

   boolean requiresConfidentiality();

   boolean isReusable();

   void setAuthenticationParameters(String var1, String var2);

   boolean nextAuthenticationStep(PacketPayload var1, List<PacketPayload> var2);
}
