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
import ua.govnojon.libs.bukkitutil.ItemStackUtil;

public class ActionSetItems extends Action {
   public ActionSetItems(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Player player = (Player)entity;
         PlayerInventory inventory = player.getInventory();
         ItemStack[] items = (ItemStack[])this.getVar("items", new ItemStack[0], gameEvent, entity);

         for(int i = 0; i < items.length; ++i) {
            ItemStack itemStack = items[i];
            if (!ItemStackUtil.isNullOrAir(itemStack)) {
               inventory.setItem(i, itemStack);
            }
         }

      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_SET_ITEMS;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_INVENTORY;
   }

   public ItemData getIcon() {
      return new ItemData(Material.ENDER_CHEST);
   }

   public boolean parseChest(ItemStack[] itemStacks) {
      this.putVar("items", itemStacks, false);
      return true;
   }
}
