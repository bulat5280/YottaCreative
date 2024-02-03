package com.p6spy.engine.spy.option;

import com.p6spy.engine.spy.P6ModuleManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

public class SystemProperties implements P6OptionsSource {
   public static final String P6SPY_PREFIX = "p6spy.config.";

   public Map<String, String> getOptions() {
      Map<String, String> result = new HashMap();
      Iterator var2 = (new HashSet(((Properties)System.getProperties().clone()).entrySet())).iterator();

      while(var2.hasNext()) {
         Entry<Object, Object> entry = (Entry)var2.next();
         String key = entry.getKey().toString();
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
