package net.mineland.creative.modules.coding.actions.ifgame;

import java.util.Iterator;
import java.util.List;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionIf;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionIfGameContainerHasItem extends ActionIf {
   public ActionIfGameContainerHasItem(Activator activator) {
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
         Location location = this.getVarLocation("location", gameEvent, entity);
         if (location == null) {
            return false;
         }

         if (!(location.getBlock().getState() instanceof Container)) {
            return false;
         }

         Inventory inventory = ((Container)location.getBlock().getState()).getInventory();
         ItemStack[] items = (ItemStack[])this.getVar("items", gameEvent, entity);
         hasItem = false;
         ItemStack[] var9 = items;
         int var10 = items.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            ItemStack item = var9[var11];
            if (inventory.contains(item, item.getAmount())) {
               hasItem = true;
               break;
            }
         }
      } while(hasItem);

      return false;
   }

   public ActionType getType() {
      return ActionType.IF_GAME_CONTAINER_HAS_ITEM;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_GAME;
   }

   public ItemData getIcon() {
      return new ItemData(Material.CHEST);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("location", chestParser.getLocation(0));
      this.putVar("items", chestParser.getItems(), false);
      return true;
   }
}
