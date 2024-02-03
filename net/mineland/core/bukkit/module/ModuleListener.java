package net.mineland.core.bukkit.module;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.server.PluginDisableEvent;

public class ModuleListener implements Listener {
   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void on(AsyncPlayerPreLoginEvent event) {
      if (!ModuleManager.isEnableServer()) {
         event.setKickMessage("§cServer load.");
         event.setLoginResult(Result.KICK_OTHER);
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void on(PlayerLoginEvent event) {
      if (!ModuleManager.isEnableServer()) {
         event.setKickMessage("§cServer load.");
         event.setResult(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_OTHER);
      }

   }

   @EventHandler
   public void onPluginDisableEvent(PluginDisableEvent event) {
      if (ModuleManager.isEnableServer() && ModuleManager.getModules().stream().anyMatch((module) -> {
         return module.getPlugin().equals(event.getPlugin());
      })) {
         ModuleManager.disableModules();
      }

   }
}
