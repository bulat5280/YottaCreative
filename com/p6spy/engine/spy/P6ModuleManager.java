package com.p6spy.engine.spy;

import com.p6spy.engine.common.P6LogQuery;
import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.spy.option.EnvironmentVariables;
import com.p6spy.engine.spy.option.P6OptionChangedListener;
import com.p6spy.engine.spy.option.P6OptionsRepository;
import com.p6spy.engine.spy.option.P6OptionsSource;
import com.p6spy.engine.spy.option.SpyDotProperties;
import com.p6spy.engine.spy.option.SystemProperties;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;

public class P6ModuleManager {
   private final P6OptionsSource[] optionsSources = new P6OptionsSource[]{new SpyDotProperties(), new EnvironmentVariables(), new SystemProperties()};
   private final Map<Class<? extends P6LoadableOptions>, P6LoadableOptions> allOptions = new HashMap();
   private final List<P6Factory> factories = new CopyOnWriteArrayList();
   private final P6MBeansRegistry mBeansRegistry = new P6MBeansRegistry();
   private final P6OptionsRepository optionsRepository = new P6OptionsRepository();
   private static P6ModuleManager instance;

   private static synchronized void initMe() {
      try {
         cleanUp();
         instance = new P6ModuleManager();
         P6LogQuery.initialize();
      } catch (IOException var1) {
         handleInitEx(var1);
      } catch (MBeanRegistrationException var2) {
         handleInitEx(var2);
      } catch (InstanceNotFoundException var3) {
         handleInitEx(var3);
      } catch (MalformedObjectNameException var4) {
         handleInitEx(var4);
      } catch (NotCompliantMBeanException var5) {
         handleInitEx(var5);
      }

   }

   private static void cleanUp() throws MBeanRegistrationException, InstanceNotFoundException, MalformedObjectNameException {
      if (instance != null) {
         P6OptionsSource[] var0 = instance.optionsSources;
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            P6OptionsSource optionsSource = var0[var2];
            optionsSource.preDestroy(instance);
         }

         if (P6SpyOptions.getActiveInstance().getJmx() && instance.mBeansRegistry != null) {
            instance.mBeansRegistry.unregisterAllMBeans(P6SpyOptions.getActiveInstance().getJmxPrefix());
         }

         (new DefaultJdbcEventListenerFactory()).clearCache();
      }
   }

   private P6ModuleManager() throws IOException, MBeanRegistrationException, NotCompliantMBeanException, MalformedObjectNameException, InstanceNotFoundException {
      this.debug(this.getClass().getName() + " re/initiating modules started");
      this.registerOptionChangedListener(new P6LogQuery());
      P6SpyLoadableOptions spyOptions = (P6SpyLoadableOptions)this.registerModule(new P6SpyFactory());
      this.loadDriversExplicitly(spyOptions);
      Set<P6Factory> moduleFactories = spyOptions.getModuleFactories();
      if (null != moduleFactories) {
         Iterator var3 = spyOptions.getModuleFactories().iterator();

         while(var3.hasNext()) {
            P6Factory factory = (P6Factory)var3.next();
            this.registerModule(factory);
         }
      }

      this.optionsRepository.initCompleted();
      this.mBeansRegistry.registerMBeans(this.allOptions.values());
      P6OptionsSource[] var7 = this.optionsSources;
      int var8 = var7.length;

      for(int var5 = 0; var5 < var8; ++var5) {
         P6OptionsSource optionsSource = var7[var5];
         optionsSource.postInit(this);
      }

      this.debug(this.getClass().getName() + " re/initiating modules done");
   }

   protected synchronized P6LoadableOptions registerModule(P6Factory factory) {
      Iterator var2 = this.factories.iterator();

      P6Factory registeredFactory;
      do {
         if (!var2.hasNext()) {
            P6LoadableOptions options = factory.getOptions(this.optionsRepository);
            this.loadOptions(options);
            this.allOptions.put(options.getClass(), options);
            this.factories.add(factory);
            this.debug("Registered factory: " + factory.getClass().getName() + " with options: " + options.getClass().getName());
            return options;
         }

         registeredFactory = (P6Factory)var2.next();
      } while(!registeredFactory.getClass().equals(factory.getClass()));

      return null;
   }

   private void loadOptions(P6LoadableOptions options) {
      options.load(options.getDefaults());
      P6OptionsSource[] var2 = this.optionsSources;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         P6OptionsSource optionsSource = var2[var4];
         Map<String, String> toLoad = optionsSource.getOptions();
         if (null != toLoad) {
            options.load(toLoad);
         }
      }

      this.allOptions.put(options.getClass(), options);
   }

   public static P6ModuleManager getInstance() {
      return instance;
   }

   private static void handleInitEx(Exception e) {
      e.printStackTrace(System.err);
   }

   private void loadDriversExplicitly(P6SpyLoadableOptions spyOptions) {
      Collection<String> driverNames = spyOptions.getDriverNames();
      if (null != driverNames) {
         Iterator var3 = driverNames.iterator();

         while(var3.hasNext()) {
            String driverName = (String)var3.next();

            try {
               P6Util.forName(driverName).newInstance();
            } catch (Exception var7) {
               String err = "Error registering driver names: " + driverNames + " \nCaused By: " + var7.toString();
               P6LogQuery.error(err);
               throw new P6DriverNotFoundError(err);
            }
         }
      }

   }

   private void debug(String msg) {
      if (instance != null && !this.factories.isEmpty()) {
         P6LogQuery.debug(msg);
      }
   }

   public <T extends P6LoadableOptions> T getOptions(Class<T> optionsClass) {
      return (P6LoadableOptions)this.allOptions.get(optionsClass);
   }

   public void reload() {
      initMe();
   }

   public List<P6Factory> getFactories() {
      return this.factories;
   }

   public void registerOptionChangedListener(P6OptionChangedListener listener) {
      this.optionsRepository.registerOptionChangedListener(listener);
   }

   public void unregisterOptionChangedListener(P6OptionChangedListener listener) {
      this.optionsRepository.unregisterOptionChangedListener(listener);
   }

   static {
      initMe();
   }
}
