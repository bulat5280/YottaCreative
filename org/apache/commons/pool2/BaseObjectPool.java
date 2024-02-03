package org.apache.commons.pool2;

public abstract class BaseObjectPool<T> extends BaseObject implements ObjectPool<T> {
   private volatile boolean closed = false;

   public abstract T borrowObject() throws Exception;

   public abstract void returnObject(T var1) throws Exception;

   public abstract void invalidateObject(T var1) throws Exception;

   public int getNumIdle() {
      return -1;
   }

   public int getNumActive() {
      return -1;
   }

   public void clear() throws Exception, UnsupportedOperationException {
      throw new UnsupportedOperationException();
   }

   public void addObject() throws Exception, UnsupportedOperationException {
      throw new UnsupportedOperationException();
   }

   public void close() {
      this.closed = true;
   }

   public final boolean isClosed() {
      return this.closed;
   }

   protected final void assertOpen() throws IllegalStateException {
      if (this.isClosed()) {
         throw new IllegalStateException("Pool not open");
      }
   }

   protected void toStringAppendFields(StringBuilder builder) {
      builder.append("closed=");
      builder.append(this.closed);
   }
}
