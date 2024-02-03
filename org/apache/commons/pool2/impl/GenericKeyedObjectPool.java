package org.apache.commons.pool2.impl;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PoolUtils;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectState;

public class GenericKeyedObjectPool<K, T> extends BaseGenericObjectPool<T> implements KeyedObjectPool<K, T>, GenericKeyedObjectPoolMXBean<K> {
   private volatile int maxIdlePerKey;
   private volatile int minIdlePerKey;
   private volatile int maxTotalPerKey;
   private final KeyedPooledObjectFactory<K, T> factory;
   private final boolean fairness;
   private final Map<K, GenericKeyedObjectPool<K, T>.ObjectDeque<T>> poolMap;
   private final List<K> poolKeyList;
   private final ReadWriteLock keyLock;
   private final AtomicInteger numTotal;
   private Iterator<K> evictionKeyIterator;
   private K evictionKey;
   private static final String ONAME_BASE = "org.apache.commons.pool2:type=GenericKeyedObjectPool,name=";

   public GenericKeyedObjectPool(KeyedPooledObjectFactory<K, T> factory) {
      this(factory, new GenericKeyedObjectPoolConfig());
   }

   public GenericKeyedObjectPool(KeyedPooledObjectFactory<K, T> factory, GenericKeyedObjectPoolConfig config) {
      super(config, "org.apache.commons.pool2:type=GenericKeyedObjectPool,name=", config.getJmxNamePrefix());
      this.maxIdlePerKey = 8;
      this.minIdlePerKey = 0;
      this.maxTotalPerKey = 8;
      this.poolMap = new ConcurrentHashMap();
      this.poolKeyList = new ArrayList();
      this.keyLock = new ReentrantReadWriteLock(true);
      this.numTotal = new AtomicInteger(0);
      this.evictionKeyIterator = null;
      this.evictionKey = null;
      if (factory == null) {
         this.jmxUnregister();
         throw new IllegalArgumentException("factory may not be null");
      } else {
         this.factory = factory;
         this.fairness = config.getFairness();
         this.setConfig(config);
         this.startEvictor(this.getTimeBetweenEvictionRunsMillis());
      }
   }

   public int getMaxTotalPerKey() {
      return this.maxTotalPerKey;
   }

   public void setMaxTotalPerKey(int maxTotalPerKey) {
      this.maxTotalPerKey = maxTotalPerKey;
   }

   public int getMaxIdlePerKey() {
      return this.maxIdlePerKey;
   }

   public void setMaxIdlePerKey(int maxIdlePerKey) {
      this.maxIdlePerKey = maxIdlePerKey;
   }

   public void setMinIdlePerKey(int minIdlePerKey) {
      this.minIdlePerKey = minIdlePerKey;
   }

   public int getMinIdlePerKey() {
      int maxIdlePerKeySave = this.getMaxIdlePerKey();
      return this.minIdlePerKey > maxIdlePerKeySave ? maxIdlePerKeySave : this.minIdlePerKey;
   }

   public void setConfig(GenericKeyedObjectPoolConfig conf) {
      this.setLifo(conf.getLifo());
      this.setMaxIdlePerKey(conf.getMaxIdlePerKey());
      this.setMaxTotalPerKey(conf.getMaxTotalPerKey());
      this.setMaxTotal(conf.getMaxTotal());
      this.setMinIdlePerKey(conf.getMinIdlePerKey());
      this.setMaxWaitMillis(conf.getMaxWaitMillis());
      this.setBlockWhenExhausted(conf.getBlockWhenExhausted());
      this.setTestOnCreate(conf.getTestOnCreate());
      this.setTestOnBorrow(conf.getTestOnBorrow());
      this.setTestOnReturn(conf.getTestOnReturn());
      this.setTestWhileIdle(conf.getTestWhileIdle());
      this.setNumTestsPerEvictionRun(conf.getNumTestsPerEvictionRun());
      this.setMinEvictableIdleTimeMillis(conf.getMinEvictableIdleTimeMillis());
      this.setSoftMinEvictableIdleTimeMillis(conf.getSoftMinEvictableIdleTimeMillis());
      this.setTimeBetweenEvictionRunsMillis(conf.getTimeBetweenEvictionRunsMillis());
      this.setEvictionPolicyClassName(conf.getEvictionPolicyClassName());
      this.setEvictorShutdownTimeoutMillis(conf.getEvictorShutdownTimeoutMillis());
   }

   public KeyedPooledObjectFactory<K, T> getFactory() {
      return this.factory;
   }

   public T borrowObject(K key) throws Exception {
      return this.borrowObject(key, this.getMaxWaitMillis());
   }

   public T borrowObject(K key, long borrowMaxWaitMillis) throws Exception {
      this.assertOpen();
      PooledObject<T> p = null;
      boolean blockWhenExhausted = this.getBlockWhenExhausted();
      long waitTime = System.currentTimeMillis();
      GenericKeyedObjectPool.ObjectDeque objectDeque = this.register(key);

      try {
         while(p == null) {
            boolean create = false;
            p = (PooledObject)objectDeque.getIdleObjects().pollFirst();
            if (p == null) {
               p = this.create(key);
               if (p != null) {
                  create = true;
               }
            }

            if (blockWhenExhausted) {
               if (p == null) {
                  if (borrowMaxWaitMillis < 0L) {
                     p = (PooledObject)objectDeque.getIdleObjects().takeFirst();
                  } else {
                     p = (PooledObject)objectDeque.getIdleObjects().pollFirst(borrowMaxWaitMillis, TimeUnit.MILLISECONDS);
                  }
               }

               if (p == null) {
                  throw new NoSuchElementException("Timeout waiting for idle object");
               }
            } else if (p == null) {
               throw new NoSuchElementException("Pool exhausted");
            }

            if (!p.allocate()) {
               p = null;
            }

            if (p != null) {
               try {
                  this.factory.activateObject(key, p);
               } catch (Exception var22) {
                  try {
                     this.destroy(key, p, true);
                  } catch (Exception var21) {
                  }

                  p = null;
                  if (create) {
                     NoSuchElementException nsee = new NoSuchElementException("Unable to activate object");
                     nsee.initCause(var22);
                     throw nsee;
                  }
               }

               if (p != null && (this.getTestOnBorrow() || create && this.getTestOnCreate())) {
                  boolean validate = false;
                  Throwable validationThrowable = null;

                  try {
                     validate = this.factory.validateObject(key, p);
                  } catch (Throwable var20) {
                     PoolUtils.checkRethrow(var20);
                     validationThrowable = var20;
                  }

                  if (!validate) {
                     try {
                        this.destroy(key, p, true);
                        this.destroyedByBorrowValidationCount.incrementAndGet();
                     } catch (Exception var19) {
                     }

                     p = null;
                     if (create) {
                        NoSuchElementException nsee = new NoSuchElementException("Unable to validate object");
                        nsee.initCause(validationThrowable);
                        throw nsee;
                     }
                  }
               }
            }
         }
      } finally {
         this.deregister(key);
      }

      this.updateStatsBorrow(p, System.currentTimeMillis() - waitTime);
      return p.getObject();
   }

   public void returnObject(K key, T obj) {
      GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = (GenericKeyedObjectPool.ObjectDeque)this.poolMap.get(key);
      PooledObject<T> p = (PooledObject)objectDeque.getAllObjects().get(new BaseGenericObjectPool.IdentityWrapper(obj));
      if (p == null) {
         throw new IllegalStateException("Returned object not currently part of this pool");
      } else {
         synchronized(p) {
            PooledObjectState state = p.getState();
            if (state != PooledObjectState.ALLOCATED) {
               throw new IllegalStateException("Object has already been returned to this pool or is invalid");
            }

            p.markReturning();
         }

         long activeTime = p.getActiveTimeMillis();

         try {
            if (!this.getTestOnReturn() || this.factory.validateObject(key, p)) {
               try {
                  this.factory.passivateObject(key, p);
               } catch (Exception var24) {
                  this.swallowException(var24);

                  try {
                     this.destroy(key, p, true);
                  } catch (Exception var20) {
                     this.swallowException(var20);
                  }

                  if (objectDeque.idleObjects.hasTakeWaiters()) {
                     try {
                        this.addObject(key);
                     } catch (Exception var19) {
                        this.swallowException(var19);
                     }

                     return;
                  }

                  return;
               }

               if (!p.deallocate()) {
                  throw new IllegalStateException("Object has already been returned to this pool");
               }

               int maxIdle = this.getMaxIdlePerKey();
               LinkedBlockingDeque<PooledObject<T>> idleObjects = objectDeque.getIdleObjects();
               if (this.isClosed() || maxIdle > -1 && maxIdle <= idleObjects.size()) {
                  try {
                     this.destroy(key, p, true);
                  } catch (Exception var23) {
                     this.swallowException(var23);
                  }

                  return;
               }

               if (this.getLifo()) {
                  idleObjects.addFirst(p);
               } else {
                  idleObjects.addLast(p);
               }

               if (this.isClosed()) {
                  this.clear(key);
               }

               return;
            }

            try {
               this.destroy(key, p, true);
            } catch (Exception var22) {
               this.swallowException(var22);
            }

            if (objectDeque.idleObjects.hasTakeWaiters()) {
               try {
                  this.addObject(key);
               } catch (Exception var21) {
                  this.swallowException(var21);
               }
            }
         } finally {
            if (this.hasBorrowWaiters()) {
               this.reuseCapacity();
            }

            this.updateStatsReturn(activeTime);
         }

      }
   }

   public void invalidateObject(K key, T obj) throws Exception {
      GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = (GenericKeyedObjectPool.ObjectDeque)this.poolMap.get(key);
      PooledObject<T> p = (PooledObject)objectDeque.getAllObjects().get(new BaseGenericObjectPool.IdentityWrapper(obj));
      if (p == null) {
         throw new IllegalStateException("Object not currently part of this pool");
      } else {
         synchronized(p) {
            if (p.getState() != PooledObjectState.INVALID) {
               this.destroy(key, p, true);
            }
         }

         if (objectDeque.idleObjects.hasTakeWaiters()) {
            this.addObject(key);
         }

      }
   }

   public void clear() {
      Iterator iter = this.poolMap.keySet().iterator();

      while(iter.hasNext()) {
         this.clear(iter.next());
      }

   }

   public void clear(K key) {
      GenericKeyedObjectPool.ObjectDeque objectDeque = this.register(key);

      try {
         LinkedBlockingDeque<PooledObject<T>> idleObjects = objectDeque.getIdleObjects();

         for(PooledObject p = (PooledObject)idleObjects.poll(); p != null; p = (PooledObject)idleObjects.poll()) {
            try {
               this.destroy(key, p, true);
            } catch (Exception var9) {
               this.swallowException(var9);
            }
         }
      } finally {
         this.deregister(key);
      }

   }

   public int getNumActive() {
      return this.numTotal.get() - this.getNumIdle();
   }

   public int getNumIdle() {
      Iterator<GenericKeyedObjectPool<K, T>.ObjectDeque<T>> iter = this.poolMap.values().iterator();

      int result;
      for(result = 0; iter.hasNext(); result += ((GenericKeyedObjectPool.ObjectDeque)iter.next()).getIdleObjects().size()) {
      }

      return result;
   }

   public int getNumActive(K key) {
      GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = (GenericKeyedObjectPool.ObjectDeque)this.poolMap.get(key);
      return objectDeque != null ? objectDeque.getAllObjects().size() - objectDeque.getIdleObjects().size() : 0;
   }

   public int getNumIdle(K key) {
      GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = (GenericKeyedObjectPool.ObjectDeque)this.poolMap.get(key);
      return objectDeque != null ? objectDeque.getIdleObjects().size() : 0;
   }

   public void close() {
      if (!this.isClosed()) {
         synchronized(this.closeLock) {
            if (!this.isClosed()) {
               this.startEvictor(-1L);
               this.closed = true;
               this.clear();
               this.jmxUnregister();
               Iterator iter = this.poolMap.values().iterator();

               while(iter.hasNext()) {
                  ((GenericKeyedObjectPool.ObjectDeque)iter.next()).getIdleObjects().interuptTakeWaiters();
               }

               this.clear();
            }
         }
      }
   }

   public void clearOldest() {
      Map<PooledObject<T>, K> map = new TreeMap();
      Iterator i$ = this.poolMap.entrySet().iterator();

      while(true) {
         Object k;
         GenericKeyedObjectPool.ObjectDeque deque;
         do {
            if (!i$.hasNext()) {
               int itemsToRemove = (int)((double)map.size() * 0.15D) + 1;
               Iterator iter = map.entrySet().iterator();

               while(iter.hasNext() && itemsToRemove > 0) {
                  Entry<PooledObject<T>, K> entry = (Entry)iter.next();
                  K key = entry.getValue();
                  PooledObject<T> p = (PooledObject)entry.getKey();
                  boolean destroyed = true;

                  try {
                     destroyed = this.destroy(key, p, false);
                  } catch (Exception var9) {
                     this.swallowException(var9);
                  }

                  if (destroyed) {
                     --itemsToRemove;
                  }
               }

               return;
            }

            Entry<K, GenericKeyedObjectPool<K, T>.ObjectDeque<T>> entry = (Entry)i$.next();
            k = entry.getKey();
            deque = (GenericKeyedObjectPool.ObjectDeque)entry.getValue();
         } while(deque == null);

         LinkedBlockingDeque<PooledObject<T>> idleObjects = deque.getIdleObjects();
         Iterator i$ = idleObjects.iterator();

         while(i$.hasNext()) {
            PooledObject<T> p = (PooledObject)i$.next();
            map.put(p, k);
         }
      }
   }

   private void reuseCapacity() {
      int maxTotalPerKeySave = this.getMaxTotalPerKey();
      int maxQueueLength = 0;
      LinkedBlockingDeque<PooledObject<T>> mostLoaded = null;
      K loadedKey = null;
      Iterator i$ = this.poolMap.entrySet().iterator();

      while(i$.hasNext()) {
         Entry<K, GenericKeyedObjectPool<K, T>.ObjectDeque<T>> entry = (Entry)i$.next();
         K k = entry.getKey();
         GenericKeyedObjectPool<K, T>.ObjectDeque<T> deque = (GenericKeyedObjectPool.ObjectDeque)entry.getValue();
         if (deque != null) {
            LinkedBlockingDeque<PooledObject<T>> pool = deque.getIdleObjects();
            int queueLength = pool.getTakeQueueLength();
            if (this.getNumActive(k) < maxTotalPerKeySave && queueLength > maxQueueLength) {
               maxQueueLength = queueLength;
               mostLoaded = pool;
               loadedKey = k;
            }
         }
      }

      if (mostLoaded != null) {
         this.register(loadedKey);

         try {
            PooledObject<T> p = this.create(loadedKey);
            if (p != null) {
               this.addIdleObject(loadedKey, p);
            }
         } catch (Exception var14) {
            this.swallowException(var14);
         } finally {
            this.deregister(loadedKey);
         }
      }

   }

   private boolean hasBorrowWaiters() {
      Iterator i$ = this.poolMap.entrySet().iterator();

      while(i$.hasNext()) {
         Entry<K, GenericKeyedObjectPool<K, T>.ObjectDeque<T>> entry = (Entry)i$.next();
         GenericKeyedObjectPool<K, T>.ObjectDeque<T> deque = (GenericKeyedObjectPool.ObjectDeque)entry.getValue();
         if (deque != null) {
            LinkedBlockingDeque<PooledObject<T>> pool = deque.getIdleObjects();
            if (pool.hasTakeWaiters()) {
               return true;
            }
         }
      }

      return false;
   }

   public void evict() throws Exception {
      this.assertOpen();
      if (this.getNumIdle() != 0) {
         PooledObject<T> underTest = null;
         EvictionPolicy<T> evictionPolicy = this.getEvictionPolicy();
         synchronized(this.evictionLock) {
            EvictionConfig evictionConfig = new EvictionConfig(this.getMinEvictableIdleTimeMillis(), this.getSoftMinEvictableIdleTimeMillis(), this.getMinIdlePerKey());
            boolean testWhileIdle = this.getTestWhileIdle();
            int i = 0;

            for(int m = this.getNumTests(); i < m; ++i) {
               if (this.evictionIterator == null || !this.evictionIterator.hasNext()) {
                  if (this.evictionKeyIterator == null || !this.evictionKeyIterator.hasNext()) {
                     List<K> keyCopy = new ArrayList();
                     Lock readLock = this.keyLock.readLock();
                     readLock.lock();

                     try {
                        keyCopy.addAll(this.poolKeyList);
                     } finally {
                        readLock.unlock();
                     }

                     this.evictionKeyIterator = keyCopy.iterator();
                  }

                  while(this.evictionKeyIterator.hasNext()) {
                     this.evictionKey = this.evictionKeyIterator.next();
                     GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = (GenericKeyedObjectPool.ObjectDeque)this.poolMap.get(this.evictionKey);
                     if (objectDeque != null) {
                        Deque<PooledObject<T>> idleObjects = objectDeque.getIdleObjects();
                        this.evictionIterator = new BaseGenericObjectPool.EvictionIterator(idleObjects);
                        if (this.evictionIterator.hasNext()) {
                           break;
                        }

                        this.evictionIterator = null;
                     }
                  }
               }

               if (this.evictionIterator == null) {
                  return;
               }

               Deque idleObjects;
               try {
                  underTest = this.evictionIterator.next();
                  idleObjects = this.evictionIterator.getIdleObjects();
               } catch (NoSuchElementException var23) {
                  --i;
                  this.evictionIterator = null;
                  continue;
               }

               if (!underTest.startEvictionTest()) {
                  --i;
               } else {
                  boolean evict;
                  try {
                     evict = evictionPolicy.evict(evictionConfig, underTest, ((GenericKeyedObjectPool.ObjectDeque)this.poolMap.get(this.evictionKey)).getIdleObjects().size());
                  } catch (Throwable var22) {
                     PoolUtils.checkRethrow(var22);
                     this.swallowException(new Exception(var22));
                     evict = false;
                  }

                  if (evict) {
                     this.destroy(this.evictionKey, underTest, true);
                     this.destroyedByEvictorCount.incrementAndGet();
                  } else {
                     if (testWhileIdle) {
                        boolean active = false;

                        try {
                           this.factory.activateObject(this.evictionKey, underTest);
                           active = true;
                        } catch (Exception var21) {
                           this.destroy(this.evictionKey, underTest, true);
                           this.destroyedByEvictorCount.incrementAndGet();
                        }

                        if (active) {
                           if (!this.factory.validateObject(this.evictionKey, underTest)) {
                              this.destroy(this.evictionKey, underTest, true);
                              this.destroyedByEvictorCount.incrementAndGet();
                           } else {
                              try {
                                 this.factory.passivateObject(this.evictionKey, underTest);
                              } catch (Exception var20) {
                                 this.destroy(this.evictionKey, underTest, true);
                                 this.destroyedByEvictorCount.incrementAndGet();
                              }
                           }
                        }
                     }

                     if (!underTest.endEvictionTest(idleObjects)) {
                     }
                  }
               }
            }

         }
      }
   }

   private PooledObject<T> create(K key) throws Exception {
      int maxTotalPerKeySave = this.getMaxTotalPerKey();
      if (maxTotalPerKeySave < 0) {
         maxTotalPerKeySave = Integer.MAX_VALUE;
      }

      int maxTotal = this.getMaxTotal();
      GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = (GenericKeyedObjectPool.ObjectDeque)this.poolMap.get(key);
      boolean loop = true;

      while(true) {
         while(loop) {
            int newNumTotal = this.numTotal.incrementAndGet();
            if (maxTotal > -1 && newNumTotal > maxTotal) {
               this.numTotal.decrementAndGet();
               if (this.getNumIdle() == 0) {
                  return null;
               }

               this.clearOldest();
            } else {
               loop = false;
            }
         }

         Boolean create = null;

         while(create == null) {
            synchronized(objectDeque.makeObjectCountLock) {
               long newCreateCount = (long)objectDeque.getCreateCount().incrementAndGet();
               if (newCreateCount > (long)maxTotalPerKeySave) {
                  objectDeque.getCreateCount().decrementAndGet();
                  if (objectDeque.makeObjectCount == 0L) {
                     create = Boolean.FALSE;
                  } else {
                     objectDeque.makeObjectCountLock.wait();
                  }
               } else {
                  objectDeque.makeObjectCount++;
                  create = Boolean.TRUE;
               }
            }
         }

         if (!create) {
            this.numTotal.decrementAndGet();
            return null;
         }

         PooledObject p = null;
         boolean var20 = false;

         try {
            var20 = true;
            p = this.factory.makeObject(key);
            var20 = false;
         } catch (Exception var23) {
            this.numTotal.decrementAndGet();
            objectDeque.getCreateCount().decrementAndGet();
            throw var23;
         } finally {
            if (var20) {
               synchronized(objectDeque.makeObjectCountLock) {
                  objectDeque.makeObjectCount--;
                  objectDeque.makeObjectCountLock.notifyAll();
               }
            }
         }

         synchronized(objectDeque.makeObjectCountLock) {
            objectDeque.makeObjectCount--;
            objectDeque.makeObjectCountLock.notifyAll();
         }

         this.createdCount.incrementAndGet();
         objectDeque.getAllObjects().put(new BaseGenericObjectPool.IdentityWrapper(p.getObject()), p);
         return p;
      }
   }

   private boolean destroy(K key, PooledObject<T> toDestroy, boolean always) throws Exception {
      GenericKeyedObjectPool.ObjectDeque objectDeque = this.register(key);

      boolean var6;
      try {
         boolean isIdle = objectDeque.getIdleObjects().remove(toDestroy);
         if (isIdle || always) {
            objectDeque.getAllObjects().remove(new BaseGenericObjectPool.IdentityWrapper(toDestroy.getObject()));
            toDestroy.invalidate();

            try {
               this.factory.destroyObject(key, toDestroy);
            } finally {
               objectDeque.getCreateCount().decrementAndGet();
               this.destroyedCount.incrementAndGet();
               this.numTotal.decrementAndGet();
            }

            var6 = true;
            return var6;
         }

         var6 = false;
      } finally {
         this.deregister(key);
      }

      return var6;
   }

   private GenericKeyedObjectPool<K, T>.ObjectDeque<T> register(K k) {
      Lock lock = this.keyLock.readLock();
      GenericKeyedObjectPool.ObjectDeque objectDeque = null;

      try {
         lock.lock();
         objectDeque = (GenericKeyedObjectPool.ObjectDeque)this.poolMap.get(k);
         if (objectDeque == null) {
            lock.unlock();
            lock = this.keyLock.writeLock();
            lock.lock();
            objectDeque = (GenericKeyedObjectPool.ObjectDeque)this.poolMap.get(k);
            if (objectDeque == null) {
               objectDeque = new GenericKeyedObjectPool.ObjectDeque(this.fairness);
               objectDeque.getNumInterested().incrementAndGet();
               this.poolMap.put(k, objectDeque);
               this.poolKeyList.add(k);
            } else {
               objectDeque.getNumInterested().incrementAndGet();
            }
         } else {
            objectDeque.getNumInterested().incrementAndGet();
         }
      } finally {
         lock.unlock();
      }

      return objectDeque;
   }

   private void deregister(K k) {
      GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = (GenericKeyedObjectPool.ObjectDeque)this.poolMap.get(k);
      long numInterested = objectDeque.getNumInterested().decrementAndGet();
      if (numInterested == 0L && objectDeque.getCreateCount().get() == 0) {
         Lock writeLock = this.keyLock.writeLock();
         writeLock.lock();

         try {
            if (objectDeque.getCreateCount().get() == 0 && objectDeque.getNumInterested().get() == 0L) {
               this.poolMap.remove(k);
               this.poolKeyList.remove(k);
            }
         } finally {
            writeLock.unlock();
         }
      }

   }

   void ensureMinIdle() throws Exception {
      int minIdlePerKeySave = this.getMinIdlePerKey();
      if (minIdlePerKeySave >= 1) {
         Iterator i$ = this.poolMap.keySet().iterator();

         while(i$.hasNext()) {
            K k = i$.next();
            this.ensureMinIdle(k);
         }

      }
   }

   private void ensureMinIdle(K key) throws Exception {
      GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = (GenericKeyedObjectPool.ObjectDeque)this.poolMap.get(key);
      int deficit = this.calculateDeficit(objectDeque);

      for(int i = 0; i < deficit && this.calculateDeficit(objectDeque) > 0; ++i) {
         this.addObject(key);
         if (objectDeque == null) {
            objectDeque = (GenericKeyedObjectPool.ObjectDeque)this.poolMap.get(key);
         }
      }

   }

   public void addObject(K key) throws Exception {
      this.assertOpen();
      this.register(key);

      try {
         PooledObject<T> p = this.create(key);
         this.addIdleObject(key, p);
      } finally {
         this.deregister(key);
      }

   }

   private void addIdleObject(K key, PooledObject<T> p) throws Exception {
      if (p != null) {
         this.factory.passivateObject(key, p);
         LinkedBlockingDeque<PooledObject<T>> idleObjects = ((GenericKeyedObjectPool.ObjectDeque)this.poolMap.get(key)).getIdleObjects();
         if (this.getLifo()) {
            idleObjects.addFirst(p);
         } else {
            idleObjects.addLast(p);
         }
      }

   }

   public void preparePool(K key) throws Exception {
      int minIdlePerKeySave = this.getMinIdlePerKey();
      if (minIdlePerKeySave >= 1) {
         this.ensureMinIdle(key);
      }
   }

   private int getNumTests() {
      int totalIdle = this.getNumIdle();
      int numTests = this.getNumTestsPerEvictionRun();
      return numTests >= 0 ? Math.min(numTests, totalIdle) : (int)Math.ceil((double)totalIdle / Math.abs((double)numTests));
   }

   private int calculateDeficit(GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque) {
      if (objectDeque == null) {
         return this.getMinIdlePerKey();
      } else {
         int maxTotal = this.getMaxTotal();
         int maxTotalPerKeySave = this.getMaxTotalPerKey();
         int objectDefecit = false;
         int objectDefecit = this.getMinIdlePerKey() - objectDeque.getIdleObjects().size();
         int growLimit;
         if (maxTotalPerKeySave > 0) {
            growLimit = Math.max(0, maxTotalPerKeySave - objectDeque.getIdleObjects().size());
            objectDefecit = Math.min(objectDefecit, growLimit);
         }

         if (maxTotal > 0) {
            growLimit = Math.max(0, maxTotal - this.getNumActive() - this.getNumIdle());
            objectDefecit = Math.min(objectDefecit, growLimit);
         }

         return objectDefecit;
      }
   }

   public Map<String, Integer> getNumActivePerKey() {
      HashMap<String, Integer> result = new HashMap();
      Iterator iter = this.poolMap.entrySet().iterator();

      while(iter.hasNext()) {
         Entry<K, GenericKeyedObjectPool<K, T>.ObjectDeque<T>> entry = (Entry)iter.next();
         if (entry != null) {
            K key = entry.getKey();
            GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDequeue = (GenericKeyedObjectPool.ObjectDeque)entry.getValue();
            if (key != null && objectDequeue != null) {
               result.put(key.toString(), objectDequeue.getAllObjects().size() - objectDequeue.getIdleObjects().size());
            }
         }
      }

      return result;
   }

   public int getNumWaiters() {
      int result = 0;
      if (this.getBlockWhenExhausted()) {
         for(Iterator iter = this.poolMap.values().iterator(); iter.hasNext(); result += ((GenericKeyedObjectPool.ObjectDeque)iter.next()).getIdleObjects().getTakeQueueLength()) {
         }
      }

      return result;
   }

   public Map<String, Integer> getNumWaitersByKey() {
      Map<String, Integer> result = new HashMap();
      Iterator i$ = this.poolMap.entrySet().iterator();

      while(i$.hasNext()) {
         Entry<K, GenericKeyedObjectPool<K, T>.ObjectDeque<T>> entry = (Entry)i$.next();
         K k = entry.getKey();
         GenericKeyedObjectPool<K, T>.ObjectDeque<T> deque = (GenericKeyedObjectPool.ObjectDeque)entry.getValue();
         if (deque != null) {
            if (this.getBlockWhenExhausted()) {
               result.put(k.toString(), deque.getIdleObjects().getTakeQueueLength());
            } else {
               result.put(k.toString(), 0);
            }
         }
      }

      return result;
   }

   public Map<String, List<DefaultPooledObjectInfo>> listAllObjects() {
      Map<String, List<DefaultPooledObjectInfo>> result = new HashMap();
      Iterator i$ = this.poolMap.entrySet().iterator();

      while(true) {
         Object k;
         GenericKeyedObjectPool.ObjectDeque deque;
         do {
            if (!i$.hasNext()) {
               return result;
            }

            Entry<K, GenericKeyedObjectPool<K, T>.ObjectDeque<T>> entry = (Entry)i$.next();
            k = entry.getKey();
            deque = (GenericKeyedObjectPool.ObjectDeque)entry.getValue();
         } while(deque == null);

         List<DefaultPooledObjectInfo> list = new ArrayList();
         result.put(k.toString(), list);
         Iterator i$ = deque.getAllObjects().values().iterator();

         while(i$.hasNext()) {
            PooledObject<T> p = (PooledObject)i$.next();
            list.add(new DefaultPooledObjectInfo(p));
         }
      }
   }

   protected void toStringAppendFields(StringBuilder builder) {
      super.toStringAppendFields(builder);
      builder.append(", maxIdlePerKey=");
      builder.append(this.maxIdlePerKey);
      builder.append(", minIdlePerKey=");
      builder.append(this.minIdlePerKey);
      builder.append(", maxTotalPerKey=");
      builder.append(this.maxTotalPerKey);
      builder.append(", factory=");
      builder.append(this.factory);
      builder.append(", fairness=");
      builder.append(this.fairness);
      builder.append(", poolMap=");
      builder.append(this.poolMap);
      builder.append(", poolKeyList=");
      builder.append(this.poolKeyList);
      builder.append(", keyLock=");
      builder.append(this.keyLock);
      builder.append(", numTotal=");
      builder.append(this.numTotal);
      builder.append(", evictionKeyIterator=");
      builder.append(this.evictionKeyIterator);
      builder.append(", evictionKey=");
      builder.append(this.evictionKey);
   }

   private class ObjectDeque<S> {
      private final LinkedBlockingDeque<PooledObject<S>> idleObjects;
      private final AtomicInteger createCount = new AtomicInteger(0);
      private long makeObjectCount = 0L;
      private final Object makeObjectCountLock = new Object();
      private final Map<BaseGenericObjectPool.IdentityWrapper<S>, PooledObject<S>> allObjects = new ConcurrentHashMap();
      private final AtomicLong numInterested = new AtomicLong(0L);

      public ObjectDeque(boolean fairness) {
         this.idleObjects = new LinkedBlockingDeque(fairness);
      }

      public LinkedBlockingDeque<PooledObject<S>> getIdleObjects() {
         return this.idleObjects;
      }

      public AtomicInteger getCreateCount() {
         return this.createCount;
      }

      public AtomicLong getNumInterested() {
         return this.numInterested;
      }

      public Map<BaseGenericObjectPool.IdentityWrapper<S>, PooledObject<S>> getAllObjects() {
         return this.allObjects;
      }

      public String toString() {
         StringBuilder builder = new StringBuilder();
         builder.append("ObjectDeque [idleObjects=");
         builder.append(this.idleObjects);
         builder.append(", createCount=");
         builder.append(this.createCount);
         builder.append(", allObjects=");
         builder.append(this.allObjects);
         builder.append(", numInterested=");
         builder.append(this.numInterested);
         builder.append("]");
         return builder.toString();
      }
   }
}
