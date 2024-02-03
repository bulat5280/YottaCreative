package net.mineland.creative.modules.coding.actions.player.animations;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionSetSneaking extends Action {
   public ActionSetSneaking(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Player player = (Player)entity;
         player.setSneaking(true);
      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_SET_SNEAK;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_ANIMATIONS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.CHAINMAIL_LEGGINGS);
   }

   public boolean parseChest(ChestParser chestParser) {
      return false;
   }
}
