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

public class ActionChangeTime extends Action {
   public ActionChangeTime(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_TIME;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_WORLD_MANIPULATION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.WATCH);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      Plot plot = gameEvent.getPlot();
      Region plotRegion = plot.getPlotRegion();
      double time = this.getVar("time", 1.0D, gameEvent, (Entity)null);
      if (plotRegion != null) {
         if (time == 1.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v1.name());
         }

         if (time == 2.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v2.name());
         }

         if (time == 3.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v3.name());
         }

         if (time == 4.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v4.name());
         }

         if (time == 5.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v5.name());
         }

         if (time == 6.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v6.name());
         }

         if (time == 7.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v7.name());
         }

         if (time == 8.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v8.name());
         }

         if (time == 9.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v9.name());
         }

         if (time == 10.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v10.name());
         }

         if (time == 11.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v11.name());
         }

         if (time == 12.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v12.name());
         }

         if (time == 13.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v13.name());
         }

         if (time == 14.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v14.name());
         }

         if (time == 15.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v15.name());
         }

         if (time == 16.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v16.name());
         }

         if (time == 17.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v17.name());
         }

         if (time == 18.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v18.name());
         }

         if (time == 19.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v19.name());
         }

         if (time == 20.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v20.name());
         }

         if (time == 21.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v21.name());
         }

         if (time == 22.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v22.name());
         }

         if (time == 23.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v23.name());
         }

         if (time == 24.0D) {
            plotRegion.setFlag(FlagType.TIME, FlagType.Values.Times.v24.name());
         }
      }

      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("time", chestParser.getNumber(0));
      return true;
   }
}
