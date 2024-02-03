package net.mineland.creative.modules.coding.actions.ifentity;

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
import org.bukkit.entity.Entity;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionIfEntityIsNear extends ActionIf {
   public ActionIfEntityIsNear(Activator activator) {
      super(activator);
   }

   public boolean expression(List<Entity> selectedEntities, GameEvent gameEvent) {
      Iterator var3 = selectedEntities.iterator();

      Location entityLocation;
      Location location;
      double range;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         Entity entity = (Entity)var3.next();
         entityLocation = entity.getLocation();
         location = this.getVarLocation("location", gameEvent, entity);
         if (location == null) {
            return false;
         }

         range = this.getVar("range", 5.0D, gameEvent, entity);
      } while(!(entityLocation.distance(location) > range));

      return false;
   }

   public ActionType getType() {
      return ActionType.IF_PLAYER_NEAR;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_ENTITY;
   }

   public ItemData getIcon() {
      return new ItemData(Material.COMPASS);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("location", chestParser.getLocation(0));
      this.putVar("range", chestParser.getNumber(0));
      return true;
   }
}
