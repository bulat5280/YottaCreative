package org.apache.commons.pool2.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import org.apache.commons.pool2.BaseObject;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.SwallowedExceptionListener;

public abstract class BaseGenericObjectPool<T> extends BaseObject {
   public static final int MEAN_TIMING_STATS_CACHE_SIZE = 100;
   private volatile int maxTotal = -1;
   private volatile boolean blockWhenExhausted = true;
   private volatile long maxWaitMillis = -1L;
   private volatile boolean lifo = true;
   private final boolean fairness;
   private volatile boolean testOnCreate = false;
   private volatile boolean testOnBorrow = false;
   private volatile boolean testOnReturn = false;
   private volatile boolean testWhileIdle = false;
   private volatile long timeBetweenEvictionRunsMillis = -1L;
   private volatile int numTestsPerEvictionRun = 3;
   private volatile long minEvictableIdleTimeMillis = 1800000L;
   private volatile long softMinEvictableIdleTimeMillis = -1L;
   private volatile EvictionPolicy<T> evictionPolicy;
   private volatile long evictorShutdownTimeoutMillis = 10000L;
   final Object closeLock = new Object();
   volatile boolean closed = false;
   final Object evictionLock = new Object();
   private BaseGenericObjectPool<T>.Evictor evictor = null;
   BaseGenericObjectPool<T>.EvictionIterator evictionIterator = null;
   private final WeakReference<ClassLoader> factoryClassLoader;
   private final ObjectName oname;
   private final String creationStackTrace;
   private final AtomicLong borrowedCount = new AtomicLong(0L);
   private final AtomicLong returnedCount = new AtomicLong(0L);
   final AtomicLong createdCount = new AtomicLong(0L);
   final AtomicLong destroyedCount = new AtomicLong(0L);
   final AtomicLong destroyedByEvictorCount = new AtomicLong(0L);
   final AtomicLong destroyedByBorrowValidationCount = new AtomicLong(0L);
   private final BaseGenericObjectPool<T>.StatsStore activeTimes = new BaseGenericObjectPool.StatsStore(100);
   private final BaseGenericObjectPool<T>.StatsStore idleTimes = new BaseGenericObjectPool.StatsStore(100);
   private final BaseGenericObjectPool<T>.StatsStore waitTimes = new BaseGenericObjectPool.StatsStore(100);
   private final AtomicLong maxBorrowWaitTimeMillis = new AtomicLong(0L);
   private volatile SwallowedExceptionListener swallowedExceptionListener = null;

   public BaseGenericObjectPool(BaseObjectPoolConfig config, String jmxNameBase, String jmxNamePrefix) {
      if (config.getJmxEnabled()) {
         this.oname = this.jmxRegister(config, jmxNameBase, jmxNamePrefix);
      } else {
         this.oname = null;
      }

      this.creationStackTrace = this.getStackTrace(new Exception());
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      if (cl == null) {
         this.factoryClassLoader = null;
      } else {
         this.factoryClassLoader = new WeakReference(cl);
      }

      this.fairness = config.getFairness();
   }

   public final int getMaxTotal() {
      return this.maxTotal;
   }

   public final void setMaxTotal(int maxTotal) {
      this.maxTotal = maxTotal;
   }

   public final boolean getBlockWhenExhausted() {
      return this.blockWhenExhausted;
   }

   public final void setBlockWhenExhausted(boolean blockWhenExhausted) {
      this.blockWhenExhausted = blockWhenExhausted;
   }

   public final long getMaxWaitMillis() {
      return this.maxWaitMillis;
   }

   public final void setMaxWaitMillis(long maxWaitMillis) {
      this.maxWaitMillis = maxWaitMillis;
   }

   public final boolean getLifo() {
      return this.lifo;
   }

   public final boolean getFairness() {
      return this.fairness;
   }

   public final void setLifo(boolean lifo) {
      this.lifo = lifo;
   }

   public final boolean getTestOnCreate() {
      return this.testOnCreate;
   }

   public final void setTestOnCreate(boolean testOnCreate) {
      this.testOnCreate = testOnCreate;
   }

   public final boolean getTestOnBorrow() {
      return this.testOnBorrow;
   }

   public final void setTestOnBorrow(boolean testOnBorrow) {
      this.testOnBorrow = testOnBorrow;
   }

   public final boolean getTestOnReturn() {
      return this.testOnReturn;
   }

   public final void setTestOnReturn(boolean testOnReturn) {
      this.testOnReturn = testOnReturn;
   }

   public final boolean getTestWhileIdle() {
      return this.testWhileIdle;
   }

   public final void setTestWhileIdle(boolean testWhileIdle) {
      this.testWhileIdle = testWhileIdle;
   }

   public final long getTimeBetweenEvictionRunsMillis() {
      return this.timeBetweenEvictionRunsMillis;
   }

   public final void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
      this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
      this.startEvictor(timeBetweenEvictionRunsMillis);
   }

   public final int getNumTestsPerEvictionRun() {
      return this.numTestsPerEvictionRun;
   }

   public final void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
      this.numTestsPerEvictionRun = numTestsPerEvictionRun;
   }

   public final long getMinEvictableIdleTimeMillis() {
      return this.minEvictableIdleTimeMillis;
   }

   public final void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
      this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
   }

   public final long getSoftMinEvictableIdleTimeMillis() {
      return this.softMinEvictableIdleTimeMillis;
   }

   public final void setSoftMinEvictableIdleTimeMillis(long softMinEvictableIdleTimeMillis) {
      this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
   }

   public final String getEvictionPolicyClassName() {
      return this.evictionPolicy.getClass().getName();
   }

   public final void setEvictionPolicyClassName(String evictionPolicyClassName) {
      try {
         Class clazz;
         try {
            clazz = Class.forName(evictionPolicyClassName, true, Thread.currentThread().getContextClassLoader());
         } catch (ClassNotFoundException var5) {
            clazz = Class.forName(evictionPolicyClassName);
         }

         Object policy = clazz.getConstructor().newInstance();
         if (policy instanceof EvictionPolicy) {
            EvictionPolicy<T> evicPolicy = (EvictionPolicy)policy;
            this.evictionPolicy = evicPolicy;
         } else {
            throw new IllegalArgumentException("[" + evictionPolicyClassName + "] does not implement EvictionPolicy");
         }
      } catch (ClassNotFoundException var6) {
         throw new IllegalArgumentException("Unable to create EvictionPolicy instance of type " + evictionPolicyClassName, var6);
      } catch (InstantiationException var7) {
         throw new IllegalArgumentException("Unable to create EvictionPolicy instance of type " + evictionPolicyClassName, var7);
      } catch (IllegalAccessException var8) {
         throw new IllegalArgumentException("Unable to create EvictionPolicy instance of type " + evictionPolicyClassName, var8);
      } catch (InvocationTargetException var9) {
         throw new IllegalArgumentException("Unable to create EvictionPolicy instance of type " + evictionPolicyClassName, var9);
      } catch (NoSuchMethodException var10) {
         throw new IllegalArgumentException("Unable to create EvictionPolicy instance of type " + evictionPolicyClassName, var10);
      }
   }

   public final long getEvictorShutdownTimeoutMillis() {
      return this.evictorShutdownTimeoutMillis;
   }

   public final void setEvictorShutdownTimeoutMillis(long evictorShutdownTimeoutMillis) {
      this.evictorShutdownTimeoutMillis = evictorShutdownTimeoutMillis;
   }

   public abstract void close();

   public final boolean isClosed() {
      return this.closed;
   }

   public abstract void evict() throws Exception;

   protected EvictionPolicy<T> getEvictionPolicy() {
      return this.evictionPolicy;
   }

   final void assertOpen() throws IllegalStateException {
      if (this.isClosed()) {
         throw new IllegalStateException("Pool not open");
      }
   }

   final void startEvictor(long delay) {
      synchronized(this.evictionLock) {
         if (null != this.evictor) {
            EvictionTimer.cancel(this.evictor, this.evictorShutdownTimeoutMillis, TimeUnit.MILLISECONDS);
            this.evictor = null;
            this.evictionIterator = null;
         }

         if (delay > 0L) {
            this.evictor = new BaseGenericObjectPool.Evictor();
            EvictionTimer.schedule(this.evictor, delay, delay);
         }

      }
   }

   abstract void ensureMinIdle() throws Exception;

   public final ObjectName getJmxName() {
      return this.oname;
   }

   public final String getCreationStackTrace() {
      return this.creationStackTrace;
   }

   public final long getBorrowedCount() {
      return this.borrowedCount.get();
   }

   public final long getReturnedCount() {
      return this.returnedCount.get();
   }

   public final long getCreatedCount() {
      return this.createdCount.get();
   }

   public final long getDestroyedCount() {
      return this.destroyedCount.get();
   }

   public final long getDestroyedByEvictorCount() {
      return this.destroyedByEvictorCount.get();
   }

   public final long getDestroyedByBorrowValidationCount() {
      return this.destroyedByBorrowValidationCount.get();
   }

   public final long getMeanActiveTimeMillis() {
      return this.activeTimes.getMean();
   }

   public final long getMeanIdleTimeMillis() {
      return this.idleTimes.getMean();
   }

   public final long getMeanBorrowWaitTimeMillis() {
      return this.waitTimes.getMean();
   }

   public final long getMaxBorrowWaitTimeMillis() {
      return this.maxBorrowWaitTimeMillis.get();
   }

   public abstract int getNumIdle();

   public final SwallowedExceptionListener getSwallowedExceptionListener() {
      return this.swallowedExceptionListener;
   }

   public final void setSwallowedExceptionListener(SwallowedExceptionListener swallowedExceptionListener) {
      this.swallowedExceptionListener = swallowedExceptionListener;
   }

   final void swallowException(Exception e) {
      SwallowedExceptionListener listener = this.getSwallowedExceptionListener();
      if (listener != null) {
         try {
            listener.onSwallowException(e);
         } catch (OutOfMemoryError var4) {
            throw var4;
         } catch (VirtualMachineError var5) {
            throw var5;
         } catch (Throwable var6) {
         }

      }
   }

   final void updateStatsBorrow(PooledObject<T> p, long waitTime) {
      this.borrowedCount.incrementAndGet();
      this.idleTimes.add(p.getIdleTimeMillis());
      this.waitTimes.add(waitTime);

      long currentMax;
      do {
         currentMax = this.maxBorrowWaitTimeMillis.get();
      } while(currentMax < waitTime && !this.maxBorrowWaitTimeMillis.compareAndSet(currentMax, waitTime));

   }

   final void updateStatsReturn(long activeTime) {
      this.returnedCount.incrementAndGet();
      this.activeTimes.add(activeTime);
   }

   final void jmxUnregister() {
      if (this.oname != null) {
         try {
            ManagementFactory.getPlatformMBeanServer().unregisterMBean(this.oname);
         } catch (MBeanRegistrationException var2) {
            this.swallowException(var2);
         } catch (InstanceNotFoundException var3) {
            this.swallowException(var3);
         }
      }

   }

   private ObjectName jmxRegister(BaseObjectPoolConfig config, String jmxNameBase, String jmxNamePrefix) {
      ObjectName objectName = null;
      MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
      int i = 1;
      boolean registered = false;
      String base = config.getJmxNameBase();
      if (base == null) {
         base = jmxNameBase;
      }

      while(!registered) {
         try {
            ObjectName objName;
            if (i == 1) {
               objName = new ObjectName(base + jmxNamePrefix);
            } else {
               objName = new ObjectName(base + jmxNamePrefix + i);
            }

            mbs.registerMBean(this, objName);
            objectName = objName;
            registered = true;
         } catch (MalformedObjectNameException var10) {
            if ("pool".equals(jmxNamePrefix) && jmxNameBase.equals(base)) {
               registered = true;
            } else {
               jmxNamePrefix = "pool";
               base = jmxNameBase;
            }
         } catch (InstanceAlreadyExistsException var11) {
            ++i;
         } catch (MBeanRegistrationException var12) {
            registered = true;
         } catch (NotCompliantMBeanException var13) {
            registered = true;
         }
      }

      return objectName;
   }

   private String getStackTrace(Exception e) {
      Writer w = new StringWriter();
      PrintWriter pw = new PrintWriter(w);
      e.printStackTrace(pw);
      return w.toString();
   }

   protected void toStringAppendFields(StringBuilder builder) {
      builder.append("maxTotal=");
      builder.append(this.maxTotal);
      builder.append(", blockWhenExhausted=");
      builder.append(this.blockWhenExhausted);
      builder.append(", maxWaitMillis=");
      builder.append(this.maxWaitMillis);
      builder.append(", lifo=");
      builder.append(this.lifo);
      builder.append(", fairness=");
      builder.append(this.fairness);
      builder.append(", testOnCreate=");
      builder.append(this.testOnCreate);
      builder.append(", testOnBorrow=");
      builder.append(this.testOnBorrow);
      builder.append(", testOnReturn=");
      builder.append(this.testOnReturn);
      builder.append(", testWhileIdle=");
      builder.append(this.testWhileIdle);
      builder.append(", timeBetweenEvictionRunsMillis=");
      builder.append(this.timeBetweenEvictionRunsMillis);
      builder.append(", numTestsPerEvictionRun=");
      builder.append(this.numTestsPerEvictionRun);
      builder.append(", minEvictableIdleTimeMillis=");
      builder.append(this.minEvictableIdleTimeMillis);
      builder.append(", softMinEvictableIdleTimeMillis=");
      builder.append(this.softMinEvictableIdleTimeMillis);
      builder.append(", evictionPolicy=");
      builder.append(this.evictionPolicy);
      builder.append(", closeLock=");
      builder.append(this.closeLock);
      builder.append(", closed=");
      builder.append(this.closed);
      builder.append(", evictionLock=");
      builder.append(this.evictionLock);
      builder.append(", evictor=");
      builder.append(this.evictor);
      builder.append(", evictionIterator=");
      builder.append(this.evictionIterator);
      builder.append(", factoryClassLoader=");
      builder.append(this.factoryClassLoader);
      builder.append(", oname=");
      builder.append(this.oname);
      builder.append(", creationStackTrace=");
      builder.append(this.creationStackTrace);
      builder.append(", borrowedCount=");
      builder.append(this.borrowedCount);
      builder.append(", returnedCount=");
      builder.append(this.returnedCount);
      builder.append(", createdCount=");
      builder.append(this.createdCount);
      builder.append(", destroyedCount=");
      builder.append(this.destroyedCount);
      builder.append(", destroyedByEvictorCount=");
      builder.append(this.destroyedByEvictorCount);
      builder.append(", destroyedByBorrowValidationCount=");
      builder.append(this.destroyedByBorrowValidationCount);
      builder.append(", activeTimes=");
      builder.append(this.activeTimes);
      builder.append(", idleTimes=");
      builder.append(this.idleTimes);
      builder.append(", waitTimes=");
      builder.append(this.waitTimes);
      builder.append(", maxBorrowWaitTimeMillis=");
      builder.append(this.maxBorrowWaitTimeMillis);
      builder.append(", swallowedExceptionListener=");
      builder.append(this.swallowedExceptionListener);
   }

   static class IdentityWrapper<T> {
      private final T instance;

      public IdentityWrapper(T instance) {
         this.instance = instance;
      }

      public int hashCode() {
         return System.identityHashCode(this.instance);
      }

      public boolean equals(Object other) {
         return other instanceof BaseGenericObjectPool.IdentityWrapper && ((BaseGenericObjectPool.IdentityWrapper)other).instance == this.instance;
      }

      public T getObject() {
         return this.instance;
      }

      public String toString() {
         StringBuilder builder = new StringBuilder();
         builder.append("IdentityWrapper [instance=");
         builder.append(this.instance);
         builder.append("]");
         return builder.toString();
      }
   }

   class EvictionIterator implements Iterator<PooledObject<T>> {
      private final Deque<PooledObject<T>> idleObjects;
      private final Iterator<PooledObject<T>> idleObjectIterator;

      EvictionIterator(Deque<PooledObject<T>> idleObjects) {
         this.idleObjects = idleObjects;
         if (BaseGenericObjectPool.this.getLifo()) {
            this.idleObjectIterator = idleObjects.descendingIterator();
         } else {
            this.idleObjectIterator = idleObjects.iterator();
         }

      }

      public Deque<PooledObject<T>> getIdleObjects() {
         return this.idleObjects;
      }

      public boolean hasNext() {
         return this.idleObjectIterator.hasNext();
      }

      public PooledObject<T> next() {
         return (PooledObject)this.idleObjectIterator.next();
      }

      public void remove() {
         this.idleObjectIterator.remove();
      }
   }

   private class StatsStore {
      private final AtomicLong[] values;
      private final int size;
      private int index;

      public StatsStore(int size) {
         this.size = size;
         this.values = new AtomicLong[size];

         for(int i = 0; i < size; ++i) {
            this.values[i] = new AtomicLong(-1L);
         }

      }

      public synchronized void add(long value) {
         this.values[this.index].set(value);
         ++this.index;
         if (this.index == this.size) {
            this.index = 0;
         }

      }

      public long getMean() {
         double result = 0.0D;
         int counter = 0;

         for(int i = 0; i < this.size; ++i) {
            long value = this.values[i].get();
            if (value != -1L) {
               ++counter;
               result = result * ((double)(counter - 1) / (double)counter) + (double)value / (double)counter;
            }
         }

         return (long)result;
      }

      public String toString() {
         StringBuilder builder = new StringBuilder();
         builder.append("StatsStore [values=");
         builder.append(Arrays.toString(this.values));
         builder.append(", size=");
         builder.append(this.size);
         builder.append(", index=");
         builder.append(this.index);
         builder.append("]");
         return builder.toString();
      }
   }

   class Evictor extends TimerTask {
      public void run() {
         ClassLoader savedClassLoader = Thread.currentThread().getContextClassLoader();

         try {
            if (BaseGenericObjectPool.this.factoryClassLoader != null) {
               ClassLoader cl = (ClassLoader)BaseGenericObjectPool.this.factoryClassLoader.get();
               if (cl == null) {
                  this.cancel();
                  return;
               }

               Thread.currentThread().setContextClassLoader(cl);
            }

            try {
               BaseGenericObjectPool.this.evict();
            } catch (Exception var9) {
               BaseGenericObjectPool.this.swallowException(var9);
            } catch (OutOfMemoryError var10) {
               var10.printStackTrace(System.err);
            }

            try {
               BaseGenericObjectPool.this.ensureMinIdle();
            } catch (Exception var8) {
               BaseGenericObjectPool.this.swallowException(var8);
            }

         } finally {
            Thread.currentThread().setContextClassLoader(savedClassLoader);
         }
      }
   }
}
