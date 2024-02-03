package net.mineland.core.bukkit.modules.myevents;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class EventsMove implements Listener {
   private void callCustomMoveEvents(PlayerMoveEvent event) {
      Location t = event.getTo();
      Location f = event.getFrom();
      int t_x = t.getBlockX();
      int t_y = t.getBlockY();
      int t_z = t.getBlockZ();
      int f_x = f.getBlockX();
      int f_y = f.getBlockY();
      int f_z = f.getBlockZ();
      if (t_x != f_x || t_y != f_y || t_z != f_z) {
         this.callPlayerMoveOnBlockEvent(event);
      }

   }

   private void callPlayerMoveOnBlockEvent(PlayerMoveEvent event) {
      PlayerMoveOnBlockMyEvent moveBlock = new PlayerMoveOnBlockMyEvent(event.getPlayer(), event.getFrom(), event.getTo(), event.isCancelled());
      Bukkit.getServer().getPluginManager().callEvent(moveBlock);
      if (moveBlock.isEdit()) {
         event.setCancelled(moveBlock.isCancelled());
         event.setTo(moveBlock.getTo());
         event.setFrom(moveBlock.getFrom());
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR,
      ignoreCancelled = true
   )
   public void onPlayerMoveEvent(PlayerMoveEvent event) {
      this.callCustomMoveEvents(event);
   }
}
