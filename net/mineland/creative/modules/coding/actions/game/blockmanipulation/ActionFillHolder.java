package net.mineland.creative.modules.coding.actions.game.blockmanipulation;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.mineland.creative.modules.coding.ChestParser;
import net.mineland.creative.modules.coding.actions.Action;
import net.mineland.creative.modules.coding.actions.ActionType;
import net.mineland.creative.modules.coding.activators.Activator;
import net.mineland.creative.modules.coding.events.GameEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionFillHolder extends Action {
   public ActionFillHolder(Activator activator) {
      super(activator);
   }

   public ActionType getType() {
      return ActionType.GAME_FILL_HOLDER;
   }

   public Action.Category getCategory() {
      return Action.Category.GAME_BLOCK_MANIPULATION;
   }

   public ItemData getIcon() {
      return new ItemData(Material.CHEST);
   }

   public boolean run(List<Entity> selectedEntities, GameEvent gameEvent, int stackCounter, AtomicInteger callCounter) {
      selectedEntities.forEach((entity) -> {
         Location location = this.getVarLocation("location", gameEvent, entity);
         if (location != null) {
            if (location.getBlock().getState() instanceof Container) {
               Container container = (Container)location.getBlock().getState();
               List<ItemStack> items = this.getVars("items", gameEvent, entity);
               items.remove(0);
               items.forEach((itemStack) -> {
                  container.getInventory().addItem(new ItemStack[]{itemStack});
               });
            }
         }
      });
      return true;
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("location", chestParser.getLocation(0));
      this.putVar("items", chestParser.getItems(), false);
      return true;
   }
}
