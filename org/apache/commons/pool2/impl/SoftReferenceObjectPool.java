package org.apache.commons.pool2.impl;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.commons.pool2.BaseObjectPool;
import org.apache.commons.pool2.PoolUtils;
import org.apache.commons.pool2.PooledObjectFactory;

public class SoftReferenceObjectPool<T> extends BaseObjectPool<T> {
   private final PooledObjectFactory<T> factory;
   private final ReferenceQueue<T> refQueue = new ReferenceQueue();
   private int numActive = 0;
   private long destroyCount = 0L;
   private long createCount = 0L;
   private final LinkedBlockingDeque<PooledSoftReference<T>> idleReferences = new LinkedBlockingDeque();
   private final ArrayList<PooledSoftReference<T>> allReferences = new ArrayList();

   public SoftReferenceObjectPool(PooledObjectFactory<T> factory) {
      this.factory = factory;
   }

   public synchronized T borrowObject() throws Exception {
      this.assertOpen();
      T obj = null;
      boolean newlyCreated = false;
      PooledSoftReference ref = null;

      while(null == obj) {
         if (this.idleReferences.isEmpty()) {
            if (null == this.factory) {
               throw new NoSuchElementException();
            }

            newlyCreated = true;
            obj = this.factory.makeObject().getObject();
            ++this.createCount;
            ref = new PooledSoftReference(new SoftReference(obj));
            this.allReferences.add(ref);
         } else {
            ref = (PooledSoftReference)this.idleReferences.pollFirst();
            obj = ref.getObject();
            ref.getReference().clear();
            ref.setReference(new SoftReference(obj));
         }

         if (null != this.factory && null != obj) {
            try {
               this.factory.activateObject(ref);
               if (!this.factory.validateObject(ref)) {
                  throw new Exception("ValidateObject failed");
               }
            } catch (Throwable var12) {
               PoolUtils.checkRethrow(var12);

               try {
                  this.destroy(ref);
               } catch (Throwable var10) {
                  PoolUtils.checkRethrow(var10);
               } finally {
                  obj = null;
               }

               if (newlyCreated) {
                  throw new NoSuchElementException("Could not create a validated object, cause: " + var12.getMessage());
               }
            }
         }
      }

      ++this.numActive;
      ref.allocate();
      return obj;
   }

   public synchronized void returnObject(T obj) throws Exception {
      boolean success = !this.isClosed();
      PooledSoftReference<T> ref = this.findReference(obj);
      if (ref == null) {
         throw new IllegalStateException("Returned object not currently part of this pool");
      } else {
         if (this.factory != null) {
            if (!this.factory.validateObject(ref)) {
               success = false;
            } else {
               try {
                  this.factory.passivateObject(ref);
               } catch (Exception var7) {
                  success = false;
               }
            }
         }

         boolean shouldDestroy = !success;
         --this.numActive;
         if (success) {
            ref.deallocate();
            this.idleReferences.add(ref);
         }

         this.notifyAll();
         if (shouldDestroy && this.factory != null) {
            try {
               this.destroy(ref);
            } catch (Exception var6) {
            }
         }

      }
   }

   public synchronized void invalidateObject(T obj) throws Exception {
      PooledSoftReference<T> ref = this.findReference(obj);
      if (ref == null) {
         throw new IllegalStateException("Object to invalidate is not currently part of this pool");
      } else {
         if (this.factory != null) {
            this.destroy(ref);
         }

         --this.numActive;
         this.notifyAll();
      }
   }

   public synchronized void addObject() throws Exception {
      this.assertOpen();
      if (this.factory == null) {
         throw new IllegalStateException("Cannot add objects without a factory.");
      } else {
         T obj = this.factory.makeObject().getObject();
         ++this.createCount;
         PooledSoftReference<T> ref = new PooledSoftReference(new SoftReference(obj, this.refQueue));
         this.allReferences.add(ref);
         boolean success = true;
         if (!this.factory.validateObject(ref)) {
            success = false;
         } else {
            this.factory.passivateObject(ref);
         }

         boolean shouldDestroy = !success;
         if (success) {
            this.idleReferences.add(ref);
            this.notifyAll();
         }

         if (shouldDestroy) {
            try {
               this.destroy(ref);
            } catch (Exception var6) {
            }
         }

      }
   }

   public synchronized int getNumIdle() {
      this.pruneClearedReferences();
      return this.idleReferences.size();
   }

   public synchronized int getNumActive() {
      return this.numActive;
   }

   public synchronized void clear() {
      if (null != this.factory) {
         Iterator iter = this.idleReferences.iterator();

         while(iter.hasNext()) {
            try {
               PooledSoftReference<T> ref = (PooledSoftReference)iter.next();
               if (null != ref.getObject()) {
                  this.factory.destroyObject(ref);
               }
            } catch (Exception var3) {
            }
         }
      }

      this.idleReferences.clear();
      this.pruneClearedReferences();
   }

   public void close() {
      super.close();
      this.clear();
   }

   public synchronized PooledObjectFactory<T> getFactory() {
      return this.factory;
   }

   private void pruneClearedReferences() {
      this.removeClearedReferences(this.idleReferences.iterator());
      this.removeClearedReferences(this.allReferences.iterator());

      while(this.refQueue.poll() != null) {
      }

   }

   private PooledSoftReference<T> findReference(T obj) {
      Iterator iterator = this.allReferences.iterator();

      PooledSoftReference reference;
      do {
         if (!iterator.hasNext()) {
            return null;
         }

         reference = (PooledSoftReference)iterator.next();
      } while(reference.getObject() == null || !reference.getObject().equals(obj));

      return reference;
   }

   private void destroy(PooledSoftReference<T> toDestroy) throws Exception {
      toDestroy.invalidate();
      this.idleReferences.remove(toDestroy);
      this.allReferences.remove(toDestroy);

      try {
         this.factory.destroyObject(toDestroy);
      } finally {
         ++this.destroyCount;
         toDestroy.getReference().clear();
      }

   }

   private void removeClearedReferences(Iterator<PooledSoftReference<T>> iterator) {
      while(iterator.hasNext()) {
         PooledSoftReference<T> ref = (PooledSoftReference)iterator.next();
         if (ref.getReference() == null || ref.getReference().isEnqueued()) {
            iterator.remove();
         }
      }

   }

   protected void toStringAppendFields(StringBuilder builder) {
      super.toStringAppendFields(builder);
      builder.append(", factory=");
      builder.append(this.factory);
      builder.append(", refQueue=");
      builder.append(this.refQueue);
      builder.append(", numActive=");
      builder.append(this.numActive);
      builder.append(", destroyCount=");
      builder.append(this.destroyCount);
      builder.append(", createCount=");
      builder.append(this.createCount);
      builder.append(", idleReferences=");
      builder.append(this.idleReferences);
      builder.append(", allReferences=");
      builder.append(this.allReferences);
   }
}
