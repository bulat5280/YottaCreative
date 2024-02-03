package org.jooq;

import java.io.Serializable;
import java.util.EventListener;

public interface ExecuteListener extends EventListener, Serializable {
   void start(ExecuteContext var1);

   void renderStart(ExecuteContext var1);

   void renderEnd(ExecuteContext var1);

   void prepareStart(ExecuteContext var1);

   void prepareEnd(ExecuteContext var1);

   void bindStart(ExecuteContext var1);

   void bindEnd(ExecuteContext var1);

   void executeStart(ExecuteContext var1);

   void executeEnd(ExecuteContext var1);

   void outStart(ExecuteContext var1);

   void outEnd(ExecuteContext var1);

   void fetchStart(ExecuteContext var1);

   void resultStart(ExecuteContext var1);

   void recordStart(ExecuteContext var1);

   void recordEnd(ExecuteContext var1);

   void resultEnd(ExecuteContext var1);

   void fetchEnd(ExecuteContext var1);

   void end(ExecuteContext var1);

   void exception(ExecuteContext var1);

   void warning(ExecuteContext var1);
}
