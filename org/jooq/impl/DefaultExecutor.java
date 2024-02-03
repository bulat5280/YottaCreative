package org.jooq.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

public class DefaultExecutor implements Executor {
   private static final Executor EXECUTOR = ForkJoinPool.getCommonPoolParallelism() > 1 ? ForkJoinPool.commonPool() : new Executor() {
      public void execute(Runnable command) {
         (new Thread(command)).start();
      }
   };

   public final void execute(Runnable command) {
      EXECUTOR.execute(command);
   }
}
