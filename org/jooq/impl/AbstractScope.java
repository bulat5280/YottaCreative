package org.jooq.impl;

import java.util.Map;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.Scope;
import org.jooq.conf.Settings;

abstract class AbstractScope implements Scope {
   final Configuration configuration;
   final Map<Object, Object> data;

   AbstractScope(Configuration configuration) {
      this(configuration, (Map)null);
   }

   AbstractScope(Configuration configuration, Map<Object, Object> data) {
      if (configuration == null) {
         configuration = new DefaultConfiguration();
      }

      if (data == null) {
         data = new DataMap();
      }

      this.configuration = (Configuration)configuration;
      this.data = (Map)data;
   }

   public final Configuration configuration() {
      return this.configuration;
   }

   public final Settings settings() {
      return Tools.settings(this.configuration());
   }

   public final SQLDialect dialect() {
      return Tools.configuration(this.configuration()).dialect();
   }

   public final SQLDialect family() {
      return this.dialect().family();
   }

   public final Map<Object, Object> data() {
      return this.data;
   }

   public final Object data(Object key) {
      return this.data.get(key);
   }

   public final Object data(Object key, Object value) {
      return this.data.put(key, value);
   }
}
