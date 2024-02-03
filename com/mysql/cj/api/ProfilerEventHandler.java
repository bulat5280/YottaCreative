package com.mysql.cj.api;

import com.mysql.cj.api.log.Log;

public interface ProfilerEventHandler {
   void init(Log var1);

   void destroy();

   void consumeEvent(ProfilerEvent var1);
}
