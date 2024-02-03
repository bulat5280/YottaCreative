package net.mineland.creative.modules.coding.actions.game.codeutility;

import java.util.Iterator;
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

public class ActionStopLoop extends Action {
   public ActionStopLoop(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_STOP_LOOP;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_CODE_UTILITY;
   }

   public ItemData getIcon() {
      return new ItemData(Material.POISONOUS_POTATO);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      Iterator<Entity> iterator = selectedEntities.iterator();
      if (iterator.hasNext()) {
         String name = this.getVarString("name", gameEvent, (Entity)iterator.next());
         gameEvent.getPlot().getCodeHandler().stopGameLoop(name);
      }

      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("name", chestParser.getText(0));
      return true;
   }
}
