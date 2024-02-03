package net.mineland.creative.modules.coding.activators.player;

import net.mineland.core.bukkit.modules.customdeaths.PlayerDeathMyEvent;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerDeathActivator extends Activator {
   public PlayerDeathActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_DEATH;
   }

   public ItemData getIcon() {
      return new ItemData(Material.REDSTONE);
   }

   public static class Event extends GamePlayerEvent {
      public Event(User user, Plot plot, PlayerDeathMyEvent event) {
         super(user, plot, event);
      }
   }
}
