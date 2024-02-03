package org.apache.commons.pool2;

public abstract class BaseKeyedPooledObjectFactory<K, V> extends BaseObject implements KeyedPooledObjectFactory<K, V> {
   public abstract V create(K var1) throws Exception;

   public abstract PooledObject<V> wrap(V var1);

   public PooledObject<V> makeObject(K key) throws Exception {
      return this.wrap(this.create(key));
   }

   public void destroyObject(K key, PooledObject<V> p) throws Exception {
   }

   public boolean validateObject(K key, PooledObject<V> p) {
      return true;
   }

   public void activateObject(K key, PooledObject<V> p) throws Exception {
   }

   public void passivateObject(K key, PooledObject<V> p) throws Exception {
   }
}
