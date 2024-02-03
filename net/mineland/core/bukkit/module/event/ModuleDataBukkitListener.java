package net.mineland.core.bukkit.module.event;

import net.mineland.core.bukkit.module.Module;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class ModuleDataBukkitListener implements ModuleData {
   public void register(Module module, Object o) {
      Listener listener = (Listener)o;

      try {
         Bukkit.getPluginManager().registerEvents(listener, module.getBukkitPlugin());
      } catch (Exception var5) {
         module.getLogger().severe("Не удалось зарегестрировать листенер: " + listener.getClass().getName());
      }

   }

   public void unregister(Module module, Object o) {
      Listener listener = (Listener)o;
      HandlerList.unregisterAll(listener);
   }
}
