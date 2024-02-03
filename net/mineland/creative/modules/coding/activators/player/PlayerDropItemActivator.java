package net.mineland.creative.modules.coding.activators.player;

import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.coding.events.ItemEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerDropItemActivator extends Activator {
   public PlayerDropItemActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_DROP_ITEM;
   }

   public ItemData getIcon() {
      return new ItemData(Material.SUGAR);
   }

   public static class Event extends GamePlayerEvent implements Cancellable, ItemEvent {
      public Event(User user, Plot plot, PlayerDropItemEvent event) {
         super(user, plot, event);
      }

      public boolean isCancelled() {
         return ((PlayerDropItemEvent)this.getHandleEvent()).isCancelled();
      }

      public void setCancelled(boolean b) {
         ((PlayerDropItemEvent)this.getHandleEvent()).setCancelled(b);
      }

      public ItemStack getItem() {
         return ((PlayerDropItemEvent)this.getHandleEvent()).getItemDrop().getItemStack();
      }

      public void setItem(ItemStack item) {
         ((PlayerDropItemEvent)this.getHandleEvent()).getItemDrop().setItemStack(item);
      }
   }
}
