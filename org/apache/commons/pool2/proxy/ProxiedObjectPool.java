package org.apache.commons.pool2.proxy;

import java.util.NoSuchElementException;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.UsageTracking;

public class ProxiedObjectPool<T> implements ObjectPool<T> {
   private final ObjectPool<T> pool;
   private final ProxySource<T> proxySource;

   public ProxiedObjectPool(ObjectPool<T> pool, ProxySource<T> proxySource) {
      this.pool = pool;
      this.proxySource = proxySource;
   }

   public T borrowObject() throws Exception, NoSuchElementException, IllegalStateException {
      UsageTracking<T> usageTracking = null;
      if (this.pool instanceof UsageTracking) {
         usageTracking = (UsageTracking)this.pool;
      }

      T pooledObject = this.pool.borrowObject();
      T proxy = this.proxySource.createProxy(pooledObject, usageTracking);
      return proxy;
   }

   public void returnObject(T proxy) throws Exception {
      T pooledObject = this.proxySource.resolveProxy(proxy);
      this.pool.returnObject(pooledObject);
   }

   public void invalidateObject(T proxy) throws Exception {
      T pooledObject = this.proxySource.resolveProxy(proxy);
      this.pool.invalidateObject(pooledObject);
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
      this.pool.close();
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("ProxiedObjectPool [pool=");
      builder.append(this.pool);
      builder.append(", proxySource=");
      builder.append(this.proxySource);
      builder.append("]");
      return builder.toString();
   }
}
