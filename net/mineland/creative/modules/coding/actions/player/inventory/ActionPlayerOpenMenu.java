package net.mineland.creative.modules.coding.actions.player.inventory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;

public class ActionPlayerOpenMenu extends Action {
   public ActionPlayerOpenMenu(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.PLAYER_OPEN_MENU;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_INVENTORY;
   }

   public boolean parseChest(ItemStack[] itemStacks) {
      this.putVar("items", itemStacks, false);
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("name", chestParser.getText(0));
      this.putVar("size", chestParser.getNumber(1));
      return true;
   }

   public ItemData getIcon() {
      return new ItemData(Material.CHEST);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int paramInt, AtomicInteger paramAtomicInteger) {
      selectedEntities.forEach((entity) -> {
         Player player = (Player)entity;
         String name = (String)this.getVar("name", gameEvent, entity);
         int size = (Integer)this.getVar("size", gameEvent, entity);
         Inventory inventory = Bukkit.createInventory((InventoryHolder)null, size, name);
         ItemStack[] items = (ItemStack[])((ItemStack[])this.getVar("items", new ItemStack[0], gameEvent, entity));

         for(int i = 0; i < items.length; ++i) {
            ItemStack itemStack = items[i];
            if (!ItemStackUtil.isNullOrAir(itemStack)) {
               inventory.setItem(i, itemStack);
            }
         }

         player.openInventory(inventory);
      });
      return true;
   }
}
