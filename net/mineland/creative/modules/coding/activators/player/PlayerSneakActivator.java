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

public class PlayerSneakActivator extends Activator {
   public PlayerSneakActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_SNEAK;
   }

   public ItemData getIcon() {
      return new ItemData(Material.CHAINMAIL_LEGGINGS);
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
