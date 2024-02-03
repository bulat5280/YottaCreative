package net.mineland.creative.modules.coding.activators.player;

import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.coding.events.ItemEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerPickupItemActivator extends Activator {
   public PlayerPickupItemActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_PICKUP_ITEM;
   }

   public ItemData getIcon() {
      return new ItemData(Material.GLOWSTONE_DUST);
   }

   public static class Event extends GamePlayerEvent implements Cancellable, ItemEvent {
      public Event(User user, Plot plot, EntityPickupItemEvent event) {
         super(user, plot, event);
      }

      public boolean isCancelled() {
         return ((EntityPickupItemEvent)this.getHandleEvent()).isCancelled();
      }

      public void setCancelled(boolean b) {
         ((EntityPickupItemEvent)this.getHandleEvent()).setCancelled(b);
      }

      public ItemStack getItem() {
         return ((EntityPickupItemEvent)this.getHandleEvent()).getItem().getItemStack();
      }

      public void setItem(ItemStack item) {
         ((EntityPickupItemEvent)this.getHandleEvent()).getItem().setItemStack(item);
      }
   }
}
