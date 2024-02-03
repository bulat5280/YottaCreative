package net.mineland.creative.modules.coding.actions.player.movement;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.LocationUtil;

public class ActionLaunchUpwards extends Action {
   public ActionLaunchUpwards(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         double multiplier = this.getVar("multiplier", 0.0D, gameEvent, entity);
         multiplier = multiplier > 10.0D ? 10.0D : (multiplier < -10.0D ? -10.0D : multiplier);
         Vector vector = new Vector(0.0D, multiplier, 0.0D);
         entity.setVelocity(LocationUtil.fixVelocityVector(vector));
      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_LAUNCH_UPWARDS;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_MOVEMENT;
   }

   public ItemData getIcon() {
      return new ItemData(Material.SLIME_BLOCK);
   }

   public boolean parseChest(ItemStack[] itemStacks) {
      this.putVar("multiplier", itemStacks[0]);
      return true;
   }
}
