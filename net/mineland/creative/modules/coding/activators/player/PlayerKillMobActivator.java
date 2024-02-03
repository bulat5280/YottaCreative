package net.mineland.creative.modules.coding.activators.player;

import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.activators.ActivatorType;
import net.mineland.creative.modules.coding.events.EntityEvent;
import net.mineland.creative.modules.coding.events.GamePlayerEvent;
import net.mineland.creative.modules.coding.events.KillEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDeathEvent;
import ua.govnojon.libs.bukkitutil.ItemData;

public class PlayerKillMobActivator extends Activator {
   public PlayerKillMobActivator(Plot plot) {
      super(plot);
   }

   public ActivatorType getType() {
      return ActivatorType.PLAYER_KILL_MOB;
   }

   public ItemData getIcon() {
      return new ItemData(Material.MONSTER_EGG);
   }

   public static class Event extends GamePlayerEvent implements EntityEvent, KillEvent {
      public Event(User user, Plot plot, EntityDeathEvent event) {
         super(user, plot, event);
      }

      public Entity getEntity() {
         return ((EntityDeathEvent)this.getHandleEvent()).getEntity();
      }

      public Entity getKiller() {
         return this.getPlayer();
      }

      public Entity getVictim() {
         return this.getEntity();
      }
   }
}
