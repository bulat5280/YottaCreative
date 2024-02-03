package com.p6spy.engine.outage;

import com.p6spy.engine.common.P6LogQuery;
import com.p6spy.engine.logging.Category;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public enum P6OutageDetector implements Runnable {
   INSTANCE;

   private ConcurrentMap<Object, InvocationInfo> pendingMessages = new ConcurrentHashMap();
   private boolean haltThread;

   private P6OutageDetector() {
      ThreadGroup group = new ThreadGroup("P6SpyThreadGroup");
      group.setDaemon(true);
      Thread outageThread = new Thread(group, this, "P6SpyOutageThread");
      outageThread.start();
      P6LogQuery.debug("P6Spy - P6OutageDetector has been invoked.");
      P6LogQuery.debug("P6Spy - P6OutageOptions.getOutageDetectionIntervalMS() = " + P6OutageOptions.getActiveInstance().getOutageDetectionIntervalMS());
   }

   public void run() {
      while(!this.haltThread) {
         this.detectOutage();

         try {
            Thread.sleep(P6OutageOptions.getActiveInstance().getOutageDetectionIntervalMS());
         } catch (Exception var2) {
         }
      }

   }

   public void shutdown() {
      this.haltThread = true;
   }

   public void registerInvocation(Object jdbcObject, long startTime, String category, String ps, String sql) {
      this.pendingMessages.put(jdbcObject, new InvocationInfo(startTime, category, ps, sql));
   }

   public void unregisterInvocation(Object jdbcObject) {
      this.pendingMessages.remove(jdbcObject);
   }

   private void detectOutage() {
      int listSize = this.pendingMessages.size();
      if (listSize != 0) {
         P6LogQuery.debug("P6Spy - detectOutage.pendingMessage.size = " + listSize);
         long currentTime = System.nanoTime();
         long threshold = TimeUnit.MILLISECONDS.toNanos(P6OutageOptions.getActiveInstance().getOutageDetectionIntervalMS());
         Iterator var6 = this.pendingMessages.keySet().iterator();

         while(var6.hasNext()) {
            Object jdbcObject = var6.next();
            InvocationInfo ii = (InvocationInfo)this.pendingMessages.get(jdbcObject);
            if (ii != null && currentTime - ii.startTime > threshold) {
               P6LogQuery.debug("P6Spy - statement exceeded threshold - check log.");
               this.logOutage(ii);
            }
         }

      }
   }

   private void logOutage(InvocationInfo ii) {
      P6LogQuery.logElapsed(-1, System.nanoTime() - ii.startTime, Category.OUTAGE, ii.preparedStmt, ii.sql);
   }
}
