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

public class ActionIfPlayerHoldingItemMainHand extends ActionIf {
   public ActionIfPlayerHoldingItemMainHand(Activator activator) {
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
         ItemStack inMainHand = inventory.getItemInMainHand();
         ItemStack[] items = (ItemStack[])this.getVar("items", new ItemStack[0], gameEvent, entity);
         hasItem = false;
         ItemStack[] var10 = items;
         int var11 = items.length;

         for(int var12 = 0; var12 < var11; ++var12) {
            ItemStack item = var10[var12];
            if (!ItemStackUtil.isNullOrAir(item) && !ItemStackUtil.isNullOrAir(inMainHand)) {
               if (item.getAmount() == 1) {
                  if (ItemStackUtil.equalsIgnoreAmount(item, inMainHand)) {
                     hasItem = true;
                     break;
                  }
               } else if (ItemStackUtil.equalsIgnoreAmount(item, inMainHand) && item.getAmount() == inMainHand.getAmount()) {
                  hasItem = true;
                  break;
               }
            }
         }
      } while(hasItem);

      return false;
   }

   public ActionType getType() {
      return ActionType.IF_PLAYER_HOLDING_ITEM_MAIN;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_PLAYER;
   }

   public ItemData getIcon() {
      return new ItemData(Material.IRON_INGOT);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("items", chestParser.getItems(), false);
      return true;
   }
}
