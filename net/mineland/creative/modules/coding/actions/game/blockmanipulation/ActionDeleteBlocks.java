package net.mineland.creative.modules.coding.actions.game.blockmanipulation;

import com.sk89q.worldedit.EditSession;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.core.bukkit.modules.region.region.territory.Point;
import net.mineland.core.bukkit.modules.region.region.territory.Territory;
import net.mineland.core.bukkit.modules.region.region.territory.TerritoryType;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.WorldUtil;

public class ActionDeleteBlocks extends Action {
   public ActionDeleteBlocks(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_DELETE_BLOCKS;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_BLOCK_MANIPULATION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.BARRIER);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      if (!selectedEntities.isEmpty() && this.getActivator() != null) {
         Entity entity = (Entity)selectedEntities.get(0);
         Territory territory = this.getActivator().getPlot().getPlotTerritory();
         Location pos1 = this.getVarLocation("pos1", gameEvent, entity);
         if (pos1 == null) {
            return true;
         } else {
            Location pos2 = this.getVarLocation("pos2", gameEvent, entity);
            if (pos2 == null) {
               if (territory.isContains(pos1) && gameEvent.getPlot().getCodeHandler().removeBlocksLimit(1) && moduleCreative.tryWorldOperation(gameEvent.getPlot())) {
                  pos1.getBlock().setType(Material.AIR);
               }
            } else if (territory.isContains(pos1) && territory.isContains(pos2)) {
               Territory editTerritory = new Territory(TerritoryType.CUBE, new Point[]{new Point(pos1), new Point(pos2)});
               if (gameEvent.getPlot().getCodeHandler().removeBlocksLimit(editTerritory.getBlocksCount()) && moduleCreative.tryWorldOperation(gameEvent.getPlot())) {
                  WorldUtil.setBlocks(pos1, pos2, new ItemData(Material.AIR, 0), EditSession::flushQueue, moduleCreative.getWorldService());
               }
            }

            return true;
         }
      } else {
         return true;
      }
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("pos1", chestParser.getLocation(0));
      this.putVar("pos2", chestParser.getLocation(1));
      return true;
   }
}
