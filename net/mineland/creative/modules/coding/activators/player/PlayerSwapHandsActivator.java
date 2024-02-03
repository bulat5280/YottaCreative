package net.mineland.creative.modules.coding.activators.player;

import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerSwapHandsActivator extends Activator {
   public PlayerSwapHandsActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_SWAP_HANDS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.COMMAND_REPEATING);
   }

   public static class Event extends GamePlayerEvent implements Cancellable {
      public Event(User user, Plot plot, PlayerSwapHandItemsEvent event) {
         super(user, plot, event);
      }

      public ItemStack getMainHandItemStack() {
         return ((PlayerSwapHandItemsEvent)this.getHandleEvent()).getMainHandItem();
      }

      public ItemStack getOffHandItemStack() {
         return ((PlayerSwapHandItemsEvent)this.getHandleEvent()).getOffHandItem();
      }

      public boolean isCancelled() {
         return ((PlayerSwapHandItemsEvent)this.getHandleEvent()).isCancelled();
      }

      public void setCancelled(boolean b) {
         ((PlayerSwapHandItemsEvent)this.getHandleEvent()).setCancelled(b);
      }
   }
}
