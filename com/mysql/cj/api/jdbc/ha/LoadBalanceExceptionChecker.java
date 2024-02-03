package com.mysql.cj.api.jdbc.ha;

import java.util.Properties;

public interface LoadBalanceExceptionChecker {
   void init(Properties var1);

   void destroy();

   boolean shouldExceptionTriggerFailover(Throwable var1);
}
