package net.mineland.creative.modules.coding.actions.game.blockmanipulation;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.sk89q.worldedit.EditSession;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.core.bukkit.modules.region.region.territory.Point;
import net.mineland.core.bukkit.modules.region.region.territory.Territory;
import net.mineland.core.bukkit.modules.region.region.territory.TerritoryType;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.CodeUtils;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Skull;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.meta.SkullMeta;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.WorldUtil;
import ua.govnojon.libs.myjava.MyObject;

public class ActionSetBlock extends Action {
   public ActionSetBlock(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_SET_BLOCK;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_BLOCK_MANIPULATION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.LOG);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      if (!selectedEntities.isEmpty() && this.getActivator() != null) {
         Entity entity = (Entity)selectedEntities.get(0);
         Territory territory = this.getActivator().getPlot().getPlotTerritory();
         List<ItemData> blocks = this.getVars("blocks", gameEvent, entity);
         if (blocks.size() == 0) {
            return true;
         } else {
            ItemData block = (ItemData)blocks.get(0);
            int data = this.getVar("data", block.getData(), gameEvent, entity);
            Location pos1 = this.getVarLocation("pos1", gameEvent, entity);
            if (pos1 == null) {
               return true;
            } else {
               Location pos2 = this.getVarLocation("pos2", gameEvent, entity);
               if (pos2 == null) {
                  if (territory.isContains(pos1) && gameEvent.getPlot().getCodeHandler().removeBlocksLimit(1) && moduleCreative.tryWorldOperation(gameEvent.getPlot())) {
                     switch(block.getType()) {
                     case SKULL_ITEM:
                        pos1.getBlock().setType(Material.SKULL);
                        Skull state = (Skull)pos1.getBlock().getState();
                        state.setSkullType(SkullType.values()[block.getData()]);
                        if (state.getSkullType().equals(SkullType.PLAYER)) {
                           Optional.ofNullable((SkullMeta)block.getOriginal().getItemMeta()).map(SkullMeta::getPlayerProfile).map(PlayerProfile::getGameProfile).ifPresent((gameProfile) -> {
                              (new MyObject(state)).setField("profile", gameProfile);
                           });
                        }

                        state.update();
                        break;
                     default:
                        pos1.getBlock().setType(block.getType());
                     }

                     pos1.getBlock().setData((byte)data);
                  }
               } else if (territory.isContains(pos1) && territory.isContains(pos2)) {
                  Territory editTerritory = new Territory(TerritoryType.CUBE, new Point[]{new Point(pos1), new Point(pos2)});
                  if (gameEvent.getPlot().getCodeHandler().removeBlocksLimit(editTerritory.getBlocksCount()) && moduleCreative.tryWorldOperation(gameEvent.getPlot())) {
                     WorldUtil.setBlocks(pos1, pos2, new ItemData(block.getType(), data), EditSession::flushQueue, moduleCreative.getWorldService());
                  }
               }

               return true;
            }
         }
      } else {
         return true;
      }
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("blocks", CodeUtils.sortByBlock(chestParser.getItems()));
      this.putVar("pos1", chestParser.getLocation(0));
      this.putVar("pos2", chestParser.getLocation(1));
      this.putVar("data", chestParser.getNumber(0));
      return true;
   }
}
