package net.mineland.creative.modules.coding.actions.player.movement;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionTeleport extends Action {
   public ActionTeleport(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Location location = this.getVarLocation("location", gameEvent, entity);
         if (location != null) {
            entity.teleport(location);
         }

      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_TELEPORT;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_MOVEMENT;
   }

   public ItemData getIcon() {
      return new ItemData(Material.ENDER_PEARL);
   }

   public boolean parseChest(ItemStack[] itemStacks) {
      this.putVar("location", itemStacks[0]);
      return true;
   }
}
