package net.mineland.creative.modules.coding.activators.player;

import net.mineland.core.bukkit.modules.customdeaths.PlayerDeathMyEvent;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.coding.events.KillEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerKillPlayerActivator extends Activator {
   public PlayerKillPlayerActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_KILL_PLAYER;
   }

   public ItemData getIcon() {
      return new ItemData(Material.SKULL_ITEM);
   }

   public static class Event extends GamePlayerEvent implements KillEvent {
      public Event(User user, Plot plot, PlayerDeathMyEvent event) {
         super(user, plot, event);
      }

      public Entity getKiller() {
         return this.getPlayer();
      }

      public Entity getVictim() {
         return ((PlayerDeathMyEvent)this.getHandleEvent()).getPlayer();
      }
   }
}
