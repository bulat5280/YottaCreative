package org.apache.commons.pool2;

public interface PooledObjectFactory<T> {
   PooledObject<T> makeObject() throws Exception;

   void destroyObject(PooledObject<T> var1) throws Exception;

   boolean validateObject(PooledObject<T> var1);

   void activateObject(PooledObject<T> var1) throws Exception;

   void passivateObject(PooledObject<T> var1) throws Exception;
}
