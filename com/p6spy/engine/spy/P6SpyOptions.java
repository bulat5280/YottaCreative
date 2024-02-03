package com.p6spy.engine.spy;

import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.logging.P6LogFactory;
import com.p6spy.engine.spy.appender.FileLogger;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import com.p6spy.engine.spy.appender.P6Logger;
import com.p6spy.engine.spy.appender.SingleLineFormat;
import com.p6spy.engine.spy.option.P6OptionsRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.management.StandardMBean;

public class P6SpyOptions extends StandardMBean implements P6SpyLoadableOptions {
   public static final String AUTOFLUSH = "autoflush";
   public static final String DRIVERLIST = "driverlist";
   public static final String LOGFILE = "logfile";
   public static final String LOG_MESSAGE_FORMAT = "logMessageFormat";
   public static final String APPEND = "append";
   public static final String DATEFORMAT = "dateformat";
   public static final String APPENDER = "appender";
   public static final String MODULELIST = "modulelist";
   public static final String STACKTRACE = "stacktrace";
   public static final String STACKTRACECLASS = "stacktraceclass";
   public static final String RELOADPROPERTIES = "reloadproperties";
   public static final String RELOADPROPERTIESINTERVAL = "reloadpropertiesinterval";
   public static final String JNDICONTEXTFACTORY = "jndicontextfactory";
   public static final String JNDICONTEXTPROVIDERURL = "jndicontextproviderurl";
   public static final String JNDICONTEXTCUSTOM = "jndicontextcustom";
   public static final String REALDATASOURCE = "realdatasource";
   public static final String REALDATASOURCECLASS = "realdatasourceclass";
   public static final String REALDATASOURCEPROPERTIES = "realdatasourceproperties";
   public static final String CUSTOM_LOG_MESSAGE_FORMAT = "customLogMessageFormat";
   public static final String DATABASE_DIALECT_DATE_FORMAT = "databaseDialectDateFormat";
   public static final String DATABASE_DIALECT_BOOLEAN_FORMAT = "databaseDialectBooleanFormat";
   public static final String JMX = "jmx";
   public static final String JMX_PREFIX = "jmxPrefix";
   public static final String DRIVER_NAMES = "driverNames";
   public static final String MODULE_FACTORIES = "moduleFactories";
   public static final String MODULE_NAMES = "moduleNames";
   public static final String LOG_MESSAGE_FORMAT_INSTANCE = "logMessageFormatInstance";
   public static final String APPENDER_INSTANCE = "appenderInstance";
   public static final Map<String, String> defaults = new HashMap();
   private final P6OptionsRepository optionsRepository;

   public P6SpyOptions(P6OptionsRepository optionsRepository) {
      super(P6SpyOptionsMBean.class, false);
      this.optionsRepository = optionsRepository;
   }

   public void load(Map<String, String> options) {
      this.setLogMessageFormat((String)options.get("logMessageFormat"));
      this.setLogfile((String)options.get("logfile"));
      this.setAppend((String)options.get("append"));
      this.setDateformat((String)options.get("dateformat"));
      this.setAppender((String)options.get("appender"));
      this.setModulelist((String)options.get("modulelist"));
      this.setDriverlist((String)options.get("driverlist"));
      this.setStackTrace((String)options.get("stacktrace"));
      this.setStackTraceClass((String)options.get("stacktraceclass"));
      this.setAutoflush((String)options.get("autoflush"));
      this.setReloadProperties((String)options.get("reloadproperties"));
      this.setReloadPropertiesInterval((String)options.get("reloadpropertiesinterval"));
      this.setJNDIContextFactory((String)options.get("jndicontextfactory"));
      this.setJNDIContextProviderURL((String)options.get("jndicontextproviderurl"));
      this.setJNDIContextCustom((String)options.get("jndicontextcustom"));
      this.setRealDataSource((String)options.get("realdatasource"));
      this.setRealDataSourceClass((String)options.get("realdatasourceclass"));
      this.setRealDataSourceProperties((String)options.get("realdatasourceproperties"));
      this.setDatabaseDialectDateFormat((String)options.get("databaseDialectDateFormat"));
      this.setDatabaseDialectBooleanFormat((String)options.get("databaseDialectBooleanFormat"));
      this.setCustomLogMessageFormat((String)options.get("customLogMessageFormat"));
      this.setJmx((String)options.get("jmx"));
      this.setJmxPrefix((String)options.get("jmxPrefix"));
   }

   public static P6SpyLoadableOptions getActiveInstance() {
      return (P6SpyLoadableOptions)P6ModuleManager.getInstance().getOptions(P6SpyOptions.class);
   }

   public void reload() {
      P6ModuleManager.getInstance().reload();
   }

   public Set<P6Factory> getModuleFactories() {
      return this.optionsRepository.getSet(P6Factory.class, "moduleFactories");
   }

   public void setAutoflush(String autoflush) {
      this.optionsRepository.set(Boolean.class, "autoflush", autoflush);
   }

   public void setAutoflush(boolean autoflush) {
      this.optionsRepository.set(Boolean.class, "autoflush", autoflush);
   }

   public boolean getAutoflush() {
      return (Boolean)this.optionsRepository.get(Boolean.class, "autoflush");
   }

   public String getDriverlist() {
      return (String)this.optionsRepository.get(String.class, "driverlist");
   }

   public void setDriverlist(String driverlist) {
      this.optionsRepository.setSet(String.class, "driverNames", driverlist);
      this.optionsRepository.set(String.class, "driverlist", P6Util.joinNullSafe(this.optionsRepository.getSet(String.class, "driverNames"), ","));
   }

   public boolean getReloadProperties() {
      return (Boolean)this.optionsRepository.get(Boolean.class, "reloadproperties");
   }

   public void setReloadProperties(String reloadproperties) {
      this.optionsRepository.set(Boolean.class, "reloadproperties", reloadproperties);
   }

   public void setReloadProperties(boolean reloadproperties) {
      this.optionsRepository.set(Boolean.class, "reloadproperties", reloadproperties);
   }

   public long getReloadPropertiesInterval() {
      return (Long)this.optionsRepository.get(Long.class, "reloadpropertiesinterval");
   }

   public void setReloadPropertiesInterval(String reloadpropertiesinterval) {
      this.optionsRepository.set(Long.class, "reloadpropertiesinterval", reloadpropertiesinterval);
   }

   public void setReloadPropertiesInterval(long reloadpropertiesinterval) {
      this.optionsRepository.set(Long.class, "reloadpropertiesinterval", reloadpropertiesinterval);
   }

   public void setJNDIContextFactory(String jndicontextfactory) {
      this.optionsRepository.set(String.class, "jndicontextfactory", jndicontextfactory);
   }

   public void unSetJNDIContextFactory() {
      this.optionsRepository.setOrUnSet(String.class, "jndicontextfactory", (Object)null, defaults.get("jndicontextfactory"));
   }

   public String getJNDIContextFactory() {
      return (String)this.optionsRepository.get(String.class, "jndicontextfactory");
   }

   public void unSetJNDIContextProviderURL() {
      this.optionsRepository.setOrUnSet(String.class, "jndicontextproviderurl", (Object)null, defaults.get("jndicontextproviderurl"));
   }

   public void setJNDIContextProviderURL(String jndicontextproviderurl) {
      this.optionsRepository.set(String.class, "jndicontextproviderurl", jndicontextproviderurl);
   }

   public String getJNDIContextProviderURL() {
      return (String)this.optionsRepository.get(String.class, "jndicontextproviderurl");
   }

   public void setJNDIContextCustom(String jndicontextcustom) {
      this.optionsRepository.set(String.class, "jndicontextcustom", jndicontextcustom);
   }

   public void unSetJNDIContextCustom() {
      this.optionsRepository.setOrUnSet(String.class, "jndicontextcustom", (Object)null, defaults.get("jndicontextcustom"));
   }

   public String getJNDIContextCustom() {
      return (String)this.optionsRepository.get(String.class, "jndicontextcustom");
   }

   public void setRealDataSource(String realdatasource) {
      this.optionsRepository.set(String.class, "realdatasource", realdatasource);
   }

   public void unSetRealDataSource() {
      this.optionsRepository.setOrUnSet(String.class, "realdatasource", (Object)null, defaults.get("realdatasource"));
   }

   public String getRealDataSource() {
      return (String)this.optionsRepository.get(String.class, "realdatasource");
   }

   public void setRealDataSourceClass(String realdatasourceclass) {
      this.optionsRepository.set(String.class, "realdatasourceclass", realdatasourceclass);
   }

   public void unSetRealDataSourceClass() {
      this.optionsRepository.setOrUnSet(String.class, "realdatasourceclass", (Object)null, defaults.get("realdatasourceclass"));
   }

   public String getRealDataSourceClass() {
      return (String)this.optionsRepository.get(String.class, "realdatasourceclass");
   }

   public void setRealDataSourceProperties(String realdatasourceproperties) {
      this.optionsRepository.set(String.class, "realdatasourceproperties", realdatasourceproperties);
   }

   public void unSetRealDataSourceProperties() {
      this.optionsRepository.setOrUnSet(String.class, "realdatasourceproperties", (Object)null, defaults.get("realdatasourceproperties"));
   }

   public String getRealDataSourceProperties() {
      return (String)this.optionsRepository.get(String.class, "realdatasourceproperties");
   }

   public Set<String> getDriverNames() {
      return this.optionsRepository.getSet(String.class, "driverNames");
   }

   public String getDatabaseDialectDateFormat() {
      return (String)this.optionsRepository.get(String.class, "databaseDialectDateFormat");
   }

   public void setDatabaseDialectDateFormat(String databaseDialectDateFormat) {
      this.optionsRepository.set(String.class, "databaseDialectDateFormat", databaseDialectDateFormat);
   }

   public String getDatabaseDialectBooleanFormat() {
      return (String)this.optionsRepository.get(String.class, "databaseDialectBooleanFormat");
   }

   public void setDatabaseDialectBooleanFormat(String databaseDialectBooleanFormat) {
      this.optionsRepository.set(String.class, "databaseDialectBooleanFormat", databaseDialectBooleanFormat);
   }

   public String getCustomLogMessageFormat() {
      return (String)this.optionsRepository.get(String.class, "customLogMessageFormat");
   }

   public void setCustomLogMessageFormat(String customLogMessageFormat) {
      this.optionsRepository.set(String.class, "customLogMessageFormat", customLogMessageFormat);
   }

   public String getModulelist() {
      return (String)this.optionsRepository.get(String.class, "modulelist");
   }

   public void setModulelist(String modulelist) {
      this.optionsRepository.setSet(String.class, "moduleNames", modulelist);
      this.optionsRepository.set(String.class, "modulelist", P6Util.joinNullSafe(this.optionsRepository.getSet(String.class, "moduleNames"), ","));
      this.optionsRepository.setSet(P6Factory.class, "moduleFactories", modulelist);
   }

   public Set<String> getModuleNames() {
      return this.optionsRepository.getSet(String.class, "moduleNames");
   }

   public void setAppend(boolean append) {
      this.optionsRepository.set(Boolean.class, "append", append);
   }

   public boolean getAppend() {
      return (Boolean)this.optionsRepository.get(Boolean.class, "append");
   }

   public String getAppender() {
      return (String)this.optionsRepository.get(String.class, "appender");
   }

   public P6Logger getAppenderInstance() {
      return (P6Logger)this.optionsRepository.get(P6Logger.class, "appenderInstance");
   }

   public void setAppender(String className) {
      this.optionsRepository.set(String.class, "appender", className);
      this.optionsRepository.set(P6Logger.class, "appenderInstance", className);
   }

   public void setDateformat(String dateformat) {
      this.optionsRepository.set(String.class, "dateformat", dateformat);
   }

   public String getDateformat() {
      return (String)this.optionsRepository.get(String.class, "dateformat");
   }

   public boolean getStackTrace() {
      return (Boolean)this.optionsRepository.get(Boolean.class, "stacktrace");
   }

   public void setStackTrace(boolean stacktrace) {
      this.optionsRepository.set(Boolean.class, "stacktrace", stacktrace);
   }

   public void setStackTrace(String stacktrace) {
      this.optionsRepository.set(Boolean.class, "stacktrace", stacktrace);
   }

   public String getStackTraceClass() {
      return (String)this.optionsRepository.get(String.class, "stacktraceclass");
   }

   public void setStackTraceClass(String stacktraceclass) {
      this.optionsRepository.set(String.class, "stacktraceclass", stacktraceclass);
   }

   public void setLogfile(String logfile) {
      this.optionsRepository.set(String.class, "logfile", logfile);
   }

   public String getLogfile() {
      return (String)this.optionsRepository.get(String.class, "logfile");
   }

   public void setAppend(String append) {
      this.optionsRepository.set(Boolean.class, "append", append);
   }

   public String getLogMessageFormat() {
      return (String)this.optionsRepository.get(String.class, "logMessageFormat");
   }

   public void setLogMessageFormat(String logMessageFormat) {
      this.optionsRepository.set(String.class, "logMessageFormat", logMessageFormat);
      this.optionsRepository.set(MessageFormattingStrategy.class, "logMessageFormatInstance", logMessageFormat);
   }

   public MessageFormattingStrategy getLogMessageFormatInstance() {
      return (MessageFormattingStrategy)this.optionsRepository.get(MessageFormattingStrategy.class, "logMessageFormatInstance");
   }

   public Map<String, String> getDefaults() {
      return defaults;
   }

   public boolean getJmx() {
      return (Boolean)this.optionsRepository.get(Boolean.class, "jmx");
   }

   public void setJmx(String string) {
      this.optionsRepository.set(Boolean.class, "jmx", string);
   }

   public void setJmx(boolean string) {
      this.optionsRepository.set(Boolean.class, "jmx", string);
   }

   public String getJmxPrefix() {
      return (String)this.optionsRepository.get(String.class, "jmxPrefix");
   }

   public void setJmxPrefix(String jmxPrefix) {
      this.optionsRepository.set(String.class, "jmxPrefix", jmxPrefix);
   }

   static {
      defaults.put("logMessageFormat", SingleLineFormat.class.getName());
      defaults.put("logfile", "spy.log");
      defaults.put("append", Boolean.TRUE.toString());
      defaults.put("appender", FileLogger.class.getName());
      defaults.put("modulelist", P6SpyFactory.class.getName() + "," + P6LogFactory.class.getName());
      defaults.put("stacktrace", Boolean.FALSE.toString());
      defaults.put("autoflush", Boolean.FALSE.toString());
      defaults.put("reloadproperties", Boolean.FALSE.toString());
      defaults.put("reloadpropertiesinterval", Long.toString(60L));
      defaults.put("databaseDialectDateFormat", "dd-MMM-yy");
      defaults.put("databaseDialectBooleanFormat", "boolean");
      defaults.put("customLogMessageFormat", String.format("%s|%s|%s|connection%s|%s", "%(currentTime)", "%(executionTime)", "%(category)", "%(connectionId)", "%(sqlSingleLine)"));
      defaults.put("jmx", Boolean.TRUE.toString());
   }
}
