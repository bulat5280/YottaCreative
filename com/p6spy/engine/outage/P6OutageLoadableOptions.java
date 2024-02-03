package com.p6spy.engine.outage;

import com.p6spy.engine.spy.P6LoadableOptions;

public interface P6OutageLoadableOptions extends P6LoadableOptions, P6OutageOptionsMBean {
   long getOutageDetectionIntervalMS();

   void setOutageDetection(String var1);

   void setOutageDetectionInterval(String var1);
}
