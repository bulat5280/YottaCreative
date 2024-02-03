package org.apache.commons.pool2.impl;

import org.apache.commons.pool2.PooledObject;

public class DefaultEvictionPolicy<T> implements EvictionPolicy<T> {
   public boolean evict(EvictionConfig config, PooledObject<T> underTest, int idleCount) {
      return config.getIdleSoftEvictTime() < underTest.getIdleTimeMillis() && config.getMinIdle() < idleCount || config.getIdleEvictTime() < underTest.getIdleTimeMillis();
   }
}
