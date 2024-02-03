package com.p6spy.engine.spy;

import java.util.Iterator;
import java.util.ServiceLoader;

class JdbcEventListenerFactoryLoader {
   private static final JdbcEventListenerFactory jdbcEventListenerFactory;

   private JdbcEventListenerFactoryLoader() {
   }

   static JdbcEventListenerFactory load() {
      return jdbcEventListenerFactory;
   }

   static {
      Iterator<JdbcEventListenerFactory> iterator = ServiceLoader.load(JdbcEventListenerFactory.class, JdbcEventListenerFactory.class.getClassLoader()).iterator();
      if (iterator.hasNext()) {
         jdbcEventListenerFactory = (JdbcEventListenerFactory)iterator.next();
      } else {
         jdbcEventListenerFactory = new DefaultJdbcEventListenerFactory();
      }

   }
}
