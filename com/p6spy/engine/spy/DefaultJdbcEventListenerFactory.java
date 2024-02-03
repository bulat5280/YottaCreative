package com.p6spy.engine.spy;

import com.p6spy.engine.event.CompoundJdbcEventListener;
import com.p6spy.engine.event.DefaultEventListener;
import com.p6spy.engine.event.JdbcEventListener;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class DefaultJdbcEventListenerFactory implements JdbcEventListenerFactory {
   private static ServiceLoader<JdbcEventListener> jdbcEventListenerServiceLoader = ServiceLoader.load(JdbcEventListener.class, DefaultJdbcEventListenerFactory.class.getClassLoader());
   private static JdbcEventListener jdbcEventListener;

   public JdbcEventListener createJdbcEventListener() {
      if (jdbcEventListener == null) {
         Class var1 = DefaultJdbcEventListenerFactory.class;
         synchronized(DefaultJdbcEventListenerFactory.class) {
            if (jdbcEventListener == null) {
               CompoundJdbcEventListener compoundEventListener = new CompoundJdbcEventListener();
               compoundEventListener.addListender(DefaultEventListener.INSTANCE);
               this.registerEventListenersFromFactories(compoundEventListener);
               this.registerEventListenersFromServiceLoader(compoundEventListener);
               jdbcEventListener = compoundEventListener;
            }
         }
      }

      return jdbcEventListener;
   }

   public void clearCache() {
      jdbcEventListener = null;
   }

   protected void registerEventListenersFromFactories(CompoundJdbcEventListener compoundEventListener) {
      List<P6Factory> factories = P6ModuleManager.getInstance().getFactories();
      if (factories != null) {
         Iterator var3 = factories.iterator();

         while(var3.hasNext()) {
            P6Factory factory = (P6Factory)var3.next();
            JdbcEventListener eventListener = factory.getJdbcEventListener();
            if (eventListener != null) {
               compoundEventListener.addListender(eventListener);
            }
         }
      }

   }

   protected void registerEventListenersFromServiceLoader(CompoundJdbcEventListener compoundEventListener) {
      Iterator iterator = jdbcEventListenerServiceLoader.iterator();

      while(iterator.hasNext()) {
         try {
            compoundEventListener.addListender((JdbcEventListener)iterator.next());
         } catch (ServiceConfigurationError var4) {
            var4.printStackTrace();
         }
      }

   }
}
