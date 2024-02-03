package net.mineland.creative.modules.coding.activators.player;

import net.mineland.core.bukkit.modules.myevents.PlayerMoveOnBlockMyEvent;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerMoveActivator extends Activator {
   public PlayerMoveActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_MOVE;
   }

   public ItemData getIcon() {
      return new ItemData(Material.LEATHER_BOOTS);
   }

   public static class Event extends GamePlayerEvent implements Cancellable {
      public Event(User user, Plot plot, PlayerMoveOnBlockMyEvent event) {
         super(user, plot, event);
      }

      public boolean isCancelled() {
         return ((PlayerMoveOnBlockMyEvent)this.getHandleEvent()).isCancelled();
      }

      public void setCancelled(boolean b) {
         ((PlayerMoveOnBlockMyEvent)this.getHandleEvent()).setCancelled(b);
      }
   }
}
