package net.mineland.creative.modules.coding.activators.player;

import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerItemHeldEvent;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerChangeSlotActivator extends Activator {
   public PlayerChangeSlotActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_CHANGE_SLOT;
   }

   public ItemData getIcon() {
      return new ItemData(Material.SLIME_BALL);
   }

   public static class Event extends GamePlayerEvent implements Cancellable {
      public Event(User user, Plot plot, PlayerItemHeldEvent event) {
         super(user, plot, event);
      }

      public int getPreviousSlot() {
         return ((PlayerItemHeldEvent)this.getHandleEvent()).getPreviousSlot();
      }

      public int getNewSlot() {
         return ((PlayerItemHeldEvent)this.getHandleEvent()).getNewSlot();
      }

      public boolean isCancelled() {
         return ((PlayerItemHeldEvent)this.getHandleEvent()).isCancelled();
      }

      public void setCancelled(boolean b) {
         ((PlayerItemHeldEvent)this.getHandleEvent()).setCancelled(b);
      }
   }
}
