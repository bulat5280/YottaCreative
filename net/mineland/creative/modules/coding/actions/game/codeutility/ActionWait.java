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

public class ActionWait extends Action {
   public ActionWait(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_WAIT;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_CODE_UTILITY;
   }

   public ItemData getIcon() {
      return new ItemData(Material.WATCH);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("duration", chestParser.getNumber(0));
      return true;
   }
}
