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
import org.bukkit.inventory.PlayerInventory;
import ua.govnojon.libs.bukkitutil.ItemData;

public class ActionIfPlayerSlotEquals extends ActionIf {
   public ActionIfPlayerSlotEquals(Activator activator) {
      super(activator);
   }

   public boolean expression(List<Entity> selectedEntities, GameEvent gameEvent) {
      Iterator var3 = selectedEntities.iterator();

      boolean hasSlot;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         Entity entity = (Entity)var3.next();
         Player player = (Player)entity;
         PlayerInventory inventory = player.getInventory();
         hasSlot = false;
         List<Number> slots = this.getVars("slots", gameEvent, entity);
         if (!slots.isEmpty()) {
            Iterator var9 = slots.iterator();

            while(var9.hasNext()) {
               Number slot = (Number)var9.next();
               if (inventory.getHeldItemSlot() + 1 == slot.intValue()) {
                  hasSlot = true;
               }
            }
         }
      } while(hasSlot);

      return false;
   }

   public ActionType getType() {
      return ActionType.IF_PLAYER_SLOT_EQUALS;
   }

   public Action.Category getCategory() {
      return Action.Category.IF_PLAYER;
   }

   public ItemData getIcon() {
      return new ItemData(Material.YELLOW_FLOWER);
   }

   public boolean parseChest(ChestParser chestParser) {
      this.putVar("slots", chestParser.getNumbers());
      return true;
   }
}
