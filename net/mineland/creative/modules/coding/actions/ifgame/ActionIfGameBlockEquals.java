package net.mineland.creative.modules.coding.actions.ifgame;

import java.util.Iterator;
import java.util.List;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.CodeUtils;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionIf;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionIfGameBlockEquals extends ActionIf {
   public ActionIfGameBlockEquals(Activator activator) {
      super(activator);
   }

   public boolean expression(List<Entity> selectedEntities, GameEvent gameEvent) {
      Iterator var3 = selectedEntities.iterator();

      boolean result;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         Entity entity = (Entity)var3.next();
         Location location = this.getVarLocation("location", gameEvent, entity);
         List<ItemData> blocks = this.getVars("blocks", gameEvent, entity);
         if (location == null) {
            return false;
         }

         Block targetBlock = location.getBlock();
         result = false;
         Iterator var9 = blocks.iterator();

         while(var9.hasNext()) {
            ItemData block = (ItemData)var9.next();
            if (targetBlock.getType() == block.getType() && targetBlock.getData() == block.getData()) {
               result = true;
               break;
            }
         }
      } while(result);

      return false;
   }

   public ActionType getType() {
      return ActionType.IF_GAME_BLOCK_EQUALS;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_GAME;
   }

   public ItemData getIcon() {
      return new ItemData(Material.BRICK);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("location", chestParser.getLocation(0));
      this.putVar("blocks", CodeUtils.sortByBlock(chestParser.getItems()));
      return true;
   }
}
