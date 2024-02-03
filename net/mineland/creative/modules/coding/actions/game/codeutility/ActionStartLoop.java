package net.mineland.creative.modules.coding.actions.game.codeutility;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionStartLoop extends Action {
   public ActionStartLoop(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_START_LOOP;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_CODE_UTILITY;
   }

   public ItemData getIcon() {
      return new ItemData(Material.POTATO_ITEM);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         List<String> names = this.getVarsStrings("names", gameEvent, entity);
         names.forEach((name) -> {
            gameEvent.getPlot().getCodeHandler().startGameLoop(name, gameEvent);
         });
      });
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("names", chestParser.getTexts());
      return true;
   }
}
