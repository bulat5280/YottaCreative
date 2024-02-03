package net.mineland.creative.modules.coding.actions.player.movement;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.LocationUtil;

public class ActionLaunchToward extends Action {
   public ActionLaunchToward(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         double x = this.getVar("x", 0.0D, gameEvent, entity);
         double y = this.getVar("y", 0.0D, gameEvent, entity);
         double z = this.getVar("z", 0.0D, gameEvent, entity);
         x = x > 10.0D ? 10.0D : (x < 10.0D ? -10.0D : x);
         y = y > 10.0D ? 10.0D : (y < 10.0D ? -10.0D : y);
         z = z > 10.0D ? 10.0D : (z < 10.0D ? -10.0D : z);
         Vector vector = new Vector(x, y, z);
         entity.setVelocity(LocationUtil.fixVelocityVector(vector));
      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_LAUNCH_TOWARD;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_MOVEMENT;
   }

   public ItemData getIcon() {
      return new ItemData(Material.PISTON_STICKY_BASE);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("x", chestParser.getNumber(0));
      this.putVar("y", chestParser.getNumber(1));
      this.putVar("z", chestParser.getNumber(2));
      return true;
   }
}
