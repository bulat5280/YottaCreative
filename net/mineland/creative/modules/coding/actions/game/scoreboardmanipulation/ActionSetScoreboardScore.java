package net.mineland.creative.modules.coding.actions.game.scoreboardmanipulation;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.PlotScoreboard;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionSetScoreboardScore extends Action {
   public ActionSetScoreboardScore(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_SET_SCOREBOARD_SCORE;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_SCOREBOARD_MANIPULATION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.BOOK);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         String name = this.getVarString("name", gameEvent, entity);
         String tag = this.getVarString("tag", gameEvent, entity);
         int value = this.getVar("value", 0, gameEvent, entity);
         PlotScoreboard scoreboard = gameEvent.getPlot().getCodeHandler().getScoreboard(name);
         if (scoreboard != null) {
            scoreboard.setScore(tag, value);
         }

      });
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("name", chestParser.getText(0));
      this.putVar("tag", chestParser.getText(1));
      this.putVar("value", chestParser.getNumber(0));
      return true;
   }
}
