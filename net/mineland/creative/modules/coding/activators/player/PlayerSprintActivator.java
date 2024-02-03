package net.mineland.creative.modules.coding.activators.player;

import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerSprintActivator extends Activator {
   public PlayerSprintActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_SPRINT;
   }

   public ItemData getIcon() {
      return new ItemData(Material.GOLD_BOOTS);
   }

   public static class Event extends GamePlayerEvent implements Cancellable {
      public Event(User user, Plot plot, PlayerToggleSprintEvent event) {
         super(user, plot, event);
      }

      public boolean isCancelled() {
         return ((PlayerToggleSprintEvent)this.getHandleEvent()).isCancelled();
      }

      public void setCancelled(boolean b) {
         ((PlayerToggleSprintEvent)this.getHandleEvent()).setCancelled(b);
      }
   }
}
