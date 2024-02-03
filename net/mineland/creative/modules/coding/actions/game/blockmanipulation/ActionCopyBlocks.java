package net.mineland.creative.modules.coding.actions.game.blockmanipulation;

import com.sk89q.worldedit.EditSession;
import java.util.Iterator;
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
import org.bukkit.util.Vector;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.WorldUtil;

public class ActionCopyBlocks extends Action {
   public ActionCopyBlocks(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_COPY_BLOCKS;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_BLOCK_MANIPULATION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.COBBLESTONE);
   }

   public boolean needPerm() {
      return true;
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      Iterator var5 = selectedEntities.iterator();

      while(var5.hasNext()) {
         Entity entity = (Entity)var5.next();
         Location pos1 = this.getVarLocation("pos1", gameEvent, entity);
         if (pos1 == null) {
            return true;
         }

         Location pos2 = this.getVarLocation("pos2", gameEvent, entity);
         if (pos2 == null) {
            return true;
         }

         Location from = this.getVarLocation("from", gameEvent, entity);
         if (from == null) {
            return true;
         }

         Location paste = this.getVarLocation("paste", gameEvent, entity);
         if (paste == null) {
            return true;
         }

         Territory territory = gameEvent.getPlot().getPlotTerritory();
         if (territory.isContains(pos1) && territory.isContains(pos2) && territory.isContains(paste)) {
            Territory editTerritory = new Territory(TerritoryType.CUBE, new Point[]{new Point(pos1), new Point(pos2)});
            Point point = editTerritory.getPoints()[0];
            Vector vector = point.toLocation().toVector().subtract(from.toVector());
            if (gameEvent.getPlot().getCodeHandler().removeBlocksLimit(editTerritory.getBlocksCount()) && moduleCreative.tryWorldOperation(gameEvent.getPlot())) {
               WorldUtil.copyBlocks(pos1, pos2, paste.clone().add(vector), gameEvent.getPlot(), EditSession::flushQueue, moduleCreative.getWorldService());
            }
         }
      }

      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("pos1", chestParser.getLocation(0));
      this.putVar("pos2", chestParser.getLocation(1));
      this.putVar("from", chestParser.getLocation(2));
      this.putVar("paste", chestParser.getLocation(3));
      return true;
   }
}
