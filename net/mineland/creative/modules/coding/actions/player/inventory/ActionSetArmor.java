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

public class ActionSetArmor extends Action {
   public ActionSetArmor(Activator activator) {
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
               switch(itemStack.getType()) {
               case CHAINMAIL_BOOTS:
               case DIAMOND_BOOTS:
               case GOLD_BOOTS:
               case IRON_BOOTS:
               case LEATHER_BOOTS:
                  inventory.setBoots(itemStack);
                  break;
               case LEATHER_LEGGINGS:
               case CHAINMAIL_LEGGINGS:
               case DIAMOND_LEGGINGS:
               case GOLD_LEGGINGS:
               case IRON_LEGGINGS:
                  inventory.setLeggings(itemStack);
                  break;
               case CHAINMAIL_CHESTPLATE:
               case DIAMOND_CHESTPLATE:
               case GOLD_CHESTPLATE:
               case IRON_CHESTPLATE:
               case LEATHER_CHESTPLATE:
               case ELYTRA:
                  inventory.setChestplate(itemStack);
                  break;
               default:
                  inventory.setHelmet(itemStack);
               }
            }
         }

      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_SET_ARMOR;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_INVENTORY;
   }

   public ItemData getIcon() {
      return new ItemData(Material.IRON_CHESTPLATE);
   }

   public boolean parseChest(ItemStack[] itemStacks) {
      this.putVar("items", itemStacks, false);
      return true;
   }
}
