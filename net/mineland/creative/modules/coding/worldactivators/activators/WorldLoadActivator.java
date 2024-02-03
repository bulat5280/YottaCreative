package net.mineland.creative.modules.coding.worldactivators.activators;

import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.coding.worldactivators.WorldActivator;
import net.mineland.creative.modules.coding.worldactivators.WorldActivatorType;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import ua.govnojon.libs.bukkitutil.ItemData;

public class WorldLoadActivator extends WorldActivator {
   public WorldLoadActivator(Plot plot) {
      super(plot);
   }

   public WorldActivatorType getType() {
      return WorldActivatorType.WORLD_LOAD;
   }

   public ItemData getIcon() {
      return new ItemData(Material.EMERALD_BLOCK);
   }

   public static class Event extends GamePlayerEvent {
      public Event(User user, Plot plot, org.bukkit.event.Event event) {
         super(user, plot, event);
      }
   }
}
