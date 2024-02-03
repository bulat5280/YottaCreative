package net.mineland.core.bukkit.modules.myevents;

import net.mineland.core.bukkit.modules.user.User;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerEntitySpawnMyEvent extends PlayerEvent implements Cancellable {
   private static final HandlerList handlers = new HandlerList();
   private User user;
   private Entity entity;
   private Location spawnLocation;
   private boolean cancel = false;

   public PlayerEntitySpawnMyEvent(User user, Entity entity, Location spawnLocation) {
      super(user.getPlayer());
      this.user = user;
      this.entity = entity;
      this.spawnLocation = spawnLocation;
   }

   public static HandlerList getHandlerList() {
      return handlers;
   }

   public HandlerList getHandlers() {
      return handlers;
   }

   public User getUser() {
      return this.user;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public Location getSpawnLocation() {
      return this.spawnLocation;
   }

   public boolean isCancelled() {
      return this.cancel;
   }

   public void setCancelled(boolean cancel) {
      this.cancel = cancel;
   }
}
