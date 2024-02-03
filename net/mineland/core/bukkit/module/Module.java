package net.mineland.core.bukkit.module;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nullable;
import net.mineland.core.bukkit.module.event.ModuleDataType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import ua.govnojon.libs.config.Config;

public abstract class Module {
   private final List<Object> datas;
   private Object plugin;
   private String name;
   private boolean enabled;
   private ModuleLogger logger;
   private int priority;
   private Config config;

   public Module(String name, int priority, Object plugin, @Nullable Config config) {
      this.datas = new LinkedList();
      this.enabled = false;
      this.name = name;
      this.plugin = plugin;
      this.logger = new ModuleLogger((Plugin)plugin, this);
      this.config = config;
      this.priority = priority;
   }

   public Module(String name, int priority, Plugin plugin) {
      this(name, priority, plugin, (Config)null);
   }

   public static <T> T getInstance(Class<T> moduleClass) {
      if (moduleClass.equals(Module.class)) {
         throw new RuntimeException("нельзя указывать Module.class");
      } else {
         return ModuleManager.getModules().stream().filter((module) -> {
            return module.getClass().equals(moduleClass);
         }).findFirst().orElseThrow(() -> {
            return new RuntimeException("Модуль " + moduleClass + " не найден.");
         });
      }
   }

   public void onFirstEnable() {
   }

   public abstract void onEnable();

   public abstract void onDisable();

   public void onReload() {
      this.standartReload();
   }

   protected void enable() {
      long time = System.currentTimeMillis();
      if (!this.enabled) {
         this.onFirstEnable();
      }

      this.onEnable();
      this.enabled = true;
      this.logger.info("Модуль '" + this.name + "' был включен за " + (System.currentTimeMillis() - time) + "ms, кол-во эвентов " + this.datas.size());
   }

   protected void disable() {
      this.onDisable();
      this.unregisterDataAll();
      this.enabled = false;
      this.logger.info("Модуль '" + this.name + "' выключен.");
   }

   protected void reload() {
      if (this.config != null) {
         this.config.reload();
      }

      this.onReload();
      Bukkit.getPluginManager().callEvent(new ModuleReloadEvent(this));
      this.logger.info("Модуль '" + this.name + "' перезагружен.");
   }

   public Plugin getBukkitPlugin() {
      return this.plugin instanceof Plugin ? (Plugin)this.plugin : null;
   }

   public String getName() {
      return this.name;
   }

   public final boolean isEnable() {
      return this.enabled;
   }

   public abstract Object getPlugin();

   public Config getConfig() {
      return this.config;
   }

   public ModuleLogger getLogger() {
      return this.logger;
   }

   public int getPriority() {
      return this.priority;
   }

   protected void standartReload() {
      this.onDisable();
      this.onEnable();
   }

   public void registerData(Object o) {
      Iterator var2 = this.datas.iterator();

      Object data;
      do {
         if (!var2.hasNext()) {
            ModuleDataType type = ModuleDataType.getTypeByClass(o);
            if (type == null) {
               throw new ModuleException("Плагин не поддерживает такую дату: " + o.toString());
            }

            type.getController().register(this, o);
            this.datas.add(o);
            return;
         }

         data = var2.next();
      } while(!data.getClass().equals(o.getClass()));

      this.getLogger().warning("Повторная попытка зарегистрировать одинаковые эвенты '" + o.getClass().getName() + "' была отменена.");
   }

   public void unregisterData(Object o) {
      ModuleDataType type = ModuleDataType.getTypeByClass(o);
      if (type == null) {
         throw new ModuleException("Плагин не поддерживает такую дату: " + o.toString());
      } else {
         type.getController().unregister(this, o);
         this.datas.remove(o);
      }
   }

   public void unregisterDataAll() {
      Iterator var1 = this.datas.iterator();

      while(var1.hasNext()) {
         Object data = var1.next();
         ModuleDataType type = ModuleDataType.getTypeByClass(data);
         if (type != null) {
            type.getController().unregister(this, data);
         }
      }

      this.datas.clear();
   }

   public void registerListenersThis() {
      this.registerData(this);
   }
}
