package net.mineland.core.bukkit.module;

import javax.annotation.Nullable;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import ua.govnojon.libs.config.Config;

public abstract class BukkitModule extends Module implements Listener {
   public BukkitModule(String name, int priority, Plugin plugin, @Nullable Config config) {
      super(name, priority, plugin, config);
   }

   public BukkitModule(String name, int priority, Plugin plugin) {
      super(name, priority, plugin);
   }

   public Plugin getPlugin() {
      return this.getBukkitPlugin();
   }
}
