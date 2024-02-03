package com.p6spy.engine.spy.appender;

import com.p6spy.engine.logging.Category;

public interface P6Logger {
   void logSQL(int var1, String var2, long var3, Category var5, String var6, String var7);

   void logException(Exception var1);

   void logText(String var1);

   boolean isCategoryEnabled(Category var1);
}
