package net.mineland.core.bukkit.modules.analyzer;

import net.mineland.core.bukkit.module.BukkitModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import ua.govnojon.libs.config.Config;

public class ModuleAnalyzer extends BukkitModule {
   public ModuleAnalyzer(int priority, Plugin plugin) {
      super("analyzer", priority, plugin, (Config)null);
   }

   public void onFirstEnable() {
      this.registerListenersThis();
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void on(PlayerCommandPreprocessEvent event) {
      if (event.isCancelled()) {
         this.getLogger().info("Отменена команда " + event.getPlayer().getName() + ": " + event.getMessage());
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void on(AsyncPlayerChatEvent event) {
      if (event.isCancelled()) {
         this.getLogger().info("Отменено сообщение " + event.getPlayer().getName() + ": " + event.getMessage());
      }

   }
}
