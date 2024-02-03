package net.mineland.creative.modules.coding.actions.player.inventory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.CodeUtils;
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

public class ActionGiveItems extends Action {
   public ActionGiveItems(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Player player = (Player)entity;
         PlayerInventory inventory = player.getInventory();
         ItemStack[] var5 = (ItemStack[])this.getVar("items", new ItemStack[0], gameEvent, entity);
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            ItemStack item = var5[var7];
            if (item != null) {
               inventory.addItem(new ItemStack[]{item});
            }
         }

      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_GIVE_ITEMS;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_INVENTORY;
   }

   public ItemData getIcon() {
      return new ItemData(Material.CHEST);
   }

   public boolean parseChest(ItemStack[] itemStacks) {
      this.putVar("items", CodeUtils.sortNotNull(itemStacks), false);
      return true;
   }
}
