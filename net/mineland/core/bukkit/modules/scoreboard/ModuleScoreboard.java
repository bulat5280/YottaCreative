package net.mineland.core.bukkit.modules.scoreboard;

import java.util.List;
import net.mineland.core.bukkit.module.BukkitModule;
import net.mineland.core.bukkit.modules.nms.NMS;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import ua.govnojon.libs.config.Config;

public class ModuleScoreboard extends BukkitModule {
   private boolean tabWorld = false;

   public ModuleScoreboard(int priority, Plugin plugin) {
      super("scoreboard", priority, plugin, new Config(plugin, "scoreboard.yml"));
   }

   public void onFirstEnable() {
      this.registerListenersThis();
   }

   public void onEnable() {
      Config config = this.getConfig();
      if (config.contains("header") || config.contains("footer")) {
         config.set("header", (Object)null);
         config.setAndSave("footer", (Object)null);
      }

      Object o = config.get("animation-side-bar-name");
      if (o instanceof List) {
         config.setAndSave("animation-side-bar-name", (Object)null);
      }

      this.tabWorld = !(Boolean)config.getOrSet("tab-global", false);
      config.setDescription("tab-global: если true, то в табе будут видны игроки из мира, false - все игроки");
   }

   public void onDisable() {
   }

   private void hide(Player player, World world) {
      Bukkit.getOnlinePlayers().forEach((online) -> {
         if (online.getWorld().equals(world)) {
            online.showPlayer(player);
            player.showPlayer(online);
         } else {
            NMS.getManagerSingle().fixHidePlayer(online, player);
            NMS.getManagerSingle().fixHidePlayer(player, online);
         }

      });
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void onPlayerJoinEventTAB(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      if (this.tabWorld) {
         this.hide(event.getPlayer(), event.getPlayer().getWorld());
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void on(PlayerChangedWorldEvent event) {
      if (this.tabWorld) {
         this.hide(event.getPlayer(), event.getPlayer().getWorld());
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onPlayerJoinEvent(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
   }
}
