package net.mineland.creative.modules.coding.actions.ifplayer;

import java.util.Iterator;
import java.util.List;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionIf;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionIfPlayerSwimming extends ActionIf {
   public ActionIfPlayerSwimming(Activator activator) {
      super(activator);
   }

   public boolean expression(List<Entity> selectedEntities, GameEvent gameEvent) {
      Iterator var3 = selectedEntities.iterator();

      while(var3.hasNext()) {
         Entity entity = (Entity)var3.next();
         Player player = (Player)entity;
         switch(player.getLocation().getBlock().getType()) {
         case WATER:
         case STATIONARY_WATER:
            break;
         default:
            return false;
         }
      }

      return true;
   }

   public ActionType getType() {
      return ActionType.IF_PLAYER_SWIMMING;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_PLAYER;
   }

   public ItemData getIcon() {
      return new ItemData(Material.WATER_BUCKET);
   }
}
