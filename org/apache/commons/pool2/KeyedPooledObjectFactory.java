package org.apache.commons.pool2;

public interface KeyedPooledObjectFactory<K, V> {
   PooledObject<V> makeObject(K var1) throws Exception;

   void destroyObject(K var1, PooledObject<V> var2) throws Exception;

   boolean validateObject(K var1, PooledObject<V> var2);

   void activateObject(K var1, PooledObject<V> var2) throws Exception;

   void passivateObject(K var1, PooledObject<V> var2) throws Exception;
}
