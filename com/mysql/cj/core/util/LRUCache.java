package com.mysql.cj.core.util;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LRUCache extends LinkedHashMap<Object, Object> {
   private static final long serialVersionUID = 1L;
   protected int maxElements;

   public LRUCache(int maxSize) {
      super(maxSize, 0.75F, true);
      this.maxElements = maxSize;
   }

   protected boolean removeEldestEntry(Entry<Object, Object> eldest) {
      return this.size() > this.maxElements;
   }
}
