package com.mysql.cj.core.profiler;

import com.mysql.cj.api.ProfilerEvent;
import com.mysql.cj.api.ProfilerEventHandler;
import com.mysql.cj.api.log.Log;

public class LoggingProfilerEventHandler implements ProfilerEventHandler {
   private Log logger;

   public void consumeEvent(ProfilerEvent evt) {
      if (evt.getEventType() == 0) {
         this.logger.logWarn(evt);
      } else {
         this.logger.logInfo(evt);
      }

   }

   public void destroy() {
      this.logger = null;
   }

   public void init(Log log) {
      this.logger = log;
   }
}
