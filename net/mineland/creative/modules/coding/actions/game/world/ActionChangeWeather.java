package net.mineland.creative.modules.coding.actions.game.world;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.core.bukkit.modules.region.region.Region;
import net.mineland.core.bukkit.modules.region.region.flag.FlagType;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import net.mineland.creative.modules.creative.Plot;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionChangeWeather extends Action {
   public ActionChangeWeather(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_WEATHER;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_WORLD_MANIPULATION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.DOUBLE_PLANT);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      Plot plot = gameEvent.getPlot();
      Region plotRegion = plot.getPlotRegion();
      String weather = this.getVarString("weather", gameEvent, (Entity)null);
      if (plotRegion != null) {
         if (weather.equals("clear")) {
            plotRegion.setFlag(FlagType.WEATHER, FlagType.Values.Weathers.CLEAR.name());
         }

         if (weather.equals("stormy")) {
            plotRegion.setFlag(FlagType.WEATHER, FlagType.Values.Weathers.DOWNFALL.name());
         }

         if (weather.equals("default")) {
            plotRegion.setFlag(FlagType.WEATHER, FlagType.Values.Weathers.SERVER.name());
         }
      }

      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("weather", chestParser.getText(0));
      return true;
   }
}
