package net.mineland.creative.modules.coding.activators.player;

import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerUnsneakActivator extends Activator {
   public PlayerUnsneakActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_UNSNEAK;
   }

   public ItemData getIcon() {
      return new ItemData(Material.IRON_LEGGINGS);
   }

   public static class Event extends GamePlayerEvent implements Cancellable {
      public Event(User user, Plot plot, PlayerToggleSneakEvent event) {
         super(user, plot, event);
      }

      public boolean isCancelled() {
         return ((PlayerToggleSneakEvent)this.getHandleEvent()).isCancelled();
      }

      public void setCancelled(boolean b) {
         ((PlayerToggleSneakEvent)this.getHandleEvent()).setCancelled(b);
      }
   }
}
