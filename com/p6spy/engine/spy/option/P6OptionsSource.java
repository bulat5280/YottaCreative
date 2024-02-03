package com.p6spy.engine.spy.option;

import com.p6spy.engine.spy.P6ModuleManager;
import java.util.Map;

public interface P6OptionsSource {
   Map<String, String> getOptions();

   void postInit(P6ModuleManager var1);

   void preDestroy(P6ModuleManager var1);
}
