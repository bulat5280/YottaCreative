package com.p6spy.engine.spy;

import java.util.Set;

public interface P6SpyOptionsMBean {
   void reload();

   void setAutoflush(boolean var1);

   boolean getAutoflush();

   String getDriverlist();

   void setDriverlist(String var1);

   Set<String> getDriverNames();

   boolean getReloadProperties();

   void setReloadProperties(boolean var1);

   long getReloadPropertiesInterval();

   void setReloadPropertiesInterval(long var1);

   void setJNDIContextFactory(String var1);

   String getJNDIContextFactory();

   void unSetJNDIContextFactory();

   void setJNDIContextProviderURL(String var1);

   void unSetJNDIContextProviderURL();

   String getJNDIContextProviderURL();

   void setJNDIContextCustom(String var1);

   void unSetJNDIContextCustom();

   String getJNDIContextCustom();

   void setRealDataSource(String var1);

   void unSetRealDataSource();

   String getRealDataSource();

   void setRealDataSourceClass(String var1);

   void unSetRealDataSourceClass();

   String getRealDataSourceClass();

   void setRealDataSourceProperties(String var1);

   void unSetRealDataSourceProperties();

   String getRealDataSourceProperties();

   String getModulelist();

   void setModulelist(String var1);

   Set<String> getModuleNames();

   String getDatabaseDialectDateFormat();

   void setDatabaseDialectDateFormat(String var1);

   String getDatabaseDialectBooleanFormat();

   void setDatabaseDialectBooleanFormat(String var1);

   String getCustomLogMessageFormat();

   void setCustomLogMessageFormat(String var1);

   void setAppend(boolean var1);

   boolean getAppend();

   void setLogfile(String var1);

   String getLogfile();

   String getAppender();

   void setAppender(String var1);

   void setDateformat(String var1);

   String getDateformat();

   boolean getStackTrace();

   void setStackTrace(boolean var1);

   String getStackTraceClass();

   void setStackTraceClass(String var1);

   String getLogMessageFormat();

   void setLogMessageFormat(String var1);

   boolean getJmx();

   void setJmx(boolean var1);

   String getJmxPrefix();

   void setJmxPrefix(String var1);
}
