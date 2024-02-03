package org.apache.commons.pool2;

import java.io.Closeable;
import java.util.NoSuchElementException;

public interface ObjectPool<T> extends Closeable {
   T borrowObject() throws Exception, NoSuchElementException, IllegalStateException;

   void returnObject(T var1) throws Exception;

   void invalidateObject(T var1) throws Exception;

   void addObject() throws Exception, IllegalStateException, UnsupportedOperationException;

   int getNumIdle();

   int getNumActive();

   void clear() throws Exception, UnsupportedOperationException;

   void close();
}
