package org.jooq.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.jooq.FutureResult;
import org.jooq.Record;
import org.jooq.Result;

/** @deprecated */
@Deprecated
final class FutureResultImpl<R extends Record> implements FutureResult<R> {
   private final Future<Result<R>> future;
   private final ExecutorService executor;

   FutureResultImpl(Future<Result<R>> future) {
      this(future, (ExecutorService)null);
   }

   FutureResultImpl(Future<Result<R>> future, ExecutorService executor) {
      this.future = future;
      this.executor = executor;
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      boolean var2;
      try {
         var2 = this.future.cancel(mayInterruptIfRunning);
      } finally {
         if (this.executor != null) {
            this.executor.shutdownNow();
         }

      }

      return var2;
   }

   public boolean isCancelled() {
      return this.future.isCancelled();
   }

   public boolean isDone() {
      return this.future.isDone();
   }

   public Result<R> get() throws InterruptedException, ExecutionException {
      Result var1;
      try {
         var1 = (Result)this.future.get();
      } finally {
         if (this.executor != null) {
            this.executor.shutdownNow();
         }

      }

      return var1;
   }

   public Result<R> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      Result var4;
      try {
         var4 = (Result)this.future.get(timeout, unit);
      } finally {
         if (this.executor != null) {
            this.executor.shutdownNow();
         }

      }

      return var4;
   }
}
