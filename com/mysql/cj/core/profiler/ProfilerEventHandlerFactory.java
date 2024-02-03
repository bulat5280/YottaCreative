package com.mysql.cj.core.profiler;

import com.mysql.cj.api.ProfilerEventHandler;
import com.mysql.cj.api.Session;
import com.mysql.cj.core.util.Util;

public class ProfilerEventHandlerFactory {
   public static synchronized ProfilerEventHandler getInstance(Session sess) {
      ProfilerEventHandler handler = sess.getProfilerEventHandler();
      if (handler == null) {
         handler = (ProfilerEventHandler)Util.getInstance(sess.getPropertySet().getStringReadableProperty("profilerEventHandler").getStringValue(), new Class[0], new Object[0], sess.getExceptionInterceptor());
         handler.init(sess.getLog());
         sess.setProfilerEventHandler(handler);
      }

      return handler;
   }

   public static synchronized void removeInstance(Session sess) {
      ProfilerEventHandler handler = sess.getProfilerEventHandler();
      if (handler != null) {
         handler.destroy();
      }

   }
}
