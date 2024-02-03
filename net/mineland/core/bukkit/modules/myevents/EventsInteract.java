package net.mineland.core.bukkit.modules.myevents;

import com.destroystokyo.paper.event.player.PlayerUseUnknownEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventsInteract implements Listener {
   @EventHandler(
      priority = EventPriority.LOW
   )
   public void on(PlayerInteractEvent event) {
      if (!event.getAction().equals(Action.PHYSICAL)) {
         Bukkit.getPluginManager().callEvent(new UserInteractMyEvent(event));
      }

   }

   @EventHandler(
      priority = EventPriority.LOW
   )
   public void on(PlayerUseUnknownEntityEvent event) {
      Bukkit.getPluginManager().callEvent(new UserInteractMyEvent(event));
   }

   @EventHandler(
      priority = EventPriority.LOW
   )
   public void on(PlayerInteractEntityEvent event) {
      Bukkit.getPluginManager().callEvent(new UserInteractMyEvent(event));
   }

   @EventHandler(
      priority = EventPriority.LOW
   )
   public void on(EntityDamageByEntityEvent event) {
      if (event.getDamager() instanceof Player && event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
         Bukkit.getPluginManager().callEvent(new UserInteractMyEvent(event));
      }

   }
}
