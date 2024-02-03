package net.mineland.core.bukkit.module;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.mineland.core.bukkit.modules.nms.NMS;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;

public class ModuleManager {
   private static List<Module> modules = new LinkedList();
   private static List<ModuleManager.ModuleCreatorData> creators = new LinkedList();
   private static boolean enableServer = false;

   public static boolean isEnableServer() {
      return enableServer;
   }

   public static void registerModule(Plugin plugin, int priority, ModuleCreator creator) throws ModuleException {
      creators.add(new ModuleManager.ModuleCreatorData(creator, priority, plugin));
   }

   public static List<Module> getModules() {
      return Collections.unmodifiableList(modules);
   }

   public static Module getModule(String name) {
      Module module = null;
      Iterator var2 = modules.iterator();

      while(var2.hasNext()) {
         Module m = (Module)var2.next();
         if (m.getName().equals(name)) {
            return m;
         }

         if (m.getName().equalsIgnoreCase(name)) {
            module = m;
         }
      }

      return module;
   }

   public static void enableModules() {
      Schedule.run(() -> {
         creators.sort(Comparator.comparingInt((o) -> {
            return o.priority;
         }));

         try {
            Iterator var0 = creators.iterator();

            while(var0.hasNext()) {
               ModuleManager.ModuleCreatorData creatorData = (ModuleManager.ModuleCreatorData)var0.next();
               Module modulex = creatorData.creator.create(creatorData.priority, creatorData.plugin);
               modules.add(modulex);
            }

            creators.clear();
            var0 = modules.iterator();

            while(var0.hasNext()) {
               Module module = (Module)var0.next();
               module.enable();
            }

            enableServer = true;
            Bukkit.getLogger().info("СЕРВЕР ПОЛНОСТЬЮ ЗАГРУЖЕН:");
            Bukkit.getLogger().info("  Модулей: " + modules.size());
            Bukkit.getLogger().info("  Плагины: " + (String)Stream.of(Bukkit.getPluginManager().getPlugins()).map((plugin) -> {
               return plugin.getDescription().getName();
            }).collect(Collectors.joining(", ")));
            Bukkit.getLogger().info("  Порт: " + Bukkit.getPort());
            Bukkit.getLogger().info("  Версия: " + NMS.getModuleNMS().VERSION + " (" + Bukkit.getBukkitVersion() + ")");
         } catch (Throwable var3) {
            Bukkit.getLogger().severe("При загрузке модулей произошла ошибка. Выключаем сервер...");
            var3.printStackTrace();
            disableModules();
            Bukkit.shutdown();
         }

      });
   }

   public static void afterEnable(Runnable runnable) {
      Schedule.later(runnable, 1L);
   }

   public static void disableModules() {
      enableServer = false;

      for(int i = modules.size() - 1; i >= 0; --i) {
         Module module = (Module)modules.get(i);

         try {
            if (module.isEnable()) {
               module.disable();
            }
         } catch (Throwable var3) {
            Bukkit.getLogger().severe("При выключении модуля '" + module.getName() + "' произошла ошибка.");
            var3.printStackTrace();
         }
      }

   }

   public static void disableModule(Module module) {
      if (module.isEnable()) {
         module.disable();
      }

   }

   public static void reload(Module module) {
      module.reload();
   }

   private static class ModuleCreatorData {
      private ModuleCreator creator;
      private int priority;
      private Plugin plugin;

      private ModuleCreatorData(ModuleCreator creator, int priority, Plugin plugin) {
         this.creator = creator;
         this.priority = priority;
         this.plugin = plugin;
      }

      // $FF: synthetic method
      ModuleCreatorData(ModuleCreator x0, int x1, Plugin x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
