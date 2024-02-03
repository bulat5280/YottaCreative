package net.mineland.creative.modules.coding.actions.player.statistics;

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

public class ActionSetFoodLevel extends Action {
   public ActionSetFoodLevel(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Player player = (Player)entity;
         int food = this.getVar("food", 10, gameEvent, entity);
         player.setFoodLevel(food);
      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_SET_FOOD_LEVEL;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_PARAMETERS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.COOKED_CHICKEN);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("food", chestParser.getNumber(0));
      return true;
   }
}
