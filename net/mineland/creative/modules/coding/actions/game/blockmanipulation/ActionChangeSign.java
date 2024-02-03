package net.mineland.creative.modules.coding.actions.game.blockmanipulation;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionChangeSign extends Action {
   public ActionChangeSign(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_CHANGE_SIGN;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_BLOCK_MANIPULATION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.SIGN);
   }

   public boolean needPerm() {
      return true;
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Location location = this.getVarLocation("location", gameEvent, entity);
         if (location != null) {
            if (location.getBlock().getState() instanceof Sign) {
               int line = this.getVar("line", 1, gameEvent, entity);
               String text = this.getVarString("text", gameEvent, entity);
               line = line < 1 ? 1 : (line > 4 ? 4 : line);
               Sign sign = (Sign)location.getBlock().getState();
               sign.setLine(line - 1, text);
            }
         }
      });
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("location", chestParser.getLocation(0));
      this.putVar("line", chestParser.getNumber(0));
      this.putVar("text", chestParser.getText(0));
      return true;
   }
}
