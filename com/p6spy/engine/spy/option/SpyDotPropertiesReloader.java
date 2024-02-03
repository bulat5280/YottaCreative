package com.p6spy.engine.spy.option;

import com.p6spy.engine.spy.P6ModuleManager;
import com.p6spy.engine.spy.P6SpyLoadableOptions;
import com.p6spy.engine.spy.P6SpyOptions;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SpyDotPropertiesReloader implements P6OptionChangedListener {
   private ScheduledExecutorService reloader;
   private final SpyDotProperties spyDotProperties;
   private boolean killed = false;

   public SpyDotPropertiesReloader(SpyDotProperties spyDotProperties, P6ModuleManager p6ModuleManager) {
      this.spyDotProperties = spyDotProperties;
      P6SpyLoadableOptions spyOptions = (P6SpyLoadableOptions)p6ModuleManager.getOptions(P6SpyOptions.class);
      this.reschedule(spyOptions.getReloadProperties(), spyOptions.getReloadPropertiesInterval());
      p6ModuleManager.registerOptionChangedListener(this);
   }

   public synchronized void reschedule(boolean enabled, long reloadInterval) {
      this.shutdownNow();
      if (enabled && !this.killed) {
         this.reloader = Executors.newSingleThreadScheduledExecutor();
         Runnable reader = new Runnable() {
            public void run() {
               if (SpyDotPropertiesReloader.this.spyDotProperties.isModified()) {
                  SpyDotPropertiesReloader.this.shutdownNow();
                  P6ModuleManager.getInstance().reload();
               }

            }
         };
         this.reloader.scheduleAtFixedRate(reader, reloadInterval, reloadInterval, TimeUnit.SECONDS);
         if (this.killed) {
            this.shutdownNow();
         }

      }
   }

   public void kill(P6ModuleManager p6ModuleManager) {
      p6ModuleManager.unregisterOptionChangedListener(this);
      this.killed = true;
      this.shutdownNow();
   }

   private void shutdownNow() {
      if (this.wasEnabled()) {
         this.reloader.shutdownNow();
         this.reloader = null;
      }

   }

   private boolean wasEnabled() {
      return this.reloader != null;
   }

   public void optionChanged(String key, Object oldValue, Object newValue) {
      if (key.equals("reloadproperties")) {
         this.reschedule(Boolean.valueOf(newValue.toString()), P6SpyOptions.getActiveInstance().getReloadPropertiesInterval());
      } else if (key.equals("reloadpropertiesinterval")) {
         this.reschedule(P6SpyOptions.getActiveInstance().getReloadProperties(), (Long)newValue);
      }

   }
}
