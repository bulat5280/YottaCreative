package net.mineland.creative.modules.coding.actions.variable;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionVarSetItemName extends Action {
   public ActionVarSetItemName(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.VARIABLE_SET_ITEM_NAME;
   }

   public Action.Category getCategory() {
      return Action.Category.SET_VARIABLE;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("item", chestParser.getItem(0));
      this.putVar("name", chestParser.getText(1));
      return true;
   }

   public ItemData getIcon() {
      return new ItemData(Material.DIAMOND_SWORD);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         ItemStack item = (ItemStack)this.getVar("item", gameEvent, entity);
         item.getItemMeta().setDisplayName((String)this.getVar("name", gameEvent, entity));
      });
      return true;
   }
}
