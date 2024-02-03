package net.mineland.creative.modules.coding.activators.player;

import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerVoteActivator extends Activator {
   public PlayerVoteActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_VOTE;
   }

   public ItemData getIcon() {
      return new ItemData(Material.GOLD_NUGGET);
   }

   public static class Event extends GamePlayerEvent {
      public Event(User user, Plot plot, org.bukkit.event.Event event) {
         super(user, plot, event);
      }
   }
}
