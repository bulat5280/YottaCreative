package org.flywaydb.core.api.logging;

public interface Log {
   void debug(String var1);

   void info(String var1);

   void warn(String var1);

   void error(String var1);

   void error(String var1, Exception var2);
}
