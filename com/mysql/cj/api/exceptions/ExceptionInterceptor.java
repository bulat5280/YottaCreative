package com.mysql.cj.api.exceptions;

import com.mysql.cj.api.log.Log;
import java.util.Properties;

public interface ExceptionInterceptor {
   ExceptionInterceptor init(Properties var1, Log var2);

   void destroy();

   Exception interceptException(Exception var1);
}
