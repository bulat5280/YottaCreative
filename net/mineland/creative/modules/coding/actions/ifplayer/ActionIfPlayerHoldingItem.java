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
import ua.govnojon.libs.bukkitutil.ItemData;
import ua.govnojon.libs.bukkitutil.ItemStackUtil;

public class ActionIfPlayerHoldingItem extends ActionIf {
   public ActionIfPlayerHoldingItem(Activator activator) {
      super(activator);
   }

   public boolean expression(List<Entity> selectedEntities, GameEvent gameEvent) {
      Iterator var3 = selectedEntities.iterator();

      boolean hasItem;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         Entity entity = (Entity)var3.next();
         Player player = (Player)entity;
         PlayerInventory inventory = player.getInventory();
         ItemStack[] items = (ItemStack[])this.getVar("items", new ItemStack[0], gameEvent, entity);
         hasItem = false;
         ItemStack[] var9 = items;
         int var10 = items.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            ItemStack item = var9[var11];
            if (!ItemStackUtil.isNullOrAir(item)) {
               if (item.getAmount() == 1) {
                  if (item.isSimilar(inventory.getItemInMainHand()) || item.isSimilar(inventory.getItemInOffHand())) {
                     hasItem = true;
                     break;
                  }
               } else if (item.equals(inventory.getItemInMainHand()) || item.equals(inventory.getItemInOffHand())) {
                  hasItem = true;
                  break;
               }
            }
         }
      } while(hasItem);

      return false;
   }

   public ActionType getType() {
      return ActionType.IF_PLAYER_HOLDING_ITEM;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_PLAYER;
   }

   public ItemData getIcon() {
      return new ItemData(Material.CHEST);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("items", chestParser.getItems(), false);
      return true;
   }
}
