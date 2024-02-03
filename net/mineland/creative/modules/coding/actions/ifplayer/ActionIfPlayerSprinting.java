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

public class ActionIfPlayerSprinting extends ActionIf {
   public ActionIfPlayerSprinting(Activator activator) {
      super(activator);
   }

   public boolean expression(List<Entity> selectedEntities, GameEvent gameEvent) {
      Iterator var3 = selectedEntities.iterator();

      Player player;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         Entity entity = (Entity)var3.next();
         player = (Player)entity;
      } while(player.isSprinting());

      return false;
   }

   public ActionType getType() {
      return ActionType.IF_PLAYER_GLIDING;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_PLAYER;
   }

   public ItemData getIcon() {
      return new ItemData(Material.GOLD_BOOTS);
   }
}
