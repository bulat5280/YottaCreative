package com.mysql.cj.jdbc.util;

import com.mysql.cj.api.CacheAdapter;
import com.mysql.cj.api.CacheAdapterFactory;
import com.mysql.cj.api.MysqlConnection;
import com.mysql.cj.core.util.LRUCache;
import com.mysql.cj.jdbc.PreparedStatement;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class PerConnectionLRUFactory implements CacheAdapterFactory<String, PreparedStatement.ParseInfo> {
   public CacheAdapter<String, PreparedStatement.ParseInfo> getInstance(MysqlConnection forConnection, String url, int cacheMaxSize, int maxKeySize, Properties connectionProperties) {
      return new PerConnectionLRUFactory.PerConnectionLRU(forConnection, cacheMaxSize, maxKeySize);
   }

   class PerConnectionLRU implements CacheAdapter<String, PreparedStatement.ParseInfo> {
      private final int cacheSqlLimit;
      private final LRUCache cache;
      private final MysqlConnection conn;

      protected PerConnectionLRU(MysqlConnection forConnection, int cacheMaxSize, int maxKeySize) {
         this.cacheSqlLimit = maxKeySize;
         this.cache = new LRUCache(cacheMaxSize);
         this.conn = forConnection;
      }

      public PreparedStatement.ParseInfo get(String key) {
         if (key != null && key.length() <= this.cacheSqlLimit) {
            synchronized(this.conn.getConnectionMutex()) {
               return (PreparedStatement.ParseInfo)this.cache.get(key);
            }
         } else {
            return null;
         }
      }

      public void put(String key, PreparedStatement.ParseInfo value) {
         if (key != null && key.length() <= this.cacheSqlLimit) {
            synchronized(this.conn.getConnectionMutex()) {
               this.cache.put(key, value);
            }
         }
      }

      public void invalidate(String key) {
         synchronized(this.conn.getConnectionMutex()) {
            this.cache.remove(key);
         }
      }

      public void invalidateAll(Set<String> keys) {
         synchronized(this.conn.getConnectionMutex()) {
            Iterator var3 = keys.iterator();

            while(var3.hasNext()) {
               String key = (String)var3.next();
               this.cache.remove(key);
            }

         }
      }

      public void invalidateAll() {
         synchronized(this.conn.getConnectionMutex()) {
            this.cache.clear();
         }
      }
   }
}
