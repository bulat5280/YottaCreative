package org.apache.commons.pool2.impl;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

class EvictionTimer {
   private static ScheduledThreadPoolExecutor executor;
   private static int usageCount;

   private EvictionTimer() {
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("EvictionTimer []");
      return builder.toString();
   }

   static synchronized void schedule(Runnable task, long delay, long period) {
      if (null == executor) {
         executor = new ScheduledThreadPoolExecutor(1, new EvictionTimer.EvictorThreadFactory());
      }

      ++usageCount;
      executor.scheduleWithFixedDelay(task, delay, period, TimeUnit.MILLISECONDS);
   }

   static synchronized void cancel(TimerTask task, long timeout, TimeUnit unit) {
      task.cancel();
      --usageCount;
      if (usageCount == 0) {
         executor.shutdown();

         try {
            executor.awaitTermination(timeout, unit);
         } catch (InterruptedException var5) {
         }

         executor.setCorePoolSize(0);
         executor = null;
      }

   }

   private static class EvictorThreadFactory implements ThreadFactory {
      private EvictorThreadFactory() {
      }

      public Thread newThread(Runnable r) {
         final Thread t = new Thread((ThreadGroup)null, r, "commons-pool-evictor-thread");
         AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
               t.setContextClassLoader(EvictionTimer.EvictorThreadFactory.class.getClassLoader());
               return null;
            }
         });
         return t;
      }

      // $FF: synthetic method
      EvictorThreadFactory(Object x0) {
         this();
      }
   }
}
