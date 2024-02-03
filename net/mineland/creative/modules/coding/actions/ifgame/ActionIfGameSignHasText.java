package net.mineland.creative.modules.coding.actions.ifgame;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionIf;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionIfGameSignHasText extends ActionIf {
   public ActionIfGameSignHasText(Activator activator) {
      super(activator);
   }

   public boolean expression(List<Entity> selectedEntities, GameEvent gameEvent) {
      Iterator var3 = selectedEntities.iterator();

      String text;
      String[] lines;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         Entity entity = (Entity)var3.next();
         Location location = this.getVarLocation("location", gameEvent, entity);
         if (location == null) {
            return false;
         }

         text = this.getVarString("text", gameEvent, entity);
         if (location.getBlock().getType() != Material.SIGN_POST && location.getBlock().getType() != Material.WALL_SIGN) {
            return false;
         }

         lines = ((Sign)location.getBlock().getState()).getLines();
      } while(!Arrays.stream(lines).noneMatch((line) -> {
         return line.equals(text);
      }));

      return false;
   }

   public ActionType getType() {
      return ActionType.IF_GAME_SIGN_HAS_TEXT;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_GAME;
   }

   public ItemData getIcon() {
      return new ItemData(Material.SIGN);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("location", chestParser.getLocation(0));
      this.putVar("text", chestParser.getText(0));
      return true;
   }
}
