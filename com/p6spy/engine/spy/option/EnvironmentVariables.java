package com.p6spy.engine.spy.option;

import com.p6spy.engine.spy.P6ModuleManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class EnvironmentVariables implements P6OptionsSource {
   public Map<String, String> getOptions() {
      Map<String, String> result = new HashMap();
      Iterator var2 = System.getenv().entrySet().iterator();

      while(var2.hasNext()) {
         Entry<String, String> entry = (Entry)var2.next();
         String key = (String)entry.getKey();
         if (key.startsWith("p6spy.config.")) {
            result.put(key.substring("p6spy.config.".length()), (String)entry.getValue());
         }
      }

      return result;
   }

   public void postInit(P6ModuleManager p6moduleManager) {
   }

   public void preDestroy(P6ModuleManager p6moduleManager) {
   }
}
