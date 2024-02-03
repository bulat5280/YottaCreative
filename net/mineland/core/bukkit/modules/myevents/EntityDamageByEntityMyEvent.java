package net.mineland.core.bukkit.modules.myevents;

import javax.annotation.Nullable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import ua.govnojon.libs.bukkitutil.PlayerUtil;

public class EntityDamageByEntityMyEvent extends Event implements Cancellable {
   private static final HandlerList handlers = new HandlerList();
   private Event original;
   private Entity entity;
   private Entity damager;
   private DamageCause cause;
   private boolean cancel;

   public EntityDamageByEntityMyEvent(Event original, Entity entity, Entity damager, DamageCause cause, boolean cancel) {
      super(false);
      this.original = original;
      this.entity = entity;
      this.damager = damager;
      this.cause = cause;
      this.cancel = cancel;
   }

   public static HandlerList getHandlerList() {
      return handlers;
   }

   public DamageCause getCause() {
      return this.cause;
   }

   public Event getOriginal() {
      return this.original;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public Entity getDamager() {
      return this.damager;
   }

   @Nullable
   public Entity getDamagerOriginal() {
      return PlayerUtil.getDamager(this.damager);
   }

   @Nullable
   public Player getDamagerPlayer() {
      return PlayerUtil.getDamager(this.damager);
   }

   public HandlerList getHandlers() {
      return handlers;
   }

   public boolean isCancelled() {
      return this.cancel;
   }

   public void setCancelled(boolean cancel) {
      this.cancel = cancel;
   }
}
