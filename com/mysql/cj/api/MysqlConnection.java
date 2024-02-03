package com.mysql.cj.api;

import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import java.util.Properties;

public interface MysqlConnection {
   PropertySet getPropertySet();

   void createNewIO(boolean var1);

   long getId();

   Properties getProperties();

   String getProcessHost();

   Object getConnectionMutex();

   Session getSession();

   String getURL();

   String getUser();

   ExceptionInterceptor getExceptionInterceptor();
}
