package org.apache.commons.pool2;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public final class PoolUtils {
   public static void checkRethrow(Throwable t) {
      if (t instanceof ThreadDeath) {
         throw (ThreadDeath)t;
      } else if (t instanceof VirtualMachineError) {
         throw (VirtualMachineError)t;
      }
   }

   public static <T> TimerTask checkMinIdle(ObjectPool<T> pool, int minIdle, long period) throws IllegalArgumentException {
      if (pool == null) {
         throw new IllegalArgumentException("keyedPool must not be null.");
      } else if (minIdle < 0) {
         throw new IllegalArgumentException("minIdle must be non-negative.");
      } else {
         TimerTask task = new PoolUtils.ObjectPoolMinIdleTimerTask(pool, minIdle);
         getMinIdleTimer().schedule(task, 0L, period);
         return task;
      }
   }

   public static <K, V> TimerTask checkMinIdle(KeyedObjectPool<K, V> keyedPool, K key, int minIdle, long period) throws IllegalArgumentException {
      if (keyedPool == null) {
         throw new IllegalArgumentException("keyedPool must not be null.");
      } else if (key == null) {
         throw new IllegalArgumentException("key must not be null.");
      } else if (minIdle < 0) {
         throw new IllegalArgumentException("minIdle must be non-negative.");
      } else {
         TimerTask task = new PoolUtils.KeyedObjectPoolMinIdleTimerTask(keyedPool, key, minIdle);
         getMinIdleTimer().schedule(task, 0L, period);
         return task;
      }
   }

   public static <K, V> Map<K, TimerTask> checkMinIdle(KeyedObjectPool<K, V> keyedPool, Collection<K> keys, int minIdle, long period) throws IllegalArgumentException {
      if (keys == null) {
         throw new IllegalArgumentException("keys must not be null.");
      } else {
         Map<K, TimerTask> tasks = new HashMap(keys.size());
         Iterator iter = keys.iterator();

         while(iter.hasNext()) {
            K key = iter.next();
            TimerTask task = checkMinIdle(keyedPool, key, minIdle, period);
            tasks.put(key, task);
         }

         return tasks;
      }
   }

   public static <T> void prefill(ObjectPool<T> pool, int count) throws Exception, IllegalArgumentException {
      if (pool == null) {
         throw new IllegalArgumentException("pool must not be null.");
      } else {
         for(int i = 0; i < count; ++i) {
            pool.addObject();
         }

      }
   }

   public static <K, V> void prefill(KeyedObjectPool<K, V> keyedPool, K key, int count) throws Exception, IllegalArgumentException {
      if (keyedPool == null) {
         throw new IllegalArgumentException("keyedPool must not be null.");
      } else if (key == null) {
         throw new IllegalArgumentException("key must not be null.");
      } else {
         for(int i = 0; i < count; ++i) {
            keyedPool.addObject(key);
         }

      }
   }

   public static <K, V> void prefill(KeyedObjectPool<K, V> keyedPool, Collection<K> keys, int count) throws Exception, IllegalArgumentException {
      if (keys == null) {
         throw new IllegalArgumentException("keys must not be null.");
      } else {
         Iterator iter = keys.iterator();

         while(iter.hasNext()) {
            prefill(keyedPool, iter.next(), count);
         }

      }
   }

   public static <T> ObjectPool<T> synchronizedPool(ObjectPool<T> pool) {
      if (pool == null) {
         throw new IllegalArgumentException("pool must not be null.");
      } else {
         return new PoolUtils.SynchronizedObjectPool(pool);
      }
   }

   public static <K, V> KeyedObjectPool<K, V> synchronizedPool(KeyedObjectPool<K, V> keyedPool) {
      return new PoolUtils.SynchronizedKeyedObjectPool(keyedPool);
   }

   public static <T> PooledObjectFactory<T> synchronizedPooledFactory(PooledObjectFactory<T> factory) {
      return new PoolUtils.SynchronizedPooledObjectFactory(factory);
   }

   public static <K, V> KeyedPooledObjectFactory<K, V> synchronizedKeyedPooledFactory(KeyedPooledObjectFactory<K, V> keyedFactory) {
      return new PoolUtils.SynchronizedKeyedPooledObjectFactory(keyedFactory);
   }

   public static <T> ObjectPool<T> erodingPool(ObjectPool<T> pool) {
      return erodingPool(pool, 1.0F);
   }

   public static <T> ObjectPool<T> erodingPool(ObjectPool<T> pool, float factor) {
      if (pool == null) {
         throw new IllegalArgumentException("pool must not be null.");
      } else if (factor <= 0.0F) {
         throw new IllegalArgumentException("factor must be positive.");
      } else {
         return new PoolUtils.ErodingObjectPool(pool, factor);
      }
   }

   public static <K, V> KeyedObjectPool<K, V> erodingPool(KeyedObjectPool<K, V> keyedPool) {
      return erodingPool(keyedPool, 1.0F);
   }

   public static <K, V> KeyedObjectPool<K, V> erodingPool(KeyedObjectPool<K, V> keyedPool, float factor) {
      return erodingPool(keyedPool, factor, false);
   }

   public static <K, V> KeyedObjectPool<K, V> erodingPool(KeyedObjectPool<K, V> keyedPool, float factor, boolean perKey) {
      if (keyedPool == null) {
         throw new IllegalArgumentException("keyedPool must not be null.");
      } else if (factor <= 0.0F) {
         throw new IllegalArgumentException("factor must be positive.");
      } else {
         return (KeyedObjectPool)(perKey ? new PoolUtils.ErodingPerKeyKeyedObjectPool(keyedPool, factor) : new PoolUtils.ErodingKeyedObjectPool(keyedPool, factor));
      }
   }

   private static Timer getMinIdleTimer() {
      return PoolUtils.TimerHolder.MIN_IDLE_TIMER;
   }

   private static final class ErodingPerKeyKeyedObjectPool<K, V> extends PoolUtils.ErodingKeyedObjectPool<K, V> {
      private final float factor;
      private final Map<K, PoolUtils.ErodingFactor> factors = Collections.synchronizedMap(new HashMap());

      public ErodingPerKeyKeyedObjectPool(KeyedObjectPool<K, V> keyedPool, float factor) {
         super(keyedPool, (PoolUtils.ErodingFactor)null);
         this.factor = factor;
      }

      protected PoolUtils.ErodingFactor getErodingFactor(K key) {
         PoolUtils.ErodingFactor eFactor = (PoolUtils.ErodingFactor)this.factors.get(key);
         if (eFactor == null) {
            eFactor = new PoolUtils.ErodingFactor(this.factor);
            this.factors.put(key, eFactor);
         }

         return eFactor;
      }

      public String toString() {
         return "ErodingPerKeyKeyedObjectPool{factor=" + this.factor + ", keyedPool=" + this.getKeyedPool() + '}';
      }
   }

   private static class ErodingKeyedObjectPool<K, V> implements KeyedObjectPool<K, V> {
      private final KeyedObjectPool<K, V> keyedPool;
      private final PoolUtils.ErodingFactor erodingFactor;

      public ErodingKeyedObjectPool(KeyedObjectPool<K, V> keyedPool, float factor) {
         this(keyedPool, new PoolUtils.ErodingFactor(factor));
      }

      protected ErodingKeyedObjectPool(KeyedObjectPool<K, V> keyedPool, PoolUtils.ErodingFactor erodingFactor) {
         if (keyedPool == null) {
            throw new IllegalArgumentException("keyedPool must not be null.");
         } else {
            this.keyedPool = keyedPool;
            this.erodingFactor = erodingFactor;
         }
      }

      public V borrowObject(K key) throws Exception, NoSuchElementException, IllegalStateException {
         return this.keyedPool.borrowObject(key);
      }

      public void returnObject(K key, V obj) throws Exception {
         boolean discard = false;
         long now = System.currentTimeMillis();
         PoolUtils.ErodingFactor factor = this.getErodingFactor(key);
         synchronized(this.keyedPool) {
            if (factor.getNextShrink() < now) {
               int numIdle = this.getNumIdle(key);
               if (numIdle > 0) {
                  discard = true;
               }

               factor.update(now, numIdle);
            }
         }

         try {
            if (discard) {
               this.keyedPool.invalidateObject(key, obj);
            } else {
               this.keyedPool.returnObject(key, obj);
            }
         } catch (Exception var10) {
         }

      }

      protected PoolUtils.ErodingFactor getErodingFactor(K key) {
         return this.erodingFactor;
      }

      public void invalidateObject(K key, V obj) {
         try {
            this.keyedPool.invalidateObject(key, obj);
         } catch (Exception var4) {
         }

      }

      public void addObject(K key) throws Exception, IllegalStateException, UnsupportedOperationException {
         this.keyedPool.addObject(key);
      }

      public int getNumIdle() {
         return this.keyedPool.getNumIdle();
      }

      public int getNumIdle(K key) {
         return this.keyedPool.getNumIdle(key);
      }

      public int getNumActive() {
         return this.keyedPool.getNumActive();
      }

      public int getNumActive(K key) {
         return this.keyedPool.getNumActive(key);
      }

      public void clear() throws Exception, UnsupportedOperationException {
         this.keyedPool.clear();
      }

      public void clear(K key) throws Exception, UnsupportedOperationException {
         this.keyedPool.clear(key);
      }

      public void close() {
         try {
            this.keyedPool.close();
         } catch (Exception var2) {
         }

      }

      protected KeyedObjectPool<K, V> getKeyedPool() {
         return this.keyedPool;
      }

      public String toString() {
         return "ErodingKeyedObjectPool{factor=" + this.erodingFactor + ", keyedPool=" + this.keyedPool + '}';
      }
   }

   private static class ErodingObjectPool<T> implements ObjectPool<T> {
      private final ObjectPool<T> pool;
      private final PoolUtils.ErodingFactor factor;

      public ErodingObjectPool(ObjectPool<T> pool, float factor) {
         this.pool = pool;
         this.factor = new PoolUtils.ErodingFactor(factor);
      }

      public T borrowObject() throws Exception, NoSuchElementException, IllegalStateException {
         return this.pool.borrowObject();
      }

      public void returnObject(T obj) {
         boolean discard = false;
         long now = System.currentTimeMillis();
         synchronized(this.pool) {
            if (this.factor.getNextShrink() < now) {
               int numIdle = this.pool.getNumIdle();
               if (numIdle > 0) {
                  discard = true;
               }

               this.factor.update(now, numIdle);
            }
         }

         try {
            if (discard) {
               this.pool.invalidateObject(obj);
            } else {
               this.pool.returnObject(obj);
            }
         } catch (Exception var8) {
         }

      }

      public void invalidateObject(T obj) {
         try {
            this.pool.invalidateObject(obj);
         } catch (Exception var3) {
         }

      }

      public void addObject() throws Exception, IllegalStateException, UnsupportedOperationException {
         this.pool.addObject();
      }

      public int getNumIdle() {
         return this.pool.getNumIdle();
      }

      public int getNumActive() {
         return this.pool.getNumActive();
      }

      public void clear() throws Exception, UnsupportedOperationException {
         this.pool.clear();
      }

      public void close() {
         try {
            this.pool.close();
         } catch (Exception var2) {
         }

      }

      public String toString() {
         return "ErodingObjectPool{factor=" + this.factor + ", pool=" + this.pool + '}';
      }
   }

   private static final class ErodingFactor {
      private final float factor;
      private transient volatile long nextShrink;
      private transient volatile int idleHighWaterMark;

      public ErodingFactor(float factor) {
         this.factor = factor;
         this.nextShrink = System.currentTimeMillis() + (long)(900000.0F * factor);
         this.idleHighWaterMark = 1;
      }

      public void update(long now, int numIdle) {
         int idle = Math.max(0, numIdle);
         this.idleHighWaterMark = Math.max(idle, this.idleHighWaterMark);
         float maxInterval = 15.0F;
         float minutes = 15.0F + -14.0F / (float)this.idleHighWaterMark * (float)idle;
         this.nextShrink = now + (long)(minutes * 60000.0F * this.factor);
      }

      public long getNextShrink() {
         return this.nextShrink;
      }

      public String toString() {
         return "ErodingFactor{factor=" + this.factor + ", idleHighWaterMark=" + this.idleHighWaterMark + '}';
      }
   }

   private static final class SynchronizedKeyedPooledObjectFactory<K, V> implements KeyedPooledObjectFactory<K, V> {
      private final WriteLock writeLock = (new ReentrantReadWriteLock()).writeLock();
      private final KeyedPooledObjectFactory<K, V> keyedFactory;

      SynchronizedKeyedPooledObjectFactory(KeyedPooledObjectFactory<K, V> keyedFactory) throws IllegalArgumentException {
         if (keyedFactory == null) {
            throw new IllegalArgumentException("keyedFactory must not be null.");
         } else {
            this.keyedFactory = keyedFactory;
         }
      }

      public PooledObject<V> makeObject(K key) throws Exception {
         this.writeLock.lock();

         PooledObject var2;
         try {
            var2 = this.keyedFactory.makeObject(key);
         } finally {
            this.writeLock.unlock();
         }

         return var2;
      }

      public void destroyObject(K key, PooledObject<V> p) throws Exception {
         this.writeLock.lock();

         try {
            this.keyedFactory.destroyObject(key, p);
         } finally {
            this.writeLock.unlock();
         }

      }

      public boolean validateObject(K key, PooledObject<V> p) {
         this.writeLock.lock();

         boolean var3;
         try {
            var3 = this.keyedFactory.validateObject(key, p);
         } finally {
            this.writeLock.unlock();
         }

         return var3;
      }

      public void activateObject(K key, PooledObject<V> p) throws Exception {
         this.writeLock.lock();

         try {
            this.keyedFactory.activateObject(key, p);
         } finally {
            this.writeLock.unlock();
         }

      }

      public void passivateObject(K key, PooledObject<V> p) throws Exception {
         this.writeLock.lock();

         try {
            this.keyedFactory.passivateObject(key, p);
         } finally {
            this.writeLock.unlock();
         }

      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append("SynchronizedKeyedPoolableObjectFactory");
         sb.append("{keyedFactory=").append(this.keyedFactory);
         sb.append('}');
         return sb.toString();
      }
   }

   private static final class SynchronizedPooledObjectFactory<T> implements PooledObjectFactory<T> {
      private final WriteLock writeLock = (new ReentrantReadWriteLock()).writeLock();
      private final PooledObjectFactory<T> factory;

      SynchronizedPooledObjectFactory(PooledObjectFactory<T> factory) throws IllegalArgumentException {
         if (factory == null) {
            throw new IllegalArgumentException("factory must not be null.");
         } else {
            this.factory = factory;
         }
      }

      public PooledObject<T> makeObject() throws Exception {
         this.writeLock.lock();

         PooledObject var1;
         try {
            var1 = this.factory.makeObject();
         } finally {
            this.writeLock.unlock();
         }

         return var1;
      }

      public void destroyObject(PooledObject<T> p) throws Exception {
         this.writeLock.lock();

         try {
            this.factory.destroyObject(p);
         } finally {
            this.writeLock.unlock();
         }

      }

      public boolean validateObject(PooledObject<T> p) {
         this.writeLock.lock();

         boolean var2;
         try {
            var2 = this.factory.validateObject(p);
         } finally {
            this.writeLock.unlock();
         }

         return var2;
      }

      public void activateObject(PooledObject<T> p) throws Exception {
         this.writeLock.lock();

         try {
            this.factory.activateObject(p);
         } finally {
            this.writeLock.unlock();
         }

      }

      public void passivateObject(PooledObject<T> p) throws Exception {
         this.writeLock.lock();

         try {
            this.factory.passivateObject(p);
         } finally {
            this.writeLock.unlock();
         }

      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append("SynchronizedPoolableObjectFactory");
         sb.append("{factory=").append(this.factory);
         sb.append('}');
         return sb.toString();
      }
   }

   private static final class SynchronizedKeyedObjectPool<K, V> implements KeyedObjectPool<K, V> {
      private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
      private final KeyedObjectPool<K, V> keyedPool;

      SynchronizedKeyedObjectPool(KeyedObjectPool<K, V> keyedPool) throws IllegalArgumentException {
         if (keyedPool == null) {
            throw new IllegalArgumentException("keyedPool must not be null.");
         } else {
            this.keyedPool = keyedPool;
         }
      }

      public V borrowObject(K key) throws Exception, NoSuchElementException, IllegalStateException {
         WriteLock writeLock = this.readWriteLock.writeLock();
         writeLock.lock();

         Object var3;
         try {
            var3 = this.keyedPool.borrowObject(key);
         } finally {
            writeLock.unlock();
         }

         return var3;
      }

      public void returnObject(K key, V obj) {
         WriteLock writeLock = this.readWriteLock.writeLock();
         writeLock.lock();

         try {
            this.keyedPool.returnObject(key, obj);
         } catch (Exception var8) {
         } finally {
            writeLock.unlock();
         }

      }

      public void invalidateObject(K key, V obj) {
         WriteLock writeLock = this.readWriteLock.writeLock();
         writeLock.lock();

         try {
            this.keyedPool.invalidateObject(key, obj);
         } catch (Exception var8) {
         } finally {
            writeLock.unlock();
         }

      }

      public void addObject(K key) throws Exception, IllegalStateException, UnsupportedOperationException {
         WriteLock writeLock = this.readWriteLock.writeLock();
         writeLock.lock();

         try {
            this.keyedPool.addObject(key);
         } finally {
            writeLock.unlock();
         }

      }

      public int getNumIdle(K key) {
         ReadLock readLock = this.readWriteLock.readLock();
         readLock.lock();

         int var3;
         try {
            var3 = this.keyedPool.getNumIdle(key);
         } finally {
            readLock.unlock();
         }

         return var3;
      }

      public int getNumActive(K key) {
         ReadLock readLock = this.readWriteLock.readLock();
         readLock.lock();

         int var3;
         try {
            var3 = this.keyedPool.getNumActive(key);
         } finally {
            readLock.unlock();
         }

         return var3;
      }

      public int getNumIdle() {
         ReadLock readLock = this.readWriteLock.readLock();
         readLock.lock();

         int var2;
         try {
            var2 = this.keyedPool.getNumIdle();
         } finally {
            readLock.unlock();
         }

         return var2;
      }

      public int getNumActive() {
         ReadLock readLock = this.readWriteLock.readLock();
         readLock.lock();

         int var2;
         try {
            var2 = this.keyedPool.getNumActive();
         } finally {
            readLock.unlock();
         }

         return var2;
      }

      public void clear() throws Exception, UnsupportedOperationException {
         WriteLock writeLock = this.readWriteLock.writeLock();
         writeLock.lock();

         try {
            this.keyedPool.clear();
         } finally {
            writeLock.unlock();
         }

      }

      public void clear(K key) throws Exception, UnsupportedOperationException {
         WriteLock writeLock = this.readWriteLock.writeLock();
         writeLock.lock();

         try {
            this.keyedPool.clear(key);
         } finally {
            writeLock.unlock();
         }

      }

      public void close() {
         WriteLock writeLock = this.readWriteLock.writeLock();
         writeLock.lock();

         try {
            this.keyedPool.close();
         } catch (Exception var6) {
         } finally {
            writeLock.unlock();
         }

      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append("SynchronizedKeyedObjectPool");
         sb.append("{keyedPool=").append(this.keyedPool);
         sb.append('}');
         return sb.toString();
      }
   }

   private static final class SynchronizedObjectPool<T> implements ObjectPool<T> {
      private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
      private final ObjectPool<T> pool;

      SynchronizedObjectPool(ObjectPool<T> pool) throws IllegalArgumentException {
         if (pool == null) {
            throw new IllegalArgumentException("pool must not be null.");
         } else {
            this.pool = pool;
         }
      }

      public T borrowObject() throws Exception, NoSuchElementException, IllegalStateException {
         WriteLock writeLock = this.readWriteLock.writeLock();
         writeLock.lock();

         Object var2;
         try {
            var2 = this.pool.borrowObject();
         } finally {
            writeLock.unlock();
         }

         return var2;
      }

      public void returnObject(T obj) {
         WriteLock writeLock = this.readWriteLock.writeLock();
         writeLock.lock();

         try {
            this.pool.returnObject(obj);
         } catch (Exception var7) {
         } finally {
            writeLock.unlock();
         }

      }

      public void invalidateObject(T obj) {
         WriteLock writeLock = this.readWriteLock.writeLock();
         writeLock.lock();

         try {
            this.pool.invalidateObject(obj);
         } catch (Exception var7) {
         } finally {
            writeLock.unlock();
         }

      }

      public void addObject() throws Exception, IllegalStateException, UnsupportedOperationException {
         WriteLock writeLock = this.readWriteLock.writeLock();
         writeLock.lock();

         try {
            this.pool.addObject();
         } finally {
            writeLock.unlock();
         }

      }

      public int getNumIdle() {
         ReadLock readLock = this.readWriteLock.readLock();
         readLock.lock();

         int var2;
         try {
            var2 = this.pool.getNumIdle();
         } finally {
            readLock.unlock();
         }

         return var2;
      }

      public int getNumActive() {
         ReadLock readLock = this.readWriteLock.readLock();
         readLock.lock();

         int var2;
         try {
            var2 = this.pool.getNumActive();
         } finally {
            readLock.unlock();
         }

         return var2;
      }

      public void clear() throws Exception, UnsupportedOperationException {
         WriteLock writeLock = this.readWriteLock.writeLock();
         writeLock.lock();

         try {
            this.pool.clear();
         } finally {
            writeLock.unlock();
         }

      }

      public void close() {
         WriteLock writeLock = this.readWriteLock.writeLock();
         writeLock.lock();

         try {
            this.pool.close();
         } catch (Exception var6) {
         } finally {
            writeLock.unlock();
         }

      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append("SynchronizedObjectPool");
         sb.append("{pool=").append(this.pool);
         sb.append('}');
         return sb.toString();
      }
   }

   private static final class KeyedObjectPoolMinIdleTimerTask<K, V> extends TimerTask {
      private final int minIdle;
      private final K key;
      private final KeyedObjectPool<K, V> keyedPool;

      KeyedObjectPoolMinIdleTimerTask(KeyedObjectPool<K, V> keyedPool, K key, int minIdle) throws IllegalArgumentException {
         if (keyedPool == null) {
            throw new IllegalArgumentException("keyedPool must not be null.");
         } else {
            this.keyedPool = keyedPool;
            this.key = key;
            this.minIdle = minIdle;
         }
      }

      public void run() {
         boolean success = false;

         try {
            if (this.keyedPool.getNumIdle(this.key) < this.minIdle) {
               this.keyedPool.addObject(this.key);
            }

            success = true;
         } catch (Exception var6) {
            this.cancel();
         } finally {
            if (!success) {
               this.cancel();
            }

         }

      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append("KeyedObjectPoolMinIdleTimerTask");
         sb.append("{minIdle=").append(this.minIdle);
         sb.append(", key=").append(this.key);
         sb.append(", keyedPool=").append(this.keyedPool);
         sb.append('}');
         return sb.toString();
      }
   }

   private static final class ObjectPoolMinIdleTimerTask<T> extends TimerTask {
      private final int minIdle;
      private final ObjectPool<T> pool;

      ObjectPoolMinIdleTimerTask(ObjectPool<T> pool, int minIdle) throws IllegalArgumentException {
         if (pool == null) {
            throw new IllegalArgumentException("pool must not be null.");
         } else {
            this.pool = pool;
            this.minIdle = minIdle;
         }
      }

      public void run() {
         boolean success = false;

         try {
            if (this.pool.getNumIdle() < this.minIdle) {
               this.pool.addObject();
            }

            success = true;
         } catch (Exception var6) {
            this.cancel();
         } finally {
            if (!success) {
               this.cancel();
            }

         }

      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append("ObjectPoolMinIdleTimerTask");
         sb.append("{minIdle=").append(this.minIdle);
         sb.append(", pool=").append(this.pool);
         sb.append('}');
         return sb.toString();
      }
   }

   static class TimerHolder {
      static final Timer MIN_IDLE_TIMER = new Timer(true);
   }
}
