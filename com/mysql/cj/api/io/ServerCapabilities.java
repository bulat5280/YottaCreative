package com.mysql.cj.api.io;

import com.mysql.cj.core.ServerVersion;

public interface ServerCapabilities {
   int getCapabilityFlags();

   void setCapabilityFlags(int var1);

   ServerVersion getServerVersion();

   void setServerVersion(ServerVersion var1);
}
