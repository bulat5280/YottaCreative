package org.apache.commons.pool2;

public abstract class BasePooledObjectFactory<T> extends BaseObject implements PooledObjectFactory<T> {
   public abstract T create() throws Exception;

   public abstract PooledObject<T> wrap(T var1);

   public PooledObject<T> makeObject() throws Exception {
      return this.wrap(this.create());
   }

   public void destroyObject(PooledObject<T> p) throws Exception {
   }

   public boolean validateObject(PooledObject<T> p) {
      return true;
   }

   public void activateObject(PooledObject<T> p) throws Exception {
   }

   public void passivateObject(PooledObject<T> p) throws Exception {
   }
}
