package com.p6spy.engine.spy;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import com.p6spy.engine.spy.appender.P6Logger;
import java.util.Set;

public interface P6SpyLoadableOptions extends P6LoadableOptions, P6SpyOptionsMBean {
   Set<P6Factory> getModuleFactories();

   void setAutoflush(String var1);

   void setReloadProperties(String var1);

   void setReloadPropertiesInterval(String var1);

   void setStackTrace(String var1);

   void setAppend(String var1);

   P6Logger getAppenderInstance();

   MessageFormattingStrategy getLogMessageFormatInstance();

   void setJmx(String var1);
}
