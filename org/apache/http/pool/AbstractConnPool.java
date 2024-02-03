package org.apache.http.pool;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@ThreadSafe
public abstract class AbstractConnPool<T, C, E extends PoolEntry<T, C>> implements ConnPool<T, E>, ConnPoolControl<T> {
   private final Lock lock;
   private final ConnFactory<T, C> connFactory;
   private final Map<T, RouteSpecificPool<T, C, E>> routeToPool;
   private final Set<E> leased;
   private final LinkedList<E> available;
   private final LinkedList<PoolEntryFuture<E>> pending;
   private final Map<T, Integer> maxPerRoute;
   private volatile boolean isShutDown;
   private volatile int defaultMaxPerRoute;
   private volatile int maxTotal;
   private volatile int validateAfterInactivity;

   public AbstractConnPool(ConnFactory<T, C> connFactory, int defaultMaxPerRoute, int maxTotal) {
      this.connFactory = (ConnFactory)Args.notNull(connFactory, "Connection factory");
      this.defaultMaxPerRoute = Args.positive(defaultMaxPerRoute, "Max per route value");
      this.maxTotal = Args.positive(maxTotal, "Max total value");
      this.lock = new ReentrantLock();
      this.routeToPool = new HashMap();
      this.leased = new HashSet();
      this.available = new LinkedList();
      this.pending = new LinkedList();
      this.maxPerRoute = new HashMap();
   }

   protected abstract E createEntry(T var1, C var2);

   protected void onLease(E entry) {
   }

   protected void onRelease(E entry) {
   }

   protected void onReuse(E entry) {
   }

   protected boolean validate(E entry) {
      return true;
   }

   public boolean isShutdown() {
      return this.isShutDown;
   }

   public void shutdown() throws IOException {
      if (!this.isShutDown) {
         this.isShutDown = true;
         this.lock.lock();

         try {
            Iterator i$ = this.available.iterator();

            PoolEntry entry;
            while(i$.hasNext()) {
               entry = (PoolEntry)i$.next();
               entry.close();
            }

            i$ = this.leased.iterator();

            while(i$.hasNext()) {
               entry = (PoolEntry)i$.next();
               entry.close();
            }

            i$ = this.routeToPool.values().iterator();

            while(i$.hasNext()) {
               RouteSpecificPool<T, C, E> pool = (RouteSpecificPool)i$.next();
               pool.shutdown();
            }

            this.routeToPool.clear();
            this.leased.clear();
            this.available.clear();
         } finally {
            this.lock.unlock();
         }
      }
   }

   private RouteSpecificPool<T, C, E> getPool(final T route) {
      RouteSpecificPool<T, C, E> pool = (RouteSpecificPool)this.routeToPool.get(route);
      if (pool == null) {
         pool = new RouteSpecificPool<T, C, E>(route) {
            protected E createEntry(C conn) {
               return AbstractConnPool.this.createEntry(route, conn);
            }
         };
         this.routeToPool.put(route, pool);
      }

      return pool;
   }

   public Future<E> lease(final T route, final Object state, FutureCallback<E> callback) {
      Args.notNull(route, "Route");
      Asserts.check(!this.isShutDown, "Connection pool shut down");
      return new PoolEntryFuture<E>(this.lock, callback) {
         public E getPoolEntry(long timeout, TimeUnit tunit) throws InterruptedException, TimeoutException, IOException {
            E entry = AbstractConnPool.this.getPoolEntryBlocking(route, state, timeout, tunit, this);
            AbstractConnPool.this.onLease(entry);
            return entry;
         }
      };
   }

   public Future<E> lease(T route, Object state) {
      return this.lease(route, state, (FutureCallback)null);
   }

   private E getPoolEntryBlocking(T route, Object state, long timeout, TimeUnit tunit, PoolEntryFuture<E> future) throws IOException, InterruptedException, TimeoutException {
      Date deadline = null;
      if (timeout > 0L) {
         deadline = new Date(System.currentTimeMillis() + tunit.toMillis(timeout));
      }

      this.lock.lock();

      PoolEntry var29;
      try {
         RouteSpecificPool pool;
         PoolEntry entry;
         label318: {
            pool = this.getPool(route);
            entry = null;

            while(entry == null) {
               Asserts.check(!this.isShutDown, "Connection pool shut down");

               while(true) {
                  entry = pool.getFree(state);
                  if (entry == null) {
                     break;
                  }

                  if (entry.isExpired(System.currentTimeMillis())) {
                     entry.close();
                  } else if (this.validateAfterInactivity > 0 && entry.getUpdated() + (long)this.validateAfterInactivity <= System.currentTimeMillis() && !this.validate(entry)) {
                     entry.close();
                  }

                  if (!entry.isClosed()) {
                     break;
                  }

                  this.available.remove(entry);
                  pool.free(entry, false);
               }

               if (entry != null) {
                  this.available.remove(entry);
                  this.leased.add(entry);
                  this.onReuse(entry);
                  PoolEntry var25 = entry;
                  return var25;
               }

               int maxPerRoute = this.getMax(route);
               int excess = Math.max(0, pool.getAllocatedCount() + 1 - maxPerRoute);
               int totalUsed;
               if (excess > 0) {
                  for(totalUsed = 0; totalUsed < excess; ++totalUsed) {
                     E lastUsed = pool.getLastUsed();
                     if (lastUsed == null) {
                        break;
                     }

                     lastUsed.close();
                     this.available.remove(lastUsed);
                     pool.remove(lastUsed);
                  }
               }

               if (pool.getAllocatedCount() < maxPerRoute) {
                  totalUsed = this.leased.size();
                  int freeCapacity = Math.max(this.maxTotal - totalUsed, 0);
                  if (freeCapacity > 0) {
                     int totalAvailable = this.available.size();
                     if (totalAvailable > freeCapacity - 1 && !this.available.isEmpty()) {
                        E lastUsed = (PoolEntry)this.available.removeLast();
                        lastUsed.close();
                        RouteSpecificPool<T, C, E> otherpool = this.getPool(lastUsed.getRoute());
                        otherpool.remove(lastUsed);
                     }
                     break label318;
                  }
               }

               boolean success = false;

               try {
                  pool.queue(future);
                  this.pending.add(future);
                  success = future.await(deadline);
               } finally {
                  pool.unqueue(future);
                  this.pending.remove(future);
               }

               if (!success && deadline != null && deadline.getTime() <= System.currentTimeMillis()) {
                  break;
               }
            }

            throw new TimeoutException("Timeout waiting for connection");
         }

         C conn = this.connFactory.create(route);
         entry = pool.add(conn);
         this.leased.add(entry);
         var29 = entry;
      } finally {
         this.lock.unlock();
      }

      return var29;
   }

   public void release(E entry, boolean reusable) {
      this.lock.lock();

      try {
         if (this.leased.remove(entry)) {
            RouteSpecificPool<T, C, E> pool = this.getPool(entry.getRoute());
            pool.free(entry, reusable);
            if (reusable && !this.isShutDown) {
               this.available.addFirst(entry);
               this.onRelease(entry);
            } else {
               entry.close();
            }

            PoolEntryFuture<E> future = pool.nextPending();
            if (future != null) {
               this.pending.remove(future);
            } else {
               future = (PoolEntryFuture)this.pending.poll();
            }

            if (future != null) {
               future.wakeup();
            }
         }
      } finally {
         this.lock.unlock();
      }

   }

   private int getMax(T route) {
      Integer v = (Integer)this.maxPerRoute.get(route);
      return v != null ? v : this.defaultMaxPerRoute;
   }

   public void setMaxTotal(int max) {
      Args.positive(max, "Max value");
      this.lock.lock();

      try {
         this.maxTotal = max;
      } finally {
         this.lock.unlock();
      }

   }

   public int getMaxTotal() {
      this.lock.lock();

      int var1;
      try {
         var1 = this.maxTotal;
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public void setDefaultMaxPerRoute(int max) {
      Args.positive(max, "Max per route value");
      this.lock.lock();

      try {
         this.defaultMaxPerRoute = max;
      } finally {
         this.lock.unlock();
      }

   }

   public int getDefaultMaxPerRoute() {
      this.lock.lock();

      int var1;
      try {
         var1 = this.defaultMaxPerRoute;
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public void setMaxPerRoute(T route, int max) {
      Args.notNull(route, "Route");
      Args.positive(max, "Max per route value");
      this.lock.lock();

      try {
         this.maxPerRoute.put(route, max);
      } finally {
         this.lock.unlock();
      }

   }

   public int getMaxPerRoute(T route) {
      Args.notNull(route, "Route");
      this.lock.lock();

      int var2;
      try {
         var2 = this.getMax(route);
      } finally {
         this.lock.unlock();
      }

      return var2;
   }

   public PoolStats getTotalStats() {
      this.lock.lock();

      PoolStats var1;
      try {
         var1 = new PoolStats(this.leased.size(), this.pending.size(), this.available.size(), this.maxTotal);
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   public PoolStats getStats(T route) {
      Args.notNull(route, "Route");
      this.lock.lock();

      PoolStats var3;
      try {
         RouteSpecificPool<T, C, E> pool = this.getPool(route);
         var3 = new PoolStats(pool.getLeasedCount(), pool.getPendingCount(), pool.getAvailableCount(), this.getMax(route));
      } finally {
         this.lock.unlock();
      }

      return var3;
   }

   public Set<T> getRoutes() {
      this.lock.lock();

      HashSet var1;
      try {
         var1 = new HashSet(this.routeToPool.keySet());
      } finally {
         this.lock.unlock();
      }

      return var1;
   }

   protected void enumAvailable(PoolEntryCallback<T, C> callback) {
      this.lock.lock();

      try {
         Iterator it = this.available.iterator();

         while(it.hasNext()) {
            E entry = (PoolEntry)it.next();
            callback.process(entry);
            if (entry.isClosed()) {
               RouteSpecificPool<T, C, E> pool = this.getPool(entry.getRoute());
               pool.remove(entry);
               it.remove();
            }
         }

         this.purgePoolMap();
      } finally {
         this.lock.unlock();
      }
   }

   protected void enumLeased(PoolEntryCallback<T, C> callback) {
      this.lock.lock();

      try {
         Iterator it = this.leased.iterator();

         while(it.hasNext()) {
            E entry = (PoolEntry)it.next();
            callback.process(entry);
         }
      } finally {
         this.lock.unlock();
      }

   }

   private void purgePoolMap() {
      Iterator it = this.routeToPool.entrySet().iterator();

      while(it.hasNext()) {
         Entry<T, RouteSpecificPool<T, C, E>> entry = (Entry)it.next();
         RouteSpecificPool<T, C, E> pool = (RouteSpecificPool)entry.getValue();
         if (pool.getPendingCount() + pool.getAllocatedCount() == 0) {
            it.remove();
         }
      }

   }

   public void closeIdle(long idletime, TimeUnit tunit) {
      Args.notNull(tunit, "Time unit");
      long time = tunit.toMillis(idletime);
      if (time < 0L) {
         time = 0L;
      }

      final long deadline = System.currentTimeMillis() - time;
      this.enumAvailable(new PoolEntryCallback<T, C>() {
         public void process(PoolEntry<T, C> entry) {
            if (entry.getUpdated() <= deadline) {
               entry.close();
            }

         }
      });
   }

   public void closeExpired() {
      final long now = System.currentTimeMillis();
      this.enumAvailable(new PoolEntryCallback<T, C>() {
         public void process(PoolEntry<T, C> entry) {
            if (entry.isExpired(now)) {
               entry.close();
            }

         }
      });
   }

   public int getValidateAfterInactivity() {
      return this.validateAfterInactivity;
   }

   public void setValidateAfterInactivity(int ms) {
      this.validateAfterInactivity = ms;
   }

   public String toString() {
      StringBuilder buffer = new StringBuilder();
      buffer.append("[leased: ");
      buffer.append(this.leased);
      buffer.append("][available: ");
      buffer.append(this.available);
      buffer.append("][pending: ");
      buffer.append(this.pending);
      buffer.append("]");
      return buffer.toString();
   }
}
