package net.mineland.creative.modules.coding.activators.player;

import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.coding.events.ItemEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerConsumeItemActivator extends Activator {
   public PlayerConsumeItemActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_CONSUME_ITEM;
   }

   public ItemData getIcon() {
      return new ItemData(Material.COOKIE);
   }

   public static class Event extends GamePlayerEvent implements Cancellable, ItemEvent {
      public Event(User user, Plot plot, PlayerItemConsumeEvent event) {
         super(user, plot, event);
      }

      public boolean isCancelled() {
         return ((PlayerItemConsumeEvent)this.getHandleEvent()).isCancelled();
      }

      public void setCancelled(boolean b) {
         ((PlayerItemConsumeEvent)this.getHandleEvent()).setCancelled(b);
      }

      public ItemStack getItem() {
         return ((PlayerItemConsumeEvent)this.getHandleEvent()).getItem();
      }

      public void setItem(ItemStack item) {
         ((PlayerItemConsumeEvent)this.getHandleEvent()).setItem(item);
      }
   }
}
