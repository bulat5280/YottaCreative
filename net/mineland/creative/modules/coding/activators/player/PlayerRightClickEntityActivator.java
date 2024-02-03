package net.mineland.creative.modules.coding.activators.player;

import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.EntityEvent;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.schedule.Schedule;

public class PlayerRightClickEntityActivator extends Activator {
   public PlayerRightClickEntityActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_RIGHT_CLICK_ENTITY;
   }

   public ItemData getIcon() {
      return new ItemData(Material.PORK);
   }

   public static class Event extends GamePlayerEvent implements Cancellable, EntityEvent {
      public Event(User user, Plot plot, PlayerInteractEntityEvent event) {
         super(user, plot, event);
      }

      public Entity getEntity() {
         return ((PlayerInteractEntityEvent)this.getHandleEvent()).getRightClicked();
      }

      public boolean isCancelled() {
         return ((PlayerInteractEntityEvent)this.getHandleEvent()).isCancelled();
      }

      public void setCancelled(boolean b) {
         ((PlayerInteractEntityEvent)this.getHandleEvent()).setCancelled(b);
         if (b) {
            Schedule.run(() -> {
               this.getPlayer().updateInventory();
            });
         }

      }
   }
}
