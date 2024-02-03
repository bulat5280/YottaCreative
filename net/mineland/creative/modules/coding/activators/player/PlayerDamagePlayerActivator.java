package net.mineland.creative.modules.coding.activators.player;

import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.DamageEvent;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerDamagePlayerActivator extends Activator {
   public PlayerDamagePlayerActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_DAMAGE_PLAYER;
   }

   public ItemData getIcon() {
      return new ItemData(Material.IRON_SWORD);
   }

   public static class Event extends GamePlayerEvent implements DamageEvent, Cancellable {
      public Event(User user, Plot plot, EntityDamageCommonEvent event) {
         super(user, plot, event);
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

      public boolean isCancelled() {
         return ((EntityDamageCommonEvent)this.getHandleEvent()).isCancelled();
      }

      public void setCancelled(boolean b) {
         ((EntityDamageCommonEvent)this.getHandleEvent()).setCancelled(b);
      }
   }
}
