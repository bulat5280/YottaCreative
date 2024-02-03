package org.apache.commons.pool2;

import java.io.PrintWriter;
import java.util.Deque;

public interface PooledObject<T> extends Comparable<PooledObject<T>> {
   T getObject();

   long getCreateTime();

   long getActiveTimeMillis();

   long getIdleTimeMillis();

   long getLastBorrowTime();

   long getLastReturnTime();

   long getLastUsedTime();

   int compareTo(PooledObject<T> var1);

   boolean equals(Object var1);

   int hashCode();

   String toString();

   boolean startEvictionTest();

   boolean endEvictionTest(Deque<PooledObject<T>> var1);

   boolean allocate();

   boolean deallocate();

   void invalidate();

   void setLogAbandoned(boolean var1);

   void use();

   void printStackTrace(PrintWriter var1);

   PooledObjectState getState();

   void markAbandoned();

   void markReturning();
}
