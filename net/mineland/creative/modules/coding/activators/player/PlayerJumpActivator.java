package net.mineland.creative.modules.coding.activators.player;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerJumpActivator extends Activator {
   public PlayerJumpActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_JUMP;
   }

   public ItemData getIcon() {
      return new ItemData(Material.RABBIT_FOOT);
   }

   public static class Event extends GamePlayerEvent implements Cancellable {
      public Event(User user, Plot plot, PlayerJumpEvent event) {
         super(user, plot, event);
      }

      public boolean isCancelled() {
         return ((PlayerJumpEvent)this.getHandleEvent()).isCancelled();
      }

      public void setCancelled(boolean b) {
         ((PlayerJumpEvent)this.getHandleEvent()).setCancelled(b);
      }
   }
}
