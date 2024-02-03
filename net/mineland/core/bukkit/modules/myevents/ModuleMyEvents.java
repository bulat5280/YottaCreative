package net.mineland.core.bukkit.modules.myevents;

import net.mineland.core.bukkit.module.BukkitModule;
import org.bukkit.plugin.Plugin;
import ua.govnojon.libs.config.Config;

public class ModuleMyEvents extends BukkitModule {
   public ModuleMyEvents(int priority, Plugin plugin) {
      super("my_events", priority, plugin, (Config)null);
   }

   public void onFirstEnable() {
      this.registerListenersThis();
      this.registerData(new EventsDamage());
      this.registerData(new EventsSpawn(this.getPlugin()));
      this.registerData(new EventsMove());
      this.registerData(new EventsInteract());
   }

   public void onEnable() {
   }

   public void onDisable() {
   }
}
