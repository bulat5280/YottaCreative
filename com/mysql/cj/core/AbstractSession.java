package com.mysql.cj.core;

import com.mysql.cj.api.ProfilerEventHandler;
import com.mysql.cj.api.Session;
import com.mysql.cj.api.conf.PropertySet;
import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.log.Log;
import com.mysql.cj.core.log.NullLogger;

public abstract class AbstractSession implements Session {
   protected PropertySet propertySet;
   protected ExceptionInterceptor exceptionInterceptor;
   private ProfilerEventHandler eventSink;
   protected transient Log log;
   protected static final Log NULL_LOGGER = new NullLogger("MySQL");

   public PropertySet getPropertySet() {
      return this.propertySet;
   }

   public ExceptionInterceptor getExceptionInterceptor() {
      return this.exceptionInterceptor;
   }

   public void setExceptionInterceptor(ExceptionInterceptor exceptionInterceptor) {
      this.exceptionInterceptor = exceptionInterceptor;
   }

   public Log getLog() {
      return this.log;
   }

   public void setLog(Log log) {
      this.log = log;
   }

   public ProfilerEventHandler getProfilerEventHandler() {
      return this.eventSink;
   }

   public void setProfilerEventHandler(ProfilerEventHandler h) {
      this.eventSink = h;
   }
}
