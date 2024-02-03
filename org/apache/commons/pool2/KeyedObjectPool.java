package org.apache.commons.pool2;

import java.io.Closeable;
import java.util.NoSuchElementException;

public interface KeyedObjectPool<K, V> extends Closeable {
   V borrowObject(K var1) throws Exception, NoSuchElementException, IllegalStateException;

   void returnObject(K var1, V var2) throws Exception;

   void invalidateObject(K var1, V var2) throws Exception;

   void addObject(K var1) throws Exception, IllegalStateException, UnsupportedOperationException;

   int getNumIdle(K var1);

   int getNumActive(K var1);

   int getNumIdle();

   int getNumActive();

   void clear() throws Exception, UnsupportedOperationException;

   void clear(K var1) throws Exception, UnsupportedOperationException;

   void close();
}
