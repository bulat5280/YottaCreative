package org.apache.commons.pool2.proxy;

import java.util.NoSuchElementException;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.UsageTracking;

public class ProxiedKeyedObjectPool<K, V> implements KeyedObjectPool<K, V> {
   private final KeyedObjectPool<K, V> pool;
   private final ProxySource<V> proxySource;

   public ProxiedKeyedObjectPool(KeyedObjectPool<K, V> pool, ProxySource<V> proxySource) {
      this.pool = pool;
      this.proxySource = proxySource;
   }

   public V borrowObject(K key) throws Exception, NoSuchElementException, IllegalStateException {
      UsageTracking<V> usageTracking = null;
      if (this.pool instanceof UsageTracking) {
         usageTracking = (UsageTracking)this.pool;
      }

      V pooledObject = this.pool.borrowObject(key);
      V proxy = this.proxySource.createProxy(pooledObject, usageTracking);
      return proxy;
   }

   public void returnObject(K key, V proxy) throws Exception {
      V pooledObject = this.proxySource.resolveProxy(proxy);
      this.pool.returnObject(key, pooledObject);
   }

   public void invalidateObject(K key, V proxy) throws Exception {
      V pooledObject = this.proxySource.resolveProxy(proxy);
      this.pool.invalidateObject(key, pooledObject);
   }

   public void addObject(K key) throws Exception, IllegalStateException, UnsupportedOperationException {
      this.pool.addObject(key);
   }

   public int getNumIdle(K key) {
      return this.pool.getNumIdle(key);
   }

   public int getNumActive(K key) {
      return this.pool.getNumActive(key);
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

   public void clear(K key) throws Exception, UnsupportedOperationException {
      this.pool.clear(key);
   }

   public void close() {
      this.pool.close();
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("ProxiedKeyedObjectPool [pool=");
      builder.append(this.pool);
      builder.append(", proxySource=");
      builder.append(this.proxySource);
      builder.append("]");
      return builder.toString();
   }
}
