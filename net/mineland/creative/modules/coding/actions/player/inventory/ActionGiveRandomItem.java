package net.mineland.creative.modules.coding.actions.player.inventory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
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
import ua.govnojon.libs.myjava.RandomUtil;

public class ActionGiveRandomItem extends Action {
   public ActionGiveRandomItem(Activator activator) {
      super(activator);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Player player = (Player)entity;
         PlayerInventory inventory = player.getInventory();
         List<ItemStack> items = this.getVars("items", gameEvent, entity);
         if (!items.isEmpty()) {
            inventory.addItem(new ItemStack[]{(ItemStack)RandomUtil.getRandomObject(items)});
         }
      });
      return true;
   }

   public ActionType getType() {
      return ActionType.PLAYER_GIVE_RANDOM_ITEM;
   }

   public Action.Category getCategory() {
      return Action.Category.PLAYER_INVENTORY;
   }

   public ItemData getIcon() {
      return new ItemData(Material.BLUE_SHULKER_BOX);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("items", CodeUtils.sortNotNull(chestParser.getItems()), false);
      return true;
   }
}
