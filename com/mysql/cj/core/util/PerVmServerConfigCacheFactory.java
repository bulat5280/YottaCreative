package com.mysql.cj.core.util;

import com.mysql.cj.api.CacheAdapter;
import com.mysql.cj.api.CacheAdapterFactory;
import com.mysql.cj.api.MysqlConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PerVmServerConfigCacheFactory implements CacheAdapterFactory<String, Map<String, String>> {
   static final ConcurrentHashMap<String, Map<String, String>> serverConfigByUrl = new ConcurrentHashMap();
   private static final CacheAdapter<String, Map<String, String>> serverConfigCache = new CacheAdapter<String, Map<String, String>>() {
      public Map<String, String> get(String key) {
         return (Map)PerVmServerConfigCacheFactory.serverConfigByUrl.get(key);
      }

      public void put(String key, Map<String, String> value) {
         PerVmServerConfigCacheFactory.serverConfigByUrl.putIfAbsent(key, value);
      }

      public void invalidate(String key) {
         PerVmServerConfigCacheFactory.serverConfigByUrl.remove(key);
      }

      public void invalidateAll(Set<String> keys) {
         Iterator var2 = keys.iterator();

         while(var2.hasNext()) {
            String key = (String)var2.next();
            PerVmServerConfigCacheFactory.serverConfigByUrl.remove(key);
         }

      }

      public void invalidateAll() {
         PerVmServerConfigCacheFactory.serverConfigByUrl.clear();
      }
   };

   public CacheAdapter<String, Map<String, String>> getInstance(MysqlConnection forConn, String url, int cacheMaxSize, int maxKeySize, Properties connectionProperties) {
      return serverConfigCache;
   }
}
