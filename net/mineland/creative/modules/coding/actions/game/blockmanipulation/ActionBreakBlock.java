package net.mineland.creative.modules.coding.actions.game.blockmanipulation;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionBreakBlock extends Action {
   public ActionBreakBlock(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_BREAK_BLOCK;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_BLOCK_MANIPULATION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.GLASS);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      Iterator var5 = selectedEntities.iterator();

      while(var5.hasNext()) {
         Entity entity = (Entity)var5.next();
         Location location = this.getVarLocation("location", gameEvent, entity);
         if (location != null && gameEvent.getPlot().getCodeHandler().removeBlocksLimit(1)) {
            location.getBlock().breakNaturally();
         }
      }

      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("location", chestParser.getLocation(0));
      return true;
   }
}
