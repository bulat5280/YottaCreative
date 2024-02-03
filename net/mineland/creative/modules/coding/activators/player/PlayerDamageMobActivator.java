package net.mineland.creative.modules.coding.activators.player;

import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.DamageEvent;
import net.mineland.creative.modules.coding.events.EntityEvent;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerDamageMobActivator extends Activator {
   public PlayerDamageMobActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_DAMAGE_MOB;
   }

   public ItemData getIcon() {
      return new ItemData(Material.STONE_SWORD);
   }

   public static class Event extends GamePlayerEvent implements Cancellable, DamageEvent, EntityEvent {
      public Event(User user, Plot plot, EntityDamageCommonEvent event) {
         super(user, plot, event);
      }

      public boolean isCancelled() {
         return ((EntityDamageCommonEvent)this.getHandleEvent()).isCancelled();
      }

      public void setCancelled(boolean b) {
         ((EntityDamageCommonEvent)this.getHandleEvent()).setCancelled(b);
      }

      public double getDamage() {
         return ((EntityDamageCommonEvent)this.getHandleEvent()).getEntity().getLastDamageCause().getFinalDamage();
      }

      public Entity getDamager() {
         return this.getPlayer();
      }

      public Entity getShooter() {
         return null;
      }

      public Entity getVictim() {
         return ((EntityDamageCommonEvent)this.getHandleEvent()).getEntity();
      }

      public Entity getEntity() {
         return ((EntityDamageCommonEvent)this.getHandleEvent()).getDamager();
      }
   }
}
