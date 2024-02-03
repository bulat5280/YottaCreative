package net.mineland.creative.modules.coding.activators.player;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntityDamageCommonEvent extends Event implements Cancellable {
   private Event delegate;
   private Cancellable cancellable;
   private Entity entity;
   private Entity damager;

   public EntityDamageCommonEvent(Event delegate, Cancellable cancellable, Entity entity, Entity damager) {
      this.delegate = delegate;
      this.cancellable = cancellable;
      this.entity = entity;
      this.damager = damager;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public Entity getDamager() {
      return this.damager;
   }

   public boolean isCancelled() {
      return this.cancellable.isCancelled();
   }

   public void setCancelled(boolean b) {
      this.cancellable.setCancelled(b);
   }

   public HandlerList getHandlers() {
      return this.delegate.getHandlers();
   }
}
