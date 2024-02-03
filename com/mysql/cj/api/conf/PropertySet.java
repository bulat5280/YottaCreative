package com.mysql.cj.api.conf;

import java.util.Properties;

public interface PropertySet {
   void addProperty(RuntimeProperty<?> var1);

   void removeProperty(String var1);

   <T> ReadableProperty<T> getReadableProperty(String var1);

   ReadableProperty<Boolean> getBooleanReadableProperty(String var1);

   ReadableProperty<Integer> getIntegerReadableProperty(String var1);

   ReadableProperty<Long> getLongReadableProperty(String var1);

   ReadableProperty<Integer> getMemorySizeReadableProperty(String var1);

   ReadableProperty<String> getStringReadableProperty(String var1);

   <T> ModifiableProperty<T> getModifiableProperty(String var1);

   void initializeProperties(Properties var1);

   void postInitialization();

   Properties exposeAsProperties(Properties var1);
}
