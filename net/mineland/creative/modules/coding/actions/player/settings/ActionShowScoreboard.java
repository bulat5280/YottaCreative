package net.mineland.creative.modules.coding.actions.player.settings;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.core.bukkit.modules.user.User;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.PlotScoreboard;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionShowScoreboard extends Action {
   public ActionShowScoreboard(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         User user = User.getUser((CommandSender)entity);
         String name = this.getVarString("name", gameEvent, entity);
         PlotScoreboard plotScoreboard = gameEvent.getPlot().getCodeHandler().getScoreboard(name);
         if (plotScoreboard != null) {
            plotScoreboard.show(user);
         }

      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_SHOW_SCOREBOARD;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_SETTINGS;
   }

   public ItemData getIcon() {
      return new ItemData(Material.PAINTING);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("name", chestParser.getText(0));
      return true;
   }
}
