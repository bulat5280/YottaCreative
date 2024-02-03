package org.apache.commons.pool2.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PoolUtils;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.PooledObjectState;
import org.apache.commons.pool2.UsageTracking;

public class GenericObjectPool<T> extends BaseGenericObjectPool<T> implements ObjectPool<T>, GenericObjectPoolMXBean, UsageTracking<T> {
   private volatile String factoryType;
   private volatile int maxIdle;
   private volatile int minIdle;
   private final PooledObjectFactory<T> factory;
   private final Map<BaseGenericObjectPool.IdentityWrapper<T>, PooledObject<T>> allObjects;
   private final AtomicLong createCount;
   private long makeObjectCount;
   private final Object makeObjectCountLock;
   private final LinkedBlockingDeque<PooledObject<T>> idleObjects;
   private static final String ONAME_BASE = "org.apache.commons.pool2:type=GenericObjectPool,name=";
   private volatile AbandonedConfig abandonedConfig;

   public GenericObjectPool(PooledObjectFactory<T> factory) {
      this(factory, new GenericObjectPoolConfig());
   }

   public GenericObjectPool(PooledObjectFactory<T> factory, GenericObjectPoolConfig config) {
      super(config, "org.apache.commons.pool2:type=GenericObjectPool,name=", config.getJmxNamePrefix());
      this.factoryType = null;
      this.maxIdle = 8;
      this.minIdle = 0;
      this.allObjects = new ConcurrentHashMap();
      this.createCount = new AtomicLong(0L);
      this.makeObjectCount = 0L;
      this.makeObjectCountLock = new Object();
      this.abandonedConfig = null;
      if (factory == null) {
         this.jmxUnregister();
         throw new IllegalArgumentException("factory may not be null");
      } else {
         this.factory = factory;
         this.idleObjects = new LinkedBlockingDeque(config.getFairness());
         this.setConfig(config);
         this.startEvictor(this.getTimeBetweenEvictionRunsMillis());
      }
   }

   public GenericObjectPool(PooledObjectFactory<T> factory, GenericObjectPoolConfig config, AbandonedConfig abandonedConfig) {
      this(factory, config);
      this.setAbandonedConfig(abandonedConfig);
   }

   public int getMaxIdle() {
      return this.maxIdle;
   }

   public void setMaxIdle(int maxIdle) {
      this.maxIdle = maxIdle;
   }

   public void setMinIdle(int minIdle) {
      this.minIdle = minIdle;
   }

   public int getMinIdle() {
      int maxIdleSave = this.getMaxIdle();
      return this.minIdle > maxIdleSave ? maxIdleSave : this.minIdle;
   }

   public boolean isAbandonedConfig() {
      return this.abandonedConfig != null;
   }

   public boolean getLogAbandoned() {
      AbandonedConfig ac = this.abandonedConfig;
      return ac != null && ac.getLogAbandoned();
   }

   public boolean getRemoveAbandonedOnBorrow() {
      AbandonedConfig ac = this.abandonedConfig;
      return ac != null && ac.getRemoveAbandonedOnBorrow();
   }

   public boolean getRemoveAbandonedOnMaintenance() {
      AbandonedConfig ac = this.abandonedConfig;
      return ac != null && ac.getRemoveAbandonedOnMaintenance();
   }

   public int getRemoveAbandonedTimeout() {
      AbandonedConfig ac = this.abandonedConfig;
      return ac != null ? ac.getRemoveAbandonedTimeout() : Integer.MAX_VALUE;
   }

   public void setConfig(GenericObjectPoolConfig conf) {
      this.setLifo(conf.getLifo());
      this.setMaxIdle(conf.getMaxIdle());
      this.setMinIdle(conf.getMinIdle());
      this.setMaxTotal(conf.getMaxTotal());
      this.setMaxWaitMillis(conf.getMaxWaitMillis());
      this.setBlockWhenExhausted(conf.getBlockWhenExhausted());
      this.setTestOnCreate(conf.getTestOnCreate());
      this.setTestOnBorrow(conf.getTestOnBorrow());
      this.setTestOnReturn(conf.getTestOnReturn());
      this.setTestWhileIdle(conf.getTestWhileIdle());
      this.setNumTestsPerEvictionRun(conf.getNumTestsPerEvictionRun());
      this.setMinEvictableIdleTimeMillis(conf.getMinEvictableIdleTimeMillis());
      this.setTimeBetweenEvictionRunsMillis(conf.getTimeBetweenEvictionRunsMillis());
      this.setSoftMinEvictableIdleTimeMillis(conf.getSoftMinEvictableIdleTimeMillis());
      this.setEvictionPolicyClassName(conf.getEvictionPolicyClassName());
      this.setEvictorShutdownTimeoutMillis(conf.getEvictorShutdownTimeoutMillis());
   }

   public void setAbandonedConfig(AbandonedConfig abandonedConfig) {
      if (abandonedConfig == null) {
         this.abandonedConfig = null;
      } else {
         this.abandonedConfig = new AbandonedConfig();
         this.abandonedConfig.setLogAbandoned(abandonedConfig.getLogAbandoned());
         this.abandonedConfig.setLogWriter(abandonedConfig.getLogWriter());
         this.abandonedConfig.setRemoveAbandonedOnBorrow(abandonedConfig.getRemoveAbandonedOnBorrow());
         this.abandonedConfig.setRemoveAbandonedOnMaintenance(abandonedConfig.getRemoveAbandonedOnMaintenance());
         this.abandonedConfig.setRemoveAbandonedTimeout(abandonedConfig.getRemoveAbandonedTimeout());
         this.abandonedConfig.setUseUsageTracking(abandonedConfig.getUseUsageTracking());
         this.abandonedConfig.setRequireFullStackTrace(abandonedConfig.getRequireFullStackTrace());
      }

   }

   public PooledObjectFactory<T> getFactory() {
      return this.factory;
   }

   public T borrowObject() throws Exception {
      return this.borrowObject(this.getMaxWaitMillis());
   }

   public T borrowObject(long borrowMaxWaitMillis) throws Exception {
      this.assertOpen();
      AbandonedConfig ac = this.abandonedConfig;
      if (ac != null && ac.getRemoveAbandonedOnBorrow() && this.getNumIdle() < 2 && this.getNumActive() > this.getMaxTotal() - 3) {
         this.removeAbandoned(ac);
      }

      PooledObject<T> p = null;
      boolean blockWhenExhausted = this.getBlockWhenExhausted();
      long waitTime = System.currentTimeMillis();

      while(true) {
         boolean create;
         do {
            do {
               do {
                  if (p != null) {
                     this.updateStatsBorrow(p, System.currentTimeMillis() - waitTime);
                     return p.getObject();
                  }

                  create = false;
                  p = (PooledObject)this.idleObjects.pollFirst();
                  if (p == null) {
                     p = this.create();
                     if (p != null) {
                        create = true;
                     }
                  }

                  if (blockWhenExhausted) {
                     if (p == null) {
                        if (borrowMaxWaitMillis < 0L) {
                           p = (PooledObject)this.idleObjects.takeFirst();
                        } else {
                           p = (PooledObject)this.idleObjects.pollFirst(borrowMaxWaitMillis, TimeUnit.MILLISECONDS);
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
               } while(p == null);

               try {
                  this.factory.activateObject(p);
               } catch (Exception var13) {
                  try {
                     this.destroy(p);
                  } catch (Exception var12) {
                  }

                  p = null;
                  if (create) {
                     NoSuchElementException nsee = new NoSuchElementException("Unable to activate object");
                     nsee.initCause(var13);
                     throw nsee;
                  }
               }
            } while(p == null);
         } while(!this.getTestOnBorrow() && (!create || !this.getTestOnCreate()));

         boolean validate = false;
         Throwable validationThrowable = null;

         try {
            validate = this.factory.validateObject(p);
         } catch (Throwable var15) {
            PoolUtils.checkRethrow(var15);
            validationThrowable = var15;
         }

         if (!validate) {
            try {
               this.destroy(p);
               this.destroyedByBorrowValidationCount.incrementAndGet();
            } catch (Exception var14) {
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

   public void returnObject(T obj) {
      PooledObject<T> p = (PooledObject)this.allObjects.get(new BaseGenericObjectPool.IdentityWrapper(obj));
      if (p == null) {
         if (!this.isAbandonedConfig()) {
            throw new IllegalStateException("Returned object not currently part of this pool");
         }
      } else {
         synchronized(p) {
            PooledObjectState state = p.getState();
            if (state != PooledObjectState.ALLOCATED) {
               throw new IllegalStateException("Object has already been returned to this pool or is invalid");
            }

            p.markReturning();
         }

         long activeTime = p.getActiveTimeMillis();
         if (this.getTestOnReturn() && !this.factory.validateObject(p)) {
            try {
               this.destroy(p);
            } catch (Exception var10) {
               this.swallowException(var10);
            }

            try {
               this.ensureIdle(1, false);
            } catch (Exception var9) {
               this.swallowException(var9);
            }

            this.updateStatsReturn(activeTime);
         } else {
            try {
               this.factory.passivateObject(p);
            } catch (Exception var12) {
               this.swallowException(var12);

               try {
                  this.destroy(p);
               } catch (Exception var8) {
                  this.swallowException(var8);
               }

               try {
                  this.ensureIdle(1, false);
               } catch (Exception var7) {
                  this.swallowException(var7);
               }

               this.updateStatsReturn(activeTime);
               return;
            }

            if (!p.deallocate()) {
               throw new IllegalStateException("Object has already been returned to this pool or is invalid");
            } else {
               int maxIdleSave = this.getMaxIdle();
               if (this.isClosed() || maxIdleSave > -1 && maxIdleSave <= this.idleObjects.size()) {
                  try {
                     this.destroy(p);
                  } catch (Exception var11) {
                     this.swallowException(var11);
                  }
               } else {
                  if (this.getLifo()) {
                     this.idleObjects.addFirst(p);
                  } else {
                     this.idleObjects.addLast(p);
                  }

                  if (this.isClosed()) {
                     this.clear();
                  }
               }

               this.updateStatsReturn(activeTime);
            }
         }
      }
   }

   public void invalidateObject(T obj) throws Exception {
      PooledObject<T> p = (PooledObject)this.allObjects.get(new BaseGenericObjectPool.IdentityWrapper(obj));
      if (p == null) {
         if (!this.isAbandonedConfig()) {
            throw new IllegalStateException("Invalidated object not currently part of this pool");
         }
      } else {
         synchronized(p) {
            if (p.getState() != PooledObjectState.INVALID) {
               this.destroy(p);
            }
         }

         this.ensureIdle(1, false);
      }
   }

   public void clear() {
      for(PooledObject p = (PooledObject)this.idleObjects.poll(); p != null; p = (PooledObject)this.idleObjects.poll()) {
         try {
            this.destroy(p);
         } catch (Exception var3) {
            this.swallowException(var3);
         }
      }

   }

   public int getNumActive() {
      return this.allObjects.size() - this.idleObjects.size();
   }

   public int getNumIdle() {
      return this.idleObjects.size();
   }

   public void close() {
      if (!this.isClosed()) {
         synchronized(this.closeLock) {
            if (!this.isClosed()) {
               this.startEvictor(-1L);
               this.closed = true;
               this.clear();
               this.jmxUnregister();
               this.idleObjects.interuptTakeWaiters();
            }
         }
      }
   }

   public void evict() throws Exception {
      this.assertOpen();
      if (this.idleObjects.size() > 0) {
         PooledObject<T> underTest = null;
         EvictionPolicy<T> evictionPolicy = this.getEvictionPolicy();
         synchronized(this.evictionLock) {
            EvictionConfig evictionConfig = new EvictionConfig(this.getMinEvictableIdleTimeMillis(), this.getSoftMinEvictableIdleTimeMillis(), this.getMinIdle());
            boolean testWhileIdle = this.getTestWhileIdle();
            int i = 0;
            int m = this.getNumTests();

            while(true) {
               if (i >= m) {
                  break;
               }

               if (this.evictionIterator == null || !this.evictionIterator.hasNext()) {
                  this.evictionIterator = new BaseGenericObjectPool.EvictionIterator(this.idleObjects);
               }

               if (!this.evictionIterator.hasNext()) {
                  return;
               }

               label81: {
                  try {
                     underTest = this.evictionIterator.next();
                  } catch (NoSuchElementException var15) {
                     --i;
                     this.evictionIterator = null;
                     break label81;
                  }

                  if (!underTest.startEvictionTest()) {
                     --i;
                  } else {
                     boolean evict;
                     try {
                        evict = evictionPolicy.evict(evictionConfig, underTest, this.idleObjects.size());
                     } catch (Throwable var14) {
                        PoolUtils.checkRethrow(var14);
                        this.swallowException(new Exception(var14));
                        evict = false;
                     }

                     if (evict) {
                        this.destroy(underTest);
                        this.destroyedByEvictorCount.incrementAndGet();
                     } else {
                        if (testWhileIdle) {
                           boolean active = false;

                           try {
                              this.factory.activateObject(underTest);
                              active = true;
                           } catch (Exception var13) {
                              this.destroy(underTest);
                              this.destroyedByEvictorCount.incrementAndGet();
                           }

                           if (active) {
                              if (!this.factory.validateObject(underTest)) {
                                 this.destroy(underTest);
                                 this.destroyedByEvictorCount.incrementAndGet();
                              } else {
                                 try {
                                    this.factory.passivateObject(underTest);
                                 } catch (Exception var12) {
                                    this.destroy(underTest);
                                    this.destroyedByEvictorCount.incrementAndGet();
                                 }
                              }
                           }
                        }

                        if (!underTest.endEvictionTest(this.idleObjects)) {
                        }
                     }
                  }
               }

               ++i;
            }
         }
      }

      AbandonedConfig ac = this.abandonedConfig;
      if (ac != null && ac.getRemoveAbandonedOnMaintenance()) {
         this.removeAbandoned(ac);
      }

   }

   public void preparePool() throws Exception {
      if (this.getMinIdle() >= 1) {
         this.ensureMinIdle();
      }
   }

   private PooledObject<T> create() throws Exception {
      int localMaxTotal = this.getMaxTotal();
      if (localMaxTotal < 0) {
         localMaxTotal = Integer.MAX_VALUE;
      }

      Boolean create = null;

      while(create == null) {
         synchronized(this.makeObjectCountLock) {
            long newCreateCount = this.createCount.incrementAndGet();
            if (newCreateCount > (long)localMaxTotal) {
               this.createCount.decrementAndGet();
               if (this.makeObjectCount == 0L) {
                  create = Boolean.FALSE;
               } else {
                  this.makeObjectCountLock.wait();
               }
            } else {
               ++this.makeObjectCount;
               create = Boolean.TRUE;
            }
         }
      }

      if (!create) {
         return null;
      } else {
         boolean var16 = false;

         PooledObject p;
         try {
            var16 = true;
            p = this.factory.makeObject();
            var16 = false;
         } catch (Exception var19) {
            this.createCount.decrementAndGet();
            throw var19;
         } finally {
            if (var16) {
               synchronized(this.makeObjectCountLock) {
                  --this.makeObjectCount;
                  this.makeObjectCountLock.notifyAll();
               }
            }
         }

         synchronized(this.makeObjectCountLock) {
            --this.makeObjectCount;
            this.makeObjectCountLock.notifyAll();
         }

         AbandonedConfig ac = this.abandonedConfig;
         if (ac != null && ac.getLogAbandoned()) {
            p.setLogAbandoned(true);
            if (p instanceof DefaultPooledObject) {
               ((DefaultPooledObject)p).setRequireFullStackTrace(ac.getRequireFullStackTrace());
            }
         }

         this.createdCount.incrementAndGet();
         this.allObjects.put(new BaseGenericObjectPool.IdentityWrapper(p.getObject()), p);
         return p;
      }
   }

   private void destroy(PooledObject<T> toDestroy) throws Exception {
      toDestroy.invalidate();
      this.idleObjects.remove(toDestroy);
      this.allObjects.remove(new BaseGenericObjectPool.IdentityWrapper(toDestroy.getObject()));

      try {
         this.factory.destroyObject(toDestroy);
      } finally {
         this.destroyedCount.incrementAndGet();
         this.createCount.decrementAndGet();
      }

   }

   void ensureMinIdle() throws Exception {
      this.ensureIdle(this.getMinIdle(), true);
   }

   private void ensureIdle(int idleCount, boolean always) throws Exception {
      if (idleCount >= 1 && !this.isClosed() && (always || this.idleObjects.hasTakeWaiters())) {
         while(this.idleObjects.size() < idleCount) {
            PooledObject<T> p = this.create();
            if (p == null) {
               break;
            }

            if (this.getLifo()) {
               this.idleObjects.addFirst(p);
            } else {
               this.idleObjects.addLast(p);
            }
         }

         if (this.isClosed()) {
            this.clear();
         }

      }
   }

   public void addObject() throws Exception {
      this.assertOpen();
      if (this.factory == null) {
         throw new IllegalStateException("Cannot add objects without a factory.");
      } else {
         PooledObject<T> p = this.create();
         this.addIdleObject(p);
      }
   }

   private void addIdleObject(PooledObject<T> p) throws Exception {
      if (p != null) {
         this.factory.passivateObject(p);
         if (this.getLifo()) {
            this.idleObjects.addFirst(p);
         } else {
            this.idleObjects.addLast(p);
         }
      }

   }

   private int getNumTests() {
      int numTestsPerEvictionRun = this.getNumTestsPerEvictionRun();
      return numTestsPerEvictionRun >= 0 ? Math.min(numTestsPerEvictionRun, this.idleObjects.size()) : (int)Math.ceil((double)this.idleObjects.size() / Math.abs((double)numTestsPerEvictionRun));
   }

   private void removeAbandoned(AbandonedConfig ac) {
      long now = System.currentTimeMillis();
      long timeout = now - (long)ac.getRemoveAbandonedTimeout() * 1000L;
      ArrayList<PooledObject<T>> remove = new ArrayList();
      Iterator it = this.allObjects.values().iterator();

      while(it.hasNext()) {
         PooledObject<T> pooledObject = (PooledObject)it.next();
         synchronized(pooledObject) {
            if (pooledObject.getState() == PooledObjectState.ALLOCATED && pooledObject.getLastUsedTime() <= timeout) {
               pooledObject.markAbandoned();
               remove.add(pooledObject);
            }
         }
      }

      Iterator itr = remove.iterator();

      while(itr.hasNext()) {
         PooledObject<T> pooledObject = (PooledObject)itr.next();
         if (ac.getLogAbandoned()) {
            pooledObject.printStackTrace(ac.getLogWriter());
         }

         try {
            this.invalidateObject(pooledObject.getObject());
         } catch (Exception var11) {
            var11.printStackTrace();
         }
      }

   }

   public void use(T pooledObject) {
      AbandonedConfig ac = this.abandonedConfig;
      if (ac != null && ac.getUseUsageTracking()) {
         PooledObject<T> wrapper = (PooledObject)this.allObjects.get(new BaseGenericObjectPool.IdentityWrapper(pooledObject));
         wrapper.use();
      }

   }

   public int getNumWaiters() {
      return this.getBlockWhenExhausted() ? this.idleObjects.getTakeQueueLength() : 0;
   }

   public String getFactoryType() {
      if (this.factoryType == null) {
         StringBuilder result = new StringBuilder();
         result.append(this.factory.getClass().getName());
         result.append('<');
         Class<?> pooledObjectType = PoolImplUtils.getFactoryType(this.factory.getClass());
         result.append(pooledObjectType.getName());
         result.append('>');
         this.factoryType = result.toString();
      }

      return this.factoryType;
   }

   public Set<DefaultPooledObjectInfo> listAllObjects() {
      Set<DefaultPooledObjectInfo> result = new HashSet(this.allObjects.size());
      Iterator i$ = this.allObjects.values().iterator();

      while(i$.hasNext()) {
         PooledObject<T> p = (PooledObject)i$.next();
         result.add(new DefaultPooledObjectInfo(p));
      }

      return result;
   }

   protected void toStringAppendFields(StringBuilder builder) {
      super.toStringAppendFields(builder);
      builder.append(", factoryType=");
      builder.append(this.factoryType);
      builder.append(", maxIdle=");
      builder.append(this.maxIdle);
      builder.append(", minIdle=");
      builder.append(this.minIdle);
      builder.append(", factory=");
      builder.append(this.factory);
      builder.append(", allObjects=");
      builder.append(this.allObjects);
      builder.append(", createCount=");
      builder.append(this.createCount);
      builder.append(", idleObjects=");
      builder.append(this.idleObjects);
      builder.append(", abandonedConfig=");
      builder.append(this.abandonedConfig);
   }
}
