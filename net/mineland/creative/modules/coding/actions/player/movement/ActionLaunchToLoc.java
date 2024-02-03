package net.mineland.creative.modules.coding.actions.player.movement;

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
import org.bukkit.util.Vector;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.LocationUtil;

public class ActionLaunchToLoc extends Action {
   public ActionLaunchToLoc(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Location loc = this.getVarLocation("location", gameEvent, entity);
         Location loc2 = entity.getLocation();
         double x = loc.getX() - loc2.getX();
         double y = loc.getY() - loc2.getY();
         double z = loc.getZ() - loc2.getZ();
         Vector vector = new Vector(x, y, z);
         entity.setVelocity(LocationUtil.fixVelocityVector(vector));
      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_LAUNCH_TOLOC;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_MOVEMENT;
   }

   public ItemData getIcon() {
      return new ItemData(Material.PAPER);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("location", chestParser.getLocation(0));
      return true;
   }
}
