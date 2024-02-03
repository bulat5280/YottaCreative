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

public class ActionCreateScoreboard extends Action {
   public ActionCreateScoreboard(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_CREATE_SCOREBOARD;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_SCOREBOARD_MANIPULATION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.EMERALD);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         String name = this.getVarString("name", gameEvent, entity);
         String displayName = this.getVarString("display_name", gameEvent, entity);
         PlotScoreboard scoreboard = gameEvent.getPlot().getCodeHandler().getScoreboard(name);
         if (scoreboard == null && name != null) {
            scoreboard = new PlotScoreboard(name);
            if (displayName != null) {
               scoreboard.setDisplayName(displayName);
            }

            gameEvent.getPlot().getCodeHandler().addScoreboard(scoreboard);
         }

      });
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("name", chestParser.getText(0));
      this.putVar("display_name", chestParser.getText(1));
      return true;
   }
}
