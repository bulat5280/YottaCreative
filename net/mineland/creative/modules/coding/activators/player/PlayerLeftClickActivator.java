package net.mineland.creative.modules.coding.activators.player;

import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.coding.events.ItemEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerLeftClickActivator extends Activator {
   public PlayerLeftClickActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_LEFT_CLICK;
   }

   public ItemData getIcon() {
      return new ItemData(Material.IRON_PICKAXE);
   }

   public static class Event extends GamePlayerEvent implements Cancellable, ItemEvent {
      public Event(User user, Plot plot, PlayerInteractEvent event) {
         super(user, plot, event);
      }

      public boolean isCancelled() {
         return ((PlayerInteractEvent)this.getHandleEvent()).isCancelled();
      }

      public void setCancelled(boolean b) {
         ((PlayerInteractEvent)this.getHandleEvent()).setCancelled(b);
      }

      public ItemStack getItem() {
         return ((PlayerInteractEvent)this.getHandleEvent()).getItem();
      }

      public void setItem(ItemStack item) {
         PlayerInteractEvent event = (PlayerInteractEvent)this.getHandleEvent();
         EquipmentSlot hand = event.getHand();
         Player player = event.getPlayer();
         player.getInventory().setItem(hand, item);
      }
   }
}
