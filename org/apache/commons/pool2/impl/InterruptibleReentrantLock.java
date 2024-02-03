package org.apache.commons.pool2.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class InterruptibleReentrantLock extends ReentrantLock {
   private static final long serialVersionUID = 1L;

   public InterruptibleReentrantLock(boolean fairness) {
      super(fairness);
   }

   public void interruptWaiters(Condition condition) {
      Collection<Thread> threads = this.getWaitingThreads(condition);
      Iterator i$ = threads.iterator();

      while(i$.hasNext()) {
         Thread thread = (Thread)i$.next();
         thread.interrupt();
      }

   }
}
