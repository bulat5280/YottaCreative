package net.mineland.creative.modules.coding.actions.ifplayer;

import java.util.Iterator;
import java.util.List;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionIf;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import ua.govnojon.libs.bukkitutil.InventoryUtil;
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;

public class ActionIfPlayerHasAllItems extends ActionIf {
   public ActionIfPlayerHasAllItems(Activator activator) {
      super(activator);
   }

   public boolean expression(List<Entity> selectedEntities, GameEvent gameEvent) {
      Iterator var3 = selectedEntities.iterator();

      while(var3.hasNext()) {
         Entity entity = (Entity)var3.next();
         Player player = (Player)entity;
         PlayerInventory inventory = player.getInventory();
         ItemStack[] items = (ItemStack[])this.getVar("items", new ItemStack[0], gameEvent, entity);
         ItemStack[] var8 = items;
         int var9 = items.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            ItemStack item = var8[var10];
            if (!ItemStackUtil.isNullOrAir(item) && !InventoryUtil.isContainsInInventory(inventory, (ItemStack)item, item.getAmount())) {
               return false;
            }
         }
      }

      return true;
   }

   public ActionType getType() {
      return ActionType.IF_PLAYER_HAS_ALL_ITEMS;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_PLAYER;
   }

   public ItemData getIcon() {
      return new ItemData(Material.OBSIDIAN);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("items", chestParser.getItems(), false);
      return true;
   }
}
