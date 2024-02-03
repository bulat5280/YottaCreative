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

public class ActionLaunchForward extends Action {
   public ActionLaunchForward(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         double multiplier = this.getVar("multiplier", 0.0D, gameEvent, entity);
         multiplier = multiplier > 10.0D ? 10.0D : (multiplier < -10.0D ? -10.0D : multiplier);
         Vector vector = entity.getLocation().getDirection().multiply(multiplier);
         entity.setVelocity(LocationUtil.fixVelocityVector(vector));
      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_LAUNCH_FORWARD;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_MOVEMENT;
   }

   public ItemData getIcon() {
      return new ItemData(Material.PISTON_BASE);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("multiplier", chestParser.getItem(0));
      return true;
   }
}
