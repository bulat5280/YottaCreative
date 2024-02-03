package net.mineland.creative.modules.coding.activators.player;

import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerFoodLevelChangeActivator extends Activator {
   public PlayerFoodLevelChangeActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_FOOD_LEVEL_CHANGE;
   }

   public ItemData getIcon() {
      return new ItemData(Material.COOKED_CHICKEN);
   }

   public static class Event extends GamePlayerEvent implements Cancellable {
      public Event(User user, Plot plot, FoodLevelChangeEvent event) {
         super(user, plot, event);
      }

      public boolean isCancelled() {
         return ((FoodLevelChangeEvent)this.getHandleEvent()).isCancelled();
      }

      public void setCancelled(boolean b) {
         ((FoodLevelChangeEvent)this.getHandleEvent()).setCancelled(b);
      }
   }
}
