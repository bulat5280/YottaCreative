package org.apache.commons.pool2.impl;

import java.io.PrintWriter;
import java.util.Deque;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectState;
import org.apache.commons.pool2.TrackedUse;

public class DefaultPooledObject<T> implements PooledObject<T> {
   private final T object;
   private PooledObjectState state;
   private final long createTime;
   private volatile long lastBorrowTime;
   private volatile long lastUseTime;
   private volatile long lastReturnTime;
   private volatile boolean logAbandoned;
   private volatile CallStack borrowedBy;
   private volatile CallStack usedBy;
   private volatile long borrowedCount;

   public DefaultPooledObject(T object) {
      this.state = PooledObjectState.IDLE;
      this.createTime = System.currentTimeMillis();
      this.lastBorrowTime = this.createTime;
      this.lastUseTime = this.createTime;
      this.lastReturnTime = this.createTime;
      this.logAbandoned = false;
      this.borrowedBy = NoOpCallStack.INSTANCE;
      this.usedBy = NoOpCallStack.INSTANCE;
      this.borrowedCount = 0L;
      this.object = object;
   }

   public T getObject() {
      return this.object;
   }

   public long getCreateTime() {
      return this.createTime;
   }

   public long getActiveTimeMillis() {
      long rTime = this.lastReturnTime;
      long bTime = this.lastBorrowTime;
      return rTime > bTime ? rTime - bTime : System.currentTimeMillis() - bTime;
   }

   public long getIdleTimeMillis() {
      long elapsed = System.currentTimeMillis() - this.lastReturnTime;
      return elapsed >= 0L ? elapsed : 0L;
   }

   public long getLastBorrowTime() {
      return this.lastBorrowTime;
   }

   public long getLastReturnTime() {
      return this.lastReturnTime;
   }

   public long getBorrowedCount() {
      return this.borrowedCount;
   }

   public long getLastUsedTime() {
      return this.object instanceof TrackedUse ? Math.max(((TrackedUse)this.object).getLastUsed(), this.lastUseTime) : this.lastUseTime;
   }

   public int compareTo(PooledObject<T> other) {
      long lastActiveDiff = this.getLastReturnTime() - other.getLastReturnTime();
      return lastActiveDiff == 0L ? System.identityHashCode(this) - System.identityHashCode(other) : (int)Math.min(Math.max(lastActiveDiff, -2147483648L), 2147483647L);
   }

   public String toString() {
      StringBuilder result = new StringBuilder();
      result.append("Object: ");
      result.append(this.object.toString());
      result.append(", State: ");
      synchronized(this) {
         result.append(this.state.toString());
      }

      return result.toString();
   }

   public synchronized boolean startEvictionTest() {
      if (this.state == PooledObjectState.IDLE) {
         this.state = PooledObjectState.EVICTION;
         return true;
      } else {
         return false;
      }
   }

   public synchronized boolean endEvictionTest(Deque<PooledObject<T>> idleQueue) {
      if (this.state == PooledObjectState.EVICTION) {
         this.state = PooledObjectState.IDLE;
         return true;
      } else {
         if (this.state == PooledObjectState.EVICTION_RETURN_TO_HEAD) {
            this.state = PooledObjectState.IDLE;
            if (!idleQueue.offerFirst(this)) {
            }
         }

         return false;
      }
   }

   public synchronized boolean allocate() {
      if (this.state == PooledObjectState.IDLE) {
         this.state = PooledObjectState.ALLOCATED;
         this.lastBorrowTime = System.currentTimeMillis();
         this.lastUseTime = this.lastBorrowTime;
         ++this.borrowedCount;
         if (this.logAbandoned) {
            this.borrowedBy.fillInStackTrace();
         }

         return true;
      } else if (this.state == PooledObjectState.EVICTION) {
         this.state = PooledObjectState.EVICTION_RETURN_TO_HEAD;
         return false;
      } else {
         return false;
      }
   }

   public synchronized boolean deallocate() {
      if (this.state != PooledObjectState.ALLOCATED && this.state != PooledObjectState.RETURNING) {
         return false;
      } else {
         this.state = PooledObjectState.IDLE;
         this.lastReturnTime = System.currentTimeMillis();
         this.borrowedBy.clear();
         return true;
      }
   }

   public synchronized void invalidate() {
      this.state = PooledObjectState.INVALID;
   }

   public void use() {
      this.lastUseTime = System.currentTimeMillis();
      this.usedBy.fillInStackTrace();
   }

   public void printStackTrace(PrintWriter writer) {
      boolean written = this.borrowedBy.printStackTrace(writer);
      written |= this.usedBy.printStackTrace(writer);
      if (written) {
         writer.flush();
      }

   }

   public synchronized PooledObjectState getState() {
      return this.state;
   }

   public synchronized void markAbandoned() {
      this.state = PooledObjectState.ABANDONED;
   }

   public synchronized void markReturning() {
      this.state = PooledObjectState.RETURNING;
   }

   public void setLogAbandoned(boolean logAbandoned) {
      this.logAbandoned = logAbandoned;
   }

   public void setRequireFullStackTrace(boolean requireFullStackTrace) {
      this.borrowedBy = CallStackUtils.newCallStack("'Pooled object created' yyyy-MM-dd HH:mm:ss Z 'by the following code has not been returned to the pool:'", true, requireFullStackTrace);
      this.usedBy = CallStackUtils.newCallStack("The last code to use this object was:", false, requireFullStackTrace);
   }
}
