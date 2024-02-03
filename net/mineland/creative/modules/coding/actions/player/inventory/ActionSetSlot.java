package net.mineland.creative.modules.coding.actions.player.inventory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionSetSlot extends Action {
   public ActionSetSlot(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Player player = (Player)entity;
         PlayerInventory inventory = player.getInventory();
         int slot = this.getVar("slot", 0, gameEvent, entity);
         inventory.setHeldItemSlot(slot);
      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_SET_SLOT;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_INVENTORY;
   }

   public ItemData getIcon() {
      return new ItemData(Material.YELLOW_FLOWER);
   }

   public boolean parseChest(ItemStack[] itemStacks) {
      this.putVar("slot", itemStacks[0]);
      return true;
   }
}
