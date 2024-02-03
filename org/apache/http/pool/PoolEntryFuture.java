package org.apache.http.pool;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.Args;

@ThreadSafe
abstract class PoolEntryFuture<T> implements Future<T> {
   private final Lock lock;
   private final FutureCallback<T> callback;
   private final Condition condition;
   private volatile boolean cancelled;
   private volatile boolean completed;
   private T result;

   PoolEntryFuture(Lock lock, FutureCallback<T> callback) {
      this.lock = lock;
      this.condition = lock.newCondition();
      this.callback = callback;
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      this.lock.lock();

      boolean var2;
      try {
         if (this.completed) {
            var2 = false;
            return var2;
         }

         this.completed = true;
         this.cancelled = true;
         if (this.callback != null) {
            this.callback.cancelled();
         }

         this.condition.signalAll();
         var2 = true;
      } finally {
         this.lock.unlock();
      }

      return var2;
   }

   public boolean isCancelled() {
      return this.cancelled;
   }

   public boolean isDone() {
      return this.completed;
   }

   public T get() throws InterruptedException, ExecutionException {
      try {
         return this.get(0L, TimeUnit.MILLISECONDS);
      } catch (TimeoutException var2) {
         throw new ExecutionException(var2);
      }
   }

   public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      Args.notNull(unit, "Time unit");
      this.lock.lock();

      Object var4;
      try {
         if (!this.completed) {
            this.result = this.getPoolEntry(timeout, unit);
            this.completed = true;
            if (this.callback != null) {
               this.callback.completed(this.result);
            }

            var4 = this.result;
            return var4;
         }

         var4 = this.result;
      } catch (IOException var8) {
         this.completed = true;
         this.result = null;
         if (this.callback != null) {
            this.callback.failed(var8);
         }

         throw new ExecutionException(var8);
      } finally {
         this.lock.unlock();
      }

      return var4;
   }

   protected abstract T getPoolEntry(long var1, TimeUnit var3) throws IOException, InterruptedException, TimeoutException;

   public boolean await(Date deadline) throws InterruptedException {
      this.lock.lock();

      boolean var3;
      try {
         if (this.cancelled) {
            throw new InterruptedException("Operation interrupted");
         }

         boolean success;
         if (deadline != null) {
            success = this.condition.awaitUntil(deadline);
         } else {
            this.condition.await();
            success = true;
         }

         if (this.cancelled) {
            throw new InterruptedException("Operation interrupted");
         }

         var3 = success;
      } finally {
         this.lock.unlock();
      }

      return var3;
   }

   public void wakeup() {
      this.lock.lock();

      try {
         this.condition.signalAll();
      } finally {
         this.lock.unlock();
      }

   }
}
