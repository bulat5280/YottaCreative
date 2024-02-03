package com.mysql.cj.api;

import java.util.Properties;

public interface CacheAdapterFactory<K, V> {
   CacheAdapter<K, V> getInstance(MysqlConnection var1, String var2, int var3, int var4, Properties var5);
}
